package com.cinntra.okana.model;

import java.util.List;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DemoResponse implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DataValue> data = null;
    public final static Creator<DemoResponse> CREATOR = new Creator<DemoResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DemoResponse createFromParcel(android.os.Parcel in) {
            return new DemoResponse(in);
        }

        public DemoResponse[] newArray(int size) {
            return (new DemoResponse[size]);
        }

    }
            ;

    protected DemoResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (com.cinntra.okana.model.DataValue.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public DemoResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public DemoResponse(String message, String status, List<DataValue> data) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataValue> getData() {
        return data;
    }

    public void setData(List<DataValue> data) {
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
