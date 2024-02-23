package com.cinntra.okana.newapimodel;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeadDocumentResponse implements Parcelable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<AttachDocument> data = null;
    public final static Creator<LeadDocumentResponse> CREATOR = new Creator<LeadDocumentResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LeadDocumentResponse createFromParcel(android.os.Parcel in) {
            return new LeadDocumentResponse(in);
        }

        public LeadDocumentResponse[] newArray(int size) {
            return (new LeadDocumentResponse[size]);
        }

    };

    protected LeadDocumentResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (AttachDocument.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public LeadDocumentResponse() {
    }

    /**
     * @param data
     * @param message
     * @param status
     */
    public LeadDocumentResponse(String message, Integer status, List<AttachDocument> data) {
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

    public List<AttachDocument> getData() {
        return data;
    }

    public void setData(List<AttachDocument> data) {
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