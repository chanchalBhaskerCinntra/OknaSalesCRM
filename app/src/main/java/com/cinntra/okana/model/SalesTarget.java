package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalesTarget implements Serializable {

    @SerializedName("SlpCode")
    String SlpCode;
    @SerializedName("InitialTotal")
    String InitialTotal;
   @SerializedName("AchivedTotal")
    String AchivedTotal;

    public String getSlpCode() {
        return SlpCode;
    }

    public void setSlpCode(String slpCode) {
        SlpCode = slpCode;
    }

    public String getInitialTotal() {
        return InitialTotal;
    }

    public void setInitialTotal(String initialTotal) {
        InitialTotal = initialTotal;
    }

    public String getAchivedTotal() {
        return AchivedTotal;
    }

    public void setAchivedTotal(String achivedTotal) {
        AchivedTotal = achivedTotal;
    }
}
