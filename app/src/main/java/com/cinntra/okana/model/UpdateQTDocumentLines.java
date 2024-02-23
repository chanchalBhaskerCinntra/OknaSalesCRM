package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateQTDocumentLines implements Parcelable {
    @SerializedName("ItemCode")
    @Expose
    private String ItemCode;
    @SerializedName("Quantity")
    @Expose
    private String Quantity;
    @SerializedName("TaxCode")
    @Expose
    private String TaxCode;
    @SerializedName("UnitPrice")
    @Expose
    private String UnitPrice;
    @SerializedName("QuotationID")
    @Expose
    String QuotationID;
    @SerializedName("DiscountPercent")
    @Expose
    float DiscountPercent;
    @SerializedName("ItemDescription")
    @Expose
    String ItemDescription;

    @SerializedName("LineNum")
    @Expose
    String LineNum;
    @SerializedName("id")
    @Expose
    String id = "";

    public String getQuotationID() {
        return QuotationID;
    }

    public void setQuotationID(String quotationID) {
        QuotationID = quotationID;
    }

    public String getLineNum() {
        return LineNum;
    }

    public void setLineNum(String lineNum) {
        LineNum = lineNum;
    }

    public float getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        DiscountPercent = discountPercent;
    }

    public String getItemCode() {
    return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String taxCode) {
        TaxCode = taxCode;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public UpdateQTDocumentLines()
    {}


    protected UpdateQTDocumentLines(Parcel in) {
        this.ItemCode    = ((String) in.readValue((String.class.getClassLoader())));
        this.Quantity  = ((String) in.readValue((String.class.getClassLoader())));
        this.TaxCode  = ((String) in.readValue((String.class.getClassLoader())));
        this.UnitPrice  = ((String) in.readValue((String.class.getClassLoader())));
        this.LineNum  = ((String) in.readValue((String.class.getClassLoader())));
        this.QuotationID  = ((String) in.readValue((String.class.getClassLoader())));
        this.ItemDescription  = ((String) in.readValue((String.class.getClassLoader())));
        this.id  = ((String) in.readValue((String.class.getClassLoader())));
        this.DiscountPercent  = ((float) in.readValue((String.class.getClassLoader())));

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ItemCode);
        dest.writeValue(Quantity);
        dest.writeValue(TaxCode);
        dest.writeValue(UnitPrice);
        dest.writeValue(LineNum);
        dest.writeValue(QuotationID);
        dest.writeValue(ItemDescription);
        dest.writeValue(DiscountPercent);
        if(!id.isEmpty())
        dest.writeValue(id);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdateQTDocumentLines> CREATOR = new Creator<UpdateQTDocumentLines>() {
        @Override
        public UpdateQTDocumentLines createFromParcel(Parcel in) {
            return new UpdateQTDocumentLines(in);
        }

        @Override
        public UpdateQTDocumentLines[] newArray(int size) {
            return new UpdateQTDocumentLines[size];
        }
    };

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
