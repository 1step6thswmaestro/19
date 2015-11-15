package com.soma6th.comdoc_android.activity;

import static com.soma6th.comdoc_android.util.Constants.JF_CONTACT_NUMBER;
import static com.soma6th.comdoc_android.util.Constants.JF_EMAIL;
import static com.soma6th.comdoc_android.util.Constants.JF_ID;
import static com.soma6th.comdoc_android.util.Constants.JF_NAME;
import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;
import static com.soma6th.comdoc_android.util.UtilMethods.isConnectedToInternet;
import static com.soma6th.comdoc_android.util.UtilMethods.savePreference;
import static com.soma6th.comdoc_android.util.Validator.isInputted;
import static com.soma6th.comdoc_android.util.Validator.isMobileNumberValid;
import static com.soma6th.comdoc_android.util.Validator.isPasswordValid;
import static com.soma6th.comdoc_android.util.Validator.setPhoneCodeListener;

import com.loopj.android.http.*;

import java.io.IOException;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.model.User;
import com.soma6th.comdoc_android.util.FloatLabel;
import com.soma6th.comdoc_android.util.UtilMethods;
import com.loopj.android.http.AsyncHttpClient;

import cz.msebera.android.httpclient.Header;

/**
 * @author Audacity IT Solutions Ltd.
 * @class SignInActivity
 * @brief Responsible for making user logged in
 */

public class SignInActivity extends Activity implements View.OnClickListener, View.OnTouchListener, UtilMethods.InternetConnectionListener {

    private final static String TAG = SignInActivity.class.getSimpleName();

    private static SignInCompleteListener signInCompleteListener;
    private final int SIGNED_IN_ACTION = 1;
    private FloatLabel etEmail;
    private FloatLabel etPassword;
    private boolean isUserCanceled = false;
    private UtilMethods.InternetConnectionListener internetConnectionListener;
    private AsyncHttpClient mHttpClient;
    private String id;
    private String phone;
    private String location;
    private User user;
    private String userId;


    public static void setListener(Context context) {
        // signInCompleteListener = (SignInCompleteListener) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mHttpClient = new AsyncHttpClient();

        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.crossImgView).setOnClickListener(this);
        findViewById(R.id.btnNewUserTV).setOnClickListener(this);
        findViewById(R.id.showPasswordImg).setOnTouchListener(this);
        findViewById(R.id.btnForgotPasswordTV).setOnClickListener(this);


        etEmail = (FloatLabel) findViewById(R.id.etEmail);
        etPassword = (FloatLabel) findViewById(R.id.etPassword);
        etPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSignIn:
                if (isInputValid()) {
                    if (isConnectedToInternet(SignInActivity.this)) {
                        try {
                            doLoginRequest(etEmail.getEditText().getText().toString(),
                                    etPassword.getEditText().getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {

                        internetConnectionListener = SignInActivity.this;
                        UtilMethods.showNoInternetDialog(SignInActivity.this, internetConnectionListener, getResources().getString(R.string.no_internet),
                                getResources().getString(R.string.no_internet_text),
                                getResources().getString(R.string.retry_string),
                                getResources().getString(R.string.cancel_string), SIGNED_IN_ACTION);
                    }

                }
                break;

            case R.id.btnNewUserTV:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                isUserCanceled = true;
                onPause();
                break;

            case R.id.crossImgView:
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;

            case R.id.btnForgotPasswordTV:
                startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
                break;

        }
    }

    private void doLoginRequest(final String email, String password) throws IOException {

        RequestParams param = new RequestParams();

        param.put("email", email);
        param.put("password", password);

        mHttpClient.post("http://40.74.139.156:1337/login/user", param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG + "Success_statuscode", Integer.toString(statusCode));
                Log.d(TAG + "Success_response", response.toString());
                if (response.has("token")) {
                    Toast.makeText(getApplication(), "로그인 성공", Toast.LENGTH_LONG).show();
                    try {
                        JSONObject object = response.getJSONObject("user");
                        userId = object.getString("id");
                        location = object.getString("location");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    try {
                        editor.putString("token", response.get("token").toString());
                        //editor.putString("email", email);
                        editor.putString("id", userId);
                        //editor.putString("location",location);
                        //editor.putString("phone",phone);

                        //Toast.makeText(getApplication(),userId, Toast.LENGTH_LONG).show();

                        User user = ComdocApplication.user;
                        user.setEmail(email);
                        user.setId(userId);
                        user.setPhoneNumber(phone);
                        user.setLocation(location);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    editor.commit();
                    //Toast.makeText(getApplication(), "Token Save", Toast.LENGTH_LONG).show();

                    //signInCompleteListener.onSignInComplete();
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);

                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplication(), "비밀번호 또는 아이디가 잘못되었습니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d(TAG + "Success_response", responseString);
                Log.d(TAG + "Fail2_statuscode", Integer.toString(statusCode));
                Toast.makeText(getApplication(), "비밀번호 또는 아이디가 잘못되었습니다", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // Log.d(TAG + "Success_response", errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d(TAG + "Fail_statuscode", Integer.toString(statusCode));
                Toast.makeText(getApplication(), "비밀번호 또는 아이디가 잘못되었습니다", Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!TextUtils.isEmpty(etPassword.getEditText().getText())) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    etPassword.getEditText().setTransformationMethod(null);
                    etPassword.getEditText().setSelection(etPassword.getEditText().getText().length());
                    break;

                case MotionEvent.ACTION_UP:
                    etPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
                    etPassword.getEditText().setSelection(etPassword.getEditText().getText().length());
                    break;
            }
        }

        return false;
    }

    private boolean isInputValid() {

        if (!isInputted(this, etEmail)) {
            return false;
        }

        if (!isMobileNumberValid(this, etEmail)) {
            return false;
        }

        if (!isInputted(this, etPassword)) {
            return false;
        }

        if (!isPasswordValid(this, etPassword)) {
            return false;
        }

        return true;
    }

    @Override
    public void onConnectionEstablished(int code) {
        if (code == SIGNED_IN_ACTION) {
            try {
                doLoginRequest(etEmail.getEditText().getText().toString(),
                        etPassword.getEditText().getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUserCanceled(int code) {

    }

    public interface SignInCompleteListener {
        void onSignInComplete();
    }


}
