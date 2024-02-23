package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class Top5CustomerResponse implements Serializable {
    @SerializedName("data")
    ArrayList<Team> value;

    public ArrayList<Team> getValue()
     {
     return value;
     }
    public void setValue(ArrayList<Team> value)
      {
    this.value = value;
      }
}
