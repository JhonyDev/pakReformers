package com.app.pakreformers.activities.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.auth.AuthLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class DriveDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_dashboard);
    }

    public void exploreCattle(View view) {

    }

    public void dairyProducts(View view) {

    }

    public void publishRequest(View view) {

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }
}