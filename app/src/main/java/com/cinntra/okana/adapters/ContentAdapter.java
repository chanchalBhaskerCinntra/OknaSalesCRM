package com.cinntra.okana.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.fragments.Content_Detail_Fragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.QuotationDocumentLines;
import java.util.ArrayList;


public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    Context context;
    ArrayList<QuotationDocumentLines> contentList;
    public ContentAdapter(Context context,ArrayList<QuotationDocumentLines> contentList)
       {
    this.context     = context;
    this.contentList = contentList;

      }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.content_adapter_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
          {

       QuotationDocumentLines obj = getItem(position);
       holder.title.setText(obj.getItemDescription());
       holder.item_id.setText(obj.getItemCode());
       holder.quantity.setText("Quantity: "+obj.getQuantity());
       holder.unit_price.setText( "Unit Price: "+Globals.getAmmount(obj.getCurrency(),obj.getUnitPrice()));
       //long total = Long.parseLong(obj.getQuantity())*Long.parseLong(obj.getPrice());
      holder.total_price.setText( "Total: "+Globals.getAmmount(obj.getCurrency(),obj.getLineTotal()));
           }

    @Override
    public int getItemCount() {
        return contentList.size();
    }
    public QuotationDocumentLines getItem(int position)
    {
   return contentList.get(position);
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,item_id,quantity,unit_price,total_price;
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
         Bundle b = new Bundle();
         b.putSerializable(Globals.QuotationLine,contentList.get(getAdapterPosition()));
         Content_Detail_Fragment fragment = new Content_Detail_Fragment();
         fragment.setArguments(b);
         FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
         transaction.add(R.id.quatoes_main_container, fragment);
         transaction.addToBackStack(null);
         transaction.commit();
              }
      });



        }
    }
}
