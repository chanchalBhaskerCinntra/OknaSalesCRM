package com.cinntra.okana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.NotificationData;
import com.cinntra.okana.model.NotificationResponse;
import com.cinntra.okana.model.NotificatonValue;
import com.cinntra.okana.webservices.NewApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
  Context context;
  ArrayList<NotificatonValue> notificationList;
  FragmentRefresher frm;
    public NotificationAdapter(Context context, ArrayList<NotificatonValue> notificationList)
      {
    this.context = context;
    this.frm = (FragmentRefresher) context;
  this.notificationList = notificationList;
     }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
      {
    View rootView = LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
    return new ViewHolder(rootView);
      }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
      {
        NotificatonValue notificationData = notificationList.get(position);
        holder.title.setText(notificationData.getNotification().getTitle());
        holder.description.setText(notificationData.getNotification().getDescription());
        if(notificationData.getNotification().getRead().equalsIgnoreCase("1")){
          holder.profile_pic.setVisibility(View.GONE);
        }else{
          holder.profile_pic.setVisibility(View.VISIBLE);
        }
     }

    @Override
    public int getItemCount()
      {
    return notificationList.size();
      }



    class ViewHolder extends RecyclerView.ViewHolder {
      private TextView title,description;

      ImageView profile_pic;
      public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title       = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        profile_pic = itemView.findViewById(R.id.profile_pic);

        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            callReadNotificationApi(notificationList.get(getAdapterPosition()).getNotification().getId());

          }
        });


        }

      private void callReadNotificationApi(Integer id)
      {

        NotificationData notificationData = new NotificationData();
        notificationData.setId(id);
        Call<NotificationResponse> call = NewApiClient.getInstance().getApiService().readnotification(notificationData);
        call.enqueue(new Callback<NotificationResponse>() {
          @Override
          public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
            if (response.code()==200)
            {
              frm.onRefresh();
            }
          }
          @Override
          public void onFailure(Call<NotificationResponse> call, Throwable t) {
            Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
          }
        });
      }
    }
}
