package com.cinntra.okana.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityLoginBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MyApp;
import com.cinntra.okana.interfaces.DatabaseClick;

import com.cinntra.okana.model.LogInDetail;
import com.cinntra.okana.model.LogInRequest;
import com.cinntra.okana.model.LogInResponse;
import com.cinntra.okana.model.NewLogINResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.APIsClient;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.HashMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener, DatabaseClick {
//    private Button signin;
//    @BindView(R.id.progressBar1)
//    ProgressBar progressBar;
//    @BindView(R.id.goto_reg)
//    LinearLayout goto_reg;
//    @BindView(R.id.sql_setting)
//    RelativeLayout sql_setting;
//    @BindView(R.id.login_username)
//    EditText login_username;
//    @BindView(R.id.login_password)
//    EditText login_password;
//    @BindView(R.id.register_here)
//    TextView register_here;


    private AppCompatActivity activity;
    private String token = "";


    ActivityLoginBinding binding;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MyApp.timer != null) {
            MyApp.timer.cancel();
            MyApp.timer = null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        activity = Login.this;
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);

        overridePendingTransition(0, 0);
        View relativeLayout = findViewById(R.id.login_container);
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);

        FirebaseMessaging messaging = FirebaseMessaging.getInstance();
        messaging.getToken().addOnSuccessListener(s -> {

            if (Prefs.getString(Globals.TOKEN, "").isEmpty()) {
                token = s;
                Prefs.putString(Globals.TOKEN, s);
            }
        });


        binding.loadingback.setVisibility(View.GONE);
        // signin =findViewById(R.id.login_button);
        binding.loginButton.setOnClickListener(this);
        binding.gotoReg.setOnClickListener(this);
        binding.sqlSetting.setOnClickListener(this);
        binding.registerHere.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:


                if (Globals.checkInternet(this)) {

                    if (validation(binding.loginUsername.getText().toString().trim(), binding.loginPassword.getText().toString().trim())) {

                        Globals.APILog = "APILog";

                        Prefs.putString(Globals.SelectedBranch, "");
                        Prefs.putString(Globals.SelectedBranchID, "");
                        Prefs.putString(Globals.SelectedWareHose, "");
                        Prefs.putString(Globals.SessionID, "");
                        Prefs.putString(Globals.USERNAME, binding.loginUsername.getText().toString().trim());
                        Prefs.putString(Globals.USER_PASSWORD, binding.loginPassword.getText().toString().trim());

                        //loginUser(Globals.SelectedDB,login_username.getText().toString().trim(),login_password.getText().toString().trim());
                    //    sessionloginApi();

                        callLogInApi(binding.loginUsername.getText().toString().trim(), binding.loginPassword.getText().toString().trim());

//             callLogInApi(login_username.getText().toString().trim(),login_password.getText().toString().trim());


                    }


                }

                break;
            case R.id.register_here:
                startActivity(new Intent(this, SignUp.class));

                break;

       /* case R.id.goto_reg:
            startActivity(new Intent(this,SignUp.class));
            break;*/
            case R.id.sql_setting:
           /* Intent intent=new Intent(this,SqlSetting.class);
            startActivityForResult(intent, 2);*/

                Intent intent = new Intent(this, DemoActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void sessionloginApi() {
        HashMap<String, String> session = new HashMap<>();
        session.put("username", "root");
        session.put("password", "Sunil@123");


        Call<NewLogINResponse> call = NewApiClient.getInstance().getApiService().sessionlogin(session);
        call.enqueue(new Callback<NewLogINResponse>() {
            @Override
            public void onResponse(Call<NewLogINResponse> call, Response<NewLogINResponse> response) {

                Globals.APILog = "Not";
                Prefs.putBoolean(Globals.AutoLogIn, true);
                Prefs.putString(Globals.SessionID, response.body().getToken());
                Log.e("DETAILS>>>", "onResponse: " + binding.loginUsername.getText().toString().trim() + binding.loginPassword.getText().toString().trim());
                callLogInApi(binding.loginUsername.getText().toString().trim(), binding.loginPassword.getText().toString().trim());


            }

            @Override
            public void onFailure(Call<NewLogINResponse> call, Throwable t) {

            }
        });
    }

    private void callLogInApi(String username, String password) {

        binding.loadingback.setVisibility(View.VISIBLE);

        LogInDetail logInDetail = new LogInDetail();
        logInDetail.setUserName(username);
        logInDetail.setPassword(password);
        logInDetail.setFcm(token);
        Prefs.putString(Globals.USER_PASSWORD, password);

        Call<NewLogINResponse> call = NewApiClient.getInstance().getApiService().loginEmployee(logInDetail);
        call.enqueue(new Callback<NewLogINResponse>() {
            @Override
            public void onResponse(Call<NewLogINResponse> call, Response<NewLogINResponse> response) {

                if (response.body().getStatus() == 200) {
                    binding.loadingback.setVisibility(View.GONE);

                    Gson gson = new Gson();
                    String json = gson.toJson(response.body().getLogInDetail());
                    Prefs.putString(Globals.AppUserDetails, json);
                    Prefs.putBoolean(Globals.AutoLogIn, true);
                    //   Globals.APILog = "APILog";
                    Globals.TeamSalesEmployeCode = response.body().getLogInDetail().getSalesEmployeeCode();
                    Globals.TeamRole = response.body().getLogInDetail().getRole();
                    Globals.TeamEmployeeID = String.valueOf(response.body().getLogInDetail().getId());
                    Globals.MYEmployeeID = String.valueOf(response.body().getLogInDetail().getId());
                    Globals.SelectedDB = String.valueOf(response.body().getSap().getCompanyDB());
                    Prefs.putString(Globals.Employee_Name, response.body().getLogInDetail().getUserName());
                    Prefs.putString(Globals.Employee_Code, response.body().getLogInDetail().getSalesEmployeeCode());
                    Prefs.putString(Globals.EmployeeID, String.valueOf(response.body().getLogInDetail().getId()));
                    Prefs.putString(Globals.SalesEmployeeCode, String.valueOf(response.body().getLogInDetail().getSalesEmployeeCode()));
                    Prefs.putString(Globals.SalesEmployeeName, String.valueOf(response.body().getLogInDetail().getSalesEmployeeName()));
                    Prefs.putString(Globals.SelectedDB, String.valueOf(response.body().getSap().getCompanyDB()));
                    Prefs.putString(Globals.Role, String.valueOf(response.body().getLogInDetail().getRole()));
                    Prefs.putString(Globals.Position, String.valueOf(response.body().getLogInDetail().getPosition()));

                    Prefs.putBoolean(Globals.Location_FirstTime,true);
                    Prefs.putBoolean(Globals.LocationRestart,false);

                    long session = Long.parseLong("30");
                    session = session * 60 * 1000;

                    Prefs.putLong(Globals.SESSION_TIMEOUT, session);
                    Prefs.putLong(Globals.SESSION_REMAIN_TIME, 0);
                            /* LogInRequest in = new LogInRequest();
                            in.setCompanyDB(response.body().getSap().getCompanyDB());  //HANA
                            in.setPassword(response.body().getSap().getPassword());//"manager"//8097
                            in.setUserName(response.body().getSap().getUserName());//"manager"
                             userLogin(in);*/
                    gotoHome();


                } else {
                    binding.loadingback.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Check Login Credentials.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<NewLogINResponse> call, Throwable t) {
                binding.loadingback.setVisibility(View.GONE);
                Toast.makeText(Login.this, "Check Login Credentials.", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void userLogin(LogInRequest in) {

        Call<LogInResponse> call = APIsClient.getInstance().getApiService().LogIn(in);
        call.enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                binding.loadingback.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Prefs.putString(Globals.USER_TYPE, "manager");
                    Prefs.putString(Globals.SessionID, response.body().getSessionId());
                    long session = Long.parseLong(response.body().getSessionTimeout());
                    session = session * 60 * 1000;

                    Prefs.putLong(Globals.SESSION_TIMEOUT, session);
                    Prefs.putLong(Globals.SESSION_REMAIN_TIME, 0);

                    // LoginHierarchy2ndLevel("manager");
                    gotoHome();
                } else {
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(Login.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        // handle failure to read error
                    }
                }

            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                binding.loadingback.setVisibility(View.GONE);
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void gotoHome() {
        Intent i = new Intent(Login.this, MainActivity.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        finish();
    }


    private boolean validation(String user, String pass) {
        if (user.isEmpty()) {
            Toast.makeText(activity, getResources().getString(R.string.enter_user), Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.isEmpty()) {
            Toast.makeText(activity, getResources().getString(R.string.enter_sql_pass), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    /******************** Rest Client ***********************/


    /************ DataBase Alert *****************/


    Dialog dialog;


    @Override
    public void onClick(int po) {


        Prefs.putString(Globals.SessionID, "");
        Globals.APILog = "APILog";
        callLogInApi(binding.loginUsername.getText().toString().trim(), binding.loginPassword.getText().toString().trim());

        if (dialog != null)
            dialog.dismiss();
    }


}