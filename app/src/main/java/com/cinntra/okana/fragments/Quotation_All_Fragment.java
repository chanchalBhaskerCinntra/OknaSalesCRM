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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddQuotationAct;
import com.cinntra.okana.activities.QuotationActivity;
import com.cinntra.okana.adapters.Quotation_Adapter;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.QuotationItem;

import java.time.LocalDate;
import java.util.ArrayList;



public class Quotation_All_Fragment extends Fragment implements View.OnClickListener {

    private int currentPage = 0;
    private boolean isLastPage = false;

    private boolean isLoading = false;


//  @BindView(R.id.recyclerview)
//  RecyclerView recyclerview;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//  @BindView(R.id.loader)
//    ProgressBar loader;
    SearchView searchView;
    Quotation_Adapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<QuotationItem> AllItemList;
    boolean recallApi = true;

    private Context mContext;


    public Quotation_All_Fragment() {
    //Required empty public constructor
       }


    // TODO: Rename and change types and number of parameters
    public static Quotation_All_Fragment newInstance(String param1, String param2) {
      Quotation_All_Fragment fragment = new Quotation_All_Fragment();
      Bundle args = new Bundle();

      fragment.setArguments(args);
      return fragment;
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
     //Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_quotes_list, container, false);
   // ButterKnife.bind(this,v);
        mContext = getActivity();
       AllItemList = new ArrayList<>();


 //   loader.setVisibility(View.VISIBLE);
        if(Globals.checkInternet(getActivity()))
            callApi();


//        swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                if(Globals.checkInternet(getActivity())){
//                    currentPage = 0;
//                    callApi();
//                }
//                else
//                    swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });
//        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener()
//        {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.e("dy = All ,Visible",""+dy+" , "+AllItemList.size()+" , "+layoutManager.findLastCompletelyVisibleItemPosition());
//
//                if(layoutManager.findLastCompletelyVisibleItemPosition() == AllItemList.size()-3 && recallApi)
//                {
//                    callApi();
//                }
//            }
//        });

        return v;
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


    /***********  API Calling ************/

    private void callApi()
             {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
      {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
       {


        //menu.clear();
        inflater.inflate(R.menu.quotation_filter, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((QuotationActivity) mContext).getSupportActionBar().getThemedContext());

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
            case R.id.plus:
                startActivity(new Intent(getContext(), AddQuotationAct.class));
                break;
            case R.id.all:
                if(adapter!=null)
                    adapter.AllData();
                break;
            case R.id.newest:
                LocalDate date = LocalDate.parse(Globals.curntDate);
                LocalDate dateafter = date.minusDays(8);
                adapter.PostingDate(dateafter, date);
                break;
            case R.id.oldest:
             //Date date1 = sdf.parse(date);
            //String curntDate = Globals.getTodaysDate();
                break;
            case R.id.my:
                if(adapter!=null)
                    adapter.Favfilter("Y");
                break;
            case  R.id.valid:
                if(adapter!=null)
                    adapter.Customerfilter();
                break;
            case R.id.posting:

                break;
        }
        return true;
    }



}