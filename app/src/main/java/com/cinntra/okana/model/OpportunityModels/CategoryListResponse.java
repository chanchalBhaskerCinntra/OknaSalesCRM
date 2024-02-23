package com.cinntra.okana.model.OpportunityModels;

import java.util.ArrayList;

public class CategoryListResponse {
    public String message;
    public int status;
    public int count;
    public ArrayList<Data> data = new ArrayList();

    public class Data{
        public int id;
        public int UnitPrice;
        public String Currency;
        public int DiscountPercent;
        public String ItemCode;
        public String ItemName;
        public String TaxCode;
        public String U_DIV;
        public String System;
        public String IsService;
        public int InStock;
        public int U_TAX;
        public String ManageSerialNumbers;
        public String ItemsGroupCode;
        public String CategoryName;
        public int TaxRate;
        
        public String Number;
        public String GroupName;
        public Boolean isIsselected=false;

        public Boolean getIsselected() {
            return isIsselected;
        }

        public void setIsselected(Boolean isselected) {
           this. isIsselected = isselected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String number) {
            Number = number;
        }

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String groupName) {
            GroupName = groupName;
        }

        public String getIsService() {
            return IsService;
        }

        public void setIsService(String isService) {
            IsService = isService;
        }

        public int getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            UnitPrice = unitPrice;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String currency) {
            Currency = currency;
        }

        public int getDiscountPercent() {
            return DiscountPercent;
        }

        public void setDiscountPercent(int discountPercent) {
            DiscountPercent = discountPercent;
        }

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String itemCode) {
            ItemCode = itemCode;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public String getTaxCode() {
            return TaxCode;
        }

        public void setTaxCode(String taxCode) {
            TaxCode = taxCode;
        }

        public String getU_DIV() {
            return U_DIV;
        }

        public void setU_DIV(String u_DIV) {
            U_DIV = u_DIV;
        }

        public String getSystem() {
            return System;
        }

        public void setSystem(String system) {
            System = system;
        }

        public int getInStock() {
            return InStock;
        }

        public void setInStock(int inStock) {
            InStock = inStock;
        }

        public int getU_TAX() {
            return U_TAX;
        }

        public void setU_TAX(int u_TAX) {
            U_TAX = u_TAX;
        }

        public String getManageSerialNumbers() {
            return ManageSerialNumbers;
        }

        public void setManageSerialNumbers(String manageSerialNumbers) {
            ManageSerialNumbers = manageSerialNumbers;
        }

        public String getItemsGroupCode() {
            return ItemsGroupCode;
        }

        public void setItemsGroupCode(String itemsGroupCode) {
            ItemsGroupCode = itemsGroupCode;
        }

        public String getCategoryName() {
            return CategoryName;
        }

        public void setCategoryName(String categoryName) {
            CategoryName = categoryName;
        }

        public int getTaxRate() {
            return TaxRate;
        }

        public void setTaxRate(int taxRate) {
            TaxRate = taxRate;
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
