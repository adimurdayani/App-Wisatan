package com.example.wisata.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wisata.R;
import com.example.wisata.data.model.Wisata;
import com.example.wisata.ui.detail.DetailActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.HolderData> {

    private ArrayList<Wisata> wisatas;
    private Context context;

    public AdapterHome(ArrayList<Wisata> wisatas, Context context) {
        this.wisatas = wisatas;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderData(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wisata, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        Wisata wisata = wisatas.get(position);
        holder.nama_objek.setText(wisata.getNama_objek());
        holder.kategori.setText(wisata.getKategori());
        holder.alamat.setText(wisata.getAlamat());

        Picasso.get()
                .load(wisata.getGambar())
                .error(R.drawable.pemandangan)
                .placeholder(R.drawable.pemandangan)
                .into(holder.image);

        holder.image.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("detail", wisata.getKey());
            Log.d("Response", "key: "+ wisata.getKey());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return wisatas.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        RoundedImageView image;
        TextView nama_objek, kategori, alamat;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            alamat = itemView.findViewById(R.id.alamat);
            nama_objek = itemView.findViewById(R.id.nama_objek);
            kategori = itemView.findViewById(R.id.kategori);
        }
    }
}
