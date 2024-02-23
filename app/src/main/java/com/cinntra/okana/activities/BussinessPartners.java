package com.cinntra.okana.activities;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.InvoicePagerAdapter;
import com.cinntra.okana.databinding.CustomersBinding;
import com.cinntra.okana.fragments.Add_BussinessPartner_Fragment2;
import com.cinntra.okana.fragments.CustomersFragment;
import com.cinntra.okana.globals.MainBaseActivity;

import java.util.ArrayList;


public class BussinessPartners extends MainBaseActivity implements View.OnClickListener {

    CustomersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CustomersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setDefaults();


    }

    private String[] tabs = {"Customers"};
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    private void setDefaults() {
        binding.headerBottomRoundedWithSearchFilter.headTitle.setText(getResources().getString(R.string.bussiness_partners));
        fragments.add(new CustomersFragment());
//    fragments.add(new LeadsActivity());
        InvoicePagerAdapter pagerAdapter = new InvoicePagerAdapter(getSupportFragmentManager(), fragments, tabs);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        binding.headerBottomRoundedWithSearchFilter.backPress.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.newQuatos.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                finish();
                break;
            case R.id.new_quatos:
                Fragment fragment = new Add_BussinessPartner_Fragment2();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_edit_qt_frame, fragment).addToBackStack("").commit();
                break;
            case R.id.search:
                binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.setVisibility(View.GONE);
                binding.headerBottomRoundedWithSearchFilter.searchLay.setVisibility(View.VISIBLE);

                binding.headerBottomRoundedWithSearchFilter.searchView.setIconifiedByDefault(true);
                binding.headerBottomRoundedWithSearchFilter.searchView.setFocusable(true);
                binding.headerBottomRoundedWithSearchFilter.searchView.setIconified(false);
                binding.headerBottomRoundedWithSearchFilter.searchView.requestFocusFromTouch();
                break;
            case R.id.filter:

                PopupMenu popupMenu = new PopupMenu(BussinessPartners.this, binding.headerBottomRoundedWithSearchFilter.filter);
                popupMenu.getMenuInflater().inflate(R.menu.filteroption_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.all:
                                item.isChecked();
                                break;
                            case R.id.my:
                                break;
                            case R.id.my_team:
                                break;
                            case R.id.newest:
                                break;
                            case R.id.oldest:
                                break;
                        }
                        return true;
                    }
                });
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (binding.headerBottomRoundedWithSearchFilter.newQuatos != null) {
            binding.headerBottomRoundedWithSearchFilter.newQuatos.setClickable(true);
            FragmentManager fm = getSupportFragmentManager();
            if (fm != null)
                if (fm.getFragments().size() > 3)
                    getSupportActionBar().hide();
                else
                    getSupportActionBar().show();


            if (binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.getVisibility() == View.GONE) {
                binding.headerBottomRoundedWithSearchFilter.searchLay.setVisibility(View.GONE);
                binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.setVisibility(View.VISIBLE);


            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}