package com.cinntra.okana.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DemoValue implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    public final static Creator<DemoValue> CREATOR = new Creator<DemoValue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DemoValue createFromParcel(android.os.Parcel in) {
            return new DemoValue(in);
        }

        public DemoValue[] newArray(int size) {
            return (new DemoValue[size]);
        }

    }
            ;

    protected DemoValue(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.company = ((String) in.readValue((String.class.getClassLoader())));
        this.timestamp = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public DemoValue() {
    }

    /**
     *
     * @param phone
     * @param name
     * @param company
     * @param email
     * @param timestamp
     */
    public DemoValue(String name, String phone, String email, String company, String timestamp) {
        super();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(phone);
        dest.writeValue(email);
        dest.writeValue(company);
        dest.writeValue(timestamp);
    }

    public int describeContents() {
        return 0;
    }

}