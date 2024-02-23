package com.cinntra.okana.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactPerson implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<ContactPersonData> data = null;
    private final static long serialVersionUID = 6127017541355153356L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ContactPerson() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public ContactPerson(String message, Integer status, List<ContactPersonData> data) {
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

    public List<ContactPersonData> getData() {
        return data;
    }

    public void setData(List<ContactPersonData> data) {
        this.data = data;
    }

}

