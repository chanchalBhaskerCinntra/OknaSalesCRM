package com.cinntra.okana.room;

import androidx.room.TypeConverter;

import com.cinntra.okana.model.UTypeData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class UTypeDataConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<UTypeData> fromString(String value) {
        return gson.fromJson(value, new TypeToken<List<UTypeData>>() {}.getType());
    }

    @TypeConverter
    public static String fromList(List<UTypeData> list) {
        return gson.toJson(list);
    }
}
