package com.app.pakreformers.models;

public class CustomLocation {
    String location;
    double lat;
    double lng;

    public CustomLocation(String location, double lat, double lng) {
        this.location = location;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
