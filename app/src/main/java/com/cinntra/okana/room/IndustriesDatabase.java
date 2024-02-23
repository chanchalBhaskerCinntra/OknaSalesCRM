package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.IndustryItem;

@Database(entities = {IndustryItem.class},version = 1,exportSchema = false)
public abstract class IndustriesDatabase extends RoomDatabase {

public abstract IndustriesDao industriesDao();

    private static volatile IndustriesDatabase industriesDatabase;

    public static IndustriesDatabase getDatabase(final Context context) {
        if (industriesDatabase == null) {
            synchronized (CountriesDatabase.class) {
                if (industriesDatabase == null) {
                    industriesDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    IndustriesDatabase.class, "my-db-industries")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return industriesDatabase;
    }


}
