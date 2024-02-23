package com.cinntra.okana.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.DocumentLines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Selected_Item_Adapter extends RecyclerView.Adapter<Selected_Item_Adapter.ViewHolder> {
    Context context;
    private ArrayList<DocumentLines> arrList;
    public Selected_Item_Adapter(Context context, ArrayList<DocumentLines> arrList)
       {
    this.context = context;
    this.arrList = arrList;
    this.tempList = new ArrayList<>();
    this.tempList.addAll(arrList);

      }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.selected_item_adapter,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DocumentLines obj = getItem(position);
        holder.title.setText(obj.getItemDescription());
        holder.item_id.setText(obj.getItemCode());
        holder.unit_price.setText( "Unit Price: "+ obj.getUnitPrice());
        holder.total_price.setText( "TaxCode: "+obj.getTaxCode());
        holder.quantity.setText(""+(int)Float.parseFloat(obj.getQuantity()));

        if(Globals.inventory_item_close){
            holder.remove.setVisibility(View.GONE);
            holder.minus.setClickable(false);
            holder.minus.setFocusable(false);
            holder.plus.setClickable(false);
            holder.plus.setFocusable(false);
            holder.quantity.setFocusable(false);
        }else{
            holder.remove.setVisibility(View.VISIBLE);
            holder.minus.setClickable(true);
            holder.minus.setFocusable(true);
            holder.plus.setClickable(true);
            holder.plus.setFocusable(true);
            holder.quantity.setFocusable(true);

            holder.plus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(holder.quantity.getText().toString())>=0){
                        int qty = Integer.parseInt(holder.quantity.getText().toString());
                        qty++;
                        holder.quantity.setText(""+qty);
                        obj.setQuantity(""+qty);
                    }
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(holder.quantity.getText().toString())>1){
                        int qty = Integer.parseInt(holder.quantity.getText().toString());
                        qty--;
                        holder.quantity.setText(""+qty);
                        obj.setQuantity(""+qty);
                    }
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    arrList.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }
    public DocumentLines getItem(int po)
    {
        return  arrList.get(po);
    }

    List<DocumentLines> tempList  ;
    public void filter(String newText)
    {
        String  charText = newText.toLowerCase(Locale.getDefault()).trim();
        arrList.clear();
        if (charText.length() == 0) {
            arrList.addAll(tempList);
        } else {
            for (DocumentLines st : tempList) {
                /*if(st.getOpportunityName()!=null&&!st.getOpportunityName().isEmpty()) {
                    if (st.getCustomerName().toLowerCase().trim().contains(charText)) {*/
                if(st.getItemCode()!=null&&!st.getItemCode().isEmpty()&&st.getItemDescription()!=null&&!st.getItemDescription().isEmpty()) {
                    if (st.getItemCode().toLowerCase().trim().contains(charText)||st.getItemDescription().toLowerCase().trim().contains(charText)) {
                        arrList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,item_id,unit_price,total_price,plus,minus;
        EditText quantity;
        LinearLayout remove;
       public ViewHolder(@NonNull View itemView) {
        super(itemView);

           title       = itemView.findViewById(R.id.title);
           item_id     = itemView.findViewById(R.id.item_id);
           quantity    = itemView.findViewById(R.id.quantity);
           unit_price  = itemView.findViewById(R.id.unit_price);
           total_price = itemView.findViewById(R.id.total_price);
           plus        = itemView.findViewById(R.id.plus);
           minus       = itemView.findViewById(R.id.minus);
           remove      = itemView.findViewById(R.id.remove);



   itemView.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {

       }
   });



        }
    }
}
