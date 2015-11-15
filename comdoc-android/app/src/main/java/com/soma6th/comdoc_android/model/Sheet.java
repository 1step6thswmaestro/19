package com.soma6th.comdoc_android.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kahye on 2015. 10. 25..
 */
public class Sheet implements Serializable {

    public ArrayList<SuggestionSheet> suggestion_sheets = new ArrayList<>();
    public Requester requester;
    public String location;
    public String address;
    public String requester_phone;
    public String computer_type;
    public String available_time;
    public String trouble_type;
    public String used_year;
    public String brand;
    public int matching_status;
    public int final_price;
    public String created_date;
    public String trouble_detail;
    public String final_start_date;
    public String final_end_date;
    public int id;
    public String createdAt;
    public String updatedAt;
    public static Sheet sheet;

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getRequester_phone() {
        return requester_phone;
    }

    public String getComputer_type() {
        return computer_type;
    }

    public String getAvailable_time() {
        return available_time;
    }

    public String getTrouble_type() {
        return trouble_type;
    }

    public String getUsed_year() {
        return used_year;
    }

    public String getBrand() {
        return brand;
    }

    public int getMatching_status() {
        return matching_status;
    }

    public int getFinal_price() {
        return final_price;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getTrouble_detail() {
        return trouble_detail;
    }

    public String getFinal_start_date() {
        return final_start_date;
    }

    public String getFinal_end_date() {
        return final_end_date;
    }

    public int getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<SuggestionSheet> getSuggestion_sheets() {
        return suggestion_sheets;
    }

    public void setSuggestion_sheets(ArrayList<SuggestionSheet> suggestion_sheets) {
        this.suggestion_sheets = suggestion_sheets;
    }

    public Requester getRequester() {
        return requester;
    }

    public Sheet(){

    }
    public Sheet(int id , String location, String address, String requester_phone, String computer_type, String available_time, String trouble_type, String used_year, int matching_status, String brand, String created_date, int final_price, String trouble_detail) {
        this.location = location;//
        this.address = address;
        this.requester_phone = requester_phone;
        this.computer_type = computer_type;//
        this.available_time = available_time;
        this.trouble_type = trouble_type;///
        this.used_year = used_year;
        this.matching_status = matching_status;
        this.brand = brand;
        this.created_date = created_date;
        this.final_price = final_price;
        this.trouble_detail = trouble_detail;
        this.id = id;
    }

    static {
        sheet = new Sheet();
    }

    public static Sheet getSheet() {
        return sheet;
    }
}