package com.umkm.ilhamcollection.ui.cart;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.umkm.ilhamcollection.model.CartData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartViewModel extends ViewModel {
    private MutableLiveData<List<CartData>> dataList;

    public CartViewModel(){
        dataList = new MutableLiveData<>();
    }

    public MutableLiveData<List<CartData>> getDataListObserver() {
        return dataList;
    }

    public void getCart(String id_user,Context contextt) {
        RequestQueue queue = Volley.newRequestQueue(contextt);
        String url ="https://ilhamcollectionss.000webhostapp.com/mobileapps/cart/cart.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            List<CartData> list = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                CartData data = new CartData(
                                        object.getInt("id_cart"),
                                        object.getInt("id_user"),
                                        object.getInt("id_pakaian"),
                                        object.getString("nama"),
                                        object.getString("gambar"),
                                        object.getString("warna"),
                                        object.getString("ukuran"),
                                        object.getString("harga"),
                                        object.getString("quantity")
                                );
                                list.add(data);
                            }
                            dataList.postValue(list);
                        }catch (JSONException e){
                            e.printStackTrace();
                            dataList.postValue(null);
                        }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contextt, error.getLocalizedMessage(),Toast.LENGTH_LONG);
                dataList.postValue(null);
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("id_user", id_user);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }

}