package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    Context context;

    public ListAdapter(Context context)
    {
      this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    return new ViewHolder(rootView);
      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
     { }

    @Override
    public int getItemCount()
      {
    return 7;
      }



    class ViewHolder extends RecyclerView.ViewHolder {
   private TextView item_name;
   public ViewHolder(@NonNull View itemView) {
   super(itemView);

          }
    }
}
