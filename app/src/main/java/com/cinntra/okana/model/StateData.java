package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StateData implements Serializable {

    @SerializedName("Code")
    String Code;
    @SerializedName("Country")
    String Country;
    @SerializedName("Name")
    String Name;
    @SerializedName("id")
    @Expose
    private Integer id;

    public StateData(String Code,String Name)
    {
        this.Code = Code;
        this.Name = Name;
    }
    public StateData(){

    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
