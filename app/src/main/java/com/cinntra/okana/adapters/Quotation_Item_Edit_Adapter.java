package com.cinntra.okana.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.fragments.Edit_Item_Fragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.DocumentLines;

import java.util.ArrayList;


public class Quotation_Item_Edit_Adapter extends RecyclerView.Adapter<Quotation_Item_Edit_Adapter.ViewHolder> {
    Context context;
    private ArrayList<DocumentLines> arrList;
    public Quotation_Item_Edit_Adapter(Context context, ArrayList<DocumentLines> arrList)
       {
    this.context = context;
    this.arrList = arrList;

      }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.quatation_edit_item_adapter_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DocumentLines obj = getItem(position);
        holder.title.setText(obj.getItemDescription());
        holder.item_id.setText(obj.getItemCode());
        holder.quantity.setText(obj.getQuantity());
        /*holder.unit_price.setText( "Unit Price: "+ Globals.getAmmount(obj.getCurrency(),obj.getUnitPrice()));
        holder.total_price.setText( "Total: "+Globals.getAmmount(obj.getCurrency(),obj.getLineTotal()));*/

        holder.unit_price.setText( "Unit Price: "+ obj.getUnitPrice());
        holder.total_price.setText( "Total: "+(Float.valueOf(obj.getUnitPrice())*Integer.valueOf(obj.getQuantity())));




    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }
    public DocumentLines getItem(int po)
    {
        return  arrList.get(po);
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,item_id,unit_price,total_price;
        EditText quantity;
       public ViewHolder(@NonNull View itemView) {
            super(itemView);

           title       = itemView.findViewById(R.id.title);
           item_id     = itemView.findViewById(R.id.item_id);
           quantity    = itemView.findViewById(R.id.quantity);
           unit_price  = itemView.findViewById(R.id.unit_price);
           total_price = itemView.findViewById(R.id.total_price);


   itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Log.e("Clicking=>",""+getAdapterPosition());
           Bundle b = new Bundle();
           b.putParcelable(Globals.QuotationLine,arrList.get(getAdapterPosition()));
           Edit_Item_Fragment fragment = new Edit_Item_Fragment();
           fragment.setArguments(b);
           FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
           transaction.replace(R.id.main_frame, fragment);
           transaction.addToBackStack(null);
           transaction.commit();
       }
   });



        }
    }
}
