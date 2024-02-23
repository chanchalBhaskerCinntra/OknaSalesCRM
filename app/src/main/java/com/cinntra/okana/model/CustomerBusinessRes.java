package com.cinntra.okana.model;

import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomerBusinessRes implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private List<BusinessPartnerData> data = null;
    private final static long serialVersionUID = -637633141134967407L;

    /**
     * No args constructor for use in serialization
     */
    public CustomerBusinessRes() {
    }

    /**
     * @param data
     * @param message
     * @param status
     */
    public CustomerBusinessRes(String message, int status, List<BusinessPartnerData> data) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BusinessPartnerData> getData() {
        return data;
    }

    public void setData(List<BusinessPartnerData> data) {
        this.data = data;
    }

}
