package com.cinntra.okana.model.orderModels;

import com.cinntra.okana.model.AddressExtensions;
import com.cinntra.okana.model.ContactEmployees;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.newapimodel.AttachDocument;

import java.util.ArrayList;

public class OrderDetailResponseModel {

    public String message;
    public int status;
    public ArrayList<Data> data;

    public static class Data{
        public String id;
        
        public String TaxDate;
        
        public String DocDueDate;
        
        public ArrayList<ContactEmployees> ContactPersonCode;
        
        public String DiscountPercent;
        
        public String DocDate;
        public String DeliveryDate;
        public String GlassDate;
        public String CoatingDate;

        public String CardCode;
        
        public String Comments;
        
        public ArrayList<SalesPersonCodes> SalesPersonCode;
        
        public String DocumentStatus;
        
        public String CancelStatus;
        
        public String DocCurrency;
        
        public String DocTotal;
        
        public String CardName;
        
        public String VatSum;
        
        public String CreationDate;
        
        public String CreatedBy;
        
        public String DocEntry;
        public String DocNum;

        public String PaymentGroupCode;
        
        public String U_Term_Condition;
        
        public String U_TermInterestRate;
        
        public String U_TermPaymentTerm;
        
        public String U_TermDueDate;
        
        public String U_QUOTNM;
        
        public String U_QUOTID;
        
        public int U_LEADID;
        
        public String U_LEADNM;
        
        public String U_OPPID;
        
        public String U_OPPRNM;
        
        public String BPLID;
        
        public String DelStatus;
        
        public String CreateDate;
        
        public String CreateTime;
        
        public String UpdateDate;
        
        public String UpdateTime;
        
        public ArrayList<AttachDocument> Attach;
        
        public String Caption;
        
        public String OrdLevel1Status;
        
        public String OrdLevel2Status;
        
        public String OrdLevel3Status;
        
        public String FinalStatus;
        
        public String PoNo;
        
        public String PoAmt;
        
        public String PoDate;
        
        public String PRID;
        
        public String ShippingAndHandling;
        public String ShippingAndHandlingTax;

        public String TermsAndConditions;
        
        public String U_Pay1;
        
        public String U_Pay2;
        
        public String U_Pay3;
        
        public String U_Pay4;
        
        public String U_Pay5;
        
        public String DepName;
        
        public String SystemName;
        
        public String TaxPercentage;
        
        public int U_ORDRID;
        
        public String U_ORDRNM;
        
        public String PayToCode;
        
        public String ShipToCode;
        
        public Object OrdLevel1;
        
        public Object OrdLevel2;
        
        public Object OrdLevel3;
        
        public AddressExtensions AddressExtension;
        
        public ArrayList<com.cinntra.okana.model.DocumentLines> DocumentLines;

        public String getDocNum() {
            return DocNum;
        }

        public void setDocNum(String docNum) {
            DocNum = docNum;
        }

        public String BPEmail;

        public String getShippingAndHandlingTax() {
            return ShippingAndHandlingTax;
        }

        public void setShippingAndHandlingTax(String shippingAndHandlingTax) {
            ShippingAndHandlingTax = shippingAndHandlingTax;
        }


        public String getDeliveryDate() {
            return DeliveryDate;
        }

        public void setDeliveryDate(String deliveryDate) {
            DeliveryDate = deliveryDate;
        }

        public String getGlassDate() {
            return GlassDate;
        }

        public void setGlassDate(String glassDate) {
            GlassDate = glassDate;
        }

        public String getCoatingDate() {
            return CoatingDate;
        }

        public void setCoatingDate(String coatingDate) {
            CoatingDate = coatingDate;
        }

        public class SalesPersonCodes{
            public int id;
            public String companyID;
            
            public String SalesEmployeeCode;
            
            public String SalesEmployeeName;
            
            public String EmployeeID;
            public String userName;
            public String password;
            public String firstName;
            public String middleName;
            public String lastName;
            
            public String Email;
            
            public String Mobile;
            
            public String CountryCode;
            public String position;
            public String branch;
            
            public String Active;
            public String salesUnit;
            public String passwordUpdatedOn;
            public String lastLoginOn;
            public String logedIn;
            public String reportingTo;
            
