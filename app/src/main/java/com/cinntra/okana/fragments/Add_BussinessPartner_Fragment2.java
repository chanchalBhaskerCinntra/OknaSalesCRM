package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.okana.R;

import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.adapters.CountryAdapter;
import com.cinntra.okana.adapters.IndustrySpinnerAdapter;
import com.cinntra.okana.adapters.PaymentAdapter;
import com.cinntra.okana.adapters.StateAdapter;
import com.cinntra.okana.databinding.FragmentAddPartner2Binding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.AddBusinessPartnerData;
import com.cinntra.okana.model.BPAddress;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.PayMentTerm;
import com.cinntra.okana.model.PostContactEmployee;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Add_BussinessPartner_Fragment2 extends Fragment implements View.OnClickListener {
    String TYPE = "";
    String industryCode;
    String shippingType;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.name_value)
//    EditText name_value;
//    @BindView(R.id.contact_owner_value)
//    EditText contact_owner_value;
//    @BindView(R.id.tab_2)
//    LinearLayout tab_2;
//    @BindView(R.id.general)
//    TextView general;
//    @BindView(R.id.contact)
//    TextView contact;
//    @BindView(R.id.tab_1)
//    LinearLayout tab_1;
//    @BindView(R.id.industry_view)
//    RelativeLayout industry_view;
//    @BindView(R.id.industry_spinner)
//    Spinner industry_spinner;
//    @BindView(R.id.type_view)
//    RelativeLayout type_view;
//    @BindView(R.id.type_spinner)
//    Spinner type_spinner;
//    @BindView(R.id.annual_revenue_value)
//    EditText annual_revenue_value;
//    @BindView(R.id.account_balance_value)
//    EditText account_balance_value;
//    @BindView(R.id.company_id_value)
//    EditText company_id_value;
//    @BindView(R.id.invoice_no_value)
//    EditText invoice_no_value;
//    @BindView(R.id.credit_limit_value)
//    EditText credit_limit_value;
//    @BindView(R.id.payment_term_value)
//    Spinner payment_term_value;
//    @BindView(R.id.parent_account_value)
//    EditText parent_account_value;
//    @BindView(R.id.ratingBar)
//    RatingBar ratingBar;
//    @BindView(R.id.mobile_value)
//    EditText mobile_value;
//    @BindView(R.id.email_value)
//    EditText email_value;
//    @BindView(R.id.website_value)
//    EditText website_value;
//    @BindView(R.id.bill_to_value)
//    EditText bill_to_value;
//    @BindView(R.id.ship_to_value)
//    EditText ship_to_value;
//    @BindView(R.id.remarks_value)
//    EditText remarks_value;
//    @BindView(R.id.upload_img)
//    ImageView upload_img;
//    @BindView(R.id.uploadview)
//    RelativeLayout uploadview;
//    @BindView(R.id.ship_block)
//    LinearLayout ship_block;
//    @BindView(R.id.general_frame)
//    FrameLayout general_frame;
//    @BindView(R.id.contact_frame)
//    FrameLayout contact_frame;
//    @BindView(R.id.create_button)
//    Button create_button;
//    @BindView(R.id.done_button)
//    Button done_button;
//    @BindView(R.id.sales_employee_spinner)
//    Spinner sales_employee_spinner;
//    @BindView(R.id.cardCode_value)
//    EditText cardCode_value;
//    @BindView(R.id.checkbox1)
//    CheckBox checkbox1;
//
//    @BindView(R.id.shipping_spinner)
//    Spinner shipping_spinner;
//
//    @BindView(R.id.shipping_spinner2)
//    Spinner shipping_spinner2;
//    @BindView(R.id.billing_name_value)
//    EditText billing_name_value;
//    @BindView(R.id.zip_code_value)
//    EditText zip_code_value;
//    @BindView(R.id.billing_address_value)
//    EditText billing_address_value;
//
//    @BindView(R.id.shipping_name_value)
//    EditText shipping_name_value;
//    @BindView(R.id.shipping_address_value)
//    EditText shipping_address_value;
//    @BindView(R.id.zipcode_value2)
//    EditText zipcode_value2;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.country_value)
//    Spinner country_value;
//    @BindView(R.id.state_value)
//    Spinner state_value;
//    @BindView(R.id.ship_country_value)
//    Spinner ship_country_value;
//    @BindView(R.id.ship_state_value)
//    Spinner ship_state_value;

    int salesEmployeeCode = 0;

    String payment_term = "";
    String[] shippinngType;
    String billshipType;
    String ship_shiptype;
    FragmentActivity act;
    String billtoState, billtoStateCode, billtoCountrycode, billtoCountryName, shiptoState, shiptoStateCode, shiptoCountrycode, shiptoCountryName;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter, shiptostateAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    ArrayList<StateData> shiptostateList = new ArrayList<>();

    public Add_BussinessPartner_Fragment2() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Add_BussinessPartner_Fragment2 newInstance(String param1, String param2) {
        Add_BussinessPartner_Fragment2 fragment = new Add_BussinessPartner_Fragment2();
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

    FragmentAddPartner2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        act = getActivity();
        // Inflate the layout for this fragment
        binding = FragmentAddPartner2Binding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.fragment_add_partner2, container, false);
        //  ButterKnife.bind(this,v);
        shippinngType = getResources().getStringArray(R.array.shippingType);
        setDefaults();

        if (Globals.checkInternet(getActivity()))
            callSalessApi();

        callPaymentApi();

        return binding.getRoot();
    }


    private void setDefaults() {
        frameManager(binding.generalFrame, binding.contactFrame, binding.general, binding.contact);
        binding.fragmentAddpartnercontact.addressSection.doneButton.setVisibility(View.GONE);
        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_bussiness));
        binding.fragmentAddpartnercontact.createButton.setOnClickListener(this);
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.tab2.setOnClickListener(this);
        binding.general.setOnClickListener(this);
        binding.tab1.setOnClickListener(this);
        binding.contact.setOnClickListener(this);
