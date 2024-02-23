package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Error implements Serializable {

    @SerializedName("code")
    String code;
    @SerializedName("message")
    ErrorMessage message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ErrorMessage getMessage() {
        return message;
    }

    public void setMessage(ErrorMessage message) {
        this.message = message;
    }
}
