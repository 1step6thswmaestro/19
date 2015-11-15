package com.soma6th.comdoc_android.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soma6th.comdoc_android.ComdocApplication;
import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.activity.SheetInfoActivity;
import com.soma6th.comdoc_android.activity.SheetInfoDetailActivity;
import com.soma6th.comdoc_android.model.Sheet;

import java.util.List;

/**
 * Created by Kahye on 2015. 10. 26..
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ThumnailViewHolder> {
    public static String TAG = "RVAdapter";
    List<Sheet> sheetList;
    public static int cardNum;

    public RVAdapter(List<Sheet> sheetList) {
        Log.d(TAG, " RVAdapter");
        this.sheetList = sheetList;
    }//
    public static class ThumnailViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView mRegion;
        TextView mTrouble_type;
        TextView mComputer_type;

        ThumnailViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, " ThumnailViewHolder");
            cv = (CardView) itemView.findViewById(R.id.sheet_thum_cv);
            mRegion = (TextView) itemView.findViewById(R.id.item_region);
            mTrouble_type = (TextView) itemView.findViewById(R.id.item_trouble_type);
            mComputer_type = (TextView) itemView.findViewById(R.id.item_computer_type);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d(TAG, " onAttachedToRecyclerView");
    }
    @Override
    public ThumnailViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Log.d(TAG, " onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sheetthumnail_item, parent, false);
        ThumnailViewHolder pvh = new ThumnailViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ThumnailViewHolder thumnailViewHolder, final int i) {
        Log.d(TAG, " onBindViewHolder");
        thumnailViewHolder.mRegion.setText(sheetList.get(i).location);
        thumnailViewHolder.mComputer_type.setText(sheetList.get(i).computer_type);
        thumnailViewHolder.mTrouble_type.setText(sheetList.get(i).trouble_type);

        thumnailViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CardviewItem NUM:", i+"");
                Log.d("matiching:", ComdocApplication.matching_status+"");
                cardNum = i;
                ComdocApplication.matching_status=0;
                final int suggestionSize = SheetInfoActivity.SuggetstionSize[i];
                Intent intent = new Intent(v.getContext(), SheetInfoDetailActivity.class);
                intent.putExtra("position",i);
                intent.putExtra("suggestionSize",suggestionSize);


                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sheetList.size();
    }

}
