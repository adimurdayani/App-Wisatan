package com.example.wisata.ui.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisata.R;
import com.example.wisata.data.model.KategoriWisata;

import java.util.ArrayList;

public class AdapterKategoriWisata extends RecyclerView.Adapter<AdapterKategoriWisata.HolderData> {
    private ArrayList<KategoriWisata> kategoriList;
    private Context context;

    public AdapterKategoriWisata(ArrayList<KategoriWisata> kategoriList, Context context) {
        this.kategoriList = kategoriList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderData(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        KategoriWisata kategori = kategoriList.get(position);
        holder.nama_kecamatan.setText(kategori.getNama_kategori());
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        LinearLayout kategori;
        TextView nama_kecamatan;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            kategori = itemView.findViewById(R.id.kategori);
            nama_kecamatan = itemView.findViewById(R.id.nama_kecamatan);
        }
    }
}
