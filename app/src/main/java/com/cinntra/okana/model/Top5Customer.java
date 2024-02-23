package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Top5Customer implements Serializable {

    @SerializedName("CardCode")
    String CardCode;
    @SerializedName("FinalTotal")
    String FinalTotal;

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getFinalTotal() {
        return FinalTotal;
    }

    public void setFinalTotal(String finalTotal) {
        FinalTotal = finalTotal;
    }
}
