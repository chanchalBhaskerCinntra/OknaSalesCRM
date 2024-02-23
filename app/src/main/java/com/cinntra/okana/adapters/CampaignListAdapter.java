package com.cinntra.okana.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.CampaignDetailFragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.CampaignListResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder> {
    Context context;
    List<CampaignListResponse> leadValueList;
    public CampaignListAdapter(Context c, List<CampaignListResponse> leadValueList) {
        this.context =c;
        this.leadValueList = leadValueList;
        this.tempList=new ArrayList<>();
        tempList.addAll(leadValueList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.campaign_adapter_screen,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CampaignListResponse lv = leadValueList.get(position);
        holder.customerName.setText(lv.getCampaignName());
        holder.date.setText(lv.getCreateDate());

        if(lv.getStatus() == 0){
            holder.cardNumber.setText(": InActive");
            holder.cardNumber.setTextColor(ContextCompat.getColor(context, R.color.red));
        }else{
            holder.cardNumber.setText(": Active");
            holder.cardNumber.setTextColor(ContextCompat.getColor(context, R.color.green));
        }



        // holder.amount.setText("Rs:" + lv.getTurnover());

    }








    @Override
    public int getItemCount() {
        return leadValueList.size();
    }

    List<CampaignListResponse>  tempList =null ;
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        leadValueList.clear();
        if (charText.length() == 0) {
            leadValueList.addAll(tempList);
        } else {
            for (CampaignListResponse st : tempList) {
                if(st.getCampaignName()!=null&&!st.getCampaignName().isEmpty()) {
                    if (st.getCampaignName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        leadValueList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName,cardNumber,date,assigned;
        LinearLayout assigned_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.item_title);
            cardNumber   = itemView.findViewById(R.id.price);
            date   = itemView.findViewById(R.id.item_date);
            assigned   = itemView.findViewById(R.id.assigned);
            assigned_view   = itemView.findViewById(R.id.assigned_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable(Globals.CampaignData, leadValueList.get(getAdapterPosition()));
                    CampaignDetailFragment fragment = new CampaignDetailFragment();
                    fragment.setArguments(b);
                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.customer_lead, fragment);
                    transaction.addToBackStack("Back");
                    transaction.commit();
                }
            });


        }

    }
}