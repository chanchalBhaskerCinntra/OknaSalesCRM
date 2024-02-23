package com.cinntra.okana.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "table_payment_term")
public class PayMentTerm implements Serializable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("GroupNumber")
    @Expose
    private String groupNumber;
    @SerializedName("PaymentTermsGroupName")
    @Expose
    private String paymentTermsGroupName;
    private final static long serialVersionUID = 821834809536410029L;

    /**
     * No args constructor for use in serialization
     */
    public PayMentTerm() {
    }

    /**
     * @param paymentTermsGroupName
     * @param id
     * @param groupNumber
     */
    public PayMentTerm(Integer id, String groupNumber, String paymentTermsGroupName) {
        super();
        this.id = id;
        this.groupNumber = groupNumber;
        this.paymentTermsGroupName = paymentTermsGroupName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getPaymentTermsGroupName() {
        return paymentTermsGroupName;
    }

    public void setPaymentTermsGroupName(String paymentTermsGroupName) {
        this.paymentTermsGroupName = paymentTermsGroupName;
    }

}