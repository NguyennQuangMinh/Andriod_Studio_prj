package com.example.login_firebase_mail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login_firebase_mail.QuanLyTaiKhoan.LoginActivity;
import com.example.login_firebase_mail.QuanLyTaiKhoan.RegisterActivity;
import com.example.login_firebase_mail.QuanLyTaiKhoan.UpdatePassActivity;

public class MainActivity extends AppCompatActivity {

    private Button doimatkhau, logout, themtaikhoan;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themtaikhoan = findViewById(R.id.btnCreate);
        doimatkhau = findViewById(R.id.btnDoiMatKhau);
        logout = findViewById(R.id.btnLogOut);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        themtaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(it);
            }
        });
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, UpdatePassActivity.class);
                startActivity(it);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });
    }
    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
}