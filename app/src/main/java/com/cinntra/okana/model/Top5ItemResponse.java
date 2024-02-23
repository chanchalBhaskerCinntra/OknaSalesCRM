package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Top5ItemResponse implements Serializable {
    @SerializedName("data")
    ArrayList<Top5Item> value;

    public ArrayList<Top5Item> getValue()
     {
     return value;
     }
    public void setValue(ArrayList<Top5Item> value)
      {
    this.value = value;
      }
}
