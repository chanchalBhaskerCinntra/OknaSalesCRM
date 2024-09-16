package com.cinntra.okana.fragments;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.EventsTask_All_Adapter;
import com.cinntra.okana.databinding.FragmentAllEventTaskBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.NewEvent;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class All_Event_Task_Fragment extends Fragment {

    private ArrayList<EventValue> TaskEventList;

    NewOpportunityRespose opportunityItem;

    public All_Event_Task_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static All_Event_Task_Fragment newInstance(String param1, String param2) {
        All_Event_Task_Fragment fragment = new All_Event_Task_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            opportunityItem = (NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
        }
    }


    FragmentAllEventTaskBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding = FragmentAllEventTaskBinding.inflate(inflater, container, false);

        if (Globals.checkInternet(getContext())) {
            binding.loader.setVisibility(View.VISIBLE);
            callApi();
        }

        binding.addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllFilterDialog();
            }
        });


        return binding.getRoot();
    }


    private void showAllFilterDialog() {
        Dialog dialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.all_calender_popup_layout, null);
        dialog.setContentView(custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        AutoCompleteTextView acType = dialog.findViewById(R.id.acType);
        MaterialButton applyBtn = dialog.findViewById(R.id.applyBtn);
        ImageView ivCrossIcon = dialog.findViewById(R.id.ivCrossIcon);

        ivCrossIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        String[] type_list = {"Event", "Task"};

        List<String> TypeList_gl = Arrays.asList(type_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, TypeList_gl);
        acType.setAdapter(adapter);

        acType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acType.setText(TypeList_gl.get(position));

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.drop_down_textview, TypeList_gl);
                acType.setAdapter(adapter);

            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (acType.getText().toString().equalsIgnoreCase("Event")){
                    showEventDialog();
                }
                else {
                    showTaskDialog();
                }

                dialog.hide();
            }
        });


        dialog.show();


    }


    private void showTaskDialog() {
        Bundle b = new Bundle();
        b.putParcelable(Globals.OpportunityItem, opportunityItem);
        FragmentManager fm = getChildFragmentManager();
        AddTaskDialogue editNameDialogFragment = AddTaskDialogue.newInstance("Some Title");
        editNameDialogFragment.setArguments(b);
        editNameDialogFragment.show(fm, "");
    }


    private void showEventDialog() {
        Bundle b = new Bundle();
        b.putParcelable(Globals.OpportunityItem, opportunityItem);
        FragmentManager fm = getChildFragmentManager();
        AddEventDialogue editNameDialogFragment = AddEventDialogue.newInstance("Some Title");
        editNameDialogFragment.setArguments(b);
        editNameDialogFragment.show(fm, "");
    }


    private void callApi() {

        TaskEventList = new ArrayList<>();

        SalesEmployeeItem eventValue = new SalesEmployeeItem();
        eventValue.setEmp(Prefs.getString(Globals.EmployeeID, ""));
        eventValue.setDate(Globals.CurrentSelectedDate);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Emp", Prefs.getString(Globals.EmployeeID, ""));
        jsonObject.addProperty("date", Globals.CurrentSelectedDate);

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().getCalendarData(jsonObject);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getData().size() > 0) {
                        binding.noDatafound.setVisibility(View.GONE);
                        TaskEventList.clear();
                        TaskEventList.addAll(response.body().getData());
                        setAdapter();
                    } else {
                        binding.noDatafound.setVisibility(View.VISIBLE);
                    }
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
                binding.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }


    private ArrayList<NewEvent> geAllTasksEvents(ArrayList<NewEvent> list) {
        ArrayList<NewEvent> events = new ArrayList<>();
        for (NewEvent event : list
        ) {
            if (event.getType() == Globals.TYPE_EVENT) {
                try {
                    String to = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Date FromDate = new SimpleDateFormat("yyyy-MM-dd").parse(event.getDateFrom().trim());
                    Date ToDate = new SimpleDateFormat("yyyy-MM-dd").parse(event.getDateTo().trim());
                    Date ToDayDate = new SimpleDateFormat("yyyy-MM-dd").parse(to);

                    boolean dateStatus = isDateInBetweenIncludingEndPoints(FromDate, ToDate, ToDayDate);


                   /* Log.e("DATE From",""+event.getDateFrom().trim());
                    Log.e("DATE TO",""+event.getDateTo().trim());
                    Log.e("DATE TODAY",""+to);
                    Log.e("DATE STATUS",""+dateStatus);*/
                    if (dateStatus)
                        events.add(event);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (Globals.CurrentSelectedDate.equalsIgnoreCase(event.getDateFrom()))
                events.add(event);
        }

        return events;
    }



    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date) {
        return !(date.before(min) || date.after(max));
    }

    /********************** Manage List in local *******************************/


    private void setAdapter() {
        EventsTask_All_Adapter all_adapter;
        binding.taskEventList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        all_adapter = new EventsTask_All_Adapter(getActivity(), filter(TaskEventList));
//        all_adapter = new EventsTask_All_Adapter(getActivity(), TaskEventList);
        binding.taskEventList.setAdapter(all_adapter);
        //all_adapter.notifyDataSetChanged();


        if (all_adapter.getItemCount() == 0)
            binding.noDatafound.setVisibility(View.VISIBLE);
        else
            binding.noDatafound.setVisibility(View.GONE);


    }

    private ArrayList<EventValue> filter(ArrayList<EventValue> task_event_list) {
        ArrayList<EventValue> filterlist = new ArrayList<>();
        for (EventValue ev : task_event_list) {
            if (ev.getType().equalsIgnoreCase("Task") || ev.getType().equalsIgnoreCase("Event") || ev.getType().equalsIgnoreCase("Followup")) {
                filterlist.add(ev);
            }
        }
        return filterlist;

    }
}