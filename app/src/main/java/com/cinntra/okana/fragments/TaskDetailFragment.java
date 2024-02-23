package com.cinntra.okana.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.NewEvent;
import com.cinntra.okana.receivers.NotificationPublisher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import static android.content.Context.MODE_PRIVATE;

public class TaskDetailFragment extends Fragment implements  View.OnClickListener {

//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.date_value)
//    TextView date_value;
//    @BindView(R.id.time_value)
//    TextView settime;
//    @BindView(R.id.title_text)
//    EditText title_text;
//    @BindView(R.id.add_location_text)
//    EditText add_location_text;
//    @BindView(R.id.host_text)
//    EditText host_text;
//    @BindView(R.id.description_text)
//    EditText description_text;
//    @BindView(R.id.simple_switch)
//    Switch simple_switch;
//    @BindView(R.id.date_view)
//    RelativeLayout date_view;
//    @BindView(R.id.color_view)
//    LinearLayout color_view;
//    @BindView(R.id.time_view)
//    RelativeLayout time_view;
//    @BindView(R.id.progress_status_view)
//    RelativeLayout progress_status_view;
//    @BindView(R.id.spinnerview)
//    RelativeLayout spinnerview;
//    @BindView(R.id.spinner)
//    Spinner preority_spinner;
//    @BindView(R.id.color_spinner)
//    Spinner colorspin;
//    @BindView(R.id.progress_spinner)
//    Spinner progress_spinner;
//    @BindView(R.id.submit_button)
//    Button submit_button;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.ok)
//    ImageView ok;
    EventValue newEvent;
    int Position ;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;
    TaskProgressSpinner taskProgressSpinner;
    int t1hr,t1min;
    ArrayList<String> categories      = new ArrayList<>();
    ArrayList<Integer> circleimage    = new ArrayList<>();
    ArrayList<String> progress_status = new ArrayList<>();
    final Calendar myCalendar         = Calendar.getInstance();

    private AlarmManager alarmManager;
    private Calendar myTime;

    public TaskDetailFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static TaskDetailFragment newInstance(String param1) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            newEvent =(EventValue) b.getParcelable("View");
            Position = b.getInt("Position");

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_task, container, false);
       // ButterKnife.bind(this, v);
//        head_title.setText("Update Task");
//        back_press.setOnClickListener(this);
//        submit_button.setOnClickListener(this);
//        ok.setOnClickListener(this);
//        add.setOnClickListener(this);
        setDisable();
        setDefaults();
        setData();

        return v;
    }

    private void setData() {
//        title_text.setText(newEvent.getTitle());
//        date_value.setText(newEvent.getFrom());
//        settime.setText(newEvent.getTime());
//        add_location_text.setText(newEvent.getLocation());
//        host_text.setText(newEvent.getHost());
//        description_text.setText(newEvent.getDescription());
        

    }

    private void setDisable() {
//        title_text.setClickable(false);
//        title_text.setFocusable(false);
//        title_text.setFocusableInTouchMode(false);
//
//        date_value.setClickable(false);
//        date_value.setFocusable(false);
//        date_value.setFocusableInTouchMode(false);
//
//        settime.setFocusableInTouchMode(false);
//        settime.setFocusable(false);
//        settime.setClickable(false);
//
//        add_location_text.setClickable(false);
//        add_location_text.setFocusableInTouchMode(false);
//        add_location_text.setFocusable(false);
//
//        host_text.setFocusable(false);
//        host_text.setClickable(false);
//        host_text.setFocusableInTouchMode(false);
//
//
//        description_text.setFocusable(false);
//        description_text.setClickable(false);
//        description_text.setFocusableInTouchMode(false);
//
//        colorspin.setEnabled(false);
//        preority_spinner.setEnabled(false);
//
//        submit_button.setVisibility(View.GONE);
//        ok.setVisibility(View.GONE);
//        add.setVisibility(View.VISIBLE);
    }

    private void setDefaults()
    {
        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);
        categories.add("Repeat");
        categories.add("Daily");
        categories.add("Monthly");
        categories.add("Weekly");
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
          //  date_value.setText(sdf.format(myCalendar.getTime()));
        };



        eventPrerioritySpinner = new EventPrerioritySpinner(getActivity(), circleimage);
      //  colorspin.setAdapter(eventPrerioritySpinner);
      //  colorspin.setDropDownVerticalOffset(120);

        eventTextSpinner = new EventTextSpinner(getActivity(), categories);
    //    preority_spinner.setAdapter(eventTextSpinner);
    //    preority_spinner.setDropDownVerticalOffset(120);

        taskProgressSpinner = new TaskProgressSpinner(getActivity(),progress_status);
      //  progress_spinner.setAdapter(taskProgressSpinner);
     //   progress_spinner.setDropDownVerticalOffset(120);


