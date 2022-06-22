package com.app.pakreformers.listeners;

public interface LocationListener {
    void onLocationUpdated(String location);
    void onGetCityCountry(String city, String country);
}
