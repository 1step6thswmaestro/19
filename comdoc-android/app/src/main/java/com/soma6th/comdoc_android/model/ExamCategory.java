package com.soma6th.comdoc_android.model;

/**
 * Created by Kahye on 2015. 10. 31..
 */
public class ExamCategory {
    public String company_id;
    public String company_email;
    public String company_phone;
    public String company_location;
    public String company_address;

    public String company_description;
    public String company_avgpoint;
    public String company_name;
    public String company_admin_name;
    public String sheet_location;
    public String sheet_address;
    public String requester_phone;
    public String computer_type;
    public String trouble_type;
    public String trouble_detail;

    public String used_year;
    public String brand;
    public String final_price;
    public String final_start_date;
    public String final_end_date;
    public String sheet_id;
    public String suggestion_comment;
    public String suggestion_price;
    public String suggestion_visit_time;
    public String suggestion_sheet_id;
    public String review_point;
    public String review_comment;

   public ExamCategory(String company_id, String company_email, String company_phone, String company_location, String company_address, String company_description, String company_avgpoint, String company_name, String company_admin_name, String sheet_location, String sheet_address, String requester_phone, String computer_type, String trouble_type, String trouble_detail, String used_year, String brand, String final_price, String final_start_date, String final_end_date, String sheet_id, String suggestion_comment, String suggestion_price, String suggestion_visit_time, String suggestion_sheet_id, String review_point, String review_comment) {
        this.company_id = company_id;
        this.company_email = company_email;
        this.company_phone = company_phone;
        this.company_location = company_location;
        this.company_address = company_address;
        this.company_description = company_description;
        this.company_avgpoint = company_avgpoint;
        this.company_name = company_name;
        this.company_admin_name = company_admin_name;
        this.sheet_location = sheet_location;
        this.sheet_address = sheet_address;
        this.requester_phone = requester_phone;
        this.computer_type = computer_type;
        this.trouble_type = trouble_type;
        this.trouble_detail = trouble_detail;
        this.used_year = used_year;
        this.brand = brand;
        this.final_price = final_price;
        this.final_start_date = final_start_date;
        this.final_end_date = final_end_date;
        this.sheet_id = sheet_id;
        this.suggestion_comment = suggestion_comment;
        this.suggestion_price = suggestion_price;
        this.suggestion_visit_time = suggestion_visit_time;
        this.suggestion_sheet_id = suggestion_sheet_id;
        this.review_point = review_point;
        this.review_comment = review_comment;
    }
}
