package com.soma6th.comdoc_android.util;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.model.Sheet;
import com.soma6th.comdoc_android.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SheetRequest {
    private static String id;
    private static String getUrl;
    private static String location = "서울시 강남구";
    private static String address = "나머지주소";
    private static String requester_phone = "010-9781-0031";
    private static String computer_type = "노트북";
    private static String brand = "삼성";
    private static String used_year = "1년 이후";
    private static String trouble_type = "인터넷이 안됌";
    private static String trouble_detail = "ㅇㄹㅇㄹㅇㄹㅇㄹㅇㄹ";
    private static String available_time = "평일오전";
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static Sheet[] sheets = null;
    private static Gson gson = new Gson();

    public static void post(final String url, final RequestParams params) {
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("sheet_post", "success");
                User user = ComdocApplication.user;
                if (response.has("id")) {
                    try {
                        id = response.get("id").toString();
                        Log.d("id", id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //TODO id로 /sheet에서 같은 id 가 있는 정보들을 가져와서 Postbuss~activity에 적음
//                getUrl = "http://40.74.139.156:1337/sheet/find?id=" + id;
                getUrl = "http://40.74.139.156:1337/sheets/user/" + user.getId();
                Log.d("getUrl", getUrl);

                client.get(getUrl, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("sheet_get", "success");
                        Log.d("response", response.toString());

                        sheets = gson.fromJson(response.toString(), Sheet[].class);

                        //sheets[0].suggestion_sheets.get(0).comment
                        if(sheets.length>0){// 요청서가 존재함
                            Log.d("SheetRequest", sheets.toString());
                        }
                        //Log.d("SheetRequest", sheets[0].suggestion_sheets.get(0).comment);
//                        Log.d("SheetRequest", sheets[0].suggestion_sheets.get(0).comment);
//                        Log.d("SheetRequest:location", sheets[0].location);

//                        ArrayList<SuggestionSheet> suggestionSheetArrayList = new ArrayList<SuggestionSheet>();


//                        int size = response.length();
//                        if (size > 0) {//애초에 요청을 하지 않았음이 아님
//                            for (int i = 0; i < size; ++i) {//3개
//                                try {
//                                    JSONArray jsonSheetArray = response.getJSONArray(i);//요청서 하나당 딸려오는 모든 거//3
//
//                                    JSONArray jsonSugShteetArray = jsonSheetArray.getJSONArray(0);
//                                    int suggetionSize = jsonSugShteetArray.length();
//                                    if (suggetionSize > 0) {
//                                        for (int j = 0; j < suggetionSize; ++j) {
//                                            JSONObject suggestionObject = jsonSugShteetArray.getJSONObject(j);
//
//                                            SuggestionSheet suggestionSheet = new SuggestionSheet();
//
//                                            suggestionSheet.setRequest_sheet(suggestionObject.getInt("request_sheet"));
//                                            suggestionSheet.setSuggester(suggestionObject.getString("suggester"));
//                                            suggestionSheet.setExpect_price(suggestionObject.getString("expect_price"));
//                                            suggestionSheet.setExpect_period(suggestionObject.getString("expect_period"));
//                                            suggestionSheet.setComment(suggestionObject.getString("comment"));
//                                            suggestionSheet.setStatus(suggestionObject.getInt("status"));
//                                            suggestionSheet.setVisit_time(suggestionObject.getString("visit_time"));
//                                            suggestionSheet.setEngineer(suggestionObject.getString("engineer"));
//                                            suggestionSheet.setId(suggestionObject.getInt("id"));
//                                            suggestionSheet.setCreatedAt(suggestionObject.getString("createdAt"));
//                                            suggestionSheet.setUpdatedAt(suggestionObject.getString("updatedAt"));
//
//                                            suggestionSheetArrayList.add(j, suggestionSheet);
//                                        }
//                                    }
//                                    JSONArray requesterArray = jsonSheetArray.getJSONArray(1);
//                                    int requesterSize = jsonSugShteetArray.length();
//                                    if (suggetionSize > 0) {
//                                        for (int j = 0; j < suggetionSize; ++j) {
//                                            JSONObject suggestionObject = jsonSugShteetArray.getJSONObject(j);
//
//                                            SuggestionSheet suggestionSheet = new SuggestionSheet();
//
//                                            suggestionSheet.setRequest_sheet(suggestionObject.getInt("request_sheet"));
//                                            suggestionSheet.setSuggester(suggestionObject.getString("suggester"));
//                                            suggestionSheet.setExpect_price(suggestionObject.getString("expect_price"));
//                                            suggestionSheet.setExpect_period(suggestionObject.getString("expect_period"));
//                                            suggestionSheet.setComment(suggestionObject.getString("comment"));
//                                            suggestionSheet.setStatus(suggestionObject.getInt("status"));
//                                            suggestionSheet.setVisit_time(suggestionObject.getString("visit_time"));
//                                            suggestionSheet.setEngineer(suggestionObject.getString("engineer"));
//                                            suggestionSheet.setId(suggestionObject.getInt("id"));
//                                            suggestionSheet.setCreatedAt(suggestionObject.getString("createdAt"));
//                                            suggestionSheet.setUpdatedAt(suggestionObject.getString("updatedAt"));
//
//                                            suggestionSheetArrayList.add(j, suggestionSheet);
//                                        }
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
////                                try {
////                                    JSONArray jsonArray = response.getJSONArray(i);//요청서 하나당 딸려오는 모든 거
////                                    int suggestionArraySize = jsonArray.length();//회사측 제안서 파싱 후 arraylist에 저장
////
////                                    if (suggestionArraySize > 0) {//요청서 하나 이상이 있음
////                                        for (int j = 0; j < suggestionArraySize; i++) {
////                                            JSONObject suggestionObject = jsonArray.getJSONObject(j);
////
////                                            SuggestionSheet suggestionSheet = new SuggestionSheet();
////
////                                            suggestionSheet.setRequest_sheet(suggestionObject.getInt("request_sheet"));
////                                            suggestionSheet.setSuggester(suggestionObject.getString("suggester"));
////                                            suggestionSheet.setExpect_price(suggestionObject.getString("expect_price"));
////                                            suggestionSheet.setExpect_period(suggestionObject.getString("expect_period"));
////                                            suggestionSheet.setComment(suggestionObject.getString("comment"));
////                                            suggestionSheet.setStatus(suggestionObject.getInt("status"));
////                                            suggestionSheet.setVisit_time(suggestionObject.getString("visit_time"));
////                                            suggestionSheet.setEngineer(suggestionObject.getString("engineer"));
////                                            suggestionSheet.setId(suggestionObject.getInt("id"));
////                                            suggestionSheet.setCreatedAt(suggestionObject.getString("createdAt"));
////                                            suggestionSheet.setUpdatedAt(suggestionObject.getString("updatedAt"));
////
////                                            suggestionSheetArrayList.add(j, suggestionSheet);
////
////                                        }
////                                    }
////
////
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
//                            }
//                        }
                        //jsonArray 파싱
//                        try {
//                            JSONObject jsonObject= response.getJSONObject(1);
//                            String email = jsonObject.toString();
//                            Requester requester = Requester.getRequester();
//                            requester.setEmail(email);
//                            Log.d("SheetRequest", requester.getEmail());
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("sheet_get", "fail");
                    }
//                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
//                        super.onSuccess(statusCode, headers, response);
//                        Log.d("sheet_get", "success");
//
//                        try {
//                            location = response.get("location").toString();
//                            address = response.get("address").toString();
//                            requester_phone = response.get("requester_phone").toString();
//                            computer_type = response.get("computer_type").toString();
//                            brand = response.get("brand").toString();
//                            used_year = response.get("used_year").toString();
//                            trouble_type = response.get("trouble_type").toString();
//                            trouble_detail = response.get("trouble_detail").toString();
//                            available_time = response.get("available_time").toString();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                        super.onFailure(statusCode, headers, throwable, errorResponse);
//                        Log.d("sheet_get", "fail");
//                    }

                });

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("sheet_post", "fail");
            }
        });
    }

}
