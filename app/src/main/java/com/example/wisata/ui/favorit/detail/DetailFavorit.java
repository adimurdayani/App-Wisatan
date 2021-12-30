package com.example.wisata.ui.favorit.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wisata.R;
import com.example.wisata.data.model.DetailWisata;
import com.example.wisata.data.model.KategoriWisata;
import com.example.wisata.ui.detail.adapter.AdapterKategoriWisata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailFavorit extends AppCompatActivity {
    LinearLayout btn_kembali;
    TextView nama_wisata, deskripsi, alamat;
    DatabaseReference reference;
    RoundedImageView image;
    RecyclerView rc_data;
    AdapterKategoriWisata adapter;
    ArrayList<KategoriWisata> kategoriWisatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorit);
        setinit();
        setButton();
        setDisplay();
    }

    private void setDisplay() {
        rc_data.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        DetailWisata wisata = (DetailWisata) getIntent().getSerializableExtra("detail");
        if (wisata != null) {
            nama_wisata.setText(wisata.getNama_wisata());
            deskripsi.setText(wisata.getDeskripsi());
            alamat.setText(wisata.getAlamat());
            Picasso.get()
                    .load(wisata.getGambar())
                    .placeholder(R.drawable.pemandangan)
                    .error(R.drawable.pemandangan)
                    .into(image);
        }
    }

    private void setKategoriWisata() {
        LottieAlertDialog dialog = new LottieAlertDialog.Builder(this, DialogTypes.TYPE_LOADING)
                .setTitle("Loading")
                .setDescription("Mohon tunggu sebentar")
                .build();
        dialog.setCancelable(false);
        dialog.show();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("kategori_wisata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                kategoriWisatas = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    dialog.dismiss();

                    KategoriWisata getkategori = data.getValue(KategoriWisata.class);
                    getkategori.setKey(data.getKey());
                    kategoriWisatas.add(getkategori);
                }
                adapter = new AdapterKategoriWisata(kategoriWisatas, getApplication());
                rc_data.setAdapter(adapter);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Response", "Error: " + error.getMessage());
                showError(error.getMessage());
                dialog.dismiss();
            }
        });
    }

    private void showError(String pesan) {
        LottieAlertDialog dialog = new LottieAlertDialog.Builder(this, DialogTypes.TYPE_ERROR)
                .setTitle("Oooops")
                .setDescription(pesan)
                .setPositiveText("Oke")
                .setPositiveTextColor(Color.WHITE)
                .setPositiveButtonColor(Color.BLUE)
                .setPositiveListener(Dialog::dismiss)
                .build();
        dialog.show();
    }

    private void setButton() {
        btn_kembali.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void setinit() {
        btn_kembali = findViewById(R.id.btn_kembali);
        nama_wisata = findViewById(R.id.nama_wisata);
        deskripsi = findViewById(R.id.deskripsi);
        image = findViewById(R.id.image);
        rc_data = findViewById(R.id.rc_data);
        alamat = findViewById(R.id.alamat);
    }

    @Override
    protected void onResume() {
        setKategoriWisata();
        super.onResume();
    }
}