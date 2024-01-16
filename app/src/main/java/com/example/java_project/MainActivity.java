package com.example.java_project;

import android.app.Activity;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import android.database.Cursor;

public class MainActivity extends Activity {
    private EditText id, password;
    private Button login, regist_bnt;
    SQLiteDatabase sqlDB;
    DBsql myDB;
    String user_password,user,nickname,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        regist_bnt = findViewById(R.id.regist_bnt);

        // DBsql 객체 초기화
        myDB = new DBsql(this);

        regist_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MainActivity.this, Regist.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "예외처리: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = id.getText().toString();
                String user_pass = password.getText().toString();
                //어드민 로그인
                if ("admin".equals(user_id) && "1234".equals(user_pass)) {
                    Intent intent = new Intent(MainActivity.this, MainAdmin.class);
                    startActivity(intent);
                    return; // 아래의 로그인 처리 코드를 실행하지 않도록 리턴
                }

                Cursor cursor;
                sqlDB = myDB.getReadableDatabase();

                try {
                    cursor = sqlDB.rawQuery("SELECT * FROM member WHERE username ='"+user_id+"'", null);

                    if (cursor.moveToFirst()) {
                        // 결과가 있을 때만 데이터를 가져옴
                        user_password = cursor.getString(3);
                        user = cursor.getString(2);
                        email = cursor.getString(4);
                        nickname = cursor.getString(1);

                        if (user_pass.equals(user_password)) {
                            Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MainPage.class);
                            intent.putExtra("userID", user);
                            intent.putExtra("userPass", user_password);
                            intent.putExtra("nickname", nickname);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }
                        else {
                            // 결과가 없을 때 처리
                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 결과가 없을 때 처리
                        Toast.makeText(getApplicationContext(), "존재하지 않는 ID입니다.", Toast.LENGTH_SHORT).show();
                    }

                } catch (SQLiteException e) {
                    Log.e("MainActivity", "SQLite 오류", e);
                    Toast.makeText(getApplicationContext(), "SQLite 오류 발생", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e2){
                    Log.e("MainActivity", "NullPointerException 발생", e2);
                    Toast.makeText(getApplicationContext(), "NullPointerException 발생", Toast.LENGTH_SHORT).show();
                } catch (Exception e3) {
                    Log.e("MainActivity", "알 수 없는 오류 발생", e3);
                    e3.printStackTrace(); // 요류 트레이스하는 코드
                    Toast.makeText(getApplicationContext(), "알 수 없는 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

