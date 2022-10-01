package com.app.pakreformers.activities.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.auth.AuthLoginActivity;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.singletons.DriveSingleton;
import com.google.firebase.auth.FirebaseAuth;

public class DriveDetails extends AppCompatActivity implements Info {

    CardView card_update;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_dashboard);
        card_update = findViewById(R.id.card_update);
        title = findViewById(R.id.title);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(DriveSingleton.getInstance().getCreator()))
            card_update.setVisibility(View.VISIBLE);
        else
            card_update.setVisibility(View.GONE);

        title.setText(DriveSingleton.getInstance().getTitle());
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

    public void editDrive(View view) {
        startActivity(new Intent(this, UpdateDrive.class));
    }

    public void chats(View view) {
        startActivity(new Intent(this, Chats.class));
    }
}