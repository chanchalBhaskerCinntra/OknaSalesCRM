package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.MessageAdapter;
import com.cinntra.okana.databinding.ChatterfragmentBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.ChatModel;
import com.cinntra.okana.model.ChatResponse;
import com.cinntra.okana.model.FollowUpData;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatterFragment extends Fragment implements View.OnClickListener {

//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.sendmessage_text)
//    EditText sendmessage_text;
//    @BindView(R.id.send)
//    ImageView send;
//    @BindView(R.id.no_datafound)
//    ImageView no_datafound;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
    String EmpId;
    ArrayList<ChatModel> messagelist = new ArrayList<>();
    NewOpportunityRespose opportunityItem;
    ChatterfragmentBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            opportunityItem =(NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ChatterfragmentBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.chatterfragment, container, false);
     //   ButterKnife.bind(this,v);
        binding.headerFrame.headTitle.setText("Chat");
        EmpId =  Prefs.getString(Globals.EmployeeID, "");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        if(Globals.checkInternet(getContext()))
        {

            callApi(opportunityItem.getId());
        }
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showBottomSheet(lv);

                if(binding.sendmessageText.getText().toString().trim().length()>0){
                    ChatModel chatModel = new ChatModel();
                    chatModel.setMessage(binding.sendmessageText.getText().toString().trim());
                    chatModel.setEmpId(Prefs.getString(Globals.EmployeeID,""));
                    chatModel.setEmpName(Prefs.getString(Globals.USER_NAME,""));
                    chatModel.setOppId(opportunityItem.getId());
                    chatModel.setUpdateDate(new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(new Date()));
                    chatModel.setUpdateTime(new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date()));
                    chatModel.setSourceType("Opportunity");
                    chatModel.setComm_mode("");
                    callcreateApi(chatModel);
                    Globals.hideKeybaord(binding.sendmessageText,getContext());
                    binding.sendmessageText.setText("");

                }
            }
        });
        binding.headerFrame.backPress.setOnClickListener(this);


        return binding.getRoot();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_press:
                getActivity().onBackPressed();
                break;

        }
    }

    private void callApi(String sequentialNo) {
       /* StagesValue opportunityValue = new StagesValue();
        opportunityValue.setOppId(Integer.valueOf(sequentialNo));*/
        FollowUpData followUpData = new FollowUpData();
        followUpData.setEmp(Integer.parseInt(Prefs.getString(Globals.EmployeeID,"")));
        followUpData.setSourceType("Opportunity");
        followUpData.setSourceID(sequentialNo);
        Call<ChatResponse> call = NewApiClient.getInstance().getApiService().getAllLeadChat(followUpData);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {

                if(response.body()!=null){
                    messagelist.clear();
                    messagelist.addAll(response.body().getData());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    MessageAdapter messageAdapter = new MessageAdapter(getContext(),messagelist);
                   layoutManager.setStackFromEnd(true);
                   // recyclerView.smoothScrollToPosition(recyclerView.getBottom());
                    binding.recyclerView.setLayoutManager(layoutManager);
                    binding.recyclerView.setAdapter(messageAdapter);

                    messageAdapter.notifyDataSetChanged();
                    if(messageAdapter.getItemCount()>0){
                        binding.noDatafound.setVisibility(View.GONE);
                    }else{
                        binding.noDatafound.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {

            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }

    private void callcreateApi(ChatModel chatModel) {

        Call<ChatResponse> call = NewApiClient.getInstance().getApiService().createChat(chatModel);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {

                if(response.body()!=null){
                    callApi(opportunityItem.getId());
                }
            }
            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {

            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }


    private void showBottomSheet(int id,String name)
    {

        ReminderSelectionSheet bottomSheetFragment = new ReminderSelectionSheet(id,name);
        bottomSheetFragment.show(((AppCompatActivity)getActivity()).getSupportFragmentManager(), bottomSheetFragment.getTag());

    }
}
