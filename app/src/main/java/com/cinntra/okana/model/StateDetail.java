package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StateDetail implements Serializable {

    @SerializedName("value")
    ArrayList<StateData> value;

    public ArrayList<StateData> getValue() {
        return value;
    }

    public void setValue(ArrayList<StateData> value) {
        this.value = value;
    }
}
