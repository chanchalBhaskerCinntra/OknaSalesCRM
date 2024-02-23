package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CatID implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedTime")
    @Expose
    private String createdTime;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedTime")
    @Expose
    private String updatedTime;
    private final static long serialVersionUID = 4631990741344095369L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CatID() {
    }

    /**
     *
     * @param updatedTime
     * @param createdDate
     * @param createdTime
     * @param id
     * @param updatedDate
     * @param categoryName
     * @param status
     */
    public CatID(Integer id, String categoryName, Integer status, String createdDate, String createdTime, String updatedDate, String updatedTime) {
        super();
        this.id = id;
        this.categoryName = categoryName;
        this.status = status;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.updatedDate = updatedDate;
        this.updatedTime = updatedTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

}
