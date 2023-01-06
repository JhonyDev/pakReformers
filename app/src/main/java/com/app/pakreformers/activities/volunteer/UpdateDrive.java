package com.app.pakreformers.activities.volunteer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.app.pakreformers.R;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.listeners.LocationListener;
import com.app.pakreformers.models.CustomLocation;
import com.app.pakreformers.models.Drive;
import com.app.pakreformers.models.User;
import com.app.pakreformers.services.FcmNotificationsSender;
import com.app.pakreformers.singletons.CurrentUserSingleton;
import com.app.pakreformers.singletons.DriveSingleton;
import com.app.pakreformers.utils.LocationUtils;
import com.app.pakreformers.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UpdateDrive extends AppCompatActivity implements LocationListener, Info {

    EditText etAddress;
    EditText et_title;
    EditText et_desc;
    TextView txt_status;

    Spinner spn_address;
    Spinner spn_status;
    Button btn_delete;

    String str_etAddress;
    String str_et_title;
    String str_et_desc;
    String str_spn_address;

    CheckBox cbCloth;
    CheckBox cbMoney;
    CheckBox cbMed;
    CheckBox cbShelter;
    CheckBox cbFood;

    boolean is_update;

    double lat = -999;
    double lng = -999;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drive);

        initCurrentUser();

        etAddress = findViewById(R.id.et_address);
        et_title = findViewById(R.id.et_title);
        et_desc = findViewById(R.id.et_desc);
        spn_address = findViewById(R.id.spn_address);
        spn_status = findViewById(R.id.spn_status);
        btn_delete = findViewById(R.id.btn_delete);
        txt_status = findViewById(R.id.txt_status);
        btn_delete.setVisibility(View.GONE);
        spn_status.setVisibility(View.GONE);
        txt_status.setVisibility(View.GONE);

        cbCloth = findViewById(R.id.cl);
        cbMoney = findViewById(R.id.mn);
        cbMed = findViewById(R.id.md);
        cbShelter = findViewById(R.id.sh);
        cbFood = findViewById(R.id.fd);

        spn_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getColor(R.color.black));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        initLocation();

        etAddress.setOnClickListener(view -> {

        });
        if (DriveSingleton.getInstance() != null) {
            if (!DriveSingleton.getInstance().getCreator().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                DriveSingleton.setSelectedDrive(null);
                return;
            }
            is_update = true;
            btn_delete.setVisibility(View.VISIBLE);
            spn_status.setVisibility(View.VISIBLE);
            txt_status.setVisibility(View.VISIBLE);
            etAddress.setText(DriveSingleton.getInstance().getAddress());
            et_title.setText(DriveSingleton.getInstance().getTitle());
            et_desc.setText(DriveSingleton.getInstance().getDescription());
            List<String> array = Arrays.asList(getResources().getStringArray(R.array.drive_type));
            spn_address.setSelection(array.indexOf(DriveSingleton.getInstance().getType()));

            List<String> array2 = Arrays.asList(getResources().getStringArray(R.array.statuses));
            spn_status.setSelection(array2.indexOf(DriveSingleton.getInstance().getStatus()));

            try {
                cbMoney.setChecked(DriveSingleton.getInstance().getMoney().equals("Money"));
                cbFood.setChecked(DriveSingleton.getInstance().getFood().equals("Food"));
                cbShelter.setChecked(DriveSingleton.getInstance().getShelter().equals("Shelter"));
                cbMed.setChecked(DriveSingleton.getInstance().getMedicine().equals("Medicine"));
                cbCloth.setChecked(DriveSingleton.getInstance().getCloths().equals("Cloth"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void initCurrentUser() {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user == null) {
                            Toast.makeText(UpdateDrive.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        CurrentUserSingleton.setInstance(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initLocation() {
        if (isLocationPermissionGranted())
            initGPS();
    }

    public boolean isLocationPermissionGranted() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return false;
        }
    }

    private void initGPS() {
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationUtils locationListener = new LocationUtils(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    public void back(View view) {
        finish();
    }

    public void submit(View view) {
        if (!Utils.validEt(et_title))
            return;
        if (!Utils.validEt(et_desc))
            return;
        if (lat == -999) {
            Toast.makeText(this, "Please wait for the location to be defined", Toast.LENGTH_SHORT).show();
            return;
        }
        castStrings();
        String id = UUID.randomUUID().toString();
        if (is_update)
            id = DriveSingleton.getInstance().getId();


        Drive drive = new Drive(id, str_et_title, str_et_desc, str_spn_address, str_etAddress
                , FirebaseAuth.getInstance().getCurrentUser().getUid(), STATUS_ACTIVE, lat, lng);

        if (cbMoney.isChecked())
            drive.setMoney("Money");
        else
            drive.setMoney("");

        if (cbFood.isChecked())
            drive.setFood("Food");
        else
            drive.setFood("");

        if (cbShelter.isChecked())
            drive.setShelter("Shelter");
        else
            drive.setShelter("");

        if (cbMed.isChecked())
            drive.setMedicine("Medicine");
        else
            drive.setMedicine("");

        if (cbCloth.isChecked())
            drive.setCloths("Cloth");
        else
            drive.setCloths("");

        drive.setLat(lat);
        drive.setLng(lng);

        FirebaseDatabase.getInstance().getReference().child(NODE_DRIVES)
                .child(id).setValue(drive).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Success! \uD83D\uDE4C", Toast.LENGTH_SHORT).show();
                        finish();
                        if (!is_update) {
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all", drive.getTitle(), drive.getDescription(), getApplicationContext(), UpdateDrive.this);
                            notificationsSender.SendNotifications();
                        }
                    }
                });
    }

    private void castStrings() {
        str_etAddress = etAddress.getText().toString();
        str_et_title = et_title.getText().toString();
        str_et_desc = et_desc.getText().toString();
        str_spn_address = spn_address.getSelectedItem().toString();
    }

    @Override
    public void onLocationUpdated(CustomLocation location) {
        etAddress.setText(location.getLocation());
        lat = location.getLat();
        lng = location.getLng();
    }

    @Override
    public void onGetCityCountry(String city, String country) {

    }

    public void delete(View view) {
        FirebaseDatabase.getInstance().getReference().child(NODE_DRIVES)
                .child(DriveSingleton.getInstance().getId()).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        finish();
                        Toast.makeText(this, "Drive successfully deleted", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void showOnMap(View view) {
        if (lat != -999) {
            Utils.showMarkerOnMap(this, lat, lng);
        } else {
            Toast.makeText(this, "Please wait for the location to be defined", Toast.LENGTH_SHORT).show();
        }
    }
}