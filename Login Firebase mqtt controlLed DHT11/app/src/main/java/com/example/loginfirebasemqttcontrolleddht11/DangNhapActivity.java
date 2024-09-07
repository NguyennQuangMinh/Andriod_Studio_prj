package com.example.loginfirebasemqttcontrolleddht11;

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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginfirebasemqttcontrolleddht11.fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhapActivity extends AppCompatActivity {
    private EditText emailedit, passedit;
    private TextView dangnhap,quenmatkhau;
    private CheckBox cbghinho;
    private FirebaseAuth mAuth;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangnhap);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        AnhXa();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        loadData();

        // Nếu thông tin đăng nhập đã được lưu và hộp kiểm nhớ được chọn, tự động đăng nhập
        if (sharedpreferences.getBoolean(REMEMBER, false)) {
            autoLogin();
        }
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        quenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DangNhapActivity.this, QuenMatKhauActivity.class);
                startActivity(it);
            }
        });

    }
    private void AnhXa(){
        emailedit = findViewById(R.id.edtEmail);
        passedit = findViewById(R.id.edtPassWord);
        dangnhap = findViewById(R.id.txtDangNhap);
        quenmatkhau = findViewById(R.id.txtQuenMatKhau);
        cbghinho = findViewById(R.id.cbGhiNho);
    }

    private void autoLogin() {
        String email = sharedpreferences.getString(USERNAME, "");
        String pass = sharedpreferences.getString(PASS, "");

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent it = new Intent(DangNhapActivity.this, MainActivity.class);
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
                    if (cbghinho.isChecked()) {
                        saveData(email, pass);
                    } else {
                        clearData();
                    }
                    Intent it = new Intent(DangNhapActivity.this, MainActivity.class);
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
        editor.putBoolean(REMEMBER, cbghinho.isChecked());
        editor.apply();
    }

    private void loadData() {
        if (sharedpreferences.getBoolean(REMEMBER, false)) {
            emailedit.setText(sharedpreferences.getString(USERNAME, ""));
            passedit.setText(sharedpreferences.getString(PASS, ""));
            cbghinho.setChecked(true);
        } else {
            cbghinho.setChecked(false);
        }
    }
}