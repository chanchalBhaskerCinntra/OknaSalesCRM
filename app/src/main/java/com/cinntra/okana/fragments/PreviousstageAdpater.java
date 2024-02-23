package com.cinntra.okana.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.StagesValue;

import java.util.List;

public class PreviousstageAdpater extends BaseAdapter {

    List<StagesValue> arrayList;
    Context context;
    LayoutInflater inflter;
    public PreviousstageAdpater(Context context, List<StagesValue> arraylist) {
        this.context = context;
        this.arrayList = arraylist;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return arrayList.size()-1;
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(arrayList.get(position).getName());
        return v;
    }
}
