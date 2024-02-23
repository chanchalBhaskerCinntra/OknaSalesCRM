package com.cinntra.okana.activities.bpActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.StateAutoAdapter;
import com.cinntra.okana.databinding.ActivityUpdateBranchBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.AddBranchResponse;
import com.cinntra.okana.model.BPModel.BranchOneResponseModel;
import com.cinntra.okana.model.BPModel.BranchesResponseModel;
import com.cinntra.okana.model.BPModel.GroupTypeResponseModel;
import com.cinntra.okana.model.BPModel.UpdateBranchRequestModel;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.CountryResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBranchActivity extends AppCompatActivity {

    ActivityUpdateBranchBinding binding;
    String id, Flag = "";
    ArrayList<BranchesResponseModel.Data> branchesAllList_gl = new ArrayList<>();
    String BPLID =  "";
    String ServiceType="";
    String Countrycode="";
    String StateName="";
    String BPCode="";
    String StateCode="";
    String billGstStateCode = "";
    String CountryName="";
    String BranchType="";
    String GST_Type="";
    String CardType="";
    String billZonalManagerCode = "";
    String billZonalManagerName = "";

    String selectedAddress, selectedLatitude, selectedLongitude = "";

    String currentAddress, currentLatitude, currentLongitude = "";

    ArrayList localData = new ArrayList<>();
    ArrayList<StateData> stateList = new ArrayList<>();

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 1;
    BranchOneResponseModel.Data branchOneResponseModel_gl = new BranchOneResponseModel.Data();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBranchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(UpdateBranchActivity.this);
        //todo get arguments..
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            Flag = intent.getStringExtra("flag");
            BPCode = intent.getStringExtra("cardCode");
            CardType = intent.getStringExtra("cardType");
            BPLID = intent.getStringExtra("BPLID");
        }

        if (Globals.checkInternet(UpdateBranchActivity.this)){
            callBranchOneAPi();
        }

        eventManager();

        binding.headerLayout.headTitle.setText("Update Branch");

        binding.headerLayout.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void eventManager() {
        callCountryApi();

        //todo bill to state item click..
        binding.acState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StateName = stateList.get(position).getName();
                StateCode = stateList.get(position).getCode();
//                billGstStateCode = stateList.get(position).getGSTCode();

                binding.acState.setText(stateList.get(position).getName());
            }
        });


        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpdateBranchRequest();

            }
        });


}


    //todo set request for add branch...
    private void setUpdateBranchRequest() {
        String branchName = binding.edtBranchName.getText().toString();
        String address = binding.addressValue.getText().toString();
        String zipcode = binding.etZipcode.getText().toString();
        String stateName = binding.acState.getText().toString();

        if (validation(branchName, address, zipcode, stateName, countryname)) {

            UpdateBranchRequestModel requestModel = new UpdateBranchRequestModel();
            requestModel.setId(branchOneResponseModel_gl.getId());
            requestModel.setAddressType("bo_ShipTo");
            requestModel.setBPID(BPLID);
            requestModel.setRowNum(branchOneResponseModel_gl.getRowNum());
            requestModel.setBPCode(BPCode);
            requestModel.setBranchName(branchName);
            requestModel.setAddressName(branchName);
            requestModel.setAddressName2("");
            requestModel.setAddressName3("");
            requestModel.setBuildingFloorRoom("");
            requestModel.setStreet(binding.addressValue.getText().toString());
            requestModel.setBlock("");
            requestModel.setCounty(countryCode);
            requestModel.setCity("");
            requestModel.setZipCode(binding.etZipcode.getText().toString());
            requestModel.setState(StateCode);
            requestModel.setCountry(countryCode);
            requestModel.setPhone("");
            requestModel.setFax("");
            requestModel.setEmail("");
            requestModel.setTaxOffice("");
            requestModel.setGSTIN("");
            requestModel.setShippingType("");
            requestModel.setPaymentTerm("");
            requestModel.setCurrentBalance("");
            requestModel.setCreditLimit("");
            requestModel.setStatus("1");
            requestModel.setDefault("0");
            requestModel.setU_SHPTYP("");
            requestModel.setU_COUNTRY(countryname);
            requestModel.setU_STATE(StateName);
            requestModel.setCreateDate(Globals.getTodaysDatervrsfrmt());
            requestModel.setCreateTime(Globals.getTCurrentTime());
            requestModel.setUpdateDate(Globals.getTodaysDatervrsfrmt());
            requestModel.setUpdateTime(Globals.getTCurrentTime());
            requestModel.setGstType("");
            requestModel.setLat(Globals.currentlattitude);
            requestModel.setLong(Globals.currentlongitude);

            if (Globals.checkInternet(UpdateBranchActivity.this)) {
                callUpdateBranchApi(requestModel);
            }
        }

    }


    //todo cal branch api here...
    private void callUpdateBranchApi(UpdateBranchRequestModel in) {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        Gson gson = new Gson();
        String jsonTut = gson.toJson(in);
        Log.e("data", jsonTut);

        Call<AddBranchResponse> call = NewApiClient.getInstance().getApiService().updateBranchApi(in);
        call.enqueue(new Callback<AddBranchResponse>() {
            @Override
            public void onResponse(Call<AddBranchResponse> call, Response<AddBranchResponse> response) {
                try {
                    if (response.body().getStatus() == 200) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Toasty.success(UpdateBranchActivity.this, "Update Successfully.", Toasty.LENGTH_SHORT).show();
                        onBackPressed();

                    } else {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(UpdateBranchActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<AddBranchResponse> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(UpdateBranchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //todo calling contact person api..
  /*  private void callBusinessPartnerOneApi() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", BPCode);
        Call<ContactPersonResponseModel> call = NewApiClient.getInstance().getApiService(this).getContactPerson(jsonObject);
        call.enqueue(new Callback<ContactPersonResponseModel>() {
            @Override
            public void onResponse(Call<ContactPersonResponseModel> call, Response<ContactPersonResponseModel> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        branchOneResponseModel_gl = response.body().getData().get(0);

                        setDefaultData();
                    }

                } else {
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }

            }

            @Override
            public void onFailure(Call<ContactPersonResponseModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/



    //todo call branch one api here...
    private void callBranchOneAPi() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("AddressType", Flag);
        Call<BranchOneResponseModel> call = NewApiClient.getInstance().getApiService().getBranchOneApi(jsonObject);
        call.enqueue(new Callback<BranchOneResponseModel>() {
            @Override
            public void onResponse(Call<BranchOneResponseModel> call, Response<BranchOneResponseModel> response) {
                try {
                    if (response.body().getStatus() == 200) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        branchOneResponseModel_gl = response.body().getData().get(0);

                        setDefaultData();

                    } else {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(UpdateBranchActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<BranchOneResponseModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(UpdateBranchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //todo set default data...
    private void setDefaultData() {

        callStateApi(branchOneResponseModel_gl.getCountry());
        binding.edtBranchName.setText(branchOneResponseModel_gl.getAddressName());
        binding.addressValue.setText(branchOneResponseModel_gl.getStreet());
        binding.etZipcode.setText(branchOneResponseModel_gl.getZipCode());
        binding.acCountry.setText(branchOneResponseModel_gl.getU_COUNTRY());
        binding.acState.setText(branchOneResponseModel_gl.getU_STATE());

        countryCode = branchOneResponseModel_gl.getCountry();
        StateCode = branchOneResponseModel_gl.getState();
        BranchType = branchOneResponseModel_gl.getBranchType();

        countryname = branchOneResponseModel_gl.getU_COUNTRY();
        StateName = branchOneResponseModel_gl.getU_STATE();
        ServiceType = branchOneResponseModel_gl.getServiceType();

    }



    //todo calling country api here...

    ArrayList<CountryData> countyList = new ArrayList<>();
    String countryname = "";
    String countryCode = "";

    private void callCountryApi() {
        Call<CountryResponse> call = NewApiClient.getInstance().getApiService().getCountryList();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.body().getStatus() == 200) {
                    if (response.body().getData().size() > 0) {

                        List<CountryData> itemsList = response.body().getData();
                        itemsList = filterList(response.body().getData());
                        countyList.addAll(itemsList);

                        List<String> itemNames = new ArrayList<>();
                        List<String> cardCodeName = new ArrayList<>();
                        for (CountryData item : countyList) {
                            itemNames.add(item.getName());
                            cardCodeName.add(item.getCode());
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<>(UpdateBranchActivity.this, R.layout.drop_down_textview, itemNames);
                        binding.acCountry.setAdapter(adapter);


                        //todo bill to and ship to address drop down item select..
                        binding.acCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    String countryName = (String) parent.getItemAtPosition(position);
                                    countryname = countryName;

                                    int pos = Globals.getCountryCodePos(countyList, countryName);
                                    countryCode = countyList.get(pos).getCode();

                                    if (countryName.isEmpty()) {
                                        binding.rlRecyclerViewLayout.setVisibility(View.GONE);
                                        binding.rvCountryList.setVisibility(View.GONE);
                                    } else {
                                        binding.rlRecyclerViewLayout.setVisibility(View.VISIBLE);
                                        binding.rvCountryList.setVisibility(View.VISIBLE);
                                    }

                                    if (!countryName.isEmpty()) {
                                        adapter.notifyDataSetChanged();
                                        binding.acCountry.setText(countryName);
                                        binding.acCountry.setSelection(binding.acCountry.length());

                                        callStateApi(countryCode);
                                    }else {
                                        countryname = "";
                                        countryCode = "";
                                        binding.acCountry.setText("");
                                    }
                                }catch (Exception e){
                                    Log.e("catch", "onItemClick: "+e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });

//                        binding.addContactPartner.addressSection.acCountry.setAdapter(new CountryListAdapter(AddBPCustomer.this, R.layout.drop_down_textview, countyList));//todo comment by me

                    }

                }else {
                    Toasty.error(UpdateBranchActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(UpdateBranchActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<CountryData> filterList(List<CountryData> value) {
        List<CountryData> tempList = new ArrayList<>();
        for (CountryData customer : value) {
            if (!customer.getName().equals("admin")) {
                tempList.add(customer);
            }
        }
        return tempList;
    }


    //todo calling state api here...
    private void callStateApi(String countryCode) {
        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.body().getStatus() == 200) {
                    stateList.clear();
                    if (response.body().getData().size() > 0) {
                        stateList.addAll(response.body().getData());
                    } else {
                        StateData sta = new StateData();
                        sta.setName("Select State");
                        stateList.add(sta);
                    }
                    StateAutoAdapter stateAdapter = new StateAutoAdapter(UpdateBranchActivity.this, R.layout.drop_down_textview, stateList);
                    binding.acState.setAdapter(stateAdapter);
                    stateAdapter.notifyDataSetChanged();
                   /* StateName = stateList.get(0).getName();
                    StateCode = stateList.get(0).getCode();*/

                } else {
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(UpdateBranchActivity.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {
                Toast.makeText(UpdateBranchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String branchName, String address, String zipcode, String stateName, String countryname) {
        if (branchName.isEmpty()) {
            Globals.showMessage(UpdateBranchActivity.this, "Enter Branch Name");
            return false;
        }else if (address.isEmpty()) {
            binding.addressValue.requestFocus();
            binding.addressValue.setError("Address is Required");
            return false;
        } else if (zipcode.isEmpty()) {
            binding.etZipcode.requestFocus();
            binding.etZipcode.setError("Zipcode is Required");
            return false;
        } else if (!zipcode.isEmpty()){
            if (zipcode.length() > 0 && zipcode.charAt(0) == '0') {
                binding.etZipcode.requestFocus();
                Globals.showMessage(UpdateBranchActivity.this, "Invalid Bill Address Zip Code");
            }
        } else if (stateName.isEmpty()) {
            binding.acState.requestFocus();
            binding.acState.setError("State Name is Required");
            return false;
        }

        else if (countryname.isEmpty()) {
            binding.acCountry.requestFocus();
            binding.acCountry.setError("Country is Required");
            return false;
        }

        return true;
    }



}