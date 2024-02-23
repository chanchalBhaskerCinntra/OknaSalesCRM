package com.cinntra.okana.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.adapters.BusinessPartnerDetail;
import com.cinntra.okana.adapters.ContactPersonAdapter;
import com.cinntra.okana.adapters.CountryAdapter;
import com.cinntra.okana.adapters.IndustrySpinnerAdapter;
import com.cinntra.okana.adapters.PaymentAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAdapter;
import com.cinntra.okana.adapters.StateAdapter;
import com.cinntra.okana.databinding.UpdatePartnerBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.BPModel.AddBusinessPartnerData;
import com.cinntra.okana.model.BPAddress;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.ContactEmployeesModel;
import com.cinntra.okana.model.ContactPerson;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.PayMentTerm;
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


public class Update_BussinessPartner_Fragment extends Fragment implements View.OnClickListener {

    String TYPE = "";
    String industryCode;
    //    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.name_value)
//    EditText name_value;
//    @BindView(R.id.contact_owner_value)
//    Spinner contact_owner_value;
//
//    @BindView(R.id.industry_view)
//    RelativeLayout industry_view;
//    @BindView(R.id.industry_spinner)
//    Spinner industry_spinner;
//    @BindView(R.id.type_view)
//    RelativeLayout type_view;
//    @BindView(R.id.type_spinner)
//    TextView type_spinner;
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
//
//    @BindView(R.id.remarks_value)
//    EditText remarks_value;
//    @BindView(R.id.upload_img)
//    ImageView upload_img;
//    @BindView(R.id.uploadview)
//    RelativeLayout uploadview;
//    @BindView(R.id.create_button)
//    Button create_button;
//    @BindView(R.id.done_button)
//    Button done_button;
//    @BindView(R.id.sales_employee_spinner)
//    Spinner sales_employee_spinner;
//    @BindView(R.id.cardCode_value)
//    EditText cardCode_value;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//
//    @BindView(R.id.billing_name_value)
//    EditText billing_name_value;
//    @BindView(R.id.zip_code_value)
//    EditText zip_code_value;
//
//    @BindView(R.id.shipping_spinner)
//    Spinner shipping_spinner;
//    @BindView(R.id.billing_address_value)
//    EditText billing_address_value;
//    @BindView(R.id.checkboxManager)
//    RelativeLayout checkboxManager;
//    @BindView(R.id.ship_block)
//    LinearLayout ship_block;
//
//    @BindView(R.id.shipping_name_value)
//    EditText shipping_name_value;
//    @BindView(R.id.shipping_address_value)
//    EditText shipping_address_value;
//    @BindView(R.id.zipcode_value2)
//    EditText zipcode_value2;
//
//    @BindView(R.id.company_email_value)
//    EditText company_email_value;
//    @BindView(R.id.company_no_value)
//    EditText company_no_value;
//    @BindView(R.id.shipping_spinner2)
//    Spinner shipping_spinner2;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.ok)
//    ImageView ok;
//    @BindView(R.id.country_value)
//    Spinner country_value;
//    @BindView(R.id.state_value)
//    Spinner state_value;
//    @BindView(R.id.ship_country_value)
//    EditText ship_country_value;
//    @BindView(R.id.shipcity_value)
//    EditText shipcity_value;
//    @BindView(R.id.city_value)
//    EditText city_value;
//    @BindView(R.id.ship_state_value)
//    EditText ship_state_value;
    String payment_term = "";
    FragmentActivity act;
    BusinessPartnerData customerItem;
    String[] shippinngType;
    String salesEmployeeCode = "";
    String salesPersonCode = "";
    String billrownum = "";
    String shiprownum = "";
    String internalCode = "";
    String billshiptype = "";
    String ship_shiptype = "";
    String billtoState, billtoCountrycode, billtoCountryName, shiptoState, shiptoCountrycode, shiptoCountryName, shiptoStateCode, billtoStateCode;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter;
    String contactpersonName = "";
    String contactpersonCode = "";
    String contactpersonmobile = "";
    String contactpersonemail = "";
    ContactPersonAdapter contactPersonAdapter;
    ArrayList<StateData> stateList = new ArrayList<>();
    DatabaseClick dbClick;
    List<ContactPersonData> contactPersonDataList = new ArrayList<>();
    private static boolean ESCAPED = true;

