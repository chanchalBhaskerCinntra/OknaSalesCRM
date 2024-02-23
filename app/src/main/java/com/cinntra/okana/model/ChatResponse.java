package com.cinntra.okana.model;

import java.util.List;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatResponse implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<ChatModel> data = null;
    public final static Creator<ChatResponse> CREATOR = new Creator<ChatResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChatResponse createFromParcel(android.os.Parcel in) {
            return new ChatResponse(in);
        }

        public ChatResponse[] newArray(int size) {
            return (new ChatResponse[size]);
        }

    }
            ;

    protected ChatResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (com.cinntra.okana.model.ChatModel.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ChatResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public ChatResponse(String message, Integer status, List<ChatModel> data) {
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

    public List<ChatModel> getData() {
        return data;
    }

    public void setData(List<ChatModel> data) {
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