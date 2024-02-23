package com.cinntra.okana.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.DepartMentAdapter;
import com.cinntra.okana.adapters.RoleAdapter;
import com.cinntra.okana.databinding.AddContactBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.BPModel.AddContactModel;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllListResponseModel;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.CreateContactData;
import com.cinntra.okana.model.DepartMent;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.Role;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddContact extends MainBaseActivity implements View.OnClickListener {
    AppCompatActivity act;
    BusinessPartnerAllListResponseModel.Datum value;

    AddContactBinding binding ;
    String BPCode = "";
    String Flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        act = AddContact.this;

        Intent intent = getIntent();
        if (intent != null) {
            BPCode = intent.getStringExtra("cardCode"); // Replace "key" with the key you used in the Fragment
            Flag = intent.getStringExtra("flag");
            BPid = intent.getStringExtra("BPLID");
            // Use the value as needed in your Activity
        }

        binding.loaderLayout.loader.setVisibility(View.GONE);
        binding.headerLayout.headTitle.setText(getString(R.string.new_contact));
        binding.headerLayout.backPress.setOnClickListener(this);
        binding.createButton.setOnClickListener(this);

    }


    public static String CardName = "";
    public static String BPid;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                onBackPressed();
                break;
            case R.id.create_button:


                String fname = binding.firstNameValue.getText().toString().trim();
                String lname = binding.lastNameValue.getText().toString().trim();
                String position = binding.positionValue.getText().toString().trim();

                String mobile = binding.mobileValue.getText().toString().trim();
                String email = binding.emailValue.getText().toString().trim();
                String address = binding.addressValue.getText().toString().trim();

                if (validation(fname, lname, position, mobile, email, address)) {

                    CreateContactData obj = new CreateContactData();
                    obj.setCardCode(BPCode);
                    obj.setPosition(position);
                    obj.setAddress(address);
                    obj.setMobilePhone(mobile);
                    obj.setFirstName(fname);
                    obj.setLastName(lname);
                    obj.setEMail(email);
                    obj.setUBpid(BPid);
                    obj.setUBranchid("1");
                    obj.setUNationalty("Indian");
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
                        addContact(obj);
                    }
                }
                break;



        }
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


    /********************* Add Contact API *************************/

    private void addContact(CreateContactData contactData) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(contactData);
        Log.e("data", jsonTut);
        Call<AddContactModel> call = NewApiClient.getInstance().getApiService().createcontact(contactData);
        call.enqueue(new Callback<AddContactModel>() {
            @Override
            public void onResponse(Call<AddContactModel> call, Response<AddContactModel> response) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                if (response.body().getStatus() == 200) {
                    onBackPressed();
                    Toast.makeText(act, "Added Successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    AddContactModel mError = new AddContactModel();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, AddContactModel.class);
                        Toast.makeText(act, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddContactModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(act, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}