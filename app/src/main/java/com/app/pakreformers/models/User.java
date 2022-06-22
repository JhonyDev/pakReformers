package com.app.pakreformers.models;

public class User {
    String id, firstName, lastName, desc,
            phone, address, status;

    public User() {
    }

    public User(String id, String firstName, String lastName, String desc, String phone, String address, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.desc = desc;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrEtFirstName() {
        return firstName;
    }

    public void setStrEtFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getStrEtLastName() {
        return lastName;
    }

    public void setStrEtLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStrEtClassroom() {
        return desc;
    }

    public void setStrEtClassroom(String desc) {
        this.desc = desc;
    }

    public String getStrEtPhone() {
        return phone;
    }

    public void setStrEtPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVerStatus() {
        return status;
    }

    public void setVerStatus(String status) {
        this.status = status;
    }
}
