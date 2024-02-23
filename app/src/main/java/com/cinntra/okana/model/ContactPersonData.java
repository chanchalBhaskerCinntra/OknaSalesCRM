package com.cinntra.okana.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "data_business_partner_employee_all")
public class ContactPersonData implements Serializable
{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("InternalCode")
    @Expose
    private String internalCode;
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
    private final static long serialVersionUID = 5160713061481795883L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ContactPersonData() {
    }

    /**
     *
     * @param profession
     * @param lastName
     * @param updateDate
     * @param address
     * @param gender
     * @param cardCode
     * @param dateOfBirth
     * @param updateTime
     * @param title
     * @param uBpid
     * @param eMail
     * @param uBranchid
     * @param firstName
     * @param mobilePhone
     * @param createTime
     * @param remarks1
     * @param middleName
     * @param id
     * @param position
     * @param fax
     * @param uNationalty
     * @param internalCode
     * @param createDate
     */
    public ContactPersonData(Integer id, String title, String firstName, String middleName, String lastName, String position, String address, String mobilePhone, String fax, String eMail, String remarks1, String internalCode, String dateOfBirth, String gender, String profession, String cardCode, String uBpid, String uBranchid, String uNationalty, String createDate, String createTime, String updateDate, String updateTime) {
        super();
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.position = position;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.fax = fax;
        this.eMail = eMail;
        this.remarks1 = remarks1;
        this.internalCode = internalCode;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.profession = profession;
        this.cardCode = cardCode;
        this.uBpid = uBpid;
        this.uBranchid = uBranchid;
        this.uNationalty = uNationalty;
        this.createDate = createDate;
        this.createTime = createTime;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getRemarks1() {
        return remarks1;
    }

    public void setRemarks1(String remarks1) {
        this.remarks1 = remarks1;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
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

    public String getUBpid() {
        return uBpid;
    }

    public void setUBpid(String uBpid) {
        this.uBpid = uBpid;
    }

    public String getUBranchid() {
        return uBranchid;
    }

    public void setUBranchid(String uBranchid) {
        this.uBranchid = uBranchid;
    }

    public String getUNationalty() {
        return uNationalty;
    }

    public void setUNationalty(String uNationalty) {
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