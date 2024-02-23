package com.cinntra.okana.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.PaymentDetailFragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.ExpenseDataModel;
import com.cinntra.okana.model.PaymentDetailsModel;
import com.cinntra.okana.model.PaymentRespnse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.ViewHolder> {
    Context context;
    List<PaymentDetailsModel> leadValueList;
    public PaymentDetailsAdapter(Context c, List<PaymentDetailsModel> leadValueList) {
        this.context =c;
        this.leadValueList = leadValueList;
        this.tempList=new ArrayList<>();
        tempList.addAll(leadValueList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.expense_adapter_screen,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentDetailsModel lv = leadValueList.get(position);
        holder.customerName.setText("Invoice No. :"+lv.getInvoiceNo());
        holder.date.setText(lv.getCreateDate());

        holder.cardNumber.setText("₹ "+lv.getReceivedAmount());

        holder.assigned_view.setVisibility(View.VISIBLE);
        holder.assigned.setText(": " +lv.getTransactMod());

        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfirmDialog(lv.getId());
            }
        });
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
        List<Integer> lis = new ArrayList<>();
        lis.add(id);
        HashMap<String,List<Integer>>  hd = new HashMap<>();
        hd.put("id",lis);
        Call<PaymentRespnse> call = NewApiClient.getInstance().getApiService().deletepayment(hd);
        call.enqueue(new Callback<PaymentRespnse>(){
            @Override
            public void onResponse(Call<PaymentRespnse> call, Response<PaymentRespnse> response) {

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
                    PaymentRespnse mError = new PaymentRespnse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, PaymentRespnse.class);
                        Toast.makeText(context, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }}
            @Override
            public void onFailure(Call<PaymentRespnse> call, Throwable t) {

                Toasty.error(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return leadValueList.size();
    }

    List<PaymentDetailsModel>  tempList =null ;

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        leadValueList.clear();
        if (charText.length() == 0) {
            leadValueList.addAll(tempList);
        } else {
            for (PaymentDetailsModel st : tempList) {
                if (st.getInvoiceNo() != null && !st.getInvoiceNo().isEmpty()) {
                    if (st.getInvoiceNo().toLowerCase(Locale.getDefault()).contains(charText)) {
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
        LinearLayoutCompat option;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.item_title);
            cardNumber   = itemView.findViewById(R.id.price);
            date   = itemView.findViewById(R.id.item_date);
            assigned   = itemView.findViewById(R.id.assigned);
            assigned_view   = itemView.findViewById(R.id.assigned_view);
            option   = itemView.findViewById(R.id.option);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable(Globals.paymentDetailsData, leadValueList.get(getAdapterPosition()));
                    PaymentDetailFragment fragment = new PaymentDetailFragment();
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