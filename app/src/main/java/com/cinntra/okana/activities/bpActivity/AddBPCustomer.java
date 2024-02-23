package com.cinntra.okana.activities.bpActivity;


import static com.cinntra.okana.activities.AddOpportunityActivity.LeadCode;
import static com.cinntra.okana.activities.MainActivity.businessPartnerDataFromLocal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.LeadsActivity;
import com.cinntra.okana.adapters.BPTypeSpinnerAdapter;
import com.cinntra.okana.adapters.CountryAdapter;
import com.cinntra.okana.adapters.IndustrySpinnerAdapter;
import com.cinntra.okana.adapters.LeadListAdapter;
import com.cinntra.okana.adapters.PaymentAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.adapters.StateAdapter;
import com.cinntra.okana.databinding.FragmentAddPartner2Binding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.BPModel.AddBusinessPartnerData;
import com.cinntra.okana.model.BPAddress;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.ContactEmployeesModel;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.CountryResponse;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.LeadFilter;
import com.cinntra.okana.model.PayMentTerm;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.LeadValue;
import com.cinntra.okana.room.BusinessPartnerDatabase;
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


public class AddBPCustomer extends MainBaseActivity implements View.OnClickListener {

    private static final String TAG = "AddBPCustomer";
    String TYPE = "";
    String U_LeadNM = "";
    String industryCode;
    String shippingType;

    int salesEmployeeCode = 0;
    String salesEmployeeName = "";

