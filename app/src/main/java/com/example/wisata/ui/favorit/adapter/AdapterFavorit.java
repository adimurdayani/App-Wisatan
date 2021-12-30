package com.example.wisata.ui.favorit.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.wisata.R;
import com.example.wisata.data.dao.room.AppData;
import com.example.wisata.data.model.DetailWisata;
import com.example.wisata.ui.detail.adapter.AdapterKategoriWisata;
import com.example.wisata.ui.favorit.detail.DetailFavorit;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterFavorit extends RecyclerView.Adapter<AdapterFavorit.HolderData> {

    private ArrayList<DetailWisata> arrayList;
    private AppData appData;
    private Context context;

    public AdapterFavorit(ArrayList<DetailWisata> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        appData = Room.databaseBuilder(
                context.getApplicationContext(),
                AppData.class,
                "dbWisata"
        ).allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderData(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_favorit, parent, false)
        );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DetailWisata wisata = arrayList.get(position);
        holder.nama_wisata.setText(wisata.getNama_wisata());
        Picasso.get()
                .load(wisata.getGambar())
                .placeholder(R.drawable.pemandangan)
                .error(R.drawable.pemandangan)
                .into(holder.image);

        holder.btn_delete.setOnClickListener(v -> {
            deleteData(position);
        });

        holder.btn_detail.setOnClickListener(v -> {
            DetailWisata detailWisata = appData.wisataDAO().selectDetailWisata(wisata.getId());
            context.startActivity(new Intent(context, DetailFavorit.class)
                    .putExtra("detail", detailWisata));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView nama_wisata;
        ImageView btn_detail, image, btn_delete;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama_wisata = itemView.findViewById(R.id.nama_wisata);
            btn_detail = itemView.findViewById(R.id.btn_detail);
            image = itemView.findViewById(R.id.image);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }

    private void deleteData(int position) {
        LottieAlertDialog dialog = new LottieAlertDialog.Builder(context, DialogTypes.TYPE_WARNING)
                .setTitle("Apakah anda yakin?")
                .setDescription("Data dihapus permanen!")
                .setPositiveText("Oke")
                .setPositiveTextColor(Color.WHITE)
                .setPositiveButtonColor(Color.BLUE)
                .setPositiveListener(lottieAlertDialog -> {
                    appData.wisataDAO().deleteWisata(arrayList.get(position));
                    arrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, arrayList.size());
                    showSukses("Favorit berhasil dihapus!");
                    lottieAlertDialog.dismiss();
                })
                .setNegativeText("Tidak")
                .setNegativeTextColor(Color.WHITE)
                .setNegativeButtonColor(Color.RED)
                .setNegativeListener(Dialog::dismiss)
                .build();
        dialog.show();
    }

    private void showSukses(String pesan){
        LottieAlertDialog dialog = new LottieAlertDialog.Builder(context, DialogTypes.TYPE_SUCCESS)
                .setTitle("Sukses")
                .setDescription(pesan)
                .setPositiveText("Oke")
                .setPositiveTextColor(Color.WHITE)
                .setPositiveButtonColor(Color.BLUE)
                .setPositiveListener(Dialog::dismiss)
                .build();
        dialog.show();
    }
}
