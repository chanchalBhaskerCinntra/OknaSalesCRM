package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LeadTypeResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<LeadTypeData> data = null;
    private final static long serialVersionUID = 7845831848307978692L;

    /**
     * No args constructor for use in serialization
     *
     */
    public LeadTypeResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public LeadTypeResponse(String message, Integer status, List<LeadTypeData> data) {
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

    public List<LeadTypeData> getData() {
        return data;
    }

    public void setData(List<LeadTypeData> data) {
        this.data = data;
    }

}
