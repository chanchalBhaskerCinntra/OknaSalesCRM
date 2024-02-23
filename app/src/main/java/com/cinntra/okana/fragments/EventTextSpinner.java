package com.cinntra.okana.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.cinntra.okana.R;

import java.util.ArrayList;

public class EventTextSpinner extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> categories;

    public EventTextSpinner(@NonNull Context context, ArrayList<String> categories) {
        super(context, R.layout.event_spinner_text_item,categories);
        this.context = context;
        this.categories = categories;
    }


    @Nullable
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.event_spinner_text_item,null);
        TextView it = (TextView) row.findViewById(R.id.categorieslist);
        Typeface typeface = ResourcesCompat.getFont(context,R.font.muli_regular);
        it.setTypeface(typeface);
        it.setTextSize(16);
        it.setTextColor(R.color.black);
        it.setText(categories.get(position));

        return row;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.event_spinner_text_item,null);
        TextView it = (TextView) row.findViewById(R.id.categorieslist);
        Typeface typeface = ResourcesCompat.getFont(context,R.font.muli_regular);
        it.setTypeface(typeface);
        it.setTextSize(16);
        it.setTextColor(R.color.black);
        it.setText(categories.get(position));

        return row;
    }
}
