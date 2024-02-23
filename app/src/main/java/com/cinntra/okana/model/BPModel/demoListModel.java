package com.cinntra.okana.model.BPModel;

import java.io.Serializable;
import java.util.ArrayList;

public class demoListModel implements Serializable {
    public String message;
    public int status;
    public ArrayList<Datum> data = new ArrayList<>();
    public Meta meta;

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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public class Datum implements Serializable {
        public String id;
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
        public String ContactPerson;
        public String U_LEADID;
        public String U_LEADNM;
        public String U_PARENTACC;
        public String U_BPGRP;
        public String U_CONTOWNR;
        public String U_RATING;
        public String U_TYPE;
        public String U_ANLRVN;
        public String U_CURBAL;
        public String U_ACCNT;
        public String U_INVNO;
        public String U_LAT;
        public String U_LONG;
        public String U_SOURCE;
        public String U_EMIRATESID;
        public String U_VATNUMBER;
        public String CreateDate;
        public String CreateTime;
        public String UpdateDate;
        public String UpdateTime;
        public String Zone;
        public String LoyaltyPoints;
        public String ShipToDefault;

        public String BpAddresses;


        // Getter for ShipToDefault
        public String getShipToDefault() {
            return ShipToDefault;
        }

        // Setter for ShipToDefault
        public void setShipToDefault(String ShipToDefault) {
            this.ShipToDefault = ShipToDefault;
        }

        public String getBpAddresses() {
            return BpAddresses;
        }

        public void setBpAddresses(String bpAddresses) {
            BpAddresses = bpAddresses;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getContactPerson() {
            return ContactPerson;
        }

        public void setContactPerson(String contactPerson) {
            ContactPerson = contactPerson;
        }

        public String getU_LEADID() {
            return U_LEADID;
        }

        public void setU_LEADID(String u_LEADID) {
            U_LEADID = u_LEADID;
        }

        public String getU_LEADNM() {
            return U_LEADNM;
        }

        public void setU_LEADNM(String u_LEADNM) {
            U_LEADNM = u_LEADNM;
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

        public String getU_SOURCE() {
            return U_SOURCE;
        }

        public void setU_SOURCE(String u_SOURCE) {
            U_SOURCE = u_SOURCE;
        }

        public String getU_EMIRATESID() {
            return U_EMIRATESID;
        }

        public void setU_EMIRATESID(String u_EMIRATESID) {
            U_EMIRATESID = u_EMIRATESID;
        }

        public String getU_VATNUMBER() {
            return U_VATNUMBER;
        }

        public void setU_VATNUMBER(String u_VATNUMBER) {
            U_VATNUMBER = u_VATNUMBER;
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

        public String getZone() {
            return Zone;
        }

        public void setZone(String zone) {
            Zone = zone;
        }

        public String getLoyaltyPoints() {
            return LoyaltyPoints;
        }

        public void setLoyaltyPoints(String loyaltyPoints) {
            LoyaltyPoints = loyaltyPoints;
        }

    }


    public class Meta {
        public int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

}
