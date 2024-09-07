package com.example.login_firebase_mail.QuanLyTaiKhoan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_firebase_mail.MainActivity;
import com.example.login_firebase_mail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailedit, passedit;
    private Button Login;
    private TextView ForgotPass;
    private CheckBox cbRemember;
    private FirebaseAuth mAuth;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailedit = findViewById(R.id.email);
        passedit = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnlogin);
        cbRemember = findViewById(R.id.cbRemember);
        ForgotPass = findViewById(R.id.txtForgotPass);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        loadData();

        // Nếu thông tin đăng nhập đã được lưu và hộp kiểm nhớ được chọn, tự động đăng nhập
        if (sharedpreferences.getBoolean(REMEMBER, false)) {
            autoLogin();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(it);
            }
        });
    }

    private void autoLogin() {
        String email = sharedpreferences.getString(USERNAME, "");
        String pass = sharedpreferences.getString(PASS, "");

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        });
    }

    private void login() {
        String email = emailedit.getText().toString();
        String pass = passedit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    if (cbRemember.isChecked()) {
                        saveData(email, pass);
                    } else {
                        clearData();
                    }
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                    finish(); // Đóng LoginActivity để người dùng không thể quay lại bằng nút back
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void saveData(String username, String pass) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASS, pass);
        editor.putBoolean(REMEMBER, cbRemember.isChecked());
        editor.apply();
    }

    private void loadData() {
        if (sharedpreferences.getBoolean(REMEMBER, false)) {
            emailedit.setText(sharedpreferences.getString(USERNAME, ""));
            passedit.setText(sharedpreferences.getString(PASS, ""));
            cbRemember.setChecked(true);
        } else {
            cbRemember.setChecked(false);
        }
    }
}
