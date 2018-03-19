package com.gk.hasitha.slasscom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

import com.gk.hasitha.slasscom.MainActivity;
import aluth.solutions.etwinkle.aluth.R;

import com.gk.hasitha.slasscom.activity.SmsActivity;
import com.gk.hasitha.slasscom.activity.WaterTapActivity;
import com.gk.hasitha.slasscom.data.CategoryData;

/**
 * Created by hasitha on 8/17/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public Context context;
    public List<CategoryData> categoryAdapterList;

    public CategoryAdapter(Context context, List<CategoryData> categoryAdapterList) {
        this.context = context;
        this.categoryAdapterList = categoryAdapterList;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_card, parent, false);

        return new CategoryAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, final int position) {

        holder.cat_title.setText(categoryAdapterList.get(position).getCategory());
        holder.cat_extra.setText(categoryAdapterList.get(position).getExtra());
        Log.d("yahook", categoryAdapterList.get(position).getCategory().toString());
        Glide.with(context).load(categoryAdapterList.get(position).getImg_url()).into(holder.cat_image);
        String photo = categoryAdapterList.get(position).getImg_url();
        if(photo.contains("temp")){
            Glide.with(context).load(R.drawable.temperature).into(holder.cat_image);
        } else if(photo.contains("humi")) {
            Glide.with(context).load(R.drawable.humidity).into(holder.cat_image);
        } else if (photo.contains("mois")){
            Glide.with(context).load(R.drawable.moisture).into(holder.cat_image);
        } else if (photo.contains("wat")){
            Glide.with(context).load(R.drawable.water).into(holder.cat_image);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (categoryAdapterList.get(position).getPaperName().contains("new")){
////                    Intent intent1 = new Intent(context, MainActivity.class);
////                    MainActivity.setPackageNameQuiz("com.etwinkle.solutions.generalknowledgeapp");
////                    MainActivity.setStatusPlayStore("Yes");
////                    context.startActivity(intent1);
//                }else {
//                    Intent intent = new Intent(context, CategoryActivity.class);
//                    intent.putExtra("paperName", categoryAdapterList.get(position).getPaperName().toString());
//                    intent.putExtra("cat", categoryAdapterList.get(position).getCategory().toString());
//                    context.startActivity(intent);
//                }
                if(categoryAdapterList.get(position).getPaperName().contains("wat")){
                    context.startActivity(new Intent(context,WaterTapActivity.class));
                } else {
                    MainActivity.type = categoryAdapterList.get(position).getPaperName();
                    Intent intent = new Intent(context, SmsActivity.class);
                    intent.putExtra("type", categoryAdapterList.get(position).getPaperName());
                    context.startActivity(new Intent(context, SmsActivity.class));
                }
            }
        });

        holder.cardView.setMinimumWidth((MainActivity.getWidth())/2);

    }

    public void updateData(ArrayList<CategoryData> viewModels) {
        /*
        int preSize = items.size();
        items.clear();
        items = new ArrayList<>();
        items.addAll(viewModels);
        */
        notifyDataSetChanged();
        //notifyItemRangeChanged(v,items.size());
    }

    @Override
    public int getItemCount() {
        return categoryAdapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cat_title;
        public ImageView cat_image;
        public TextView cat_extra;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cat_title = (TextView) itemView.findViewById(R.id.cat_title);
            cat_image = (ImageView) itemView.findViewById(R.id.cat_image);
            cat_extra = (TextView) itemView.findViewById(R.id.cat_extra);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

        }
    }

    public void setFilter(ArrayList<CategoryData> newList){
        categoryAdapterList = new ArrayList<>();
        categoryAdapterList.addAll(newList);
        notifyDataSetChanged();
    }

    public void setFilterProfile(ArrayList<CategoryData> newList){
        categoryAdapterList = new ArrayList<>();
        categoryAdapterList.addAll(newList);
        notifyDataSetChanged();
    }

}
