package com.app.pakreformers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.auth.AuthLoginActivity;
import com.app.pakreformers.activities.volunteer.DrivesDashboard;
import com.app.pakreformers.info.Info;
import com.google.firebase.database.FirebaseDatabase;

public class Splash extends AppCompatActivity implements Info {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        FirebaseDatabase.getInstance().getReference().child(NODE_DRIVES).removeValue();

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, AuthLoginActivity.class));
            finish();
        }, 2000);
    }
}