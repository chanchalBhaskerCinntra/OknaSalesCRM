package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.BottomSheetAdapter;
import com.cinntra.okana.databinding.CustomBottomSheetBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.ChangeTeam;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.ChatModel;
import com.cinntra.okana.model.ChatResponse;
import com.cinntra.okana.model.FollowUpData;
import com.cinntra.okana.model.FollowUpResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderSelectionSheet extends BottomSheetDialogFragment implements BottomSheetAdapter.ItemListener {
    Context context;

    FragmentRefresher fragmentRefresher;
    ChangeTeam changeTeam;

    LeadFollowUpFragment Refresher;
    CustomBottomSheetBinding binding;


    int id;
    String name;

    public ReminderSelectionSheet(int id, String name) {
        // this.changeTeam = dashboard;
        this.id = id;
        this.name = name;
    }

    public ReminderSelectionSheet(int id, String name, LeadFollowUpFragment Refresher) {
        // this.changeTeam = dashboard;
        this.id = id;
        this.name = name;
        this.Refresher = Refresher;
        fragmentRefresher = (FragmentRefresher) Refresher;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = CustomBottomSheetBinding.inflate(inflater, container, false);
        //  View v = inflater.inflate(R.layout.custom_bottom_sheet, container, false);

        //   ButterKnife.bind(this,v);
        // fragmentRefresher =(FragmentRefresher)getActivity();
        context = getContext();
        if (Refresher != null) {
            reminderDialog(id, name);
            dismiss();
        }


        binding.btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderDialog(id, name);
                dismiss();
            }
        });
        binding.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackDialog(id, name);
                dismiss();
            }
        });
        return binding.getRoot();
    }


    @Override
    public void onClickAt(int position) {
      /*  Prefs.putString(Globals.SalesEmployeeCode,Dashboard.teamList_Hearchi.get(position).getSalesEmployeeCode());
        Prefs.putString(Globals.SalesEmployeeName,Dashboard.teamList_Hearchi.get(position).getSalesEmployeeName());

        Globals.TeamEmployeeID = Dashboard.teamList_Hearchi.get(position).getId().toString();
        Prefs.putString(Globals.Role,Dashboard.teamList_Hearchi.get(position).getRole());
        changeTeam.changeTeam(Dashboard.teamList_Hearchi.get(position).getSalesEmployeeName());
        fragmentRefresher.onRefresh();*/
        dismiss();
    }


    private void feedbackDialog(Integer id, String companyName) {
        final String[] comm_Mode = {""};
        final Dialog dialog = new Dialog(getContext());
        // dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        // dialog.setTitle("FollowUp FeedBack");
        dialog.setContentView(R.layout.followup_dialog);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        EditText date_value = dialog.findViewById(R.id.date_value);
        EditText time_value = dialog.findViewById(R.id.time_value);
        EditText comment_value = dialog.findViewById(R.id.comment_value);
        TextView txtDate = dialog.findViewById(R.id.txtDate);
        RelativeLayout viewDate = dialog.findViewById(R.id.viewDate);
        TextView txtTime = dialog.findViewById(R.id.txtTime);
        RelativeLayout viewTime = dialog.findViewById(R.id.viewTime);
        Button add = dialog.findViewById(R.id.add);
        TextView title = dialog.findViewById(R.id.title);
        Spinner communication_spinner = dialog.findViewById(R.id.communication_spinner);
        TextView communication_txt = dialog.findViewById(R.id.communication_txt);
        communication_spinner.setVisibility(View.VISIBLE);
        communication_txt.setVisibility(View.VISIBLE);
        title.setText("Feedback");
        txtDate.setVisibility(View.GONE);
        viewDate.setVisibility(View.GONE);
        txtTime.setVisibility(View.GONE);
        viewTime.setVisibility(View.GONE);

        communication_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comm_Mode[0] = communication_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = date_value.getText().toString().trim();
                String time = time_value.getText().toString().trim();
                String comment = comment_value.getText().toString().trim();
                if (validation("NoNeed", "NoNeed", comment)) {
                   /* FollowUpData followUpData = new FollowUpData();
                    followUpData.setSubject(companyName);
                    followUpData.setSourceID(id.toString());
                    followUpData.setSourceType("Lead");
                    followUpData.setComment(comment);
                    followUpData.setType("Followup");
                    followUpData.setComm_mode(comm_Mode[0]);
                    followUpData.setCreateDate(Globals.getTodaysDate());
                    followUpData.setCreateTime(Globals.getTCurrentTime());
                    followUpData.setEmp(Integer.parseInt(Prefs.getString(Globals.EmployeeID,"")));
                    followUpData.setEmpName(Prefs.getString(Globals.Employee_Name,""));
                    callfollowupApi(followUpData);*/


                    ChatModel chatModel = new ChatModel();
                    chatModel.setMessage(comment);
                    chatModel.setEmpId(Prefs.getString(Globals.EmployeeID, ""));
                    chatModel.setEmpName(Prefs.getString(Globals.USER_NAME, ""));
                    chatModel.setOppId(id.toString());
//                    chatModel.setUpdateDate(new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(new Date()));
                    chatModel.setUpdateDate(Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(date));
                    chatModel.setUpdateTime(new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date()));
                    chatModel.setSourceType("Lead");
                    chatModel.setComm_mode(comm_Mode[0]);

                    callcreateApi(chatModel);

                    dialog.cancel();


                    //Globals.hideKeybaord(sendmessage_text,getContext());


                }


            }
        });


        dialog.show();
    }


    private void reminderDialog(Integer id, String companyName) {
        final String[] comm_Mode = {""};
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        //dialog.setTitle("FollowUp Reminder");
        dialog.setContentView(R.layout.followup_dialog);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        EditText date_value = dialog.findViewById(R.id.date_value);
        EditText time_value = dialog.findViewById(R.id.time_value);
        EditText comment_value = dialog.findViewById(R.id.comment_value);
        Button add = dialog.findViewById(R.id.add);
        Spinner communication_spinner = dialog.findViewById(R.id.communication_spinner);
        TextView communication_txt = dialog.findViewById(R.id.communication_txt);
        communication_spinner.setVisibility(View.VISIBLE);
        communication_txt.setVisibility(View.VISIBLE);
        TextView title = dialog.findViewById(R.id.title);
        title.setText("Follow Up");


        communication_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comm_Mode[0] = communication_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                comm_Mode[0] = communication_spinner.getSelectedItem().toString();
            }
        });

        date_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.disablePastSelectDate(dialog.getContext(), date_value);

            }
        });


        time_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.selectTime(context, time_value);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = Globals.convert_dd_MM_yyyy_to_yyyy_MM_dd(date_value.getText().toString().trim());
                String time = time_value.getText().toString().trim();
                String comment = comment_value.getText().toString().trim();
                if (validation(date, time, comment)) {
                    FollowUpData followUpData = new FollowUpData();
                    followUpData.setSubject(companyName);
                    followUpData.setSourceID(id.toString());
                    followUpData.setSourceType("Lead");
                    followUpData.setComment(comment);
                    followUpData.setFrom(date);
                    followUpData.setTime(time);
                    followUpData.setComm_mode(comm_Mode[0]);
                    followUpData.setType("Followup");
                    followUpData.setCreateDate(Globals.getTodaysDatervrsfrmt());
                    followUpData.setCreateTime(Globals.getTCurrentTime());
                    followUpData.setLeadType("");
                    followUpData.setEmp(Integer.parseInt(Prefs.getString(Globals.EmployeeID, "")));
                    followUpData.setEmpName(Prefs.getString(Globals.Employee_Name, ""));

                    callfollowupApi(followUpData);
                    dialog.cancel();
                }


            }
        });


        dialog.show();
    }


    private boolean validation(String date, String time, String comment) {
        if (date.length() == 0) {
            Toast.makeText(context, "Enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (time.length() == 0) {
            Toast.makeText(context, "Enter time", Toast.LENGTH_SHORT).show();

            return false;

        } else if (comment.length() == 0) {
            Toast.makeText(context, "Enter comment", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }


    private void callfollowupApi(FollowUpData followUpData) {

        Call<FollowUpResponse> call = NewApiClient.getInstance().getApiService().createfollowUP(followUpData);
        call.enqueue(new Callback<FollowUpResponse>() {
            @Override
            public void onResponse(Call<FollowUpResponse> call, Response<FollowUpResponse> response) {

                if (response.code() == 200) {
                    if (fragmentRefresher != null)
                        fragmentRefresher.onRefresh();
                    Toast.makeText(context, "Created Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FollowUpResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void callcreateApi(ChatModel chatModel) {

        Call<ChatResponse> call = NewApiClient.getInstance().getApiService().createChat(chatModel);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {

                if (response.body() != null) {
                    //callApi(opportunityItem.getId());
                    Globals.showSuccessMessage(context, "Create Successfully ! ");
                    if (fragmentRefresher != null)
                        fragmentRefresher.onRefresh();
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {

            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }

}