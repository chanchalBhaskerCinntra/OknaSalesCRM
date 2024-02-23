package com.cinntra.okana.viewModel;

import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.webservices.NewApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewModel extends ViewModel
     {
    MutableLiveData<List<BusinessPartnerData>> businessPartners;
    public LiveData<List<BusinessPartnerData>> getCustomersList(ProgressBar loader)
         {
             //if the list is null
        // if (businessPartners == null)
         {
         businessPartners = new MutableLiveData<List<BusinessPartnerData>>();
                 //we will load it asynchronously from server in this method
             loadCustomers(loader);
             }

             //finally we will return the list
             return businessPartners;
         }

    private void loadCustomers(ProgressBar loader)
          {
        /*loader.setVisibility(View.VISIBLE);
        String url = Globals.GetCustomers+" &$skip="+Globals.SkipItem(pageNo);*/
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().getAllBusinessPartner();
        call.enqueue(new Callback<CustomerBusinessRes>() {
        @Override
        public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
            if(response.code()==200){
                loader.setVisibility(View.GONE);
                if (response.body().getData() != null )
                    businessPartners.setValue(response.body().getData());
            }
        }
         @Override
         public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
         loader.setVisibility(View.GONE);
             }
          });
         }





     }
