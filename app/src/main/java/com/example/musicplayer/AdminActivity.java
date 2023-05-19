package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.musicplayer.fragment.CategoryManagerFragment;
import com.example.musicplayer.fragment.HomeFragment;
import com.example.musicplayer.fragment.SettingFragment;
import com.example.musicplayer.fragment.SongManagerFragment;
import com.example.musicplayer.fragment.UserFragment;
import com.example.musicplayer.fragment.UserManagerFragment;
import com.example.musicplayer.utilities.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import soup.neumorphism.NeumorphCardView;

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    MenuItem menuItemSong,menuItemCategory, menuItemUser;
    ImageButton btnAdd, btnBack;
    Fragment currentFragment, previousFragment;
    TextView tvTitle;
    String title;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
    }

    private void init() {
        tvTitle = findViewById(R.id.tvTitleAdmin);
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        menuItemSong = bottomNavigationView.getMenu().findItem(R.id.navigation_song);
        menuItemCategory = bottomNavigationView.getMenu().findItem(R.id.navigation_category);
        menuItemUser = bottomNavigationView.getMenu().findItem(R.id.navigation_user_maneger);

        Utility.setScrollText(tvTitle);

        fm = getSupportFragmentManager();
        //load Bottom navigation, show default Fragment (SongFragment)
        loadBottomNavigationView();
    }

    private void loadData() {
        previousFragment = currentFragment;
        currentFragment = fm.findFragmentById(R.id.container_admin);
        setTitle();
        setEvent();
    }

    private void setTitle(){
        String title = getResources().getString(R.string.list);
        if(currentFragment instanceof SongManagerFragment){
            title = getResources().getString(R.string.song_list);
            btnBack.setImageResource(R.drawable.ic_song);
        } else if (currentFragment instanceof CategoryManagerFragment) {
            title = getResources().getString(R.string.category_list);
            btnBack.setImageResource(R.drawable.ic_category);
        } else if (currentFragment instanceof UserManagerFragment) {
            title = getResources().getString(R.string.user_list);
            btnBack.setImageResource(R.drawable.ic_user_maneger);
        }else if (currentFragment instanceof UserFragment){
            btnBack.setImageResource(R.drawable.ic_user);
        }

        tvTitle.setText(title.trim());
    }

    private Fragment findCurrentFragment(){
        Fragment rs = new SongManagerFragment();
        Fragment songFragment = fm.findFragmentByTag("SongManagerFragment");
        Fragment categoryFragment = fm.findFragmentByTag("CategoryManagerFragment");
        Fragment userFragment = fm.findFragmentByTag("UserManagerFragment");

        if(songFragment != null && songFragment.isVisible()){
            rs = new SongManagerFragment();
        }
        if (categoryFragment != null && categoryFragment.isVisible()) {
            rs = new CategoryManagerFragment();
        }
        if(userFragment != null && userFragment.isVisible()){
            rs = new UserManagerFragment();
        }

        return rs;
    }

    private void forwardFragment(Fragment mFragment, String tag){
        fm.beginTransaction()
                .replace(R.id.container_admin, mFragment, tag)
                .commit();
        fm.executePendingTransactions();
        loadData();
    }

    private void loadBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_song:
                                forwardFragment(new SongManagerFragment(), "SongManagerFragment");
                                return true;
                            case R.id.navigation_category:
                                forwardFragment(new CategoryManagerFragment(), "CategoryManagerFragment");
                                return true;
                            case R.id.navigation_user_maneger:
                                forwardFragment(new UserManagerFragment(), "UserManagerFragment");
                                return true;
                            case R.id.navigation_user:
                                forwardFragment(new UserFragment(), "UserFragment");
                                return true;
                        }
                        return false;
                    }
                });

        forwardFragment(new SongManagerFragment(), "SongManagerFragment");
    }

    private void setEvent(){
        //        Chuyển sang Add Activity, check Fragment hiện tại để biết nên chuyển sang Activity phù hợp
        btnAdd.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                if(currentFragment instanceof SongManagerFragment){
                    intent = new Intent(AdminActivity.this, SongFormActivity.class);
                } else if (currentFragment instanceof CategoryManagerFragment) {
                    intent = new Intent(AdminActivity.this, CategoryFormActivity.class);
                }
//                else if (currentFragment instanceof UserManagerFragment) {
//                    intent = new Intent(AdminActivity.this, UserFormActivity.class);
//                }
                startActivity(intent);
            }
        });

//        Set event cho nút Back
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                forwardFragment(previousFragment, previousFragment.getTag());
//
////                Tạo hiệu ứng seleted.
//                if(currentFragment instanceof SongManagerFragment){
//                    menuItemSong.setChecked(true);
//                } else if (currentFragment instanceof CategoryManagerFragment) {
//                    menuItemCategory.setChecked(true);
//                } else if (currentFragment instanceof UserManagerFragment) {
//                    menuItemUser.setChecked(true);
//                }
//            }
//        });
    }

}
