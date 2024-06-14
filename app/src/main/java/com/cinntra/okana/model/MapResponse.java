package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MapResponse implements Serializable {

    String message = "";
    int status ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("data")
    ArrayList<MapData> value;

    public ArrayList<MapData> getValue() {
        return value;
    }

    public void setValue(ArrayList<MapData> value) {
        this.value = value;
    }
}
