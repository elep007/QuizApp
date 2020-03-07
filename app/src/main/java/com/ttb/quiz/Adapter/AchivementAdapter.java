package com.ttb.quiz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttb.quiz.Models.Achivement_model;
import com.ttb.quiz.R;

import java.util.List;

public class AchivementAdapter extends RecyclerView.Adapter<AchivementAdapter.SingleItemRowHolder> {

    private List itemsList;
    private Context mContext;

    public AchivementAdapter(Context context, List itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;

    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.achivement_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        final Achivement_model model = (Achivement_model) itemsList.get(i);
        holder.achivement_text_1.setText(model.getTitle());
//        holder.txt_wins_ranking_percentage .setText(model.getCoins());

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        TextView achivement_text_1,txt_wins_ranking_percentage;

        public SingleItemRowHolder(View view) {
            super(view);
            achivement_text_1 = (TextView) view.findViewById(R.id.achivement_text_1);
            txt_wins_ranking_percentage= (TextView) view.findViewById(R.id.txt_wins_ranking_percentage);

        }
    }


}
