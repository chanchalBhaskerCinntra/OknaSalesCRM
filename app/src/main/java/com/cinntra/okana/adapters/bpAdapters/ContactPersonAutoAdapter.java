package com.cinntra.okana.adapters.bpAdapters;

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
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.LeadTypeData;

import java.util.ArrayList;

public class ContactPersonAutoAdapter extends ArrayAdapter<ContactPersonData> {
    Context context;
    int resourceId;
    ArrayList<ContactPersonData> items;

    public ContactPersonAutoAdapter(@NonNull Context context, int resourceId, ArrayList<ContactPersonData> items) {
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
        ContactPersonData model = getItem(position);
        TextView name = view.findViewById(R.id.text_view);
        name.setText(model.getFirstName());
        return view;
    }

    @Nullable
    @Override
    public ContactPersonData getItem(int position) {
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
