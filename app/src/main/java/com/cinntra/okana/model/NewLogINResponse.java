package com.cinntra.okana.model;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewLogINResponse implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("token")
    private String token;
    @SerializedName("data")
    @Expose
    private NewLoginData LogInDetail;

    @SerializedName("SAP")
    @Expose
    private Sap sap;
    public final static Creator<NewLogINResponse> CREATOR = new Creator<NewLogINResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewLogINResponse createFromParcel(android.os.Parcel in) {
            return new NewLogINResponse(in);
        }

        public NewLogINResponse[] newArray(int size) {
            return (new NewLogINResponse[size]);
        }

    }
            ;

    protected NewLogINResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.LogInDetail = ((NewLoginData) in.readValue((NewLoginData.class.getClassLoader())));
        this.sap = ((Sap) in.readValue((Sap.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public NewLogINResponse() {
    }

    /**
     *
     * @param sap
     * @param LogInDetail
     * @param message
     * @param status
     */
    public NewLogINResponse(String message, Integer status, NewLoginData LogInDetail, Sap sap) {
        super();
        this.message = message;
        this.status = status;
        this.LogInDetail = LogInDetail;
        this.sap = sap;
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

    public NewLoginData getLogInDetail() {
        return LogInDetail;
    }

    public void setLogInDetail(NewLoginData LogInDetail) {
        this.LogInDetail = LogInDetail;
    }

    public Sap getSap() {
        return sap;
    }

    public void setSap(Sap sap) {
        this.sap = sap;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(status);
        dest.writeValue(LogInDetail);
        dest.writeValue(sap);
    }

    public int describeContents() {
        return 0;
    }

}
