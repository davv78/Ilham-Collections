package com.umkm.ilhamcollection.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.umkm.ilhamcollection.About;
import com.umkm.ilhamcollection.AccountSetting;
import com.umkm.ilhamcollection.Login;
import com.umkm.ilhamcollection.R;
import com.umkm.ilhamcollection.databinding.FragmentSettingsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import im.crisp.client.ChatActivity;
import im.crisp.client.Crisp;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    TextView textViewName,textViewEmail,textViewError;
    SharedPreferences sharedPreferences;
    Button buttonLogout;
    LinearLayout settingakun,aboutus, cs;
    String iduser,username, email, address, phone_number, password, firstname,lastname,apiKey;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        textViewName = view.findViewById(R.id.name);
       textViewEmail = view.findViewById(R.id.email);
        buttonLogout = view.findViewById(R.id.logout);
        settingakun = view.findViewById(R.id.settingakun);
        aboutus = view.findViewById(R.id.aboutus);
        textViewError = view.findViewById(R.id.error);
        cs = view.findViewById(R.id.customerService);
        sharedPreferences = getActivity().getSharedPreferences("miecustom", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("logged", "false").equals("false")){
            Intent intent = new Intent(getContext().getApplicationContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        }
        textViewName.setText(sharedPreferences.getString("username",""));
       // textViewPhoneNumber.setText(sharedPreferences.getString("address",""));
        textViewEmail.setText(sharedPreferences.getString("email",""));

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crisp.configure(getContext().getApplicationContext(), "8d5e7306-aa3a-4a23-aeb5-6ada68beee90");
                Intent crispIntent = new Intent(getContext(), ChatActivity.class);
                startActivity(crispIntent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext().getApplicationContext(), About.class);
                startActivity(intent);
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
                String url ="https://miesuhh.000webhostapp.com/mobileapps/userdata/logout.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("logged", "");
                                    editor.putString("id_user", "");
                                    editor.putString("username", "");
                                    editor.putString("email", "");
                                    editor.putString("address", "");
                                    editor.putString("apiKey", "");
                                    editor.apply();
                                    Intent intent = new Intent(getContext().getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", sharedPreferences.getString("email",""));
                        paramV.put("apiKey", sharedPreferences.getString("apiKey",""));
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
        settingakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext().getApplicationContext());
                String url ="https://miesuhh.000webhostapp.com/mobileapps/userdata/profile.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                username = String.valueOf(sharedPreferences.getString("username",""));
                                apiKey = String.valueOf(sharedPreferences.getString("apiKey",""));
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");
                                    String message = jsonObject.getString("message");
                                    if(status.equals("success")){
                                        iduser = jsonObject.getString("id_user");
                                        username = jsonObject.getString("username");
                                        firstname = jsonObject.getString("firstname");
                                        lastname = jsonObject.getString("lastname");
                                        email = jsonObject.getString("email");
                                        phone_number = jsonObject.getString("phone_number");
                                        password = jsonObject.getString("password");
                                        address = jsonObject.getString("address");
                                        Intent intent = new Intent(getContext().getApplicationContext(), AccountSetting.class);
                                        intent.putExtra("id_user",iduser);
                                        intent.putExtra("username",username);
                                        intent.putExtra("firstname",firstname);
                                        intent.putExtra("lastname",lastname);
                                        intent.putExtra("email",email);
                                        intent.putExtra("phone_number",phone_number);
                                        intent.putExtra("password",password);
                                        intent.putExtra("address",address);
                                        startActivity(intent);
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
                        textViewError.setText(error.getLocalizedMessage());
                        textViewError.setVisibility(View.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("username", sharedPreferences.getString("username",username));
                        paramV.put("apiKey", sharedPreferences.getString("apiKey",apiKey));
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}