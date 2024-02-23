package com.cinntra.okana.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.EditExpenseActivity;
import com.cinntra.okana.adapters.PreviousImageViewAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAdapter;
import com.cinntra.okana.databinding.AddExpenseBinding;
import com.cinntra.okana.databinding.ExpenseDetailLayoutBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.ExpenseDataModel;
import com.cinntra.okana.model.ExpenseResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.TokenExpireModel;
import com.cinntra.okana.model.expenseModels.ExpenseOneDataResponseModel;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseDetailFragment extends Fragment{

    ExpenseDataModel expenseDataModel;

    int salesEmployeeCode = 0;

    ExpenseDetailLayoutBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            expenseDataModel = (ExpenseDataModel) b.getSerializable(Globals.ExpenseData);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ExpenseDetailLayoutBinding.inflate(getLayoutInflater());
       /* View v = inflater.inflate(R.layout.add_expense, container, false);
        ButterKnife.bind(this, v);*/

        binding.headerLayout.headTitle.setText("Expense Detail");
        binding.headerLayout.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        binding.headerLayout.editExpense.setVisibility(View.VISIBLE);
        binding.headerLayout.editExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditExpenseActivity.class);
                intent.putExtra("id", expenseDataModel.getId());
                startActivity(intent);

            }
        });


        if (Globals.checkInternet(getContext())) {
            callOneDetailApi();
        }

        return binding.getRoot();
    }

    //todo call expense one api here..
    private void callOneDetailApi(){
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", expenseDataModel.getId());
        Call<ExpenseOneDataResponseModel> call = NewApiClient.getInstance().getApiService().getExpenseOneData(jsonObject);
        call.enqueue(new Callback<ExpenseOneDataResponseModel>() {
            @Override
            public void onResponse(Call<ExpenseOneDataResponseModel> call, Response<ExpenseOneDataResponseModel> response) {
                try {
                    if (response.code() == 200){
                        if (response.body().getStatus() == 200){
                            if (response.body().getData().size() > 0 && response.body().getData() != null){
                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                setDefaultData(response.body().getData().get(0));
                            }

                        }else {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else if (response.code() == 401){
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response.body().getStatus() == 301){
                            Gson gson = new GsonBuilder().create();
                            TokenExpireModel mError = new TokenExpireModel();
                            try {
                                String s = response.errorBody().string();
                                mError = gson.fromJson(s, TokenExpireModel.class);
                                Toast.makeText(getActivity(), mError.getDetail(), Toast.LENGTH_LONG).show();
//                                Globals.logoutScreen(getActivity());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }catch (Exception e){
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ExpenseOneDataResponseModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefaultData(ExpenseOneDataResponseModel.Data modelData) {
        binding.tvEmployeeName.setText(modelData.getCreatedBy().get(0).getFirstName());
        binding.tvTripName.setText(modelData.getTrip_name());
        binding.tvExpenseName.setText(modelData.getType_of_expense());
        binding.tvTotalAmount.setText(modelData.getTotalAmount());
        if (modelData.getEmployeeId().size() > 0){
            binding.tvTravelBy.setText(modelData.getEmployeeId().get(0).getFirstName());
        }

        if (modelData.getExpense_from().isEmpty()){
            binding.tvFromExpenseDate.setText("NA");
        }else {
            binding.tvFromExpenseDate.setText(modelData.getExpense_from());
        }

        if (modelData.getExpense_to().isEmpty()){
            binding.tvToExpenseDate.setText("NA");
        }else {
            binding.tvToExpenseDate.setText(modelData.getExpense_to());
        }

        if (modelData.getRemarks().isEmpty()){
            binding.tvRemarrks.setText("NA");
        }else {
            binding.tvRemarrks.setText(modelData.getRemarks());
        }

        PreviousImageViewAdapter adapter = new PreviousImageViewAdapter(getContext(), modelData.getAttach(), "");
        binding.prevattachment.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        binding.prevattachment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }


}
