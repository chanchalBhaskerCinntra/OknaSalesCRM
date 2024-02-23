package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Top5Item implements Serializable {

    @SerializedName("ItemCode")
    String itemCode;
    @SerializedName("ItemName")
    String itemName;
    @SerializedName("Total")
    String Total;

    public Top5Item(String itemCode, String itemName, String total) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        Total = total;
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

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }
}
