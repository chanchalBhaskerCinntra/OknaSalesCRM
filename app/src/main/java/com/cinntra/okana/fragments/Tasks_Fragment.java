package com.cinntra.okana.fragments;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.adapters.TasksAdapter;
import com.cinntra.okana.databinding.FragmentTasksBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.NewEvent;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;



public class Tasks_Fragment extends Fragment {


//    @BindView(R.id.taskList)
//    RecyclerView taskList;
//    @BindView(R.id.loader)
//    SpinKitView loader;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
    public Tasks_Fragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static Tasks_Fragment newInstance(String param1, String param2) {
        Tasks_Fragment fragment = new Tasks_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    FragmentTasksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentTasksBinding.inflate(getLayoutInflater());
        View v=inflater.inflate(R.layout.fragment_tasks, container, false);
        //ButterKnife.bind(this,v);

       // loadData();
        callApi();
       return binding.getRoot();
    }
    private void callApi() {
        TaskEventList= new ArrayList<>();
       binding.loader .setVisibility(View.VISIBLE);

        SalesEmployeeItem eventValue = new SalesEmployeeItem();
        eventValue.setEmp(Prefs.getString(Globals.EmployeeID,""));
        eventValue.setDate(Globals.CurrentSelectedDate);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Emp", Prefs.getString(Globals.EmployeeID, ""));
        jsonObject.addProperty("date", Globals.CurrentSelectedDate);

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().getCalendarData(jsonObject);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {

                if(response.code()==200||response.body()!=null)
                {
                    if(response.body().getData().size()>0){
                        binding.noDatafound.setVisibility(View.GONE);
                        TaskEventList.clear();
                        TaskEventList.addAll(response.body().getData());
                        binding.loader.setVisibility(View.GONE);
                        setAdapter();
                    }
                    else {
                        binding.noDatafound.setVisibility(View.VISIBLE);
                        binding.loader.setVisibility(View.GONE);
                    }
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(getActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                   binding. loader.setVisibility(View.GONE);
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
               binding. loader.setVisibility(View.GONE);
            }

        });

    }


    private void setAdapter() {

       binding.taskList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.taskList.setAdapter(new TasksAdapter(getActivity(), filter("Task")));
        if(filter("Task").size()==0){
            binding.noDatafound.setVisibility(View.VISIBLE);
        }else {
            binding.noDatafound.setVisibility(View.GONE);
        }

    }
    public ArrayList<EventValue> filter(String text ) {

        ArrayList<EventValue> templist= new ArrayList<>();
        templist.clear();
        for (EventValue st : TaskEventList) {

            if(st.getType().equals(text)) {

                templist.add(st);

            }


        }

        return templist;
    }
    private ArrayList<NewEvent> geTasks(ArrayList<NewEvent> list)
        {
        ArrayList<NewEvent> events = new ArrayList<>();
        for (NewEvent event :list
        ) {
            if(event.getType()==Globals.TYPE_TASK&&Globals.CurrentSelectedDate.equalsIgnoreCase(event.getDateFrom()))
                events.add(event);
        }

        return events;
    }


    /********************** Manage List in local *******************************/
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

        /*taskList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        taskList.setAdapter(new TasksAdapter(getActivity(), geTasks(TaskEventList)));*/

    }











}