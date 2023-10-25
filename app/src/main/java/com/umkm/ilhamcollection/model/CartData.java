package com.umkm.ilhamcollection.model;

import com.google.gson.annotations.SerializedName;

public class CartData {
    @SerializedName("id_cart")
    private Integer id_cart;
    @SerializedName("id_user")
    private Integer id_user;
    @SerializedName("id_pakaian")
    private Integer id_pakaian;
    @SerializedName("nama")
    private String nama;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("warna")
    private String warna;
    @SerializedName("ukuran")
    private String ukuran;
    @SerializedName("harga")
    private String harga;
    @SerializedName("quantity")
    private String quantity;

    public CartData(Integer id_cart, Integer id_user, Integer id_pakaian, String nama, String gambar, String warna, String ukuran, String harga, String quantity) {
        this.id_cart = id_cart;
        this.id_user = id_user;
        this.id_pakaian = id_pakaian;
        this.nama = nama;
        this.gambar = gambar;
        this.warna = warna;
        this.ukuran = ukuran;
        this.harga = harga;
        this.quantity = quantity;
    }

    public Integer getId_cart() {
        return id_cart;
    }

    public void setId_cart(Integer id_cart) {
        this.id_cart = id_cart;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_pakaian() {
        return id_pakaian;
    }

    public void setId_pakaian(Integer id_pakaian) {
        this.id_pakaian = id_pakaian;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

