package com.example.musicplayer.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.EditUserActivity;
import com.example.musicplayer.LoginActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongManagerAdapter;
import com.example.musicplayer.adapter.UserAdapter;
import com.example.musicplayer.api.SongApi;
import com.example.musicplayer.api.UserApi;
import com.example.musicplayer.domain.OnItemClickListener;
import com.example.musicplayer.domain.Song;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.domain.UserMessage;
import com.example.musicplayer.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManagerFragment extends Fragment  {
    ImageButton btnAdd;
    List<User> userList;

    UserApi userApi;
    View view;
    int currentPosition;
    private RecyclerView mRecyclerView, headerRecyclerView;
    private UserAdapter userAdapter;
    public UserManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_user_manager, container, false);

        init();

        return view;
    }

    private void setEvent() {
    }

    private void loadData(){

        mRecyclerView = view.findViewById(R.id.rcvUserListManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList = new ArrayList<>();

        userApi = RetrofitClient.getInstance().getRetrofit().create(UserApi.class);
        userApi.getAllUser().enqueue(new Callback<UserMessage>() {
            @Override
            public void onResponse(Call<UserMessage> call, Response<UserMessage> response) {
                userList = response.body().getUsers();
                userAdapter = new UserAdapter(userList);
                mRecyclerView.setAdapter(userAdapter);
                userAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        User data = userList.get(position);
                        showDialog(data);
                    }
                });
            }

            @Override
            public void onFailure(Call<UserMessage> call, Throwable t) {

            }
        });

        // Create a list of songs

        // Create and set the adapter for the RecyclerView


    }

    private void init() {

//        btnAdd = view.findViewById(R.id.bt);
        loadData();
        setEvent();
    }

    private void showDialog(User data) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);

        LinearLayout deteteLayout = dialog.findViewById(R.id.layout_delete);
        LinearLayout editLayout = dialog.findViewById(R.id.layout_edit);

        deteteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userApi = RetrofitClient.getInstance().getRetrofit().create(UserApi.class);
                userApi.deleteUser(data.getId()).enqueue(new Callback<UserMessage>() {
                    @Override
                    public void onResponse(Call<UserMessage> call, Response<UserMessage> response) {
                        String string= response.body().getMessage();
                        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        onResume();
                    }

                    @Override
                    public void onFailure(Call<UserMessage> call, Throwable t) {

                    }
                });
                dialog.dismiss();
                onResume();
            }
        });

        editLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditUserActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    @Override
    public void onResume() {
        super.onResume();
        init();
        // Perform any necessary updates here
    }
}
