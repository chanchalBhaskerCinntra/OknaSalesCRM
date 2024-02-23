package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.bpActivity.AddBPCustomer;
import com.cinntra.okana.activities.BussinessPartners;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.adapters.BPStateFilterAdapter;
import com.cinntra.okana.adapters.BPTypeFilterAdapter;
import com.cinntra.okana.adapters.CustomersAdapter;
import com.cinntra.okana.adapters.CustomersAdapterDetals;
import com.cinntra.okana.adapters.StateAutoAdapter;
import com.cinntra.okana.databinding.FragmentCustomerLeadBinding;
import com.cinntra.okana.globals.Globals;

import com.cinntra.okana.interfaces.CommentStage;
import com.cinntra.okana.model.BPModel.BPAllFilterRequestModel;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllResponse;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.BPModel.demoListModel;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.IndustryResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.material.button.MaterialButton;
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


public class CustomersFragment extends Fragment implements CommentStage {
    int currentPage = 0;
    private SearchView searchView;
    boolean recallApi = true;
    LinearLayoutManager layoutManager;
    private Context mContext;
    FragmentCustomerLeadBinding binding;

    int page = 1;
    Boolean apicall = true;
    Boolean isScrollingpage = false;
    int maxItem = 10;
    private String searchTextValue = "";

    public CustomersFragment() {
        // Required empty public constructor
    }

    ArrayList<BusinessPartnerData> AllitemsList;
    ArrayList<demoListModel.Datum> businessPartnerList_gl = new ArrayList<>();
    ArrayList<BusinessPartnerAllResponse.Datum> allBpList_gl = new ArrayList<>();

