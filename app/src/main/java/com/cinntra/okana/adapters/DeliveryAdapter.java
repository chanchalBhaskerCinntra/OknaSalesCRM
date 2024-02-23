package com.cinntra.okana.adapters;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.model.QuotationStringItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    Context context;
    ArrayList<QuotationStringItem>  itemsList;
    int itemnumber;
    String DelievryType;

    public DeliveryAdapter(Context context,  ArrayList<QuotationStringItem>  itemsList,int num,String DelievryType)
        {
      this.context   = context;
      this.itemsList = itemsList;
      this.itemnumber= num;
      this.DelievryType= DelievryType;
            this.tempList  = new ArrayList<QuotationStringItem>();
            this.tempList.addAll(itemsList);
       }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.delievery_new_screen,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {


            QuotationStringItem obj = getItem(position);

      holder.doc_date.setText(obj.getDocDate());
      holder.title.setText(obj.getCardName());
      holder.order_id_value.setText(obj.getDocEntry());
      holder.expecteddate.setText(obj.getDocDueDate());
      holder.shipto.setText(obj.getAddressExtension().getShipToStreet());
      //holder.amount.setText(Globals.getAmmount(obj.getCurrency(),obj.getDocumentTotal()));

/*      if(!obj.getDocStatus().isEmpty()||obj.getDocStatus()!=null)
      holder.status.setText(Globals.getStatus(obj.getDocStatus()));*/


        }

    @Override
    public int getItemCount()
      {
    return itemsList.size();
      }
    public QuotationStringItem getItem(int position)
        {
    return  itemsList.get(position);
        }



    class ViewHolder extends RecyclerView.ViewHolder {
     TextView title,doc_date,expecteddate,shipto,order_id_value;

     public ViewHolder(@NonNull View itemView) {
     super(itemView);
       title    = itemView.findViewById(R.id.title);
       doc_date = itemView.findViewById(R.id.doc_date);
         expecteddate   = itemView.findViewById(R.id.amount);
         shipto   = itemView.findViewById(R.id.status);
         order_id_value   = itemView.findViewById(R.id.order_id_value);
       itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              /* DelieveryDetails fragment = new DelieveryDetails();
               FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.temp, fragment).addToBackStack("");
               fragmentTransaction.commit();*/
           }
       });



        }
    }

    List<QuotationStringItem> tempList =null ;
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        itemsList.clear();
        if (charText.length() == 0) {
            itemsList.addAll(tempList);
        } else {
            for (QuotationStringItem st : tempList) {
                if(st.getCardName()!=null&&!st.getCardName().trim().isEmpty()) {
                    if (st.getCardName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        itemsList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }
    public void AllData()
    {   itemsList.clear();
        itemsList.addAll(tempList);
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ValidDate(LocalDate afterdate, LocalDate dateObj) {
        itemsList.clear();
        for (QuotationStringItem st : tempList) {

            if(st.getDocDate()!=null&&!st.getDocDate().trim().isEmpty()) {
                String sDate1 = st.getDocDate();
                LocalDate date1=LocalDate.parse(sDate1);
                if(date1.isBefore(afterdate) && date1.isAfter(dateObj) ){
                    Toast.makeText(context,"Updated",Toast.LENGTH_LONG).show();
                    itemsList.add(st);
                }
            }
        }
        notifyDataSetChanged();
    }
}
