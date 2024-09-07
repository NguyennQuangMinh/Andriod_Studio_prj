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

import com.example.login_firebase_mail.MainActivity;
import com.example.login_firebase_mail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassActivity extends AppCompatActivity {

    private EditText pass1, pass2;
    private Button back, changepass;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;
    private FirebaseAuth mAuth;
    private FirebaseUser user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);

        pass1 = findViewById(R.id.changepassword1);
        pass2 = findViewById(R.id.changepassword2);
        changepass = findViewById(R.id.btnChangePass);
        back = findViewById(R.id.btnback1);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        user  = FirebaseAuth.getInstance().getCurrentUser();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(UpdatePassActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePass();
            }
        });
    }

    private void UpdatePass() {
        String updatePass1 = pass1.getText().toString().trim();
        String updatePass2 = pass2.getText().toString().trim();

        // Check if either password field is empty
        if (TextUtils.isEmpty(updatePass1) || TextUtils.isEmpty(updatePass2)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!updatePass1.equals(updatePass2)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update password
        user.updatePassword(updatePass1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            clearData();
                            Intent it = new Intent(UpdatePassActivity.this, LoginActivity.class);
                            startActivity(it);
                            finish(); // Close the RegisterActivity after starting LoginActivity
                        } else {
                            Toast.makeText(getApplicationContext(), "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
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