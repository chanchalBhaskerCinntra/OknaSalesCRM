
package com.cinntra.okana.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOpportunityActivity;
import com.cinntra.okana.activities.Opportunities_Pipeline_Activity;
import com.cinntra.okana.adapters.ProductAdapter;
import com.cinntra.okana.databinding.FragmentOpenOppBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.Company;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.viewModel.Opportunities_ViewModel;
import java.util.ArrayList;
import java.util.List;


public class Opportunity_pipeline extends Fragment implements View.OnClickListener {

//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
    Context mcontext;

    ProductAdapter adapter;
    
    LinearLayoutManager layoutManager;
    List<Company> allopplist = new ArrayList<>();


    List<NewOpportunityRespose> alllistDAta = new ArrayList<>();
    List<NewOpportunityRespose> leadlist = new ArrayList<>();
    List<NewOpportunityRespose> needanalysislist = new ArrayList<>();
    List<NewOpportunityRespose> quotationlist = new ArrayList<>();
    List<NewOpportunityRespose> negotiationlist = new ArrayList<>();
    List<NewOpportunityRespose> orderlist = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
     super.onCreate(savedInstanceState);


    }

    FragmentOpenOppBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       binding=FragmentOpenOppBinding.inflate(getLayoutInflater());
        View v = inflater.inflate(R.layout.fragment_open_opp,container,false);
       // ButterKnife.bind(this,v);

        mcontext= getActivity();
        setDefaults();
        layoutManager= new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.recyclerview.setLayoutManager(layoutManager);
        Company lead = new Company("Lead", leadlist);
        allopplist.add(lead);
        Company needanalysis = new Company("Need Analysis", needanalysislist);
        allopplist.add(needanalysis);
        Company quotation = new Company("Quotation", quotationlist);
        allopplist.add(quotation);
        Company negotiation = new Company("Negotiation", negotiationlist);
        allopplist.add(negotiation);
        Company order = new Company("Order", orderlist);
        allopplist.add(order);

        if(Globals.checkInternet(getActivity())){
            binding.  loader.setVisibility(View.VISIBLE);
            callApi( binding.loader);}


        binding.  swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Globals.checkInternet(getActivity()))
                    callApi( binding.loader);
                else
                    binding. swipeRefreshLayout.setRefreshing(false);
            }
        });




        return binding.getRoot();
    }

    private void setDefaults() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }


    private void callApi(ProgressBar loader)
    {
        Opportunities_ViewModel model = ViewModelProviders.of(this).get(Opportunities_ViewModel.class);
        model.newOpprtunities(loader).observe(getActivity(), new Observer<List<NewOpportunityRespose>>() {
            @Override
            public void onChanged(@Nullable List<NewOpportunityRespose> itemsList)
            {
                alllistDAta.clear();
                alllistDAta.addAll(itemsList);
                binding.swipeRefreshLayout.setRefreshing(false);
                setData();

            }
        });
    }

    private void setData() {
        allopplist.get(0).setTitle("Lead"+ "(" + filterlist(alllistDAta,"1").size()+ ")");
        leadlist.clear();
        leadlist.addAll(filterlist(alllistDAta,"1"));
        allopplist.get(0).setItems(leadlist);


        allopplist.get(1).setTitle("Need Analysis"+ "("+filterlist(alllistDAta,"2").size()+ ")");
        needanalysislist.clear();
        needanalysislist.addAll(filterlist(alllistDAta,"2"));
        allopplist.get(1).setItems(needanalysislist);

        allopplist.get(2).setTitle("Quotation"+ "("+filterlist(alllistDAta,"3").size()+ ")");
        quotationlist.clear();
        quotationlist.addAll(filterlist(alllistDAta,"3"));
        allopplist.get(2).setItems(quotationlist);

        allopplist.get(3).setTitle("Negotiation"+ "("+filterlist(alllistDAta,"4").size()+ ")");
        negotiationlist.clear();
        negotiationlist.addAll(filterlist(alllistDAta,"4"));
        allopplist.get(3).setItems(negotiationlist);

        allopplist.get(4).setTitle("Order"+ "("+filterlist(alllistDAta,"5").size()+ ")");
        orderlist.clear();
        orderlist.addAll(filterlist(alllistDAta,"5"));
        allopplist.get(4).setItems(orderlist);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new ProductAdapter(allopplist,leadlist,needanalysislist,quotationlist,negotiationlist,orderlist,alllistDAta,getActivity());
        binding. recyclerview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private List<NewOpportunityRespose> filterlist(List<NewOpportunityRespose> leadlist, String s) {
        ArrayList <NewOpportunityRespose> templist = new ArrayList<>();
        templist.clear();
        for (NewOpportunityRespose list : leadlist){
            if(Character.toString(list.getCurrentStageNumber().charAt(0)).equals(s)){
                templist.add(list);
            }
        }
        return templist;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        //menu.clear();
        inflater.inflate(R.menu.filteroption_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((Opportunities_Pipeline_Activity) mcontext).getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Search Opportunity");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null&&newText.length()>0)
                    adapter.filter(newText);


                return true;
            }
        });




    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {

            case R.id.plus:
                startActivity(new Intent(getContext(), AddOpportunityActivity.class));
                break;
            case R.id.all:

                if(adapter!=null)
                       adapter.AllData();

                break;
            case R.id.my:

                break;
            case R.id.my_team:
                if(adapter!=null)
                     adapter.Favfilter("Y");
                break;
            case  R.id.valid:

                break;

        }
        return true;
    }

}
