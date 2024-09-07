package com.example.loginfirebasemqttcontrolleddht11.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.example.loginfirebasemqttcontrolleddht11.DangNhapActivity;
import com.example.loginfirebasemqttcontrolleddht11.MainActivity;
import com.example.loginfirebasemqttcontrolleddht11.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoiMatKhauFragment extends Fragment {
    private EditText pass1, pass2;
    private TextView back, doimatkhau;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doimatkhau, container, false);

        // Ánh xạ
        pass1 = view.findViewById(R.id.edtPassWordDMK);
        pass2 = view.findViewById(R.id.edtPassWordDMK2);
        back = view.findViewById(R.id.txtBackDMK);
        doimatkhau = view.findViewById(R.id.txtDoiMatKhau);

        // Lấy SharedPreferences
        sharedpreferences = requireActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // Set up Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Sự kiện đổi mật khẩu
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doimatkhau();
            }
        });

        // Sự kiện quay lại
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        // Handle back button press
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Intent intent = new Intent(requireActivity(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                }
        );

        return view;
    }

    private void doimatkhau() {
        String updatePass1 = pass1.getText().toString().trim();
        String updatePass2 = pass2.getText().toString().trim();

        // Kiểm tra mật khẩu
        if (TextUtils.isEmpty(updatePass1) || TextUtils.isEmpty(updatePass2)) {
            Toast.makeText(requireActivity(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra khớp mật khẩu
        if (!updatePass1.equals(updatePass2)) {
            Toast.makeText(requireActivity(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật mật khẩu
        user.updatePassword(updatePass1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    clearData();
                    Intent it = new Intent(requireActivity(), DangNhapActivity.class);
                    startActivity(it);
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireActivity(), "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
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
