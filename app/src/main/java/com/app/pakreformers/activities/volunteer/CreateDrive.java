package com.app.pakreformers.activities.volunteer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.app.pakreformers.R;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.listeners.LocationListener;
import com.app.pakreformers.utils.LocationUtils;

public class CreateDrive extends AppCompatActivity implements LocationListener, Info {

    EditText etAddress;
    EditText et_title;
    EditText et_desc;
    Spinner spn_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drive);

        etAddress = findViewById(R.id.et_address);
        et_title = findViewById(R.id.et_title);
        et_desc = findViewById(R.id.et_desc);
        spn_address = findViewById(R.id.spn_address);
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
//            Intent intent = new Intent(this, MapActivity.class);
//            Bundle bundle = new Bundle();
//
//            bundle.putString(SimplePlacePicker.API_KEY,apiKey);
//            bundle.putString(SimplePlacePicker.COUNTRY,country);
//            bundle.putString(SimplePlacePicker.LANGUAGE,language);
//            bundle.putStringArray(SimplePlacePicker.SUPPORTED_AREAS,supportedAreas);
//
//            intent.putExtras(bundle);
//            startActivityForResult(intent, SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE);
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

    }

    @Override
    public void onLocationUpdated(String location) {
        etAddress.setText(location);
        Log.i(TAG, "onLocationUpdated: " + location);
    }

    @Override
    public void onGetCityCountry(String city, String country) {
//        etAddress.setText(city + " " + country);
        Log.i(TAG, "onLocationUpdated: " + city);
        Log.i(TAG, "onLocationUpdated: " + country);
    }
}