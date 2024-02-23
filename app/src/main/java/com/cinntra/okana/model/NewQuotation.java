package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewQuotation implements Parcelable {
    @SerializedName("CompanyDB")
    @Expose
    private String CompanyDB;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("UserName")
    @Expose
    private String UserName;

    public String getCompanyDB() {
        return CompanyDB;
    }

    public void setCompanyDB(String companyDB) {
        CompanyDB = companyDB;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
    public NewQuotation()
    {}


    protected NewQuotation(Parcel in) {
        this.CompanyDB    = ((String) in.readValue((String.class.getClassLoader())));
        this.Password  = ((String) in.readValue((String.class.getClassLoader())));
        this.UserName  = ((String) in.readValue((String.class.getClassLoader())));

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(CompanyDB);
        dest.writeValue(Password);
        dest.writeValue(UserName);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NewQuotation> CREATOR = new Creator<NewQuotation>() {
        @Override
        public NewQuotation createFromParcel(Parcel in) {
            return new NewQuotation(in);
        }

        @Override
        public NewQuotation[] newArray(int size) {
            return new NewQuotation[size];
        }
    };
}
