package com.app.cattlemanagement.activities.buyer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cattlemanagement.R;
import com.app.cattlemanagement.activities.auth.AuthLoginActivity;
import com.app.cattlemanagement.activities.seller.SellerDashboard;
import com.google.firebase.auth.FirebaseAuth;

public class BuyerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_dashboard);
    }

    public void exploreCattle(View view) {

    }

    public void dairyProducts(View view) {

    }

    public void publishRequest(View view) {

    }

    public void switchToSeller(View view) {
        startActivity(new Intent(this, SellerDashboard.class));
        finish();

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }
}