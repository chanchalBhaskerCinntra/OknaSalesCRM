package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.adapters.BranchAdapter;
import com.cinntra.okana.adapters.BusinessPartnerDetail;
import com.cinntra.okana.adapters.CountryAdapter;
import com.cinntra.okana.adapters.StateAdapter;
import com.cinntra.okana.databinding.ContactViewBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.Branch;
import com.cinntra.okana.model.BranchResponse;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.data.geojson.GeoJsonPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessPartnerBranch extends Fragment {

//    @BindView(R.id.new_contact)
//    FloatingActionButton new_contact;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
    BranchAdapter contactAdapter ;
    BusinessPartnerData customerItem;
    ArrayList<Branch> branchList = new ArrayList<>();
    DatabaseClick dbClick;

    ContactViewBinding binding;
    public BusinessPartnerBranch(BusinessPartnerDetail businessPartnerDetail, BusinessPartnerData customerItem) {
        this.dbClick = ( DatabaseClick)businessPartnerDetail;
        this.customerItem = customerItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ContactViewBinding.inflate(inflater,container,false);
        View v = inflater.inflate(R.layout.contact_view,container,false);
        //   ButterKnife.bind(this,v);

        if(Globals.checkInternet(getContext())) {
            binding.loader.loader.setVisibility(View.VISIBLE);
            callApi(customerItem.getCardCode());

        }
        
        binding.newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openaddbranchdialog();
            }
        });

        binding. swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(Globals.checkInternet(getActivity())){
                    callApi(customerItem.getCardCode());
                }
                else
                    binding. swipeRefreshLayout.setRefreshing(false);

            }
        });

        return binding.getRoot();
    }

    private void callApi(String id) {
        Branch branch = new Branch();
        branch.setBPCode(id);

        Call<BranchResponse> call = NewApiClient.getInstance().getApiService().getBranch(branch);
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {

                if(response.code()==200)
                {
                    binding.loader. loader.setVisibility(View.GONE);
                    if(response.body().getData().size()>0) {
                        binding.noDatafound.setVisibility(View.GONE);
                        branchList.clear();
                        branchList.addAll(response.body().getData());
                    }else{
                        binding.noDatafound.setVisibility(View.VISIBLE);

                    }
                    contactAdapter =new BranchAdapter(dbClick,getContext(),branchList);
                    binding. recyclerView.setAdapter(contactAdapter);
                    contactAdapter.notifyDataSetChanged();

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(getContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
                binding.swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                binding. swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter;
    String countryCode, countryName;
    String stateName,stateCode;
    ArrayList<StateData> stateList = new ArrayList<>();
    private void openaddbranchdialog() {
        countryAdapter = new CountryAdapter(getContext(), MainActivity.countrylistFromLocal);
        final Dialog dialog = new Dialog(getContext());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.add_branch_dialog);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        EditText address_value = dialog.findViewById(R.id.address_value);
        EditText city_value = dialog.findViewById(R.id.city_value);
        Spinner country = dialog.findViewById(R.id.country);
        Spinner state = dialog.findViewById(R.id.state);
        EditText zipcode = dialog.findViewById(R.id.zipcode);
        EditText name = dialog.findViewById(R.id.name);
        Button done = dialog.findViewById(R.id.done);
        country.setAdapter(countryAdapter);
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryCode = MainActivity.countrylistFromLocal.get(position).getCode();
                countryName = MainActivity.countrylistFromLocal.get(position).getName();
                callStateApi(countryCode);
//                stateAdapter = new StateAdapter(getContext(),stateList);
                state.setAdapter(stateAdapter);
                stateAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                countryCode = MainActivity.countrylistFromLocal.get(0).getCode();
                countryName = MainActivity.countrylistFromLocal.get(0).getName();
            }
        });

        if(stateList.isEmpty()){
            StateData sta = new StateData();
            sta.setName("Select State");
            stateList.add(sta);
        }

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateName= stateList.get(position).getName();
                stateCode= stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
           }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation(address_value,name,zipcode,stateName)){
                    String addr= stateName;

                    GeoJsonPoint final_coordinates = null;
                    try {
                        final_coordinates = getLocationFromAddress(addr);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    double latitude = final_coordinates.getCoordinates().latitude;
                    double longitude = final_coordinates.getCoordinates().longitude;
                    Branch branch = new Branch();
                    branch.setBPCode(customerItem.getCardCode());
                    branch.setBpid((customerItem.getId()).toString());
                    branch.setBranchName("");
                    branch.setAddressName(name.getText().toString());
                    branch.setAddressName2("");
                    branch.setAddressName3("");
                    branch.setBuildingFloorRoom("");
                    branch.setStreet(address_value.getText().toString());
                    branch.setBlock("");
                    branch.setCounty("");
                    branch.setCity(city_value.getText().toString());
                    branch.setZipCode(zipcode.getText().toString());
                    branch.setState(stateCode);
                    branch.setU_STATE(stateName);
                    branch.setCountry(countryCode);
                    branch.setU_COUNTRY(countryName);
                    branch.setPhone("");
                    branch.setFax("");
                    branch.setEmail("");
                    branch.setTaxOffice("");
                    branch.setGstin("");
                    branch.setGstType("");
                    branch.setShippingType("");
                    branch.setPaymentTerm("");
                    branch.setCurrentBalance("");
                    branch.setCreditLimit("");
                    branch.setCreateDate(Globals.getTodaysDate());
                    branch.setUpdateDate(Globals.getTodaysDate());
                    branch.setCreateTime(Globals.getTCurrentTime());
                    branch.setUpdateTime(Globals.getTCurrentTime());
                    branch.setLat(String.valueOf(latitude));
                    branch.setLong(String.valueOf(longitude));
                    if(Globals.checkInternet(getContext())) {
                        calladdbranchAPi(branch);
                        dialog.cancel();
                    }
                }
                
            }
        });




        dialog.show();
    }

    private void callStateApi(String countryCode) {
        stateName = "";
        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if(response.code()==200)
                {
                    stateList.clear();
                    if(response.body().getData().size()>0) {

                        stateList.addAll(response.body().getData());
                    }else{
                        StateData sta = new StateData();
                        sta.setName("Select State");
                        stateList.add(sta);
                    }
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(getContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public GeoJsonPoint getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        GeoJsonPoint p1 = null;


        address = coder.getFromLocationName(strAddress, 5);
        if (address == null) {
            return null;
        }
        Address location = address.get(0);
        double lat = location.getLatitude();
        double longi = location.getLongitude();

        LatLng coordinates = new LatLng(lat, longi);

        p1 = new GeoJsonPoint(coordinates);
        return p1;
    }

    private void calladdbranchAPi(Branch branch) {

        Call<BranchResponse> call = NewApiClient.getInstance().getApiService().addBranch(branch);
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {

                if(response.code()==200)
                {
                    Toast.makeText(getContext(),"Added Successfully",Toast.LENGTH_LONG).show();
                    binding.loader.loader.setVisibility( View.VISIBLE);
                    callApi(customerItem.getCardCode());
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(getContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                binding.loader. loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validation(EditText addresss, EditText name, EditText zipcode, String stateName) {
        if(addresss.length()<0){
            addresss.setError(getContext().getResources().getString(R.string.address_error));
            return false;
        }else if (name.length()<0){
            name.setError(getContext().getResources().getString(R.string.name_error));
            return false;
        }else  if (zipcode.length()<0){
            zipcode.setError(getContext().getResources().getString(R.string.zip_code_error));
            return false;
        }else if(stateName.isEmpty()){
            Toast.makeText(getContext(), "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        callApi(customerItem.getCardCode());
    }
}
