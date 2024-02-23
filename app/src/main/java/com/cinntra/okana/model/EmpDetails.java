package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmpDetails implements Serializable
{

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
    @SerializedName("FCM")
    @Expose
    private String fcm;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    private final static long serialVersionUID = -3348895438157683886L;

    /**
     * No args constructor for use in serialization
     *
     */
    public EmpDetails() {
    }

    /**
     *
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
     * @param fcm
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
    public EmpDetails(Integer id, String companyID, String salesEmployeeCode, String salesEmployeeName, String employeeID, String userName, String password, String firstName, String middleName, String lastName, String email, String mobile, String role, String position, String branch, String active, String passwordUpdatedOn, String lastLoginOn, String logedIn, String reportingTo, String fcm, String timestamp) {
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
        this.fcm = fcm;
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

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
