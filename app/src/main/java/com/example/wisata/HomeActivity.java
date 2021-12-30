package com.example.wisata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wisata.ui.favorit.FavoritFragment;
import com.example.wisata.ui.home.HomeFragment;
import com.example.wisata.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

@SuppressLint("NonConstantResourceId")
public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView btn_navigasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_navigasi = findViewById(R.id.btn_nav);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frm_home, new HomeFragment())
                .commit();

        BottomNavigationView.OnNavigationItemSelectedListener navigasi =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.favorit:
                        fragment = new FavoritFragment();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frm_home, fragment).commit();
                return true;
            }
        };
        btn_navigasi.setOnNavigationItemSelectedListener(navigasi);
    }
}