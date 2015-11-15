package com.soma6th.comdoc_android.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soma6th.comdoc_android.R;
import com.soma6th.comdoc_android.model.ExamCategory;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> implements View.OnClickListener {

    List<ExamCategory> examCategories;
    private final LayoutInflater inflater;
    private int i = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        static TextView mFixtype;
        static TextView mComputertype;
        static TextView mCompany;
        static TextView mLocation;
        static TextView mRating;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.sheet_thumnail_rv);
            mFixtype = (TextView) itemView.findViewById(R.id.fixtype_fixtype);
            mComputertype = (TextView) itemView.findViewById(R.id.fixtype_computertype);
            mCompany = (TextView) itemView.findViewById(R.id.fixtype_company);
            mLocation = (TextView) itemView.findViewById(R.id.fixtype_location);
            mRating = (TextView) itemView.findViewById(R.id.fixtype_rating);
        }
    }


    public SubCategoryAdapter(Activity activity, List<ExamCategory> examCategories) {
        //super(activity, R.layout.layout_sub_category_list, examCategories);
        this.inflater = LayoutInflater.from(activity.getApplicationContext());
        this.examCategories = examCategories;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sheetthumnail_item, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        ViewHolder.mFixtype.setText(examCategories.get(i).trouble_type);
        ViewHolder.mComputertype.setText(examCategories.get(i).computer_type);
        ViewHolder.mCompany.setText(examCategories.get(i).company_email);
        ViewHolder.mLocation.setText(examCategories.get(i).company_location);
        ViewHolder.mRating.setText(examCategories.get(i).review_point);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CardviewExampleNUM:", i + "");
//                final int suggestionSize = SheetInfoActivity.SuggetstionSize[i];
//                Intent intent = new Intent(v.getContext(), SheetInfoDetailActivity.class);
//                intent.putExtra("position",i);
//                intent.putExtra("suggestionSize",suggestionSize);
//
//
//                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return examCategories.size();
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder row;
//
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.fixtype_item, null);
//            row = new ViewHolder(convertView);
//            //row.subCategoryName = (TextView) convertView.findViewById(R.id.subCatTxtView);
//            convertView.setTag(row);
//        } else {
//            //row = (ViewHolder) convertView.getTag();
//        }
//
//        Category category = subCategoryList.get(position);
//        row.subCategoryName.setText(category.getTitle());
//        row.subCategoryName.setOnClickListener(this);
//        row.subCategoryName.setTag(position);
//        return convertView;
//    }

//    @Override
//    public int getCount() {
//        return examCategories.size();
//    }

    @Override
    public void onClick(View v) {
//        int position = Integer.parseInt(v.getTag().toString());
//        mCallbacks.onSubCategorySelected(subCategoryList.get(position).getId(), subCategoryList.get(position).getTitle());
    }



}
