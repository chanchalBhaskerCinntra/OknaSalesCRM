package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class GetBPAdresses implements Serializable {
    @SerializedName("AddressName")
    @Expose
    String AddressName;
    @SerializedName("Street")
    @Expose
    String Street;
    @SerializedName("Block")
    @Expose
    String Block;
    @SerializedName("ZipCode")
    @Expose
    String ZipCode;
    @SerializedName("City")
    @Expose
    String City;
    @SerializedName("Country")
    @Expose
    String Country;
    @SerializedName("State")
    @Expose
    String State;
    @SerializedName("AddressType")
    @Expose
    String AddressType;
    @SerializedName("BPCode")
    @Expose
    String BPCode;
    @SerializedName("RowNum")
    @Expose
    String RowNum;
    @SerializedName("CreateDate")
    @Expose
    String CreateDate;
    @SerializedName("U_COUNTRY")
    @Expose
    String U_COUNTRY;
    @SerializedName("U_STATE")
    @Expose
    String U_STATE;
    @SerializedName("U_SHPTYP")
    @Expose
    String U_SHPTYP;

    public GetBPAdresses(){};

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public String getBPCode() {
        return BPCode;
    }

    public void setBPCode(String BPCode) {
        this.BPCode = BPCode;
    }

    public String getRowNum() {
        return RowNum;
    }

    public void setRowNum(String rowNum) {
        RowNum = rowNum;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getU_COUNTRY() {
        return U_COUNTRY;
    }

    public void setU_COUNTRY(String u_COUNTRY) {
        U_COUNTRY = u_COUNTRY;
    }

    public String getU_STATE() {
        return U_STATE;
    }

    public void setU_STATE(String u_STATE) {
        U_STATE = u_STATE;
    }

    public String getU_SHPTYP() {
        return U_SHPTYP;
    }

    public void setU_SHPTYP(String u_SHPTYP) {
        U_SHPTYP = u_SHPTYP;
    }
}
