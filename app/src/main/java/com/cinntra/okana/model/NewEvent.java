package com.cinntra.okana.model;
import java.io.Serializable;

public class NewEvent implements Serializable {
   String title;
   String dateFrom;
   String dateTo;
   boolean allDay;
   String repeat;
   String location;
   String host;
   String preority;
   String participant;
   String description;
   String relatedDoc;
   String Time;
   int type;

    public NewEvent(String title, String dateFrom, String dateTo, boolean allDay, String repeat,
           String location, String host, String preority, String participant,
           String description, String relatedDoc,int type,String Time)
             {
        this.title        = title;
        this.dateFrom     = dateFrom;
        this.dateTo       = dateTo;
        this.allDay       = allDay;
        this.repeat       = repeat;
        this.location     = location;
        this.host         = host;
        this.preority     = preority;
        this.participant  = participant;
        this.description  = description;
        this.relatedDoc   = relatedDoc;
        this.type         = type;
        this.Time         = Time;
             }
     public String getTitle()
     {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
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

    public String getPreority() {
        return preority;
    }

    public void setPreority(String preority) {
        this.preority = preority;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelatedDoc() {
        return relatedDoc;
    }

    public void setRelatedDoc(String relatedDoc) {
        this.relatedDoc = relatedDoc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
