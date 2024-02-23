package com.cinntra.okana.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.BussinessPartners;
import com.cinntra.okana.activities.ItemsList;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.activities.OwnerList;
import com.cinntra.okana.activities.SelectedItems;
import com.cinntra.okana.adapters.BPTypeSpinnerAdapter;
import com.cinntra.okana.adapters.CategoryAdapter;
import com.cinntra.okana.adapters.ContactPersonAdapter;
import com.cinntra.okana.adapters.LeadTypeAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.adapters.bpAdapters.ContactPersonAutoAdapter;
import com.cinntra.okana.databinding.UpdateOpportunityBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.ChangeTeam;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllResponse;
import com.cinntra.okana.model.ContactPerson;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.ContactPersonResponseModel;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.ItemCategoryData;
import com.cinntra.okana.model.ItemCategoryResponse;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.LeadTypeResponse;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.OwnerItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.SalesOpportunitiesLines;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.newapimodel.AddOpportunityModel;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.newapimodel.OpportunityValue;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Opportunity_Update_Fragment extends Fragment implements View.OnClickListener, DatabaseClick {

    FragmentActivity act;
    int salesEmployeeCode = 0;
    String OppID = "";
    public int ITEMSVIEWCODE = 10000;

    public static int OWNERCODE = 1001;
    private static boolean ESCAPED = true;

    NewOpportunityRespose opportunityItem;
    String salesEmployeename = "";
    String ContactPersonName = "";
    String DataOwnershipfield = "";

    UpdateOpportunityBinding binding;

    public Opportunity_Update_Fragment() {
        //Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Opportunity_Update_Fragment newInstance(String param1, String param2) {
        Opportunity_Update_Fragment fragment = new Opportunity_Update_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            opportunityItem = (NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        act = getActivity();
        binding = UpdateOpportunityBinding.inflate(inflater, container, false);
        // View v=inflater.inflate(R.layout.update_opportunity, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding.headerBottomroundEdit.headTitle.setText(getString(R.string.update_opportunity));
        // getActivity().getActionBar().hide();
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
//        setDisable();
        Globals.SelectedItems.clear();
        setEnable();
        setDefaults();

        if (Globals.checkInternet(getActivity())){
            callOppOneApi(opportunityItem.getId());
        }


        eventManager();

        callSourceApi();


        binding.itemFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Globals.SelectedItems.size() == 0) {
//                    Globals.ItemType = "Paid";
                    openCategorydailog();
                  /*  Intent intent = new Intent(getContext(), ItemsList.class);
                    intent.putExtra("CategoryID", 0);
                    startActivityForResult(intent, ITEMSVIEWCODE);*/
                } else {
                    Intent intent = new Intent(getContext(), SelectedItems.class);
                    intent.putExtra("FromWhere", "addQt");
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
            }
        });

        return binding.getRoot();
    }


    //todo open dialog for category list..
    Dialog TaxListdialog;

    private void openCategorydailog() {
        RelativeLayout backPress;
        TextView head_title;
        RecyclerView recyclerview;
        ProgressBar loader;

        TaxListdialog = new Dialog(requireContext());
        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());
        View custom_dialog = layoutInflater.inflate(R.layout.taxes_alert, null);
        recyclerview = custom_dialog.findViewById(R.id.recyclerview);
        backPress = custom_dialog.findViewById(R.id.back_press);
        head_title = custom_dialog.findViewById(R.id.head_title);
        loader = custom_dialog.findViewById(R.id.loader);
        head_title.setText(getResources().getString(R.string.select_tax));
        TaxListdialog.setContentView(custom_dialog);
        TaxListdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TaxListdialog.show();

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxListdialog.dismiss();
            }
        });


        loader.setVisibility(View.VISIBLE);
        Call<ItemCategoryResponse> call = NewApiClient.getInstance().getApiService().getAllCategory();//getAllCategoryList
        call.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            loader.setVisibility(View.GONE);
                            if (response.body().getData().size() > 0 && response.body().getData() != null) {
                                List<ItemCategoryData> itemsList = response.body().getData();

                                CategoryAdapter adapter = new CategoryAdapter(Opportunity_Update_Fragment.this, itemsList, TaxListdialog);

                                recyclerview.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
                                recyclerview.setAdapter(adapter);
                            } else {
                                Globals.setmessage(requireContext());
                            }

                        }
                    } else {
                        loader.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(requireContext(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    List<NewOpportunityRespose> particularoppdata = new ArrayList<>();


    private void callOppOneApi(String sequentialNo) {
        OpportunityValue opportunityValue = new OpportunityValue();
        opportunityValue.setId(Integer.valueOf(sequentialNo));
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().getparticularopportunity(opportunityValue);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {

                if(response.body()!=null){
                    particularoppdata.clear();
                    particularoppdata.add(response.body().getData().get(0));

                    callUTypeApi();
                }
            }
            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }


    private void setDisable() {
        binding.headerBottomroundEdit.add.setOnClickListener(this);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.opportunityNameValue.getApplicationWindowToken(), 0);

        binding.opportunityNameValue.setFocusable(false);
        binding.opportunityNameValue.setClickable(false);

        binding.acContactPerson.setEnabled(false);

        binding.opportunityOwnerValue.setClickable(false);
        binding.opportunityOwnerValue.setFocusable(false);

        binding.acSalesEmployee.setEnabled(false);
        binding.acSalesEmployee.setClickable(false);

        binding.acProbability.setEnabled(false);
        binding.acProbability.setClickable(false);
        binding.acProbability.setFocusableInTouchMode(false);

        binding.potentialAmountValue.setFocusable(false);
        binding.potentialAmountValue.setClickable(false);


        binding.descriptionValue.setClickable(false);
        binding.descriptionValue.setFocusable(false);


        binding.submitButton.setVisibility(View.GONE);
        binding.submitButton.setEnabled(false);
        binding.submitButton.setClickable(false);
        binding.submitButton.setFocusable(false);

        binding.headerBottomroundEdit.add.setVisibility(View.VISIBLE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);
        ESCAPED = true;
    }

    String salesPersonCode = "";
    String ContactPersonCode = "";
    String CurrentStage = "";
    String Ownershipfield = "";
    String LEAD_SOURCE = "";
    String CardName = "";
    String CardCode = "";

    private void setData(NewOpportunityRespose opportunityItem) {

        CardCode = opportunityItem.getCardCode();
        CardName = opportunityItem.getCustomerName();

        binding.leadValue.setText(opportunityItem.getU_LEADNM());
        salesEmployeename = opportunityItem.getSalesPersonName();
        ContactPersonName = opportunityItem.getContactPersonName();
        salesPersonCode = opportunityItem.getSalesPerson();
        ContactPersonCode = opportunityItem.getContactPerson();
        CurrentStage = opportunityItem.getCurrentStageNo();
        Ownershipfield = opportunityItem.getDataOwnershipfield();
        OppID = opportunityItem.getSequentialNo();
        binding.opportunityNameValue.setText(opportunityItem.getOpportunityName());
        binding.businessPartnerValue.setText(opportunityItem.getCustomerName());

        LEAD_SOURCE = opportunityItem.getULsource();
        binding.leadSourceSpinner.setSelection(Globals.getLeadSourcePos(sourceData, opportunityItem.getULsource()));

        binding.closeDateValue.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(opportunityItem.getPredictedClosingDate()));
        binding.descriptionValue.setText(opportunityItem.getRemarks());

        if (opportunityItem.getUType().size() > 0)
             TYPE = String.valueOf(opportunityItem.getUType().get(0).getId());


        binding.acSalesEmployee.setText(opportunityItem.getSalesPersonName());
        salesEmployeeCode = Integer.parseInt(opportunityItem.getSalesPerson());
        salesEmployeename = opportunityItem.getSalesPersonName();


        binding.acProbability.setText(opportunityItem.getUProblty());


        ArrayAdapter<String> probailityAdapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, probability_list);
        binding.acProbability.setAdapter(probailityAdapter);

        binding.potentialAmountValue.setText(opportunityItem.getMaxLocalTotal());
        binding.opportunityOwnerValue.setText(opportunityItem.getDataOwnershipName());
        salesEmployeeCode = Integer.parseInt(opportunityItem.getSalesPerson());

        //todo set document lines..
        Globals.SelectedItems.addAll(opportunityItem.getOppItem());

        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

        String totalBefroe = String.valueOf(Globals.calculateTotalOfItem(Globals.SelectedItems));

        binding.totalBeforeDiscontValue.setText(String.valueOf(Globals.foo(Double.parseDouble(totalBefroe))));


        if (!opportunityItem.getContactPersonName().isEmpty()) {
            binding.acContactPerson.setText(opportunityItem.getContactPersonName());
            ContactPersonCode = opportunityItem.getContactPerson();
            ContactPerson = opportunityItem.getContactPersonName();
        }
        callContactApi(CardCode);


    }


    ArrayList<LeadTypeData> sourceData = new ArrayList<>();

    //tod calling source type api ---
    private void callSourceApi() {
        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {

                if (response.body().getStatus() == 200) {

                    sourceData.clear();
                    sourceData.addAll(response.body().getData());
//                    sourceData.addAll(MainActivity.leadSourceListFromLocal);
                    binding.leadSourceSpinner.setAdapter(new LeadTypeAdapter(getActivity(), sourceData));


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

            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
//                binding.loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    List<UTypeData> utypelist = new ArrayList<>();

    private void callUTypeApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getOPpTypeList().observe(this, new Observer<List<UTypeData>>() {
            @Override
            public void onChanged(@Nullable List<UTypeData> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(act);
                } else {
                    utypelist = itemsList;
                    binding.typeSpinner.setAdapter(new BPTypeSpinnerAdapter(act, itemsList));

                    binding.typeSpinner.setSelection(Globals.getOppType(utypelist, particularoppdata.get(0).getUType().get(0).getType()));
                    TYPE = String.valueOf(particularoppdata.get(0).getUType().get(0).getId());

                    callSalessApi("");
                }
            }
        });
    }

    private void setDefaults() {
        binding.closeCalender.setOnClickListener(this);
        binding.headerBottomroundEdit.ok.setOnClickListener(this);
        binding.closeDate.setOnClickListener(this);
        binding.closeDateValue.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);
        binding.opportunityOwnerValue.setOnClickListener(this);
        binding.businessPartnerValue.setOnClickListener(this);
    }

    ArrayList<SalesOpportunitiesLines> jsonlist = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:

                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.opportunityNameValue.getApplicationWindowToken(), 0);
                getActivity().finish();

                break;
            case R.id.add:
                if (ESCAPED) {
                    Globals.openKeyboard(getContext());
                    setEnable();
                    setDefaults();
                } else {
                    setDisable();
                }
                break;

            case R.id.close_date:
            case R.id.closeDate:
            case R.id.close_date_value:
                Globals.selectDate(act, binding.closeDateValue);
                break;

            case R.id.opportunity_owner_value:
                Intent ii = new Intent(getActivity(), OwnerList.class);
                startActivityForResult(ii, OWNERCODE);
                break;
            case R.id.bussinessPartner:
            case R.id.business_partner_value:
                selectBPartner();
                break;
            case R.id.submit_button:
            case R.id.ok:
