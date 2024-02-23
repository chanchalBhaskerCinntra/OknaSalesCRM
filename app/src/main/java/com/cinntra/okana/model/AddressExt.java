package com.cinntra.okana.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressExt implements Serializable {

    @SerializedName("ShipToStreet")
    @Expose
    String ShipToStreet;
    @SerializedName("ShipToStreetNo")
    @Expose
    String ShipToStreetNo;
    @SerializedName("ShipToBlock")
    @Expose
    String ShipToBlock;
    @SerializedName("ShipToBuilding")
    @Expose
    String ShipToBuilding;
    @SerializedName("ShipToCity")
    @Expose
    String ShipToCity;
    @SerializedName("ShipToZipCode")
    @Expose
    String ShipToZipCode;
    @SerializedName("ShipToState")
    @Expose
    String ShipToState;
    @SerializedName("ShipToCountry")
    @Expose
    String ShipToCountry;
    @SerializedName("BillToStreet")
    @Expose
    String BillToStreet;
    @SerializedName("BillToZipCode")
    @Expose
    String BillToZipCode;
    @SerializedName("BillToStreetNo")
    @Expose
    String BillToStreetNo;
    @SerializedName("BillToBlock")
    @Expose
    String BillToBlock;
    @SerializedName("BillToBuilding")
    @Expose
    String BillToBuilding;
    @SerializedName("BillToCity")
    @Expose
    String BillToCity;
    @SerializedName("BillToState")
    @Expose
    String BillToState;
    @SerializedName("BillToCountry")
    @Expose
    String BillToCountry;
    @SerializedName("U_BSTATE")
    @Expose
    String U_BSTATE;
    @SerializedName("U_BCOUNTRY")
    @Expose
    String U_BCOUNTRY;
    @SerializedName("U_SSTATE")
    @Expose
    String U_SSTATE;
    @SerializedName("U_SCOUNTRY")
    @Expose
    String U_SCOUNTRY;
    @SerializedName("U_SHPTYPB")
    @Expose
    String U_SHPTYPB;
    @SerializedName("U_SHPTYPS")
    @Expose
    String U_SHPTYPS;
    @SerializedName("id")
    @Expose
    String id;
    String QuotationID;


    public String getQuotationID() {
        return QuotationID;
    }

    public void setQuotationID(String quotationID) {
        QuotationID = quotationID;
    }

    public String getShipToStreet() {
        return ShipToStreet;
    }

    public void setShipToStreet(String shipToStreet) {
        ShipToStreet = shipToStreet;
    }

    public String getShipToStreetNo() {
        return ShipToStreetNo;
    }

    public void setShipToStreetNo(String shipToStreetNo) {
        ShipToStreetNo = shipToStreetNo;
    }

    public String getShipToBlock() {
        return ShipToBlock;
    }

    public void setShipToBlock(String shipToBlock) {
        ShipToBlock = shipToBlock;
    }

    public String getShipToBuilding() {
        return ShipToBuilding;
    }

    public void setShipToBuilding(String shipToBuilding) {
        ShipToBuilding = shipToBuilding;
    }

    public String getShipToCity() {
        return ShipToCity;
    }

    public void setShipToCity(String shipToCity) {
        ShipToCity = shipToCity;
    }

    public String getShipToZipCode() {
        return ShipToZipCode;
    }

    public void setShipToZipCode(String shipToZipCode) {
        ShipToZipCode = shipToZipCode;
    }

    public String getShipToState() {
        return ShipToState;
    }

    public void setShipToState(String shipToState) {
        ShipToState = shipToState;
    }

    public String getShipToCountry() {
        return ShipToCountry;
    }

    public void setShipToCountry(String shipToCountry) {
        ShipToCountry = shipToCountry;
    }

    public String getBillToStreet() {
        return BillToStreet;
    }

    public void setBillToStreet(String billToStreet) {
        BillToStreet = billToStreet;
    }

    public String getBillToZipCode() {
        return BillToZipCode;
    }

    public void setBillToZipCode(String billToZipCode) {
        BillToZipCode = billToZipCode;
    }

    public String getBillToStreetNo() {
        return BillToStreetNo;
    }

    public void setBillToStreetNo(String billToStreetNo) {
        BillToStreetNo = billToStreetNo;
    }

    public String getBillToBlock() {
        return BillToBlock;
    }

    public void setBillToBlock(String billToBlock) {
        BillToBlock = billToBlock;
    }

    public String getBillToBuilding() {
        return BillToBuilding;
    }

    public void setBillToBuilding(String billToBuilding) {
        BillToBuilding = billToBuilding;
    }

    public String getBillToCity() {
        return BillToCity;
    }

    public void setBillToCity(String billToCity) {
        BillToCity = billToCity;
    }

    public String getBillToState() {
        return BillToState;
    }

    public void setBillToState(String billToState) {
        BillToState = billToState;
    }

    public String getBillToCountry() {
        return BillToCountry;
    }

    public void setBillToCountry(String billToCountry) {
        BillToCountry = billToCountry;
    }

    public String getU_BSTATE() {
        return U_BSTATE;
    }

    public void setU_BSTATE(String u_BSTATE) {
        U_BSTATE = u_BSTATE;
    }

    public String getU_BCOUNTRY() {
        return U_BCOUNTRY;
    }

    public void setU_BCOUNTRY(String u_BCOUNTRY) {
        U_BCOUNTRY = u_BCOUNTRY;
    }

    public String getU_SSTATE() {
        return U_SSTATE;
    }

    public void setU_SSTATE(String u_SSTATE) {
        U_SSTATE = u_SSTATE;
    }

    public String getU_SCOUNTRY() {
        return U_SCOUNTRY;
    }

    public void setU_SCOUNTRY(String u_SCOUNTRY) {
        U_SCOUNTRY = u_SCOUNTRY;
    }

    public String getU_SHPTYPB() {
        return U_SHPTYPB;
    }

    public void setU_SHPTYPB(String u_SHPTYPB) {
        U_SHPTYPB = u_SHPTYPB;
    }

    public String getU_SHPTYPS() {
        return U_SHPTYPS;
    }

    public void setU_SHPTYPS(String u_SHPTYPS) {
        U_SHPTYPS = u_SHPTYPS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
