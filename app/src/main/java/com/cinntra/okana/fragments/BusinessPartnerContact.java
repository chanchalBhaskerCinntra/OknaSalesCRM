package com.cinntra.okana.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddContact;
import com.cinntra.okana.adapters.ContactAdapter;
import com.cinntra.okana.databinding.ContactViewBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.ContactPerson;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessPartnerContact extends Fragment {

//    @BindView(R.id.new_contact)
//    FloatingActionButton new_contact;
//    @BindView(R.id.swipeRefreshLayout)
//    PullRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
    BusinessPartnerData customerItem;
    ContactAdapter contactAdapter ;
    private ArrayList<ContactPersonData> ContactEmployeesList;
    public BusinessPartnerContact(BusinessPartnerData customerItem) {
        this.customerItem = customerItem;
    }

    ContactViewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ContactViewBinding.inflate(inflater,container,false);
        View v = inflater.inflate(R.layout.contact_view,container,false);
      //  ButterKnife.bind(this,v);
        if(Globals.checkInternet(getContext())) {
           binding.loader. loader.setVisibility(View.VISIBLE);
            callContactEmployeeApi(customerItem.getCardCode());

        }
        binding.newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putString(Globals.AddContactPerson,"BusinessPartner");
                Bundle bundle = new Bundle();
                bundle.putSerializable(Globals.AddContactPerson,customerItem);
                Intent intent = new Intent(getContext(), AddContact.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
      binding. swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if(Globals.checkInternet(getActivity())){
                        callContactEmployeeApi(customerItem.getCardCode());
                    }
                    else
                        binding.swipeRefreshLayout.setRefreshing(false);

                }
            });


        return binding.getRoot();
    }
    private void callContactEmployeeApi(String id) {
        ContactPersonData contactPersonData = new ContactPersonData();
        contactPersonData.setCardCode(id);

        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().contactemplist(contactPersonData);

        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {

                if(response.code()==200)
                {
                    binding.loader.loader.setVisibility(View.GONE);
                    if(response.body().getData().size()>0) {
                        binding.noDatafound.setVisibility(View.GONE);
                        ContactEmployeesList = new ArrayList<>();
                        ContactEmployeesList.clear();
                        ContactEmployeesList.addAll(response.body().getData());
                    }else{
                        binding.noDatafound.setVisibility(View.VISIBLE);

                    }
                    contactAdapter =new ContactAdapter(getActivity(),ContactEmployeesList);
                    binding.recyclerView.setAdapter(contactAdapter);
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
               binding. swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {
                binding.loader.loader.setVisibility(View.GONE);
                binding.swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
