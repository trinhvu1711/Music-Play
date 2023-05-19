package com.example.musicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.musicplayer.R;
import com.example.musicplayer.api.SongApi;

public class SettingFragment extends Fragment {

    View view;
    EditText edSearching;

    private SongApi songApi;
    ImageButton ibBack, ibSearch;
    TextView tv;
    FragmentManager fragmentManager;
    public SettingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_searching, container, false);

        init();
        setEvent();

        return view;
    }

    private void setEvent() {

    }

    private void init(){
        edSearching = view.findViewById(R.id.edSearching);
        ibBack = view.findViewById(R.id.ibBackSearching);
        tv = view.findViewById(R.id.tvFirstSong);
        ibSearch= view.findViewById(R.id.ibSearch);
    }
}
