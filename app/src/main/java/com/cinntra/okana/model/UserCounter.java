package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserCounter implements Serializable {
    @SerializedName("UserCount")
    String UserCount;

    public String getUserCount() {
        return UserCount;
    }

    public void setUserCount(String userCount) {
        UserCount = userCount;
    }
}
