package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DepartMentDetail implements Serializable {

    @SerializedName("message")
    String message;
    @SerializedName("status")
    String status;
    @SerializedName("data")
    ArrayList<DepartMent> value;

    public ArrayList<DepartMent> getValue() {
        return value;
    }

    public void setValue(ArrayList<DepartMent> value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
