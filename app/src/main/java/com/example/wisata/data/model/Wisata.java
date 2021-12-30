package com.example.wisata.data.model;

public class Wisata {
    private String kategori, nama_objek, gambar, alamat, key;

    public Wisata() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama_objek() {
        return nama_objek;
    }

    public void setNama_objek(String nama_objek) {
        this.nama_objek = nama_objek;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
