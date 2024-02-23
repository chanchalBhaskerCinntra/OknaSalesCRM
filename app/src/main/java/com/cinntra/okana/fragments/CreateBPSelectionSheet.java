package com.cinntra.okana.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.bpActivity.AddBPCustomer;
import com.cinntra.okana.adapters.BottomSheetAdapter;
import com.cinntra.okana.databinding.CustomBottomSheetBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.ChangeTeam;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.newapimodel.LeadValue;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pixplicity.easyprefs.library.Prefs;


public class CreateBPSelectionSheet extends BottomSheetDialogFragment implements BottomSheetAdapter.ItemListener {
    Context context;
    /*@BindView(R.id.database_listing)
    RecyclerView database_listing;*/
//    @BindView(R.id.btnCancelDialog)
//    TextView btnCancelDialog;
//    @BindView(R.id.feedback)
//    TextView feedback;
//    @BindView(R.id.reminder)
//    TextView reminder;


    FragmentRefresher fragmentRefresher;
    ChangeTeam changeTeam;
    CustomBottomSheetBinding binding;

    LeadValue lv;
    String name;

    public CreateBPSelectionSheet(LeadValue id) {
        // this.changeTeam = dashboard;
        this.lv = id;

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

        //  ButterKnife.bind(this,v);
        // fragmentRefresher =(FragmentRefresher)getActivity();
        context = getContext();
        binding.feedback.setText("Create Business Partner");
        binding.reminder.setText("Update");

        if (lv.getStatus().equalsIgnoreCase("Qualified")) {
            binding.feedback.setVisibility(View.VISIBLE);
        } else {
            binding.feedback.setVisibility(View.GONE);
        }
        /*database_listing.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        database_listing.setAdapter(new BottomSheetAdapter(getActivity(),Dashboard.teamList_Hearchi,this));
*/
        binding.btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Bundle bundle = new Bundle();
                bundle.putParcelable(Globals.Lead_Data,lv);
                LeadFollowUpFragment chatterFragment = new LeadFollowUpFragment();
                chatterFragment.setArguments(bundle);
                FragmentTransaction chattransaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                chattransaction.add(R.id.customer_lead, chatterFragment).addToBackStack(null);
                chattransaction.commit();
                dismiss();*/

                Bundle b = new Bundle();
                b.putParcelable(Globals.LeadDetails, lv);
                b.putString("From", "Lead");
                LeadDetail fragment = new LeadDetail(context);
                fragment.setArguments(b);
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.customer_lead, fragment).addToBackStack(null);
                transaction.commit();
                dismiss();
            }
        });
        binding.feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putString(Globals.AddBp, "Lead");
                Intent intent = new Intent(context, AddBPCustomer.class);
                intent.putExtra(Globals.AddBp, lv);
                context.startActivity(intent);
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


}