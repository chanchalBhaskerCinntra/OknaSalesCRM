package com.cinntra.okana.fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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
import com.cinntra.okana.model.InvoiceResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.viewModel.QuotationList_ViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Invoices_Override_Fragment extends Fragment implements View.OnClickListener {

   private QuotationList_ViewModel viewModel;
//  @BindView(R.id.recyclerview)
//  RecyclerView recyclerview;
//  @BindView(R.id.loader)
//  ProgressBar loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;


  private SearchView searchView;
  private Invoices_Adapter adapter;
    int currentpage = 0;
    boolean recallApi = true;
    LinearLayoutManager layoutManager;
    ArrayList<InvoiceNewData> AllItemList;



    public Invoices_Override_Fragment() {
    //Required empty public constructor
       }


    // TODO: Rename and change types and number of parameters
    public static Invoices_Override_Fragment newInstance(String param1, String param2) {
      Invoices_Override_Fragment fragment = new Invoices_Override_Fragment();
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
        binding=FragmentQuotesListBinding.inflate(inflater,container,false);
    View v = inflater.inflate(R.layout.fragment_quotes_list, container, false);
 //   ButterKnife.bind(this,v);
        AllItemList = new ArrayList<>();

        eventSearchManager();
        if(Globals.checkInternet(getActivity()))
            callApi(binding.loaderLayout.loader);

        binding.noDatafound.setVisibility(View.VISIBLE);


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(Globals.checkInternet(getActivity())){

                    callApi(binding.loaderLayout.loader);
                }
                else
                    binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        return binding.getRoot();
     }

    private void eventSearchManager()
    {
        searchView = (SearchView)getActivity().findViewById(R.id.searchView);
        searchView.setBackgroundColor(Color.parseColor("#00000000"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(adapter!=null){
                    adapter.filter(query);
                }else{
                    Toast.makeText(getActivity(), "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(adapter!=null){
                    adapter.filter(newText);
                }else{
                    Toast.makeText(getActivity(), "No Match found",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        Fragment   fragment = null;
    switch(v.getId())
           {
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


    private void callApi(ProgressBar loader) {
        loader.setVisibility(View.VISIBLE);
        Call<InvoiceResponse> call = NewApiClient.getInstance().getApiService().getallinvoice();
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {

                if(response.code()==200)
                {

                       AllItemList.clear();
                    AllItemList.addAll(response.body().getValue());
                    layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
                    adapter = new Invoices_Adapter(getContext(),AllItemList);
                    binding.recyclerview.setLayoutManager(layoutManager);
                    binding.recyclerview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if(adapter.getItemCount()==0)
                        binding.noDatafound.setVisibility(View.VISIBLE);
                    else
                        binding.noDatafound.setVisibility(View.GONE);

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,LeadResponse.class);
                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
                loader.setVisibility(View.GONE);
               binding.swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        //menu.clear();
        inflater.inflate(R.menu.invoice_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = new SearchView(((InvoiceActivity) getContext()).getSupportActionBar().getThemedContext());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if(adapter!=null)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
           {
    switch(item.getItemId())
        {
    case R.id.all:
      if(adapter!=null)
         adapter.AllData();
         break;
    case R.id.my:
         if(adapter!=null)
            adapter.Customername();
            break;
    case R.id.my_team:
           break;
    case  R.id.valid:
          if(adapter!= null)
             adapter.ValidDate();
           break;
     case R.id.newest:
          LocalDate dateObj1 = LocalDate.parse(Globals.curntDate);
          LocalDate afterdate1 = dateObj1.minusDays(8);
          adapter.PostingDate(afterdate1, dateObj1);
          break;
     case R.id.oldest:
          Toast.makeText(getContext(),"Existing",Toast.LENGTH_LONG).show();
           break;

        }
        return true;
    }


}