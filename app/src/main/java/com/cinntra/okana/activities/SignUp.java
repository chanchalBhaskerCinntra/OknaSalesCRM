package com.cinntra.okana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityNewuserBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.NewEmployeeUser;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp extends MainBaseActivity
      {
//    @BindView(R.id.progressBar2)
//    ProgressBar progressBar;
//    @BindView(R.id.login_here)
//    TextView login_here;
//    @BindView(R.id.username)
//    EditText username;
//    @BindView(R.id.company_name)
//    EditText company_name;
//    @BindView(R.id.last_name)
//    EditText last_name;
//    @BindView(R.id.first_name)
//    EditText first_name;
//    @BindView(R.id.email_value)
//    EditText email_value;
//    @BindView(R.id.mobile_no_value)
//    EditText mobile_no_value;
//    @BindView(R.id.designation)
//    EditText designation;
//    @BindView(R.id.password)
//    EditText password;
//    @BindView(R.id.confirm_pass)
//    EditText confirm_pass;
//    @BindView(R.id.register_button)
//    Button register_button;


          ActivityNewuserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding=ActivityNewuserBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
  //  setContentView(R.layout.activity_newuser);
  //  ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
    //progressBar = findViewById(R.id.progressBar);
   binding.progressBar2.setVisibility(View.GONE);
        binding.loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation(binding.companyName,binding.firstName,binding.mobileNoValue,binding.emailValue,binding.designation,binding.password,binding.confirmPass)){
                    NewEmployeeUser newEmployeeUser = new NewEmployeeUser();
                    newEmployeeUser.setCompanyID("");
                    newEmployeeUser.setFirstName(binding.firstName.getText().toString());
                    newEmployeeUser.setLastName(binding.lastName.getText().toString());
                    newEmployeeUser.setMiddleName("");
                    newEmployeeUser.setActive("");
                    newEmployeeUser.setBranch("");
                    newEmployeeUser.setLastLoginOn("");
                    newEmployeeUser.setSalesEmployeeCode("");
                    newEmployeeUser.setSalesEmployeeName("");
                    newEmployeeUser.setEmployeeID("");
                    newEmployeeUser.setRole("salesperson");
                    newEmployeeUser.setPosition(binding.designation.getText().toString());
                    newEmployeeUser.setReportingTo("");
                    newEmployeeUser.setPasswordUpdatedOn("");
                    newEmployeeUser.setUserName(binding.username.getText().toString());
                    newEmployeeUser.setTimestamp("");
                    newEmployeeUser.setPassword(binding.password.getText().toString());
                    newEmployeeUser.setEmail(binding.emailValue.getText().toString());
                    newEmployeeUser.setMobile(binding.mobileNoValue.getText().toString());
                    if(Globals.checkInternet(SignUp.this)){
                        binding.progressBar2.setVisibility(View.VISIBLE);
                        callNewemployeCreateApi(newEmployeeUser);
                    }
                }
            }
        });
       }

          private void callNewemployeCreateApi(NewEmployeeUser newEmployeeUser) {

              Call<LeadResponse> call = NewApiClient.getInstance().getApiService().createdemoEmployee(newEmployeeUser);
              call.enqueue(new Callback<LeadResponse>() {
                  @Override
                  public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {

                      if(response.code()==200)
                      {
                          if(response.body().getMessage().equalsIgnoreCase("successful")){
                              onBackPressed();
                              Toasty.success(SignUp.this, "Create Successfully", Toast.LENGTH_LONG).show();
                          }
                          else{
                              Toasty.warning(SignUp.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                          }

                      }
                      else
                      {
                          //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                          Gson gson = new GsonBuilder().create();
                          LeadResponse mError = new LeadResponse();
                          try {
                              String s =response.errorBody().string();
                              mError= gson.fromJson(s, LeadResponse.class);
                              Toast.makeText(SignUp.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                          } catch (IOException e) {
                              //handle failure to read error
                          }
                      }
                      binding.progressBar2.setVisibility(View.GONE);
                  }
                  @Override
                  public void onFailure(Call<LeadResponse> call, Throwable t) {
                      binding. progressBar2.setVisibility(View.GONE);
                      Toasty.error(SignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              });
          }

          private boolean validation(EditText company_name, EditText first_name, EditText mobile_no_value, EditText email_value, EditText designation, EditText password, EditText confirm_pass) {
          if(binding.username.length()==0) {
              Globals.showMessage(this,getResources().getString(R.string.enter_username));
              return false;
          }else if(company_name.length()==0){
                  Globals.showMessage(this,getResources().getString(R.string.entercompany_name));
                  return false;
              }else if(first_name.length()==0){
                  Globals.showMessage(this,getResources().getString(R.string.enter_firstname));
                  return false;
              }else if(mobile_no_value.length()<6||mobile_no_value.length()>12){
                  Globals.showMessage(this,getResources().getString(R.string.enter_mobileno));
                  return false;
              }else if(email_value.length()==0||!Globals.isvalidateemail(email_value)){
                  Globals.showMessage(this,getResources().getString(R.string.enter_email));
                  return false;
              }else if(designation.length()==0){
                  Globals.showMessage(this,getResources().getString(R.string.entry_deignation));
                  return false;
              }else if(!password.getText().toString().trim().equalsIgnoreCase(confirm_pass.getText().toString().trim())){
                  Globals.showMessage(this,getResources().getString(R.string.pass_notMatch));
                  return false;
              }

    return true;
    }

          @Override
          public boolean onOptionsItemSelected(@NonNull MenuItem item) {
              switch (item.getItemId()){
                  case android.R.id.home:
                      this.finish();
                      return true;
              }
              return super.onOptionsItemSelected(item);
          }



       }