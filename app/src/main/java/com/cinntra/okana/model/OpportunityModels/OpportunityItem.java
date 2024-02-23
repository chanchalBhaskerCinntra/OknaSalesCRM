package com.cinntra.okana.model.OpportunityModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinntra.okana.model.SalesOpportunitiesLinesGet;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;


public class OpportunityItem implements Serializable, Parcelable {
    @SerializedName("SequentialNo")
    String SequentialNo;
    @SerializedName("OpportunityName")
    String OpportunityName;

    @SerializedName("CardCode")
    String CardCode;
    @SerializedName("SalesPerson")
    String SalesPerson;
    @SerializedName("StartDate")
    String StartDate;
    @SerializedName("PredictedClosingDate")
    String PredictedClosingDate;
    @SerializedName("MaxLocalTotal")
    String MaxLocalTotal;
    @SerializedName("MaxSystemTotal")
    String MaxSystemTotal;
    @SerializedName("WeightedSumLC")
    String WeightedSumLC;
    @SerializedName("WeightedSumSC")
    String WeightedSumSC;
    @SerializedName("GrossProfit")
    String GrossProfit;
    @SerializedName("GrossProfitTotalLocal")
    String GrossProfitTotalLocal;
    @SerializedName("GrossProfitTotalSystem")
    String GrossProfitTotalSystem;
    @SerializedName("Remarks")
    String Remarks;
    @SerializedName("Status")
    String Status;
    @SerializedName("TotalAmountLocal")
    String TotalAmountLocal;
    @SerializedName("TotalAmounSystem")
    String TotalAmounSystem;
    @SerializedName("ClosingGrossProfitLocal")
    String ClosingGrossProfitLocal;
    @SerializedName("ClosingGrossProfitSystem")
    String ClosingGrossProfitSystem;
    @SerializedName("ClosingPercentage")
    String ClosingPercentage;
    @SerializedName("CurrentStageNo")
    String CurrentStageNo;
    @SerializedName("CurrentStageNumber")
    String CurrentStageNumber;
    @SerializedName("ClosingType")
    String ClosingType;
    @SerializedName("UserSignature")
    String UserSignature;
    @SerializedName("CustomerName")
    String CustomerName;
    @SerializedName("OpportunityType")
    String OpportunityType;
    @SerializedName("BPChanelName")
    String BPChanelName;
    @SerializedName("ContactPerson")
    String ContactPerson;
    @SerializedName("ClosingDate")
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
    @SerializedName("Opp_Id")
    @Expose
    String Opp_Id;



    @SerializedName("U_FAV")
    @Expose
    String U_FAV;


    @SerializedName("DataOwnershipfield")
    @Expose
    String DataOwnershipfield;

    @SerializedName("SalesOpportunitiesLines")
    @Expose
    private ArrayList<SalesOpportunitiesLinesGet> SalesOpportunitiesLines;

    public OpportunityItem(){
    }

