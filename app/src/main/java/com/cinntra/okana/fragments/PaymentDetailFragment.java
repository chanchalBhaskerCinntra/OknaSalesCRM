package com.cinntra.okana.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.FileUtils;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.adapters.ImageViewAdapter;
import com.cinntra.okana.adapters.PaymentFetchImageAdapter;
import com.cinntra.okana.adapters.PreviousImageViewAdapter;
import com.cinntra.okana.databinding.AddPaymentDetailsBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.ExpenseResponse;
import com.cinntra.okana.model.PaymentDetailsModel;
import com.cinntra.okana.model.PaymentRespnse;
import com.cinntra.okana.model.PerformaInvoiceModel.QuotationOneAPiModel;
import com.cinntra.okana.model.TokenExpireModel;
import com.cinntra.okana.newapimodel.LeadDocumentResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pixplicity.easyprefs.library.Prefs;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailFragment extends Fragment implements View.OnClickListener, PreviousImageViewAdapter.DeleteItemClickListener {


    PaymentDetailsModel expenseDataModel;

    String customername, TransactMod, customercode;
    ArrayList<BusinessPartnerData> AllitemsList = new ArrayList<>();

    AddPaymentDetailsBinding binding;

    JSONObject jsonObject = new JSONObject();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            expenseDataModel = (PaymentDetailsModel) b.getSerializable(Globals.paymentDetailsData);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddPaymentDetailsBinding.inflate(inflater, container, false);

        if (Globals.checkInternet(getContext())) {
            callcustomerApi();

        }
        binding.loader.loader.setVisibility(View.VISIBLE);
        binding.headerBottomRounded.back.setOnClickListener(this);
        binding.expenseFromDate.setOnClickListener(this);
        binding.create.setOnClickListener(this);
        binding.headerBottomRounded.headTitle.setText("Edit Payment Details");
        binding.attachment.setVisibility(View.GONE);
        binding.create.setText("Update");
        eventmanger();
        callPaymentOneApi();
//        setData();

        //todo click on attachment
        binding.attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intentDispatcher();
                openimageuploader();
            }
        });


        return binding.getRoot();
    }

    private void eventmanger() {
        binding.expenseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TransactMod = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TransactMod = adapterView.getSelectedItem().toString();

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
    }


    private void callPaymentOneApi() {
        binding.loader.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", expenseDataModel.getId());

        Call<PaymentRespnse> call = NewApiClient.getInstance().getApiService().onePaymentApi(jsonObject);
        call.enqueue(new Callback<PaymentRespnse>() {
            @Override
            public void onResponse(Call<PaymentRespnse> call, Response<PaymentRespnse> response) {
                try {
                    if (response.isSuccessful()) {
                        binding.loader.loader.setVisibility(View.GONE);
                        if (response.code() == 200) {
                            if (response.body().getStatus() == 200) {

                                expenseDataModel = response.body().getData().get(0);

                                setData(response.body().getData().get(0));
                            }

                        } else {
                            binding.loader.loader.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 401) {
                        binding.loader.loader.setVisibility(View.GONE);
                        if (response.body().getStatus() == 301) {
                            Gson gson = new GsonBuilder().create();
                            TokenExpireModel mError = new TokenExpireModel();
                            try {
                                String s = response.errorBody().string();
                                mError = gson.fromJson(s, TokenExpireModel.class);
                                Toast.makeText(getActivity(), mError.getDetail(), Toast.LENGTH_LONG).show();
//                                Globals.logoutScreen(getActivity());
                            } catch (IOException e) {
                                // handle failure to read error
                            }

                        }
                    } else {
                        binding.loader.loader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Gson gson = new GsonBuilder().create();
                        QuotationOneAPiModel mError = new QuotationOneAPiModel();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationOneAPiModel.class);
                            Toast.makeText(getActivity(), mError.getError().getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    binding.loader.loader.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PaymentRespnse> call, Throwable t) {
                Log.e("TAG_APi_failure", "onFailure: " + t.getMessage());
                binding.loader.loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(PaymentDetailsModel expenseDataModel) {

        binding.tripname.setText(expenseDataModel.getInvoiceNo());
        binding.expenseTodate.setText(expenseDataModel.getTransactId());
        binding.amount.setText(expenseDataModel.getTotalAmt());
        binding.receivedamount.setText(expenseDataModel.getReceivedAmount());
        binding.dueamount.setText(expenseDataModel.getDueAmount());
        binding.remarks.setText(expenseDataModel.getRemarks());
        binding.expenseFromDate.setText(expenseDataModel.getPaymentDate());
        ArrayAdapter<String> transactionmodadpter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.transaction_mode));
        binding.expenseTypeSpinner.setAdapter(transactionmodadpter);
        binding.expenseTypeSpinner.setSelection(transactionmodadpter.getPosition(expenseDataModel.getTransactMod()));


        if (expenseDataModel.getAttach().size() > 0) {
            bindAdapter(expenseDataModel);
        } else {
            bindAdapter(expenseDataModel);
        }

        binding.loader.loader.setVisibility(View.GONE);
    }

    ArrayList<String> tempList = new ArrayList<>();

    private void bindAdapter(PaymentDetailsModel expenseDataModel) {

        for (int i = 0; i < expenseDataModel.getAttach().size(); i++) {
            String fileUriString = expenseDataModel.getAttach().get(i).getFile();
            Uri uri = Uri.parse(fileUriString);
            mSelected.add(uri);
        }

        Log.e("mSelected Data == >", "bindAdapter: " + mSelected.size());

        PreviousImageViewAdapter adapter = new PreviousImageViewAdapter(getContext(), expenseDataModel.getAttach(), "");
        binding.prevattachment.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        binding.prevattachment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteItemClick(int attachId, Dialog dialog) {
        callAttachmentDeleteApi(attachId, dialog);
    }


    //todo call delete attachment api here---
    private void callAttachmentDeleteApi(int attachId, Dialog dialog) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", attachId);
        jsonObject.addProperty("image_id", expenseDataModel.getId());

        Call<LeadDocumentResponse> call = NewApiClient.getInstance().getApiService().deletePaymentAttachment(jsonObject);
        call.enqueue(new Callback<LeadDocumentResponse>() {
            @Override
            public void onResponse(Call<LeadDocumentResponse> call, Response<LeadDocumentResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {

                        callPaymentOneApi();

                        dialog.dismiss();
                        Log.d("DeleteAttachResponse =>", "onResponse: Successful");
                    } else {
                        Log.d("DeleteAttachNot200St", "onResponse: QuotAttachmentNot200Status");
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getActivity(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<LeadDocumentResponse> call, Throwable t) {
                Log.e("TAG_Attachment_Api", "onFailure: AttachmentAPi");
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;

    //todo select attachment ---
    private void intentDispatcher() {
        checkAndRequestPermissions();

        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(takePictureIntent, RESULT_LOAD_IMAGE);

    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int write = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

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
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[0]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    private void openimageuploader() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Matisse.from(PaymentDetailFragment.this)
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .maxSelectable(5)
                                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false)
                                    .forResult(RESULT_LOAD_IMAGE);// Default is `true`

                        } else {
                            Toast.makeText(getActivity(), "Please enable permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    File file;
    String picturePath = "";

    List<Uri> mSelected = new ArrayList<>();
    List<Uri> uriArrayList = new ArrayList<>();
    List<Uri> mArrayList = new ArrayList<>();
    List<String> path = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            //mSelected.add(data.getData());
            mArrayList.clear();
            path.clear();
            mArrayList = Matisse.obtainResult(data);
            uriArrayList.addAll(mArrayList);
            mSelected.addAll(uriArrayList);
            for (int i = 0; i < mSelected.size(); i++) {
                path.add(FileUtils.getPath(getActivity(), mSelected.get(i)));
            }

           /* ImageViewAdapter adapter = new ImageViewAdapter(getActivity(), uriArrayList, "");
            binding.addNewAttachment.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
            binding.addNewAttachment.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // Get the Image from data*/
        } else {
            // show this if no image is selected
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

        //todo for attachment selected---
        /*if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                Uri selectedImage = data.getData();

//                binding.ivQuotationImageSelected.setImageURI(selectedImage);

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.e("picturePath", picturePath);
                    file = new File(picturePath);
                    Log.e("FILE>>>>", "onActivityResult: " + file.getName());

                    binding.loader.loader.setVisibility(View.VISIBLE);

                    ImageViewAdapter adapter = new ImageViewAdapter(getActivity(), mSelected);
                    binding.prevattachment.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
                    binding.prevattachment.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

//                    callPaymentAttachmentApi(String.valueOf(expenseDataModel.getId()));
                }
            }
        }*/

    }


    private int getcustomerposition(String customercode) {
        for (BusinessPartnerData bd : AllitemsList) {
            if (bd.getCardCode().equalsIgnoreCase(customercode)) {
                return AllitemsList.indexOf(bd);
            }
        }
        return 0;
    }


    private void callcustomerApi() {
//        CustomerViewModel model = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        model.getCustomersList(loader).observe(getActivity(), new Observer<List<BusinessPartnerData>>() {
//            @Override
//            public void onChanged(@Nullable List<BusinessPartnerData> itemsList) {
//
//                if(itemsList.size()>0) {
//                    AllitemsList.clear();
//                    AllitemsList.addAll(itemsList);
//                    sales_employee_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
//                    customercode = expenseDataModel.getCardCode();
//                    sales_employee_spinner.setSelection(getcustomerposition(customercode));
//
//
//                }
//
//            }
//        });

        if (MainActivity.businessPartnerDataFromLocal.size() > 0) {
            AllitemsList.clear();
            AllitemsList.addAll(MainActivity.businessPartnerDataFromLocal);
            binding.salesEmployeeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, addDatatoCategoryList(AllitemsList)));
            customercode = expenseDataModel.getCardCode();
            binding.salesEmployeeSpinner.setSelection(getcustomerposition(customercode));

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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.expense_from_date:
                Globals.selectDate(getContext(), binding.expenseFromDate);
                break;

            case R.id.back:
                getActivity().onBackPressed();
                break;

            case R.id.create:
                if (validation(binding.tripname, binding.expenseFromDate, binding.expenseTodate, binding.amount, binding.receivedamount, binding.dueamount)) {
                    if (Globals.checkInternet(getContext())) {
                        binding.loader.loader.setVisibility(View.VISIBLE);
                        updatepaymentdetailsapi();
                    }
                }
                break;

        }

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
            Toasty.warning(getContext(), "Received amount will not be maximum then Total amount").show();
            return false;
        }
        return true;
    }

    private void updatepaymentdetailsapi() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);


        builder.addFormDataPart("id", expenseDataModel.getId().toString());
        builder.addFormDataPart("Remarks", binding.remarks.getText().toString());
        builder.addFormDataPart("InvoiceNo", binding.tripname.getText().toString());
        builder.addFormDataPart("TransactId", binding.expenseTodate.getText().toString());
        builder.addFormDataPart("TotalAmt", binding.amount.getText().toString());
        builder.addFormDataPart("TransactMod", TransactMod);
        builder.addFormDataPart("DueAmount", binding.dueamount.getText().toString());
        builder.addFormDataPart("PaymentDate", binding.expenseFromDate.getText().toString());
        builder.addFormDataPart("ReceivedAmount", binding.receivedamount.getText().toString());
        builder.addFormDataPart("CardCode", customercode);
        builder.addFormDataPart("updateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("updateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("updatedBy", Prefs.getString(Globals.SalesEmployeeCode, ""));
        builder.addFormDataPart("Attach", "", RequestBody.create(MediaType.parse("multipart/form-data"), expenseDataModel.getAttach().toString()));


     /*   if (mSelected.size() > 0) {
            for (int i = 0; i < mSelected.size(); i++) {
                File file = new File(path.get(i));
                builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        } else {
            builder.addFormDataPart("Attach", "", RequestBody.create(MediaType.parse("multipart/form-data"), expenseDataModel.getAttach().toString()));
        }*/


        JsonObject jsonObject1 = new JsonObject();

        jsonObject1.addProperty("id", expenseDataModel.getId().toString());
        jsonObject1.addProperty("Remarks", binding.remarks.getText().toString());
        jsonObject1.addProperty("InvoiceNo", binding.tripname.getText().toString());
        jsonObject1.addProperty("TransactId", binding.expenseTodate.getText().toString());
        jsonObject1.addProperty("TotalAmt", binding.amount.getText().toString());
        jsonObject1.addProperty("TransactMod", TransactMod);
        jsonObject1.addProperty("DueAmount", binding.dueamount.getText().toString());
        jsonObject1.addProperty("PaymentDate", binding.expenseFromDate.getText().toString());
        jsonObject1.addProperty("ReceivedAmount", binding.receivedamount.getText().toString());
        jsonObject1.addProperty("CardCode", customercode);
        jsonObject1.addProperty("updateDate", Globals.getTodaysDatervrsfrmt());
        jsonObject1.addProperty("updateTime", Globals.getTCurrentTime());
        jsonObject1.addProperty("updatedBy", Prefs.getString(Globals.SalesEmployeeCode, ""));

        Gson gson = new Gson();
        String jsonTut = gson.toJson(jsonObject1);
        Log.e("data", jsonTut);


        MultipartBody requestBody = builder.build();
        Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().updatepayment(requestBody);
        call.enqueue(new Callback<ExpenseResponse>() {
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                if (response.body().getStatus() == 200) {

                    if (response.body().getMessage().equalsIgnoreCase("successful")) {
                        Toasty.success(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();

                        getActivity().onBackPressed();
                    } else {
                        Toasty.warning(requireContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show();
                }
                binding.loader.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                Toasty.error(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
