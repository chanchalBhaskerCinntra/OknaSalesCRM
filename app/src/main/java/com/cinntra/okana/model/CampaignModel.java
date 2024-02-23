package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CampaignModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("CampaignSetName")
    @Expose
    private String campaignSetName;
    @SerializedName("CampaignAccess")
    @Expose
    private String campaignAccess;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("LeadSource")
    @Expose
    private String leadSource;
    @SerializedName("LeadPriority")
    @Expose
    private String leadPriority;
    @SerializedName("LeadStatus")
    @Expose
    private String leadStatus;
    @SerializedName("LeadFromDate")
    @Expose
    private String leadFromDate;
    @SerializedName("LeadToDate")
    @Expose
    private String leadToDate;
    @SerializedName("OppType")
    @Expose
    private String oppType;
    @SerializedName("OppSalePerson")
    @Expose
    private List<NewLoginData> oppSalePerson = null;
    @SerializedName("OppStage")
    @Expose
    private String oppStage;
    @SerializedName("OppFromDate")
    @Expose
    private String oppFromDate;
    @SerializedName("OppToDate")
    @Expose
    private String oppToDate;
    @SerializedName("BPType")
    @Expose
    private String bPType;
    @SerializedName("BPSalePerson")
    @Expose
    private List<NewLoginData> bPSalePerson;
    @SerializedName("BPCountry")
    @Expose
    private String bPCountry;
    @SerializedName("BPCountryCode")
    @Expose
    private String bPCountryCode;
    @SerializedName("BPState")
    @Expose
    private String bPState;
    @SerializedName("BPStateCode")
    @Expose
    private String bPStateCode;
    @SerializedName("BPIndustry")
    @Expose
    private List<IndustryItem> bPIndustry;
    @SerializedName("BPFromDate")
    @Expose
    private String bPFromDate;
    @SerializedName("BPToDate")
    @Expose
    private String bPToDate;
    @SerializedName("MemberList")
    @Expose
    private List<MemberList> memberList = null;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("CreateTime")
    @Expose
    private String createTime;
    @SerializedName("CampaignSetOwner")
    @Expose
    private List<CampaignSetOwner> campaignSetOwner = null;
    @SerializedName("CreateBy")
    @Expose
    private List<CreateBy> createBy = null;
    private final static long serialVersionUID = 526302999024249157L;

    /**
     * No args constructor for use in serialization
     *
     */
    public CampaignModel() {
    }

    /**
     *
     * @param campaignAccess
     * @param leadStatus
     * @param bPType
     * @param bPIndustry
     * @param bPStateCode
     * @param description
     * @param oppType
     * @param oppStage
     * @param memberList
     * @param leadToDate
     * @param campaignSetOwner
     * @param oppFromDate
     * @param bPSalePerson
     * @param id
     * @param bPCountryCode
     * @param createDate
     * @param campaignSetName
     * @param bPFromDate
     * @param oppToDate
     * @param leadSource
     * @param bPCountry
     * @param leadFromDate
     * @param bPState
     * @param bPToDate
     * @param createBy
     * @param createTime
     * @param oppSalePerson
     * @param leadPriority
     * @param status
     */
    public CampaignModel(Integer id, String campaignSetName, String campaignAccess, String description, String leadSource, String leadPriority, String leadStatus, String leadFromDate, String leadToDate, String oppType, List<NewLoginData> oppSalePerson, String oppStage, String oppFromDate, String oppToDate, String bPType, List<NewLoginData> bPSalePerson, String bPCountry, String bPCountryCode, String bPState, String bPStateCode, List<IndustryItem> bPIndustry, String bPFromDate, String bPToDate, List<MemberList> memberList, Integer status, String createDate, String createTime, List<CampaignSetOwner> campaignSetOwner, List<CreateBy> createBy) {
        super();
        this.id = id;
        this.campaignSetName = campaignSetName;
        this.campaignAccess = campaignAccess;
        this.description = description;
        this.leadSource = leadSource;
        this.leadPriority = leadPriority;
        this.leadStatus = leadStatus;
        this.leadFromDate = leadFromDate;
        this.leadToDate = leadToDate;
        this.oppType = oppType;
        this.oppSalePerson = oppSalePerson;
        this.oppStage = oppStage;
        this.oppFromDate = oppFromDate;
        this.oppToDate = oppToDate;
        this.bPType = bPType;
        this.bPSalePerson = bPSalePerson;
        this.bPCountry = bPCountry;
        this.bPCountryCode = bPCountryCode;
        this.bPState = bPState;
        this.bPStateCode = bPStateCode;
        this.bPIndustry = bPIndustry;
        this.bPFromDate = bPFromDate;
        this.bPToDate = bPToDate;
        this.memberList = memberList;
        this.status = status;
        this.createDate = createDate;
        this.createTime = createTime;
        this.campaignSetOwner = campaignSetOwner;
        this.createBy = createBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCampaignSetName() {
        return campaignSetName;
    }

    public void setCampaignSetName(String campaignSetName) {
        this.campaignSetName = campaignSetName;
    }

    public String getCampaignAccess() {
        return campaignAccess;
    }

    public void setCampaignAccess(String campaignAccess) {
        this.campaignAccess = campaignAccess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }

    public String getLeadPriority() {
        return leadPriority;
    }

    public void setLeadPriority(String leadPriority) {
        this.leadPriority = leadPriority;
    }

    public String getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }

    public String getLeadFromDate() {
        return leadFromDate;
    }

    public void setLeadFromDate(String leadFromDate) {
        this.leadFromDate = leadFromDate;
    }

    public String getLeadToDate() {
        return leadToDate;
    }

    public void setLeadToDate(String leadToDate) {
        this.leadToDate = leadToDate;
    }

    public String getOppType() {
        return oppType;
    }

    public void setOppType(String oppType) {
        this.oppType = oppType;
    }

    public List<NewLoginData> getOppSalePerson() {
        return oppSalePerson;
    }

    public void setOppSalePerson(List<NewLoginData> oppSalePerson) {
        this.oppSalePerson = oppSalePerson;
    }

    public String getOppStage() {
        return oppStage;
    }

    public void setOppStage(String oppStage) {
        this.oppStage = oppStage;
    }

    public String getOppFromDate() {
        return oppFromDate;
    }

    public void setOppFromDate(String oppFromDate) {
        this.oppFromDate = oppFromDate;
    }

    public String getOppToDate() {
        return oppToDate;
    }

    public void setOppToDate(String oppToDate) {
        this.oppToDate = oppToDate;
    }

    public String getBPType() {
        return bPType;
    }

    public void setBPType(String bPType) {
        this.bPType = bPType;
    }

    public List<NewLoginData> getBPSalePerson() {
        return bPSalePerson;
    }

    public void setBPSalePerson(List<NewLoginData> bPSalePerson) {
        this.bPSalePerson = bPSalePerson;
    }

    public String getBPCountry() {
        return bPCountry;
    }

    public void setBPCountry(String bPCountry) {
        this.bPCountry = bPCountry;
    }

    public String getBPCountryCode() {
        return bPCountryCode;
    }

    public void setBPCountryCode(String bPCountryCode) {
        this.bPCountryCode = bPCountryCode;
    }

    public String getBPState() {
        return bPState;
    }

    public void setBPState(String bPState) {
        this.bPState = bPState;
    }

    public String getBPStateCode() {
        return bPStateCode;
    }

    public void setBPStateCode(String bPStateCode) {
        this.bPStateCode = bPStateCode;
    }

    public List<IndustryItem> getBPIndustry() {
        return bPIndustry;
    }

    public void setBPIndustry(List<IndustryItem> bPIndustry) {
        this.bPIndustry = bPIndustry;
    }

    public String getBPFromDate() {
        return bPFromDate;
    }

    public void setBPFromDate(String bPFromDate) {
        this.bPFromDate = bPFromDate;
    }

    public String getBPToDate() {
        return bPToDate;
    }

    public void setBPToDate(String bPToDate) {
        this.bPToDate = bPToDate;
    }

    public List<MemberList> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberList> memberList) {
        this.memberList = memberList;
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

    public List<CampaignSetOwner> getCampaignSetOwner() {
        return campaignSetOwner;
    }

    public void setCampaignSetOwner(List<CampaignSetOwner> campaignSetOwner) {
        this.campaignSetOwner = campaignSetOwner;
    }

    public List<CreateBy> getCreateBy() {
        return createBy;
    }

    public void setCreateBy(List<CreateBy> createBy) {
        this.createBy = createBy;
    }

}

