package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostContactEmployees implements Parcelable {
    @SerializedName("CardCode")
    @Expose
    String CardCode;
    @SerializedName("Name")
    @Expose
    String Name;
    @SerializedName("Position")
    @Expose
    String Position;
    @SerializedName("Address")
    @Expose
    String Address;
    @SerializedName("Phone1")
    @Expose
    String Phone1;
    @SerializedName("MobilePhone")
    @Expose
    String MobilePhone;
    @SerializedName("E_Mail")
    @Expose
    String E_Mail;
    @SerializedName("Gender")
    @Expose
    String Gender;
    @SerializedName("Active")
    @Expose
    String Active;
    @SerializedName("FirstName")
    @Expose
    String FirstName;
    @SerializedName("MiddleName")
    @Expose
    String MiddleName;
    @SerializedName("LastName")
    @Expose
    String LastName;

    protected PostContactEmployees(Parcel in) {
        CardCode = in.readString();
        Name = in.readString();
        Position = in.readString();
        Address = in.readString();
        Phone1 = in.readString();
        MobilePhone = in.readString();
        E_Mail = in.readString();
        Gender = in.readString();
        Active = in.readString();
        FirstName = in.readString();
        MiddleName = in.readString();
        LastName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CardCode);
        dest.writeString(Name);
        dest.writeString(Position);
        dest.writeString(Address);
        dest.writeString(Phone1);
        dest.writeString(MobilePhone);
        dest.writeString(E_Mail);
        dest.writeString(Gender);
        dest.writeString(Active);
        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(LastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostContactEmployees> CREATOR = new Creator<PostContactEmployees>() {
        @Override
        public PostContactEmployees createFromParcel(Parcel in) {
            return new PostContactEmployees(in);
        }

        @Override
        public PostContactEmployees[] newArray(int size) {
            return new PostContactEmployees[size];
        }
    };

    public String getCardCode()
     {
    return CardCode;
     }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }

    public String getE_Mail() {
        return E_Mail;
    }

    public void setE_Mail(String e_Mail) {
        E_Mail = e_Mail;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
