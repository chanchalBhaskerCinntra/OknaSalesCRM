package com.cinntra.okana.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.MapsActivity;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllResponse;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.ViewHolder> {
    Context context;
    List<BusinessPartnerAllResponse.Datum> customerList;

    //    List<BusinessPartnerData> customerList;
    public CustomersAdapter(Context context, List<BusinessPartnerAllResponse.Datum> customerList) {
        this.context = context;
        this.customerList = customerList;
        this.tempList = new ArrayList<BusinessPartnerAllResponse.Datum>();
        this.tempList.addAll(customerList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.customers_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BusinessPartnerAllResponse.Datum obj = getItem(position);
        holder.customerName.setText(obj.getCardName());
        holder.cardNumber.setText(obj.getCardCode());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public BusinessPartnerAllResponse.Datum getItem(int position) {
        return customerList.get(position);
    }

    public void StateFilter(String state) {
        customerList.clear();
        if (state.length() == 0) {
            customerList.addAll(tempList);
        } else {
            for (BusinessPartnerAllResponse.Datum bde : tempList) {
                if (bde.getBPAddresses().size() > 0) {
                    if (state.trim().equalsIgnoreCase(bde.getBPAddresses().get(0).getU_STATE())) {
                        customerList.add(bde);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void Typefilter(String name) {

        customerList.clear();
        if (name.length() == 0) {
            customerList.addAll(tempList);
        } else {
            for (BusinessPartnerAllResponse.Datum bde : tempList) {
                if (name.trim().equalsIgnoreCase(bde.getuType().get(0).getType())) {
                    customerList.add(bde);
                }
            }
        }
        notifyDataSetChanged();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, cardNumber;
        ImageView map_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            cardNumber = itemView.findViewById(R.id.cardNumber);
            map_icon = itemView.findViewById(R.id.map_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(Globals.CustomerItemData, customerList.get(getAdapterPosition()));
                    ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                    ((AppCompatActivity) context).finish();

                }
            });


            map_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MapsActivity.class);
                    context.startActivity(i);

                }
            });

        }
    }


    List<BusinessPartnerAllResponse.Datum> tempList = null;

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        customerList.clear();
        if (charText.length() == 0) {
            customerList.addAll(tempList);
        } else {
            for (BusinessPartnerAllResponse.Datum st : tempList) {
                if (st.getCardName() != null && !st.getCardName().isEmpty() || st.getCardCode() != null && !st.getCardCode().isEmpty()) {
                    if (st.getCardName().toLowerCase(Locale.getDefault()).contains(charText) || st.getCardCode().toLowerCase(Locale.getDefault()).contains(charText)) {
                        customerList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }


 /*   public void AllData() {
        customerList.addAll(tempList);
        notifyDataSetChanged();
    }
*/
    public void AllData(List<BusinessPartnerAllResponse.Datum> tmp) {
        customerList.clear();
        customerList.addAll(tmp);
        notifyDataSetChanged();
    }

    public void Customerfilter() {
        customerList.addAll(tempList);
        Collections.sort(customerList, new Comparator<BusinessPartnerAllResponse.Datum>() {
            @Override
            public int compare(BusinessPartnerAllResponse.Datum o1, BusinessPartnerAllResponse.Datum o2) {
                return o1.getCardName().compareTo(o2.getCardName());
            }
        });

        notifyDataSetChanged();

    }
}
