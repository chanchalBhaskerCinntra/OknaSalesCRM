package com.cinntra.okana.activities;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.DeliveryAdapter;
import com.cinntra.okana.databinding.DeliveryActBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.QuotationStringItem;
import com.cinntra.okana.model.QuotationStringResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.time.LocalDate;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DeliveryActivity extends MainBaseActivity implements View.OnClickListener {

    DeliveryAdapter adapter;
    DeliveryActBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DeliveryActBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  ButterKnife.bind(this);
        setDefaults();
        eventSearchManager();

    }

    String DelievryType;
    private ArrayList<QuotationStringItem> deliveryList = new ArrayList<>();

    private void setDefaults() {
        DelievryType = getIntent().getExtras().getString("DeliveryType").trim();
        binding.quotesHeader.headTitle.setText(DelievryType + " Deliveries");
        binding.quotesHeader.newQuatos.setVisibility(View.GONE);
        binding.quotesHeader.search.setOnClickListener(this);
        binding.quotesHeader.backPress.setOnClickListener(this);
        binding.quotesHeader.filter.setOnClickListener(this);
        binding.quotesHeader.filter.setVisibility(View.GONE);


        SalesEmployeeItem salesEmployeeItem = new SalesEmployeeItem();
        salesEmployeeItem.setSalesEmployeeCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
        if (DelievryType.equalsIgnoreCase("Overdue"))
            salesEmployeeItem.setType("over");
        else if (DelievryType.equalsIgnoreCase("Open"))
            salesEmployeeItem.setType("open");
        else if (DelievryType.equalsIgnoreCase("Closed"))
            salesEmployeeItem.setType("close");


        if (Globals.checkInternet(this)) {
            binding.loader.loader.setVisibility(View.VISIBLE);
            callApi(salesEmployeeItem);
        }




    /*if(Globals.checkInternet(getApplicationContext()))
        callApi(loader,DelievryType);*/


    }

    private void callApi(SalesEmployeeItem salesEmployeeItem) {

        Call<QuotationStringResponse> call = NewApiClient.getInstance().getApiService().orderlist(salesEmployeeItem);
        call.enqueue(new Callback<QuotationStringResponse>() {
            @Override
            public void onResponse(Call<QuotationStringResponse> call, Response<QuotationStringResponse> response) {

                if (response.code() == 200) {

                    deliveryList.clear();
                    deliveryList.addAll(response.body().getValue());
                    adapter = new DeliveryAdapter(DeliveryActivity.this, deliveryList, 3, DelievryType);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(DeliveryActivity.this, LinearLayoutManager.VERTICAL, false));
                    binding.recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (deliveryList.size() <= 0) {
                        binding.noDatafound.setVisibility(View.VISIBLE);
                    }
                }
                binding.loader.loader.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<QuotationStringResponse> call, Throwable t) {
                Toast.makeText(DeliveryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.loader.loader.setVisibility(View.GONE);
            }
        });
    }

    private void eventSearchManager() {

        binding.quotesHeader.searchView.setBackgroundColor(Color.parseColor("#00000000"));
        binding.quotesHeader.searchLay.setVisibility(View.VISIBLE);
        binding.quotesHeader.searchView.setVisibility(View.VISIBLE);
        binding.quotesHeader.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null) {
                    adapter.filter(query);
                } else {
                    Toast.makeText(DeliveryActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.filter(newText);
                } else {
                    Toast.makeText(DeliveryActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                finish();
                break;
            case R.id.filter:
                showfilteroption();
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

    @Override
    public void onBackPressed() {

        if (binding.quotesHeader.mainHeaderLay.getVisibility() == View.GONE) {
            binding.quotesHeader.searchLay.setVisibility(View.GONE);
            binding.quotesHeader.mainHeaderLay.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    private void showfilteroption() {
        PopupMenu popup = new PopupMenu(DeliveryActivity.this, binding.quotesHeader.filter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.delivery_filter, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.all:
                        if (adapter != null)
                            adapter.AllData();

                        break;

                    case R.id.my:

                        break;
                    case R.id.posting:

                        break;
                    case R.id.valid:
                        LocalDate dateObj1 = LocalDate.parse(Globals.curntDate);
                        LocalDate afterdate1 = dateObj1.plusDays(8);
                        adapter.ValidDate(afterdate1, dateObj1);
                        break;
                    case R.id.newest:

                        break;
                    case R.id.oldest:

                        break;

                    case R.id.filter:
                        showfilteroption();
                        break;
                }
                return true;

            }
        });
        popup.show();
    }

}