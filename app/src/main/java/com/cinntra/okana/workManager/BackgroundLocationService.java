package com.cinntra.okana.workManager;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.Dashboard;
import com.cinntra.okana.globals.Globals;


public class BackgroundLocationService extends Service {
    private LocationManager locationManager;
    private double totalDistanceInMeters = 5.0;
    private Location lastLocation;
    private LocationListener locationListener;
    private static final long MIN_TIME_BETWEEN_UPDATES = 1000L; // 1 second (adjust as needed)
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10.0f; // 10 meters (adjust as needed)
    private static final String TAG = "BackgroundLocationServi";

    @Override
    public IBinder onBind(Intent intent) {
        return new LocationServiceBinder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.e(TAG, "onCreate: "+ "Location Saring == >" );
        createNotificationChannel();
        startLocationUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();
        return START_STICKY;
    }

    private void showNotification() {
        Intent notificationIntent = new Intent(this, Dashboard.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, Globals.channelId)
                .setContentTitle("Location Sharing")
                .setContentText("Your Current Location is Sharing in every 15 min.")
                .setSmallIcon(R.mipmap.ic_launcher_okna)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(Globals.notificationId, notification);
        } else {
            startForeground(Globals.notificationId, notification);
        }


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Location Sharing";
            String descriptionText = "Your Current Location is Sharing in every 15 min.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(Globals.channelId, name, importance);
            channel.setDescription(descriptionText);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startLocationUpdates() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation != null) {
                    float distanceInMeters = lastLocation.distanceTo(location);

                    Log.e(TAG, "onLocationChanged: " + lastLocation.getLatitude());
                    Log.e(TAG, "onLocationChanged: " + lastLocation.getLongitude());

                    totalDistanceInMeters += distanceInMeters;
                }
                lastLocation = location;

                // You can update your UI or perform other operations with the current location here
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BETWEEN_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                locationListener
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop location updates when the service is destroyed
        locationManager.removeUpdates(locationListener);
    }


}
