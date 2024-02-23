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

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOpportunityActivity;
import com.cinntra.okana.activities.Opportunities_Pipeline_Activity;
import com.cinntra.okana.adapters.FinalStatusAdapter;

import com.cinntra.okana.databinding.FragmentOpenOppBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.Company;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.viewModel.Opportunities_ViewModel;

import java.util.ArrayList;
import java.util.List;



public class FinalStatusFragment extends Fragment
{

//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
    Context mcontext;

    List<NewOpportunityRespose> allOpplist = new ArrayList<>();
    List<NewOpportunityRespose> leadlist = new ArrayList<>();
    List<NewOpportunityRespose> needanalysislist = new ArrayList<>();
    List<NewOpportunityRespose> quotationlist = new ArrayList<>();
    List<NewOpportunityRespose> negotiationlist = new ArrayList<>();
    ArrayList<Company> companies = new ArrayList<>();
    FinalStatusAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    FragmentOpenOppBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding=FragmentOpenOppBinding.inflate(inflater,container,false);
        View v = inflater.inflate(R.layout.fragment_open_opp,container,false);
       // ButterKnife.bind(this,v);
        mcontext = getActivity();
        Company won = new Company("Won", leadlist);
        companies.add(won);
        Company lost = new Company("Lost", needanalysislist);
        companies.add(lost);
        Company missed = new Company("Missed",quotationlist);
        companies.add(missed);
        Company dropped = new Company("Dropped", negotiationlist);
        companies.add(dropped);
    if(Globals.checkInternet(getContext())){
       binding. loader.setVisibility(View.VISIBLE);
         callApi(binding.loader);
    }
        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Globals.checkInternet(getActivity()))
                    callApi(binding.loader);
                else
                    binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        return binding.getRoot();
    }


    private void callApi(ProgressBar loader)
    {
        Opportunities_ViewModel model = ViewModelProviders.of(this).get(Opportunities_ViewModel.class);
        model.newOpprtunities(loader).observe(getActivity(), new Observer<List<NewOpportunityRespose>>() {
            @Override
            public void onChanged(@Nullable List<NewOpportunityRespose> itemsList)
            {
                allOpplist.clear();
                allOpplist.addAll(itemsList);
                binding. swipeRefreshLayout.setRefreshing(false);
                setData();

            }

        });
    }
    private void setData() {
        companies.get(0).setTitle("Won"+ "("+filterlist(allOpplist,"sos_Open").size()+ ")");
        leadlist.clear();
        leadlist.addAll(filterlist(allOpplist,"sos_Open"));
        companies.get(0).setItems(leadlist);

        companies.get(1).setTitle("Lost"+ "("+filterlist(allOpplist,"so_Closed").size()+ ")");
        needanalysislist.clear();
        needanalysislist.addAll(filterlist(allOpplist,"so_Closed"));
        companies.get(1).setItems(needanalysislist);

        companies.get(2).setTitle("Overdue"+ "("+filterlist(allOpplist,"sos_Missed").size()+ ")");
        quotationlist.clear();
        quotationlist.addAll(filterlist(allOpplist,"sos_Missed"));
        companies.get(2).setItems(quotationlist);

        companies.get(3).setTitle("Dropped"+ "("+filterlist(allOpplist,"sos_Sold").size()+ ")");
        negotiationlist.clear();
        negotiationlist.addAll(filterlist(allOpplist,"sos_Sold"));
        companies.get(3).setItems(negotiationlist);

        binding. recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter= new FinalStatusAdapter(companies,allOpplist,getActivity());

        binding. recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
    private List<NewOpportunityRespose> filterlist(List<NewOpportunityRespose> leadlist, String s) {
        ArrayList <NewOpportunityRespose> templist = new ArrayList<>();
        templist.clear();
        for (NewOpportunityRespose list : leadlist){
            if(list.getStatus().equalsIgnoreCase(s)){
                templist.add(list);
            }
        }
        return templist;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
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
            case R.id.newest:

                if(adapter!=null)
                    adapter.Typefilter("New Business");
                break;
            case R.id.oldest:
                if(adapter!=null)
                    adapter.Typefilter("Existing Business");
                break;
        }
        return true;
    }
}
