package com.cinntra.okana.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.fragments.ChatterFragment;
import com.cinntra.okana.fragments.Open_Opprtunity_Fragment;
import com.cinntra.okana.fragments.Opportunity_Detail_NewFragment;
import com.cinntra.okana.fragments.PastGeneral;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.FollowUpData;
import com.cinntra.okana.model.FollowUpResponse;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.UpdateFavourites;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.like.LikeButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class OpenOpportunities_Adapter extends RecyclerView.Adapter<OpenOpportunities_Adapter.ViewHolder> {
    Context context;
    List<NewOpportunityRespose> itemsList;
    boolean click = true;
    FragmentRefresher fragmentRefresher;

    public OpenOpportunities_Adapter(Open_Opprtunity_Fragment open_opprtunity_fragment, Context context, List<NewOpportunityRespose> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        this.tempList = new ArrayList<NewOpportunityRespose>();
  /* this.sampleData = new ArrayList<OpportunityItem>();
   this.sampleData.addAll(itemsList);*/
        this.tempList.addAll(itemsList);
        this.fragmentRefresher = open_opprtunity_fragment;
    }

    public OpenOpportunities_Adapter(PastGeneral open_opprtunity_fragment, Context context, List<NewOpportunityRespose> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        this.tempList = new ArrayList<NewOpportunityRespose>();
  /* this.sampleData = new ArrayList<OpportunityItem>();
   this.sampleData.addAll(itemsList);*/
        this.tempList.addAll(itemsList);
        this.fragmentRefresher = open_opprtunity_fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.opportunity_new_screen, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewOpportunityRespose obj = getItem(position);
        holder.item_title.setText(obj.getOpportunityName());
        if (obj.getUType().size() > 0)
            holder.item_date.setText("Type : " + obj.getUType().get(0).getType());
        else
            holder.item_date.setText("Type : N/A");
        holder.price.setText(" : " + obj.getULsource());
        holder.name.setText("ID : Opp-" + obj.getId());
        holder.mode1.setText(obj.getCurrentStageName());

       /* if(obj.getUFav().equalsIgnoreCase("Y")){
            holder.star_button.setLiked(true);

        }else{
            holder.star_button.setLiked(false);
        }
*/
        holder.follow_up.setVisibility(View.GONE);
        holder.follow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFollowUpdialog(obj.getId());
            }
        });



/*
        holder.star_button.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);
                UpdateFavourites obj1 = new UpdateFavourites();
                obj1.setU_FAV("Y");
                obj1.setId(obj.getId());
                updatefavourites(obj1);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
                UpdateFavourites obj1 = new UpdateFavourites();
                obj1.setU_FAV("N");
                obj1.setId(obj.getId());
                updatefavourites(obj1);
            }
        });
*/
        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Globals.OpportunityItem, obj);
                ChatterFragment chatterFragment = new ChatterFragment();
                chatterFragment.setArguments(bundle);
                FragmentTransaction chattransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                chattransaction.replace(R.id.quatoes_main_container, chatterFragment);
                chattransaction.addToBackStack("Back");
                chattransaction.commit();

            }

        });
