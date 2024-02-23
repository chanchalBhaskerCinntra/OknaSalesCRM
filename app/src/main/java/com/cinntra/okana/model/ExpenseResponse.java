package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpenseResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private List<ExpenseDataModel> data = null;
    private final static long serialVersionUID = 299612448943767153L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ExpenseResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public ExpenseResponse(String message, int status, List<ExpenseDataModel> data) {
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

    public List<ExpenseDataModel> getData() {
        return data;
    }

    public void setData(List<ExpenseDataModel> data) {
        this.data = data;
    }

}
