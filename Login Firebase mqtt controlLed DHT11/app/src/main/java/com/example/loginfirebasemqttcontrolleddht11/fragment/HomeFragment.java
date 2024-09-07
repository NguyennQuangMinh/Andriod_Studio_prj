package com.example.loginfirebasemqttcontrolleddht11.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginfirebasemqttcontrolleddht11.MainActivity;
import com.example.loginfirebasemqttcontrolleddht11.R;

public class HomeFragment extends Fragment {
    private RelativeLayout kv1, kv2;
    private MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        kv1 = view.findViewById(R.id.KV1);
        kv2 = view.findViewById(R.id.KV2);
        mainActivity = (MainActivity) getActivity();

        kv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.gotoKhuVuc1();
            }
        });

        kv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.gotoKhuVuc2();
            }
        });


        return view;
    }
}