//                setDisable();

                getQuotationDocLin();

                String cardValue = binding.businessPartnerValue.getText().toString().trim();
                String remark = binding.descriptionValue.getText().toString().trim();
                String inputString = binding.acProbability.getText().toString();
                String stringWithoutPercent = inputString.replace("%", "");

                int valueOfProbability = Integer.parseInt(stringWithoutPercent);
                System.out.println("Value at 0 index: " + valueOfProbability);

                if (validation(cardValue, salesEmployeeCode,  TYPE, LEAD_SOURCE, ContactPerson, binding.closeDateValue)) {
                    SalesOpportunitiesLines dc = new SalesOpportunitiesLines();
                    dc.setSalesPerson(salesEmployeeCode);
                    dc.setDocumentType("bodt_MinusOne");
                    dc.setMaxSystemTotal(Float.valueOf(binding.potentialAmountValue.getText().toString().trim()));
                    dc.setMaxLocalTotal(Float.valueOf(binding.potentialAmountValue.getText().toString().trim()));
                    dc.setStageKey(CurrentStage);
                    jsonlist.add(dc);

                    AddOpportunityModel obj = new AddOpportunityModel();
                    obj.setMaxSystemTotal(binding.potentialAmountValue.getText().toString().trim());
                    obj.setOpportunityName(binding.opportunityNameValue.getText().toString().trim());
                    obj.setClosingDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.closeDateValue.getText().toString().trim()));
                    obj.setStartDate(opportunityItem.getStartDate());
                    obj.setDataOwnershipfield(opportunityItem.getDataOwnershipfield());
                    obj.setUProblty(String.valueOf(valueOfProbability));
                    obj.setCardCode(CardCode); //cardcode
                    obj.setSalesPerson(String.valueOf(salesEmployeeCode)); //salesEmployeeCode
                    obj.setContactPerson(ContactPersonCode);
                    obj.setMaxLocalTotal(binding.potentialAmountValue.getText().toString().trim());//Potential Ammount
                    obj.setRemarks(remark);
                    obj.setId(opportunityItem.getId());
                    obj.setSequentialNo(opportunityItem.getSequentialNo());
                    obj.setSource("None");
                    obj.setStatus("sos_Open");
                    obj.setReasonForClosing("None");
                    obj.setTotalAmounSystem(opportunityItem.getTotalAmounSystem());
                    obj.setTotalAmountLocal(opportunityItem.getTotalAmountLocal());
                    obj.setCurrentStageNo(opportunityItem.getCurrentStageNo());
                    obj.setIndustry("None");
                    obj.setLinkedDocumentType("None");
                    obj.setStatusRemarks("None");
                    obj.setProjectCode("None");
                    obj.setCustomerName(opportunityItem.getCustomerName());
                    obj.setClosingType("sos_Days");
                    obj.setOpportunityType("boOpSales");
                    obj.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                    obj.setUpdateTime(Globals.getTCurrentTime());
                    obj.setUType(TYPE);
                    obj.setULsource(LEAD_SOURCE);
                    obj.setUFav(opportunityItem.getUFav());
                    obj.setSalesPersonName(salesEmployeename);
                    obj.setContactPersonName(ContactPersonName);
                    obj.setDataOwnershipName(salesEmployeename);
                    obj.setPredictedClosingDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(binding.closeDateValue.getText().toString().trim()));
                    obj.setU_LEADID(opportunityItem.getU_LEADID());
                    obj.setU_LEADNM(binding.leadValue.getText().toString());
                    obj.setCurrentStageName(binding.leadValue.getText().toString());
                    obj.setCurrentStageNumber(binding.leadValue.getText().toString());


                    obj.setSalesOpportunitiesLines(jsonlist);

                    //todo opp items adding...
                    ArrayList<DocumentLines> oppItemsArrayList = new ArrayList<>();
                    if (oppItemTempList_gl.size() > 0) {
                        for (int j = 0; j < oppItemTempList_gl.size(); j++) {
                            DocumentLines oppItems = new DocumentLines();
                            if (!oppItemTempList_gl.get(j).getId().equalsIgnoreCase("") && oppItemTempList_gl.get(j).getId() != null){
                                oppItems.setId(oppItemTempList_gl.get(j).getId());
                                oppItems.setOid(oppItemTempList_gl.get(j).getId());
                            }
                            oppItems.setCostingCode2(oppItemTempList_gl.get(j).getCostingCode2());
                            oppItems.setQuantity(oppItemTempList_gl.get(j).getQuantity());
                            oppItems.setDiscountPercent(oppItemTempList_gl.get(j).getDiscountPercent());
                            oppItems.setUnitPrice(oppItemTempList_gl.get(j).getUnitPrice());
                            oppItems.setItemCode(oppItemTempList_gl.get(j).getItemCode());
                            oppItems.setItemDescription(oppItemTempList_gl.get(j).getItemDescription());
                            oppItems.setTaxCode(oppItemTempList_gl.get(j).getTaxCode());
                            oppItems.setProjectCode(oppItemTempList_gl.get(j).getProjectCode());
                            oppItems.setU_FGITEM(oppItemTempList_gl.get(j).getU_FGITEM());
                            oppItems.setFreeText(oppItemTempList_gl.get(j).getFreeText());
                            oppItems.setTax(oppItemTempList_gl.get(j).getTax());//oppItemTempList_gl.get(j).getTax()
                            oppItems.setUomNo("NOS");

                            oppItemsArrayList.addAll(Collections.singleton(oppItems));

                        }
                    }
                    else {
                        for (int j = 0; j < opportunityItem.getOppItem().size(); j++) {
                            DocumentLines oppItems = new DocumentLines();
                            oppItems.setCostingCode2("");
                            oppItems.setId(opportunityItem.getOppItem().get(j).getId());
                            oppItems.setOid(opportunityItem.getOppItem().get(j).getId());
                            oppItems.setQuantity(opportunityItem.getOppItem().get(j).getQuantity());
                            oppItems.setDiscountPercent(opportunityItem.getOppItem().get(j).getDiscountPercent());
                            oppItems.setUnitPrice(opportunityItem.getOppItem().get(j).getUnitPrice());
                            oppItems.setItemCode(opportunityItem.getOppItem().get(j).getItemCode());
                            oppItems.setItemDescription(opportunityItem.getOppItem().get(j).getItemDescription());
                            oppItems.setTaxCode(opportunityItem.getOppItem().get(j).getTaxCode());
                            oppItems.setU_FGITEM(opportunityItem.getOppItem().get(j).getU_FGITEM());
                            oppItems.setFreeText(opportunityItem.getOppItem().get(j).getFreeText());
                            oppItems.setTax(opportunityItem.getOppItem().get(j).getTax());//
                            oppItems.setUomNo(opportunityItem.getOppItem().get(j).getUomNo());

                            oppItemsArrayList.addAll(Collections.singleton(oppItems));

                        }
                    }


                    obj.setOppItem(oppItemsArrayList);
