package com.app.pakreformers.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.app.pakreformers.info.Info;
import com.app.pakreformers.listeners.LocationListener;

import java.util.List;
import java.util.Locale;

public class LocationUtils implements android.location.LocationListener, Info {

    Context context;
    LocationListener locationListener;

    public LocationUtils(Context context) {
        this.context = context;
        locationListener = (LocationListener) context;
    }

    @Override
    public void onLocationChanged(Location loc) {
        Log.i(TAG, "onLocationChanged: LocationUtils");
        String cityName = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                Log.i(TAG, "onLocationChanged: " + addresses.get(0));
                try {
                    cityName = addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                    locationListener.onLocationUpdated(cityName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onLocationChanged: Addresses " + addresses);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onLocationChanged: Exception " + e.getMessage());
        }
        String s = cityName;
        Log.i(TAG, "onLocationChanged: String city name" + s);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
