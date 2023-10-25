package com.umkm.ilhamcollection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView textViewRegisterNow;
    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSubmit;
    String username, email, iduser, addres, password, apiKey;
    TextView textViewError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    boolean passwordvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewRegisterNow = findViewById(R.id.registerNow);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.loading);
        textViewError = findViewById(R.id.error);
        sharedPreferences = getSharedPreferences("miecustom", Context.MODE_PRIVATE);

        getSupportActionBar().hide();

        textInputEditTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX()>=textInputEditTextPassword.getRight()-textInputEditTextPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=textInputEditTextPassword.getSelectionEnd();
                        if (passwordvisible){
                            textInputEditTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            textInputEditTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible=false;
                        }else{
                            textInputEditTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            textInputEditTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible=true;
                        }
                        textInputEditTextPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        if(sharedPreferences.getString("logged", "false").equals("true")){
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
            finish();
        }

        buttonSubmit = findViewById(R.id.submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewError.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="https://miesuhh.000webhostapp.com/mobileapps/userdata/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if(status.equals("success")){
                                        iduser = jsonObject.getString("id_user");
                                        username = jsonObject.getString("username");
                                        email = jsonObject.getString("email");
                                        addres = jsonObject.getString("address");
                                        apiKey = jsonObject.getString("apiKey");
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("logged","true");
                                        editor.putString("id_user",iduser);
                                        editor.putString("username",username);
                                        editor.putString("email",email);
                                        editor.putString("address",addres);
                                        editor.putString("apiKey",apiKey);
                                        editor.apply();
                                        Intent intent = new Intent(getApplicationContext(), Homepage.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        textViewError.setText(message);
                                        textViewError.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

        textViewRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}