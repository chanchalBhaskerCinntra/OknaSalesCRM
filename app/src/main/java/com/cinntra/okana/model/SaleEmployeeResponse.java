package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SaleEmployeeResponse implements Serializable {


    /******************** DB Manager ***************************/
    public static final String TABLE_NAME = "SalesEmployees";

    public static final String COLUMN_SalesEmployeeCode = "_SalesEmployeeCode";
    public static final String COLUMN_SalesEmployeeName = "_SalesEmployeeName";



    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_SalesEmployeeCode + " TEXT,"
                    + COLUMN_SalesEmployeeName + " TEXT,"
                    + ")";

    public SaleEmployeeResponse() {
    }
    public SaleEmployeeResponse(String v1,String v2) {
    }


    String message = "";
    int status ;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("data")
    ArrayList<SalesEmployeeItem> value;

    public ArrayList<SalesEmployeeItem> getValue() {
        return value;
    }

    public void setValue(ArrayList<SalesEmployeeItem> value) {
        this.value = value;
    }
}
