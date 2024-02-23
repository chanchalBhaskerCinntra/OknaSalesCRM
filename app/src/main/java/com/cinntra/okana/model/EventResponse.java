package com.cinntra.okana.model;

import java.util.List;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EventResponse implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<EventValue> data ;
    public final static Creator<EventResponse> CREATOR = new Creator<EventResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EventResponse createFromParcel(android.os.Parcel in) {
            return new EventResponse(in);
        }

        public EventResponse[] newArray(int size) {
            return (new EventResponse[size]);
        }

    }
            ;

    protected EventResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (com.cinntra.okana.model.EventValue.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public EventResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public EventResponse(String message, Integer status, List<EventValue> data) {
        super();
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<EventValue> getData() {
        return data;
    }

    public void setData(List<EventValue> data) {
        this.data = data;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(status);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}