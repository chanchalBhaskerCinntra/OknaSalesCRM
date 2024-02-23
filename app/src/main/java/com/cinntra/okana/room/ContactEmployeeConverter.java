package com.cinntra.okana.room;

import androidx.room.TypeConverter;

import com.cinntra.okana.model.ContactEmployeesModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ContactEmployeeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<ContactEmployeesModel> fromString(String value) {
        return gson.fromJson(value, new TypeToken<List<ContactEmployeesModel>>() {}.getType());
    }

    @TypeConverter
    public static String fromList(List<ContactEmployeesModel> list) {
        return gson.toJson(list);
    }
}
