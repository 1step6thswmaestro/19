package com.soma6th.comdoc_android.model;

/**
 * Created by Kahye on 2015. 10. 25..
 */
public class SuggestionSheet {
    public int request_sheet;
    public String suggester;
    public int expect_price;
    public String expect_period;
    public String comment;
    public int status;
    public String visit_time;
    public String engineer;
    public String engineer_phone;
    public int id;
    public String createdAt;
    public String updatedAt;
    public String phone_number;


    public int getRequest_sheet() {
        return request_sheet;
    }

    public String getSuggester() {
        return suggester;
    }

    public int getExpect_price() {
        return expect_price;
    }

    public String getExpect_period() {
        return expect_period;
    }

    public String getComment() {
        return comment;
    }

    public int getStatus() {
        return status;
    }

    public String getVisit_time() {
        return visit_time;
    }

    public String getEngineer() {
        return engineer;
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

    public String getPhone_number() {
        return phone_number;
    }

    public String getEngineer_phone() {
        return engineer_phone;
    }

    public SuggestionSheet(int request_sheet, String suggester, int expect_price, String expect_period, String comment, int status, String visit_time, String engineer, String engineer_phone, int id, String createdAt, String updatedAt, String phone_number) {
        this.request_sheet = request_sheet;
        this.suggester = suggester;
        this.expect_price = expect_price;
        this.expect_period = expect_period;
        this.comment = comment;
        this.status = status;
        this.visit_time = visit_time;
        this.engineer = engineer;
        this.engineer_phone = engineer_phone;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.phone_number = phone_number;
    }

}
