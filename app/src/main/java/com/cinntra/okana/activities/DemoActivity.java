package com.cinntra.okana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cinntra.okana.R;
import com.cinntra.okana.databinding.DemoBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.DemoResponse;
import com.cinntra.okana.model.DemoValue;
import com.cinntra.okana.model.LogInRequest;
import com.cinntra.okana.model.LogInResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.APIsClient;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoActivity extends AppCompatActivity {


//    @BindView(R.id.name_value)
//    EditText name_value;
//    @BindView(R.id.email_value)
//    EditText email_value;
//    @BindView(R.id.password)
//    EditText password;
//    @BindView(R.id.company_name)
//    EditText company_name;
//    @BindView(R.id.submit)
//    Button submit;
//    @BindView(R.id.progressBar2)
//    SpinKitView progressBar;

    DemoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  ButterKnife.bind(this);
        getSupportActionBar().hide();

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DemoValue demoValue = new DemoValue();
                demoValue.setName(binding.nameValue.getText().toString());
                demoValue.setCompany(binding.companyName.getText().toString());
                demoValue.setEmail(binding.emailValue.getText().toString());
                demoValue.setPhone(binding.password.getText().toString());
                demoValue.setTimestamp(Globals.getTimestamp());
                if(Globals.checkInternet(DemoActivity.this)){
                    binding.progressBar2.setVisibility(View.VISIBLE);
                    callApi(demoValue);
                }else{
                    Toast.makeText(DemoActivity.this, "Check your internet Connection", Toast.LENGTH_SHORT).show();

                }



            }
        });





    }

    private void callApi(DemoValue demoValue) {

        Call<DemoResponse> call = NewApiClient.getInstance().getApiService().createDemo(demoValue);
        call.enqueue(new Callback<DemoResponse>() {
            @Override
            public void onResponse(Call<DemoResponse> call, Response<DemoResponse> response) {
                if (response != null)
                {
                    Globals.APILog = "APILog";
                    Prefs.putString(Globals.USER_NAME,response.body().getData().get(0).getUserName());
                    Prefs.putString(Globals.USER_PASSWORD,response.body().getData().get(0).getPassword());
                    LogInRequest in = new LogInRequest();
                    in.setCompanyDB(response.body().getData().get(0).getCompanyDB());  //HANA
                    in.setPassword(response.body().getData().get(0).getPassword());//"manager"//8097
                    in.setUserName(response.body().getData().get(0).getUserName());//"manager"
                    userLogin(in);

                }
            }
            @Override
            public void onFailure(Call<DemoResponse> call, Throwable t) {

            }
        });
    }


    private void userLogin(LogInRequest in)
    {
//        progressBar.setVisibility(View.VISIBLE);
        Call<LogInResponse> call = APIsClient.getInstance().getApiService().LogIn(in);
        call.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                binding.progressBar2.setVisibility(View.GONE);
                if(response.code()==200) {
                    Prefs.putString(Globals.USER_TYPE,"manager");
                    Prefs.putString(Globals.SessionID, response.body().getSessionId());
                    long session = Long.valueOf(response.body().getSessionTimeout());
                    session = session*60*1000;

                    Prefs.putLong(Globals.SESSION_TIMEOUT,session);
                    Prefs.putLong(Globals.SESSION_REMAIN_TIME,0);

                    // LoginHierarchy2ndLevel("manager");
                    gotoHome();
                }
                else {
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(DemoActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        // handle failure to read error
                    }
                }

            }
            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                binding.progressBar2.setVisibility(View.GONE);
                Toast.makeText(DemoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void gotoHome()
    {
        Intent i = new Intent(DemoActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}