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
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.EnventoryItemsAdapter;
import com.cinntra.okana.databinding.DeliveryActBinding;
import com.cinntra.okana.fragments.Dashboard;
import com.cinntra.okana.globals.MainBaseActivity;


public class InventoryActivity extends MainBaseActivity implements View.OnClickListener {


    private EnventoryItemsAdapter adapter;

    DeliveryActBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DeliveryActBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);
        setDefaults();
        String itemNames = getIntent().getStringExtra("IN_Type");

        if (itemNames.equalsIgnoreCase("Fast Moving")) {
            if (Dashboard.fastInventoryList.size() > 0)
                adapter = new EnventoryItemsAdapter(InventoryActivity.this, Dashboard.fastInventoryList);
            else
                binding.noDatafound.setVisibility(View.VISIBLE);
        } else if (itemNames.equalsIgnoreCase("Slow Moving")) {
            if (Dashboard.mediumInventoryList.size() > 0)
                adapter = new EnventoryItemsAdapter(InventoryActivity.this, Dashboard.mediumInventoryList);
            else
                binding.noDatafound.setVisibility(View.VISIBLE);
        } else if (itemNames.equalsIgnoreCase("Non Moving")) {
            if (Dashboard.nonInventoryList.size() > 0)
                adapter = new EnventoryItemsAdapter(InventoryActivity.this, Dashboard.nonInventoryList);
            else
                binding.noDatafound.setVisibility(View.VISIBLE);
        } else {
            if (Dashboard.allInventoryList.size() > 0)
                adapter = new EnventoryItemsAdapter(InventoryActivity.this, Dashboard.allInventoryList);
            else
                binding.noDatafound.setVisibility(View.VISIBLE);
        }

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(InventoryActivity.this, RecyclerView.VERTICAL, false));
        binding.recyclerview.setAdapter(adapter);

        // eventSearchManager();
    }

    private void setDefaults() {
        binding.quotesHeader.headTitle.setText(getString(R.string.inventory));
        /*InventoryItemAdapter delivery_adapter = new InventoryItemAdapter(InventoryActivity.this,itemsList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerview.setAdapter(delivery_adapter);
         */
        binding.quotesHeader.backPress.setOnClickListener(this);
        binding.quotesHeader.search.setOnClickListener(this);
        binding.quotesHeader.filter.setOnClickListener(this);
        binding.quotesHeader.filter.setVisibility(View.GONE);
        binding.quotesHeader.newQuatos.setVisibility(View.GONE);

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
                    Toast.makeText(InventoryActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.filter(newText);
                } else {
                    Toast.makeText(InventoryActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }

    private void showfilteroption() {
        PopupMenu popup = new PopupMenu(InventoryActivity.this, binding.quotesHeader.filter);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.inventory_filter, popup.getMenu());

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
                    case R.id.newest:

                        break;
                    case R.id.alpha:
                        if (adapter != null)
                            adapter.Alphabetical();
                        break;

                }
                return true;

            }
        });
        popup.show();
    }
}