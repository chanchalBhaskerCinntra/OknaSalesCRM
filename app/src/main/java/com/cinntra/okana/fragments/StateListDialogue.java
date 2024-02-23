package com.cinntra.okana.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.StateNewAdapter;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.viewModel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;




public class StateListDialogue extends DialogFragment implements View.OnClickListener, DatabaseClick {

    private static TextView country_value;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.list)
//    RecyclerView list;
//    @BindView(R.id.filterView)
//    RelativeLayout filterView;
//    @BindView(R.id.search)
//    RelativeLayout search;
//    @BindView(R.id.new_quatos)
//    RelativeLayout new_quatos;
//    @BindView(R.id.searchLay)
//    RelativeLayout searchLay;
//    @BindView(R.id.searchView)
//    SearchView searchView;
//    @BindView(R.id.mainHeaderLay)
//    RelativeLayout mainHeaderLay;
   private LinearLayoutManager layoutManager;
   private StateNewAdapter adapter;
   private ArrayList<StateData> countryState;
   private int currentPage = 0;
   private boolean recall = true;



    public StateListDialogue()
      {

    }

    public static StateListDialogue newInstance(String title, TextView textView)
          {
          country_value = textView;
        StateListDialogue frag = new StateListDialogue();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
      @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
      {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL,
    android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
      }
      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState)
      {
    View v = inflater.inflate(R.layout.selectcountry, container);
//    ButterKnife.bind(this,v);
//          filterView.setVisibility(View.GONE);
//          new_quatos.setVisibility(View.GONE);
//          back_press.setOnClickListener(this);
//          search.setOnClickListener(this);
//          searchLay.setOnClickListener(this);
     Globals.hideKeybaord(v,getContext());
    countryState  = new ArrayList<>();
    layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
    adapter = new StateNewAdapter(getActivity(),countryState,this);
   //list.setLayoutManager(layoutManager);
  // list.setAdapter(adapter);
   callApi();
//          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//              @Override
//              public boolean onQueryTextSubmit(String query) {
//                  return false;
//              }
//
//              @Override
//              public boolean onQueryTextChange(String newText) {
//                  if(adapter!=null)
//                      adapter.filter(newText);
//                  return false;
//              }
//          });
//    list.addOnScrollListener(new RecyclerView.OnScrollListener() {
//         @Override
//    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//         super.onScrollStateChanged(recyclerView, newState);
//        }
//       @Override
//     public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//       super.onScrolled(recyclerView, dx, dy);
//        if(layoutManager.findLastCompletelyVisibleItemPosition()==countryState.size()-3)
//          {
//              if(recall)
//              callApi();
//          }
//           }
//       });
    return v;
      }
      @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
   // head_title.setText(getActivity().getResources().getString(R.string.select_state));
          Globals.hideKeybaord(view,getContext());


    }

    @Override
    public void onClick(View v)
           {
    if(v.getId()==R.id.back_press)
      getDialog().dismiss();
    else if(v.getId()==R.id.search){
//        mainHeaderLay.setVisibility(View.GONE);
//        search.setVisibility(View.GONE);
//        searchLay.setVisibility(View.VISIBLE);
    }
    else if(v.getId()==R.id.searchLay)
    {

    }

    }
    private void callApi()
        {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getStateList(currentPage).observe(getActivity(), new Observer<List<StateData>>()
        {
            @Override
            public void onChanged(List<StateData> countries) {
                if (countries == null || countries.size() == 0) {
                    recall = false;
                    Globals.setmessage(getContext());
                } else {

                    currentPage++;
                  countryState.addAll(countries);
                  adapter.notifyDataSetChanged();
                    adapter.setList(countryState);
                }

            }
        });

    }


    @Override
    public void onClick(int po) {
      country_value.setText(countryState.get(po).getName());
      getDialog().dismiss();
    }
}
