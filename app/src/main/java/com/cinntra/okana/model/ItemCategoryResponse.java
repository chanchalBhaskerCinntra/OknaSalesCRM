package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemCategoryResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<ItemCategoryData> data = null;
    private final static long serialVersionUID = 7529420726937608505L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ItemCategoryResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public ItemCategoryResponse(String message, Integer status, List<ItemCategoryData> data) {
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

    public List<ItemCategoryData> getData() {
        return data;
    }

    public void setData(List<ItemCategoryData> data) {
        this.data = data;
    }

}
