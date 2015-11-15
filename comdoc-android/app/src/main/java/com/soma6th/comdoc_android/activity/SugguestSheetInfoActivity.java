package com.soma6th.comdoc_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.adapter.RVSuggetAdapter;
import com.soma6th.comdoc_android.model.SuggestionSheet;

import java.util.List;

import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;

public class SugguestSheetInfoActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "SugguestSheetInfoActivity";
    private boolean mVisible;
    private boolean isUserCanceled = false;
    int position;
    TextView expect_price;
    TextView expect_period;
    TextView comment;
    TextView status;
    TextView visit_time;
    TextView mBrand;
    TextView enginner;
    TextView createdAt;
    TextView updatedAt;
    int suggest_sheet_id;
    String company_id;
    int sheet_id;
    int matching_status;

    public int getSuggest_sheet_id() {
        return suggest_sheet_id;
    }

    public int getSheet_id() {
        return sheet_id;
    }


    public static RecyclerView rv;
    private List<SuggestionSheet> suggestionSheets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_sugguest_sheet_info);

        findViewById(R.id.suggest_crossImgView).setOnClickListener(this);
        Intent intent = getIntent();
        sheet_id = intent.getExtras().getInt("sheet_id");
        matching_status = intent.getExtras().getInt("Matching_status");
        this.position = intent.getExtras().getInt("position");
        init();

    }

    private void init() {
        Log.d(TAG, "init");
        mVisible = true;

        rv = (RecyclerView) findViewById(R.id.sheet_thumnail_rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

    }

    private void initializeData() {
        Log.d(TAG, "initializeData");
        this.suggestionSheets = ComdocApplication.sheetDatas.get(position).suggestion_sheets;
        Log.i("suggestionSheet", suggestionSheets.size() + "");
        if (suggestionSheets.size() == 0) {
            Toast.makeText(getApplicationContext(), "제안서 내역이 없습니다", Toast.LENGTH_SHORT).show();
            onPause();
        }
        //company_id = ComdocApplication.companyDatas.get(position).getSuggester().getId();
        Log.d("suggest_sheet_id",suggest_sheet_id+"");
        Log.d("suggest_sheet_id",suggest_sheet_id+"");


    }

    private void initializeAdapter() {
        Log.d(TAG, "initializeAdapter");
        RVSuggetAdapter adapter = new RVSuggetAdapter(getParent(),suggestionSheets, sheet_id, matching_status);
        rv.setAdapter(adapter);
        Log.d(TAG, "exe adapter success");
    }

    public RecyclerView getRv() {
        return rv;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.suggest_crossImgView:
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        finish();

    }

    public void activityDestroyAndSendData() {
        setResult(100);
        Log.d(TAG, "activityDestroyAndSendData");
        finish();
    }
}
