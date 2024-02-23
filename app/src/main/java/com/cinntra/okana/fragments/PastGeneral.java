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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOpportunityActivity;
import com.cinntra.okana.activities.Opportunities_Pipeline_Activity;
import com.cinntra.okana.adapters.OpenOpportunities_Adapter;
import com.cinntra.okana.databinding.FragmentPastGeneralBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.viewModel.Opportunities_ViewModel;
import com.github.ybq.android.spinkit.SpinKitView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class PastGeneral extends Fragment implements View.OnClickListener, FragmentRefresher {

    Context mContext;
//    @BindView(R.id.week)
//    TextView week;
//    @BindView(R.id.month)
//    TextView month;
//    @BindView(R.id.quarterly)
//    TextView quarterly;
//    @BindView(R.id.year)
//    TextView year;
//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.loader)
//    SpinKitView loader;
    int currentpage = 0;
    boolean recallApi = true;
    LinearLayoutManager layoutManager;
    ArrayList<NewOpportunityRespose> AllitemLIst;
    OpenOpportunities_Adapter adapter;
    FragmentPastGeneralBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
     {
         binding=FragmentPastGeneralBinding.inflate(inflater,container,false);
     View v=inflater.inflate(R.layout.fragment_past_general, container, false);
  //   ButterKnife.bind(this,v);
     mContext = getActivity();
     binding.week.setOnClickListener(this);
         binding. month.setOnClickListener(this);
         binding.quarterly.setOnClickListener(this);
         binding. year.setOnClickListener(this);
     AllitemLIst = new ArrayList<NewOpportunityRespose>();
     if(Globals.checkInternet(getActivity())) {
         binding. loader.loader.setVisibility(View.VISIBLE);
         callApi(binding. loader.loader);
     }
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        //  menu.clear();
        inflater.inflate(R.menu.filteroption_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((Opportunities_Pipeline_Activity) mContext).getSupportActionBar().getThemedContext());
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
                if(adapter!=null && newText.trim().length() >  0)
                    adapter.filter(newText.trim());


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
               /* if(adapter!=null)
                    adapter.Favfilter("Yes");*/
                break;
            case  R.id.valid:

                break;
           /* case R.id.newest:
                if(adapter!=null)
                    adapter.Typefilter("New Business");
                break;
            case R.id.oldest:
                if(adapter!=null)
                    adapter.Typefilter("Existing Business");
                break;*/
        }
        return true;
    }
    private void callApi(SpinKitView loader)
    {
        Opportunities_ViewModel model = ViewModelProviders.of(this).get(Opportunities_ViewModel.class);
        model.newOpprtunities(loader).observe(getActivity(), new Observer<List<NewOpportunityRespose>>() {
            @Override
            public void onChanged(@Nullable List<NewOpportunityRespose> itemsList)
            {

                if (itemsList == null || itemsList.size() == 0) {
                    recallApi = false;
                    currentpage++;
                    Globals.setmessage(getActivity());
                } else {
                    if (itemsList.size() < 20)
                        recallApi = false;

                    AllitemLIst.addAll(itemsList);
                    layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    adapter = new OpenOpportunities_Adapter(PastGeneral.this, getContext(),AllitemLIst);
                   binding. recyclerview.setLayoutManager(layoutManager);
                   binding. recyclerview.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

             /*adapter = new OpenOpportunities_Adapter(getActivity(), itemsList);
             recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
             recyclerview.setAdapter(adapter);*/
                }
            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.week:
                LocalDate dateObj1 = LocalDate.parse(Globals.curntDate);
                LocalDate afterdate1 = dateObj1.minusDays(7);
                adapter.pipelinefilter(afterdate1, dateObj1);
                settexBackground(binding.week,binding.month,binding.year,binding.quarterly);
                break;
            case R.id.month:
                LocalDate dateObj2 = LocalDate.parse(Globals.curntDate);
                LocalDate afterdate2 = dateObj2.minusMonths(1);
                adapter.pipelinefilter(afterdate2, dateObj2);
                settexBackground(binding.month,binding.week,binding.year,binding.quarterly);
                break;
            case R.id.year:
                LocalDate dateObj3 = LocalDate.parse(Globals.curntDate);
                LocalDate afterdate3 = dateObj3.minusYears(1);
                adapter.pipelinefilter(afterdate3, dateObj3);
                settexBackground(binding.year,binding.month,binding.week,binding.quarterly);
                break;
            case R.id.quarterly:
                LocalDate dateObj4 = LocalDate.parse(Globals.curntDate);
                LocalDate afterdate4 = dateObj4.minusMonths(3);
                adapter.pipelinefilter(afterdate4, dateObj4);
                settexBackground(binding.quarterly,binding.month,binding.year,binding.week);
                break;
        }
    }

    private void settexBackground(TextView week, TextView month, TextView year, TextView quarterly) {
    week.setTextColor(getResources().getColor(R.color.white));
    week.setBackgroundResource(R.drawable.background_blue_rounded);
    month.setTextColor(getResources().getColor(R.color.black));
    month.setBackgroundResource(R.drawable.background_grey_rounded);
    year.setTextColor(getResources().getColor(R.color.black));
    year.setBackgroundResource(R.drawable.background_grey_rounded);
    quarterly.setTextColor(getResources().getColor(R.color.black));
    quarterly.setBackgroundResource(R.drawable.background_grey_rounded);
    }

    @Override
    public void onRefresh() {

    }
}
