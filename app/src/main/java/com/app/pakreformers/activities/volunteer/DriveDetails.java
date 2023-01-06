package com.app.pakreformers.activities.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.auth.AuthLoginActivity;
import com.app.pakreformers.adapters.TypeRecyclerViewAdapter;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.models.Participant;
import com.app.pakreformers.models.Super;
import com.app.pakreformers.singletons.DriveSingleton;
import com.app.pakreformers.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriveDetails extends AppCompatActivity implements Info {

    CardView card_update;
    TextView title;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    RecyclerView recyclerView;
    List<Super> listInstances;

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
        initRv();
        initData();
    }

    private void initData() {
        listInstances.clear();
        typeRecyclerViewAdapter.notifyDataSetChanged();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_PARTICIPANTS)
                .child(DriveSingleton.getInstance().getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listInstances.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Participant participant = child.getValue(Participant.class);
                            listInstances.add(participant);
                        }
                        typeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initRv() {
        recyclerView = findViewById(R.id.rv_participants);
        listInstances = new ArrayList<>();
        typeRecyclerViewAdapter = new TypeRecyclerViewAdapter(this, listInstances, RV_TYPE_PARTICIPANTS);
        recyclerView.setAdapter(typeRecyclerViewAdapter);
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

    public void resource(View view) {
        startActivity(new Intent(this, ResourceManagement.class));
    }

    public void driveLocation(View view) {
        Utils.showMarkerOnMap(this, DriveSingleton.getInstance().getLat(), DriveSingleton.getInstance().getLng());
    }

    public void driveParticipants(View view) {

    }
}