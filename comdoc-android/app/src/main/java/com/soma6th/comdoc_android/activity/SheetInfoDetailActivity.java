package com.soma6th.comdoc_android.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.adapter.RVAdapter;
import com.soma6th.comdoc_android.fragment.HomeFragment;
import com.soma6th.comdoc_android.model.Sheet;
import com.soma6th.comdoc_android.model.SuggestionSheet;
import com.soma6th.comdoc_android.model.User;
import com.soma6th.comdoc_android.util.SheetRequest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;
import static com.soma6th.comdoc_android.util.UtilMethods.showFinishFixDialog;
import static com.soma6th.comdoc_android.util.UtilMethods.showReviewDialog;


public class SheetInfoDetailActivity extends AppCompatActivity implements View.OnClickListener, HomeFragment.CategorySelectionCallbacks {

    public static String TAG = "SheetInfoDetailActivity";
    private boolean isUserCanceled = false;
    private int matching_flag = 0;
    private int position;
    private int suggestionSize;
    private int Matching_status;
    private CardView cvStatus;
    private CardView cvReview;
    private TextView mLocation;
    private TextView mRequester_phone;
    private TextView mComputer_type;
    private TextView mAvailable_time;
    private TextView mTrouble_type;
    private TextView mUsed_year;
    private TextView mBrand;
    private TextView mMatching_status;
    private TextView mAddress;
    private TextView mSelectbtn;
    private TextView mReviewbtn;
    private ArrayList<SuggestionSheet> suggestionSheets;

    private static AsyncHttpClient client = new AsyncHttpClient();
    public Sheet[] sheets = null;
    private Gson gson = new Gson();
    public static int SheetSize;
    private List<SheetThumnail> thumnails;
    public static int SuggetstionSize[];
    public RVAdapter adapter = null;

    public int getSheet_id() {
        return sheet_id;
    }

    private TextView mTrouble_detail;
    private TextView mFinal_price;
    private TextView mCreatedAt;
    private int sheet_id;
    private String engneer_phone;
    private android.support.v4.app.FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private List<Sheet> requireSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        ComdocApplication.resumeFlag = 1;
        setContentView(R.layout.activity_sheet_info_detail);

        mSelectbtn = (TextView) findViewById(R.id.Info_btnSelect);
        mReviewbtn = (TextView) findViewById(R.id.Info_review_btnSelect);

        findViewById(R.id.Info_crossImgView).setOnClickListener(this);
        findViewById(R.id.Info_btnSubmit).setOnClickListener(this);
        findViewById(R.id.Info_review_btnSelect).setOnClickListener(this);
        mSelectbtn.setOnClickListener(this);

