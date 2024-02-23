package com.cinntra.okana.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostContactEmployee implements Parcelable {
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
    @SerializedName("Fax")
    @Expose
    String Fax;
    @SerializedName("E_Mail")
    @Expose
    String E_Mail;
    @SerializedName("Pager")
    @Expose
    String Pager;

    @SerializedName("Remarks1")
    @Expose
    String Remarks1;
    @SerializedName("Remarks2")
    @Expose
    String Remarks2;
    @SerializedName("Password")
    @Expose
    String Password;
    @SerializedName("Gender")
    @Expose
    String Gender;
    @SerializedName("Title")
    @Expose
    String Title;
    @SerializedName("FirstName")
    @Expose
    String FirstName;
    @SerializedName("MiddleName")
    @Expose
    String MiddleName;
    @SerializedName("LastName")
    @Expose
    String LastName;
    @SerializedName("InternalCode")
    @Expose
    String InternalCode;


    public PostContactEmployee(){};


    protected PostContactEmployee(Parcel in) {
        CardCode = in.readString();
        Name = in.readString();
        Position = in.readString();
        Address = in.readString();
        Phone1 = in.readString();
        Phone2 = in.readString();
        MobilePhone = in.readString();
        Fax = in.readString();
        E_Mail = in.readString();
        Pager = in.readString();
        Remarks1 = in.readString();
        Remarks2 = in.readString();
        Password = in.readString();
        Gender = in.readString();
        Title = in.readString();
        FirstName = in.readString();
        MiddleName = in.readString();
        LastName = in.readString();
        InternalCode = in.readString();
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
        dest.writeString(Fax);
        dest.writeString(E_Mail);
        dest.writeString(Pager);
        dest.writeString(Remarks1);
        dest.writeString(Remarks2);
        dest.writeString(Password);
        dest.writeString(Gender);
        dest.writeString(Title);
        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(LastName);
        dest.writeString(InternalCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostContactEmployee> CREATOR = new Creator<PostContactEmployee>() {
        @Override
        public PostContactEmployee createFromParcel(Parcel in) {
            return new PostContactEmployee(in);
        }

        @Override
        public PostContactEmployee[] newArray(int size) {
            return new PostContactEmployee[size];
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

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getE_Mail() {
        return E_Mail;
    }

    public void setE_Mail(String e_Mail) {
        E_Mail = e_Mail;
    }

    public String getPager() {
        return Pager;
    }

    public void setPager(String pager) {
        Pager = pager;
    }

    public String getRemarks1() {
        return Remarks1;
    }

    public void setRemarks1(String remarks1) {
        Remarks1 = remarks1;
    }

    public String getRemarks2() {
        return Remarks2;
    }

    public void setRemarks2(String remarks2) {
        Remarks2 = remarks2;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
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

    public String getInternalCode() {
        return InternalCode;
    }

    public void setInternalCode(String internalCode) {
        InternalCode = internalCode;
    }
}
