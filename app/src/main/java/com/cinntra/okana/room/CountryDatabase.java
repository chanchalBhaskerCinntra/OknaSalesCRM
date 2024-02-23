package com.cinntra.okana.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.Countries;

@Database(entities = {Countries.class},version = 1)
public abstract class CountryDatabase extends RoomDatabase {
    public abstract CountryDao myDataDao();
}
