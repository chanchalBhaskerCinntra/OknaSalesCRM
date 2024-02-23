package com.cinntra.okana.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



@Entity(tableName = "country")
public class Countries implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("Code")
    String Code;
    @SerializedName("Name")
    String Name;

    public Countries(String Code,String Name){
       this.Code = Code;
       this.Name = Name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