    // TODO: Rename and change types and number of parameters
    public static CustomersFragment newInstance(String param1, String param2) {
        CustomersFragment fragment = new CustomersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerLeadBinding.inflate(getLayoutInflater());
        mContext = getActivity();
        AllitemsList = new ArrayList<>();
      /* if (Globals.checkInternet(getActivity())) {
           loader.setVisibility(View.VISIBLE);
           callApi(loader);
       }*/

        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        calltypeapi();
//        callStateApi(acState);

        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchTextValue="";
                salesEmployeeCode = "";
                IndustryCode = "";

                if (Globals.checkInternet(getActivity())) {
                    apicall = true;
                    page = 1;
                    callApi(binding.loaderLayout.loader, maxItem, page, "", "");
                } else {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }

            }
        });


        //todo pagination for list..
        binding.customerLeadList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompletelyVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (isScrollingpage && lastCompletelyVisibleItemPosition == businessPartnerList_gl.size() - 2  && apicall) {
                    page++;
                    Log.e("page--->", String.valueOf(page));
                    callApi(binding.loaderLayout.loader, maxItem, page, "", "");
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



        return binding.getRoot();
    }

    @Override
    public void onResume() {
        if (Globals.checkInternet(getActivity())) {
            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
            callApi(binding.loaderLayout.loader, maxItem, page, "", "");
            setAdapter();
        }
        super.onResume();
    }


    private CustomersAdapterDetals dadapter;
    private CustomersAdapter adapter;


    //todo calling bp all list..
    private void callApi(ProgressBar loader, int maxItem, int page, String industryCode, String salesEmployeeCode) {
        loader.setVisibility(View.VISIBLE);
        if (Prefs.getString(Globals.BussinessPageType, "").equalsIgnoreCase("DashBoard")) {
            BPAllFilterRequestModel requestModel = new BPAllFilterRequestModel();
            requestModel.setSalesPersonCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
            requestModel.setPageNo(page);
            requestModel.setMaxItem(maxItem);
            requestModel.setSearchText(searchTextValue);
            BPAllFilterRequestModel.Field field = new BPAllFilterRequestModel.Field();
            field.setCardType("");
            field.setIndustry(industryCode);
            field.setSalesPersonPerson(salesEmployeeCode);//solIdName
            requestModel.setField(field);


            Call<demoListModel> call = NewApiClient.getInstance().getApiService().getBPAllPageList(requestModel);
            call.enqueue(new Callback<demoListModel>() {
                @Override
                public void onResponse(Call<demoListModel> call, Response<demoListModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                if (response.body().getData() != null && response.body().getData().size() > 0) {
                                    loader.setVisibility(View.GONE);

                                    List<demoListModel.Datum> itemsList = response.body().getData();

                                    binding.noDatafound.setVisibility(View.GONE);
                                    if (page == 1) {
                                        businessPartnerList_gl.clear();
                                        businessPartnerList_gl.addAll(itemsList);
                                        dadapter.AllData(itemsList);
                                    } else {
                                        businessPartnerList_gl.addAll(itemsList);
                                        dadapter.AllData(itemsList);
                                    }

                                    binding.swipeRefreshLayout.setRefreshing(false);
                                    dadapter.notifyDataSetChanged();

                                    if (itemsList.size() < 10)
                                        apicall = false;

                                } else {
                                    binding.customerLeadList.setAdapter(null);
                                    loader.setVisibility(View.GONE);
                                    binding.noDatafound.setVisibility(View.VISIBLE);
                                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                        loader.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<demoListModel> call, Throwable t) {
                    loader.setVisibility(View.GONE);
                    Log.e(t.getMessage(), "onFailure: "+t.getMessage() );
                    Toast.makeText(loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        else {
            Call<BusinessPartnerAllResponse> call = NewApiClient.getInstance().getApiService().getAllParentAccBP();
            call.enqueue(new Callback<BusinessPartnerAllResponse>() {
                @Override
                public void onResponse(Call<BusinessPartnerAllResponse> call, Response<BusinessPartnerAllResponse> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                if (response.body().getData() != null && response.body().getData().size() > 0) {
                                    loader.setVisibility(View.GONE);
                                    List<BusinessPartnerAllResponse.Datum> itemsList = response.body().getData();
                                    binding.noDatafound.setVisibility(View.GONE);
                                    if (page == 1) {
                                        allBpList_gl.clear();
                                        allBpList_gl.addAll(itemsList);
                                        adapter.AllData(itemsList);
                                    } else {
                                        allBpList_gl.addAll(itemsList);
                                        adapter.AllData(itemsList);
                                    }

                                    binding.swipeRefreshLayout.setRefreshing(false);
                                    adapter.notifyDataSetChanged();

                                    if (itemsList.size() < 10)
                                        apicall = false;

                                } else {
                                    binding.customerLeadList.setAdapter(null);
                                    loader.setVisibility(View.GONE);
                                    binding.noDatafound.setVisibility(View.VISIBLE);
                                    Toast.makeText(requireContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                        loader.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<BusinessPartnerAllResponse> call, Throwable t) {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.customerLeadList.setLayoutManager(layoutManager);
        if (Prefs.getString(Globals.BussinessPageType, "").equalsIgnoreCase("DashBoard")) {
            dadapter = new CustomersAdapterDetals(getActivity(), businessPartnerList_gl);
            binding.customerLeadList.setAdapter(dadapter);
            dadapter.notifyDataSetChanged();
        } else {
            adapter = new CustomersAdapter(getActivity(), allBpList_gl);
            binding.customerLeadList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } //todo comment by me
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        //menu.clear();
        inflater.inflate(R.menu.bussiness_filter, menu);

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView(((BussinessPartners) mContext).getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Search Customers");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                page = 1;
                searchTextValue = query;
                if (!searchTextValue.isEmpty())
                    callApi(binding.loaderLayout.loader, maxItem, page, "", "");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (Prefs.getString(Globals.BussinessPageType, "").equalsIgnoreCase("DashBoard")) {
                    if (dadapter != null)
                        dadapter.filter(newText);

                } else {
                    if (adapter != null)
                        adapter.filter(newText);
                }
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.plus:
                Prefs.putString(Globals.AddBp, "");
                startActivity(new Intent(getContext(), AddBPCustomer.class));
                break;
            case R.id.filter:
                showAllFilterDialog();
                break;
           /* case R.id.all:
                item.setChecked(!item.isChecked());
                if (dadapter != null)
//                    dadapter.AllData();
                if (adapter != null)
                    adapter.AllData();
                break;*/
            case R.id.my:
                item.setChecked(!item.isChecked());
                opentypedialog();
               /* if(dadapter!=null)
                    dadapter.Customerfilter();

                if(adapter!=null)
                    adapter.Customerfilter();*/
                break;
            case R.id.my_team:
                openstatedialog();
                break;
            /*case R.id.newest:
                item.setChecked(!item.isChecked());
                if(dadapter!=null)
                    dadapter.Typefilter("New Business");
                if(adapter!=null)
                    adapter.Typefilter("New Business");
                break;
            case R.id.oldest:
                item.setChecked(!item.isChecked());
                if(dadapter!=null)
                    dadapter.Typefilter("Existing Business");
                if(adapter!=null)
                    adapter.Typefilter("Existing Business");
                break;
*/
        }
        return true;
    }



    String solIdName = "";
    String salesEmployeeCode = "";
    String SalesEmployeeName = "";
    String IndustryName = "";
    String IndustryCode = "";


    private void showAllFilterDialog() {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.customer_filter_layout, null);
        dialog.setContentView(custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        AutoCompleteTextView acIndustry = dialog.findViewById(R.id.acIndustry);
        AutoCompleteTextView acSalesEmployee = dialog.findViewById(R.id.acSalesEmployee);
        MaterialButton resetBtn = dialog.findViewById(R.id.resetBtn);
        MaterialButton applyBtn = dialog.findViewById(R.id.applyBtn);
        ImageView ivCrossIcon = dialog.findViewById(R.id.ivCrossIcon);
        LinearLayout rlRecyclerViewLayout = dialog.findViewById(R.id.rlRecyclerViewLayout);
        LinearLayout rlSalesLayout = dialog.findViewById(R.id.rlSalesLayout);
        RecyclerView rvIndustrySearchList = dialog.findViewById(R.id.rvIndustrySearchList);
        RecyclerView rvSalesEmployeeList = dialog.findViewById(R.id.rvSalesEmployeeList);

        ivCrossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        acIndustry.setText(IndustryName);
        acSalesEmployee.setText(SalesEmployeeName);


        callSalesEmployeeApi(acSalesEmployee, rlSalesLayout, rvSalesEmployeeList);

//        callStateApi(acState);
        callIndustryListApi(acIndustry, rlRecyclerViewLayout, rvIndustrySearchList);



        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acIndustry.setText("");
                acSalesEmployee.setText("");

                SalesEmployeeName = "";
                IndustryName = "";

            }
        });


        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi(binding.loaderLayout.loader, maxItem, page, IndustryCode, salesEmployeeCode);

                dialog.dismiss();
            }
        });

        dialog.show();
    }



    List<IndustryItem> industryList_gl = new ArrayList<>();

    //todo industry api ..
    private void callIndustryListApi(AutoCompleteTextView acIndustry, LinearLayout rlRecyclerViewLayout, RecyclerView rvIndustrySearchList) {
        Call<IndustryResponse> call = NewApiClient.getInstance().getApiService().getIndustryList();
        call.enqueue(new Callback<IndustryResponse>() {
            @Override
            public void onResponse(Call<IndustryResponse> call, Response<IndustryResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            if (response.body().getValue() != null && response.body().getValue().size() > 0) {

                                List<IndustryItem> itemsList = response.body().getValue();
                                itemsList = industryFilterList(response.body().getValue());
                                industryList_gl.clear();
                                industryList_gl.addAll(itemsList);

                                List<String> itemNames = new ArrayList<>();
                                List<String> cardCodeName = new ArrayList<>();
                                for (IndustryItem item : industryList_gl) {
                                    itemNames.add(item.getIndustryName());
                                    cardCodeName.add(item.getIndustryCode());
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_textview, itemNames);
                                acIndustry.setAdapter(adapter);

                                //todo bill to and ship to address drop down item select..
                                acIndustry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String industryName = (String) parent.getItemAtPosition(position);
                                        IndustryName = industryName;

                                        int pos = Globals.getIndustryPos(industryList_gl, industryName);
                                        IndustryCode = industryList_gl.get(pos).getIndustryCode();

                                        if (industryName.isEmpty()) {
                                            rlRecyclerViewLayout.setVisibility(View.GONE);
                                            rvIndustrySearchList.setVisibility(View.GONE);
                                        } else {
                                            rlRecyclerViewLayout.setVisibility(View.VISIBLE);
                                            rvIndustrySearchList.setVisibility(View.VISIBLE);
                                        }

                                        if (!industryName.isEmpty()) {
                                            adapter.notifyDataSetChanged();
                                            acIndustry.setText(industryName);
                                            acIndustry.setSelection(acIndustry.length());

                                        }else {
                                            acIndustry.setText("");
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
            public void onFailure(Call<IndustryResponse> call, Throwable t) {
                Toast.makeText(binding.loaderLayout.loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private List<IndustryItem> industryFilterList(ArrayList<IndustryItem> value) {
        List<IndustryItem> tempList = new ArrayList<>();
        for (IndustryItem customer : value) {
            if (!customer.getIndustryName().equals("admin")) {
                tempList.add(customer);
            }
        }
        return tempList;
    }




    //todo call customer api here...
    ArrayList<SalesEmployeeItem> salesEmployeeItemList_gl = new ArrayList<>();

    private void callSalesEmployeeApi(AutoCompleteTextView acSalesEmployee, LinearLayout rlSalesLayout, RecyclerView rvSalesEmployeeList) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SalesEmployeeCode", Prefs.getString(Globals.SalesEmployeeCode, ""));

        EmployeeValue employeeValue = new EmployeeValue();
        employeeValue.setSalesEmployeeCode(Prefs.getString(Globals.SalesEmployeeCode, ""));

        Call<SaleEmployeeResponse> call = NewApiClient.getInstance().getApiService().getSalesEmplyeeList(employeeValue);
        call.enqueue(new Callback<SaleEmployeeResponse>() {
            @Override
            public void onResponse(Call<SaleEmployeeResponse> call, Response<SaleEmployeeResponse> response) {

                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            if (response.body().getValue() != null && response.body().getValue().size() > 0) {

                                List<SalesEmployeeItem> SalesEmployeeList = new ArrayList<>();
                                SalesEmployeeList = filterList(response.body().getValue());
                                salesEmployeeItemList_gl.clear();
                                salesEmployeeItemList_gl.addAll(SalesEmployeeList);

                                List<String> itemNames = new ArrayList<>();
                                List<String> cardCodeName = new ArrayList<>();
                                for (SalesEmployeeItem item : salesEmployeeItemList_gl) {
                                    itemNames.add(item.getSalesEmployeeName());
                                    cardCodeName.add(item.getSalesEmployeeCode());
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_textview, itemNames);
                                acSalesEmployee.setAdapter(adapter);

                                //todo bill to and ship to address drop down item select..
                                acSalesEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String salesEmployeeName = (String) parent.getItemAtPosition(position);

                                        SalesEmployeeName = salesEmployeeName;

                                        int pos = Globals.getSalesEmployeePOs(salesEmployeeItemList_gl, salesEmployeeName);
                                        salesEmployeeCode = salesEmployeeItemList_gl.get(pos).getSalesEmployeeCode();

                                        if (salesEmployeeName.isEmpty()) {
                                            rlSalesLayout.setVisibility(View.GONE);
                                            rvSalesEmployeeList.setVisibility(View.GONE);
                                        } else {
                                            rlSalesLayout.setVisibility(View.VISIBLE);
                                            rvSalesEmployeeList.setVisibility(View.VISIBLE);
                                        }

                                        if (!salesEmployeeName.isEmpty()) {
                                            adapter.notifyDataSetChanged();
                                            acSalesEmployee.setText(salesEmployeeName);
                                            acSalesEmployee.setSelection(acSalesEmployee.length());

                                        }else {
                                            acSalesEmployee.setText("");
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
            public void onFailure(Call<SaleEmployeeResponse> call, Throwable t) {
                Log.e("Api_Failure===>", t.getMessage());
            }
        });

    }


    private List<SalesEmployeeItem> filterList(ArrayList<SalesEmployeeItem> value) {
        List<SalesEmployeeItem> tempList = new ArrayList<>();
        for (SalesEmployeeItem customer : value) {
            if (!customer.getSalesEmployeeName().equals("foo")) {
                tempList.add(customer);
            }
        }
        return tempList;
    }


    private void openstatedialog() {
        dialog = new Dialog(getContext());
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.dialog_recycler, null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerview = dialog.findViewById(R.id.dist);
        Button done = dialog.findViewById(R.id.done);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new BPStateFilterAdapter(getContext(), CustomersFragment.this, stateList));
        done.setVisibility(View.GONE);

       /* done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Log.e("list==>", String.valueOf(Globals.sourcechecklist));
                dialog.cancel();
            }
        });
*/
        dialog.show();
    }

    Dialog dialog;

    private void opentypedialog() {
        dialog = new Dialog(getContext());
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.dialog_recycler, null);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        RecyclerView recyclerview = dialog.findViewById(R.id.dist);
        Button done = dialog.findViewById(R.id.done);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(new BPTypeFilterAdapter(getContext(), CustomersFragment.this, utypelist));
        done.setVisibility(View.GONE);

       /* done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Log.e("list==>", String.valueOf(Globals.sourcechecklist));
                dialog.cancel();
            }
        });
*/
        dialog.show();
    }


    ArrayList<StateData> stateList = new ArrayList<>();

    private void callStateApi(AutoCompleteTextView acState) {

        StateData stateData = new StateData();
        stateData.setCountry("IN");
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.body().getStatus() == 200) {
                    stateList.clear();
                    stateList.addAll(response.body().getData());

                    acState.setAdapter(new StateAutoAdapter(getActivity(), R.layout.drop_down_textview, stateList));
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(getContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    List<UTypeData> utypelist = new ArrayList<>();

    private void calltypeapi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getBpTypeList().observe(getActivity(), new Observer<List<UTypeData>>() {
//            @Override
//            public void onChanged(@Nullable List<UTypeData> itemsList) {
//                if(itemsList == null || itemsList.size()== 0){
//
//                }else {
//                    utypelist = itemsList;
//
//                }
//            }
//        });

        utypelist = MainActivity.itemBpTypeDataFromLocal;
    }


    @Override
    public void stagecomment(String name, String id) {
        if (id.equalsIgnoreCase("Type")) {
            if (dadapter != null) {
                dadapter.Typefilter(name);
                if (dadapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }

            if (adapter != null) {
                adapter.Typefilter(name);
                if (adapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }
        } else if (id.equalsIgnoreCase("State")) {
            if (dadapter != null) {
                dadapter.StateFilter(name);
                if (dadapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }

            if (adapter != null) {
                adapter.StateFilter(name);
                if (adapter.getItemCount() == 0)
                    binding.noDatafound.setVisibility(View.VISIBLE);
                else
                    binding.noDatafound.setVisibility(View.GONE);
            }
        }
        dialog.cancel();
    }
}