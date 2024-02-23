package com.cinntra.okana.newapimodel;

import java.util.List;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadResponse implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<LeadValue> data = null;
    public final static Creator<LeadResponse> CREATOR = new Creator<LeadResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LeadResponse createFromParcel(android.os.Parcel in) {
            return new LeadResponse(in);
        }

        public LeadResponse[] newArray(int size) {
            return (new LeadResponse[size]);
        }

    }
            ;

    protected LeadResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (com.cinntra.okana.newapimodel.LeadValue.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public LeadResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public LeadResponse(String message, Integer status, List<LeadValue> data) {
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

    public List<LeadValue> getData() {
        return data;
    }

    public void setData(List<LeadValue> data) {
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