package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetUserID implements Serializable {
    @SerializedName("InternalKey")
    String InternalKey;

    public String getInternalKey() {
        return InternalKey;
    }

    public void setInternalKey(String internalKey) {
        InternalKey = internalKey;
    }
}
