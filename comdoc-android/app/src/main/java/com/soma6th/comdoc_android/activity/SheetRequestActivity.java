package com.soma6th.comdoc_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.util.FloatLabel;
import com.soma6th.comdoc_android.util.SheetRequest;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;

public class SheetRequestActivity extends Activity implements View.OnClickListener {
    private boolean isUserCanceled = false;
    private static AlertDialog dialog = null;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private RadioGroup rgFix;
    private RadioGroup rgTime;
    private RadioGroup rgType;
    private RadioGroup rgbuytime;
    private RadioGroup rgbrand;
    private FloatLabel etPhon;//연락처
    private FloatLabel etRegion;//지역
    private FloatLabel etsfRegion;//지역
    private FloatLabel etDtailfix;
    private TextView tvLocation;
    private String location = "서울시 강남구";
    private String address = "나머지주소";
    private String requester_phone = "010-9781-0031";
    private String computer_type = "노트북";
    private String brand = "삼성";
    private String used_year = "1년 이후";
    private String trouble_type = "인터넷이 안됌";
    private String trouble_detail = "고장상세유형";
    private String available_time = "평일오전";
    private String include;
    private String company_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_request);
        if (ComdocApplication.company_flag == 1) {
            Log.d("compay_request", "회사선택제");
        }
        init();
    }

    private void init() {
        findViewById(R.id.request_crossImgView).setOnClickListener(this);
        findViewById(R.id.request_btnSubmit).setOnClickListener(this);

        etPhon = (FloatLabel) findViewById(R.id.request_et_phone);
        tvLocation = (TextView) findViewById(R.id.request_tv_location);
        tvLocation.setOnClickListener(this);
        etsfRegion = (FloatLabel) findViewById(R.id.request_et_sf_regeion);
        etDtailfix = (FloatLabel) findViewById(R.id.request_et_detail);
        rgType = (RadioGroup) findViewById(R.id.request_radio_tv_type);
        rgbrand = (RadioGroup) findViewById(R.id.request_radio_tv_brand);
        rgTime = (RadioGroup) findViewById(R.id.request_radio_tv_time);
        rgFix = (RadioGroup) findViewById(R.id.request_radio_tv_fix);
        rgbuytime = (RadioGroup) findViewById(R.id.request_radio_tv_buytime);

        if (ComdocApplication.company_flag == 1) {
            Intent intent = getIntent();
            include = intent.getExtras().getString("CompanyId");
            company_phone = intent.getExtras().getString("Company_phone");
        }


        rgFix.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fix1:
                        trouble_type = "컴퓨터 전원이 들어오지 않음";
                        break;
                    case R.id.fix2:
                        trouble_type = "컴퓨터 성능이 현저히 저하됌";
                        break;
                    case R.id.fix3:
                        trouble_type = "인터넷이 안됌";
                        break;
                    case R.id.fix4:
                        trouble_type = "알 수 없는 화면이 뜸";
                        break;
                    case R.id.fix5:
                        trouble_type = "기타";
                        break;

                }
            }
        });
        rgbrand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.barnd1:
                        brand = "삼성";
                        break;
                    case R.id.barnd2:
                        brand = "LG";
                        break;
                    case R.id.barnd3:
                        brand = "TG";
                        break;
                    case R.id.barnd4:
                        brand = "HP";
                        break;
                    case R.id.barnd5:
                        brand = "조립식PC";
                        break;
                    case R.id.barnd6:
                        brand = "기타";
                        break;
                }
            }
        });
        rgTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.time1:
                        available_time = "평일오전";
                        break;
                    case R.id.time2:
                        available_time = "평일오후";
                        break;
                    case R.id.time3:
                        available_time = "평일저녁";
                        break;
                    case R.id.time4:
                        available_time = "주말오전";
                        break;
                    case R.id.time5:
                        available_time = "주말오후";
                        break;
                    case R.id.time6:
                        available_time = "주말저녁";
                        break;
                }
            }
        });
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.type1:
                        computer_type = "데스크탑";
                        break;
                    case R.id.type2:
                        computer_type = "노트북";
                        break;

                }
            }
        });
        rgbuytime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buy1:
                        used_year = "1년이하";
                        break;
                    case R.id.buy2:
                        used_year = "1년~2년";
                        break;
                    case R.id.buy3:
                        used_year = "2년~3년";
                        break;
                    case R.id.buy4:
                        used_year = "3년이상";
                        break;

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.request_crossImgView:
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;

            case R.id.request_tv_location:
                dialog = createInflaterDialog();
                dialog.show();
                break;

            case R.id.request_btnSubmit:
                if (ComdocApplication.company_flag == 1) {
                    ComdocApplication.company_flag = 0;
                    Log.d("compay_request", "회사선택제");

                    requester_phone = etPhon.getEditText().getText().toString();
                    location = tvLocation.getText().toString();
                    address = etsfRegion.getEditText().getText().toString();
                    trouble_detail = etDtailfix.getEditText().getText().toString();

                    Toast.makeText(getApplicationContext(), "제출 완료", Toast.LENGTH_SHORT).show();


                    RequestParams param = new RequestParams();

                    param.put("requester",ComdocApplication.user.getId());
                    param.put("location", location);
                    param.put("address", address);
                    param.put("requester_phone", requester_phone);
                    param.put("computer_type", computer_type);
                    param.put("brand", brand);
                    param.put("used_year", used_year);
                    param.put("trouble_type", trouble_type);
                    param.put("trouble_detail", trouble_detail);
                    param.put("available_time", available_time);
                    param.put("include", include);
                    Log.d("parm", ComdocApplication.user.getId());
                    Log.d("parm", location);
                    Log.d("parm", address);
                    Log.d("parm", requester_phone);
                    Log.d("parm", computer_type);
                    Log.d("parm", brand);
                    Log.d("parm", used_year);
                    Log.d("parm", trouble_type);
                    Log.d("parm", trouble_detail);
                    Log.d("parm", available_time);


                    try {
                        StringEntity entity = new StringEntity(param.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    SheetRequest.post("http://40.74.139.156:1337/insert/sheet", param);
                    //문자보내기
                    final RequestParams param2 = new RequestParams();
                    Log.d("company_push", "업체포함 문자보내기");

                    param2.put("to", company_phone);
                    param2.put("from", "010-4561-8243");
                    param2.put("text", "고객이 업체포함 요청서를 보냈습니다");

                    client.get("http://40.74.139.156:1337/push_notification",param2, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                } else {
                    requester_phone = etPhon.getEditText().getText().toString();
                    location = tvLocation.getText().toString();
                    address = etsfRegion.getEditText().getText().toString();
                    trouble_detail = etDtailfix.getEditText().getText().toString();

                    Toast.makeText(getApplicationContext(), "제출 완료", Toast.LENGTH_SHORT).show();

                    RequestParams param = new RequestParams();

                    param.put("requester", ComdocApplication.user.getId());
                    param.put("location", location);
                    param.put("address", address);
                    param.put("requester_phone", requester_phone);
                    param.put("computer_type", computer_type);
                    param.put("brand", brand);
                    param.put("used_year", used_year);
                    param.put("trouble_type", trouble_type);
                    param.put("trouble_detail", trouble_detail);
                    param.put("available_time", available_time);
                    Log.d("parm",  ComdocApplication.user.getId());
                    Log.d("parm", location);
                    Log.d("parm", address);
                    Log.d("parm", requester_phone);
                    Log.d("parm", computer_type);
                    Log.d("parm", brand);
                    Log.d("parm", used_year);
                    Log.d("parm", trouble_type);
                    Log.d("parm", trouble_detail);
                    Log.d("parm", available_time);

                    try {
                        StringEntity entity = new StringEntity(param.toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    SheetRequest.post("http://40.74.139.156:1337/insert/sheet", param);
                }
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;


        }
    }

    private AlertDialog createInflaterDialog() {
        final CharSequence[] items = {"서울시 강남구", "서울시 강동구", "서울시 강북구", "서울시 강서구", "서울시 관악구", "서울시 광진구"
                , "서울시 금천구", "서울시 구로구", "서울시 노원구", "서울시 도봉구", "서울시 동대문구", "서울시 동작구", "서울시 마포구", "서울시 서대문구", "서울시 서초구", "서울시 성동구", "서울시 성북구"
                , "서울시 송파구", "서울시 양천구", "서울시 영등포", "서울시 용산구", "서울시 은평구", "서울시 종로구", "서울시 중구", "서울시 중랑구"};
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("지역을 선택해 주세요");
        ab.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {


                switch (item) {
                    case 0:
                        tvLocation.setText(items[0].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 1:
                        tvLocation.setText(items[1].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 2:
                        tvLocation.setText(items[2].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 3:
                        tvLocation.setText(items[3].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 4:
                        tvLocation.setText(items[4].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 5:
                        tvLocation.setText(items[5].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 6:
                        tvLocation.setText(items[6].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 7:
                        tvLocation.setText(items[7].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 8:
                        tvLocation.setText(items[8].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 9:
                        tvLocation.setText(items[9].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 10:
                        tvLocation.setText(items[10].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 11:
                        tvLocation.setText(items[11].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 12:
                        tvLocation.setText(items[12].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 13:
                        tvLocation.setText(items[13].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 14:
                        tvLocation.setText(items[14].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 15:
                        tvLocation.setText(items[15].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 16:
                        tvLocation.setText(items[16].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 17:
                        tvLocation.setText(items[17].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 18:
                        tvLocation.setText(items[18].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 19:
                        tvLocation.setText(items[19].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 20:
                        tvLocation.setText(items[20].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 21:
                        tvLocation.setText(items[21].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 22:
                        tvLocation.setText(items[22].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 23:
                        tvLocation.setText(items[23].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 24:
                        tvLocation.setText(items[24].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;
                    case 25:
                        tvLocation.setText(items[25].toString());
                        tvLocation.setTextColor(Color.WHITE);
                        break;

                }
                dialog.dismiss();
            }
        });
        dialog = ab.create();
        dialog.show();
        return dialog;

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isUserCanceled) {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            finish();
        }
    }
}
