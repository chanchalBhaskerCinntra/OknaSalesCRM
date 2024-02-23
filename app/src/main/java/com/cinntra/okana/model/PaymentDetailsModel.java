package com.cinntra.okana.model;

import com.cinntra.okana.newapimodel.AttachDocument;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentDetailsModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("TransactId")
    @Expose
    private String transactId;
    @SerializedName("TotalAmt")
    @Expose
    private String totalAmt;
    @SerializedName("TransactMod")
    @Expose
    private String transactMod;
    @SerializedName("DueAmount")
    @Expose
    private String dueAmount;
    @SerializedName("PaymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("ReceivedAmount")
    @Expose
    private String receivedAmount;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("createTime")
    @Expose
    private String createTime;
    @SerializedName("createdBy")
    @Expose
    private List<CreateBy> createdBy;
    @SerializedName("updateDate")
    @Expose
    private String updateDate;
    @SerializedName("updateTime")
    @Expose
    private String updateTime;
    @SerializedName("updatedBy")
    @Expose
    private List<CreateBy> updatedBy = null;
    @SerializedName("CardCode")
    @Expose
    private String cardCode;
    @SerializedName("Attach")
    @Expose
    private List<AttachDocument> attach = null;
    private final static long serialVersionUID = 8259097755936824817L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaymentDetailsModel() {
    }

    /**
     *
     * @param updateDate
     * @param updatedBy
     * @param transactId
     * @param cardCode
     * @param updateTime
     * @param receivedAmount
     * @param totalAmt
     * @param dueAmount
     * @param createTime
     * @param createdBy
     * @param transactMod
     * @param id
     * @param attach
     * @param invoiceNo
     * @param paymentDate
     * @param remarks
     * @param createDate
     */
    public PaymentDetailsModel(Integer id, String invoiceNo, String transactId, String totalAmt, String transactMod, String dueAmount, String paymentDate, String receivedAmount, String remarks, String createDate, String createTime, List<CreateBy> createdBy, String updateDate, String updateTime, List<CreateBy> updatedBy, String cardCode, List<AttachDocument> attach) {
        super();
        this.id = id;
        this.invoiceNo = invoiceNo;
        this.transactId = transactId;
        this.totalAmt = totalAmt;
        this.transactMod = transactMod;
        this.dueAmount = dueAmount;
        this.paymentDate = paymentDate;
        this.receivedAmount = receivedAmount;
        this.remarks = remarks;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
        this.updatedBy = updatedBy;
        this.cardCode = cardCode;
        this.attach = attach;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getTransactId() {
        return transactId;
    }

    public void setTransactId(String transactId) {
        this.transactId = transactId;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTransactMod() {
        return transactMod;
    }

    public void setTransactMod(String transactMod) {
        this.transactMod = transactMod;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public List<CreateBy> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<CreateBy> createdBy) {
        this.createdBy = createdBy;
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

    public List<CreateBy> getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(List<CreateBy> updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public List<AttachDocument> getAttach() {
        return attach;
    }

    public void setAttach(List<AttachDocument> attach) {
        this.attach = attach;
    }

}