    protected OpportunityItem(Parcel in) {
        SequentialNo = in.readString();
        OpportunityName = in.readString();
        CardCode = in.readString();
        SalesPerson = in.readString();
        StartDate = in.readString();
        PredictedClosingDate = in.readString();
        MaxLocalTotal = in.readString();
        MaxSystemTotal = in.readString();
        WeightedSumLC = in.readString();
        WeightedSumSC = in.readString();
        GrossProfit = in.readString();
        GrossProfitTotalLocal = in.readString();
        GrossProfitTotalSystem = in.readString();
        Remarks = in.readString();
        Status = in.readString();
        TotalAmountLocal = in.readString();
        TotalAmounSystem = in.readString();
        ClosingGrossProfitLocal = in.readString();
        ClosingGrossProfitSystem = in.readString();
        ClosingPercentage = in.readString();
        CurrentStageNo = in.readString();
        CurrentStageNumber = in.readString();
        ClosingType = in.readString();
        UserSignature = in.readString();
        CustomerName = in.readString();
        OpportunityType = in.readString();
        BPChanelName = in.readString();
        ContactPerson = in.readString();
        ClosingDate = in.readString();
        U_TYPE = in.readString();
        U_LSOURCE = in.readString();
        U_PROBLTY = in.readString();
        U_FAV = in.readString();
        DataOwnershipfield = in.readString();
        Opp_Id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SequentialNo);
        dest.writeString(OpportunityName);
        dest.writeString(CardCode);
        dest.writeString(SalesPerson);
        dest.writeString(StartDate);
        dest.writeString(PredictedClosingDate);
        dest.writeString(MaxLocalTotal);
        dest.writeString(MaxSystemTotal);
        dest.writeString(WeightedSumLC);
        dest.writeString(WeightedSumSC);
        dest.writeString(GrossProfit);
        dest.writeString(GrossProfitTotalLocal);
        dest.writeString(GrossProfitTotalSystem);
        dest.writeString(Remarks);
        dest.writeString(Status);
        dest.writeString(TotalAmountLocal);
        dest.writeString(TotalAmounSystem);
        dest.writeString(ClosingGrossProfitLocal);
        dest.writeString(ClosingGrossProfitSystem);
        dest.writeString(ClosingPercentage);
        dest.writeString(CurrentStageNo);
        dest.writeString(CurrentStageNumber);
        dest.writeString(ClosingType);
        dest.writeString(UserSignature);
        dest.writeString(CustomerName);
        dest.writeString(OpportunityType);
        dest.writeString(BPChanelName);
        dest.writeString(ContactPerson);
        dest.writeString(ClosingDate);
        dest.writeString(U_TYPE);
        dest.writeString(U_LSOURCE);
        dest.writeString(U_PROBLTY);
        dest.writeString(U_FAV);
        dest.writeString(DataOwnershipfield);
        dest.writeString(Opp_Id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OpportunityItem> CREATOR = new Creator<OpportunityItem>() {
        @Override
        public OpportunityItem createFromParcel(Parcel in) {
            return new OpportunityItem(in);
        }

        @Override
        public OpportunityItem[] newArray(int size) {
            return new OpportunityItem[size];
        }
    };

    public String getU_FAV() {
        return U_FAV;
    }

    public void setU_FAV(String u_FAV) {
        U_FAV = u_FAV;
    }


    public ArrayList<SalesOpportunitiesLinesGet> getSalesOpportunitiesLines() {
        return SalesOpportunitiesLines;
    }

    public void setSalesOpportunitiesLines(ArrayList<SalesOpportunitiesLinesGet> salesOpportunitiesLines) {
        SalesOpportunitiesLines = salesOpportunitiesLines;
    }

    public String getSequentialNo() {
        return SequentialNo;
    }

    public void setSequentialNo(String sequentialNo) {
        SequentialNo = sequentialNo;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        SalesPerson = salesPerson;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getPredictedClosingDate() {
        return PredictedClosingDate;
    }

    public void setPredictedClosingDate(String predictedClosingDate) {
        PredictedClosingDate = predictedClosingDate;
    }

    public String getMaxLocalTotal() {
        return MaxLocalTotal;
    }

    public void setMaxLocalTotal(String maxLocalTotal) {
        MaxLocalTotal = maxLocalTotal;
    }

    public String getMaxSystemTotal() {
        return MaxSystemTotal;
    }

    public void setMaxSystemTotal(String maxSystemTotal) {
        MaxSystemTotal = maxSystemTotal;
    }

    public String getWeightedSumLC() {
        return WeightedSumLC;
    }

    public void setWeightedSumLC(String weightedSumLC) {
        WeightedSumLC = weightedSumLC;
    }

    public String getWeightedSumSC() {
        return WeightedSumSC;
    }

    public void setWeightedSumSC(String weightedSumSC) {
        WeightedSumSC = weightedSumSC;
    }

    public String getGrossProfit() {
        return GrossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        GrossProfit = grossProfit;
    }

    public String getGrossProfitTotalLocal() {
        return GrossProfitTotalLocal;
    }

    public void setGrossProfitTotalLocal(String grossProfitTotalLocal) {
        GrossProfitTotalLocal = grossProfitTotalLocal;
    }

    public String getGrossProfitTotalSystem() {
        return GrossProfitTotalSystem;
    }

    public void setGrossProfitTotalSystem(String grossProfitTotalSystem) {
        GrossProfitTotalSystem = grossProfitTotalSystem;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotalAmountLocal() {
        return TotalAmountLocal;
    }

    public void setTotalAmountLocal(String totalAmountLocal) {
        TotalAmountLocal = totalAmountLocal;
    }

    public String getTotalAmounSystem() {
        return TotalAmounSystem;
    }

    public void setTotalAmounSystem(String totalAmounSystem) {
        TotalAmounSystem = totalAmounSystem;
    }

    public String getClosingGrossProfitLocal() {
        return ClosingGrossProfitLocal;
    }

    public void setClosingGrossProfitLocal(String closingGrossProfitLocal) {
        ClosingGrossProfitLocal = closingGrossProfitLocal;
    }

    public String getClosingGrossProfitSystem() {
        return ClosingGrossProfitSystem;
    }

    public void setClosingGrossProfitSystem(String closingGrossProfitSystem) {
        ClosingGrossProfitSystem = closingGrossProfitSystem;
    }

    public String getClosingPercentage() {
        return ClosingPercentage;
    }

    public void setClosingPercentage(String closingPercentage) {
        ClosingPercentage = closingPercentage;
    }

    public String getCurrentStageNo() {
        return CurrentStageNo;
    }

    public void setCurrentStageNo(String currentStageNo) {
        CurrentStageNo = currentStageNo;
    }

    public String getCurrentStageNumber() {
        return CurrentStageNumber;
    }

    public void setCurrentStageNumber(String currentStageNumber) {
        CurrentStageNumber = currentStageNumber;
    }

    public String getClosingType() {
        return ClosingType;
    }

    public void setClosingType(String closingType) {
        ClosingType = closingType;
    }

    public String getUserSignature() {
        return UserSignature;
    }

    public void setUserSignature(String userSignature) {
        UserSignature = userSignature;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getOpportunityType() {
        return OpportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        OpportunityType = opportunityType;
    }

    public String getOpportunityName() {
        return OpportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        OpportunityName = opportunityName;
    }

    public String getBPChanelName() {
        return BPChanelName;
    }

    public void setBPChanelName(String BPChanelName) {
        this.BPChanelName = BPChanelName;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
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

    public String getDataOwnershipfield() {
        return DataOwnershipfield;
    }

    public void setDataOwnershipfield(String dataOwnershipfield) {
        DataOwnershipfield = dataOwnershipfield;
    }

    public String getU_PROBLTY() {
        return U_PROBLTY;
    }

    public void setU_PROBLTY(String u_PROBLTY) {
        U_PROBLTY = u_PROBLTY;
    }


    public String getOpp_Id() {
        return Opp_Id;
    }

    public void setOpp_Id(String opp_Id) {
        Opp_Id = opp_Id;
    }
}
