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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityAddTaskDialogueLayoutBinding;
import com.cinntra.okana.databinding.FragmentAddTaskBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.NewEvent;
import com.cinntra.okana.model.OpportunityModels.OppActivityUpdateResponse;
import com.cinntra.okana.model.OpportunityModels.UpdateActivityEventModel;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.receivers.NotificationPublisher;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UpdateActivityTaskDetailFragment extends Fragment implements View.OnClickListener {

    EventValue newEvent;
    int Position;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;
    TaskProgressSpinner taskProgressSpinner;
    int t1hr, t1min;
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Integer> circleimage = new ArrayList<>();
    ArrayList<String> progress_status = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();

    private AlarmManager alarmManager;
    private Calendar myTime;
    private String priority, repeated, progresstatus;

    public UpdateActivityTaskDetailFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static UpdateActivityTaskDetailFragment newInstance(String param1) {
        UpdateActivityTaskDetailFragment fragment = new UpdateActivityTaskDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            newEvent = (EventValue) b.getParcelable("View");
            Position = b.getInt("Position");

        }
    }


    ActivityAddTaskDialogueLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityAddTaskDialogueLayoutBinding.inflate(inflater, container, false);
        // ButterKnife.bind(this, v);

        binding.headerLayout.headTitle.setText("Update Task");
        binding.headerLayout.backPress.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);
        binding.headerLayout.ok.setOnClickListener(this);
        binding.headerLayout.add.setOnClickListener(this);

        binding.loaderLayout.loader.setVisibility(View.GONE);
//        setDisable();
        setDefaults();
        setData();
//        loadData();

        return binding.getRoot();
    }

    private void setData() {
        binding.titleText.setText(newEvent.getTitle());
        binding.fromValue.setText(newEvent.getFrom());
        binding.timeValue.setText(newEvent.getTime());
        binding.addLocationText.setText(newEvent.getLocation());
        binding.hostText.setText(newEvent.getHost());
        binding.descriptionText.setText(newEvent.getDescription());


        if (newEvent.getRepeated().equals(""))
            binding.simpleSwitch.setChecked(true);
        else
            binding.simpleSwitch.setChecked(false);

        binding.spinner.setSelection(getposition(categories, newEvent.getRepeated()));

        binding.progressSpinner.setSelection(getposition(progress_status, newEvent.getProgressStatus()));

        binding.simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeated = "";
                    binding.spinnerview.setVisibility(View.INVISIBLE);
                } else {
                    binding.spinnerview.setVisibility(View.VISIBLE);
//                    repeated = "Repeated";
                }
            }
        });


    }



    private int getposition(ArrayList<String> categories, String repeated) {
        int pos = -1;
        for (String s : categories) {
            if (s.equals(repeated)) {
                return categories.indexOf(s);
            }
        }
        return pos;
    }

    private void updateLabel(TextView pass_date, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        pass_date.setText(sdf.format(myCalendar.getTime()));
    }


