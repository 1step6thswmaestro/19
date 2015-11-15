package com.audacityit.finder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.audacityit.finder.R;

/**
 * @author Audacity IT Solutions Ltd.
 * @class LandingActivity
 * @brief Activity for showing Sign up, Sign In and See first option
 */
public class LandingActivity extends AppCompatActivity implements View.OnClickListener,
        VerificationActivity.SignUpCompleteListener, SignInActivity.SignInCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);
        findViewById(R.id.btnSignUp).setOnClickListener(this);
        findViewById(R.id.btnSeeFirst).setOnClickListener(this);
        findViewById(R.id.btnHaveAccountTV).setOnClickListener(this);
        VerificationActivity.setListener(this);
        SignInActivity.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSeeFirst:
                startActivity(new Intent(LandingActivity.this, HomeActivity.class));
                finish();
                break;

            case R.id.btnSignUp:
                startActivity(new Intent(LandingActivity.this, SignUpActivity.class));
                break;

            case R.id.btnHaveAccountTV:
                startActivity(new Intent(LandingActivity.this, SignInActivity.class));
                break;
        }
    }

    @Override
    public void onSignUpComplete() {
        finish();
    }

    @Override
    public void onSignInComplete() {
        finish();
    }
}
