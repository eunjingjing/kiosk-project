package com.example.java_project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        Button DailyBtn = findViewById(R.id.MainOrderbtn);
        Button MonthlyBtn = findViewById(R.id.Mypagebtn);
        Button logoutBtn = findViewById(R.id.Logoutbtn);

        DailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "일별 매출" 버튼 클릭 시 수행할 동작 정의
                // 예를 들어 Order 페이지로 이동하는 코드를 여기에 작성
                Intent intent = new Intent(MainAdmin.this, DailySales.class);
                startActivity(intent);
            }
        });

        MonthlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "월별 매출" 버튼 클릭 시 수행할 동작 정의
                // 예를 들어 MonthlySales 페이지로 이동하는 코드를 여기에 작성
                Intent intent = new Intent(MainAdmin.this, MonthlySales.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "홈으로" 버튼 클릭 시 수행할 동작 정의
                // 예를 들어 로그아웃 및 MainActivity로 이동하는 코드를 여기에 작성
                Intent intent = new Intent(MainAdmin.this, MainActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티를 종료
            }
        });
    }
}
