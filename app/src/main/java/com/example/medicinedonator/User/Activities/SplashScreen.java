package com.example.medicinedonator.User.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.medicinedonator.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,LoginRegistration.class);
                startActivity(intent);
            }
        },2000);

        //if Logout button clicked My account activity will parse the Intent to this
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }
}