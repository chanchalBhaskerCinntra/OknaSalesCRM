package com.cinntra.okana.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.fragments.Opportunity_Update_Fragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.OpportunityModels.OpportunityItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.UpdateFavourites;
import com.cinntra.okana.webservices.APIsClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lost_Opportunities_Adapter extends RecyclerView.Adapter<Lost_Opportunities_Adapter.ViewHolder> {
    Context context;
    List<OpportunityItem> itemsList;
    boolean click = true;
    public Lost_Opportunities_Adapter(Context context, List<OpportunityItem> itemsList)
        {
   this.context    = context;
   this.itemsList  = itemsList;
   this.tempList   = new ArrayList<OpportunityItem>();
   this.tempList.addAll(itemsList);
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.opportunity_new_screen,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OpportunityItem obj = getItem(position);
        holder.item_title.setText(obj.getOpportunityName());
        holder.item_date.setText(obj.getPredictedClosingDate());
        holder.price.setText(" : "+obj.getMaxLocalTotal());
        holder.name.setText(obj.getCardCode());
        if(obj.getU_FAV().equalsIgnoreCase("Yes")){
            holder.star.setBackgroundResource(R.drawable.star_yellow);

        }else{
            holder.star.setBackgroundResource(R.drawable.ic_star_3);

        }

        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(click){
                    holder.star.setBackgroundResource(R.drawable.star_yellow);
                    click = false;
                }else{
                    holder.star.setBackgroundResource(R.drawable.ic_star_3);
                    click = true;
                }



            }

        });
//   holder.price_percent.setText(Globals.convertDecemal(obj.getClosingPercentage()));


    }
    public OpportunityItem getItem(int po)
      {
    return  itemsList.get(po);
      }

    @Override
    public int getItemCount() {
  return itemsList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title,price,item_date,quanity,name;
        ImageView star;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_date  = itemView.findViewById(R.id.item_date);
            quanity    = itemView.findViewById(R.id.quanity);
            price      = itemView.findViewById(R.id.price);
            name       = itemView.findViewById(R.id.name);
            star       = itemView.findViewById(R.id.star);



            itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {

          Bundle b = new Bundle();
          b.putSerializable(Globals.OpportunityItem,itemsList.get(getAdapterPosition()));
          //Opportunity_Detail_Fragment fragment = new Opportunity_Detail_Fragment();
          Opportunity_Update_Fragment fragment = new Opportunity_Update_Fragment();
          fragment.setArguments(b);
          FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
          transaction.replace(R.id.quatoes_main_container, fragment);
          transaction.addToBackStack("Back");
          transaction.commit();

                 }
             });



        }
    }


    List<OpportunityItem> tempList =null ;
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        itemsList.clear();
        if (charText.length() == 0) {
            itemsList.addAll(tempList);
        } else {
            for (OpportunityItem st : tempList) {
                /*if(st.getOpportunityName()!=null&&!st.getOpportunityName().isEmpty()) {
                    if (st.getCustomerName().toLowerCase().trim().contains(charText)) {*/
                if(st.getCardCode()!=null&&!st.getCardCode().isEmpty()) {
                    if (st.getCardCode().toLowerCase().trim().contains(charText)||st.getOpportunityName().toLowerCase().trim().contains(charText)) {
                        itemsList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }
    public void AllData()
    {
        itemsList.addAll(tempList);
        notifyDataSetChanged();
    }
    public void Typefilter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        itemsList.clear();
        if (charText.length() == 0) {
            itemsList.addAll(tempList);
        } else {
            for (OpportunityItem st : tempList) {

                if(st.getU_TYPE()!=null&&!st.getU_TYPE().isEmpty()) {
                    if (st.getU_TYPE().toLowerCase().trim().equalsIgnoreCase(charText)) {
                        itemsList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }
    private void updateOpportunity(UpdateFavourites in, String OppID)
    {
        Call<QuotationResponse> call = APIsClient.getInstance().getApiService().updateFavorite(OppID,in);
        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                if(response.code()==201||response.code()==204)
                {
                    Globals.SelectedItems.clear();
                    Toast.makeText(context, "Posted Successfully.", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(context, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<QuotationResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void Favfilter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        itemsList.clear();
        if (charText.length() == 0) {
            itemsList.addAll(tempList);
        } else {
            for (OpportunityItem st : tempList) {

                if(st.getU_FAV()!=null&&!st.getU_FAV().isEmpty()) {
                    if (st.getU_FAV().toLowerCase().trim().equalsIgnoreCase(charText)) {
                        itemsList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ValidDate(LocalDate afterdate, LocalDate dateObj)
      {
        itemsList.clear();
        for (OpportunityItem st : tempList) {

            if(st.getPredictedClosingDate()!=null&&!st.getPredictedClosingDate().isEmpty()) {
                String sDate1 = st.getPredictedClosingDate();
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
