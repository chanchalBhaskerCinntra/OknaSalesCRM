package com.cinntra.okana.model.orderModels;


import com.cinntra.okana.model.AddressExt;
import com.cinntra.okana.model.ContactEmployees;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.newapimodel.AttachDocument;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderListModel implements Serializable {
    @SerializedName("data")
    ArrayList<Data> value;
    @SerializedName("error")
    Error error;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<Data> getValue() {
        return value;
    }

    public void setValue(ArrayList<Data> value) {
        this.value = value;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Data implements Serializable {
        @SerializedName("DocEntry")
        @Expose
        String DocEntry;
        @SerializedName("qt_Id")
        @Expose
        String qt_Id;
        @SerializedName("DocType")
        String DocType;
        @SerializedName("DocDate")
        String DocDate;
        @SerializedName("DocDueDate")
        String DocDueDate;
        @SerializedName("CardCode")
        String CardCode;
        @SerializedName("CardName")
        String CardName;
        @SerializedName("Address2")
        String Address;
        @SerializedName("DocTotal")
        String DocTotal;
        String NetTotal;
        @SerializedName("DocCurrency")
        String DocCurrency;
        @SerializedName("DocRate")
        String DocRate;
        @SerializedName("Comments")
        String Comments;
        @SerializedName("JournalMemo")
        String JournalMemo;
        @SerializedName("DocTime")
        String DocTime;
        @SerializedName("Series")
        String Series;
        @SerializedName("TaxDate")
        String TaxDate;
        @SerializedName("CreationDate")
        String CreationDate;
        @SerializedName("UpdateDate")
        String UpdateDate;
        @SerializedName("VatSumSys")
        String VatSumSys;
        @SerializedName("DocTotalSys")
        String DocTotalSys;
        @SerializedName("PaymentMethod")
        String PaymentMethod;
        @SerializedName("ControlAccount")
        String ControlAccount;

        @SerializedName("DiscountPercent")
        String DiscountPercent;
        @SerializedName("VatSum")
        String VatSum;

        @SerializedName("DownPaymentStatus")
        String DownPaymentStatus;

        @SerializedName("DocumentLines")
        ArrayList<com.cinntra.okana.model.DocumentLines> DocumentLines;

        @SerializedName("DocumentStatus")
        String DocumentStatus;
        @SerializedName("TotalDiscount")
        String TotalDiscount;
        @SerializedName("TotalEqualizationTax")
        // @SerializedName("TaxTotal")
        String TotalEqualizationTax;
        @SerializedName("RoundingDiffAmount")
        String RoundingDiffAmount;
        @SerializedName("SeriesString")
        String SeriesString;
        @SerializedName("DocNum")
        String DocNum;
        @SerializedName("BPLName")
        String BPLName;
        @SerializedName("ShipToDescription")
        String ShipToDescription;
        @SerializedName("DownPaymentType")
        String DownPaymentType;
        @SerializedName("ContactPersonCode")
        @Expose
        List<ContactEmployees> ContactPersonCode;
//        String ContactPersonCode;
        @SerializedName("U_OPPRNM")
        @Expose
        String OpportunityName;

        @SerializedName("SalesPersonCode")
        @Expose
//        String SalesPersonCode;
        List<SalesEmployeeItem> SalesPersonCode;

        @SerializedName("U_FAV")
        @Expose
        String U_FAV;

        @SerializedName("U_OPPID")
        @Expose
        String U_OPPID;

        @SerializedName("CreateDate")
        @Expose
        String CreateDate;
        @SerializedName("CreateTime")
        @Expose
        String CreateTime;

        @SerializedName("UpdateTime")
        @Expose
        String UpdateTime;
        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("U_QUOTNM")
        @Expose
        String U_QUOTNM;
        @SerializedName("U_PREQUOTATION")
        @Expose
        String U_PREQUOTATION;
        @SerializedName("U_PREQTNM")
        @Expose
        String U_PREQTNM;
        @SerializedName("U_Term_Condition")
        @Expose
        String U_Term_Condition;
        @SerializedName("U_TermInterestRate")
        @Expose
        String U_TermInterestRate;
        @SerializedName("U_TermDueDate")
        @Expose
        String U_TermDueDate;

        @SerializedName("AddressExtension")
        @Expose
        AddressExt addressExtension;

        @SerializedName("U_LEADID")
        @Expose
        String U_LEADID;
        @SerializedName("U_LEADNM")
        @Expose
        String U_LEADNM;
        @SerializedName("PaymentGroupCode")
        @Expose
        String PaymentGroupCode;
        @SerializedName("BPLID")
        @Expose
        String BPLID;
        @SerializedName("U_TermPaymentTerm")
        @Expose
        String U_TermPaymentTerm;
        @SerializedName("CreatedBy")
        @Expose
        String CreatedBy;
        @SerializedName("PoNo")
        @Expose
        String PoNo;
        @SerializedName("PoAmt")
        @Expose
        String PoAmt;
        @SerializedName("PoDate")
        @Expose
        String PoDate;
        @SerializedName("PRID")
        @Expose
        String PRID;
        @SerializedName("DepName")
        @Expose
        String DepName;
        @SerializedName("U_Pay1")
        @Expose
        String U_Pay1;
        @SerializedName("U_Pay2")
        @Expose
        String U_Pay2;
        @SerializedName("U_Pay3")
        @Expose
        String U_Pay3;
        @SerializedName("U_Pay4")
        @Expose
        String U_Pay4;
        @SerializedName("U_Pay5")
        @Expose
        String U_Pay5;
        @SerializedName("ShippingAndHandling")
        @Expose
        String ShippingAndHandling;
        @SerializedName("TermsAndConditions")
        @Expose
        String TermsAndConditions;
        @SerializedName("APPROVEID")
        @Expose
        String APPROVEID;
        @SerializedName("FinalStatus")
        @Expose
        String FinalStatus;
        @SerializedName("CancelStatus")
        @Expose
        String CancelStatus;

        @SerializedName("PayToCode")
        @Expose
        String PayToCode;
        @SerializedName("ShipToCode")
        @Expose
        String ShipToCode;
        String U_MR_NO;
        @SerializedName("Template")
        @Expose
        String Template;
        @SerializedName("TicketNo")
        @Expose
        String TicketNo;

        public String Level1;

        public String Level1Status;

        public String Level2;
        public String Level2Status;


        public String Level3;
        public String Level3Status;

        public String U_APPROVENM;
        public String TypeOfQutation;
        public String TicketStatus;
        public String SystemName;
        public String BillToID;
        public String ShipToID;
        public String RefKey;
        public ArrayList<AttachDocument> Attach = new ArrayList<>();


        public ArrayList<QuotAttachment> QuotAttachments;


        public String getNetTotal() {
            return NetTotal;
        }

        public void setNetTotal(String netTotal) {
            NetTotal = netTotal;
        }

        public class QuotAttachment implements Serializable {
            public int id;

            public String QuotationID;

            public String File;

            public String Datetime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getQuotationID() {
                return QuotationID;
            }

            public void setQuotationID(String quotationID) {
                QuotationID = quotationID;
            }

            public String getFile() {
                return File;
            }

            public void setFile(String file) {
                File = file;
            }

            public String getDatetime() {
                return Datetime;
            }

            public void setDatetime(String datetime) {
                Datetime = datetime;
            }
        }


        public List<ContactEmployees> getContactPersonCode() {
            return ContactPersonCode;
        }

        public void setContactPersonCode(List<ContactEmployees> contactPersonCode) {
            ContactPersonCode = contactPersonCode;
        }

        public List<SalesEmployeeItem> getSalesPersonCode() {
            return SalesPersonCode;
        }

        public void setSalesPersonCode(List<SalesEmployeeItem> salesPersonCode) {
            SalesPersonCode = salesPersonCode;
        }

        public String getU_MR_NO() {
            return U_MR_NO;
        }

        public void setU_MR_NO(String u_MR_NO) {
            U_MR_NO = u_MR_NO;
        }

        public ArrayList<AttachDocument> getAttach() {
            return Attach;
        }

        public void setAttach(ArrayList<AttachDocument> attach) {
            Attach = attach;
        }

        public String getRefKey() {
            return RefKey;
        }

        public void setRefKey(String refKey) {
            RefKey = refKey;
        }

        public String getBillToID() {
            return BillToID;
        }

        public void setBillToID(String billToID) {
            BillToID = billToID;
        }

        public String getShipToID() {
            return ShipToID;
        }

        public void setShipToID(String shipToID) {
            ShipToID = shipToID;
        }

        public String getSystemName() {
            return SystemName;
        }

        public void setSystemName(String systemName) {
            SystemName = systemName;
        }

        public String getTypeOfQutation() {
            return TypeOfQutation;
        }

        public void setTypeOfQutation(String typeOfQutation) {
            TypeOfQutation = typeOfQutation;
        }

        public String getTicketStatus() {
            return TicketStatus;
        }

        public void setTicketStatus(String ticketStatus) {
            TicketStatus = ticketStatus;
        }

        public String getTicketNo() {
            return TicketNo;
        }

        public void setTicketNo(String ticketNo) {
            TicketNo = ticketNo;
        }

        public String getTemplate() {
            return Template;
        }

        public void setTemplate(String template) {
            Template = template;
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

        public String getCancelStatus() {
            return CancelStatus;
        }

        public void setCancelStatus(String cancelStatus) {
            CancelStatus = cancelStatus;
        }

        public String getLevel1() {
            return Level1;
        }

        public void setLevel1(String level1) {
            Level1 = level1;
        }

        public String getLevel1Status() {
            return Level1Status;
        }

        public void setLevel1Status(String level1Status) {
            Level1Status = level1Status;
        }

        public String getLevel2() {
            return Level2;
        }

        public void setLevel2(String level2) {
            Level2 = level2;
        }

        public String getLevel2Status() {
            return Level2Status;
        }

        public void setLevel2Status(String level2Status) {
            Level2Status = level2Status;
        }

        public String getLevel3() {
            return Level3;
        }

        public void setLevel3(String level3) {
            Level3 = level3;
        }

        public String getLevel3Status() {
            return Level3Status;
        }

        public void setLevel3Status(String level3Status) {
            Level3Status = level3Status;
        }

        public String getU_APPROVENM() {
            return U_APPROVENM;
        }

        public void setU_APPROVENM(String u_APPROVENM) {
            U_APPROVENM = u_APPROVENM;
        }

        public ArrayList<QuotAttachment> getQuotAttachments() {
            return QuotAttachments;
        }

        public void setQuotAttachments(ArrayList<QuotAttachment> quotAttachments) {
            QuotAttachments = quotAttachments;
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

        public String getAPPROVEID() {
            return APPROVEID;
        }

        public void setAPPROVEID(String APPROVEID) {
            this.APPROVEID = APPROVEID;
        }

        public String getFinalStatus() {
            return FinalStatus;
        }

        public void setFinalStatus(String finalStatus) {
            FinalStatus = finalStatus;
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

        public String getPaymentGroupCode() {
            return PaymentGroupCode;
        }

        public void setPaymentGroupCode(String paymentGroupCode) {
            PaymentGroupCode = paymentGroupCode;
        }

        public String getBPLID() {
            return BPLID;
        }

        public void setBPLID(String BPLID) {
            this.BPLID = BPLID;
        }

        public String getU_TermPaymentTerm() {
            return U_TermPaymentTerm;
        }

        public void setU_TermPaymentTerm(String u_TermPaymentTerm) {
            U_TermPaymentTerm = u_TermPaymentTerm;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
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

        public String getQt_Id() {
            return qt_Id;
        }

        public void setQt_Id(String qt_Id) {
            this.qt_Id = qt_Id;
        }

        public String getU_TermDueDate() {
            return U_TermDueDate;
        }

        public void setU_TermDueDate(String u_TermDueDate) {
            U_TermDueDate = u_TermDueDate;
        }

        public String getU_PREQUOTATION() {
            return U_PREQUOTATION;
        }

        public void setU_PREQUOTATION(String u_PREQUOTATION) {
            U_PREQUOTATION = u_PREQUOTATION;
        }

        public String getU_PREQTNM() {
            return U_PREQTNM;
        }

        public void setU_PREQTNM(String u_PREQTNM) {
            U_PREQTNM = u_PREQTNM;
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

        public AddressExt getAddressExtension() {
            return addressExtension;
        }

        public void setAddressExtension(AddressExt addressExtension) {
            this.addressExtension = addressExtension;
        }


        public String getU_OPPID() {
            return U_OPPID;
        }

        public void setU_OPPID(String u_OPPID) {
            U_OPPID = u_OPPID;
        }


        public String getU_FAV() {
            return U_FAV;
        }

        public void setU_FAV(String u_FAV) {
            U_FAV = u_FAV;
        }

        public String getDocEntry() {
            return DocEntry;
        }

        public void setDocEntry(String docEntry) {
            DocEntry = docEntry;
        }

        public String getDownPaymentType() {
            return DownPaymentType;
        }

        public void setDownPaymentType(String downPaymentType) {
            DownPaymentType = downPaymentType;
        }

        public String getShipToDescription() {
            return ShipToDescription;
        }

        public void setShipToDescription(String shipToDescription) {
            ShipToDescription = shipToDescription;
        }

        public String getBPLName() {
            return BPLName;
        }

        public void setBPLName(String BPLName) {
            this.BPLName = BPLName;
        }

        public String getDocNum() {
            return DocNum;
        }

        public void setDocNum(String docNum) {
            DocNum = docNum;
        }

        public String getSeriesString() {
            return SeriesString;
        }

        public void setSeriesString(String seriesString) {
            SeriesString = seriesString;
        }

        public String getRoundingDiffAmount() {
            return RoundingDiffAmount;
        }

        public void setRoundingDiffAmount(String roundingDiffAmount) {
            RoundingDiffAmount = roundingDiffAmount;
        }

        public String getTotalEqualizationTax() {
            return TotalEqualizationTax;
        }

        public void setTotalEqualizationTax(String totalEqualizationTax) {
            TotalEqualizationTax = totalEqualizationTax;
        }

        public String getTotalDiscount() {
            return TotalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            TotalDiscount = totalDiscount;
        }

        public String getDiscountPercent() {
            return DiscountPercent;
        }

        public void setDiscountPercent(String discountPercent) {
            DiscountPercent = discountPercent;
        }


        public String getDownPaymentStatus() {
            return DownPaymentStatus;
        }

        public void setDownPaymentStatus(String downPaymentStatus) {
            DownPaymentStatus = downPaymentStatus;
        }

        public String getDocumentStatus() {
            return DocumentStatus;
        }

        public void setDocumentStatus(String documentStatus) {
            DocumentStatus = documentStatus;
        }

        public String getDocType() {
            return DocType;
        }

        public void setDocType(String docType) {
            DocType = docType;
        }

        public String getDocDate() {
            return DocDate;
        }

        public void setDocDate(String docDate) {
            DocDate = docDate;
        }

        public String getVatSum() {
            return VatSum;
        }

        public void setVatSum(String vatSum) {
            VatSum = vatSum;
        }

        public String getDocDueDate() {
            return DocDueDate;
        }

        public void setDocDueDate(String docDueDate) {
            DocDueDate = docDueDate;
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

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getDocTotal() {
            return DocTotal;
        }

        public void setDocTotal(String docTotal) {
            DocTotal = docTotal;
        }

        public String getDocCurrency() {
            return DocCurrency;
        }

        public void setDocCurrency(String docCurrency) {
            DocCurrency = docCurrency;
        }

        public String getDocRate() {
            return DocRate;
        }

        public void setDocRate(String docRate) {
            DocRate = docRate;
        }

        public String getComments() {
            return Comments;
        }

        public void setComments(String comments) {
            Comments = comments;
        }


        public String getJournalMemo() {
            return JournalMemo;
        }

        public void setJournalMemo(String journalMemo) {
            JournalMemo = journalMemo;
        }

        public String getDocTime() {
            return DocTime;
        }

        public void setDocTime(String docTime) {
            DocTime = docTime;
        }

        public String getSeries() {
            return Series;
        }

        public void setSeries(String series) {
            Series = series;
        }

        public String getTaxDate() {
            return TaxDate;
        }

        public void setTaxDate(String taxDate) {
            TaxDate = taxDate;
        }

        public String getCreationDate() {
            return CreationDate;
        }

        public void setCreationDate(String creationDate) {
            CreationDate = creationDate;
        }

        public String getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(String updateDate) {
            UpdateDate = updateDate;
        }

        public String getVatSumSys() {
            return VatSumSys;
        }

        public void setVatSumSys(String vatSumSys) {
            VatSumSys = vatSumSys;
        }

        public String getDocTotalSys() {
            return DocTotalSys;
        }

        public void setDocTotalSys(String docTotalSys) {
            DocTotalSys = docTotalSys;
        }

        public String getPaymentMethod() {
            return PaymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            PaymentMethod = paymentMethod;
        }

        public String getControlAccount() {
            return ControlAccount;
        }

        public void setControlAccount(String controlAccount) {
            ControlAccount = controlAccount;
        }

        public ArrayList<DocumentLines> getDocumentLines() {
            return DocumentLines;
        }

        public void setDocumentLines(ArrayList<DocumentLines> documentLines) {
            DocumentLines = documentLines;
        }


        public String getOpportunityName() {
            return OpportunityName;
        }

        public void setOpportunityName(String opportunityName) {
            OpportunityName = opportunityName;
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

        public String getUpdateTime() {
            return UpdateTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUpdateTime(String updateTime) {
            UpdateTime = updateTime;


        }

        public String getU_QUOTNM() {
            return U_QUOTNM;
        }

        public void setU_QUOTNM(String u_QUOTNM) {
            U_QUOTNM = u_QUOTNM;
        }
    }

}
