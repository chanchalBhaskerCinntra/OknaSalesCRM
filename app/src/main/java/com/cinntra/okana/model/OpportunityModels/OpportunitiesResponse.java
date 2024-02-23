package com.cinntra.okana.model.OpportunityModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OpportunitiesResponse implements Serializable {

    @SerializedName("value")
    ArrayList<OpportunityItem> value;
    /*@SerializedName("error.code")
    String errorCode;*/
    public ArrayList<OpportunityItem> getValue() {
        return value;
    }

    /*public String getErrorCode()
     {
    return errorCode;
      }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }*/

    public void setValue(ArrayList<OpportunityItem> value)
        {
    this.value = value;
        }
}
