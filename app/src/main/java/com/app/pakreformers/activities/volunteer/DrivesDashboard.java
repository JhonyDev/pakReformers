package com.app.pakreformers.activities.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.auth.AuthLoginActivity;
import com.app.pakreformers.adapters.TypeRecyclerViewAdapter;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.models.Drive;
import com.app.pakreformers.models.Super;
import com.app.pakreformers.services.FcmNotificationsSender;
import com.app.pakreformers.singletons.DriveSingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class DrivesDashboard extends AppCompatActivity implements Info {
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    List<Super> listInstances;
    RecyclerView recyclerView;
    RadioButton rbMy;
    RadioButton rbActive;
    TextView tvActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drives);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, AuthLoginActivity.class));
            finish();
        }

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        initRv();
        initData(false);
        rbMy.setChecked(true);
        rbActive.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                initData(true);

        });
        rbMy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b)
                initData(false);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DriveSingleton.setSelectedDrive(null);
        initData(false);
    }

    private void initData(Boolean is_active) {
        listInstances.clear();
        typeRecyclerViewAdapter.notifyDataSetChanged();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_DRIVES)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listInstances.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Drive drive = child.getValue(Drive.class);
                            if (drive != null) {
                                if (is_active) {
                                    if (drive.getStatus().equals(STATUS_ACTIVE))
                                        listInstances.add(drive);
                                } else {
                                    if (drive.getCreator().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                        listInstances.add(drive);
                                }

                            }
                        }
                        if (listInstances.isEmpty())
                            tvActive.setVisibility(View.VISIBLE);
                        else
                            tvActive.setVisibility(View.GONE);
                        typeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

    private void initRv() {
        recyclerView = findViewById(R.id.rv_services);
        rbActive = findViewById(R.id.radio_active_drives);
        rbMy = findViewById(R.id.radio_my_drives);
        tvActive = findViewById(R.id.no_drives);
        listInstances = new ArrayList<>();
        typeRecyclerViewAdapter = new TypeRecyclerViewAdapter(this, listInstances, 1);
        recyclerView.setAdapter(typeRecyclerViewAdapter);
    }

    public void createDrive(View view) {
        startActivity(new Intent(this, UpdateDrive.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DriveSingleton.setSelectedDrive(null);
    }
}