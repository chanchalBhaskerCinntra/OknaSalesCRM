package com.cinntra.okana.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InventoryData implements Serializable
{

    @SerializedName("FastMovingItemsList")
    @Expose
    private List<FastMovingItems> fastMovingItemsList = null;
    @SerializedName("FastItemsCount")
    @Expose
    private Integer fastItemsCount;
    @SerializedName("SlowMovingItemsList")
    @Expose
    private List<FastMovingItems> slowMovingItemsList = null;
    @SerializedName("SlowItemsCount")
    @Expose
    private Integer slowItemsCount;
    @SerializedName("NotMovingItemsList")
    @Expose
    private List<FastMovingItems> notMovingItemsList = null;
    @SerializedName("NotMovingItemsCount")
    @Expose
    private Integer notMovingItemsCount;
    private final static long serialVersionUID = -93163822046969012L;

    /**
     * No args constructor for use in serialization
     *
     */
    public InventoryData() {
    }

    /**
     *
     * @param slowItemsCount
     * @param fastMovingItemsList
     * @param notMovingItemsCount
     * @param notMovingItemsList
     * @param fastItemsCount
     * @param slowMovingItemsList
     */
    public InventoryData(List<FastMovingItems> fastMovingItemsList, Integer fastItemsCount, List<FastMovingItems> slowMovingItemsList, Integer slowItemsCount, List<FastMovingItems> notMovingItemsList, Integer notMovingItemsCount) {
        super();
        this.fastMovingItemsList = fastMovingItemsList;
        this.fastItemsCount = fastItemsCount;
        this.slowMovingItemsList = slowMovingItemsList;
        this.slowItemsCount = slowItemsCount;
        this.notMovingItemsList = notMovingItemsList;
        this.notMovingItemsCount = notMovingItemsCount;
    }

    public List<FastMovingItems> getFastMovingItemsList() {
        return fastMovingItemsList;
    }

    public void setFastMovingItemsList(List<FastMovingItems> fastMovingItemsList) {
        this.fastMovingItemsList = fastMovingItemsList;
    }

    public Integer getFastItemsCount() {
        return fastItemsCount;
    }

    public void setFastItemsCount(Integer fastItemsCount) {
        this.fastItemsCount = fastItemsCount;
    }

    public List<FastMovingItems> getSlowMovingItemsList() {
        return slowMovingItemsList;
    }

    public void setSlowMovingItemsList(List<FastMovingItems> slowMovingItemsList) {
        this.slowMovingItemsList = slowMovingItemsList;
    }

    public Integer getSlowItemsCount() {
        return slowItemsCount;
    }

    public void setSlowItemsCount(Integer slowItemsCount) {
        this.slowItemsCount = slowItemsCount;
    }

    public List<FastMovingItems> getNotMovingItemsList() {
        return notMovingItemsList;
    }

    public void setNotMovingItemsList(List<FastMovingItems> notMovingItemsList) {
        this.notMovingItemsList = notMovingItemsList;
    }

    public Integer getNotMovingItemsCount() {
        return notMovingItemsCount;
    }

    public void setNotMovingItemsCount(Integer notMovingItemsCount) {
        this.notMovingItemsCount = notMovingItemsCount;
    }

}