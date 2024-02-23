package com.cinntra.okana.model.OpportunityModels;

import java.util.ArrayList;

public class OppActivityUpdateResponse {
    public String message;
    public int status;
    public ArrayList<Datum> data;

    public class Datum{

        public String Subject;

        public String Comment;

        public String Name;

        public String RelatedTo;

        public int Emp;

        public String Title;

        public String Description;

        public String From;

        public String To;

        public String Time;

        public String Allday;

        public String Location;

        public String Host;

        public ArrayList<String> Participants;

        public String Document;

        public String Repeated;

        public String Priority;

        public String ProgressStatus;

        public String Type;

        public String Status;

        public String CreateDate;

        public String CreateTime;

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String subject) {
            Subject = subject;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getRelatedTo() {
            return RelatedTo;
        }

        public void setRelatedTo(String relatedTo) {
            RelatedTo = relatedTo;
        }

        public int getEmp() {
            return Emp;
        }

        public void setEmp(int emp) {
            Emp = emp;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getFrom() {
            return From;
        }

        public void setFrom(String from) {
            From = from;
        }

        public String getTo() {
            return To;
        }

        public void setTo(String to) {
            To = to;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getAllday() {
            return Allday;
        }

        public void setAllday(String allday) {
            Allday = allday;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getHost() {
            return Host;
        }

        public void setHost(String host) {
            Host = host;
        }

        public ArrayList<String> getParticipants() {
            return Participants;
        }

        public void setParticipants(ArrayList<String> participants) {
            Participants = participants;
        }

        public String getDocument() {
            return Document;
        }

        public void setDocument(String document) {
            Document = document;
        }

        public String getRepeated() {
            return Repeated;
        }

        public void setRepeated(String repeated) {
            Repeated = repeated;
        }

        public String getPriority() {
            return Priority;
        }

        public void setPriority(String priority) {
            Priority = priority;
        }

        public String getProgressStatus() {
            return ProgressStatus;
        }

        public void setProgressStatus(String progressStatus) {
            ProgressStatus = progressStatus;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String createDate) {
            CreateDate = createDate;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }
}
