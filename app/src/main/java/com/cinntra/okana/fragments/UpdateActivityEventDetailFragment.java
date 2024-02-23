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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.ActivityAddEventDialogueLayoutBinding;
import com.cinntra.okana.databinding.FragmentAddEventBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.OpportunityModels.OppActivityUpdateResponse;
import com.cinntra.okana.model.OpportunityModels.UpdateActivityEventModel;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.receivers.NotificationPublisher;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivityEventDetailFragment extends Fragment implements View.OnClickListener {

    TextView pass_date;
    EventValue newEvent;

    String priority = "";
    String allday = "";
    String repeated = "";
    private AlarmManager alarmManager;
    private Calendar myTime;
    EventValue setEventData = new EventValue();
    int t1hr, t1min;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;

    ArrayList<String> categories = new ArrayList<>();
    ArrayList<Integer> circleimage = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();

    public UpdateActivityEventDetailFragment() {

    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    // TODO: Rename and change types and number of parameters
    public static UpdateActivityEventDetailFragment newInstance(String param1) {
        UpdateActivityEventDetailFragment fragment = new UpdateActivityEventDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            newEvent = (EventValue) b.getParcelable("View");

        }
    }

    ActivityAddEventDialogueLayoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = ActivityAddEventDialogueLayoutBinding.inflate(inflater, container, false);
        // ButterKnife.bind(this, v);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding.headerLayout.headTitle.setText("Update Event");
        binding.headerLayout.backPress.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);
        binding.headerLayout.ok.setOnClickListener(this);
        binding.headerLayout.add.setOnClickListener(this);

        binding.loaderLayout.loader.setVisibility(View.GONE);

