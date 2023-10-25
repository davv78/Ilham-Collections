package com.umkm.ilhamcollection.model;

import com.google.gson.annotations.SerializedName;

public class TransactionData {
    @SerializedName("id_order")
    private String id_transaction;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("nama")
    private String nama;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("harga")
    private String harga;
    @SerializedName("status")
    private String status;
    @SerializedName("remark")
    private String remark;
    @SerializedName("date")
    private String date;

    public TransactionData(String id_transaction, String id_user, String nama, String quantity, String harga, String status, String remark, String date) {
        this.id_transaction = id_transaction;
        this.id_user = id_user;
        this.nama = nama;
        this.quantity = quantity;
        this.harga = harga;
        this.status = status;
        this.remark = remark;
        this.date = date;
    }

    public String getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(String id_transaction) {
        this.id_transaction = id_transaction;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
