package com.cinntra.okana.model.OpportunityModels;

import java.util.ArrayList;

public class ResponseCategoryOFANG {
    public String message;
    public int status;
    public ArrayList<DataCategoryAng> data;

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

    public ArrayList<DataCategoryAng> getData() {
        return data;
    }

    public void setData(ArrayList<DataCategoryAng> data) {
        this.data = data;
    }
}
