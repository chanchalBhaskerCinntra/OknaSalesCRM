package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Inventory implements Serializable {
    @SerializedName("ItemCode")
    String itemCode;
    @SerializedName("ItemName")
    String itemName;
    @SerializedName("OnHand")
    String quantity;
    @SerializedName("Price")
    String price;

    public Inventory(String itemCode, String itemName, String quantity, String price) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
