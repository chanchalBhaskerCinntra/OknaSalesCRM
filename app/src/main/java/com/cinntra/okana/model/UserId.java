package com.cinntra.okana.model;



import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserId implements Serializable {

    @SerializedName("UserName")
    String UserName;
    @SerializedName("eMail")
    String eMail;
    @SerializedName("MobilePhoneNumber")
    String MobilePhoneNumber;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getMobilePhoneNumber() {
        return MobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        MobilePhoneNumber = mobilePhoneNumber;
    }
}
