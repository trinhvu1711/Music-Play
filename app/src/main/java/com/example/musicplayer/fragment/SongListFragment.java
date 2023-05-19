package com.example.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.MainActivity;
import com.example.musicplayer.PlayerActivity;
import com.example.musicplayer.PlayingActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.SharedPrefManager;
import com.example.musicplayer.adapter.SongAdapter;
import com.example.musicplayer.adapter.SongListAdapter;
import com.example.musicplayer.api.CategoryApi;
import com.example.musicplayer.api.FavouriteApi;
import com.example.musicplayer.api.SongApi;
import com.example.musicplayer.domain.Category;
import com.example.musicplayer.domain.Favourite;
import com.example.musicplayer.domain.FavouriteMessage;
import com.example.musicplayer.domain.OnItemClickListener;
import com.example.musicplayer.domain.Song;
import com.example.musicplayer.domain.SongMessage;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.retrofit.RetrofitClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SongListAdapter mSongAdapter;

    private TextView tvTitle;

    private SongApi songApi;

    private FavouriteApi favouriteApi;

    View view;

    static boolean isCategory;

    private Long categoryId;
    String title;

    public SongListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_song_list, container, false);

//        Get categoryId
        init();
        return view;
    }

    private void init(){
        tvTitle = view.findViewById(R.id.tvTitle);
        Bundle bundle = getArguments();
        if(bundle != null){
            categoryId = bundle.getLong("category");
            title = bundle.getString("title");
            tvTitle.setText(title);
            if(categoryId != null) {
                isCategory = true;
            }
            else {
                isCategory = false;
            }
        }
        else
        {
            isCategory = false;
        }
        // Initialize the RecyclerView
        mRecyclerView = view.findViewById(R.id.rcvSongList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getSong();
    }
    private void getSong(){
        if(isCategory == true) {
            getSongByCategory();
        }
        else {
            getSongByFavourite();
        }
    }
    private void getSongByCategory(){
        songApi = RetrofitClient.getInstance().getRetrofit().create(SongApi.class);

        songApi.SongCategory(categoryId).enqueue(new Callback<SongMessage>() {
            @Override
            public void onResponse(Call<SongMessage> call, Response<SongMessage> response) {
                List<Song> songs;
                SongMessage songMessage = response.body();
                songs = songMessage.getSongs();
                List<Song> songList = songs;
                mSongAdapter = new SongListAdapter(songs);
                mRecyclerView.setAdapter(mSongAdapter);
                mRecyclerView.setHasFixedSize(true);
                mSongAdapter.notifyDataSetChanged();
                if (songs != null && !songs.isEmpty()) {
                    mSongAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Song data = songs.get(position);
                            Intent intent = new Intent(getActivity(), PlayerActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("songs", (Serializable) songs);
                            System.out.println("-----------------");
                            System.out.println(data);
                            //intent.putExtra("songList", new ArrayList<>(songList));
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SongMessage> call, Throwable t) {
                System.out.println(t);
            }
        });
    }
    private void getSongByFavourite(){
        User user = SharedPrefManager.getInstance(getContext()).getUser();
        favouriteApi =  RetrofitClient.getInstance().getRetrofit().create(FavouriteApi.class);

        favouriteApi.listByUser(user.getId()).enqueue(new Callback<FavouriteMessage>() {
            @Override
            public void onResponse(Call<FavouriteMessage> call, Response<FavouriteMessage> response) {
                FavouriteMessage favouriteMessage = response.body();
                List<Song> songs = new ArrayList<>();
                for(Favourite favourite: favouriteMessage.getFavourites()) {
                    songs.add(favourite.getSong());
                }
                mSongAdapter = new SongListAdapter(songs);
                mRecyclerView.setAdapter(mSongAdapter);
                mRecyclerView.setHasFixedSize(true);
                mSongAdapter.notifyDataSetChanged();
                if (songs != null && !songs.isEmpty()) {
                    mSongAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Song data = songs.get(position);
                            Intent intent = new Intent(getActivity(), PlayerActivity.class);
                            intent.putExtra("position", position);
                            intent.putExtra("songs", (Serializable) songs);
                            System.out.println("-----------------");
                            System.out.println(data);
                            //intent.putExtra("songList", new ArrayList<>(songList));
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FavouriteMessage> call, Throwable t) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        init();
        // Perform any necessary updates here
    }
}
