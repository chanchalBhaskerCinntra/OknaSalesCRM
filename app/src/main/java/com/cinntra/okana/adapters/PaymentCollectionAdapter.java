package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.PaymentCollectionDetails;


public class PaymentCollectionAdapter extends RecyclerView.Adapter<PaymentCollectionAdapter.ViewHolder> {
    Context context;

    public PaymentCollectionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.payment_collection,parent,false);
        return new  ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    if(position>2){
        holder.qt_status.setText("Pending");
        holder.qt_status.setBackground(ContextCompat.getDrawable(context,R.drawable.saffron_rounded));
    }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView invoice_no,due_amount,qt_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            invoice_no =itemView.findViewById(R.id.invoice_no);
            due_amount =itemView.findViewById(R.id.due_amount);
            qt_status =itemView.findViewById(R.id.qt_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentCollectionDetails fragment = new PaymentCollectionDetails();
                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.temp, fragment).addToBackStack("");
                    fragmentTransaction.commit();
                }
            });

        }
    }
}
