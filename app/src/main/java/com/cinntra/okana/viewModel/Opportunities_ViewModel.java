package com.cinntra.okana.viewModel;


import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.OpportunityModels.OpportunitiesResponse;
import com.cinntra.okana.model.OpportunityModels.OpportunityItem;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.newapimodel.OpportunityValue;
import com.cinntra.okana.webservices.APIsClient;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Opportunities_ViewModel extends ViewModel {

    MutableLiveData<List<OpportunityItem>> opoorunityAllList;



    //we will call this method to get the data
    public LiveData<List<OpportunityItem>> getOpprtunities(ProgressBar loader,String type,int page) {
        //if the list is null

        opoorunityAllList = new MutableLiveData<List<OpportunityItem>>();
        //we will load it asynchronously from server in this method
        loadOpportunies(loader,type,page);


        //finally we will return the list
        return opoorunityAllList;
    }


    private void loadOpportunies(ProgressBar loader,String type, int page)
    {
        loader.setVisibility(View.VISIBLE);

        Call<OpportunitiesResponse> call = null;
        if(type.equalsIgnoreCase("Open")){
            String url = Globals.GetOpportunityOpen+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesOpenList(url);
        }
        else if(type.equalsIgnoreCase("Won"))
        {
            String url = Globals.GetOpportunityWon+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesWonList(url);
        }
        else if(type.equalsIgnoreCase("Lost"))
        {
            String url = Globals.GetOpportunityLost+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesLostList(url);
        }
        else {
            String url = Globals.GetOpportunity+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesList(url);
        }
        call.enqueue(new Callback<OpportunitiesResponse>() {
            @Override
            public void onResponse(Call<OpportunitiesResponse> call, Response<OpportunitiesResponse> response) {
                if (response != null) {

                    if(response.code()==401) {
                        //tempBox(loader,type);


                    }
                    else {
                        if (response.body().getValue().size() > 0) {
                            opoorunityAllList.setValue(response.body().getValue());

                        }
                    }
                    loader.setVisibility(View.GONE);
                }


            }
            @Override
            public void onFailure(Call<OpportunitiesResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });



    }



















    public static List<OpportunityItem> openList = new ArrayList<>();
    public static List<OpportunityItem> openOpportunities(List<OpportunityItem> list)
    {
        openList.clear();
        for (OpportunityItem obj: list)
            if(obj.getStatus().equalsIgnoreCase("sos_Open"))
                openList.add(obj);

        return  openList;
    }
    public static List<OpportunityItem> wonList = new ArrayList<>();
    public static List<OpportunityItem> wonOpportunities(List<OpportunityItem> list)
    {
        wonList.clear();
        for (OpportunityItem obj: list)
            if(obj.getStatus().equalsIgnoreCase("sos_Sold"))
                wonList.add(obj);

        return  wonList;
    } public static List<OpportunityItem> lostList = new ArrayList<>();
    public static List<OpportunityItem> lostOpportunities(List<OpportunityItem> list)
    {
        lostList.clear();
        for (OpportunityItem obj: list)
            if(obj.getStatus().equalsIgnoreCase("sos_closed"))
                lostList.add(obj);

        return  lostList;
    }


 /*   private void tempBox(ProgressBar loader, String type){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyApp.getInstance());
        builder1.setTitle("Server Error!.");
        builder1.setMessage("Please try again.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadOpportunies(loader,type);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }*/

    public LiveData<List<OpportunityItem>> getOpprtunities1(String type,int page)
    {
        //if the list is null

        opoorunityAllList = new MutableLiveData<List<OpportunityItem>>();
        //we will load it asynchronously from server in this method
        loadOpportunies1(type,page);


        //finally we will return the list
        return opoorunityAllList;
    }


    private void loadOpportunies1(String type, int page)
    {


        Call<OpportunitiesResponse> call = null;
        if(type.equalsIgnoreCase("Open")){
            String url = Globals.GetOpportunityOpen+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesOpenList(url);
        }
        else if(type.equalsIgnoreCase("Won"))
        {
            String url = Globals.GetOpportunityWon+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesWonList(url);
        }
        else if(type.equalsIgnoreCase("Lost"))
        {
            String url = Globals.GetOpportunityLost+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesLostList(url);
        }
        else {
            String url = Globals.GetOpportunity+" &$skip="+Globals.SkipItem(page);
            call = APIsClient.getInstance().getApiService().OpportunitiesList(url);
        }
        call.enqueue(new Callback<OpportunitiesResponse>() {
            @Override
            public void onResponse(Call<OpportunitiesResponse> call, Response<OpportunitiesResponse> response) {
                if (response != null) {

                    if(response.code()==401) {
                        //tempBox(loader,type);

                    }

                    else {
                        if (response.body().getValue().size() >= 0) {
                            opoorunityAllList.setValue(response.body().getValue());

                        }
                    }

                }


            }
            @Override
            public void onFailure(Call<OpportunitiesResponse> call, Throwable t) {

            }
        });



    }


    MutableLiveData<List<NewOpportunityRespose>> newopportunitylist;



    public LiveData<List<NewOpportunityRespose>> newOpprtunities(ProgressBar loader) {
        //if the list is null

        newopportunitylist=new MutableLiveData<List<NewOpportunityRespose>>();
        //we will load it asynchronously from server in this method
        loadnewOpportunies(loader);


        //finally we will return the list
        return newopportunitylist;
    }

    private void loadnewOpportunies(ProgressBar loader)
    {

        OpportunityValue opportunityValue = new OpportunityValue();
        opportunityValue.setSalesPerson(Prefs.getString(Globals.SalesEmployeeCode,""));
        Call<NewOppResponse> call  = NewApiClient.getInstance().getApiService().allopportinitylist(opportunityValue);

        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                if (response != null) {

                    if(response.code()==401) {
                        //tempBox(loader,type);

                    }

                    else {
                        if (response.body().getData().size() >= 0) {
                            newopportunitylist.setValue(response.body().getData());
                            loader.setVisibility(View.GONE);

                        }
                    }

                }


            }
            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(loader.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

}
