package com.cinntra.okana.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.adapters.CountryNewAdapter;
import com.cinntra.okana.databinding.SelectcountryBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.Countries;
import com.cinntra.okana.viewModel.ItemViewModel;
import java.util.ArrayList;
import java.util.List;



public class CountryListDialogue extends DialogFragment implements View.OnClickListener, DatabaseClick {

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
   private CountryNewAdapter adapter;
   private ArrayList<Countries> countryState;
   private int currentPage = 0;
   private boolean recall = true;



    public CountryListDialogue()
      {

    }

    public static CountryListDialogue newInstance(String title,TextView textView)
          {
          country_value = textView;
        CountryListDialogue frag = new CountryListDialogue();
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

      SelectcountryBinding binding;

      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState)
      {
          binding=SelectcountryBinding.inflate(inflater,container,false);
    View v = inflater.inflate(R.layout.selectcountry, container);
   // ButterKnife.bind(this,v);
    binding.headerBottomRoundedWithSearchFilter.filterView.setVisibility(View.GONE);
          binding.headerBottomRoundedWithSearchFilter.newQuatos.setVisibility(View.GONE);
          binding.headerBottomRoundedWithSearchFilter.newQuatos.setOnClickListener(this);
          binding.headerBottomRoundedWithSearchFilter.search.setOnClickListener(this);
          binding.headerBottomRoundedWithSearchFilter.searchLay.setOnClickListener(this);
    Globals.hideKeybaord(v,getContext());
    countryState  = new ArrayList<>();
    layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
          adapter = new CountryNewAdapter(getActivity(),countryState,this);
   binding.list.setLayoutManager(layoutManager);
   binding.list.setAdapter(adapter);
   callApi();

          binding.headerBottomRoundedWithSearchFilter. searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

   binding. list.addOnScrollListener(new RecyclerView.OnScrollListener()
    {
         @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
         super.onScrollStateChanged(recyclerView, newState);
        }
       @Override
     public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
       super.onScrolled(recyclerView, dx, dy);
        if(layoutManager.findLastCompletelyVisibleItemPosition()==countryState.size()-3)
          {
              if(recall)
              callApi();
          }
           }
       });
    return binding.getRoot();
      }
      @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding.headerBottomRoundedWithSearchFilter.headTitle.setText(getActivity().getResources().getString(R.string.select_country));


    }

    @Override
    public void onClick(View v)
           {
    if(v.getId()==R.id.back_press)
      getDialog().dismiss();
    else if(v.getId()==R.id.search){
        binding.headerBottomRoundedWithSearchFilter.mainHeaderLay.setVisibility(View.GONE);
        binding.headerBottomRoundedWithSearchFilter.  search.setVisibility(View.GONE);
        binding.headerBottomRoundedWithSearchFilter. searchLay.setVisibility(View.VISIBLE);
    }
    else if(v.getId()==R.id.searchLay)
    {

    }

    }
    private void callApi()
        {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getCountrylist(currentPage).observe(getActivity(), new Observer<List<Countries>>()
        {
            @Override
            public void onChanged(List<Countries> countries) {
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
