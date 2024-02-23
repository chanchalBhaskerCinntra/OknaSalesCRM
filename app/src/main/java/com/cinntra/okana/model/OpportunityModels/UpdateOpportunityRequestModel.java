package com.cinntra.okana.model.OpportunityModels;

import java.util.ArrayList;

public class UpdateOpportunityRequestModel  {
    public String id;
    public String SequentialNo = "";

    public int U_LEADID = 0;

    public String U_LEADNM = "";

    public String CardCode = "";

    public String SalesPerson = "";

    public String SalesPersonName = "";

    public String ContactPerson = "";

    public String ContactPersonName = "";

    public String Source = "";

    public String StartDate = "";

    public String PredictedClosingDate = "";

    public String MaxLocalTotal = "";

    public String MaxSystemTotal = "";

    public String Remarks = "";

    public String Status = "";

    public String ReasonForClosing = "";

    public String TotalAmountLocal = "";

    public String TotalAmounSystem = "";

    public String CurrentStageNo = "";

    public String CurrentStageNumber = "";

    public String CurrentStageName = "";

    public String OpportunityName = "";

    public String Industry = "";

    public String LinkedDocumentType = "";

    public int DataOwnershipfield = 0;

    public String DataOwnershipName = "";

    public String StatusRemarks = "";

    public String ProjectCode = "";

    public String CustomerName = "";

    public String ClosingDate = "";

    public String ClosingType = "";

    public String OpportunityType = "";

    public String UpdateDate = "";

    public String UpdateTime = "";

    public String U_TYPE = "";

    public String U_LSOURCE = "";

    public String U_FAV = "";

    public String U_PROBLTY = "";

    public ArrayList<SalesOpportunitiesLine> SalesOpportunitiesLines = new ArrayList<>();
    public ArrayList<OppItems> OppItem = new ArrayList<>();

    public static class SalesOpportunitiesLine {
        public String DocumentType = "";
        public String MaxLocalTotal = "";
        public String MaxSystemTotal = "";
        public String SalesPerson = "";
        public String StageKey = "";

        public String getDocumentType() {
            return DocumentType;
        }

        public void setDocumentType(String documentType) {
            DocumentType = documentType;
        }

        public String getMaxLocalTotal() {
            return MaxLocalTotal;
        }

        public void setMaxLocalTotal(String maxLocalTotal) {
            MaxLocalTotal = maxLocalTotal;
        }

        public String getMaxSystemTotal() {
            return MaxSystemTotal;
        }

        public void setMaxSystemTotal(String maxSystemTotal) {
            MaxSystemTotal = maxSystemTotal;
        }

        public String getSalesPerson() {
            return SalesPerson;
        }

        public void setSalesPerson(String salesPerson) {
            SalesPerson = salesPerson;
        }

        public String getStageKey() {
            return StageKey;
        }

        public void setStageKey(String stageKey) {
            StageKey = stageKey;
        }
    }

    public static class OppItems {
        public String id = "";
        public String Oid = "";
        public int Quantity = 0;

        public float UnitPrice =0 ;

        public float DiscountPercent = 0;

        public String ItemCode = "";

        public String ItemDescription = "";

        public String TaxCode = "";

        public String ProjectCode = "";

        public String U_FGITEM = "";

        public String CostingCode2 = "";

        public String FreeText = "";

        public String Tax = "";

        public String UomNo = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOid() {
            return Oid;
        }

        public void setOid(String oid) {
            Oid = oid;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }

        public float getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(float unitPrice) {
            UnitPrice = unitPrice;
        }

        public float getDiscountPercent() {
            return DiscountPercent;
        }

        public void setDiscountPercent(float discountPercent) {
            DiscountPercent = discountPercent;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String itemCode) {
            ItemCode = itemCode;
        }

        public String getItemDescription() {
            return ItemDescription;
        }

        public void setItemDescription(String itemDescription) {
            ItemDescription = itemDescription;
        }

        public String getTaxCode() {
            return TaxCode;
        }

        public void setTaxCode(String taxCode) {
            TaxCode = taxCode;
        }

        public String getProjectCode() {
            return ProjectCode;
        }

        public void setProjectCode(String projectCode) {
            ProjectCode = projectCode;
        }

        public String getU_FGITEM() {
            return U_FGITEM;
        }

        public void setU_FGITEM(String u_FGITEM) {
            U_FGITEM = u_FGITEM;
        }

        public String getCostingCode2() {
            return CostingCode2;
        }

        public void setCostingCode2(String costingCode2) {
            CostingCode2 = costingCode2;
        }

        public String getFreeText() {
            return FreeText;
        }

        public void setFreeText(String freeText) {
            FreeText = freeText;
        }

        public String getTax() {
            return Tax;
        }

        public void setTax(String tax) {
            Tax = tax;
        }

        public String getUomNo() {
            return UomNo;
        }

