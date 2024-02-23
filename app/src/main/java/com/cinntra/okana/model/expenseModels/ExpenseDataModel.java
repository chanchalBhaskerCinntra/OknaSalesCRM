package com.cinntra.okana.model.expenseModels;


import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.newapimodel.AttachDocument;
import com.cinntra.okana.newapimodel.EmployeeId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpenseDataModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("trip_name")
    @Expose
    private String tripName;
    @SerializedName("type_of_expense")
    @Expose
    private String typeOfExpense;
    @SerializedName("expense_from")
    @Expose
    private String expenseFrom;
    @SerializedName("expense_to")
    @Expose
    private String expenseTo;
    @SerializedName("totalAmount")
    @Expose
    private int totalAmount;
    @SerializedName("travel_amount")
    @Expose
    private int travel_amount;
    @SerializedName("stay_amount")
    @Expose
    private int stay_amount;
    @SerializedName("DA_amount")
    @Expose
    private int DA_amount;
    @SerializedName("expense_mode")
    @Expose
    private String expense_mode;
    @SerializedName("travel_line")
    @Expose
    private String travel_line;
    @SerializedName("stay_line")
    @Expose
    private String stay_line;
    @SerializedName("DA_line")
    @Expose
    private String DA_line;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("createTime")
    @Expose
    private String createTime;
    @SerializedName("createdBy")
    @Expose
    private List<EmployeeValue> createdBy = null;
    @SerializedName("updateDate")
    @Expose
    private String updateDate;
    @SerializedName("updateTime")
    @Expose
    private String updateTime;
   /* @SerializedName("updatedBy")
    @Expose
    private List<EmployeeValue> updatedBy = null;*/
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("employeeId")
    @Expose
    private List<EmployeeId> employeeId = null;
    @SerializedName("Attach")
    @Expose
    private List<AttachDocument> attach = null;

    public int getTravel_amount() {
        return travel_amount;
    }

    public void setTravel_amount(int travel_amount) {
        this.travel_amount = travel_amount;
    }

    public int getStay_amount() {
        return stay_amount;
    }

    public void setStay_amount(int stay_amount) {
        this.stay_amount = stay_amount;
    }

    public int getDA_amount() {
        return DA_amount;
    }

    public void setDA_amount(int DA_amount) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTypeOfExpense() {
        return typeOfExpense;
    }

    public void setTypeOfExpense(String typeOfExpense) {
        this.typeOfExpense = typeOfExpense;
    }

    public String getExpenseFrom() {
        return expenseFrom;
    }

    public void setExpenseFrom(String expenseFrom) {
        this.expenseFrom = expenseFrom;
    }

    public String getExpenseTo() {
        return expenseTo;
    }

    public void setExpenseTo(String expenseTo) {
        this.expenseTo = expenseTo;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
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

    public List<EmployeeValue> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<EmployeeValue> createdBy) {
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

   /* public List<EmployeeValue> getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(List<EmployeeValue> updatedBy) {
        this.updatedBy = updatedBy;
    }*/

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<EmployeeId> getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(List<EmployeeId> employeeId) {
        this.employeeId = employeeId;
    }

    public List<AttachDocument> getAttach() {
        return attach;
    }

    public void setAttach(List<AttachDocument> attach) {
        this.attach = attach;
    }

}