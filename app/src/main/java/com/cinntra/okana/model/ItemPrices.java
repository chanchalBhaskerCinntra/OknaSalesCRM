package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemPrices implements Serializable {

    @SerializedName("PriceList")
    String PriceList;
    @SerializedName("Price")
    String Price;
    @SerializedName("Currency")
    String Currency;
    @SerializedName("BasePriceList")
    String BasePriceList;
    @SerializedName("Factor")
    String Factor;

    public String getPriceList() {
        return PriceList;
    }

    public void setPriceList(String priceList) {
        PriceList = priceList;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getBasePriceList() {
        return BasePriceList;
    }

    public void setBasePriceList(String basePriceList) {
        BasePriceList = basePriceList;
    }

    public String getFactor() {
        return Factor;
    }

    public void setFactor(String factor) {
        Factor = factor;
    }
}
