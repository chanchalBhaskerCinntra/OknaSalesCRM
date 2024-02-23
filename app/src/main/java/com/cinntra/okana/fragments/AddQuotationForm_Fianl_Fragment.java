package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddQuotationAct;

import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.adapters.CountryAdapter;
import com.cinntra.okana.adapters.StateAdapter;
import com.cinntra.okana.databinding.FragmentAddQtFinalBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.SubmitQuotation;
import com.cinntra.okana.model.AddressExtension;
import com.cinntra.okana.model.AddressExtensions;
import com.cinntra.okana.model.BPAddress;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.CountryResponse;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.room.CountriesDatabase;
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


public class AddQuotationForm_Fianl_Fragment extends Fragment {

    FragmentAddQtFinalBinding binding;

    SubmitQuotation submitQuotation;

    String[] shippinngType;
//    String[] billingType;
    String billshipType;
    String ship_shiptype;
    String billtoState, billtoStateCode, billtoCountrycode, billtoCountryName, shiptoState, shiptoCountrycode, shiptoCountryName, shiptoStateCode;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    ArrayList<StateData> shipstateList = new ArrayList<>();

    NewOpportunityRespose oppItemLines = new NewOpportunityRespose();
    String mCardCode = "";


    public AddQuotationForm_Fianl_Fragment(NewOpportunityRespose quotationItem1, String mCardCode) {
        oppItemLines = quotationItem1;
        this.mCardCode = mCardCode;
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddQuotationForm_Fianl_Fragment newInstance(String param1, String param2, NewOpportunityRespose quotationItem1, String mCardCode) {
        AddQuotationForm_Fianl_Fragment fragment = new AddQuotationForm_Fianl_Fragment(quotationItem1, mCardCode);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddQtFinalBinding.inflate(getLayoutInflater());
//        View v = inflater.inflate(R.layout.fragment_add_qt_final, container, false);
//        ButterKnife.bind(this, v);

        shippinngType = getResources().getStringArray(R.array.bpShippingType);
        ship_shiptype = shippinngType[0];
//        billingType = getResources().getStringArray(R.array.bpBillingType);
        billshipType = shippinngType[0];

        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_quotation));

        binding.quotationPreparedForContent.addressSection.checkboxManager.setVisibility(View.VISIBLE);
        submitQuotation = (SubmitQuotation) getActivity();

        if (!mCardCode.equalsIgnoreCase("") || mCardCode != null) {
            callBpAddressAPI();
        }


        //todo calling country api here---
        callCountryApi();

        eventManager();

