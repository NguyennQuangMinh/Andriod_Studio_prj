package com.example.iot_android_ytbmeigroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class main_activity extends AppCompatActivity {
    private RelativeLayout kv1, kv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kv1 = findViewById(R.id.KV1);
        kv2 = findViewById(R.id.KV2);

        kv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x1 = new Intent(main_activity.this, khu_vuc_1.class);
                startActivity(x1);
            }
        });

        kv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x2 = new Intent(main_activity.this, khu_vuc_2.class);
                startActivity(x2);
            }
        });
    }
}