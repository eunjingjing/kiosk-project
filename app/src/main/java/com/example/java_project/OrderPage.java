package com.example.java_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.util.Log;

import org.tensorflow.lite.Tensor;


public class OrderPage extends AppCompatActivity {
    private int[] prices = {6700, 5500, 7900, 7300, 7300, 7000, 5500, 6800, 7800, 7300, 4900, 7000};
    private String[] menuItems = {"이탈리안 BMT", "에그마요", "스테이크 & 치즈", "로스트 치킨", "K-바비큐",
            "머쉬룸", "쉬림프", "치킨 슬라이스", "로티세리 바비큐 치킨", "터키 베이컨",
            "베지", "스파이시 이탈리안"};
    private int[] drawableIds = {R.drawable.italianbmt, R.drawable.eggmayo, R.drawable.cube_stake,
            R.drawable.roastchicken, R.drawable.k_bbq, R.drawable.mushroom, R.drawable.shrimp,
            R.drawable.chickenslice, R.drawable.rotisseriebarbecuechicken, R.drawable.turkeybacon,
            R.drawable.veggie, R.drawable.spicyitalian};
    private Map<String, List<String>> selectedItemsMap = new HashMap<>(); // 각 메뉴에 대한 선택사항 저장
    private int total = 0;
    private TextView result;
    private Button OrderBtn;
    private Button Recommend;
    private ListView menuList;
    private ImageButton returnBtn;

