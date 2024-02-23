package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.LeadTypeData;

import java.util.List;


public class LeadTypeAdapter extends BaseAdapter {
    Context context;
    List<LeadTypeData> stagesList;
    LayoutInflater inflter;

    public LeadTypeAdapter(Context context, List<LeadTypeData> stagesList) {
        this.context = context;
        this.stagesList = stagesList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return stagesList.size();
    }

    @Override
    public LeadTypeData getItem(int position) {
        return stagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView) v.findViewById(R.id.title);
//        if(!stagesList.get(position).getRole().equals("admin"))
        title.setText(stagesList.get(position).getName());
        return v;
    }
}
