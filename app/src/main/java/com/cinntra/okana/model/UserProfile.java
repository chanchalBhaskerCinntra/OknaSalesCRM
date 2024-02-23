package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserProfile implements Serializable {

    @SerializedName("value")
    ArrayList<UserProfileSetting> value;

    public ArrayList<UserProfileSetting> getValue() {
        return value;
    }

    public void setValue(ArrayList<UserProfileSetting> value) {
        this.value = value;
    }
}
