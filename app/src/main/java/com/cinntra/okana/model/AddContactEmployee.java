package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddContactEmployee implements Parcelable {
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
    @SerializedName("Phone2")
    @Expose
    String Phone2;
    @SerializedName("MobilePhone")
    @Expose
    String MobilePhone;

    @SerializedName("E_Mail")
    @Expose
    String E_Mail;
    @SerializedName("Profession")
    @Expose
    String Profession;
    @SerializedName("FirstName")
    @Expose
    String FirstName;
    @SerializedName("LastName")
    @Expose
    String LastName;

    public AddContactEmployee(){};


    protected AddContactEmployee(Parcel in) {
        CardCode   = in.readString();
        Name       = in.readString();
        Position   = in.readString();
        Address    = in.readString();
        Phone1     = in.readString();
        Phone2     = in.readString();
        MobilePhone = in.readString();
        Profession = in.readString();
        E_Mail = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(CardCode);
        dest.writeString(Name);
        dest.writeString(Position);
        dest.writeString(Address);
        dest.writeString(Phone1);
        dest.writeString(Phone2);
        dest.writeString(MobilePhone);
        dest.writeString(Profession);
        dest.writeString(E_Mail);
        dest.writeString(FirstName);
        dest.writeString(LastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddContactEmployee> CREATOR = new Creator<AddContactEmployee>() {
        @Override
        public AddContactEmployee createFromParcel(Parcel in) {
            return new AddContactEmployee(in);
        }

        @Override
        public AddContactEmployee[] newArray(int size) {
            return new AddContactEmployee[size];
        }
    };

    public String getCardCode() {
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

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
    }
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getE_Mail() {
        return E_Mail;
    }

    public void setE_Mail(String e_Mail) {
        E_Mail = e_Mail;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }
}
