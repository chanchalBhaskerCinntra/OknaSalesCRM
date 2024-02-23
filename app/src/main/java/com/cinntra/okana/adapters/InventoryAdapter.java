package com.cinntra.okana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.InventoryActivity;
import com.cinntra.okana.fragments.Dashboard;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    Context context;
    String itemNames[];
    int icons[];
    public InventoryAdapter(Context context, String itemNames[],int []icons)
      {
    this.context    = context;
    this.itemNames  = itemNames;
    this.icons      = icons;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
      {
     View rootView = LayoutInflater.from(context).inflate(R.layout.dashboard_item,parent,false);
     return new ViewHolder(rootView);
       }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
      {
      holder.item_name.setText(itemNames[position]);
      holder.icon.setImageResource(icons[position]);
      if(itemNames[position].equalsIgnoreCase("Fast Moving"))
        {
     holder.counter.setText(""+Dashboard.fastInventoryList.size());
         }
      else if(itemNames[position].equalsIgnoreCase("Slow Moving"))
        {
     holder.counter.setText(""+Dashboard.mediumInventoryList.size());
         }
      else if(itemNames[position].equalsIgnoreCase("Non Moving"))
        {
     holder.counter.setText(""+Dashboard.nonInventoryList.size());
        }

     holder.card.setOnClickListener(new View.OnClickListener()
        {
     @Override
     public void onClick(View v)
        {
     Intent intent = new Intent(context, InventoryActivity.class);
     intent.putExtra("IN_Type",itemNames[position]);
     context.startActivity(intent);
        }
      });
    }
    @Override
    public int getItemCount()
      {
    return itemNames.length;
      }



    class ViewHolder extends RecyclerView.ViewHolder
        {
          private TextView item_name,counter;
          private CardView card;
          private ImageView icon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            card      = itemView.findViewById(R.id.card);
            icon      = itemView.findViewById(R.id.icon);
            counter   = itemView.findViewById(R.id.counter);



        }
    }
}
