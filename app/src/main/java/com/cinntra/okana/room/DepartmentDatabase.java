package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.DepartMent;


@Database(entities = {DepartMent.class},version = 1,exportSchema = false)
public abstract class DepartmentDatabase extends RoomDatabase {
    public abstract DepartmentDao myDataDao();
    private static volatile DepartmentDatabase departmentDatabase;

     public static DepartmentDatabase getDatabase(final Context context) {
        if (departmentDatabase == null) {
            synchronized (DepartmentDatabase.class) {
                if (departmentDatabase == null) {
                    departmentDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    DepartmentDatabase.class, "my-db-department")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return departmentDatabase;
    }

}
