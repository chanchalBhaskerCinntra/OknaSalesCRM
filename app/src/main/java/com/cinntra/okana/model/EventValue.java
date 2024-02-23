package com.cinntra.okana.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventValue implements Parcelable, Serializable
{


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("SourceID")
    @Expose
    private Integer oppId;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("RelatedTo")
    @Expose
    private String relatedTo;
    @SerializedName("Emp")
    @Expose
    private Integer emp;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("From")
    @Expose
    private String from;
    @SerializedName("To")
    @Expose
    private String to;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("Allday")
    @Expose
    private String allday;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Host")
    @Expose
    private String host;
    @SerializedName("Participants")
    @Expose
    private String participants;
    @SerializedName("Document")
    @Expose
    private String document;
    @SerializedName("Repeated")
    @Expose
    private String repeated;
    @SerializedName("Priority")
    @Expose
    private String priority;
    @SerializedName("ProgressStatus")
    @Expose
    private String progressStatus;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("SourceType")
    @Expose
    private String sourceType;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("CreateTime")
    @Expose
    private String createTime;
    @SerializedName("Status")
    @Expose
    private String Status;

    public final static Creator<EventValue> CREATOR = new Creator<EventValue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EventValue createFromParcel(android.os.Parcel in) {
            return new EventValue(in);
        }

        public EventValue[] newArray(int size) {
            return (new EventValue[size]);
        }

    };

    protected EventValue(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.oppId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.subject = ((String) in.readValue((String.class.getClassLoader())));
        this.comment = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.relatedTo = ((String) in.readValue((String.class.getClassLoader())));
        this.emp = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.from = ((String) in.readValue((String.class.getClassLoader())));
        this.to = ((String) in.readValue((String.class.getClassLoader())));
        this.time = ((String) in.readValue((String.class.getClassLoader())));
        this.allday = ((String) in.readValue((String.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.host = ((String) in.readValue((String.class.getClassLoader())));
        this.participants = ((String) in.readValue((String.class.getClassLoader())));
        this.document = ((String) in.readValue((String.class.getClassLoader())));
        this.repeated = ((String) in.readValue((String.class.getClassLoader())));
        this.priority = ((String) in.readValue((String.class.getClassLoader())));
        this.progressStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.createDate = ((String) in.readValue((String.class.getClassLoader())));
        this.createTime = ((String) in.readValue((String.class.getClassLoader())));
        this.Status = ((String) in.readValue((String.class.getClassLoader())));
        this.sourceType = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public EventValue() {
    }

    /**
     *
     * @param subject
     * @param document
     * @param emp
     * @param description
     * @param title
     * @param priority
     * @param type
     * @param repeated
     * @param relatedTo
     * @param allday
     * @param createTime
     * @param name
     * @param host
     * @param progressStatus
     * @param oppId
     * @param comment
     * @param from
     * @param location
     * @param id
     * @param to
     * @param time
     * @param participants
     * @param createDate
     */
    public EventValue(Integer id, Integer oppId, String subject, String comment, String name, String relatedTo, Integer emp, String title, String description, String from, String to, String time, String allday, String location, String host, String participants, String document, String repeated, String priority, String progressStatus, String type, String createDate, String createTime, String sourceType) {
        super();
        this.id = id;
        this.oppId = oppId;
        this.subject = subject;
        this.comment = comment;
        this.name = name;
        this.relatedTo = relatedTo;
        this.emp = emp;
        this.title = title;
        this.description = description;
        this.from = from;
        this.to = to;
        this.time = time;
        this.allday = allday;
        this.location = location;
        this.host = host;
        this.participants = participants;
        this.document = document;
        this.repeated = repeated;
        this.priority = priority;
        this.progressStatus = progressStatus;
        this.type = type;
        this.createDate = createDate;
        this.createTime = createTime;
        this.sourceType = sourceType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOppId() {
        return oppId;
    }

    public void setOppId(Integer oppId) {
        this.oppId = oppId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    public Integer getEmp() {
        return emp;
    }

    public void setEmp(Integer emp) {
        this.emp = emp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getAllday() {
        return allday;
    }

    public void setAllday(String allday) {
        this.allday = allday;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getRepeated() {
        return repeated;
    }

    public void setRepeated(String repeated) {
        this.repeated = repeated;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(oppId);
        dest.writeValue(subject);
        dest.writeValue(comment);
        dest.writeValue(name);
        dest.writeValue(relatedTo);
        dest.writeValue(emp);
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(from);
        dest.writeValue(to);
        dest.writeValue(time);
        dest.writeValue(allday);
        dest.writeValue(location);
        dest.writeValue(host);
        dest.writeValue(participants);
        dest.writeValue(document);
        dest.writeValue(repeated);
        dest.writeValue(priority);
        dest.writeValue(progressStatus);
        dest.writeValue(type);
        dest.writeValue(createDate);
        dest.writeValue(createTime);
        dest.writeValue(Status);
        dest.writeValue(sourceType);
    }

    public int describeContents() {
        return 0;
    }

}
