package com.soma6th.comdoc_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.soma6th.comdoc_android.adapter.RVAdapter;
import com.soma6th.comdoc_android.model.Sheet;
import com.soma6th.comdoc_android.model.SuggestionSheet;
import com.soma6th.comdoc_android.model.User;
import com.soma6th.comdoc_android.util.SheetRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;

public class SheetInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = SheetInfoActivity.class.getSimpleName();

    private final int DISTRICT_LIST_ACTION = 1;
    private final int POST_BUSINESS_ACTION = 2;
    private boolean isUserCanceled = false;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static Gson gson = new Gson();
    private List<SheetThumnail> thumnails;
    public static List<Sheet> requireSheet;
    public static List<SuggestionSheet> suggestionSheets;
    public static RecyclerView rv;
    private static String getUrl;
    public RVAdapter adapter = null;
    public Sheet[] sheets = null;
    public static int SheetSize;
    public static int SuggetstionSize[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_info);
        findViewById(R.id.crossImgView).setOnClickListener(this);

        Log.i(TAG, "onCreate");
        //ComdocApplication.matching_status = 0;
        init();

        Log.i(TAG, "onCreate2");
    }


    private void init() {
        SuggetstionSize = new int[100];
        rv = (RecyclerView) findViewById(R.id.sheet_thumnail_rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
    }

    public RecyclerView getRv() {
        return rv;
    }


    private void initializeData() {
        thumnails = new ArrayList<SheetThumnail>();
        requireSheet = new ArrayList<Sheet>();
        suggestionSheets = new ArrayList<SuggestionSheet>();
        User user = ComdocApplication.user;

        getUrl = "http://40.74.139.156:1337/sheets/user/" + user.getId();
        Log.d("getUrl", getUrl);

        client.get(getUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("sheet_get", "success");
                Log.d("response", response.toString());

                sheets = gson.fromJson(response.toString(), Sheet[].class);
                ComdocApplication.sheetDatas = Arrays.asList(sheets);

                if (ComdocApplication.sheetDatas.size() == 0) {
                    Toast.makeText(getApplication(), "견적서 신청내역이 없습니다", Toast.LENGTH_LONG).show();
                    finish();
                }

                adapter = new RVAdapter(ComdocApplication.sheetDatas);
                rv.setAdapter(adapter);

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("sheet_get", "fail");
            }

        });

        sheets = SheetRequest.sheets;
        Log.d("thumnails", thumnails.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        Log.i(TAG, "onResume");
        if(ComdocApplication.resumeFlag==1){
            ComdocApplication.resumeFlag=0;
            finish();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        if (isUserCanceled) {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            finish();
            Log.i(TAG, "onPause_in");
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSubmit:

                break;

            case R.id.crossImgView:
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;

        }
    }

    public void onUserCanceled(int code) {

    }

}
