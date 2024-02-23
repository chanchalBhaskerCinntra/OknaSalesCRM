package com.cinntra.okana.viewModel;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.AdressDetail;
import com.cinntra.okana.model.BPTypeResponse;
import com.cinntra.okana.model.ContactPerson;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.Countries;
import com.cinntra.okana.model.DepartMent;
import com.cinntra.okana.model.DepartMentDetail;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.GetUserID;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.IndustryResponse;
import com.cinntra.okana.model.ItemCategoryData;
import com.cinntra.okana.model.ItemResponse;
import com.cinntra.okana.model.OwnerItem;
import com.cinntra.okana.model.OwnerResponse;
import com.cinntra.okana.model.PayMentTerm;
import com.cinntra.okana.model.PayMentTermsDetail;
import com.cinntra.okana.model.Role;
import com.cinntra.okana.model.RoleListDetail;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StagesItem;
import com.cinntra.okana.model.StagesResponse;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateDetail;
import com.cinntra.okana.model.TaxItem;
import com.cinntra.okana.model.TaxItemResponse;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.model.UserIDResponse;
import com.cinntra.okana.model.UserResponse;
import com.cinntra.okana.webservices.APIsClient;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemViewModel extends ViewModel {
    MutableLiveData<List<DocumentLines>> businessPartners;

    public LiveData<List<DocumentLines>> getItemsList(ProgressBar loader, ItemCategoryData pageNo) {
        //if the list is null
        // if (businessPartners == null)
        {
            businessPartners = new MutableLiveData<List<DocumentLines>>();
            //we will load it asynchronously from server in this method
//            loadItems(loader, pageNo);
        }

        //finally we will return the list
        return businessPartners;
    }

/*    private void loadItems(ProgressBar loader, ItemCategoryData pageNo) {


        loader.setVisibility(View.VISIBLE);
        Call<ItemResponse> call = NewApiClient.getInstance().getApiService().ItemsList(pageNo);
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                loader.setVisibility(View.GONE);

                businessPartners.setValue(response.body().getValue());
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }*/


    /***************** Load Tax slabs *******************/


    MutableLiveData<List<TaxItem>> TaxList;

    public LiveData<List<TaxItem>> getTaxList(ProgressBar loader) {
        //if the list is null
        if (TaxList == null) {
            TaxList = new MutableLiveData<List<TaxItem>>();
            //we will load it asynchronously from server in this method
            loadTaxItems(loader);
        }

        //finally we will return the list
        return TaxList;
    }

    private void loadTaxItems(ProgressBar loader) {
        loader.setVisibility(View.VISIBLE);
        Call<TaxItemResponse> call = APIsClient.getInstance().getApiService().taxcodes();
        call.enqueue(new Callback<TaxItemResponse>() {
            @Override
            public void onResponse(Call<TaxItemResponse> call, Response<TaxItemResponse> response) {
                if (response != null) {
                    loader.setVisibility(View.GONE);
                    TaxList.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<TaxItemResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
            }
        });
    }


    /********************** Employees List ***********************************/


    MutableLiveData<List<OwnerItem>> EmployeesList;

    public LiveData<List<OwnerItem>> getEmployeesList(ProgressBar loader) {
        //if the list is null
        if (EmployeesList == null) {
            EmployeesList = new MutableLiveData<List<OwnerItem>>();
            //we will load it asynchronously from server in this method
            loadEmployees(loader);
        }

        //finally we will return the list
        return EmployeesList;
    }

    private void loadEmployees(ProgressBar loader) {
        if (loader != null)
            loader.setVisibility(View.VISIBLE);
        Call<OwnerResponse> call = APIsClient.getInstance().getApiService().Employees_Owener_List();
        call.enqueue(new Callback<OwnerResponse>() {
            @Override
            public void onResponse(Call<OwnerResponse> call, Response<OwnerResponse> response) {
                if (response != null) {
                    if (loader != null)
                        loader.setVisibility(View.GONE);
                    EmployeesList.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<OwnerResponse> call, Throwable t) {
                if (loader != null)
                    loader.setVisibility(View.GONE);
            }
        });

    }

    /**************************Get Country**************************/


    MutableLiveData<List<StateData>> statelist;

    public LiveData<List<StateData>> getStateList(int pageNo) {
        //if the list is null
        //if (statelist == null)
        {
            statelist = new MutableLiveData<List<StateData>>();
            //we will load it asynchronously from server in this method
            loadState(pageNo);
        }

        //finally we will return the list
        return statelist;
    }


    private void loadState(int pageNo) {
        String url = Globals.GetStates + "?$skip=" + Globals.skipTo(pageNo);
        Call<StateDetail> call = APIsClient.getInstance().getApiService().getStateName(url);
        call.enqueue(new Callback<StateDetail>() {
            @Override
            public void onResponse(Call<StateDetail> call, Response<StateDetail> response) {
                if (response != null) {
                    if (response.body().getValue() != null && response.body().getValue().size() > 0)
                        statelist.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<StateDetail> call, Throwable t) {

            }
        });
    }

    /**************************Get DepartMent**************************/


    MutableLiveData<List<DepartMent>> departMentList;

    public LiveData<List<DepartMent>> getDepartMent() {
        //if the list is null
        if (departMentList == null) {
            departMentList = new MutableLiveData<List<DepartMent>>();
            //we will load it asynchronously from server in this method
            loadDepartMent();
        }

        //finally we will return the list
        return departMentList;
    }

    private void loadDepartMent() {
        Call<DepartMentDetail> call = NewApiClient.getInstance().getApiService().getDepartMent();
        call.enqueue(new Callback<DepartMentDetail>() {
            @Override
            public void onResponse(Call<DepartMentDetail> call, Response<DepartMentDetail> response) {
                if (response != null) {
                    departMentList.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<DepartMentDetail> call, Throwable t) {

            }
        });
    }

    MutableLiveData<List<Role>> rolelist;

    public LiveData<List<Role>> getRoleList() {
        //if the list is null
        if (rolelist == null) {
            rolelist = new MutableLiveData<List<Role>>();
            //we will load it asynchronously from server in this method
            loadRole();
        }

        //finally we will return the list
        return rolelist;
    }

    private void loadRole() {
        Call<RoleListDetail> call = NewApiClient.getInstance().getApiService().getRole();
        call.enqueue(new Callback<RoleListDetail>() {
            @Override
            public void onResponse(Call<RoleListDetail> call, Response<RoleListDetail> response) {
                if (response != null) {
                    rolelist.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<RoleListDetail> call, Throwable t) {

            }
        });
    }


    /**************************Get State**************************/


    MutableLiveData<List<Countries>> countrylist;

    public LiveData<List<Countries>> getCountrylist(int pageNo) {
        //if the list is null
        //  if (countrylist == null)
        {
            countrylist = new MutableLiveData<List<Countries>>();
            //we will load it asynchronously from server in this method
            loadCountry(pageNo);
        }

        //finally we will return the list
        return countrylist;
    }

    private void loadCountry(int pageNo) {

        String url = Globals.GetCountry + "?$skip=" + Globals.skipTo(pageNo);
        Call<AdressDetail> call = APIsClient.getInstance().getApiService().getCountryName(url);
        call.enqueue(new Callback<AdressDetail>() {
            @Override
            public void onResponse(Call<AdressDetail> call, Response<AdressDetail> response) {
                if (response != null) {
                    countrylist.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<AdressDetail> call, Throwable t) {

            }
        });
    }

    /*******************  Get Sales Stages  **********************/

    MutableLiveData<List<StagesItem>> StagesList;

    public LiveData<List<StagesItem>> getStagesList() {
        //if the list is null
        if (StagesList == null) {
            StagesList = new MutableLiveData<List<StagesItem>>();
            //we will load it asynchronously from server in this method
            loadstages();
        }

        //finally we will return the list
        return StagesList;
    }

    private void loadstages() {

        Call<StagesResponse> call = APIsClient.getInstance().getApiService().getStagesList();
        call.enqueue(new Callback<StagesResponse>() {
            @Override
            public void onResponse(Call<StagesResponse> call, Response<StagesResponse> response) {
                if (response != null) {
                    StagesList.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<StagesResponse> call, Throwable t) {

            }
        });
    }

    /*******************  Sale Employee List **********************/

    MutableLiveData<List<SalesEmployeeItem>> SalesEmployeeList;

    public LiveData<List<SalesEmployeeItem>> getSalesEmployeeList() {
        //if the list is null
        if (SalesEmployeeList == null) {
            SalesEmployeeList = new MutableLiveData<List<SalesEmployeeItem>>();
            //we will load it asynchronously from server in this method
            loadsalesemps();
        }

        //finally we will return the list
        return SalesEmployeeList;
    }

    private void loadsalesemps() {

        EmployeeValue employeeValue = new EmployeeValue();
        employeeValue.setSalesEmployeeCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
        Call<SaleEmployeeResponse> call = NewApiClient.getInstance().getApiService().getSalesEmplyeeList(employeeValue);
        call.enqueue(new Callback<SaleEmployeeResponse>() {
            @Override
            public void onResponse(Call<SaleEmployeeResponse> call, Response<SaleEmployeeResponse> response) {
                if (response != null) {
                    if (response.body().getValue() != null && response.body().getValue().size() > 0)
                        SalesEmployeeList.setValue(filterlist(response.body().getValue()));

                    //
                }
            }

            @Override
            public void onFailure(Call<SaleEmployeeResponse> call, Throwable t) {
            }
        });
    }

    private List<SalesEmployeeItem> filterlist(ArrayList<SalesEmployeeItem> value) {
        List<SalesEmployeeItem> tempList = new ArrayList<>();
        for (SalesEmployeeItem salesEmployeeItem : value) {
            if (!salesEmployeeItem.getRole().equals("foo")) {
                tempList.add(salesEmployeeItem);
            }
        }
        return tempList;
    }


    /*******************  Sale Employee List **********************/

    MutableLiveData<List<ContactPersonData>> ContactEmployeeList;

    public LiveData<List<ContactPersonData>> getContactEmployeeList(String cardcode) {
        //if the list is null
        if (ContactEmployeeList == null) {
            ContactEmployeeList = new MutableLiveData<List<ContactPersonData>>();
            //we will load it asynchronously from server in this method
            loadcontactemps(cardcode);
        }

        //finally we will return the list
        return ContactEmployeeList;
    }

    private void loadcontactemps(String cardCode) {
        ContactPersonData businessPartnerData = new ContactPersonData();
        businessPartnerData.setCardCode(cardCode);
        Call<ContactPerson> call = NewApiClient.getInstance().getApiService().ContactEmployeesList(businessPartnerData);
        call.enqueue(new Callback<ContactPerson>() {
            @Override
            public void onResponse(Call<ContactPerson> call, Response<ContactPerson> response) {
                if (response.code() == 200) {

                    ContactEmployeeList.setValue(response.body().getData());

                }
            }

            @Override
            public void onFailure(Call<ContactPerson> call, Throwable t) {

            }
        });
    }

    /*******************  Get BPType List  **********************/

    MutableLiveData<List<UTypeData>> bptypelist;

    public LiveData<List<UTypeData>> getBpTypeList() {
        //if the list is null
        if (bptypelist == null) {
            bptypelist = new MutableLiveData<List<UTypeData>>();
            //we will load it asynchronously from server in this method
            loadBPtypelist();
        }

        //finally we will return the list
        return bptypelist;
    }

    private void loadBPtypelist() {
        Call<BPTypeResponse> call = NewApiClient.getInstance().getApiService().getBptypelist();
        call.enqueue(new Callback<BPTypeResponse>() {
            @Override
            public void onResponse(Call<BPTypeResponse> call, Response<BPTypeResponse> response) {
                if (response != null) {
                    bptypelist.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BPTypeResponse> call, Throwable t) {

            }
        });
    }


    /*******************  Get Opptype List  **********************/

    MutableLiveData<List<UTypeData>> opptypelist;

    public LiveData<List<UTypeData>> getOPpTypeList() {
        //if the list is null
        if (opptypelist == null) {
            opptypelist = new MutableLiveData<List<UTypeData>>();
            //we will load it asynchronously from server in this method
            loadOpptypelist();
        }

        //finally we will return the list
        return opptypelist;
    }

    private void loadOpptypelist() {
        Call<BPTypeResponse> call = NewApiClient.getInstance().getApiService().getopptypelist();
        call.enqueue(new Callback<BPTypeResponse>() {
            @Override
            public void onResponse(Call<BPTypeResponse> call, Response<BPTypeResponse> response) {
                if (response != null) {
                    opptypelist.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BPTypeResponse> call, Throwable t) {

            }
        });
    }

    /*******************  Get Industry List  **********************/

    MutableLiveData<List<IndustryItem>> IndustryList;

    public LiveData<List<IndustryItem>> getIndustryList() {
        //if the list is null
        if (IndustryList == null) {
            IndustryList = new MutableLiveData<List<IndustryItem>>();
            //we will load it asynchronously from server in this method
            loadIndustry();
        }

        //finally we will return the list
        return IndustryList;
    }

    private void loadIndustry() {
        Call<IndustryResponse> call = NewApiClient.getInstance().getApiService().getIndustryList();
        call.enqueue(new Callback<IndustryResponse>() {
            @Override
            public void onResponse(Call<IndustryResponse> call, Response<IndustryResponse> response) {
                if (response != null) {
                    IndustryList.setValue(response.body().getValue());
                }
            }

            @Override
            public void onFailure(Call<IndustryResponse> call, Throwable t) {

            }
        });
    }


    /********************** Get UserID ***********************/

    MutableLiveData<List<GetUserID>> userIDList;

    public LiveData<List<GetUserID>> getUserID(String cardcode) {
        //if the list is null
        if (userIDList == null) {
            userIDList = new MutableLiveData<List<GetUserID>>();
            //we will load it asynchronously from server in this method
            loadUserID(cardcode);
        }

        //finally we will return the list
        return userIDList;
    }

    private void loadUserID(String userType) {
        String url = Globals.GetUserID + userType + "'";
        Call<UserIDResponse> call = APIsClient.getInstance().getApiService().getUserID(url);
        call.enqueue(new Callback<UserIDResponse>() {
            @Override
            public void onResponse(Call<UserIDResponse> call, Response<UserIDResponse> response) {
                if (response != null) {
                    if (response.body().getValue() != null && response.body().getValue().size() > 0) {
                        userIDList.setValue(response.body().getValue());
                        Prefs.putString(Globals.App_USERID, response.body().getValue().get(0).getInternalKey());

                    }
                }
            }

            @Override
            public void onFailure(Call<UserIDResponse> call, Throwable t) {

            }
        });
    }


    private void loadProfile(String userID) {
        String url = Globals.GetProfile + userID + ")";
        Call<UserResponse> call = APIsClient.getInstance().getApiService().getUserProfile(url);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response != null) {


                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    /**************************Get PaymentTerm*************************/

    MutableLiveData<List<PayMentTerm>> paymentlist;

    public LiveData<List<PayMentTerm>> getPaymentList() {
        //if the list is null
        if (paymentlist == null) {
            paymentlist = new MutableLiveData<List<PayMentTerm>>();
            //we will load it asynchronously from server in this method
            loadPayment();
        }

        //finally we will return the list
        return paymentlist;
    }

    private void loadPayment() {
        Call<PayMentTermsDetail> call = NewApiClient.getInstance().getApiService().getPaymentTerm();
        call.enqueue(new Callback<PayMentTermsDetail>() {
            @Override
            public void onResponse(Call<PayMentTermsDetail> call, Response<PayMentTermsDetail> response) {
                if (response != null) {
                    if (response.body().getData() != null && response.body().getData().size() > 0)
                        paymentlist.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<PayMentTermsDetail> call, Throwable t) {

            }
        });
    }

}
