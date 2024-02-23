package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CampaignResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<CampaignModel> data = null;
    private final static long serialVersionUID = -3509678452236860349L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CampaignResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public CampaignResponse(String message, Integer status, List<CampaignModel> data) {
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

    public List<CampaignModel> getData() {
        return data;
    }

    public void setData(List<CampaignModel> data) {
        this.data = data;
    }

}