            public String FCM;
            public String div;
            public int level;
            public String timestamp;
            public String zone;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCompanyID() {
                return companyID;
            }

            public void setCompanyID(String companyID) {
                this.companyID = companyID;
            }

            public String getSalesEmployeeCode() {
                return SalesEmployeeCode;
            }

            public void setSalesEmployeeCode(String salesEmployeeCode) {
                SalesEmployeeCode = salesEmployeeCode;
            }

            public String getSalesEmployeeName() {
                return SalesEmployeeName;
            }

            public void setSalesEmployeeName(String salesEmployeeName) {
                SalesEmployeeName = salesEmployeeName;
            }

            public String getEmployeeID() {
                return EmployeeID;
            }

            public void setEmployeeID(String employeeID) {
                EmployeeID = employeeID;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
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

            public String getEmail() {
                return Email;
            }

            public void setEmail(String email) {
                Email = email;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String mobile) {
                Mobile = mobile;
            }

            public String getCountryCode() {
                return CountryCode;
            }

            public void setCountryCode(String countryCode) {
                CountryCode = countryCode;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getBranch() {
                return branch;
            }

            public void setBranch(String branch) {
                this.branch = branch;
            }

            public String getActive() {
                return Active;
            }

            public void setActive(String active) {
                Active = active;
            }

            public String getSalesUnit() {
                return salesUnit;
            }

            public void setSalesUnit(String salesUnit) {
                this.salesUnit = salesUnit;
            }

            public String getPasswordUpdatedOn() {
                return passwordUpdatedOn;
            }

            public void setPasswordUpdatedOn(String passwordUpdatedOn) {
                this.passwordUpdatedOn = passwordUpdatedOn;
            }

            public String getLastLoginOn() {
                return lastLoginOn;
            }

            public void setLastLoginOn(String lastLoginOn) {
                this.lastLoginOn = lastLoginOn;
            }

            public String getLogedIn() {
                return logedIn;
            }

            public void setLogedIn(String logedIn) {
                this.logedIn = logedIn;
            }

            public String getReportingTo() {
                return reportingTo;
            }

            public void setReportingTo(String reportingTo) {
                this.reportingTo = reportingTo;
            }

            public String getFCM() {
                return FCM;
            }

            public void setFCM(String FCM) {
                this.FCM = FCM;
            }

            public String getDiv() {
                return div;
            }

            public void setDiv(String div) {
                this.div = div;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTaxDate() {
            return TaxDate;
        }

        public void setTaxDate(String taxDate) {
            TaxDate = taxDate;
        }

        public String getDocDueDate() {
            return DocDueDate;
        }

        public void setDocDueDate(String docDueDate) {
            DocDueDate = docDueDate;
        }

        public ArrayList<ContactEmployees> getContactPersonCode() {
            return ContactPersonCode;
        }

        public void setContactPersonCode(ArrayList<ContactEmployees> contactPersonCode) {
            ContactPersonCode = contactPersonCode;
        }

        public String getDiscountPercent() {
            return DiscountPercent;
        }

        public void setDiscountPercent(String discountPercent) {
            DiscountPercent = discountPercent;
        }

        public String getDocDate() {
            return DocDate;
        }

        public void setDocDate(String docDate) {
            DocDate = docDate;
        }

        public String getCardCode() {
            return CardCode;
        }

        public void setCardCode(String cardCode) {
            CardCode = cardCode;
        }

        public String getComments() {
            return Comments;
        }

        public void setComments(String comments) {
            Comments = comments;
        }

        public ArrayList<SalesPersonCodes> getSalesPersonCode() {
            return SalesPersonCode;
        }

        public void setSalesPersonCode(ArrayList<SalesPersonCodes> salesPersonCode) {
            SalesPersonCode = salesPersonCode;
        }

        public String getDocumentStatus() {
            return DocumentStatus;
        }

        public void setDocumentStatus(String documentStatus) {
            DocumentStatus = documentStatus;
        }

        public String getCancelStatus() {
            return CancelStatus;
        }

        public void setCancelStatus(String cancelStatus) {
            CancelStatus = cancelStatus;
        }

        public String getDocCurrency() {
            return DocCurrency;
        }

        public void setDocCurrency(String docCurrency) {
            DocCurrency = docCurrency;
        }

        public String getDocTotal() {
            return DocTotal;
        }

        public void setDocTotal(String docTotal) {
            DocTotal = docTotal;
        }

        public String getCardName() {
            return CardName;
        }

        public void setCardName(String cardName) {
            CardName = cardName;
        }

        public String getVatSum() {
            return VatSum;
        }

        public void setVatSum(String vatSum) {
            VatSum = vatSum;
        }

        public String getCreationDate() {
            return CreationDate;
        }

        public void setCreationDate(String creationDate) {
            CreationDate = creationDate;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
        }

        public String getDocEntry() {
            return DocEntry;
        }

        public void setDocEntry(String docEntry) {
            DocEntry = docEntry;
        }

        public String getPaymentGroupCode() {
            return PaymentGroupCode;
        }

        public void setPaymentGroupCode(String paymentGroupCode) {
            PaymentGroupCode = paymentGroupCode;
        }

        public String getU_Term_Condition() {
            return U_Term_Condition;
        }

        public void setU_Term_Condition(String u_Term_Condition) {
            U_Term_Condition = u_Term_Condition;
        }

        public String getU_TermInterestRate() {
            return U_TermInterestRate;
        }

        public void setU_TermInterestRate(String u_TermInterestRate) {
            U_TermInterestRate = u_TermInterestRate;
        }

        public String getU_TermPaymentTerm() {
            return U_TermPaymentTerm;
        }

        public void setU_TermPaymentTerm(String u_TermPaymentTerm) {
            U_TermPaymentTerm = u_TermPaymentTerm;
        }

        public String getU_TermDueDate() {
            return U_TermDueDate;
        }

        public void setU_TermDueDate(String u_TermDueDate) {
            U_TermDueDate = u_TermDueDate;
        }

        public String getU_QUOTNM() {
            return U_QUOTNM;
        }

        public void setU_QUOTNM(String u_QUOTNM) {
            U_QUOTNM = u_QUOTNM;
        }

        public String getU_QUOTID() {
            return U_QUOTID;
        }

        public void setU_QUOTID(String u_QUOTID) {
            U_QUOTID = u_QUOTID;
        }

        public int getU_LEADID() {
            return U_LEADID;
        }

        public void setU_LEADID(int u_LEADID) {
            U_LEADID = u_LEADID;
        }

        public String getU_LEADNM() {
            return U_LEADNM;
        }

        public void setU_LEADNM(String u_LEADNM) {
            U_LEADNM = u_LEADNM;
        }

        public String getU_OPPID() {
            return U_OPPID;
        }

        public void setU_OPPID(String u_OPPID) {
            U_OPPID = u_OPPID;
        }

        public String getU_OPPRNM() {
            return U_OPPRNM;
        }

        public void setU_OPPRNM(String u_OPPRNM) {
            U_OPPRNM = u_OPPRNM;
        }

        public String getBPLID() {
            return BPLID;
        }

        public void setBPLID(String BPLID) {
            this.BPLID = BPLID;
        }

        public String getDelStatus() {
            return DelStatus;
        }

        public void setDelStatus(String delStatus) {
            DelStatus = delStatus;
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

        public ArrayList<AttachDocument> getAttach() {
            return Attach;
        }

        public void setAttach(ArrayList<AttachDocument> attach) {
            Attach = attach;
        }

        public String getCaption() {
            return Caption;
        }

        public void setCaption(String caption) {
            Caption = caption;
        }

        public String getOrdLevel1Status() {
            return OrdLevel1Status;
        }

        public void setOrdLevel1Status(String ordLevel1Status) {
            OrdLevel1Status = ordLevel1Status;
        }

        public String getOrdLevel2Status() {
            return OrdLevel2Status;
        }

        public void setOrdLevel2Status(String ordLevel2Status) {
            OrdLevel2Status = ordLevel2Status;
        }

        public String getOrdLevel3Status() {
            return OrdLevel3Status;
        }

        public void setOrdLevel3Status(String ordLevel3Status) {
            OrdLevel3Status = ordLevel3Status;
        }

        public String getFinalStatus() {
            return FinalStatus;
        }

        public void setFinalStatus(String finalStatus) {
            FinalStatus = finalStatus;
        }

        public String getPoNo() {
            return PoNo;
        }

        public void setPoNo(String poNo) {
            PoNo = poNo;
        }

        public String getPoAmt() {
            return PoAmt;
        }

        public void setPoAmt(String poAmt) {
            PoAmt = poAmt;
        }

        public String getPoDate() {
            return PoDate;
        }

        public void setPoDate(String poDate) {
            PoDate = poDate;
        }

        public String getPRID() {
            return PRID;
        }

        public void setPRID(String PRID) {
            this.PRID = PRID;
        }

        public String getShippingAndHandling() {
            return ShippingAndHandling;
        }

        public void setShippingAndHandling(String shippingAndHandling) {
            ShippingAndHandling = shippingAndHandling;
        }

        public String getTermsAndConditions() {
            return TermsAndConditions;
        }

        public void setTermsAndConditions(String termsAndConditions) {
            TermsAndConditions = termsAndConditions;
        }

        public String getU_Pay1() {
            return U_Pay1;
        }

        public void setU_Pay1(String u_Pay1) {
            U_Pay1 = u_Pay1;
        }

        public String getU_Pay2() {
            return U_Pay2;
        }

        public void setU_Pay2(String u_Pay2) {
            U_Pay2 = u_Pay2;
        }

        public String getU_Pay3() {
            return U_Pay3;
        }

        public void setU_Pay3(String u_Pay3) {
            U_Pay3 = u_Pay3;
        }

        public String getU_Pay4() {
            return U_Pay4;
        }

        public void setU_Pay4(String u_Pay4) {
            U_Pay4 = u_Pay4;
        }

        public String getU_Pay5() {
            return U_Pay5;
        }

        public void setU_Pay5(String u_Pay5) {
            U_Pay5 = u_Pay5;
        }

        public String getDepName() {
            return DepName;
        }

        public void setDepName(String depName) {
            DepName = depName;
        }

        public String getSystemName() {
            return SystemName;
        }

        public void setSystemName(String systemName) {
            SystemName = systemName;
        }

        public String getTaxPercentage() {
            return TaxPercentage;
        }

        public void setTaxPercentage(String taxPercentage) {
            TaxPercentage = taxPercentage;
        }

        public int getU_ORDRID() {
            return U_ORDRID;
        }

        public void setU_ORDRID(int u_ORDRID) {
            U_ORDRID = u_ORDRID;
        }

        public String getU_ORDRNM() {
            return U_ORDRNM;
        }

        public void setU_ORDRNM(String u_ORDRNM) {
            U_ORDRNM = u_ORDRNM;
        }

        public String getPayToCode() {
            return PayToCode;
        }

        public void setPayToCode(String payToCode) {
            PayToCode = payToCode;
        }

        public String getShipToCode() {
            return ShipToCode;
        }

        public void setShipToCode(String shipToCode) {
            ShipToCode = shipToCode;
        }

        public Object getOrdLevel1() {
            return OrdLevel1;
        }

        public void setOrdLevel1(Object ordLevel1) {
            OrdLevel1 = ordLevel1;
        }

        public Object getOrdLevel2() {
            return OrdLevel2;
        }

        public void setOrdLevel2(Object ordLevel2) {
            OrdLevel2 = ordLevel2;
        }

        public Object getOrdLevel3() {
            return OrdLevel3;
        }

        public void setOrdLevel3(Object ordLevel3) {
            OrdLevel3 = ordLevel3;
        }

        public AddressExtensions getAddressExtension() {
            return AddressExtension;
        }

        public void setAddressExtension(AddressExtensions addressExtension) {
            AddressExtension = addressExtension;
        }

        public ArrayList<DocumentLines> getDocumentLines() {
            return DocumentLines;
        }

        public void setDocumentLines(ArrayList<DocumentLines> documentLines) {
            DocumentLines = documentLines;
        }

        public String getBPEmail() {
            return BPEmail;
        }

        public void setBPEmail(String BPEmail) {
            this.BPEmail = BPEmail;
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
