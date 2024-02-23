package com.cinntra.okana.model;
 import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class Items implements Serializable {

    @SerializedName("ItemCode")
    String ItemCode;
    @SerializedName("ItemName")
    String ItemName;
    String ItemUnitPrice;
    String ItemTaxCode;
    String Quantity;
    String id;
    @SerializedName("ItemPrices")
    ArrayList<ItemPrices> ItemPrices;




    public String getItemCode()
      {
    return ItemCode;
      }

    public void setItemCode(String itemCode)
      {
     ItemCode = itemCode;
      }

    public String getItemName()
     {
    return ItemName;
     }

    public void setItemName(String itemName)
      {
    ItemName = itemName;
      }

    public ArrayList<ItemPrices> getItemPrices()
     {
    return ItemPrices;
     }

    public void setItemPrices(ArrayList<ItemPrices> itemPrices)
     {
    ItemPrices = itemPrices;
     }


    public String getItemUnitPrice() {
        return ItemUnitPrice;
    }

    public void setItemUnitPrice(String itemUnitPrice) {
        ItemUnitPrice = itemUnitPrice;
    }

    public String getItemTaxCode() {
        return ItemTaxCode;
    }

    public void setItemTaxCode(String itemTaxCode) {
        ItemTaxCode = itemTaxCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
