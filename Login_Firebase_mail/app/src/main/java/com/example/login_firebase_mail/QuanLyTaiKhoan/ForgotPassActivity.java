package com.example.login_firebase_mail.QuanLyTaiKhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_firebase_mail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {
    private EditText emailedit;
    private Button send,back;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        emailedit = findViewById(R.id.emailedit);
        send = findViewById(R.id.btnsend);
        back = findViewById(R.id.btnback);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ForgotPassActivity.this, LoginActivity.class);
                startActivity(it);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPass();
            }
        });

    }

    private void ForgotPass() {
        String email;
        email = emailedit.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Email send", Toast.LENGTH_SHORT).show();
                    clearData();
                    Intent it = new Intent(ForgotPassActivity.this, LoginActivity.class);
                    startActivity(it);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Email not send", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
}