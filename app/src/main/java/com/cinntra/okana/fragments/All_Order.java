package com.cinntra.okana.fragments;

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
import com.cinntra.okana.activities.AddOrderAct;
import com.cinntra.okana.activities.OrderActivity;
import com.cinntra.okana.adapters.Open_Order_Adapter;
import com.cinntra.okana.databinding.FragmentAllOrderBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.viewModel.QuotationList_ViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class All_Order extends Fragment {

//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;


    private SearchView searchView;
    LinearLayoutManager layoutManager;
    ArrayList<QuotationItem> AllItemList;
    int curntpage = 0;
    boolean recallApi = true;

    public All_Order() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static All_Order newInstance(String param1, String param2) {
        All_Order fragment = new All_Order();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    FragmentAllOrderBinding binding;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        //menu.clear();
        inflater.inflate(R.menu.order_filter, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((OrderActivity) getContext()).getSupportActionBar().getThemedContext());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null)
                    adapter.filter(newText);
                return false;
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllOrderBinding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.fragment_all_order, container, false);
        //ButterKnife.bind(this,v);
        AllItemList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        if (Globals.checkInternet(getActivity())) {
            binding.loader.loader.setVisibility(View.VISIBLE);
            callApi(binding.loader.loader);
        }

        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Globals.checkInternet(getActivity())) {

                    callApi(binding.loader.loader);
                } else
                    binding.swipeRefreshLayout.setRefreshing(false);

            }
        });
        /*recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy)
             {
            super.onScrolled(recyclerView, dx, dy);
            if(layoutManager.findLastCompletelyVisibleItemPosition() == AllItemList.size()-3 && recallApi)
                  {
              callApi(loader);
                  }
            }
        });*/
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.plus:
                startActivity(new Intent(getContext(), AddOrderAct.class));
                break;
            case R.id.all:
                if (adapter != null)
                    adapter.allData();
                break;
            case R.id.customer:
                if (adapter != null)
                    adapter.Customerfilter();
                break;
            case R.id.posting:

                break;
            case R.id.valid:
                if (adapter != null)
                    adapter.ValidDate();
                break;
            case R.id.order:
                LocalDate dateObj2 = LocalDate.parse(Globals.curntDate);
                LocalDate afterdate2 = dateObj2.minusDays(8);
                adapter.PostingDate(afterdate2, dateObj2);
                break;
        }
        return true;
    }


    private Open_Order_Adapter adapter;

    private void callApi(ProgressBar loader) {
        QuotationList_ViewModel model = ViewModelProviders.of(this).get(QuotationList_ViewModel.class);
        model.getOrders(loader).observe(getActivity(), new Observer<List<QuotationItem>>() {
            @Override
            public void onChanged(@Nullable List<QuotationItem> itemsList) {


                AllItemList.clear();
                assert itemsList != null;
                AllItemList.addAll(itemsList);

//                adapter = new Open_Order_Adapter(getContext(), AllItemList);//todo comment by chancahl
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                if (itemsList.size() <= 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
            }

        });
    }
}