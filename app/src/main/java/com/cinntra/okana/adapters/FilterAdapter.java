package com.cinntra.okana.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    Context context;
    Dialog dialog;
    String text[] = {"All Opportunity", "My Opportunty", "My Favourite", "Closure tis month", "Existing Business", "New Business"};

    public FilterAdapter(Context context) {
        this.context = context;

    }
    public FilterAdapter(Context context, Dialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.filter_screen,parent,false);
        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.first.setText(text[position]);
        holder.first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {
        return text.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton first;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            first = itemView.findViewById(R.id.first);
        }
    }
}
