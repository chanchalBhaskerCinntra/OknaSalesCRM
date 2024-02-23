package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.TaxItem;


@Database(entities = {TaxItem.class},version = 1,exportSchema = false)
public abstract class TaxItemDatabase extends RoomDatabase {
    public abstract TaxItemDao myDataDao();
    private static volatile TaxItemDatabase taxItemDatabase;

     public static TaxItemDatabase getDatabase(final Context context) {
        if (taxItemDatabase == null) {
            synchronized (TaxItemDatabase.class) {
                if (taxItemDatabase == null) {
                    taxItemDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    TaxItemDatabase.class, "my-db-tax_item")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return taxItemDatabase;
    }

}
