package com.soma6th.comdoc_android.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.util.FloatLabel;
import com.soma6th.comdoc_android.util.UtilMethods;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;
import static com.soma6th.comdoc_android.util.UtilMethods.isConnectedToInternet;
import static com.soma6th.comdoc_android.util.Validator.setPhoneCodeListener;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, UtilMethods.InternetConnectionListener {

    private final int SIGNED_UP_ACTION = 1;
    private static AlertDialog dialog = null;
    private FloatLabel etEmail;
    private FloatLabel etFullName;
    private FloatLabel etPhonNum;
    //private FloatLabel etLocation;
    private TextView tvLocation;
    private FloatLabel etPassword;
    private FloatLabel etRetypePassword;
    private boolean isUserCanceled = false;
    private UtilMethods.InternetConnectionListener internetConnectionListener;
    private AsyncHttpClient mHttpClient;
    private Context context;
    private ArrayAdapter<CharSequence> adspin;

    private String email;
    private String name;
    private String phone;
    private String password;
    private String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Toast.makeText(getApplicationContext(),"hellow", Toast.LENGTH_SHORT).show();
        mHttpClient=new AsyncHttpClient();

        findViewById(R.id.crossImgView).setOnClickListener(this);
        findViewById(R.id.btnHaveAccountTV).setOnClickListener(this);
        findViewById(R.id.showPasswordImg).setOnTouchListener(this);
        findViewById(R.id.showRetypePasswordImg).setOnTouchListener(this);
        findViewById(R.id.btnSignUp).setOnClickListener(this);

        etEmail = (FloatLabel) findViewById(R.id.etEmail);
        etFullName = (FloatLabel) findViewById(R.id.etFullName);
        etFullName.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etPhonNum = (FloatLabel) findViewById(R.id.etPhoneNum);
        etPassword = (FloatLabel) findViewById(R.id.etPassword);
        etRetypePassword = (FloatLabel) findViewById(R.id.etRetypePassword);
        etPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        etRetypePassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
//        etLocation = (FloatLabel) findViewById(R.id.etLocation);
//        etLocation.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvLocation.setOnClickListener(this);
        etEmail.getEditText().setOnFocusChangeListener(setPhoneCodeListener(this));
        etEmail.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (etEmail.getEditText().getText().length() <=
                            getResources().getText(R.string.mobile_country_code).length()) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isUserCanceled) {
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            finish();
        }
    }
//    private AlertDialog createDialog() {
//        AlertDialog.Builder ab = new AlertDialog.Builder(this);
//        ab.setTitle("Title");
//        ab.setMessage("내용");
//        ab.setCancelable(false);
//
//        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//
//            }
//        });
//
//        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//
//            }
//        });
//
//        return ab.create();
//    }

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnHaveAccountTV:
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                isUserCanceled = true;
                onPause();
                break;

            case R.id.crossImgView:

                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;
            case R.id.tvLocation:
                dialog = createInflaterDialog();
                dialog.show();
                break;

            case R.id.btnSignUp:
                if (isInputValid()) {
                    if (isConnectedToInternet(SignUpActivity.this)) {
                        //TODO: network call
                        email = etEmail.getEditText().getText().toString();
                        name = etFullName.getEditText().getText().toString();
                        phone = etPhonNum.getEditText().getText().toString();
                        password = etPassword.getEditText().getText().toString();
                        location = tvLocation.getText().toString();
                        //Toast.makeText(getApplicationContext(),phone+" "+password,Toast.LENGTH_SHORT).show();
                        initRequest(email, name, phone, password, location);
                    } else {
                        internetConnectionListener = SignUpActivity.this;
                        UtilMethods.showNoInternetDialog(SignUpActivity.this, internetConnectionListener, getResources().getString(R.string.no_internet),
                                getResources().getString(R.string.no_internet_text),
                                getResources().getString(R.string.retry_string),
                                getResources().getString(R.string.cancel_string), SIGNED_UP_ACTION);
                    }
                }
                break;
        }
    }

    private void initRequest(String email, String name, String phone, String password,String location) {
        RequestParams param = new RequestParams();
        Log.d("initRequest","start");
        param.put("email", email);
        param.put("username", name);
        param.put("phone_number", phone);
        param.put("location", location);
        param.put("password", password);
        try {
            StringEntity entity=new StringEntity(param.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mHttpClient.post("http://40.74.139.156:1337/register/user", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("initRequest","success");
                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show();
                Intent landing = new Intent(SignUpActivity.this, LandingActivity.class);
                startActivity(landing);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "회원가입 실패.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isInputValid() {

//        if (!isInputted(this, etEmail)) {
//            return false;
//        }
//
//        if (!isValidEmail(this, etEmail)) {
//            return false;
//        }
//
//        if (!isInputted(this, etFullName)) {
//            return false;
//        }
//
//        if (!isInputted(this, etPhonNum)) {
//            return false;
//        }
//
//        if (!isMobileNumberValid(this, etPhonNum)) {
//            return false;
//        }
//
//        if (!isInputted(this, etPassword)) {
//            return false;
//        }
//
//        if (!isPasswordValid(this, etPassword)) {
//            return false;
//        }
//
//        if (!isPasswordMatched(this, etPassword, etRetypePassword)) {
//            return false;
//        }

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.showPasswordImg) {
            if (!TextUtils.isEmpty(etPassword.getEditText().getText())) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    etPassword.getEditText().setTransformationMethod(null);
                    etPassword.getEditText().setSelection(etPassword.getEditText().getText().length());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    etPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
                    etPassword.getEditText().setSelection(etPassword.getEditText().getText().length());
                }
            }

        } else {
            if (!TextUtils.isEmpty(etRetypePassword.getEditText().getText())) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    etRetypePassword.getEditText().setTransformationMethod(null);
                    etRetypePassword.getEditText().setSelection(etRetypePassword.getEditText().getText().length());
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    etRetypePassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
                    etRetypePassword.getEditText().setSelection(etRetypePassword.getEditText().getText().length());
                }
            }
        }
        return false;
    }

    @Override
    public void onConnectionEstablished(int code) {
        if (code == SIGNED_UP_ACTION) {
        }
    }

    @Override
    public void onUserCanceled(int code) {

    }

}

