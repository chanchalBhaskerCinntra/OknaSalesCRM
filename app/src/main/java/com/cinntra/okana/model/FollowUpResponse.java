package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FollowUpResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<FollowUpData> data = null;
    private final static long serialVersionUID = 7692194984111109293L;

    /**
     * No args constructor for use in serialization
     *
     */
    public FollowUpResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public FollowUpResponse(String message, Integer status, List<FollowUpData> data) {
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

    public List<FollowUpData> getData() {
        return data;
    }

    public void setData(List<FollowUpData> data) {
        this.data = data;
    }

}
