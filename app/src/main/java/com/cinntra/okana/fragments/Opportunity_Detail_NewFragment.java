package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOrderAct;
import com.cinntra.okana.activities.AddQuotationAct;
import com.cinntra.okana.activities.QuotationActivity;
import com.cinntra.okana.databinding.OpportunityDetailNewscreenBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.CommentStage;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.CompleteStageResponse;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.OpportunityModels.OpportunityItem;
import com.cinntra.okana.model.OpportunityModels.OpportunityStageResponse;
import com.cinntra.okana.model.StagesValue;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.newapimodel.OpportunityValue;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Opportunity_Detail_NewFragment extends Fragment implements View.OnClickListener, FragmentRefresher, CommentStage {


    boolean bottomView = false;
    String selectedStage = "";
    LinearLayoutManager layoutManager;
    NewOpportunityRespose opportunityItem;
    TimelineAdapter adapter;
    List<StagesValue> arraylist = new ArrayList<StagesValue>();
    PreviousstageAdpater previousstageadapter;
    List<NewOpportunityRespose> particularoppdata = new ArrayList<>();
    String pos;

    OpportunityDetailNewscreenBinding binding;

    public Opportunity_Detail_NewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            opportunityItem = (NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
            Globals.opp = opportunityItem;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = OpportunityDetailNewscreenBinding.inflate(inflater, container, false);
        // View v=inflater.inflate(R.layout.opportunity_detail_newscreen, container, false);
        // ButterKnife.bind(this,v);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding.loader.setVisibility(View.VISIBLE);


        if (Globals.checkInternet(getContext())) {

            callApi(opportunityItem.getId());
        }

        manageclickevent();
        return binding.getRoot();
    }

    private void callApi(String sequentialNo) {
        OpportunityValue opportunityValue = new OpportunityValue();
        opportunityValue.setId(Integer.valueOf(sequentialNo));
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().getparticularopportunity(opportunityValue);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {

                if (response.body() != null) {
                    particularoppdata.clear();
                    particularoppdata.add(response.body().getData().get(0));
                    setData(response.body().getData().get(0));
                }
            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }

    private void callStagesApi(String opp_id) {
        OpportunityItem oppitem = new OpportunityItem();
        oppitem.setOpp_Id(opp_id);
        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().getAllStages(oppitem);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.GONE);
                    if (response.body().getData().size() > 0) {
                        if (response.body().getData().get(response.body().getData().size() - 1).getStatus() == 2) {
                            binding.markascomplete.setVisibility(View.GONE);
                            binding.addStage.setVisibility(View.GONE);
                        } else if (response.body().getData().get(response.body().getData().size() - 1).getStatus() == 1) {
                            binding.markascomplete.setText("Complete");
                        } else {
                            binding.markascomplete.setVisibility(View.VISIBLE);
                            binding.addStage.setVisibility(View.VISIBLE);
                            binding.markascomplete.setText("Mark as Complete");
                        }
                        callstagecommentApi(opportunityItem.getCurrentStageNumber());
                        arraylist.clear();
                        arraylist.addAll(response.body().getData());
                        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                        adapter = new TimelineAdapter(Opportunity_Detail_NewFragment.this, getContext(), arraylist, particularoppdata.get(0).getCurrentStageNo());
                        binding.recyclerView.setLayoutManager(layoutManager);
                        binding.recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
                binding.loader.setVisibility(View.GONE);
            }
        });
    }

    private void manageclickevent() {
        binding.headerFrame.headTitle.setText(getString(R.string.opportunity));
        binding.headerFrame.backPress.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.multioption.setOnClickListener(this);
        binding.addStage.setOnClickListener(this);
        binding.seemore.setOnClickListener(this);
        binding.activityView.setOnClickListener(this);
        binding.markascurrent.setOnClickListener(this);
        binding.markascomplete.setOnClickListener(this);
        binding.chatterView.setOnClickListener(this);
    }



    private void setData(NewOpportunityRespose particularoppdata) {
//        binding.updateProjectStatus.setText(particularoppdata.getProject_Status());
        binding.headerFrame.headTitle.setText(particularoppdata.getOpportunityName());
        binding.opportunityNameValue.setText(particularoppdata.getCurrentStageName());
        binding.tvContactPerson.setText(particularoppdata.getContactPersonName());
        binding.tvClientBudget.setText("Rs 90000/-");
//        binding.tvStageTitle.setText(particularoppdata.getCurrentStageName());
        binding.mainName.setText(particularoppdata.getOpportunityName());
        if (!particularoppdata.getUType().isEmpty())
            binding.typeValue.setText(particularoppdata.getUType().get(0).getType());

        binding.tvProbability.setText(particularoppdata.getUProblty());
        binding.closeDateValue.setText(particularoppdata.getClosingDate());
        binding.leadSourceValue.setText(particularoppdata.getULsource());
        binding.stageValue.setText(particularoppdata.getCurrentStageName());
        binding.opportunityOwnerValue.setText(particularoppdata.getSalesPersonName());


        if (particularoppdata.getU_LEADNM().isEmpty()) {
            binding.tvLead.setText("NA");
        } else {
            binding.tvLead.setText(particularoppdata.getU_LEADNM());
        }
        binding.tvBusinessPartner.setText(particularoppdata.getCustomerName());

        binding.tvRemarks.setText(particularoppdata.getRemarks());

        binding.modifiedOn.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(particularoppdata.getUpdateDate()));
        binding.createdbyDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(particularoppdata.getStartDate()));

        callStagesApi(opportunityItem.getId());

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.add_stage:
                openaddstagedialog();
                break;
            case R.id.option:
                openoptionpopup();
                break;
            case R.id.multioption:
                editdeletepopup();
                break;
            case R.id.seemore:
                showmorevisibilty();
                break;
            case R.id.activity_view:

                Bundle b = new Bundle();
                b.putParcelable(Globals.OpportunityItem, opportunityItem);
                ActivityFragment fragment = new ActivityFragment();
