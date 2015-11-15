package com.soma6th.comdoc_android.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.model.Category;
import com.soma6th.comdoc_android.model.ExamCategory;
import com.soma6th.comdoc_android.model.User;
import com.soma6th.comdoc_android.util.ApiHandler;
import com.soma6th.comdoc_android.util.UtilMethods.InternetConnectionListener;

import org.json.JSONArray;

import java.util.ArrayList;

///**
// * @author Audacity IT Solutions Ltd.
// * @class HomeFragment
// * @brief Fragment for showing the sub-category list
// */
//
//public class SubCategoryFragment extends Fragment implements InternetConnectionListener, ApiHandler.ApiHandlerListener {
//
//
//}
