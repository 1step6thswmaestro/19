package com.soma6th.comdoc_android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.helper.MakeBlurHelper;

/**
 * @author Audacity IT Solutions Ltd.
 * @class LandingActivity
 * @brief Activity for showing Sign up, Sign In and See first option
 */
public class LandingActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land);

        ImageView bgImageView = (ImageView) findViewById(R.id.landingActivity_background);
        Bitmap blurImage = MakeBlurHelper.makeBlur(getApplicationContext(), getBitmapFromDrawable(), 20);
        bgImageView.setImageBitmap(blurImage);

        findViewById(R.id.btnSignUp).setOnClickListener(this);
        findViewById(R.id.btnSeeFirst).setOnClickListener(this);
        findViewById(R.id.btnHaveAccountTV).setOnClickListener(this);
        SignInActivity.setListener(this);
    }
    private Bitmap getBitmapFromDrawable() {
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.app_wallpaper3);
        if (drawable != null) {
            Bitmap bitmap = drawable.getBitmap();
            return bitmap;
        }
        return null;
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

}
