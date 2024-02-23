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
import com.cinntra.okana.activities.DeliveryActivity;


public class DeliveryItemAdapter extends RecyclerView.Adapter<DeliveryItemAdapter.ViewHolder> {
    Context context;
    String itemNames[];
    int icons[];
    String[] deliverycounter;
    public DeliveryItemAdapter(Context context, String itemNames[], int []icons,String[] deliverycounter)
       {
    this.context   = context;
    this.itemNames = itemNames;
    this.icons     = icons;
    this.deliverycounter = deliverycounter;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View rootView = LayoutInflater.from(context).inflate(R.layout.dashboard_item,parent,false);
     return new ViewHolder(rootView);
       }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.item_name.setText(itemNames[position]);
      holder.icon.setImageResource(icons[position]);

      if(itemNames[position].equalsIgnoreCase("Open"))
          holder.counter.setText(""+ deliverycounter[1]);
      else if(itemNames[position].equalsIgnoreCase("Closed"))
          holder.counter.setText(""+deliverycounter[2]);
      else if(itemNames[position].equalsIgnoreCase("Overdue"))
          holder.counter.setText(""+deliverycounter[0]);

      holder.card.setOnClickListener(new View.OnClickListener()
         {
      @Override
      public void onClick(View v) {
       Intent i = new Intent(context, DeliveryActivity.class);
       i.putExtra("DeliveryType",itemNames[position]);
       context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
      {
    return itemNames.length;
      }



    class ViewHolder extends RecyclerView.ViewHolder {
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