// holder.price_percent.setText(Globals.convertDecemal(obj.getClosingPercentage()));


    }

    private void openFollowUpdialog(String id) {

        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("Follow Up");
        dialog.setContentView(R.layout.followup_dialog);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        EditText date_value = dialog.findViewById(R.id.date_value);
        EditText time_value = dialog.findViewById(R.id.time_value);
        Button add = dialog.findViewById(R.id.add);
        EditText comment_value = dialog.findViewById(R.id.comment_value);

        date_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.selectDate(dialog.getContext(), date_value);

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
                String date = date_value.getText().toString().trim();
                String time = time_value.getText().toString().trim();
                String comment = comment_value.getText().toString().trim();
                if (validation(date, time, comment)) {
                    FollowUpData followUpData = new FollowUpData();
                    followUpData.setSourceID(id);
                    followUpData.setSourceType("Opportunity");
                    followUpData.setComment(comment);
                    followUpData.setFrom(date);
                    followUpData.setTime(time);
                    followUpData.setType("Followup");
                    followUpData.setCreateDate(Globals.getTodaysDate());
                    followUpData.setCreateTime(Globals.getTCurrentTime());
                    followUpData.setEmp(Integer.parseInt(Prefs.getString(Globals.EmployeeID, "")));
                    followUpData.setEmpName(Prefs.getString(Globals.Employee_Name, ""));
                    callfollowupApi(followUpData);
                    dialog.cancel();
                }

            }
        });


        dialog.show();
    }

    private void callfollowupApi(FollowUpData followUpData) {

        Call<FollowUpResponse> call = NewApiClient.getInstance().getApiService().createfollowUP(followUpData);
        call.enqueue(new Callback<FollowUpResponse>() {
            @Override
            public void onResponse(Call<FollowUpResponse> call, Response<FollowUpResponse> response) {

                if (response.code() == 200) {
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

    public NewOpportunityRespose getItem(int po) {
        return itemsList.get(po);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void Typefilter(String name) {
        itemsList.clear();
        if (name.length() == 0) {
            itemsList.addAll(tempList);
        } else {
            for (NewOpportunityRespose bde : tempList) {
                if (name.trim().equalsIgnoreCase(bde.getUType().get(0).getType())) {
                    itemsList.add(bde);
                }
            }
        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_title, price, item_date, quanity, name, mode1, follow_up;
        ImageView star, chat;
        LikeButton star_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_date = itemView.findViewById(R.id.item_date);
            quanity = itemView.findViewById(R.id.quanity);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            star = itemView.findViewById(R.id.star);
            mode1 = itemView.findViewById(R.id.mode1);
            star_button = itemView.findViewById(R.id.star_button);
            chat = itemView.findViewById(R.id.chat);
            follow_up = itemView.findViewById(R.id.follow_up);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Prefs.getString(Globals.SelectOpportnity, "").equalsIgnoreCase("Quotation") ||
                            Prefs.getString(Globals.SelectOpportnity, "").equalsIgnoreCase("Add_Order")) {
                        Globals.opp = itemsList.get(getAdapterPosition());
                        Bundle b = new Bundle();
                        b.putParcelable(Globals.OpportunityItemData, itemsList.get(getAdapterPosition()));
                        Intent intent = new Intent();
                        intent.putExtras(b);
                        ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                        ((AppCompatActivity) context).finish();

                    } else {
                        Bundle b = new Bundle();
                        b.putParcelable(Globals.OpportunityItem, itemsList.get(getAdapterPosition()));
//          Opportunity_Update_Fragment fragment = new Opportunity_Update_Fragment();
                        Opportunity_Detail_NewFragment fragment = new Opportunity_Detail_NewFragment();
                        fragment.setArguments(b);
                        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.quatoes_main_container, fragment);
                        transaction.addToBackStack("Back");
                        transaction.commit();
                    }

                }
            });


        }
    }


    List<NewOpportunityRespose> tempList = null;

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        itemsList.clear();
        if (charText.length() == 0) {
            itemsList.addAll(tempList);
        } else {
            for (NewOpportunityRespose st : tempList) {
                /*if(st.getOpportunityName()!=null&&!st.getOpportunityName().isEmpty()) {
                    if (st.getCustomerName().toLowerCase().trim().contains(charText)) {*/
                if (st.getCardCode() != null && !st.getCardCode().isEmpty() && st.getOpportunityName() != null && !st.getOpportunityName().isEmpty()) {
                    if (st.getCardCode().toLowerCase().trim().contains(charText) || st.getOpportunityName().toLowerCase().trim().contains(charText)) {
                        itemsList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void AllData() {
        itemsList.clear();
        itemsList.addAll(tempList);
        notifyDataSetChanged();
    }

    public void AllData(List<NewOpportunityRespose> tmp) {
        tempList.clear();
        tempList.addAll(tmp);
        notifyDataSetChanged();
    }

    private void updatefavourites(UpdateFavourites in) {
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().updateoppFavorite(in);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                assert response.body() != null;
                if (response.code() == 200) {
                    fragmentRefresher.onRefresh();
                }

            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Favfilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault()).trim();
        itemsList.clear();
        if (charText.length() == 0) {
            itemsList.addAll(tempList);
        } else {
            for (NewOpportunityRespose st : tempList) {

                if (st.getUFav() != null && !st.getUFav().isEmpty()) {
                    if (st.getUFav().toLowerCase().trim().equalsIgnoreCase(charText)) {
                        itemsList.add(st);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void sourcefilter(ArrayList<String> sourcechecklist) {
        itemsList.clear();
        if (sourcechecklist.size() == 0) {
            itemsList.addAll(tempList);

        } else {
            for (NewOpportunityRespose lv : tempList) {
                if (sourcechecklist.contains(lv.getSource())) {
                    itemsList.add(lv);
                }
            }
        }
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ValidDate(LocalDate afterdate, LocalDate dateObj) {
        itemsList.clear();
        for (NewOpportunityRespose st : tempList) {

            if (st.getPredictedClosingDate() != null && !st.getPredictedClosingDate().isEmpty()) {
                String sDate1 = st.getPredictedClosingDate();
                LocalDate date1 = LocalDate.parse(sDate1);
                if (date1.isBefore(afterdate) && date1.isAfter(dateObj)) {
                    Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show();
                    itemsList.add(st);
                }
            }
        }
        notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pipelinefilter(LocalDate afterdate1, LocalDate dateObj1) {
        itemsList.clear();
        for (NewOpportunityRespose st : tempList) {

            if (st.getStartDate() != null && !st.getStartDate().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String sDate1 = st.getStartDate();
                LocalDate date1 = LocalDate.parse(sDate1, formatter);
                if (date1.isAfter(afterdate1) || date1.isEqual(dateObj1)) {
                    itemsList.add(st);
                }
            }
        }
        notifyDataSetChanged();
    }

}
