package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InventoryResponse implements Serializable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<InventoryData> data = null;
    private final static long serialVersionUID = -7357713952718712484L;

    /**
     * No args constructor for use in serialization
     *
     */
    public InventoryResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public InventoryResponse(String message, Integer status, List<InventoryData> data) {
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

    public List<InventoryData> getData() {
        return data;
    }

    public void setData(List<InventoryData> data) {
        this.data = data;
    }

}
