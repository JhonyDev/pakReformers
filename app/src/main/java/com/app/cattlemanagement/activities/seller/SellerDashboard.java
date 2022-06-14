package com.app.cattlemanagement.activities.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cattlemanagement.R;
import com.app.cattlemanagement.activities.auth.AuthLoginActivity;
import com.app.cattlemanagement.activities.buyer.BuyerDashboard;
import com.google.firebase.auth.FirebaseAuth;

public class SellerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);
    }

    public void sellCattle(View view) {

    }

    public void updateCattleInfo(View view) {

    }

    public void addDiary(View view) {

    }

    public void checkMilk(View view) {

    }

    public void feedManagement(View view) {

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }

    public void switchToBuyer(View view) {
        startActivity(new Intent(this, BuyerDashboard.class));
        finish();
    }
}