//        date_value.setOnClickListener(v -> {
//            date_value.getText().toString();
//            if (!date_value.getText().toString().isEmpty()) {
//                String value = date_value.getText().toString();
//                String[] dd = value.split("-");
//                int y = Integer.parseInt(dd[2]);
//                int m = Integer.parseInt(dd[1])-1;
//                int d = Integer.parseInt(dd[0]);
//                myCalendar.set(Calendar.DAY_OF_MONTH, y);
//                myCalendar.set(Calendar.MONTH, m);
//                myCalendar.set(Calendar.YEAR, d);
//                new DatePickerDialog(getActivity(), date, d, m, y).show();
//            } else {
//                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//
//        });
//
//        settime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        t1hr = hourOfDay;
//                        t1min = minute;
//                        myTime = Calendar.getInstance();
////                        myTime.set(0,0,0,t1hr,t1min);
//                        myTime.set(Calendar.HOUR_OF_DAY,t1hr);
//                        myTime.set(Calendar.MINUTE,t1min);
//                        myTime.set(Calendar.SECOND,0);
//                        myTime.set(Calendar.MILLISECOND,0);
//                        settime.setText(DateFormat.format("hh:mm aa",myTime));
//                        setAlarm();
//                    }
//                },12,0,false
//                );
//                timePickerDialog.updateTime(t1hr,t1min);
//                timePickerDialog.show();
//
//            }
//
//        });



    }

    private ArrayList<EventValue> TaskEventList;
    private void loadData()
    {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Globals.TaskEventList, null);
        Type type = new TypeToken<ArrayList<NewEvent>>() {}.getType();
        TaskEventList = gson.fromJson(json, type);
        if (TaskEventList == null) {
            TaskEventList = new ArrayList<>();
        }
    }
    private void saveData(ArrayList<EventValue> taskEventList)
    {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskEventList);
        editor.putString(Globals.TaskEventList, json);
        editor.apply();
        // Toast.makeText(getActivity(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.submit_button:
            case R.id.ok:
              //  String title      = title_text.getText().toString().trim();
              //  String date       = date_value.getText().toString().trim();
              //  String location   = add_location_text.getText().toString().trim();
             //   String host       = host_text.getText().toString().trim();
             //   String desc       = description_text.getText().toString().trim();
             //   String time       = settime.getText().toString().trim();





              ///  newEvent.setTitle(title);
//                newEvent.setFrom(date);
//                newEvent.setLocation(location);
//                newEvent.setHost(host);
//                newEvent.setDescription(desc);
//                newEvent.setTime(time);
                TaskEventList.set(Position,newEvent);
                saveData(TaskEventList);
                setDisable();
                getActivity().onBackPressed();
                break;
            case R.id.add:
                setEnable();
                break;
        }
    }

    private void setEnable() {
//        add.setVisibility(View.GONE);
//        ok.setVisibility(View.VISIBLE);
//        title_text.setClickable(true);
//        title_text.setFocusable(true);
//        title_text.setFocusableInTouchMode(true);
//
//        date_value.setClickable(true);
//        date_value.setFocusable(true);
//
//        settime.setFocusable(true);
//        settime.setClickable(true);
//
//        add_location_text.setClickable(true);
//        add_location_text.setFocusableInTouchMode(true);
//        add_location_text.setFocusable(true);
//
//        host_text.setFocusable(true);
//        host_text.setClickable(true);
//        host_text.setFocusableInTouchMode(true);
//
//        description_text.setFocusable(true);
//        description_text.setClickable(true);
//        description_text.setFocusableInTouchMode(true);
//
//        colorspin.setEnabled(true);
//        preority_spinner.setEnabled(true);
//
//        submit_button.setVisibility(View.VISIBLE);
    }

    private void setAlarm()
         {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), NotificationPublisher.class);
        i.putExtra("value",getActivity().getResources().getString(R.string.task_notification));
        i.putExtra("title",getActivity().getResources().getString(R.string.ur_task));
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),id,i,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,myTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        }


   }
