package com.example.java_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DailySales extends AppCompatActivity {
    private ImageButton returnBtn;
    DBsql myDB;
    SQLiteDatabase sqlDB;
    // 연도와 월을 저장할 전역 변수
    private int selectedYear = -1;
    private int selectedMonth = -1;
    private int selectedDate = -1;
    private RecyclerView recyclerView;
    private OrderRecordAdapter adapter;
    private List<OrderRecord> orderRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_sales);

        // 뒤로 가기 버튼 처리
        returnBtn = findViewById(R.id.backButton);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(DailySales.this, MainAdmin.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 초기에는 빈 목록을 표시
        orderRecords = new ArrayList<>();
        adapter = new OrderRecordAdapter(orderRecords);
        recyclerView.setAdapter(adapter);

        //DB 초기화
        myDB = new DBsql(this);
        sqlDB = myDB.getReadableDatabase();

        if (sqlDB == null) {
            Log.e("DailySales", "Failed to open the database");
        } else {
            Log.d("DailySales", "Database opened successfully");
        }

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                // 클릭시 선택된 날짜를 저장
                selectedYear = year;
                selectedMonth = month + 1; // 1을 더해서 저장 (월은 0부터 시작)
                selectedDate = dayOfMonth;

                // 선택된 연도, 월, 일로 주문 기록 불러오기
                getOrderRecordsForDate(selectedYear, selectedMonth, selectedDate);
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

    private List<OrderRecord> getOrderRecordsForDate(int year, int month, int day) {
        if (sqlDB == null) {
            Log.e("DailySales", "Database is null");
            return new ArrayList<>(); // 빈 목록 반환 또는 다른 적절한 처리
        }

        String selectedDateString = String.format("%04d-%02d-%02d", year, month, day);
        String query = "SELECT * FROM order_list WHERE date = ?;";
        String[] selectionArgs = {selectedDateString};
        Log.d("DailySales", "Query: " + query + " Args: " + Arrays.toString(selectionArgs));
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
            Log.e("DailySales", "Cursor is null or empty");
        }
        return orderRecords;
    }
}
