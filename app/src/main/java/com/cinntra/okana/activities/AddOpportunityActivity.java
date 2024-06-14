package com.cinntra.okana.activities;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.BPTypeSpinnerAdapter;
import com.cinntra.okana.adapters.CategoryAdapter;
import com.cinntra.okana.adapters.LeadTypeAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.adapters.bpAdapters.ContactPersonAutoAdapter;
import com.cinntra.okana.databinding.AddOpportunityBinding;
import com.cinntra.okana.databinding.TaxesAlertBinding;
import com.cinntra.okana.globals.FileUtilsPdf;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.AttachmentResponseModel;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllResponse;
import com.cinntra.okana.model.BPTypeResponse;
import com.cinntra.okana.model.ContactPerson;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.IndustryResponse;
import com.cinntra.okana.model.ItemCategoryData;
import com.cinntra.okana.model.ItemCategoryResponse;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.LeadTypeResponse;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.OwnerItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.SalesOpportunitiesLines;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.newapimodel.AddOpportunityModel;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.LeadValue;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOpportunityActivity extends MainBaseActivity implements View.OnClickListener, DatabaseClick , FragmentRefresher {

    public static int PARTNERCODE = 100;
    public static int OWNERCODE = 1001;
    public static int LeadCode = 101;
    public int ITEMSVIEWCODE = 10000;

    Activity act;
    int salesEmployeeCode = 0;
    String salesEmployeename = "";
    String ContactPersonName = "";
    String ContactPersonCode = "";

    String stagesCode = "No";
    String TYPE = "";
    String LEAD_SOURCE = "";

    String DataOwnershipfield = "";
    String LeadID = "0";
    String CardName = "";
    AddOpportunityBinding binding;
    String  BPCardCode = "";
    private static final int RESULT_LOAD_IMAGE = 123;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    File file;
    String picturePath = "";
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddOpportunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loader.loader.setVisibility(View.GONE);

        act = AddOpportunityActivity.this;
        Globals.SelectedItems.clear();

        if (getIntent() != null) {
            BPCardCode  = getIntent().getStringExtra("BPCardCodeShortCut");
            // Use the passed data as needed
        }

        setDefaults();
        eventManager();

        if (Globals.checkInternet(this)) {
            callSourceApi();

//            callUTypeApi();

            try {
                if (BPCardCode != null){
                    callBPOneAPi(BPCardCode);
                }
                else {
                    callSalessApi();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            callDepartmentApi(); //todo comment by me
        }

      /*  if (Globals.checkInternet(this)) {
            callSalessApi();
            callSourceApi();

        }*/

        binding.itemFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.SelectedItems.size() == 0) {
//                    Globals.ItemType = "Paid";
                    openCategorydailog();
                   /* Intent intent = new Intent(AddOpportunityActivity.this, ItemsList.class);
                    intent.putExtra("CategoryID", 0);
                    startActivityForResult(intent, ITEMSVIEWCODE);*/
                } else {
                    Intent intent = new Intent(AddOpportunityActivity.this, SelectedItems.class);
                    intent.putExtra("FromWhere", "addQt");
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
            }
        });


        //todo click on attachment
        binding.addOppAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAttachmentDialog();

