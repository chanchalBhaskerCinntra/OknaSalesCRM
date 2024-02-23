package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CampaignListModel implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<CampaignListResponse> data = null;
    private final static long serialVersionUID = -1411898342252452692L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CampaignListModel() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public CampaignListModel(String message, Integer status, List<CampaignListResponse> data) {
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

    public List<CampaignListResponse> getData() {
        return data;
    }

    public void setData(List<CampaignListResponse> data) {
        this.data = data;
    }

}
