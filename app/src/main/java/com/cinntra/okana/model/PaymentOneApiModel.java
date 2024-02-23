package com.cinntra.okana.model;

import com.cinntra.okana.newapimodel.AttachDocument;

import java.util.List;

public class PaymentOneApiModel {
    private String message;
    private Long status;
    private List<DataValue> data;


    public class DataValue {
        private Long id;
        
        private String InvoiceNo;
        
        private String TransactId;
        
        private String TotalAmt;
        
        private String TransactMod;
        
        private String DueAmount;
        
        private String PaymentDate;
        
        private String ReceivedAmount;
        
        private String Remarks;
        private String createDate;
        private String createTime;
        private List<CreatedBy> createdBy;
        private String updateDate;
        private String updateTime;
        private List<?> updatedBy;
        private String CardCode;
        private List<AttachDocument> Attach;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getInvoiceNo() {
            return InvoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            InvoiceNo = invoiceNo;
        }

        public String getTransactId() {
            return TransactId;
        }

        public void setTransactId(String transactId) {
            TransactId = transactId;
        }

        public String getTotalAmt() {
            return TotalAmt;
        }

        public void setTotalAmt(String totalAmt) {
            TotalAmt = totalAmt;
        }

        public String getTransactMod() {
            return TransactMod;
        }

        public void setTransactMod(String transactMod) {
            TransactMod = transactMod;
        }

        public String getDueAmount() {
            return DueAmount;
        }

        public void setDueAmount(String dueAmount) {
            DueAmount = dueAmount;
        }

        public String getPaymentDate() {
            return PaymentDate;
        }

        public void setPaymentDate(String paymentDate) {
            PaymentDate = paymentDate;
        }

        public String getReceivedAmount() {
            return ReceivedAmount;
        }

        public void setReceivedAmount(String receivedAmount) {
            ReceivedAmount = receivedAmount;
        }

        public String getRemarks() {
            return Remarks;
        }

        public void setRemarks(String remarks) {
            Remarks = remarks;
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

        public List<CreatedBy> getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(List<CreatedBy> createdBy) {
            this.createdBy = createdBy;
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

        public List<?> getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(List<?> updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getCardCode() {
            return CardCode;
        }

        public void setCardCode(String cardCode) {
            CardCode = cardCode;
        }

        public List<AttachDocument> getAttach() {
            return Attach;
        }

        public void setAttach(List<AttachDocument> attach) {
            Attach = attach;
        }
    }


    public class CreatedBy {
        
        private String FirstName;
        
        private String MiddleName;
        
        private String LastName;

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
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<DataValue> getData() {
        return data;
    }

    public void setData(List<DataValue> data) {
        this.data = data;
    }


}
