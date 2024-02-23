package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentRespnse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<PaymentDetailsModel> data = null;
    private final static long serialVersionUID = -2175471236340733027L;

    /**
     * No args constructor for use in serialization
     */
    public PaymentRespnse() {
    }

    /**
     * @param data
     * @param message
     * @param status
     */
    public PaymentRespnse(String message, Integer status, List<PaymentDetailsModel> data) {
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

    public List<PaymentDetailsModel> getData() {
        return data;
    }

    public void setData(List<PaymentDetailsModel> data) {
        this.data = data;
    }

}