//        binding.fragmentAddpartnercontact.addressSection.countryValue.setOnClickListener(this);
//        binding.fragmentAddpartnercontact.addressSection.stateValue.setOnClickListener(this);
//        binding.fragmentAddpartnercontact.addressSection.shipCountryValue.setOnClickListener(this);
//        binding.fragmentAddpartnercontact.addressSection.shipStateValue.setOnClickListener(this);
        eventManager();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.tab_1:
            case R.id.general:
                frameManager(binding.generalFrame, binding.contactFrame, binding.general, binding.contact);
                break;
            case R.id.tab_2:
            case R.id.contact:
                frameManager(binding.contactFrame, binding.generalFrame, binding.contact, binding.general);
                break;


            case R.id.create_button:
                String name = binding.fragmentAddpartnergeneral.nameValue.getText().toString().trim();
                String balance = binding.fragmentAddpartnergeneral.accountBalanceValue.getText().toString().trim();
//                String anlrvnue = binding.fragmentAddpartnergeneral.annualRevenueValue.getText().toString().trim();
                String credit_limit = binding.fragmentAddpartnergeneral.creditLimitValue.getText().toString().trim();
                String parenT_account = binding.fragmentAddpartnergeneral.parentAccount.getText().toString().trim();
                String invoice = binding.fragmentAddpartnergeneral.invoiceNoValue.getText().toString().trim();
                String rating = String.valueOf(binding.fragmentAddpartnergeneral.ratingBar.getRating());
                String cardCode = binding.fragmentAddpartnergeneral.cardCodeValue.getText().toString().trim();
                String mobile = binding.fragmentAddpartnercontact.mobileValue.getText().toString().trim();
                String email = binding.fragmentAddpartnercontact.emailValue.getText().toString().trim();
