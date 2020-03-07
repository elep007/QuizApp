package com.ttb.quiz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ttb.quiz.Models.Achivement_model;
import com.ttb.quiz.Models.LeaderboardModel;
import com.ttb.quiz.Models.RankListModel;
import com.ttb.quiz.Models.View_pager_model;
import com.ttb.quiz.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends ArrayAdapter {
    List view_pager_models;
    Context context;
    int resource;


    public LeaderboardAdapter(Context context, int resource, List view_pager_models) {
        super(context, resource,view_pager_models);
        this.context = context;
        this.resource = resource;
        this.view_pager_models = view_pager_models;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource,null,false);
        final View_pager_model model = (View_pager_model) view_pager_models.get(position);
//        TextView txt_tittle = view.findViewById(R.id.txt_tittle);
//        TextView txt_overall_gobal_rank = view.findViewById(R.id.txt_overall_gobal_rank);
//        TextView txt_overall_india = view.findViewById(R.id.txt_overall_india);
//        txt_tittle.setText(rank.getTittle());
//        txt_overall_gobal_rank.setText(rank.getGlobalrank());
//        txt_overall_india.setText(rank.getIndiarank());


        return view;

    }
}
