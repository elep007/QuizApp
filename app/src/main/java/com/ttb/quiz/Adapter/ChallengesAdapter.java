package com.ttb.quiz.Adapter;

import android.content.Context;
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
import com.ttb.quiz.View.Challenges;
import com.ttb.quiz.connections.Utils;

import java.util.List;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.SingleItemRowHolder> {

    private List itemsList;
    private Context mContext;
    Challenges challenges;

    public ChallengesAdapter(Context context, List itemsList, Challenges challenges) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.challenges=challenges;
    }

    @Override
    public ChallengesAdapter.SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.challenges_items, null);
        ChallengesAdapter.SingleItemRowHolder mh = new ChallengesAdapter.SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final ChallengesAdapter.SingleItemRowHolder holder, final int i) {
        final Result_model model = (Result_model) itemsList.get(i);
        holder.txt_topic_challenges.setText(model.getTitle());
        holder.txt_username_challenges.setText(model.getName());
        holder.txt_country_challenges.setText(model.getCountry());
        holder.txt_trophy_challenges.setText(model.getRanking());
        holder.txt_level_challenges.setText(model.getLevel());
       // holder.btn_status_challenges.setText("You are "+model.getResult());
//        holder.btn_status_challenges.setOnClickListener(new View.OnClickListener() {
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
                .into(holder.profile_image_challenges);
        Picasso.with(mContext).load(Utils.country_flag_url + model.getCountry_flag())
                .fit()
                .placeholder(R.drawable.circle)
                .into(holder.country_challenges);

        holder.btn_challenges_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Toast.makeText(mContext,model.getGame_id()+" "+model.getTopicid()+"  "+model.getSubtopicid(),Toast.LENGTH_LONG).show();
                challenges.accept_challenge(model.getGame_id(),model.getTopicid(),model.getSubtopicid(),model.getUser_id(),model.getName(),model.getCountry()
                ,model.getCountry_flag(),model.getProfile_pic());
            }
        });
        holder.btn_challenges_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challenges.deny_challenge(model.getGame_id(),model.getTopicid(),model.getSubtopicid());
            }
        });
    }
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        TextView txt_topic_challenges, txt_username_challenges, txt_country_challenges, txt_trophy_challenges, txt_level_challenges;        //
        Button btn_challenges_decline, btn_challenges_accept;
        ImageView profile_image_challenges, country_challenges;
        public SingleItemRowHolder(View view) {
            super(view);
            profile_image_challenges = (ImageView) view.findViewById(R.id.profile_image_challenges);
            country_challenges = (ImageView) view.findViewById(R.id.country_challenges);
            txt_username_challenges = (TextView) view.findViewById(R.id.txt_username_challenges);
            txt_country_challenges = (TextView) view.findViewById(R.id.txt_country_challenges);
            txt_trophy_challenges = (TextView) view.findViewById(R.id.txt_trophy_challenges);
            txt_topic_challenges = (TextView) view.findViewById(R.id.txt_topic_challenges);
            txt_level_challenges = (TextView) view.findViewById(R.id.txt_level_challenges);
            btn_challenges_decline = (Button) view.findViewById(R.id.btn_challenges_decline);
            btn_challenges_accept = (Button) view.findViewById(R.id.btn_challenges_accept);

        }
    }
}
