package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NotificationResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<NotificatonValue> data = null;
    private final static long serialVersionUID = -4394025924824530640L;

    /**
     * No args constructor for use in serialization
     *
     */
    public NotificationResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public NotificationResponse(String message, Integer status, List<NotificatonValue> data) {
        super();
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<NotificatonValue> getData() {
        return data;
    }

    public void setData(List<NotificatonValue> data) {
        this.data = data;
    }

}
