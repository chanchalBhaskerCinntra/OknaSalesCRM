package com.cinntra.okana.spinneradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.CountryData;

import java.util.List;

public class CountrySpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<CountryData> data; // Replace 'YourDataClass' with the actual class name.

    public CountrySpinnerAdapter(Context context, List<CountryData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.stages_spinner_item, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.item_name); // Assuming you have a TextView in your item layout with id 'item_name'

        // Get the data item for this position
        CountryData item = (CountryData) getItem(position);

        // Set the item name to the TextView
        itemName.setText(item.getName());

        return convertView;
    }
}

