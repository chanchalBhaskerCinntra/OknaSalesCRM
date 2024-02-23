package com.cinntra.okana.model.OpportunityModels;

import java.util.ArrayList;

public class ItemListRequest {
    
    public int PageNo;
    
    public ArrayList<String> System;
    
    public ArrayList<String> ItemsGroupCode = new ArrayList<>();







    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public ArrayList<String> getSystem() {
        return System;
    }

    public void setSystem(ArrayList<String> system) {
        System = system;
    }

    public ArrayList<String> getItemsGroupCode() {
        return ItemsGroupCode;
    }

    public void setItemsGroupCode(ArrayList<String> itemsGroupCode) {
        ItemsGroupCode = itemsGroupCode;
    }
}
