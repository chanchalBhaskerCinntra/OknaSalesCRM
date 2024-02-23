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
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.model.UpdateQuotationModel;

import java.util.ArrayList;

public class InvoiceItemAdapter extends RecyclerView.Adapter<InvoiceItemAdapter.ViewHolder> {
    Context mContext;
    private ArrayList<DocumentLines> arrList;
    UpdateQuotationModel addQuotationObj;
    QuotationItem quotationItem;
    public InvoiceItemAdapter(Context context, ArrayList<DocumentLines> arrayList) {
        this.mContext = context;
        this.arrList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View mview= LayoutInflater.from(parent.getContext()).inflate(R.layout.record, parent, false);
        return new ViewHolder(mview);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentLines obj = getItem(position);
        //int totalqtyprice = Integer.valueOf(obj.getQuantity()) * Integer.valueOf(obj.getItemUnitPrice()) ;

        holder.Sno.setText(""+position);
        holder.Item.setText(obj.getItemCode());
        holder.Desc.setText(obj.getItemName());
//        holder.HSN.setText(hsncode[position].trim());
        holder.Qty.setText(obj.getQuantity());
        holder.Price.setText(String.valueOf(obj.getUnitPrice()));
        holder.Tax.setText(obj.getTaxCode());
//        holder.total.setText("" + totalqtyprice);
        if(position==0){
            holder.Sno.setText("S. No");
            holder.total.setText("Total");
            holder.mainFrame.setBackground(mContext.getDrawable(R.drawable.table_background));
            holder.Sno.setTypeface(null,Typeface.BOLD);
            holder.Item.setTypeface(null,Typeface.BOLD);
            holder.Desc.setTypeface(null,Typeface.BOLD);
            holder.HSN.setTypeface(null,Typeface.BOLD);
            holder.Qty.setTypeface(null,Typeface.BOLD);
            holder.Price.setTypeface(null,Typeface.BOLD);
            holder.Tax.setTypeface(null,Typeface.BOLD);
            holder.uom.setTypeface(null,Typeface.BOLD);
            holder.cgst.setTypeface(null,Typeface.BOLD);
            holder.cgstamt.setTypeface(null,Typeface.BOLD);
            holder.sgst.setTypeface(null,Typeface.BOLD);
            holder.sgstamt.setTypeface(null,Typeface.BOLD);
            holder.igst.setTypeface(null,Typeface.BOLD);
            holder.igstamt.setTypeface(null,Typeface.BOLD);
            holder.total.setTypeface(null,Typeface.BOLD);
        }
        else {
            float totalqtyprice = caltotal(Float.valueOf(obj.getQuantity()).floatValue(), Float.valueOf(obj.getUnitPrice()).floatValue());
            holder.total.setText(String.valueOf(totalqtyprice));
        }
    }
    private float caltotal(float quantity,float price ){
        return (quantity * price);
    }

    public DocumentLines getItem(int po)
    {
        return  arrList.get(po);
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Sno;
        TextView Item;
        TextView Desc;
        TextView HSN;
        TextView Qty;
        TextView Price;
        TextView Tax;
        TextView uom;
        TextView cgst;
        TextView cgstamt;
        TextView sgst;
        TextView sgstamt;
        TextView igst;
        TextView igstamt;
        TextView total;

        LinearLayout mainFrame;
        public ViewHolder(@NonNull View view) {
            super(view);
            this.Sno = (TextView) view.findViewById(R.id.sno_);
            this.Item = (TextView) view.findViewById(R.id.itemcode);
            this.Desc= (TextView) view.findViewById(R.id.description);
            this.HSN= (TextView) view.findViewById(R.id.hsncode);
            this.Qty= (TextView) view.findViewById(R.id.qty);
            this.Price= (TextView) view.findViewById(R.id.price);
            this.Tax= (TextView) view.findViewById(R.id.tax);
            this.uom= (TextView) view.findViewById(R.id.uom);
            this.cgst= (TextView) view.findViewById(R.id.cgst);
            this.cgstamt= (TextView) view.findViewById(R.id.cgstamt);
            this.sgst= (TextView) view.findViewById(R.id.sgst);
            this.sgstamt= (TextView) view.findViewById(R.id.sgstamt);
            this.igst= (TextView) view.findViewById(R.id.igst);
            this.igstamt= (TextView) view.findViewById(R.id.igstamt);
            this.total= (TextView) view.findViewById(R.id.total);
            this.mainFrame =(LinearLayout) view.findViewById(R.id.mainframe);
        }

    }

}