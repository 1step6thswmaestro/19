package com.soma6th.comdoc_android.model;

/**
 * Created by Kahye on 2015. 11. 8..
 */
public class Company {
    Company_requester request_sheet;
    Company_company suggester;
    int expect_price;
    String expect_period;
    String comment;
    int status;
    String visit_time;
    String engineer;
    String engineer_phone;
    int id;
    String createdAt;

    public Company_requester getRequest_sheet() {
        return request_sheet;
    }

    public Company_company getSuggester() {
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

    public String getEngineer_phone() {
        return engineer_phone;
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

    String updatedAt;
}
