package com.example.wisata.ui.home;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.wisata.R;
import com.example.wisata.data.model.Kategori;
import com.example.wisata.data.model.Wisata;
import com.example.wisata.ui.home.adapter.AdapterHome;
import com.example.wisata.ui.home.adapter.AdapterKategori;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View view;
    private CircularImageView circularImageView;
    private RecyclerView rc_wisata, rc_data;
    DatabaseReference reference;
    ArrayList<Kategori> kategoris;
    ArrayList<Wisata> wisatas;
    private AdapterHome adapter;
    private AdapterKategori kategori;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setinit();
        setImage();
        setDisplay();
        setDisplayKategori();
        return view;
    }

    private void setDisplayKategori() {
        rc_data.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
    }

    private void setDisplay() {
        rc_wisata.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
    }

    private void getWisata() {
        LottieAlertDialog dialog = new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_LOADING)
                .setTitle("Loading")
                .setDescription("Mohon tunggu sebentar")
                .build();
        dialog.setCancelable(false);
        dialog.show();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("wisata").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wisatas = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    dialog.dismiss();

                    Wisata wisata = data.getValue(Wisata.class);
                    wisata.setKey(data.getKey());
                    wisatas.add(wisata);
                }
                adapter = new AdapterHome(wisatas, getContext());
                rc_wisata.setAdapter(adapter);
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

    private void getKategori(){
        LottieAlertDialog dialog = new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_LOADING)
                .setTitle("Loading")
                .setDescription("Mohon tunggu sebentar")
                .build();
        dialog.setCancelable(false);
        dialog.show();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("kategori").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                kategoris = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    dialog.dismiss();

                    Kategori getkategori = data.getValue(Kategori.class);
                    getkategori.setKey(data.getKey());
                    kategoris.add(getkategori);
                }
                kategori = new AdapterKategori(kategoris, getContext());
                rc_data.setAdapter(kategori);
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
        LottieAlertDialog dialog = new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_ERROR)
                .setTitle("Oooops")
                .setDescription(pesan)
                .setPositiveText("Oke")
                .setPositiveTextColor(Color.WHITE)
                .setPositiveButtonColor(Color.BLUE)
                .setPositiveListener(Dialog::dismiss)
                .build();
        dialog.show();
    }

    private void setImage() {
        circularImageView.setCircleColor(Color.WHITE);
// or with gradient
        circularImageView.setCircleColorStart(Color.BLACK);
        circularImageView.setCircleColorEnd(Color.BLUE);
        circularImageView.setCircleColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

// Set Border
        circularImageView.setBorderWidth(10f);
        circularImageView.setBorderColor(Color.BLACK);
// or with gradient
        circularImageView.setBorderColorStart(Color.BLACK);
        circularImageView.setBorderColorEnd(Color.BLUE);
        circularImageView.setBorderColorDirection(CircularImageView.GradientDirection.TOP_TO_BOTTOM);

// Add Shadow with default param
        circularImageView.setShadowEnable(true);
// or with custom param
        circularImageView.setShadowRadius(7f);
        circularImageView.setShadowColor(Color.BLUE);
        circularImageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER);
    }

    private void setinit() {
        circularImageView = view.findViewById(R.id.img_logo);
        rc_wisata = view.findViewById(R.id.rc_wisata);
        rc_data = view.findViewById(R.id.rc_data);
    }

    @Override
    public void onResume() {
        getKategori();
        getWisata();
        super.onResume();
    }
}
