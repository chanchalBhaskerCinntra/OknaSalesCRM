package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalesOpportunitiesLinesGet implements Serializable {
    @SerializedName("SalesPerson")
    @Expose
    int SalesPerson;
    @SerializedName("MaxLocalTotal")
    @Expose
    float MaxLocalTotal;
    @SerializedName("MaxSystemTotal")
    @Expose
    float MaxSystemTotal;
    @SerializedName("DocumentType")
    @Expose
    String DocumentType;
    @SerializedName("StageKey")
    @Expose
    String StageKey;

    @SerializedName("LineNum")
    @Expose
    String LineNum;



    public int getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(int salesPerson) {
        SalesPerson = salesPerson;
    }

    public float getMaxLocalTotal() {
        return MaxLocalTotal;
    }

    public void setMaxLocalTotal(float maxLocalTotal) {
        MaxLocalTotal = maxLocalTotal;
    }

    public float getMaxSystemTotal() {
        return MaxSystemTotal;
    }

    public void setMaxSystemTotal(float maxSystemTotal) {
        MaxSystemTotal = maxSystemTotal;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getStageKey() {
        return StageKey;
    }

    public void setStageKey(String stageKey) {
        StageKey = stageKey;
    }

    public String getLineNum() {
        return LineNum;
    }

    public void setLineNum(String lineNum) {
        LineNum = lineNum;
    }
}
