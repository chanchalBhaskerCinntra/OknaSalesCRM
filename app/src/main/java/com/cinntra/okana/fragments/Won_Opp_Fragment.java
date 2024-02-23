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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOpportunityActivity;
import com.cinntra.okana.activities.OpportunitiesActivity;
import com.cinntra.okana.adapters.Won_Opportunities_Adapter;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.OpportunityModels.OpportunityItem;
import com.cinntra.okana.viewModel.Opportunities_ViewModel;
import java.util.List;



 public class Won_Opp_Fragment extends Fragment {
//      @BindView(R.id.recyclerview)
//      RecyclerView recyclerview;
//      @BindView(R.id.loader)
//      ProgressBar loader;
      SearchView searchView;
      int curntpage = 0;
      Context mContext;

      public Won_Opp_Fragment()
       {
   //Required empty public constructor
       }


    // TODO: Rename and change types and number of parameters
    public static Won_Opp_Fragment newInstance()
      {
        Won_Opp_Fragment fragment = new Won_Opp_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState)
       {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_open_opp, container, false);
       // ButterKnife.bind(this,v);
           mContext =getActivity();

        if(Globals.checkInternet(getActivity()));
        //    callApi(loader);
           return v;
    }


    Won_Opportunities_Adapter adapter;

    private void callApi(ProgressBar loader)
      {
   Opportunities_ViewModel model = ViewModelProviders.of(this).get(Opportunities_ViewModel.class);
   model.getOpprtunities(loader,"Won",curntpage).observe(getActivity(), new Observer<List<OpportunityItem>>() {
   @Override
   public void onChanged(@Nullable List<OpportunityItem> itemsList) {
       curntpage++;
       if(itemsList==null || itemsList.size()==0){
           Globals.setmessage(getActivity());
       }
       else {
    adapter = new Won_Opportunities_Adapter(getActivity(),itemsList);
  //  recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
   // recyclerview.setAdapter(adapter);
            }
            }
        });
    }



     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         setHasOptionsMenu(true);
         super.onCreate(savedInstanceState);

     }
     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


         menu.clear();
         inflater.inflate(R.menu.main_menu_search, menu);

         super.onCreateOptionsMenu(menu, inflater);

         MenuItem item = menu.findItem(R.id.search);
         SearchView searchView = new SearchView(((OpportunitiesActivity) mContext).getSupportActionBar().getThemedContext());
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

                 if(newText.equalsIgnoreCase("")||newText.isEmpty())
                 {
                     searchView.setIconified(true);
                     searchView.setIconified(true);
                 }
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
                     adapter.Favfilter("Yes");
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