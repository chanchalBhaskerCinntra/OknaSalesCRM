package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.Role;

import java.util.List;

public class RoleAdapter extends BaseAdapter {

    List<Role> getRole;
    Context context;
    LayoutInflater inflter;
    public RoleAdapter(Context context, List<Role> getRole) {
        this.context = context ;
        this.getRole = getRole;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return getRole.size();
    }

    @Override
    public Object getItem(int position) {
        return getRole.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(getRole.get(position).getName());
        return v;
    }
}
