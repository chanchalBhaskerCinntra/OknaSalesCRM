package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.LeadTypeData;


@Database(entities = {LeadTypeData.class},version = 1,exportSchema = false)
public abstract class LeadSourceDatabase extends RoomDatabase {
    public abstract LeadSourceDao myDataDao();
    private static volatile LeadSourceDatabase leadTypeDatabase;

     public static LeadSourceDatabase getDatabase(final Context context) {
        if (leadTypeDatabase == null) {
            synchronized (LeadSourceDatabase.class) {
                if (leadTypeDatabase == null) {
                    leadTypeDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    LeadSourceDatabase.class, "my-db-lead_source")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return leadTypeDatabase;
    }

}
