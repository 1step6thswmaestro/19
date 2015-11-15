package com.soma6th.comdoc_android.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.activity.FixExamDetailActivity;
import com.soma6th.comdoc_android.model.ExamCategory;

import java.util.List;

/**
 * Created by Kahye on 2015. 10. 31..
 */
public class RVFixExamAdapter extends RecyclerView.Adapter<RVFixExamAdapter.FixExamHolder> {
    public  static String TAG = "RVFixExamAdapter";
    List<ExamCategory> examCategories;
    public int trouble_type;
    public String sheet_id;

    public RVFixExamAdapter(List<ExamCategory> examCategories, int trouble_type) {
        this.examCategories = examCategories;
        this.trouble_type = trouble_type;
        Log.d(TAG,"RVFixExamAdapter");
    }

    public static class FixExamHolder extends RecyclerView.ViewHolder {
        CardView cv;
        static TextView mFixtype;
        static TextView mComputertype;
        static TextView mCompanyname;
        static TextView mLocation;
        static TextView mRating;

        FixExamHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FixExamHolder");
            cv = (CardView) itemView.findViewById(R.id.fix_cv);
            mFixtype = (TextView) itemView.findViewById(R.id.fixtype_fixtype);
            mComputertype = (TextView) itemView.findViewById(R.id.fixtype_computertype);
            mCompanyname = (TextView) itemView.findViewById(R.id.fixtype_company);
            mLocation = (TextView) itemView.findViewById(R.id.fixtype_location);
            mRating = (TextView) itemView.findViewById(R.id.fixtype_rating);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d(TAG, "onAttachedToRecyclerView");
    }

    @Override
    public FixExamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixtype_item, parent, false);
        FixExamHolder pvh = new FixExamHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FixExamHolder holder, final int position){
        Log.d(TAG, "onBindViewHolder");
        holder.mCompanyname.setText(examCategories.get(position).company_name);
        holder.mComputertype.setText(examCategories.get(position).computer_type);
        holder.mFixtype.setText(examCategories.get(position).trouble_type);
        holder.mLocation.setText(examCategories.get(position).company_location);
        holder.mRating.setText(examCategories.get(position).review_point);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FIxItem NUM:", position + "");
                sheet_id = examCategories.get(position).sheet_id;
                Intent intent = new Intent(v.getContext(), FixExamDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("trouble_type", trouble_type);
                intent.putExtra("sheet_id", sheet_id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return examCategories.size();
    }

}
