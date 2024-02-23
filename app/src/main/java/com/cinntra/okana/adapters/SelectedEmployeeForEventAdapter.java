package com.cinntra.okana.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;

import java.util.List;

public class SelectedEmployeeForEventAdapter extends RecyclerView.Adapter<SelectedEmployeeForEventAdapter.ViewHolder> {
    Context context;
    List<String> employeeName;
//    List<Integer> employeeIdList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SelectedEmployeeForEventAdapter(Context context, List<String> employeeName) {
        this.context = context;
        this.employeeName = employeeName;
//        this.employeeIdList = selectDataId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_participant_chip, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Split the name by whitespace
        String[] nameParts = employeeName.get(position).split("\\s+");
        String firstName = nameParts[1];

        holder.tvNameOfEmployee.setText(firstName);

        holder.ivCrossIcon.setOnClickListener(view -> {
            Log.e("ivcross", "onBindViewHolder: " + position);
            employeeName.remove(holder.getAdapterPosition());

            notifyDataSetChanged();
//            if (listener != null) {
//                int pos = holder.getAdapterPosition();
//                if (pos != RecyclerView.NO_POSITION) {
//                    String item = employeeName.get(position);
//                    listener.onItemClick(item);
//                }
//            }
        });


    }

    @Override
    public int getItemCount() {
        return employeeName.size() ;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameOfEmployee;
        ImageView ivCrossIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameOfEmployee = itemView.findViewById(R.id.tvNameEmployee);
            ivCrossIcon = itemView.findViewById(R.id.ivCrossIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }



}
