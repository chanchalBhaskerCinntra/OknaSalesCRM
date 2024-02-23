package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class ItemResponse implements Serializable {
    @SerializedName("data")
    ArrayList<DocumentLines> value;

    String message ;
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

    public ArrayList<DocumentLines> getValue() {
        return value;
    }

    public void setValue(ArrayList<DocumentLines> value) {
        this.value = value;
    }
}
