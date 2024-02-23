package com.cinntra.okana.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOrderAct;
import com.cinntra.okana.activities.WebViewToPdf;
import com.cinntra.okana.fragments.QuotationDetailFragment;
import com.cinntra.okana.fragments.Quotation_Open_Fragment;
import com.cinntra.okana.fragments.Quotation_Update_Fragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Quotation_Adapter extends RecyclerView.Adapter<Quotation_Adapter.ViewHolder> {


    private AdapterCallback callback;

    public interface AdapterCallback {
        void onItemClicked(String id, String remark, String status, Dialog dialog);
    }

    String quotationId = "";
    Context context;
    List<QuotationItem> arrayList;
    FragmentRefresher fragmentRefresher;

    public Quotation_Adapter(Quotation_Open_Fragment quotation_open_fragment, Context context, List<QuotationItem> arrayList, AdapterCallback callback) {
        this.context = context;
        this.arrayList = arrayList;
        this.tempList = new ArrayList<QuotationItem>();
        this.tempList.addAll(arrayList);
        this.fragmentRefresher = (FragmentRefresher) quotation_open_fragment;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.quotation_new_screen, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuotationItem obj = getItem(position);
        holder.title.setText(obj.getU_QUOTNM());
        holder.name.setText("Quo - " +obj.getId());
        holder.doc_date.setText(obj.getQuoteNo());

        if (!obj.getTaxDate().isEmpty()){
            holder.amount.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(obj.getTaxDate()));
        }

        //holder.amount.setText(Globals.getAmmount(obj.getDocCurrency(),obj.getDocTotal()));


        if (Globals.viewStatus(obj.getDocumentStatus()) == "Open") {
            holder.option.setVisibility(View.VISIBLE);
            holder.status.setText(Globals.viewStatus(obj.getDocumentStatus()));
            holder.status.setBackgroundResource(R.drawable.openroundedgreen);
        } else {
            holder.option.setVisibility(View.GONE);
            holder.status.setText(Globals.viewStatus(obj.getDocumentStatus()));
            holder.status.setBackgroundResource(R.drawable.saffron_rounded);
        }

        if (obj.getU_FAV().equalsIgnoreCase("Y")) {
            holder.star_button.setLiked(true);


        } else {

            holder.star_button.setLiked(false);

        }

        holder.star_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);
                updateQuotation(obj.getId(), "Y");
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                updateQuotation(obj.getId(), "N");
            }
        });

        holder.preview_file.setVisibility(View.GONE);

        holder.preview_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebViewToPdf.class);
                i.putExtra("PDfFrom", "Quotation");
                i.putExtra("PdfID", obj.getId());
                context.startActivity(i);
            }
        });


        holder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quotationId = obj.getId();
//                if (Globals.viewStatus(arrayList.get(getAdapterPosition()).getDocumentStatus()) == "Open") {

                    PopupMenu popup = new PopupMenu(context, holder.option);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.createorder, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.createorder:
                                    Prefs.putString(Globals.FromQuotation, "Quotation");
                                    Globals.quotationOrder.clear();
                                    Intent intent = new Intent(context, AddOrderAct.class);
                                    Globals.quotationOrder.add(arrayList.get(position));
                                    intent.putExtra("QuotationObject", arrayList.get(position));

                                    context.startActivity(intent);

                                    break;

                                case R.id.outletLayout1:
                                    Intent i = new Intent(context, WebViewToPdf.class);
                                    i.putExtra("PDfFrom", "Quotation");
                                    i.putExtra("PdfID", quotationId);
                                    i.putExtra("quot", "quotation");
                                    context.startActivity(i);
                                    break;


                                case R.id.editQuotation:
                                    if (Prefs.getString(Globals.SelectQuotation, "").equalsIgnoreCase("Dashboard")) {
                                        Bundle b = new Bundle();
                                        b.putSerializable(Globals.QuotationItem, arrayList.get(position));
                                        b.putString("Flag", "Update");
                                        Quotation_Update_Fragment fragment = new Quotation_Update_Fragment();
                                        fragment.setArguments(b);
                                        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.quatoes_main_container, fragment);
                                        transaction.addToBackStack("Test");
                                        transaction.commit();
                                    }

                                    break;

                            }
                            return true;

                        }
                    });
                    popup.show();
