package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SalesTargetResponse implements Serializable {
    @SerializedName("value")
    ArrayList<SalesTarget> value;

    public ArrayList<SalesTarget> getValue()
     {
     return value;
     }
    public void setValue(ArrayList<SalesTarget > value)
      {
    this.value = value;
      }
}
