package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class IndustryResponse implements Serializable {
    @SerializedName("data")
    ArrayList<IndustryItem> value;

    int status ;
    String message ;

    public ArrayList<IndustryItem> getValue()
     {
     return value;
     }
    public void setValue(ArrayList<IndustryItem> value)
      {
    this.value = value;
      }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
