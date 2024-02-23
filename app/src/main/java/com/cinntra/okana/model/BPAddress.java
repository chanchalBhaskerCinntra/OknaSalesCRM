package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BPAddress implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("BPID")
    @Expose
    private String bpid;
    @SerializedName("BPCode")
    @Expose
    private String bPCode;
    @SerializedName("AddressName")
    @Expose
    private String addressName;
    @SerializedName("Street")
    @Expose
    private String street;
    @SerializedName("Block")
    @Expose
    private String block;
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
    @SerializedName("RowNum")
    @Expose
    private String rowNum;
    @SerializedName("U_SHPTYP")
    @Expose
    private String uShptyp;

    @SerializedName("U_COUNTRY")
    @Expose
    private String uCountry;
    @SerializedName("U_STATE")
    @Expose
    private String uState;
    @SerializedName("Default")
    @Expose
    private Integer Default;
    private final static long serialVersionUID = -8291604007794346546L;

    /**
     * No args constructor for use in serialization
     *
     */
    public BPAddress() {
    }

    /**
     *
     * @param zipCode
     * @param country
     * @param city
     * @param addressType
     * @param bpid
     * @param uShptyp
     * @param uState
     * @param street
     * @param rowNum
     * @param uCountry
     * @param bPCode
     * @param addressName
     * @param block
     * @param id
     * @param state
     */
    public BPAddress(Integer id, String bpid, String bPCode, String addressName, String street, String block, String city, String state, String zipCode, String country, String addressType, String rowNum, String uShptyp, String uCountry, String uState,Integer Default) {
        super();
        this.id = id;
        this.bpid = bpid;
        this.bPCode = bPCode;
        this.addressName = addressName;
        this.street = street;
        this.block = block;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.addressType = addressType;
        this.rowNum = rowNum;
        this.uShptyp = uShptyp;
        this.uCountry = uCountry;
        this.uState = uState;
        this.Default = Default;
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

    public String getBPCode() {
        return bPCode;
    }

    public void setBPCode(String bPCode) {
        this.bPCode = bPCode;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getUShptyp() {
        return uShptyp;
    }

    public void setUShptyp(String uShptyp) {
        this.uShptyp = uShptyp;
    }

    public String getUCountry() {
        return uCountry;
    }

    public void setUCountry(String uCountry) {
        this.uCountry = uCountry;
    }

    public String getUState() {
        return uState;
    }

    public void setUState(String uState) {
        this.uState = uState;
    }

    public Integer getDefault() {
        return Default;
    }

    public void setDefault(Integer aDefault) {
        Default = aDefault;
    }
}
