package com.cinntra.okana.workManager;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.LocationLatLongResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.TokenExpireModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkManagerApplication extends JobService {
    FusedLocationProviderClient fusedLocationProviderClient;
    Context context;

    @Override
    public boolean onStartJob(JobParameters params) {
        context = getApplicationContext();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        try {
            Log.d("JobSchedular===>", "Schedular Called");
            getMyCurrentLocation();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("JobService===>", "Stop Service----");
        return false;
    }


    private void getMyCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(AppCompatActivity.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            if (ActivityCompat.checkSelfPermission(WorkManagerApplication.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WorkManagerApplication.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder;
                        List<Address> addresses = new ArrayList<>();
                        geocoder = new Geocoder(WorkManagerApplication.this, Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String address = addresses.get(0).getAddressLine(0);
                            Log.e("manager_current_lat", String.valueOf(location.getLatitude()));
                            Log.e("manager_current_long", String.valueOf(location.getLongitude()));
                            Log.e("address", address);
//                            if (Prefs.getBoolean(Globals.Location_Boolean_MInutes, false) && Prefs.getBoolean(Globals.checkIn, true))
                                bindLocationApi(location.getLatitude(), location.getLongitude(), address);//address
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        LocationRequest locationRequest;
                        locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000); // Update interval in milliseconds (e.g., every 10 seconds)
                        locationRequest.setFastestInterval(1000); // Fastest interval for updates
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Request high-accuracy locations


                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                if (locationResult != null) {
                                    Location location = locationResult.getLastLocation();
                                    Geocoder geocoder = new Geocoder(WorkManagerApplication.this, Locale.getDefault());
                                    List<Address> addresses = new ArrayList<>();
                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        String address = addresses.get(0).getAddressLine(0);

                                        bindLocationApi(location.getLatitude(), location.getLongitude(), address);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                                super.onLocationResult(locationResult);
                            }

                        };
                        // Request location updates
                        if (ActivityCompat.checkSelfPermission(WorkManagerApplication.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WorkManagerApplication.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }

            });
        }
    }

    private void bindLocationApi(double latitude, double longitude, String address) {


        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        float batteryPercentage = 0.0f;
        // Check if the battery status is available
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            batteryPercentage = (float) level / (float) scale * 100;

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
            boolean isFull = status == BatteryManager.BATTERY_STATUS_FULL;

            // You can now use batteryPercentage, isCharging, and isFull as needed
        }

        Log.e("TAG====>", "bindLocationApi: " + Prefs.getString(Globals.EmployeeID, ""));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Lat", String.valueOf(latitude));
        jsonObject.addProperty("Long", String.valueOf(longitude));
        jsonObject.addProperty("Address", address);
        jsonObject.addProperty("Emp_Id", Prefs.getString(Globals.SalesEmployeeCode, "")); //Prefs.getString(Globals.SalesEmployeeCode, "")
        jsonObject.addProperty("Emp_Name", Prefs.getString(Globals.SalesEmployeeName, "")); //Prefs.getString(Globals.Employee_Name, "")
        jsonObject.addProperty("UpdateDate", Globals.getTodaysDatervrsfrmt());
        jsonObject.addProperty("UpdateTime", Globals.getCurrentTimeIn_hh_mm_ss_aa()); //getTCurrentTime
        jsonObject.addProperty("type", "");
        jsonObject.addProperty("shape", "location");//meeting
        jsonObject.addProperty("remark", "Battery Percentage " + batteryPercentage);

        Log.d("jsonObject === >", "bindLocationApi: " + jsonObject);


        Call<LocationLatLongResponse> call = NewApiClient.getInstance().getApiService().getCurrentLocationLatLong(jsonObject);
        call.enqueue(new Callback<LocationLatLongResponse>() {
            @Override
            public void onResponse(Call<LocationLatLongResponse> call, Response<LocationLatLongResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
//                            Prefs.putBoolean(Globals.Location_Boolean_MInutes, true);
                            if (response.body().getData().size() > 0 && response.body().getData() != null) {
//                                Toast.makeText(WorkManagerApplication.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Globals.setmessage(getApplicationContext());
                            }

                        }
                    } else if (response.body().getStatus() == 301) {
                        Gson gson = new GsonBuilder().create();
                        TokenExpireModel mError = new TokenExpireModel();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, TokenExpireModel.class);
                            Toast.makeText(WorkManagerApplication.this, mError.getDetail(), Toast.LENGTH_LONG).show();
//                            Globals.logoutScreen(context);
                        } catch (IOException e) {
                            // handle failure to read error
                        }
                    } else {
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(WorkManagerApplication.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LocationLatLongResponse> call, Throwable t) {
                Toast.makeText(WorkManagerApplication.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
