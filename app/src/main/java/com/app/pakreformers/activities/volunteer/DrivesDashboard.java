package com.app.pakreformers.activities.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.auth.AuthLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DrivesDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drives);
    }

    public void back(View view) {
        finish();
    }

    public void exploreCattle(View view) {
        startActivity(new Intent(this, DriveDetails.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }
}