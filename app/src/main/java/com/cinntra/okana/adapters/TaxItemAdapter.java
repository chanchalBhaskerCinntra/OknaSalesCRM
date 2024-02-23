package com.cinntra.okana.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.TaxItem;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TaxItemAdapter extends RecyclerView.Adapter<TaxItemAdapter.ViewHolder> {
    Context context;

    List<TaxItem> taxList;
    DocumentLines itemsObj;
    Dialog TaxListdialog;

    public TaxItemAdapter(Context context, List<TaxItem> taxList, DocumentLines itemsObj, Dialog TaxListdialog)
     {
   this.context   = context;
   this.taxList   = taxList;
   this.itemsObj  = itemsObj;
   this.TaxListdialog = TaxListdialog;
     }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(context).inflate(R.layout.tax_adapter_item,parent,false);
    return new ViewHolder(rootView);
      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
      {
   holder.tax.setText(taxList.get(position).getCode());
      }

    @Override
    public int getItemCount()
      {
    return taxList.size();
      }



    class ViewHolder extends RecyclerView.ViewHolder
       {
    private TextView tax;
    public ViewHolder(@NonNull View itemView) {
    super(itemView);
    tax    = itemView.findViewById(R.id.tax);
    itemView.setOnClickListener(new View.OnClickListener() {
     @Override
    public void onClick(View v) {

        itemsObj.setTaxCode(taxList.get(getAdapterPosition()).getCode());
//         Globals.SelectedItems.add(itemsObj);
         Globals.SelectedItems.add(postjson(itemsObj));

         Intent intent = new Intent();
         intent.putExtra(Globals.CustomerItemData, (Parcelable) itemsObj);
         ((AppCompatActivity)context).setResult(RESULT_OK, intent);
         ((AppCompatActivity)context).finish();

         if(TaxListdialog!=null)
         TaxListdialog.dismiss();

        }
    });

      }
    }

    private DocumentLines postjson(DocumentLines itemsObj) {
        DocumentLines dc = new DocumentLines();
        dc.setItemCode(itemsObj.getItemCode());
        dc.setQuantity(itemsObj.getQuantity());
        dc.setTaxCode(itemsObj.getTaxCode());//BED+VAT
        dc.setUnitPrice(itemsObj.getUnitPrice());
        dc.setItemDescription(itemsObj.getItemName());
        dc.setDiscountPercent(Float.valueOf("0.0"));
        return dc;
    }


}
