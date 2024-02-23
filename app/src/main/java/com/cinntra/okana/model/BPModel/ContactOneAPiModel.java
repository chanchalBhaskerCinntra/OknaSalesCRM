package com.cinntra.okana.model.BPModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactOneAPiModel implements Serializable {

    public String message;
    public int status;
    public ArrayList<Data> data;

    public static class Data implements Serializable {
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("MiddleName")
        @Expose
        private String middleName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Position")
        @Expose
        private String position;
        @SerializedName("Address")
        @Expose
        private String address;
        @SerializedName("MobilePhone")
        @Expose
        private String mobilePhone;
        @SerializedName("Fax")
        @Expose
        private String fax;
        @SerializedName("E_Mail")
        @Expose
        private String eMail;
        @SerializedName("Remarks1")
        @Expose
        private String remarks1;
        @SerializedName("DateOfBirth")
        @Expose
        private String dateOfBirth;
        @SerializedName("Gender")
        @Expose
        private String gender;
        @SerializedName("Profession")
        @Expose
        private String profession;
        @SerializedName("CardCode")
        @Expose
        private String cardCode;
        @SerializedName("U_BPID")
        @Expose
        private String uBpid;
        @SerializedName("U_BRANCHID")
        @Expose
        private String uBranchid;
        @SerializedName("U_NATIONALTY")
        @Expose
        private String uNationalty;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("CreateTime")
        @Expose
        private String createTime;
        @SerializedName("UpdateDate")
        @Expose
        private String updateDate;
        @SerializedName("UpdateTime")
        @Expose
        private String updateTime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getFax() {
            return fax;
        }

        public void setFax(String fax) {
            this.fax = fax;
        }

        public String geteMail() {
            return eMail;
        }

        public void seteMail(String eMail) {
            this.eMail = eMail;
        }

        public String getRemarks1() {
            return remarks1;
        }

        public void setRemarks1(String remarks1) {
            this.remarks1 = remarks1;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getCardCode() {
            return cardCode;
        }

        public void setCardCode(String cardCode) {
            this.cardCode = cardCode;
        }

        public String getuBpid() {
            return uBpid;
        }

        public void setuBpid(String uBpid) {
            this.uBpid = uBpid;
        }

        public String getuBranchid() {
            return uBranchid;
        }

        public void setuBranchid(String uBranchid) {
            this.uBranchid = uBranchid;
        }

        public String getuNationalty() {
            return uNationalty;
        }

        public void setuNationalty(String uNationalty) {
            this.uNationalty = uNationalty;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
