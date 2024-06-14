package com.cinntra.okana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.AssignToAdapter;
import com.cinntra.okana.adapters.LeadTypeAdapter;
import com.cinntra.okana.adapters.StateAutoAdapter;
import com.cinntra.okana.databinding.CreateLeadFromBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.CreateLead;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.LeadTypeResponse;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import javax.microedition.khronos.opengles.GL;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddLead extends MainBaseActivity {
    public int ITEMSVIEWCODE = 10000;


    String status = "Follow up";
    String leadtype = "";
    String sourcetype = "";
    ArrayList<LeadTypeData> leadTypeData = new ArrayList<>();
    ArrayList<LeadTypeData> sourceData = new ArrayList<>();
    CreateLeadFromBinding binding;


    @Override
    protected void onResume() {
        super.onResume();
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreateLeadFromBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // ButterKnife.bind(this);
        binding.headerBottomRounded.headTitle.setText("Add Lead");
        Globals.SelectedItems.clear();
        callleadTypeApi();

        callStateAPi();
        callAssignToApi();

        callSourceApi();

        eventmanager();


        binding.itemFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.SelectedItems.size() == 0) {
                    /*Globals.ItemType = "Paid";
                    openCategorydailog();*/
                    Intent intent = new Intent(AddLead.this, ItemsList.class);
                    intent.putExtra("CategoryID", 0);
                    startActivityForResult(intent, ITEMSVIEWCODE);
                } else {
                    Intent intent = new Intent(AddLead.this, SelectedItems.class);
                    intent.putExtra("FromWhere", "addQt");
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
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
                        AssignToAdapter stateAdapter = new AssignToAdapter(AddLead.this, R.layout.drop_down_textview, assignToList_gl);

                        //todo set state..
                        binding.acAssignTo.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
//                        billtoState = billStateList.get(0).getName();
//                        billtoStateCode = billStateList.get(0).getCode();
                    }

                } else if (response.body().getStatus() == 201) {
                    Globals.showMessage(AddLead.this, response.body().getMessage());
                } else if (response.body().getStatus() == 500) {
                    Globals.showMessage(AddLead.this, response.body().getMessage());
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    StateRespose mError = new StateRespose();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, StateRespose.class);
                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaleEmployeeResponse> call, Throwable t) {
                Toast.makeText(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void callleadTypeApi() {

        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getLeadType();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {

                if(response.code()==200)
                {


                    leadTypeData.clear();
                    leadTypeData.addAll(response.body().getData());
                    binding.leadTypeSpinner.setAdapter(new LeadTypeAdapter(AddLead.this, leadTypeData));
                    leadtype = leadTypeData.get(0).getName();
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
              //  loader.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
               // loader.setVisibility(View.GONE);
                Toast.makeText(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



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
                        StateAutoAdapter stateAdapter = new StateAutoAdapter(AddLead.this, R.layout.drop_down_textview, stateList_gl);

                        //todo set state..
                        binding.acState.setAdapter(stateAdapter);
                        stateAdapter.notifyDataSetChanged();
//                        billtoState = billStateList.get(0).getName();
//                        billtoStateCode = billStateList.get(0).getCode();
                    }

                } else if (response.body().getStatus() == 201) {
                    Globals.showMessage(AddLead.this, response.body().getMessage());
                } else if (response.body().getStatus() == 500) {
                    Globals.showMessage(AddLead.this, response.body().getMessage());
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    StateRespose mError = new StateRespose();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, StateRespose.class);
                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void callSourceApi() {


        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {

                if (response.body().getStatus() == 200) {
                    sourceData.clear();

                    sourceData.addAll(response.body().getData());
                    binding.sourceSpinner.setAdapter(new LeadTypeAdapter(AddLead.this, sourceData));
                    sourcetype = sourceData.get(0).getName();

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
                binding.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

      /*  sourceData.clear();
        sourceData.addAll(MainActivity.leadSourceListFromLocal);
        binding.sourceSpinner.setAdapter(new LeadTypeAdapter(AddLead.this, sourceData));
        sourcetype = sourceData.get(0).getName();*///todo comment


    }

    String selectedCategory = "";

    String StateName = "";
    String StateCode = "";
    String AssignName = "";
    String AssignCode = "";
    String ProductInterestName = "";


    List<String> productInterestList_gl = Arrays.asList(Globals.productInterestList_gl);

    private void eventmanager() {

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
        ArrayAdapter<String> productInterestAdapter = new ArrayAdapter<>(AddLead.this, R.layout.drop_down_textview, productInterestList_gl);
        binding.acProductInterest.setAdapter(productInterestAdapter);


        binding.acProductInterest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (productInterestList_gl.size() > 0) {
                    binding.acProductInterest.setText(productInterestList_gl.get(position));
                    ProductInterestName = productInterestList_gl.get(position);

                    if (ProductInterestName == "Other"){
                        binding.productDetailLayout.setVisibility(View.VISIBLE);
                    }else {
                        binding.productDetailLayout.setVisibility(View.GONE);
                    }

                    ArrayAdapter<String> productInterestAdapter = new ArrayAdapter<>(AddLead.this, R.layout.drop_down_textview, productInterestList_gl);
                    binding.acProductInterest.setAdapter(productInterestAdapter);


                }
            }
        });

        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcreatelead();
            }
        });

        binding.headerBottomRounded.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
        String[] itemList = getResources().getStringArray(R.array.category);
        selectedCategory = itemList[0];

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
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

    }

    private void addcreatelead() {
        if (validation(binding.personName, StateName, binding.contactNo, binding.location, binding.email, sourcetype, ProductInterestName, selectedCategory, status, binding.companyname, binding.contactNo)) {


            CreateLead lv = new CreateLead();
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
            lv.setEmployeeId(Prefs.getString(Globals.SalesEmployeeCode, ""));
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


            if (Globals.checkInternet(AddLead.this)) {
                binding.loader.setVisibility(View.VISIBLE);
                binding.create.setEnabled(false);
                callcreateLeadApi(lv);
            }
        }


    }

    private void callcreateLeadApi(CreateLead lv) {
        Gson gson = new Gson();
        String jsonTut = gson.toJson(lv);
        Log.e("data", jsonTut);
        ArrayList<CreateLead> createLeads = new ArrayList<>();
        createLeads.add(lv);
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().createLead(createLeads);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {

                if (response.code() == 200) {
                    binding.create.setEnabled(true);
                    if (response.body().getMessage().equalsIgnoreCase("successful")) {

                        Globals.SelectedItems.clear();
                        Toasty.success(AddLead.this, "Add Successfully", Toast.LENGTH_LONG).show();

                        onBackPressed();
                    } else {
                        Toasty.warning(AddLead.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    binding.create.setEnabled(true);
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(AddLead.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
                binding.loader.setVisibility(View.GONE);
                binding.create.setEnabled(true);
            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                binding.create.setEnabled(true);
                Toasty.error(AddLead.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation(EditText personName, String StateName, EditText contact_no, EditText location, EditText email, String sourcetype,
                               String productInterestName, String selectedCategory, String status, EditText companyname, EditText contactNo) {

        if (personName.getText().toString().isEmpty()) {
            personName.requestFocus();
            personName.setError("Enter Person Name");
            Toasty.warning(this, "Enter Person Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (companyname.getText().toString().isEmpty()) {
            companyname.requestFocus();
            companyname.setError("Enter Company Name");
            Toasty.warning(this, "Enter Company Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (contactNo.getText().toString().isEmpty()) {
            contactNo.requestFocus();
            contactNo.setError("Enter Phone Number");
            Toasty.warning(this, "Select Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (location.getText().toString().equalsIgnoreCase("")) {
            location.requestFocus();
            location.setError("Enter Address Required");
            Toasty.warning(this, "Enter Address is Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (StateName.isEmpty()) {
            Toasty.warning(this, "Select State Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (status.isEmpty()) {
            Toasty.warning(this, "Status is Required", Toast.LENGTH_SHORT).show();
            return false;
        }else if (contact_no.getText().toString().isEmpty() || contact_no.length() != 10) {
            contact_no.requestFocus();
            contact_no.setError("Enter Valid Contact No");
            Toasty.warning(this, "Enter Contact No", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!email.getText().toString().isEmpty()) {
            if (isvalidateemail()) {
                email.requestFocus();
                email.setError("This email address is not valid");
                return false;
            }
        } else if (sourcetype.isEmpty()) {
            Toasty.warning(this, "Select Source Type Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (productInterestName.isEmpty()) {
            Toasty.warning(this, "Product Interest is Required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selectedCategory.isEmpty()) {
            Toasty.warning(this, "Category is Required", Toast.LENGTH_SHORT).show();
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

}
