package com.cinntra.okana.viewModel;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.InvoiceNewData;
import com.cinntra.okana.model.InvoiceResponse;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.APIsClient;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationList_ViewModel extends ViewModel {

    MutableLiveData<List<QuotationItem>> quatotaionAllList;
    public static List<QuotationItem> qutotaionOpenList = new ArrayList<>();

    //we will call this method to get the data
    public LiveData<List<QuotationItem>> getQutotation(ProgressBar loader) {
        //if the list is null

        quatotaionAllList = new MutableLiveData<List<QuotationItem>>();
        //we will load it asynchronously from server in this method
        loadQuotation(loader);


        //finally we will return the list
        return quatotaionAllList;
    }


    private void loadQuotation(ProgressBar loader) {
        HashMap<String, String> hd = new HashMap<>();
        if (!Prefs.getString(Globals.QuotationListing, "").equalsIgnoreCase("null")) {
            hd.put("U_OPPID", Prefs.getString(Globals.QuotationListing, ""));

        } else {
            hd.put("SalesPersonCode", Prefs.getString(Globals.SalesEmployeeCode, ""));
        }

        Call<QuotationResponse> call = NewApiClient.getInstance().getApiService().OpenQuotationList(hd);
        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                loader.setVisibility(View.GONE);

                if (response.code() == 200) {
                    // loadQuotation(loader, fromWhere, currentPage);
                    quatotaionAllList.setValue(response.body().getValue());
                }


            }

            @Override
            public void onFailure(Call<QuotationResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    public static List<QuotationItem> getopenList(List<QuotationItem> openList) {
        qutotaionOpenList.clear();
        for (QuotationItem obj : openList
        )
            if (obj.getDocumentStatus().equalsIgnoreCase("bost_Open"))
                qutotaionOpenList.add(obj);

        return qutotaionOpenList;
    }

    public static List<QuotationItem> getcloseList(List<QuotationItem> openList) {

        for (QuotationItem obj : openList
        )
            if (obj.getDocumentStatus().equalsIgnoreCase("bost_close"))
                qutotaionOpenList.add(obj);

        return qutotaionOpenList;
    }

    MutableLiveData<List<InvoiceNewData>> invoicesAllList;

    public LiveData<List<InvoiceNewData>> getInvoices(ProgressBar loader, String type, int pageNo) {
        //if the list is null

        invoicesAllList = new MutableLiveData<List<InvoiceNewData>>();
        //we will load it asynchronously from server in this method
        loadIvoices(loader, type, pageNo);


        //finally we will return the list
        return invoicesAllList;
    }

    private void loadIvoices(ProgressBar loader, String type, int pageno) {
        Call<InvoiceResponse> call = null;
        loader.setVisibility(View.VISIBLE);
        if (type.equalsIgnoreCase("OverDue")) {
//            String url = Globals.OverDueInvoicesList+"&$skip="+Globals.SkipItem(pageno);
            String url = Globals.GetInvoice + "?$skip=" + Globals.SkipItem(pageno);
            call = APIsClient.getInstance().getApiService().InvoicesOverDueList(url);
        } else {
            String url = Globals.GetInvoice + "?$skip=" + Globals.SkipItem(pageno);
            call = APIsClient.getInstance().getApiService().InvoicesList(url);
        }

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response != null) {
                    if (response.body().getValue() != null)
                        invoicesAllList.setValue(response.body().getValue());
                    loader.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    MutableLiveData<List<QuotationItem>> OrderAllList;

    public LiveData<List<QuotationItem>> getOrders(ProgressBar loader) {
        //if the list is null

        OrderAllList = new MutableLiveData<List<QuotationItem>>();
        //we will load it asynchronously from server in this method
//        loadOrders(loader);


        //finally we will return the list
        return OrderAllList;
    }

 /*   private void loadOrders(ProgressBar loader) {

        HashMap<String, String> hd = new HashMap<>();
        hd.put("SalesPersonCode", Prefs.getString(Globals.SalesEmployeeCode, ""));
        Call<QuotationResponse> call;
        call = NewApiClient.getInstance().getApiService().OrdersList(hd);

        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                if (response.body().getStatus() == 200) {
                    OrderAllList.setValue(response.body().getValue());
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<QuotationResponse> call, Throwable t) {
                Log.e("fail==>", t.getMessage());
                loader.setVisibility(View.GONE);
            }
        });
    }*///todo comment by chancahl


    public LiveData<List<QuotationItem>> getDeliveries(ProgressBar loader, String type) {
        //if the list is null
        if (deliveryAllList == null) {
            deliveryAllList = new MutableLiveData<List<QuotationItem>>();
            //we will load it asynchronously from server in this method
            loadDeliveries(loader, type);
        }

        //finally we will return the list
        return deliveryAllList;
    }

    MutableLiveData<List<QuotationItem>> deliveryAllList;

    private void loadDeliveries(ProgressBar loader, String type) {
        Call<QuotationResponse> call = null;
        loader.setVisibility(View.VISIBLE);
        if (type.equalsIgnoreCase("Open"))
            call = APIsClient.getInstance().getApiService().deliveryOpenList();
        else if (type.equalsIgnoreCase("Close"))
            call = APIsClient.getInstance().getApiService().deliveryCloseList();
        else if (type.equalsIgnoreCase("OverDue")) {
            String url = Globals.deliveryOverDueList + Globals.getTodaysDate() + "'";
            call = APIsClient.getInstance().getApiService().deliveryOverDueList(url);
        } else
            call = APIsClient.getInstance().getApiService().deliveryList();

        call.enqueue(new Callback<QuotationResponse>() {
            @Override
            public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
                if (response != null) {

                    deliveryAllList.setValue(response.body().getValue());


                }
                loader.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<QuotationResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    public LiveData<List<InvoiceNewData>> getInventories(ProgressBar loader) {
        //if the list is null
        if (invoicesAllList == null) {
            invoicesAllList = new MutableLiveData<List<InvoiceNewData>>();
            //we will load it asynchronously from server in this method
            loadInventories(loader);
        }

        //finally we will return the list
        return invoicesAllList;
    }

    MutableLiveData<List<QuotationItem>> InventoryAllList;

    private void loadInventories(ProgressBar loader) {
        loader.setVisibility(View.VISIBLE);
        Call<InvoiceResponse> call = APIsClient.getInstance().getApiService().inventoryList();
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response != null) {

                    invoicesAllList.setValue(response.body().getValue());

                    loader.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }

}
