package com.cinntra.okana.model;

import java.util.ArrayList;

public class ResponseModel {
    private String message;
    private int status;
    private ArrayList<Object> data = new ArrayList<>();

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

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }
    public ResponseModel() {
    }
}
