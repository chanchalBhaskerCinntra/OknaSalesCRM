package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FastMovingItems implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("CodeType")
    @Expose
    private String codeType;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemCode")
    @Expose
    private String itemCode;
    @SerializedName("Inventory")
    @Expose
    private Integer inventory;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UnitPrice")
    @Expose
    private Float unitPrice;
    @SerializedName("UoS")
    @Expose
    private String uoS;
    @SerializedName("Packing")
    @Expose
    private String packing;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("HSN")
    @Expose
    private String hsn;
    @SerializedName("TaxCode")
    @Expose
    private Float taxCode;
    @SerializedName("Discount")
    @Expose
    private Float discount;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedTime")
    @Expose
    private String createdTime;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedTime")
    @Expose
    private String updatedTime;
    @SerializedName("CatID")
    @Expose
    private CatID catID;
    private final static long serialVersionUID = -584575754132011377L;

    /**
     * No args constructor for use in serialization
     *
     */
    public FastMovingItems() {
    }

    /**
     *
     * @param unitPrice
     * @param updatedTime
     * @param hsn
     * @param codeType
     * @param itemCode
     * @param description
     * @param discount
     * @param updatedDate
     * @param inventory
     * @param packing
     * @param taxCode
     * @param catID
     * @param itemName
     * @param createdDate
     * @param uoS
     * @param createdTime
     * @param currency
     * @param id
     * @param status
     */
    public FastMovingItems(Integer id, String codeType, String itemName, String itemCode, Integer inventory, String description, Float unitPrice, String uoS, String packing, String currency, String hsn, Float taxCode, Float discount, Integer status, String createdDate, String createdTime, String updatedDate, String updatedTime, CatID catID) {
        super();
        this.id = id;
        this.codeType = codeType;
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.inventory = inventory;
        this.description = description;
        this.unitPrice = unitPrice;
        this.uoS = uoS;
        this.packing = packing;
        this.currency = currency;
        this.hsn = hsn;
        this.taxCode = taxCode;
        this.discount = discount;
        this.status = status;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.updatedDate = updatedDate;
        this.updatedTime = updatedTime;
        this.catID = catID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUoS() {
        return uoS;
    }

    public void setUoS(String uoS) {
        this.uoS = uoS;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public Float getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(Float taxCode) {
        this.taxCode = taxCode;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public CatID getCatID() {
        return catID;
    }

    public void setCatID(CatID catID) {
        this.catID = catID;
    }

}
