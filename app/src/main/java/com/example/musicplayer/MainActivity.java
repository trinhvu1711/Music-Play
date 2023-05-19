package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.musicplayer.domain.User;
import com.example.musicplayer.fragment.HomeFragment;
import com.example.musicplayer.fragment.SearchFragment;
import com.example.musicplayer.fragment.SettingFragment;
import com.example.musicplayer.fragment.SongListFragment;
import com.example.musicplayer.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
//        System.out.println("------------------");
//        System.out.println(user.getPhone());

//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, new HomeFragment())
                                        .commit();
                                return true;
                            case R.id.navigation_search:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, new SearchFragment())
                                        .commit();
                                return true;
                            case R.id.navigation_user:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, new UserFragment())
                                        .commit();
                                return true;
                        }
                        return false;
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();
    }
}