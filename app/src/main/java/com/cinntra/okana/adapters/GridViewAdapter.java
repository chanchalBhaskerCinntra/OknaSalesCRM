package com.cinntra.okana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.InvoiceActivity;
import com.cinntra.okana.activities.OpportunitiesActivity;
import com.cinntra.okana.activities.OrderActivity;
import com.cinntra.okana.activities.QuotationActivity;
import com.cinntra.okana.fragments.BlankFragment;
import com.cinntra.okana.globals.Globals;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    String aa[];
    int icons[];
    public GridViewAdapter (Context context, String aa[],int icons[])
    {
   this.context = context;
   this.aa      = aa;
   this.icons   = icons;
   inflter      = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
    return aa.length;
     }
    @Override
    public Object getItem(int i)
     {
   return null;
     }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int position, View itemView, ViewGroup viewGroup) {
        itemView = inflter.inflate(R.layout.latest_home_item, null); // inflate the layout
       TextView item_name;
       CardView card;
        ImageView icon;
        item_name = itemView.findViewById(R.id.item_name);
        card      = itemView.findViewById(R.id.card);
        icon      = itemView.findViewById(R.id.icon);



        item_name.setText(aa[position]);
        // holder.card.setCardBackgroundColor(Color.parseColor(colors[position]));

        icon.setImageResource(icons[position]);
        int width = Globals.getDeviceWidth(context)/2-30;
       // c.getLayoutParams().width =width;

       card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                if(position==0)
                 {
                context.startActivity(new Intent(context, OpportunitiesActivity.class));
                 }
                else if(position==1)
                  {
                context.startActivity(new Intent(context, QuotationActivity.class));
                  }
                else if(position==2)
                  {
                context.startActivity(new Intent(context, OrderActivity.class));
                   }
                else if(position==3)
                  {
                context.startActivity(new Intent(context, InvoiceActivity.class));
                   }
                else
                {
              fragment = new BlankFragment();
              FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
              transaction.replace(R.id.frame, fragment);
              transaction.addToBackStack(null);
              transaction.commit();
                }





            }
        });


        return itemView;
    }
}