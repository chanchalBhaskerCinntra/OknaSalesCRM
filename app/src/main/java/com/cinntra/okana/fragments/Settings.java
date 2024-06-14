package com.cinntra.okana.fragments;

import static com.cinntra.okana.activities.MainActivity.businessPartnerDataFromLocal;
import static com.cinntra.okana.activities.MainActivity.countrylistFromLocal;
import static com.cinntra.okana.activities.MainActivity.departmentDataFromLocal;
import static com.cinntra.okana.activities.MainActivity.industryListFromLocal;
import static com.cinntra.okana.activities.MainActivity.itemBpTypeDataFromLocal;
import static com.cinntra.okana.activities.MainActivity.itemCategoryDataFromLocal;
import static com.cinntra.okana.activities.MainActivity.leadSourceListFromLocal;
import static com.cinntra.okana.activities.MainActivity.leadTypeListFromLocal;
import static com.cinntra.okana.activities.MainActivity.ownerListFromLocal;
import static com.cinntra.okana.activities.MainActivity.paymentTermListFromLocal;
import static com.cinntra.okana.activities.MainActivity.roleDataFromLocal;
import static com.cinntra.okana.activities.MainActivity.taxItemDataFromLocal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;


import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.LocationListing;
import com.cinntra.okana.activities.Login;
import com.cinntra.okana.activities.MyMapLocation;
import com.cinntra.okana.databinding.FragmentProfileBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.BPTypeResponse;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.CountryResponse;
import com.cinntra.okana.model.CustomerBusinessRes;
import com.cinntra.okana.model.DepartMent;
import com.cinntra.okana.model.EmpDetails;
import com.cinntra.okana.model.EmployeeProfile;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.IndustryResponse;
import com.cinntra.okana.model.ItemCategoryData;
import com.cinntra.okana.model.ItemCategoryResponse;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.LeadTypeResponse;
import com.cinntra.okana.model.MapData;
import com.cinntra.okana.model.MapResponse;
import com.cinntra.okana.model.NewLoginData;
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
import com.cinntra.okana.workManager.ApiSchedular;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import com.google.protobuf.Api;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Settings extends Fragment implements View.OnClickListener {
    private static final String TAG = "Settings";

    LocationManager locationManager;
    FusedLocationProviderClient client;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;


    public static String locationtype;

    public Settings() {
        // Required empty public constructor
    }

    FragmentProfileBinding binding;

    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        Globals.CURRENT_CLASS = getClass().getName();
        Gson gson = new Gson();
        String json = Prefs.getString(Globals.AppUserDetails, "");
        NewLoginData obj = gson.fromJson(json, NewLoginData.class);
        alertDialogBuilder = new AlertDialog.Builder(requireContext());
        alertDialogBuilder.setView(R.layout.dialog_progress_or_sync).setCancelable(false);
        alertDialog = alertDialogBuilder.create();
        binding.logout.setOnClickListener(this);
        binding.editProfile.setOnClickListener(this);
        binding.currentlocation.setOnClickListener(this);
        callgetlocationApi();

        if (Prefs.getString(Globals.locationcondition, "Off").equalsIgnoreCase("On")) {
            binding.mode.setChecked(true);
            binding.checkin.setText("Check Out");
        } else {
            binding.mode.setChecked(false);
            binding.checkin.setText("Check In");
        }

        if (mdplist.size() > 0) {
            if (mdplist.get(0).getType().equalsIgnoreCase("Start")) {
                binding.meetingmode.setChecked(true);
                binding.meetingon.setText("Meeting Off");
            } else {
                binding.meetingmode.setChecked(false);
                binding.meetingon.setText("Meeting On");
            }
        } else {
            binding.meetingmode.setChecked(false);
            binding.meetingon.setText("Meeting On");
        }


        binding.locationlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdplist.size() > 0) {
                    Intent intent = new Intent(getContext(), LocationListing.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "No Location Found", Toast.LENGTH_LONG).show();
                }
            }
        });
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRound(Character.toString(Prefs.getString(Globals.Employee_Name, "").charAt(0)).toUpperCase(), requireActivity().getResources().getColor(R.color.colorPrimary));
        binding.profilePic.setImageDrawable(drawable);
        String name = Prefs.getString(Globals.Employee_Name, "");
        String cap = name.substring(0, 1).toUpperCase() + name.substring(1);
        binding.userName.setText(cap);
