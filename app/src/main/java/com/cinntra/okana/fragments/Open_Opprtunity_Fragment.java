package com.cinntra.okana.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.activities.Opportunities_Pipeline_Activity;
import com.cinntra.okana.adapters.BPTypeFilterAdapter;
import com.cinntra.okana.adapters.OpenOpportunities_Adapter;
import com.cinntra.okana.adapters.SourceAdpater;
import com.cinntra.okana.databinding.FragmentOpenOppBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.CommentStage;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllResponse;
import com.cinntra.okana.model.BPTypeResponse;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.IndustryResponse;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.LeadTypeResponse;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.OpportunityModels.OppAddressResponseModel;
import com.cinntra.okana.model.OpportunityModels.OpportunityAllListRequest;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.viewModel.Opportunities_ViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Open_Opprtunity_Fragment extends Fragment implements FragmentRefresher, CommentStage {

    Context mContext;

    LinearLayoutManager layoutManager;
    ArrayList<NewOpportunityRespose> opportunityResposeArrayList_gl;
    OpenOpportunities_Adapter adapter;
    Dialog dialog;
    List<UTypeData> utypelist = new ArrayList<>();
    int page = 1;
    Boolean apicall = true;
    Boolean isScrollingpage = false;
    int maxItem = 8;
    private String searchTextValue = "";

//    FragmentOpenOppBinding binding;

    FragmentOpenOppBinding binding;

    String  BPCardCode = null;
    public Open_Opprtunity_Fragment() {
    }

    //TODO: Rename and change types and number of parameters
    public static Open_Opprtunity_Fragment newInstance(String param1, String param2) {
        Open_Opprtunity_Fragment fragment = new Open_Opprtunity_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOpenOppBinding.inflate(getLayoutInflater());
        mContext = getActivity();

        if (getArguments() != null) {
            BPCardCode  = getArguments().getString("BPCardCodeShortCut");
            // Use the passed data as needed
        }


//          getActivity().getActionBar().setTitle((getString(R.string.opportunities)));
        opportunityResposeArrayList_gl = new ArrayList<>();
        Globals.sourcechecklist.clear();
        //eventSearchManager();


        //todo pagination for list..
        binding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompletelyVisibleItemPosition = layoutManager.findLastVisibleItemPosition();


                int firstVisibleitempositon = layoutManager.findFirstVisibleItemPosition(); //first item

                int visibleItemCount = layoutManager.getChildCount(); //total number of visible item

                int totalItemCount = layoutManager.getItemCount(); //total number of item

                Log.e("current1--->", String.valueOf(totalskipCount(lastCompletelyVisibleItemPosition)));
                if (isScrollingpage && visibleItemCount + firstVisibleitempositon == totalItemCount) {
                    page++;
                    Log.e("page--->", String.valueOf(page));
                    getOpportunityListOtherPageApi(binding.loader, maxItem, page, "", "", "", "", "");
                    isScrollingpage = false;
                } else {
                    recyclerView.setPadding(0, 0, 0, 0);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) { //it means we are scrolling
                    isScrollingpage = true;
                }
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CardName = "";
                OppTypeName = "";
                fromDate = "";
                toDate = "";
                searchTextValue = "";
                SourceName = "";
                Globals.sourcechecklist.clear();
                if (Globals.checkInternet(getActivity())) {
                    page = 1;
                    apicall = true;
                    if (BPCardCode == null){
                        getOpportunityListApi(binding.loader, maxItem, page, "", "", "", "", "");
                    }else {
                        getOpportunityListApi(binding.loader, maxItem, page, BPCardCode, "", "", "", "");
                    }

                } else {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        return binding.getRoot();
    }


    //todo count items..
    int totalskipCount(int current) {
        int total = maxItem * page;
        return total;
    }



    @Override
    public void onResume() {
        super.onResume();
        if (Globals.checkInternet(getActivity())) {
            binding.loader.setVisibility(View.VISIBLE);
            //todo loading initial list items and calling adapter-----
            if (BPCardCode == null){
                getOpportunityListApi(binding.loader, maxItem, page, "", "", "", "", "");
            }else {
                getOpportunityListApi(binding.loader, maxItem, page, BPCardCode, "", "", "", "");
            }

            setAdapter();
        }
    }


    //todo opportunity all list api with pagination..
    private void getOpportunityListApi(ProgressBar loader, int maxItem, int page, String cardCode, String OppTypeID, String fromDate, String toDate, String sourceName) {
        loader.setVisibility(View.VISIBLE);
        OpportunityAllListRequest opportunityAllListRequest = new OpportunityAllListRequest();
        opportunityAllListRequest.setSalesPersonCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
        opportunityAllListRequest.setMaxItem(maxItem);
        opportunityAllListRequest.setPageNo(page);
        opportunityAllListRequest.setSearchText(searchTextValue);
        OpportunityAllListRequest.Field field = new OpportunityAllListRequest.Field();
        field.setCardCode(cardCode);
        field.setStatus("");
        if (!fromDate.isEmpty()) {
            field.setFromDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(fromDate));
        }else{
            field.setFromDate("");
        }
        if (!toDate.isEmpty()) {
            field.setToDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(toDate));
        }else{
            field.setToDate("");
        }
        field.setCardName("");
        field.setOppotype(OppTypeID);
        field.setSource(sourceName);
        opportunityAllListRequest.setField(field);
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().getOpportunityList(opportunityAllListRequest);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            loader.setVisibility(View.GONE);
                            if (response.body().getData().size() == 0) {
                                opportunityResposeArrayList_gl.clear();
                                opportunityResposeArrayList_gl.addAll(response.body().getData());
                                apicall = false;
                                adapter.notifyDataSetChanged();
                                loader.setVisibility(View.GONE);
                                binding.noDatafound.setVisibility(View.VISIBLE);

                            } else if (response.body().getData().size() > 0 && response.body().getData() != null) {
                                List<NewOpportunityRespose> opportunityResposeList = response.body().getData();
                                if (page == 1) {
                                    opportunityResposeArrayList_gl.clear();
                                    opportunityResposeArrayList_gl.addAll(opportunityResposeList);
                                    adapter.AllData(opportunityResposeList);
                                } else {
                                    opportunityResposeArrayList_gl.addAll(opportunityResposeList);
                                    adapter.AllData(opportunityResposeList);
                                }
                                binding.swipeRefreshLayout.setRefreshing(false);

                                adapter.notifyDataSetChanged();

                                binding.noDatafound.setVisibility(View.GONE);

                                if (opportunityResposeList.size() < 10)
                                    apicall = false;
                            }
                        }

                        else if (response.body().getStatus() == 401) {
                            Gson gson = new GsonBuilder().create();
                            QuotationResponse mError = new QuotationResponse();
                            try {
                                String s = response.errorBody().string();
                                mError = gson.fromJson(s, QuotationResponse.class);
                                Toast.makeText(getActivity(), mError.getMessage(), Toast.LENGTH_LONG).show();
//                                Globals.logoutScreen(getActivity());
                            } catch (IOException e) {
                                // handle failure to read error
                            }
                        } else {
                            binding.noDatafound.setVisibility(View.VISIBLE);
                            loader.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        loader.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(requireContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getOpportunityListOtherPageApi(ProgressBar loader, int maxItem, int page, String cardCode, String OppTypeID, String fromDate, String toDate, String sourceName) {
        loader.setVisibility(View.VISIBLE);
        OpportunityAllListRequest opportunityAllListRequest = new OpportunityAllListRequest();
        opportunityAllListRequest.setSalesPersonCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
        opportunityAllListRequest.setMaxItem(maxItem);
        opportunityAllListRequest.setPageNo(page);
        opportunityAllListRequest.setSearchText(searchTextValue);
        OpportunityAllListRequest.Field field = new OpportunityAllListRequest.Field();
        field.setCardCode(cardCode);
        field.setStatus("");
        if (!fromDate.isEmpty()) {
            field.setFromDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(fromDate));
        }else{
            field.setFromDate("");
        }
        if (!toDate.isEmpty()) {
            field.setToDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(toDate));
        }else{
            field.setToDate("");
        }

        field.setCardName("");
        field.setOppotype(OppTypeID);
        field.setSource(sourceName);
        opportunityAllListRequest.setField(field);
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().getOpportunityList(opportunityAllListRequest);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            loader.setVisibility(View.GONE);
                            if (response.body().getData().size() == 0) {
                                //   opportunityResposeArrayList_gl.clear();
                                opportunityResposeArrayList_gl.addAll(response.body().getData());
                                apicall = false;
                                adapter.notifyDataSetChanged();
                                loader.setVisibility(View.GONE);
                                // binding.noDatafound.setVisibility(View.VISIBLE);

                            } else if (response.body().getData().size() > 0 && response.body().getData() != null) {
                                List<NewOpportunityRespose> opportunityResposeList = response.body().getData();
                                if (page == 1) {
                                    //  opportunityResposeArrayList_gl.clear();
                                    opportunityResposeArrayList_gl.addAll(opportunityResposeList);
                                    adapter.AllData(opportunityResposeList);
                                } else {
                                    opportunityResposeArrayList_gl.addAll(opportunityResposeList);
                                    adapter.AllData(opportunityResposeList);
                                }
                                binding.swipeRefreshLayout.setRefreshing(false);

                                adapter.notifyDataSetChanged();

                                binding.noDatafound.setVisibility(View.GONE);

                                if (opportunityResposeList.size() < 10)
                                    apicall = false;
                            }
                        } else if (response.body().getStatus() == 401) {
                            Gson gson = new GsonBuilder().create();
                            QuotationResponse mError = new QuotationResponse();
                            try {
                                String s = response.errorBody().string();
                                mError = gson.fromJson(s, QuotationResponse.class);
                                Toast.makeText(getActivity(), mError.getMessage(), Toast.LENGTH_LONG).show();
//                                Globals.logoutScreen(getActivity());
                            } catch (IOException e) {
                                // handle failure to read error
                            }
                        } else {
                            binding.noDatafound.setVisibility(View.VISIBLE);
                            loader.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        loader.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(requireContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new OpenOpportunities_Adapter(Open_Opprtunity_Fragment.this, getContext(), opportunityResposeArrayList_gl);
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

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
                searchView.clearFocus();
                page = 1;
                searchTextValue = query;
                if (!searchTextValue.isEmpty()) {
                    getOpportunityListApi(binding.loader, maxItem, page, "", "", "", "", "");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null)
                    adapter.filter(newText);
                return false;
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.plus:
//                startActivity(new Intent(getContext(), AddOpportunityActivity.class));
                Intent intent = new Intent(getContext(), AddOpportunityActivity.class);
                intent.putExtra("BPCardCodeShortCut", BPCardCode);
                startActivity(intent);
                break;

            case R.id.filter:
                showAllFilterDialog();
                break;


        }
        return true;
    }


    String OppTypeName = "";
    String OppTypeID = "";

    String SourceName = "";
    String SourceCode = "";
    String cardCode = "";
    String CardName = "";
    String fromDate = "";
    String toDate = "";


    private void showAllFilterDialog() {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.opportunity_filter_layout, null);
        dialog.setContentView(custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        AutoCompleteTextView acCustomer = dialog.findViewById(R.id.acCustomer);
        AutoCompleteTextView acOppType = dialog.findViewById(R.id.acOppType);
        AutoCompleteTextView acSource = dialog.findViewById(R.id.acSource);
        TextInputEditText edtFromDate = dialog.findViewById(R.id.edtFromDate);
        TextInputEditText edtToDate = dialog.findViewById(R.id.edtToDate);

        MaterialButton resetBtn = dialog.findViewById(R.id.resetBtn);
        MaterialButton applyBtn = dialog.findViewById(R.id.applyBtn);
        ImageView ivCrossIcon = dialog.findViewById(R.id.ivCrossIcon);

        LinearLayout rlRecyclerViewLayout = dialog.findViewById(R.id.rlRecyclerViewLayout);
        LinearLayout rlOppTypeRecyclerViewLayout = dialog.findViewById(R.id.rlOppTypeRecyclerViewLayout);
        LinearLayout rlSalesLayout = dialog.findViewById(R.id.rlSalesLayout);
        RecyclerView rvCustomerSearchList = dialog.findViewById(R.id.rvCustomerSearchList);
        RecyclerView rvIndustrySearchList = dialog.findViewById(R.id.rvIndustrySearchList);
        RecyclerView rvSourceList = dialog.findViewById(R.id.rvSourceList);

        ivCrossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        acCustomer.setText(CardName);
        acOppType.setText(OppTypeName);
        acSource.setText(SourceName);
        edtFromDate.setText(fromDate);
        edtToDate.setText(toDate);


        callCustomerApi(acCustomer, rlRecyclerViewLayout, rvCustomerSearchList);

        callOppTypeApi(acOppType, rlOppTypeRecyclerViewLayout, rvIndustrySearchList);

        callSourceApi(acSource, rlSalesLayout, rvSourceList);


        edtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.disableFutureDates(getContext(), edtFromDate);

            }
        });


        edtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.enableAllCalenderDateSelect(getContext(), edtToDate);

            }
        });


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acCustomer.setText("");
                acSource.setText("");
                acOppType.setText("");
                edtFromDate.setText("");
                edtToDate.setText("");

                cardCode = "";
                CardName = "";
                OppTypeName = "";
                SourceName = "";
                fromDate = "";
                toDate = "";

            }
        });


        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate = edtFromDate.getText().toString();
                toDate = edtToDate.getText().toString();
                getOpportunityListApi(binding.loader, maxItem, page, cardCode, OppTypeID, edtFromDate.getText().toString(), edtToDate.getText().toString(), SourceName);

                dialog.dismiss();
            }
        });

        dialog.show();
    }


    //todo call customer api here...
    ArrayList<BusinessPartnerAllResponse.Datum> customerList_gl = new ArrayList<>();

    private void callCustomerApi(AutoCompleteTextView acCustomer, LinearLayout rlRecyclerViewLayout, RecyclerView rvCustomerSearchList) {
        Call<BusinessPartnerAllResponse> call = NewApiClient.getInstance().getApiService().getAllParentAccBP();
        call.enqueue(new Callback<BusinessPartnerAllResponse>() {
            @Override
            public void onResponse(Call<BusinessPartnerAllResponse> call, Response<BusinessPartnerAllResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                List<BusinessPartnerAllResponse.Datum> itemsList = response.body().getData();
                                itemsList = filterList(response.body().getData());
                                customerList_gl.clear();
                                customerList_gl.addAll(itemsList);

                                List<String> itemNames = new ArrayList<>();
                                List<String> cardCodeName = new ArrayList<>();
                                for (BusinessPartnerAllResponse.Datum item : customerList_gl) {
                                    itemNames.add(item.getCardName());
                                    cardCodeName.add(item.getCardCode());
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_textview, itemNames);
                                acCustomer.setAdapter(adapter);

                                //todo bill to and ship to address drop down item select..
                                acCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                        cardCode = cardCodeName.get(position);
                                        CardName = (String) parent.getItemAtPosition(position);

                                        int pos = Globals.getCustomerPos(customerList_gl, CardName);
                                        cardCode = customerList_gl.get(pos).getCardCode();

                                        if (CardName.isEmpty()) {
                                            rlRecyclerViewLayout.setVisibility(View.GONE);
                                            rvCustomerSearchList.setVisibility(View.GONE);
                                        } else {
                                            rlRecyclerViewLayout.setVisibility(View.VISIBLE);
                                            rvCustomerSearchList.setVisibility(View.VISIBLE);
                                        }

                                        if (!CardName.isEmpty()) {
                                            adapter.notifyDataSetChanged();
                                            acCustomer.setText(CardName);
                                            acCustomer.setSelection(acCustomer.length());

                                        } else {
                                            acCustomer.setText("");
                                            CardName = "";
                                            cardCode = "";
                                        }
                                    }
                                });


                            } else {
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(requireContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BusinessPartnerAllResponse> call, Throwable t) {
                Toast.makeText(binding.loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<BusinessPartnerAllResponse.Datum> filterList(ArrayList<BusinessPartnerAllResponse.Datum> value) {
        List<BusinessPartnerAllResponse.Datum> tempList = new ArrayList<>();
        for (BusinessPartnerAllResponse.Datum customer : value) {
            if (!customer.getCardName().equals("admin")) {
                tempList.add(customer);
            }
        }
        return tempList;
    }


    List<UTypeData> oppTypeLIst_gl = new ArrayList<>();

    //todo industry api ..
    private void callOppTypeApi(AutoCompleteTextView acOppType, LinearLayout rlOppTypeRecyclerViewLayout, RecyclerView rvIndustrySearchList) {
        Call<BPTypeResponse> call = NewApiClient.getInstance().getApiService().getopptypelist();
        call.enqueue(new Callback<BPTypeResponse>() {
            @Override
            public void onResponse(Call<BPTypeResponse> call, Response<BPTypeResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                List<UTypeData> itemsList = response.body().getData();
                                itemsList = industryFilterList(response.body().getData());
                                oppTypeLIst_gl.clear();
                                oppTypeLIst_gl.addAll(itemsList);

                                List<String> itemNames = new ArrayList<>();
                                List<String> cardCodeName = new ArrayList<>();
                                for (UTypeData item : oppTypeLIst_gl) {
                                    itemNames.add(item.getType());
                                    cardCodeName.add(String.valueOf(item.getId()));
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_textview, itemNames);
                                acOppType.setAdapter(adapter);

                                //todo bill to and ship to address drop down item select..
                                acOppType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String industryName = (String) parent.getItemAtPosition(position);
                                        OppTypeName = industryName;

                                        int pos = Globals.getOppType(oppTypeLIst_gl, industryName);
                                        OppTypeID = String.valueOf(oppTypeLIst_gl.get(pos).getId());

                                        if (industryName.isEmpty()) {
                                            rlOppTypeRecyclerViewLayout.setVisibility(View.GONE);
                                            rvIndustrySearchList.setVisibility(View.GONE);
                                        } else {
                                            rlOppTypeRecyclerViewLayout.setVisibility(View.VISIBLE);
                                            rvIndustrySearchList.setVisibility(View.VISIBLE);
                                        }

                                        if (!industryName.isEmpty()) {
                                            adapter.notifyDataSetChanged();
                                            acOppType.setText(industryName);
                                            acOppType.setSelection(acOppType.length());

                                        } else {
                                            acOppType.setText("");
                                        }
                                    }
                                });


                            } else {
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(requireContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BPTypeResponse> call, Throwable t) {
                Toast.makeText(binding.loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private List<UTypeData> industryFilterList(List<UTypeData> value) {
        List<UTypeData> tempList = new ArrayList<>();
        for (UTypeData customer : value) {
            if (!customer.getType().equals("admin")) {
                tempList.add(customer);
            }
        }
        return tempList;
    }

    ArrayList<LeadTypeData> sourceData = new ArrayList<>();


    //todo call source api here..
    private void callSourceApi(AutoCompleteTextView acSource, LinearLayout rlSalesLayout, RecyclerView rvSourceList) {
        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {

                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                List<LeadTypeData> itemsList = response.body().getData();
                                itemsList = sourceFilterList(response.body().getData());
                                sourceData.clear();
                                sourceData.addAll(itemsList);

                                List<String> itemNames = new ArrayList<>();
                                List<String> cardCodeName = new ArrayList<>();
                                for (LeadTypeData item : sourceData) {
                                    itemNames.add(item.getName());
                                    cardCodeName.add(String.valueOf(item.getId()));
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_textview, itemNames);
                                acSource.setAdapter(adapter);

                                //todo bill to and ship to address drop down item select..
                                acSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String sourceName = (String) parent.getItemAtPosition(position);
                                        SourceName = sourceName;

                                        int pos = Globals.getSourcePos(sourceData, sourceName);
                                        SourceCode = String.valueOf(sourceData.get(pos).getId());

                                        if (sourceName.isEmpty()) {
                                            rlSalesLayout.setVisibility(View.GONE);
                                            rvSourceList.setVisibility(View.GONE);
                                        } else {
                                            rlSalesLayout.setVisibility(View.VISIBLE);
                                            rvSourceList.setVisibility(View.VISIBLE);
                                        }

                                        if (!sourceName.isEmpty()) {
                                            adapter.notifyDataSetChanged();
                                            acSource.setText(sourceName);
                                            acSource.setSelection(acSource.length());

                                        } else {
                                            acSource.setText("");
                                        }
                                    }
                                });


                            } else {
                                Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(requireContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private List<LeadTypeData> sourceFilterList(List<LeadTypeData> value) {
        List<LeadTypeData> tempList = new ArrayList<>();
        for (LeadTypeData customer : value) {
            if (!customer.getName().equals("admin")) {
                tempList.add(customer);
            }
        }
        return tempList;
    }



    private void showSourceDialog() {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.dialog_recycler, null);
        dialog.setContentView(custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerview = dialog.findViewById(R.id.dist);
        Button done = dialog.findViewById(R.id.done);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        SourceAdpater adpater = new SourceAdpater(getContext(), sourceData);
        recyclerview.setAdapter(adpater);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter != null) {
                    adapter.sourcefilter(Globals.sourcechecklist);
                }
                Log.e("list==>", String.valueOf(Globals.sourcechecklist));
                dialog.cancel();
            }
        });

        dialog.show();
    }


    @Override
    public void onRefresh() {
        if (Globals.checkInternet(getActivity())) {
            binding.loader.setVisibility(View.VISIBLE);
            getOpportunityListApi(binding.loader, maxItem, page, "", "", "", "", "");
        }
    }


    @Override
    public void stagecomment(String id, String name) {
        dialog.cancel();
        if (adapter != null) {
            adapter.Typefilter(id);
            if (adapter.getItemCount() == 0)
                binding.noDatafound.setVisibility(View.VISIBLE);
            else
                binding.noDatafound.setVisibility(View.GONE);
        }
    }

}