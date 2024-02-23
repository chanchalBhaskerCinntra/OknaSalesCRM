package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class AddOpportunity implements Parcelable {

    @SerializedName("CardCode")
    @Expose
    private String CardCode;
    @SerializedName("SalesPerson")
    @Expose
    private int SalesPerson;
    @SerializedName("MaxLocalTotal")
    @Expose
    float MaxLocalTotal;
    @SerializedName("MaxSystemTotal")
    @Expose
    float MaxSystemTotal;
    @SerializedName("Remarks")
    @Expose
    String Remarks;
    @SerializedName("StartDate")
    @Expose
    String StartDate;
    @SerializedName("PredictedClosingDate")
    @Expose
    String ClosingDate;
    @SerializedName("U_TYPE")
    @Expose
    String U_TYPE;
    @SerializedName("U_LSOURCE")
    @Expose
    String U_LSOURCE;
    @SerializedName("U_PROBLTY")
    @Expose
    String U_PROBLTY;
    @SerializedName("OpportunityName")
    @Expose
    String opportunityName;
    @SerializedName("DataOwnershipfield")
    @Expose
    String DataOwnershipfield;

    @SerializedName("U_FAV")
    @Expose
    String U_FAV;

    @SerializedName("ContactPerson")
    @Expose
    String ContactPerson;

    @SerializedName("SalesOpportunitiesLines")
    @Expose
    private ArrayList<SalesOpportunitiesLines> SalesOpportunitiesLines;

    public String getU_PROBLTY() {
        return U_PROBLTY;
    }

    public void setU_PROBLTY(String u_PROBLTY) {
        U_PROBLTY = u_PROBLTY;
    }

    public String getDataOwnershipfield() {
        return DataOwnershipfield;
    }

    public void setDataOwnershipfield(String dataOwnershipfield) {
        DataOwnershipfield = dataOwnershipfield;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getClosingDate() {
        return ClosingDate;
    }

    public void setClosingDate(String closingDate) {
        ClosingDate = closingDate;
    }

    public String getU_TYPE() {
        return U_TYPE;
    }

    public void setU_TYPE(String u_TYPE) {
        U_TYPE = u_TYPE;

    }

    public String getU_LSOURCE() {
        return U_LSOURCE;
    }

    public void setU_LSOURCE(String u_LSOURCE) {
        U_LSOURCE = u_LSOURCE;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public int getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(int salesPerson) {
        SalesPerson = salesPerson;
    }

    public float getMaxLocalTotal() {
        return MaxLocalTotal;
    }

    public void setMaxLocalTotal(float maxLocalTotal) {
        MaxLocalTotal = maxLocalTotal;
    }

    public float getMaxSystemTotal() {
        return MaxSystemTotal;
    }

    public void setMaxSystemTotal(float maxSystemTotal) {
        MaxSystemTotal = maxSystemTotal;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public ArrayList<SalesOpportunitiesLines> getSalesOpportunitiesLines() {
        return SalesOpportunitiesLines;
    }

    public void setSalesOpportunitiesLines(ArrayList<SalesOpportunitiesLines> salesOpportunitiesLines) {
        SalesOpportunitiesLines = salesOpportunitiesLines;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getU_FAV() {
        return U_FAV;
    }

    public void setU_FAV(String u_FAV) {
        U_FAV = u_FAV;
    }

    public AddOpportunity()
      {}


    protected AddOpportunity(Parcel in) {
        this.CardCode        = ((String) in.readValue((String.class.getClassLoader())));
        this.ClosingDate     = ((String) in.readValue((String.class.getClassLoader())));
        this.StartDate       = ((String) in.readValue((String.class.getClassLoader())));
        this.U_TYPE          = ((String) in.readValue((String.class.getClassLoader())));
        this.U_LSOURCE       = ((String) in.readValue((String.class.getClassLoader())));
        this.opportunityName    = ((String) in.readValue((String.class.getClassLoader())));
        this.DataOwnershipfield = ((String) in.readValue((String.class.getClassLoader())));
        this.ContactPerson = ((String) in.readValue((String.class.getClassLoader())));
        this.U_PROBLTY     = ((String) in.readValue((String.class.getClassLoader())));
        this.U_FAV         = ((String) in.readValue((String.class.getClassLoader())));

        this.SalesOpportunitiesLines  = ((ArrayList) in.readValue((ArrayList.class.getClassLoader())));

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(CardCode);
        dest.writeValue(ClosingDate);
        dest.writeValue(StartDate);
        dest.writeValue(U_TYPE);
        dest.writeValue(U_LSOURCE);
        dest.writeValue(opportunityName);
        dest.writeValue(DataOwnershipfield);
        dest.writeValue(ContactPerson);
        dest.writeValue(U_PROBLTY);
        dest.writeValue(U_FAV);

        dest.writeValue(SalesOpportunitiesLines);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddOpportunity> CREATOR = new Creator<AddOpportunity>()
      {
        @Override
        public AddOpportunity createFromParcel(Parcel in) {
            return new AddOpportunity(in);
        }

        @Override
        public AddOpportunity[] newArray(int size) {
            return new AddOpportunity[size];
        }
    };
}
