package com.cinntra.okana.workManager;

import android.os.Binder;

public class LocationServiceBinder extends Binder {
    private BackgroundLocationService service;

    public LocationServiceBinder(BackgroundLocationService service) {
        this.service = service;
    }

    public BackgroundLocationService getService() {
        return service;
    }
}