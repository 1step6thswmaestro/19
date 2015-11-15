package com.soma6th.comdoc_android.model;

import java.io.Serializable;

/**
 * @author Audacity IT Solutions Ltd.
 * @class User
 * @brief data structure class for storing user information
 */

public class User implements Serializable {

    public String id;
    public String phoneNumber;
    public String name;
    public String email;
    public String location;

    public User(String id, String phoneNumber, String name, String email, String location) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.location = location;
    }
    public User(){

    }
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
