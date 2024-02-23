package com.cinntra.okana.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.WebViewToPdf;
import com.cinntra.okana.databinding.OrderNewScreenBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.QuotationItem;

import java.util.ArrayList;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private ArrayList<QuotationItem> itemsList;
    Activity activity;
    ArrayList<QuotationItem> tempList ;

    public DemoAdapter(Activity context, ArrayList<QuotationItem> dataList) {
        this.itemsList = dataList;
        this.activity = context;
        this.tempList = new ArrayList<QuotationItem>();
    }


    @NonNull
    @Override
    public DemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderNewScreenBinding binding = OrderNewScreenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoAdapter.ViewHolder holder, int position) {
        QuotationItem obj = itemsList.get(position);
        holder.binding.title.setText(obj.getCardName());
        holder.binding.docDate.setText(obj.getDocDueDate());
        //    holder.amount.setText(Globals.getAmmount(obj.getDocCurrency(),obj.getDocTotal()));
        //   holder.status.setText(Globals.viewStatus(obj.getDocumentStatus()));

        if (Globals.viewStatus(obj.getDocumentStatus()) == "Open") {
            holder.binding.status.setText("Open");
            holder.binding.status.setBackgroundResource(R.drawable.openroundedgreen);
        } else {
            holder.binding.status.setText("Closed");
            holder.binding.status.setBackgroundResource(R.drawable.saffron_rounded);
        }
        holder.binding.previewFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, WebViewToPdf.class);
                i.putExtra("PDfFrom", "Order");
                i.putExtra("PdfID", obj.getId());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final OrderNewScreenBinding binding;

        public ViewHolder(OrderNewScreenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
