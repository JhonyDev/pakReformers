package com.app.pakreformers.models;

public class Drive extends Super {
    String id;
    String title;
    String description;
    String type;
    String address;
    String creator;
    String status;

    String cloths;
    String money;
    String food;
    String shelter;
    String medicine;

    double lat;
    double lng;

    public Drive() {
    }

    public Drive(String id, String title, String description, String type, String address,
                 String creator, String status, double lat, double lng) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.address = address;
        this.creator = creator;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
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

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getCloths() {
        return cloths;
    }

    public void setCloths(String cloths) {
        this.cloths = cloths;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getShelter() {
        return shelter;
    }

    public void setShelter(String shelter) {
        this.shelter = shelter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