//                String website = binding.fragmentAddpartnercontact.websiteValue.getText().toString().trim();

                if (validation(cardCode, "cowner", mobile, email, industryCode)) {


                    /************************ BP Address *****************************/
                    ArrayList<BPAddress> postbpAddresses = new ArrayList<>();

                    BPAddress bp = new BPAddress();
                    bp.setBPCode(cardCode);
                    bp.setAddressName(binding.fragmentAddpartnercontact.addressSection.billingNameValue.getText().toString());
                    bp.setStreet(binding.fragmentAddpartnercontact.addressSection.billingAddressValue.getText().toString());
                    bp.setBlock("");
                    bp.setZipCode(binding.fragmentAddpartnercontact.addressSection.zipCodeValue.getText().toString());
                    bp.setCity("");
                    bp.setCountry(billtoCountrycode);  //countryCode
                    bp.setState(billtoStateCode);
                    bp.setUCountry(billtoCountrycode);
                    bp.setUState(billtoState);
                    bp.setUShptyp(billshipType);
                    bp.setRowNum("0");
                    bp.setAddressType("bo_BillTo");
                    postbpAddresses.add(bp);


                    BPAddress bp1 = new BPAddress();
                    if (binding.fragmentAddpartnercontact.addressSection.checkbox1.isChecked()) {
                        bp1.setZipCode(binding.fragmentAddpartnercontact.addressSection.zipcodeValue2.getText().toString());
                        bp1.setAddressName(binding.fragmentAddpartnercontact.addressSection.shippingNameValue.getText().toString());
                        bp1.setStreet(binding.fragmentAddpartnercontact.addressSection.shippingAddressValue.getText().toString());
                        bp1.setUState(shiptoState);
                        bp1.setUCountry(shiptoCountryName);
                        bp1.setUShptyp(ship_shiptype);


                    } else {
                        bp1.setAddressName(binding.fragmentAddpartnercontact.addressSection.billingNameValue.getText().toString());
                        bp1.setStreet(binding.fragmentAddpartnercontact.addressSection.billingAddressValue.getText().toString());
                        bp1.setZipCode(binding.fragmentAddpartnercontact.addressSection.zipCodeValue.getText().toString());
                        bp1.setUCountry(billtoCountryName);
                        bp1.setUState(billtoState);
                        bp1.setUShptyp(billshipType);


                    }
                    bp.setRowNum("1");
                    bp1.setState(shiptoStateCode);
                    bp1.setCountry(shiptoCountrycode);
                    bp1.setBPCode(cardCode);
                    bp1.setBlock("B");
                    bp1.setCity("");
                    bp1.setAddressType("bo_ShipTo");
                    postbpAddresses.add(bp1);

                    /********************* Con Employee ************************/
                    ArrayList<PostContactEmployee> postcontactEmployees = new ArrayList<>();
                    PostContactEmployee postemp = new PostContactEmployee();
                    postemp.setName(binding.fragmentAddpartnercontact.contactOwnerValue.getText().toString());
                   /* postemp.setCardCode("Code");
                    postemp.setAddress("Cinntra");
                    postemp.setPosition("1");
                    postemp.setPhone1("96868768");
                    postemp.setPhone2("7863823132");
                    postemp.setMobilePhone("896787632");
                    postemp.setFax("jkdf");
                    postemp.setPager("4");
                    postemp.setRemarks1("Remarks1");
                    postemp.setRemarks2("Remarks1");
                    postemp.setGender("Male");
                    postemp.setTitle("Mr");
                    postemp.setPassword("Pass");
                    postemp.setFirstName("First");
                    postemp.setMiddleName("Middle");
                    postemp.setLastName("LastName");*/

                    postcontactEmployees.add(postemp);


                    AddBusinessPartnerData contactExtension = new AddBusinessPartnerData();
                    contactExtension.setUCurbal(balance);
                    contactExtension.setPayTermsGrpCode(payment_term);
                    contactExtension.setCreditLimit(credit_limit);
                    contactExtension.setURating(rating);
                    contactExtension.setUInvno(invoice);
                    contactExtension.setUParentacc(parenT_account);
                    contactExtension.setCardCode(cardCode);
                    contactExtension.setCardName(name);
                    contactExtension.setCardType("cCustomer"); //select value from spinner
                    contactExtension.setSalesPersonCode(String.valueOf(salesEmployeeCode));
                    contactExtension.setEmailAddress(email);
                    contactExtension.setPhone1(mobile);
                    contactExtension.setUType(TYPE);
                    contactExtension.setNotes(binding.fragmentAddpartnercontact.remarksValue.getText().toString());
//                    contactExtension.setUAnlrvn(anlrvnue);
                    contactExtension.setIndustry(industryCode);
                    contactExtension.setUAccnt("");
//                    contactExtension.setWebsite(website);
                    contactExtension.setDiscountPercent("");
                    contactExtension.setCurrency("INR");
                    contactExtension.setIntrestRatePercent("");
                    contactExtension.setCommissionPercent("");
                    contactExtension.setAttachmentEntry("");
                    contactExtension.setUBpgrp("");
                    contactExtension.setUContownr(binding.fragmentAddpartnercontact.contactOwnerValue.getText().toString());
                    contactExtension.setContactPerson(binding.fragmentAddpartnercontact.contactOwnerValue.getText().toString());
                    contactExtension.setCreateDate(Globals.getTodaysDate());
                    contactExtension.setCreateTime(Globals.getTCurrentTime());
                    contactExtension.setUpdateDate("");
                    contactExtension.setUpdateTime("");
                    contactExtension.setBPAddresses(postbpAddresses);
                    //    contactExtension.setContactEmployees(postcontactEmployees);
                    if (Globals.checkInternet(getActivity()))
                        createBP(contactExtension);
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

    private void eventManager() {

          /* binding.fragmentAddpartnercontact.addressSection.shipCountryValue.setAdapter(countryAdapter);

           binding.fragmentAddpartnercontact.addressSection.shipCountryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   shiptoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                   shiptoCountryName = MainActivity.countrylistFromLocal.get(position).getName();
                   shiptoState = "";
                   shiptoStateCode = "";
                   callStateApi(shiptoCountrycode,"shipto");

               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

                   shiptoCountrycode = MainActivity.countrylistFromLocal.get(0).getCode();
                   shiptoCountryName = MainActivity.countrylistFromLocal.get(0).getName();
               }
           });

           binding.fragmentAddpartnercontact.addressSection.shipStateValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   shiptoState= shiptostateList.get(position).getName();
                   shiptoStateCode= shiptostateList.get(position).getCode();
               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {
               }
           });

           if(stateList.isEmpty()){
               StateData sta = new StateData();
               sta.setName("Select State");
               stateList.add(sta);
           }else if(shiptostateList.isEmpty()){
               StateData sta = new StateData();
               sta.setName("Select State");
               shiptostateList.add(sta);
           }

           binding.fragmentAddpartnergeneral.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
          {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  if(salesEmployeeItemList.size()>0&&position>0)
                      salesEmployeeCode = Integer.valueOf(salesEmployeeItemList.get(position).getSalesEmployeeCode());
              }
              @Override
              public void onNothingSelected(AdapterView<?> parent) {
                  salesEmployeeCode = 0;
              }
          });*/


        binding.fragmentAddpartnergeneral.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TYPE = binding.fragmentAddpartnergeneral.typeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TYPE = binding.fragmentAddpartnergeneral.typeSpinner.getSelectedItem().toString();
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
                    binding.fragmentAddpartnercontact.addressSection.shipBlock.setVisibility(View.VISIBLE);
                } else {
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

            }
        });

    }

    private void callStateApi(String countryCode, String type) {

        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.code() == 200) {
                    if (type.equalsIgnoreCase("billto")) {
                        stateList.clear();
                        if (response.body().getData().size() > 0) {

                            stateList.addAll(response.body().getData());
                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            stateList.add(sta);
                        }
//                        stateAdapter = new StateAdapter(getContext(), stateList);
//                        binding.fragmentAddpartnercontact.addressSection.stateValue.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
                    } else {
                        shiptostateList.clear();
                        if (response.body().getData().size() > 0) {

                            shiptostateList.addAll(response.body().getData());
                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            shiptostateList.add(sta);
                        }

//                        shiptostateAdapter = new StateAdapter(getContext(),shiptostateList);
//                        binding.fragmentAddpartnercontact.addressSection.shipStateValue.setAdapter(shiptostateAdapter);
                        shiptostateAdapter.notifyDataSetChanged();
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

    private boolean validation(String fname, String cowner,
                               String mobile, String email, String industryCode) {

        if (fname.isEmpty()) {
            Globals.showMessage(getContext(), "Cannot be empty");
            return false;
        } else if (cowner.isEmpty()) {
            Globals.showMessage(getContext(), "Contact owner be empty");
            return false;
        } else if (mobile.isEmpty()) {
            Globals.showMessage(getContext(), "Mobile number be empty");
            return false;
        } else if (industryCode.trim().equalsIgnoreCase("-1")) {
            Globals.showMessage(getContext(), "Select Industry.");
            return false;
        } else if (email.isEmpty()) {
            Globals.showMessage(getContext(), "Email be empty");
            return false;
        }
        return true;
    }


    /********************* APIs ***************************/
    List<PayMentTerm> getPaymenterm = new ArrayList<>();

    private void callPaymentApi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getPaymentList().observe(getActivity(), new Observer<List<PayMentTerm>>() {
//            @Override
//            public void onChanged(List<PayMentTerm> payMentTermList) {
//                if (payMentTermList == null || payMentTermList.size() == 0) {
//                    Globals.setmessage(getContext());
//                } else {
//                    getPaymenterm.clear();
//                    getPaymenterm = payMentTermList;
//                    binding.fragmentAddpartnergeneral.paymentTermValue.setAdapter(new PaymentAdapter(getActivity(), getPaymenterm));
//                }
//
//            }
//        });

        if (MainActivity.paymentTermListFromLocal == null || MainActivity.paymentTermListFromLocal.size() == 0) {
            Globals.setmessage(getContext());
        } else {
            getPaymenterm.clear();
            getPaymenterm = MainActivity.paymentTermListFromLocal;
            binding.fragmentAddpartnergeneral.paymentTermValue.setAdapter(new PaymentAdapter(getActivity(), getPaymenterm));
        }
    }


    List<IndustryItem> IndustryItemItemList = new ArrayList<>();

    private void callStagesApi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getIndustryList().observe(this, new Observer<List<IndustryItem>>() {
//            @Override
//            public void onChanged(@Nullable List<IndustryItem> itemsList) {
//                if(itemsList == null || itemsList.size()== 0){
//                    Globals.setmessage( getActivity());
//                }else {
//                    IndustryItemItemList = itemsList;
//                    industry_spinner.setAdapter(new IndustrySpinnerAdapter(getActivity(),itemsList));
//                }
//            }
//        });

        if (MainActivity.industryListFromLocal == null || MainActivity.industryListFromLocal.size() == 0) {
            Globals.setmessage(act);
        } else {
            IndustryItemItemList = MainActivity.industryListFromLocal;
            binding.fragmentAddpartnergeneral.industrySpinner.setAdapter(new IndustrySpinnerAdapter(act, MainActivity.industryListFromLocal));
            industryCode = IndustryItemItemList.get(0).getIndustryCode();

        }
    }


    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(getActivity(), new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getActivity());
                } else {
                    salesEmployeeItemList = itemsList;
//                    binding.fragmentAddpartnergeneral.salesEmployeeSpinner.setAdapter(new SalesEmployeeAdapter(getActivity(), itemsList));
                    callStagesApi();
                }
            }
        });
    }

    /********************* Add Contact API *************************/

    private void createBP(AddBusinessPartnerData in) {
        binding.loader.loader.setVisibility(View.VISIBLE);
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().addnewCustomer(in);
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Globals.SelectedItems.clear();
                    Toast.makeText(act, "Posted Successfully.", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(act, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(act, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}