package com.cinntra.okana.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.LeadTypeAutoAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.adapters.StateAdapter;
import com.cinntra.okana.adapters.bpAdapters.ContactPersonAutoAdapter;
import com.cinntra.okana.databinding.FragmentUpdateBusinessPartnerBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.AddBusinessPartnerData;
import com.cinntra.okana.model.BPAddress;
import com.cinntra.okana.model.BPModel.demoListModel;
import com.cinntra.okana.model.ContactEmployeesModel;
import com.cinntra.okana.model.ContactPerson;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.ContactPersonResponseModel;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.CountryResponse;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateBusinessPartnerFragment extends Fragment implements View.OnClickListener{

    FragmentUpdateBusinessPartnerBinding binding;
//    BusinessPartnerData customerItem;
    demoListModel.Datum customerItem;

    Activity dbClick;
    String selectedAddress, selectedLatitude, selectedLongitude = "";
    String currentAddress, currentLatitude, currentLongitude = "";
    FusedLocationProviderClient fusedLocationProviderClient;
    ContactPersonResponseModel.Datum businessPartnerOneData_gl = null;

    String salesEmployeeCode = "";
    String salesEmployeeName = "";
    ArrayList<StateData> billStateList = new ArrayList<>();
    ArrayList<StateData> shipStateList = new ArrayList<>();
    String billtoState = "";
    String billtoStateCode = "";
    String GROUP_TYPE = "";
    String industryCode = "";
    String ContactPersonName = "";
    String payment_term = "";
    String billtoCountrycode, billtoCountryName, shiptoState, shiptoCountrycode, shiptoCountryName, shiptoStateCode;
    StateAdapter stateAdapter, shipStateAdapter;

    String[] shippinngType;
//    String[] billingType;

    String billshipType;
    String ship_shiptype;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
    public UpdateBusinessPartnerFragment(Context businessPartnerDetail) {
        this.dbClick = (Activity) businessPartnerDetail;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            customerItem = (demoListModel.Datum) b.getSerializable(Globals.BussinessItemData);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Hide the action bar if the fragment is attached to an activity
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBusinessPartnerBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.headerLayout.headTitle.setText("Update Business Partner");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        binding.headerLayout.add.setVisibility(View.GONE);

        if (Globals.checkInternet(getContext())) {
            //todo calling country api here---
            callCountryApi();
            callBusinessPartnerOneApi();
        }


        shippinngType = getResources().getStringArray(R.array.bpShippingType);
        ship_shiptype = shippinngType[0];
//        billingType = getResources().getStringArray(R.array.bpBillingType);
        billshipType = shippinngType[0];

        eventManager();

        getMyCurrentLocation();


    }

    //todo event manager listener...
    private void eventManager() {
        binding.leadValue.setFocusableInTouchMode(false);
        binding.leadValue.setFocusable(false);
        binding.leadValue.setClickable(false);

        binding.headerLayout.backPress.setOnClickListener(this);
        binding.updateButton.setOnClickListener(this);


        //todo sales employee item selected..

        binding.acSalesEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0) {
                    binding.acSalesEmployee.setText(salesEmployeeItemList.get(position).getSalesEmployeeName() );
                    salesEmployeeCode = salesEmployeeItemList.get(position).getSalesEmployeeCode();
                } else {
                    binding.acSalesEmployee.setText("");
                    salesEmployeeCode = "";
                }
            }
        });


        //todo item click of industries..
        binding.acLeadType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IndustryItem industryItem = IndustryItemItemList.get(position);
                if (industryItem != null) {
                    binding.acLeadType.setText(industryItem.getIndustryName());
                    industryCode = industryItem.getIndustryCode();
                } else {
                    binding.acLeadType.setText("");
                    industryCode = "";
                }
            }
        });


        //todo item click of industries..
        binding.acContactPersonName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactPersonData contactPersonData = contactPersonDataList.get(position);
                if (contactPersonData != null) {
                    binding.acContactPersonName.setText(contactPersonData.getFirstName());
                    ContactPersonName = contactPersonData.getFirstName();
                } else {
                    binding.acContactPersonName.setText("");
                    ContactPersonName = "";
                }
            }
        });


        //todo bill to state item click..
        binding.addressSectionLayout.acBillToState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (billStateList.size() > 0) {
                    billtoState = billStateList.get(position).getName();
                    billtoStateCode = billStateList.get(position).getCode();

                    binding.addressSectionLayout.acBillToState.setText(billStateList.get(position).getName());
                }

            }
        });


        //todo ship to state item click..
        binding.addressSectionLayout.acShipToState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shiptoState = shipStateList.get(position).getName();
                shiptoStateCode = shipStateList.get(position).getCode();

                binding.addressSectionLayout.acShipToState.setText(shipStateList.get(position).getName());
            }
        });


        binding.addressSectionLayout.shippingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billshipType = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                billshipType = shippinngType[0];

            }
        });

        binding.addressSectionLayout.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                Globals.hideKeybaord(v, getContext());
                getActivity().onBackPressed();
                break;

            case R.id.updateButton:
                String name = binding.edCompanyName.getText().toString().trim();
                String comp_no = binding.edContactPhoneNo.getText().toString().trim();
                String contactName = ContactPersonName;
                String mobile = binding.mobileValue.getText().toString().trim();
                String email = binding.emailValue.getText().toString().trim();
                String website = binding.websiteValue.getText().toString().trim();
                String comp_email = binding.edContactEmail.getText().toString().trim();
                String parentAccName = binding.edParentAccount.getText().toString().trim();

                String billName = binding.addressSectionLayout.billingNameValue.getText().toString().trim();
                String billZipcode = binding.addressSectionLayout.zipCodeValue.getText().toString().trim();
                String billCity = binding.addressSectionLayout.cityValue.getText().toString().trim();
                String billAddressValue = binding.addressSectionLayout.billingAddressValue.getText().toString().trim();

                String shipName = binding.addressSectionLayout.shippingNameValue.getText().toString().trim();
                String shipZipcode = binding.addressSectionLayout.zipcodeValue2.getText().toString().trim();
                String shipCity = binding.addressSectionLayout.shipcityValue.getText().toString().trim();
                String shipAddressValue = binding.addressSectionLayout.shippingAddressValue.getText().toString().trim();

                if (validation(name, comp_email, comp_no, mobile, email, industryCode, Integer.parseInt(salesEmployeeCode), contactName, billName, billZipcode,
                        billCity, billAddressValue, billtoCountryName, billtoState, shipName, shipZipcode, shipCity, shipAddressValue, shiptoCountryName, shiptoState, billshipType, ship_shiptype)) {

                    AddBusinessPartnerData contactExtension = new AddBusinessPartnerData();
                    contactExtension.setId(businessPartnerOneData_gl.getId());
                    contactExtension.setU_LEADID(businessPartnerOneData_gl.getU_LEADID());
                    contactExtension.setU_LEADNM(businessPartnerOneData_gl.getU_LEADNM());
                    contactExtension.setCardCode(businessPartnerOneData_gl.getCardCode());
                    contactExtension.setCardName(name);
                    contactExtension.setCardType("cCustomer"); //select value from spinner
                    contactExtension.setIndustry(industryCode);
                    contactExtension.setWebsite(website);
                    contactExtension.setEmailAddress(email);
                    contactExtension.setPhone1(comp_no);
                    contactExtension.setDiscountPercent("");
                    contactExtension.setCurrency("INR");
                    contactExtension.setIntrestRatePercent("");
                    contactExtension.setCommissionPercent("");
                    contactExtension.setNotes(binding.remarksValue.getText().toString());
                    contactExtension.setPayTermsGrpCode(payment_term);
                    contactExtension.setCreditLimit("");
                    contactExtension.setAttachmentEntry("");
                    contactExtension.setSalesPersonCode(String.valueOf(salesEmployeeCode));
                    contactExtension.setUParentacc(parentAccName);
                    contactExtension.setUBpgrp("");
                    contactExtension.setUContownr(contactName);
                    contactExtension.setURating("");
                    if (!businessPartnerOneData_gl.getU_TYPE().isEmpty()) {
                        contactExtension.setUType(String.valueOf(businessPartnerOneData_gl.getU_TYPE().get(0).getId()));
                    }else {
                        contactExtension.setUType("");
                    }
                    contactExtension.setUAnlrvn("");
                    contactExtension.setUCurbal("");
                    contactExtension.setUAccnt("");
                    contactExtension.setUInvno(binding.invoiceNoValue.getText().toString().trim());

                    contactExtension.setCreateDate(Globals.getTodaysDatervrsfrmt());
                    contactExtension.setCreateTime(Globals.getTCurrentTime());
                    contactExtension.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                    contactExtension.setuLat(String.valueOf(Globals.currentlattitude));
                    contactExtension.setuLong(String.valueOf(Globals.currentlongitude));
                    contactExtension.setUpdateTime(Globals.getTCurrentTime());


                    /************************ BP Address *****************************/
                    ArrayList<BPAddress> postbpAddresses = new ArrayList<>();

                    BPAddress bp = new BPAddress();
                    bp.setId(businessPartnerOneData_gl.getBPAddresses().get(0).getId());
                    bp.setBPCode(businessPartnerOneData_gl.getBPAddresses().get(0).getBPCode());
                    bp.setAddressName(billName);
                    bp.setAddressType("bo_BillTo");
                    bp.setBlock(businessPartnerOneData_gl.getBPAddresses().get(0).getBlock());
                    bp.setCity(billCity);
                    bp.setCountry(billtoCountrycode);  //countryCode
                    bp.setRowNum(businessPartnerOneData_gl.getBPAddresses().get(0).getRowNum());
                    bp.setState(billtoStateCode);
                    bp.setStreet(billAddressValue);
                    bp.setUCountry(billtoCountryName);
                    bp.setUState(billtoState);
                    bp.setUShptyp(billshipType);
                    bp.setZipCode(billZipcode);
                    bp.setBpid(businessPartnerOneData_gl.getBPAddresses().get(0).getBpid());

                    postbpAddresses.add(bp);


                    BPAddress bp1 = new BPAddress();

                    bp1.setId(businessPartnerOneData_gl.getBPAddresses().get(1).getId());
                    bp1.setAddressType("bo_ShipTo");
                    bp1.setRowNum(businessPartnerOneData_gl.getBPAddresses().get(1).getRowNum());
                    bp1.setBPCode(businessPartnerOneData_gl.getBPAddresses().get(1).getBPCode());
                    bp1.setBlock(businessPartnerOneData_gl.getBPAddresses().get(1).getBlock());
                    bp1.setAddressName(shipName);
                    bp1.setZipCode(shipZipcode);
                    bp1.setStreet(shipAddressValue);
                    bp1.setUState(shiptoState);
                    bp1.setUCountry(shiptoCountryName);
                    bp1.setUShptyp(ship_shiptype);
                    bp1.setState(shiptoStateCode);
                    bp1.setCountry(shiptoCountrycode);
                    bp1.setCity(shipCity);
                    bp1.setBpid(businessPartnerOneData_gl.getBPAddresses().get(1).getBpid());

                    postbpAddresses.add(bp1);

                    contactExtension.setBPAddresses(postbpAddresses);

                    /********************* Con Employee ************************/
                    ArrayList<ContactEmployeesModel> postcontactEmployees = new ArrayList<>();
                    ContactEmployeesModel postemp = new ContactEmployeesModel();
                    if (!businessPartnerOneData_gl.getContactEmployees().isEmpty() || businessPartnerOneData_gl.getContactEmployees().size() > 0) {
                        postemp.setId(businessPartnerOneData_gl.getContactEmployees().get(0).getId());
                        postemp.setName(contactName);
                        postemp.setE_Mail(email);
                        postemp.setMobilePhone(mobile);
                        postemp.setFirstName(contactName);
                        postemp.setInternalCode(businessPartnerOneData_gl.getContactEmployees().get(0).getInternalCode());
                        postemp.setCardCode(businessPartnerOneData_gl.getContactEmployees().get(0).getCardCode());
                    }
                    postcontactEmployees.add(postemp);
                    contactExtension.setContactEmployees(postcontactEmployees);


                    if (Globals.checkInternet(getActivity())) {
                        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                        binding.updateButton.setEnabled(false);
                        updateBP(contactExtension);
                    }
                }
                break;

        }
    }


    /****API Calling***/

    private void updateBP(AddBusinessPartnerData in) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(in);
        Log.e("data", jsonTut);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().updatecustomer(in);
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                if (response.code() == 200) {
                    binding.updateButton.setEnabled(true);

                    if (response.body().getStatus() == 200) {
                        Globals.branchData.clear();
                        Toasty.success(dbClick, "Updated Successfully.", Toasty.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    } else {
                        binding.updateButton.setEnabled(true);
                        Toasty.warning(dbClick, response.body().getMessage(), Toasty.LENGTH_SHORT).show();

                    }
                } else {
                    binding.updateButton.setEnabled(true);
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(dbClick, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                binding.updateButton.setEnabled(true);
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(dbClick, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //todo calling contact person api..
    private void callBusinessPartnerOneApi() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", customerItem.getCardCode());
        Call<ContactPersonResponseModel> call = NewApiClient.getInstance().getApiService().getContactPerson(jsonObject);
        call.enqueue(new Callback<ContactPersonResponseModel>() {
            @Override
            public void onResponse(Call<ContactPersonResponseModel> call, Response<ContactPersonResponseModel> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        businessPartnerOneData_gl = response.body().getData().get(0);
                        callSalesEmployeeApi();
                        callLeadTypeApi();

                        setDefaultData();
                    }

                } else {
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }

            }

            @Override
            public void onFailure(Call<ContactPersonResponseModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //todo set default data from one api...
    private void setDefaultData() {

        callCountryApi();

        callContactPersonApi(businessPartnerOneData_gl.getCardCode());

        //todo Contact Details data set..
        binding.leadValue.setText(businessPartnerOneData_gl.getU_LEADNM());
        binding.edCompanyName.setText(businessPartnerOneData_gl.getCardName());

        if (businessPartnerOneData_gl.getPhone1().isEmpty()){
            binding.mobileValue.setHint("NA");
        }else {
            binding.mobileValue.setText(businessPartnerOneData_gl.getPhone1());
        }

        if (businessPartnerOneData_gl.getEmailAddress().isEmpty()){
            binding.emailValue.setHint("NA");
        }else {
            binding.emailValue.setText(businessPartnerOneData_gl.getEmailAddress());
        }

        if (businessPartnerOneData_gl.getWebsite().isEmpty()){
            binding.websiteValue.setHint("NA");
        }else {
            binding.websiteValue.setText(businessPartnerOneData_gl.getWebsite());
        }


        if (businessPartnerOneData_gl.getSalesPersonCode().size() > 0 || !businessPartnerOneData_gl.getSalesPersonCode().isEmpty()){
            binding.acSalesEmployee.setText(businessPartnerOneData_gl.getSalesPersonCode().get(0).getSalesEmployeeName());
            salesEmployeeCode = businessPartnerOneData_gl.getSalesPersonCode().get(0).getSalesEmployeeCode();
            salesEmployeeName = businessPartnerOneData_gl.getSalesPersonCode().get(0).getSalesEmployeeName();
        }else {
            binding.acSalesEmployee.setHint("NA");
            salesEmployeeCode = "";
            salesEmployeeName = "";
        }

        if (businessPartnerOneData_gl.getU_INVNO().isEmpty()){
            binding.invoiceNoValue.setHint("NA");
        }else {
            binding.invoiceNoValue.setText(businessPartnerOneData_gl.getU_INVNO());
        }

        if (businessPartnerOneData_gl.getU_PARENTACC().isEmpty()){
            binding.edParentAccount.setHint("NA");
        }else {
            binding.edParentAccount.setText(businessPartnerOneData_gl.getU_PARENTACC());
        }

     /*   if (businessPartnerOneData_gl.getU_CONTOWNR().isEmpty()){
            binding.contactOwnerValue.setText("NA");
        }else {
            binding.contactOwnerValue.setText(businessPartnerOneData_gl.getU_CONTOWNR());
        }
*/
        if (businessPartnerOneData_gl.getContactEmployees().size() > 0 || !businessPartnerOneData_gl.getContactEmployees().isEmpty()){
            binding.edContactPhoneNo.setText(businessPartnerOneData_gl.getContactEmployees().get(0).getMobilePhone());
            binding.edContactEmail.setText(businessPartnerOneData_gl.getContactEmployees().get(0).getE_Mail());
        }else {
            binding.edContactPhoneNo.setHint("NA");
            binding.edContactEmail.setHint("NA");
        }

        binding.remarksValue.setText(businessPartnerOneData_gl.getNotes());


        //todo set Bill To data here..

        int pos_bo_BillTo = Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo");
        int pos_bo_ShipTo = Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo");

        if (businessPartnerOneData_gl.getBPAddresses().size() > 0 && pos_bo_BillTo == 0) {
            billtoCountrycode = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getCountry();
            billtoCountryName = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getUCountry();
            binding.addressSectionLayout.acCountry.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getUCountry());
            binding.addressSectionLayout.billingAddressValue.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getStreet());
            binding.addressSectionLayout.billingNameValue.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getAddressName());
            binding.addressSectionLayout.cityValue.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getCity());
            binding.addressSectionLayout.zipCodeValue.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getZipCode());
            binding.addressSectionLayout.acBillToState.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getUState());

            billtoState = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getUState();
            billtoStateCode = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getState();
//            billtoStateCode = businessPartnerOneData_gl.getBPAddresses().get(getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo")).getState();//todo before getting data from lising detail api of bp--
            callBillToStateApi(billtoCountrycode);

            billshipType = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Bill_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_BillTo")).getUShptyp();
            if (billshipType != null || !billshipType.equalsIgnoreCase("")) {
                binding.addressSectionLayout.shippingSpinner.setSelection(Globals.getShipTypePo(shippinngType, billshipType));
            }
        }

        if (businessPartnerOneData_gl.getBPAddresses().size() > 0 && pos_bo_ShipTo == 1) {
            shiptoCountrycode = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getCountry();
            shiptoCountryName = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getUCountry();
            binding.addressSectionLayout.acShipCountry.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getUCountry());
            binding.addressSectionLayout.shippingAddressValue.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getStreet());
            binding.addressSectionLayout.shippingNameValue.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getAddressName());
            binding.addressSectionLayout.shipcityValue.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getCity());
            binding.addressSectionLayout.zipcodeValue2.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getZipCode());
            binding.addressSectionLayout.acShipToState.setText(businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getUState());

            shiptoState = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getUState();
            shiptoStateCode = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getState();
            callShipToStateApi(shiptoCountrycode);

            ship_shiptype = businessPartnerOneData_gl.getBPAddresses().get(Globals.getAddres_Ship_Po(businessPartnerOneData_gl.getBPAddresses(), "bo_ShipTo")).getUShptyp();
            if (ship_shiptype != null || !ship_shiptype.equalsIgnoreCase("")) {
                binding.addressSectionLayout.shippingSpinner2.setSelection(Globals.getShipTypePo(shippinngType, ship_shiptype));
            }
        }

    }


    //todo calling contact person api as per card code ---
    ArrayList<ContactPersonData> contactPersonDataList = new ArrayList<>();
    private void callContactPersonApi(String cardCode) {
        ContactPersonData contactPersonData = new ContactPersonData();
        contactPersonData.setCardCode(cardCode);

        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().contactemplist(contactPersonData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {

                if (response.code() == 200) {
                    contactPersonDataList.clear();

                    if (response.body().getData().size() > 0) {
                        contactPersonDataList.addAll(response.body().getData());
                        ContactPersonAutoAdapter contactPersonAdapter = new ContactPersonAutoAdapter(getContext(),R.layout.drop_down_textview, contactPersonDataList);
                        binding.acContactPersonName.setAdapter(contactPersonAdapter);
                        contactPersonAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<ContactPerson> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    //todo calling sales employee api..

    private void callSalesEmployeeApi() {
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
                                SalesEmployeeList = filterlist(response.body().getValue());
                                salesEmployeeItemList = SalesEmployeeList;
                                binding.acSalesEmployee.setAdapter(new SalesEmployeeAutoAdapter(getContext(), R.layout.drop_down_textview, salesEmployeeItemList));
                            } else {
                                Globals.setmessage(getContext());
                            }

                        }
                    } else {
                        Log.e("not_respond===>", "Not Responding..");
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


    private List<SalesEmployeeItem> filterlist(ArrayList<SalesEmployeeItem> value) {
        List<SalesEmployeeItem> tempList = new ArrayList<>();
        for (SalesEmployeeItem salesEmployeeItem : value) {
            if (!salesEmployeeItem.getFirstName().equals("foo")) {
                tempList.add(salesEmployeeItem);
            }
        }
        return tempList;
    }


    //todo calling industry api for lead type drop down---
    List<IndustryItem> IndustryItemItemList = new ArrayList<>();

    private void callLeadTypeApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getIndustryList().observe(this, new Observer<List<IndustryItem>>() {
            @Override
            public void onChanged(@Nullable List<IndustryItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(dbClick);
                } else {
                    IndustryItemItemList = itemsList;
                    binding.acLeadType.setAdapter(new LeadTypeAutoAdapter(dbClick, R.layout.drop_down_textview,itemsList));

                    int pos = Globals.getIndustryPo(IndustryItemItemList, businessPartnerOneData_gl.getIndustry());
                    binding.acLeadType.setText(IndustryItemItemList.get(pos).getIndustryName());
                    industryCode = businessPartnerOneData_gl.getIndustry();

                }
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

                        List<CountryData> itemsList = response.body().getData();
                        itemsList = countryFilterList(response.body().getData());
                        countyList.addAll(itemsList);

                        List<String> itemNames = new ArrayList<>();
                        List<String> cardCodeName = new ArrayList<>();
                        for (CountryData item : countyList) {
                            itemNames.add(item.getName());
                            cardCodeName.add(item.getCode());
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, itemNames);
                        binding.addressSectionLayout.acCountry.setAdapter(adapter);


                        //todo bill to and ship to address drop down item select..
                        binding.addressSectionLayout.acCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String countryName = (String) parent.getItemAtPosition(position);
                                    billtoCountryName = countryName;

                                    int pos = Globals.getCountryCodePos(countyList, countryName);
                                    billtoCountrycode = countyList.get(pos).getCode();

                                    if (countryName.isEmpty()) {
                                        binding.addressSectionLayout.rlRecyclerViewLayout.setVisibility(View.GONE);
                                        binding.addressSectionLayout.rvCountryList.setVisibility(View.GONE);
                                    } else {
                                        binding.addressSectionLayout.rlRecyclerViewLayout.setVisibility(View.VISIBLE);
                                        binding.addressSectionLayout.rvCountryList.setVisibility(View.VISIBLE);
                                    }

                                    if (!countryName.isEmpty()) {
                                        adapter.notifyDataSetChanged();
                                        binding.addressSectionLayout.acCountry.setText(countryName);
                                        binding.addressSectionLayout.acCountry.setSelection(binding.addressSectionLayout.acCountry.length());

                                        callBillToStateApi(billtoCountrycode);
                                    } else {
                                        billtoCountryName = "";
                                        billtoCountrycode = "";
                                        binding.addressSectionLayout.acCountry.setText("");
                                    }
                                } catch (Exception e) {
                                    Log.e("catch", "onItemClick: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, itemNames);
                        binding.addressSectionLayout.acShipCountry.setAdapter(adapter);



                        //todo set on ship country Item Click
                        binding.addressSectionLayout.acShipCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String countryName = (String) parent.getItemAtPosition(position);
                                    shiptoCountryName = countryName;

                                    int pos = Globals.getCountryCodePos(countyList, countryName);
                                    shiptoCountrycode = countyList.get(pos).getCode();

                                    if (countryName.isEmpty()) {
                                        binding.addressSectionLayout.rlShipREcyclerLayout.setVisibility(View.GONE);
                                        binding.addressSectionLayout.rvShipCountryList.setVisibility(View.GONE);
                                    } else {
                                        binding.addressSectionLayout.rlShipREcyclerLayout.setVisibility(View.VISIBLE);
                                        binding.addressSectionLayout.rvShipCountryList.setVisibility(View.VISIBLE);
                                    }

                                    if (!countryName.isEmpty()) {
                                        adapter1.notifyDataSetChanged();
                                        binding.addressSectionLayout.acShipCountry.setText(countryName);
                                        binding.addressSectionLayout.acShipCountry.setSelection(binding.addressSectionLayout.acShipCountry.length());

                                        callShipToStateApi(shiptoCountrycode);
                                    } else {
                                        shiptoCountryName = "";
                                        shiptoCountrycode = "";
                                        binding.addressSectionLayout.acShipCountry.setText("");
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


    private List<CountryData> countryFilterList(List<CountryData> value) {
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

                        billStateList.clear();
                        if (response.body().getData().size() > 0) {
                            billStateList.addAll(response.body().getData());
                            stateAdapter = new StateAdapter(getContext(), R.layout.drop_down_textview, billStateList);
                            binding.addressSectionLayout.acBillToState.setAdapter(stateAdapter);
                            stateAdapter.notifyDataSetChanged();
                        } 
                      
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

                        shipStateList.clear();
                        if (response.body().getData().size() > 0) {
                            shipStateList.addAll(response.body().getData());
                            stateAdapter = new StateAdapter(getContext(), R.layout.drop_down_textview, shipStateList);
                            binding.addressSectionLayout.acShipToState.setAdapter(stateAdapter);
                            stateAdapter.notifyDataSetChanged();
                        }
                    

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


    //todo validation..

    private boolean validation(String cowner, String comp_email, String comp_no, String mobile, String email, String industryCode, int salesEmployeeCode,
                               String contactName, String billName, String billZipcode, String billCity, String billAddressValue, String countryname,
                               String billtoState, String shipName, String shipZipcode, String shipCity,
                               String shipAddressValue, String shiptoCountryName, String shiptoState, String billshipType, String ship_shiptype) {


        if (cowner.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Company name");
            return false;
        } else if (!comp_email.isEmpty() && Globals.isvalidateemail(binding.edContactEmail)) {
            binding.edContactEmail.requestFocus();
            return false;
        } else if (comp_no.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Company Contact No.");
            return false;
        } else if (mobile.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Contact person mobile number");
            return false;
        } else if (industryCode.trim().equalsIgnoreCase("-1")) {
            Globals.showMessage(dbClick, "Select Industry.");
            return false;
        }  else if (!email.isEmpty() && Globals.isvalidateemail(binding.emailValue)) {
            binding.emailValue.requestFocus();
            return false;
        }
        else if (salesEmployeeCode == 0) {
            Globals.showMessage(dbClick, "Select Sales Employee ");
            return false;
        }
        else if (contactName.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Contact Name ");
            return false;
        } else if (billName.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Billing Name");
            return false;
        } else if (billZipcode.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Billing Zipcode");
            return false;
        }else if (billCity.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Bill To City");
            return false;
        }
        else if (countryname.isEmpty()) {
            Globals.showMessage(dbClick, "Select Bill To Country");
            return false;
        }
        else if (billtoState.isEmpty()) {
            Globals.showMessage(dbClick, "Select Bill To State");
            return false;
        }
        else if (billAddressValue.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Billing Address");
            return false;
        }
        else if (shipName.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Shipping Name");
            return false;
        } else if (shipZipcode.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Ship To Zipcode");
            return false;
        }else if (shipCity.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Ship To City");
            return false;
        }
        else if (shiptoCountryName.isEmpty()) {
            Globals.showMessage(dbClick, "Select Ship To Country");
            return false;
        }
        else if (shiptoState.isEmpty()) {
            Globals.showMessage(dbClick, "Select Ship To State");
            return false;
        }
        else if (shipAddressValue.isEmpty()) {
            Globals.showMessage(dbClick, "Enter Ship Address");
            return false;
        } else if (billshipType.equalsIgnoreCase("Select Shipping Type")) {
            Globals.showMessage(dbClick, "Enter Bill To Shipping Type");
            return false;
        }
        else if (ship_shiptype.equalsIgnoreCase("Select Shipping Type")) {
            Globals.showMessage(dbClick, "Enter Ship To Shipping Type");
            return false;
        }
        return true;
    }



    //todo calling get current location method here for get current lat and long and address.
    private void getMyCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder;
                        List<Address> addresses = new ArrayList<>();
                        geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String currentAddress1 = addresses.get(0).getAddressLine(0);
                        Log.e("current_lat", String.valueOf(location.getLatitude()));
                        Log.e("current_long", String.valueOf(location.getLongitude()));
                        Log.e("current_address", String.valueOf(currentAddress1));

                        //todo set selected location blank..
                        selectedAddress = "";
                        selectedLatitude = "";
                        selectedLongitude = "";

                        currentAddress = currentAddress1;
                        currentLatitude = String.valueOf(location.getLatitude());
                        currentLongitude = String.valueOf(location.getLongitude());

                    } else {
                        LocationRequest locationRequest;
                        locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000); // Update interval in milliseconds (e.g., every 10 seconds)
                        locationRequest.setFastestInterval(1000); // Fastest interval for updates
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Request high-accuracy locations


                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                if (locationResult != null) {
                                    Location location = locationResult.getLastLocation();
                                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                    List<Address> addresses = new ArrayList<>();
                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    String address = addresses.get(0).getAddressLine(0);
                                }
                                super.onLocationResult(locationResult);
                            }

                        };
                        // Request location updates
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }

            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    }


}