        public void setUomNo(String uomNo) {
            UomNo = uomNo;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequentialNo() {
        return SequentialNo;
    }

    public void setSequentialNo(String sequentialNo) {
        SequentialNo = sequentialNo;
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

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        SalesPerson = salesPerson;
    }

    public String getSalesPersonName() {
        return SalesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        SalesPersonName = salesPersonName;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getContactPersonName() {
        return ContactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        ContactPersonName = contactPersonName;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getPredictedClosingDate() {
        return PredictedClosingDate;
    }

    public void setPredictedClosingDate(String predictedClosingDate) {
        PredictedClosingDate = predictedClosingDate;
    }

    public String getMaxLocalTotal() {
        return MaxLocalTotal;
    }

    public void setMaxLocalTotal(String maxLocalTotal) {
        MaxLocalTotal = maxLocalTotal;
    }

    public String getMaxSystemTotal() {
        return MaxSystemTotal;
    }

    public void setMaxSystemTotal(String maxSystemTotal) {
        MaxSystemTotal = maxSystemTotal;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReasonForClosing() {
        return ReasonForClosing;
    }

    public void setReasonForClosing(String reasonForClosing) {
        ReasonForClosing = reasonForClosing;
    }

    public String getTotalAmountLocal() {
        return TotalAmountLocal;
    }

    public void setTotalAmountLocal(String totalAmountLocal) {
        TotalAmountLocal = totalAmountLocal;
    }

    public String getTotalAmounSystem() {
        return TotalAmounSystem;
    }

    public void setTotalAmounSystem(String totalAmounSystem) {
        TotalAmounSystem = totalAmounSystem;
    }

    public String getCurrentStageNo() {
        return CurrentStageNo;
    }

    public void setCurrentStageNo(String currentStageNo) {
        CurrentStageNo = currentStageNo;
    }

    public String getCurrentStageNumber() {
        return CurrentStageNumber;
    }

    public void setCurrentStageNumber(String currentStageNumber) {
        CurrentStageNumber = currentStageNumber;
    }

    public String getCurrentStageName() {
        return CurrentStageName;
    }

    public void setCurrentStageName(String currentStageName) {
        CurrentStageName = currentStageName;
    }

    public String getOpportunityName() {
        return OpportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        OpportunityName = opportunityName;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public String getLinkedDocumentType() {
        return LinkedDocumentType;
    }

    public void setLinkedDocumentType(String linkedDocumentType) {
        LinkedDocumentType = linkedDocumentType;
    }

    public int getDataOwnershipfield() {
        return DataOwnershipfield;
    }

    public void setDataOwnershipfield(int dataOwnershipfield) {
        DataOwnershipfield = dataOwnershipfield;
    }

    public String getDataOwnershipName() {
        return DataOwnershipName;
    }

    public void setDataOwnershipName(String dataOwnershipName) {
        DataOwnershipName = dataOwnershipName;
    }

    public String getStatusRemarks() {
        return StatusRemarks;
    }

    public void setStatusRemarks(String statusRemarks) {
        StatusRemarks = statusRemarks;
    }

    public String getProjectCode() {
        return ProjectCode;
    }

    public void setProjectCode(String projectCode) {
        ProjectCode = projectCode;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getClosingDate() {
        return ClosingDate;
    }

    public void setClosingDate(String closingDate) {
        ClosingDate = closingDate;
    }

    public String getClosingType() {
        return ClosingType;
    }

    public void setClosingType(String closingType) {
        ClosingType = closingType;
    }

    public String getOpportunityType() {
        return OpportunityType;
    }

    public void setOpportunityType(String opportunityType) {
        OpportunityType = opportunityType;
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

    public String getU_TYPE() {
        return U_TYPE;
    }

    public void setU_TYPE(String u_TYPE) {
        U_TYPE = u_TYPE;
    }

    public String getU_LSOURCE() {
        return U_LSOURCE;
    }

    public void setU_LSOURCE(String u_LSOURCE) {
        U_LSOURCE = u_LSOURCE;
    }

    public String getU_FAV() {
        return U_FAV;
    }

    public void setU_FAV(String u_FAV) {
        U_FAV = u_FAV;
    }

    public String getU_PROBLTY() {
        return U_PROBLTY;
    }

    public void setU_PROBLTY(String u_PROBLTY) {
        U_PROBLTY = u_PROBLTY;
    }

    public ArrayList<SalesOpportunitiesLine> getSalesOpportunitiesLines() {
        return SalesOpportunitiesLines;
    }

    public void setSalesOpportunitiesLines(ArrayList<SalesOpportunitiesLine> salesOpportunitiesLines) {
        SalesOpportunitiesLines = salesOpportunitiesLines;
    }

    public ArrayList<OppItems> getOppItem() {
        return OppItem;
    }

    public void setOppItem(ArrayList<OppItems> oppItem) {
        OppItem = oppItem;
    }
}

