package com.cinntra.okana.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ActivityLogAdapter;
import com.cinntra.okana.databinding.FragmentLogBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.NewEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



import static android.content.Context.MODE_PRIVATE;

public class Log_Fragment extends Fragment {

//    @BindView(R.id.eventList)
//    RecyclerView eventList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    FragmentLogBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentLogBinding.inflate(getLayoutInflater());
        View v=inflater.inflate(R.layout.fragment_log, container, false);
      //  ButterKnife.bind(this,v);
        loadData();

        return binding.getRoot();
    }

    private ArrayList<NewEvent> TaskEventList;
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

      binding.  eventList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
       binding. eventList.setAdapter(new ActivityLogAdapter(getActivity(), TaskEventList));
    }

}
