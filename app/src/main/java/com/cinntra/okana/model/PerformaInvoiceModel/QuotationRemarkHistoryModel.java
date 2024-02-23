package com.cinntra.okana.model.PerformaInvoiceModel;

import java.util.ArrayList;

public class QuotationRemarkHistoryModel {
    public String message;
    public int status;
    public ArrayList<Data> data;

    public class Data{
        public int id;
        
        public String QuotationID;
        
        public String SalesEmployeeCode;
        
        public String Status;
        
        public String Remarks;
        
        public String Datetime;
        
        public String EmployeeName;

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

        public String getSalesEmployeeCode() {
            return SalesEmployeeCode;
        }

        public void setSalesEmployeeCode(String salesEmployeeCode) {
            SalesEmployeeCode = salesEmployeeCode;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getRemarks() {
            return Remarks;
        }

        public void setRemarks(String remarks) {
            Remarks = remarks;
        }

        public String getDatetime() {
            return Datetime;
        }

        public void setDatetime(String datetime) {
            Datetime = datetime;
        }

        public String getEmployeeName() {
            return EmployeeName;
        }

        public void setEmployeeName(String employeeName) {
            EmployeeName = employeeName;
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
