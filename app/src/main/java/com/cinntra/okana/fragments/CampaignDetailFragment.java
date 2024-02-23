package com.cinntra.okana.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.CampaignDetailScreenBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.CampaignModel;
import com.cinntra.okana.model.CampaignResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CampaignDetailFragment extends Fragment implements View.OnClickListener {


//    @BindView(R.id.campaign_setname)
//    TextView campaign_setname;
//    @BindView(R.id.campaign_access)
//    TextView campaign_access;
//    @BindView(R.id.description)
//    TextView description;
//
//
//    /******************      Leads    *********************/
//    @BindView(R.id.from_date)
//    TextView from_date;
//    @BindView(R.id.to_date)
//    TextView to_date;
//    @BindView(R.id.source)
//    TextView source;
//    @BindView(R.id.priority)
//    TextView priority;
//    @BindView(R.id.status)
//    TextView status;
//
//    /******************      Opportunity    *********************/
//    @BindView(R.id.oppfrom_date)
//    TextView oppfrom_date;
//    @BindView(R.id.oppto_date)
//    TextView oppto_date;
//    @BindView(R.id.sales_employee)
//    TextView sales_employee;
//    @BindView(R.id.type)
//    TextView type;
//
//
//    /******************      Customer    *********************/
//
//    @BindView(R.id.bpfrom_date)
//    TextView customerfrom_date;
//    @BindView(R.id.bpto_date)
//    TextView customerto_date;
//    @BindView(R.id.bpsales_employee)
//    TextView bpsales_employee;
//    @BindView(R.id.bptype)
//    TextView bptype;
//    @BindView(R.id.industry)
//    TextView industry;
//    @BindView(R.id.state)
//    TextView state;
//    @BindView(R.id.country)
//    TextView country;
//
//
//    @BindView(R.id.loader)
//    ProgressBar loader;
//    @BindView(R.id.campaign_list)
//    Button campaign_list;
//    @BindView(R.id.member_list)
//    Button member_list;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back)
//    ImageView back_press;


    CampaignModel campaignData;
    CampaignDetailScreenBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            campaignData =(CampaignModel) b.getSerializable(Globals.CampaignData);

        }

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=CampaignDetailScreenBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.campaign_detail_screen, container, false);
      //  ButterKnife.bind(this,v);

        binding.loader.loader.setVisibility(View.VISIBLE);
        binding.memberList.setOnClickListener(this);
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.headerBottomRounded.headTitle.setText(campaignData.getCampaignSetName());
        if(Globals.checkInternet(getContext()))
        {

            callApi(campaignData.getId());
        }


        return binding.getRoot();
    }

    private void callApi(Integer id) {

        CampaignModel cm = new CampaignModel();
        cm.setId(id);

        Call<CampaignResponse> call = NewApiClient.getInstance().getApiService().getCampsetDetails(cm);
        call.enqueue(new Callback<CampaignResponse>() {
            @Override
            public void onResponse(Call<CampaignResponse> call, Response<CampaignResponse> response) {

                if(response.code()==200)
                {
                   setData(response.body().getData().get(0));
                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }

            }
            @Override
            public void onFailure(Call<CampaignResponse> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(CampaignModel cmp) {
        binding.loader.loader.setVisibility(View.GONE);
        binding.campaignSetname.setText(cmp.getCampaignSetName());
        binding.campaignAccess.setText(cmp.getCampaignAccess());
        binding.description.setText(cmp.getDescription());
        binding.source.setText(cmp.getLeadSource());
        binding.priority.setText(cmp.getLeadPriority());
        binding.fromDate.setText(cmp.getLeadFromDate());
        binding.toDate.setText(cmp.getLeadToDate());
        binding.status.setText(cmp.getLeadStatus());

        if(cmp.getOppSalePerson().size()>0)
        binding.salesEmployee.setText(cmp.getOppSalePerson().get(0).getSalesEmployeeName());
        binding.type.setText(cmp.getOppType());
        binding.oppfromDate.setText(cmp.getOppFromDate());
        binding.opptoDate.setText(cmp.getOppToDate());

        if(cmp.getBPSalePerson().size()>0)
        binding.bpsalesEmployee.setText(cmp.getBPSalePerson().get(0).getSalesEmployeeName());
        binding.bptype.setText(cmp.getOppType());
        binding.bpfromDate.setText(cmp.getBPFromDate());
        binding.bptoDate.setText(cmp.getBPToDate());
        binding.country.setText(cmp.getBPCountry());
        binding.state.setText(cmp.getBPState());
        if(cmp.getBPIndustry().size()>0)
       binding.industry.setText(cmp.getBPIndustry().get(0).getIndustryName());




    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.member_list:
                Bundle b = new Bundle();
                b.putSerializable(Globals.CampaignData, campaignData);
                MemberListFragment fragment = new  MemberListFragment();
                fragment.setArguments(b);
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.container, fragment);
                transaction.addToBackStack("Back");
                transaction.commit();

                break;
            case R.id.back:
                requireActivity().onBackPressed();
                break;

        }

    }
}
