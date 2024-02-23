package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.BPModel.BusinessPartnerData;


@Database(entities = {BusinessPartnerData.class},version = 2,exportSchema = false)
public abstract class BusinessPartnerDatabase extends RoomDatabase {
    public abstract BusinessPartnerDao myDataDao();
    private static volatile BusinessPartnerDatabase businessPartnerDatabase;

     public static BusinessPartnerDatabase getDatabase(final Context context) {
        if (businessPartnerDatabase == null) {
            synchronized (BusinessPartnerDatabase.class) {
                if (businessPartnerDatabase == null) {
                    businessPartnerDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    BusinessPartnerDatabase.class, "my-db-business_partner")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return businessPartnerDatabase;
    }

}
