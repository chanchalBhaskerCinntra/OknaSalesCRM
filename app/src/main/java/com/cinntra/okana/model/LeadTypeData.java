package com.cinntra.okana.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "data_lead_type")
public class LeadTypeData implements Serializable {


    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedTime")
    @Expose
    private String createdTime;

    private boolean ischecked = false;
    private final static long serialVersionUID = 7065155881312542169L;

    /**
     * No args constructor for use in serialization
     */
    public LeadTypeData() {
    }

    /**
     * @param createdDate
     * @param name
     * @param createdTime
     * @param id
     */
    public LeadTypeData(Integer id, String name, String createdDate, String createdTime) {
        super();
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }
}