package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotificationData implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("SourceType")
    @Expose
    private String sourceType;
    @SerializedName("SourceID")
    @Expose
    private String sourceID;
    @SerializedName("Emp")
    @Expose
    private String emp;
    @SerializedName("Read")
    @Expose
    private String read;
    @SerializedName("SourceTime")
    @Expose
    private String sourceTime;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedTime")
    @Expose
    private String createdTime;
    private final static long serialVersionUID = 4052727032058761998L;

    /**
     * No args constructor for use in serialization
     *
     */
    public NotificationData() {
    }

    /**
     *
     * @param sourceID
     * @param read
     * @param createdDate
     * @param sourceType
     * @param sourceTime
     * @param description
     * @param emp
     * @param createdTime
     * @param id
     * @param title
     * @param type
     */
    public NotificationData(Integer id, String title, String description, String type, String sourceType, String sourceID, String emp, String read, String sourceTime, String createdDate, String createdTime) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.sourceType = sourceType;
        this.sourceID = sourceID;
        this.emp = emp;
        this.read = read;
        this.sourceTime = sourceTime;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getEmp() {
        return emp;
    }

    public void setEmp(String emp) {
        this.emp = emp;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getSourceTime() {
        return sourceTime;
    }

    public void setSourceTime(String sourceTime) {
        this.sourceTime = sourceTime;
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

}
