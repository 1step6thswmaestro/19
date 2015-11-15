package com.soma6th.comdoc_android.model;

/**
 * Created by Kahye on 2015. 10. 25..
 */
public class RequestSheet {
    private String location;
    private String address;
    private String requester_phone;
    private String computer_type;
    private String available_time;
    private String trouble_type;
    private String used_year;
    private String brand;
    private String matching_status;
    private String final_price;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRequester_phone() {
        return requester_phone;
    }

    public void setRequester_phone(String requester_phone) {
        this.requester_phone = requester_phone;
    }

    public String getComputer_type() {
        return computer_type;
    }

    public void setComputer_type(String computer_type) {
        this.computer_type = computer_type;
    }

    public String getAvailable_time() {
        return available_time;
    }

    public void setAvailable_time(String available_time) {
        this.available_time = available_time;
    }

    public String getTrouble_type() {
        return trouble_type;
    }

    public void setTrouble_type(String trouble_type) {
        this.trouble_type = trouble_type;
    }

    public String getUsed_year() {
        return used_year;
    }

    public void setUsed_year(String used_year) {
        this.used_year = used_year;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMatching_status() {
        return matching_status;
    }

    public void setMatching_status(String matching_status) {
        this.matching_status = matching_status;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getTrouble_detail() {
        return trouble_detail;
    }

    public void setTrouble_detail(String trouble_detail) {
        this.trouble_detail = trouble_detail;
    }

    public String getFinal_start_date() {
        return final_start_date;
    }

    public void setFinal_start_date(String final_start_date) {
        this.final_start_date = final_start_date;
    }

    public String getFinal_end_date() {
        return final_end_date;
    }

    public void setFinal_end_date(String final_end_date) {
        this.final_end_date = final_end_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String created_date;
    private String trouble_detail;
    private String final_start_date;
    private String final_end_date;
    private String id;
    private String createdAt;
    private String updatedAt;
}
