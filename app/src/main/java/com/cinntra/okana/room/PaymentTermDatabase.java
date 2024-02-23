package com.cinntra.okana.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cinntra.okana.model.PayMentTerm;


@Database(entities = {PayMentTerm.class},version = 1,exportSchema = false)
public abstract class PaymentTermDatabase extends RoomDatabase {
    public abstract PaymentTermDao myDataDao();
    private static volatile PaymentTermDatabase paymentTermDatabase;

     public static PaymentTermDatabase getDatabase(final Context context) {
        if (paymentTermDatabase == null) {
            synchronized (PaymentTermDatabase.class) {
                if (paymentTermDatabase == null) {
                    paymentTermDatabase = Room.databaseBuilder(context.getApplicationContext(),
                                    PaymentTermDatabase.class, "my-db-payment-term")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return paymentTermDatabase;
    }

}
