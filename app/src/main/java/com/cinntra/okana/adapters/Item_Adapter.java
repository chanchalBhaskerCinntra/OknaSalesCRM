package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;


public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.ViewHolder> {
    Context context;
    public Item_Adapter(Context context)
       {
    this.context = context;

      }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_adapter_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {




    }

    @Override
    public int getItemCount() {
        return 7;
    }



    class ViewHolder extends RecyclerView.ViewHolder {
       public ViewHolder(@NonNull View itemView) {
            super(itemView);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });



        }
    }
}
