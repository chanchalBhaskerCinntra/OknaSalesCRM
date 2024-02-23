package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.ItemCategoryData;


@Database(entities = {ItemCategoryData.class},version = 1,exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ItemCategoryDao myDataDao();
    private static volatile ProductDatabase productDatabase;

     public static ProductDatabase getDatabase(final Context context) {
        if (productDatabase == null) {
            synchronized (ProductDatabase.class) {
                if (productDatabase == null) {
                    productDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    ProductDatabase.class, "my-db-product")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return productDatabase;
    }

}