    List<LeadValue> leadValueList = new ArrayList<>();
    String LeadID = "0";
    String payment_term = "";
    String parenT_account = "";
    String[] shippinngType;
    String billshipType;
    String ship_shiptype;
    AppCompatActivity act;
    String billtoState, billtoStateCode, billtoCountrycode, billtoCountryName, shiptoState, shiptoCountrycode, shiptoCountryName, shiptoStateCode;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter, shipStateAdapter;
    ArrayList<StateData> billStateList = new ArrayList<>();
    ArrayList<StateData> shipstateList = new ArrayList<>();
    LeadValue leadValue;
    FragmentAddPartner2Binding binding;
    String countryCode = "";
    String countryname = "";
    boolean IS_CHECKED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = AddBPCustomer.this;
        binding = FragmentAddPartner2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);
        Intent intent = getIntent();

        binding.loader.loader.setVisibility(View.GONE);

        if (intent != null && Prefs.getString(Globals.AddBp, "").equalsIgnoreCase("Lead")) {
            leadValue = intent.getParcelableExtra(Globals.AddBp);
            binding.fragmentAddpartnergeneral.leadValue.setEnabled(false);
            setData(leadValue);
        }
        act = this;
        shippinngType = getResources().getStringArray(R.array.bpShippingType);
        ship_shiptype = shippinngType[0];
        billshipType = shippinngType[0];

        setDefaults();
        //  callLeadApi();
        callBPlistApi();
        if (Globals.checkInternet(this)) {
            callCountryApi();
            callSalessApi();

        }
        eventManager();
    }


    private void eventManager() {


        binding.fragmentAddpartnergeneral.parentAccountValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getPaymenterm.size() > 0)
                    parenT_account = addDatatoCategoryList(AllitemsList).get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parenT_account = addDatatoCategoryList(AllitemsList).get(0);
            }
        });


    /*    countryAdapter = new CountryAdapter(AddBPCustomer.this, MainActivity.countrylistFromLocal);
        binding.fragmentAddpartnercontact.addressSection.countryValue.setAdapter(countryAdapter);
        binding.fragmentAddpartnercontact.addressSection.countryValue.setSelection(Globals.getCountrypos(MainActivity.countrylistFromLocal, "India"));

        binding.fragmentAddpartnercontact.addressSection.countryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                billtoCountryName = MainActivity.countrylistFromLocal.get(position).getName();
                billtoState = "";
                billtoStateCode = "";
                callStateApi(billtoCountrycode, "billto");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                billtoCountrycode = MainActivity.countrylistFromLocal.get(0).getCode();
                billtoCountryName = MainActivity.countrylistFromLocal.get(0).getName();
            }
        });*/


        //todo set bill to item click of autocomplete state
        binding.fragmentAddpartnercontact.addressSection.acBillToState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (billStateList.size() > 0) {
                    billtoState = billStateList.get(position).getName();
                    billtoStateCode = billStateList.get(position).getCode();

                    binding.fragmentAddpartnercontact.addressSection.acBillToState.setText(billStateList.get(position).getName());

                    callCountryApi();
                }

            }
        });


        //todo ship to state item click..
        binding.fragmentAddpartnercontact.addressSection.acShipToState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shiptoState = shipstateList.get(position).getName();
                shiptoStateCode = shipstateList.get(position).getCode();

                binding.fragmentAddpartnercontact.addressSection.acShipToState.setText(shipstateList.get(position).getName());
            }
        });


        /*binding.fragmentAddpartnergeneral.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0)
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(0).getSalesEmployeeCode());
            }

        });*/

        binding.fragmentAddpartnergeneral.acSalesEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0) {
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
                    salesEmployeeName = salesEmployeeItemList.get(position).getSalesEmployeeName();

                    binding.fragmentAddpartnergeneral.acSalesEmployee.setText(salesEmployeeItemList.get(position).getSalesEmployeeName());
                } else {
                    salesEmployeeCode = 0;
                    salesEmployeeName = "";

                    binding.fragmentAddpartnergeneral.acSalesEmployee.setText("");
                }
            }
        });

        binding.fragmentAddpartnergeneral.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (utypelist.size() > 0) {
                    TYPE = utypelist.get(position).getId().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TYPE = utypelist.get(0).getId().toString();
            }
        });


        binding.fragmentAddpartnercontact.addressSection.shippingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billshipType = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                billshipType = shippinngType[0];

            }
        });

        binding.fragmentAddpartnercontact.addressSection.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ship_shiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ship_shiptype = shippinngType[0];
            }
        });

        binding.fragmentAddpartnergeneral.industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (IndustryItemItemList.size() > 0)
                    industryCode = IndustryItemItemList.get(position).getIndustryCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (IndustryItemItemList.size() > 0)
                    industryCode = IndustryItemItemList.get(0).getIndustryCode();
            }
        });
        binding.fragmentAddpartnercontact.addressSection.checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IS_CHECKED = true;
                    binding.fragmentAddpartnercontact.addressSection.shipBlock.setVisibility(View.VISIBLE);
                } else {
                    IS_CHECKED = false;
                    binding.fragmentAddpartnercontact.addressSection.shipBlock.setVisibility(View.GONE);
                }
            }
        });
        binding.fragmentAddpartnergeneral.paymentTermValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getPaymenterm.size() > 0)
                    payment_term = getPaymenterm.get(position).getGroupNumber();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                payment_term = getPaymenterm.get(0).getGroupNumber();

            }
        });

    }


    private void callLeadApi() {
        LeadFilter value = new LeadFilter();
        value.setAssignedTo(Globals.TeamEmployeeID);
        value.setLeadType("All");
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().getAllLead(value);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {
                if (response.code() == 200) {
                    LeadValue lvt = new LeadValue();
                    lvt.setCompanyName("Select lead");
                    lvt.setContactPerson("");
                    lvt.setEmail("");
                    lvt.setPhoneNumber("");

                    leadValueList.clear();
                    leadValueList.add(lvt);
                    leadValueList.addAll(filter(response.body().getData()));

                    binding.fragmentAddpartnergeneral.leadSpinner.setAdapter(new LeadListAdapter(AddBPCustomer.this, leadValueList));
                    if (Prefs.getString(Globals.AddBp, "").equalsIgnoreCase("Lead")) {
                        binding.fragmentAddpartnergeneral.leadSpinner.setSelection(getleadpos(leadValueList, leadValue.getCompanyName()));
                    }
                } else {

                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddBPCustomer.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {

                Toast.makeText(AddBPCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<LeadValue> filter(List<LeadValue> data) {
        ArrayList<LeadValue> templ = new ArrayList<>();
        for (LeadValue lv : data) {
            if (lv.getStatus().equalsIgnoreCase("Qualified")) {
                templ.add(lv);
            }
        }
        return templ;
    }


    private void setData(LeadValue leadValue) {
        binding.fragmentAddpartnergeneral.nameValue.setText(leadValue.getCompanyName());
        binding.fragmentAddpartnercontact.contactOwnerValue.setText(leadValue.getContactPerson());
        binding.fragmentAddpartnercontact.emailValue.setText(leadValue.getEmail());
        binding.fragmentAddpartnercontact.mobileValue.setText(leadValue.getPhoneNumber());
        binding.fragmentAddpartnergeneral.companyNoValue.setText(leadValue.getPhoneNumber());
//        binding.fragmentAddpartnercontact.addressSection.billingNameValue.setText(leadValue.getLocation());
        binding.fragmentAddpartnergeneral.leadValue.setText(leadValue.getCompanyName());
        binding.fragmentAddpartnergeneral.acSalesEmployee.setText(leadValue.getAssignedTo().getFirstName());
        salesEmployeeCode = Integer.parseInt(leadValue.getAssignedTo().getSalesEmployeeCode());

    }

    private int getleadpos(List<LeadValue> leadValueList, String companyName) {
        for (LeadValue ld : leadValueList) {
            if (ld.getCompanyName().equalsIgnoreCase(companyName)) {
                return leadValueList.indexOf(ld);
            }
        }
        return 0;
    }

    private void setDefaults() {
        frameManager(binding.generalFrame, binding.contactFrame, binding.general, binding.contact);
        binding.fragmentAddpartnercontact.addressSection.doneButton.setVisibility(View.GONE);
        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_bussiness));
        binding.fragmentAddpartnercontact.createButton.setOnClickListener(this);
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        // tab_2.setOnClickListener(this);
        binding.general.setOnClickListener(this);
        binding.tab2.setOnClickListener(this);
        binding.contact.setOnClickListener(this);
        binding.fragmentAddpartnergeneral.leadValue.setOnClickListener(this);

    }


    ArrayList<BusinessPartnerData> AllitemsList = new ArrayList<>();

    private void callBPlistApi() {

        if (businessPartnerDataFromLocal.size() >= 0) {
            AllitemsList.clear();
            BusinessPartnerData bpd = new BusinessPartnerData();
            bpd.setCardName("Select Parent Account");
            AllitemsList.add(bpd);
            AllitemsList.addAll(businessPartnerDataFromLocal);

//                    parent_account_value.setAdapter(new ParentAccAdapter(act,filter(AllitemsList)));
            binding.fragmentAddpartnergeneral.parentAccountValue.setAdapter(new ArrayAdapter(act, android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
            parenT_account = addDatatoCategoryList(AllitemsList).get(0);


        }


    }

    private ArrayList<String> addDatatoCategoryList(ArrayList<BusinessPartnerData> allitemsList) {
        ArrayList<String> bplist = new ArrayList<>();
        for (BusinessPartnerData bpdata : allitemsList) {
                   /* if(LeadID.isEmpty()){

                    }else{
                        if(bpdata.getLeadID().equalsIgnoreCase(LeadID)){
                            bplist.add(bpdata.getCardName());
                        }
                    }
*/
            bplist.add(bpdata.getCardName());
        }
        return bplist;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                onBackPressed();
                break;
            case R.id.tab_1:
            case R.id.general:
                frameManager(binding.generalFrame, binding.contactFrame, binding.general, binding.contact);
                break;
            case R.id.tab_2:
            case R.id.contact:
                frameManager(binding.contactFrame, binding.generalFrame, binding.contact, binding.general);
                break;
            case R.id.lead_value:
                selectLead();
                break;

            case R.id.create_button:
                String name = binding.fragmentAddpartnergeneral.nameValue.getText().toString().trim();
                String comp_no = binding.fragmentAddpartnergeneral.companyNoValue.getText().toString().trim();
                String contactName = binding.fragmentAddpartnercontact.contactOwnerValue.getText().toString().trim();
                String mobile = binding.fragmentAddpartnercontact.mobileValue.getText().toString().trim();
                String email = binding.fragmentAddpartnercontact.emailValue.getText().toString().trim();
                String website = binding.fragmentAddpartnergeneral.websiteValue.getText().toString().trim();
                String comp_email = binding.fragmentAddpartnergeneral.companyEmailValue.getText().toString().trim();

                String billName = binding.fragmentAddpartnercontact.addressSection.billingNameValue.getText().toString().trim();
                String billZipcode = binding.fragmentAddpartnercontact.addressSection.zipCodeValue.getText().toString().trim();
                String billCity = binding.fragmentAddpartnercontact.addressSection.cityValue.getText().toString().trim();
                String billAddressValue = binding.fragmentAddpartnercontact.addressSection.billingAddressValue.getText().toString().trim();

                String shipName = binding.fragmentAddpartnercontact.addressSection.shippingNameValue.getText().toString().trim();
                String shipZipcode = binding.fragmentAddpartnercontact.addressSection.zipcodeValue2.getText().toString().trim();
                String shipCity = binding.fragmentAddpartnercontact.addressSection.shipcityValue.getText().toString().trim();
                String shipAddressValue = binding.fragmentAddpartnercontact.addressSection.shippingAddressValue.getText().toString().trim();

                if (IS_CHECKED == true){
                    if (validation(name, comp_email, comp_no, mobile, email, industryCode, salesEmployeeCode, contactName, billName, billZipcode,
                            billCity, billAddressValue, billtoCountryName, billtoState, shipName, shipZipcode, shipCity, shipAddressValue, shiptoCountryName, shiptoState, billshipType, ship_shiptype)) {

                        AddBusinessPartnerData contactExtension = new AddBusinessPartnerData();
                        contactExtension.setU_LEADID(LeadID);
                        contactExtension.setU_LEADNM(U_LeadNM);
                        contactExtension.setCardCode("");
                        contactExtension.setCardName(name);
                        contactExtension.setCardType("cCustomer"); //select value from spinner
                        contactExtension.setIndustry(industryCode);
                        contactExtension.setWebsite(website);
                        contactExtension.setEmailAddress(comp_email);
                        contactExtension.setPhone1(comp_no);
                        contactExtension.setDiscountPercent("");
                        contactExtension.setCurrency("INR");
                        contactExtension.setIntrestRatePercent("");
                        contactExtension.setCommissionPercent("");
                        contactExtension.setNotes(binding.fragmentAddpartnercontact.remarksValue.getText().toString());
                        contactExtension.setPayTermsGrpCode(payment_term);
                        contactExtension.setCreditLimit("");
                        contactExtension.setAttachmentEntry("");
                        contactExtension.setSalesPersonCode(String.valueOf(salesEmployeeCode));
                        contactExtension.setUParentacc(parenT_account);
                        contactExtension.setUBpgrp("");
                        contactExtension.setUContownr(contactName);
                        contactExtension.setURating("");
                        contactExtension.setUType(TYPE);
                        contactExtension.setUAnlrvn("");
                        contactExtension.setUCurbal("");
                        contactExtension.setUAccnt("");
                        contactExtension.setUInvno(binding.fragmentAddpartnergeneral.invoiceNoValue.getText().toString().trim());

                        contactExtension.setCreateDate(Globals.getTodaysDatervrsfrmt());
                        contactExtension.setCreateTime(Globals.getTCurrentTime());
                        contactExtension.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                        contactExtension.setuLat(String.valueOf(Globals.currentlattitude));
                        contactExtension.setuLong(String.valueOf(Globals.currentlongitude));
                        contactExtension.setUpdateTime(Globals.getTCurrentTime());


                        /************************ BP Address *****************************/
                        ArrayList<BPAddress> postbpAddresses = new ArrayList<>();

                        BPAddress bp = new BPAddress();
                        bp.setBPCode("");
                        bp.setAddressName(billName);
                        bp.setAddressType("bo_BillTo");
                        bp.setBlock("");
                        bp.setCity(billCity);
                        bp.setCountry(billtoCountrycode);  //countryCode
                        bp.setRowNum("0");
                        bp.setState(billtoStateCode);
                        bp.setStreet(billAddressValue);
                        bp.setUCountry(billtoCountryName);
                        bp.setUState(billtoState);
                        bp.setUShptyp(billshipType);
                        bp.setZipCode(billZipcode);

                        postbpAddresses.add(bp);


                        BPAddress bp1 = new BPAddress();

                        bp1.setAddressType("bo_ShipTo");
                        bp1.setRowNum("1");
                        bp1.setBPCode("");
                        bp1.setBlock("");
                        if (binding.fragmentAddpartnercontact.addressSection.checkbox1.isChecked()) {
                            bp1.setAddressName(shipName);
                            bp1.setZipCode(shipZipcode);
                            bp1.setStreet(shipAddressValue);
                            bp1.setUState(shiptoState);
                            bp1.setUCountry(shiptoCountryName);
                            bp1.setUShptyp(ship_shiptype);
                            bp1.setState(shiptoStateCode);
                            bp1.setCountry(shiptoCountrycode);
                            bp1.setCity(shipCity);

                        } else {
                            bp1.setAddressName(billName);
                            bp1.setStreet(billAddressValue);
                            bp1.setZipCode(billZipcode);
                            bp1.setUCountry(billtoCountryName);
                            bp1.setUState(billtoState);
                            bp1.setUShptyp(billshipType);
                            bp1.setState(billtoStateCode);
                            bp1.setCountry(billtoCountrycode);
                            bp1.setCity(billCity);
                        }

                        postbpAddresses.add(bp1);

                        contactExtension.setBPAddresses(postbpAddresses);

                        /********************* Con Employee ************************/
                        ArrayList<ContactEmployeesModel> postcontactEmployees = new ArrayList<>();
                        ContactEmployeesModel postemp = new ContactEmployeesModel();
                        postemp.setName(contactName);
                        postemp.setE_Mail(email);
                        postemp.setMobilePhone(mobile);
                        postcontactEmployees.add(postemp);
                        contactExtension.setContactEmployees(postcontactEmployees);


                        if (Globals.checkInternet(this)) {
                            binding.loader.loader.setVisibility(View.VISIBLE);
                            binding.fragmentAddpartnercontact.createButton.setEnabled(false);
                            createBP(contactExtension);
                        }
                    }

                }else {
                    if (unCheckShipValidation(name, comp_email, comp_no, mobile, email, industryCode, salesEmployeeCode, contactName, billName, billZipcode,
                            billCity, billAddressValue, billtoCountryName, billtoState)) {

                        AddBusinessPartnerData contactExtension = new AddBusinessPartnerData();
                        contactExtension.setU_LEADID(LeadID);
                        contactExtension.setU_LEADNM(U_LeadNM);
                        contactExtension.setCardCode("");
                        contactExtension.setCardName(name);
                        contactExtension.setCardType("cCustomer"); //select value from spinner
                        contactExtension.setIndustry(industryCode);
                        contactExtension.setWebsite(website);
                        contactExtension.setEmailAddress(comp_email);
                        contactExtension.setPhone1(comp_no);
                        contactExtension.setDiscountPercent("");
                        contactExtension.setCurrency("INR");
                        contactExtension.setIntrestRatePercent("");
                        contactExtension.setCommissionPercent("");
                        contactExtension.setNotes(binding.fragmentAddpartnercontact.remarksValue.getText().toString());
                        contactExtension.setPayTermsGrpCode(payment_term);
                        contactExtension.setCreditLimit("");
                        contactExtension.setAttachmentEntry("");
                        contactExtension.setSalesPersonCode(String.valueOf(salesEmployeeCode));
                        contactExtension.setUParentacc(parenT_account);
                        contactExtension.setUBpgrp("");
                        contactExtension.setUContownr(contactName);
                        contactExtension.setURating("");
                        contactExtension.setUType(TYPE);
                        contactExtension.setUAnlrvn("");
                        contactExtension.setUCurbal("");
                        contactExtension.setUAccnt("");
                        contactExtension.setUInvno(binding.fragmentAddpartnergeneral.invoiceNoValue.getText().toString().trim());

                        contactExtension.setCreateDate(Globals.getTodaysDatervrsfrmt());
                        contactExtension.setCreateTime(Globals.getTCurrentTime());
                        contactExtension.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                        contactExtension.setuLat(String.valueOf(Globals.currentlattitude));
                        contactExtension.setuLong(String.valueOf(Globals.currentlongitude));
                        contactExtension.setUpdateTime(Globals.getTCurrentTime());


                        /************************ BP Address *****************************/
                        ArrayList<BPAddress> postbpAddresses = new ArrayList<>();

                        BPAddress bp = new BPAddress();
                        bp.setBPCode("");
                        bp.setAddressName(billName);
                        bp.setAddressType("bo_BillTo");
                        bp.setBlock("");
                        bp.setCity(billCity);
                        bp.setCountry(billtoCountrycode);  //countryCode
                        bp.setRowNum("0");
                        bp.setState(billtoStateCode);
                        bp.setStreet(billAddressValue);
                        bp.setUCountry(billtoCountryName);
                        bp.setUState(billtoState);
                        bp.setUShptyp(billshipType);
                        bp.setZipCode(billZipcode);

                        postbpAddresses.add(bp);


                        BPAddress bp1 = new BPAddress();

                        bp1.setAddressType("bo_ShipTo");
                        bp1.setRowNum("1");
                        bp1.setBPCode("");
                        bp1.setBlock("");
                        if (binding.fragmentAddpartnercontact.addressSection.checkbox1.isChecked() && IS_CHECKED == true) {
                            bp1.setAddressName(shipName);
                            bp1.setZipCode(shipZipcode);
                            bp1.setStreet(shipAddressValue);
                            bp1.setUState(shiptoState);
                            bp1.setUCountry(shiptoCountryName);
                            bp1.setUShptyp(ship_shiptype);
                            bp1.setState(shiptoStateCode);
                            bp1.setCountry(shiptoCountrycode);
                            bp1.setCity(shipCity);

                        } else {
                            bp1.setAddressName(billName);
                            bp1.setStreet(billAddressValue);
                            bp1.setZipCode(billZipcode);
                            bp1.setUCountry(billtoCountryName);
                            bp1.setUState(billtoState);
                            bp1.setUShptyp(billshipType);
                            bp1.setState(billtoStateCode);
                            bp1.setCountry(billtoCountrycode);
                            bp1.setCity(billCity);
                        }

                        postbpAddresses.add(bp1);

                        contactExtension.setBPAddresses(postbpAddresses);

                        /********************* Con Employee ************************/
                        ArrayList<ContactEmployeesModel> postcontactEmployees = new ArrayList<>();
                        ContactEmployeesModel postemp = new ContactEmployeesModel();
                        postemp.setName(contactName);
                        postemp.setE_Mail(email);
                        postemp.setMobilePhone(mobile);
                        postcontactEmployees.add(postemp);
                        contactExtension.setContactEmployees(postcontactEmployees);


                        if (Globals.checkInternet(this)) {
                            binding.loader.loader.setVisibility(View.VISIBLE);
                            binding.fragmentAddpartnercontact.createButton.setEnabled(false);
                            createBP(contactExtension);
                        }
                    }

                }


                break;

        }
    }

    private void frameManager(FrameLayout visiblle_frame, FrameLayout f1, TextView selected, TextView t1) {
        selected.setTextColor(getResources().getColor(R.color.colorPrimary));
        t1.setTextColor(getResources().getColor(R.color.black));
        visiblle_frame.setVisibility(View.VISIBLE);
        f1.setVisibility(View.GONE);

    }

    private void selectLead() {
        Prefs.putString(Globals.BussinessPageType, "AddBPLead");
        Intent i = new Intent(AddBPCustomer.this, LeadsActivity.class);
        startActivityForResult(i, LeadCode);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LeadCode && resultCode == RESULT_OK) {
            LeadValue leadValue = data.getParcelableExtra(Globals.Lead_Data);
            binding.fragmentAddpartnergeneral.leadValue.setText(leadValue.getCompanyName());
            LeadID = leadValue.getId().toString();
            U_LeadNM = leadValue.getCompanyName().toString();
            setData(leadValue);
        }
    }


    /********************* APIs ***************************/


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


                        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBPCustomer.this, R.layout.drop_down_textview, itemNames);
                        binding.fragmentAddpartnercontact.addressSection.acCountry.setAdapter(adapter);


                        //todo bill to and ship to address drop down item select..
                        binding.fragmentAddpartnercontact.addressSection.acCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String countryName = (String) parent.getItemAtPosition(position);
                                    billtoCountryName = countryName;

                                    int pos = Globals.getCountryCodePos(countyList, countryName);
                                    billtoCountrycode = countyList.get(pos).getCode();

                                    if (countryName.isEmpty()) {
                                        binding.fragmentAddpartnercontact.addressSection.rlRecyclerViewLayout.setVisibility(View.GONE);
                                        binding.fragmentAddpartnercontact.addressSection.rvCountryList.setVisibility(View.GONE);
                                    } else {
                                        binding.fragmentAddpartnercontact.addressSection.rlRecyclerViewLayout.setVisibility(View.VISIBLE);
                                        binding.fragmentAddpartnercontact.addressSection.rvCountryList.setVisibility(View.VISIBLE);
                                    }

                                    if (!countryName.isEmpty()) {
                                        adapter.notifyDataSetChanged();
                                        binding.fragmentAddpartnercontact.addressSection.acCountry.setText(countryName);
                                        binding.fragmentAddpartnercontact.addressSection.acCountry.setSelection(binding.fragmentAddpartnercontact.addressSection.acCountry.length());

                                        callBillToStateApi(billtoCountrycode);
                                    } else {
                                        billtoCountryName = "";
                                        billtoCountrycode = "";
                                        binding.fragmentAddpartnercontact.addressSection.acCountry.setText("");
                                    }
                                } catch (Exception e) {
                                    Log.e("catch", "onItemClick: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AddBPCustomer.this, R.layout.drop_down_textview, itemNames);
                        binding.fragmentAddpartnercontact.addressSection.acShipCountry.setAdapter(adapter);



                        //todo set on ship country Item Click
                        binding.fragmentAddpartnercontact.addressSection.acShipCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String countryName = (String) parent.getItemAtPosition(position);
                                    shiptoCountryName = countryName;

                                    int pos = Globals.getCountryCodePos(countyList, countryName);
                                    shiptoCountrycode = countyList.get(pos).getCode();

                                    if (countryName.isEmpty()) {
                                        binding.fragmentAddpartnercontact.addressSection.rlShipREcyclerLayout.setVisibility(View.GONE);
                                        binding.fragmentAddpartnercontact.addressSection.rvShipCountryList.setVisibility(View.GONE);
                                    } else {
                                        binding.fragmentAddpartnercontact.addressSection.rlShipREcyclerLayout.setVisibility(View.VISIBLE);
                                        binding.fragmentAddpartnercontact.addressSection.rvShipCountryList.setVisibility(View.VISIBLE);
                                    }

                                    if (!countryName.isEmpty()) {
                                        adapter1.notifyDataSetChanged();
                                        binding.fragmentAddpartnercontact.addressSection.acShipCountry.setText(countryName);
                                        binding.fragmentAddpartnercontact.addressSection.acShipCountry.setSelection(binding.fragmentAddpartnercontact.addressSection.acShipCountry.length());

                                        callShipToStateApi(shiptoCountrycode);
                                    } else {
                                        shiptoCountryName = "";
                                        shiptoCountrycode = "";
                                        binding.fragmentAddpartnercontact.addressSection.acShipCountry.setText("");
                                    }
                                } catch (Exception e) {
                                    Log.e("catch", "onItemClick: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });


                    }

                } else {
                    Toasty.error(AddBPCustomer.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(AddBPCustomer.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                        StateAdapter stateAdapter = new StateAdapter(AddBPCustomer.this, R.layout.drop_down_textview, billStateList);

                        //todo set bill state..
                        binding.fragmentAddpartnercontact.addressSection.acBillToState.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
//                        billtoState = billStateList.get(0).getName();
//                        billtoStateCode = billStateList.get(0).getCode();
                    }

                }
                else if (response.body().getStatus() == 201){
                    Globals.showMessage(AddBPCustomer.this, response.body().getMessage());
                }
                else if (response.body().getStatus() == 500){
                    Globals.showMessage(AddBPCustomer.this, response.body().getMessage());
                }else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    StateRespose mError = new StateRespose();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, StateRespose.class);
                        Toast.makeText(AddBPCustomer.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(AddBPCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                        shipstateList.clear();
                        if (response.body().getData().size() > 0) {
                            shipstateList.addAll(response.body().getData());
                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            shipstateList.add(sta);
                        }
                        stateAdapter = new StateAdapter(AddBPCustomer.this, R.layout.drop_down_textview, shipstateList);
                        binding.fragmentAddpartnercontact.addressSection.acShipToState.setAdapter(stateAdapter);
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
                        Toast.makeText(AddBPCustomer.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(AddBPCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(act, new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(act);
                } else {
                    salesEmployeeItemList = itemsList;
                    SalesEmployeeAutoAdapter adapter = new SalesEmployeeAutoAdapter(AddBPCustomer.this, R.layout.drop_down_textview, itemsList);
                    binding.fragmentAddpartnergeneral.acSalesEmployee.setAdapter(adapter);
                   /* binding.fragmentAddpartnergeneral.salesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(act, itemsList));
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(0).getSalesEmployeeCode());*/

                }
            }
        });
        callStagesApi();
    }

    List<PayMentTerm> getPaymenterm = new ArrayList<>();

    private void callPaymentApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getPaymentList().observe(act, new Observer<List<PayMentTerm>>() {
            @Override
            public void onChanged(List<PayMentTerm> payMentTermList) {
                if (payMentTermList == null || payMentTermList.size() == 0) {
                    Globals.setmessage(act);
                } else {
                    getPaymenterm.clear();
                    getPaymenterm = payMentTermList;
                    binding.fragmentAddpartnergeneral.paymentTermValue.setAdapter(new PaymentAdapter(act, getPaymenterm));
                    payment_term = getPaymenterm.get(0).getGroupNumber();

                }

            }
        });

      /*  if (MainActivity.paymentTermListFromLocal == null || MainActivity.paymentTermListFromLocal.size() == 0) {
            Globals.setmessage(act);
        } else {
            getPaymenterm.clear();
            getPaymenterm = MainActivity.paymentTermListFromLocal;
            binding.fragmentAddpartnergeneral.paymentTermValue.setAdapter(new PaymentAdapter(act, getPaymenterm));
            payment_term = getPaymenterm.get(0).getGroupNumber();

        }*/

        callUTypeApi();
    }

    List<UTypeData> utypelist = new ArrayList<>();

    private void callUTypeApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getBpTypeList().observe(this, new Observer<List<UTypeData>>() {
            @Override
            public void onChanged(@Nullable List<UTypeData> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(act);
                } else {
                    utypelist = itemsList;
                    binding.fragmentAddpartnergeneral.typeSpinner.setAdapter(new BPTypeSpinnerAdapter(act, itemsList));
                    TYPE = utypelist.get(0).getId().toString();

                }
            }
        });

       /* utypelist = MainActivity.itemBpTypeDataFromLocal;
        binding.fragmentAddpartnergeneral.typeSpinner.setAdapter(new BPTypeSpinnerAdapter(act, MainActivity.itemBpTypeDataFromLocal));
        TYPE = utypelist.get(0).getId().toString();*/
    }


    List<IndustryItem> IndustryItemItemList = new ArrayList<>();

    private void callStagesApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getIndustryList().observe(this, new Observer<List<IndustryItem>>() {
            @Override
            public void onChanged(@Nullable List<IndustryItem> itemsList) {
                if(itemsList == null || itemsList.size()== 0){
                    Globals.setmessage( act);
                }else {
                    IndustryItemItemList = itemsList;
                    binding.fragmentAddpartnergeneral.industrySpinner.setAdapter(new IndustrySpinnerAdapter(act, itemsList));
                    industryCode = IndustryItemItemList.get(0).getIndustryCode();

                }
            }
        });

       /* if (MainActivity.industryListFromLocal == null || MainActivity.industryListFromLocal.size() == 0) {
            Globals.setmessage(act);
        } else {
            IndustryItemItemList = MainActivity.industryListFromLocal;
            binding.fragmentAddpartnergeneral.industrySpinner.setAdapter(new IndustrySpinnerAdapter(act, MainActivity.industryListFromLocal));
            industryCode = IndustryItemItemList.get(0).getIndustryCode();

        }*/


        callPaymentApi();
    }




    /********************* Add Contact API *************************/

    private void createBP(AddBusinessPartnerData in) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(in);
        Log.e("data", jsonTut);
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().addnewCustomer(in);
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if (response.body().getStatus()  == 200) {
                    binding.fragmentAddpartnercontact.createButton.setEnabled(true);

                    fetchBusinessPartnertDataFromApi(AddBPCustomer.this);

                    Toasty.success(AddBPCustomer.this, "Add Successfully", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    binding.loader.loader.setVisibility(View.GONE);
                    binding.fragmentAddpartnercontact.createButton.setEnabled(true);
                    Toasty.warning(AddBPCustomer.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
//                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    QuotationResponse mError = new QuotationResponse();
//                    try {
//                        String s = response.errorBody().string();
//                        mError = gson.fromJson(s, QuotationResponse.class);
//                        Toast.makeText(act, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                binding.fragmentAddpartnercontact.createButton.setEnabled(true);
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(AddBPCustomer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String cowner, String comp_email, String comp_no, String mobile, String email, String industryCode, int salesEmployeeCode,
                               String contactName, String billName, String billZipcode, String billCity, String billAddressValue, String countryname,
                               String billtoState, String shipName, String shipZipcode, String shipCity,
                               String shipAddressValue, String shiptoCountryName, String shiptoState, String billshipType, String ship_shiptype) {


        if (cowner.isEmpty()) {
            Globals.showMessage(act, "Enter Company name");
            return false;
        } else if (!comp_email.isEmpty() && Globals.isvalidateemail(binding.fragmentAddpartnergeneral.companyEmailValue)) {
            binding.fragmentAddpartnergeneral.companyEmailValue.requestFocus();
            return false;
        } else if (comp_no.isEmpty()) {
            Globals.showMessage(act, "Enter Company Contact No.");
            return false;
        } else if (mobile.isEmpty()) {
            Globals.showMessage(act, "Enter Contact person mobile number");
            return false;
        } else if (industryCode.trim().equalsIgnoreCase("-1")) {
            Globals.showMessage(act, "Select Industry.");
            return false;
        } else if (!email.isEmpty() && Globals.isvalidateemail(binding.fragmentAddpartnercontact.emailValue)) {
            binding.fragmentAddpartnercontact.emailValue.requestFocus();
            return false;
        }
      else if (salesEmployeeCode == 0) {
            Globals.showMessage(act, "Select Sales Employee ");
            return false;
        }
        else if (contactName.isEmpty()) {
            Globals.showMessage(act, "Enter Contact Name ");
            return false;
        } else if (billName.isEmpty()) {
            Globals.showMessage(act, "Enter Billing Name");
            return false;
        } else if (billZipcode.isEmpty()) {
            Globals.showMessage(act, "Enter Billing Zipcode");
            return false;
        }else if (billCity.isEmpty()) {
            Globals.showMessage(act, "Enter Bill To City");
            return false;
        }
        else if (countryname.isEmpty()) {
            Globals.showMessage(act, "Select Bill To Country");
            return false;
        }
        else if (billtoState.isEmpty()) {
            Globals.showMessage(act, "Select Bill To State");
            return false;
        }
        else if (billAddressValue.isEmpty()) {
            Globals.showMessage(act, "Enter Billing Address");
            return false;
        }
        else if (shipName.isEmpty()) {
            Globals.showMessage(act, "Enter Shipping Name");
            return false;
        } else if (shipZipcode.isEmpty()) {
            Globals.showMessage(act, "Enter Ship To Zipcode");
            return false;
        }else if (shipCity.isEmpty()) {
            Globals.showMessage(act, "Enter Ship To City");
            return false;
        }
        else if (shiptoCountryName.isEmpty()) {
            Globals.showMessage(act, "Select Ship To Country");
            return false;
        }
        else if (shiptoState.isEmpty()) {
            Globals.showMessage(act, "Select Ship To State");
            return false;
        }
        else if (shipAddressValue.isEmpty()) {
            Globals.showMessage(act, "Enter Ship Address");
            return false;
        }
        else if (billshipType.equalsIgnoreCase("Select Shipping Type")) {
            Globals.showMessage(act, "Enter Bill To Shipping Type");
            return false;
        }
        else if (ship_shiptype.equalsIgnoreCase("Select Shipping Type")) {
            Globals.showMessage(act, "Enter Ship To Shipping Type");
            return false;
        }
        return true;
    }


    private boolean unCheckShipValidation(String cowner, String comp_email, String comp_no, String mobile, String email, String industryCode, int salesEmployeeCode,
                               String contactName, String billName, String billZipcode, String billCity, String billAddressValue, String countryname,
                               String billtoState) {


        if (cowner.isEmpty()) {
            Globals.showMessage(act, "Enter Company name");
            return false;
        } else if (!comp_email.isEmpty() && Globals.isvalidateemail(binding.fragmentAddpartnergeneral.companyEmailValue)) {
            binding.fragmentAddpartnergeneral.companyEmailValue.requestFocus();
            return false;
        } else if (comp_no.isEmpty()) {
            Globals.showMessage(act, "Enter Company Contact No.");
            return false;
        } else if (mobile.isEmpty()) {
            Globals.showMessage(act, "Enter Contact person mobile number");
            return false;
        } else if (industryCode.trim().equalsIgnoreCase("-1")) {
            Globals.showMessage(act, "Select Industry.");
            return false;
        } else if (!email.isEmpty() && Globals.isvalidateemail(binding.fragmentAddpartnercontact.emailValue)) {
            binding.fragmentAddpartnercontact.emailValue.requestFocus();
            return false;
        }
        else if (salesEmployeeCode == 0) {
            Globals.showMessage(act, "Select Sales Employee ");
            return false;
        }
        else if (contactName.isEmpty()) {
            Globals.showMessage(act, "Enter Contact Name ");
            return false;
        } else if (billName.isEmpty()) {
            Globals.showMessage(act, "Enter Billing Name");
            return false;
        } else if (billZipcode.isEmpty()) {
            Globals.showMessage(act, "Enter Billing Zipcode");
            return false;
        }else if (billCity.isEmpty()) {
            Globals.showMessage(act, "Enter Bill To City");
            return false;
        }
        else if (countryname.isEmpty()) {
            Globals.showMessage(act, "Select Bill To Country");
            return false;
        }
        else if (billtoState.isEmpty()) {
            Globals.showMessage(act, "Select Bill To State");
            return false;
        }
        else if (billAddressValue.isEmpty()) {
            Globals.showMessage(act, "Enter Billing Address");
            return false;
        }

        return true;
    }



    private List<BusinessPartnerData> fetchBusinessPartnertDataFromApi(Context context) {
        // alertDialog.show();
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().getAllBusinessPartner();
        ArrayList<BusinessPartnerData> countrylist = new ArrayList<>();
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                //  alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        BusinessPartnerDatabase db = BusinessPartnerDatabase.getDatabase(getApplicationContext());
                        List<BusinessPartnerData> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            businessPartnerDataFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: " + db.myDataDao().getAll().toString());
                            Toasty.success(AddBPCustomer.this, "Add Successfully", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                Toast.makeText(AddBPCustomer.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }

}