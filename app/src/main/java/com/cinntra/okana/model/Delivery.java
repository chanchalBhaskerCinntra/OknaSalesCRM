package com.cinntra.okana.model;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Delivery implements Serializable {

    @SerializedName("DocumentNo")
    String DocumentNo;
    @SerializedName("DocEntry")
    String DocEntry;

    @SerializedName("CustomerName")
    String CustomerName;
    @SerializedName("Date")
    String DocumentDate;
    @SerializedName("DocumentTotal")
    String DocumentTotal;
    @SerializedName("Currency")
    String Currency;
    @SerializedName("DocStatus")
    String DocStatus;

    public Delivery(String documentNo, String docEntry, String customerName, String documentDate, String documentTotal,String Currency,String DocStatus) {
        this.DocumentNo    = documentNo;
        this.DocEntry      = docEntry;
        this.CustomerName  = customerName;
        this.DocumentDate  = documentDate;
        this.DocumentTotal = documentTotal;
        this.Currency = Currency;
        this.DocStatus = DocStatus;
    }

    public String getDocumentNo() {
        return DocumentNo;
    }

    public void setDocumentNo(String documentNo) {
        DocumentNo = documentNo;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getDocumentDate() {
        return DocumentDate;
    }

    public void setDocumentDate(String documentDate) {
        DocumentDate = documentDate;
    }

    public String getDocumentTotal() {
        return DocumentTotal;
    }

    public void setDocumentTotal(String documentTotal) {
        DocumentTotal = documentTotal;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getDocStatus() {
        return DocStatus;
    }

    public void setDocStatus(String docStatus) {
        DocStatus = docStatus;
    }
}
