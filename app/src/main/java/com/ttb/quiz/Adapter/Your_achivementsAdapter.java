package com.ttb.quiz.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttb.quiz.Models.Achivement_model;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.connections.Utils;

import java.util.List;

public class Your_achivementsAdapter extends RecyclerView.Adapter<Your_achivementsAdapter.SingleItemRowHolder> {

    private List itemsList;
    private Context mContext;


    public Your_achivementsAdapter(Context context, List itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;


    }

    @Override
    public Your_achivementsAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.your_achivements_items, null);
        Your_achivementsAdapter.SingleItemRowHolder mh = new Your_achivementsAdapter.SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final Your_achivementsAdapter.SingleItemRowHolder holder, final int i) {
        final Achivement_model model = (Achivement_model) itemsList.get(i);
//        holder.txt_win_lose_achivement.setText("Result "+session.getGamae_result());
        holder.txt_sub_title_achivements.setText("Play " + model.getGames_to_play() + " Games");
        holder.txt_title_achivements.setText(model.getTitle());
        holder.txt_coins_achivements.setText(model.getCoins());
        Picasso.with(mContext).load(Utils.acheivement_images + model.getImage())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(holder.image);
        holder.mprogressBar.setProgress(model.getProgress());
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        ProgressBar mprogressBar, achivement_progress_bar;
        ImageView image;
        TextView txt_title_achivements, txt_sub_title_achivements, txt_win_lose_achivement, txt_coins_achivements;

        public SingleItemRowHolder(View view) {
            super(view);
            mprogressBar = (ProgressBar) view.findViewById(R.id.achivement_progress_bar);
            achivement_progress_bar = (ProgressBar) view.findViewById(R.id.achivement_progress_bar);
            image = (ImageView) view.findViewById(R.id.image);
            txt_title_achivements = (TextView) view.findViewById(R.id.txt_title_achivements);
            txt_sub_title_achivements = (TextView) view.findViewById(R.id.txt_sub_title_achivements);
            txt_win_lose_achivement = (TextView) view.findViewById(R.id.txt_win_lose_achivement);
            txt_coins_achivements = (TextView) view.findViewById(R.id.txt_coins_achivements);

        }
    }
}
