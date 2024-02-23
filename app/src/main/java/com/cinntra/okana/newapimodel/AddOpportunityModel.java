package com.cinntra.okana.newapimodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import android.os.Parcel;
import android.os.Parcelable;

import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.SalesOpportunitiesLines;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOpportunityModel implements Parcelable, Serializable {

    @SerializedName("StatusRemarks")
    @Expose
    private String statusRemarks;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("SalesPerson")
    @Expose
    private String salesPerson;
    @SerializedName("Industry")
    @Expose
    private String industry;
    @SerializedName("OpportunityType")
    @Expose
    private String opportunityType;
    @SerializedName("ClosingType")
    @Expose
    private String closingType;
    @SerializedName("PredictedClosingDate")
    @Expose
    private String predictedClosingDate;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("UpdateDate")
    @Expose
    private String updateDate;
    @SerializedName("OpportunityName")
    @Expose
    private String opportunityName;
    @SerializedName("TotalAmountLocal")
    @Expose
    private String totalAmountLocal;
    @SerializedName("ReasonForClosing")
    @Expose
    private String reasonForClosing;
    @SerializedName("MaxSystemTotal")
    @Expose
    private String maxSystemTotal;
    @SerializedName("U_LSOURCE")
    @Expose
    private String uLsource;
    @SerializedName("SalesOpportunitiesLines")
    @Expose
    private List<SalesOpportunitiesLines> salesOpportunitiesLines;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("ClosingDate")
    @Expose
    private String closingDate;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("CurrentStageNo")
    @Expose
    private String currentStageNo;
    @SerializedName("CardCode")
    @Expose
    private String cardCode;
    @SerializedName("MaxLocalTotal")
    @Expose
    private String maxLocalTotal;
    @SerializedName("DataOwnershipfield")
    @Expose
    private String dataOwnershipfield;
    @SerializedName("ProjectCode")
    @Expose
    private String projectCode;
    @SerializedName("UpdateTime")
    @Expose
    private String updateTime;
    @SerializedName("U_FAV")
    @Expose
    private String uFav;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("U_TYPE")
    @Expose
    private String uType;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("LinkedDocumentType")
    @Expose
    private String linkedDocumentType;
    @SerializedName("U_PROBLTY")
    @Expose
    private String uProblty;
    @SerializedName("TotalAmounSystem")
    @Expose
    private String totalAmounSystem;
    @SerializedName("SequentialNo")
    @Expose
    private String SequentialNo;
    @SerializedName("CurrentStageNumber")
    @Expose
    private String CurrentStageNumber;
    @SerializedName("SalesPersonName")
    @Expose
    private String SalesPersonName;
    @SerializedName("ContactPersonName")
    @Expose
    private String ContactPersonName;
    @SerializedName("DataOwnershipName")
    @Expose
    private String DataOwnershipName;
    @SerializedName("CurrentStageName")
    @Expose
    private String CurrentStageName;
    @SerializedName("U_LEADID")
    @Expose
    private String U_LEADID;
    @SerializedName("U_LEADNM")
    @Expose
    private String U_LEADNM;
    @SerializedName("OppItem")
    @Expose
    private List<DocumentLines> OppItem = new ArrayList<>();

    @SerializedName("DocumentLines")
    @Expose
    private List<DocumentLines> documentLines = new ArrayList<>();


    public String getuLsource() {
        return uLsource;
    }

    public void setuLsource(String uLsource) {
        this.uLsource = uLsource;
    }

    public String getuFav() {
        return uFav;
    }

    public void setuFav(String uFav) {
        this.uFav = uFav;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getuProblty() {
        return uProblty;
    }

    public void setuProblty(String uProblty) {
        this.uProblty = uProblty;
    }

    public List<DocumentLines> getDocumentLines() {
        return documentLines;
    }

    public void setDocumentLines(List<DocumentLines> documentLines) {
        this.documentLines = documentLines;
    }

    public final static Creator<NewOpportunityRespose> CREATOR = new Creator<NewOpportunityRespose>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NewOpportunityRespose createFromParcel(Parcel in) {
            return new NewOpportunityRespose(in);
        }

        public NewOpportunityRespose[] newArray(int size) {
            return (new NewOpportunityRespose[size]);
        }

    };


    public AddOpportunityModel(Parcel in) {
        this.statusRemarks = in.readString();
        this.id = in.readString();
        this.customerName = in.readString();
        this.salesPerson = in.readString();
        this.industry = in.readString();
        this.opportunityType = in.readString();
        this.closingType = in.readString();
        this.predictedClosingDate = in.readString();
        this.status = in.readString();
        this.updateDate = in.readString();
        this.opportunityName = in.readString();
        this.totalAmountLocal = in.readString();
        this.reasonForClosing = in.readString();
        this.maxSystemTotal = in.readString();
        this.uLsource = in.readString();
        in.readList(this.salesOpportunitiesLines, (SalesOpportunitiesLines.class.getClassLoader()));
        this.startDate = in.readString();
        this.closingDate = in.readString();
        this.contactPerson = in.readString();
        this.currentStageNo = in.readString();
        this.cardCode = in.readString();
        this.maxLocalTotal = in.readString();
        this.dataOwnershipfield = in.readString();
        this.projectCode = in.readString();
        this.updateTime = in.readString();
        this.uFav = in.readString();
        this.remarks = in.readString();
        this.uType = in.readString();
        this.source = in.readString();
        this.linkedDocumentType = in.readString();
        this.uProblty = in.readString();
        this.totalAmounSystem = in.readString();
        this.SalesPersonName = in.readString();
        this.ContactPersonName = in.readString();
        this.DataOwnershipName = in.readString();
        this.CurrentStageName = in.readString();
        this.U_LEADID = in.readString();
        in.readList(this.OppItem, (DocumentLines.class.getClassLoader()));

    }

    /**
     * No args constructor for use in serialization
     */
    public AddOpportunityModel() {
    }

    /**
     * @param uLsource
     * @param updateDate
     * @param currentStageNo
     * @param contactPerson
     * @param industry
     * @param salesOpportunitiesLines
     * @param source
     * @param reasonForClosing
     * @param totalAmountLocal
     * @param uFav
     * @param projectCode
     * @param uType
     * @param predictedClosingDate
     * @param statusRemarks
     * @param opportunityName
     * @param cardCode
     * @param uProblty
     * @param maxLocalTotal
     * @param updateTime
     * @param totalAmounSystem
     * @param linkedDocumentType
     * @param closingDate
     * @param customerName
     * @param opportunityType
     * @param dataOwnershipfield
     * @param maxSystemTotal
     * @param closingType
     * @param salesPerson
     * @param startDate
     * @param remarks
     * @param status
     */
    public AddOpportunityModel(String statusRemarks, String customerName, String salesPerson, String industry, String opportunityType, String closingType, String predictedClosingDate, String status, String updateDate, String opportunityName, String totalAmountLocal, String reasonForClosing, String maxSystemTotal, String uLsource, List<SalesOpportunitiesLines> salesOpportunitiesLines, String startDate, String closingDate, String contactPerson, String currentStageNo, String cardCode, String maxLocalTotal, String dataOwnershipfield, String projectCode, String updateTime, String uFav, String remarks, String uType, String source, String linkedDocumentType, String uProblty, String totalAmounSystem, String U_LEADID, String U_LEADNM) {
        super();
        this.statusRemarks = statusRemarks;
        this.customerName = customerName;
        this.salesPerson = salesPerson;
        this.industry = industry;
        this.opportunityType = opportunityType;
        this.closingType = closingType;
        this.predictedClosingDate = predictedClosingDate;
        this.status = status;
        this.updateDate = updateDate;
        this.opportunityName = opportunityName;
        this.totalAmountLocal = totalAmountLocal;
        this.reasonForClosing = reasonForClosing;
        this.maxSystemTotal = maxSystemTotal;
        this.uLsource = uLsource;
        this.salesOpportunitiesLines = salesOpportunitiesLines;
        this.startDate = startDate;
        this.closingDate = closingDate;
        this.contactPerson = contactPerson;
        this.currentStageNo = currentStageNo;
        this.cardCode = cardCode;
        this.maxLocalTotal = maxLocalTotal;
        this.dataOwnershipfield = dataOwnershipfield;
        this.projectCode = projectCode;
        this.updateTime = updateTime;
        this.uFav = uFav;
        this.remarks = remarks;
        this.uType = uType;
        this.source = source;
        this.linkedDocumentType = linkedDocumentType;
        this.uProblty = uProblty;
        this.totalAmounSystem = totalAmounSystem;
        this.U_LEADID = U_LEADID;
        this.U_LEADNM = U_LEADNM;
    }

    public String getStatusRemarks() {
        return statusRemarks;
    }

    public void setStatusRemarks(String statusRemarks) {
        this.statusRemarks = statusRemarks;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        this.opportunityType = opportunityType;
    }

    public String getClosingType() {
        return closingType;
    }

    public void setClosingType(String closingType) {
        this.closingType = closingType;
    }

    public String getPredictedClosingDate() {
        return predictedClosingDate;
    }

    public void setPredictedClosingDate(String predictedClosingDate) {
        this.predictedClosingDate = predictedClosingDate;
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

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getTotalAmountLocal() {
        return totalAmountLocal;
    }

    public void setTotalAmountLocal(String totalAmountLocal) {
        this.totalAmountLocal = totalAmountLocal;
    }

    public String getReasonForClosing() {
        return reasonForClosing;
    }

    public void setReasonForClosing(String reasonForClosing) {
        this.reasonForClosing = reasonForClosing;
    }

    public String getMaxSystemTotal() {
        return maxSystemTotal;
    }

    public void setMaxSystemTotal(String maxSystemTotal) {
        this.maxSystemTotal = maxSystemTotal;
    }

    public String getULsource() {
        return uLsource;
    }

    public void setULsource(String uLsource) {
        this.uLsource = uLsource;
    }

    public List<SalesOpportunitiesLines> getSalesOpportunitiesLines() {
        return salesOpportunitiesLines;
    }

    public void setSalesOpportunitiesLines(List<SalesOpportunitiesLines> salesOpportunitiesLines) {
        this.salesOpportunitiesLines = salesOpportunitiesLines;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCurrentStageNo() {
        return currentStageNo;
    }

    public void setCurrentStageNo(String currentStageNo) {
        this.currentStageNo = currentStageNo;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getMaxLocalTotal() {
        return maxLocalTotal;
    }

    public void setMaxLocalTotal(String maxLocalTotal) {
        this.maxLocalTotal = maxLocalTotal;
    }

    public String getDataOwnershipfield() {
        return dataOwnershipfield;
    }

    public void setDataOwnershipfield(String dataOwnershipfield) {
        this.dataOwnershipfield = dataOwnershipfield;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUFav() {
        return uFav;
    }

    public void setUFav(String uFav) {
        this.uFav = uFav;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUType() {
        return uType;
    }

    public void setUType(String uType) {
        this.uType = uType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLinkedDocumentType() {
        return linkedDocumentType;
    }

    public void setLinkedDocumentType(String linkedDocumentType) {
        this.linkedDocumentType = linkedDocumentType;
    }

    public String getUProblty() {
        return uProblty;
    }

    public void setUProblty(String uProblty) {
        this.uProblty = uProblty;
    }

    public String getTotalAmounSystem() {
        return totalAmounSystem;
    }

    public void setTotalAmounSystem(String totalAmounSystem) {
        this.totalAmounSystem = totalAmounSystem;
    }

    public String getSequentialNo() {
        return SequentialNo;
    }

    public void setSequentialNo(String sequentialNo) {
        SequentialNo = sequentialNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentStageNumber() {
        return CurrentStageNumber;
    }

    public void setCurrentStageNumber(String currentStageNumber) {
        CurrentStageNumber = currentStageNumber;
    }

    public String getSalesPersonName() {
        return SalesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        SalesPersonName = salesPersonName;
    }

    public String getContactPersonName() {
        return ContactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        ContactPersonName = contactPersonName;
    }

    public String getDataOwnershipName() {
        return DataOwnershipName;
    }

    public void setDataOwnershipName(String dataOwnershipName) {
        DataOwnershipName = dataOwnershipName;
    }

    public String getCurrentStageName() {
        return CurrentStageName;
    }

    public void setCurrentStageName(String currentStageName) {
        CurrentStageName = currentStageName;
    }


    public String getU_LEADID() {
        return U_LEADID;
    }

    public void setU_LEADID(String u_LEADID) {
        U_LEADID = u_LEADID;
    }

    public String getU_LEADNM() {
        return U_LEADNM;
    }

    public void setU_LEADNM(String u_LEADNM) {
        U_LEADNM = u_LEADNM;
    }

    public List<DocumentLines> getOppItem() {
        return OppItem;
    }

    public void setOppItem(List<DocumentLines> oppItem) {
        OppItem = oppItem;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(statusRemarks);
        dest.writeString(id);
        dest.writeString(customerName);
        dest.writeString(salesPerson);
        dest.writeString(industry);
        dest.writeString(opportunityType);
        dest.writeString(closingType);
        dest.writeString(predictedClosingDate);
        dest.writeString(status);
        dest.writeString(updateDate);
        dest.writeString(opportunityName);
        dest.writeString(totalAmountLocal);
        dest.writeString(reasonForClosing);
        dest.writeString(maxSystemTotal);
        dest.writeString(uLsource);
        dest.writeList(salesOpportunitiesLines);
        dest.writeString(startDate);
        dest.writeString(closingDate);
        dest.writeString(contactPerson);
        dest.writeString(CurrentStageNumber);
        dest.writeString(currentStageNo);
        dest.writeString(cardCode);
        dest.writeString(maxLocalTotal);
        dest.writeString(dataOwnershipfield);
        dest.writeString(projectCode);
        dest.writeString(updateTime);
        dest.writeString(uFav);
        dest.writeString(remarks);
        dest.writeString(uType);
        dest.writeString(source);
        dest.writeString(linkedDocumentType);
        dest.writeString(uProblty);
        dest.writeString(totalAmounSystem);
        dest.writeString(SequentialNo);
        dest.writeString(ContactPersonName);
        dest.writeString(SalesPersonName);
        dest.writeString(DataOwnershipName);
        dest.writeString(CurrentStageName);
        dest.writeString(U_LEADID);
        dest.writeString(U_LEADNM);
        dest.writeList(OppItem);
    }


    public int describeContents() {
        return 0;
    }

}
