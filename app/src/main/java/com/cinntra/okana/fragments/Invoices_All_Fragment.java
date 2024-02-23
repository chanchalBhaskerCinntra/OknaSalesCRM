package com.cinntra.okana.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.InvoiceActivity;
import com.cinntra.okana.adapters.Invoices_Adapter;
import com.cinntra.okana.databinding.FragmentQuotesListBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.InvoiceNewData;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.viewModel.QuotationList_ViewModel;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;


public class Invoices_All_Fragment extends Fragment implements View.OnClickListener {


    //  @BindView(R.id.recyclerview)
//  RecyclerView recyclerview;
//  @BindView(R.id.loader)
//  ProgressBar loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.swipeRefreshLayout)
    //   PullRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private Invoices_Adapter adapter;
    int currentpage = 0;
    boolean recallApi = true;
    LinearLayoutManager layoutManager;
    ArrayList<InvoiceNewData> AllItemList;


    public Invoices_All_Fragment() {
        //Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Invoices_All_Fragment newInstance(String param1, String param2) {
        Invoices_All_Fragment fragment = new Invoices_All_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);


    }


    FragmentQuotesListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding = FragmentQuotesListBinding.inflate(getLayoutInflater());
        View v = inflater.inflate(R.layout.fragment_quotes_list, container, false);
        //  ButterKnife.bind(this,v);
        AllItemList = new ArrayList<>();

        eventSearchManager();
        if (Globals.checkInternet(getActivity()))
            callApi(binding.loaderLayout.loader);

        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("dy = All ,Visible", "" + dy + " , " + AllItemList.size() + " , " + layoutManager.findLastCompletelyVisibleItemPosition());

                if (layoutManager.findLastCompletelyVisibleItemPosition() == AllItemList.size() - 3 && recallApi) {
                    callApi(binding.loaderLayout.loader);
                }
            }
        });


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Globals.checkInternet(getActivity())) {
                    callApi(binding.loaderLayout.loader);
                } else {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
        /*  case R.id.new_quatos:
          fragment = new New_Quotation();
        FragmentManager fm       = getFragmentManager();
        FragmentTransaction transaction  = fm.beginTransaction();
       //FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quatoes_main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
           break;*/
        }


    }


    private void eventSearchManager() {
        searchView = (SearchView) getActivity().findViewById(R.id.searchView);
        searchView.setBackgroundColor(Color.parseColor("#00000000"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null) {
                    adapter.filter(query);
                } else {
                    Toast.makeText(getActivity(), "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.filter(newText);
                } else {
                    Toast.makeText(getActivity(), "No Match found", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }

    private void callApi(SpinKitView loader) {

        QuotationList_ViewModel model = ViewModelProviders.of(this).get(QuotationList_ViewModel.class);
        model.getInvoices(loader, "All", currentpage).observe(getActivity(), new Observer<List<InvoiceNewData>>() {
            @Override
            public void onChanged(@Nullable List<InvoiceNewData> itemsList) {

                if (itemsList == null || itemsList.size() == 0) {
                    recallApi = false;
                    Globals.setmessage(getActivity());
                } else {
                    if (itemsList.size() < 20)
                        recallApi = false;
                    currentpage++;
                    AllItemList.addAll(itemsList);
                    layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    //   adapter = new Invoices_Adapter(getContext(),AllItemList);
                    binding.recyclerview.setLayoutManager(layoutManager);
                    binding.recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                if (itemsList.size() <= 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);

                binding.swipeRefreshLayout.setRefreshing(false);
            }

        });
    }


    /***********  API Calling ************/

    private MutableLiveData<QuotationResponse> volumesResponseLiveData;

    public LiveData<QuotationResponse> getVolumesResponseLiveData() {
        return volumesResponseLiveData;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        //menu.clear();
        inflater.inflate(R.menu.invoice_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = new SearchView(((InvoiceActivity) getContext()).getSupportActionBar().getThemedContext());
        // MenuItemCompat.setShowAsAction(item, //MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | //MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        //  MenuItemCompat.setActionView(item, searchView);
        // These lines are deprecated in API 26 use instead
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity(), "OPEN  => " + newText, Toast.LENGTH_SHORT).show();
                if (adapter != null)
                    adapter.filter(newText);
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                          }
                                      }
        );


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.all:
                Toast.makeText(getContext(), "All", Toast.LENGTH_LONG).show();
                break;
            case R.id.my:
                Toast.makeText(getContext(), "My", Toast.LENGTH_LONG).show();
                break;
            case R.id.my_team:
                Toast.makeText(getContext(), "My Fav", Toast.LENGTH_LONG).show();
                break;
            case R.id.valid:
                Toast.makeText(getContext(), "Valid", Toast.LENGTH_LONG).show();
                break;
            case R.id.newest:
                Toast.makeText(getContext(), "New", Toast.LENGTH_LONG).show();
                break;
            case R.id.oldest:
                Toast.makeText(getContext(), "Existing", Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }

}