package com.cinntra.okana.calender;

import android.util.Log;
import org.joda.time.DateTime;
import java.util.Locale;

public class Day {

    private String DayName;
    private DateTime date;
    private boolean selected;
    private String monthPattern = "MMMM YYYY";

    public Day(DateTime date) {
        this.date = date;
    }

    public String getDay() {
        Log.e("Days=>",String.valueOf(date.getDayOfMonth()));
        return String.valueOf(date.getDayOfMonth());
    }

    public String getWeekDay() {
        return date.toString("EEE", Locale.getDefault());
    }

    public String getMonth() { return getMonth(""); }

    public String getMonth(String pattern) {
        if (!pattern.isEmpty())
            this.monthPattern = pattern;

        return date.toString(monthPattern, Locale.getDefault());
    }

    public DateTime getDate() {
        return date.withTime(0,0,0,0);
    }

    public boolean isToday() {
        DateTime today=new DateTime().withTime(0,0,0,0);
        return getDate().getMillis()==today.getMillis();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }
}