/*
        binding.mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Prefs.putString(Globals.locationcondition, "On");
                    locationtype = "Start";
                    if (MainBaseActivity.boolean_permission) {
                        Log.e("start", "start");
                        Intent intent = new Intent(getContext(), GoogleService.class);
                        getContext().startService(intent);
                    } else {
                        givepermission();
                    }
                } else {
                    Prefs.putString(Globals.locationcondition, "Off");
                    locationtype = "Stop";
                    Intent intent = new Intent(getContext(), GoogleService.class);
                    getContext().stopService(intent);
                }
                *//*  checkpermission(locationtype);*//*
            }
        });*/


        binding.mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mode.isChecked()) {
                    Prefs.putString(Globals.locationcondition, "On");

                    Prefs.putBoolean(Globals.checkIn, true);
                    locationtype = "Start";
                    binding.checkin.setText("Check Out");
                    GlobalStatus = "Toggle_CheckIn";

//                    Prefs.putBoolean(Globals.Location_Boolean_MInutes, true);

                    givepermission(null);

                    //todo calling job schedular for current location in every 15 minutes.
                    if (Prefs.getBoolean(Globals.Location_FirstTime, false) ) {//&& Prefs.getBoolean(Globals.LocationRestart, false) == false
                        ApiSchedular.schedularCall(getActivity());
                    }else {
                        ApiSchedular.schedularCall(getActivity());
//                        ApiSchedular.restartScheduledJob(getActivity());
                    }


                 /*   if (MainBaseActivity.boolean_permission) {
                        Log.e("start", "start");
                        Intent intent = new Intent(getContext(), GoogleService.class);
                        getContext().startService(intent);
                    } else {
                        binding.checkin.setText("Check Out");
                        GlobalStatus = "Toggle_CheckIn";
                        givepermission();
//                        showStatusChangePopup(locationtype);
                    }*/  //todo comment by chanchal

                } else {
                    locationtype = "Stop";
                    Prefs.putString(Globals.locationcondition, "Off");
                    Prefs.putBoolean(Globals.checkIn, false);

                    binding.checkin.setText("Check In");
                    GlobalStatus = "Toggle_CheckOut";
                    givepermission(null);

                    ApiSchedular.cancelScheduledJob(getActivity());

                  /*  if (MainBaseActivity.boolean_permission) {
                        Log.e("Stop", "Stop");
                        Intent intent = new Intent(getContext(), GoogleService.class);
                        getContext().startService(intent);
                    } else {
                        binding.checkin.setText("Check In");
                        GlobalStatus = "Toggle_CheckOut";
//                        givepermission(locationtype, "");
//                        showStatusChangePopup(locationtype);
                        givepermission();
                    }*/   //todo comment by chanchal

                  /*  Prefs.putString(Globals.locationcondition, "Off");
                    locationtype = "Stop";
                    Intent intent = new Intent(getContext(), GoogleService.class);
                    getContext().stopService(intent);*/   //todo comment by me


                }
            }
        });


        binding.meetingmode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Prefs.putString(Globals.MeetingMode, "On");
                    locationtype = "Start";
                } else {
                    Prefs.putString(Globals.MeetingMode, "Off");
                    locationtype = "Stop";
                }
                checkpermission(locationtype);
                callgetlocationApi();
            }
        });
       /* if (Globals.checkInternet(getActivity()))
            loadProfile(Prefs.getString(Globals.EmployeeID, ""));*/
        SetData(obj);

        binding.linearSyncData.setOnClickListener(view ->
        {
            fetchCountryDataFromApi(requireContext());
            fetchIndustryDataFromApi(requireContext());
            fetchLeadTypeDataFromApi(requireContext());
            fetchLeadSourceDataFromApi(requireContext());
            fetchOwnerListDataFromApi(requireContext());
            fetchItemCategoryDataDataFromApi(requireContext());
            fetchBusinessPartnertDataFromApi(requireContext());
            fetchBpTypeDataDataFromApi(requireContext());
            fetchTaxesListFromApi();
            fetchDepartmentDataFromApi();
            fetchRoleFromApi();
            fetchPaymentTermListDataFromApi(requireContext());
        });


        return binding.getRoot();
    }


    public String GlobalStatus = "";

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
                        CountriesDatabase db = CountriesDatabase.getDatabase(getActivity().getApplicationContext());
                        List<CountryData> localData = db.myDataDao().getAll();
                        if (!localData.equals(countrylist)) {
                            db.myDataDao().insertAll(countrylist);
                            countrylistFromLocal.addAll(countrylist);
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
                        LeadTypeDatabase db = LeadTypeDatabase.getDatabase(getActivity().getApplicationContext());
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
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
                        LeadSourceDatabase db = LeadSourceDatabase.getDatabase(getActivity().getApplicationContext());
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
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
                        OwnerDatabase db = OwnerDatabase.getDatabase(getActivity().getApplicationContext());
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
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
                        BusinessPartnerDatabase db = BusinessPartnerDatabase.getDatabase(getActivity().getApplicationContext());
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
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
                        PaymentTermDatabase db = PaymentTermDatabase.getDatabase(getActivity().getApplicationContext());
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
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
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


                        IndustriesDatabase db = IndustriesDatabase.getDatabase(getActivity().getApplicationContext());


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


                        ProductDatabase db = ProductDatabase.getDatabase(getActivity().getApplicationContext());


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
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
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


                        BpTypeDatabase db = BpTypeDatabase.getDatabase(getActivity().getApplicationContext());


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


                }
            }

            @Override
            public void onFailure(Call<BPTypeResponse> call, Throwable t) {
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return industrylist;


    }

    private void fetchDepartmentDataFromApi() {
        ArrayList<DepartMent> taxesList = new ArrayList<>();
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getDepartMent().observe(getViewLifecycleOwner(), new Observer<List<DepartMent>>() {
            @Override
            public void onChanged(@Nullable List<DepartMent> itemsList) {
                taxesList.clear();
                taxesList.addAll(itemsList);
//                TaxItemAdapter adapter = new TaxItemAdapter(context, taxSlabList,itemsObj,TaxListdialog);
//                recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
//                recyclerview.setAdapter(adapter);

                DepartmentDatabase db = DepartmentDatabase.getDatabase(getActivity().getApplicationContext());


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
        model.getTaxList(new ProgressBar(requireContext())).observe(getViewLifecycleOwner(), new Observer<List<TaxItem>>() {
            @Override
            public void onChanged(@Nullable List<TaxItem> itemsList) {
                taxesList.clear();
                taxesList.addAll(itemsList);
//                TaxItemAdapter adapter = new TaxItemAdapter(context, taxSlabList,itemsObj,TaxListdialog);
//                recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
//                recyclerview.setAdapter(adapter);

                TaxItemDatabase db = TaxItemDatabase.getDatabase(getActivity().getApplicationContext());


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
        model.getRoleList().observe(getViewLifecycleOwner(), new Observer<List<Role>>() {
            @Override
            public void onChanged(@Nullable List<Role> itemsList) {
                taxesList.clear();
                taxesList.addAll(itemsList);
//                TaxItemAdapter adapter = new TaxItemAdapter(context, taxSlabList,itemsObj,TaxListdialog);
//                recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
//                recyclerview.setAdapter(adapter);

                RoleDatabase db = RoleDatabase.getDatabase(getActivity().getApplicationContext());


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


    public static List<MapData> mdplist = new ArrayList<>();


    private void callgetlocationApi() {
        binding.loadingback.setVisibility(View.VISIBLE);
        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("Emp_Id", Prefs.getString(Globals.SalesEmployeeCode, ""));
        mapData.put("UpdateDate", Globals.getTodaysDatervrsfrmt());
        mapData.put("shape", "meeting");

        Call<MapResponse> call = NewApiClient.getInstance().getApiService().getmaplocation(mapData);
        call.enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                if (response != null) {
                    binding.loadingback.setVisibility(View.GONE);
                    mdplist.clear();
                    mdplist.addAll(response.body().getValue());
                    Log.e("success", "success");


                }
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {
                binding.loadingback.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }

    private void givepermission(SweetAlertDialog sDialog) {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                            Log.e(TAG, "onComplete: " + "givepermission");
                            getmyCurrentLocation(locationtype, sDialog);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                })
                .onSameThread()
                .check();
    }

    private void SetData(NewLoginData obj) {
        binding.employee.setText("Employee ID : EMP00" + obj.getId());
        binding.phone.setText(obj.getMobile());
        binding.mail.setText(obj.getEmail());
        if (obj.getActive().equalsIgnoreCase("tYES"))
            binding.employeeStatus.setText("Employee Status : Active");
        else
            binding.employeeStatus.setText("Employee Status : InActive");

        binding.appRole.setText("Designation :" + obj.getRole());
        binding.designation.setText(obj.getPosition());
        binding.companyName.setText("Company Name :" + getResources().getString(R.string.app_name));
        //  reporting_to.setText("Reporting to : "+ obj.getReportingName());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                /*Prefs.clear();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/
                openConfirmDialog();

                break;
            case R.id.mode:

                break;
            case R.id.edit_profile:
                if (mdplist.size() > 0) {
                    Bundle b = new Bundle();
                    b.putSerializable("MapList", (Serializable) mdplist);
                    Intent intent = new Intent(getContext(), MyMapLocation.class);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "No Location Found", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.currentlocation:
                getCurrentLocation(getContext());
/*
//                  getcurrentLocation();
                LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                // get the last know location from your location manager.
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                  // now get the lat/lon from the location and do something with it.
                  Log.e("location","Current Location: " + location.getLatitude() + ", " + location.getLongitude());

                  Geocoder geocoder;
                  List<Address> addresses = null;
                  geocoder = new Geocoder(getContext(), Locale.getDefault());
                  try {
                      addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                  }catch (Exception e){
                      Log.e("address",e.getLocalizedMessage());
                  }
                  String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                  String uri = "https://www.google.com/maps/?q=" + location.getLatitude()+ "," +location.getLongitude() ;
                  Intent sendIntent = new Intent();
                  sendIntent.setAction(Intent.ACTION_SEND);
                  sendIntent.putExtra(Intent.EXTRA_TEXT, uri);
                 // sendIntent.putExtra(Intent.EXTRA_TEXT,address);
                  sendIntent.setType("text/plain");
                  sendIntent.setPackage("com.whatsapp");
                  startActivity(sendIntent);*/

                break;
        }

    }

    private void checkpermission(String type) {
        if (ContextCompat.checkSelfPermission(
                getActivity(),
                Manifest.permission
                        .ACCESS_FINE_LOCATION)
                == PackageManager
                .PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                getActivity(),
                Manifest.permission
                        .ACCESS_COARSE_LOCATION)
                == PackageManager
                .PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            Log.e(TAG, "onComplete: " + "checkpermission");
            getmyCurrentLocation(type, null);
        } else {
            // When permission is not granted
            // Call method
            requestPermissions(
                    new String[]{
                            Manifest.permission
                                    .ACCESS_FINE_LOCATION,
                            Manifest.permission
                                    .ACCESS_COARSE_LOCATION},
                    100);
        }

    }

    @SuppressLint("MissingPermission")
    private void getmyCurrentLocation(String type, SweetAlertDialog sDialog) {
        // Initialize Location manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {

                            // Initialize location
                            Location location = task.getResult();
                            // Check condition
                            if (location != null) {
                                Log.e(TAG, "onComplete: " + "locationNotNull");
                                // When location result is not
                                // null set latitude
                                Geocoder geocoder  = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> addresses = null;


                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String city = addresses.get(0).getLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();
                                    Log.e(TAG, "onComplete: Call Api" );
                                    callApi(location.getLatitude(), location.getLongitude(), type, address, sDialog);
                                } catch (IOException e) {

                                    e.printStackTrace();
                                }


                            } else {
                                Log.e(TAG, "onComplete: " + "locationNull");
                                // When location result is null
                                // initialize location request
                                LocationRequest locationRequest = new LocationRequest()
                                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                        .setInterval(10000)
                                        .setFastestInterval(
                                                1000)
                                        .setNumUpdates(1);

                                // Initialize location call back
                                LocationCallback
                                        locationCallback
                                        = new LocationCallback() {
                                    @Override
                                    public void
                                    onLocationResult(
                                            LocationResult
                                                    locationResult) {
                                        // Initialize
                                        // location
                                        Location location1
                                                = locationResult
                                                .getLastLocation();

                                        Geocoder geocoder;
                                        List<Address> addresses = null;
                                        geocoder = new Geocoder(getContext(), Locale.getDefault());

                                        try {
                                            addresses = geocoder.getFromLocation(location1
                                                    .getLatitude(), location1
                                                    .getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        String city = addresses.get(0).getLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                        String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();
                                        callApi(location1
                                                .getLatitude(), location1
                                                .getLongitude(), type, address, sDialog);
                                    }
                                };

                                // Request location updates
                                client.requestLocationUpdates(
                                        locationRequest,
                                        locationCallback,
                                        Looper.myLooper());
                            }
                        }
                    });
        } else {
            // When location service is not enabled
            // open location setting
            startActivity(
                    new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            .setFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void callApi(double latitude, double longitude, String type, String address, SweetAlertDialog sDialog) {
        binding.loadingback.setVisibility(View.VISIBLE);
        MapData mapData = new MapData();
        mapData.setEmp_Id(Prefs.getString(Globals.SalesEmployeeCode, ""));//Globals.MYEmployeeID
        mapData.setEmp_Name(Prefs.getString(Globals.SalesEmployeeName, "")); //Employee_Name
        mapData.setLat(String.valueOf(latitude));
        mapData.setLong(String.valueOf(longitude));
        mapData.setUpdateDate(Globals.getTodaysDatervrsfrmt());
        mapData.setUpdateTime(Globals.getCurrentTimeIn_hh_mm_ss());
        mapData.setAddress(address);
        mapData.setShape("meeting");
        mapData.setType(type);
        mapData.setRemark("");
        Call<MapResponse> call = NewApiClient.getInstance().getApiService().sendMaplatlong(mapData);
        call.enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                try {
                    if (response.body().getStatus() == 200) {
                        binding.loadingback.setVisibility(View.GONE);
                        Log.e("success", "success_checkIN/Out");


                        if (logout == true){

                            ApiSchedular.cancelScheduledJob(getActivity());

                            sDialog.dismissWithAnimation();
                            Prefs.clear();
                            Intent intent = new Intent(getActivity(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }else {
                        binding.loadingback.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse: Error"+response.body().getMessage() );
                    }

                } catch (Exception e) {
                    binding.loadingback.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {
                binding.loadingback.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0)
                && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call  method
            Log.e(TAG, "onComplete: " + "onRequestPermissionsResult");
            getmyCurrentLocation(locationtype, null);
        } else {
            // When permission are denied
            // Display toast
            Toast
                    .makeText(getActivity(),
                            "Permission denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private boolean logout = false;

    SweetAlertDialog swDialog ;
    private void openConfirmDialog() {

        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You Want to Logout!")
                .setConfirmText("Yes,LogOut!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

//                        swDialog = new SweetAlertDialog(getContext());

                        if (Prefs.getString(Globals.locationcondition, "Off").equalsIgnoreCase("On")) {
                            logout = true;
                            locationtype = "Stop";
                            givepermission(sDialog);

                        }else {
                            sDialog.dismissWithAnimation();
                            Prefs.clear();
                            Intent intent = new Intent(getActivity(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }


                       /* sDialog.dismissWithAnimation();
                        Prefs.clear();
                        Intent intent = new Intent(getActivity(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);*/


                    }
                })

                .show();

    }

    private void getCurrentLocation(Context context) {
        SingleShotLocationProvider.requestSingleUpdate(context,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        //      Log.d("Location", "my location is " + location.latitude);
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder(getContext(), Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (Exception e) {
                            Log.e("address", e.getLocalizedMessage());
                        }
                        String address = addresses.get(0).getAddressLine(0);// If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                     /*   String uri = "https://www.google.com/maps/?q=" + location.latitude+ "," +location.longitude ;
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, uri);
                        // sendIntent.putExtra(Intent.EXTRA_TEXT,address);
                        sendIntent.setType("text/plain");
                        sendIntent.setPackage("com.whatsapp");
                        startActivity(sendIntent);*/
                    }
                });
    }


    private void loadProfile(String userID) {
        EmpDetails empDetails = new EmpDetails();
        empDetails.setId(Integer.parseInt(userID));
        Call<EmployeeProfile> call = NewApiClient.getInstance().getApiService().getProfileDetail(empDetails);
        call.enqueue(new Callback<EmployeeProfile>() {
            @Override
            public void onResponse(Call<EmployeeProfile> call, Response<EmployeeProfile> response) {
                if (response != null) {
                    if (response.body().getData().size() > 0) {

//                    Prefs.putString(Globals.USERNAME,response.body().getValue().get(0).getUserId().getUserName());

                        binding.phone.setText(response.body().getData().get(0).getMobile());
                        binding.mail.setText(response.body().getData().get(0).getEmail());
                        if (response.body().getData().get(0).getActive().equalsIgnoreCase("tYes"))
                            binding.employeeStatus.setText("Employee Status : Yes");
                        else
                            binding.employeeStatus.setText("Employee Status : No");
                        binding.employee.setText("Employee ID : " + response.body().getData().get(0).getEmployeeID());
                        binding.designation.setText(response.body().getData().get(0).getRole());
                        binding.branchName.setText(response.body().getData().get(0).getBranch());
                        binding.reportingTo.setText(response.body().getData().get(0).getReportingTo());


                    }
                }
            }

            @Override
            public void onFailure(Call<EmployeeProfile> call, Throwable t) {

            }
        });
    }
}