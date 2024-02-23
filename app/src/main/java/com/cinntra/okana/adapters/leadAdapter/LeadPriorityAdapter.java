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

import java.util.List;


public class LeadPriorityAdapter extends ArrayAdapter<LeadTypeData> {
    private Context context;
    private int resourceId;
    List<LeadTypeData> stagesList;


    public LeadPriorityAdapter(@NonNull Context context, int resourceId, List<LeadTypeData> stagesList) {
        super(context, resourceId, stagesList);
        this.stagesList = stagesList;
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
        name.setText(stagesList.get(position).getName());
        return view;
    }

    @Override
    public int getCount() {
        return stagesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public LeadTypeData getItem(int position) {
        return stagesList.get(position);
    }

}
