package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostBPAddresses implements Parcelable {
    @SerializedName("BPCode")
    @Expose
    String BPCode;
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
    @SerializedName("BuildingFloorRoom")
    @Expose
    String BuildingFloorRoom;
    @SerializedName("AddressType")
    @Expose
    String AddressType;
    @SerializedName("RowNum")
    @Expose
    String RowNum;
    @SerializedName("U_COUNTRY")
    @Expose
    String U_COUNTRY;
    @SerializedName("U_STATE")
    @Expose
    String U_STATE;
    @SerializedName("U_SHPTYP")
    @Expose
    String U_SHPTYP;



    public PostBPAddresses(){};

    protected PostBPAddresses(Parcel in) {
        BPCode = in.readString();
        AddressName = in.readString();
        Street = in.readString();
        Block = in.readString();
        ZipCode = in.readString();
        City = in.readString();
        Country = in.readString();
        State = in.readString();
        BuildingFloorRoom = in.readString();
        AddressType = in.readString();
        RowNum = in.readString();
        U_COUNTRY = in.readString();
        U_STATE = in.readString();
        U_SHPTYP = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(BPCode);
        dest.writeString(AddressName);
        dest.writeString(Street);
        dest.writeString(Block);
        dest.writeString(ZipCode);
        dest.writeString(City);
        dest.writeString(Country);
        dest.writeString(State);
        dest.writeString(BuildingFloorRoom);
        dest.writeString(AddressType);
        dest.writeString(RowNum);
        dest.writeString(U_COUNTRY);
        dest.writeString(U_STATE);
        dest.writeString(U_SHPTYP);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostBPAddresses> CREATOR = new Creator<PostBPAddresses>() {
        @Override
        public PostBPAddresses createFromParcel(Parcel in) {
            return new PostBPAddresses(in);
        }

        @Override
        public PostBPAddresses[] newArray(int size) {
            return new PostBPAddresses[size];
        }
    };

    public String getBPCode() {
        return BPCode;
    }

    public void setBPCode(String BPCode) {
        this.BPCode = BPCode;
    }

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

    public String getBuildingFloorRoom() {
        return BuildingFloorRoom;
    }

    public void setBuildingFloorRoom(String buildingFloorRoom) {
        BuildingFloorRoom = buildingFloorRoom;
    }

    public String getAddressType() {
        return AddressType;
    }

    public void setAddressType(String addressType) {
        AddressType = addressType;
    }

    public String getRowNum() {
        return RowNum;
    }

    public void setRowNum(String rowNum) {
        RowNum = rowNum;
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

    public String getU_SHPTYP() {
        return U_SHPTYP;
    }

    public void setU_SHPTYP(String u_SHPTYP) {
        U_SHPTYP = u_SHPTYP;
    }
}
