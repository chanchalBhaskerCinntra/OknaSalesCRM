package com.cinntra.okana.model.expenseModels;


import com.cinntra.okana.newapimodel.AttachDocument;

import java.io.Serializable;
import java.util.ArrayList;

public class ExpenseOneDataResponseModel implements Serializable {

    public String message;
    public int status;
    public ArrayList<Data> data = new ArrayList<>();

    public static class Data implements Serializable{
        public int id;
        public String trip_name;
        public String expense_from;
        public String expense_to;
        public String total_amount;
        public String travel_amount;
        public String stay_amount;
        
        public String DA_amount;
        public String expense_mode;
        public String travel_line;
        public String stay_line;
        
        public String DA_line;
        public String createDate;
        public String createTime;
        public String updateDate;
        public String updateTime;
        public ArrayList<CreatedBy> createdBy;
        public String tripId;
        public String approval_status;
        public String approved_by;
        public String type_of_expense;
        public String totalAmount;
        public String remarks;

        public ArrayList<EmployeeId> employeeId;

        public ArrayList<AttachDocument> Attach = new ArrayList<>();


        public class EmployeeId implements Serializable{
            public int id;
            public String firstName;
            public String lastName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }
        }

     /*   public class Attachments{
            public int id;
            
            public String File;
            
            public String LinkType;
            
            public String Caption;
            
            public int LinkID;
            
            public String CreateDate;
            
            public String CreateTime;
            
            public String UpdateDate;
            
            public String UpdateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFile() {
                return File;
            }

            public void setFile(String file) {
                File = file;
            }

            public String getLinkType() {
                return LinkType;
            }

            public void setLinkType(String linkType) {
                LinkType = linkType;
            }

            public String getCaption() {
                return Caption;
            }

            public void setCaption(String caption) {
                Caption = caption;
            }

            public int getLinkID() {
                return LinkID;
            }

            public void setLinkID(int linkID) {
                LinkID = linkID;
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
        }*/


        public String getType_of_expense() {
            return type_of_expense;
        }

        public void setType_of_expense(String type_of_expense) {
            this.type_of_expense = type_of_expense;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public ArrayList<EmployeeId> getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(ArrayList<EmployeeId> employeeId) {
            this.employeeId = employeeId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTrip_name() {
            return trip_name;
        }

        public void setTrip_name(String trip_name) {
            this.trip_name = trip_name;
        }

        public String getExpense_from() {
            return expense_from;
        }

        public void setExpense_from(String expense_from) {
            this.expense_from = expense_from;
        }

        public String getExpense_to() {
            return expense_to;
        }

        public void setExpense_to(String expense_to) {
            this.expense_to = expense_to;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getTravel_amount() {
            return travel_amount;
        }

        public void setTravel_amount(String travel_amount) {
            this.travel_amount = travel_amount;
        }

        public String getStay_amount() {
            return stay_amount;
        }

        public void setStay_amount(String stay_amount) {
            this.stay_amount = stay_amount;
        }

        public String getDA_amount() {
            return DA_amount;
        }

        public void setDA_amount(String DA_amount) {
            this.DA_amount = DA_amount;
        }

        public String getExpense_mode() {
            return expense_mode;
        }

        public void setExpense_mode(String expense_mode) {
            this.expense_mode = expense_mode;
        }

        public String getTravel_line() {
            return travel_line;
        }

        public void setTravel_line(String travel_line) {
            this.travel_line = travel_line;
        }

        public String getStay_line() {
            return stay_line;
        }

        public void setStay_line(String stay_line) {
            this.stay_line = stay_line;
        }

        public String getDA_line() {
            return DA_line;
        }

        public void setDA_line(String DA_line) {
            this.DA_line = DA_line;
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

        public ArrayList<CreatedBy> getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(ArrayList<CreatedBy> createdBy) {
            this.createdBy = createdBy;
        }

        public String getTripId() {
            return tripId;
        }

        public void setTripId(String tripId) {
            this.tripId = tripId;
        }

        public String getApproval_status() {
            return approval_status;
        }

        public void setApproval_status(String approval_status) {
            this.approval_status = approval_status;
        }

        public String getApproved_by() {
            return approved_by;
        }

        public void setApproved_by(String approved_by) {
            this.approved_by = approved_by;
        }

        public ArrayList<AttachDocument> getAttach() {
            return Attach;
        }

        public void setAttach(ArrayList<AttachDocument> attach) {
            Attach = attach;
        }
    }

    public class CreatedBy implements Serializable{
        public int id;
        public String firstName;
        public String lastName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
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
