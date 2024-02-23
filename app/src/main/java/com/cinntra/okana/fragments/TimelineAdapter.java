package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.CommentStage;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.CompleteStageResponse;
import com.cinntra.okana.model.OpportunityModels.OpportunityStageResponse;
import com.cinntra.okana.model.StagesValue;
import com.cinntra.okana.webservices.NewApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    Context context;
    List<StagesValue> itemsList;
    String currentstageno;
    FragmentRefresher fragmentRefresher;
    CommentStage commentStage;
    String selectedStage= "";

    public TimelineAdapter(Opportunity_Detail_NewFragment fra,Context context, List<StagesValue> itemsList, String currentStageNo) {
        this.context = context;
        this.commentStage = fra;
        this.itemsList = itemsList;
        this.currentstageno = currentStageNo;
        this.fragmentRefresher = fra;
    }

    @NonNull
    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.opportunity_timelineview, parent, false);
        return new TimelineAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.ViewHolder holder, int position) {

        StagesValue obj = getItem(position);

        holder.stage_name.setText(obj.getName());
        if(position==itemsList.size()-1){
            holder.divider.setVisibility(View.GONE);
        }
//        holder.date.setText(obj.get());
        if (obj.getStatus() ==1){
            holder.tick_green.setBackground(context.getResources().getDrawable(R.drawable.tick_square_blue));
            holder.divider.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
        }else if (obj.getStatus() ==0) {
            holder.tick_green.setBackground(context.getResources().getDrawable(R.drawable.tick_square_grey));
            holder.divider.setBackgroundColor(ContextCompat.getColor(context,R.color.lightGrey));
        }else if (obj.getStatus()==2){
            holder.tick_green.setBackground(context.getResources().getDrawable(R.drawable.tick_square_green));
            holder.divider.setBackgroundColor(ContextCompat.getColor(context,R.color.green));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               commentStage.stagecomment(obj.getStageno(),obj.getName());
                if(position==itemsList.size()-1 ){
                    if(obj.getStatus()==2){

                    }else
                    openfinaldialog(obj.getOppId());
                }else if (obj.getStatus()==0||obj.getStatus()==1){
                    opencommentdialog(obj.getStageno(),obj.getOppId());
                }
            }
        });

    }

    private void opencommentdialog(String stageno, Integer oppId) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.addcomment_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        Button done = dialog.findViewById(R.id.done);
        EditText comment = dialog.findViewById(R.id.comment);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.COMMENT = comment.getText().toString();
                if(!comment.getText().toString().trim().isEmpty()){
                callUpdatestageapi(stageno,oppId);
                dialog.cancel();
                }else{
                    Toast.makeText(context,"Please write some Comment",Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    private void callUpdatestageapi(String currentStageNo, Integer oppId) {
        StagesValue stval = new StagesValue();
        stval.setOppId(oppId);
        stval.setUpdateDate(Globals.getTodaysDate());
        stval.setUpdateTime(Globals.getTCurrentTime());
        stval.setStageno(currentStageNo);
        stval.setComment(Globals.COMMENT);
        stval.setFile("");
        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().updatestage(stval);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if(response.code()==200){
                    fragmentRefresher.onRefresh();
                    Log.e("pass","PASS");
                }
            }
            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure",""+t.getMessage());
            }
        });
    }

    private void openfinaldialog(Integer oppId) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.completestage_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        Button done = dialog.findViewById(R.id.save);
        Spinner previous_stage = dialog.findViewById(R.id.previous_stage);
        EditText comments_val = dialog.findViewById(R.id.comments_val);
        previous_stage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStage = previous_stage.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStage = previous_stage.getSelectedItem().toString();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!comments_val.getText().toString().trim().isEmpty()) {
                    CompleteStageResponse completeStageResponse = new CompleteStageResponse();
                    completeStageResponse.setOppId(Integer.valueOf(oppId));
                    completeStageResponse.setStatus(selectedStage);
                    completeStageResponse.setRemarks(comments_val.getText().toString());
                    completeStageResponse.setUpdateDate(Globals.getTodaysDate());
                    completeStageResponse.setUpdateTime(Globals.getTCurrentTime());
                    callcompletestageApi(completeStageResponse);
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }
    private void callcompletestageApi(CompleteStageResponse completeStageResponse) {


        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().completestage(completeStageResponse);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if(response.code()==200){
                    fragmentRefresher.onRefresh();

                }
            }
            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure",""+t.getMessage());
            }
        });
    }

    public StagesValue getItem(int po) {
        return itemsList.get(po);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView stage_name,date;
        ImageView tick_green;
        View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stage_name = itemView.findViewById(R.id.stage_name);
            date = itemView.findViewById(R.id.date);
            tick_green = itemView.findViewById(R.id.tick_green);
            divider = itemView.findViewById(R.id.divider);





        }
    }
}
