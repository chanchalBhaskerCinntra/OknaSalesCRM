package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalesEmployeeItem implements Serializable {

    @SerializedName("SalesEmployeeCode")
    @Expose
    String SalesEmployeeCode;
    @SerializedName("SalesEmployeeName")
    @Expose
    String SalesEmployeeName;
    @SerializedName("userName")
    @Expose
    String userName;
    @SerializedName("role")
    @Expose
    String role;
    @SerializedName("month")
    @Expose
    String month;
    @SerializedName("Emp")
    @Expose
    String Emp;
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("Type")
    @Expose
    String Type;
    String firstName;
    @SerializedName("id")
    @Expose
    Integer id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSalesEmployeeCode()
     {
    return SalesEmployeeCode;
     }

    public void setSalesEmployeeCode(String salesEmployeeCode)
     {
    SalesEmployeeCode = salesEmployeeCode;
     }

    public String getSalesEmployeeName() {
        return SalesEmployeeName;
    }

    public void setSalesEmployeeName(String salesEmployeeName) {
        SalesEmployeeName = salesEmployeeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEmp() {
        return Emp;
    }

    public void setEmp(String emp) {
        Emp = emp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