//    Opportunity_Detail_NewFragment fragment = new Opportunity_Detail_NewFragment();
                fragment.setArguments(b);
                FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.quatoes_main_container, fragment);
                transaction.addToBackStack("Back");
                transaction.commit();
                break;
            case R.id.markascomplete:
                markascompletemethod(particularoppdata.get(0).getCurrentStageNumber());
                break;
            case R.id.chatterView:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Globals.OpportunityItem, opportunityItem);
                ChatterFragment chatterFragment = new ChatterFragment();
                chatterFragment.setArguments(bundle);
                FragmentTransaction chattransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                chattransaction.replace(R.id.quatoes_main_container, chatterFragment);
                chattransaction.addToBackStack("Back");
                chattransaction.commit();

        }
    }

    private void opencommentdialog(String stageno) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.addcomment_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button done = dialog.findViewById(R.id.done);
        EditText comment = dialog.findViewById(R.id.comment);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.COMMENT = comment.getText().toString();
                if (!comment.getText().toString().trim().isEmpty()) {
                    callUpdatestageapi(stageno);
                    dialog.cancel();
                } else {
                    Toast.makeText(getContext(), "Please write some Comment", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    private void markascompletemethod(String currentStageNo) {
        if (binding.markascomplete.getText().equals("Complete")) {
            opefinalpopup();
        } else {
            opencommentdialog(currentStageNo);

        }


    }

    private void opefinalpopup() {


        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.completestage_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
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
                if (validation(comments_val, selectedStage)) {
                    CompleteStageResponse completeStageResponse = new CompleteStageResponse();
                    completeStageResponse.setOppId(Integer.valueOf(opportunityItem.getId()));
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

    private boolean validation(EditText text, String selectedStage) {
        if (text.getText().toString().trim().isEmpty()) {
            text.setError("Enter remarks");
            return false;
        } else if (selectedStage.isEmpty()) {
            Toast.makeText(getContext(), "Please Select Stage", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void callcompletestageApi(CompleteStageResponse completeStageResponse) {


        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().completestage(completeStageResponse);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.VISIBLE);
                    callApi(opportunityItem.getId());
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
            }
        });
    }

    private void callUpdatestageapi(String currentStageNo) {
        StagesValue stval = new StagesValue();
        stval.setOppId(Integer.valueOf(opportunityItem.getId()));
        stval.setUpdateDate(Globals.getTodaysDate());
        stval.setUpdateTime(Globals.getTCurrentTime());
        stval.setStageno(currentStageNo);
        stval.setComment(Globals.COMMENT);
        stval.setFile("");
        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().updatestage(stval);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.VISIBLE);
                    callApi(opportunityItem.getId());
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
            }
        });
    }

    private void showmorevisibilty() {
        if (!bottomView) {
            bottomView = true;
            binding.seemore.setText("See less");
            binding.bottomView.setVisibility(View.VISIBLE);
        } else {
            bottomView = false;
            binding.seemore.setText("See more");
            binding.bottomView.setVisibility(View.GONE);
        }

    }

    private void editdeletepopup() {
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.multioption);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.editdeletemenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.edit:
                        Bundle b = new Bundle();
                        b.putParcelable(Globals.OpportunityItem, opportunityItem);
                        Opportunity_Update_Fragment fragment = new Opportunity_Update_Fragment();
