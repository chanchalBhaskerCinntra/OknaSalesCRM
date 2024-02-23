package com.cinntra.okana.viewModel;



import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cinntra.okana.model.Count;
import com.cinntra.okana.model.CounterResponse;
import com.cinntra.okana.webservices.APIsClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounterViewModel extends ViewModel
     {
    MutableLiveData<List<Count>> CounterList;
    public LiveData<List<Count>> getItemsList(String type, TextView counter)
     {
             //if the list is null
         if (CounterList == null) {
             CounterList = new MutableLiveData<List<Count>>();
                 //we will load it asynchronously from server in this method
             counter(type,counter);
             }

             //finally we will return the list
             return CounterList;
         }

   private void counter(String type,TextView counter)
      {
          Call<CounterResponse> call = null;
          if(type.equalsIgnoreCase("Quotations"))
          call = APIsClient.getInstance().getApiService().QuotationCount();
          else  if(type.equalsIgnoreCase("Opportunities"))
              call = APIsClient.getInstance().getApiService().OpportunityCount();
              else  if(type.equalsIgnoreCase("Orders"))
              call = APIsClient.getInstance().getApiService().OrdersCount();


        call.enqueue(new Callback<CounterResponse>() {
        @Override
        public void onResponse(Call<CounterResponse> call, Response<CounterResponse> response) {
         if (response != null)
              {
                 /* Log.e("Okh=>",""+response.body().getValue().get(0).getCount());
                  counter.setText(response.body().getValue().get(0).getCount());
*/

              }
            }
         @Override
         public void onFailure(Call<CounterResponse> call, Throwable t) {

             }
          });
         }


         /***************** Load Tax slabs *******************/






     }
