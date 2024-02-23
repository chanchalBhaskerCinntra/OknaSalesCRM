package com.cinntra.okana.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.adapters.bpAdapters.ContactPersonAutoAdapter;
import com.cinntra.okana.databinding.AddQuotationBinding;
import com.cinntra.okana.fragments.AddQuotationForm_One_Fragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.interfaces.SubmitQuotation;
import com.cinntra.okana.model.AddQuotation;
import com.cinntra.okana.model.AttachmentResponseModel;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllResponse;
import com.cinntra.okana.model.ContactPerson;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.newapimodel.OpportunityValue;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddQuotationAct extends MainBaseActivity implements View.OnClickListener, SubmitQuotation {
    public static int PARTNERCODE = 10000;
    public static int OPPCODE = 1001;
    public static int ITEMSCODE = 1000;

    public static int PARENT_PROFORMA_INVOICE = 1002;

    public static String CardValue;
    public static String CardName;
    public static String CardCode;
    public static String salePCode;
    public static AddQuotation addQuotationObj;
    AppCompatActivity act;
    String OPPID = "";
    public String salesEmployeeCode = "";
    public String salesEmployeeName = "";
    List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();
    NewOpportunityRespose oppdata;

    AddQuotationBinding binding;
    String QuotationType = "";
    String templeteName = "";

    String BPLID = "";
    String OppID = null ;

    NewOpportunityRespose oppItemLine = new NewOpportunityRespose();

    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    File file;
    String picturePath = "";
    Uri fileUri;
    String  BPCardCode = null;

    private ArrayList<ContactPersonData> ContactEmployeesList;
    String ContactPerson = "";
    String ContactPersonCode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = AddQuotationAct.this;
        binding = AddQuotationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addQuotationObj = new AddQuotation();

        if (getIntent() != null) {
            BPCardCode  = getIntent().getStringExtra("BPCardCodeShortCut");
        }

        //todo create quotation of particular opportunity if quotation not created of that opportunity.
        OppID = getIntent().getStringExtra("FROM_OPP_ID");

        if (OppID != null && !OppID.isEmpty()) {
            callOppOneApi("");
        } else {

        }

        if (Globals.checkInternet(this)){

            clickListeners();
            try {
                if (BPCardCode != null && !BPCardCode.isEmpty()) {
                    callBPOneAPi(BPCardCode, "fromBP");
                }else {
                    callSalessApi();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (!Prefs.getString(Globals.QuotationListing, "").equalsIgnoreCase("null")) {
            if (Globals.opportunityData.size() > 0) {
                oppdata = Globals.opportunityData.get(0);
                if (oppdata != null) {
                    setOppData(oppdata);
                }
            } else {
//                Toast.makeText(act, "Please Select Another Opportunity", Toast.LENGTH_SHORT).show();
            }

        }


        //todo click on attachment
        binding.quotationGeneralContent.quotAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDispatcher();
            }
        });


        binding.quotationGeneralContent.acContactPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ContactEmployeesList.size() > 0) {
                    ContactPersonCode = ContactEmployeesList.get(position).getInternalCode();
                    ContactPerson = ContactEmployeesList.get(position).getFirstName();
                    binding.quotationGeneralContent.acContactPerson.setText(ContactEmployeesList.get(position).getFirstName());
                } else {
                    ContactPersonCode = "";
                    ContactPerson = "";
                }

            }
        });


        binding.quotationGeneralContent.acSalesEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0) {
                    salesEmployeeCode = salesEmployeeItemList.get(position).getSalesEmployeeCode();
                    salesEmployeeName = salesEmployeeItemList.get(position).getSalesEmployeeName();

                    binding.quotationGeneralContent.acSalesEmployee.setText(salesEmployeeItemList.get(position).getSalesEmployeeName());
                } else {
                    salesEmployeeCode = "";
                    salesEmployeeName = "";

                    binding.quotationGeneralContent.acSalesEmployee.setText("");
                }
            }
        });




    }
    BusinessPartnerAllResponse.Datum customerItem = null;

    //todo calling bp one api here...
    private void callBPOneAPi(String bpCardCode, String fromBP) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", bpCardCode);
        Call<BusinessPartnerAllResponse> call = NewApiClient.getInstance().getApiService().callBPOneAPi(jsonObject);
        call.enqueue(new Callback<BusinessPartnerAllResponse>() {
            @Override
            public void onResponse(Call<BusinessPartnerAllResponse> call, Response<BusinessPartnerAllResponse> response) {
                if (response.body().getStatus() == 200) {

                    if (response.body().getData().size() > 0){
                        customerItem = response.body().getData().get(0);

                        binding.quotationGeneralContent.businessPartnerValue.setText(customerItem.getCardName());

                        OppID = customerItem.getId();
                        if (fromBP == "fromBP") {
                            callOppOneApi(fromBP);
                        }
                    }else {

                        binding.quotationGeneralContent.acSalesEmployee.setText("");
                        binding.quotationGeneralContent.acSalesEmployee.setEnabled(true);
                        binding.quotationGeneralContent.acSalesEmployee.setClickable(true);
                        salesEmployeeCode = "";
                        salesEmployeeName = "";

                        binding.quotationGeneralContent.businessPartnerValue.setText("");
                        binding.quotationGeneralContent.businessPartnerValue.setEnabled(true);
                        binding.quotationGeneralContent.businessPartnerValue.setClickable(true);
                    }


                    callSalessApi();


                }  else if (response.code() == 500){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 201){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }else if (response.code() == 401){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusinessPartnerAllResponse> call, Throwable t) {
                Log.e("ApiFailure==>", "onFailure: "+t.getMessage() );
            }
        });

    }


    private void callContactEmployeeApi(String cardCode) {
        ContactPersonData contactPersonData = new ContactPersonData();
        contactPersonData.setCardCode(cardCode);

        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().contactemplist(contactPersonData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {
                if(response.code()==200) {
                    if(response.body().getData().size()>0) {
                        ContactEmployeesList = new ArrayList<>();
                        ContactEmployeesList.clear();
                        ContactEmployeesList.addAll(response.body().getData());
                        ContactPersonAutoAdapter leadTypeAdapter = new ContactPersonAutoAdapter(AddQuotationAct.this, R.layout.drop_down_textview, ContactEmployeesList);
                        binding.quotationGeneralContent.acContactPerson.setAdapter(leadTypeAdapter);

                    }else{
                        Toasty.error(AddQuotationAct.this, response.body().getMessage());
                    }
                }
                else if (response.code() == 500){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 201){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }else if (response.code() == 401){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {

                Toast.makeText(AddQuotationAct.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    //todo on click listeners
    private void clickListeners() {
        binding.quotationGeneralContent.bpView.setVisibility(View.VISIBLE);
        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_quotation));
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.quotationGeneralContent.postingDate.setOnClickListener(this);
        binding.quotationGeneralContent.edCreatedDate.setOnClickListener(this);
        binding.quotationGeneralContent.postCal.setOnClickListener(this);
        binding.quotationGeneralContent.validDate.setOnClickListener(this);
        binding.quotationGeneralContent.validTillValue.setOnClickListener(this);
        binding.quotationGeneralContent.validCal.setOnClickListener(this);
        binding.quotationGeneralContent.documentDate.setOnClickListener(this);
        binding.quotationGeneralContent.documentDateValue.setOnClickListener(this);
        binding.quotationGeneralContent.docCal.setOnClickListener(this);
        binding.quotationGeneralContent.bussinessPartner.setOnClickListener(this);
        binding.quotationGeneralContent.businessPartnerValue.setOnClickListener(this);
        binding.quotationGeneralContent.opportunityNameValue.setOnClickListener(this);
        binding.quotationGeneralContent.oppView.setOnClickListener(this);

        binding.quotationGeneralContent.submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                } else {
                    Globals.opp = null;
                    onBackPressed();
                }
                break;
            case R.id.postingDate:
            case R.id.postCal:
            case R.id.edCreatedDate:
                Globals.enableAllCalenderDateSelect(act, binding.quotationGeneralContent.edCreatedDate);
                break;
            case R.id.validDate:
            case R.id.validCal:
            case R.id.valid_till_value:
                Globals.disablePastSelectDate(act, binding.quotationGeneralContent.validTillValue);
                break;
            case R.id.documentDate:
            case R.id.document_date_value:
            case R.id.docCal:
                Globals.disablePastSelectDate(act, binding.quotationGeneralContent.documentDateValue);
                break;
            case R.id.bussinessPartner:
            case R.id.business_partner_value:
                selectBPartner();
                break;
            case R.id.opportunity_name_value:
            case R.id.opp_view:
                selectOpportunity();
                break;
          
            case R.id.itemsView:
                if (Globals.SelectedItems.size() == 0) {
                    Intent intent = new Intent(AddQuotationAct.this, ItemsList.class);
                    intent.putExtra("CardCode", CardCode);
                    startActivityForResult(intent, ITEMSCODE);
                } else {
                    Intent intent = new Intent(AddQuotationAct.this, SelectedItems.class);
                    intent.putExtra("CardCode", CardCode);
                    intent.putExtra("FromWhere", "AddQt");
                    startActivityForResult(intent, ITEMSCODE);
                }
                break;
            case R.id.submit:
                String oppname = binding.quotationGeneralContent.opportunityNameValue.getText().toString().trim();
                String poDate = binding.quotationGeneralContent.edCreatedDate.getText().toString().trim();
                String vDate = binding.quotationGeneralContent.validTillValue.getText().toString().trim();
                String docDate = binding.quotationGeneralContent.documentDateValue.getText().toString().trim();
                String remark = binding.quotationGeneralContent.remarkValue.getText().toString().trim();
                String businessPartnerValue = binding.quotationGeneralContent.businessPartnerValue.getText().toString().trim();

                if (valiadtion( businessPartnerValue, salesEmployeeCode, poDate, ContactPersonCode, binding.quotationGeneralContent.edQuoteNo, binding.quotationGeneralContent.quoNamevalue)) {
                    addQuotationObj.setU_QUOTNM(binding.quotationGeneralContent.quoNamevalue.getText().toString().trim());
                    addQuotationObj.setQuoteNo(binding.quotationGeneralContent.edQuoteNo.getText().toString().trim());
                    addQuotationObj.setCardCode(CardCode.trim());
                    addQuotationObj.setCardName(CardName.trim());
                    addQuotationObj.setSalesPersonCode(salesEmployeeCode.trim());
                    addQuotationObj.setValidDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(vDate));
                    addQuotationObj.setPostingDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(docDate));
                    addQuotationObj.setDocumentDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(poDate));//docDate
                    addQuotationObj.setSalesPerson(ContactPersonCode);
//                    addQuotationObj.setContactPersonCode(salePCode);

                    addQuotationObj.setRemarks(remark);
                    addQuotationObj.setOpportunityName(oppname.trim());

                    if (OPPID.equalsIgnoreCase("")){
                        addQuotationObj.setU_OPPID("");
                    }else {
                        addQuotationObj.setU_OPPID(OPPID.trim());
                    }

                    addQuotationObj.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                    addQuotationObj.setUpdateTime(Globals.getTCurrentTime());
                    addQuotationObj.setCreateTime(Globals.getTCurrentTime());
                    addQuotationObj.setCreateDate(Globals.getTodaysDatervrsfrmt());


                    AddQuotationForm_One_Fragment addQuotationForm_one_fragment = new AddQuotationForm_One_Fragment(oppItemLine, CardCode);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.main_edit_qt_frame, addQuotationForm_one_fragment).addToBackStack(null);
                    fragmentTransaction.commit();

                }