//                        Opportunity_Detail_NewFragment fragment = new Opportunity_Detail_NewFragment();
                        fragment.setArguments(b);
                        FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.quatoes_main_container, fragment);
                        transaction.addToBackStack("Back");
                        transaction.commit();

                        break;
                }
                popupMenu.dismiss();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }

    private void openoptionpopup() {
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.option);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.optionmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                switch (menuItem.getItemId()) {
                    case R.id.quotation:
                       /* Globals.opportunityData.clear();
                        Prefs.putString(Globals.QuotationListing,opportunityItem.getId());
//                        Prefs.putBoolean(Globals.SelectQuotation,true);
                        Globals.opportunityData.add(opportunityItem);
                        Intent intent = new Intent(getContext(), QuotationActivity.class);
                        startActivity(intent);*/ //todo comment

                        Intent intent = new Intent(getActivity(), AddQuotationAct.class);
                        intent.putExtra("FROM_OPP_ID", String.valueOf(particularoppdata.get(0).getId()));
                        getActivity().startActivity(intent);

                        break;

                    case R.id.order:

                        Intent intent1 = new Intent(getActivity(), AddOrderAct.class);
                        intent1.putExtra("FROM_OPP_ID", String.valueOf(particularoppdata.get(0).getId()));
                        getActivity().startActivity(intent1);

                        break;
                }

                popupMenu.dismiss();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    private void openaddstagedialog() {
        previousstageadapter = new PreviousstageAdpater(getContext(), arraylist);
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.addnewstage_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Spinner previous_stage = dialog.findViewById(R.id.previous_stage);
        EditText new_stage = dialog.findViewById(R.id.new_stage);
        EditText date_value = dialog.findViewById(R.id.date_value);
        Button add = dialog.findViewById(R.id.add);
        previous_stage.setAdapter(previousstageadapter);
        previous_stage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = String.valueOf(arraylist.get(position).getStageno());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pos = "0.0";
            }
        });
        date_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.selectDate(dialog.getContext(), date_value);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_stage.length() > 0) {
                    // arraylist.add(pos+1, new TimeLineView(new_stage.getText().toString(),date_value.getText().toString()));
                    callcreatestageapi(pos, new_stage.getText().toString());

                    dialog.cancel();
                } else {
                    Toast.makeText(getContext(), "Fill Properly", Toast.LENGTH_LONG).show();
                }
            }
        });


        dialog.show();
    }

    private void callcreatestageapi(String pos, String s) {
        StagesValue model = new StagesValue();
        model.setName(s);
        model.setOppId(Integer.valueOf(opportunityItem.getId()));
        model.setStageno(pos);
        model.setSequenceNo(opportunityItem.getSequentialNo());
        model.setClosingPercentage("0.0");
        model.setIsSales("tYES");
        model.setIsPurchasing("tYES");
        model.setCancelled("tNO");
        model.setCreateDate(Globals.getTodaysDate());
        model.setUpdateDate(Globals.getTodaysDate());

        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().createStages(model);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                        callApi(opportunityItem.getId());
                    }


                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onRefresh() {
        binding.loader.setVisibility(View.VISIBLE);
        callStagesApi(opportunityItem.getId());

    }

    private void callstagecommentApi(String val) {

        StagesValue oppitem = new StagesValue();
        oppitem.setOppId(Integer.valueOf(opportunityItem.getId()));
        oppitem.setStageno(val);
        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().getStagesComment(oppitem);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getData().get(0).getComment().trim().isEmpty()) {
                        binding.bottomCommentview.setVisibility(View.GONE);
                    } else {
                        binding.bottomCommentview.setVisibility(View.VISIBLE);
                        binding.commentsVal.setText(response.body().getData().get(0).getComment());
                    }
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
                binding.loader.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void stagecomment(String id, String name) {
        binding.opportunityNameValue.setText(name);
        binding.loader.setVisibility(View.VISIBLE);
        callstagecommentApi(id);
    }
}