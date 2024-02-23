package com.cinntra.okana.globals;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.CountDownTimer;

import androidx.multidex.MultiDex;

import com.cinntra.okana.receivers.ConnectivityReceiver;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.pixplicity.easyprefs.library.Prefs;

public class MyApp extends Application {
    private FirebaseAnalytics mFirebaseAnalytics;
    public static MyApp mInstance;
    public static CountDownTimer timer;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(mInstance);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        new Prefs.Builder()
        .setContext(this)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(getPackageName())
        .setUseDefaultSharedPreference(true)
         .build();
    }

    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public static void installServiceProviderIfNeeded(Context context) {
        try {
            ProviderInstaller.installIfNeeded(context);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();

            // Prompt the user to install/update/enable Google Play services.
         //   GooglePlayServicesUtil.showErrorNotification(e.getConnectionStatusCode(), context);

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
