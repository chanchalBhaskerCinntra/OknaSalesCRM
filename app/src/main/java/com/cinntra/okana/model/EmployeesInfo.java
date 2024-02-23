package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmployeesInfo implements Serializable {

    @SerializedName("JobTitle")
    String JobTitle;
    @SerializedName("HomeStreet")
    String HomeStreet;
    @SerializedName("HomeZipCode")
    String HomeZipCode;
    @SerializedName("HomeState")
    String HomeState;
    @SerializedName("HomeCountry")
    String HomeCountry;
    @SerializedName("Active")
    String Active;


    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getHomeStreet() {
        return HomeStreet;
    }

    public void setHomeStreet(String homeStreet) {
        HomeStreet = homeStreet;
    }

    public String getHomeZipCode() {
        return HomeZipCode;
    }

    public void setHomeZipCode(String homeZipCode) {
        HomeZipCode = homeZipCode;
    }

    public String getHomeState() {
        return HomeState;
    }

    public void setHomeState(String homeState) {
        HomeState = homeState;
    }

    public String getHomeCountry() {
        return HomeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        HomeCountry = homeCountry;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }
}
