package com.soma6th.comdoc_android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.model.ExamCategory;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

import static com.soma6th.comdoc_android.R.id.exam_detail_review_rating;
import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;

public class FixExamDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isUserCanceled = false;
    TextView mExam_detail_location;
    TextView mExam_detail_computer_type;
    TextView mExam_detail_brand;
    TextView mExam_detail_used_year;
    TextView mExam_detail_final_price;
    TextView mExam_detail_final_start_date;
    TextView mExam_detail_final_end_date;
    TextView mExam_detail_sheet_id;
    TextView mExam_detail_suggestion_comment;
    TextView mExam_detail_suggestion_price;
    TextView mExam_detail_suggestion_visit_time;
    String CompanyId;


    TextView mExam_detail_suggestion_review_point;
    TextView mExam_detail_suggestion_review_comment;

    TextView mExam_detail_company_name;
    TextView mExam_detail_company_location;
    TextView mExam_detail_company_dtlocation;
    TextView mExam_detail_company_phone;
    TextView mExam_detail_company_explan;
    TextView mExam_detail_company_avrating;
    RatingBar mExam_detail_review_rating;
    RatingBar mExam_detail_av_rating;


    private List<ExamCategory> examCategoryList;
    private static AsyncHttpClient client = new AsyncHttpClient();
    public ExamCategory[] examCategories = null;
    private static Gson gson = new Gson();

    int position;
    int trouble_type;
    String sheet_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_exam_detail);
        findViewById(R.id.Info_crossImgView).setOnClickListener(this);
        findViewById(R.id.exam_detail_request).setOnClickListener(this);
        init();

    }

    private void init() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        sheet_id = intent.getStringExtra("sheet_id");
        trouble_type = intent.getIntExtra("trouble_type", 0);
        trouble_type+=1;

        String getUrl = "http://40.74.139.156:1337/repaircase/trouble_type/detail/" + trouble_type+"/"+sheet_id;
        Log.d("getUrl", getUrl);

        client.get(getUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response", response.toString());

                examCategories = gson.fromJson(response.toString(), ExamCategory[].class);
                examCategoryList = Arrays.asList(examCategories);

                mExam_detail_location = (TextView) findViewById(R.id.exam_detail_location);
                mExam_detail_computer_type = (TextView) findViewById(R.id.exam_detail_computer_type);
                mExam_detail_brand = (TextView) findViewById(R.id.exam_detail_brand);
                mExam_detail_used_year = (TextView) findViewById(R.id.exam_detail_used_year);
                mExam_detail_final_price = (TextView) findViewById(R.id.exam_detail_final_price);
                mExam_detail_final_start_date = (TextView) findViewById(R.id.exam_detail_final_start_date);
                mExam_detail_final_end_date = (TextView) findViewById(R.id.exam_detail_final_end_date);
                mExam_detail_suggestion_comment = (TextView) findViewById(R.id.exam_detail_suggestion_comment);
                mExam_detail_suggestion_price = (TextView) findViewById(R.id.exam_detail_suggestion_price);
                mExam_detail_suggestion_visit_time = (TextView) findViewById(R.id.exam_detail_suggestion_visit_time);

                mExam_detail_suggestion_review_point = (TextView) findViewById(R.id.exam_detail_suggestion_review_point);
                mExam_detail_suggestion_review_comment = (TextView) findViewById(R.id.exam_detail_suggestion_review_comment);
                mExam_detail_review_rating = (RatingBar) findViewById(exam_detail_review_rating);
                Drawable progress = mExam_detail_review_rating.getProgressDrawable();
                DrawableCompat.setTint(progress, Color.WHITE);
                LayerDrawable stars = (LayerDrawable) mExam_detail_review_rating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.rgb(255, 166, 51), PorterDuff.Mode.SRC_ATOP);

                mExam_detail_company_name = (TextView) findViewById(R.id.exam_detail_company_name);
                mExam_detail_company_location = (TextView) findViewById(R.id.exam_detail_company_location);
                mExam_detail_company_dtlocation = (TextView) findViewById(R.id.exam_detail_company_dtlocation);
                mExam_detail_company_phone = (TextView) findViewById(R.id.exam_detail_company_phone);
                mExam_detail_company_explan = (TextView) findViewById(R.id.exam_detail_company_explan);
                mExam_detail_company_avrating = (TextView) findViewById(R.id.exam_detail_company_avrating);
                mExam_detail_av_rating = (RatingBar) findViewById(R.id.exam_detail_av_rating);
                progress = mExam_detail_av_rating.getProgressDrawable();
                DrawableCompat.setTint(progress, Color.WHITE);
                stars = (LayerDrawable) mExam_detail_av_rating.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(Color.rgb(255, 166, 51), PorterDuff.Mode.SRC_ATOP);

                Log.d("examCategoryList size", examCategoryList.size() + "");
                Log.d("position", position+"");
                mExam_detail_location.setText(examCategoryList.get(0).sheet_location.toString());
                mExam_detail_computer_type.setText(examCategoryList.get(0).computer_type.toString());
                mExam_detail_brand.setText(examCategoryList.get(0).brand.toString());
                mExam_detail_used_year.setText(examCategoryList.get(0).used_year.toString());
                mExam_detail_final_price.setText(examCategoryList.get(0).final_price.toString());
                mExam_detail_final_start_date.setText(examCategoryList.get(0).final_start_date.toString());
                mExam_detail_final_end_date.setText(examCategoryList.get(0).final_end_date.toString());
                mExam_detail_suggestion_comment.setText(examCategoryList.get(0).suggestion_comment.toString());
                mExam_detail_suggestion_price.setText(examCategoryList.get(0).suggestion_price.toString());
                mExam_detail_suggestion_visit_time.setText(examCategoryList.get(0).suggestion_visit_time.toString());
                CompanyId = examCategoryList.get(0).company_id.toString();

                mExam_detail_suggestion_review_point.setText( examCategoryList.get(0).review_point.toString());
                mExam_detail_suggestion_review_comment.setText(examCategoryList.get(0).review_comment.toString());
                mExam_detail_review_rating.setIsIndicator(true);
                mExam_detail_review_rating.setRating(Float.parseFloat(mExam_detail_suggestion_review_point.getText().toString()));

                mExam_detail_company_name.setText(examCategoryList.get(0).company_admin_name.toString());
                mExam_detail_company_location.setText(examCategoryList.get(0).company_location.toString());
                mExam_detail_company_dtlocation.setText(" " + examCategoryList.get(0).company_address.toString());
                mExam_detail_company_phone.setText(examCategoryList.get(0).company_phone.toString());
                mExam_detail_company_explan.setText(examCategoryList.get(0).company_description.toString());
                mExam_detail_company_avrating.setText(examCategoryList.get(0).company_avgpoint.toString());
                mExam_detail_av_rating.setIsIndicator(true);
                mExam_detail_av_rating.setRating(Float.parseFloat(mExam_detail_company_avrating.getText().toString()));

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("sheet_get", "fail");
            }

        });
        

    }
    @Override
     protected void onPause() {
        super.onPause();

        if (isUserCanceled) {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Info_crossImgView:
            hideSoftKeyboard(this);
            isUserCanceled = true;
            onPause();
            break;

            case R.id.exam_detail_request:
                //Todo 회사포함 견적서 요청
                Intent intent = new Intent(FixExamDetailActivity.this, SheetRequestActivity.class);
                intent.putExtra("CompanyId", CompanyId);
                intent.putExtra("Company_phone", examCategoryList.get(0).company_phone.toString());
                ComdocApplication.company_flag=1;
                Log.d("company_id",CompanyId);
                startActivity(intent);
                finish();
                break;
        }

    }
}
