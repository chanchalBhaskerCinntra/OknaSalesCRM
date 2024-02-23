package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class InvoiceItem implements Serializable {

    @SerializedName("DocEntry")
    String DocEntry;

    @SerializedName("DocType")
    String DocType;
    @SerializedName("DocDate")
    String DocDate;
    @SerializedName("DocDueDate")
    String DocDueDate;
    @SerializedName("CardCode")
    String CardCode;
    @SerializedName("CardName")
    String CardName;
    @SerializedName("Address")
    String Address;
    @SerializedName("DocTotal")
    String DocTotal;
    @SerializedName("DocCurrency")
    String DocCurrency;
    @SerializedName("DocRate")
    String DocRate;
    @SerializedName("Comments")
    String Comments;
    @SerializedName("JournalMemo")
    String JournalMemo;
    @SerializedName("DocTime")
    String DocTime;
    @SerializedName("Series")
    String Series;
    @SerializedName("TaxDate")
    String TaxDate;
    @SerializedName("CreationDate")
    String CreationDate;
    @SerializedName("UpdateDate")
    String UpdateDate;
    @SerializedName("VatSumSys")
    String VatSumSys;
    @SerializedName("DocTotalSys")
    String DocTotalSys;
    @SerializedName("PaymentMethod")
    String PaymentMethod;
    @SerializedName("ControlAccount")
    String ControlAccount;

    @SerializedName("DocumentLines")
    ArrayList<QuotationDocumentLines> DocumentLines;

    @SerializedName("DocumentStatus")
    String DocumentStatus;

    public String getDocumentStatus() {
        return DocumentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        DocumentStatus = documentStatus;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getDocDueDate() {
        return DocDueDate;
    }

    public void setDocDueDate(String docDueDate) {
        DocDueDate = docDueDate;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDocTotal() {
        return DocTotal;
    }

    public void setDocTotal(String docTotal) {
        DocTotal = docTotal;
    }

    public String getDocCurrency() {
        return DocCurrency;
    }

    public void setDocCurrency(String docCurrency) {
        DocCurrency = docCurrency;
    }

    public String getDocRate() {
        return DocRate;
    }

    public void setDocRate(String docRate) {
        DocRate = docRate;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getJournalMemo() {
        return JournalMemo;
    }

    public void setJournalMemo(String journalMemo) {
        JournalMemo = journalMemo;
    }

    public String getDocTime() {
        return DocTime;
    }

    public void setDocTime(String docTime) {
        DocTime = docTime;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getTaxDate() {
        return TaxDate;
    }

    public void setTaxDate(String taxDate) {
        TaxDate = taxDate;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getVatSumSys() {
        return VatSumSys;
    }

    public void setVatSumSys(String vatSumSys) {
        VatSumSys = vatSumSys;
    }

    public String getDocTotalSys() {
        return DocTotalSys;
    }

    public void setDocTotalSys(String docTotalSys) {
        DocTotalSys = docTotalSys;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getControlAccount() {
        return ControlAccount;
    }

    public void setControlAccount(String controlAccount) {
        ControlAccount = controlAccount;
    }

    public ArrayList<QuotationDocumentLines> getDocumentLines()
      {
    return DocumentLines;
      }

    public void setDocumentLines(ArrayList<QuotationDocumentLines> documentLines)
      {
   DocumentLines = documentLines;
      }
}
