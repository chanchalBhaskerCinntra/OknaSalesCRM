package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.ExpenseResponse;
import com.cinntra.okana.model.MapData;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationListingAdapter extends RecyclerView.Adapter<LocationListingAdapter.ViewHolder> {
    Context context;
    List<MapData> leadValueList;
    public LocationListingAdapter(Context c, List<MapData> leadValueList) {
        this.context =c;
        this.leadValueList = leadValueList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.location_listing_adapter,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MapData lv = leadValueList.get(position);
        holder.customerName.setText(lv.getAddress());
        holder.date.setText( lv.getUpdateDate());

        holder.cardNumber.setText(lv.getUpdateTime());

        holder.assigned_view.setVisibility(View.GONE);
        holder.assignedto.setText("Remarks :");
        holder.assigned.setText(lv.getRemark());
        holder.option.setVisibility(View.GONE);
       /* if(lv.getType().equalsIgnoreCase("Start")){
            holder.follow_up.setBackgroundResource(R.drawable.background_green_rounded);

            holder.follow_up.setText("Check In");
        }else{
            holder.follow_up.setBackgroundResource(R.drawable.background_red_rounded);
            holder.follow_up.setText("Check Out");
        }*/

        if(lv.getType().equalsIgnoreCase("Stop")){
            holder.follow_up.setBackgroundResource(R.drawable.background_red_rounded);
            holder.follow_up.setText("Check Out");
        }else{
            holder.follow_up.setBackgroundResource(R.drawable.background_green_rounded);
            holder.follow_up.setText("Check In");
        }

     /*   holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDialog(lv.getId());
            }
        });*/
        // holder.amount.setText("Rs:" + lv.getTurnover());

    }



    private void openConfirmDialog(Integer id) {

        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You Want to Delete!")
                .setConfirmText("Yes,Delete!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        deleteapi(id);

                    }
                })

                .show();

    }

    private void deleteapi(Integer id) {
        List<String> ls = new ArrayList<>();
        ls.add(id.toString());
        HashMap<String,List<String>>  hd = new HashMap<>();
        hd.put("id",ls);
        Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().deleteexpense(hd);
        call.enqueue(new Callback<ExpenseResponse>(){
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                if(response.code()==200)
                {
                    if(response.body().getMessage().equalsIgnoreCase("successful")){

                        Toasty.success(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toasty.warning(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    ExpenseResponse mError = new ExpenseResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, ExpenseResponse.class);
                        Toast.makeText(context, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }}
            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {

                Toasty.error(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return leadValueList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName,cardNumber,date,assigned,assignedto;
        TextView follow_up;
        LinearLayout assigned_view;
        LinearLayoutCompat option;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.item_title);
            cardNumber   = itemView.findViewById(R.id.price);
            date   = itemView.findViewById(R.id.item_date);
            assigned   = itemView.findViewById(R.id.assigned);
            assignedto   = itemView.findViewById(R.id.assignedto);
            assigned_view   = itemView.findViewById(R.id.assigned_view);
            option   = itemView.findViewById(R.id.option);
            follow_up   = itemView.findViewById(R.id.follow_up);


/*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable(Globals.ExpenseData, leadValueList.get(getAdapterPosition()));
                    ExpenseDetailFragment fragment = new ExpenseDetailFragment();
                    fragment.setArguments(b);
                    FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.customer_lead, fragment);
                    transaction.addToBackStack("Back");
                    transaction.commit();
                }
            });
*/


        }

    }
}