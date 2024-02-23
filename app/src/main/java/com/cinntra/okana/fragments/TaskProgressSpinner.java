package com.cinntra.okana.fragments;

import android.annotation.SuppressLint;
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


public class TaskProgressSpinner extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> progress_status;

    public TaskProgressSpinner(@NonNull Context context, ArrayList<String> progress_status) {
        super(context, R.layout.spinner_item,progress_status);
        this.context = context;
        this.progress_status = progress_status;
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.spinner_item,null);
        TextView it = (TextView) row.findViewById(R.id.categorieslist);
        Typeface typeface = ResourcesCompat.getFont(context,R.font.muli_regular);
        it.setTypeface(typeface);
        it.setTextSize(16);
        it.setTextColor(R.color.black);
        it.setText(progress_status.get(position));

        return row;
    }


    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.spinner_item,null);
        TextView it = (TextView) row.findViewById(R.id.categorieslist);
        Typeface typeface = ResourcesCompat.getFont(context,R.font.muli_regular);
        it.setTypeface(typeface);
        it.setTextSize(16);
        it.setTextColor(R.color.black);
        it.setText(progress_status.get(position));

        return row;
    }


}
