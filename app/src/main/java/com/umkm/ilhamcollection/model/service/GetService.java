package com.umkm.ilhamcollection.model.service;

import com.umkm.ilhamcollection.model.CartData;
import com.umkm.ilhamcollection.model.ProductData;
import com.umkm.ilhamcollection.model.TransactionData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetService {
    @GET("/mobileapps/getdataproduk/produkAll.php")
    Call<List<ProductData>> getAll();
    @GET("/mobileapps/getdataproduk/produkHoodie.php")
    Call<List<ProductData>> getHoodie();
    @GET("/mobileapps/getdataproduk/produkSweater.php")
    Call<List<ProductData>> getSweater();
    @GET("/mobileapps/getdataproduk/produkKemeja.php")
    Call<List<ProductData>> getKemeja();
    @GET("/mobileapps/cart/addcart.php")
    Call<List<CartData>> getcart();
//    @GET("/cart/getdatacustom.php")
//    Call<List<CartData>> getcartcustom();
//    @GET("/transaction/tampiltransaksi.php")
//    Call<List<TransactionData>> getTransaction();
}