//        setDisable();

        setDefaults();

        if (Globals.checkInternet(getContext()))
            callApi(newEvent.getId());


        return binding.getRoot();
    }

    private void callApi(Integer eventValue) {
        EventValue event = new EventValue();
        event.setId(eventValue);
        Call<EventResponse> call = NewApiClient.getInstance().getApiService().particularevent(event);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.code() == 200) {

                    setEventData = response.body().getData().get(0);
                    setData(setEventData);
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
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDefaults() {

        binding.submitButton.setText("Update");

        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);
        categories.add("Repeat");
        categories.add("Once");
        categories.add("Daily");
        categories.add("Monthly");
        categories.add("Weekly");


        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(pass_date, myCalendar);
        };


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

    //todo set default data from api--
    private void setData(EventValue setEventData) {
        binding.titleText.setText(setEventData.getTitle());
        binding.fromValue.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(setEventData.getFrom()));
        binding.timeValue.setText(setEventData.getTime());
        binding.addLocationText.setText(setEventData.getLocation());
        binding.hostText.setText(setEventData.getHost());
        binding.participantValue.setText(setEventData.getParticipants());
        binding.descriptionText.setText(setEventData.getDescription());
        binding.relatedDocumentValue.setText(setEventData.getRelatedTo());

        if (setEventData.getRepeated().equals(""))
            binding.simpleSwitch.setChecked(true);
        else
            binding.simpleSwitch.setChecked(false);

        binding.spinner.setSelection(getposition(categories, setEventData.getRepeated()));

        binding.simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeated = "";
                    binding.spinner.setVisibility(View.INVISIBLE);
                } else {
                    binding.spinner.setVisibility(View.VISIBLE);
//                    repeated = "Repeated";
                }
            }
        });


    }

    private int getpos(ArrayList<Integer> circleimage, String priority) {
        int pos = -1;
        for (Integer s : circleimage) {
            if (priority.equals("high"))
                pos = circleimage.indexOf(s);
            else if (priority.equals("medium"))
                pos = circleimage.indexOf(s);
            else
                pos = circleimage.indexOf(s);
        }
        return pos;
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

   /* private void setDisable() {
        title_text.setClickable(false);
        title_text.setFocusable(false);
        title_text.setFocusableInTouchMode(false);

        from_date_value.setClickable(false);
        from_date_value.setFocusable(false);
        from_date_value.setFocusableInTouchMode(false);


        time_value.setClickable(false);
        time_value.setFocusable(false);
        time_value.setFocusableInTouchMode(false);
        add_location_text.setClickable(false);
        add_location_text.setFocusableInTouchMode(false);
        add_location_text.setFocusable(false);

        host_text.setFocusable(false);
        host_text.setClickable(false);
        host_text.setFocusableInTouchMode(false);

        related_document_value.setFocusable(false);
        related_document_value.setClickable(false);
        related_document_value.setFocusableInTouchMode(false);

        description_text.setFocusable(false);
        description_text.setClickable(false);
        description_text.setFocusableInTouchMode(false);

        preority_spinner.setEnabled(false);
        upload_button.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);


    }*/

    private void setEnable() {
        binding.headerLayout.add.setVisibility(View.GONE);
        binding.headerLayout.ok.setVisibility(View.VISIBLE);
        binding.titleText.setClickable(true);
        binding.titleText.setFocusable(true);
        binding.titleText.setFocusableInTouchMode(true);

        binding.fromValue.setClickable(true);
        binding.fromValue.setFocusable(true);


        binding.timeValue.setClickable(true);
        binding.timeValue.setFocusable(true);

        binding.addLocationText.setClickable(true);
        binding.addLocationText.setFocusableInTouchMode(true);
        binding.addLocationText.setFocusable(true);

        binding.hostText.setFocusable(true);
        binding.hostText.setClickable(true);
        binding.hostText.setFocusableInTouchMode(true);

        binding.relatedDocumentValue.setFocusable(true);
        binding.relatedDocumentValue.setClickable(true);
        binding.relatedDocumentValue.setFocusableInTouchMode(true);

        binding.descriptionText.setFocusable(true);
        binding.descriptionText.setClickable(true);
        binding.descriptionText.setFocusableInTouchMode(true);

        binding.spinner.setEnabled(true);
        binding.uploadButton.setVisibility(View.VISIBLE);
        binding.uploadButton.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.add:
                setEnable();
                break;
            case R.id.ok:
            case R.id.submit_button:
                String title = binding.titleText.getText().toString().trim();
                String fromDate = binding.fromValue.getText().toString().trim();
                String location = binding.addLocationText.getText().toString().trim();
                String host = binding.hostText.getText().toString().trim();
                String desc = binding.descriptionText.getText().toString().trim();
                String related = binding.relatedDocumentValue.getText().toString().trim();
                String time = binding.timeValue.getText().toString().trim();
                String partcipant = binding.participantValue.getText().toString().trim();
                ArrayList<String> partcipantList = new ArrayList<>();
                partcipantList.add(partcipant);

                if (validation(title, fromDate, location, host, time)) {
                    UpdateActivityEventModel eventValue = new UpdateActivityEventModel();
                    eventValue.setOpp_Id(setEventData.getOppId());
                    eventValue.setTitle(title);
                    eventValue.setDescription(desc);
                    eventValue.setFrom(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(fromDate));
                    eventValue.setTo(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(fromDate));
                    eventValue.setEmp(setEventData.getEmp());
                    eventValue.setCreateTime(Globals.getTCurrentTime());
                    eventValue.setCreateDate(Globals.getTodaysDatervrsfrmt());
                    eventValue.setType("Event");
                    eventValue.setParticipants(partcipantList);
                    eventValue.setComment("");
                    eventValue.setSubject("");
                    eventValue.setTime(Globals.getTCurrentTime());
                    eventValue.setDocument("");
                    eventValue.setRelatedTo("hii");
                    eventValue.setLocation(location);
                    eventValue.setHost(host);
                    eventValue.setAllday("false");
                    eventValue.setName(setEventData.getName());
                    eventValue.setProgressStatus("WIP");
                    eventValue.setPriority("low");
                    eventValue.setRepeated(repeated);
                    eventValue.setId(String.valueOf(setEventData.getId()));


                    if (Globals.checkInternet(getContext())) {
                        callupdateApi(eventValue);
//                        setDisable();
                    }

                }
        }
    }


    //todo set update event api here---
    private void callupdateApi(UpdateActivityEventModel eventValue) {

        Call<OppActivityUpdateResponse> call = NewApiClient.getInstance().getApiService().updateOppEventActivity(eventValue);
        call.enqueue(new Callback<OppActivityUpdateResponse>() {
            @Override
            public void onResponse(Call<OppActivityUpdateResponse> call, Response<OppActivityUpdateResponse> response) {
                if (response.body().getStatus() == 200) {

                    Toast.makeText(getActivity(), "Updated Successfully.", Toast.LENGTH_SHORT).show();
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
                               String host,  String time) {
        if (title.isEmpty()) {
            binding.titleText.setError(getResources().getString(R.string.title_error));
            return false;
        } else if (fromDate.isEmpty()) {
            binding.fromValue.setError(getResources().getString(R.string.fromdate_error));
            return false;
        } else if (location.isEmpty()) {
            binding.addLocationText.setError(getResources().getString(R.string.location_error));
            return false;
        } else if (host.isEmpty()) {
            binding.hostText.setError(getResources().getString(R.string.host_error));
            return false;
        }else if (time.isEmpty()) {
            binding.timeValue.setError(getResources().getString(R.string.time_error));
            return false;
        }
        return true;
    }


    private void setAlarm() {

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), NotificationPublisher.class);
        i.putExtra("value", getActivity().getResources().getString(R.string.meeting_notification));
        i.putExtra("title", getActivity().getResources().getString(R.string.meeting));
        final int id = (int) System.currentTimeMillis();

        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, i, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, i, PendingIntent.FLAG_ONE_SHOT);
        }

//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, i, PendingIntent.FLAG_IMMUTABLE);//todo comment

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, myTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }


}
