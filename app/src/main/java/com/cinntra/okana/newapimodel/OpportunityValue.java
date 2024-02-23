package com.cinntra.okana.newapimodel;


import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OpportunityValue implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("SequentialNo")
    @Expose
    private String sequentialNo;
    @SerializedName("CardCode")
    @Expose
    private String cardCode;
    @SerializedName("SalesPerson")
    @Expose
    private String salesPerson;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("PredictedClosingDate")
    @Expose
    private String predictedClosingDate;
    @SerializedName("MaxLocalTotal")
    @Expose
    private String maxLocalTotal;
    @SerializedName("MaxSystemTotal")
    @Expose
    private String maxSystemTotal;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ReasonForClosing")
    @Expose
    private String reasonForClosing;
    @SerializedName("TotalAmountLocal")
    @Expose
    private String totalAmountLocal;
    @SerializedName("TotalAmounSystem")
    @Expose
    private String totalAmounSystem;
    @SerializedName("CurrentStageNo")
    @Expose
    private String currentStageNo;
    @SerializedName("OpportunityName")
    @Expose
    private String opportunityName;
    @SerializedName("Industry")
    @Expose
    private String industry;
    @SerializedName("LinkedDocumentType")
    @Expose
    private String linkedDocumentType;
    @SerializedName("StatusRemarks")
    @Expose
    private String statusRemarks;
    @SerializedName("ProjectCode")
    @Expose
    private String projectCode;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("ClosingDate")
    @Expose
    private String closingDate;
    @SerializedName("ClosingType")
    @Expose
    private String closingType;
    @SerializedName("OpportunityType")
    @Expose
    private String opportunityType;
    @SerializedName("UpdateDate")
    @Expose
    private String updateDate;
    @SerializedName("UpdateTime")
    @Expose
    private String updateTime;
    @SerializedName("U_TYPE")
    @Expose
    private String uType;
    @SerializedName("U_LSOURCE")
    @Expose
    private String U_LSOURCE;
    @SerializedName("U_FAV")
    @Expose
    private String U_FAV;
    @SerializedName("U_PROBLTY")
    @Expose
    private String U_PROBLTY;
    public final static Creator<OpportunityValue> CREATOR = new Creator<OpportunityValue>() {


        @SuppressWarnings({
                "unchecked"
        })
        public OpportunityValue createFromParcel(android.os.Parcel in) {
            return new OpportunityValue(in);
        }

        public OpportunityValue[] newArray(int size) {
            return (new OpportunityValue[size]);
        }

    }
            ;

    protected OpportunityValue(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sequentialNo = ((String) in.readValue((String.class.getClassLoader())));
        this.cardCode = ((String) in.readValue((String.class.getClassLoader())));
        this.salesPerson = ((String) in.readValue((String.class.getClassLoader())));
        this.contactPerson = ((String) in.readValue((String.class.getClassLoader())));
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.startDate = ((String) in.readValue((String.class.getClassLoader())));
        this.predictedClosingDate = ((String) in.readValue((String.class.getClassLoader())));
        this.maxLocalTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.maxSystemTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.remarks = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.reasonForClosing = ((String) in.readValue((String.class.getClassLoader())));
        this.totalAmountLocal = ((String) in.readValue((String.class.getClassLoader())));
        this.totalAmounSystem = ((String) in.readValue((String.class.getClassLoader())));
        this.currentStageNo = ((String) in.readValue((String.class.getClassLoader())));
        this.opportunityName = ((String) in.readValue((String.class.getClassLoader())));
        this.industry = ((String) in.readValue((String.class.getClassLoader())));
        this.linkedDocumentType = ((String) in.readValue((String.class.getClassLoader())));
        this.statusRemarks = ((String) in.readValue((String.class.getClassLoader())));
        this.projectCode = ((String) in.readValue((String.class.getClassLoader())));
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.closingDate = ((String) in.readValue((String.class.getClassLoader())));
        this.closingType = ((String) in.readValue((String.class.getClassLoader())));
        this.opportunityType = ((String) in.readValue((String.class.getClassLoader())));
        this.updateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.updateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.uType = ((String) in.readValue((String.class.getClassLoader())));
        this.U_LSOURCE = ((String) in.readValue((String.class.getClassLoader())));
        this.U_FAV = ((String) in.readValue((String.class.getClassLoader())));
        this.U_PROBLTY = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public OpportunityValue() {
    }

    /**
     *
     * @param updateDate
     * @param currentStageNo
     * @param sequentialNo
     * @param contactPerson
     * @param industry
     * @param source
     * @param reasonForClosing
     * @param totalAmountLocal
     * @param projectCode
     * @param uType
     * @param predictedClosingDate
     * @param id
     * @param statusRemarks
     * @param opportunityName
     * @param cardCode
     * @param maxLocalTotal
     * @param totalAmounSystem
     * @param updateTime
     * @param linkedDocumentType
     * @param closingDate
     * @param customerName
     * @param opportunityType
     * @param maxSystemTotal
     * @param closingType
     * @param salesPerson
     * @param startDate
     * @param remarks
     * @param status
     */
    public OpportunityValue(Integer id, String sequentialNo, String cardCode, String salesPerson, String contactPerson, String source, String startDate, String predictedClosingDate, String maxLocalTotal, String maxSystemTotal, String remarks, String status, String reasonForClosing, String totalAmountLocal, String totalAmounSystem, String currentStageNo, String opportunityName, String industry, String linkedDocumentType, String statusRemarks, String projectCode, String customerName, String closingDate, String closingType, String opportunityType, String updateDate, String updateTime, String uType, String U_LSource, String uFav, String u_PROBLTY) {
        super();
        this.id = id;
        this.sequentialNo = sequentialNo;
        this.cardCode = cardCode;
        this.salesPerson = salesPerson;
        this.contactPerson = contactPerson;
        this.source = source;
        this.startDate = startDate;
        this.predictedClosingDate = predictedClosingDate;
        this.maxLocalTotal = maxLocalTotal;
        this.maxSystemTotal = maxSystemTotal;
        this.remarks = remarks;
        this.status = status;
        this.reasonForClosing = reasonForClosing;
        this.totalAmountLocal = totalAmountLocal;
        this.totalAmounSystem = totalAmounSystem;
        this.currentStageNo = currentStageNo;
        this.opportunityName = opportunityName;
        this.industry = industry;
        this.linkedDocumentType = linkedDocumentType;
        this.statusRemarks = statusRemarks;
        this.projectCode = projectCode;
        this.customerName = customerName;
        this.closingDate = closingDate;
        this.closingType = closingType;
        this.opportunityType = opportunityType;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
        this.uType = uType;
        this.U_LSOURCE =U_LSource;
        this.U_FAV =uFav;
        this.U_PROBLTY =u_PROBLTY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSequentialNo() {
        return sequentialNo;
    }

    public void setSequentialNo(String sequentialNo) {
        this.sequentialNo = sequentialNo;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPredictedClosingDate() {
        return predictedClosingDate;
    }

    public void setPredictedClosingDate(String predictedClosingDate) {
        this.predictedClosingDate = predictedClosingDate;
    }

    public String getMaxLocalTotal() {
        return maxLocalTotal;
    }

    public void setMaxLocalTotal(String maxLocalTotal) {
        this.maxLocalTotal = maxLocalTotal;
    }

    public String getMaxSystemTotal() {
        return maxSystemTotal;
    }

    public void setMaxSystemTotal(String maxSystemTotal) {
        this.maxSystemTotal = maxSystemTotal;
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

    public String getReasonForClosing() {
        return reasonForClosing;
    }

    public void setReasonForClosing(String reasonForClosing) {
        this.reasonForClosing = reasonForClosing;
    }

    public String getTotalAmountLocal() {
        return totalAmountLocal;
    }

    public void setTotalAmountLocal(String totalAmountLocal) {
        this.totalAmountLocal = totalAmountLocal;
    }

    public String getTotalAmounSystem() {
        return totalAmounSystem;
    }

    public void setTotalAmounSystem(String totalAmounSystem) {
        this.totalAmounSystem = totalAmounSystem;
    }

    public String getCurrentStageNo() {
        return currentStageNo;
    }

    public void setCurrentStageNo(String currentStageNo) {
        this.currentStageNo = currentStageNo;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLinkedDocumentType() {
        return linkedDocumentType;
    }

    public void setLinkedDocumentType(String linkedDocumentType) {
        this.linkedDocumentType = linkedDocumentType;
    }

    public String getStatusRemarks() {
        return statusRemarks;
    }

    public void setStatusRemarks(String statusRemarks) {
        this.statusRemarks = statusRemarks;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getClosingType() {
        return closingType;
    }

    public void setClosingType(String closingType) {
        this.closingType = closingType;
    }

    public String getOpportunityType() {
        return opportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        this.opportunityType = opportunityType;
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

    public String getUType() {
        return uType;
    }

    public void setUType(String uType) {
        this.uType = uType;
    }

    public String getU_LSOURCE() {
        return U_LSOURCE;
    }

    public void setU_LSOURCE(String u_LSOURCE) {
        U_LSOURCE = u_LSOURCE;
    }

    public String getU_FAV() {
        return U_FAV;
    }

    public void setU_FAV(String u_FAV) {
        U_FAV = u_FAV;
    }

    public String getU_PROBLTY() {
        return U_PROBLTY;
    }

    public void setU_PROBLTY(String u_PROBLTY) {
        U_PROBLTY = u_PROBLTY;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(sequentialNo);
        dest.writeValue(cardCode);
        dest.writeValue(salesPerson);
        dest.writeValue(contactPerson);
        dest.writeValue(source);
        dest.writeValue(startDate);
        dest.writeValue(predictedClosingDate);
        dest.writeValue(maxLocalTotal);
        dest.writeValue(maxSystemTotal);
        dest.writeValue(remarks);
        dest.writeValue(status);
        dest.writeValue(reasonForClosing);
        dest.writeValue(totalAmountLocal);
        dest.writeValue(totalAmounSystem);
        dest.writeValue(currentStageNo);
        dest.writeValue(opportunityName);
        dest.writeValue(industry);
        dest.writeValue(linkedDocumentType);
        dest.writeValue(statusRemarks);
        dest.writeValue(projectCode);
        dest.writeValue(customerName);
        dest.writeValue(closingDate);
        dest.writeValue(closingType);
        dest.writeValue(opportunityType);
        dest.writeValue(updateDate);
        dest.writeValue(updateTime);
        dest.writeValue(uType);
        dest.writeValue(U_FAV);
        dest.writeValue(U_LSOURCE);
        dest.writeValue(U_PROBLTY);
    }

    public int describeContents() {
        return 0;
    }

}