//                    obj.setOppItem(Globals.SelectedItems);

                    if (Globals.checkInternet(getActivity())) {
                        updateOpportunity(obj);
                    }


                }
                break;
        }


    }



    //todo getting opp items list from items list screen..
    ArrayList<DocumentLines> oppItemTempList_gl = new ArrayList<>();

    public ArrayList<DocumentLines> getQuotationDocLin() {
        oppItemTempList_gl.clear();
        for (DocumentLines dcc : Globals.SelectedItems) {
            DocumentLines dc = new DocumentLines();
            dc.setId(dcc.getId());
            dc.setCostingCode2(dcc.getCostingCode2());
            dc.setItemCode(dcc.getItemCode());
            dc.setQuantity(dcc.getQuantity());
            dc.setTaxCode(dcc.getTaxCode());//BED+VAT
            dc.setUnitPrice(dcc.getUnitPrice());
            dc.setItemDescription(dcc.getItemDescription());
            dc.setItemName(dcc.getItemName());
            dc.setDiscountPercent(dcc.getDiscountPercent());
            dc.setTax(dcc.getTax());
            dc.setUomNo("NOS");
            dc.setCostingCode2(dcc.getCostingCode2());
            dc.setU_FGITEM(dcc.getU_FGITEM());
            dc.setProjectCode(dcc.getProjectCode());
            dc.setFreeText(dcc.getFreeText());

            oppItemTempList_gl.add(dc);
        }
        return oppItemTempList_gl;
    }

    private void setEnable() {


        binding.opportunityNameValue.setFocusable(true);
        binding.opportunityNameValue.setClickable(true);
        binding.opportunityNameValue.setFocusableInTouchMode(true);

        binding.acContactPerson.setEnabled(true);

        binding.opportunityOwnerValue.setClickable(true);
        binding.opportunityOwnerValue.setFocusable(true);

        binding.acSalesEmployee.setEnabled(true);

        binding.acProbability.setFocusable(true);
        binding.acProbability.setClickable(true);
        binding.acProbability.setFocusableInTouchMode(true);

        binding.potentialAmountValue.setFocusable(true);
        binding.potentialAmountValue.setClickable(true);
        binding.potentialAmountValue.setFocusableInTouchMode(true);


        binding.descriptionValue.setClickable(true);
        binding.descriptionValue.setFocusable(true);
        binding.descriptionValue.setFocusableInTouchMode(true);


        binding.submitButton.setVisibility(View.VISIBLE);
        binding.submitButton.setEnabled(true);
        binding.submitButton.setClickable(true);
        binding.submitButton.setFocusable(true);
        binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);

        ESCAPED = false;
    }


    public static int PARTNERCODE = 100;

    //todo select BP --
    private void selectBPartner() {
        Prefs.putString(Globals.BussinessPageType, "UpdateOpportunity");
        Intent i = new Intent(requireContext(), BussinessPartners.class);
        startActivityForResult(i, PARTNERCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PARTNERCODE && resultCode == RESULT_OK) {
            BusinessPartnerAllResponse.Datum customerItem = (BusinessPartnerAllResponse.Datum) data.getSerializableExtra(Globals.CustomerItemData);
            callBPOneAPi(customerItem.getCardCode(), "OtherBPSelect");

        }
        else if (requestCode == OWNERCODE && resultCode == RESULT_OK) {
            OwnerItem ownerItem = (OwnerItem) data.getSerializableExtra(Globals.OwnerItemData);
            binding.opportunityOwnerValue.setText(ownerItem.getFirstName() + " " + ownerItem.getMiddleName() + " " + ownerItem.getLastName());
            DataOwnershipfield = ownerItem.getEmployeeID();
        }
        else if (resultCode == RESULT_OK && requestCode == ITEMSVIEWCODE) {
            binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");
            String totalBefroe = String.valueOf(Globals.calculateTotalOfItem(Globals.SelectedItems));
            binding.totalBeforeDiscontValue.setText(String.valueOf(Globals.foo(Double.parseDouble(totalBefroe))));
            getQuotationDocLin();
        }


    }


    List<String> probability_list = Arrays.asList(Globals.probability_list);
    String ContactPerson = "";
    String TYPE = "";

    private void eventManager() {

        //todo bind probability adapter---
        ArrayAdapter<String> probailityAdapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, probability_list);
        binding.acProbability.setAdapter(probailityAdapter);


        binding.acProbability.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.acProbability.setText(probability_list.get(position));

                ArrayAdapter<String> probailityAdapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, probability_list);
                binding.acProbability.setAdapter(probailityAdapter);
            }
        });


        binding.acSalesEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (salesEmployeeItemList.size() > 0 && position > 0) {
                    salesEmployeeCode = Integer.parseInt(salesEmployeeItemList.get(position).getSalesEmployeeCode());
                    salesEmployeename = salesEmployeeItemList.get(position).getSalesEmployeeName();

                    binding.acSalesEmployee.setText(salesEmployeeItemList.get(position).getSalesEmployeeName());
                } else {
                    salesEmployeeCode = 0;
                    salesEmployeename = "";

                    binding.acSalesEmployee.setText("");
                }
            }
        });


        //todo item select of contact person..
        binding.acContactPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactPersonData partnerData = ContactEmployeesList.get(position);
                if (partnerData != null) {
                    ContactPerson = partnerData.getFirstName();
                    ContactPersonCode = partnerData.getInternalCode();
                    binding.acContactPerson.setText(partnerData.getFirstName());
                }
            }
        });


        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (utypelist.size() > 0)
                    TYPE = utypelist.get(position).getId().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }


    BusinessPartnerAllResponse.Datum customerItem = null;

    //todo calling bp one api here...
    private void callBPOneAPi(String BPCardCode, String otherBPSelect) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", BPCardCode);
        Call<BusinessPartnerAllResponse> call = NewApiClient.getInstance().getApiService().callBPOneAPi(jsonObject);
        call.enqueue(new Callback<BusinessPartnerAllResponse>() {
            @Override
            public void onResponse(Call<BusinessPartnerAllResponse> call, Response<BusinessPartnerAllResponse> response) {
                if (response.body().getStatus() == 200) {
                    customerItem = response.body().getData().get(0);

                    binding.businessPartnerValue.setText(customerItem.getCardName());

                    callSalessApi(otherBPSelect);

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BusinessPartnerAllResponse> call, Throwable t) {
                Log.e("ApiFailure==>", "onFailure: "+t.getMessage() );
            }
        });

    }


    private void setBPData(BusinessPartnerAllResponse.Datum customerItem) {

        if (Globals.checkInternet(getActivity()))
            callContactPersonOneApi(customerItem.getCardCode());


        Prefs.putString(Globals.BusinessPartnerCardCode, "");

        CardName = customerItem.getCardName();
        CardCode = customerItem.getCardCode();

        Prefs.putString(Globals.BusinessPartnerCardCode, customerItem.getCardCode());

        binding.businessPartnerValue.setText(customerItem.getCardName());

        //todo bind adapter list--

        if (customerItem.getContactEmployees().size() > 0){
            ContactPersonCode = customerItem.getContactEmployees().get(0).getInternalCode();
            ContactPerson = customerItem.getContactEmployees().get(0).getFirstName();
            binding.acContactPerson.setText(customerItem.getContactEmployees().get(0).getFirstName());

            callContactEmployeeApi(CardCode);

        }else {
            binding.acContactPerson.setText("");
        }


        //todo comment by me---

        binding.acSalesEmployee.setText(customerItem.getSalesPersonCode().get(0).getSalesEmployeeName());
        salesEmployeeCode = Integer.parseInt(customerItem.getSalesPersonCode().get(0).getSalesEmployeeCode());
        salesEmployeename = customerItem.getSalesPersonCode().get(0).getSalesEmployeeName();


    }


    ArrayList<ContactPersonResponseModel.Datum> contactPersonListgl = new ArrayList<>();
    //todo calling contact person api..
    private void callContactPersonOneApi(String cardCode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", cardCode);
        Call<ContactPersonResponseModel> call = NewApiClient.getInstance().getApiService().getContactPerson(jsonObject);
        call.enqueue(new Callback<ContactPersonResponseModel>() {
            @Override
            public void onResponse(Call<ContactPersonResponseModel> call, Response<ContactPersonResponseModel> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        contactPersonListgl.clear();
                        contactPersonListgl.addAll(response.body().getData());
                        ContactPerson = contactPersonListgl.get(0).getContactPerson();
//                        ContactPersonAdapter leadTypeAdapter = new ContactPersonAdapter(requireContext(), R.layout.drop_down_textview, contactPersonListgl);
//                        binding.acContactPerson.setAdapter(leadTypeAdapter);
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(requireContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactPersonResponseModel> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    SalesEmployeeAdapter salesadapter;
    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi(String otherBPSelect) {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(requireActivity(), new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getActivity());
                } else {

                    salesEmployeeItemList = itemsList;
                    SalesEmployeeAutoAdapter adapter = new SalesEmployeeAutoAdapter(getActivity(), R.layout.drop_down_textview, itemsList);
                    binding.acSalesEmployee.setAdapter(adapter);

                    if (particularoppdata != null && otherBPSelect == ""){
                        setData(particularoppdata.get(0));
                    }else {
                        setBPData(customerItem);
                    }

                }
            }
        });
    }

    private ArrayList<ContactPersonData> ContactEmployeesList = new ArrayList<>();

    private void callContactApi(String cardCode) {

        ContactPersonData businessPartnerData = new ContactPersonData();
        businessPartnerData.setCardCode(cardCode);
        Call<com.cinntra.okana.model.ContactPerson> call = NewApiClient.getInstance().getApiService().ContactEmployeesList(businessPartnerData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {

                if(response.code()==200) {
                    if(response.body().getData().size()>0) {
                        ContactEmployeesList = new ArrayList<>();
                        ContactEmployeesList.clear();
                        ContactEmployeesList.addAll(response.body().getData());
                        ContactPersonAutoAdapter leadTypeAdapter = new ContactPersonAutoAdapter(getActivity(), R.layout.drop_down_textview, ContactEmployeesList);
                        binding.acContactPerson.setAdapter(leadTypeAdapter);

                    }else{
                        Toasty.error(getActivity(), response.body().getMessage());
                    }
                }
                else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(getActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void callContactEmployeeApi(String cardCode) {
        ContactPersonData contactPersonData = new ContactPersonData();
        contactPersonData.setCardCode(cardCode);

        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().contactemplist(contactPersonData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {
                if(response.code()==200) {
                    if(response.body().getData().size()>0) {
                        ContactEmployeesList = new ArrayList<>();
                        ContactEmployeesList.clear();
                        ContactEmployeesList.addAll(response.body().getData());
                        ContactPersonAutoAdapter leadTypeAdapter = new ContactPersonAutoAdapter(getActivity(), R.layout.drop_down_textview, ContactEmployeesList);
                        binding.acContactPerson.setAdapter(leadTypeAdapter);

                    }else{
                        Toasty.error(getActivity(), response.body().getMessage());
                    }
                }
                else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(getActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void callOwenerApi() {

        if (MainActivity.ownerListFromLocal == null || MainActivity.ownerListFromLocal.size() == 0) {
            Globals.setmessage(getActivity());
        } else {
            Globals.OwnerList.clear();
            Globals.OwnerList.addAll(MainActivity.ownerListFromLocal);

            if (Ownershipfield != null) {
                int pos = Globals.getOwenerPo(Globals.OwnerList, Ownershipfield);
                binding.opportunityOwnerValue.setText(MainActivity.ownerListFromLocal.get(pos).getFirstName() + " " + MainActivity.ownerListFromLocal.get(pos).getLastName());
                DataOwnershipfield = Globals.OwnerList.get(pos).getEmployeeID();
            }


        }


    }

    @Override
    public void onResume() {
        super.onResume();
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

    }

    private void updateOpportunity(AddOpportunityModel in) {
        binding.submitButton.setEnabled(false);
        Gson gson = new Gson();
        String jsonTut = gson.toJson(in);
        Log.e("data", jsonTut);
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().newUpdateOpportunity(in);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                if (response.code() == 200) {
                    binding.submitButton.setEnabled(true);
                    Globals.SelectedItems.clear();
                    Toast.makeText(getActivity(), "Posted Successfully.", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    binding.submitButton.setEnabled(true);
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(getActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                binding.submitButton.setEnabled(true);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String cardCode, int salesEmployeeCode, String potentialAmount, String remark, String contactPerson, EditText closeDateValue) {
        if (Globals.SelectedItems.size() == 0){
            Globals.showMessage(act, "Select Atleast One Item");
            return false;
        }
        else if (cardCode.isEmpty()) {
            Globals.showMessage(act, getString(R.string.select_bp));
            return false;
        } else if (ContactPersonCode.equalsIgnoreCase("-1")) {
            Globals.showMessage(act, getString(R.string.enter_cp));
            return false;
        }
       /* else if (potentialAmount.isEmpty()) {
            Globals.showMessage(act, getString(R.string.potential_amnt_error));
            return false;
        }*/ else if (binding.opportunityNameValue.getText().toString().trim().length() == 0) {
            binding.opportunityNameValue.requestFocus();
            binding.opportunityNameValue.setError(getString(R.string.enter_opp));
            Globals.showMessage(act, getString(R.string.enter_opp));
            return false;
        } else if (binding.closeDateValue.getText().toString().trim().length() == 0) {
            Globals.showMessage(act, "Enter closing date");
            return false;
        } else if (TYPE.equalsIgnoreCase("-None-")) {
            Globals.showMessage(act, getString(R.string.enter_tye));
            return false;
        } else if (LEAD_SOURCE.equalsIgnoreCase("-None-")) {
            Globals.showMessage(act, getString(R.string.enter_lead_source));
            return false;
        } else if (salesEmployeeCode == 0) {
            Globals.showMessage(act, getString(R.string.enter_sp));
            return false;
        } /*else if (remark.isEmpty()) {
            binding.descriptionValue.requestFocus();
            binding.descriptionValue.setError(getString(R.string.remark_error));
            Globals.showMessage(act, getString(R.string.remark_error));
            return false;
        }*/

        return true;
    }


 /*   @Override
    public void changeTeam(String name) {
        Intent intent = new Intent(requireContext(), ItemsList.class);
        intent.putExtra("ItemGroupCode", name);
        startActivityForResult(intent, ITEMSVIEWCODE);
    }
*/

    @Override
    public void onClick(int po) {
        Intent intent = new Intent(requireContext(), ItemsList.class);
        intent.putExtra("CategoryID", po);
        startActivityForResult(intent, ITEMSVIEWCODE);
    }


}