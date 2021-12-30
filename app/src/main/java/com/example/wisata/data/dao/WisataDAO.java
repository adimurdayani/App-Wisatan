package com.example.wisata.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wisata.data.model.DetailWisata;

@Dao
public interface WisataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWisata(DetailWisata wisata);

    @Query("SELECT * FROM tswisata4")
    DetailWisata[] readDataWisata();

    @Update
    int updateWisata(DetailWisata wisata);

    @Delete
    void deleteWisata(DetailWisata wisata);

    @Query("SELECT * FROM tswisata4 WHERE id = :id LIMIT 1")
    DetailWisata selectDetailWisata(int id);

}
