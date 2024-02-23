package com.cinntra.okana.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

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

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ImageViewAdapter;
import com.cinntra.okana.adapters.PreviousImageViewAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.databinding.ActivityEditExpenseBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.TokenExpireModel;
import com.cinntra.okana.model.expenseModels.ExpenseOneDataResponseModel;
import com.cinntra.okana.model.expenseModels.ExpenseResponse;
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

public class EditExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE_CHOOSE = 1000;
    ActivityEditExpenseBinding binding;
    List<String> expenseModeList = Arrays.asList(Globals.expenseModeList_gl);

    String expenseModeName = "";

    int id = 0;
    int salesEmployeeCode = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id = getIntent().getIntExtra("id", 0);
        binding.headerLayout.headTitle.setText("Edit Expense");

        eventmanager();
        if (Globals.checkInternet(this)) {
            if (id != 0) {
                callOneDetailApi();

                callSalessApi();
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.drop_down_textview, expenseModeList);
        binding.acExpenseMode.setAdapter(arrayAdapter);
    }


    private void eventmanager() {
        binding.expenseFromDate.setOnClickListener(this);
        binding.expenseTodate.setOnClickListener(this);
        binding.attachment.setOnClickListener(this);
        binding.headerLayout.back.setOnClickListener(this);
        binding.update.setOnClickListener(this);

        //todo bind expense mode with item click..
        binding.salesEmployeeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!salesEmployeeItemList.isEmpty()){
                    binding.salesEmployeeSpinner.setText(salesEmployeeItemList.get(position).getSalesEmployeeName());
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
                }else {
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

                if (!expenseModeList.isEmpty()){
                    binding.acExpenseMode.setText(expenseModeList.get(position));
                    expenseModeName = expenseModeList.get(position);
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditExpenseActivity.this, R.layout.drop_down_textview, expenseModeList);
                    binding.acExpenseMode.setAdapter(arrayAdapter);
                }else {
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
            case R.id.update:
                if (binding.tripname.length() != 0) {
                    if (Globals.checkInternet(this)) {
                        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                        updateExpenseApi();
                    }
                } else {
                    Toasty.warning(this, "Enter Trip Name").show();
                }

                break;
        }
    }


    //todo call expense one api here..
    ExpenseOneDataResponseModel.Data oneModelData = new ExpenseOneDataResponseModel.Data();

    private void callOneDetailApi() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        Call<ExpenseOneDataResponseModel> call = NewApiClient.getInstance().getApiService().getExpenseOneData(jsonObject);
        call.enqueue(new Callback<ExpenseOneDataResponseModel>() {
            @Override
            public void onResponse(Call<ExpenseOneDataResponseModel> call, Response<ExpenseOneDataResponseModel> response) {
                try {
                    if (response.code() == 200) {
                        if (response.body().getStatus() == 200) {
                            if (response.body().getData().size() > 0 && response.body().getData() != null) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                oneModelData = response.body().getData().get(0);
                                setDefaultData(response.body().getData().get(0));
                            }

                        } else {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            Toast.makeText(EditExpenseActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 401) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response.body().getStatus() == 301) {
                            Gson gson = new GsonBuilder().create();
                            TokenExpireModel mError = new TokenExpireModel();
                            try {
                                String s = response.errorBody().string();
                                mError = gson.fromJson(s, TokenExpireModel.class);
                                Toast.makeText(EditExpenseActivity.this, mError.getDetail(), Toast.LENGTH_LONG).show();
//                                Globals.logoutScreen(EditExpenseActivity.this);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } catch (Exception e) {
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ExpenseOneDataResponseModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(EditExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefaultData(ExpenseOneDataResponseModel.Data data) {
        binding.tripname.setText(data.getTrip_name());
        binding.amount.setText(data.getTotalAmount());


        PreviousImageViewAdapter adapter = new PreviousImageViewAdapter(this, data.getAttach(), "");
        binding.prevattachment.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        binding.prevattachment.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        if (!data.getType_of_expense().equalsIgnoreCase("")) {
            binding.acExpenseMode.setText(data.getType_of_expense());
            expenseModeName = data.getType_of_expense();

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(EditExpenseActivity.this, R.layout.drop_down_textview, expenseModeList);
            binding.acExpenseMode.setAdapter(arrayAdapter);
        }
        if (!data.getExpense_from().equalsIgnoreCase("")) {
            binding.expenseFromDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(data.getExpense_from()));
        }

        if (!data.getExpense_to().equalsIgnoreCase("")) {
            binding.expenseTodate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(data.getExpense_to()));
        }

        if (data.getEmployeeId().size() > 0){
            binding.salesEmployeeSpinner.setText(data.getEmployeeId().get(0).getFirstName());
            salesEmployeeCode = data.getEmployeeId().get(0).getId();
        }

        if (!data.getRemarks().isEmpty()){
            binding.remarks.setText(data.getRemarks());
        }else {
            binding.remarks.setText("");
        }


    }


    //todo update expense api herer...
    private void updateExpenseApi() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        try {
            if (validation(binding.tripname, expenseModeName, salesEmployeeCode)) {

                builder.addFormDataPart("id", String.valueOf(oneModelData.getId()));
                builder.addFormDataPart("trip_name", binding.tripname.getText().toString());
                builder.addFormDataPart("remarks", binding.remarks.getText().toString());
                builder.addFormDataPart("expense_from", Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.expenseFromDate.getText().toString()));
                builder.addFormDataPart("expense_to", Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.expenseTodate.getText().toString()));
                builder.addFormDataPart("type_of_expense", expenseModeName.toString());
                builder.addFormDataPart("updateDate", Globals.getTodaysDatervrsfrmt());
                builder.addFormDataPart("updateTime", Globals.getTCurrentTime());
                builder.addFormDataPart("updatedBy", Prefs.getString(Globals.SalesEmployeeCode, ""));
                builder.addFormDataPart("employeeId", String.valueOf(salesEmployeeCode));

                if (!binding.amount.getText().toString().equalsIgnoreCase("")){
                    MultipartBody.Part amount = MultipartBody.Part.createFormData("totalAmount", binding.amount.getText().toString());

                    builder.addPart(amount);

                }else {
                    MultipartBody.Part amount = MultipartBody.Part.createFormData("totalAmount", "0");

                    builder.addPart(amount);
                }

                if (oneModelData.getAttach().size() > 0 && mSelected.size() == 0) {
                    builder.addFormDataPart("Attach", "", RequestBody.create(MediaType.parse("multipart/form-data"), oneModelData.getAttach().toString()));
                } else {
                    if (mSelected.size() > 0) {
                        for (int i = 0; i < mSelected.size(); i++) {
                            File file = new File(path.get(i));
                            builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                        }
                    } else {
                        builder.addFormDataPart("Attach", "");
//                        builder.addFormDataPart("Attach", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));

                    }
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", String.valueOf(oneModelData.getId()));
                jsonObject.addProperty("trip_name", binding.tripname.getText().toString());
                jsonObject.addProperty("remarks", binding.remarks.getText().toString());
                jsonObject.addProperty("expense_from", Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.expenseFromDate.getText().toString()));
                jsonObject.addProperty("expense_to", Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.expenseTodate.getText().toString()));
                jsonObject.addProperty("type_of_expense", expenseModeName.toString());
                jsonObject.addProperty("totalAmount", binding.amount.getText().toString());
                jsonObject.addProperty("updateDate", Globals.getTodaysDatervrsfrmt());
                jsonObject.addProperty("updateTime", Globals.getTCurrentTime());
                jsonObject.addProperty("updatedBy", Prefs.getString(Globals.SalesEmployeeCode, ""));
                jsonObject.addProperty("employeeId", String.valueOf(salesEmployeeCode));



                MultipartBody requestBody = builder.build();

                Gson gson = new Gson();
                String jsonTut = gson.toJson(jsonObject);
                Log.e("data ===> ", jsonTut);


                Call<ExpenseResponse> call = NewApiClient.getInstance().getApiService().updateexpense(requestBody);
                call.enqueue(new Callback<ExpenseResponse>() {
                    @Override
                    public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {

                        if (response.code() == 200) {
                            if (response.body().getStatus() == 200) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                if (response.body().getMessage().equalsIgnoreCase("successful")) {
                                    Toasty.success(EditExpenseActivity.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else {
                                    Toasty.warning(EditExpenseActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                Toast.makeText(EditExpenseActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else if (response.code() == 401) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            if (response.code() == 301) {
                                Toast.makeText(EditExpenseActivity.this, response.message(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ExpenseResponse> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Toasty.error(EditExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    private void openimageuploader() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Matisse.from(EditExpenseActivity.this)
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
                            Toast.makeText(EditExpenseActivity.this, "Please enable permission", Toast.LENGTH_SHORT).show();
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
    List<Uri> mArrayList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && null != data) {

            //mSelected.add(data.getData());
            mArrayList = Matisse.obtainResult(data);
            mSelected.addAll(mArrayList);
            for (int i = 0; i < mSelected.size(); i++) {
                path.add(FileUtils.getPath(EditExpenseActivity.this, mSelected.get(i)));
            }

            // todo Get the Image from data
            ImageViewAdapter adapter = new ImageViewAdapter(this, mSelected, "");
            binding.prevattachment.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
            binding.prevattachment.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            // todo show this if no image is selected
            Toast.makeText(EditExpenseActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validation(EditText tripname, String expenseModeName, int salesEmployeeCode) {
        if (tripname.getText().toString().trim().equalsIgnoreCase("")) {
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
        else if (salesEmployeeCode == 0) {
            binding.salesEmployeeSpinner.requestFocus();
            binding.salesEmployeeSpinner.setError("Expense Employee Name is Required !");
            Globals.showMessage(this, "Expense Employee Name is Required!");
            return false;
        }

        return true;
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
                        binding.salesEmployeeSpinner.setAdapter(new SalesEmployeeAutoAdapter(EditExpenseActivity.this, R.layout.drop_down_textview, salesEmployeeItemList));

                    } else {
                        Globals.setmessage(EditExpenseActivity.this);
                    }

                } else if(response.code()==500){
                    Toasty.warning(EditExpenseActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                } else if(response.code()==201){
                    Toasty.warning(EditExpenseActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                } else if(response.code()==400){
                    Toasty.warning(EditExpenseActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                } else if(response.code()==401){
                    Toasty.warning(EditExpenseActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }else {
                    Globals.setmessage(getApplicationContext());
                }


            }
            @Override
            public void onFailure(Call<SaleEmployeeResponse> call, Throwable t) {
                Toast.makeText(EditExpenseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}