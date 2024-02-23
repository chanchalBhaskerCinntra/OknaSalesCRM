package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;



public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    Context context;

    public TimeLineAdapter(Context context)
    {
      this.context = context;

    }
    @Override
    public int getItemViewType(int position) {
        return 0;
      //  return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_timeline,parent,false);
        return new TimeLineViewHolder(rootView,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

       // holder.item_name.setText(aa[position]);


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder{
   // public TimelineView mTimelineView;
    public TimeLineViewHolder(View itemView, int viewType) {
    super(itemView);
   // mTimelineView = (TimelineView) itemView.findViewById(R.id.timeline);
  //  mTimelineView.initLine(viewType);
        }
    }




}
