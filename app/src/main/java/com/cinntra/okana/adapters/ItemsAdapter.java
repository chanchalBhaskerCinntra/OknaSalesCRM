package com.cinntra.okana.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.TaxItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private String SelectedTaxSlab = "";
    private int currentItemPo = 0;
    Context context;
    List<DocumentLines> ItemsList;

    public ItemsAdapter(Context context, List<DocumentLines> ItemsList) {
        this.context = context;
        this.ItemsList = ItemsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DocumentLines obj = getItem(position);
        holder.customerName.setText(obj.getItemName());
        holder.cardNumber.setText(obj.getItemCode());
    }

    @Override
    public int getItemCount() {
        return ItemsList.size();
    }

    public DocumentLines getItem(int position) {
        return ItemsList.get(position);
    }


    List<DocumentLines> tempList = null;

    public void filter(String newText) {
        String charText = newText.toLowerCase(Locale.getDefault()).trim();
        ItemsList.clear();
        if (charText.length() == 0) {
            ItemsList.addAll(tempList);
        } else {
            for (DocumentLines st : tempList) {
                /*if(st.getOpportunityName()!=null&&!st.getOpportunityName().isEmpty()) {
                    if (st.getCustomerName().toLowerCase().trim().contains(charText)) {*/
                if (st.getItemCode() != null && !st.getItemCode().isEmpty() && st.getItemName() != null && !st.getItemName().isEmpty()) {
                    if (st.getItemCode().toLowerCase().trim().contains(charText) || st.getItemName().toLowerCase().trim().contains(charText)) {
                        ItemsList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName, cardNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            cardNumber = itemView.findViewById(R.id.cardNumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentItemPo = getAdapterPosition();

      /*   DocumentLines itemObj = ItemsList.get(currentItemPo);
         itemObj.setUnitPrice(ItemsList.get(currentItemPo).getUnitPrice());*/
                    setQuantity(context, ItemsList.get(currentItemPo));
          /*
          ItemsList.get(currentItemPo).setItemUnitPrice(ItemsList.get(currentItemPo).getItemPrices().get(CreateContact.PriceListNum-1).getPrice());
          ItemsList.get(currentItemPo).setItemTaxCode(SelectedTaxSlab);
           */
                }
            });
        }
    }

    /*********** Make Custom Views and Data *************/
    private void setQuantity(Context context, DocumentLines itemsObj) {
        EditText editText, discount_value, tax_value,edUnitPrice;
        Button button;
        Dialog dialog = new Dialog(context);
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View custom_dialog = layoutInflater.inflate(R.layout.quantity_alert, null);
        editText = custom_dialog.findViewById(R.id.editText);
        discount_value = custom_dialog.findViewById(R.id.discount_value);
        tax_value = custom_dialog.findViewById(R.id.tax_value);
        edUnitPrice = custom_dialog.findViewById(R.id.edUnitPrice);
        button = custom_dialog.findViewById(R.id.button);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        edUnitPrice.setText(String.valueOf(itemsObj.getUnitPrice()));
        tax_value.setText(itemsObj.getTaxCode());
        tax_value.setEnabled(false);
        tax_value.setClickable(false);

        //editText.getText();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean alertStatus = true;
                if (!editText.getText().toString().isEmpty() && Integer.parseInt(editText.getText().toString()) > 0) {
                    if (!discount_value.getText().toString().trim().isEmpty()) {
                        if (Globals.SelectedItems.size() > 0) {
                            for (DocumentLines item : Globals.SelectedItems) {
                                if (item.getItemCode().equals(itemsObj.getItemCode())) {
                                    int total_qty = Integer.parseInt(editText.getText().toString()) + (int) Double.parseDouble(item.getQuantity());
                                    Globals.SelectedItems.get(Globals.SelectedItems.indexOf(item)).setQuantity("" + total_qty);
                                    alertStatus = false;
                                    dialog.dismiss();
                                    ((AppCompatActivity) context).finish();
                                    break;
                                }
                            }
                        }
                        if (Globals.SelectedItems.size() > 0 && alertStatus) {
                            itemsObj.setQuantity(editText.getText().toString());
                            itemsObj.setTax(tax_value.getText().toString());
                            itemsObj.setUnitPrice(Float.parseFloat(String.valueOf(edUnitPrice.getText())));
                            itemsObj.setDiscountPercent(Float.parseFloat(discount_value.getText().toString()));
                            dialog.dismiss();
                            Globals.SelectedItems.add(postjson(itemsObj));
                            Intent intent = new Intent();
                            intent.putExtra(Globals.CustomerItemData, (Parcelable) itemsObj);
                            ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                            ((AppCompatActivity) context).finish();

                        } else if (Globals.SelectedItems.size() == 0) {
                            itemsObj.setQuantity(editText.getText().toString());
                            itemsObj.setTax(tax_value.getText().toString());
                            itemsObj.setDiscountPercent(Float.parseFloat(discount_value.getText().toString()));
                            itemsObj.setUnitPrice(Float.parseFloat(String.valueOf(edUnitPrice.getText())));

                            dialog.dismiss();
                            Globals.SelectedItems.add(postjson(itemsObj));
                            Intent intent = new Intent();
                            intent.putExtra(Globals.CustomerItemData, (Parcelable) itemsObj);
                            ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                            ((AppCompatActivity) context).finish();

//                     setTaxes(context, itemsObj);
                        }
                    } else {
                        Toast.makeText(context, "Enter Discount", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(context, "Enter valid Quanity", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    Dialog TaxListdialog;
    List<TaxItem> taxSlabList = new ArrayList<>();


    private DocumentLines postjson(DocumentLines itemsObj) {
        DocumentLines dc = new DocumentLines();
        dc.setCostingCode2(itemsObj.getCostingCode2());
        dc.setItemCode(itemsObj.getItemCode());
        dc.setQuantity(itemsObj.getQuantity());
        dc.setTaxCode(itemsObj.getTaxCode());//BED+VAT
        dc.setUnitPrice(itemsObj.getUnitPrice());
        dc.setItemDescription(itemsObj.getItemName());
        dc.setDiscountPercent(itemsObj.getDiscountPercent());
        dc.setTax(itemsObj.getTax());
        dc.setUomNo("NOS");
        dc.setCostingCode2(itemsObj.getCostingCode2());
        dc.setU_FGITEM(itemsObj.getU_FGITEM());
        dc.setProjectCode(itemsObj.getProjectCode());
        dc.setFreeText(itemsObj.getFreeText());
        return dc;
    }


    private void setTaxes(Context context, DocumentLines itemsObj) {
        RelativeLayout backPress;
        TextView head_title;
        RecyclerView recyclerview;
        ProgressBar loader;

        TaxListdialog = new Dialog(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View custom_dialog = layoutInflater.inflate(R.layout.taxes_alert, null);
        recyclerview = custom_dialog.findViewById(R.id.recyclerview);
        backPress = custom_dialog.findViewById(R.id.back_press);
        head_title = custom_dialog.findViewById(R.id.head_title);
        loader = custom_dialog.findViewById(R.id.loader);
        head_title.setText(context.getString(R.string.select_tax));
        TaxListdialog.setContentView(custom_dialog);
        TaxListdialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        TaxListdialog.show();

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxListdialog.dismiss();
            }
        });
        /************** Call Api **************/

//    ItemViewModel model = ViewModelProviders.of((AppCompatActivity)context).get(ItemViewModel.class);
//    model.getTaxList(loader).observe((AppCompatActivity)context, new Observer<List<TaxItem>>() {
//    @Override
//    public void onChanged(@Nullable List<TaxItem> itemsList) {
//        taxSlabList.clear();
//        taxSlabList.addAll(itemsList);
//      TaxItemAdapter adapter = new TaxItemAdapter(context, taxSlabList,itemsObj,TaxListdialog);
//      recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
//      recyclerview.setAdapter(adapter);
//
//        }
//    });

        TaxItemAdapter adapter = new TaxItemAdapter(context, MainActivity.taxItemDataFromLocal, itemsObj, TaxListdialog);
        recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerview.setAdapter(adapter);

    }

}
