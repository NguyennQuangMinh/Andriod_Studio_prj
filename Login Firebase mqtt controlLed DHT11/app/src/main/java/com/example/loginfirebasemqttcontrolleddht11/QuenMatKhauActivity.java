package com.example.loginfirebasemqttcontrolleddht11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class QuenMatKhauActivity extends AppCompatActivity {
    private EditText emailqmkedit;
    private TextView gui,back;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quen_mat_khau);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        emailqmkedit = findViewById(R.id.edtEmailQMK);
        gui = findViewById(R.id.txtGui);
        back = findViewById(R.id.txtBackQMK);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(QuenMatKhauActivity.this, DangNhapActivity.class);
                startActivity(it);
            }
        });

        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPass();
            }
        });

    }

    private void ForgotPass() {
        String email;
        email = emailqmkedit.getText().toString();

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
                    Intent it = new Intent(QuenMatKhauActivity.this, DangNhapActivity.class);
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