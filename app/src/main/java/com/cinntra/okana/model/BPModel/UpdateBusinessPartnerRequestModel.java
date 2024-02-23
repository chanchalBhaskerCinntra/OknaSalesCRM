package com.cinntra.okana.model.BPModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateBusinessPartnerRequestModel {

    public String id;
    public String U_LEADNM;

    public int U_LEADID;

    public String BPCode;

    public String ShipToDefault;

    public String BilltoDefault;

    public String CardCode;

    public String CardName;

    public String Industry;

    public String UseBillToAddrToDetermineTax;

    public String PAN;

    public String CardType;

    public String Website;

    public String EmailAddress;
    public String secondEmail;
    public String managerName;
    public String createdBy;
    public String ContactPerson;

    public String U_SOURCE;

    public String U_EMIRATESID;

    public String U_VATNUMBER;

    public String Phone1;

    public String CountryCode;

    public String DiscountPercent;

    public String Currency;

    public String IntrestRatePercent;

    public String CommissionPercent;

    public String Notes;

    public String PayTermsGrpCode;

    public ArrayList<Object> BPLID;

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

    public String Cellular;

    public String GroupCode;

    public String U_LONG;

    public String Zone;
    public String dep;

    public ArrayList<BPAddress> BPAddresses;

    private List<ContactEmployees> ContactEmployees = new ArrayList<>();


    public static class ContactEmployees{

        public String Name;

        public String MobilePhone;

        public String E_Mail;
        public String CardCode;
        public String FirstName;
        public String id;
        public String InternalCode;

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

        public String getCardCode() {
            return CardCode;
        }

        public void setCardCode(String cardCode) {
            CardCode = cardCode;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInternalCode() {
            return InternalCode;
        }

        public void setInternalCode(String internalCode) {
            InternalCode = internalCode;
        }
    }


    public static class BPAddress {

        public String id;
        public String AddressName;

        public String AddressType;

        public String BPID;

        public String FederalTaxID;

        public String BranchName;

        public String BPCode;

        public String Block;

        public String City;

        public String Country;

        public String RowNum;
        public String StateCode;

        public String State;

        public String Street;

        public String U_COUNTRY;

        public String U_SHPTYP;

        public String U_STATE;

        public String ZipCode;

        public String GSTIN;

        public String GstType;

        public String U_Contperson;

        public String U_CONTNO;

        public String Email;

        public String TaxCode;

        public String BuildingFloorRoom;

        public String AddressName2;

        public String AddressName3;

        public String TypeOfAddress;

        public String StreetNo;

        public String GlobalLocationNumber;

        public String Nationality;

        public String TaxOffice;

        public String CreateDate;

        public String CreateTime;

        public String MYFType;

        public String TaasEnabled;

        public String U_CGSTN;

        public String County;

        public String Lat;

        public String Long;

        public String Status;

        public String Default;

        public String BranchId;

        public String BranchType;

        public String ServiceType;
        public String U_Zonalmanager;


        public String getStateCode() {
            return StateCode;
        }

        public void setStateCode(String stateCode) {
            StateCode = stateCode;
        }

        public String getU_Zonalmanager() {
            return U_Zonalmanager;
        }

        public void setU_Zonalmanager(String u_Zonalmanager) {
            U_Zonalmanager = u_Zonalmanager;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getBPID() {
            return BPID;
        }

        public void setBPID(String BPID) {
            this.BPID = BPID;
        }

        public String getFederalTaxID() {
            return FederalTaxID;
        }

        public void setFederalTaxID(String federalTaxID) {
            FederalTaxID = federalTaxID;
        }

        public String getBranchName() {
            return BranchName;
        }

        public void setBranchName(String branchName) {
            BranchName = branchName;
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

        public String getGSTIN() {
            return GSTIN;
        }

        public void setGSTIN(String GSTIN) {
            this.GSTIN = GSTIN;
        }

        public String getGstType() {
            return GstType;
        }

        public void setGstType(String gstType) {
            GstType = gstType;
        }

        public String getU_Contperson() {
            return U_Contperson;
        }

        public void setU_Contperson(String u_Contperson) {
            U_Contperson = u_Contperson;
        }

        public String getU_CONTNO() {
            return U_CONTNO;
        }

        public void setU_CONTNO(String u_CONTNO) {
            U_CONTNO = u_CONTNO;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getTaxCode() {
            return TaxCode;
        }

        public void setTaxCode(String taxCode) {
            TaxCode = taxCode;
        }

        public String getBuildingFloorRoom() {
            return BuildingFloorRoom;
        }

        public void setBuildingFloorRoom(String buildingFloorRoom) {
            BuildingFloorRoom = buildingFloorRoom;
        }

        public String getAddressName2() {
            return AddressName2;
        }

        public void setAddressName2(String addressName2) {
            AddressName2 = addressName2;
        }

        public String getAddressName3() {
            return AddressName3;
        }

        public void setAddressName3(String addressName3) {
            AddressName3 = addressName3;
        }

        public String getTypeOfAddress() {
            return TypeOfAddress;
        }

        public void setTypeOfAddress(String typeOfAddress) {
            TypeOfAddress = typeOfAddress;
        }

        public String getStreetNo() {
            return StreetNo;
        }

        public void setStreetNo(String streetNo) {
            StreetNo = streetNo;
        }

        public String getGlobalLocationNumber() {
            return GlobalLocationNumber;
        }

        public void setGlobalLocationNumber(String globalLocationNumber) {
            GlobalLocationNumber = globalLocationNumber;
        }

        public String getNationality() {
            return Nationality;
        }

        public void setNationality(String nationality) {
            Nationality = nationality;
        }

        public String getTaxOffice() {
            return TaxOffice;
        }

        public void setTaxOffice(String taxOffice) {
            TaxOffice = taxOffice;
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

        public String getMYFType() {
            return MYFType;
        }

        public void setMYFType(String MYFType) {
            this.MYFType = MYFType;
        }

        public String getTaasEnabled() {
            return TaasEnabled;
        }

        public void setTaasEnabled(String taasEnabled) {
            TaasEnabled = taasEnabled;
        }

        public String getU_CGSTN() {
            return U_CGSTN;
        }

        public void setU_CGSTN(String u_CGSTN) {
            U_CGSTN = u_CGSTN;
        }

        public String getCounty() {
            return County;
        }

        public void setCounty(String county) {
            County = county;
        }

        public String getLat() {
            return Lat;
        }

        public void setLat(String lat) {
            Lat = lat;
        }

        public String getLong() {
            return Long;
        }

        public void setLong(String aLong) {
            Long = aLong;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getDefault() {
            return Default;
        }

        public void setDefault(String aDefault) {
            Default = aDefault;
        }

        public String getBranchId() {
            return BranchId;
        }

        public void setBranchId(String branchId) {
            BranchId = branchId;
        }

        public String getBranchType() {
            return BranchType;
        }

        public void setBranchType(String branchType) {
            BranchType = branchType;
        }

        public String getServiceType() {
            return ServiceType;
        }

        public void setServiceType(String serviceType) {
            ServiceType = serviceType;
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

    public String getBPCode() {
        return BPCode;
    }

    public void setBPCode(String BPCode) {
        this.BPCode = BPCode;
    }

    public String getShipToDefault() {
        return ShipToDefault;
    }

    public void setShipToDefault(String shipToDefault) {
        ShipToDefault = shipToDefault;
    }

    public String getBilltoDefault() {
        return BilltoDefault;
    }

    public void setBilltoDefault(String billtoDefault) {
        BilltoDefault = billtoDefault;
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

    public String getUseBillToAddrToDetermineTax() {
        return UseBillToAddrToDetermineTax;
    }

    public void setUseBillToAddrToDetermineTax(String useBillToAddrToDetermineTax) {
        UseBillToAddrToDetermineTax = useBillToAddrToDetermineTax;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
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


    public ArrayList<Object> getBPLID() {
        return BPLID;
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

    public String getCellular() {
        return Cellular;
    }

    public void setCellular(String cellular) {
        Cellular = cellular;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
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

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public ArrayList<BPAddress> getBPAddresses() {
        return BPAddresses;
    }

    public void setBPAddresses(ArrayList<BPAddress> BPAddresses) {
        this.BPAddresses = BPAddresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public void setBPLID(ArrayList<Object> BPLID) {
        this.BPLID = BPLID;
    }


    public List<UpdateBusinessPartnerRequestModel.ContactEmployees> getContactEmployees() {
        return ContactEmployees;
    }

    public void setContactEmployees(List<UpdateBusinessPartnerRequestModel.ContactEmployees> contactEmployees) {
        ContactEmployees = contactEmployees;
    }
}
