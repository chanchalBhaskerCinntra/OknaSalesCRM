package com.cinntra.okana.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.NotificationAdapter;
import com.cinntra.okana.databinding.ActivityNotificationsBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.NotificationResponse;
import com.cinntra.okana.model.NotificatonValue;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications extends MainBaseActivity implements View.OnClickListener, FragmentRefresher {

    private AppCompatActivity activity;

    ArrayList<NotificatonValue> notificationData = new ArrayList<>();
    ActivityNotificationsBinding binding;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = Notifications.this;
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  ButterKnife.bind(this);


        binding.loader.setVisibility(View.GONE);
        binding.headerWhiteBack.backPress.setOnClickListener(this);
        if (Globals.checkInternet(Notifications.this))
            callApi();
        swipeManager();


    }

    private void callApi() {
        binding.loader.setVisibility(View.VISIBLE);
        SalesEmployeeItem salesEmployeeItem = new SalesEmployeeItem();
        salesEmployeeItem.setEmp(Prefs.getString(Globals.EmployeeID, ""));
        Call<NotificationResponse> call = NewApiClient.getInstance().getApiService().allnotification(salesEmployeeItem);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                if (response != null) {
                    if (response.body().getData().size() > 0) {

                        notificationData.clear();
                        notificationData.addAll(response.body().getData());
                        binding.notificationList.setLayoutManager(new LinearLayoutManager(Notifications.this, RecyclerView.VERTICAL, false));
                        binding.notificationList.setAdapter(new NotificationAdapter(activity, notificationData));
                    } else {
                        binding.NoNotificationView.setVisibility(View.VISIBLE);
                        binding.notificationView.setVisibility(View.GONE);

                    }
                    binding.loader.setVisibility(View.GONE);
                    binding.swipeContainer.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                binding.swipeContainer.setRefreshing(false);
                binding.loader.setVisibility(View.GONE);
            }
        });
    }

    private void swipeManager() {
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
      /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
      @Override public void run() {

         }}, 2000);*/
                if (Globals.checkInternet(Notifications.this))
                    callApi();
                else
                    binding.swipeContainer.setRefreshing(false);
            }
        });
    }


    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onRefresh() {
        if (Globals.checkInternet(Notifications.this)) {
            binding.loader.setVisibility(View.VISIBLE);
            callApi();
        }
    }
}