package com.cinntra.okana.model.PerformaInvoiceModel;

import com.cinntra.okana.model.AddressExt;
import com.cinntra.okana.model.ContactEmployeesModel;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuotationUpdateResponseModel implements Serializable {
    @SerializedName("DocEntry")
    @Expose
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
    @SerializedName("Address2")
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

    @SerializedName("DiscountPercent")
    String DiscountPercent;
    @SerializedName("VatSum")
    String VatSum;

    @SerializedName("DownPaymentStatus")
    String DownPaymentStatus;

    @SerializedName("DocumentLines")
    ArrayList<com.cinntra.okana.model.DocumentLines> DocumentLines;

    @SerializedName("DocumentStatus")
    String DocumentStatus;
    @SerializedName("TotalDiscount")
    String TotalDiscount;
    @SerializedName("TotalEqualizationTax")
    // @SerializedName("TaxTotal")
    String TotalEqualizationTax;
    @SerializedName("RoundingDiffAmount")
    String RoundingDiffAmount;
    @SerializedName("SeriesString")
    String SeriesString;
    @SerializedName("DocNum")
    String DocNum;
    @SerializedName("BPLName")
    String BPLName;
    @SerializedName("ShipToDescription")
    String ShipToDescription;
    @SerializedName("DownPaymentType")
    String DownPaymentType;
    @SerializedName("ContactPersonCode")
    @Expose
//    List<ContactEmployeesModel> ContactPersonCode;
    String ContactPersonCode;
    @SerializedName("U_OPPRNM")
    @Expose
    String OpportunityName;

    @SerializedName("SalesPersonCode")
    @Expose
//    List<SalesEmployeeItem> SalesPersonCode;
    String SalesPersonCode;

    @SerializedName("U_FAV")
    @Expose
    String U_FAV;

    @SerializedName("U_OPPID")
    @Expose
    String U_OPPID;

    @SerializedName("CreateDate")
    @Expose
    String CreateDate;
    @SerializedName("CreateTime")
    @Expose
    String CreateTime;

    @SerializedName("UpdateTime")
    @Expose
    String UpdateTime;
    @SerializedName("id")
    @Expose
    String id;
    String qt_Id;

    @SerializedName("U_QUOTNM")
    @Expose
    String U_QUOTNM;


    public String getQt_Id() {
        return qt_Id;
    }

    public void setQt_Id(String qt_Id) {
        this.qt_Id = qt_Id;
    }

    @SerializedName("AddressExtension")
    @Expose
    AddressExt addressExtension;



    public AddressExt getAddressExtension() {
        return addressExtension;
    }

    public void setAddressExtension(AddressExt addressExtension) {
        this.addressExtension = addressExtension;
    }


    public String getU_OPPID() {
        return U_OPPID;
    }

    public void setU_OPPID(String u_OPPID) {
        U_OPPID = u_OPPID;
    }



    public String getU_FAV() {
        return U_FAV;
    }

    public void setU_FAV(String u_FAV) {
        U_FAV = u_FAV;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getDownPaymentType() {
        return DownPaymentType;
    }

    public void setDownPaymentType(String downPaymentType) {
        DownPaymentType = downPaymentType;
    }

    public String getShipToDescription() {
        return ShipToDescription;
    }

    public void setShipToDescription(String shipToDescription) {
        ShipToDescription = shipToDescription;
    }
    public String getBPLName() {
        return BPLName;
    }

    public void setBPLName(String BPLName) {
        this.BPLName = BPLName;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String docNum) {
        DocNum = docNum;
    }

    public String getSeriesString() {
        return SeriesString;
    }

    public void setSeriesString(String seriesString) {
        SeriesString = seriesString;
    }

    public String getRoundingDiffAmount() {
        return RoundingDiffAmount;
    }

    public void setRoundingDiffAmount(String roundingDiffAmount) {
        RoundingDiffAmount = roundingDiffAmount;
    }

    public String getTotalEqualizationTax() {
        return TotalEqualizationTax;
    }

    public void setTotalEqualizationTax(String totalEqualizationTax) {
        TotalEqualizationTax = totalEqualizationTax;
    }
    public String getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        TotalDiscount = totalDiscount;
    }

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
    }


    public String getDownPaymentStatus() {
        return DownPaymentStatus;
    }

    public void setDownPaymentStatus(String downPaymentStatus) {
        DownPaymentStatus = downPaymentStatus;
    }

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

    public String getVatSum() {
        return VatSum;
    }

    public void setVatSum(String vatSum) {
        VatSum = vatSum;
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

    public ArrayList<DocumentLines> getDocumentLines()
    {
        return DocumentLines;
    }

    public void setDocumentLines(ArrayList<DocumentLines> documentLines)
    {
        DocumentLines = documentLines;
    }

    public  String getContactPersonCode() {
        return ContactPersonCode;
    }

    public void setContactPersonCode(String contactPersonCode) {
        ContactPersonCode = contactPersonCode;
    }

    public String getOpportunityName() {
        return OpportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        OpportunityName = opportunityName;
    }

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;


    }

    public String getU_QUOTNM() {
        return U_QUOTNM;
    }

    public void setU_QUOTNM(String u_QUOTNM) {
        U_QUOTNM = u_QUOTNM;
    }
}

