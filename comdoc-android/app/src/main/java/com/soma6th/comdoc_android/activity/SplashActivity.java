package com.soma6th.comdoc_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.model.User;
import com.soma6th.comdoc_android.util.UtilMethods;

import cz.msebera.android.httpclient.Header;

/**
 * @author Audacity IT Solutions Ltd.
 * @mainpage SplashActivity
 * @class SplashActivity
 * @brief Activity for showing the splash screen
 */

public class SplashActivity extends AppCompatActivity implements Runnable {

    private ScrollView promoScrollView;
    private ImageView promoView;
    Animation translateAnimation;
    RequestParams param = new RequestParams();

    private AsyncHttpClient mHttpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComdocApplication.user = new User();
        setContentView(R.layout.activity_splash);
        promoScrollView = (ScrollView) findViewById(R.id.promoScrollView);
        promoView = (ImageView) findViewById(R.id.promoImageView);
        Point point = UtilMethods.getWindowSize(this);
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(point.x * 2,
                ScrollView.LayoutParams.MATCH_PARENT);
        promoView.setLayoutParams(params);

        promoScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        translateAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, -point.x, TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f);
        translateAnimation.setDuration(8000);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setInterpolator(new LinearInterpolator());
        promoView.startAnimation(translateAnimation);
        promoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        promoScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(this, 1500);

    }

    private void tokenCheck() {


        final SharedPreferences pref;
        String token = null;
        boolean bLoginSucceed = false;

        if (getSharedPreferences("pref", MODE_PRIVATE) != null) {
            pref = getSharedPreferences("pref", MODE_PRIVATE);
            token = pref.getString("token", "");          //Get token when it is saved
            User user = ComdocApplication.user;
            user.setId(pref.getString("id", ""));

            //Toast.makeText(getApplicationContext(),user.getId(),Toast.LENGTH_SHORT).show();

            if (token.length() != 0) {
                bLoginSucceed = true;
                param.put("access_token", token);
                //Toast.makeText(getApplicationContext(), "I have token" ,Toast.LENGTH_SHORT).show();
                mHttpClient.post("http://40.74.139.156:1337/post/jwt", param, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //Toast.makeText(getApplicationContext(), "token save success" ,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //Toast.makeText(getApplicationContext(), "token save fail", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                        finish();
                    }
                });
            }
        }

        if (bLoginSucceed == false) {
            //Toast.makeText(getApplicationContext(), "I have not token.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this, LandingActivity.class));
            finish();
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void run() {
        tokenCheck();
    }
}
