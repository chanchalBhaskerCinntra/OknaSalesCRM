 package com.cinntra.okana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.OrderPagerAdapter;
import com.cinntra.okana.databinding.FragmentOrdersBinding;
import com.cinntra.okana.fragments.Open_Order;
import com.cinntra.okana.globals.MainBaseActivity;

import java.util.ArrayList;



public class OrderActivity extends MainBaseActivity implements View.OnClickListener {

    FragmentOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     binding = FragmentOrdersBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
   //  ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
     setDefaults();

        }
    //private String []tabs = {"Open","All","Approval Status"};
    private String []tabs = {"Open","All"};
    private ArrayList<Fragment> fragments =new ArrayList<Fragment>();
    private void setDefaults() {
        binding.headerBottomRoundedWithSearchFilter.headTitle.setText(getString(R.string.sales_order));
        fragments.add(new Open_Order()); //Open_order()
        //fragments.add(new All_Order());
        binding.headerBottomRoundedWithSearchFilter.newQuatos.setVisibility(View.VISIBLE);
        binding.headerBottomRoundedWithSearchFilter.backPress.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.newQuatos.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.search.setOnClickListener(this);
        binding.headerBottomRoundedWithSearchFilter.filter.setOnClickListener(this);

        OrderPagerAdapter pagerAdapter = new OrderPagerAdapter(getSupportFragmentManager(),fragments,tabs);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);


    }


    @Override
    public void onClick(View v) {
     switch (v.getId())
             {
     case R.id.back_press :
          finish();
          break;
      case R.id.new_quatos:

          startActivity(new Intent(OrderActivity.this,AddOrderAct.class));
          /* New_Order fragment = new New_Order();
           FragmentManager fm       = getSupportFragmentManager();
           FragmentTransaction transaction  = fm.beginTransaction();
           transaction.replace(R.id.main_container, fragment);
           transaction.addToBackStack(null);
           transaction.commit();*/
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

             PopupMenu popupMenu = new PopupMenu(OrderActivity.this,binding.headerBottomRoundedWithSearchFilter.filter);
             popupMenu.getMenuInflater().inflate(R.menu.order_filter,popupMenu.getMenu());
             popupMenu.show();

             popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
              @Override
              public boolean onMenuItemClick(MenuItem item) {
                /* switch (item.getItemId()){
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
                             }*/
               return true;
                         }
                     });
                     break;

              }
    }

    @Override
    public void onBackPressed()
    {
        if(binding.headerBottomRoundedWithSearchFilter.newQuatos!=null) {
            binding.headerBottomRoundedWithSearchFilter.newQuatos.setClickable(true);
            getSupportActionBar().show();
            if(binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.getVisibility()==View.GONE)
            {
                binding.headerBottomRoundedWithSearchFilter.searchLay.setVisibility(View.GONE);
                binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.setVisibility(View.VISIBLE);
            }
            else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}