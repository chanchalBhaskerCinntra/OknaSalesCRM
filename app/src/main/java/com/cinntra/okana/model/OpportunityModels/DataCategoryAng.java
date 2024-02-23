package com.cinntra.okana.model.OpportunityModels;

import com.google.gson.annotations.SerializedName;

public class DataCategoryAng {

    @SerializedName("id")
    public int id;
    @SerializedName("Number")
    public String number;

    @SerializedName("GroupName")
    public String groupName;

    @SerializedName("IsService")
    public String isService;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIsService() {
        return isService;
    }

    public void setIsService(String isService) {
        this.isService = isService;
    }
}
