package com.umkm.ilhamcollection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.umkm.ilhamcollection.adapter.CartListAdapter;
import com.umkm.ilhamcollection.model.CartData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderConfirm extends AppCompatActivity {

    private List<CartData> CartList;
    private CartListAdapter adapter;
    private OrderConfirmationViewModel viewModel;

    LinearLayout backk;

    String id_user;
    String totalse;
    Integer jumlahtotal1;
    Integer jumlahtotal2;
    Integer totalasli;
    String nkir;
    String totalfix;
    String alamat;
    TextView txttotal;
    TextView ongkir;
    TextView totalfixx;
    TextView alamatuser;
    LinearLayout btnpesanskrg;

    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        getSupportActionBar().hide();

        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        SharedPreferences sharedPreferences = getSharedPreferences("miecustom", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("id_user","").toString();

        txttotal = findViewById(R.id.totalhargapesanuser);
        ongkir = findViewById(R.id.txtongkir);
        totalfixx = findViewById(R.id.txttotalkeseluruhan);
        alamatuser = findViewById(R.id.txtalamatuser);
        btnpesanskrg = findViewById(R.id.btnpesansekarang);
        alamat = sharedPreferences.getString("address","").toString();
        alamatuser.setText(alamat);
        nkir = ongkir.getText().toString();
        totalse = getIntent().getStringExtra("totall");
        txttotal.setText(totalse);
        getviewcart();

        //harga total
        try {
            jumlahtotal1 = NumberFormat.getInstance().parse(totalse).intValue();
            jumlahtotal2 = NumberFormat.getInstance().parse(nkir).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        totalasli = jumlahtotal1+jumlahtotal2;
        totalfix = String.valueOf(totalasli);
        totalfixx.setText(totalfix);

        backk = findViewById(R.id.btnback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnpesanskrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bi ="addtransaksi.php";
                kirim(bi);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void getviewcart(){

        SharedPreferences sharedPreferences = getSharedPreferences("miecustom", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("id_user","").toString();

        RecyclerView recyclerView = findViewById(R.id.customRecyclerVieww);
        final TextView noresult = findViewById(R.id.noResultTv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new CartListAdapter(this,CartList);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(OrderConfirmationViewModel.class);
        viewModel.getDataListObserver().observe(this, new Observer<List<CartData>>() {
            @Override
            public void onChanged(List<CartData> dataList) {
                if(dataList != null) {
                    CartList = dataList;
                    adapter.setCartList(dataList);
                    noresult.setVisibility(View.GONE);
                    progressDoalog.dismiss();
                } else {
                    noresult.setVisibility(View.VISIBLE);
                    progressDoalog.dismiss();
                }
            }
        });
        viewModel.getallorder(id_user,this);
    }
    public void kirim(String file){
        SharedPreferences sharedPreferences = getSharedPreferences("miecustom", Context.MODE_PRIVATE);
        id_user = sharedPreferences.getString("id_user","").toString();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://ilhamcollectionss.000webhostapp.com/mobileapps/transaction/"+file;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if (status.equals("success")){
                                Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                //textViewError.setText(response);
                                //textViewError.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // textViewError.setText(error.getLocalizedMessage());
                // textViewError.setVisibility(View.VISIBLE);
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