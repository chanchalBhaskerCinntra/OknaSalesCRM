package com.cinntra.okana.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.AssignToAdapter;
import com.cinntra.okana.adapters.LeadTypeAdapter;
import com.cinntra.okana.adapters.StateAutoAdapter;
import com.cinntra.okana.databinding.LeadDetailBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.CreateLead;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.LeadTypeResponse;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.model.UpdateLeadModel;
import com.cinntra.okana.newapimodel.GlobalResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.LeadValue;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadDetail extends Fragment implements View.OnClickListener {

    LeadValue leadValue;
    Context leadsActivity;
    List<LeadValue> leadValues = new ArrayList<>();
    String[] leadstatus = new String[4];
    String status = "";
    String leadtype = "";
    Integer id;
    ArrayList<LeadTypeData> leadTypeData = new ArrayList<>();
    Context context;

    LeadDetailBinding binding;
    String selectedCategory = "";

    String StateName = "";
    String StateCode = "";
    String AssignName = "";
    String AssignCode = "";
    String ProductInterestName = "";
    String sourcetype = "";


    List<String> productInterestList_gl = Arrays.asList(Globals.productInterestList_gl);

    public LeadDetail(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            if (b.getString("From").equalsIgnoreCase("Lead")) {
                leadValue = (LeadValue) b.getParcelable(Globals.LeadDetails);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                id = leadValue.getId();
            } else {
                id = Integer.parseInt(b.getString("From", "2"));
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = LeadDetailBinding.inflate(inflater, container, false);
        // View v=inflater.inflate(R.layout.lead_detail, container, false);
        //  ButterKnife.bind(this,v);
        binding.headerBottomRounded.headTitle.setText("Lead Detail");
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        leadstatus = getResources().getStringArray(R.array.lead_status);
        binding.loader.setVisibility(View.VISIBLE);
        eventManager();
        if (Globals.checkInternet(getContext())) {
            callStateAPi();

            callAssignToApi();

            callSourceApi();

        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }


        return binding.getRoot();
    }


    ArrayList<StateData> stateList_gl = new ArrayList<>();

    //todo calling  state api --
    private void callStateAPi() {
        StateData stateData = new StateData();
        stateData.setCountry("IN");
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.body().getStatus() == 200) {
                    stateList_gl.clear();
                    if (response.body().getData().size() > 0) {
                        stateList_gl.addAll(response.body().getData());
                        StateAutoAdapter stateAdapter = new StateAutoAdapter(getActivity(), R.layout.drop_down_textview, stateList_gl);

                        //todo set state..
                        binding.acState.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
//                        billtoState = billStateList.get(0).getName();
//                        billtoStateCode = billStateList.get(0).getCode();
                    }

                } else if (response.body().getStatus() == 201) {
                    Globals.showMessage(getActivity(), response.body().getMessage());
                } else if (response.body().getStatus() == 500) {
                    Globals.showMessage(getActivity(), response.body().getMessage());
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    StateRespose mError = new StateRespose();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, StateRespose.class);
                        Toast.makeText(getActivity(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    ArrayList<SalesEmployeeItem> assignToList_gl = new ArrayList<>();

    //todo calling assign to api here---
    private void callAssignToApi() {
        EmployeeValue employeeValue = new EmployeeValue();
        employeeValue.setSalesEmployeeCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
        Call<SaleEmployeeResponse> call = NewApiClient.getInstance().getApiService().getSalesEmplyeeList(employeeValue);
        call.enqueue(new Callback<SaleEmployeeResponse>() {
            @Override
            public void onResponse(Call<SaleEmployeeResponse> call, Response<SaleEmployeeResponse> response) {
                if (response.body().getStatus() == 200) {
                    assignToList_gl.clear();
                    if (response.body().getValue().size() > 0) {
                        assignToList_gl.addAll(response.body().getValue());
                        AssignToAdapter stateAdapter = new AssignToAdapter(getActivity(), R.layout.drop_down_textview, assignToList_gl);

                        //todo set state..
                        binding.acAssignTo.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
//                        billtoState = billStateList.get(0).getName();
//                        billtoStateCode = billStateList.get(0).getCode();
                    }

                } else if (response.body().getStatus() == 201) {
                    Globals.showMessage(getActivity(), response.body().getMessage());
                } else if (response.body().getStatus() == 500) {
                    Globals.showMessage(getActivity(), response.body().getMessage());
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    StateRespose mError = new StateRespose();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, StateRespose.class);
                        Toast.makeText(getActivity(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaleEmployeeResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void callleadTypeApi(String type) {

        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getLeadType();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {

                if (response.code() == 200) {
                    leadTypeData.clear();
                    leadTypeData.addAll(response.body().getData());
                    binding.leadTypeSpinner.setAdapter(new LeadTypeAdapter(requireContext(), leadTypeData));
                    binding.leadTypeSpinner.setSelection(Globals.getleadType(leadTypeData, type));
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
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
                //  loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
                // loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    ArrayList<LeadTypeData> sourceData = new ArrayList<>();

    private void callSourceApi() {


        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {

                if (response.body().getStatus() == 200) {
                    sourceData.clear();

                    sourceData.addAll(response.body().getData());
                    binding.sourceSpinner.setAdapter(new LeadTypeAdapter(getActivity(), sourceData));
//                    sourcetype = sourceData.get(0).getName();

                    callApi(id);

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
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
                binding.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void eventManager() {

        //todo set bill to item click of autocomplete state
        binding.acState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (stateList_gl.size() > 0) {
                    StateName = stateList_gl.get(position).getName();
                    StateCode = stateList_gl.get(position).getCode();

                    binding.acState.setText(stateList_gl.get(position).getName());
                }

            }
        });


        //todo set assign to item click of autocomplete
        binding.acAssignTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (assignToList_gl.size() > 0) {
                    AssignName = assignToList_gl.get(position).getFirstName();
                    AssignCode = assignToList_gl.get(position).getSalesEmployeeCode();

                    binding.acAssignTo.setText(assignToList_gl.get(position).getFirstName());
                }

            }
        });


        //todo bind product interest adapter with item click--
        ArrayAdapter<String> productInterestAdapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, productInterestList_gl);
        binding.acProductInterest.setAdapter(productInterestAdapter);


        binding.acProductInterest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (productInterestList_gl.size() > 0) {
                    binding.acProductInterest.setText(productInterestList_gl.get(position));
                    ProductInterestName = productInterestList_gl.get(position);

                    if (ProductInterestName == "Other") {
                        binding.productDetailLayout.setVisibility(View.VISIBLE);
                    } else {
                        binding.productDetailLayout.setVisibility(View.GONE);
                    }


                    //todo bind product interest adapter with item click--
                    ArrayAdapter<String> productInterestAdapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, productInterestList_gl);
                    binding.acProductInterest.setAdapter(productInterestAdapter);
                }
            }
        });


        binding.sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourcetype = sourceData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sourcetype = sourceData.get(0).getName();
            }
        });

        // Retrieve items from strings.xml

        String[] cateogoryList = getResources().getStringArray(R.array.category);

        selectedCategory = cateogoryList[0];

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cateogoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.selectCategorySpinner.setAdapter(adapter);

        binding.selectCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                selectedCategory = selectedItem;
                //  Toast.makeText(AddLead.this, "Selected Item: " + selectedItem + "\nPosition: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

                // Do nothing here if no item is selected
            }
        });


        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation(binding.personName, StateName, binding.contactNo, binding.location, binding.email, sourcetype, ProductInterestName, selectedCategory, status, binding.fullName)) {


                    UpdateLeadModel lv = new UpdateLeadModel();
                    lv.setId(id);
                    lv.setCompanyName(binding.companyname.getText().toString());
                    lv.setCustomerName(binding.fullName.getText().toString());
                    lv.setContactPerson(binding.personName.getText().toString());
                    lv.setPhoneNumber(binding.contactNo.getText().toString());
                    lv.setEmail(binding.email.getText().toString());
                    lv.setSource(sourcetype);
                    lv.setProductInterest(ProductInterestName);
                    lv.setProductDetail(binding.tvProductDetail.getText().toString());
                    if (!AssignCode.isEmpty()) {
                        lv.setAssignedTo(AssignCode);
                    }else {
                        lv.setAssignedTo(Prefs.getString(Globals.SalesEmployeeCode, ""));
                    }
                    lv.setNumOfEmployee("0");
                    lv.setTurnover(selectedCategory);
                    lv.setDesignation(binding.designation.getText().toString());
                    lv.setEmployeeId(String.valueOf(leadValues.get(0).getEmployeeId().getId()));
                    lv.setMessage(binding.comment.getText().toString());
                    lv.setDate(Globals.getTodaysDatervrsfrmt());
                    lv.setTimestamp(Globals.getTimestamp());
                    lv.setStatus(status);
                    lv.setLeadType(leadtype);
                    lv.setAttach("");
                    lv.setCaption("");
                    lv.setCountry("India");
                    lv.setState(StateName);
                    lv.setCaption("");
                    lv.setProjectAmount(binding.projectAMount.getText().toString());
                    lv.setLocation(binding.location.getText().toString());

                    if (Globals.checkInternet(getContext())) {
                        binding.update.setEnabled(false);
                        binding.loader.setVisibility(View.VISIBLE);
                        callUpdateApi(lv);
                    }
                }
            }
        });

       /* binding.contactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.contactNo.getText().toString().trim().isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + binding.contactNo.getText().toString()));


                    Dexter.withActivity(getActivity())
                            .withPermission(Manifest.permission.CALL_PHONE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    startActivity(intent);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {
                                    // check for permanent denial of permission
                                    if (response.isPermanentlyDenied()) {
                                        // navigate user to app settings

                                        Toast.makeText(getContext(), "Please Enable phone from setting", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();
                    // startActivity(intent);
                }
            }
        });*///todo comment by me--


        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status = parent.getSelectedItem().toString();
            }
        });

        binding.leadTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leadtype = leadTypeData.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                leadtype = leadTypeData.get(0).getName();
            }
        });


    }

    private boolean validation(EditText personName, String StateName, EditText contact_no, EditText location, EditText email, String sourcetype,
                               String productInterestName, String selectedCategory, String status, EditText fullName) {

        if (personName.getText().toString().isEmpty()) {
            personName.requestFocus();
            personName.setError("Enter Person Name");
            Toasty.warning(getActivity(), "Enter Person Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (fullName.getText().toString().isEmpty()) {
            fullName.requestFocus();
            fullName.setError("Enter Customer Name");
            Toasty.warning(getActivity(), "Enter Person Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (StateName.isEmpty()) {
            Toasty.warning(getActivity(), "Select State Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (contact_no.getText().toString().isEmpty()) {
            Toasty.warning(getActivity(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (contact_no.getText().toString().isEmpty() || contact_no.length() != 10) {
            contact_no.requestFocus();
            contact_no.setError("Enter Valid Contact No");
            Toasty.warning(getActivity(), "Enter Contact No", Toast.LENGTH_SHORT).show();
            return false;
        }else if (location.getText().toString().isEmpty()) {
            location.requestFocus();
            location.setError("Enter Address Required");
            Toasty.warning(getActivity(), "Enter Address is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.getText().toString().isEmpty()) {
            if (isvalidateemail()) {
                email.requestFocus();
                email.setError("This email address is not valid");
                return false;
            }
        } else if (sourcetype.isEmpty()) {
            Toasty.warning(getActivity(), "Select Source Type Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (productInterestName.isEmpty()) {
            Toasty.warning(getActivity(), "Product Interest is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedCategory.isEmpty()) {
            Toasty.warning(getActivity(), "Category is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (status.isEmpty()) {
            Toasty.warning(getActivity(), "Status is Required", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;

    }

    private boolean isvalidateemail() {
        String checkEmail = binding.email.getText().toString();
        boolean hasSpecialEmail = Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches();
        if (!hasSpecialEmail) {
            binding.email.setError("This email address is not valid");
            return true;
        }
        return false;
    }

    private void callUpdateApi(UpdateLeadModel lv) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(lv);
        Log.e("data", jsonTut);
        Call<GlobalResponse> call = NewApiClient.getInstance().getApiService().updateLead(lv);
        call.enqueue(new Callback<GlobalResponse>() {
            @Override
            public void onResponse(Call<GlobalResponse> call, Response<GlobalResponse> response) {

                if (response.body().getStatus() == 200) {
                    binding.update.setEnabled(true);
                    if (response.body().getMessage().equalsIgnoreCase("successful")) {
                        Toasty.success(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    } else {
                        Toasty.warning(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    binding.update.setEnabled(true);
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
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
                binding.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GlobalResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                binding.update.setEnabled(true);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callApi(int id) {
        LeadValue lv = new LeadValue();
        lv.setId(id);
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().particularlead(lv);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {
                if (response != null) {
                    if (response.body() != null) {

                        leadValues = response.body().getData();
                        setData(leadValues.get(0));

                    }


                }
            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {

            }
        });
    }

    private void setData(LeadValue lv) {

        leadtype = lv.getLeadType();
        status = lv.getStatus();
        binding.statusSpinner.setSelection(getStatuspos(lv.getStatus()));
        binding.companyname.setText(lv.getCompanyName());
        binding.personName.setText(lv.getContactPerson());
        binding.fullName.setText(lv.getCustomerName());
        binding.contactNo.setText(lv.getPhoneNumber());
        binding.email.setText(lv.getEmail());
        binding.location.setText(lv.getLocation());

        binding.acProductInterest.setText(lv.getProductInterest());
        //todo bind product interest adapter with item click--
        ArrayAdapter<String> productInterestAdapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, productInterestList_gl);
        binding.acProductInterest.setAdapter(productInterestAdapter);

        ProductInterestName = lv.getProductInterest();
        binding.designation.setText(lv.getDesignation());
        binding.comment.setText(lv.getMessage());

        if (ProductInterestName == "Other") {
            binding.productDetailLayout.setVisibility(View.VISIBLE);
            binding.tvProductDetail.setText(lv.getProductDetail());
        } else {
            binding.productDetailLayout.setVisibility(View.GONE);
        }

        binding.projectAMount.setText(lv.getProjectAmount());
        binding.location.setText(lv.getLocation());
        binding.acState.setText(lv.getState());
        StateName = lv.getState();
        binding.selectCategorySpinner.setSelection(getCateogory(lv.getTurnover()));
        selectedCategory = lv.getTurnover();
        binding.sourceSpinner.setSelection(getSourcePos(lv.getSource()));
        sourcetype = lv.getSource();

        binding.acAssignTo.setText(lv.getAssignedTo().getFirstName());
        if (!lv.getAssignedTo().getSalesEmployeeCode().isEmpty()) {
            AssignCode = lv.getAssignedTo().getSalesEmployeeCode();
        }
        binding.loader.setVisibility(View.GONE);
        callleadTypeApi(lv.getLeadType());

    }

    private int getStatuspos(String status) {
        int pos = -1;
        for (int i = 0; i < leadstatus.length; i++) {
            String data = leadstatus[i];
            if (data.equalsIgnoreCase(status)) {
                pos = i;
            }
        }
        return pos;
    }

    private int getSourcePos(String status) {
        int pos = -1;
        for (int i = 0; i < sourceData.size(); i++) {
            String data = sourceData.get(i).getName();
            if (data.equalsIgnoreCase(status)) {
                pos = i;
            }
        }
        return pos;
    }

    private int getCateogory(String status) {
        String[] cateogoryList = getResources().getStringArray(R.array.category);

        int pos = -1;
        for (int i = 0; i < cateogoryList.length; i++) {
            String data = cateogoryList[i];
            if (data.equalsIgnoreCase(status)) {
                pos = i;
            }
        }
        return pos;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_press:

                getActivity().onBackPressed();
                break;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }
}
