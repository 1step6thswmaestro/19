package com.soma6th.comdoc_android;

import android.app.Application;

import com.soma6th.comdoc_android.model.Company;
import com.soma6th.comdoc_android.model.ExamCategory;
import com.soma6th.comdoc_android.model.Sheet;
import com.soma6th.comdoc_android.model.User;

import java.util.List;

/**
 * Created by Kahye on 2015. 10. 30..
 */
public class ComdocApplication extends Application {

    public static List<Sheet> sheetDatas;
    public static List<Company> companyDatas;
    public static User user;
    public static int matching_status;
    public static int position=-1;
    public static int resumeFlag=0;
    public static List<ExamCategory> examCategories;
    public static int to;
    public static int from;
    public static int company_flag;
    public static String[] date = new String[2];
    public static String start_date;
    public static String final_date;

    @Override
    public void onCreate() {
        super.onCreate();
        matching_status = 0;
    }
}
