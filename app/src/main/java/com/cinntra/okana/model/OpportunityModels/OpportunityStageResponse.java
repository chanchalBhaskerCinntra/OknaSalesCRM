package com.cinntra.okana.model.OpportunityModels;

import java.util.List;

import android.os.Parcelable;

import com.cinntra.okana.model.StagesValue;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OpportunityStageResponse implements Parcelable
{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<StagesValue> data = null;
    public final static Creator<OpportunityStageResponse> CREATOR = new Creator<OpportunityStageResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public OpportunityStageResponse createFromParcel(android.os.Parcel in) {
            return new OpportunityStageResponse(in);
        }

        public OpportunityStageResponse[] newArray(int size) {
            return (new OpportunityStageResponse[size]);
        }

    }
            ;

    protected OpportunityStageResponse(android.os.Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.data, (com.cinntra.okana.model.StagesValue.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public OpportunityStageResponse() {
    }

    /**
     *
     * @param data
     * @param message
     * @param status
     */
    public OpportunityStageResponse(String message, Integer status, List<StagesValue> data) {
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

    public List<StagesValue> getData() {
        return data;
    }

    public void setData(List<StagesValue> data) {
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