package com.cinntra.okana.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PayMentTermsDetail implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<PayMentTerm> data = null;
    private final static long serialVersionUID = -8597635123082354506L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PayMentTermsDetail() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public PayMentTermsDetail(String message, Integer status, List<PayMentTerm> data) {
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

    public List<PayMentTerm> getData() {
        return data;
    }

    public void setData(List<PayMentTerm> data) {
        this.data = data;
    }

}