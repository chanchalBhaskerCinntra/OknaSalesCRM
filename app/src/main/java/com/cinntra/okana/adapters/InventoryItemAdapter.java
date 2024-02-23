package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.model.QuotationItem;

import java.util.List;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.ViewHolder> {
    Context context;
   List<QuotationItem> list;
    public InventoryItemAdapter(Context context, List<QuotationItem> list)
       {
   this.context = context;
   this.list = list;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View rootView = LayoutInflater.from(context).inflate(R.layout.inventory_item_adapter,parent,false);
     return new ViewHolder(rootView);
       }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final  QuotationItem obj = getItem(position);
       holder.item_name.setText(obj.getCardName());

    }

    @Override
    public int getItemCount() {
        return list.size();

    }
    public QuotationItem getItem(int position)
     {
    return list.get(position);
     }



    class ViewHolder extends RecyclerView.ViewHolder {
          private TextView item_name;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);




        }
    }
}
