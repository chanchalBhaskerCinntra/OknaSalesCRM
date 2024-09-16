package com.cinntra.okana.services;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.cinntra.okana.fragments.Settings;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.MapData;
import com.cinntra.okana.model.MapResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleService extends Service implements LocationListener {

    FusedLocationProviderClient client;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 1000 * 60 * 10;
    //    long notify_interval = 1000*5;
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;
    private final IBinder mbinder = new MyBinder();
    private static final String CHANNEL_ID = "2";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    public GoogleService() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
//        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mTimer = new Timer();
        buildnotification();
//        mTimer.schedule(new TimerTaskToGetLocation(), 1000, notify_interval);
//        intent = new Intent(str_receiver);
    }

    @SuppressLint("LaunchActivityFromNotification")
    private void buildnotification() {
        String stop = "Stop";


        PendingIntent broadcastintent = PendingIntent.getBroadcast(this, 0, new Intent(stop), PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Sharing")
                .setContentText("Location Sharing is On")
                .setOngoing(true)
                .setContentIntent(broadcastintent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Standalone", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setShowBadge(false);
            notificationChannel.setDescription("Location Tracking is working");
            notificationChannel.setSound(null, null);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        startForeground(1, builder.build());

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        /*if (Prefs.getString(Globals.locationcondition, "Off").equalsIgnoreCase("On") || Prefs.getString(Globals.MeetingMode, "Off").equalsIgnoreCase("On")) {
            long ct = System.currentTimeMillis(); //get current time
            Intent restartService = new Intent(getApplicationContext(),
                    GoogleService.class);
            PendingIntent restartServicePI = PendingIntent.getService(
                    getApplicationContext(), 0, restartService,
                    PendingIntent.FLAG_MUTABLE);

            AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            mgr.setRepeating(AlarmManager.RTC_WAKEUP, ct, 1 * 1000, restartServicePI);
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* if (Prefs.getString(Globals.locationcondition, "Off").equalsIgnoreCase("On") || Prefs.getString(Globals.MeetingMode, "Off").equalsIgnoreCase("On")) {

            startService(new Intent(this, GoogleService.class));
        } else {
            stopService(new Intent(this, GoogleService.class));

        }*/
    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
//                    getmyCurrentLocation(Settings.locationtype);

                }
            });

        }
    }
/*
    @SuppressLint("MissingPermission")
    private void getmyCurrentLocation(String type) {
        // Initialize Location manager
        LocationManager locationManager
                = (LocationManager) getApplicationContext()
                .getSystemService(
                        Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            client.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<Location> task) {

                            // Initialize location
                            Location location
                                    = task.getResult();
                            // Check condition
                            if (location != null) {
                                // When location result is not
                                // null set latitude
                                Geocoder geocoder;
                                List<Address> addresses = null;
                                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                                try {
                                    addresses = geocoder.getFromLocation(location
                                            .getLatitude(), location
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
                                fn_update(location);
                                LatLng destination = new LatLng(location.getLatitude(), location.getLongitude());
                                LatLng source = new LatLng(Prefs.getDouble(Globals.Lattitude, 0.0), Prefs.getDouble(Globals.Longitude, 0.0));
                                double distance = SphericalUtil.computeDistanceBetween(source, destination);
                                Log.e("distance", String.valueOf(distance));
                                Log.e("afterlatlong", String.valueOf(location.getLatitude()) + "==>" + location.getLongitude());
                                Log.e("prelatlong", String.valueOf(Prefs.getDouble(Globals.Lattitude, 0.0)) + "==>" + Prefs.getDouble(Globals.Longitude, 0.0));
                                if (Prefs.getDouble(Globals.Lattitude, 0.0) == 0.0) {
//                                    callApi(location.getLatitude(), location.getLongitude(), type, address);
                                } else if (distance > 500) {

//                                    callApi(location.getLatitude(), location.getLongitude(), type, address);
                                } else {
                                }
                            } else {
                                // When location result is null
                                // initialize location request
                                LocationRequest locationRequest = new LocationRequest()
                                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                        .setInterval(1000)
                                        .setFastestInterval(
                                                3000)
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
                                        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

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
                                        fn_update(location1);
                                        LatLng destination = new LatLng(location1.getLatitude(), location1.getLongitude());
                                        LatLng source = new LatLng(Prefs.getDouble(Globals.Lattitude, 0.0), Prefs.getDouble(Globals.Longitude, 0.0));
                                        double distance = SphericalUtil.computeDistanceBetween(source, destination);
                                        Log.e("distance", String.valueOf(distance));
                                        String s = "" + location1.getLatitude();
                                        Log.e("afterlatlong", String.valueOf(location1.getLatitude()) + "==>" + location1.getLongitude());
                                        Log.e("prelatlong", String.valueOf(Prefs.getDouble(Globals.Lattitude, 0.0)) + "==>" + Prefs.getDouble(Globals.Longitude, 0.0));
                                        if (Prefs.getDouble(Globals.Lattitude, 0.0) == 0.0) {
//                                            callApi(location1.getLatitude(), location1.getLongitude(), type, address);
                                        } else if (distance > 500) {
                                            Log.e("distance", String.valueOf(distance));

//                                            callApi(location1.getLatitude(), location1.getLongitude(), type, address);
                                        } else {
                                            Log.e("distance", String.valueOf(distance));
                                        }
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
    }*/

    private void fn_update(Location location) {

        intent.putExtra("latutide", location.getLatitude() + "");
        intent.putExtra("longitude", location.getLongitude() + "");
        sendBroadcast(intent);
    }
    /*private void callApi(double latitude, double longitude, String type, String address) {
        Log.e("savelatlong", String.valueOf(latitude) + "==>" + longitude);

        Prefs.putDouble(Globals.Lattitude, latitude);
        Prefs.putDouble(Globals.Longitude, longitude);
        MapData mapData = new MapData();
        mapData.setEmp_Id(Globals.TeamEmployeeID);
        mapData.setEmp_Name(Prefs.getString(Globals.Employee_Name, ""));
        mapData.setLat(String.valueOf(latitude));
        mapData.setLong(String.valueOf(longitude));
        mapData.setUpdateDate(Globals.getTodaysDatervrsfrmt());
        mapData.setUpdateTime(Globals.getCurrentTimeIn_hh_mm_ss());
        mapData.setAddress(address);
        mapData.setShape("location");
        mapData.setType("");
        mapData.setRemark("");
        Call<MapResponse> call = NewApiClient.getInstance().getApiService().sendMaplatlong(mapData);
        call.enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                if (response != null) {

                    Log.e("success", "success");


                }
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {

            }
        });
    }*/


    private class MyBinder extends Binder {
       /* public GoogleService getService() {
            return GoogleService.this;
        }*/
    }
}
