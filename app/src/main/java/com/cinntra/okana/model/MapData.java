package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MapData implements Serializable {

    @SerializedName("Lat")
    String Lat;
    @SerializedName("Long")
    String Long;
    @SerializedName("Emp_Id")
    String Emp_Id;
    @SerializedName("Emp_Name")
    String Emp_Name;
    @SerializedName("UpdateDate")
    String UpdateDate;
    @SerializedName("UpdateTime")
    String UpdateTime;
    @SerializedName("Address")
    String Address;
    @SerializedName("type")
    String type;
    @SerializedName("shape")
    String shape;
    @SerializedName("remark")
    String remark;


    public MapData(String lat, String aLong, String emp_Id, String emp_Name, String updateDate, String updateTime) {
        Lat = lat;
        Long = aLong;
        Emp_Id = emp_Id;
        Emp_Name = emp_Name;
        UpdateDate = updateDate;
        UpdateTime = updateTime;
    }

    public MapData() {
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getEmp_Id() {
        return Emp_Id;
    }

    public void setEmp_Id(String emp_Id) {
        Emp_Id = emp_Id;
    }

    public String getEmp_Name() {
        return Emp_Name;
    }

    public void setEmp_Name(String emp_Name) {
        Emp_Name = emp_Name;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
