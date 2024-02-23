package com.cinntra.okana.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ImageViewAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.databinding.AddExpenseBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.ExpenseResponse;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
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
import java.util.Arrays;
import java.util.List;


import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpense extends MainBaseActivity implements View.OnClickListener {


    int salesEmployeeCode = 0;

    private final int REQUEST_CODE_CHOOSE = 1000;

    AddExpenseBinding binding;

    List<String> expenseModeList = Arrays.asList(Globals.expenseModeList_gl);

    String expenseModeName = "";
    String travelName = "";
    String stayName = "";
    String daName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.headerLayout.headTitle.setText("Add Expense");
        binding.create.setText("Create");

        binding.loaderLayout.loader.setVisibility(View.GONE);

        eventmanager();
        if (Globals.checkInternet(this)) {
            callSalessApi();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_textview, expenseModeList);
        binding.acExpenseMode.setAdapter(arrayAdapter);


    }


    private void eventmanager() {
        binding.expenseFromDate.setOnClickListener(this);
        binding.expenseTodate.setOnClickListener(this);
        binding.attachment.setOnClickListener(this);
        binding.headerLayout.back.setOnClickListener(this);
        binding.create.setOnClickListener(this);


        binding.salesEmployeeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!salesEmployeeItemList.isEmpty()) {
                    binding.salesEmployeeSpinner.setText(salesEmployeeItemList.get(position).getSalesEmployeeName());
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
                } else {
                    binding.salesEmployeeSpinner.setText("");
                    salesEmployeeCode = 0;

                }

            }
        });


        //todo bind expense mode with item click..
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_textview, expenseModeList);
        binding.acExpenseMode.setAdapter(arrayAdapter);

        binding.acExpenseMode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedData = (String) parent.getItemAtPosition(position);

                if (!expenseModeList.isEmpty()) {
                    binding.acExpenseMode.setText(expenseModeList.get(position));
                    expenseModeName = expenseModeList.get(position);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddExpense.this, R.layout.drop_down_textview, expenseModeList);
                    binding.acExpenseMode.setAdapter(arrayAdapter);
                } else {
                    binding.acExpenseMode.setText("");
                    expenseModeName = "";
                }

            }
        });


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expense_from_date:
                Globals.enableAllCalenderDateSelect(this, binding.expenseFromDate);
                break;
            case R.id.expense_todate:
                Globals.enableAllCalenderDateSelect(this, binding.expenseTodate);
                break;
            case R.id.attachment:
                openimageuploader();
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.create:
                if (binding.tripname.length() != 0) {
                    if (Globals.checkInternet(this)) {

                        addExpenseApi();
                    }
                } else {
                    Toasty.warning(this, "Enter Trip Name").show();
                }

                break;
        }
    }

    //todo add expense api here...
    private void addExpenseApi() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        try {
            if (validation(binding.tripname, expenseModeName, salesEmployeeCode)) {


//                builder.addFormDataPart("tripId", "");
                builder.addFormDataPart("trip_name", binding.tripname.getText().toString());
                builder.addFormDataPart("remarks", binding.remarks.getText().toString());
                builder.addFormDataPart("type_of_expense", expenseModeName);
                builder.addFormDataPart("expense_from", Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.expenseFromDate.getText().toString()));
                builder.addFormDataPart("expense_to", Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.expenseTodate.getText().toString()));
                builder.addFormDataPart("createDate", Globals.getTodaysDatervrsfrmt());
                builder.addFormDataPart("createTime", Globals.getTCurrentTime());
                builder.addFormDataPart("createdBy", Prefs.getString(Globals.SalesEmployeeCode, ""));
                builder.addFormDataPart("employeeId", String.valueOf(salesEmployeeCode));

                if (!binding.amount.getText().toString().equalsIgnoreCase("")){
                    MultipartBody.Part amount = MultipartBody.Part.createFormData("totalAmount", binding.amount.getText().toString());

                    builder.addPart(amount);

                }else {
                    MultipartBody.Part amount = MultipartBody.Part.createFormData("totalAmount", "0");

                    builder.addPart(amount);
                }

                if (mSelected.size() > 0) {
                    for (int i = 0; i < mSelected.size(); i++) {
                        File file = new File(path.get(i));
                        builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                    }
                } else {
                    builder.addFormDataPart("Attach", "");
                }

                MultipartBody requestBody = builder.build();

                Gson gson = new Gson();
                String jsonTut = gson.toJson(requestBody);
                Log.e("data", jsonTut);

                binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().createexpense(requestBody);
                call.enqueue(new Callback<ExpenseResponse>() {
                    @Override
                    public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response.code() == 200) {
                            if (response.body().getMessage().equalsIgnoreCase("successful")) {

                                Toasty.success(AddExpense.this, "Add Successfully", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            } else {
                                Toasty.warning(AddExpense.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                            Gson gson = new GsonBuilder().create();
                            ExpenseResponse mError = new ExpenseResponse();
                            try {
                                String s = response.errorBody().string();
                                mError = gson.fromJson(s, ExpenseResponse.class);
                                Toast.makeText(AddExpense.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                //handle failure to read error
                            }
                        }
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Toasty.error(AddExpense.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    private void openimageuploader() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Matisse.from(AddExpense.this)
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .maxSelectable(5)
                                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false) // Default is `true`
                                    .forResult(REQUEST_CODE_CHOOSE);
                           /* Intent intent = new Intent();

                            // setting type to select to be image
                            intent.setType("image/*");

                            // allowing multiple image to be selected
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_CHOOSE);*/
                        } else {
                            Toast.makeText(AddExpense.this, "Please enable permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    List<Uri> mSelected = new ArrayList<>();
    List<String> path = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && null != data) {

            //mSelected.add(data.getData());
            mSelected = Matisse.obtainResult(data);
            for (int i = 0; i < mSelected.size(); i++) {
                path.add(FileUtils.getPath(AddExpense.this, mSelected.get(i)));
            }

            ImageViewAdapter adapter = new ImageViewAdapter(this, mSelected, "");
            binding.prevattachment.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
            binding.prevattachment.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            // Get the Image from data
        } else {
            // show this if no image is selected
            Toast.makeText(AddExpense.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }


    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi() {
        EmployeeValue employeeValue = new EmployeeValue();
        employeeValue.setSalesEmployeeCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
        Call<SaleEmployeeResponse> call = NewApiClient.getInstance().getApiService().getSalesEmplyeeList(employeeValue);
        call.enqueue(new Callback<SaleEmployeeResponse>() {
            @Override
            public void onResponse(Call<SaleEmployeeResponse> call, Response<SaleEmployeeResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getValue().size() > 0) {
                        salesEmployeeItemList.clear();
                        salesEmployeeItemList = response.body().getValue();
                        binding.salesEmployeeSpinner.setAdapter(new SalesEmployeeAutoAdapter(AddExpense.this, R.layout.drop_down_textview, salesEmployeeItemList));

                    } else {
                        Globals.setmessage(AddExpense.this);
                    }

                } else if (response.code() == 500) {
                    Toasty.warning(AddExpense.this, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 201) {
                    Toasty.warning(AddExpense.this, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toasty.warning(AddExpense.this, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Toasty.warning(AddExpense.this, response.message(), Toast.LENGTH_SHORT).show();
                } else {
                    Globals.setmessage(getApplicationContext());
                }


            }

            @Override
            public void onFailure(Call<SaleEmployeeResponse> call, Throwable t) {
                Toast.makeText(AddExpense.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean validation(EditText tripname, String expenseModeName, int salesEmployeeCode) {

        if (salesEmployeeCode == 0) {
            binding.salesEmployeeSpinner.requestFocus();
            binding.salesEmployeeSpinner.setError("Expense Employee Name is Required !");
            Globals.showMessage(this, "Expense Employee Name is Required!");
            return false;
        } else if (tripname.getText().toString().trim().equalsIgnoreCase("")) {
            binding.tripname.requestFocus();
            binding.tripname.setError("Trip Name is Required !");
            Globals.showMessage(this, "Trip Name is Required !");
            return false;
        } else if (expenseModeName.trim().equalsIgnoreCase("")) {
            binding.acExpenseMode.requestFocus();
            binding.acExpenseMode.setError("Type of Expense is Required !");
            Globals.showMessage(this, "Type of Expense is Required!");
            return false;
        }

        return true;
    }


}
