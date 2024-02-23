package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.DepartMent;

import java.util.List;

public class DepartMentAdapter extends BaseAdapter {

    List<DepartMent> getDepartMent;
    Context context;
    LayoutInflater inflter;
    public DepartMentAdapter(Context context, List<DepartMent> getDepartMent) {
        this.context = context ;
        this.getDepartMent = getDepartMent;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return getDepartMent.size();
    }

    @Override
    public Object getItem(int position) {
        return getDepartMent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(getDepartMent.get(position).getName());
        return v;
    }
}