    private SQLiteDatabase sqlDB;
    private DBsql myDB;
    private Intent intent;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        menuList = findViewById(R.id.MenuList);
        Recommend = findViewById(R.id.recommend);
        OrderBtn = findViewById(R.id.OrderBtn);
        result = findViewById(R.id.result);
        intent = getIntent();
        user_id = intent.getStringExtra("userID");
        Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_SHORT).show();

        ListView listview ;
        ListViewAdapter adapter;

        // db 생성
        myDB = new DBsql(this);

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.MenuList);
        listview.setAdapter(adapter);

        /*for(int i=0; i<12; i++){
            adapter.addItem(ContextCompat.getDrawable(this, drawableIds[i]),
                    menuItems[i], prices[i] + "원") ;
        }*/
        for(int i=0; i<12; i++){
            adapter.addItem(ContextCompat.getDrawable(this, drawableIds[i]),
                    menuItems[i], prices[i] + "원") ;
        }

        // 이전에 선택한 항목과 총 가격을 복원
        if (savedInstanceState != null) {
            total = savedInstanceState.getInt("totalPrice");
            Bundle selectedItemsBundle = savedInstanceState.getBundle("selectedItemsBundle");
            if (selectedItemsBundle != null) {
                for (String key : selectedItemsBundle.keySet()) {
                    selectedItemsMap.put(key, selectedItemsBundle.getStringArrayList(key));
                }
            }
            //updateTotalPrice();
        }

        menuList.setAdapter(adapter);

        returnBtn = findViewById(R.id.returnBtn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(OrderPage.this, MainPage.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(OrderPage.this, tensor.class);
                    intent.putExtra("selectedMenu", "");
                    startActivityForResult(intent, 1001);//
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = menuItems[position];
                //int itemPrice = prices[position];
                //total += itemPrice; // 메뉴 가격을 총 가격에 추가
                //updateTotalPrice(); // 업데이트된 총 가격을 화면에 반영

                Intent intent = new Intent(OrderPage.this, OrderPage2.class);
                intent.putExtra("selectedMenu", selectedItem);
                startActivityForResult(intent, 1001);
            }
        });

        OrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemsMap.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "메뉴를 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 데이터베이스에 주문 정보 저장
                    if (saveOrderToDatabase()) {
                        // 저장이 성공하면 화면 갱신
                        total = 0;
                        //updateTotalPrice();
                        Toast.makeText(getApplicationContext(), "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                        // MainPage로 이동
                        Intent orderPageIntent = new Intent(OrderPage.this, MainPage.class);
                        startActivity(orderPageIntent);
                    } else {
                        // 저장 실패 시 메시지 표시
                        Toast.makeText(getApplicationContext(), "주문 저장 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    // db 저장 코드
    private boolean saveOrderToDatabase() {
        // 데이터베이스에 저장
        sqlDB = myDB.getWritableDatabase();

        try {
            // 각 주문 정보를 순회하면서 데이터베이스에 저장
            for (Map.Entry<String, List<String>> entry : selectedItemsMap.entrySet()) {
                String menu = entry.getKey();
                List<String> options = entry.getValue();

                // 주문 정보를 ContentValues에 저장
                ContentValues values = new ContentValues();
                values.put("username", user_id); // 여기에 사용자명을 지정
                values.put("menu", menu);
                values.put("bread", options.get(0));
                values.put("sause", options.get(1));
                values.put("price", prices[findIndexUsingLoop(menuItems, menu)]);

                // 현재 날짜를 추가
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());
                values.put("date", currentDate);

                // 데이터베이스에 저장
                long result = sqlDB.insert("order_list", null, values);

                // 저장 결과 확인
                if (result == -1) {
                    return false; // 저장 실패
                }
            }

            return true; // 모든 주문 저장 성공
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false; // 저장 실패
        } finally {
            // 데이터베이스 닫기
            if (sqlDB != null) {
                sqlDB.close();
            }
        }
    }
    // 값 저장하기 위한 인덱스 찾는 반복문
    private int findIndexUsingLoop(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i; // 찾은 경우 해당 인덱스 반환
            }
        }
        return -1; // 찾지 못한 경우 -1 반환
    }

    private void updateTotalPrice(String selectedMenu) {
        int index = findIndexUsingLoop(menuItems, selectedMenu);
        if (index != -1) {
            int itemPrice = prices[index];
            total += itemPrice; // 메뉴 가격을 총 가격에 추가
            result.setText(total + " 원");
        } else {
            // 메뉴를 찾지 못한 경우에 대한 예외 처리
            Log.e("OrderPage", "Menu not found: " + selectedMenu);
        }
    }


    @Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && data != null) {
        //Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_SHORT).show();
        String selectedMenu = data.getStringExtra("selectedMenu");
        String selectedBread = data.getStringExtra("selectedBread");
        String selectedSauce = data.getStringExtra("selectedSauce");

        if (selectedMenu != null) {
            // 선택한 메뉴에 대한 선택사항을 저장할 리스트
            List<String> selectedOptions = new ArrayList<>();
            //Toast.makeText(getApplicationContext(), "리스트 생성", Toast.LENGTH_SHORT).show();

            // 선택한 빵 정보 처리
            if (selectedBread != null) {
                selectedOptions.add(selectedBread);
                //Toast.makeText(getApplicationContext(), "빵 저장", Toast.LENGTH_SHORT).show();
            }

            // 선택한 소스 정보 처리
            if (selectedSauce != null) {
                selectedOptions.add(selectedSauce);
                //Toast.makeText(getApplicationContext(), "소스 저장", Toast.LENGTH_SHORT).show();
            }

            // 선택한 메뉴와 선택사항을 맵에 저장
            selectedItemsMap.put(selectedMenu, selectedOptions);
            //Toast.makeText(getApplicationContext(), "최종 저장", Toast.LENGTH_SHORT).show();
        }

        // 총 주문 금액 업데이트
        updateTotalPrice(selectedMenu);
    } else {
        // 예외 처리: RESULT_OK가 아니거나 데이터가 없는 경우
        Log.e("OrderPage", "Error in onActivityResult: resultCode=" + resultCode);
    }
}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("totalPrice", total);

        Bundle selectedItemsBundle = new Bundle();
        for (String key : selectedItemsMap.keySet()) {
            selectedItemsBundle.putStringArrayList(key, new ArrayList<>(selectedItemsMap.get(key)));
        }
        outState.putBundle("selectedItemsBundle", selectedItemsBundle);
    }


}
