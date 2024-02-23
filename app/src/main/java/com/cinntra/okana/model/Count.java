package com.cinntra.okana.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Count implements Serializable {
    @SerializedName("Opportunity")
    String Opportunity;
    @SerializedName("Quotation")
    String Quotation;
    @SerializedName("Order")
    String Order;
    @SerializedName("Customer")
    String Customer;
    @SerializedName("Invoice")
    String Invoice;
    @SerializedName("amount")
    String amount;
    @SerializedName("sale")
    String sale;
    @SerializedName("sale_diff")
    String sale_diff;
    @SerializedName("notification")
    String notification;
    @SerializedName("Over")
    String Over;
    @SerializedName("Open")
    String Open;
    @SerializedName("Close")
    String Close;
    @SerializedName("Leads")
    String Leads;
    @SerializedName("campset_count")
    String campaign_count;
    @SerializedName("expense_all")
    String expense_all;
    @SerializedName("payment_all")
    String payment_all;

    public String getOpportunity() {
        return Opportunity;
    }

    public void setOpportunity(String opportunity) {
        Opportunity = opportunity;
    }

    public String getQuotation() {
        return Quotation;
    }

    public void setQuotation(String quotation) {
        Quotation = quotation;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getInvoice() {
        return Invoice;
    }

    public void setInvoice(String invoice) {
        Invoice = invoice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getSale_diff() {
        return sale_diff;
    }

    public void setSale_diff(String sale_diff) {
        this.sale_diff = sale_diff;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getOver() {
        return Over;
    }

    public void setOver(String over) {
        Over = over;
    }

    public String getOpen() {
        return Open;
    }

    public void setOpen(String open) {
        Open = open;
    }

    public String getClose() {
        return Close;
    }

    public void setClose(String close) {
        Close = close;
    }

    public String getLeads() {
        return Leads;
    }

    public void setLeads(String leads) {
        Leads = leads;
    }

    public String getCampaign_count() {
        return campaign_count;
    }

    public void setCampaign_count(String campaign_count) {
        this.campaign_count = campaign_count;
    }

    public String getExpense_all() {
        return expense_all;
    }

    public void setExpense_all(String expense_all) {
        this.expense_all = expense_all;
    }

    public String getPayment_all() {
        return payment_all;
    }

    public void setPayment_all(String payment_all) {
        this.payment_all = payment_all;
    }
}
