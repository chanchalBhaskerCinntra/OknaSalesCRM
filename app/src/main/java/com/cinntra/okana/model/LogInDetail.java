package com.cinntra.okana.model;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LogInDetail implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("companyID")
    @Expose
    private String companyID;

    @SerializedName("SalesEmployeeCode")
    @Expose
    private String SalesEmployeeCode;

    @SerializedName("SalesEmployeeName")
    @Expose
    private String SalesEmployeeName;
    @SerializedName("EmployeeID")
    @Expose
    private String EmployeeID;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;

    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Mobile")
    @Expose
    private String phone;
    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("position")
    @Expose
    private String position;

    @SerializedName("branch")
    @Expose
    private String branch;

    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("passwordUpdatedOn")
    @Expose
    private String passwordUpdatedOn;
    @SerializedName("lastLoginOn")
    @Expose
    private String lastLoginOn;
    @SerializedName("logedIn")
    @Expose
    private Integer logedIn;
    @SerializedName("reportingTo")
    @Expose
    private Integer reportingTo;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("FCM")
    @Expose
    private String fcm;
    public final static Creator<LogInDetail> CREATOR = new Creator<LogInDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LogInDetail createFromParcel(android.os.Parcel in) {
            return new LogInDetail(in);
        }

        public LogInDetail[] newArray(int size) {
            return (new LogInDetail[size]);
        }

    };

    protected LogInDetail(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyID = ((String) in.readValue((Integer.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.middleName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.role = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((String) in.readValue((String.class.getClassLoader())));
        this.branch = ((String) in.readValue((Integer.class.getClassLoader())));
        this.active = ((String) in.readValue((Integer.class.getClassLoader())));
        this.passwordUpdatedOn = ((String) in.readValue((String.class.getClassLoader())));
        this.lastLoginOn = ((String) in.readValue((String.class.getClassLoader())));
        this.logedIn = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.reportingTo = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.timestamp = ((String) in.readValue((String.class.getClassLoader())));
        this.fcm = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public LogInDetail() {
    }

    /**
     * @param lastName
     * @param role
     * @param lastLoginOn
     * @param active
     * @param userName
     * @param branch
     * @param logedIn
     * @param firstName
     * @param companyID
     * @param password
     * @param phone
     * @param middleName
     * @param id
     * @param position
     * @param passwordUpdatedOn
     * @param email
     * @param reportingTo
     * @param timestamp
     */
    public LogInDetail(Integer id, String companyID, String userName, String password, String firstName, String middleName, String lastName, String email, String phone, String role, String position, String branch, String active, String passwordUpdatedOn, String lastLoginOn, Integer logedIn, Integer reportingTo, String timestamp) {
        super();
        this.id = id;
        this.companyID = companyID;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.position = position;
        this.branch = branch;
        this.active = active;
        this.passwordUpdatedOn = passwordUpdatedOn;
        this.lastLoginOn = lastLoginOn;
        this.logedIn = logedIn;
        this.reportingTo = reportingTo;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPasswordUpdatedOn() {
        return passwordUpdatedOn;
    }

    public void setPasswordUpdatedOn(String passwordUpdatedOn) {
        this.passwordUpdatedOn = passwordUpdatedOn;
    }

    public String getLastLoginOn() {
        return lastLoginOn;
    }

    public void setLastLoginOn(String lastLoginOn) {
        this.lastLoginOn = lastLoginOn;
    }

    public Integer getLogedIn() {
        return logedIn;
    }

    public void setLogedIn(Integer logedIn) {
        this.logedIn = logedIn;
    }

    public Integer getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(Integer reportingTo) {
        this.reportingTo = reportingTo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyID);
        dest.writeValue(userName);
        dest.writeValue(password);
        dest.writeValue(firstName);
        dest.writeValue(middleName);
        dest.writeValue(lastName);
        dest.writeValue(email);
        dest.writeValue(phone);
        dest.writeValue(role);
        dest.writeValue(position);
        dest.writeValue(branch);
        dest.writeValue(active);
        dest.writeValue(passwordUpdatedOn);
        dest.writeValue(lastLoginOn);
        dest.writeValue(logedIn);
        dest.writeValue(reportingTo);
        dest.writeValue(timestamp);
        dest.writeValue(fcm);
    }

    public int describeContents() {
        return 0;
    }

}
