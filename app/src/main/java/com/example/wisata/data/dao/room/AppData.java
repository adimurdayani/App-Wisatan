package com.example.wisata.data.dao.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.wisata.data.dao.WisataDAO;
import com.example.wisata.data.model.DetailWisata;

@Database(entities = {DetailWisata.class}, version = 3)
public abstract class AppData extends RoomDatabase {
    public abstract WisataDAO wisataDAO();
}
