package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class CustomerItem implements Serializable {
    @SerializedName("CardCode")
    String CardCode;
    @SerializedName("CardName")
    String CardName;
    @SerializedName("CardType")
    String CardType;
    @SerializedName("GroupCode")
    String GroupCode;
    @SerializedName("MailAddress")
    String MailAddress;
    @SerializedName("MailZipCode")
    String MailZipCode;
    @SerializedName("City")
    String City;
    @SerializedName("Country")
    String Country;
    @SerializedName("MailCity")
    String MailCity;
    @SerializedName("Series")
    String Series;
    @SerializedName("UpdateDate")
    String UpdateDate;
    @SerializedName("CreateDate")
    String CreateDate;
    @SerializedName("ShipToDefault")
    String ShipToDefault;
    @SerializedName("PeymentMethodCode")
    String PeymentMethodCode;
    @SerializedName("Currency")
    String Currency;
    @SerializedName("Remarks1")
    String Remarks;
    @SerializedName("ShippingType")
    String ShippingType;
    @SerializedName("PriceListNum")
    int PriceListNum;
    @SerializedName("ContactPerson")
    @Expose
    String ContactPerson;
    @SerializedName("Industry")
    @Expose
    String Industry;
    @SerializedName("BPAddresses")
    @Expose
    ArrayList<GetBPAdresses> BPAddresses;

    @SerializedName("ContactEmployees")
    @Expose
    ArrayList<ContactEmployeesModel> ContactEmployees;

    @SerializedName("SalesPersonCode")
    @Expose
    String SalesPersonCode;
    @SerializedName("U_CURBAL")
    @Expose
    String CurrentAccountBalance;
    @SerializedName("PayTermsGrpCode")
    @Expose
    String PayTermsGrpCode;
    @SerializedName("U_ANLRVN")
    @Expose
    String U_ANLRVN;
    @SerializedName("U_TYPE")
    @Expose
    String U_TYPE;
    @SerializedName("CreditLimit")
    @Expose
    String CreditLimit;
    @SerializedName("U_RATING")
    @Expose
    String U_RATING;
    @SerializedName("U_ACCNT")
    @Expose
    String DefaultAccount;
    @SerializedName("U_INVNO")
    @Expose
    String U_INVNO;
    @SerializedName("Website")
    @Expose
    String Website;
    @SerializedName("EmailAddress")
    @Expose
    String EmailAddress;
    @SerializedName("Phone1")
    @Expose
    String Phone1;
    @SerializedName("Notes")
    @Expose
    String Notes;



    public ArrayList<ContactEmployeesModel> getContactEmployees() {
        return ContactEmployees;
    }

    public void setContactEmployees(ArrayList<ContactEmployeesModel> contactEmployees) {
        ContactEmployees = contactEmployees;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
    }

    public String getMailZipCode() {
        return MailZipCode;
    }

    public void setMailZipCode(String mailZipCode) {
        MailZipCode = mailZipCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getMailCity() {
        return MailCity;
    }

    public void setMailCity(String mailCity) {
        MailCity = mailCity;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getShipToDefault() {
        return ShipToDefault;
    }

    public void setShipToDefault(String shipToDefault) {
        ShipToDefault = shipToDefault;
    }

    public String getPeymentMethodCode()
     {
        return PeymentMethodCode;
    }

    public void setPeymentMethodCode(String peymentMethodCode)
     {
        PeymentMethodCode = peymentMethodCode;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getShippingType() {
        return ShippingType;
    }

    public void setShippingType(String shippingType) {
        ShippingType = shippingType;
    }

    public int getPriceListNum() {
        return PriceListNum;
    }

    public void setPriceListNum(int priceListNum) {
        PriceListNum = priceListNum;
    }

    public ArrayList<GetBPAdresses> getBPAddresses() {
        return BPAddresses;
    }

    public void setBPAddresses(ArrayList<GetBPAdresses> BPAddresses) {
        this.BPAddresses = BPAddresses;
    }

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }


    public String getCurrentAccountBalance() {
        return CurrentAccountBalance;
    }

    public void setCurrentAccountBalance(String currentAccountBalance) {
        CurrentAccountBalance = currentAccountBalance;
    }

    public String getPayTermsGrpCode() {
        return PayTermsGrpCode;
    }

    public void setPayTermsGrpCode(String payTermsGrpCode) {
        PayTermsGrpCode = payTermsGrpCode;
    }

    public String getU_ANLRVN() {
        return U_ANLRVN;
    }

    public void setU_ANLRVN(String u_ANLRVN) {
        U_ANLRVN = u_ANLRVN;
    }

    public String getU_TYPE() {
        return U_TYPE;
    }

    public void setU_TYPE(String u_TYPE) {
        U_TYPE = u_TYPE;
    }

    public String getCreditLimit() {
        return CreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        CreditLimit = creditLimit;
    }

    public String getU_RATING() {
        return U_RATING;
    }

    public void setU_RATING(String u_RATING) {
        U_RATING = u_RATING;
    }

    public String getDefaultAccount() {
        return DefaultAccount;
    }

    public void setDefaultAccount(String defaultAccount) {
        DefaultAccount = defaultAccount;
    }

    public String getU_INVNO() {
        return U_INVNO;
    }

    public void setU_INVNO(String u_INVNO) {
        U_INVNO = u_INVNO;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
