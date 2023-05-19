package com.example.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.PlayerActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.SongListAdapter;
import com.example.musicplayer.domain.OnItemClickListener;
import com.example.musicplayer.domain.Song;

import java.io.Serializable;
import java.util.List;

public class SearchFragment extends Fragment {
    EditText edSearch;

    List<Song> songs;
    RecyclerView songRecyclerView;

    private SongListAdapter mSongAdapter;
    Button btnSearch;
    View view;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_search, container, false);

        init();
        setEvent();

        return view;
    }

    private void setEvent(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // if using support library
                SearchingFragment searchingFragment = new SearchingFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, searchingFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void init(){
        btnSearch = view.findViewById(R.id.btnSearch);
        songRecyclerView = view.findViewById(R.id.songRecyclerView);

        Bundle bundle = getArguments();
        if(bundle != null){
            songs = (List<Song>) bundle.getSerializable("songs");
            System.out.println(songs);
        }
        songRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getSong(songs);
    }

    private void getSong(List<Song> songs) {
        mSongAdapter = new SongListAdapter(songs);
        songRecyclerView.setAdapter(mSongAdapter);
        songRecyclerView.setHasFixedSize(true);
        mSongAdapter.notifyDataSetChanged();
        if (songs != null && !songs.isEmpty()) {
            mSongAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Song data = songs.get(position);
                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("songs", (Serializable) songs);
                    startActivity(intent);
                }
            });
        }
    }

}
