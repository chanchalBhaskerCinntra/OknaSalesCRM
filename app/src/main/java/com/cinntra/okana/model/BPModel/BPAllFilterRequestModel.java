package com.cinntra.okana.model.BPModel;

public class BPAllFilterRequestModel {
    public String SalesPersonCode;
    public int PageNo;
    public int maxItem;
    public String SearchText;
    public Field field = null;

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public int getMaxItem() {
        return maxItem;
    }

    public void setMaxItem(int maxItem) {
        this.maxItem = maxItem;
    }

    public String getSearchText() {
        return SearchText;
    }

    public void setSearchText(String searchText) {
        SearchText = searchText;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public static class Field{
        public String CardType;
        public String Industry;
        public String SalesPersonPerson;

        public String getIndustry() {
            return Industry;
        }

        public void setIndustry(String industry) {
            Industry = industry;
        }

        public String getSalesPersonPerson() {
            return SalesPersonPerson;
        }

        public void setSalesPersonPerson(String salesPersonPerson) {
            SalesPersonPerson = salesPersonPerson;
        }

        public String getCardType() {
            return CardType;
        }

        public void setCardType(String cardType) {
            CardType = cardType;
        }
    }
}
