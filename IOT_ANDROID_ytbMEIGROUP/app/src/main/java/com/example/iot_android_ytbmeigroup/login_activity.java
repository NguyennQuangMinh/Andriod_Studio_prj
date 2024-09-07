package com.example.iot_android_ytbmeigroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class login_activity extends AppCompatActivity {

    private EditText user, pass;
    private RelativeLayout dangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.edus);
        pass = findViewById(R.id.edps);
        dangnhap = findViewById(R.id.btn);
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getText().toString().equals("admin") && pass.getText().toString().equals("123"))
                {
                    Intent x = new Intent(login_activity.this, main_activity.class);
                    startActivity(x);
                }
                else
                {
                    Toast.makeText(login_activity.this, "sai tai khoan hoac mat khau!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}