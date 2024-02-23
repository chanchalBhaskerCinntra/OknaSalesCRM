package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.LeadTypeData;


@Database(entities = {LeadTypeData.class},version = 1,exportSchema = false)
public abstract class LeadTypeDatabase extends RoomDatabase {
    public abstract LeadTypeDao myDataDao();
    private static volatile LeadTypeDatabase leadTypeDatabase;

     public static LeadTypeDatabase getDatabase(final Context context) {
        if (leadTypeDatabase == null) {
            synchronized (LeadTypeDatabase.class) {
                if (leadTypeDatabase == null) {
                    leadTypeDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    LeadTypeDatabase.class, "my-db-lead_type")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return leadTypeDatabase;
    }

}
