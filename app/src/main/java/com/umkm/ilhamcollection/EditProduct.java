package com.umkm.ilhamcollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.umkm.ilhamcollection.adapter.UkuranAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProduct extends AppCompatActivity {

    private ImageView imageView;
    private TextView qtyprod;
    private TextView titleprod;
    private TextView descprod;
    private TextView hargaprod;
    private TextView noteuser;

    LinearLayout backk;

    String idprod;
    String imageUri;
    String titlep;
    String desp;
    String hargap;
    String iduser;
    String qty;

    int idp;
    int toidp;

    int totalpr;

    String id_cart, id_user,id_product,title_product,image,description,note,price,quantity;
    String id_cart2, id_user2,id_product2,title_product2,image2,description2,note2,price2,quantity2;
    String fulldata;
    ImageView kurang,tambah;
    TextView jumlah,simpanjumlah;

    private UkuranAdapter catedapter;
    String ukuranbaju,warnabaju;

    Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        kurang = findViewById(R.id.btnkurang);
        tambah = findViewById(R.id.btntambah);
        jumlah= findViewById(R.id.txtjumlahproduk);
        simpanjumlah = findViewById(R.id.btntambahproduk);
        SharedPreferences sharedPreferences = getSharedPreferences("miecustom", Context.MODE_PRIVATE);
        iduser = sharedPreferences.getString("id_user","");

        // Inisialisasi variabel
        imageView = findViewById(R.id.imageView);
        titleprod = findViewById(R.id.titleview);
        descprod = findViewById(R.id.descview);
        hargaprod = findViewById(R.id.hargaview);
        qtyprod = findViewById(R.id.txtjumlahproduk);


        backk = findViewById(R.id.btnback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String tmbhkecart= "Update Pesanan";
        String kemenu="Hapus Pesanan";

        getSupportActionBar().hide();

        //Data dari btn edit cart adapter
        id_cart2 = getIntent().getStringExtra("idcart");
        title_product2 = getIntent().getStringExtra("nama");
        image2 = String.valueOf("https://ilhamcollectionss.000webhostapp.com/mobileapps/okta/Foto/"+getIntent().getStringExtra("gambar"));
        description2 = getIntent().getStringExtra("warna");
        note2 = getIntent().getStringExtra("ukuran");
        price2 = getIntent().getStringExtra("harga");
        quantity2 = getIntent().getStringExtra("quantity");
        ////////////////////////////////////////////////////////

        //Menampilkan data yang diambil dari adapter
        Glide.with(EditProduct.this).load(image2).into(imageView);
        titleprod.setText(title_product2);
        descprod.setText(description2);
        hargaprod.setText(price2);
        RecyclerView catee = findViewById(R.id.btnukuran2);
        LinearLayoutManager layoutManagercate = new LinearLayoutManager(this);
        layoutManagercate.setOrientation(RecyclerView.HORIZONTAL);
        catee.setLayoutManager(layoutManagercate);
        ArrayList<String> catest = new ArrayList<>();
        for (int i=0;i<1;i++){
            catest.add("XL");
            catest.add("L");
            catest.add("M");
            catest.add("S");
        }
        catedapter = new UkuranAdapter(this, catest, new UkuranAdapter.ItemClickListener() {
            @Override
            public void onClick(String list) {

                if (list.equals("XL")){
                    ukuranbaju = "XL";
                }else if(list.equals("L")){
                    ukuranbaju = "L";
                }else if(list.equals("M")){
                    ukuranbaju = "M";
                }else if(list.equals("S")){
                    ukuranbaju = "S";
                }else {
                    Toast.makeText(EditProduct.this,"Ukuran tidak boleh kosong",Toast.LENGTH_LONG).show();
                }
            }
        });
        catee.setAdapter(catedapter);
        qtyprod.setText(quantity2);
        count = Integer.parseInt(quantity2);
        ///////////////////////////////////////////

        //Data tambah kurang order
        id_cart = id_cart2;
        title_product = title_product2;
        image = image2;
        description = description2;
        price = price2;


        /////////////////////////////


        simpanjumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = String.valueOf(noteuser.getText());
                quantity = count.toString();
                updatecart(id_cart,description,note,price,quantity);
            }
        });


        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count ++;
                jumlah.setText(""+count);
                note = String.valueOf(noteuser.getText());
                quantity = count.toString();
                simpanjumlah.setText(tmbhkecart);
                simpanjumlah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatecart(id_cart,description,note,price,quantity);
                    }
                });
            }
        });

        kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count<0){
                    count = 0;
                    jumlah.setText(count);
                    note = String.valueOf(noteuser.getText());
                    quantity = count.toString();
                    simpanjumlah.setText(kemenu);

                }else if (count==0){
                    quantity = count.toString();
                    simpanjumlah.setText(kemenu);
                    simpanjumlah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletecart(id_cart2);
                        }
                    });
                }else if (count==1){
                    count--;
                    jumlah.setText(""+count);
                    note = String.valueOf(noteuser.getText());
                    quantity = count.toString();
                    simpanjumlah.setText(kemenu);
                    simpanjumlah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletecart(id_cart2);
                        }
                    });
                } else {
                    count--;
                    jumlah.setText(""+count);
                    note = String.valueOf(noteuser.getText());
                    quantity = count.toString();
                    simpanjumlah.setText(tmbhkecart);
                    simpanjumlah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updatecart(id_cart,description,note,price,quantity);
                        }
                    });
                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    public void deletecart(String idcrt){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://ilhamcollectionss.000webhostapp.com/mobileapps/cart/deletecart.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if (status.equals("success")){
                                onBackPressed();
                                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(),"gagal", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("id_cart", idcrt);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
    public void updatecart(String idcrt,String dess,String nt,String hargaa, String qtyy){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="https://ilhamcollectionss.000webhostapp.com/mobileapps/cart/updateqtycart.php";
        String note = nt;
        String quantity = qtyy;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String message = jsonObject.getString("message");
                            if (status.equals("success")){
                                onBackPressed();
                                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> paramV = new HashMap<>();
                paramV.put("id_cart", idcrt);
                paramV.put("description", dess);
                paramV.put("note", note);
                paramV.put("price", hargaa);
                paramV.put("quantity", quantity);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}