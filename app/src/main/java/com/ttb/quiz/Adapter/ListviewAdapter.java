package com.ttb.quiz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ttb.quiz.Models.RankListModel;
import com.ttb.quiz.R;

import java.util.ArrayList;
import java.util.List;

public class ListviewAdapter extends ArrayAdapter {
    List<RankListModel> ranklist;
    Context context;
    int resource;


    public ListviewAdapter(Context context, int resource, List<RankListModel> ranklist) {
        super(context, resource,ranklist);
        this.context = context;
        this.resource = resource;
        this.ranklist = ranklist;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource,null,false);
        TextView txt_tittle = view.findViewById(R.id.txt_tittle);
        TextView txt_overall_gobal_rank = view.findViewById(R.id.txt_overall_gobal_rank);
        TextView txt_overall_india = view.findViewById(R.id.txt_overall_india);
        RankListModel rank = ranklist.get(position);
        txt_tittle.setText(rank.getTittle());
        txt_overall_gobal_rank.setText(rank.getGlobalrank());
        txt_overall_india.setText(rank.getIndiarank());
        return view;

    }
}
