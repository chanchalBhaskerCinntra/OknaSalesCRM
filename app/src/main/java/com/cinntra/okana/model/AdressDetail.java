package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AdressDetail implements Serializable {

    @SerializedName("value")
    ArrayList<Countries> value;

    public ArrayList<Countries> getValue() {
        return value;
    }

    public void setValue(ArrayList<Countries> value) {
        this.value = value;
    }
}
