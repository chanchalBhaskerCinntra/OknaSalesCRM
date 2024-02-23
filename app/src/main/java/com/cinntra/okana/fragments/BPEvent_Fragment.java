package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ActivityEventsAdapter;
import com.cinntra.okana.databinding.FragmentEventsBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.EventResponse;
import com.cinntra.okana.model.EventValue;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BPEvent_Fragment extends Fragment {

//     @BindView(R.id.eventList)
//     RecyclerView eventList;

    ArrayList<EventValue> alleventlist ;
    ActivityEventsAdapter adapter;
    String id;


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    FragmentEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentEventsBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.fragment_events, container, false);
      //  ButterKnife.bind(this,v);
    //    loadData();
//        callApi(id);


        return binding.getRoot();
    }
    private void callApi(String id) {
        alleventlist= new ArrayList<>();


        EventValue eventValue = new EventValue();
        eventValue.setEmp(Integer.valueOf(Prefs.getString(Globals.EmployeeID,"")));
        Call<EventResponse> call = NewApiClient.getInstance().getApiService().getallevent(eventValue);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {

                if(response.code()==200||response.body()!=null)
                {
                    if(response.body().getData().size()>0){
                        alleventlist.clear();
                        alleventlist.addAll(response.body().getData());
                    setAdapter();
                    }
                    else
                        Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
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
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }
    public ArrayList<EventValue> filter(String text ) {

        ArrayList<EventValue> templist= new ArrayList<>();
        templist.clear();
        for (EventValue st : alleventlist) {

            if(st.getType().equals(text)) {

                templist.add(st);

            }


        }

return templist;
    }
    private void setAdapter() {
        binding.eventList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        adapter = new ActivityEventsAdapter(getActivity(), filter("Event"));

       binding.eventList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    private ArrayList<EventValue> geEvents(ArrayList<EventValue> list)
          {

      ArrayList<EventValue> events = new ArrayList<>();
        for (EventValue event :list
             ) {


            if(event.getType()=="Task") {
                // &&Globals.CurrentSelectedDate.equalsIgnoreCase(event.getDateFrom())

                try {
                    String to = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Date FromDate = new SimpleDateFormat("yyyy-MM-dd").parse(event.getFrom().trim());
                    Date ToDate = new SimpleDateFormat("yyyy-MM-dd").parse(event.getTo().trim());
                    Date ToDayDate = new SimpleDateFormat("yyyy-MM-dd").parse(to);

                    boolean dateStatus = isDateInBetweenIncludingEndPoints(FromDate, ToDate, ToDayDate);



                    if (dateStatus)
                        events.add(event);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }

        return events;
    }

    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
        return !(date.before(min) || date.after(max));
    }


}