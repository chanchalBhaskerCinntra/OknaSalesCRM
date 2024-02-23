package com.cinntra.okana.adapters;

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
import com.cinntra.okana.model.OpportunityModels.OppAddressResponseModel;

import java.util.ArrayList;

public class BPAddressAdapter extends ArrayAdapter<OppAddressResponseModel.Data> {
    Context context;
    int resourceId;
    ArrayList<OppAddressResponseModel.Data> items;

    public BPAddressAdapter(@NonNull Context context, int resourceId, ArrayList<OppAddressResponseModel.Data> items) {
        super(context, resourceId, items);
        this.items = items;
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
        OppAddressResponseModel.Data model = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(model.getAddressName());
        return view;
    }

    @Nullable
    @Override
    public OppAddressResponseModel.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

