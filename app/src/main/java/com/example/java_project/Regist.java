package com.example.java_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.widget.ImageButton;

public class Regist extends Activity{
    private EditText id, password, Email, nickname;
    private Button regist_bnt;
    private ImageButton returnBtn2;
    SQLiteDatabase sqlDB;
    DBsql myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);

        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        nickname = findViewById(R.id.nickname);
        Email = findViewById(R.id.Email);
        regist_bnt = findViewById(R.id.regist_bnt);
        returnBtn2 = findViewById(R.id.returnBtn2);

        myDB = new DBsql(this);
        returnBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Regist.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        regist_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = id.getText().toString();    // 아이디
                String userPass = password.getText().toString();    // 비밀번호
                String userEmail = Email.getText().toString();  // 이메일
                String userNickname = nickname.getText().toString();    //닉네임

                // 어떤 하나라도 비어있으면 토스트 메시지를 표시하고 메소드 종료
                if (userID.isEmpty() || userPass.isEmpty() || userEmail.isEmpty() || userNickname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // 데이터베이스에 회원 정보 삽입
                    sqlDB = myDB.getWritableDatabase();
                    try {
                        sqlDB.execSQL("INSERT INTO member (username, nickname, userpassword, email) VALUES " +
                                "('" + userID + "','" + userNickname + "','" + userPass + "','" + userEmail + "');");
                        Log.d("RegistActivity", "회원 등록 성공");
                        Toast.makeText(getApplicationContext(), "회원 등록 성공", Toast.LENGTH_SHORT).show();
                        // 회원 등록 성공하면 메인 화면으로 돌아가기
                        Intent intent = new Intent(Regist.this, MainActivity.class);
                        startActivity(intent);
                    } catch (SQLiteException e) {
                        Log.d("RegistActivity", "회원 등록 실패: " + e);
                        Toast.makeText(getApplicationContext(), "이미 존재하는 ID 입니다.", Toast.LENGTH_SHORT).show();
                    } finally {
                        // 사용 후 반드시 닫아주어야 합니다.
                        sqlDB.close();
                    }
                }
            }
        });
    }
}
