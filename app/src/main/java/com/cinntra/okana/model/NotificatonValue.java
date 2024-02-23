package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificatonValue implements Serializable {

    @SerializedName("notification")
    @Expose
    private NotificationData notification;
    @SerializedName("source")
    @Expose
    private EventValue source;


    public NotificatonValue(NotificationData notification, EventValue source) {
        this.notification = notification;
        this.source = source;
    }

    public NotificatonValue() {
    }

    public NotificationData getNotification() {
        return notification;
    }

    public void setNotification(NotificationData notification) {
        this.notification = notification;
    }

    public EventValue getSource() {
        return source;
    }

    public void setSource(EventValue source) {
        this.source = source;
    }
}
