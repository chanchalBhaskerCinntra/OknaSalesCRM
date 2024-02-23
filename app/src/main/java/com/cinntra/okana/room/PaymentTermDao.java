package com.cinntra.okana.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cinntra.okana.model.PayMentTerm;

import java.util.List;

@Dao
public interface PaymentTermDao {
    @Query("SELECT * FROM table_payment_term")
    List<PayMentTerm> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PayMentTerm> data);



}
