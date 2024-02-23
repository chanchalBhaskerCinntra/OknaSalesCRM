package com.cinntra.okana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.InvoiceActivity;
import com.cinntra.okana.activities.OpportunitiesActivity;
import com.cinntra.okana.activities.QuotationActivity;
import com.cinntra.okana.activities.OrderActivity;
import com.cinntra.okana.fragments.BlankFragment;
import com.cinntra.okana.globals.Globals;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    String aa[];
    int icons[];
    public HomeAdapter (Context context, String aa[],int icons[])
       {
    this.context = context;
    this.aa      = aa;
    this.icons   = icons;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View rootView = LayoutInflater.from(context).inflate(R.layout.home_item,parent,false);
     return new ViewHolder(rootView);
       }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.item_name.setText(aa[position]);
       // holder.card.setCardBackgroundColor(Color.parseColor(colors[position]));

        holder.icon.setImageResource(icons[position]);
        int width = Globals.getDeviceWidth(context)/2-30;
        holder.layout.getLayoutParams().width =width;

      holder.card.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
       Fragment fragment = null;
       if(position==0) {
         context.startActivity(new Intent(context, QuotationActivity.class));
          }
       else if(position==1)
         {
       context.startActivity(new Intent(context, OpportunitiesActivity.class));
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
    }

    @Override
    public int getItemCount()
      {
    return aa.length;
     }



    class ViewHolder extends RecyclerView.ViewHolder {
          private TextView item_name;
          private CardView card;
          private ImageView icon;
          private LinearLayout layout;


        public ViewHolder(@NonNull View itemView)
          {
       super(itemView);
       item_name = itemView.findViewById(R.id.item_name);
       card      = itemView.findViewById(R.id.card);
       icon      = itemView.findViewById(R.id.icon);
       layout    = itemView.findViewById(R.id.layout);
          }
      }
}