//                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public QuotationItem getItem(int position) {
        return arrayList.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, doc_date, amount, status, name;
        LikeButton star_button;
        LinearLayout option;
        ImageView preview_file;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.name);
            doc_date = itemView.findViewById(R.id.doc_date);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
            star_button = itemView.findViewById(R.id.star_button);
            option = itemView.findViewById(R.id.option);
            preview_file = itemView.findViewById(R.id.preview_file);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Prefs.getString(Globals.SelectQuotation, "").equalsIgnoreCase("Add_Quotation") || Prefs.getString(Globals.SelectQuotation, "").equalsIgnoreCase("Add_Order") ||
                            Prefs.getString(Globals.SelectQuotation, "").equalsIgnoreCase("AddSaleOrder")) {
                        Globals.partent_proforma_invoice = arrayList.get(getAdapterPosition());
                        Bundle b = new Bundle();
                        b.putSerializable(Globals.QuotationData, arrayList.get(getAdapterPosition()));
                        Intent intent = new Intent();
                        intent.putExtras(b);
                        ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                        ((AppCompatActivity) context).finish();
                    } else if (Prefs.getString(Globals.SelectQuotation, "").equalsIgnoreCase("Dashboard")) {
//                        Globals.SelectedItems.clear();
                        Bundle b = new Bundle();
                        b.putSerializable(Globals.QuotationItem, arrayList.get(getAdapterPosition()));
                        b.putString("Flag", "Detail");
                        QuotationDetailFragment fragment = new QuotationDetailFragment();
                        fragment.setArguments(b);
                        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.quatoes_main_container, fragment);
                        transaction.addToBackStack("Test");
                        transaction.commit();
                    }


                }
            });

          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Prefs.getBoolean(Globals.SelectQuotation, true)) {
                        Globals.SelectedItems.clear();

                        Bundle b = new Bundle();
                        b.putSerializable(Globals.QuotationItem, arrayList.get(getAdapterPosition()));
                        Quotation_Update_Fragment fragment = new Quotation_Update_Fragment();
                        fragment.setArguments(b);
                        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.quatoes_main_container, fragment);
                        transaction.addToBackStack("Test");
                        transaction.commit();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(Globals.QuotationData, arrayList.get(getAdapterPosition()));
                        ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                        ((AppCompatActivity) context).finish();
                    }

                }
            });*/



        }
    }


    List<QuotationItem> tempList = null;

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayList.clear();
        if (charText.length() == 0) {
            arrayList.addAll(tempList);
        } else {
            for (QuotationItem st : tempList) {
                if (st.getCardName() != null && !st.getCardName().isEmpty()) {
                    if (st.getCardName().toLowerCase().trim().contains(charText)) {
                        arrayList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void AllData() {
        arrayList.clear();
        arrayList.addAll(tempList);
        notifyDataSetChanged();
    }

    public void AllData(List<QuotationItem> tmp) {
        tempList.clear();
        tempList.addAll(tmp);
        notifyDataSetChanged();
    }

    public void Customernamefilter(String name, String s) {
        name = name.toLowerCase(Locale.getDefault()).trim();
        arrayList.clear();
        if (name.length() == 0) {
            arrayList.addAll(tempList);
        } else {
            for (QuotationItem st : tempList) {
                if (st.getCardName() != null && !st.getCardName().isEmpty()) {
                    if (st.getCardName().toLowerCase().trim().equalsIgnoreCase(name)) {
                        arrayList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void Favfilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        arrayList.clear();
        if (charText.length() == 0) {
            arrayList.addAll(tempList);
        } else {
            for (QuotationItem st : tempList) {

                if (st.getU_FAV() != null && !st.getU_FAV().isEmpty()) {
                    if (st.getU_FAV().toLowerCase().trim().equalsIgnoreCase(charText)) {
                        arrayList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    private void updateQuotation(String QT_ID, String in) {
        QuotationItem qt = new QuotationItem();
        qt.setId(QT_ID);
        qt.setU_FAV(in);
        Call<QuotationResponse> call = NewApiClient.getInstance().getApiService().updateFavQuotation(qt);
        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                if (response.code() == 200) {
                    /* Globals.SelectedItems.clear();*/
                    Toast.makeText(context, "Updated Successfully.", Toast.LENGTH_SHORT).show();
                    fragmentRefresher.onRefresh();

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void PostingDate(final LocalDate selected, final LocalDate current) {
        arrayList.clear();
        for (QuotationItem st : tempList) {

            if (st.getDocDate() != null && !st.getDocDate().isEmpty()) {
                String sDate1 = st.getDocDate();

                SimpleDateFormat dateFormatter = new SimpleDateFormat(
                        "yyyy-MM-DD");
                Date strDate = null;
                try {
                    strDate = dateFormatter.parse(sDate1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                LocalDate date1=LocalDate.parse(sDate1);
                LocalDate date1 = LocalDate.parse(dateFormatter.format(strDate));
                if (date1.isAfter(selected) && date1.isBefore(current)) {
                    arrayList.add(st);
                }
            }
        }
        notifyDataSetChanged();

    }

    public void Customerfilter() {
        arrayList.clear();

        for (QuotationItem st : tempList) {
            if (st.getCardName() != null && !st.getCardName().isEmpty())
                arrayList.add(st);
        }
        Collections.sort(arrayList, new Comparator<QuotationItem>() {
            @Override
            public int compare(QuotationItem o1, QuotationItem o2) {
                return o1.getCardName().compareTo(o2.getCardName());
            }
        });

        notifyDataSetChanged();

    }

}
