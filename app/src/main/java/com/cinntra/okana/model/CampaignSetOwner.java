package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CampaignSetOwner implements Serializable
{

    @SerializedName("SalesEmployeeCode")
    @Expose
    private String salesEmployeeCode;
    @SerializedName("SalesEmployeeName")
    @Expose
    private String salesEmployeeName;
    private final static long serialVersionUID = 534120184722062792L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CampaignSetOwner() {
    }

    /**
     *
     * @param salesEmployeeName
     * @param salesEmployeeCode
     */
    public CampaignSetOwner(String salesEmployeeCode, String salesEmployeeName) {
        super();
        this.salesEmployeeCode = salesEmployeeCode;
        this.salesEmployeeName = salesEmployeeName;
    }

    public String getSalesEmployeeCode() {
        return salesEmployeeCode;
    }

    public void setSalesEmployeeCode(String salesEmployeeCode) {
        this.salesEmployeeCode = salesEmployeeCode;
    }

    public String getSalesEmployeeName() {
        return salesEmployeeName;
    }

    public void setSalesEmployeeName(String salesEmployeeName) {
        this.salesEmployeeName = salesEmployeeName;
    }

}
