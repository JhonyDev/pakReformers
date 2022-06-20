package com.app.pakreformers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.volunteer.DrivesDashboard;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, DrivesDashboard.class));
            finish();
        }, 2000);
    }
}