package com.cinntra.okana.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.FragmentAddTaskBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.CreateCalenderActivityRequest;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.NewEvent;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.receivers.NotificationPublisher;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AddTaskDialogue extends DialogFragment implements View.OnClickListener {
    String priority = "";
    String allday = "";
    String repeated = "";
    String progresstatus = "";
    int t1hr, t1min;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;
    TaskProgressSpinner taskProgressSpinner;

    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Integer> circleimage = new ArrayList<>();
    ArrayList<String> progress_status = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    private AlarmManager alarmManager;
    private Calendar myTime;

    public AddTaskDialogue() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddTaskDialogue newInstance(String title) {
        AddTaskDialogue frag = new AddTaskDialogue();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    FragmentAddTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(getLayoutInflater());

        setDefaults();
       
        // loadData();
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.headerLayout.headTitle.setText("New Task");

        binding.headerLayout.backPress.setOnClickListener(this);
        binding.taskContent.submitButton.setOnClickListener(this);

        binding.loaderLayout.loader.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_press) {
            getDialog().dismiss();
        } else if (v.getId() == R.id.submit_button) {

            String title = binding.taskContent.titleText.getText().toString().trim();
            String date = binding.taskContent.dateValue.getText().toString().trim();
            String desc = binding.taskContent.descriptionText.getText().toString().trim();
            String time = binding.taskContent.timeValue.getText().toString().trim();


            if (validation(title, desc, time, date)) {
                CreateCalenderActivityRequest eventValue = new CreateCalenderActivityRequest();
                // Concatenate the elements of the ArrayList into integer arraylist

                eventValue.setSourceID("0");
                eventValue.setTitle(title);
                eventValue.setDescription(desc);
                eventValue.setFrom("");
                eventValue.setTo(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(date));
                eventValue.setEmp(Prefs.getString(Globals.EmployeeID, ""));
                eventValue.setCreateTime(Globals.getTCurrentTime());
                eventValue.setCreateDate(Globals.getTodaysDatervrsfrmt());
                eventValue.setType("Task");
                eventValue.setSourceType("Opportunity");
                eventValue.setParticipants("");
                eventValue.setComment("");
                eventValue.setSubject("");
                eventValue.setTime(time);
                eventValue.setDocument("");
                eventValue.setRelatedTo("hii");
                eventValue.setLocation("");
                eventValue.setHost("");
                eventValue.setAllday("false");
                eventValue.setName("");
                eventValue.setProgressStatus("WIP");//progresstatus
                eventValue.setPriority("low");
                eventValue.setRepeated("");
                eventValue.setLeadType("");


                if (Globals.checkInternet(getContext())) {
                    binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                    callApi(eventValue);
                }

            }

        }
    }


    private void callApi(CreateCalenderActivityRequest eventValue) {

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().createnewevent(eventValue);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.body().getStatus() == 200) {
                    Toasty.success(getContext(), "Add Successfully", Toast.LENGTH_LONG).show();
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    getDialog().dismiss();

                } else {
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(getActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String title, String desc, String time, String date) {
        if (title.isEmpty()) {
            binding.taskContent.titleText.setError(getResources().getString(R.string.title_error));
            return false;
        } else if (date.isEmpty()) {
            binding.taskContent.dateValue.setError(getResources().getString(R.string.todate_error));
            return false;
        } else if (desc.isEmpty()) {
            binding.taskContent.descriptionText.setError(getResources().getString(R.string.description_error));
            return false;
        }
        return true;

    }



    private void setDefaults() {
        binding.headerLayout.add.setVisibility(View.GONE);
        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);
        categories.add("Daily");
        categories.add("Weekly");
        categories.add("Monthly");
        progress_status.add("Completed");
        progress_status.add("In Progress");
        progress_status.add("Not Started");
        progress_status.add("Waiting for input");


        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            binding.taskContent.dateValue.setText(sdf.format(myCalendar.getTime()));
        };


        eventPrerioritySpinner = new EventPrerioritySpinner(getActivity(), circleimage);


        binding.taskContent.dateValue.setOnClickListener(v -> {
            /*date_value.getText().toString();
            if (!date_value.getText().toString().isEmpty()) {
                String value = date_value.getText().toString();
                String[] dd = value.split("-");
                int y = Integer.parseInt(dd[2]);
                int m = Integer.parseInt(dd[1])-1;
                int d = Integer.parseInt(dd[0]);
                myCalendar.set(Calendar.DAY_OF_MONTH, y);
                myCalendar.set(Calendar.MONTH, m);
                myCalendar.set(Calendar.YEAR, d);
                new DatePickerDialog(getActivity(), date, d, m, y).show();
            } else {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }*/
            Globals.disablePastSelectDate(getContext(), binding.taskContent.dateValue);

        });

        binding.taskContent.timeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1hr = hourOfDay;
                        t1min = minute;
                        myTime = Calendar.getInstance();
//                        myTime.set(0,0,0,t1hr,t1min);
                        myTime.set(Calendar.HOUR_OF_DAY, t1hr);
                        myTime.set(Calendar.MINUTE, t1min);
                        myTime.set(Calendar.SECOND, 0);
                        myTime.set(Calendar.MILLISECOND, 0);
                        binding.taskContent.timeValue.setText(DateFormat.format("hh:mm aa", myTime));
                        //setAlarm();
                    }
                }, 12, 0, false
                );
                timePickerDialog.updateTime(t1hr, t1min);
                timePickerDialog.show();

            }

        });


    }


    /****************** Manage List in local ***************************/

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(TaskEventList);
        editor.putString(Globals.TaskEventList, json);
        editor.apply();
        // Toast.makeText(getActivity(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<NewEvent> TaskEventList;

    private void loadData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Globals.TaskEventList, null);
        Type type = new TypeToken<ArrayList<NewEvent>>() {
        }.getType();
        TaskEventList = gson.fromJson(json, type);
        if (TaskEventList == null) {
            TaskEventList = new ArrayList<>();
        }
    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), NotificationPublisher.class);
        i.putExtra("value", getActivity().getResources().getString(R.string.task_notification));
        i.putExtra("title", getActivity().getResources().getString(R.string.ur_task));
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, i, PendingIntent.FLAG_MUTABLE);
        }
        else {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, i, PendingIntent.FLAG_ONE_SHOT);
        }
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, i, PendingIntent.FLAG_IMMUTABLE);//todo comment
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, myTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


}