//                intentDispatcher();
            }
        });

    }


    private static final int RESULT_LOAD_PDF = 2;

    private void openAttachmentDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.picturedialog);
        dialog.getWindow().getAttributes().width = ActionBar.LayoutParams.FILL_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView cancel = dialog.findViewById(R.id.canceldialog);
        ImageView gallery = dialog.findViewById(R.id.gallerySelect);
        ImageView camera = dialog.findViewById(R.id.cameraSelect);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i, RESULT_LOAD_IMAGE);*/

                intentDispatcher();
                dialog.dismiss();


            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, RESULT_LOAD_PDF);

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    //todo select attachment ---
    private void intentDispatcher() {
        checkAndRequestPermissions();

        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takePictureIntent.setType("image/*");
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


    List<String> probability_list = Arrays.asList(Globals.probability_list);

    //todo event listeners---
    private void eventManager() {


        binding.acSalesEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0) {
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
                    salesEmployeename = salesEmployeeItemList.get(position).getSalesEmployeeName();

                    binding.acSalesEmployee.setText(salesEmployeeItemList.get(position).getSalesEmployeeName());
                } else {
                    salesEmployeeCode = 0;
                    salesEmployeename = "";

                    binding.acSalesEmployee.setText("");
                }
            }
        });


        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (utypelist.size() > 0)
                    TYPE = utypelist.get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.leadSourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sourceData.size() > 0) {
                    LEAD_SOURCE = sourceData.get(position).getName().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                LEAD_SOURCE = sourceData.get(0).getName().toString();
            }
        });


        //todo item click of contact person---

        binding.acContactPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ContactEmployeesList.size() > 0) {
                    ContactPersonName = ContactEmployeesList.get(position).getFirstName();
                    ContactPersonCode = ContactEmployeesList.get(position).getInternalCode();

                    binding.acContactPerson.setText(ContactEmployeesList.get(position).getFirstName());
                }
            }
        });


        //todo bind probability adapter---
        ArrayAdapter<String> probailityAdapter = new ArrayAdapter<>(AddOpportunityActivity.this, R.layout.drop_down_textview, probability_list);
        binding.acProbability.setAdapter(probailityAdapter);


        binding.acProbability.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.acProbability.setText(probability_list.get(position));

                ArrayAdapter<String> probailityAdapter = new ArrayAdapter<>(AddOpportunityActivity.this, R.layout.drop_down_textview, probability_list);
                binding.acProbability.setAdapter(probailityAdapter);
            }
        });


    }


    Dialog TaxListdialog;

    private void openCategorydailog() {
        RelativeLayout backPress;
        TextView head_title;
        RecyclerView recyclerview;
//        ProgressBar loader;
        SpinKitView loader;

        TaxListdialog = new Dialog(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View custom_dialog = layoutInflater.inflate(R.layout.taxes_alert, null);
        recyclerview = custom_dialog.findViewById(R.id.recyclerview);
        backPress = custom_dialog.findViewById(R.id.back_press);
        head_title = custom_dialog.findViewById(R.id.head_title);
        loader = custom_dialog.findViewById(R.id.loader);
        head_title.setText(getResources().getString(R.string.select_tax));
        TaxListdialog.setContentView(custom_dialog);
        TaxListdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TaxListdialog.show();

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxListdialog.dismiss();
            }
        });

        loader.setVisibility(View.GONE);

        Call<ItemCategoryResponse> call = NewApiClient.getInstance().getApiService().getAllCategory();
        call.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
                loader.setVisibility(View.GONE);
                if (response.body().getStatus() == 200) {
                    List<ItemCategoryData> itemsList = response.body().getData();

                    CategoryAdapter adapter = new CategoryAdapter(AddOpportunityActivity.this, itemsList, TaxListdialog);
                    recyclerview.setLayoutManager(new LinearLayoutManager(AddOpportunityActivity.this, RecyclerView.VERTICAL, false));
                    recyclerview.setAdapter(adapter);
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setDefaults() {
        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.add_opportunity));
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.bussinessPartner.setOnClickListener(this);
        binding.owener.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);
        binding.businessPartnerValue.setOnClickListener(this);
        binding.startDateValue.setOnClickListener(this);
        binding.opportunityOwnerValue.setOnClickListener(this);
        binding.startDate.setOnClickListener(this);
        binding.startcalender.setOnClickListener(this);
        binding.closeDate.setOnClickListener(this);
        binding.closeDateValue.setOnClickListener(this);
        binding.startcalender.setOnClickListener(this);
        binding.leadView.setOnClickListener(this);
        binding.leadValue.setOnClickListener(this);
        binding.startDateValue.setText(Globals.getTodaysDate());

    }


    boolean isPDF = true;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PARTNERCODE && resultCode == RESULT_OK) {

            /*BusinessPartnerData customerItem = (BusinessPartnerData) data.getSerializableExtra(Globals.CustomerItemData);
            setData(customerItem);*/

            BusinessPartnerAllResponse.Datum customerItem = (BusinessPartnerAllResponse.Datum) data.getSerializableExtra(Globals.CustomerItemData);
            callBPOneAPi(customerItem.getCardCode());

        } else if (requestCode == OWNERCODE && resultCode == RESULT_OK) {
            OwnerItem ownerItem = (OwnerItem) data.getSerializableExtra(Globals.OwnerItemData);
            binding.opportunityOwnerValue.setText(ownerItem.getFirstName() + " " + ownerItem.getMiddleName() + " " + ownerItem.getLastName());
            DataOwnershipfield = ownerItem.getEmployeeID();
        } else if (requestCode == LeadCode && resultCode == RESULT_OK) {
            LeadValue leadValue = data.getParcelableExtra(Globals.Lead_Data);
            LeadID = leadValue.getId().toString();
            binding.leadValue.setText(leadValue.getCompanyName());
            binding.opportunityNameValue.setText(leadValue.getCompanyName());

        }
        else if (resultCode == RESULT_OK && requestCode == ITEMSVIEWCODE) {
            binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");
//            binding.totalAfterItemDiscount = Double.parseDouble(String.valueOf(Globals.calculateTotalOfItem(Globals.SelectedItemsData)));
            String sum = String.valueOf(Globals.calculateTotalOfItem(Globals.SelectedItems));
            binding.totalBeforeDiscontValue.setText(sum);
//            getQuotationDocLin();
        }
        //todo for attachment selected---

        else if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                Uri selectedImage = data.getData();

                isPDF = false;

                if (!isPDF){
                    binding.tvPdf.setVisibility(View.GONE);
                }
                binding.ivQuotationImageSelected.setVisibility(View.VISIBLE);

                if (selectedImage != null){
                    binding.tvAttachments.setVisibility(View.GONE);
                }else {
                    binding.tvAttachments.setVisibility(View.VISIBLE);
                }

                binding.ivQuotationImageSelected.setImageURI(selectedImage);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.e("picturePath", picturePath);
                    file = new File(picturePath);
                    FileExtension = "image";
                    Log.e("FILE>>>>", "onActivityResult: " + file.getName());
                }


            }
        }

        /*** PDF Load**/
        else if (requestCode == RESULT_LOAD_PDF && resultCode == RESULT_OK) {
            if (data != null) {
                Uri pdfUri = data.getData();

                isPDF = true;
                if (isPDF){
                    binding.ivQuotationImageSelected.setVisibility(View.GONE);
                }
//                binding.ivQuotationImageSelected.setVisibility(View.VISIBLE);

                String filePath = FileUtilsPdf.getPathFromUri(this,pdfUri);
                if (filePath != null) {
                    // Now you have the file path
                    Log.e("File Path", filePath);

                    picturePath = filePath;
                    Log.e("picturePath", picturePath);
                    file = new File(picturePath);
                    Log.e("FILE>>>>", "onActivityResult: " + file.getName());

                    FileExtension = "pdf";

                    if (pdfUri != null){
                        binding.tvPdf.setVisibility(View.VISIBLE);
                        binding.tvPdf.setText(file.getName());
                        binding.tvAttachments.setVisibility(View.GONE);
                    }else {
                        binding.tvPdf.setVisibility(View.GONE);
                        binding.tvAttachments.setVisibility(View.VISIBLE);
                    }

                    binding.ivQuotationImageSelected.setImageURI(pdfUri);

                }


            }
        }


    }


    private String FileExtension = "";

    //todo quotation Attachment api calling---
    private void callOpportunityAttachmentApi(String qt_id) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //todo get model data in multipart body request..

        builder.addFormDataPart("Caption", "");
        builder.addFormDataPart("FileExtension", FileExtension);
        builder.addFormDataPart("id", qt_id.trim());
        builder.addFormDataPart("CreateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("CreateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("UpdateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("UpdateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("LinkType", "Opportunity");
        builder.addFormDataPart("LinkID", qt_id.trim());


        try {
            if (picturePath.isEmpty()) {
                builder.addFormDataPart("File", "");
            } else {
                builder.addFormDataPart("File", picturePath, RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        } catch (Exception e) {
            Log.d("TAG===>", "AddOpportunityApi: ");
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
                        Log.d("AttachmentNot200Status", "onResponse: OppAttachmentNot200Status");
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<AttachmentResponseModel> call, Throwable t) {
                Log.e("TAG_Attachment_Api", "onFailure: AttachmentAPi" );
                Toast.makeText(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                finish();
                break;
            case R.id.startcalender:
            case R.id.start_date_value:
            case R.id.startDate:
                startDate();
                break;
            case R.id.closeCalender:
            case R.id.close_date_value:
            case R.id.closeDate:
                closetDate();
                break;

            case R.id.bussinessPartner:
            case R.id.business_partner_value:

                selectBPartner();
                break;
            case R.id.lead_value:
            case R.id.lead_view:
                Prefs.putString(Globals.BussinessPageType, "AddOpportunityLead");
                Intent i = new Intent(AddOpportunityActivity.this, LeadsActivity.class);
                startActivityForResult(i, LeadCode);
                break;
            case R.id.owener:
            case R.id.opportunity_owner_value:

                Intent ii = new Intent(AddOpportunityActivity.this, OwnerList.class);
                startActivityForResult(ii, OWNERCODE);
                break;
            case R.id.submit_button:

                String cardValue = binding.businessPartnerValue.getText().toString().trim();
                String remark = binding.descriptionValue.getText().toString().trim();

                String inputString = binding.acProbability.getText().toString();

                int valueOfProbability = 0;
                if (!inputString.equalsIgnoreCase("")){
                    String stringWithoutPercent = inputString.replace("%", "");

                    valueOfProbability = Integer.parseInt(stringWithoutPercent);
                    System.out.println("Value at 0 index: " + valueOfProbability);
                }else {
//                    String stringWithoutPercent = inputString.replace("%", "");

                    valueOfProbability = Integer.parseInt("0");
                    System.out.println("Value at 0 index: " + valueOfProbability);
                }


                if (validation(cardValue, salesEmployeeCode,  TYPE, LEAD_SOURCE, ContactPerson, binding.closeDateValue)) {
                    jsonlist.clear();
                    SalesOpportunitiesLines dc = new SalesOpportunitiesLines();
                    dc.setSalesPerson(salesEmployeeCode);
                    dc.setDocumentType("bodt_MinusOne");
                    if (binding.potentialAmountValue.getText().toString().trim().isEmpty()){
                        dc.setMaxLocalTotal(0.0f);
                    }else {
                        dc.setMaxLocalTotal(Float.valueOf(binding.potentialAmountValue.getText().toString().trim()));
                    }
                    dc.setStageKey("2");
                    jsonlist.add(dc);

                    AddOpportunityModel obj = new AddOpportunityModel();
                    obj.setOpportunityName(binding.opportunityNameValue.getText().toString().trim());
                    obj.setClosingDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.closeDateValue.getText().toString().trim()));
                    obj.setPredictedClosingDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.closeDateValue.getText().toString().trim()));
                    obj.setUType(TYPE);
                    obj.setCustomerName(CardName);
                    obj.setUFav("N");
                    obj.setULsource(LEAD_SOURCE);
                    obj.setUProblty(String.valueOf(valueOfProbability));
                    // obj.setDataOwnershipfield(DataOwnershipfield);
                    obj.setCardCode(CardCode); //cardcode
                    obj.setSalesPerson(String.valueOf(salesEmployeeCode));
                    obj.setContactPerson(ContactPersonCode);
                    obj.setContactPersonName(ContactPersonName);
                    obj.setCurrentStageName("");
                    obj.setCurrentStageNumber("");
                    obj.setSequentialNo("");
                    obj.setMaxLocalTotal(binding.potentialAmountValue.getText().toString().trim());//Potential Ammount
                    obj.setRemarks(remark);
                    obj.setMaxSystemTotal("0.7576");
                    obj.setStatus("sos_Open");
                    obj.setReasonForClosing("None");
                    obj.setTotalAmountLocal("5.0");
                    obj.setTotalAmounSystem("0.075");
                    obj.setCurrentStageNo("2");
                    obj.setIndustry("None");
                    obj.setLinkedDocumentType("None");
                    obj.setStatusRemarks("None");
                    obj.setProjectCode("None");
                    obj.setClosingType("sos_Days");
                    obj.setOpportunityType("boOpSales");
                    obj.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                    obj.setUpdateTime(Globals.getTCurrentTime());
                    obj.setSource("None");
                    obj.setDataOwnershipfield(String.valueOf(salesEmployeeCode));
                    obj.setSalesPersonName(salesEmployeename);
                    obj.setDataOwnershipName(salesEmployeename);
                    obj.setStartDate(Globals.getTodaysDatervrsfrmt());
                    obj.setU_LEADID(LeadID);
                    obj.setU_LEADNM(binding.leadValue.getText().toString());

                    obj.setSalesOpportunitiesLines("");

                    obj.setOppItem("");

                    obj.setDocumentLines("");

                    //todo comment opp items , document and sales opportunity need to remove--
                   /* obj.setSalesOpportunitiesLines(jsonlist);

                    obj.setOppItem(Globals.SelectedItems);

                    obj.setDocumentLines(Globals.SelectedItems);*/


                    if (Globals.checkInternet(getApplicationContext()))
                        addQuotation(obj);
                }


                break;

        }
    }


    private void startDate() {
        Globals.selectDate(AddOpportunityActivity.this, binding.startDateValue);
    }

    private void closetDate() {
        Globals.enableAllCalenderDateSelect(AddOpportunityActivity.this, binding.closeDateValue);
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

    }

    private void selectBPartner() {
        Prefs.putString(Globals.BussinessPageType, "AddOpportunity");
        Intent i = new Intent(AddOpportunityActivity.this, BussinessPartners.class);
        startActivityForResult(i, PARTNERCODE);
    }

    ArrayList<SalesOpportunitiesLines> jsonlist = new ArrayList<>();
    //    private ContactPersonAdapter contactPersonAdapter;
    private ContactPersonAutoAdapter contactPersonAdapter;
    String CardCode = "";



    BusinessPartnerAllResponse.Datum customerItem = null;

    //todo calling bp one api here...
    private void callBPOneAPi(String BPCardCode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", BPCardCode);
        Call<BusinessPartnerAllResponse> call = NewApiClient.getInstance().getApiService().callBPOneAPi(jsonObject);
        call.enqueue(new Callback<BusinessPartnerAllResponse>() {
            @Override
            public void onResponse(Call<BusinessPartnerAllResponse> call, Response<BusinessPartnerAllResponse> response) {
                if (response.body().getStatus() == 200) {
                    customerItem = response.body().getData().get(0);

                    binding.businessPartnerValue.setText(customerItem.getCardName());

                    if (customerItem != null) {
                        setData(customerItem);
                    }

                    callSalessApi();

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BusinessPartnerAllResponse> call, Throwable t) {
                Log.e("ApiFailure==>", "onFailure: " + t.getMessage());
            }
        });

    }


    String ContactPerson = "";

    //todo set bp data..
    private void setData(BusinessPartnerAllResponse.Datum customerItem) {
        Prefs.putString(Globals.BusinessPartnerCardCode, "");

        CardName = customerItem.getCardName();
        CardCode = customerItem.getCardCode();

        Prefs.putString(Globals.BusinessPartnerCardCode, customerItem.getCardCode());

//        callBPBillToAddressApi(customerItem.getCardCode());
//        callBPShipToAddressApi(customerItem.getCardCode());

        binding.businessPartnerValue.setText(customerItem.getCardName());
        binding.leadValue.setText(customerItem.getU_LEADNM());

        callLeadOneApi(customerItem.U_LEADID);

        //todo bind adapter list----

        if (customerItem.getContactEmployees().size() > 0) {
            ContactPersonCode = customerItem.getContactEmployees().get(0).getInternalCode();
            ContactPersonName = customerItem.getContactEmployees().get(0).getFirstName();
            binding.acContactPerson.setText(customerItem.getContactEmployees().get(0).getFirstName());

            callContactEmployeeApi(CardCode);

        } else {
            binding.acContactPerson.setText("");
        }


       /* if (customerItem.getSalesPersonCode().size() > 0) {
            binding.salesEmployeeSpinner.setSelection(Globals.getSalesEmployeePos(salesEmployeeItemList, customerItem.getSalesPersonCode().get(0).getSalesEmployeeName()));
            salesEmployeeCode = Integer.valueOf(Globals.getSelectedSalesP(salesEmployeeItemList, customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode()));
        }
*/
        if (customerItem.getSalesPersonCode().size() > 0) {
            binding.acSalesEmployee.setText(customerItem.getSalesPersonCode().get(0).getSalesEmployeeName());
            salesEmployeeCode = Integer.parseInt(customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode());
            salesEmployeename = customerItem.getSalesPersonCode().get(0).getSalesEmployeeName();
        }

        binding.acSalesEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0) {
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
                    salesEmployeename = salesEmployeeItemList.get(position).getSalesEmployeeName();

                    binding.acSalesEmployee.setText(salesEmployeeItemList.get(position).getSalesEmployeeName());
                } else {
                    salesEmployeeCode = 0;
                    salesEmployeename = "";

                    binding.acSalesEmployee.setText("");
                }
            }
        });


        /*binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0) {
                    salesEmployeename = salesEmployeeItemList.get(position).getSalesEmployeeName();
                    salesEmployeeCode = Integer.valueOf(salesEmployeeItemList.get(position).getSalesEmployeeCode());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                salesEmployeename = "";
                salesEmployeeCode = 0;
            }
        });
*/

    }


    //todo call lead one api here---
    private void callLeadOneApi(String id) {
        LeadValue lv = new LeadValue();
        lv.setId(Integer.valueOf(id));
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().particularlead(lv);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {
                if (response != null) {
                    if (response.body().getStatus() == 200) {
                        LeadValue leadValue = response.body().getData().get(0);

                        if (!leadValue.getSource().isEmpty()){
                            LEAD_SOURCE = leadValue.getSource();
                            binding.leadSourceSpinner.setSelection(Globals.getLeadSourcePos(sourceData, leadValue.getSource()));
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {

            }
        });
    }


    ArrayList<ContactPersonData> ContactEmployeesList = new ArrayList<>();


    //todo calling contact person api here---
    private void callContactEmployeeApi(String id) {
        ContactPersonData contactPersonData = new ContactPersonData();
        contactPersonData.setCardCode(id);
        binding.loader.loader.setVisibility(View.VISIBLE);
        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().contactemplist(contactPersonData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {
                binding.loader.loader.setVisibility(View.GONE);
                if (response.body().getStatus() == 200) {
                    if (response.body().getData().size() > 0) {
                        ContactEmployeesList.clear();
                        ContactEmployeesList.addAll(response.body().getData());

                        contactPersonAdapter = new ContactPersonAutoAdapter(AddOpportunityActivity.this, R.layout.drop_down_textview, ContactEmployeesList);
                        binding.acContactPerson.setAdapter(contactPersonAdapter);
                    }

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();


    //todo calling sales employee api..
    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(this, new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getApplicationContext());
                } else {
                    salesEmployeeItemList = itemsList;
                    SalesEmployeeAutoAdapter adapter = new SalesEmployeeAutoAdapter(AddOpportunityActivity.this, R.layout.drop_down_textview, itemsList);
                    binding.acSalesEmployee.setAdapter(adapter);

                    callUTypeApi();

                }
            }
        });
    }


    List<UTypeData> utypelist = new ArrayList<>();

    private void callUTypeApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getOPpTypeList().observe(this, new Observer<List<UTypeData>>() {
            @Override
            public void onChanged(@Nullable List<UTypeData> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(act);
                } else {
                    utypelist = itemsList;
                    binding.typeSpinner.setAdapter(new BPTypeSpinnerAdapter(act, itemsList));
                    TYPE = utypelist.get(0).getId().toString();

                }
            }
        });
    }

    ArrayList<LeadTypeData> sourceData = new ArrayList<>();

    //tod calling source type api ---
    private void callSourceApi() {
        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {

                if (response.body().getStatus() == 200) {

                    sourceData.clear();
//                    sourceData.addAll(MainActivity.leadSourceListFromLocal);
                    sourceData.addAll(response.body().getData());
                    binding.leadSourceSpinner.setAdapter(new LeadTypeAdapter(AddOpportunityActivity.this, sourceData));
                    LEAD_SOURCE = sourceData.get(0).getName();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }

            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//                binding.loader.setVisibility(View.GONE);
                Toast.makeText(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addQuotation(AddOpportunityModel in) {
        binding.submitButton.setEnabled(false);
        binding.loader.loader.setVisibility(View.VISIBLE);
        Gson gson = new Gson();
        String jsonTut = gson.toJson(in);
        Log.e("data", jsonTut);
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().createopportunity(in);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                binding.submitButton.setEnabled(true);
                binding.loader.loader.setVisibility(View.GONE);
                if (response.code() == 200) {

                    if (response.body().getStatus() == 200) {
                        Toasty.success(AddOpportunityActivity.this, "Add Successfully", Toast.LENGTH_LONG).show();
                        Globals.SelectedItems.clear();
                        onBackPressed();

                        callOpportunityAttachmentApi(response.body().getData().get(0).getOpp_Id());
                    } else {
                        Toasty.warning(AddOpportunityActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    }
                } else {
                    binding.submitButton.setEnabled(true);
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(AddOpportunityActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                binding.submitButton.setEnabled(true);
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(AddOpportunityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation(String cardCode, int salesEmployeeCode, String opportunityType, String remark, String contactPerson, EditText closeDateValue) {
       /* if (Globals.SelectedItems.size() == 0){
            Globals.showMessage(act, "Select Atleast One Item");
            return false;
        }*/
        if (cardCode.isEmpty()) {
            Globals.showMessage(act, getString(R.string.select_bp));
            return false;
        } else if (ContactPersonCode.equalsIgnoreCase("-1")) {
            Globals.showMessage(act, getString(R.string.enter_cp));
            return false;
        }
        else if (opportunityType.isEmpty()) {
            Globals.showMessage(act, getString(R.string.opp_type));
            return false;
        }
        else if (binding.opportunityNameValue.getText().toString().trim().length() == 0) {
            binding.opportunityNameValue.requestFocus();
            binding.opportunityNameValue.setError(getString(R.string.enter_opp));
            Globals.showMessage(act, getString(R.string.enter_opp));
            return false;
        } else if (binding.closeDateValue.getText().toString().trim().length() == 0) {
            Globals.showMessage(act, "Enter closing date");
            return false;
        } else if (TYPE.equalsIgnoreCase("-None-")) {
            Globals.showMessage(act, getString(R.string.enter_tye));
            return false;
        } else if (LEAD_SOURCE.equalsIgnoreCase("-None-")) {
            Globals.showMessage(act, getString(R.string.enter_lead_source));
            return false;
        } else if (salesEmployeeCode == 0) {
            Globals.showMessage(act, getString(R.string.enter_sp));
            return false;
        } /*else if (remark.isEmpty()) {
            binding.descriptionValue.requestFocus();
            binding.descriptionValue.setError(getString(R.string.remark_error));
            Globals.showMessage(act, getString(R.string.remark_error));
            return false;
        }*/

        return true;
    }


    //todo adapter interface value getting..
    @Override
    public void onClick(int po) {
        Intent intent = new Intent(this, ItemsList.class);
        intent.putExtra("CategoryID", po);
        startActivityForResult(intent, ITEMSVIEWCODE);
    }


    @Override
    public void onRefresh() {
        Intent intent = new Intent(AddOpportunityActivity.this, ItemsList.class);
        intent.putExtra("CardCode", CardCode);
        startActivity(intent);
    }


}