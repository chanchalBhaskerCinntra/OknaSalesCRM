package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LeadFilter implements Serializable {
    @SerializedName("assignedTo")
    @Expose
    private String assignedTo;
    @SerializedName("employeeId")
    @Expose
    private String employeeId;
    @SerializedName("leadType")
    @Expose
    private String leadType;
    @SerializedName("maxItem")
    @Expose
    private int MaxItem;
    @SerializedName("PageNo")
    @Expose
    private int PageNo;

    @SerializedName("source")
    @Expose
    private ArrayList<String> source;

    @SerializedName("SearchText")
    @Expose
    private String search;

    @SerializedName("field")
    @Expose
    private Field field = new Field();

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public static class Field implements Serializable{
        @SerializedName("source")
        @Expose
        private String Source = "";
        @SerializedName("FromDate")
        @Expose
        private String FromDate = "";

        @SerializedName("ToDate")
        @Expose
        private String ToDate = "";

        @SerializedName("Status")
        @Expose
        private String Status = "";

        @SerializedName("assignTo")
        @Expose
        private String assignTo = "";
        private String Priority = "";

        public String getAssignTo() {
            return assignTo;
        }

        public void setAssignTo(String assignTo) {
            this.assignTo = assignTo;
        }

        public String getPriority() {
            return Priority;
        }

        public void setPriority(String priority) {
            Priority = priority;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getToDate() {
            return ToDate;
        }

        public void setToDate(String ToDate) {
            this.ToDate = ToDate;
        }
        public String getFromDate() {
            return FromDate;
        }

        public void setFromDate(String FromDate) {
            this.FromDate = FromDate;
        }
        public String getSource() {
            return Source;
        }

        public void setSource(String Source) {
            this.Source = Source;
        }
    }


    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeadType() {
        return leadType;
    }

    public void setLeadType(String leadType) {
        this.leadType = leadType;
    }

    public int getMaxItem() {
        return MaxItem;
    }

    public ArrayList<String> getSource() {
        return source;
    }

    public void setSource(ArrayList<String> source) {
        this.source = source;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setMaxItem(int maxItem) {
        MaxItem = maxItem;
    }

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }
}
