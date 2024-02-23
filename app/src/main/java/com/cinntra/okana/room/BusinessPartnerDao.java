package com.cinntra.okana.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.okana.model.BPModel.BusinessPartnerData;

import java.util.List;

@Dao
public interface BusinessPartnerDao {
    @Query("SELECT * FROM table_bussiness_partner_data")
    List<BusinessPartnerData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BusinessPartnerData> data);



}
