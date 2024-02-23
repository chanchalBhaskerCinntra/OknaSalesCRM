package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.CountryData;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    Context context;
    List<CountryData> countries;
    LayoutInflater inflter;

    public CountryAdapter(Context context, List<CountryData> countries) {
        this.context = context;
        this.countries = countries;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int position) {
        return countries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflter.inflate(R.layout.stages_spinner_item, null);
        TextView title = (TextView)v.findViewById(R.id.title);
        title.setText(countries.get(position).getName());
        return v;
    }
}
