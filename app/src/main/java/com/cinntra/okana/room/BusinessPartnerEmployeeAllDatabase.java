package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.ContactPersonData;


@Database(entities = {ContactPersonData.class}, version = 1, exportSchema = false)
public abstract class BusinessPartnerEmployeeAllDatabase extends RoomDatabase {

    public abstract DaoBusinessPartnerEmployeeAll myDataDao();

    private static volatile BusinessPartnerEmployeeAllDatabase busiessPartnerEmployeeAllDatabase;

    public static BusinessPartnerEmployeeAllDatabase getDatabase(final Context context) {
        if (busiessPartnerEmployeeAllDatabase == null) {
            synchronized (BusinessPartnerEmployeeAllDatabase.class) {
                if (busiessPartnerEmployeeAllDatabase == null) {
                    busiessPartnerEmployeeAllDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    BusinessPartnerEmployeeAllDatabase.class, "my-db-businesspartner-employee-all")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return busiessPartnerEmployeeAllDatabase;
    }


}