/*    private void setDisable() {
        binding.titleText.setClickable(false);
        binding.titleText.setFocusable(false);
        binding.titleText.setFocusableInTouchMode(false);

        binding..setClickable(false);
        binding.dateValue.setFocusable(false);
        binding.dateValue.setFocusableInTouchMode(false);

        binding.timeValue.setFocusableInTouchMode(false);
        binding.timeValue.setFocusable(false);
        binding.timeValue.setClickable(false);

        binding.addLocationText.setClickable(false);
        binding.addLocationText.setFocusableInTouchMode(false);
        binding.addLocationText.setFocusable(false);

        binding.hostText.setFocusable(false);
        binding.hostText.setClickable(false);
        binding.hostText.setFocusableInTouchMode(false);


        binding.descriptionText.setFocusable(false);
        binding.descriptionText.setClickable(false);
        binding.descriptionText.setFocusableInTouchMode(false);

        binding.colorSpinner.setEnabled(false);
        binding.spinner.setEnabled(false);

        binding.submitButton.setVisibility(View.GONE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);
        binding.headerBottomroundEdit.add.setVisibility(View.VISIBLE);
    }*/

    private void setDefaults() {
        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);

        categories.add("Repeat");
        categories.add("Once");
        categories.add("Daily");
        categories.add("Weekly");
        categories.add("Monthly");


        progress_status.add("In Progress");
        progress_status.add("Completed");


        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(binding.fromValue, myCalendar);
        };


        eventTextSpinner = new EventTextSpinner(getActivity(), categories);
        binding.spinner.setAdapter(eventTextSpinner);
        binding.spinner.setDropDownVerticalOffset(60);

        binding.spinner.setSelection(categories.indexOf(newEvent.getRepeated()));

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                repeated = binding.spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                repeated = categories.get(0);
            }
        });

        taskProgressSpinner = new TaskProgressSpinner(getActivity(), progress_status);
        binding.progressSpinner.setAdapter(taskProgressSpinner);
        binding.progressSpinner.setDropDownVerticalOffset(60);

        binding.progressSpinner.setSelection(progress_status.indexOf(newEvent.getProgressStatus()));

        binding.progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progresstatus = binding.spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                progresstatus = progress_status.get(0);
            }
        });


        binding.fromValue.setOnClickListener(v -> {
            Globals.enableAllCalenderDateSelect(getContext(), binding.fromValue);

        });

        binding.timeValue.setOnClickListener(new View.OnClickListener() {
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
                        binding.timeValue.setText(DateFormat.format("hh:mm aa", myTime));
                        //setAlarm();
                    }
                }, 12, 0, false
                );
                timePickerDialog.updateTime(t1hr, t1min);
                timePickerDialog.show();

            }

        });


    }

    private ArrayList<EventValue> TaskEventList;

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

    private void saveData(ArrayList<EventValue> taskEventList) {

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
        switch (v.getId()) {
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.submit_button:
            case R.id.ok:
                String title = binding.titleText.getText().toString().trim();
                String date = binding.fromValue.getText().toString().trim();
                String location = binding.addLocationText.getText().toString().trim();
                String host = binding.hostText.getText().toString().trim();
                String desc = binding.descriptionText.getText().toString().trim();
                String time = binding.timeValue.getText().toString().trim();

/*                newEvent.setTitle(title);
                newEvent.setFrom(date);
                newEvent.setLocation(location);
                newEvent.setHost(host);
                newEvent.setDescription(desc);
                newEvent.setTime(time);*/

                if (validation(title, date, location, host, time)) {

                    ArrayList<String> partcipantList = new ArrayList<>();
                    partcipantList.add("");

                    UpdateActivityEventModel eventValue = new UpdateActivityEventModel();
                    eventValue.setOpp_Id(newEvent.getOppId());
                    eventValue.setTitle(title);
                    eventValue.setDescription(desc);
                    eventValue.setFrom(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(date));
                    eventValue.setTo(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(date));
                    eventValue.setEmp(newEvent.getEmp());
                    eventValue.setCreateTime(Globals.getTCurrentTime());
                    eventValue.setCreateDate(Globals.getTodaysDatervrsfrmt());
                    eventValue.setType("Task");
                    eventValue.setParticipants(partcipantList);
                    eventValue.setComment("");
                    eventValue.setSubject("");
                    eventValue.setTime(Globals.getTCurrentTime());
                    eventValue.setDocument("");
                    eventValue.setRelatedTo("hii");
                    eventValue.setLocation(location);
                    eventValue.setHost(host);
                    eventValue.setAllday("false");
                    eventValue.setName(newEvent.getName());
                    eventValue.setProgressStatus("WIP");
                    eventValue.setPriority("low");
                    eventValue.setRepeated(repeated);
                    eventValue.setStatus("");
                    eventValue.setId(String.valueOf(newEvent.getId()));


                    if (Globals.checkInternet(getContext()))
                        callApi(eventValue);

                }


                break;
            case R.id.add:
//                setEnable();
                break;
        }
    }


    //todo update call api here---
    private void callApi(UpdateActivityEventModel eventValue) {

        Call<OppActivityUpdateResponse> call = NewApiClient.getInstance().getApiService().updateOppEventActivity(eventValue);
        call.enqueue(new Callback<OppActivityUpdateResponse>() {
            @Override
            public void onResponse(Call<OppActivityUpdateResponse> call, Response<OppActivityUpdateResponse> response) {
                if (response.body().getStatus() == 200) {

                    Toast.makeText(getContext(), "Updated Successfully.", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(getActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OppActivityUpdateResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String title, String fromDate, String location,
                               String host, String time) {
        if (title.isEmpty()) {
            binding.titleText.setError(getResources().getString(R.string.title_error));
            return false;
        } else if (fromDate.isEmpty()) {
            binding.fromValue.setError(getResources().getString(R.string.fromdate_error));
            return false;
        }else if (location.isEmpty()) {
            binding.addLocationText.setError(getResources().getString(R.string.location_error));
            return false;
        } else if (host.isEmpty()) {
            binding.hostText.setError(getResources().getString(R.string.host_error));
            return false;
        } else if (repeated.isEmpty()) {
            Globals.showMessage(getContext(), "Repeat is Required !");
            return false;
        } else if (time.isEmpty()) {
            binding.timeValue.setError(getResources().getString(R.string.time_error));
            return false;
        }
        return true;
    }


    private void setAlarm() {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), NotificationPublisher.class);
        i.putExtra("value", getActivity().getResources().getString(R.string.task_notification));
        i.putExtra("title", getActivity().getResources().getString(R.string.ur_task));
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, i, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, myTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


}
