package com.cinntra.okana.newapimodel;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignedTo implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("companyID")
    @Expose
    private String companyID;
    @SerializedName("SalesEmployeeCode")
    @Expose
    private String salesEmployeeCode;
    @SerializedName("SalesEmployeeName")
    @Expose
    private String salesEmployeeName;
    @SerializedName("EmployeeID")
    @Expose
    private String employeeID;
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
    private String mobile;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("Active")
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
    private String logedIn;
    @SerializedName("reportingTo")
    @Expose
    private String reportingTo;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    public final static Creator<AssignedTo> CREATOR = new Creator<AssignedTo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public com.cinntra.okana.newapimodel.AssignedTo createFromParcel(android.os.Parcel in) {
            return new com.cinntra.okana.newapimodel.AssignedTo(in);
        }

        public com.cinntra.okana.newapimodel.AssignedTo[] newArray(int size) {
            return (new com.cinntra.okana.newapimodel.AssignedTo[size]);
        }

    };

    protected AssignedTo(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.companyID = ((String) in.readValue((String.class.getClassLoader())));
        this.salesEmployeeCode = ((String) in.readValue((String.class.getClassLoader())));
        this.salesEmployeeName = ((String) in.readValue((String.class.getClassLoader())));
        this.employeeID = ((String) in.readValue((String.class.getClassLoader())));
        this.userName = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.middleName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.mobile = ((String) in.readValue((String.class.getClassLoader())));
        this.role = ((String) in.readValue((String.class.getClassLoader())));
        this.position = ((String) in.readValue((String.class.getClassLoader())));
        this.branch = ((String) in.readValue((String.class.getClassLoader())));
        this.active = ((String) in.readValue((String.class.getClassLoader())));
        this.passwordUpdatedOn = ((String) in.readValue((String.class.getClassLoader())));
        this.lastLoginOn = ((String) in.readValue((String.class.getClassLoader())));
        this.logedIn = ((String) in.readValue((String.class.getClassLoader())));
        this.reportingTo = ((String) in.readValue((String.class.getClassLoader())));
        this.timestamp = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public AssignedTo() {
    }

    /**
     * @param salesEmployeeName
     * @param lastName
     * @param role
     * @param salesEmployeeCode
     * @param lastLoginOn
     * @param mobile
     * @param active
     * @param employeeID
     * @param userName
     * @param branch
     * @param logedIn
     * @param firstName
     * @param companyID
     * @param password
     * @param middleName
     * @param id
     * @param position
     * @param passwordUpdatedOn
     * @param email
     * @param reportingTo
     * @param timestamp
     */
    public AssignedTo(Integer id, String companyID, String salesEmployeeCode, String salesEmployeeName, String employeeID, String userName, String password, String firstName, String middleName, String lastName, String email, String mobile, String role, String position, String branch, String active, String passwordUpdatedOn, String lastLoginOn, String logedIn, String reportingTo, String timestamp) {
        super();
        this.id = id;
        this.companyID = companyID;
        this.salesEmployeeCode = salesEmployeeCode;
        this.salesEmployeeName = salesEmployeeName;
        this.employeeID = employeeID;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
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

    public String getSalesEmployeeCode() {
        return salesEmployeeCode;
    }

    public void setSalesEmployeeCode(String salesEmployeeCode) {
        this.salesEmployeeCode = salesEmployeeCode;
    }

    public String getSalesEmployeeName() {
        return salesEmployeeName;
    }

    public void setSalesEmployeeName(String salesEmployeeName) {
        this.salesEmployeeName = salesEmployeeName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getLogedIn() {
        return logedIn;
    }

    public void setLogedIn(String logedIn) {
        this.logedIn = logedIn;
    }

    public String getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(String reportingTo) {
        this.reportingTo = reportingTo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(companyID);
        dest.writeValue(salesEmployeeCode);
        dest.writeValue(salesEmployeeName);
        dest.writeValue(employeeID);
        dest.writeValue(userName);
        dest.writeValue(password);
        dest.writeValue(firstName);
        dest.writeValue(middleName);
        dest.writeValue(lastName);
        dest.writeValue(email);
        dest.writeValue(mobile);
        dest.writeValue(role);
        dest.writeValue(position);
        dest.writeValue(branch);
        dest.writeValue(active);
        dest.writeValue(passwordUpdatedOn);
        dest.writeValue(lastLoginOn);
        dest.writeValue(logedIn);
        dest.writeValue(reportingTo);
        dest.writeValue(timestamp);
    }

    public int describeContents() {
        return 0;
    }

}
