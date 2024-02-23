package com.cinntra.okana.model;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CompleteStageResponse implements Parcelable
{

    @SerializedName("Opp_Id")
    @Expose
    private Integer oppId;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("UpdateDate")
    @Expose
    private String updateDate;
    @SerializedName("UpdateTime")
    @Expose
    private String updateTime;
    public final static Creator<CompleteStageResponse> CREATOR = new Creator<CompleteStageResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CompleteStageResponse createFromParcel(android.os.Parcel in) {
            return new CompleteStageResponse(in);
        }

        public CompleteStageResponse[] newArray(int size) {
            return (new CompleteStageResponse[size]);
        }

    }
            ;

    protected CompleteStageResponse(android.os.Parcel in) {
        this.oppId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.remarks = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.updateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateTime = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public CompleteStageResponse() {
    }

    /**
     *
     * @param updateDate
     * @param oppId
     * @param updateTime
     * @param remarks
     * @param status
     */
    public CompleteStageResponse(Integer oppId, String remarks, String status, String updateDate, String updateTime) {
        super();
        this.oppId = oppId;
        this.remarks = remarks;
        this.status = status;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
    }

    public Integer getOppId() {
        return oppId;
    }

    public void setOppId(Integer oppId) {
        this.oppId = oppId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(oppId);
        dest.writeValue(remarks);
        dest.writeValue(status);
        dest.writeValue(updateDate);
        dest.writeValue(updateTime);
    }

    public int describeContents() {
        return 0;
    }

}
