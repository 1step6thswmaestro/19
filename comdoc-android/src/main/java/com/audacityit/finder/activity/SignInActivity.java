package com.audacityit.finder.activity;

import static com.audacityit.finder.util.Constants.JF_CONTACT_NUMBER;
import static com.audacityit.finder.util.Constants.JF_EMAIL;
import static com.audacityit.finder.util.Constants.JF_ID;
import static com.audacityit.finder.util.Constants.JF_NAME;
import static com.audacityit.finder.util.UtilMethods.hideSoftKeyboard;
import static com.audacityit.finder.util.UtilMethods.isConnectedToInternet;
import static com.audacityit.finder.util.UtilMethods.savePreference;
import static com.audacityit.finder.util.Validator.isInputted;
import static com.audacityit.finder.util.Validator.isMobileNumberValid;
import static com.audacityit.finder.util.Validator.isPasswordValid;
import static com.audacityit.finder.util.Validator.setPhoneCodeListener;
import com.loopj.android.http.*;

import java.io.IOException;
import java.net.URI;
        import java.util.*;
        import org.apache.http.*;
        import org.apache.http.client.*;
        import org.apache.http.client.entity.*;
        import org.apache.http.client.methods.*;
        import org.apache.http.client.utils.URIUtils;
        import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
        import org.apache.http.message.*;
        import org.apache.http.protocol.*;
        import org.apache.http.util.*;
        import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.*;
        import android.view.*;
        import android.widget.*;

import com.audacityit.finder.R;
import com.audacityit.finder.model.User;
import com.audacityit.finder.util.FloatLabel;
import com.audacityit.finder.util.UtilMethods;
import com.loopj.android.http.AsyncHttpClient;

import cz.msebera.android.httpclient.*;
import cz.msebera.android.httpclient.Header;

/**
 * @author Audacity IT Solutions Ltd.
 * @class SignInActivity
 * @brief Responsible for making user logged in
 */

public class SignInActivity extends Activity implements View.OnClickListener, View.OnTouchListener, UtilMethods.InternetConnectionListener {

    private static SignInCompleteListener signInCompleteListener;
    private final int SIGNED_IN_ACTION = 1;
    private FloatLabel etMobileNumber;
    private FloatLabel etPassword;
    private boolean isUserCanceled = false;
    private UtilMethods.InternetConnectionListener internetConnectionListener;
    private AsyncHttpClient mHttpClient;

    public static void setListener(Context context) {
        signInCompleteListener = (SignInCompleteListener) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mHttpClient=new AsyncHttpClient();

        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.crossImgView).setOnClickListener(this);
        findViewById(R.id.btnNewUserTV).setOnClickListener(this);
        findViewById(R.id.showPasswordImg).setOnTouchListener(this);
        findViewById(R.id.btnForgotPasswordTV).setOnClickListener(this);


        etMobileNumber = (FloatLabel) findViewById(R.id.etMobileNumber);
        etPassword = (FloatLabel) findViewById(R.id.etPassword);
        etPassword.getEditText().setTransformationMethod(new PasswordTransformationMethod());
        etMobileNumber.getEditText().setOnFocusChangeListener(setPhoneCodeListener(this));
        etMobileNumber.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (etMobileNumber.getEditText().getText().length() <=
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
                            doLoginRequest(etMobileNumber.getEditText().getText().toString(),
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

    private void doLoginRequest(String mobileNumber, String password) throws IOException {
        User user = ComdocApplication.user;
        user.setId("1"); //dummy
        user.setPhoneNumber(mobileNumber);
        user.setName("User"); // dummy value
        user.setEmail("user@email.com");
//        try {
//            url = new URL("http://40.74.139.156:1337");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


//        //GET으로 데이터 전송
//        Properties prop = new Properties();
//        prop.setProperty(mobileNumber, password);
//
//       // String encodedString = encodeString(prop);
//
//
//        String path = "http://40.74.139.156:1337/login?email=\"";
//                path += mobileNumber;
//                path += "\"&password=\"";
//                path += password;
//                path += "\"";
//        url = new URL(path);
//        try {
//            conn = url.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        InputStream is = null;
//        try {
//            is = conn.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        char[] buff = new char[512];
//        int len = -1;
//
//        try {
//            while( (len = br.read(buff)) != -1) {
//                System.out.print(new String(buff, 0, len));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        br.close();
//
//        try {
//            conn = url.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        conn.setUseCaches(false);
//        is = conn.getInputStream();
//        String path = "http://40.74.139.156:1337/login?email=\"";
//        path += mobileNumber;
//        path += "\"&password=\"";
//        path += password;
//        path += "\"";
//        URL url = new URL(path);

        savePreference(SignInActivity.this, JF_ID, user.getId());
        savePreference(SignInActivity.this, JF_CONTACT_NUMBER, user.getPhoneNumber());
        savePreference(SignInActivity.this, JF_NAME, user.getName());
        savePreference(SignInActivity.this, JF_EMAIL, user.getEmail());

        RequestParams param = new RequestParams();

        param.put("email", mobileNumber);
        param.put("password", password);
        StringEntity entity=new StringEntity(param.toString());
        mHttpClient.post("http://40.74.139.156:1337/login", param, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getApplicationContext(), "토스트메시지입니다.",Toast.LENGTH_SHORT).show();
                        signInCompleteListener.onSignInComplete();
                        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //Log.e("POST요청 에러",  "" + e.getMessage());
                        Toast.makeText(getApplicationContext(), "fial.",Toast.LENGTH_SHORT).show();
                    }
                });
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                signInCompleteListener.onSignInComplete();
//                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
//                finish();
//            }
//        });
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

        if (!isInputted(this, etMobileNumber)) {
            return false;
        }

        if (!isMobileNumberValid(this, etMobileNumber)) {
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
                doLoginRequest(etMobileNumber.getEditText().getText().toString(),
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
