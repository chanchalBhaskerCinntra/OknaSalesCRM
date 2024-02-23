package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FollowUpData implements Serializable
{

    @SerializedName("SourceID")
    @Expose
    private String sourceID;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("Emp")
    @Expose
    private Integer emp;
    @SerializedName("From")
    @Expose
    private String from;
    @SerializedName("To")
    @Expose
    private String to;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("SourceType")
    @Expose
    private String sourceType;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("CreateTime")
    @Expose
    private String createTime;
    @SerializedName("Emp_Name")
    @Expose
    private String empName;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Subject")
    @Expose
    private String Subject;
    @SerializedName("Mode")
    @Expose
    private String comm_mode;

    @SerializedName("leadType")
    @Expose
    private String leadType;

    private final static long serialVersionUID = -2113127842408772923L;

    /**
     * No args constructor for use in serialization
     *
     */
    public FollowUpData() {
    }

    /**
     *
     * @param sourceID
     * @param sourceType
     * @param createTime
     * @param emp
     * @param comment
     * @param from
     * @param to
     * @param time
     * @param createDate
     */
    public FollowUpData(String sourceID, String comment, Integer emp, String from, String to, String time, String sourceType, String createDate, String createTime,String empName, String type, String leadType) {
        super();
        this.sourceID = sourceID;
        this.comment = comment;
        this.emp = emp;
        this.from = from;
        this.to = to;
        this.time = time;
        this.sourceType = sourceType;
        this.createDate = createDate;
        this.createTime = createTime;
        this.empName = empName;
        this.type = type;
        this.leadType = leadType;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getEmp() {
        return emp;
    }

    public void setEmp(Integer emp) {
        this.emp = emp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getComm_mode() {
        return comm_mode;
    }

    public void setComm_mode(String comm_mode) {
        this.comm_mode = comm_mode;
    }


    public String getLeadType() {
        return leadType;
    }

    public void setLeadType(String leadType) {
        this.leadType = leadType;
    }
}
