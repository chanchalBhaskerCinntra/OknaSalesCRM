package com.cinntra.okana.fragments;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.cinntra.okana.R;
import java.util.ArrayList;

public class EventPrerioritySpinner  extends ArrayAdapter<Integer> {
    Context context;
    ArrayList<Integer> circleimage;
    public EventPrerioritySpinner(@NonNull Context context, ArrayList<Integer> circleimage) {
        super(context, R.layout.event_preority_item,circleimage);
        this.context = context;
        this.circleimage = circleimage;
    }


    @Nullable
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.event_preority_item,null);
        ImageView iv = (ImageView) row.findViewById(R.id.imageitem);
        iv.setImageResource(circleimage.get(position));

        return row;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.event_preority_item,null);
        ImageView iv = (ImageView) row.findViewById(R.id.imageitem);
        iv.setImageResource(circleimage.get(position));

        return row;
    }
}
