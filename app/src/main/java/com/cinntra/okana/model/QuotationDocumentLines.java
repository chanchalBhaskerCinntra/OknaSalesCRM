package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class QuotationDocumentLines implements Serializable {

    @SerializedName("LineNum")
    @Expose
    String LineNum ;
    @SerializedName("id")
    @Expose
    String id ;

    @SerializedName("ItemCode")
    String ItemCode;
     @SerializedName("ItemDescription")
    String ItemDescription;
    @SerializedName("Quantity")
    String Quantity= "0";
    @SerializedName("Price")
    String Price= "0";
    @SerializedName("PriceAfterVAT")
    String PriceAfterVAT;
    @SerializedName("Currency")
    String Currency;
    @SerializedName("Rate")
    String Rate;
    @SerializedName("DiscountPercent")
    String DiscountPercent;
    @SerializedName("WarehouseCode")
    String WarehouseCode;
    @SerializedName("SalesPersonCode")
    String SalesPersonCode;
    @SerializedName("CommisionPercent")
    String CommisionPercent;
    @SerializedName("AccountCode")
    String AccountCode;
    @SerializedName("Volume")
    String Volume;
    @SerializedName("AppliedTax")
    String AppliedTax;
    @SerializedName("AppliedTaxSC")
    String AppliedTaxSC;
    @SerializedName("AppliedTaxFC")
    String AppliedTaxFC;
    @SerializedName("NetTaxAmount")
    String NetTaxAmount;
    @SerializedName("NetTaxAmountFC")
    String NetTaxAmountFC;
    @SerializedName("NetTaxAmountSC")
    String NetTaxAmountSC;
    @SerializedName("UnitsOfMeasurment")
    String UnitsOfMeasurment;
    @SerializedName("LineTotal")
    String LineTotal;
    @SerializedName("TaxPercentagePerRow")
    String TaxPercentagePerRow;
    @SerializedName("TaxTotal")
    String TaxTotal;
    @SerializedName("ExciseAmount")
    String ExciseAmount;

    @SerializedName("TaxPerUnit")
    String TaxPerUnit;
    @SerializedName("TotalInclTax")
    String TotalInclTax;
    @SerializedName("RowTotalFC")
    String RowTotalFC;
    @SerializedName("RowTotalSC")
    String RowTotalSC;
    @SerializedName("LastBuyInmPrice")
    String LastBuyInmPrice;
    @SerializedName("LastBuyDistributeSumFc")
    String LastBuyDistributeSumFc;
    @SerializedName("UnitPrice")
    String UnitPrice;
    @SerializedName("ShipDate")
    String ShipDate;
    @SerializedName("GrossPrice")
    String GrossPrice;
    @SerializedName("ProjectCode")
    String ProjectCode;
    @SerializedName("DistributeExpense")
    String DistributeExpense;
    @SerializedName("TaxCode")
    String TaxCode;
    @SerializedName("InventoryQuantity")
    String InventoryQuantity;


    public String getInventoryQuantity() {
        return InventoryQuantity;
    }

    public void setInventoryQuantity(String inventoryQuantity) {
        InventoryQuantity = inventoryQuantity;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String taxCode) {
        TaxCode = taxCode;
    }

    public String getDistributeExpense() {
        return DistributeExpense;
    }

    public void setDistributeExpense(String distributeExpense) {
        DistributeExpense = distributeExpense;
    }

    public String getProjectCode() {
        return ProjectCode;
    }

    public void setProjectCode(String projectCode) {
        ProjectCode = projectCode;
    }

    public String getGrossPrice() {
        return GrossPrice;
    }

    public void setGrossPrice(String grossPrice) {
        GrossPrice = grossPrice;
    }

    public String getShipDate() {
        return ShipDate;
    }

    public void setShipDate(String shipDate) {
        ShipDate = shipDate;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPriceAfterVAT() {
        return PriceAfterVAT;
    }

    public void setPriceAfterVAT(String priceAfterVAT) {
        PriceAfterVAT = priceAfterVAT;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
    }

    public String getWarehouseCode() {
        return WarehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        WarehouseCode = warehouseCode;
    }

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public String getCommisionPercent() {
        return CommisionPercent;
    }

    public void setCommisionPercent(String commisionPercent) {
        CommisionPercent = commisionPercent;
    }

    public String getAccountCode() {
        return AccountCode;
    }

    public void setAccountCode(String accountCode) {
        AccountCode = accountCode;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getAppliedTax() {
        return AppliedTax;
    }

    public void setAppliedTax(String appliedTax) {
        AppliedTax = appliedTax;
    }

    public String getAppliedTaxSC() {
        return AppliedTaxSC;
    }

    public void setAppliedTaxSC(String appliedTaxSC) {
        AppliedTaxSC = appliedTaxSC;
    }

    public String getAppliedTaxFC() {
        return AppliedTaxFC;
    }

    public void setAppliedTaxFC(String appliedTaxFC) {
        AppliedTaxFC = appliedTaxFC;
    }

    public String getNetTaxAmount() {
        return NetTaxAmount;
    }

    public void setNetTaxAmount(String netTaxAmount) {
        NetTaxAmount = netTaxAmount;
    }

    public String getNetTaxAmountFC() {
        return NetTaxAmountFC;
    }

    public void setNetTaxAmountFC(String netTaxAmountFC) {
        NetTaxAmountFC = netTaxAmountFC;
    }

    public String getNetTaxAmountSC() {
        return NetTaxAmountSC;
    }

    public void setNetTaxAmountSC(String netTaxAmountSC) {
        NetTaxAmountSC = netTaxAmountSC;
    }

    public String getUnitsOfMeasurment() {
        return UnitsOfMeasurment;
    }

    public void setUnitsOfMeasurment(String unitsOfMeasurment) {
        UnitsOfMeasurment = unitsOfMeasurment;
    }

    public String getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(String lineTotal) {
        LineTotal = lineTotal;
    }

    public String getTaxPercentagePerRow() {
        return TaxPercentagePerRow;
    }

    public void setTaxPercentagePerRow(String taxPercentagePerRow) {
        TaxPercentagePerRow = taxPercentagePerRow;
    }

    public String getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        TaxTotal = taxTotal;
    }

    public String getExciseAmount() {
        return ExciseAmount;
    }

    public void setExciseAmount(String exciseAmount) {
        ExciseAmount = exciseAmount;
    }

    public String getTaxPerUnit() {
        return TaxPerUnit;
    }

    public void setTaxPerUnit(String taxPerUnit) {
        TaxPerUnit = taxPerUnit;
    }

    public String getTotalInclTax() {
        return TotalInclTax;
    }

    public void setTotalInclTax(String totalInclTax) {
        TotalInclTax = totalInclTax;
    }

    public String getRowTotalFC() {
        return RowTotalFC;
    }

    public void setRowTotalFC(String rowTotalFC) {
        RowTotalFC = rowTotalFC;
    }

    public String getRowTotalSC() {
        return RowTotalSC;
    }

    public void setRowTotalSC(String rowTotalSC) {
        RowTotalSC = rowTotalSC;
    }

    public String getLastBuyInmPrice() {
        return LastBuyInmPrice;
    }

    public void setLastBuyInmPrice(String lastBuyInmPrice) {
        LastBuyInmPrice = lastBuyInmPrice;
    }

    public String getLastBuyDistributeSumFc() {
        return LastBuyDistributeSumFc;
    }

    public void setLastBuyDistributeSumFc(String lastBuyDistributeSumFc) {
        LastBuyDistributeSumFc = lastBuyDistributeSumFc;
    }

    public String getLineNum() {
        return LineNum;
    }

    public void setLineNum(String lineNum) {
        LineNum = lineNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
