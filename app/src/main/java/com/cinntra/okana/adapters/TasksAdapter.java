package com.cinntra.okana.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    Context context;
    ArrayList<EventValue> allEventTaskList;

    public TasksAdapter(Context context, ArrayList<EventValue> allEventTaskList) {
        this.context = context;
        this.allEventTaskList = allEventTaskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(rootView);


    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {


        holder.title.setText(allEventTaskList.get(holder.getAdapterPosition()).getTitle());
        holder.location.setText(allEventTaskList.get(holder.getAdapterPosition()).getDescription());
        holder.timing.setText(allEventTaskList.get(holder.getAdapterPosition()).getTime());

        if (allEventTaskList.get(holder.getAdapterPosition()).getPriority().equalsIgnoreCase("high")) {
            holder.priority_dot.setBackground(context.getDrawable(R.drawable.ic_red_dot));
        } else if (allEventTaskList.get(holder.getAdapterPosition()).getPriority().equalsIgnoreCase("medium")) {
            holder.priority_dot.setBackground(context.getDrawable(R.drawable.ic_green_dot));
        } else {
            holder.priority_dot.setBackground(context.getDrawable(R.drawable.yellow_dot));
        }

        if (allEventTaskList.get(holder.getAdapterPosition()).getStatus().equals("1"))
            holder.completed.setChecked(true);
        else
            holder.completed.setChecked(false);

        holder.completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    openConfirmDialog(holder.getAdapterPosition());


            }
        });

    }

    private void openConfirmDialog(int position) {

        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Are you Sure?")
                .setContentText("You mark as Complete")
                .setConfirmText("Yes,Marked")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        callcompletedApi(allEventTaskList.get(position).getId());


                    }
                })

                .show();

    }


    @Override
    public int getItemCount() {
        return allEventTaskList.size();
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        LinearLayout taskView;
        TextView title, location, timing;
        ImageView priority_dot;
        CheckBox completed;
        CardView card;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskView = itemView.findViewById(R.id.taskView);
            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);
            timing = itemView.findViewById(R.id.timing);
            priority_dot = itemView.findViewById(R.id.priority_dot);
            completed = itemView.findViewById(R.id.status);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(v.getContext(), card);
                    popup.getMenuInflater().inflate(R.menu.delete_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.delete) {
                                callDeletApi(allEventTaskList.get(getAdapterPosition()).getId());
                                allEventTaskList.remove(getAdapterPosition());
                                notifyDataSetChanged();

                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            });


        }

    }

    private void callcompletedApi(Integer id) {
        EventValue eventValue = new EventValue();
        eventValue.setId(id);

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().completeEvent(eventValue);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.code() == 200) {
                    Toasty.success(context, "Marked Successfully", Toast.LENGTH_LONG).show();
                    //notifyDataSetChanged();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(context, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void callDeletApi(Integer id) {
        EventValue eventValue = new EventValue();
        eventValue.setId(id);
        Call<EventResponse> call = NewApiClient.getInstance().getApiService().deleteEvent(eventValue);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Deleted Successfully.", Toast.LENGTH_SHORT).show();

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(context, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
