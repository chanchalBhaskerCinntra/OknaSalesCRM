package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LogIn implements Serializable {
    @SerializedName("CompanyDB")
    String CompanyDB;
    @SerializedName("Password")
    String Password;
    @SerializedName("UserName")
     String UserName;


    public String getCompanyDB() {
        return CompanyDB;
    }

    public void setCompanyDB(String companyDB) {
        CompanyDB = companyDB;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
