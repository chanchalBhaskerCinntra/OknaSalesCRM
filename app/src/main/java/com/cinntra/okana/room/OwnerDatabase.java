package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.OwnerItem;


@Database(entities = {OwnerItem.class},version = 1,exportSchema = false)
public abstract class OwnerDatabase extends RoomDatabase {
    public abstract OwnerItemDao myDataDao();
    private static volatile OwnerDatabase ownerDatabase;

     public static OwnerDatabase getDatabase(final Context context) {
        if (ownerDatabase == null) {
            synchronized (OwnerDatabase.class) {
                if (ownerDatabase == null) {
                    ownerDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    OwnerDatabase.class, "my-db-owner-item")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return ownerDatabase;
    }

}
