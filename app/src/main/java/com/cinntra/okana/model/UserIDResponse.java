package com.cinntra.okana.model;

 import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class UserIDResponse implements Serializable {
    @SerializedName("value")
    ArrayList<GetUserID> value;
    public ArrayList<GetUserID> getValue() {
        return value;
    }
    public void setValue(ArrayList<GetUserID> value) {
        this.value = value;
    }
}
