package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.FragmentOpportunityDetailBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.OpportunityModels.OpportunityItem;


public class Opportunity_Detail_Fragment extends Fragment implements View.OnClickListener {

//     @BindView(R.id.head_title)
//     TextView head_title;
//     @BindView(R.id.back_press)
//     RelativeLayout back_press;
//     @BindView(R.id.save)
//     TextView save;
//     @BindView(R.id.opportunity_no_value)
//     TextView opportunity_no_value;
//     @BindView(R.id.opportunity_name_value)
//     TextView opportunity_name_value;
//     @BindView(R.id.bussiness_partner_value)
//     EditText bussiness_partner_value;
//     @BindView(R.id.businessPartners)
//     LinearLayout businessPartners;
//     @BindView(R.id.contact)
//     LinearLayout contact;
//     @BindView(R.id.contact_value)
//     EditText contact_value;
//     @BindView(R.id.predicted_closing_value)
//     TextView predicted_closing_value;
//      @BindView(R.id.remark_value)
//     TextView remark_value;
//     @BindView(R.id.stage_remark_value)
//     TextView stage_remark_value;
//     @BindView(R.id.stage_value)
//     TextView stage_value;
//     @BindView(R.id.potential_value)
//     TextView potential_value;
//     @BindView(R.id.closing_rate_value)
//     TextView closing_rate_value;
//     @BindView(R.id.stage_start_date_value)
//     TextView stage_start_date_value;
//     @BindView(R.id.stage_end_date_value)
//     TextView stage_end_date_value;
//     @BindView(R.id.sales_employee_value)
//     TextView sales_employee_value;
//     @BindView(R.id.all_stages)
//     LinearLayout all_stages;

    OpportunityItem opportunityItem;

    public Opportunity_Detail_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Opportunity_Detail_Fragment newInstance(String param1, String param2) {
        Opportunity_Detail_Fragment fragment = new Opportunity_Detail_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            opportunityItem = (OpportunityItem) b.getSerializable(Globals.OpportunityItem);
        }
    }


    FragmentOpportunityDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentget
        binding = FragmentOpportunityDetailBinding.inflate(getLayoutInflater());
        View v = inflater.inflate(R.layout.fragment_opportunity_detail, container, false);
        //  ButterKnife.bind(this,v);
        setDefaults();
        setData();
        return binding.getRoot();
    }

    private void setData() {
        binding.opportunityNoValue.setText(opportunityItem.getSequentialNo());
        binding.opportunityNameValue.setText(opportunityItem.getOpportunityName());
        binding.bussinessPartnerValue.setText(opportunityItem.getBPChanelName());
        binding.contactValue.setText(opportunityItem.getContactPerson());
        binding.predictedClosingValue.setText(opportunityItem.getPredictedClosingDate());
        binding.salesEmployeeValue.setText(opportunityItem.getSalesPerson());
        binding.remarkValue.setText(opportunityItem.getRemarks());
        binding.stageValue.setText(opportunityItem.getCurrentStageNo());
        binding.potentialValue.setText(opportunityItem.getTotalAmountLocal());
        binding.closingRateValue.setText(opportunityItem.getClosingPercentage() + "%");
        binding.stageStartDateValue.setText(opportunityItem.getStartDate());
        binding.stageEndDateValue.setText(opportunityItem.getClosingDate());

    }

    private void setDefaults() {
        binding.headerOneButtonTitle.save.setVisibility(View.GONE);
        binding.headerOneButtonTitle.headTitle.setText(getString(R.string.quotation));
        binding.headerOneButtonTitle.backPress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
        }
    }
}