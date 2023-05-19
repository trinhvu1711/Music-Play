package com.example.musicplayer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.EditUserActivity;
import com.example.musicplayer.LoginActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.SharedPrefManager;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.utilities.Utility;

public class UserFragment extends Fragment {
    View view;

    TextView tvProfileName, tvProfilePhone, tvProfileEmail;

    User user;

    Button btnProfileLogout, btnProfileEdit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_profile, container, false);
        user = SharedPrefManager.getInstance(getContext()).getUser();
        init();
        event();

        return view;
    }

    private void event() {
        btnProfileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        btnProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });
    }

    private void edit() {
        Intent intent = new Intent(getActivity(), EditUserActivity.class);
        intent.putExtra("data", user);
        startActivity(intent);
    }

    private void logout() {
        Activity activity = getActivity();
        SharedPreferences preferences = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user");
        editor.apply();

        // chuyển hướng đến màn hình đăng nhập
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        activity.finish();
    }

    private void init() {
        btnProfileLogout = view.findViewById(R.id.btnProfileLogout);
        btnProfileEdit = view.findViewById(R.id.btnProfileEdit);
        tvProfileName= view.findViewById(R.id.tvProfileName);
        tvProfilePhone= view.findViewById(R.id.tvProfilePhone);
        tvProfileEmail= view.findViewById(R.id.tvProfileEmail);

        Utility.setScrollText(tvProfileName);
        Utility.setScrollText(tvProfilePhone);
        Utility.setScrollText(tvProfileEmail);

        tvProfileName.setText(user.getFirst_name() +" " + user.getLast_name());
        tvProfilePhone.setText(user.getPhone());
        tvProfileEmail.setText(user.getEmail());
    }
}
