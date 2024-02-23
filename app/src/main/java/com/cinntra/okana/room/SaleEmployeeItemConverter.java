package com.cinntra.okana.room;

import androidx.room.TypeConverter;

import com.cinntra.okana.model.SalesEmployeeItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SaleEmployeeItemConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<SalesEmployeeItem> fromString(String value) {
        return gson.fromJson(value, new TypeToken<List<SalesEmployeeItem>>() {}.getType());
    }

    @TypeConverter
    public static String fromList(List<SalesEmployeeItem> list) {
        return gson.toJson(list);
    }
}
