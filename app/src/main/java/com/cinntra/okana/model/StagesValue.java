package com.cinntra.okana.model;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StagesValue implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("SequenceNo")
    @Expose
    private String sequenceNo;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Stageno")
    @Expose
    private String stageno;
    @SerializedName("ClosingPercentage")
    @Expose
    private String closingPercentage;
    @SerializedName("Cancelled")
    @Expose
    private String cancelled;
    @SerializedName("IsSales")
    @Expose
    private String isSales;
    @SerializedName("IsPurchasing")
    @Expose
    private String isPurchasing;
    @SerializedName("CreateDate")
    @Expose
    private String CreateDate;
    @SerializedName("UpdateDate")
    @Expose
    private String UpdateDate;
    @SerializedName("UpdateTime")
    @Expose
    private String UpdateTime;
    @SerializedName("Opp_Id")
    @Expose
    private Integer oppId;
    @SerializedName("Status")
    @Expose
    private Integer Status;

    @SerializedName("Comment")
    @Expose
    private String Comment;
    @SerializedName("File")
    @Expose
    private String File;

    public final static Creator<StagesValue> CREATOR = new Creator<StagesValue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StagesValue createFromParcel(android.os.Parcel in) {
            return new StagesValue(in);
        }

        public StagesValue[] newArray(int size) {
            return (new StagesValue[size]);
        }

    }
            ;

    protected StagesValue(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sequenceNo = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.stageno = ((String) in.readValue((Integer.class.getClassLoader())));
        this.closingPercentage = ((String) in.readValue((String.class.getClassLoader())));
        this.cancelled = ((String) in.readValue((String.class.getClassLoader())));
        this.isSales = ((String) in.readValue((String.class.getClassLoader())));
        this.UpdateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.CreateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.isPurchasing = ((String) in.readValue((String.class.getClassLoader())));
        this.UpdateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.Comment = ((String) in.readValue((String.class.getClassLoader())));
        this.File    = ((String) in.readValue((String.class.getClassLoader())));
        this.oppId   = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.Status  = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public StagesValue() {
    }

    /**
     *
     * @param sequenceNo
     * @param name
     * @param isSales
     * @param stageno
     * @param cancelled
     * @param closingPercentage
     * @param oppId
     * @param id
     * @param isPurchasing
     */
    public StagesValue(Integer id, String sequenceNo, String name, String stageno, String closingPercentage, String cancelled, String isSales, String isPurchasing, Integer oppId) {
        super();
        this.id = id;
        this.sequenceNo = sequenceNo;
        this.name = name;
        this.stageno = stageno;
        this.closingPercentage = closingPercentage;
        this.cancelled = cancelled;
        this.isSales = isSales;
        this.isPurchasing = isPurchasing;
        this.oppId = oppId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStageno() {
        return stageno;
    }

    public void setStageno(String stageno) {
        this.stageno = stageno;
    }

    public String getClosingPercentage() {
        return closingPercentage;
    }

    public void setClosingPercentage(String closingPercentage) {
        this.closingPercentage = closingPercentage;
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public String getIsSales() {
        return isSales;
    }

    public void setIsSales(String isSales) {
        this.isSales = isSales;
    }

    public String getIsPurchasing() {
        return isPurchasing;
    }

    public void setIsPurchasing(String isPurchasing) {
        this.isPurchasing = isPurchasing;
    }

    public Integer getOppId() {
        return oppId;
    }

    public void setOppId(Integer oppId) {
        this.oppId = oppId;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(sequenceNo);
        dest.writeValue(CreateDate);
        dest.writeValue(name);
        dest.writeValue(stageno);
        dest.writeValue(closingPercentage);
        dest.writeValue(cancelled);
        dest.writeValue(isSales);
        dest.writeValue(isPurchasing);
        dest.writeValue(oppId);
        dest.writeValue(UpdateDate);
        dest.writeValue(Status);
        dest.writeValue(UpdateTime);
        dest.writeValue(Comment);
        dest.writeValue(File);
    }

    public int describeContents() {
        return 0;
    }

}
