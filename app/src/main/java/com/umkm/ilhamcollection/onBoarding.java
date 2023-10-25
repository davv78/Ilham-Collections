package com.umkm.ilhamcollection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class onBoarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        getSupportActionBar().hide();
        Button btn;
        btn = findViewById(R.id.getstarted);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(onBoarding.this,Login.class));
                onBoardingFinished();
                finish();
            }
        });
    }
    private void onBoardingFinished(){
        SharedPreferences sharedPref = getSharedPreferences("miecustom",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Finished onBoarding",true);
        editor.apply();
    }
}