//
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            Globals.opp = null;
            super.onBackPressed();
        }
    }

    private void selectBPartner() {
        Prefs.putString(Globals.BussinessPageType, "Quotation");
        Intent i = new Intent(act, BussinessPartners.class);
        startActivityForResult(i, PARTNERCODE);
    }

    private void selectOpportunity() {
        Prefs.putString(Globals.SelectOpportnity, "Quotation");
        Intent in = new Intent(act, Opportunities_Pipeline_Activity.class);
        startActivityForResult(in, OPPCODE);
    }

    private void selectParentProformaInvoice() {
        Prefs.putString(Globals.SelectQuotation, "Add_Quotation");
        Intent in = new Intent(act, QuotationActivity.class);
        startActivityForResult(in, PARENT_PROFORMA_INVOICE);
    }



    //todo select attachment ---
    private void intentDispatcher() {
        checkAndRequestPermissions();

        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(takePictureIntent, RESULT_LOAD_IMAGE);
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PARTNERCODE) {
            BusinessPartnerAllResponse.Datum customerItem = (BusinessPartnerAllResponse.Datum) data.getSerializableExtra(Globals.CustomerItemData);
            binding.quotationGeneralContent.acSalesEmployee.setEnabled(true);
            binding.quotationGeneralContent.acSalesEmployee.setClickable(true);

            binding.quotationGeneralContent.businessPartnerValue.setEnabled(true);
            binding.quotationGeneralContent.businessPartnerValue.setClickable(true);
            callBPOneAPi(customerItem.getCardCode(), "");
//            setData(customerItem);
        } else if (resultCode == RESULT_OK && requestCode == OPPCODE) {
            NewOpportunityRespose oppItem = Globals.opp;
            if (oppItem != null) {
                oppItemLine = oppItem;
                setOppData(oppItem);
//            OPPID = oppItem.getId();
            }
        } else if (resultCode == RESULT_OK && requestCode == PARENT_PROFORMA_INVOICE) {
            QuotationItem quotationItem = Globals.partent_proforma_invoice;
            if (quotationItem != null) {
//                quotationItem1 = quotationItem;
                setProformaInvoiceData(quotationItem);
            }
        }

        //todo for attachment selected---

        else if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                Uri selectedImage = data.getData();
                binding.quotationGeneralContent.ivQuotationImageSelected.setVisibility(View.VISIBLE);

                if (selectedImage != null){
                    binding.quotationGeneralContent.tvAttachments.setVisibility(View.GONE);
                }else {
                    binding.quotationGeneralContent.tvAttachments.setVisibility(View.VISIBLE);
                }


                binding.quotationGeneralContent.ivQuotationImageSelected.setImageURI(selectedImage);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.e("picturePath", picturePath);
                    file = new File(picturePath);
                    Log.e("FILE>>>>", "onActivityResult: " + file.getName());
                }
            }
        }

    }


    //todo set data acc to proforma invoice..
    private void setProformaInvoiceData(QuotationItem quotationItem) {
        binding.quotationGeneralContent.businessPartnerValue.setText(quotationItem.getCardName());
//        binding.quotationGeneralContent.acContactPerson.setText(quotationItem.getContactPersonCode().get(0).getFirstName());
        ContactPersonCode = String.valueOf(quotationItem.getContactPersonCode().get(0).getId());
    }


    //todoset opportunity based data..
    @SuppressLint("ResourceType")
    private void setOppData(NewOpportunityRespose oppdata) {
        binding.quotationGeneralContent.businessPartnerValue.setTextColor(Color.parseColor(getString(R.color.black)));
        binding.quotationGeneralContent.opportunityNameValue.setClickable(true);
        binding.quotationGeneralContent.opportunityNameValue.setEnabled(true);
        binding.quotationGeneralContent.opportunityNameValue.setTextColor(Color.parseColor(getString(R.color.black)));
        binding.quotationGeneralContent.oppView.setClickable(true);
        binding.quotationGeneralContent.oppView.setEnabled(true);
        OPPID = oppdata.getId();
        binding.quotationGeneralContent.opportunityNameValue.setText(oppdata.getOpportunityName());
        binding.quotationGeneralContent.businessPartnerValue.setText(oppdata.getCustomerName());
        binding.quotationGeneralContent.acSalesEmployee.setEnabled(false);
        binding.quotationGeneralContent.acSalesEmployee.setClickable(false);

        binding.quotationGeneralContent.businessPartnerValue.setEnabled(false);
        binding.quotationGeneralContent.businessPartnerValue.setClickable(false);

        callBPOneAPi(oppdata.getCardCode(), "");

        CardCode = oppdata.getCardCode();
        CardName = oppdata.getCustomerName();
        salePCode = oppdata.getContactPerson();
      /*  salesEmployeeCode = oppdata.getSalesPerson();
        salesEmployeeName = oppdata.getSalesPersonName();*/

    }

    //todo set bp selected data..
    private void setData(BusinessPartnerAllResponse.Datum customerItem) {

        Prefs.putString(Globals.BusinessPartnerCardCode, "");

        CardCode = customerItem.getCardCode();
        CardName = customerItem.getCardName();


        //todo bind adapter list----

        if (customerItem.getContactEmployees().size() > 0){
            ContactPersonCode = customerItem.getContactEmployees().get(0).getInternalCode();
            ContactPerson = customerItem.getContactEmployees().get(0).getFirstName();
            binding.quotationGeneralContent.acContactPerson.setText(customerItem.getContactEmployees().get(0).getFirstName());

            callContactEmployeeApi(CardCode);

        }else {
            binding.quotationGeneralContent.acContactPerson.setText("");
        }


        Prefs.putString(Globals.BusinessPartnerCardCode, customerItem.getCardCode());


        binding.quotationGeneralContent.businessPartnerValue.setText(customerItem.getCardName());


        //todo item select of contact person..
        binding.quotationGeneralContent.acContactPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ContactEmployeesList.size() > 0) {
                    ContactPersonCode = ContactEmployeesList.get(position).getInternalCode();
                    ContactPerson = ContactEmployeesList.get(position).getFirstName();
                    binding.quotationGeneralContent.acContactPerson.setText(ContactEmployeesList.get(position).getFirstName());
                } else {
                    ContactPersonCode = "";
                    ContactPerson = "";
                }

            }
        });



        if (customerItem.getSalesPersonCode().size() > 0){


            binding.quotationGeneralContent.acSalesEmployee.setTextColor(getResources().getColor(R.color.black));
            binding.quotationGeneralContent.acSalesEmployee.setText(customerItem.getSalesPersonCode().get(0).getSalesEmployeeName());

//            binding.quotationGeneralContent.salesemployeeSpinner.setSelection(Globals.getSalesEmployeePos(salesEmployeeItemList, customerItem.getSalesPersonCode().get(0).getSalesEmployeeName()));
//            salesEmployeeCode = String.valueOf(Globals.getSelectedSalesP(salesEmployeeItemList, customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode()));
            salesEmployeeCode = customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode();
            salesEmployeeName = customerItem.getSalesPersonCode().get(0).getSalesEmployeeName();

        }


    }


    //todo calling opportunity on eapi here..
    private void callOppOneApi(String from) {
        OpportunityValue opportunityValue = new OpportunityValue();
        opportunityValue.setId(Integer.valueOf(OppID));
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().getparticularopportunity(opportunityValue);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {

                        oppItemLine = response.body().getData().get(0);

                        if (from == "fromBP"){
                            OPPID = oppItemLine.getId();
                            binding.quotationGeneralContent.opportunityNameValue.setText(oppItemLine.getCustomerName());
                        }else {
                            setOppData(response.body().getData().get(0));
                        }

                    } else {
                        Toast.makeText(AddQuotationAct.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 500){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 201){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }else if (response.code() == 401){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(AddQuotationAct.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }


    //todo calling sales employee api here...
    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(this, new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getApplicationContext());
                } else {
                    salesEmployeeItemList = itemsList;

                    SalesEmployeeAutoAdapter adapter = new SalesEmployeeAutoAdapter(AddQuotationAct.this, R.layout.drop_down_textview, itemsList);
                    binding.quotationGeneralContent.acSalesEmployee.setAdapter(adapter);

                    if (customerItem != null) {
                        setData(customerItem);
                    }

                }

            }
        });
    }


    //todo quotation Attachment api calling---
    private void callQuotationAttachmentApi(String qt_id) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //todo get model data in multipart body request..

        builder.addFormDataPart("Caption", binding.quotationGeneralContent.edAttachmentCaption.getText().toString().trim());
        builder.addFormDataPart("id", qt_id.trim());
        builder.addFormDataPart("CreateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("CreateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("UpdateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("UpdateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("LinkType", "Quotation");
        builder.addFormDataPart("LinkID", qt_id.trim());

        try {
            if (picturePath.isEmpty()) {
                builder.addFormDataPart("File", "");
            } else {
                builder.addFormDataPart("File", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        } catch (Exception e) {
            Log.d("TAG===>", "AddQuotationApi: ");
        }

        MultipartBody requestBody = builder.build();

        Call<AttachmentResponseModel> call = NewApiClient.getInstance().getApiService().attachmentCreated(requestBody);
        call.enqueue(new Callback<AttachmentResponseModel>() {
            @Override
            public void onResponse(Call<AttachmentResponseModel> call, Response<AttachmentResponseModel> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        Log.d("AttachmentResponse =>", "onResponse: Successful");
                    }else {
                        Log.d("AttachmentNot200Status", "onResponse: QuotAttachmentNot200Status");
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(AddQuotationAct.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<AttachmentResponseModel> call, Throwable t) {
                Log.e("TAG_Attachment_Api", "onFailure: AttachmentAPi" );
                Toast.makeText(AddQuotationAct.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    //todo create quotation api ...
    private void addQuotation(AddQuotation in, ProgressBar loader) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(in);
        Log.e("data", jsonTut);
        loader.setVisibility(View.VISIBLE);
        Call<QuotationResponse> call = NewApiClient.getInstance().getApiService().addQuotation(in);
        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                loader.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        Globals.SelectedItems.clear();
                        Globals.opp = null;

                        //todo calling quotation attachment api here---
                        callQuotationAttachmentApi(response.body().getValue().get(0).getQt_Id());

                        Toasty.success(AddQuotationAct.this, "Add Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }else if (response.body().getStatus() == 202) {
                        Toasty.warning(AddQuotationAct.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else if (response.body().getStatus() == 500) {
                        Toasty.warning(AddQuotationAct.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else if (response.body().getStatus() == 401) {
                        Toasty.warning(AddQuotationAct.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toasty.warning(AddQuotationAct.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (response.code() == 500){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 201){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }else if (response.code() == 401){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuotationResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toasty.error(AddQuotationAct.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //todo
    @Override
    public void submitQuotaion(ProgressBar loader) {
//        addQuotationObj.setDocumentLines(Globals.SelectedItems);
        addQuotation(addQuotationObj, loader);

    }

    private boolean valiadtion(String businessPartnerValue, String salesEmployeeCode, String poDate, String contactPersonCode, EditText edQuoteNo, EditText quoNamevalue) {
        if (businessPartnerValue.isEmpty()) {
//            binding.quotationGeneralContent.businessPartnerValue.requestFocus();
//            binding.quotationGeneralContent.businessPartnerValue.setError("Select Business Partner");
            Globals.showMessage(act, "Select Business Partner");
            return false;
        }
        else if (edQuoteNo.getText().toString().isEmpty()) {
            binding.quotationGeneralContent.edQuoteNo.requestFocus();
            binding.quotationGeneralContent.edQuoteNo.setError("Enter Quote No");
            Globals.showMessage(act, "Enter Quote No");
            return false;
        }

        else if (quoNamevalue.getText().toString().isEmpty()) {
            binding.quotationGeneralContent.quoNamevalue.requestFocus();
            binding.quotationGeneralContent.quoNamevalue.setError("Enter Quotation Name");
            Globals.showMessage(act, "Enter Quotation Name");
            return false;
        }
        else if (salesEmployeeCode.isEmpty()) {
            Globals.showMessage(act, "Select Sales Representative is Required ! ");
            return false;
        }
        else if (poDate.isEmpty()) {
            Globals.showMessage(act, "Enter Created date");
            return false;
        } else if (contactPersonCode.isEmpty()) {
            Globals.showMessage(act, "Select Contact Person");
            return false;
        }

        return true;
    }



}