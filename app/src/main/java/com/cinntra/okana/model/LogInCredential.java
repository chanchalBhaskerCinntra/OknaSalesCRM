package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LogInCredential implements Serializable {

    @SerializedName("User")
    @Expose
    String User;

    @SerializedName("Password")
    @Expose
    String Password;

    @SerializedName("Valid")
    @Expose
    String Valid;
    @SerializedName("empID")
    @Expose
    String empID;

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getValid() {
        return Valid;
    }

    public void setValid(String valid) {
        Valid = valid;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }
}
