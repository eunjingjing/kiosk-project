package com.example.java_project;

import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends Activity {
    private Button MainOrderbtn, Mypagebtn, Logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        MainOrderbtn = findViewById(R.id.MainOrderbtn);
        Mypagebtn = findViewById(R.id.Mypagebtn);
        Logoutbtn = findViewById(R.id.Logoutbtn);
        Intent intent =getIntent();
        String user_id=intent.getStringExtra("userID");
        String userPass=intent.getStringExtra("userPass");
        String email=intent.getStringExtra("email");
        String nickname=intent.getStringExtra("nickname");

        MainOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 주문 화면으로 이동
                Intent intent = new Intent(MainPage.this, OrderPage.class);
                intent.putExtra("userID", user_id);
                startActivity(intent);
            }
        });

        Mypagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 마이페이지 화면으로 이동
                Intent intent = new Intent(MainPage.this, UserPage.class);
                intent.putExtra("userID", user_id);
                intent.putExtra("userPass", userPass);
                intent.putExtra("nickname", nickname);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        Logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
