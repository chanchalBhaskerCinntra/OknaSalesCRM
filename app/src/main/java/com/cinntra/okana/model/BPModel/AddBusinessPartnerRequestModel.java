package com.cinntra.okana.model.BPModel;

import java.util.ArrayList;

public class AddBusinessPartnerRequestModel {

    public String U_LEADNM;
    
    public int U_LEADID;
    
    public String managerName;
    public String createdBy;
    public String CardCode;

    public String CardName;
    
    public String Industry;
    
    public String CardType;
    
    public String Website;
    
    public String EmailAddress;
    
    public String Phone1;
    
    public String CountryCode;
    
    public String DiscountPercent;
    
    public String Currency;
    
    public String IntrestRatePercent;
    
    public String CommissionPercent;
    
    public String Notes;
    
    public String PayTermsGrpCode;

    public String CreditLimit;
    
    public String AttachmentEntry;
    
    public String SalesPersonCode;
    
    public String U_PARENTACC;
    
    public String U_BPGRP;
    
    public String U_CONTOWNR;
    
    public String U_RATING;
    
    public String U_TYPE;
    
    public String U_ANLRVN;
    
    public String U_CURBAL;
    
    public String U_ACCNT;
    
    public String U_INVNO;
    
    public String CreateDate;
    
    public String CreateTime;
    
    public String UpdateDate;
    
    public String UpdateTime;

    public String U_LAT;
    
    public String U_LONG;
    
    public String Zone;
    public String secondEmail;

    public ArrayList<BPAddress> BPAddresses;

    public ArrayList<ContactEmployees> ContactEmployees;


    public static class ContactEmployees{
        
        public String Name;
        
        public String MobilePhone;

        public String E_Mail;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
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
    }


    public static class BPAddress{
        
        public String AddressName;
        
        public String AddressType;
        
        public String BPCode;
        
        public String Block;
        
        public String City;
        
        public String Country;
        
        public String RowNum;
        
        public String State;
        
        public String Street;
        
        public String U_COUNTRY;
        
        public String U_SHPTYP;
        
        public String U_STATE;
        
        public String ZipCode;


        public String getAddressName() {
            return AddressName;
        }

        public void setAddressName(String addressName) {
            AddressName = addressName;
        }

        public String getAddressType() {
            return AddressType;
        }

        public void setAddressType(String addressType) {
            AddressType = addressType;
        }


        public String getBPCode() {
            return BPCode;
        }

        public void setBPCode(String BPCode) {
            this.BPCode = BPCode;
        }

        public String getBlock() {
            return Block;
        }

        public void setBlock(String block) {
            Block = block;
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

        public String getRowNum() {
            return RowNum;
        }

        public void setRowNum(String rowNum) {
            RowNum = rowNum;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String street) {
            Street = street;
        }

        public String getU_COUNTRY() {
            return U_COUNTRY;
        }

        public void setU_COUNTRY(String u_COUNTRY) {
            U_COUNTRY = u_COUNTRY;
        }

        public String getU_SHPTYP() {
            return U_SHPTYP;
        }

        public void setU_SHPTYP(String u_SHPTYP) {
            U_SHPTYP = u_SHPTYP;
        }

        public String getU_STATE() {
            return U_STATE;
        }

        public void setU_STATE(String u_STATE) {
            U_STATE = u_STATE;
        }

        public String getZipCode() {
            return ZipCode;
        }

        public void setZipCode(String zipCode) {
            ZipCode = zipCode;
        }

    }


    public String getU_LEADNM() {
        return U_LEADNM;
    }

    public void setU_LEADNM(String u_LEADNM) {
        U_LEADNM = u_LEADNM;
    }

    public int getU_LEADID() {
        return U_LEADID;
    }

    public void setU_LEADID(int u_LEADID) {
        U_LEADID = u_LEADID;
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

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
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

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getIntrestRatePercent() {
        return IntrestRatePercent;
    }

    public void setIntrestRatePercent(String intrestRatePercent) {
        IntrestRatePercent = intrestRatePercent;
    }

    public String getCommissionPercent() {
        return CommissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        CommissionPercent = commissionPercent;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getPayTermsGrpCode() {
        return PayTermsGrpCode;
    }

    public void setPayTermsGrpCode(String payTermsGrpCode) {
        PayTermsGrpCode = payTermsGrpCode;
    }

    public String getCreditLimit() {
        return CreditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        CreditLimit = creditLimit;
    }

    public String getAttachmentEntry() {
        return AttachmentEntry;
    }

    public void setAttachmentEntry(String attachmentEntry) {
        AttachmentEntry = attachmentEntry;
    }

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public String getU_PARENTACC() {
        return U_PARENTACC;
    }

    public void setU_PARENTACC(String u_PARENTACC) {
        U_PARENTACC = u_PARENTACC;
    }

    public String getU_BPGRP() {
        return U_BPGRP;
    }

    public void setU_BPGRP(String u_BPGRP) {
        U_BPGRP = u_BPGRP;
    }

    public String getU_CONTOWNR() {
        return U_CONTOWNR;
    }

    public void setU_CONTOWNR(String u_CONTOWNR) {
        U_CONTOWNR = u_CONTOWNR;
    }

    public String getU_RATING() {
        return U_RATING;
    }

    public void setU_RATING(String u_RATING) {
        U_RATING = u_RATING;
    }

    public String getU_TYPE() {
        return U_TYPE;
    }

    public void setU_TYPE(String u_TYPE) {
        U_TYPE = u_TYPE;
    }

    public String getU_ANLRVN() {
        return U_ANLRVN;
    }

    public void setU_ANLRVN(String u_ANLRVN) {
        U_ANLRVN = u_ANLRVN;
    }

    public String getU_CURBAL() {
        return U_CURBAL;
    }

    public void setU_CURBAL(String u_CURBAL) {
        U_CURBAL = u_CURBAL;
    }

    public String getU_ACCNT() {
        return U_ACCNT;
    }

    public void setU_ACCNT(String u_ACCNT) {
        U_ACCNT = u_ACCNT;
    }

    public String getU_INVNO() {
        return U_INVNO;
    }

    public void setU_INVNO(String u_INVNO) {
        U_INVNO = u_INVNO;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getU_LAT() {
        return U_LAT;
    }

    public void setU_LAT(String u_LAT) {
        U_LAT = u_LAT;
    }

    public String getU_LONG() {
        return U_LONG;
    }

    public void setU_LONG(String u_LONG) {
        U_LONG = u_LONG;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public ArrayList<BPAddress> getBPAddresses() {
        return BPAddresses;
    }

    public void setBPAddresses(ArrayList<BPAddress> BPAddresses) {
        this.BPAddresses = BPAddresses;
    }

    public ArrayList<ContactEmployees> getContactEmployees() {
        return ContactEmployees;
    }

    public void setContactEmployees(ArrayList<ContactEmployees> contactEmployees) {
        ContactEmployees = contactEmployees;
    }

    public String getSecondEmail() {
        return secondEmail;
    }

    public void setSecondEmail(String secondEmail) {
        this.secondEmail = secondEmail;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
