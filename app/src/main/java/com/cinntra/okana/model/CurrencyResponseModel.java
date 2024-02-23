package com.cinntra.okana.model;

import java.util.ArrayList;

public class CurrencyResponseModel {
    public String message;
    public int status;
    public ArrayList<Data> data;

    public class Data{
        public int id;
        public String CountryName;
        public String CurrencyCode;
        public String DropDownDescription;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCountryName() {
            return CountryName;
        }

        public void setCountryName(String countryName) {
            CountryName = countryName;
        }

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }

        public String getDropDownDescription() {
            return DropDownDescription;
        }

        public void setDropDownDescription(String dropDownDescription) {
            DropDownDescription = dropDownDescription;
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
