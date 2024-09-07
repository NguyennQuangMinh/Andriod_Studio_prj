package com.example.loginfirebasemqttcontrolleddht11.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginfirebasemqttcontrolleddht11.MainActivity;
import com.example.loginfirebasemqttcontrolleddht11.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ThemTaiKhoanFragment extends Fragment {
    private EditText emailedit, passedit1, passedit2;
    private TextView them, back;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_themtaikhoan, container, false);

        // Initialize the views
        emailedit = view.findViewById(R.id.edtEmailThem);
        passedit1 = view.findViewById(R.id.edtPassWordThem);
        passedit2 = view.findViewById(R.id.edtPassWordThem2);
        them = view.findViewById(R.id.txtThem);
        back = view.findViewById(R.id.txtBackThem);


        // Set up Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // su kien them
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them();
            }
        });

        // su kien back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                // Optionally, you can finish the current activity if you want to close the fragment's hosting activity
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

    //xu ly su kien them
    private void them() {
        String email = emailedit.getText().toString();
        String pass1 = passedit1.getText().toString();
        String pass2 = passedit2.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass1) || TextUtils.isEmpty(pass2)) {
            Toast.makeText(getContext(), "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass1.equals(pass2)) {
            Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                    // Replace the current fragment with HomeFragment
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, new HomeFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
