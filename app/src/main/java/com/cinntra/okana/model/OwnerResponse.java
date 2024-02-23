package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OwnerResponse implements Serializable {

    @SerializedName("value")
    @Expose
    ArrayList<OwnerItem> value;

    public ArrayList<OwnerItem> getValue() {
        return value;
    }

    public void setValue(ArrayList<OwnerItem> value) {
        this.value = value;
    }
}
