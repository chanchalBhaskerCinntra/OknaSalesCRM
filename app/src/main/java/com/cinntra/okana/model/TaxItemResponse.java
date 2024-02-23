package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TaxItemResponse implements Serializable {
    @SerializedName("value")
    ArrayList<TaxItem> value;

    public ArrayList<TaxItem> getValue() {
        return value;
    }

    public void setValue(ArrayList<TaxItem> value) {
        this.value = value;
    }
}
