package com.example.login_firebase_mail.QuanLyTaiKhoan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_firebase_mail.MainActivity;
import com.example.login_firebase_mail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailedit, passedit1, passedit2;
    private Button Register, Back;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailedit = findViewById(R.id.email);
        passedit1 = findViewById(R.id.password1);
        passedit2 = findViewById(R.id.password2);
        Register = findViewById(R.id.btnregister);
        Back = findViewById(R.id.btnBack);


        mAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }

    private void register() {
        String email = emailedit.getText().toString();
        String pass1 = passedit1.getText().toString();
        String pass2 = passedit2.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass1) || TextUtils.isEmpty(pass2)) {
            Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass1.equals(pass2)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(it);
                    finish(); // Close the RegisterActivity after starting LoginActivity
                } else {
                    Toast.makeText(getApplicationContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
