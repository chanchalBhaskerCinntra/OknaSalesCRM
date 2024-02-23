package com.cinntra.okana.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


@Entity(tableName = "industry")
  public class IndustryItem implements Serializable {
    @SerializedName("IndustryName")
    String IndustryName;

    @PrimaryKey
    @NonNull
    @SerializedName("IndustryCode")
    String IndustryCode;
    @SerializedName("IndustryDescription")
    String IndustryDescription;

    public String getIndustryName() {
        return IndustryName;
    }

    public void setIndustryName(String industryName) {
        IndustryName = industryName;
    }

    public String getIndustryCode() {
        return IndustryCode;
    }

    public void setIndustryCode(String industryCode) {
        IndustryCode = industryCode;
    }

    public String getIndustryDescription() {
        return IndustryDescription;
    }

    public void setIndustryDescription(String industryDescription) {
        IndustryDescription = industryDescription;
    }
}