        init();

    }

    private void init() {
        Log.d(TAG, "Init");
        Intent intent = getIntent();
        position = RVAdapter.cardNum;

        suggestionSize = intent.getIntExtra("suggestionSize", 0);

        requireSheet = ComdocApplication.sheetDatas;
        cvStatus = (CardView) findViewById(R.id.cvStatus);
        cvReview = (CardView) findViewById(R.id.cvReview);
        mLocation = (TextView) findViewById(R.id.detail_location);
        mRequester_phone = (TextView) findViewById(R.id.detail_requester_phone);
        mComputer_type = (TextView) findViewById(R.id.detail_computer_type);
        mAvailable_time = (TextView) findViewById(R.id.detail_available_time);
        mTrouble_type = (TextView) findViewById(R.id.detail_trouble_type);
        mUsed_year = (TextView) findViewById(R.id.detail_used_year);
        mBrand = (TextView) findViewById(R.id.detail_brand);
        mAddress = (TextView) findViewById(R.id.detail_address);
        mTrouble_detail = (TextView) findViewById(R.id.detail_trouble_detail);
        mCreatedAt = (TextView) findViewById(R.id.detail_create_time);

        mLocation.setText(requireSheet.get(position).location);
        mRequester_phone.setText(requireSheet.get(position).requester_phone);
        mComputer_type.setText(requireSheet.get(position).computer_type);
        mAvailable_time.setText(requireSheet.get(position).available_time);
        mTrouble_type.setText(requireSheet.get(position).trouble_type);
        mUsed_year.setText(requireSheet.get(position).used_year);
        mBrand.setText(requireSheet.get(position).brand);

        mAddress.setText(requireSheet.get(position).address);
        mTrouble_detail.setText(requireSheet.get(position).trouble_detail);
        mCreatedAt.setText(requireSheet.get(position).created_date.substring(0, 10));

        if (requireSheet.get(position).matching_status == 0) {//제안서 없던 상태
            if (ComdocApplication.matching_status == 1) {//제안서 선택함
                Log.d("asdfdf", "IF in 1");
                Log.d("asdfdf", position + "");
                if (matching_flag == 1) {//수리완료를 누렀는데 다시 제안서를 선택한 경우
                    Toast.makeText(getApplicationContext(), "이미 수리가 완료되었습니다", Toast.LENGTH_SHORT).show();
                }
                //mMatching_status.setText("연결완료");
                mSelectbtn.setText("수리가 완료되었다면 클릭해주세요");
                cvStatus.setVisibility(View.VISIBLE);
                mSelectbtn.setVisibility(View.VISIBLE);
            } else if (ComdocApplication.matching_status == 2) {//수리완료를 누름
                Log.d("asdfdf", "IF in 2");
                Log.d("asdfdf", position + "");
                matching_flag = 1;
                //mMatching_status.setText("수리완료");
                mSelectbtn.setText(" 수리완료 ");
                mSelectbtn.setVisibility(View.VISIBLE);
                mReviewbtn.setVisibility(View.VISIBLE);
            } else {//
                //mMatching_status.setText("연결중");
            }
        } else if (requireSheet.get(position).matching_status == 1) {//연결중
            if (ComdocApplication.matching_status == 2) {
                Log.d("asdfdf", "IF in 3");
                Log.d("asdfdf", position + "");
                // mMatching_status.setText(" 수리완료 ");
                mSelectbtn.setText(" 수리완료 ");
                cvStatus.setVisibility(View.VISIBLE);
                cvReview.setVisibility(View.VISIBLE);
                mSelectbtn.setVisibility(View.VISIBLE);
                mReviewbtn.setVisibility(View.VISIBLE);
            } else {
                Log.d("asdfdf", "IF in 4");
                Log.d("asdfdf", position + "");
                Log.d("asdfdf", ComdocApplication.matching_status + "");
                mSelectbtn.setText(" 수리가 완료되었다면 클릭해주세요 ");
                mSelectbtn.setVisibility(View.VISIBLE);
                cvStatus.setVisibility(View.VISIBLE);
            }
        } else if (requireSheet.get(position).matching_status == 2) {
            Log.d("asdfdf", "IF in 5");
            Log.d("asdfdf", position + "");
            //mMatching_status.setText("수리완료");
            mSelectbtn.setText(" 수리완료 ");
            cvStatus.setVisibility(View.VISIBLE);
            cvReview.setVisibility(View.VISIBLE);
            mSelectbtn.setVisibility(View.VISIBLE);
            mReviewbtn.setVisibility(View.VISIBLE);

        }
        sheet_id = requireSheet.get(position).id;
        Matching_status = requireSheet.get(position).matching_status;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Info_crossImgView:
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onFinsh();
                break;

            case R.id.Info_btnSubmit:
                //Toast.makeText(getApplicationContext(), "제출 완료", Toast.LENGTH_SHORT).show();
                hideSoftKeyboard(this);
                isUserCanceled = true;
                if (requireSheet.size() > 0) {
                    Intent intent = new Intent(SheetInfoDetailActivity.this, SugguestSheetInfoActivity.class);
                    intent.putExtra("sheet_id", sheet_id);
                    intent.putExtra("position", position);
                    intent.putExtra("Matching_status", Matching_status);
                    //startActivity(intent);
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(getApplicationContext(), "견적서 내역이 없습니다", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.Info_review_btnSelect:

                Toast.makeText(getApplicationContext(), "리뷰페이지", Toast.LENGTH_SHORT).show();
                //리뷰를 위해 수리완료된 업체의 번호를 가져옴
                sheet_id = requireSheet.get(position).id;
                for (int i = 0; i < requireSheet.get(position).suggestion_sheets.size(); i++) {
                    if (requireSheet.get(position).suggestion_sheets.get(i).status == 2) {
                        engneer_phone = requireSheet.get(position).suggestion_sheets.get(i).engineer_phone;
                        showReviewDialog(v.getContext(), "리뷰등록", "리뷰를 등록해 주세요", "NO", "YES", sheet_id, engneer_phone);
                        Log.d("engneer Phone", engneer_phone);
                        break;
                    }
                }

                break;
            case R.id.Info_btnSelect:
                hideSoftKeyboard(this);
                isUserCanceled = true;
                Log.d("suggestion size", ComdocApplication.sheetDatas.get(position).suggestion_sheets.size() + "");
                showFinishFixDialog(v.getContext(), "수리완료", "수리가 완료되었습니까?", "NO", "YES", sheet_id);
                break;

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        if (ComdocApplication.matching_status != 0) {
            finish();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (isUserCanceled) {
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            finish();
        }
    }

    protected void onFinsh() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (isUserCanceled) {
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            finish();
        }
    }

    @Override
    public void onCategorySelected(String catID, String title) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, " onActivityResult");
        requireSheet = new ArrayList<Sheet>();
        suggestionSheets = new ArrayList<SuggestionSheet>();
        User user = ComdocApplication.user;

        String getUrl = "http://40.74.139.156:1337/sheets/user/" + user.getId();
        Log.d("getUrl", getUrl);

        client = new AsyncHttpClient();

        client.get(getUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("sheet_get", "success");
                Log.d("response", response.toString());

                sheets = gson.fromJson(response.toString(), Sheet[].class);
                List<Sheet> sheetList = Arrays.asList(sheets);

                if (sheetList.size() == 0) {
                    Toast.makeText(getApplication(), "견적서 신청내역이 없습니다", Toast.LENGTH_LONG).show();
                    finish();
                }

                adapter = new RVAdapter(sheetList);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("sheet_get", "fail");
            }

        });

        sheets = SheetRequest.sheets;

    }

    public void activityDestroyAndSendData() {
        Log.d(TAG, " activityDestroyAndSendData");
        setResult(100);
        finish();
    }

}
