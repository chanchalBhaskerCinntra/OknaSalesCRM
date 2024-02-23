package com.cinntra.okana.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HeirarchiResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<EmployeeValue> data = null;
    public final static Parcelable.Creator<HeirarchiResponse> CREATOR = new Parcelable.Creator<HeirarchiResponse>() {



        public HeirarchiResponse createFromParcel(android.os.Parcel in) {
            return new HeirarchiResponse(in);
        }

        public HeirarchiResponse[] newArray(int size) {
            return (new HeirarchiResponse[size]);
        }

    }
            ;

    protected HeirarchiResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (EmployeeValue.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public HeirarchiResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public HeirarchiResponse(String message, Integer status, List<EmployeeValue> data) {
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

    public List<EmployeeValue> getData() {
        return data;
    }

    public void setData(List<EmployeeValue> data) {
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
