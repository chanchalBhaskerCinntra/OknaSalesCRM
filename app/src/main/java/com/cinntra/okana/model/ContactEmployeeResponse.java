package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactEmployeeResponse implements Serializable {
    @SerializedName("ContactEmployees")
    ArrayList<ContactEmployeesModel> value;

    public ArrayList<ContactEmployeesModel> getValue() {
        return value;
    }

    public void setValue(ArrayList<ContactEmployeesModel> value) {
        this.value = value;
    }
}
