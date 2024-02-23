package com.cinntra.okana.model;

import com.cinntra.okana.newapimodel.AttachDocument;
import com.cinntra.okana.newapimodel.EmployeeId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpenseNewDataModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Integer totalAmount;
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
    @SerializedName("updatedBy")
    @Expose
    private List<EmployeeValue> updatedBy = null;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("employeeId")
    @Expose
    private List<EmployeeId> employeeId = null;
    @SerializedName("Attach")
    @Expose
    private List<AttachDocument> attach = null;
    private final static long serialVersionUID = -4928717263569093848L;

    /**
     * No args constructor for use in serialization
     *
     */
    public ExpenseNewDataModel() {
    }

    /**
     *
     * @param updateDate
     * @param updatedBy
     * @param typeOfExpense
     * @param expenseFrom
     * @param updateTime
     * @param employeeId
     * @param totalAmount
     * @param createTime
     * @param createdBy
     * @param expenseTo
     * @param tripName
     * @param id
     * @param attach
     * @param remarks
     * @param createDate
     */
    public ExpenseNewDataModel(Integer id, String tripName, String typeOfExpense, String expenseFrom, String expenseTo, Integer totalAmount, String createDate, String createTime, List<EmployeeValue> createdBy, String updateDate, String updateTime, List<EmployeeValue> updatedBy, String remarks, List<EmployeeId> employeeId, List<AttachDocument> attach) {
        super();
        this.id = id;
        this.tripName = tripName;
        this.typeOfExpense = typeOfExpense;
        this.expenseFrom = expenseFrom;
        this.expenseTo = expenseTo;
        this.totalAmount = totalAmount;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createdBy = createdBy;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
        this.updatedBy = updatedBy;
        this.remarks = remarks;
        this.employeeId = employeeId;
        this.attach = attach;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
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

    public List<EmployeeValue> getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(List<EmployeeValue> updatedBy) {
        this.updatedBy = updatedBy;
    }

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