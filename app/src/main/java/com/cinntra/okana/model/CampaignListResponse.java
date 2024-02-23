package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CampaignListResponse implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("CampaignName")
    @Expose
    private String campaignName;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Frequency")
    @Expose
    private String frequency;
    @SerializedName("WeekDay")
    @Expose
    private String weekDay;
    @SerializedName("MonthlyDate")
    @Expose
    private String monthlyDate;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("QualityResponse")
    @Expose
    private String qualityResponse;
    @SerializedName("Sent")
    @Expose
    private Integer sent;
    @SerializedName("Delivered")
    @Expose
    private Integer delivered;
    @SerializedName("Opened")
    @Expose
    private Integer opened;
    @SerializedName("Responded")
    @Expose
    private Integer responded;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("CreateTime")
    @Expose
    private String createTime;
    @SerializedName("CampaignSetId")
    @Expose
    private Integer campaignSetId;
    @SerializedName("SalesEmployeeItem")
    @Expose
    private List<SalesEmployeeItem> SalesEmployeeItem = null;
    private final static long serialVersionUID = -6923468063395049691L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CampaignListResponse() {
    }

    /**
     *
     * @param endDate
     * @param delivered
     * @param opened
     * @param type
     * @param message
     * @param qualityResponse
     * @param sent
     * @param SalesEmployeeItem
     * @param frequency
     * @param campaignSetId
     * @param createTime
     * @param weekDay
     * @param id
     * @param campaignName
     * @param startDate
     * @param monthlyDate
     * @param responded
     * @param status
     * @param createDate
     */
    public CampaignListResponse(Integer id, String campaignName, String startDate, String endDate, String type, String frequency, String weekDay, String monthlyDate, String message, String qualityResponse, Integer sent, Integer delivered, Integer opened, Integer responded, Integer status, String createDate, String createTime, Integer campaignSetId, List<SalesEmployeeItem> SalesEmployeeItem) {
        super();
        this.id = id;
        this.campaignName = campaignName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.frequency = frequency;
        this.weekDay = weekDay;
        this.monthlyDate = monthlyDate;
        this.message = message;
        this.qualityResponse = qualityResponse;
        this.sent = sent;
        this.delivered = delivered;
        this.opened = opened;
        this.responded = responded;
        this.status = status;
        this.createDate = createDate;
        this.createTime = createTime;
        this.campaignSetId = campaignSetId;
        this.SalesEmployeeItem = SalesEmployeeItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMonthlyDate() {
        return monthlyDate;
    }

    public void setMonthlyDate(String monthlyDate) {
        this.monthlyDate = monthlyDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQualityResponse() {
        return qualityResponse;
    }

    public void setQualityResponse(String qualityResponse) {
        this.qualityResponse = qualityResponse;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
    }

    public Integer getDelivered() {
        return delivered;
    }

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    public Integer getOpened() {
        return opened;
    }

    public void setOpened(Integer opened) {
        this.opened = opened;
    }

    public Integer getResponded() {
        return responded;
    }

    public void setResponded(Integer responded) {
        this.responded = responded;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCampaignSetId() {
        return campaignSetId;
    }

    public void setCampaignSetId(Integer campaignSetId) {
        this.campaignSetId = campaignSetId;
    }

    public List<SalesEmployeeItem> getSalesEmployeeItem() {
        return SalesEmployeeItem;
    }

    public void setSalesEmployeeItem(List<SalesEmployeeItem> SalesEmployeeItem) {
        this.SalesEmployeeItem = SalesEmployeeItem;
    }

}