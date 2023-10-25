package com.umkm.ilhamcollection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginFinished()){
                    startActivity(new Intent(SplashScreen.this,Homepage.class));
                }else if(onBoardingFinished()){
                    startActivity(new Intent(SplashScreen.this, Login.class));
                }else{
                    startActivity(new Intent(SplashScreen.this, onBoarding.class));
                }

                finish();

            }
        },3000);
    }
    private boolean onBoardingFinished(){
        SharedPreferences sharedPref = getSharedPreferences("miecustom", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("Finished onBoarding",false);
    }
    private boolean loginFinished(){
        SharedPreferences sharedPrefLogin = getSharedPreferences("miecustom", Context.MODE_PRIVATE);
        return sharedPrefLogin.getBoolean("Finished login",false);
    }

}