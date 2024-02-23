package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StagesResponse implements Serializable {
    @SerializedName("value")
    ArrayList<StagesItem> value;

    public ArrayList<StagesItem> getValue() {
        return value;
    }

    public void setValue(ArrayList<StagesItem> value) {
        this.value = value;
    }
}
