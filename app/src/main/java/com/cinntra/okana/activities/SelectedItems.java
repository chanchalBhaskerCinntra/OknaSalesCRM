package com.cinntra.okana.activities;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.CategoryAdapter;
import com.cinntra.okana.adapters.Selected_Item_Adapter;
import com.cinntra.okana.databinding.ActivitySelectedItemBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.interfaces.ChangeTeam;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.ItemCategoryResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectedItems extends MainBaseActivity implements View.OnClickListener, DatabaseClick, ChangeTeam {

    ActivitySelectedItemBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectedItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //ButterKnife.bind(this);
        binding.quotesHeader.filterView.setVisibility(View.GONE);

        binding.loader.loader.setVisibility(View.GONE);
        setDefaults();
        eventSearchManager();

    }

    @Override
    public void onBackPressed() {

        if (binding.quotesHeader.mainHeaderLay.getVisibility() == View.GONE) {
            binding.quotesHeader.searchLay.setVisibility(View.GONE);
            binding.quotesHeader.mainHeaderLay.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private void setDefaults() {
        String fromwhere = getIntent().getStringExtra("FromWhere");
        if (fromwhere.equalsIgnoreCase("invoices")) {
            binding.quotesHeader.newQuatos.setVisibility(View.GONE);
            binding.quotesHeader.newQuatos.setClickable(false);
        }

        binding.quotesHeader.headTitle.setText(getResources().getString(R.string.selected_items));
        binding.quotesHeader.backPress.setOnClickListener(this);
        binding.quotesHeader.newQuatos.setOnClickListener(this);
        binding.done.setOnClickListener(this);
        binding.quotesHeader.search.setOnClickListener(this);

        setAdapter();
    }

    Selected_Item_Adapter adapter;

    private void setAdapter() {
        adapter = new Selected_Item_Adapter(SelectedItems.this, Globals.SelectedItems);
        binding.itemsRecycler.setLayoutManager(new LinearLayoutManager(SelectedItems.this, RecyclerView.VERTICAL, false));
        binding.itemsRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
       /* Intent ii = new Intent();
        setResult(RESULT_OK, ii);*/
                onBackPressed();
//        finish();
                break;
            case R.id.new_quatos:
               /* Intent intent = new Intent(SelectedItems.this, ItemsList.class);
                intent.putExtra("CategoryID", 0);
                startActivityForResult(intent, CreateContact.ITEMSCODE);*///todo
                openCategorydailog();
                break;
            case R.id.done:
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
                break;
            case R.id.search:
                binding.quotesHeader.mainHeaderLay.setVisibility(View.GONE);
                binding.quotesHeader.searchLay.setVisibility(View.VISIBLE);

                binding.quotesHeader.searchView.setIconifiedByDefault(true);
                binding.quotesHeader.searchView.setFocusable(true);
                binding.quotesHeader.searchView.setIconified(false);
                binding.quotesHeader.searchView.requestFocusFromTouch();
                break;
        }
    }


    private void eventSearchManager() {

        binding.quotesHeader.searchView.setBackgroundColor(Color.parseColor("#00000000"));
        binding.quotesHeader.searchLay.setVisibility(View.VISIBLE);
        binding.quotesHeader.searchView.setVisibility(View.VISIBLE);
        binding.quotesHeader.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.quotesHeader.mainHeaderLay.setVisibility(View.VISIBLE);
                binding.quotesHeader.searchLay.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.filter(newText);
                }
                return true;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setAdapter();

        }
    }

    Dialog TaxListdialog;

    private void openCategorydailog() {
        RelativeLayout backPress;
        TextView head_title;
        RecyclerView recyclerview;
        ProgressBar loader;

        TaxListdialog = new Dialog(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View custom_dialog = layoutInflater.inflate(R.layout.taxes_alert, null);
        recyclerview = custom_dialog.findViewById(R.id.recyclerview);
        backPress = custom_dialog.findViewById(R.id.back_press);
        head_title = custom_dialog.findViewById(R.id.head_title);
        loader = custom_dialog.findViewById(R.id.loader);
        head_title.setText(this.getString(R.string.select_tax));
        TaxListdialog.setContentView(custom_dialog);
        TaxListdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TaxListdialog.show();

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxListdialog.dismiss();
            }
        });

        Call<ItemCategoryResponse> call = NewApiClient.getInstance().getApiService().getAllCategory();
        call.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
                loader.setVisibility(View.GONE);
                if (response.code() == 200) {

                    CategoryAdapter adapter = new CategoryAdapter(SelectedItems.this, response.body().getData(), TaxListdialog);
                    recyclerview.setLayoutManager(new LinearLayoutManager(SelectedItems.this, RecyclerView.VERTICAL, false));
                    recyclerview.setAdapter(adapter);
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(SelectedItems.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(SelectedItems.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    //todo adapter interface value getting..
    @Override
    public void changeTeam(String name) {
        Intent intent = new Intent(SelectedItems.this, ItemsList.class);
        intent.putExtra("ItemGroupCode", name);
        startActivityForResult(intent, CreateContact.ITEMSCODE);
    }


    @Override
    public void onClick(int po) {
        Intent intent = new Intent(SelectedItems.this, ItemsList.class);
        intent.putExtra("CategoryID", po);
        startActivityForResult(intent, CreateContact.ITEMSCODE);
    }


}