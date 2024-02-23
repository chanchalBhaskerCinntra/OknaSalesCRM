package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EmployeeProfile implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<EmpDetails> data = null;
    private final static long serialVersionUID = 3222073499862784573L;

    /**
     * No args constructor for use in serialization
     */
    public EmployeeProfile() {
    }

    /**
     * @param data
     * @param message
     * @param status
     */
    public EmployeeProfile(String message, Integer status, List<EmpDetails> data) {
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

    public List<EmpDetails> getData() {
        return data;
    }

    public void setData(List<EmpDetails> data) {
        this.data = data;
    }

}
