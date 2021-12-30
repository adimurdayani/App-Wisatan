package com.example.wisata.ui.favorit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.wisata.R;
import com.example.wisata.data.dao.room.AppData;
import com.example.wisata.data.model.DetailWisata;
import com.example.wisata.ui.favorit.adapter.AdapterFavorit;

import java.util.ArrayList;
import java.util.Arrays;

public class FavoritFragment extends Fragment {
    private View view;
    RecyclerView rc_data;
    ArrayList<DetailWisata> detailWisatas;
    AppData database;
    AdapterFavorit adapter;

    public FavoritFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorit, container, false);
        setinit();
        return view;
    }

    private void setData() {
        rc_data.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        );

        detailWisatas = new ArrayList<>();
        database = Room.databaseBuilder(
                getContext(),
                AppData.class,
                "dbWisata"
        ).allowMainThreadQueries().build();

        detailWisatas.addAll(Arrays.asList(database.wisataDAO().readDataWisata()));
        rc_data.setHasFixedSize(true);

        adapter = new AdapterFavorit(detailWisatas, getContext());
        rc_data.setAdapter(adapter);
    }

    private void setinit() {
        rc_data = view.findViewById(R.id.rc_data);
    }

    @Override
    public void onResume() {
        setData();
        super.onResume();
    }
}