    UpdatePartnerBinding binding;

    public Update_BussinessPartner_Fragment(BusinessPartnerDetail businessPartnerDetail, BusinessPartnerData customerItem) {
        this.customerItem = customerItem;
        this.dbClick = (DatabaseClick) businessPartnerDetail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        act = getActivity();
        // Inflate the layout for this fragment
        binding = UpdatePartnerBinding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.update_partner, container, false);
        // ButterKnife.bind(this, v);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        setDefaults();
        setData(customerItem);
        if (Globals.checkInternet(getActivity()))
            callContactEmployeeApi(customerItem.getCardCode());


        eventManager();


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!Globals.branchData.isEmpty()) {
            binding.updateaddressSection.shippingNameValue.setText(Globals.branchData.get(0).getAddressName());
            binding.updateaddressSection.zipcodeValue2.setText(Globals.branchData.get(0).getZipCode());
            binding.updateaddressSection.shippingAddressValue.setText(Globals.branchData.get(0).getStreet());
            shiptoState = Globals.branchData.get(0).getU_STATE();
            shiptoStateCode = Globals.branchData.get(0).getState();
            binding.updateaddressSection.shipcityValue.setText(Globals.branchData.get(0).getCity());
            shiptoCountrycode = Globals.branchData.get(0).getCountry();
            shiptoCountryName = Globals.branchData.get(0).getU_COUNTRY();
        }
      /*  if (shiptoCountryName != null) {
            binding.updateaddressSection.shipCountryValue.setText(shiptoCountryName);

        }

        if (shiptoState != null) {
            binding.updateaddressSection.shipStateValue.setText(shiptoState);

        }
*/

    }


    private void callContactEmployeeApi(String id) {
        ContactPersonData contactPersonData = new ContactPersonData();
        contactPersonData.setCardCode(id);

        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().contactemplist(contactPersonData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {

                if (response.code() == 200) {
                    contactPersonDataList.clear();

                    if (response.body().getData().size() > 0) {
                        contactPersonDataList.addAll(response.body().getData());
                    } else {
                        ContactPersonData cd = new ContactPersonData();
                        cd.setFirstName("No Contact");
                        cd.setLastName("Person");
                        contactPersonDataList.add(cd);
                    }
                    contactPersonAdapter = new ContactPersonAdapter(getContext(), contactPersonDataList);
                    binding.contactOwnerValue.setAdapter(contactPersonAdapter);

                    contactPersonAdapter.notifyDataSetChanged();
                    if (customerItem.getUContownr() != null && !customerItem.getUContownr().trim().isEmpty())
                        binding.contactOwnerValue.setSelection(Globals.getContactPos(contactPersonDataList, customerItem.getUContownr().trim()));

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
        callSalessApi();

    }

    private void callStateApi(String countryCode, String code) {

        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.body().getStatus() == 200) {
                    if (code.equalsIgnoreCase("billto")) {


                        stateList.clear();
                        if (response.body().getData().size() > 0) {

                            stateList.addAll(response.body().getData());

                        } else {
                            StateData sta = new StateData();
                            sta.setName("Select State");
                            stateList.add(sta);
                        }
//                        stateAdapter = new StateAdapter(act, stateList);
//                        binding.updateaddressSection.stateValue.setAdapter(stateAdapter);

                        stateAdapter.notifyDataSetChanged();
                        if (customerItem.getBPAddresses().get(getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo")).getState() != null){

                        }
//                            binding.updateaddressSection.stateValue.setSelection(Globals.getStatePo(stateList, customerItem.getBPAddresses().get(getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo")).getUState()));

                    }
                } else {
                    Globals.ErrorMessage(requireContext(), response.body().getMessage());
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
//                    Gson gson = new GsonBuilder().create();
//                    QuotationResponse mError = new QuotationResponse();
//                    try {
//                        String s = response.errorBody().string();
//                        mError = gson.fromJson(s, QuotationResponse.class);
//                        Toast.makeText(getContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        //handle failure to read error
//                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setDisable() {

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.nameValue.getApplicationWindowToken(), 0);

        binding.headerBottomroundEdit.add.setOnClickListener(this);
        binding.nameValue.setClickable(false);
        binding.nameValue.setFocusable(false);

        binding.companyIdValue.setClickable(false);
        binding.companyIdValue.setFocusable(false);

        binding.contactOwnerValue.setFocusable(false);
        binding.contactOwnerValue.setClickable(false);
        binding.createButton.setVisibility(View.GONE);
        binding.salesEmployeeSpinner.setEnabled(false);
        binding.industrySpinner.setEnabled(false);
        binding.typeSpinner.setEnabled(false);

        binding.annualRevenueValue.setClickable(false);
        binding.annualRevenueValue.setFocusable(false);

        binding.accountBalanceValue.setFocusable(false);
        binding.accountBalanceValue.setClickable(false);

        binding.invoiceNoValue.setClickable(false);
        binding.invoiceNoValue.setFocusable(false);

        binding.creditLimitValue.setFocusable(false);
        binding.creditLimitValue.setClickable(false);

        binding.paymentTerm.setFocusable(false);
        binding.paymentTerm.setClickable(false);

        binding.parentAccountValue.setFocusable(false);
        binding.parentAccountValue.setClickable(false);

        binding.mobileValue.setClickable(false);
        binding.mobileValue.setFocusable(false);

        binding.emailValue.setClickable(false);
        binding.emailValue.setFocusable(false);

        binding.websiteValue.setClickable(false);
        binding.websiteValue.setFocusable(false);


        binding.updateaddressSection.shippingAddressValue.setFocusableInTouchMode(false);
        binding.updateaddressSection.shippingAddressValue.setFocusable(false);
        binding.updateaddressSection.shippingAddressValue.setClickable(false);

        binding.updateaddressSection.billingAddressValue.setClickable(false);
        binding.updateaddressSection.billingAddressValue.setFocusable(false);
        binding.updateaddressSection.billingAddressValue.setFocusableInTouchMode(false);

        binding.updateaddressSection.billingNameValue.setFocusableInTouchMode(false);
        binding.updateaddressSection.billingNameValue.setFocusable(false);
        binding.updateaddressSection.billingNameValue.setClickable(false);

        binding.updateaddressSection.zipCodeValue.setClickable(false);
        binding.updateaddressSection.zipCodeValue.setFocusable(false);
        binding.updateaddressSection.zipCodeValue.setFocusableInTouchMode(false);


        binding.updateaddressSection.shippingSpinner.setEnabled(false);

        binding.remarksValue.setClickable(false);
        binding.remarksValue.setFocusable(false);

        binding.createButton.setVisibility(View.GONE);
        binding.createButton.setClickable(false);
        binding.createButton.setEnabled(false);

        binding.cardCodeValue.setClickable(false);
        binding.cardCodeValue.setFocusable(false);


        binding.updateaddressSection.shippingAddressValue.setFocusable(false);
        binding.updateaddressSection.shippingAddressValue.setClickable(false);

        binding.updateaddressSection.billingAddressValue.setClickable(false);
        binding.updateaddressSection.billingAddressValue.setFocusable(false);

        binding.updateaddressSection.billingNameValue.setFocusable(false);
        binding.updateaddressSection.billingNameValue.setClickable(false);

        binding.updateaddressSection.shippingNameValue.setClickable(false);
        binding.updateaddressSection.shippingNameValue.setFocusable(false);

        binding.updateaddressSection.zipcodeValue2.setClickable(false);
        binding.updateaddressSection.zipcodeValue2.setFocusable(false);

        binding.updateaddressSection.zipCodeValue.setClickable(false);
        binding.updateaddressSection.zipCodeValue.setFocusable(false);

       /* binding.updateaddressSection.countryValue.setClickable(false);
        binding.updateaddressSection.shipStateValue.setClickable(false);
        binding.updateaddressSection.shipCountryValue.setClickable(false);
        binding.updateaddressSection.stateValue.setClickable(false);*/
        binding.updateaddressSection.shippingSpinner2.setEnabled(false);
        ESCAPED = true;
        binding.headerBottomroundEdit.add.setVisibility(View.VISIBLE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);

    }

    private void setDefaults() {

        shippinngType = getActivity().getResources().getStringArray(R.array.shippingType);
        binding.headerBottomroundEdit.headTitle.setText(getResources().getString(R.string.update_bussiness));
        binding.createButton.setText(getResources().getString(R.string.update));
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
        binding.createButton.setOnClickListener(this);
        binding.headerBottomroundEdit.ok.setOnClickListener(this);
//        binding.updateaddressSection.checkboxManager.setOnClickListener(this);
        binding.cardCodeValue.setClickable(false);
        binding.cardCodeValue.setFocusable(false);

    }


    private void setData(BusinessPartnerData customerItem) {
        if (customerItem.getContactEmployees().size() > 0) {
            contactpersonName = customerItem.getContactEmployees().get(0).getName();
            contactpersonCode = customerItem.getContactEmployees().get(0).getInternalCode();
            contactpersonmobile = customerItem.getContactEmployees().get(0).getMobilePhone();
            contactpersonemail = customerItem.getContactEmployees().get(0).getE_Mail();
        }

        billtoCountrycode = customerItem.getBPAddresses().get(getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo")).getCountry();
        billtoStateCode = customerItem.getBPAddresses().get(getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo")).getState();
        billtoCountryName = customerItem.getBPAddresses().get(getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo")).getUCountry();

        billtoState = customerItem.getBPAddresses().get(getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo")).getUState();
        shiptoCountrycode = customerItem.getBPAddresses().get(getAddres_Ship_Po(customerItem.getBPAddresses(), "bo_ShipTo")).getUCountry();
        shiptoStateCode = customerItem.getBPAddresses().get(getAddres_Ship_Po(customerItem.getBPAddresses(), "bo_ShipTo")).getUState();
        shiptoCountryName = customerItem.getBPAddresses().get(getAddres_Ship_Po(customerItem.getBPAddresses(), "bo_ShipTo")).getUCountry();
        shiptoState = customerItem.getBPAddresses().get(getAddres_Ship_Po(customerItem.getBPAddresses(), "bo_ShipTo")).getUState();
        callStateApi(billtoCountrycode, "billto");

        countryAdapter = new CountryAdapter(getContext(), MainActivity.countrylistFromLocal);
//        binding.updateaddressSection.countryValue.setAdapter(countryAdapter);
        if (customerItem.getSalesPersonCode().size() > 0) {
            salesPersonCode = customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode();
            salesEmployeeCode = customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode();
        }
        binding.nameValue.setText("" + customerItem.getCardName());
                /* if(customerItem.getContactEmployees().size() > 0)
                        internalCode = customerItem.getContactEmployees().get(0).getInternalCode();*/
        binding.cardCodeValue.setText("" + customerItem.getCardCode());
//                 contact_owner_value.setText(""+customerItem.getContactPerson());
        if (customerItem.getUType().size() > 0)
            TYPE = customerItem.getUType().get(0).getId().toString();
        industryCode = customerItem.getIndustry();
        //shiptype = customerItem.get();


        if (customerItem.getUAccnt() != null)
            binding.accountBalanceValue.setText("" + customerItem.getUCurbal());
        if (customerItem.getUInvno() != null)
            binding.invoiceNoValue.setText("" + customerItem.getUInvno());
        if (customerItem.getUParentacc() != null)
            binding.parentAccountValue.setText("" + customerItem.getUParentacc());
                 /*if(customerItem.getURating() != null)
                     ratingBar.setRating(Float.valueOf(customerItem.getURating()));*/
        if (customerItem.getPayTermsGrpCode().size() > 0)
            payment_term = customerItem.getPayTermsGrpCode().get(0).getGroupNumber();
        if (customerItem.getCreditLimit() != null)
            binding.creditLimitValue.setText("" + customerItem.getCreditLimit());
        if (customerItem.getUType() != null && customerItem.getUType().size() > 0)
            binding.typeSpinner.setText(customerItem.getUType().get(0).getType());
        if (customerItem.getUAnlrvn() != null)
            binding.annualRevenueValue.setText(customerItem.getUAnlrvn());
        if (customerItem.getWebsite() != null)
            binding.websiteValue.setText(customerItem.getWebsite());
        if (customerItem.getEmailAddress() != null)
            binding.companyEmailValue.setText(customerItem.getEmailAddress());

                 /*mobile_value.setText(customerItem.getContactEmployees().get(0).getMobilePhone());
                 email_value.setText(customerItem.getContactEmployees().get(0).getE_Mail());*/
        if (customerItem.getPhone1() != null)
            binding.companyNoValue.setText(customerItem.getPhone1());
        if (customerItem.getNotes() != null)
            binding.remarksValue.setText(customerItem.getNotes());


        //eventManager();

        /******************* Set Address Data ********************/

        int pos_bo_BillTo = getAddres_Bill_Po(customerItem.getBPAddresses(), "bo_BillTo");
        int pos_bo_ShipTo = getAddres_Ship_Po(customerItem.getBPAddresses(), "bo_ShipTo");

        if (pos_bo_BillTo != -1) {
            binding.updateaddressSection.billingNameValue.setText(customerItem.getBPAddresses().get(pos_bo_BillTo).getAddressName());
            binding.updateaddressSection.zipCodeValue.setText(customerItem.getBPAddresses().get(pos_bo_BillTo).getZipCode());
            binding.updateaddressSection.billingAddressValue.setText(customerItem.getBPAddresses().get(pos_bo_BillTo).getStreet());
            billrownum = customerItem.getBPAddresses().get(pos_bo_BillTo).getRowNum();
            binding.updateaddressSection.cityValue.setText(customerItem.getBPAddresses().get(pos_bo_BillTo).getCity());
            if (billtoCountryName != null) {
//                binding.updateaddressSection.countryValue.setSelection(Globals.getCountrypos(MainActivity.countrylistFromLocal, billtoCountryName));
            }
            billshiptype = customerItem.getBPAddresses().get(pos_bo_BillTo).getUShptyp();
            if (billshiptype != null)
                binding.updateaddressSection.shippingSpinner.setSelection(Globals.getShipTypePo(shippinngType, billshiptype));
        }
        if (pos_bo_ShipTo != -1) {


            binding.updateaddressSection.shippingNameValue.setText(customerItem.getBPAddresses().get(pos_bo_ShipTo).getAddressName());
            binding.updateaddressSection.zipcodeValue2.setText(customerItem.getBPAddresses().get(pos_bo_ShipTo).getZipCode());
            binding.updateaddressSection.shippingAddressValue.setText(customerItem.getBPAddresses().get(pos_bo_ShipTo).getStreet());
            binding.updateaddressSection.shipcityValue.setText(customerItem.getBPAddresses().get(pos_bo_ShipTo).getCity());
            shiprownum = customerItem.getBPAddresses().get(pos_bo_ShipTo).getRowNum();

            ship_shiptype = customerItem.getBPAddresses().get(pos_bo_ShipTo).getUShptyp();
            if (ship_shiptype != null)
                binding.updateaddressSection.shippingSpinner2.setSelection(Globals.getShipTypePo(shippinngType, ship_shiptype));

        }
        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0) {
                    salesPersonCode = salesEmployeeItemList.get(position).getSalesEmployeeCode();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        binding.updateaddressSection.shippingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billshiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (billshiptype != null)
                    binding.updateaddressSection.shippingSpinner.setSelection(Globals.getShipTypePo(shippinngType, billshiptype));
            }
        });

        binding.updateaddressSection.shippingSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ship_shiptype = shippinngType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (ship_shiptype != null)
                    binding.updateaddressSection.shippingSpinner2.setSelection(Globals.getShipTypePo(shippinngType, ship_shiptype));
            }
        });


        binding.paymentTermValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getPaymenterm.size() > 0) {
                    payment_term = getPaymenterm.get(position).getGroupNumber();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        callPaymentApi();
        binding.loader.loader.setVisibility(View.GONE);
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
                if (obj.getDefault() == 1) {
                    po = bpAddresses.indexOf(obj);
                    break;
                }
            }
        }
        return po;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                Globals.hideKeybaord(v, getContext());
                getActivity().onBackPressed();
                break;
            case R.id.add:
