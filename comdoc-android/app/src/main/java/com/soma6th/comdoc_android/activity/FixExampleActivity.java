package com.soma6th.comdoc_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.adapter.RVFixExamAdapter;
import com.soma6th.comdoc_android.model.ExamCategory;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;

public class FixExampleActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = "FixExampleActivity";
    private boolean isUserCanceled = false;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static Gson gson = new Gson();
    private List<ExamCategory> examCategoryList;
    public static RecyclerView rv;
    private static String getUrl;
    private int position;
    String sheet_id;
    public ExamCategory[] examCategories = null;
    public RVFixExamAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_fix_example);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        findViewById(R.id.fix_crossImgView).setOnClickListener(this);
        init();
    }

    private void init() {
        Log.d(TAG, "init");
        rv = (RecyclerView) findViewById(R.id.fix_exam_rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
    }

    private void initializeData() {
        Log.d(TAG, "initializeData");
        ComdocApplication.examCategories = new ArrayList<ExamCategory>();
        examCategoryList = ComdocApplication.examCategories;
        position += 1;//trouble_Type
        getUrl = "http://40.74.139.156:1337/repaircase/trouble_type/" + position;
        Log.d("getUrl", getUrl);

        client.get(getUrl, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.d(TAG, "onSuccess");
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG + "category", response.toString());
                examCategories = gson.fromJson(response.toString(), ExamCategory[].class);
                examCategoryList = Arrays.asList(examCategories);//해당 trouble Type의 수리사례만 있음
                position -= 1;
                Log.d(TAG, " :" + position);
                //sheet_id = examCategoryList.get(position).sheet_id;

                //Log.d(TAG + "examCategoryList", examCategoryList.get(1).toString());
                if (examCategories.length == 0) {
                    Toast.makeText(getApplication(), "수리사례가 아직 등록되지않았습니다", Toast.LENGTH_LONG).show();
                    finish();
                }
                adapter = new RVFixExamAdapter(examCategoryList, position);
                rv.setAdapter(adapter);
                Log.d(TAG + "FixExam_Get", statusCode + "success2");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d(TAG, "onFailure");
                Log.d("category", statusCode + errorResponse.toString());
                if (statusCode == 200) {
                    examCategories = gson.fromJson(errorResponse.toString(), ExamCategory[].class);
                    examCategoryList = Arrays.asList(examCategories);

                    Log.d(TAG + "examCategoryList", examCategoryList.get(0).company_location);
                    if (examCategories.length == 0) {
                        Toast.makeText(getApplication(), "수리사례가 아직 등록되지않았습니다", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    adapter = new RVFixExamAdapter(examCategoryList, position);
                    rv.setAdapter(adapter);
                    Log.d(TAG + "FixExam_Get", statusCode + "success1");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fix_crossImgView:
                Log.d("fix exit","fix iexit");
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        finish();
    }
}
