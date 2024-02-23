package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateBy implements Serializable
{

    @SerializedName("SalesEmployeeCode")
    @Expose
    private String salesEmployeeCode;
    @SerializedName("SalesEmployeeName")
    @Expose
    private String salesEmployeeName;
    private final static long serialVersionUID = -1128275760341755045L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreateBy() {
    }

    /**
     *
     * @param salesEmployeeName
     * @param salesEmployeeCode
     */
    public CreateBy(String salesEmployeeCode, String salesEmployeeName) {
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
