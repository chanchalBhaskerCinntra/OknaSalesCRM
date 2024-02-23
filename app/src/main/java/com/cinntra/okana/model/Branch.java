package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Branch implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("BPID")
    @Expose
    private String bpid;
    @SerializedName("RowNum")
    @Expose
    private String rowNum;
    @SerializedName("BPCode")
    @Expose
    private String bPCode;
    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @SerializedName("AddressName")
    @Expose
    private String addressName;
    @SerializedName("AddressName2")
    @Expose
    private String addressName2;
    @SerializedName("AddressName3")
    @Expose
    private String addressName3;
    @SerializedName("BuildingFloorRoom")
    @Expose
    private String buildingFloorRoom;
    @SerializedName("Street")
    @Expose
    private String street;
    @SerializedName("Block")
    @Expose
    private String block;
    @SerializedName("County")
    @Expose
    private String county;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("AddressType")
    @Expose
    private String addressType;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Fax")
    @Expose
    private String fax;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("TaxOffice")
    @Expose
    private String taxOffice;
    @SerializedName("GSTIN")
    @Expose
    private String gstin;
    @SerializedName("GstType")
    @Expose
    private String gstType;
    @SerializedName("ShippingType")
    @Expose
    private String shippingType;
    @SerializedName("PaymentTerm")
    @Expose
    private String paymentTerm;
    @SerializedName("CurrentBalance")
    @Expose
    private String currentBalance;
    @SerializedName("CreditLimit")
    @Expose
    private String creditLimit;
    @SerializedName("Lat")
    @Expose
    private String lat;
    @SerializedName("Long")
    @Expose
    private String _long;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("CreateTime")
    @Expose
    private String createTime;
    @SerializedName("UpdateDate")
    @Expose
    private String updateDate;
    @SerializedName("UpdateTime")
    @Expose
    private String updateTime;
    @SerializedName("companyId")
    @Expose
    private String companyId;
    @SerializedName("U_STATE")
    @Expose
    private String U_STATE;
    @SerializedName("U_COUNTRY")
    @Expose
    private String U_COUNTRY;
    private final static long serialVersionUID = 7657937503252979289L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Branch() {
    }

    /**
     *
     * @param zipCode
     * @param country
     * @param updateDate
     * @param city
     * @param bpid
     * @param county
     * @param shippingType
     * @param gstType
     * @param gstin
     * @param buildingFloorRoom
     * @param street
     * @param rowNum
     * @param creditLimit
     * @param block
     * @param id
     * @param state
     * @param taxOffice
     * @param fax
     * @param email
     * @param lat
     * @param createDate
     * @param addressType
     * @param currentBalance
     * @param branchName
     * @param updateTime
     * @param phone
     * @param createTime
     * @param _long
     * @param bPCode
     * @param addressName
     * @param addressName2
     * @param addressName3
     * @param paymentTerm
     * @param status
     */
    public Branch(Integer id, String bpid, String rowNum, String bPCode, String branchName, String addressName, String addressName2, String addressName3, String buildingFloorRoom, String street, String block, String county, String city, String state, String zipCode, String country, String addressType, String phone, String fax, String email, String taxOffice, String gstin, String gstType, String shippingType, String paymentTerm, String currentBalance, String creditLimit, String lat, String _long, Integer status, String createDate, String createTime, String updateDate, String updateTime) {
        super();
        this.id = id;
        this.bpid = bpid;
        this.rowNum = rowNum;
        this.bPCode = bPCode;
        this.branchName = branchName;
        this.addressName = addressName;
        this.addressName2 = addressName2;
        this.addressName3 = addressName3;
        this.buildingFloorRoom = buildingFloorRoom;
        this.street = street;
        this.block = block;
        this.county = county;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.addressType = addressType;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.taxOffice = taxOffice;
        this.gstin = gstin;
        this.gstType = gstType;
        this.shippingType = shippingType;
        this.paymentTerm = paymentTerm;
        this.currentBalance = currentBalance;
        this.creditLimit = creditLimit;
        this.lat = lat;
        this._long = _long;
        this.status = status;
        this.createDate = createDate;
        this.createTime = createTime;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBpid() {
        return bpid;
    }

    public void setBpid(String bpid) {
        this.bpid = bpid;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getBPCode() {
        return bPCode;
    }

    public void setBPCode(String bPCode) {
        this.bPCode = bPCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressName2() {
        return addressName2;
    }

    public void setAddressName2(String addressName2) {
        this.addressName2 = addressName2;
    }

    public String getAddressName3() {
        return addressName3;
    }

    public void setAddressName3(String addressName3) {
        this.addressName3 = addressName3;
    }

    public String getBuildingFloorRoom() {
        return buildingFloorRoom;
    }

    public void setBuildingFloorRoom(String buildingFloorRoom) {
        this.buildingFloorRoom = buildingFloorRoom;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getGstType() {
        return gstType;
    }

    public void setGstType(String gstType) {
        this.gstType = gstType;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getU_STATE() {
        return U_STATE;
    }

    public void setU_STATE(String u_STATE) {
        U_STATE = u_STATE;
    }

    public String getU_COUNTRY() {
        return U_COUNTRY;
    }

    public void setU_COUNTRY(String u_COUNTRY) {
        U_COUNTRY = u_COUNTRY;
    }
}