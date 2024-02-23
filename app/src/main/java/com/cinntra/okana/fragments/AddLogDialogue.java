package com.cinntra.okana.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.FragmentAddLogBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.NewEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



import static android.content.Context.MODE_PRIVATE;

public class AddLogDialogue  extends DialogFragment implements View.OnClickListener{

//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.submit_button)
//    Button submit;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.name_search)
//    SearchView name_search;
//    @BindView(R.id.related_to_search)
//    SearchView related_to_search;
//    @BindView(R.id.comments_val)
//    EditText comments_val;
//    @BindView(R.id.subject)
//    Spinner subject;
    private ArrayList<NewEvent> TaskEventList;
    public AddLogDialogue() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddLogDialogue newInstance(String title) {
        AddLogDialogue frag = new AddLogDialogue();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
    }

    FragmentAddLogBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding=FragmentAddLogBinding.inflate(inflater,container,false);
        View v = inflater.inflate(R.layout.fragment_add_log, container);
      //  ButterKnife.bind(this,v);
        binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        loadData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      binding.headerBottomroundEdit.headTitle.setText("New Log");
       binding.headerBottomroundEdit.backPress.setOnClickListener(this);
        binding.submitButton.setOnClickListener(this);

    }


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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back_press)
            getDialog().dismiss();
        else if(v.getId()==R.id.submit_button)
        {

            String comment      = binding.commentsVal.getText().toString().trim();
            String name   = binding.nameSearch.getQuery().toString().trim();
            String relatedto     = binding.relatedToSearch.getQuery().toString().trim();
            String subjectval   = binding.subject.getSelectedItem().toString().trim();

            NewEvent event = new NewEvent(name,null,null,true,"Repeate",null,
                    subjectval,"top",null,comment,relatedto,1,null);
            TaskEventList.add(event);

            saveData();
            getDialog().dismiss();

        }
    }


    private void saveData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(TaskEventList);
        editor.putString(Globals.TaskEventList, json);
        editor.apply();
        // Toast.makeText(getActivity(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }
}
