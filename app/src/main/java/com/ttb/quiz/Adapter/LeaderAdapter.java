package com.ttb.quiz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ttb.quiz.Models.Result_model;
import com.ttb.quiz.Models.View_pager_model;
import com.ttb.quiz.R;
import com.ttb.quiz.Session;
import com.ttb.quiz.View.Challenges;
import com.ttb.quiz.View.Leaderboard;
import com.ttb.quiz.connections.Utils;

import java.util.List;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.SingleItemRowHolder> {

    private List itemsList;
    private Context mContext;
    Leaderboard challenges;
    Session session;

    public LeaderAdapter(Context context, List itemsList, Leaderboard challenges, Session session) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.challenges=challenges;
        this.session=session;
    }

    @Override
    public LeaderAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leaderboard_exapandable_item, null);
        LeaderAdapter.SingleItemRowHolder mh = new LeaderAdapter.SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final LeaderAdapter.SingleItemRowHolder holder, final int i) {
        final View_pager_model model = (View_pager_model) itemsList.get(i);
        if(session.getId().equals(model.getId())){
            holder.txt_challenge_leardboard.setVisibility(View.GONE);
        }else {
            holder.txt_challenge_leardboard.setVisibility(View.VISIBLE);
        }
        holder.txt_home.setText(model.getName());
        holder.txt_country.setText(model.getCountry());
        holder.txt_rank.setText(model.getSubtopic_points());
        holder.rank.setText(model.getRank()+"st");

        Picasso.with(mContext).load(Utils.user_image_url + model.getProfile_pic())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(holder.play_profile_image);
        Picasso.with(mContext).load(Utils.country_flag_url + model.getCountry_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(holder.play_country);
        holder.txt_challenge_leardboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challenges.add_challenge(model.getTopic_id(),model.getSub_topic_id(),model.getId());
            }
        });

        // Set Data here

    }
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        TextView  txt_home,txt_country,txt_rank,rank,txt_challenge_leardboard;
        de.hdodenhof.circleimageview.CircleImageView play_profile_image;
        ImageView play_country;
        public SingleItemRowHolder(View view) {
            super(view);
            txt_home=view.findViewById(R.id.txt_home);
            txt_country=view.findViewById(R.id.txt_country);
            rank=view.findViewById(R.id.rank);
            play_profile_image=(de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.play_profile_image);
            play_country=(ImageView)view.findViewById(R.id.play_country);
            txt_rank=view.findViewById(R.id.txt_rank);
            txt_home=view.findViewById(R.id.txt_home);
            txt_challenge_leardboard=view.findViewById(R.id.txt_challenge_leardboard);

        }
    }
}
