package com.soma6th.comdoc_android.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.activity.HomeActivity;
import com.soma6th.comdoc_android.activity.LandingActivity;
import com.soma6th.comdoc_android.activity.SheetInfoDetailActivity;
import com.soma6th.comdoc_android.model.Company;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

import static com.soma6th.comdoc_android.util.Constants.FINDER_HOTLINE;
import static com.soma6th.comdoc_android.util.Constants.JF_CONTACT_NUMBER;
import static com.soma6th.comdoc_android.util.Constants.JF_EMAIL;
import static com.soma6th.comdoc_android.util.Constants.JF_ID;
import static com.soma6th.comdoc_android.util.Constants.JF_NAME;

/**
 * @author Audacity IT Solutions Ltd.
 * @class UtilMethods
 * @brief Methods used randomly through out the projects are described here
 */

public class UtilMethods {
    public static int rate;
    //! to activate internet checking set APP_TEST_MODE to false
    private static final boolean APP_TEST_MODE = true;
    //! to activate internet checking set APP_MAP_MODE to false
    public static boolean APP_MAP_MODE = false;
    private static AlertDialog dialog = null;
    private static AsyncHttpClient mHttpClient;
    static Gson gson;

    static int time_flag = 0;

    /**
     * @param context
     * @return true or false mentioning the device is connected or not
     * @brief checking the internet connection on run time
     */
    public static boolean isConnectedToInternet(Context context) {
        Log.d("INTERNET", "internet");
        if (APP_TEST_MODE && !APP_MAP_MODE) {
            return true;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
            }
        }
        return false;
    }

    /**
     * @param context the application context
     * @param key     variable in which the value will be stored to be retrieved later
     * @param value   the value to store
     * @brief save int value with shared preference
     */
    public static void savePreference(Context context, String key, int value) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * @param context the application context
     * @param key     variable from which the value will be retrieved
     * @return int
     * @brief retrieve int value from specific key
     */
    public static int getPreferenceInt(Context context, String key) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * @param context the application context
     * @param key     variable in which the value will be stored to be retrieved later
     * @param value   the value to store
     * @brief save String value with shared preference
     */
    public static void savePreference(Context context, String key, String value) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * @param context the application context
     * @param key     variable from which the value will be retrieved
     * @return Sting
     * @brief retrieve String value from specific key
     */
    public static String getPreferenceString(Context context, String key) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    /**
     * @param context the application context
     * @param url     the specified url to which the browser will be redirected
     * @brief methods for calling browser's intent with specified url
     */
    public static void browseUrl(Context context, String url) {
        if (!url.startsWith("http") && !url.startsWith("https"))
            url = "http://" + url;
        Intent openBrowserIntent = new Intent(Intent.ACTION_VIEW);
        openBrowserIntent.setData(Uri.parse(url));
        context.startActivity(openBrowserIntent);
    }

    /**
     * @param context the application context
     * @param number  the specified phone number
     * @brief methods for doing a phone call with specified phone number
     */
    public static void phoneCall(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    /**
     * @param context the application context
     * @return true or false
     * @brief methods for identifying the device is supported for calling feature or not
     */
    public static boolean isDeviceCallSupported(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            Toast.makeText(context, context.getResources().getString(R.string.no_call_feature),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param context the application context
     * @param address the specified email address
     * @brief methods for sending a mail via mail intent
     */
    public static void mailTo(Context context, String address) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact with Amar Phonebook");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    /**
     * @param context the application context
     * @param name    the subject of the mail to be sent
     * @param address the specified email address
     * @brief methods for sending a mail via mail intent
     */
    public static void mailTo(Context context, String name, String address) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact with " + name);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    /**
     * @param activity the context of the activity
     * @brief methods for showing the soft keyboard by forced
     */
    public static void showSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(activity.getCurrentFocus(), 0);
        }
    }

    /**
     * @param activity the context of the activity
     * @brief methods for hiding the soft keyboard by forced
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * @param context the application context
     * @return true or false
     * @brief methods for checking any user has already signed in or not
     */
    public static boolean isUserSignedIn(Context context) {
        if (!TextUtils.isEmpty(getPreferenceString(context, Constants.JF_CONTACT_NUMBER))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param context                    the application context
     * @param internetConnectionListener listener from which the method is called
     * @param headline                   headline text in String
     * @param body                       body text in String
     * @param positiveString             positive text in String
     * @param negativeString             negative text in String
     * @param code                       check flag for detecting the case when the class has multiple internet checking task
     * @brief methods for showing a custom no internet dialog
     */
    public static void showNoInternetDialog(final Context context, final InternetConnectionListener internetConnectionListener, final String headline, final String body,
                                            final String positiveString, final String negativeString, final int code) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog, null);
        ((TextView) view.findViewById(R.id.headlineTV)).setText(headline);
        ((TextView) view.findViewById(R.id.bodyTV)).setText(body);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (isConnectedToInternet(context)) {
                            internetConnectionListener.onConnectionEstablished(code);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            showNoInternetDialog(context, internetConnectionListener, headline, body, positiveString, negativeString, code);
                        }
                    }
                })
                .setNegativeButton(negativeString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        internetConnectionListener.onUserCanceled(code);
                        dialog.dismiss();
                    }
                })
                .setView(view)
                .setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    /**
     * @param context        the application context
     * @param heading        the headline text in String
     * @param body           the body text in String
     * @param positiveString positive text in String
     * @param negativeString negative text in String
     * @brief methods for showing a custom exit dialog
     */
    public static void showExitDialog(final Context context, final String heading, final String body, final String positiveString, final String negativeString) {
        Log.d("TAG", "showExitDialog");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog, null);
        ((TextView) view.findViewById(R.id.headlineTV)).setText(heading);
        ((TextView) view.findViewById(R.id.bodyTV)).setText(body);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(context);
                        context.startActivity(new Intent(context, LandingActivity.class));
                        ((Activity) context).finish();
                    }
                })
                .setNegativeButton(negativeString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setView(view)
                .setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public static void showSelectSuggestDialog(final Context context, final String heading, final String body, final String positiveString, final String negativeString, final int sheet_id, final int suggest_id, final int matching_status, final String engneer_phone) {

        Log.d("TAG", "showSelectSuggestDialog");
        Log.d("to:from", ComdocApplication.sheetDatas.get(0).requester_phone + ":" + engneer_phone);

        mHttpClient = new AsyncHttpClient();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog, null);

        ((TextView) view.findViewById(R.id.headlineTV)).setText(heading);
        ((TextView) view.findViewById(R.id.bodyTV)).setText(body);
        ComdocApplication.matching_status = 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {//no
                        deleteUser(context);
                        ((Activity) context).finish();
                    }
                })
                .setNegativeButton(negativeString, new DialogInterface.OnClickListener() {//yes
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, SheetInfoDetailActivity.class));

                        Log.d("SheetId", sheet_id + "");
                        Log.d("btn", "Postive");
                        Log.d("status", matching_status + "");
                        Log.d("suggest_id", "http://40.74.139.156:1337/suggestion_sheets/adopted/" + sheet_id + "/" + suggest_id);

                        if (matching_status != 2) {

                            mHttpClient.get("http://40.74.139.156:1337/suggestion_sheets/adopted/" + sheet_id + "/" + suggest_id, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    Log.d("btn", "success");
                                    Log.d("sheet id", sheet_id + "," + suggest_id);
                                    Toast.makeText(context, "신청이 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    //문자날리기
                                    //업체
                                    final RequestParams param1 = new RequestParams();
                                    //ComdocApplication.sheetDatas.get(0).requester_phone
                                    //engneer_phone
                                    Log.d("engneer Phone", engneer_phone);
                                    param1.put("to", engneer_phone);
                                    param1.put("from", "010-4561-8243");
                                    param1.put("text", "제안서가 채택되었습니다. \n-ComDoc");

                                    mHttpClient.get("http://40.74.139.156:1337/push_notification", param1, new AsyncHttpResponseHandler() {

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                            Log.d("push", statusCode + responseBody.toString());

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                        }
                                    });
                                    //고객
                                    final RequestParams param3 = new RequestParams();

                                    param3.put("to", ComdocApplication.sheetDatas.get(0).requester_phone);
                                    param3.put("from", "010-4561-8243");
                                    param3.put("text", "견적서 채택이 완료되었습니다. 해당 수리업체가 고객님의 집에 방문할 예정입니다.\n -ComDoc");

                                    mHttpClient.get("http://40.74.139.156:1337/push_notification", param3, new AsyncHttpResponseHandler() {

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                            //ComdocApplication.sheetDatas.get(0).requester_phone
                                            //engneer_phone
                                            Log.d("regueseter phone", ComdocApplication.sheetDatas.get(0).requester_phone);
                                            Log.d("push", statusCode + responseBody.toString());

                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                        }
                                    });

                                    ((Activity) context).finish();
                                    context.startActivity(new Intent(context, HomeActivity.class));


                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(context, "신청실패", Toast.LENGTH_SHORT).show();
                                    Log.d("btn", "fail");

                                }
                            });
                        } else {
                            Toast.makeText(context, "이미 수리가 완료되었습니다", Toast.LENGTH_SHORT).show();
                        }
                        ((Activity) context).finish();
                    }
                })
                .setView(view)
                .setCancelable(false);


        dialog = builder.create();
        dialog.show();
        //((Activity) context).finish();
    }

    public static void showFinishFixDialog(final Context context, final String heading, final String body, final String positiveString, final String negativeString, final int sheet_id) {
        Log.d("TAG", "showFinishFixDialog");

        ComdocApplication.matching_status = 2;
        mHttpClient = new AsyncHttpClient();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_dialog, null);
        ((TextView) view.findViewById(R.id.headlineTV)).setText(heading);
        ((TextView) view.findViewById(R.id.bodyTV)).setText(body);
        final RequestParams param = new RequestParams();
        param.put("id", sheet_id);
        param.put("final_start_date", "-");
        param.put("final_end_date: ", "-");
        param.put("final_price", 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {//no
                        Toast.makeText(context, "수리완료 취소", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(negativeString, new DialogInterface.OnClickListener() {//yes
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("aaaa", Integer.toString(sheet_id));
                        mHttpClient.put("http://40.74.139.156:1337/sheets/repairCompleted", param, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Toast.makeText(context, "수리가 완료되었습니다", Toast.LENGTH_SHORT).show();
                                ComdocApplication.matching_status = 2;
                                ((Activity) context).finish();
                                context.startActivity(new Intent(context, HomeActivity.class));
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(context, "수리완료 실패" + sheet_id, Toast.LENGTH_SHORT).show();

                            }
                        });
                        ((Activity) context).finish();
                    }
                })
                .setView(view)
                .setCancelable(false);
        //

        dialog = builder.create();
        dialog.show();
        //((Activity) context).finish();
    }

    //시간선택
    public static void showPickerDialog(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final DatePicker datePicker;
        final TimePicker timePicker;


        View view = inflater.inflate(R.layout.layout_timepicker_dialog, null);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (time_flag == 0) {
                            ComdocApplication.start_date = datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth() + " " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
                            time_flag=1;
                            Toast.makeText(context, ComdocApplication.start_date, Toast.LENGTH_SHORT).show();
                        } else {
                            ComdocApplication.final_date = datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth() + " " + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
                            time_flag=0;
                            Toast.makeText(context, ComdocApplication.final_date, Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setView(view)
                .setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public static void showReviewDialog(final Context context, final String heading, final String body, final String positiveString, final String negativeString, final int sheet_id, final String engneer_phone) {

        Log.d("TAG", "showReviewDialog");

        final String TIME_PATTERN = "HH:mm";

        final AsyncHttpClient mHttpClient = new AsyncHttpClient();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_review_dialog, null);
        final EditText reviewEt;
        final EditText priceEt;
        final TextView startTv;
        final TextView finalTv;

        RatingBar mRating;
        reviewEt = (EditText) view.findViewById(R.id.review_dialog_comment_et);
        priceEt = (EditText) view.findViewById(R.id.review_dialog_price_et);
        startTv = (TextView) view.findViewById(R.id.review_dialog_start);
        finalTv = (TextView) view.findViewById(R.id.review_dialog_fianl);
        ((TextView) view.findViewById(R.id.headlineTV)).setText(heading);

        view.findViewById(R.id.review_dialog_start_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerDialog(context);

            }
        });

        view.findViewById(R.id.review_dialog_fianl_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerDialog(context);

            }
        });

        mRating = (RatingBar) view.findViewById(R.id.review_ratingbar);
        Drawable progress = mRating.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.WHITE);
        LayerDrawable stars = (LayerDrawable) mRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255, 166, 51), PorterDuff.Mode.SRC_ATOP);
        startTv.setText(ComdocApplication.start_date);
        finalTv.setText(ComdocApplication.final_date);
        mRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                Log.d("Now Rate : ", rating + "" + "sheetID : " + sheet_id);
                rate = (int) rating;
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteUser(context);

            }
        })
                .setNegativeButton(negativeString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("review", reviewEt.getText().toString());
                        if (reviewEt.getText().length() > 0) {

                            RequestParams param = new RequestParams();
                            RequestParams param2 = new RequestParams();

                            param.put("review_comment", sheet_id);
                            param.put("comment", reviewEt.getText().toString());
                            param.put("point", rate);

                            param2.put("id", sheet_id);
                            param2.put("final_price", Integer.parseInt(priceEt.getText().toString()));
                            param2.put("final_start_date", ComdocApplication.start_date);
                            param2.put("final_end_date",ComdocApplication.final_date);
                            try {
                                StringEntity entity = new StringEntity(param.toString());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            mHttpClient.post("http://40.74.139.156:1337/insert/review", param, new AsyncHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    Toast.makeText(context, "리뷰완료", Toast.LENGTH_SHORT).show();
                                    Log.d("review", reviewEt.getText().toString() + ":" + sheet_id + ":" + rate);
                                    //문자날리기
                                    //업체
                                    final RequestParams param4 = new RequestParams();

                                    param4.put("to", engneer_phone);
                                    param4.put("from", "010-4561-8243");
                                    param4.put("text", "고객님께서 귀사의 출장수리 서비스에 리뷰를 등록하셨습니다. \n-ComDoc");

                                    mHttpClient.get("http://40.74.139.156:1337/push_notification", param4, new AsyncHttpResponseHandler() {

                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            Log.d("push_reveiw", statusCode + responseBody.toString());
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Toast.makeText(context, "리뷰 실패", Toast.LENGTH_SHORT).show();

                                }
                            });
                            mHttpClient.put("http://40.74.139.156:1337/sheets/repairCompleted/", param2, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    Log.d("review&Fix", "review&Fix success");
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    //Toast.makeText(context, "수리완료 실패"+sheet_id, Toast.LENGTH_SHORT).show();
                                    Log.d("review&Fix", "review&Fix fail");
                                }
                            });

                        } else {
                            Toast.makeText(context, "리뷰를 등록해주세요", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setView(view)
                .setCancelable(false);


        dialog = builder.create();
        dialog.show();
    }

    public static void showCompanyInfoDialog(final Context context, final String heading, final String positiveString, final String suggester_id, final Company[] companies) {
        Log.d("TAG", "showCompanyInfoDialog");

        mHttpClient = new AsyncHttpClient();

        final TextView company_location;
        final TextView company_address;
        final TextView company_phone;
        final TextView company_name;
        final TextView company_rating;
        final TextView company_descrip;
        final RatingBar ratingBar;
        gson = new Gson();
        final Company[][] companies2 = {null};

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_companyinfo_dialog, null);
        ((TextView) view.findViewById(R.id.headlineTV)).setText(heading);
        company_location = (TextView) view.findViewById(R.id.suggest1_company_location);
        company_address = (TextView) view.findViewById(R.id.suggest1_company_address);
        company_phone = (TextView) view.findViewById(R.id.suggest1_company_phone);
        company_name = (TextView) view.findViewById(R.id.suggest1_company_adminname);
        company_rating = (TextView) view.findViewById(R.id.suggest1_company_avg_point);
        company_descrip = (TextView) view.findViewById(R.id.suggest1_company_description);
        companies2[0] = companies;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        String getUrl = " http://40.74.139.156:1337/suggestion_sheets/company/" + suggester_id;
        Log.d("getUrl", getUrl);

        mHttpClient.get(getUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    companies2[0] = gson.fromJson(response.toString(), Company[].class);
                    ComdocApplication.companyDatas = Arrays.asList(companies2[0]);

                    company_location.setText(ComdocApplication.companyDatas.get(0).getSuggester().getLocation());
                    company_address.setText(ComdocApplication.companyDatas.get(0).getSuggester().getAddress());
                    company_name.setText(ComdocApplication.companyDatas.get(0).getSuggester().getAdminname());
                    company_phone.setText(ComdocApplication.companyDatas.get(0).getSuggester().getPhone_number());
                    company_rating.setText(ComdocApplication.companyDatas.get(0).getSuggester().getAvg_point() + "");
                    company_descrip.setText(ComdocApplication.companyDatas.get(0).getSuggester().getDescription());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        builder.setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {//no
            }
        });
        builder.setPositiveButton(positiveString, new DialogInterface.OnClickListener() {//yes
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public static void showNoGpsDialog(final Activity context, final String heading, final String body, final String positiveString, final String negativeString) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog, null);
        ((TextView) view.findViewById(R.id.headlineTV)).setText(heading);
        ((TextView) view.findViewById(R.id.bodyTV)).setText(body);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

                    }
                })
                .setNegativeButton(negativeString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setView(view)
                .setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    /**
     * @param context        the application context
     * @param heading        the headline text in String
     * @param body           the body text in String
     * @param positiveString positive text in String
     * @param negativeString positive text in String
     * @brief methods for showing a hotline calling dialog
     */
    public static void showHotLineCallDialog(final Activity context, final String heading, final String body, final String positiveString, final String negativeString) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog, null);
        ((TextView) view.findViewById(R.id.headlineTV)).setText(heading);
        ((TextView) view.findViewById(R.id.bodyTV)).setText(body);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setPositiveButton(positiveString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        phoneCall(context, FINDER_HOTLINE);

                    }
                })
                .setNegativeButton(negativeString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setView(view)
                .setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    /**
     * @param context the application context
     * @return Point containing the width and height
     * @brief methods for getting device window height and width via Point object
     */
    public static Point getWindowSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * @param context
     * @brief methods for delete the existing log in user by putting empty string to the shared
     * preference field
     */
    public static void deleteUser(Context context) {
        savePreference(context, JF_ID, "");
        savePreference(context, JF_CONTACT_NUMBER, "");
        savePreference(context, JF_NAME, "");
        savePreference(context, JF_EMAIL, "");
    }

    /**
     * @param context the application context
     * @param dp      the value in density pixel to be converted into pixel
     * @return pixel in int
     * @brief convert density pixel to standard pixel
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /**
     * @param context the application context
     * @return true or false
     * @brief methods for checking the device's gps is enable or not
     */
    public static boolean isGpsEnable(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * @brief interface used by showNoInternetDialog() methods
     */
    public interface InternetConnectionListener {
        public void onConnectionEstablished(int code);

        public void onUserCanceled(int code);
    }

    /**
     * @param context  the application context
     * @param fileName name of the file from which the text will be loaded
     * @return json text in String
     * @brief methods for loading dummy JSON String from asset folder
     */
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("json/" + fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * @param context   the application context
     * @param imageName the name of the image file
     * @return \c Uri object
     * @brief methods for getting \c Uri of a drawable from file name
     */
    public static String getDrawableFromFileName(Context context, String imageName) {
        return Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" + imageName).toString();
    }


}
