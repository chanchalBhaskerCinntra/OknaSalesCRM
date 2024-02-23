package com.cinntra.okana.adapters.leadAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cinntra.okana.R;
import com.cinntra.okana.model.LeadTypeData;

import java.util.ArrayList;


public class LeadSourceAdapter extends ArrayAdapter<LeadTypeData> {
    private Context context;
    private int resourceId;
    ArrayList<LeadTypeData> assignList;


    public LeadSourceAdapter(@NonNull Context context, int resourceId, ArrayList<LeadTypeData> assignList) {
        super(context, resourceId, assignList);
        this.assignList = assignList;
        this.context = context;
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(resourceId, parent, false);
        }
        TextView name = view.findViewById(R.id.text_view);
        name.setText(assignList.get(position).getName());
        return view;
    }

    @Nullable
    @Override
    public LeadTypeData getItem(int position) {
        return assignList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return assignList.size();
    }
}