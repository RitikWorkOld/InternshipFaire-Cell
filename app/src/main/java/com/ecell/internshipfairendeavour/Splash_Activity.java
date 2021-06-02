package com.ecell.internshipfairendeavour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.messaging.FirebaseMessaging;

public class Splash_Activity extends AppCompatActivity {

    int DELAY_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, DELAY_TIME);
    }
}