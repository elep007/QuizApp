package com.ttb.quiz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttb.quiz.Models.Result_model;
import com.ttb.quiz.R;
import com.ttb.quiz.View.Login;
import com.ttb.quiz.View.Won_page;
import com.ttb.quiz.connections.Utils;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.SingleItemRowHolder> {

    private List itemsList;
    private Context mContext;

    public GameAdapter(Context context, List itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;

    }

    @Override
    public GameAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.games_result_items, null);
        GameAdapter.SingleItemRowHolder mh = new GameAdapter.SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final GameAdapter.SingleItemRowHolder holder, final int i) {
        final Result_model model = (Result_model) itemsList.get(i);
        holder.txt_topic_game_results.setText(model.getTitle());
        holder.txt_username_game_results.setText(model.getName());
        holder.txt_country_game_results.setText(model.getCountry());
        holder.txt_trophy_game_results.setText(model.getRanking());
        holder.txt_level_game_results.setText(model.getLevel());
        holder.btn_status_game_results.setText("You "+model.getResult());
//        holder.btn_status_game_results.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mContext.startActivity(new Intent(mContext,Login.class));
//
//            }
//        });

        Picasso.with(mContext).load(Utils.user_image_url + model.getProfile_pic())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(holder.profile_image_game_results);
        Picasso.with(mContext).load(Utils.country_flag_url + model.getCountry_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(holder.country_games_results);

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        TextView txt_topic_game_results, txt_username_game_results,
                txt_country_game_results, txt_trophy_game_results, txt_level_game_results;

        Button btn_status_game_results;
        ImageView profile_image_game_results,country_games_results;

        public SingleItemRowHolder(View view) {
            super(view);
            profile_image_game_results=(ImageView)view.findViewById(R.id.profile_image_game_results);
            country_games_results=(ImageView)view.findViewById(R.id.country_games_results);
            txt_topic_game_results = (TextView) view.findViewById(R.id.txt_topic_game_results);
            txt_username_game_results = (TextView) view.findViewById(R.id.txt_username_game_results);
            txt_country_game_results = (TextView) view.findViewById(R.id.txt_country_game_results);
            txt_trophy_game_results = (TextView) view.findViewById(R.id.txt_trophy_game_results);
            txt_level_game_results = (TextView) view.findViewById(R.id.txt_level_game_results);
            btn_status_game_results =(Button)view.findViewById(R.id.btn_status_game_results);

        }
    }
}
