package com.ttb.quiz.Adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ttb.quiz.Models.Topics_model;
import com.ttb.quiz.R;
import com.ttb.quiz.View.All_Topic_Internal;
import com.ttb.quiz.connections.Utils;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapterInternal extends RecyclerView.Adapter<TopicAdapterInternal.SingleItemRowHolder> {

    private List itemsList;
    private Context mContext;
    int rotationAngle = 0, rotationAnglehelp = 0;
    boolean showsetting = true, showhelp = true;
    ArrayList<String> home_models;
    All_Topic_Internal all_topic_internal;
    String title="";

    public TopicAdapterInternal(Context context, List itemsList, All_Topic_Internal all_topic_internal) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.all_topic_internal=all_topic_internal;

    }

    @Override
    public TopicAdapterInternal.SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.topic_items_internal, null);
        TopicAdapterInternal.SingleItemRowHolder mh = new TopicAdapterInternal.SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final TopicAdapterInternal.SingleItemRowHolder holder, final int i) {

        final Topics_model model = (Topics_model) itemsList.get(i);

        holder.iv_play_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                all_topic_internal.play_game(model.getTopic_id(),model.getSub_topic_id());
                // Toast.makeText(mContext,i+"  "+model.getSub_topic_id(),Toast.LENGTH_LONG).show();
            }
        });
        if(model.getPosition()!=0){
            holder.txt_topic.setVisibility(View.GONE);
        }
        holder.txt_topic.setText(model.getTitle());
        holder.txt_technology.setText(model.getSub_title());
        holder.txt_tittle.setText(model.getDesc());
        holder.txt_level_topic.setText("LEVEL "+model.getLevel());

        if(model.getLevel().equals("1")){
            holder.txt_rank_topic.setText(model.getLevel() + "st");
        }else if(model.getLevel().equals("2")){
            holder.txt_rank_topic.setText(model.getLevel() + "nd");
        } else if(model.getLevel().equals("3")){
            holder.txt_rank_topic.setText(model.getLevel() + "rd");
        } else if(model.getLevel().equals("4")){
            holder.txt_rank_topic.setText(model.getLevel() + "th");
        }
        Picasso.with(mContext).load(Utils.subtopic_images + model.getImage())
                .fit()
                .placeholder(R.drawable.ic_face)
                .into(holder.img_topic);

        holder.im_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(holder.im_setting, "rotation", rotationAngle, rotationAngle + 180);
                anim.setDuration(500);
                anim.start();
                rotationAngle += 180;
                rotationAngle = rotationAngle % 360;
                if (showsetting) {
                    showsetting = false;
                    if (!showhelp) {
                        ObjectAnimator anim1 = ObjectAnimator.ofFloat(holder.ll_topic_show, "rotation", rotationAnglehelp, rotationAnglehelp + 180);
                        anim1.setDuration(500);
                        anim1.start();
                        rotationAnglehelp += 180;
                        rotationAnglehelp = rotationAnglehelp % 360;
                    }
                    showhelp = true;

                    holder.ll_topic_show.setVisibility(View.VISIBLE);
                    holder.ll_topic_show.animate().alpha(1.0f);
                } else {
                    showsetting = true;
                    holder.ll_topic_show.animate().alpha(0.0f);
                    holder.ll_topic_show.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        TextView txt_topic, txt_technology, txt_rank_topic, txt_level_topic,txt_tittle;
        ImageView iv_play_topic;
        de.hdodenhof.circleimageview.CircleImageView im_setting,img_topic;
        LinearLayout ll_topic_show;



        @SuppressLint("WrongViewCast")
        public SingleItemRowHolder(View view) {
            super(view);
            txt_topic = (TextView) view.findViewById(R.id.txt_topic);
            iv_play_topic = (ImageView) view.findViewById(R.id.iv_play_topic);
            txt_technology = (TextView) view.findViewById(R.id.txt_technology);
            txt_rank_topic = (TextView) view.findViewById(R.id.txt_rank_topic);
            txt_level_topic = (TextView) view.findViewById(R.id.txt_level_topic);
            img_topic = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.img_topic);
            txt_tittle = (TextView) view.findViewById(R.id.txt_tittle);
            im_setting = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.im_setting);
            ll_topic_show = (LinearLayout) view.findViewById(R.id.ll_topic_show);

        }
    }

}
