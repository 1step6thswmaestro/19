package com.soma6th.comdoc_android.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.model.Company;
import com.soma6th.comdoc_android.model.SuggestionSheet;
import com.soma6th.comdoc_android.util.UtilMethods;

import java.util.List;

import static com.soma6th.comdoc_android.util.UtilMethods.showSelectSuggestDialog;

/**
 * Created by Kahye on 2015. 10. 28..
 */
public class RVSuggetAdapter extends RecyclerView.Adapter<RVSuggetAdapter.SuggetserHolder> {

    public static String TAG = "RVSuggetAdapter";
    public static Gson gson = new Gson();
    private String getUrl;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private Activity activity;


    public Company[] companies = null;

    List<SuggestionSheet> suggestionSheets;
    int sheet_id;
    int suggest_id;
    int matching_status;
    String suggester_id;
    TextView company_location;
    TextView company_address;

    TextView company_name;
    TextView company_rating;
    TextView company_descrip;

    public class SuggetserHolder extends RecyclerView.ViewHolder {
        CardView cv;
        CardView cvCompanyInfo;

        TextView expect_price;
        TextView comment;
        TextView status;
        TextView visit_time;
        TextView enginner;
        TextView expect_period;
        TextView suggestertv;
        TextView company_infobtn;
        TextView engineer_phone;


        SuggetserHolder(View itemView) {
            super(itemView);
            Log.d(TAG, " SuggetserHolder");

            cv = (CardView) itemView.findViewById(R.id.sheet_detail_cv);
            cvCompanyInfo = (CardView) itemView.findViewById(R.id.cvCompanyInfo);
            this.company_infobtn = (TextView) itemView.findViewById(R.id.Info_btnCompanyInfo);
            expect_price = (TextView) itemView.findViewById(R.id.suggest_expect_price);
            comment = (TextView) itemView.findViewById(R.id.suggest_comment);
            status = (TextView) itemView.findViewById(R.id.suggest_status);
            visit_time = (TextView) itemView.findViewById(R.id.suggest_visit_time);
            enginner = (TextView) itemView.findViewById(R.id.suggest_enginner);
            expect_period = (TextView) itemView.findViewById(R.id.suggest_expect_period);
            suggestertv = (TextView) itemView.findViewById(R.id.suggest_suggester);
            engineer_phone = (TextView) itemView.findViewById(R.id.suggest_enginner_phone);

            // String suggester;
        }

    }


    public RVSuggetAdapter(Activity activity, List<SuggestionSheet> sheetDatas, int sheet_id, int matching_status) {
        Log.d(TAG, " RVSuggetAdapter");
        this.activity = activity;
        this.suggestionSheets = sheetDatas;
        this.sheet_id = sheet_id;
        //this.suggester_id = suggester_id;
        this.matching_status = matching_status;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d(TAG, " onAttachedToRecyclerView");
    }

    @Override
    public SuggetserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d(TAG, " onCreateViewHolder");
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.suggestions_shteet_item, viewGroup, false);
        SuggetserHolder pvh = new SuggetserHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final SuggetserHolder suggetserHolder, final int i) {
        Log.d(TAG, " onBindViewHolder : " + i);
        //suggetserHolder.suggester.setText("회사 : "+suggestionSheets.get(i).suggester);
        try {
            suggest_id = suggestionSheets.get(i).id;
            Log.d("suggestion positon"," : "+i);
            Log.d("suggestion id"," : "+suggest_id);
            suggetserHolder.expect_price.setText(suggestionSheets.get(i).expect_price + "");
        } catch (NullPointerException e) {
            suggetserHolder.expect_price.setText("미정");
        }
        // suggester = suggestionSheets.get(i).suggester;

        String getUrl = "http://40.74.139.156:1337/suggestion_sheets/company/" + suggester_id;
        Log.d("getUrl", getUrl);

       // Log.d("suggester_id", suggester_id);

        suggetserHolder.comment.setText(suggestionSheets.get(i).comment);
        suggetserHolder.enginner.setText(suggestionSheets.get(i).engineer);
        suggetserHolder.visit_time.setText(suggestionSheets.get(i).visit_time + "");
        suggetserHolder.suggestertv.setText(suggestionSheets.get(i).suggester);
        suggetserHolder.engineer_phone.setText(suggestionSheets.get(i).engineer_phone);
         Log.d("engineer_phone", suggestionSheets.get(i).engineer_phone);
        suggetserHolder.company_infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("postiong_id", i + "");
                UtilMethods.showCompanyInfoDialog(v.getContext(), "회사정보", "확인", suggetserHolder.suggestertv.getText().toString(), companies);
            }
        });


        if (suggestionSheets.get(i).status == 0)

        {
            suggetserHolder.status.setText("조회되지 않음");
        } else if (suggestionSheets.get(i).status == 1)

        {
            suggetserHolder.status.setText("선택하지 않은 견적서");
        } else if (suggestionSheets.get(i).status == 2)

        {
            suggetserHolder.status.setText("선택완료");
        }

        suggetserHolder.expect_period.setText(suggestionSheets.get(i).expect_period + "");

        suggetserHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Log.d("CardviewItem NUM:", i + "");
                                                            Log.d("sheet_id:", sheet_id + "," + suggest_id);
                                                            Log.d("suggestion id"," : "+suggestionSheets.get(i).id);
                                                            showSelectSuggestDialog(v.getContext(), "제안서 선택", "해당 회사의 수리를 선택하시겠습니까", "NO", "YES", sheet_id, suggestionSheets.get(i).id, matching_status,suggestionSheets.get(i).engineer_phone);

                                                        }
                                                    }

        );
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (suggestionSheets.size() > 0) {
            size = suggestionSheets.size();
        }
        return size;
    }

}
