//package com.example.java_project;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.ArrayAdapter;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class OrderPage2 extends AppCompatActivity {
//    private ListView menuListView;
//    private Button checkMenuButton;
//    private ImageButton returnBtn;
//    private String selectedMenu; // 이전 화면에서 전달된 선택한 메뉴
//    private String selectedBread; // 이전 화면에서 전달된 선택한 빵
//    private String selectedSauce; // 이전 화면에서 전달된 선택한 소스
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.order_page2);
//
//        // 이전 선택한 메뉴 가져오기
//        selectedMenu = getIntent().getStringExtra("selectedMenu");
//        if (selectedMenu == null) {
//            // 예외 처리 코드: 선택한 메뉴가 없을 때
//            Log.e("OrderPage2", "No selected menu provided.");
//            // 추가적인 예외 처리 또는 기본 메뉴 설정 등을 수행할 수 있습니다.
//        }
//
//        menuListView = findViewById(R.id.MenuList);
//        checkMenuButton = findViewById(R.id.CheckMenuBtn);
//        returnBtn = findViewById(R.id.returnBtn);
//
//        // 빵 종류 목록
//        String[] breadOptions = {"위트", "허니오트", "플랫브래드"};
//
//        // ArrayAdapter를 사용하여 Spinner에 빵 목록을 연결
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, breadOptions);
//        menuListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // CHOICE_MODE_SINGLE로 변경
//        menuListView.setAdapter(adapter);
//
//        returnBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 현재 화면을 종료하고 이전 화면으로 돌아가기
//                finish();
//            }
//        });
//
//        checkMenuButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedPosition = menuListView.getCheckedItemPosition();
//                if (selectedPosition != ListView.INVALID_POSITION) {
//                    // 선택한 빵 정보 가져오기
//                    selectedBread = breadOptions[selectedPosition];
//
//                    // 이전 선택한 메뉴, 빵 정보와 함께 데이터 전송
//                    Intent intent = new Intent(OrderPage2.this, OrderPage3.class);
//                    intent.putExtra("selectedMenu", selectedMenu);
//                    intent.putExtra("selectedBread", selectedBread);
//                    startActivityForResult(intent, 1);
//                } else {
//                    // 아무 것도 선택하지 않았을 때 예외 처리
//                    Toast.makeText(getApplicationContext(), "빵을 선택해주세요.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 1 && resultCode == RESULT_OK) {
////            if (data != null) {
////                String selectedSauce = data.getStringExtra("selectedSauce");
////                if (selectedSauce != null) {
////                    Intent resultIntent = new Intent();
////                    resultIntent.putExtra("selectedMenu", selectedMenu);
////                    resultIntent.putExtra("selectedBread", selectedBread);
////                    resultIntent.putExtra("selectedSauce", selectedSauce);
////                    setResult(RESULT_OK, resultIntent);
////                    finish();
////                }
////            }
////        }
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1001 && resultCode == RESULT_OK) {
//            String selectedMenu = data.getStringExtra("selectedMenu");
//
//            // OrderPage3로 메뉴 값 전달
//            Intent intent = new Intent(OrderPage2.this, OrderPage3.class);
//            intent.putExtra("selectedMenu", selectedMenu);
//            startActivityForResult(intent, 1002);  // requestCode는 1002로 설정
//        }
//    }
//}
//
//

package com.example.java_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class OrderPage2 extends AppCompatActivity {
    private ListView breadListView;
    private ListView sauceListView;
    private Button checkMenuButton;
    private ImageButton returnBtn;
    private String selectedMenu; // 이전 화면에서 전달된 선택한 메뉴
    private String selectedBread; // 이전 화면에서 전달된 선택한 빵
    private String selectedSauce; // 이전 화면에서 전달된 선택한 소스
    // 빵 종류 목록
    private String[] breadOptions = {"위트", "허니오트", "플랫브래드"};

    // 소스 종류 목록
    private String[] sauceOptions = {"스위트 어니언", "마요네즈", "스위트 칠리", "스모크 바베큐"};
    private int[] drawableIds_brd = { R.drawable.wit, R.drawable.honey_oat, R.drawable.flat };
    private int[] drawableIds_srs = { R.drawable.sweet_onion, R.drawable.mayonnaise, R.drawable.chili, R.drawable.smoked_bbq };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page2);

        // 이전 선택한 메뉴 가져오기
        selectedMenu = getIntent().getStringExtra("selectedMenu");
        if (selectedMenu == null) {
            // 예외 처리 코드: 선택한 메뉴가 없을 때
            Log.e("OrderPage2", "No selected menu provided.");
            // 추가적인 예외 처리 또는 기본 메뉴 설정 등을 수행할 수 있습니다.
        }

        breadListView = findViewById(R.id.breadListView);
        sauceListView = findViewById(R.id.sauceListView);
        checkMenuButton = findViewById(R.id.CheckMenuBtn);
        returnBtn = findViewById(R.id.returnBtn);



        ListView listview1,  listview2;
        ListViewAdapter breadadapter, sauseadapter;

        // Adapter 생성
        breadadapter = new ListViewAdapter() ;
        sauseadapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview1 = (ListView) findViewById(R.id.breadListView);
        listview1.setAdapter(breadadapter);

        for(int i=0; i<3; i++){
            breadadapter.addItem(ContextCompat.getDrawable(this, drawableIds_brd[i]),
                    breadOptions[i], "") ;
        }

        // 리스트뷰 참조 및 Adapter달기
        listview2 = (ListView) findViewById(R.id.breadListView);
        listview2.setAdapter(sauseadapter);

        for(int i=0; i<4; i++){
            sauseadapter.addItem(ContextCompat.getDrawable(this, drawableIds_srs[i]),
                    sauceOptions[i], "") ;
        }

        /*// ArrayAdapter를 사용하여 ListView에 목록 연결
        ArrayAdapter<String> breadAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, breadOptions);
        ArrayAdapter<String> sauceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, sauceOptions);
*/
        breadListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // CHOICE_MODE_SINGLE로 변경
        breadListView.setAdapter(breadadapter);

        sauceListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // CHOICE_MODE_SINGLE로 변경
        sauceListView.setAdapter(sauseadapter);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderPage2.this, OrderPage.class);
                setResult(RESULT_OK, intent);
                finish(); // 현재 액티비티 종료
            }
        });

        checkMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedBreadPosition = breadListView.getCheckedItemPosition();
                int selectedSaucePosition = sauceListView.getCheckedItemPosition();

                if (selectedBreadPosition != ListView.INVALID_POSITION && selectedSaucePosition != ListView.INVALID_POSITION) {
                    // 선택한 빵, 소스 정보 가져오기
                    selectedBread = breadOptions[selectedBreadPosition];
                    selectedSauce = sauceOptions[selectedSaucePosition];

                    // 이전 선택한 메뉴, 빵, 소스 정보와 함께 데이터 전송
                    Intent intent = new Intent(OrderPage2.this, OrderPage.class);
                    intent.putExtra("selectedMenu", selectedMenu);
                    intent.putExtra("selectedBread", selectedBread);
                    intent.putExtra("selectedSauce", selectedSauce);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    // 빵과 소스를 모두 선택하지 않았을 때 예외 처리
                    Toast.makeText(getApplicationContext(), "빵과 소스를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

