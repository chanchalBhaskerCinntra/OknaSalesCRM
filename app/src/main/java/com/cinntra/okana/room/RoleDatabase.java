package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.Role;


@Database(entities = {Role.class},version = 1,exportSchema = false)
public abstract class RoleDatabase extends RoomDatabase {
    public abstract RoleDao myDataDao();
    private static volatile RoleDatabase roleDatabase;

     public static RoleDatabase getDatabase(final Context context) {
        if (roleDatabase == null) {
            synchronized (RoleDatabase.class) {
                if (roleDatabase == null) {
                    roleDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    RoleDatabase.class, "my-db-role")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return roleDatabase;
    }

}
