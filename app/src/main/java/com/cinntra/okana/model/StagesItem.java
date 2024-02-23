package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StagesItem implements Serializable {

    @SerializedName("SequenceNo")
    @Expose
    String SequenceNo;
    @SerializedName("Name")
    @Expose
    String Name;
    @SerializedName("Stageno")
    @Expose
    String Stageno;
    @SerializedName("ClosingPercentage")
    @Expose
    String ClosingPercentage;

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStageno() {
        return Stageno;
    }

    public void setStageno(String stageno) {
        Stageno = stageno;
    }

    public String getClosingPercentage() {
        return ClosingPercentage;
    }

    public void setClosingPercentage(String closingPercentage) {
        ClosingPercentage = closingPercentage;
    }
}
