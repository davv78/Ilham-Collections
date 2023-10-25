package com.umkm.ilhamcollection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

public class Register extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextFirstname, textInputEditTextLastname,textInputEditTextEmail, textInputEditTextPhoneNumber,textInputEditTextPassword,textInputEditTextAddress;
    Button buttonSubmit;
    String username, firstname, lastname, email, phone_number, password, address;
    TextView textViewError,textViewLoginNow;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextFirstname = findViewById(R.id.fisrtname);
        textInputEditTextLastname = findViewById(R.id.lastname);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPhoneNumber = findViewById(R.id.phone_number);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextAddress = findViewById(R.id.address);
        buttonSubmit = findViewById(R.id.submit);
        textViewError = findViewById(R.id.error);
        textViewLoginNow = findViewById(R.id.loginNow);
        progressBar = findViewById(R.id.loading);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                username = String.valueOf(textInputEditTextUsername.getText());
                firstname = String.valueOf(textInputEditTextFirstname.getText());
                lastname = String.valueOf(textInputEditTextLastname.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                phone_number = String.valueOf(textInputEditTextPhoneNumber.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                address = String.valueOf(textInputEditTextAddress.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://miesuhh.000webhostapp.com/mobileapps/userdata/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                if (response.equals("success")){
                                    Toast.makeText(getApplicationContext(),"Registrations Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    textViewError.setText(response);
                                    textViewError.setVisibility(View.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
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
                        paramV.put("phone_number", phone_number);
                        paramV.put("password", password);
                        paramV.put("address", address);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

        textViewLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}