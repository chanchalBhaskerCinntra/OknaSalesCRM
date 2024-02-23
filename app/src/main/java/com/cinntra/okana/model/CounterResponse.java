package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CounterResponse implements Serializable {
    @SerializedName("data")
    ArrayList<Count> value;

    public ArrayList<Count> getValue() {
        return value;
    }

    public void setValue(ArrayList<Count> value) {
        this.value = value;
    }
}
