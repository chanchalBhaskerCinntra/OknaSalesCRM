package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocumentLines implements Parcelable, Serializable {
    @SerializedName("ItemCode")
    @Expose
    private String ItemCode;
    @SerializedName("id")
    @Expose
    private String id = "" ;
    @SerializedName("Quantity")
    @Expose
    private String Quantity;
    @SerializedName("TaxCode")
    @Expose
    private String TaxCode;
    @SerializedName("UnitPrice")
    @Expose
    private float UnitPrice;
    @SerializedName("DiscountPercent")
    @Expose
    float DiscountPercent;
    @SerializedName("WarehouseCode")
    @Expose
    String WarehouseCode;
    @SerializedName("ItemDescription")
    @Expose
    String ItemDescription;
    @SerializedName("ItemName")
    @Expose
    String ItemName;
    @SerializedName("Tax")
    @Expose
    String Tax;
    @SerializedName("UomNo")
    @Expose
    String UomNo;
    @SerializedName("U_FGITEM")
    @Expose
    String U_FGITEM = "";
    @SerializedName("CostingCode2")
    @Expose
    String CostingCode2= "DEPT001";
    @SerializedName("ProjectCode")
    @Expose
    String ProjectCode= "";

    @SerializedName("FreeText")
    @Expose
    String FreeText= "";
    String Oid= "";
    String QuotationID = "";

    public String getQuotationID() {
        return QuotationID;
    }

    public void setQuotationID(String quotationID) {
        QuotationID = quotationID;
    }

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
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

    public float getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getWarehouseCode() {
        return WarehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        WarehouseCode = warehouseCode;
    }

    public DocumentLines()
      {}


    protected DocumentLines(Parcel in) {
        this.ItemCode         = ((String) in.readValue((String.class.getClassLoader())));
        this.Quantity         = ((String) in.readValue((String.class.getClassLoader())));
        this.TaxCode          = ((String) in.readValue((String.class.getClassLoader())));
        this.UnitPrice        = ((float) in.readValue((float.class.getClassLoader())));
        this.WarehouseCode    = ((String) in.readValue((String.class.getClassLoader())));
        this.ItemDescription    = ((String) in.readValue((String.class.getClassLoader())));
        this.ItemName    = ((String) in.readValue((String.class.getClassLoader())));
        this.Tax    = ((String) in.readValue((String.class.getClassLoader())));
        this.UomNo    = ((String) in.readValue((String.class.getClassLoader())));
        this.U_FGITEM    = ((String) in.readValue((String.class.getClassLoader())));
        this.CostingCode2    = ((String) in.readValue((String.class.getClassLoader())));
        this.ProjectCode    = ((String) in.readValue((String.class.getClassLoader())));
        this.FreeText    = ((String) in.readValue((String.class.getClassLoader())));
        this.DiscountPercent  = ((float) in.readValue((String.class.getClassLoader())));

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ItemCode);
        dest.writeValue(Quantity);
        dest.writeValue(TaxCode);
        dest.writeValue(UnitPrice);
        dest.writeValue(WarehouseCode);
        dest.writeValue(DiscountPercent);
        dest.writeValue(ItemDescription);
        dest.writeValue(ItemName);
        dest.writeValue(Tax);
        dest.writeValue(UomNo);
        dest.writeValue(U_FGITEM);
        dest.writeValue(CostingCode2);
        dest.writeValue(ProjectCode);
        dest.writeValue(FreeText);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DocumentLines> CREATOR = new Creator<DocumentLines>() {
        @Override
        public DocumentLines createFromParcel(Parcel in) {
            return new DocumentLines(in);
        }

        @Override
        public DocumentLines[] newArray(int size) {
            return new DocumentLines[size];
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

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getUomNo() {
        return UomNo;
    }

    public void setUomNo(String uomNo) {
        UomNo = uomNo;
    }

    public String getU_FGITEM() {
        return U_FGITEM;
    }

    public void setU_FGITEM(String u_FGITEM) {
        U_FGITEM = u_FGITEM;
    }

    public String getCostingCode2() {
        return CostingCode2;
    }

    public void setCostingCode2(String costingCode2) {
        CostingCode2 = costingCode2;
    }

    public String getProjectCode() {
        return ProjectCode;
    }

    public void setProjectCode(String projectCode) {
        ProjectCode = projectCode;
    }

    public String getFreeText() {
        return FreeText;
    }

    public void setFreeText(String freeText) {
        FreeText = freeText;
    }
}
