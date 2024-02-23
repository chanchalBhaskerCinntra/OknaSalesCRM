package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.adapters.BusinessPartnerDetail;
import com.cinntra.okana.databinding.FragmentBPDetailBinding;
import com.cinntra.okana.model.BPModel.BussinessPartnerDetailModel;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.BPModel.demoListModel;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BPDetailFragment extends Fragment {

    FragmentBPDetailBinding binding;
    demoListModel.Datum customerItem;
//    BusinessPartnerData customerItem;
    BussinessPartnerDetailModel.Datum dataModel_gl = new BussinessPartnerDetailModel.Datum();

    public BPDetailFragment(BusinessPartnerDetail businessPartnerDetail, demoListModel.Datum customerItem) {
        this.customerItem = customerItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBPDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("TAG", "onViewCreated: " + customerItem);
        callBpAddressAPI();

    }

    private void setDefaultData() {
        binding.tvComapnyName.setText(dataModel_gl.getCardName());
        if (dataModel_gl.getU_CONTOWNR().isEmpty()){
            binding.tvContactOwner.setText("NA");
        }else {
            binding.tvContactOwner.setText(dataModel_gl.getU_CONTOWNR());
        }
        if (dataModel_gl.getU_LEADNM().isEmpty()){
            binding.tvLead.setText("NA");
        }else {
            binding.tvLead.setText(dataModel_gl.getU_LEADNM());
        }

        if (dataModel_gl.getPhone1().isEmpty()){
            binding.tvMobileNO.setText("NA");
        }else {
            binding.tvMobileNO.setText(dataModel_gl.getPhone1());
        }

        if (dataModel_gl.getEmailAddress().isEmpty()){
            binding.tvEmail.setText("NA");
        }else {
            binding.tvEmail.setText(dataModel_gl.getEmailAddress());
        }

        if (dataModel_gl.getSalesPersonCode().size() > 0){
            if (dataModel_gl.getSalesPersonCode().get(0).getSalesEmployeeName().isEmpty()){
                binding.tvSalesEmployee.setText("NA");
            }else {
                binding.tvSalesEmployee.setText(dataModel_gl.getSalesPersonCode().get(0).getSalesEmployeeName());
            }
        }

        if (dataModel_gl.getWebsite().isEmpty()){
            binding.tvWebsite.setText("NA");
        }else {
            binding.tvWebsite.setText(dataModel_gl.getWebsite());
        }

        if (dataModel_gl.getU_PARENTACC().isEmpty()){
            binding.tvParrentAcc.setText("NA");
        }else {
            binding.tvParrentAcc.setText(dataModel_gl.getU_PARENTACC());
        }

        if (dataModel_gl.getU_CONTOWNR().isEmpty()){
            binding.tvContactPerson.setText("NA");
        }else {
            binding.tvContactPerson.setText(dataModel_gl.getU_CONTOWNR());
        }

        if (dataModel_gl.getU_INVNO().isEmpty()){
            binding.tvGST.setText("NA");
        }else {
            binding.tvGST.setText(dataModel_gl.getU_INVNO());
        }


        //todo set Address details---

        if (dataModel_gl.getBPAddresses().size() > 0){
            if (dataModel_gl.getBPAddresses().get(0).getStreet().isEmpty()){
                binding.tvBillingAddress.setText("NA");
            }else {
                binding.tvBillingAddress.setText(dataModel_gl.getBPAddresses().get(0).getStreet());
            }
            if (dataModel_gl.getBPAddresses().get(0).getZipCode().isEmpty()){
                binding.tvZipcode.setText("NA");
            }else {
                binding.tvZipcode.setText(dataModel_gl.getBPAddresses().get(0).getZipCode());
            }
            if (dataModel_gl.getBPAddresses().get(0).getU_COUNTRY().isEmpty()){
                binding.tvCOuntry.setText("NA");
            }else {
                binding.tvCOuntry.setText(dataModel_gl.getBPAddresses().get(0).getU_COUNTRY());
            }
            if (dataModel_gl.getBPAddresses().get(0).getU_STATE().isEmpty()){
                binding.tvState.setText("NA");
            }else {
                binding.tvState.setText(dataModel_gl.getBPAddresses().get(0).getU_STATE());
            }
            if (dataModel_gl.getBPAddresses().get(0).getCity().isEmpty()){
                binding.tvCity.setText("NA");
            }else {
                binding.tvCity.setText(dataModel_gl.getBPAddresses().get(0).getCity());
            }
            if (dataModel_gl.getBPAddresses().get(0).getU_SHPTYP().isEmpty()){
                binding.tvShippingType.setText("NA");
            }else {
                binding.tvShippingType.setText(dataModel_gl.getBPAddresses().get(0).getU_SHPTYP());
            }
            if (dataModel_gl.getBPAddresses().get(0).getAddressName().isEmpty()){
                binding.tvBillingName.setText("NA");
            }else {
                binding.tvBillingName.setText(dataModel_gl.getBPAddresses().get(0).getAddressName());
            }
        }

    }


    //todo calling bp api here...
    private void callBpAddressAPI() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", customerItem.getCardCode());
        Call<BussinessPartnerDetailModel> call = NewApiClient.getInstance().getApiService().businessPartnerDetailOne(jsonObject);
        call.enqueue(new Callback<BussinessPartnerDetailModel>() {
            @Override
            public void onResponse(Call<BussinessPartnerDetailModel> call, Response<BussinessPartnerDetailModel> response) {
                if (response.body().getStatus() == 200) {

                    dataModel_gl = response.body().getData().get(0);
                    setDefaultData();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BussinessPartnerDetailModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}