package com.cinntra.okana.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.SelectedEmployeeForEventAdapter;
import com.cinntra.okana.databinding.FragmentAddEventBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.CreateCalenderActivityRequest;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SaleEmployeeResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.receivers.NotificationPublisher;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventDialogue extends DialogFragment implements View.OnClickListener {

    TextView pass_date;
    String priority = "";
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

    FragmentAddEventBinding binding;


    public AddEventDialogue() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddEventDialogue newInstance(String title) {
        AddEventDialogue frag = new AddEventDialogue();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddEventBinding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.fragment_add_event, container);
        // ButterKnife.bind(this,v);
        setDefaults();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.headerLayout.headTitle.setText("New Event");
        binding.headerLayout.backPress.setOnClickListener(this);
        binding.eventContent.submitButton.setOnClickListener(this);
        binding.loaderLayout.loader.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_press)
            getDialog().dismiss();
        else if (v.getId() == R.id.submit_button) {
            String title = binding.eventContent.titleText.getText().toString().trim();
//            String fromDate = binding.eventContent.fromValue.getText().toString().trim();
            String toDate = binding.eventContent.dateValue.getText().toString().trim();
            String desc = binding.eventContent.descriptionText.getText().toString().trim();
            String related = binding.eventContent.relatedDocumentValue.getText().toString().trim();
            String time = binding.eventContent.timeValue.getText().toString().trim();


            if (validation(title, toDate,  desc, time)) {
                CreateCalenderActivityRequest activityRequest = new CreateCalenderActivityRequest();
                // Concatenate the elements of the ArrayList into integer arraylist

                activityRequest.setParticipants("");
                activityRequest.setTitle(title);
                activityRequest.setSourceID("0");
                activityRequest.setDescription(desc);
                activityRequest.setFrom("");
                activityRequest.setTo(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(toDate));
                activityRequest.setEmp(Prefs.getString(Globals.EmployeeID, ""));
                activityRequest.setCreateTime(Globals.getTCurrentTime());
                activityRequest.setCreateDate(Globals.getTodaysDatervrsfrmt());
                activityRequest.setType("Event");
                activityRequest.setSourceType("Opportunity");
                activityRequest.setComment("");
                activityRequest.setSubject("");
                activityRequest.setTime(time);
                activityRequest.setDocument("");
                activityRequest.setRelatedTo("hi");//related
                activityRequest.setLocation(""); //location
                activityRequest.setHost("");
                activityRequest.setAllday("false");//allday
                activityRequest.setName("");
                activityRequest.setProgressStatus("WIP");
                activityRequest.setPriority("low");//priority
                activityRequest.setRepeated(""); //repeated
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

    private boolean validation(String title, String toDate, String desc, String time) {
        if (title.isEmpty()) {
            binding.eventContent.titleText.setError(getResources().getString(R.string.title_error));
            return false;
        }  else if (toDate.isEmpty()) {
            binding.eventContent.dateValue.setError(getResources().getString(R.string.date_error));
            return false;
        } else if (desc.isEmpty()) {
            binding.eventContent.descriptionText.setError(getResources().getString(R.string.description_error));
            return false;
        } else if (time.isEmpty()) {
            binding.eventContent.timeValue.setError(getResources().getString(R.string.time_error));
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


        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(pass_date, myCalendar);
        };


        binding.eventContent.dateValue.setOnClickListener(v -> {

            Globals.disablePastSelectDate(getContext(), binding.eventContent.dateValue);


        });


        binding.eventContent.timeValue.setOnClickListener(new View.OnClickListener() {
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
                        binding.eventContent.timeValue.setText(DateFormat.format("hh:mm aa", myTime));
                        setAlarm();
                    }
                }, 12, 0, false
                );
                timePickerDialog.updateTime(t1hr, t1min);
                timePickerDialog.setMessage(binding.eventContent.timeValue.getHint().toString());
                timePickerDialog.show();

            }

        });


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
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, i, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getBroadcast(getActivity(), id, i, PendingIntent.FLAG_ONE_SHOT);
        }
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, i, PendingIntent.FLAG_IMMUTABLE);//todo comment
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, myTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }



}
