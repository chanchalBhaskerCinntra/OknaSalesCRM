package com.cinntra.okana.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityAddEventDialogueLayoutBinding;
import com.cinntra.okana.databinding.FragmentAddEventBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.CreateCalenderActivityRequest;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.receivers.NotificationPublisher;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddEventDialogue extends DialogFragment implements View.OnClickListener {

    String priority = "";

    TextView pass_date;
    String allday = "";
    String repeated = "";
    int t1hr, t1min;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;

    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Integer> circleimage = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();

    private AlarmManager alarmManager;
    private Calendar myTime;
    NewOpportunityRespose opportunityItem;

    ActivityAddEventDialogueLayoutBinding binding;

    public ActivityAddEventDialogue() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ActivityAddEventDialogue newInstance(String title) {
        ActivityAddEventDialogue frag = new ActivityAddEventDialogue();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            opportunityItem = (NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
        }

        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ActivityAddEventDialogueLayoutBinding.inflate(inflater, container, false);
//        View v = inflater.inflate(R.layout.fragment_add_event, container);
        //ButterKnife.bind(this,v);
        setDefaults();
        //loadData();

        binding.loaderLayout.loader.setVisibility(View.GONE);


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


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.headerLayout.headTitle.setText("New Event");
        binding.headerLayout.backPress.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);

    }


    //todo default data---
    private void setDefaults() {
        binding.headerLayout.add.setVisibility(View.GONE);
        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);
        categories.add("Repeat");
        categories.add("Once");
        categories.add("Daily");
        categories.add("Weekly");
        categories.add("Monthly");


        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(pass_date, myCalendar);
        };


        //todo set repeat drop down spinner here--
        eventTextSpinner = new EventTextSpinner(getActivity(), categories);
        binding.spinner.setAdapter(eventTextSpinner);
        binding.spinner.setDropDownVerticalOffset(60);

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

        binding.fromValue.setOnClickListener(v -> {
            Globals.enableAllCalenderDateSelect(getContext(), binding.fromValue);
            pass_date = binding.fromValue;

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
                        setAlarm();
                    }
                }, 12, 0, false
                );
                timePickerDialog.updateTime(t1hr, t1min);
                timePickerDialog.show();

            }

        });


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_press)
            getDialog().dismiss();
        else if (v.getId() == R.id.submit_button) {

            String title = binding.titleText.getText().toString().trim();
            String fromDate = binding.fromValue.getText().toString().trim();
            String location = binding.addLocationText.getText().toString().trim();
            String host = binding.hostText.getText().toString().trim();
            String partcipant = binding.participantValue.getText().toString().trim();
            String desc = binding.descriptionText.getText().toString().trim();
            String related = binding.relatedDocumentValue.getText().toString().trim();
            String time = binding.timeValue.getText().toString().trim();

            if (validation(title, fromDate, location, host, time)) {
                CreateCalenderActivityRequest activityRequest = new CreateCalenderActivityRequest();


                activityRequest.setSourceID(opportunityItem.getId());
                activityRequest.setTitle(title);
                activityRequest.setDescription(desc);
                activityRequest.setFrom(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(fromDate));
                activityRequest.setTo(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(fromDate));
                activityRequest.setEmp(Prefs.getString(Globals.EmployeeID, ""));
                activityRequest.setCreateTime(Globals.getTCurrentTime());
                activityRequest.setCreateDate(Globals.getTodaysDatervrsfrmt());
                activityRequest.setType("Event");
                activityRequest.setSourceType("Opportunity");
                activityRequest.setParticipants(partcipant);
                activityRequest.setComment("");
                activityRequest.setSubject("");
                activityRequest.setTime(Globals.getTCurrentTime());
                activityRequest.setDocument("");
                activityRequest.setRelatedTo("hi");
                activityRequest.setLocation(location);
                activityRequest.setHost(host);
                activityRequest.setAllday("false");
                activityRequest.setName(opportunityItem.getCustomerName());
                activityRequest.setProgressStatus("WIP");
                activityRequest.setPriority("low");
                activityRequest.setRepeated(repeated);
                activityRequest.setLeadType("");


                if (Globals.checkInternet(getContext())) {
                    binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                    callApi(activityRequest);
                }
            }
        }
    }

    private void callApi(CreateCalenderActivityRequest activityRequest) {

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().createnewevent(activityRequest);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.body().getStatus() == 200) {
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    Toasty.success(getContext(), "Add Successfully", Toast.LENGTH_LONG).show();

//                    fragment.onRefresh();

                    ActivityEvent_Fragment yourFragment = (ActivityEvent_Fragment) getActivity().getSupportFragmentManager().findFragmentByTag("");

                    if (yourFragment != null) {
                        yourFragment.onRefresh();
                    }

                    getDialog().dismiss();

                } else {
                    binding.loaderLayout.loader.setVisibility(View.GONE);
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
            public void onFailure(Call<EventResponse> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
        }  else if (time.isEmpty()) {
            binding.timeValue.setError(getResources().getString(R.string.time_error));
            return false;
        }
        return true;
    }


    private void updateLabel(TextView pass_date, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        pass_date.setText(sdf.format(myCalendar.getTime()));
    }


    private void setAlarm() {

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), NotificationPublisher.class);
        i.putExtra("value", getActivity().getResources().getString(R.string.meeting_notification));
        i.putExtra("title", getActivity().getResources().getString(R.string.meeting));
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, i, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, myTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


}