        binding.quotationPreparedForContent.addressSection.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (billshipType == "Select Shipping Type") {
                    Toasty.warning(getActivity(), "Please Select Valid Bill To Shipping Type");
                } else if (ship_shiptype == "Select Shipping Type") {
                    Toasty.warning(getActivity(), "Please Select Valid Ship To Shipping Type");
                } else {

                    AddressExtensions addressExtension = new AddressExtensions();

                    addressExtension.setBillToBuilding(binding.quotationPreparedForContent.addressSection.billingNameValue.getText().toString().trim()); //billing_name_value.getText().toString().trim()
                    addressExtension.setBillToCity(binding.quotationPreparedForContent.addressSection.cityValue.getText().toString().trim());
                    addressExtension.setBillToZipCode(binding.quotationPreparedForContent.addressSection.zipCodeValue.getText().toString().trim());
                    addressExtension.setBillToState(billtoStateCode);// billtoStateCode
                    addressExtension.setBillToStreet(binding.quotationPreparedForContent.addressSection.billingAddressValue.getText().toString().trim());// billtoStateCode
                    addressExtension.setBillToCountry(billtoCountrycode);
                    addressExtension.setU_BSTATE(billtoState);
                    addressExtension.setU_BCOUNTRY(billtoCountryName);
                    addressExtension.setU_SHPTYPB(billshipType);


                    if (binding.quotationPreparedForContent.addressSection.checkbox1.isChecked()) {
                        addressExtension.setShipToBuilding(binding.quotationPreparedForContent.addressSection.shippingNameValue.getText().toString().trim());
                        addressExtension.setShipToZipCode(binding.quotationPreparedForContent.addressSection.zipcodeValue2.getText().toString());
                        addressExtension.setShipToCity(binding.quotationPreparedForContent.addressSection.shipcityValue.getText().toString());
                        addressExtension.setShipToState(shiptoStateCode);
                        addressExtension.setU_SSTATE(shiptoState);
                        addressExtension.setU_SHPTYPS(ship_shiptype);
                        addressExtension.setU_SCOUNTRY(shiptoCountryName);
                        addressExtension.setShipToCountry(shiptoCountrycode);
                        addressExtension.setShipToStreet(binding.quotationPreparedForContent.addressSection.shippingAddressValue.getText().toString().trim());


                    } else {
                        addressExtension.setShipToBuilding(binding.quotationPreparedForContent.addressSection.billingNameValue.getText().toString().trim());
                        addressExtension.setShipToZipCode(binding.quotationPreparedForContent.addressSection.zipCodeValue.getText().toString());
                        addressExtension.setShipToCity(binding.quotationPreparedForContent.addressSection.cityValue.getText().toString());
                        addressExtension.setShipToState(billtoStateCode);
                        addressExtension.setU_SSTATE(billtoState);
                        addressExtension.setU_SHPTYPS(billshipType);
                        addressExtension.setU_SCOUNTRY(billtoCountryName);
                        addressExtension.setShipToCountry(billtoCountrycode);
                        addressExtension.setShipToStreet(binding.quotationPreparedForContent.addressSection.billingAddressValue.getText().toString().trim());


                    }

                    AddQuotationAct.addQuotationObj.setAddressExtension(addressExtension);
                    if (Globals.checkInternet(getActivity()))
                        submitQuotation.submitQuotaion(binding.loader.loader);

                }

            }

        });

        binding.headerBottomRounded.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });


        return binding.getRoot();

    }

    //todo set fields data acc to parent proforma invoice...
    private void setDefaultData(List<BPAddress> bpAddresses) {
      /*  binding.quotationPreparedForContent.addressSection.zipCode.setVisibility(View.GONE);
        binding.quotationPreparedForContent.addressSection.zipCode1.setVisibility(View.VISIBLE);
        binding.quotationPreparedForContent.addressSection.zipcode2.setVisibility(View.GONE);
        binding.quotationPreparedForContent.addressSection.zipcode3.setVisibility(View.VISIBLE);*/


        int boBillToPos = getAddres_BillRow_Pos(bpAddresses, "0");
        int boShipToPos = getAddres_ShipRow_Pos(bpAddresses, "1");

        if (bpAddresses.size() > 0) {

            if (boBillToPos == 0) {
                billtoCountrycode = bpAddresses.get(boBillToPos).getCountry();
                billtoCountryName = bpAddresses.get(boBillToPos).getUCountry();
                billtoStateCode = bpAddresses.get(0).getState();
                billtoState = bpAddresses.get(0).getUState();
                callBillToStateApi(billtoCountrycode);
                binding.quotationPreparedForContent.addressSection.billingNameValue.setText(bpAddresses.get(boBillToPos).getAddressName());
                binding.quotationPreparedForContent.addressSection.zipCodeValue.setText(bpAddresses.get(boBillToPos).getZipCode());
//                binding.quotationPreparedForContent.addressSection.acCountry.setSelection(Globals.getCountrypos(countyList, bpAddresses.get(boBillToPos).getUCountry()));
                binding.quotationPreparedForContent.addressSection.acCountry.setText(bpAddresses.get(boBillToPos).getUCountry());
                binding.quotationPreparedForContent.addressSection.acBillToState.setText(bpAddresses.get(0).getUState());
                binding.quotationPreparedForContent.addressSection.cityValue.setText(bpAddresses.get(boBillToPos).getCity());
                binding.quotationPreparedForContent.addressSection.shippingSpinner.setSelection(Globals.getShipTypePo(shippinngType, bpAddresses.get(boBillToPos).getUShptyp()));
                binding.quotationPreparedForContent.addressSection.billingAddressValue.setText(bpAddresses.get(boBillToPos).getStreet());

            }

            if (boShipToPos == 1) {
                shiptoCountrycode = bpAddresses.get(boShipToPos).getCountry();
                shiptoCountryName = bpAddresses.get(boShipToPos).getUCountry();
                shiptoStateCode = bpAddresses.get(1).getState();
                shiptoState = bpAddresses.get(1).getUState();
                callShipToStateApi(shiptoCountrycode);
                binding.quotationPreparedForContent.addressSection.shippingNameValue.setText(bpAddresses.get(boShipToPos).getAddressName());
                binding.quotationPreparedForContent.addressSection.zipcodeValue2.setText(bpAddresses.get(boShipToPos).getZipCode());
//                binding.quotationPreparedForContent.addressSection.acShipCountry.setSelection(Globals.getCountrypos(countyList, bpAddresses.get(boShipToPos).getUCountry()));
                binding.quotationPreparedForContent.addressSection.acShipCountry.setText(bpAddresses.get(boShipToPos).getUCountry());
//                binding.quotationPreparedForContent.addressSection.acShipToState.setSelection(Globals.getStatePo(stateList, bpAddresses.get(boShipToPos).getUState()));
                binding.quotationPreparedForContent.addressSection.acShipToState.setText(bpAddresses.get(1).getUState());
                binding.quotationPreparedForContent.addressSection.shipcityValue.setText(bpAddresses.get(boShipToPos).getCity());
                binding.quotationPreparedForContent.addressSection.shippingSpinner2.setSelection(Globals.getShipTypePo(shippinngType, bpAddresses.get(boShipToPos).getUShptyp()));
                binding.quotationPreparedForContent.addressSection.shippingAddressValue.setText(bpAddresses.get(boShipToPos).getStreet()); //quotationItem.getAddressExtension().getShipToStreet()

            }

        }


    }

    private void eventManager() {


   /*     billtoCountrycode = "";
        billtoCountryName = "India";
        shiptoCountrycode = "IN";
        shiptoCountryName = "India";
*/
//        callStateApi(billtoCountrycode);


        //todo bill to state item click..
        binding.quotationPreparedForContent.addressSection.acBillToState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                billtoState = stateList.get(position).getName();
                billtoStateCode = stateList.get(position).getCode();

                binding.quotationPreparedForContent.addressSection.acBillToState.setText(stateList.get(position).getName());
            }
        });


        //todo ship to state item click..
        binding.quotationPreparedForContent.addressSection.acShipToState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shiptoState = stateList.get(position).getName();
                shiptoStateCode = stateList.get(position).getCode();

                binding.quotationPreparedForContent.addressSection.acShipToState.setText(stateList.get(position).getName());
            }
        });


        binding.quotationPreparedForContent.addressSection.checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.quotationPreparedForContent.addressSection.shipBlock.setVisibility(View.VISIBLE);
                } else {
                    binding.quotationPreparedForContent.addressSection.shipBlock.setVisibility(View.GONE);
                }
            }
        });

        binding.quotationPreparedForContent.addressSection.shippingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                billshipType = shippinngType[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                billshipType = shippinngType[0];
            }
        });


        binding.quotationPreparedForContent.addressSection.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ship_shiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ship_shiptype = shippinngType[0];
            }
        });
    }

    String contactPersonCode = "";

    private void callBpAddressAPI() {
        BusinessPartnerData contactPersonData = new BusinessPartnerData();

        if (Globals.opportunityData.size() > 0) {
            contactPersonData.setCardCode(mCardCode); //Globals.opportunityData.get(0).getCardCode()
        } else {
        }

        contactPersonData.setCardCode(mCardCode);
        binding.loader.loader.setVisibility(View.VISIBLE);
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().particularcustomerdetails(contactPersonData);
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if (response.body().getStatus() == 200) {

                    if (response.body().getData().size() > 0) {
                        setDefaultData(response.body().getData().get(0).getBPAddresses());
                    }


                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static int getAddres_Bill_Po(List<BPAddress> bpAddresses, String bo_billTo) {
        int po = -1;
        for (BPAddress obj : bpAddresses) {
            if (obj.getAddressType().trim().equalsIgnoreCase(bo_billTo.trim())) {
                po = bpAddresses.indexOf(obj);
                break;
            }
        }
        return po;

    }

    private static int getAddres_Ship_Po(List<BPAddress> bpAddresses, String bo_billTo) {
        int po = -1;
        for (BPAddress obj : bpAddresses) {
            if (obj.getAddressType().trim().equalsIgnoreCase(bo_billTo.trim())) {
                po = bpAddresses.indexOf(obj);
                break;
            }
        }
        return po;
    }

    private static int getAddres_BillRow_Pos(List<BPAddress> bpAddresses, String bo_billTo) {
        int po = -1;
        for (BPAddress obj : bpAddresses) {
            if (obj.getRowNum().trim().equalsIgnoreCase(bo_billTo.trim())) {
                po = bpAddresses.indexOf(obj);
                break;
            }
        }
        return po;
    }

    private static int getAddres_ShipRow_Pos(List<BPAddress> bpAddresses, String bo_billTo) {
        int po = -1;
        for (BPAddress obj : bpAddresses) {
            if (obj.getRowNum().trim().equalsIgnoreCase(bo_billTo.trim())) {
                po = bpAddresses.indexOf(obj);
                break;
            }
        }
        return po;
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

                        List<String> itemNames = new ArrayList<>();
                        List<String> cardCodeName = new ArrayList<>();
                        for (CountryData item : countyList) {
                            itemNames.add(item.getName());
                            cardCodeName.add(item.getCode());
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, itemNames);
                        binding.quotationPreparedForContent.addressSection.acCountry.setAdapter(adapter);


                        //todo bill to and ship to address drop down item select..
                        binding.quotationPreparedForContent.addressSection.acCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String countryName = (String) parent.getItemAtPosition(position);
                                    billtoCountryName = countryName;

                                    int pos = Globals.getCountryCodePos(countyList, countryName);
                                    billtoCountrycode = countyList.get(pos).getCode();

                                    if (countryName.isEmpty()) {
                                        binding.quotationPreparedForContent.addressSection.rlRecyclerViewLayout.setVisibility(View.GONE);
                                        binding.quotationPreparedForContent.addressSection.rvCountryList.setVisibility(View.GONE);
                                    } else {
                                        binding.quotationPreparedForContent.addressSection.rlRecyclerViewLayout.setVisibility(View.VISIBLE);
                                        binding.quotationPreparedForContent.addressSection.rvCountryList.setVisibility(View.VISIBLE);
                                    }

                                    if (!countryName.isEmpty()) {
                                        adapter.notifyDataSetChanged();
                                        binding.quotationPreparedForContent.addressSection.acCountry.setText(countryName);
                                        binding.quotationPreparedForContent.addressSection.acCountry.setSelection(binding.quotationPreparedForContent.addressSection.acCountry.length());

                                        callBillToStateApi(billtoCountrycode);
                                    } else {
                                        billtoCountryName = "";
                                        billtoCountrycode = "";
                                        binding.quotationPreparedForContent.addressSection.acCountry.setText("");
                                    }
                                } catch (Exception e) {
                                    Log.e("catch", "onItemClick: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, itemNames);
                        binding.quotationPreparedForContent.addressSection.acShipCountry.setAdapter(adapter);


                        //todo set on ship country Item Click
                        binding.quotationPreparedForContent.addressSection.acShipCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String countryName = (String) parent.getItemAtPosition(position);
                                    shiptoCountryName = countryName;

                                    int pos = Globals.getCountryCodePos(countyList, countryName);
                                    shiptoCountrycode = countyList.get(pos).getCode();

                                    if (countryName.isEmpty()) {
                                        binding.quotationPreparedForContent.addressSection.rlShipREcyclerLayout.setVisibility(View.GONE);
                                        binding.quotationPreparedForContent.addressSection.rvShipCountryList.setVisibility(View.GONE);
                                    } else {
                                        binding.quotationPreparedForContent.addressSection.rlShipREcyclerLayout.setVisibility(View.VISIBLE);
                                        binding.quotationPreparedForContent.addressSection.rvShipCountryList.setVisibility(View.VISIBLE);
                                    }

                                    if (!countryName.isEmpty()) {
                                        adapter1.notifyDataSetChanged();
                                        binding.quotationPreparedForContent.addressSection.acShipCountry.setText(countryName);
                                        binding.quotationPreparedForContent.addressSection.acShipCountry.setSelection(binding.quotationPreparedForContent.addressSection.acShipCountry.length());

                                        callShipToStateApi(shiptoCountrycode);
                                    } else {
                                        shiptoCountryName = "";
                                        shiptoCountrycode = "";
                                        binding.quotationPreparedForContent.addressSection.acShipCountry.setText("");
                                    }
                                } catch (Exception e) {
                                    Log.e("catch", "onItemClick: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });


                    }

                } else {
                    Toasty.error(getActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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


    private void callBillToStateApi(String billtoCountrycode) {
        StateData stateData = new StateData();
        stateData.setCountry(billtoCountrycode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {

                        stateList.clear();
                        if (response.body().getData().size() > 0) {
                            stateList.addAll(response.body().getData());
                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            stateList.add(sta);
                        }
                        stateAdapter = new StateAdapter(getContext(), R.layout.drop_down_textview, stateList);
                        binding.quotationPreparedForContent.addressSection.acBillToState.setAdapter(stateAdapter);
                       /* billtoState = stateList.get(0).getName();
                        billtoStateCode = stateList.get(0).getCode();*/
                        stateAdapter.notifyDataSetChanged();
                    }

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


    private void callShipToStateApi(String shiptoCountrycode) {
        StateData stateData = new StateData();
        stateData.setCountry(shiptoCountrycode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {

                        stateList.clear();
                        if (response.body().getData().size() > 0) {
                            stateList.addAll(response.body().getData());
                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            stateList.add(sta);
                        }
                        stateAdapter = new StateAdapter(getContext(), R.layout.drop_down_textview, stateList);
                        binding.quotationPreparedForContent.addressSection.acShipToState.setAdapter(stateAdapter);
                       /* shiptoState = stateList.get(0).getName();
                        shiptoStateCode = stateList.get(0).getCode();*/
                        stateAdapter.notifyDataSetChanged();

                    }

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


    private boolean validation(String billtoStateCode, String shiptoStateCode) {
        if (billtoStateCode.isEmpty()) {
            Globals.showMessage(getActivity(), "State is Required !");
            return false;
        } else if (shiptoStateCode.isEmpty()) {
            Globals.showMessage(getActivity(), "State is Required !");
            return false;
        }
        return true;

    }


}