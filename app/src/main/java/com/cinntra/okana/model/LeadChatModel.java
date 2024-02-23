package com.cinntra.okana.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LeadChatModel implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Lead_Id")
    @Expose
    private String oppId;
    @SerializedName("Emp_Id")
    @Expose
    private String empId;
    @SerializedName("Emp_Name")
    @Expose
    private String empName;
    @SerializedName("UpdateDate")
    @Expose
    private String updateDate;
    @SerializedName("UpdateTime")
    @Expose
    private String updateTime;
    public final static Creator<LeadChatModel> CREATOR = new Creator<LeadChatModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LeadChatModel createFromParcel(android.os.Parcel in) {
            return new LeadChatModel(in);
        }

        public LeadChatModel[] newArray(int size) {
            return (new LeadChatModel[size]);
        }

    }
            ;

    protected LeadChatModel(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.oppId = ((String) in.readValue((String.class.getClassLoader())));
        this.empId = ((String) in.readValue((String.class.getClassLoader())));
        this.empName = ((String) in.readValue((String.class.getClassLoader())));
        this.updateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public LeadChatModel() {
    }

    /**
     *
     * @param empId
     * @param updateDate
     * @param empName
     * @param oppId
     * @param updateTime
     * @param id
     * @param message
     */
    public LeadChatModel(Integer id, String message, String oppId, String empId, String empName, String updateDate, String updateTime) {
        super();
        this.id = id;
        this.message = message;
        this.oppId = oppId;
        this.empId = empId;
        this.empName = empName;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(message);
        dest.writeValue(oppId);
        dest.writeValue(empId);
        dest.writeValue(empName);
        dest.writeValue(updateDate);
        dest.writeValue(updateTime);
    }

    public int describeContents() {
        return 0;
    }

}