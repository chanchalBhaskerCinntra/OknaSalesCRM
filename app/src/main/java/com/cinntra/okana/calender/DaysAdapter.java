package com.cinntra.okana.calender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private int itemWidth;
    Context context;
    private ArrayList<Day> items;


    public DaysAdapter(int itemWidth, Context context,ArrayList<Day> items)
       {
    this.context   = context;
    this.itemWidth = itemWidth;
    this.items     = items;
    //this.shortWeekdays = shortWeekdays;
       }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
       {
    return new ViewHolder(
    LayoutInflater.from(parent.getContext())
    .inflate(R.layout.days_adapter,parent,false));
      }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Day item=getItem(position);

        holder.tvWeekDay.setText(item.getWeekDay());
        holder.tvDay.setText(item.getDay());
        //holder.tvWeekDay.setText(shortWeekdays[position]);


    }



    public Day getItem(int position)
      {
    return items.get(position);
      }

    @Override
    public int getItemCount()
      {
   return items.size();
  //  return shortWeekdays.length;
      }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvDay,tvWeekDay;
    public ViewHolder(View itemView)
      {
    super(itemView);
    tvDay     = (TextView) itemView.findViewById(R.id.tvDay);
    tvDay.setWidth(itemWidth);
    tvWeekDay = (TextView) itemView.findViewById(R.id.tvWeekDay);
    tvWeekDay.setWidth(itemWidth);
       }
     }
}