package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.cinntra.okana.databinding.SaleOrderRequestDocumentLineLayoutBinding;
import com.cinntra.okana.model.DocumentLines;

import java.util.ArrayList;

public class SaleOrderLinesAdapter extends RecyclerView.Adapter<SaleOrderLinesAdapter.ViewHolder> {
    Context context;
    private ArrayList<DocumentLines> arrList;

    public SaleOrderLinesAdapter(Context context, ArrayList<DocumentLines> documentLines) {
        this.context = context;
        this.arrList = documentLines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SaleOrderRequestDocumentLineLayoutBinding binding = SaleOrderRequestDocumentLineLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentLines data = arrList.get(position);

        holder.binding.tvItemName.setText(String.valueOf(data.getItemDescription()));
        holder.binding.tvUnitPrice.setText(String.valueOf(data.getUnitPrice()));
        holder.binding.tvItemCode.setText(data.getItemCode());
        holder.binding.tvTax.setText(data.getTax());
        holder.binding.tvQty.setText(data.getQuantity());
        holder.binding.tvDiscount.setText(String.valueOf(data.getDiscountPercent()) + " %");

    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SaleOrderRequestDocumentLineLayoutBinding binding;

        public ViewHolder(@NonNull SaleOrderRequestDocumentLineLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
