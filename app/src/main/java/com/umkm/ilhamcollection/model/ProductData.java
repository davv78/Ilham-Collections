package com.umkm.ilhamcollection.model;

import com.google.gson.annotations.SerializedName;

public class ProductData {
    @SerializedName("id_pakaian")
    private String id_pakaian;
    @SerializedName("jenis")
    private String jenis;
    @SerializedName("nama")
    private String nama;
    @SerializedName("warna")
    private String warna;
    @SerializedName("harga")
    private String harga;
    @SerializedName("keunggulan")
    private String keunggulan;
    @SerializedName("ukuran")
    private String ukuran;
    @SerializedName("bahan")
    private String bahan;
    @SerializedName("gambar")
    private String gambar;

    public ProductData(String id_pakaian, String jenis, String nama, String warna, String harga, String keunggulan, String ukuran, String bahan, String gambar) {
        this.id_pakaian = id_pakaian;
        this.jenis = jenis;
        this.nama = nama;
        this.warna = warna;
        this.harga = harga;
        this.keunggulan = keunggulan;
        this.ukuran = ukuran;
        this.bahan = bahan;
        this.gambar = gambar;
    }

    public String getId_pakaian() {
        return id_pakaian;
    }

    public void setId_pakaian(String id_pakaian) {
        this.id_pakaian = id_pakaian;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKeunggulan() {
        return keunggulan;
    }

    public void setKeunggulan(String keunggulan) {
        this.keunggulan = keunggulan;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}

