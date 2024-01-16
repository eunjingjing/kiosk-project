package com.example.java_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonthlySales extends AppCompatActivity {
    SQLiteDatabase sqlDB;
    DBsql myDB;
    // 연도와 월을 저장할 전역 변수
    private int selectedYear = -1;
    private int selectedMonth = -1;
    private RecyclerView recyclerView;
    private OrderRecordAdapter adapter;
    private List<OrderRecord> orderRecords;
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_sales);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MonthlySales.this, MainAdmin.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        orderRecords = getOrderRecordsForMonth(selectedYear, selectedMonth);
        adapter = new OrderRecordAdapter(orderRecords);
        recyclerView.setAdapter(adapter);

        // DBsql 객체 초기화
        myDB = new DBsql(this);
        sqlDB = myDB.getReadableDatabase();

        if (sqlDB == null) {
            Log.e("MonthlySales", "Failed to open the database");
        } else {
            Log.d("MonthlySales", "Database opened successfully");
        }


        // 연도 Spinner 설정
        Spinner yearSpinner = findViewById(R.id.yearSpinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.years_array,
                android.R.layout.simple_spinner_item
        );
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        // 월 Spinner 설정
        Spinner monthSpinner = findViewById(R.id.monthSpinner);
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.months_array,
                android.R.layout.simple_spinner_item
        );
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        // 연도와 월을 선택했을 때의 동작
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 연도를 선택하면 해당 연도를 저장
                selectedYear = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                // 선택된 연도와 월이 유효한 경우 getOrderRecordsForMonth 함수 호출
                if (selectedYear != -1 && selectedMonth != -1) {
                    getOrderRecordsForMonth(selectedYear, selectedMonth);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 월을 선택하면 해당 월을 저장
                selectedMonth = position + 1; // 1부터 시작하는 인덱스를 월로 변환
                // 선택된 연도와 월이 유효한 경우 getOrderRecordsForMonth 함수 호출
                if (selectedYear != -1 && selectedMonth != -1) {
                    getOrderRecordsForMonth(selectedYear, selectedMonth);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        });
    }
    private void setupRecyclerView(List<OrderRecord> orderRecords) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        OrderRecordAdapter adapter = new OrderRecordAdapter(orderRecords);
        recyclerView.setAdapter(adapter);
    }
    private List<OrderRecord> getOrderRecordsForMonth(int year, int month) {
        if (sqlDB == null) {
            Log.e("MonthlySales", "Database is null");
            return new ArrayList<>(); // 빈 목록 반환 또는 다른 적절한 처리
        }

        String query = "SELECT * FROM order_list WHERE strftime('%Y-%m', date) = ?;";
        String[] selectionArgs = {String.format("%04d-%02d", year, month)};
        Log.d("MonthlySales", "Query: " + query + " Args: " + Arrays.toString(selectionArgs));
        Cursor cursor = sqlDB.rawQuery(query, selectionArgs);

        List<OrderRecord> orderRecords = getOrderRecordsFromCursor(cursor);
        // RecyclerView에 데이터 설정
        setupRecyclerView(orderRecords);

        return orderRecords;

    }


    private List<OrderRecord> getOrderRecordsFromCursor(Cursor cursor) {
        List<OrderRecord> orderRecords = new ArrayList<>();

        int usernameIndex = cursor.getColumnIndex("username");
        int menuIndex = cursor.getColumnIndex("menu");
        int breadIndex = cursor.getColumnIndex("bread");
        int sauceIndex = cursor.getColumnIndex("sause");
        int priceIndex = cursor.getColumnIndex("price");
        int dateIndex = cursor.getColumnIndex("date");

        if (usernameIndex >= 0 && menuIndex >= 0 && breadIndex >= 0
                && sauceIndex >= 0 && priceIndex >= 0 && dateIndex >= 0 && cursor != null && cursor.moveToFirst()) {
            do {
                OrderRecord orderRecord = new OrderRecord(
                        cursor.getString(usernameIndex),
                        cursor.getString(menuIndex),
                        cursor.getString(breadIndex),
                        cursor.getString(sauceIndex),
                        cursor.getInt(priceIndex),
                        cursor.getString(dateIndex)
                );
                orderRecords.add(orderRecord);
            } while (cursor.moveToNext());
        } else {
            // 컬럼이 존재하지 않는 경우에 대한 처리
            Log.e("Error", "One or more columns do not exist");
        }

        // Cursor를 닫아줍니다.
        if (cursor != null && cursor.moveToFirst()) {
            // Your existing code to retrieve data
            cursor.close();
        } else {
            Log.e("MonthlySales", "Cursor is null or empty");
        }
        return orderRecords;
    }
}
