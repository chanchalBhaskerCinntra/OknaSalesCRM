package com.cinntra.okana.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.bpActivity.AddBPCustomer;
import com.cinntra.okana.adapters.CountryAdapter;
import com.cinntra.okana.adapters.CountryAutoAdapter;
import com.cinntra.okana.adapters.IndustrySpinnerAdapter;
import com.cinntra.okana.adapters.LeadTypeAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAdapter;
import com.cinntra.okana.adapters.StateAdapter;
import com.cinntra.okana.animation.ViewAnimationUtils;
import com.cinntra.okana.databinding.AddCampaignBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.AddCampaignModel;
import com.cinntra.okana.model.CampaignResponse;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.CountryResponse;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCampaign extends MainBaseActivity implements View.OnClickListener {

    CountryAdapter countryAdapter;
    String Countrycode = "";
    String CountryName = "";
    String StateName = "";
    String StateCode = "";
    String industryCode = "";
    String sourcetype = "";
    String leadtype = "";
    ArrayList<LeadTypeData> sourceData = new ArrayList<>();
    ArrayList<LeadTypeData> leadTypeData = new ArrayList<>();
    List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    String CustomerType, OppType, CampaignAccess = "--None--";
    String OppsalesEmployeeCode, OppsalesEmployeename, CustomersalesEmployeeCode, CustomersalesEmployeename;
    String status = "Follow Up";
    StateAdapter stateAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    AddCampaignBinding binding;

    ArrayList<StateData> billStateList = new ArrayList<>();
    ArrayList<StateData> shipstateList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddCampaignBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  ButterKnife.bind(this);
        eventmanager();

        binding.headerBottomRounded.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        callCountryApi();

    }

    private void eventmanager() {

        binding.headerBottomRounded.headTitle.setText("Add Campaign Set");
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.fromDate.setOnClickListener(this);
        binding.toDate.setOnClickListener(this);
        binding.oppfromDate.setOnClickListener(this);
        binding.opptoDate.setOnClickListener(this);
        binding.customerfromDate.setOnClickListener(this);
        binding.customertoDate.setOnClickListener(this);
        binding.create.setOnClickListener(this);


        binding.allOpp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ViewAnimationUtils.collapse(binding.oppView);
                else
                    ViewAnimationUtils.expand(binding.oppView);
            }
        });

        binding.allLead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ViewAnimationUtils.collapse(binding.leadView);
                else
                    ViewAnimationUtils.expand(binding.leadView);
            }
        });
        binding.allBp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ViewAnimationUtils.collapse(binding.bPview);
                else
                    ViewAnimationUtils.expand(binding.bPview);
            }
        });


        callleadTypeApi();


        binding.acBillToState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (billStateList.size() > 0){

                    binding.acBillToState.setText(billStateList.get(position).getName());
                    StateCode = billStateList.get(position).getCode();
                    StateName = billStateList.get(position).getName();

                }else {
                    StateCode = "";
                    StateName = "";
                    binding.acBillToState.setText("");
                }
            }
        });

        binding.customertypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomerType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CustomerType = parent.getSelectedItem().toString();
            }
        });


        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status = parent.getSelectedItem().toString();
            }
        });

        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OppType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                OppType = parent.getSelectedItem().toString();
            }
        });


        binding.campaignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CampaignAccess = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CampaignAccess = parent.getSelectedItem().toString();
            }
        });


        binding.prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leadtype = leadTypeData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                leadtype = leadTypeData.get(0).getName();
            }
        });


        binding.sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourcetype = sourceData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sourcetype = sourceData.get(0).getName();
            }
        });

        binding.industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                industryCode = IndustryItemItemList.get(position).getIndustryCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                industryCode = IndustryItemItemList.get(0).getIndustryCode();
            }
        });


    }


    //todo calling country api here...

    ArrayList<CountryData> countyList = new ArrayList<>();

    private void callCountryApi() {
        Call<CountryResponse> call = NewApiClient.getInstance().getApiService().getCountryList();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.body().getStatus() == 200) {
                    if (response.body().getData().size() > 0) {
                        countyList.clear();
                        List<CountryData> itemsList = response.body().getData();
                        itemsList = filterList(response.body().getData());
                        countyList.addAll(itemsList);

                        CountryAutoAdapter countryAutoAdapter = new CountryAutoAdapter(AddCampaign.this, R.layout.drop_down_textview, countyList);

                        //todo set bill state..
                        binding.acCountry.setAdapter(countryAutoAdapter);
                        countryAutoAdapter.notifyDataSetChanged();

                        //todo bill to and ship to address drop down item select..
                        binding.acCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {

                                    if (countyList.size() > 0) {

                                        binding.acCountry.setText(countyList.get(position).getName());
                                        binding.acCountry.setSelection(binding.acCountry.length());
                                        Countrycode = countyList.get(position).getCode();
                                        CountryName = countyList.get(position).getName();

                                        callBillToStateApi(Countrycode);
                                    } else {
                                        CountryName = "";
                                        Countrycode = "";
                                        binding.acCountry.setText("");
                                    }
                                } catch (Exception e) {
                                    Log.e("catch", "onItemClick: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });

                    }

                } else {
                    Toasty.error(AddCampaign.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(AddCampaign.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<CountryData> filterList(List<CountryData> value) {
        List<CountryData> tempList = new ArrayList<>();
        for (CountryData customer : value) {
            if (!customer.getName().equals("admin")) {
                tempList.add(customer);
            }
        }
        return tempList;
    }


    //todo calling bill To state api --
    private void callBillToStateApi(String countryCode) {
        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.body().getStatus() == 200) {
                    billStateList.clear();
                    if (response.body().getData().size() > 0) {
                        billStateList.addAll(response.body().getData());
                        StateAdapter stateAdapter = new StateAdapter(AddCampaign.this, R.layout.drop_down_textview, billStateList);

                        //todo set bill state..
                        binding.acBillToState.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();

                    }

                }
                else if (response.body().getStatus() == 201){
                    Globals.showMessage(AddCampaign.this, response.body().getMessage());
                }
                else if (response.body().getStatus() == 500){
                    Globals.showMessage(AddCampaign.this, response.body().getMessage());
                }else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    StateRespose mError = new StateRespose();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, StateRespose.class);
                        Toast.makeText(AddCampaign.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callleadTypeApi() {

        //   Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getLeadType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if (response.code() == 200) {
//                    leadTypeData.clear();
//                    leadTypeData.addAll(response.body().getData());
//                    priority_spinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, leadTypeData));
//                    leadtype = leadTypeData.get(0).getName();
//                } else {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s = response.errorBody().string();
//                        mError = gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(AddCampaign.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//
//                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        leadTypeData.clear();
        leadTypeData.addAll(MainActivity.leadTypeListFromLocal);
        binding.prioritySpinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, leadTypeData));
        leadtype = leadTypeData.get(0).getName();

        callSourceApi();
    }


    private void callSourceApi() {


//        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
//        call.enqueue(new Callback<LeadTypeResponse>() {
//            @Override
//            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
//
//                if (response.code() == 200) {
//                    sourceData.clear();
//                    sourceData.addAll(response.body().getData());
//                    source_spinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, sourceData));
//                    sourcetype = sourceData.get(0).getName();
//                } else {
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    LeadResponse mError = new LeadResponse();
//                    try {
//                        String s = response.errorBody().string();
//                        mError = gson.fromJson(s, LeadResponse.class);
//                        Toast.makeText(AddCampaign.this, mError.getMessage(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//
//                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        sourceData.clear();
        sourceData.addAll(MainActivity.leadSourceListFromLocal);
        binding.sourceSpinner.setAdapter(new LeadTypeAdapter(AddCampaign.this, sourceData));
        sourcetype = sourceData.get(0).getName();


        callIndustryApi();
    }


    List<IndustryItem> IndustryItemItemList = new ArrayList<>();

    private void callIndustryApi() {
//    {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getIndustryList().observe(this, new Observer<List<IndustryItem>>() {
//            @Override
//            public void onChanged(@Nullable List<IndustryItem> itemsList) {
//                if(itemsList == null || itemsList.size()== 0){
//                    Globals.setmessage(AddCampaign.this);
//                }else {
//                    IndustryItemItemList = itemsList;
//                    industry_spinner.setAdapter(new IndustrySpinnerAdapter(AddCampaign.this,itemsList));
//                    industryCode = IndustryItemItemList.get(0).getIndustryCode();
//
//                }
//            }
//        });

        if (MainActivity.industryListFromLocal == null || MainActivity.industryListFromLocal.size() == 0) {
            Globals.setmessage(this);
        } else {
            IndustryItemItemList = MainActivity.industryListFromLocal;
            binding.industrySpinner.setAdapter(new IndustrySpinnerAdapter(this, MainActivity.industryListFromLocal));
            industryCode = IndustryItemItemList.get(0).getIndustryCode();

        }


        callSalesEmployeeApi();
    }

    private void callSalesEmployeeApi() {

        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(AddCampaign.this, new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(AddCampaign.this);
                } else {
                    salesEmployeeItemList = itemsList;
                    binding.salesemployeeSpinner.setAdapter(new SalesEmployeeAdapter(AddCampaign.this, itemsList));
                    binding.customersalesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(AddCampaign.this, itemsList));
                    OppsalesEmployeeCode = salesEmployeeItemList.get(0).getSalesEmployeeCode();
                    OppsalesEmployeename = salesEmployeeItemList.get(0).getSalesEmployeeName();
                    CustomersalesEmployeeCode = salesEmployeeItemList.get(0).getSalesEmployeeCode();
                    CustomersalesEmployeename = salesEmployeeItemList.get(0).getSalesEmployeeName();

                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                onBackPressed();
                break;
            case R.id.from_date:
                Globals.selectDate(this, binding.fromDate);
                break;
            case R.id.to_date:
                Globals.selectDate(this, binding.toDate);
                break;
            case R.id.oppfrom_date:
                Globals.selectDate(this, binding.oppfromDate);
                break;
            case R.id.oppto_date:
                Globals.selectDate(this, binding.opptoDate);
                break;
            case R.id.customerfrom_date:
                Globals.selectDate(this, binding.customerfromDate);
                break;
            case R.id.customerto_date:
                Globals.selectDate(this, binding.customertoDate);
                break;
            case R.id.create:

                if (validation(CampaignAccess, binding.companyname, binding.description)) {
                    AddCampaignModel campaignModel = new AddCampaignModel();
                    campaignModel.setCampaignSetName(binding.companyname.getText().toString());
                    campaignModel.setCampaignAccess(CampaignAccess);
                    campaignModel.setDescription(binding.description.getText().toString());
                    campaignModel.setLeadSource(sourcetype);
                    campaignModel.setLeadPriority(leadtype);
                    campaignModel.setLeadFromDate(binding.fromDate.getText().toString());
                    campaignModel.setLeadToDate(binding.toDate.getText().toString());
                    campaignModel.setBPFromDate(binding.customerfromDate.getText().toString());
                    campaignModel.setBPToDate(binding.customertoDate.getText().toString());
                    campaignModel.setOppFromDate(binding.oppfromDate.getText().toString());
                    campaignModel.setOppToDate(binding.opptoDate.getText().toString());
                    campaignModel.setLeadStatus(status);
                    campaignModel.setOppType(OppType);
                    campaignModel.setBPType(CustomerType);

                    if (!StateName.equalsIgnoreCase("") && !StateCode.equalsIgnoreCase("")) {
                        campaignModel.setBPState(StateName);
                        campaignModel.setBPStateCode(StateCode);
                    }else {
                        campaignModel.setBPState("");
                        campaignModel.setBPStateCode("");
                    }
                    if (!CountryName.equalsIgnoreCase("") && !Countrycode.equalsIgnoreCase("")) {
                        campaignModel.setBPCountry(CountryName);
                        campaignModel.setBPCountryCode(Countrycode);
                    }else {
                        campaignModel.setBPCountry("India");
                        campaignModel.setBPCountryCode("IN");
                    }

                    campaignModel.setCreateDate(Globals.getTodaysDatervrsfrmt());
                    campaignModel.setCreateTime(Globals.getTCurrentTime());
                    campaignModel.setBPIndustry(industryCode);
                    campaignModel.setOppSalePerson(OppsalesEmployeeCode);
                    campaignModel.setBPSalePerson(CustomersalesEmployeeCode);
                    campaignModel.setCreateBy(Prefs.getString(Globals.SalesEmployeeCode, ""));
                    campaignModel.setCampaignSetOwner(Prefs.getString(Globals.SalesEmployeeCode, ""));
                    campaignModel.setMemberList("");
                    campaignModel.setOppStage("");
                    campaignModel.setStatus(1);
                    campaignModel.setAllBP(binding.allBp.isChecked() ? 1 : 0);
                    campaignModel.setAllLead(binding.allLead.isChecked() ? 1 : 0);
                    campaignModel.setAllOpp(binding.allOpp.isChecked() ? 1 : 0);

                    if (Globals.checkInternet(AddCampaign.this))
                        createnewCampaign(campaignModel);


                }


                break;
        }
    }

    private void createnewCampaign(AddCampaignModel campaignModel) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(campaignModel);
        Log.e("data", jsonTut);
        Call<CampaignResponse> call = NewApiClient.getInstance().getApiService().createCampaign(campaignModel);
        call.enqueue(new Callback<CampaignResponse>() {
            @Override
            public void onResponse(Call<CampaignResponse> call, Response<CampaignResponse> response) {

                if (response.code() == 200) {

                    Toasty.success(AddCampaign.this, "Posted Successfull", Toasty.LENGTH_LONG).show();
                    onBackPressed();


                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddCampaign.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CampaignResponse> call, Throwable t) {

                Toast.makeText(AddCampaign.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validation(String campaignAccess, EditText companyname, EditText description) {
        if (campaignAccess.equalsIgnoreCase("--None--")) {
            Toasty.warning(this, "Select Campaign Access", Toasty.LENGTH_SHORT).show();
            return false;
        } else if (companyname.length() == 0) {
            Toasty.warning(this, "Enter Campaign Set Name", Toasty.LENGTH_SHORT).show();
            return false;
        } else if (description.length() == 0) {
            Toasty.warning(this, "Enter Description", Toasty.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
