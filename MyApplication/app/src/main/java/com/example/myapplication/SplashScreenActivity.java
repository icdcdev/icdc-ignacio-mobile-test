package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("VW", Context.MODE_PRIVATE);
            boolean logged = sharedPref.getBoolean(getString(R.string.logged_key), false);

            Intent intent;
            if( logged ) {
                intent = new Intent(this, MainActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        },
            3000
        );
    }
}