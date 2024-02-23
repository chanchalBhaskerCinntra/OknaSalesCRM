package com.cinntra.okana.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.adapters.CampaignAdapter;
import com.cinntra.okana.databinding.FragmentCampaignBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.CampaignModel;
import com.cinntra.okana.model.CampaignResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignActivity extends MainBaseActivity {




//    @BindView(R.id.customer_lead_List)
//    RecyclerView recyclerView;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;

    List<CampaignModel> campaignModelList = new ArrayList<>();
    CampaignAdapter campaignAdapter;

    FragmentCampaignBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentCampaignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);


        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Globals.checkInternet(CampaignActivity.this)) {
                    callApi();
                } else
                   binding.swipeRefreshLayout.setRefreshing(false);

            }
        });


    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() >= 0) {
                    for (Fragment childfragnested: childFm.getFragments()) {
                        FragmentManager childFmNestManager = childfragnested.getParentFragmentManager();
                        if(childfragnested.isVisible()) {

                            childFmNestManager.popBackStack();
                            return;
                        }
                    }
                }

            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Globals.checkInternet(this)) {
            binding.loader.loader.setVisibility(View.VISIBLE);
            callApi();
        } else {
            Toasty.error(CampaignActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void callApi() {
        Call<CampaignResponse> call = NewApiClient.getInstance().getApiService().getAllCampaign();
        call.enqueue(new Callback<CampaignResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<CampaignResponse> call, Response<CampaignResponse> response) {
                if(response.code()==200)
                {
                    campaignModelList.clear();
                    campaignModelList.addAll(response.body().getData());
                    campaignAdapter = new CampaignAdapter(CampaignActivity.this,campaignModelList);
                    binding.customerLeadList.setLayoutManager(new LinearLayoutManager(CampaignActivity.this,RecyclerView.VERTICAL,false));
                    binding.customerLeadList.setAdapter(campaignAdapter);
                    campaignAdapter.notifyDataSetChanged();
                }
                else
                {

                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(CampaignActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
                nodatafoundimage();
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.loader. loader.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CampaignResponse> call, Throwable t) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(CampaignActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void  nodatafoundimage(){
        if(campaignAdapter.getItemCount()==0){
            binding.noDatafound.setVisibility(View.VISIBLE);
        }else{
            binding.noDatafound.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.campaign_filter, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((CampaignActivity) this).getSupportActionBar().getThemedContext());

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setQueryHint("Search Campaign");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if(campaignAdapter!=null)
                    campaignAdapter.filter(newText);


                return true;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.search:

                break;
            case R.id.plus:
                startActivity(new Intent(this, AddCampaign.class));
                break;



            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }
}
