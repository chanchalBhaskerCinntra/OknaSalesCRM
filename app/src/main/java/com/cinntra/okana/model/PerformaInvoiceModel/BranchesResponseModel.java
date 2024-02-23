package com.cinntra.okana.model.PerformaInvoiceModel;

import java.util.ArrayList;

public class BranchesResponseModel {
    public String message;
    public int status;
    public ArrayList<Data> data;

    public class Data{
        public int id;
        
        public String BPLId;
        
        public String BPLName;
        
        public String Address;
        
        public String MainBPL;
        
        public String Disabled;
        
        public String UserSign2;
        
        public String UpdateDate;
        
        public String DflWhs;
        
        public String TaxIdNum;
        
        public String StreetNo;
        
        public String Building;
        
        public String ZipCode;
        
        public String City;
        
        public String State;
        
        public String Country;
        
        public String Series;
        
        public String FederalTaxID;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBPLId() {
            return BPLId;
        }

        public void setBPLId(String BPLId) {
            this.BPLId = BPLId;
        }

        public String getBPLName() {
            return BPLName;
        }

        public void setBPLName(String BPLName) {
            this.BPLName = BPLName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getMainBPL() {
            return MainBPL;
        }

        public void setMainBPL(String mainBPL) {
            MainBPL = mainBPL;
        }

        public String getDisabled() {
            return Disabled;
        }

        public void setDisabled(String disabled) {
            Disabled = disabled;
        }

        public String getUserSign2() {
            return UserSign2;
        }

        public void setUserSign2(String userSign2) {
            UserSign2 = userSign2;
        }

        public String getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(String updateDate) {
            UpdateDate = updateDate;
        }

        public String getDflWhs() {
            return DflWhs;
        }

        public void setDflWhs(String dflWhs) {
            DflWhs = dflWhs;
        }

        public String getTaxIdNum() {
            return TaxIdNum;
        }

        public void setTaxIdNum(String taxIdNum) {
            TaxIdNum = taxIdNum;
        }

        public String getStreetNo() {
            return StreetNo;
        }

        public void setStreetNo(String streetNo) {
            StreetNo = streetNo;
        }

        public String getBuilding() {
            return Building;
        }

        public void setBuilding(String building) {
            Building = building;
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

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getSeries() {
            return Series;
        }

        public void setSeries(String series) {
            Series = series;
        }

        public String getFederalTaxID() {
            return FederalTaxID;
        }

        public void setFederalTaxID(String federalTaxID) {
            FederalTaxID = federalTaxID;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
