package com.cinntra.okana.model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Team implements Serializable {

    @SerializedName("CardCode")
    String EmpName;
    @SerializedName("CardName")
    String CardName;
    @SerializedName("Total")
    String id;
    String position;
    float qty;

    public Team(String EmpName, String id, String position,String CardName){
        this.EmpName        = EmpName;
        this.id             = id;
        this.position       = position;
        this.CardName       = CardName;

    }
    public Team(String EmpName, String id)
       {
    this.EmpName   = EmpName;
    this.id        = id;
       }
    public Team(String EmpName, float qty)
    {
        this.EmpName = EmpName;
        this.qty     = qty;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String empName) {
        EmpName = empName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }
}
