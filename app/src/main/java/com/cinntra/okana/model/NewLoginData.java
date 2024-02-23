package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

public class NewLoginData{

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("SalesEmployeeCode")
	private String salesEmployeeCode;

	@SerializedName("Email")
	private String email;

	@SerializedName("role")
	private String role;

	@SerializedName("lastLoginOn")
	private String lastLoginOn;

	@SerializedName("SalesEmployeeName")
	private String salesEmployeeName;

	@SerializedName("userName")
	private String userName;

	@SerializedName("Mobile")
	private String mobile;

	@SerializedName("branch")
	private String branch;

	@SerializedName("logedIn")
	private String logedIn;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("companyID")
	private String companyID;

	@SerializedName("password")
	private String password;

	@SerializedName("Active")
	private String active;

	@SerializedName("middleName")
	private String middleName;

	@SerializedName("id")
	private int id;

	@SerializedName("position")
	private String position;

	@SerializedName("EmployeeID")
	private String employeeID;

	@SerializedName("passwordUpdatedOn")
	private String passwordUpdatedOn;

	@SerializedName("reportingTo")
	private String reportingTo;

	@SerializedName("timestamp")
	private String timestamp;


	public String getLastName(){
		return lastName;
	}

	public String getSalesEmployeeCode(){
		return salesEmployeeCode;
	}

	public String getEmail(){
		return email;
	}

	public String getRole(){
		return role;
	}

	public String getLastLoginOn(){
		return lastLoginOn;
	}

	public String getSalesEmployeeName(){
		return salesEmployeeName;
	}

	public String getUserName(){
		return userName;
	}

	public String getMobile(){
		return mobile;
	}

	public String getBranch(){
		return branch;
	}

	public String getLogedIn(){
		return logedIn;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getCompanyID(){
		return companyID;
	}

	public String getPassword(){
		return password;
	}

	public String getActive(){
		return active;
	}

	public String getMiddleName(){
		return middleName;
	}

	public int getId(){
		return id;
	}

	public String getPosition(){
		return position;
	}

	public String getEmployeeID(){
		return employeeID;
	}

	public String getPasswordUpdatedOn(){
		return passwordUpdatedOn;
	}

	public String getReportingTo(){
		return reportingTo;
	}

	public String getTimestamp(){
		return timestamp;
	}
}