package com.cinntra.okana.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.okana.model.ItemCategoryData;

import java.util.List;

@Dao
public interface ItemCategoryDao {
    @Query("SELECT * FROM table_item_category")
    List<ItemCategoryData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ItemCategoryData> data);



}
