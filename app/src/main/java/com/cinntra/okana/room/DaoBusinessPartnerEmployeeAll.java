package com.cinntra.okana.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.okana.model.ContactPersonData;

import java.util.List;

@Dao
public interface DaoBusinessPartnerEmployeeAll {
    @Query("SELECT * FROM data_business_partner_employee_all")
    List<ContactPersonData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ContactPersonData> data);


}
