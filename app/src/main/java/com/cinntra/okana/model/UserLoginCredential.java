package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserLoginCredential implements Serializable {

    @SerializedName("value")
    ArrayList<LogInCredential> value;

    public ArrayList<LogInCredential> getValue() {
        return value;
    }

    public void setValue(ArrayList<LogInCredential> value) {
        this.value = value;
    }
}
