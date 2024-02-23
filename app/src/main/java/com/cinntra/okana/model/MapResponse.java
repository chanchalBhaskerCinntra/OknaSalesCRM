package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MapResponse implements Serializable {
    @SerializedName("data")
    ArrayList<MapData> value;

    public ArrayList<MapData> getValue() {
        return value;
    }

    public void setValue(ArrayList<MapData> value) {
        this.value = value;
    }
}