//                if(ESCAPED){
//                    Globals.openKeyboard(getContext());
//                    setEnable();
//                    setDefaults();
//                }else {
//                  //  setDisable();
//                }
                break;


            case R.id.create_button:
            case R.id.ok:

                String balance = binding.accountBalanceValue.getText().toString().trim();
                String credit_limit = binding.creditLimitValue.getText().toString().trim();
                String parenT_account = binding.parentAccountValue.getText().toString().trim();
//                String rating = String.valueOf(binding.ratingBar.getRating());
                String invoice_n0 = binding.invoiceNo.getText().toString().trim();
                String anlrvnue = binding.annualRevenueValue.getText().toString().trim();
                String name = binding.nameValue.getText().toString().trim();
                String cardCode = binding.cardCodeValue.getText().toString().trim();
                String mobile = binding.mobileValue.getText().toString().trim();
                String email = binding.emailValue.getText().toString().trim();
                String website = binding.websiteValue.getText().toString().trim();
                String comp_email = binding.companyEmailValue.getText().toString().trim();
                String comp_no = binding.companyNoValue.getText().toString().trim();
                if (validation(cardCode, "cowner", comp_no, comp_email, industryCode)) {

                    //      setDisable();
                    /************************ BP Address *****************************/
                    ArrayList<BPAddress> postbpAddresses = new ArrayList<>();

                    BPAddress bp = new BPAddress();
                    bp.setBPCode(cardCode);
                    if (!billrownum.isEmpty())
                        bp.setRowNum(billrownum);
                    bp.setId(customerItem.getBPAddresses().get(0).getId());
                    bp.setBpid(customerItem.getBPAddresses().get(0).getBpid());
                    bp.setAddressName(binding.updateaddressSection.billingNameValue.getText().toString());
                    bp.setStreet(binding.updateaddressSection.billingAddressValue.getText().toString());
                    bp.setBlock("B");
                    bp.setZipCode(binding.updateaddressSection.zipCodeValue.getText().toString());
                    bp.setCity(binding.updateaddressSection.cityValue.getText().toString());
                    bp.setCountry(billtoCountrycode);
                    bp.setState(billtoStateCode);
                    bp.setUCountry(billtoCountryName);
                    bp.setUState(billtoState);
                    bp.setAddressType("bo_BillTo");
                    bp.setUShptyp(billshiptype);
                    postbpAddresses.add(bp);

                    BPAddress bp1 = new BPAddress();
                    int branchId;
                    if (Globals.branchData.size() > 0) {
                        branchId = Globals.branchData.get(0).getId();
                    } else
                        branchId = customerItem.getBPAddresses().get(1).getId();

                    bp1.setId(branchId);
                    bp1.setBpid(customerItem.getBPAddresses().get(1).getBpid());
                    bp1.setBPCode(cardCode);
                    if (!shiprownum.isEmpty())
                        bp1.setRowNum(shiprownum);
                    bp1.setAddressName(binding.updateaddressSection.shippingNameValue.getText().toString());
                    bp1.setStreet(binding.updateaddressSection.shippingAddressValue.getText().toString());
                    bp1.setBlock("B");
                    bp1.setZipCode(binding.updateaddressSection.zipcodeValue2.getText().toString());
                    bp1.setCity(binding.updateaddressSection.shipcityValue.getText().toString());
                    bp1.setState(shiptoStateCode);
                    bp1.setCountry(shiptoCountrycode);
                    bp1.setUState(shiptoState);
                    bp1.setUCountry(shiptoCountryName);
                    bp1.setAddressType("bo_ShipTo");
                    bp1.setUShptyp(ship_shiptype);
                    postbpAddresses.add(bp1);

                    /********************* Con Employee ************************/
                    List<ContactEmployeesModel> postcontactEmployees = new ArrayList<>();
                    ContactEmployeesModel postemp = new ContactEmployeesModel();
                    postemp.setName(contactpersonName);
//                        postemp.setFirstName(contactpersonName);
                    postemp.setCardCode(customerItem.getCardCode());
                    postemp.setMobilePhone(contactpersonmobile);
                    postemp.setE_Mail(contactpersonemail);
//                    if(!internalCode.isEmpty())
                    postemp.setInternalCode(contactpersonCode);

                     /*postemp.setCardCode("Code");
                     postemp.setAddress("Cinntra");
                     postemp.setPosition("1");

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
                    contactExtension.setId(customerItem.getId());
                    contactExtension.setUAccnt(customerItem.getUAccnt());
                    contactExtension.setUpdateDate(Globals.getTodaysDate());
                    contactExtension.setUpdateTime(Globals.getTCurrentTime());
                    contactExtension.setCreateTime(customerItem.getCreateTime());
                    contactExtension.setCreateDate(customerItem.getCreateDate());
                    contactExtension.setCardCode(cardCode);
                    contactExtension.setUCurbal(balance);
                    contactExtension.setPayTermsGrpCode(payment_term);
                    contactExtension.setCreditLimit(credit_limit);
                    contactExtension.setURating(customerItem.getURating());
                    contactExtension.setUInvno(invoice_n0);
                    contactExtension.setuLat(String.valueOf(Globals.currentlattitude));
                    contactExtension.setuLong(String.valueOf(Globals.currentlongitude));
                    contactExtension.setCommissionPercent(customerItem.getCommissionPercent());
                    contactExtension.setAttachmentEntry(customerItem.getAttachmentEntry());
                    contactExtension.setIntrestRatePercent(customerItem.getIntrestRatePercent());
                    contactExtension.setCurrency(customerItem.getCurrency());
                    contactExtension.setDiscountPercent(customerItem.getDiscountPercent());
                    contactExtension.setUParentacc(parenT_account);
                    contactExtension.setCardName(name);
                    contactExtension.setCardType("cCustomer"); //select value from spinner
                    contactExtension.setSalesPersonCode(String.valueOf(salesEmployeeCode));
                    contactExtension.setEmailAddress(comp_email);
                    contactExtension.setPhone1(comp_no);
                    contactExtension.setUType(TYPE);
                    contactExtension.setIndustry(industryCode);
                    contactExtension.setUAnlrvn(anlrvnue);
                    contactExtension.setWebsite(website);
                    contactExtension.setUBpgrp(customerItem.getUBpgrp());
                    contactExtension.setNotes(binding.remarksValue.getText().toString());
                    contactExtension.setUContownr(contactpersonName);
                    contactExtension.setBPAddresses(postbpAddresses);
                    contactExtension.setContactEmployees(postcontactEmployees);
                    if (Globals.checkInternet(getActivity()))
                        updateBP(contactExtension);
                }


                break;
            case R.id.checkboxManager:
                Globals.branchData.clear();
                Prefs.putString(Globals.SelectAddress, "UpdateAddress");
                dbClick.onClick(2);
                break;

        }
    }


    private void eventManager() {

/*
        binding.updateaddressSection.countryValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoCountrycode = MainActivity.countrylistFromLocal.get(position).getCode();
                billtoCountryName = MainActivity.countrylistFromLocal.get(position).getName();

                callStateApi(billtoCountrycode, "billto");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        binding.updateaddressSection.stateValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                billtoState = stateList.get(position).getName();
                billtoStateCode = stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        binding.contactOwnerValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                contactpersonName = contactPersonDataList.get(position).getFirstName();
                contactpersonCode = contactPersonDataList.get(position).getInternalCode();
                contactpersonmobile = contactPersonDataList.get(position).getMobilePhone();
                contactpersonemail = contactPersonDataList.get(position).getEMail();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (salesEmployeeItemList.size() > 0 && position > 0)
                    salesEmployeeCode = salesEmployeeItemList.get(position).getSalesEmployeeCode();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // salesEmployeeCode = 0;
            }
        });

        binding.industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    }

    private boolean validation
            (String fname, String cowner,
             String mobile, String email,
             String industryCode) {

        if (fname.isEmpty()) {
            Globals.showMessage(getContext(), "Enter First Name");
            return false;
        } else if (cowner.isEmpty()) {
            Globals.showMessage(getContext(), "Contact owner cannot be empty");
            return false;
        } else if (mobile.isEmpty()) {
            Globals.showMessage(getContext(), "Mobile number cannot be empty");
            return false;
        } else if (industryCode.trim().equalsIgnoreCase("-1")) {
            Globals.showMessage(getContext(), "Select Industry.");
            return false;
        } else if (email.isEmpty()) {
            Globals.showMessage(getContext(), "Email cannot be empty");
            return false;
        }
        return true;
    }

    /********************* APIs ***************************/
    List<IndustryItem> IndustryItemItemList = new ArrayList<>();

    private void callStagesApi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getIndustryList().observe(this, new Observer<List<IndustryItem>>()
//        {
//            @Override
//            public void onChanged(@Nullable List<IndustryItem> itemsList) {
//                if(itemsList == null || itemsList.size() == 0){
//                    Globals.setmessage( getActivity());
//                }else {
//                    IndustryItemItemList = itemsList;
//                    industry_spinner.setAdapter(new IndustrySpinnerAdapter(getActivity(),itemsList));
//                    if(industryCode!=null)
//                       industry_spinner.setSelection(Globals.getIndustryPo(IndustryItemItemList,industryCode)); ;
//
//                }
//            }
//        });

        if (MainActivity.industryListFromLocal == null || MainActivity.industryListFromLocal.size() == 0) {
            Globals.setmessage(act);
        } else {
            IndustryItemItemList = MainActivity.industryListFromLocal;
            binding.industrySpinner.setAdapter(new IndustrySpinnerAdapter(act, MainActivity.industryListFromLocal));
            industryCode = IndustryItemItemList.get(0).getIndustryCode();

        }

    }

    SalesEmployeeAdapter salesadapter;
    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(getActivity(), new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getActivity());
                } else if (itemsList != null || itemsList.size() > 0) {

                    salesEmployeeItemList = itemsList;
                    salesadapter = new SalesEmployeeAdapter(getContext(), itemsList);
                    binding.salesEmployeeSpinner.setAdapter(salesadapter);
                    if (!itemsList.isEmpty() && !salesPersonCode.isEmpty())
                        binding.salesEmployeeSpinner.setSelection(Globals.getSelectedSalesP(itemsList, salesPersonCode));

                    callStagesApi();
                }
            }
        });
    }

    /********************* Add Contact API *************************/

    private void updateBP(AddBusinessPartnerData in) {
        binding.loader.loader.setVisibility(View.VISIBLE);
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().updatecustomer(in);
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if (response.code() == 200) {

                    if (response.body().getStatus() == 200 ) {
                        Globals.branchData.clear();
                        Toasty.success(act, "Updated Successfully.", Toasty.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    } else {
                        Toasty.warning(act, response.body().getMessage(), Toasty.LENGTH_SHORT).show();

                    }
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


    List<PayMentTerm> getPaymenterm = new ArrayList<>();
    PaymentAdapter paymentAdapter;

    private void callPaymentApi() {
//        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//        model.getPaymentList().observe(requireActivity(), new Observer<List<PayMentTerm>>() {
//            @Override
//            public void onChanged(List<PayMentTerm> payMentTermList) {
//                if (payMentTermList == null || payMentTermList.size() == 0) {
//                    Globals.setmessage(getContext());
//                } else {
//                    getPaymenterm.clear();
//                    getPaymenterm = payMentTermList;
//                    paymentAdapter = new PaymentAdapter(requireActivity(), getPaymenterm);
//                    payment_term_value.setAdapter(paymentAdapter);
//
//                    if (!getPaymenterm.isEmpty() && payment_term != null)
//                        payment_term_value.setSelection(Globals.getPaymentTermPo(getPaymenterm, payment_term));
//                }
//
//            }
//        });

        if (MainActivity.paymentTermListFromLocal == null || MainActivity.paymentTermListFromLocal.size() == 0) {
            Globals.setmessage(getContext());
        } else {
            getPaymenterm.clear();
            getPaymenterm = MainActivity.paymentTermListFromLocal;
            paymentAdapter = new PaymentAdapter(requireActivity(), getPaymenterm);
            binding.paymentTermValue.setAdapter(paymentAdapter);

            if (!getPaymenterm.isEmpty() && payment_term != null)
                binding.paymentTermValue.setSelection(Globals.getPaymentTermPo(getPaymenterm, payment_term));
        }

    }

}
