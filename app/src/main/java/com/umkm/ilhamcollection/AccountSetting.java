package com.umkm.ilhamcollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class AccountSetting extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername, textInputEditTextFirstname, textInputEditTextLastname,textInputEditTextEmail, textInputEditTextPhoneNumber,textInputEditTextPassword,textInputEditTextAddress;
    Button buttonSubmit;
    String username, firstname, lastname, email, phone_number, password, address;
    ImageView back;
    String iduser,usernames, firstnames, lastnames, emails, phone_numbers, passwords, addresss,apiKey;
    TextView textViewError,textViewLoginNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        getSupportActionBar().hide();
        back = findViewById(R.id.btnback);
        textViewError = findViewById(R.id.error);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextFirstname = findViewById(R.id.fisrtname);
        textInputEditTextLastname = findViewById(R.id.lastname);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPhoneNumber = findViewById(R.id.phonenumber);
        textInputEditTextAddress = findViewById(R.id.address);
        buttonSubmit = findViewById(R.id.simpan);
        SharedPreferences sharedPreferences = getSharedPreferences("miecustom", Context.MODE_PRIVATE);

        iduser = getIntent().getStringExtra("id_user");
        usernames = getIntent().getStringExtra("username");
        firstnames = getIntent().getStringExtra("firstname");
        lastnames = getIntent().getStringExtra("lastname");
        emails = getIntent().getStringExtra("email");
        phone_numbers = getIntent().getStringExtra("phone_number");
        passwords = getIntent().getStringExtra("password");
        addresss = getIntent().getStringExtra("address");
        apiKey = String.valueOf(sharedPreferences.getString("apiKey",""));

        textInputEditTextUsername.setText(usernames);
        textInputEditTextFirstname.setText(firstnames);
        textInputEditTextLastname.setText(lastnames);
        textInputEditTextEmail.setText(emails);
        textInputEditTextPhoneNumber.setText(phone_numbers);
        textInputEditTextAddress.setText(addresss);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewError.setVisibility(View.GONE);
                username = String.valueOf(textInputEditTextUsername.getText());
                firstname = String.valueOf(textInputEditTextFirstname.getText());
                lastname = String.valueOf(textInputEditTextLastname.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                phone_number = String.valueOf(textInputEditTextPhoneNumber.getText());
                address = String.valueOf(textInputEditTextAddress.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://miesuhh.000webhostapp.com/userdata/updateuser.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")){
                                    Toast.makeText(getApplicationContext(),"Update Successful", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("logged","true");
                                    editor.putString("id_user",iduser);
                                    editor.putString("username",username);
                                    editor.putString("email",email);
                                    editor.putString("address",address);
                                    editor.putString("apiKey",apiKey);
                                    editor.apply();
                                    onBackPressed();
                                } else {
                                    textViewError.setText(response);
                                    textViewError.setVisibility(View.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textViewError.setText(error.getLocalizedMessage());
                        textViewError.setVisibility(View.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("username", username);
                        paramV.put("firstname", firstname);
                        paramV.put("lastname", lastname);
                        paramV.put("email", email);
                        paramV.put("phonenumber", phone_number);
                        paramV.put("address", address);
                        paramV.put("id_user", iduser);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}