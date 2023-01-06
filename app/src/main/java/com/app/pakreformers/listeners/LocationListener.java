package com.app.pakreformers.listeners;

import com.app.pakreformers.models.CustomLocation;

public interface LocationListener {
    void onLocationUpdated(CustomLocation location);
    void onGetCityCountry(String city, String country);
}
