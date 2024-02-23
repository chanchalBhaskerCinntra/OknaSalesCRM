package com.cinntra.okana.activities.bpActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityUpdateContactBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.BPModel.ContactOneAPiModel;
import com.cinntra.okana.model.BPModel.UpdateContactDataModel;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateContactActivity extends MainBaseActivity implements View.OnClickListener {
    AppCompatActivity act;
    String id = "";
    String BPCode = "";
    String BPLID = "";
    ActivityUpdateContactBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        act = UpdateContactActivity.this;

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            BPCode = intent.getStringExtra("cardCode");
        }

        binding.loaderLayout.loader.setVisibility(View.GONE);
        binding.headerLayout.headTitle.setText(getString(R.string.new_contact));
        binding.headerLayout.backPress.setOnClickListener(this);
        binding.updateButton.setOnClickListener(this);

        if (Globals.checkInternet(UpdateContactActivity.this)){
            callContactOneAPi();
        }


    }


    //todo calling one contact api here---
    ContactOneAPiModel.Data contactOneData = new ContactOneAPiModel.Data();

    private void callContactOneAPi() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        Call<ContactOneAPiModel> call = NewApiClient.getInstance().getApiService().getContactOneApi(jsonObject);
        call.enqueue(new Callback<ContactOneAPiModel>() {
            @Override
            public void onResponse(Call<ContactOneAPiModel> call, Response<ContactOneAPiModel> response) {
                try {
                    if (response.body().getStatus() == 200) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        contactOneData = response.body().getData().get(0);

                        setDefaultData();

                    }
                    else if (response.body().getStatus() == 201){
                        Toasty.error(UpdateContactActivity.this, response.body().getMessage());
                    }else if (response.body().getStatus() == 401){
                        Toasty.error(UpdateContactActivity.this, response.body().getMessage());
                    }else if (response.body().getStatus() == 500){
                        Toasty.error(UpdateContactActivity.this, response.body().getMessage());
                    }else {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(UpdateContactActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ContactOneAPiModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(UpdateContactActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDefaultData() {
        binding.firstNameValue.setText(contactOneData.getFirstName());
        binding.lastNameValue.setText(contactOneData.getLastName());
        binding.mobileValue.setText(contactOneData.getMobilePhone());
        binding.emailValue.setText(contactOneData.geteMail());
        binding.positionValue.setText(contactOneData.getPosition());
        binding.addressValue.setText(contactOneData.getAddress());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                onBackPressed();
                break;
            case R.id.updateButton:
                updateContactAPi();
                break;
        }
    }

    private void updateContactAPi() {
        String fname = binding.firstNameValue.getText().toString().trim();
        String lname = binding.lastNameValue.getText().toString().trim();
        String position = binding.positionValue.getText().toString().trim();
        String mobile = binding.mobileValue.getText().toString().trim();
        String email = binding.emailValue.getText().toString().trim();
        String address = binding.addressValue.getText().toString().trim();
        if (validation(fname, lname, position, mobile, email, address)) {
            UpdateContactDataModel obj = new UpdateContactDataModel();
            obj.setId(id);
            obj.setCardCode(BPCode);
            obj.setPosition(position);
            obj.setAddress(address);
            obj.setMobilePhone(mobile);
            obj.setFirstName(fname);
            obj.setLastName(lname);
            obj.seteMail(email);
            obj.setuBpid(Prefs.getString(Globals.STOREBPLID, ""));
            obj.setuBranchid("1");
            obj.setuNationalty("Indian");
            obj.setUpdateDate(Globals.getTodaysDatervrsfrmt());
            obj.setUpdateTime(Globals.getTCurrentTime());
            obj.setCreateDate(Globals.getTodaysDatervrsfrmt());
            obj.setCreateTime(Globals.getTCurrentTime());
            obj.setTitle("");
            obj.setProfession("");
            obj.setMiddleName("");
            obj.setFax("");
            obj.setRemarks1("");
            obj.setDateOfBirth("");
            obj.setGender("");

            if (Globals.checkInternet(getApplicationContext())) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                updateContact(obj);
            }
        }
    }


    /********************* Add Contact API *************************/

    private void updateContact(UpdateContactDataModel contactData) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(contactData);
        Log.e("data", jsonTut);
        Call<ContactOneAPiModel> call = NewApiClient.getInstance().getApiService().updateContact(contactData);
        call.enqueue(new Callback<ContactOneAPiModel>() {
            @Override
            public void onResponse(Call<ContactOneAPiModel> call, Response<ContactOneAPiModel> response) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                if (response.body().getStatus() == 200) {
                    onBackPressed();
                    Toast.makeText(act, "Update Successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    ContactOneAPiModel mError = new ContactOneAPiModel();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, ContactOneAPiModel.class);
                        Toast.makeText(act, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactOneAPiModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(act, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String fname, String lname, String rtvalue, String mobile, String email, String address) {

        if (fname.isEmpty()) {
            Globals.showMessage(getApplicationContext(), "Enter First Name");
            return false;
        } else if (lname.isEmpty()) {
            Globals.showMessage(getApplicationContext(), "Enter Last Name");
            return false;
        }else if (rtvalue.isEmpty()) {
            Globals.showMessage(getApplicationContext(), "Enter Position");
            return false;
        }  else if (mobile.isEmpty()) {
            Globals.showMessage(getApplicationContext(), "Enter Mobile Number");
            return false;
        } else if (!email.isEmpty()) {
            if (isvalidateemail()) {
                binding.emailValue.setError("This email address is not valid");
                return false;
            }
        } else if (address.isEmpty()) {
            Globals.showMessage(getApplicationContext(), "Enter Address");
            return false;
        }
        return true;
    }


    private boolean isvalidateemail() {
        String checkEmail = binding.emailValue.getText().toString();
        boolean hasSpecialEmail = Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches();
        if (!hasSpecialEmail) {
            binding.emailValue.setError("This email address is not valid");
            return true;
        }
        return false;
    }

}