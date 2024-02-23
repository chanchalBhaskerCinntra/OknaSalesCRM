package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UserCounterResponse implements Serializable {
    @SerializedName("value")
    ArrayList<UserCounter> value;

    public ArrayList<UserCounter> getValue()
     {
     return value;
     }
    public void setValue(ArrayList<UserCounter> value)
      {
    this.value = value;
      }
}
