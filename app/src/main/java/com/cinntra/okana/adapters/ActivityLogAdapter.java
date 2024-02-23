package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.NewEvent;

import java.util.ArrayList;


public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.TaskViewHolder> {
    Context context;
    ArrayList<NewEvent> allEventTaskList;
    public ActivityLogAdapter(Context context, ArrayList<NewEvent> allEventTaskList)
    {
        this.context = context;
        this.allEventTaskList = allEventTaskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.activitytask_item,parent,false);
        return new TaskViewHolder(rootView);


    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        if(getItemViewType(position)== Globals.TYPE_TASK){
        holder.title.setText(allEventTaskList.get(position).getTitle());
        holder.location.setText(allEventTaskList.get(position).getRelatedDoc());
        }


    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return allEventTaskList.get(position).getType();
    }



    @Override
    public int getItemCount() {
        return allEventTaskList.size();
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        LinearLayout taskView;
        TextView title,location;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskView = itemView.findViewById(R.id.taskView);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);


        }
    }


}
