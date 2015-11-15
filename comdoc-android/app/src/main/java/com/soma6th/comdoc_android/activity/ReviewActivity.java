package com.soma6th.comdoc_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.soma6th.comdoc_android.R;

public class ReviewActivity extends AppCompatActivity {
    int sheet_id;
    RatingBar mRating;
    TextView mRateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        sheet_id = intent.getExtras().getInt("sheet_id");
        init();


    }

    private void init() {
        mRating = (RatingBar) findViewById(R.id.review_ratingbar);
        mRateText = (TextView) findViewById(R.id.review_ratetext);

        mRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                mRateText.setText("Now Rate : " + rating);
            }
        });
    }

}
