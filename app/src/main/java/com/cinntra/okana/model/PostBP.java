package com.cinntra.okana.model;

  import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class PostBP implements Parcelable {

    @SerializedName("CardCode")
    @Expose
    String CardCode;
    @SerializedName("CardName")
    @Expose
    String CardName;
    @SerializedName("CardType")
    @Expose
    String CardType;
    @SerializedName("SalesPersonCode")
    @Expose
    String SalesPersonCode;

    @SerializedName("Phone1")
    @Expose
    String Phone1;
    @SerializedName("Phone2")
    @Expose
    String Phone2;
    @SerializedName("EmailAddress")
    @Expose
    String EmailAddress;
    @SerializedName("Industry")
    @Expose
    String Industry;


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
    @SerializedName("ContactPerson")
    @Expose
    String ContactPerson;
    @SerializedName("ShippingType")
    @Expose
    String ShippingType;
    @SerializedName("Website")
    @Expose
    String Website;
    @SerializedName("BPAddresses")
    @Expose
    ArrayList<PostBPAddresses> BPAddresses;
    @SerializedName("ContactEmployees")
    ArrayList<PostContactEmployee>  ContactEmployees;
    @SerializedName("Notes")
    @Expose
    String Notes;



    public PostBP()
     {
     }


    protected PostBP(Parcel in) {
        CardCode = in.readString();
        CardName = in.readString();
        CardType = in.readString();
        SalesPersonCode = in.readString();
        Phone1 = in.readString();
        Phone2 = in.readString();
        CurrentAccountBalance = in.readString();
        PayTermsGrpCode = in.readString();
        U_ANLRVN = in.readString();
        U_INVNO = in.readString();
        U_RATING = in.readString();
        U_TYPE = in.readString();
        DefaultAccount = in.readString();
        CreditLimit = in.readString();
        EmailAddress = in.readString();
        ContactPerson = in.readString();
        ShippingType = in.readString();
        Website = in.readString();
        Notes = in.readString();
        BPAddresses = in.createTypedArrayList(PostBPAddresses.CREATOR);
        ContactEmployees = in.createTypedArrayList(PostContactEmployee.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CardCode);
        dest.writeString(CardName);
        dest.writeString(CardType);
        dest.writeString(SalesPersonCode);
        dest.writeString(Phone1);
        dest.writeString(Phone2);
        dest.writeString(EmailAddress);
        dest.writeString(U_ANLRVN);
        dest.writeString(U_INVNO);
        dest.writeString(U_RATING);
        dest.writeString(U_TYPE);
        dest.writeString(CreditLimit);
        dest.writeString(DefaultAccount);
        dest.writeString(PayTermsGrpCode);
        dest.writeString(CurrentAccountBalance);
        dest.writeString(ContactPerson);
        dest.writeString(ShippingType);
        dest.writeString(Website);
        dest.writeString(Notes);
        dest.writeTypedList(BPAddresses);
        dest.writeTypedList(ContactEmployees);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostBP> CREATOR = new Creator<PostBP>() {
        @Override
        public PostBP createFromParcel(Parcel in) {
            return new PostBP(in);
        }

        @Override
        public PostBP[] newArray(int size) {
            return new PostBP[size];
        }
    };

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

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public ArrayList<PostBPAddresses> getBPAddresses() {
        return BPAddresses;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public void setBPAddresses(ArrayList<PostBPAddresses> BPAddresses) {
        this.BPAddresses = BPAddresses;
    }

    public ArrayList<PostContactEmployee> getContactEmployees() {
        return ContactEmployees;
    }

    public void setContactEmployees(ArrayList<PostContactEmployee> contactEmployees) {
        ContactEmployees = contactEmployees;
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

    public String getContactPerson() {
        return ContactPerson;
    }

    public String getShippingType() {
        return ShippingType;
    }

    public void setShippingType(String shippingType) {
        ShippingType = shippingType;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
