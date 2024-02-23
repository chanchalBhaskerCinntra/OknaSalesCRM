package com.cinntra.okana.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;

public class InvoiceTaxAdapter extends RecyclerView.Adapter<InvoiceTaxAdapter.ViewHolder> {
    Context mContext;
    String[] taxrate;
    String[] taxsum ;
    String[] cgst ;
    String[] sgst ;
    String[] total ;



    public InvoiceTaxAdapter(Context context, String[] sno1, String[] itemcode1, String[] description1, String[] hsncode1, String[] qty1) {
        this.mContext = context;
        this.taxrate = sno1;
        this.taxsum = itemcode1;
        this.cgst = description1;
        this.sgst = hsncode1;
        this.total = qty1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mview= LayoutInflater.from(parent.getContext()).inflate(R.layout.tax, parent, false);
        return new ViewHolder(mview);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.taxrate.setText(""+taxrate[position]);
        holder.taxsum.setText(taxsum[position]);
        holder.cgst.setText(cgst[position]);
        holder.sgst.setText(sgst[position]);
        holder.totaltax.setText(""+total[position]);

        if(position==0){
            holder.mainframe.setBackground(mContext.getDrawable(R.drawable.table_background));
            holder.taxrate.setTypeface(null, Typeface.BOLD);
            holder.taxsum.setTypeface(null, Typeface.BOLD);
            holder.cgst.setTypeface(null, Typeface.BOLD);
            holder.sgst.setTypeface(null, Typeface.BOLD);
            holder.totaltax.setTypeface(null, Typeface.BOLD);
        }else{
        }
    }

    @Override
    public int getItemCount() {
        return taxrate.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taxrate;
        TextView taxsum;
        TextView cgst;
        TextView sgst;
        TextView totaltax;

        LinearLayout mainframe;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.taxrate = (TextView) view.findViewById(R.id.taxrate);
            this.taxsum = (TextView) view.findViewById(R.id.taxsum);
            this.cgst= (TextView) view.findViewById(R.id.cgst);
            this.sgst= (TextView) view.findViewById(R.id.sgst);
            this.totaltax= (TextView) view.findViewById(R.id.tax);
            this.mainframe = (LinearLayout) view.findViewById(R.id.mainframe);
        }

    }

}
