package com.cinntra.okana.model;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesOpportunitiesLines implements Parcelable {
    @SerializedName("SalesPerson")
    @Expose
    int SalesPerson;
    @SerializedName("MaxLocalTotal")
    @Expose
    float MaxLocalTotal;
    @SerializedName("MaxSystemTotal")
    @Expose
    float MaxSystemTotal;
    @SerializedName("DocumentType")
    @Expose
    String DocumentType;
    @SerializedName("StageKey")
    @Expose
    String StageKey;

    @SerializedName("LineNum")
    @Expose
    String LineNum;



    public SalesOpportunitiesLines(){}
    protected SalesOpportunitiesLines(Parcel in)
     {
        SalesPerson = in.readInt();
        MaxLocalTotal = in.readFloat();
        MaxSystemTotal = in.readFloat();
        DocumentType = in.readString();
        StageKey = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(SalesPerson);
        dest.writeFloat(MaxLocalTotal);
        dest.writeFloat(MaxSystemTotal);
        dest.writeString(DocumentType);
        dest.writeString(StageKey);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SalesOpportunitiesLines> CREATOR = new Creator<SalesOpportunitiesLines>() {
        @Override
        public SalesOpportunitiesLines createFromParcel(Parcel in) {
            return new SalesOpportunitiesLines(in);
        }

        @Override
        public SalesOpportunitiesLines[] newArray(int size) {
            return new SalesOpportunitiesLines[size];
        }
    };

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

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }

    public String getStageKey() {
        return StageKey;
    }

    public void setStageKey(String stageKey) {
        StageKey = stageKey;
    }

    public String getLineNum() {
        return LineNum;
    }

    public void setLineNum(String lineNum) {
        LineNum = lineNum;
    }


}
