package com.cinntra.okana.calender;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import org.joda.time.DateTime;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class HorizontalPicker extends LinearLayout implements HorizontalPickerListener {

    private static final int NO_SETTED = -1;
    private View vHover;
    private TextView tvMonth;
    private TextView tvToday;
    private DatePickerListener listener;
    private OnTouchListener monthListener;
    private HorizontalPickerRecyclerView rvDays;
    private int days;
    private int offset;
    private int mDateSelectedColor = -1;
    private int mDateSelectedTextColor = -1;
    private int mMonthAndYearTextColor = -1;
    private int mTodayButtonTextColor = -1;
    private boolean showTodayButton = true;
    public String mMonthPattern = "";
    private int mTodayDateTextColor = -1;
    private int mTodayDateBackgroundColor = -1;
    private int mDayOfWeekTextColor = -1;
    private int mUnselectedDayTextColor = -1;

    RecyclerView rec_days;

    public HorizontalPicker(Context context) {
        super(context);
        internInit();
    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        internInit();

    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        internInit();
    }

    private void internInit() {
        this.days   = NO_SETTED;
        this.offset = NO_SETTED;
    }

    public HorizontalPicker setListener(DatePickerListener listener) {
        this.listener = listener;
        return this;
    }

    public HorizontalPicker setMonthListener(OnTouchListener listener) {
        this.monthListener = listener;
        return this;
    }

    public void setDate(final DateTime date) {
        rvDays.post(new Runnable() {
            @Override
            public void run() {
                rvDays.setDate(date);
            }
        });
    }

    public void init() {
        inflate(getContext(), R.layout.horizontal_picker_new, this);
        rvDays = (HorizontalPickerRecyclerView) findViewById(R.id.rvDays);
        int DEFAULT_DAYS_TO_PLUS = 100;
        int finalDays = days == NO_SETTED ? DEFAULT_DAYS_TO_PLUS : days;
        int DEFAULT_INITIAL_OFFSET = 7;
        int finalOffset = offset == NO_SETTED ? DEFAULT_INITIAL_OFFSET : offset;
        vHover = findViewById(R.id.vHover);

        tvMonth = (TextView) findViewById(R.id.tvMonth);
        if (monthListener != null) {
            tvMonth.setClickable(true);
            tvMonth.setOnTouchListener(monthListener);
        }

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(Calendar.getInstance().getTime());
        tvMonth.setText(" "+month_name+" ");

        rec_days = (RecyclerView) findViewById(R.id.days);
        tvToday  = (TextView) findViewById(R.id.tvToday);
        rvDays.setListener(this);
        tvToday.setOnClickListener(rvDays);
        tvMonth.setTextColor(mMonthAndYearTextColor != -1 ? mMonthAndYearTextColor : getColor(R.color.black));
        tvToday.setVisibility(showTodayButton ? VISIBLE : INVISIBLE);
        tvToday.setTextColor(mTodayButtonTextColor != -1 ? mTodayButtonTextColor : getColor(R.color.black));
        int mBackgroundColor = getBackgroundColor();
        setBackgroundColor(mBackgroundColor != Color.TRANSPARENT ? mBackgroundColor : Color.WHITE);
        mDateSelectedColor      = mDateSelectedColor == -1 ? getColor(R.color.colorPrimary) : mDateSelectedColor;
        mDateSelectedTextColor  = mDateSelectedTextColor  == -1 ? Color.WHITE : mDateSelectedTextColor;
        mTodayDateTextColor     = mTodayDateTextColor     == -1 ? getColor(R.color.primaryTextColor) : mTodayDateTextColor;
        mDayOfWeekTextColor     = mDayOfWeekTextColor     == -1 ? getColor(R.color.secundaryTextColor) : mDayOfWeekTextColor;
        mUnselectedDayTextColor = mUnselectedDayTextColor == -1 ? getColor(R.color.primaryTextColor) : mUnselectedDayTextColor;
        mDateSelectedTextColor  = getColor(R.color.white);
        mUnselectedDayTextColor = getColor(R.color.light_black);

        rvDays.init(
                getContext(),
                finalDays,
                finalOffset,
                mBackgroundColor,
                mDateSelectedColor,
                mDateSelectedTextColor,
                mTodayDateTextColor,
                mTodayDateBackgroundColor,
                mDayOfWeekTextColor,
                mUnselectedDayTextColor);

        generateDays(7,new DateTime().minusDays(offset).getMillis()-DAY_MILLIS,false);
        setDays();

     // sWeekDaysList();

    }

    private int getColor(int colorId)
      {
    return getResources().getColor(colorId);
      }

    public int getBackgroundColor()
      {
    int color = Color.TRANSPARENT;
    Drawable background = getBackground();
    if (background instanceof ColorDrawable)
        color = ((ColorDrawable) background).getColor();
    return color;
    }

    @Override
    public boolean post(Runnable action) {
    return rvDays.post(action);
    }

    @Override
    public void onStopDraggingPicker() {
        if (vHover.getVisibility() == VISIBLE)
            vHover.setVisibility(INVISIBLE);
    }

    @Override
    public void onDraggingPicker() {
        if (vHover.getVisibility() == INVISIBLE)
            vHover.setVisibility(VISIBLE);
    }

    @Override
    public void onDateSelected(Day item) {
        tvMonth.setText(item.getMonth(mMonthPattern));
        if (showTodayButton)
           tvToday.setVisibility(item.isToday() ? INVISIBLE : VISIBLE);
          // tvMonth.setVisibility(item.isToday() ? INVISIBLE : VISIBLE);
        if (listener != null) {
            listener.onDateSelected(item.getDate());
          }


    }

    @Override
    public void onTodayClick(Day item) {

    }


    public HorizontalPicker setDays(int days)
      {
     this.days = days;
     return this;
      }

    public int getDays()
     {
   return days;
     }

    public HorizontalPicker setOffset(int offset)
      {
    this.offset = offset;
    return this;
      }

    public int getOffset() {
        return offset;
    }

    public HorizontalPicker setDateSelectedColor(@ColorInt int color) {
        mDateSelectedColor = color;
        return this;
    }

    public HorizontalPicker setDateSelectedTextColor(@ColorInt int color) {
        mDateSelectedTextColor = color;
        return this;
    }

    public HorizontalPicker setMonthAndYearTextColor(@ColorInt int color) {
        mMonthAndYearTextColor = color;
        return this;
    }

    public HorizontalPicker setTodayButtonTextColor(@ColorInt int color) {
        mTodayButtonTextColor = color;
        return this;
    }

    public HorizontalPicker showTodayButton(boolean show) {
        showTodayButton = show;
        return this;
    }

    public HorizontalPicker setTodayDateTextColor(int color) {
        mTodayDateTextColor = color;
        return this;
    }

    public HorizontalPicker setTodayDateBackgroundColor(@ColorInt int color) {
        mTodayDateBackgroundColor = color;
        return this;
    }

    public HorizontalPicker setDayOfWeekTextColor(@ColorInt int color) {
        mDayOfWeekTextColor = color;
        return this;
    }

    public HorizontalPicker setUnselectedDayTextColor(@ColorInt int color) {
        mUnselectedDayTextColor = color;
        return this;
    }

    public HorizontalPicker setMonthPattern(String pattern) {
        mMonthPattern = pattern;
        return this;
    }
    private float itemWidth;
    private static final long DAY_MILLIS = AlarmManager.INTERVAL_DAY;
    private ArrayList<Day> no_of_Day = new ArrayList<>();
    public  void generateDays(int n, long initialDate, boolean cleanArray) {
      if(cleanArray)
      no_of_Day.clear();
      int i=0;
      while(i<n)
        {

             /*DateTime initialDate1 = new DateTime();
             long ml= initialDate + (    miliSecond * i);
             i++;*/
            initialDate = System.currentTimeMillis();
           long res = initialDate + (    miliSecond * i);
           DateTime actualDate = new DateTime(res);
           no_of_Day.add(new Day(actualDate));
             //getDay( DAY_MILLIS * i);

         /*   Log.e("Current Day: ",""+new SimpleDateFormat("EEEE MM-dd-yyyy HH:mm:ss a").format(Calendar.getInstance().getTime()));
            Log.e("DayNo=>"+i,""+new Day(actualDate).getDay().toString());
            Log.e("WeekDayNo=>"+i,""+new Day(actualDate).getWeekDay().toString());
*/
            i++;
        }
    }

    private String getDay(long ff)
       {
    Date now = new Date(miliSecond);
    SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
    Log.e("D=>",""+simpleDateformat.format(now));
    return simpleDateformat.format(now);
       }
    int miliSecond = (int) (1000*60*60*24);
    private void setDays()
      {


    /*DateFormatSymbols dfs = new DateFormatSymbols();
    String[] shortWeekdays = dfs.getShortWeekdays();
     int j =0;
     int sz= no_of_Day.size();
    for (String shortWeekday : shortWeekdays) {
     Log.e("dayName = "+j ,""+ shortWeekday);
        no_of_Day.get(j).setDayName(shortWeekday);
        j++;
       }*/

    itemWidth = getMeasuredWidth()/7;
    DaysAdapter  Dayadapter = new DaysAdapter((int)  getwidth(),getContext(),no_of_Day );
    rec_days.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
    rec_days.setAdapter(Dayadapter);
      }

    private  int getwidth()
         {
     DisplayMetrics displayMetrics = new DisplayMetrics();
     ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
     int height = displayMetrics.heightPixels;
     int width = displayMetrics.widthPixels;
     return width/7;
          }
    @RequiresApi(api = Build.VERSION_CODES.O)
          private void sWeekDaysList(){
              DateFormatSymbols dfs = new DateFormatSymbols();


              LocalDate localDate
                      = LocalDate.of(1947,
                      Month.AUGUST, 15);

              // Find the day from the local date
              DayOfWeek dayOfWeek
                      = DayOfWeek.from(localDate);

              // Printing the day of the week
              // and its Int value
              Log.e("Day of the Week on",""+ dayOfWeek.name());

              String[] weekdays = dfs.getWeekdays();
              for (String weekday : weekdays) {
               Log.e("weekday = ","" + weekday);
              }


          }

}
