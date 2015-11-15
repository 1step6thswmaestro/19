package com.soma6th.comdoc_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.model.User;
import com.soma6th.comdoc_android.util.FloatLabel;
import com.soma6th.comdoc_android.util.UtilMethods;
import com.soma6th.comdoc_android.util.UtilMethods.InternetConnectionListener;

import static com.soma6th.comdoc_android.util.Constants.JF_CONTACT_NUMBER;
import static com.soma6th.comdoc_android.util.Constants.JF_EMAIL;
import static com.soma6th.comdoc_android.util.Constants.JF_ID;
import static com.soma6th.comdoc_android.util.Constants.JF_NAME;
import static com.soma6th.comdoc_android.util.UtilMethods.getPreferenceString;
import static com.soma6th.comdoc_android.util.UtilMethods.hideSoftKeyboard;
import static com.soma6th.comdoc_android.util.UtilMethods.isConnectedToInternet;
import static com.soma6th.comdoc_android.util.UtilMethods.isUserSignedIn;
import static com.soma6th.comdoc_android.util.UtilMethods.savePreference;
import static com.soma6th.comdoc_android.util.Validator.isInputted;
import static com.soma6th.comdoc_android.util.Validator.isMobileNumberValid;
import static com.soma6th.comdoc_android.util.Validator.isValidEmail;
import static com.soma6th.comdoc_android.util.Validator.setPhoneCodeListener;

/**
 * @author Audacity IT Solutions Ltd.
 * @class ProfileUpdateActivity
 * @brief Activity of updating user information
 */
public class ProfileUpdateActivity extends Activity implements View.OnClickListener, InternetConnectionListener {

    private final int PROFILE_UPDATE_ACTION = 1;
    private FloatLabel etMobileNumber;
    private FloatLabel etFullName;
    private FloatLabel etEmail;
    private boolean isUserCanceled = false;
    private InternetConnectionListener internetConnectionListener;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        etMobileNumber = (FloatLabel) findViewById(R.id.etEmail);
        etFullName = (FloatLabel) findViewById(R.id.etFullName);
        etEmail = (FloatLabel) findViewById(R.id.etEmail);
        findViewById(R.id.crossImgView).setOnClickListener(this);
        findViewById(R.id.btnUpdate).setOnClickListener(this);
        etMobileNumber.getEditText().setOnFocusChangeListener(setPhoneCodeListener(this));

        if (isUserSignedIn(this)) {
            getUserInfo();
            etFullName.getEditText().setText(user.getName());
            etMobileNumber.getEditText().setText(user.getPhoneNumber());
            etMobileNumber.getEditText().setClickable(false);
            etMobileNumber.getEditText().setFocusable(false);
            etMobileNumber.getEditText().setFocusableInTouchMode(false);
            etMobileNumber.getEditText().setCursorVisible(false);
            etEmail.getEditText().setText(user.getEmail());
        }
    }

    private void getUserInfo() {
        user.setId(getPreferenceString(this, JF_ID));
        user.setPhoneNumber(getPreferenceString(this, JF_CONTACT_NUMBER));
        user.setName(getPreferenceString(this, JF_NAME));
        user.setEmail(getPreferenceString(this, JF_EMAIL));

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
            case R.id.crossImgView:
                hideSoftKeyboard(this);
                isUserCanceled = true;
                onPause();
                break;

            case R.id.btnUpdate:
                if (inputValid()) {
                    if (isConnectedToInternet(ProfileUpdateActivity.this)) {
                        updateProfile(etFullName.getEditText().getText().toString(),
                                etMobileNumber.getEditText().getText().toString(),
                                etEmail.getEditText().getText().toString());
                    } else {
                        internetConnectionListener = ProfileUpdateActivity.this;
                        UtilMethods.showNoInternetDialog(ProfileUpdateActivity.this, internetConnectionListener, getResources().getString(R.string.no_internet),
                                getResources().getString(R.string.no_internet_text),
                                getResources().getString(R.string.retry_string),
                                getResources().getString(R.string.cancel_string), PROFILE_UPDATE_ACTION);
                    }

                }
                break;
        }
    }

    private void updateProfile(String name, String mobileNumber, String email) {
        user.setPhoneNumber(mobileNumber);
        user.setName(name);
        user.setEmail(email);
        savePreference(ProfileUpdateActivity.this, JF_ID, user.getId());
        savePreference(ProfileUpdateActivity.this, JF_CONTACT_NUMBER, user.getPhoneNumber());
        savePreference(ProfileUpdateActivity.this, JF_NAME, user.getName());
        savePreference(ProfileUpdateActivity.this, JF_EMAIL, user.getEmail());
        Toast.makeText(ProfileUpdateActivity.this, getResources().getString(R.string.profile_update_success), Toast.LENGTH_SHORT).show();
        isUserCanceled = true;
        onPause();

    }

    private boolean inputValid() {

        if (!isInputted(this, etFullName)) {
            return false;
        }

        if (!isInputted(this, etMobileNumber)) {
            return false;
        }

        if (!isMobileNumberValid(this, etMobileNumber)) {
            return false;
        }

        if (!isInputted(this, etEmail)) {
            return false;
        }

        if (!isValidEmail(this, etEmail)) {
            return false;
        }

        return true;
    }


    @Override
    public void onConnectionEstablished(int code) {
        if (code == PROFILE_UPDATE_ACTION) {
            updateProfile(etFullName.getEditText().getText().toString(),
                    etMobileNumber.getEditText().getText().toString(),
                    etEmail.getEditText().getText().toString());
        }
    }

    @Override
    public void onUserCanceled(int code) {

    }
}
