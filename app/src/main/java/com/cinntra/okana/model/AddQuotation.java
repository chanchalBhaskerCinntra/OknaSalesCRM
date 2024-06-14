package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddQuotation implements Parcelable {

    @SerializedName("CardCode")
    @Expose
    private String CardCode;
    @SerializedName("CardName")
    @Expose
    private String CardName;
    @SerializedName("U_OPPRNM")
    @Expose
    String OpportunityName;

    @SerializedName("DocDate")
    @Expose
    String PostingDate;
    @SerializedName("DocDueDate")
    @Expose
    String ValidDate;
    @SerializedName("TaxDate")
    @Expose
    String DocumentDate;
    String GlassDate;
    String CoatingDate;
    String DeliveryDate;
    @SerializedName("ContactPersonCode")
    @Expose
    String salesPerson;
    @SerializedName("SalesPersonCode")
    @Expose
    String SalesPersonCode;

    @SerializedName("Comments")
    @Expose
    String Remarks;

    @SerializedName("BPL_IDAssignedToInvoice")
    @Expose
    String BPL_IDAssignedToInvoice;
    @SerializedName("BPLName")
    @Expose
    String BPLName;
    @SerializedName("UpdateTime")
    @Expose
    String UpdateTime;
    @SerializedName("UpdateDate")
    @Expose
    String UpdateDate;
    @SerializedName("CreateTime")
    @Expose
    String CreateTime;
    @SerializedName("CreateDate")
    @Expose
    String CreateDate;
    @SerializedName("U_OPPID")
    @Expose
    String U_OPPID;
    @SerializedName("U_QUOTID")
    @Expose
    String U_QUOTID;


    @SerializedName("U_QUOTNM")
    @Expose
    String U_QUOTNM;
    float NetTotal;
    String QuoteNo;
    String MatirialType = "";
    @SerializedName("DiscountPercent")
    @Expose
    float DiscountPercent;

    public String SiteNumber;

    @SerializedName("AddressExtension")
    @Expose
    private AddressExtensions addressExtension;

   /* @SerializedName("DocumentLines")
    @Expose
    private ArrayList<DocumentLines> DocumentLines;*/

    private String DocumentLines;
    private String CreatedBy;


    public String getDocumentLines() {
        return DocumentLines;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getSiteNumber() {
        return SiteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        SiteNumber = siteNumber;
    }

    public String getMatirialType() {
        return MatirialType;
    }

    public void setMatirialType(String matirialType) {
        MatirialType = matirialType;
    }

    public void setDocumentLines(String documentLines) {
        DocumentLines = documentLines;
    }

    public String getGlassDate() {
        return GlassDate;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public void setGlassDate(String glassDate) {
        GlassDate = glassDate;
    }

    public String getCoatingDate() {
        return CoatingDate;
    }

    public void setCoatingDate(String coatingDate) {
        CoatingDate = coatingDate;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }



    public String getQuoteNo() {
        return QuoteNo;
    }

    public void setQuoteNo(String quoteNo) {
        QuoteNo = quoteNo;
    }

/*
    public void setDocumentLines(ArrayList<com.cinntra.okana.model.DocumentLines> documentLines) {
        DocumentLines = documentLines;
    }

    public ArrayList<com.cinntra.okana.model.DocumentLines> getDocumentLines() {
        return DocumentLines;
    }
*/

    public AddQuotation() {
    }


    public float getNetTotal() {
        return NetTotal;
    }

    public void setNetTotal(float netTotal) {
        NetTotal = netTotal;
    }

    protected AddQuotation(Parcel in) {
        this.CardCode = ((String) in.readValue((String.class.getClassLoader())));
        this.DocumentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.PostingDate = ((String) in.readValue((String.class.getClassLoader())));
        this.ValidDate = ((String) in.readValue((String.class.getClassLoader())));
        this.salesPerson = ((String) in.readValue((String.class.getClassLoader())));
        this.Remarks = ((String) in.readValue((String.class.getClassLoader())));
        this.BPL_IDAssignedToInvoice = ((String) in.readValue((String.class.getClassLoader())));
        this.BPLName = ((String) in.readValue((String.class.getClassLoader())));
        this.DiscountPercent = ((Float) in.readValue((String.class.getClassLoader())));
        this.addressExtension = ((AddressExtensions) in.readValue((String.class.getClassLoader())));
        this.DocumentLines = ((String) in.readValue((String.class.getClassLoader())));
        this.U_OPPID = ((String) in.readValue((String.class.getClassLoader())));
        this.CreateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.CreateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.UpdateDate = ((String) in.readValue((String.class.getClassLoader())));
        this.UpdateTime = ((String) in.readValue((String.class.getClassLoader())));
        this.CardName = ((String) in.readValue((String.class.getClassLoader())));
        this.U_QUOTNM = ((String) in.readValue((String.class.getClassLoader())));

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(CardCode);
        dest.writeValue(DocumentDate);
        dest.writeValue(BPL_IDAssignedToInvoice);
        dest.writeValue(BPLName);
        dest.writeValue(PostingDate);
        dest.writeValue(ValidDate);
        dest.writeValue(salesPerson);
        dest.writeValue(Remarks);
        dest.writeValue(DiscountPercent);
        dest.writeValue(addressExtension);
        dest.writeValue(DocumentLines);
        dest.writeValue(U_OPPID);
        dest.writeValue(CreateDate);
        dest.writeValue(CreateTime);
        dest.writeValue(UpdateTime);
        dest.writeValue(UpdateDate);
        dest.writeValue(CardName);
        dest.writeValue(U_QUOTNM);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddQuotation> CREATOR = new Creator<AddQuotation>() {
        @Override
        public AddQuotation createFromParcel(Parcel in) {
            return new AddQuotation(in);
        }

        @Override
        public AddQuotation[] newArray(int size) {
            return new AddQuotation[size];
        }
    };


    public String getU_QUOTID() {
        return U_QUOTID;
    }

    public void setU_QUOTID(String u_QUOTID) {
        U_QUOTID = u_QUOTID;
    }

    public String getOpportunityName() {
        return OpportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        OpportunityName = opportunityName;
    }

    public String getPostingDate() {
        return PostingDate;
    }

    public void setPostingDate(String postingDate) {
        PostingDate = postingDate;
    }

    public String getValidDate() {
        return ValidDate;
    }

    public void setValidDate(String validDate) {
        ValidDate = validDate;
    }

    public String getDocumentDate() {
        return DocumentDate;
    }

    public void setDocumentDate(String documentDate) {
        DocumentDate = documentDate;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        this.salesPerson = salesPerson;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public AddressExtensions getAddressExtension() {
        return addressExtension;
    }

    public void setAddressExtension(AddressExtensions addressExtension) {
        this.addressExtension = addressExtension;
    }

    public String getBPL_IDAssignedToInvoice() {
        return BPL_IDAssignedToInvoice;
    }

    public void setBPL_IDAssignedToInvoice(String BPL_IDAssignedToInvoice) {
        this.BPL_IDAssignedToInvoice = BPL_IDAssignedToInvoice;
    }

    public String getBPLName() {
        return BPLName;
    }

    public void setBPLName(String BPLName) {
        this.BPLName = BPLName;
    }

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public float getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        DiscountPercent = discountPercent;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getU_OPPID() {
        return U_OPPID;
    }

    public void setU_OPPID(String u_OPPID) {
        U_OPPID = u_OPPID;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getU_QUOTNM() {
        return U_QUOTNM;
    }

    public void setU_QUOTNM(String u_QUOTNM) {
        U_QUOTNM = u_QUOTNM;
    }
}
