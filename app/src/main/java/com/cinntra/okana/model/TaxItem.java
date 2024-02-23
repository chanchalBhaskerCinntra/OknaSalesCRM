package com.cinntra.okana.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "table_Tax")
public class TaxItem implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("Code")

    String Code;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
