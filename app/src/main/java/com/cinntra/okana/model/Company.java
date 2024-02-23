package com.cinntra.okana.model;

import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Company extends ExpandableGroup<NewOpportunityRespose> {
    String title;
    List<NewOpportunityRespose> items;
    public Company(String title, List<NewOpportunityRespose> items) {
        super(title, items);
        this.title = title;
        this.items = items;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<NewOpportunityRespose> getItems() {
        return items;
    }

    public void setItems(List<NewOpportunityRespose> items) {
        this.items = items;
    }
}
