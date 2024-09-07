package com.example.loginfirebasemqttcontrolleddht11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.loginfirebasemqttcontrolleddht11.fragment.DoiMatKhauFragment;
import com.example.loginfirebasemqttcontrolleddht11.fragment.HomeFragment;
import com.example.loginfirebasemqttcontrolleddht11.fragment.KhuVuc1Fragment;
import com.example.loginfirebasemqttcontrolleddht11.fragment.KhuVuc2Fragment;
import com.example.loginfirebasemqttcontrolleddht11.fragment.ThemTaiKhoanFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_THEMTAIKHOAN = 1;
    private static final int FRAGMENT_DOIMATKHAU = 2;

    private int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//        HomeFragment homeFragment = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, homeFragment);
//        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.nav_home) {
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        } else if ( id == R.id.nav_themtaikhoan) {
            if(mCurrentFragment != FRAGMENT_THEMTAIKHOAN){
                replaceFragment(new ThemTaiKhoanFragment());
                mCurrentFragment = FRAGMENT_THEMTAIKHOAN;
            }
        } else if ( id == R.id.nav_doimatkhau) {
            if(mCurrentFragment != FRAGMENT_DOIMATKHAU){
                replaceFragment(new DoiMatKhauFragment());
                mCurrentFragment = FRAGMENT_DOIMATKHAU;
            }
        } else if ( id == R.id.nav_dangxuat) {
            // Clear login data before logging out
            clearData();
            // Navigate to DangNhapActivity
            Intent intent = new Intent(this, DangNhapActivity.class);
            startActivity(intent);
            // Finish MainActivity so that the user cannot return to it
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
    // Xóa dữ liệu đăng nhập
    private void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    // chuyển flagment home sang flagment khu vuc 1
    public void gotoKhuVuc1() {
        KhuVuc1Fragment khuVuc1Fragment = new KhuVuc1Fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, khuVuc1Fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // chuyển flagment home sang flagment khu vuc 2
    public void gotoKhuVuc2() {
        KhuVuc2Fragment khuVuc2Fragment = new KhuVuc2Fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, khuVuc2Fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}