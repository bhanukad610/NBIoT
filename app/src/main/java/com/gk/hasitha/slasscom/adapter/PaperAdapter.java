package com.gk.hasitha.slasscom.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gk.hasitha.slasscom.data.PapersDataGetter;

import java.util.ArrayList;
import java.util.List;

import aluth.solutions.etwinkle.aluth.R;
import com.gk.hasitha.slasscom.data.MyDataOne;

/**
 * Created by hasitha on 7/19/17.
 */

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.ViewHolder>{

    public Context context;
    public String paperID;
    public List<PapersDataGetter> papersDataGetters;
    public List<MyDataOne> myDataOnesList;
    public String status;

    public PaperAdapter(Context context, List<PapersDataGetter> papersDataGetters, List<MyDataOne> myDataOnesList, String status) {
        this.context = context;
        this.papersDataGetters = papersDataGetters;
        this.myDataOnesList = myDataOnesList;
        this.status = status;
    }

    @Override
    public PaperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);

        return new PaperAdapter.ViewHolder(itemView);
    }

    public void updateData(List<PapersDataGetter> viewModels) {
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
    public void onBindViewHolder(PaperAdapter.ViewHolder holder, final int position) {

        if(status == "Cat_Filter"){
            holder.id.setVisibility(View.GONE);
        }

        holder.question.setText(papersDataGetters.get(position).getPaperName());
        holder.id.setText(papersDataGetters.get(position).getId()+".");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             Intent intent = new Intent(context, QuizPaperActivity.class);
//             intent.putExtra("id",papersDataGetters.get(position).getPaperID());
//             context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return papersDataGetters.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        public TextView question,answer,id;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.question);
            id = (TextView) itemView.findViewById(R.id.id);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
    public void setFilter(ArrayList<PapersDataGetter> newList){
        papersDataGetters = new ArrayList<>();
        papersDataGetters.addAll(newList);
        notifyDataSetChanged();
    }

    public void setFilterProfile(ArrayList<PapersDataGetter> newList){
        papersDataGetters = new ArrayList<>();
        papersDataGetters.addAll(newList);
        notifyDataSetChanged();
    }

}
