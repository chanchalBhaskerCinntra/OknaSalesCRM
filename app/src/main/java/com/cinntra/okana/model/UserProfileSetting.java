package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserProfileSetting implements Serializable {

    @SerializedName("Users")
    UserId userId;
    @SerializedName("EmployeesInfo")
    EmployeesInfo employeesInfo;

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public EmployeesInfo getEmployeesInfo() {
        return employeesInfo;
    }

    public void setEmployeesInfo(EmployeesInfo employeesInfo) {
        this.employeesInfo = employeesInfo;
    }
}
