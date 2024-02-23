package com.cinntra.okana.calender;

import android.app.AlarmManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import org.joda.time.DateTime;
import java.util.ArrayList;

public class HorizontalPickerAdapter extends RecyclerView.Adapter<HorizontalPickerAdapter.ViewHolder> {

    private static final long DAY_MILLIS = AlarmManager.INTERVAL_DAY;
    private final int mBackgroundColor;
    private final int mDateSelectedTextColor;
    private final int mDateSelectedColor;
    private final int mTodayDateTextColor;
    private final int mTodayDateBackgroundColor;
    private final int mDayOfWeekTextColor;
    private final int mUnselectedDayTextColor;
    private int itemWidth;
    private final OnItemClickedListener listener;
    private ArrayList<Day> items;

    public HorizontalPickerAdapter(int itemWidth, OnItemClickedListener listener, Context context, int daysToCreate, int offset, int mBackgroundColor, int mDateSelectedColor, int mDateSelectedTextColor, int mTodayDateTextColor, int mTodayDateBackgroundColor, int mDayOfWeekTextColor, int mUnselectedDayTextColor) {
        items=new ArrayList<>();
        this.itemWidth=itemWidth;
        this.listener=listener;
        generateDays(daysToCreate,new DateTime().minusDays(offset).getMillis(),false);
        this.mBackgroundColor=mBackgroundColor;
        this.mDateSelectedTextColor=mDateSelectedTextColor;
        this.mDateSelectedColor=mDateSelectedColor;
        this.mTodayDateTextColor=mTodayDateTextColor;
        this.mTodayDateBackgroundColor=mTodayDateBackgroundColor;
        this.mDayOfWeekTextColor=mDayOfWeekTextColor;
        this.mUnselectedDayTextColor=mUnselectedDayTextColor;
    }

    public  void generateDays(int n, long initialDate, boolean cleanArray) {
        if(cleanArray)
            items.clear();
        int i=0;
        while(i<n)
        {
            DateTime actualDate = new DateTime(initialDate + (    DAY_MILLIS * i++));
            items.add(new Day(actualDate));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
        LayoutInflater.from(parent.getContext())
       .inflate(R.layout.item_day_name,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Day item=getItem(position);
        holder.tvDay.setText(item.getDay());
        holder.tvWeekDay.setText(item.getWeekDay());
        holder.tvWeekDay.setTextColor(mDayOfWeekTextColor);
        holder.tvDay.setBackground(getDayTodayBackground(holder.tvDay));
        if(item.isSelected())
           {
        holder.tvDay.setBackgroundDrawable(getDaySelectedBackground(holder.itemView));
        holder.tvDay.setTextColor(mDateSelectedTextColor);
        holder.tvDay.setBackground(getDaySelectedBackground(holder.tvDay));
           }
        else if(item.isToday())
           {
         holder.tvDay.setBackgroundDrawable(getDayTodayBackground(holder.itemView));
         holder.tvDay.setTextColor(mTodayDateTextColor);
         holder.tvDay.setBackgroundResource(R.drawable.yellow_dot);
           // holder.tvDay.setTextColor();
           }
        else
           {
        holder.tvDay.setBackgroundDrawable(getDayTodayBackground(holder.itemView));
        holder.tvDay.setBackgroundColor(mBackgroundColor);
        holder.tvDay.setTextColor(mUnselectedDayTextColor);
        holder.tvDay.setBackground(getDayTodayBackground(holder.tvDay));
           }
    }

    private Drawable getDaySelectedBackground(View view) {
        //Drawable drawable=view.getResources().getDrawable(R.drawable.background_day_selected);
        Drawable drawable=view.getResources().getDrawable(R.drawable.selected_day);
        DrawableCompat.setTint(drawable,mDateSelectedColor);
        return drawable;
    }

    private Drawable getDayTodayBackground(View view) {
        Drawable drawable=view.getResources().getDrawable(R.drawable.background_day_today);
        if(mTodayDateBackgroundColor!=-1)
            DrawableCompat.setTint(drawable,mTodayDateBackgroundColor);
        return drawable;
    }



    public Day getItem(int position) {
    return items.get(position);
      }

    @Override
    public int getItemCount() {
    return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDay,tvWeekDay;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDay= (TextView) itemView.findViewById(R.id.tvDay);
            tvDay.setWidth(itemWidth);
            tvWeekDay= (TextView) itemView.findViewById(R.id.tvWeekDay);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickView(v,getAdapterPosition());
        }
    }
}