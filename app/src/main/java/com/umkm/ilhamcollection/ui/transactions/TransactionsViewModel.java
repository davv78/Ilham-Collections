package com.umkm.ilhamcollection.ui.transactions;

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
import com.umkm.ilhamcollection.model.TransactionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsViewModel extends ViewModel {

    private MutableLiveData<List<TransactionData>> dataList;

    public TransactionsViewModel() {
        dataList = new MutableLiveData<>();
    }

    public MutableLiveData<List<TransactionData>> getDataListObserver() {
        return dataList;
    }

    public void getDataTransaction(String id_user, Context contextt) {
        RequestQueue queue = Volley.newRequestQueue(contextt);
        String url ="https://ilhamcollectionss.000webhostapp.com/mobileapps/transaction/tampiltransaksi.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            List<TransactionData> list = new ArrayList<>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                TransactionData data = new TransactionData(
                                        object.getString("id_order"),
                                        object.getString("id_user"),
                                        object.getString("nama"),
                                        object.getString("quantity"),
                                        object.getString("harga"),
                                        object.getString("status"),
                                        object.getString("remark"),
                                        object.getString("date")
                                );
                                list.add(data);
                            }
                            dataList.postValue(list);
                        }catch (JSONException e){
                            e.printStackTrace();
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