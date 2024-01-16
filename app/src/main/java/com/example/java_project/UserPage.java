package com.example.java_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;

public class UserPage extends AppCompatActivity {
    private ImageButton returnBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page); // 사용자 정보가 나타날 화면으로 전환

        // 사용자 정보 표시를 위한 TextView 위젯 정의
        TextView name = findViewById(R.id.name);
        TextView id = findViewById(R.id.id);
        TextView password = findViewById(R.id.password);
        TextView Email = findViewById(R.id.Email);

        Intent intent =getIntent();
        String user_id=intent.getStringExtra("userID");
        String userPass=intent.getStringExtra("userPass");
        String email=intent.getStringExtra("email");
        String nickname=intent.getStringExtra("nickname");

        name.setText(nickname);
        id.setText(user_id);
        password.setText(userPass);
        Email.setText(email);

        returnBtn2 = findViewById(R.id.returnBtn2);

        returnBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(UserPage.this, MainPage.class);
                    intent.putExtra("userID", user_id);
                    intent.putExtra("userPass", userPass);
                    intent.putExtra("nickname", nickname);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
