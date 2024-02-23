package com.cinntra.okana.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ImageViewAdapter;
import com.cinntra.okana.databinding.AddPaymentDetailsBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.PaymentRespnse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pixplicity.easyprefs.library.Prefs;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

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

public class AddPaymentDetails extends MainBaseActivity implements View.OnClickListener {


    private final int REQUEST_CODE_CHOOSE = 1000;


    String customername, TransactMod, customercode;
    ArrayList<BusinessPartnerData> AllitemsList = new ArrayList<>();
    AddPaymentDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddPaymentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);
        binding.headerBottomRounded.headTitle.setText("Add Payment Details");

        binding.loader.loader.setVisibility(View.GONE);
        eventmanager();
        if (Globals.checkInternet(this)) {
            callcustomerApi();

        }


    }


    private void callcustomerApi() {
//        CustomerViewModel model = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        model.getCustomersList(loader).observe(this, new Observer<List<BusinessPartnerData>>() {
//            @Override
//            public void onChanged(@Nullable List<BusinessPartnerData> itemsList) {
//
//                if(itemsList.size()>0) {
//                    AllitemsList.clear();
//                    AllitemsList.addAll(itemsList);
//                    sales_employee_spinner.setAdapter(new ArrayAdapter<String>(AddPaymentDetails.this, android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
//                    customername = addDatatoCategoryList(AllitemsList).get(0);
//                    customercode =AllitemsList.get(0).getCardCode();
//
//                }
//
//            }
//        });


        if (MainActivity.businessPartnerDataFromLocal.size() > 0) {
            AllitemsList.clear();
            AllitemsList.addAll(MainActivity.businessPartnerDataFromLocal);
            binding.salesEmployeeSpinner.setAdapter(new ArrayAdapter<String>(AddPaymentDetails.this, android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
            customername = addDatatoCategoryList(AllitemsList).get(0);
            customercode = AllitemsList.get(0).getCardCode();

        }


    }

    private ArrayList<String> addDatatoCategoryList(ArrayList<BusinessPartnerData> allitemsList) {
        ArrayList<String> bplist = new ArrayList<>();
        for (BusinessPartnerData bpdata : allitemsList) {
                   /* if(LeadID.isEmpty()){

                    }else{
                        if(bpdata.getU_LEADID().equalsIgnoreCase(LeadID)){
                            bplist.add(bpdata.getCardName());
                        }
                    }
*/
            bplist.add(bpdata.getCardName());
        }
        return bplist;
    }

    private void eventmanager() {
        binding.expenseFromDate.setOnClickListener(this);
        binding.create.setOnClickListener(this);
        binding.attachment.setOnClickListener(this);
        binding.headerBottomRounded.back.setOnClickListener(this);

        binding.receivedamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.amount.getText().toString().isEmpty()) {
                    Toasty.warning(AddPaymentDetails.this, "Enter Total Amount").show();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!binding.amount.getText().toString().isEmpty() && !charSequence.toString().isEmpty()) {
                    if (Integer.parseInt(charSequence.toString()) < Integer.parseInt(binding.amount.getText().toString())) {
                        if (!charSequence.toString().isEmpty()) {
                            binding.dueamount.setText(String.valueOf(Integer.parseInt(binding.amount.getText().toString()) - Integer.parseInt(binding.receivedamount.getText().toString())));
                        } else {
                            binding.dueamount.setText(String.valueOf(Integer.parseInt(binding.amount.getText().toString())));
                        }
                    } else {
                        Toasty.warning(AddPaymentDetails.this, "Received amount not be more then total amount").show();

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!binding.amount.getText().toString().isEmpty() && !editable.toString().isEmpty()) {
                    if (Integer.parseInt(editable.toString()) > Integer.parseInt(binding.amount.getText().toString())) {
                        Toasty.warning(AddPaymentDetails.this, "Received amount not be more then total amount").show();
                    }
                }
            }
        });


        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                customername = AllitemsList.get(i).getCardName();
                customercode = AllitemsList.get(i).getCardCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                customername = AllitemsList.get(0).getCardName();
                customercode = AllitemsList.get(0).getCardCode();
            }
        });


        binding.expenseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TransactMod = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TransactMod = adapterView.getItemAtPosition(0).toString();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expense_from_date:
                Globals.selectDate(this, binding.expenseFromDate);
                break;
            case R.id.attachment:
                openimageuploader();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.create:
                if (validation(binding.tripname, binding.expenseFromDate, binding.expenseTodate, binding.amount, binding.receivedamount, binding.dueamount)) {
                    if (Globals.checkInternet(this)) {
                        binding.loader.loader.setVisibility(View.VISIBLE);
                        createpaymentdetailsapi();
                    }
                }
                break;
        }
    }


    private void openimageuploader() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Matisse.from(AddPaymentDetails.this)
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .maxSelectable(5)
                                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false)
                                    .forResult(REQUEST_CODE_CHOOSE);// Default is `true`

                           /* Intent intent = new Intent();

                            // setting type to select to be image
                            intent.setType("image/*");

                            // allowing multiple image to be selected
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_CHOOSE);*/
                        } else {
                            Toast.makeText(AddPaymentDetails.this, "Please enable permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    List<Uri> mSelected = new ArrayList<>();
    List<Uri> mArrayList = new ArrayList<>();
    List<String> path = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && null != data) {

            //mSelected.add(data.getData());
            mArrayList = Matisse.obtainResult(data);
            mSelected.addAll(mArrayList);
            for (int i = 0; i < mSelected.size(); i++) {
                path.add(FileUtils.getPath(AddPaymentDetails.this, mSelected.get(i)));
            }

            ImageViewAdapter adapter = new ImageViewAdapter(this, mSelected, "");
            binding.prevattachment.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
            binding.prevattachment.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // Get the Image from data
        } else {
            // show this if no image is selected
            Toast.makeText(AddPaymentDetails.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }


    private void createpaymentdetailsapi() {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("Remarks", binding.remarks.getText().toString());
        builder.addFormDataPart("InvoiceNo", binding.tripname.getText().toString());
        builder.addFormDataPart("TransactId", binding.expenseTodate.getText().toString());
        builder.addFormDataPart("TotalAmt", binding.amount.getText().toString());
        builder.addFormDataPart("TransactMod", TransactMod);
        builder.addFormDataPart("DueAmount", binding.dueamount.getText().toString());
        builder.addFormDataPart("PaymentDate", binding.expenseFromDate.getText().toString());
        builder.addFormDataPart("ReceivedAmount", binding.receivedamount.getText().toString());
        builder.addFormDataPart("CardCode", customercode);
        builder.addFormDataPart("createDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("createTime", Globals.getTCurrentTime());
        builder.addFormDataPart("createdBy", Prefs.getString(Globals.SalesEmployeeCode, ""));
        if (mSelected.size() > 0) {
            for (int i = 0; i < mSelected.size(); i++) {
                File file = new File(path.get(i));
                builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        } else {
            builder.addFormDataPart("Attach", "", RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    ""));
        }
        MultipartBody requestBody = builder.build();
        Call<PaymentRespnse> call = NewApiClient.getInstance().getApiService().createpaymentdetails(requestBody);
        call.enqueue(new Callback<PaymentRespnse>() {
            @Override
            public void onResponse(Call<PaymentRespnse> call, Response<PaymentRespnse> response) {

                if (response.code() == 200) {
                    if (response.body().getMessage().equalsIgnoreCase("successful")) {

                        Toasty.success(AddPaymentDetails.this, "Add Successfully", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Toasty.warning(AddPaymentDetails.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    PaymentRespnse mError = new PaymentRespnse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, PaymentRespnse.class);
                        Toast.makeText(AddPaymentDetails.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
                binding.loader.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PaymentRespnse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(AddPaymentDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation(EditText invoice_id, EditText payment_date, EditText transactionid, EditText totalamount, EditText receivedamount, EditText dueamount) {

        if (invoice_id.length() == 0) {
            invoice_id.setError("Enter Invoice Id");
            return false;
        } else if (payment_date.length() == 0) {
            payment_date.setError("Enter Payment Date");
            return false;
        } else if (transactionid.length() == 0) {
            transactionid.setError("Enter Transaction ID");
            return false;
        } else if (totalamount.length() == 0) {
            totalamount.setError("Enter Total Amount");
            return false;
        } else if (receivedamount.length() == 0) {
            receivedamount.setError("Enter Received Amount");
            return false;
        } else if (Integer.parseInt(totalamount.getText().toString()) < Integer.parseInt(receivedamount.getText().toString())) {
            Toasty.warning(this, "Received amount will not be maximum then Total amount").show();
            return false;
        }
        return true;
    }
}
