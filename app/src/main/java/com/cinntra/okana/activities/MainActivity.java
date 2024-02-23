package com.cinntra.okana.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityMainBinding;
import com.cinntra.okana.fragments.Calender;
import com.cinntra.okana.fragments.Dashboard;
import com.cinntra.okana.fragments.Graph_;
import com.cinntra.okana.fragments.Settings;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.BPTypeResponse;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.CountryResponse;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.model.DepartMent;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.IndustryResponse;
import com.cinntra.okana.model.ItemCategoryData;
import com.cinntra.okana.model.ItemCategoryResponse;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.LeadTypeResponse;
import com.cinntra.okana.model.OwnerItem;
import com.cinntra.okana.model.OwnerResponse;
import com.cinntra.okana.model.PayMentTerm;
import com.cinntra.okana.model.PayMentTermsDetail;
import com.cinntra.okana.model.Role;
import com.cinntra.okana.model.TaxItem;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.room.BpTypeDatabase;
import com.cinntra.okana.room.BusinessPartnerDatabase;
import com.cinntra.okana.room.CountriesDatabase;
import com.cinntra.okana.room.DepartmentDatabase;
import com.cinntra.okana.room.IndustriesDatabase;
import com.cinntra.okana.room.LeadSourceDatabase;
import com.cinntra.okana.room.LeadTypeDatabase;
import com.cinntra.okana.room.OwnerDatabase;
import com.cinntra.okana.room.PaymentTermDatabase;
import com.cinntra.okana.room.ProductDatabase;
import com.cinntra.okana.room.RoleDatabase;
import com.cinntra.okana.room.TaxItemDatabase;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends MainBaseActivity implements FragmentRefresher {


//    @BindView(R.id.new_contact)
//    FloatingActionButton add_contact;

//    var builder: AlertDialog.Builder? = null
//    var alertDialog: AlertDialog? = null


    ActivityMainBinding binding;

    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
      //  ButterKnife.bind(this);
        Globals.CURRENT_CLASS = getClass().getName();
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(R.layout.dialog_progress_or_sync).setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        //fetchDataAndUpdateDb(this);
        fetchCountryDataFromApi(this);
        fetchIndustryDataFromApi(this);
        fetchLeadTypeDataFromApi(this);
        fetchLeadSourceDataFromApi(this);
      //  fetchOwnerListDataFromApi(this);
        fetchItemCategoryDataDataFromApi(this);
        fetchBusinessPartnertDataFromApi(this);
        fetchBpTypeDataDataFromApi(this);
//        fetchTaxesListFromApi();//todo comment by me---
        fetchDepartmentDataFromApi();
        fetchRoleFromApi();
        fetchPaymentTermListDataFromApi(this);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 100, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 25);
        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: "+e.getMessage() );
        }


        loadFragment(new Dashboard());
        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setBackground(null);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new Dashboard();
                        break;
                    case R.id.graph:
                        fragment = new Graph_();
                        break;
                    case R.id.notification:
                        fragment = new Calender();
                        break;
                    case R.id.settings:
                        fragment = new Settings();

                        break;
                    case R.id.add_contact:
                        break;
                }
                return loadFragment(fragment);
            }
        });


        binding.newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(MainActivity.this, AddContact.class);
                Prefs.putString(Globals.AddContactPerson, "Dashboard");

                startActivity(intent);*/ //todo comment by me--
            }
        });
    }


    public void fetchDataAndUpdateDb(Context context) {
        CountriesDatabase db = Room.databaseBuilder(
                context.getApplicationContext(),
                CountriesDatabase.class, "my-db-country"
        ).allowMainThreadQueries().build();

        // List<CountryData> apiData = fetchDataFromApi();
        //    Log.d(TAG, "fetchDataAndUpdateDb: "+fetchDataFromApi().size());

    }


    public static ArrayList<CountryData> countrylistFromLocal = new ArrayList<>();
    public static ArrayList<IndustryItem> industryListFromLocal = new ArrayList<>();
    public static ArrayList<LeadTypeData> leadTypeListFromLocal = new ArrayList<>();
    public static ArrayList<LeadTypeData> leadSourceListFromLocal = new ArrayList<>();
    public static ArrayList<OwnerItem> ownerListFromLocal = new ArrayList<>();
    public static ArrayList<PayMentTerm> paymentTermListFromLocal = new ArrayList<>();
    public static ArrayList<ItemCategoryData> itemCategoryDataFromLocal = new ArrayList<>();
    public static ArrayList<UTypeData> itemBpTypeDataFromLocal = new ArrayList<>();
    public static ArrayList<TaxItem> taxItemDataFromLocal = new ArrayList<>();
    public static ArrayList<Role> roleDataFromLocal = new ArrayList<>();
    public static ArrayList<DepartMent> departmentDataFromLocal = new ArrayList<>();
    public static ArrayList<BusinessPartnerData> businessPartnerDataFromLocal = new ArrayList<>();


    private List<CountryData> fetchCountryDataFromApi(Context context) {
        alertDialog.show();
        Call<CountryResponse> call = NewApiClient.getInstance().getApiService().getCountryList();
        ArrayList<CountryData> countrylist = new ArrayList<>();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.body().getStatus() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        CountriesDatabase db = CountriesDatabase.getDatabase(getApplicationContext());
                        List<CountryData> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            countrylistFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: "+countrylistFromLocal.get(0).getName());
                            Log.e(TAG, "onResponseItemCountry: "+db.myDataDao().getAll().get(0).getName() );
                            Log.e(TAG, "onResponseCOuntry: " + db.myDataDao().getAll());

                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                // Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }

    private List<LeadTypeData> fetchLeadTypeDataFromApi(Context context) {
        alertDialog.show();
        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getLeadType();
        ArrayList<LeadTypeData> countrylist = new ArrayList<>();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
                alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.body().getStatus() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        LeadTypeDatabase db = LeadTypeDatabase.getDatabase(getApplicationContext());
                        List<LeadTypeData> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            leadTypeListFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: " + db.myDataDao().getAll().toString());

                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }


    private List<LeadTypeData> fetchLeadSourceDataFromApi(Context context) {
        alertDialog.show();
        Call<LeadTypeResponse> call = NewApiClient.getInstance().getApiService().getsourceType();
        ArrayList<LeadTypeData> countrylist = new ArrayList<>();
        call.enqueue(new Callback<LeadTypeResponse>() {
            @Override
            public void onResponse(Call<LeadTypeResponse> call, Response<LeadTypeResponse> response) {
                alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.body().getStatus() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        LeadSourceDatabase db = LeadSourceDatabase.getDatabase(getApplicationContext());
                        List<LeadTypeData> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            leadSourceListFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: " + db.myDataDao().getAll().toString());

                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<LeadTypeResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }


    private List<OwnerItem> fetchOwnerListDataFromApi(Context context) {
        alertDialog.show();
        Call<OwnerResponse> call = NewApiClient.getInstance().getApiService().Employees_Owener_List();
        ArrayList<OwnerItem> countrylist = new ArrayList<>();
        call.enqueue(new Callback<OwnerResponse>() {
            @Override
            public void onResponse(Call<OwnerResponse> call, Response<OwnerResponse> response) {
                alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getValue().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getValue());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        OwnerDatabase db = OwnerDatabase.getDatabase(getApplicationContext());
                        List<OwnerItem> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            ownerListFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: " + db.myDataDao().getAll().toString());

                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<OwnerResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }


    private List<BusinessPartnerData> fetchBusinessPartnertDataFromApi(Context context) {
        alertDialog.show();
        Call<CustomerBusinessRes> call = NewApiClient.getInstance().getApiService().getAllBusinessPartner();
        ArrayList<BusinessPartnerData> countrylist = new ArrayList<>();
        call.enqueue(new Callback<CustomerBusinessRes>() {
            @Override
            public void onResponse(Call<CustomerBusinessRes> call, Response<CustomerBusinessRes> response) {
                alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        BusinessPartnerDatabase db = BusinessPartnerDatabase.getDatabase(getApplicationContext());
                        List<BusinessPartnerData> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            businessPartnerDataFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: " + db.myDataDao().getAll().toString());

                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<CustomerBusinessRes> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }

    private List<PayMentTerm> fetchPaymentTermListDataFromApi(Context context) {
        alertDialog.show();
        Call<PayMentTermsDetail> call = NewApiClient.getInstance().getApiService().getPaymentTerm();
        ArrayList<PayMentTerm> countrylist = new ArrayList<>();
        call.enqueue(new Callback<PayMentTermsDetail>() {
            @Override
            public void onResponse(Call<PayMentTermsDetail> call, Response<PayMentTermsDetail> response) {
                alertDialog.dismiss();
                Log.d(TAG, "onResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        countrylist.clear();
                        countrylist.addAll(response.body().getData());
                        Log.d(TAG, "onResponse: " + countrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();
                        PaymentTermDatabase db = PaymentTermDatabase.getDatabase(getApplicationContext());
                        List<PayMentTerm> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            paymentTermListFromLocal.addAll(countrylist);
                            Log.e(TAG, "onResponse: " + db.myDataDao().getAll().toString());

                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //  Log.d(TAG, "firstValue: " + localData.get(0).getName());

                        Log.d(TAG, "fetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<PayMentTermsDetail> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return countrylist;


    }


    private List<IndustryItem> fetchIndustryDataFromApi(Context context) {
        Call<IndustryResponse> call = NewApiClient.getInstance().getApiService().getIndustryList();
        ArrayList<IndustryItem> industrylist = new ArrayList<>();
        call.enqueue(new Callback<IndustryResponse>() {
            @Override
            public void onResponse(Call<IndustryResponse> call, Response<IndustryResponse> response) {
                Log.d(TAG, "onIndustryResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onIndustryResponse: enter in response");
                    if (response.body().getValue().size() > 0) {
                        industrylist.clear();
                        industrylist.addAll(response.body().getValue());
                        Log.d(TAG, "onIndustryResponse: " + industrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();


                        IndustriesDatabase db = IndustriesDatabase.getDatabase(getApplicationContext());


                        List<IndustryItem> localData = db.industriesDao().getAll();
                        if (!localData.equals(industrylist)) {
                            db.industriesDao().insertAll(industrylist);
                            industryListFromLocal.addAll(industrylist);
                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //   Log.d(TAG, "industryValue: " + localData.get(0).getIndustryName());

                        Log.d(TAG, "IndustryFetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<IndustryResponse> call, Throwable t) {
                // Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return industrylist;


    }


    private List<ItemCategoryData> fetchItemCategoryDataDataFromApi(Context context) {
        Call<ItemCategoryResponse> call = NewApiClient.getInstance().getApiService().getAllCategory();
        ArrayList<ItemCategoryData> industrylist = new ArrayList<>();
        call.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
                Log.d(TAG, "onIndustryResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d(TAG, "onIndustryResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        industrylist.clear();
                        industrylist.addAll(response.body().getData());
                        Log.d(TAG, "onIndustryResponse: " + industrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();


                        ProductDatabase db = ProductDatabase.getDatabase(getApplicationContext());


                        List<ItemCategoryData> localData = db.myDataDao().getAll();
                        if (!localData.equals(industrylist)) {
                            db.myDataDao().insertAll(industrylist);
                            itemCategoryDataFromLocal.addAll(industrylist);
                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //   Log.d(TAG, "industryValue: " + localData.get(0).getIndustryName());

                        Log.d(TAG, "IndustryFetchDataAndUpdateDb: " + localData.size());
                    }


                }
            }

            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return industrylist;


    }

    private List<UTypeData> fetchBpTypeDataDataFromApi(Context context) {
        Call<BPTypeResponse> call = NewApiClient.getInstance().getApiService().getBptypelist();
        ArrayList<UTypeData> industrylist = new ArrayList<>();
        call.enqueue(new Callback<BPTypeResponse>() {
            @Override
            public void onResponse(Call<BPTypeResponse> call, Response<BPTypeResponse> response) {
                Log.d(TAG, "onIndustryResponse: " + response.code());
                if (response.body().getStatus() == 200) {
                    Log.d(TAG, "onIndustryResponse: enter in response");
                    if (response.body().getData().size() > 0) {
                        industrylist.clear();
                        industrylist.addAll(response.body().getData());
                        Log.d(TAG, "onIndustryResponse: " + industrylist.size());
//                        CountriesDatabase db = Room.databaseBuilder(
//                                context.getApplicationContext(),
//                                CountriesDatabase.class, "my-db-country"
//                        ).allowMainThreadQueries().build();


                        BpTypeDatabase db = BpTypeDatabase.getDatabase(getApplicationContext());


                        List<UTypeData> localData = db.myDataDao().getAll();
                        if (!localData.equals(industrylist)) {
                            db.myDataDao().insertAll(industrylist);
                            itemBpTypeDataFromLocal.addAll(industrylist);
                        } else {
                            //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                        }

                        //   Log.d(TAG, "industryValue: " + localData.get(0).getIndustryName());

                        Log.d(TAG, "IndustryFetchDataAndUpdateDb: " + localData.size());
                    }


                } else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BPTypeResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return industrylist;


    }

    private void fetchDepartmentDataFromApi() {
        ArrayList<DepartMent> taxesList = new ArrayList<>();
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getDepartMent().observe(this, new Observer<List<DepartMent>>() {
            @Override
            public void onChanged(@Nullable List<DepartMent> itemsList) {
                taxesList.clear();
                taxesList.addAll(itemsList);
//                TaxItemAdapter adapter = new TaxItemAdapter(context, taxSlabList,itemsObj,TaxListdialog);
//                recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
//                recyclerview.setAdapter(adapter);

                DepartmentDatabase db = DepartmentDatabase.getDatabase(getApplicationContext());


                List<DepartMent> localData = db.myDataDao().getAll();
                if (!localData.equals(taxesList)) {
                    db.myDataDao().insertAll(taxesList);
                    departmentDataFromLocal.addAll(taxesList);
                } else {
                    //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                }

                //   Log.d(TAG, "industryValue: " + localData.get(0).getIndustryName());

                Log.d(TAG, "IndustryFetchDataAndUpdateDb: " + localData.size());

            }
        });


    }

    private void fetchTaxesListFromApi() {
        ArrayList<TaxItem> taxesList = new ArrayList<>();
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getTaxList(new ProgressBar(this)).observe(this, new Observer<List<TaxItem>>() {
            @Override
            public void onChanged(@Nullable List<TaxItem> itemsList) {
                taxesList.clear();
                taxesList.addAll(itemsList);
//                TaxItemAdapter adapter = new TaxItemAdapter(context, taxSlabList,itemsObj,TaxListdialog);
//                recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
//                recyclerview.setAdapter(adapter);

                TaxItemDatabase db = TaxItemDatabase.getDatabase(getApplicationContext());


                List<TaxItem> localData = db.myDataDao().getAll();
                if (!localData.equals(taxesList)) {
                    db.myDataDao().insertAll(taxesList);
                    taxItemDataFromLocal.addAll(taxesList);
                } else {
                    //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                }

                //   Log.d(TAG, "industryValue: " + localData.get(0).getIndustryName());

                Log.d(TAG, "IndustryFetchDataAndUpdateDb: " + localData.size());

            }
        });


    }

    private void fetchRoleFromApi() {
        ArrayList<Role> taxesList = new ArrayList<>();
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getRoleList().observe(this, new Observer<List<Role>>() {
            @Override
            public void onChanged(@Nullable List<Role> itemsList) {
                taxesList.clear();
                taxesList.addAll(itemsList);
//                TaxItemAdapter adapter = new TaxItemAdapter(context, taxSlabList,itemsObj,TaxListdialog);
//                recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
//                recyclerview.setAdapter(adapter);

                RoleDatabase db = RoleDatabase.getDatabase(getApplicationContext());


                List<Role> localData = db.myDataDao().getAll();
                if (!localData.equals(taxesList)) {
                    db.myDataDao().insertAll(taxesList);
                    roleDataFromLocal.addAll(taxesList);
                } else {
                    //  Toast.makeText(MainActivity.this, "updated value", Toast.LENGTH_SHORT).show();

                }

                //   Log.d(TAG, "industryValue: " + localData.get(0).getIndustryName());

                Log.d(TAG, "IndustryFetchDataAndUpdateDb: " + localData.size());

            }
        });
    }


    private boolean loadFragment(Fragment fragment) {
        if (Globals.CURRENT_CLASS.equalsIgnoreCase(fragment.getClass().getName())) {
            return false;
        }
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)

                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onRefresh() {
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    private static final String TAG = "MainActivity";
}