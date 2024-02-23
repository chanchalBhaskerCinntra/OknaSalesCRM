package com.cinntra.okana.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.InvoicePagerAdapter;
import com.cinntra.okana.databinding.OpportunityPipelineBinding;
import com.cinntra.okana.fragments.FinalStatusFragment;
import com.cinntra.okana.fragments.Open_Opprtunity_Fragment;
import com.cinntra.okana.fragments.Opportunity_pipeline;
import com.cinntra.okana.fragments.PastGeneral;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;


public class Opportunities_Pipeline_Activity extends MainBaseActivity implements View.OnClickListener {


    //    @BindView(R.id.current_view)
//    RelativeLayout current_view;
//    @BindView(R.id.current_text)
//    TextView current_text;
//    @BindView(R.id.past_view)
//    RelativeLayout past_view;
//    @BindView(R.id.mainHeaderLay)
//    LinearLayout mainHeaderLay;
//    @BindView(R.id.past_text)
//    TextView past_text;
    Context context;
    InvoicePagerAdapter pagerAdapter;
    OpportunityPipelineBinding binding;

    @Override
    protected void onResume() {
        super.onResume();


        if (pagerAdapter != null)
            pagerAdapter.notifyDataSetChanged();
    }

    String  BPCardCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OpportunityPipelineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  ButterKnife.bind(this);


        if (getIntent() != null) {
            BPCardCode  = getIntent().getStringExtra("BPCardCodeShortCut");
            // Use the passed data as needed
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Globals.sourcechecklist.clear();
        setDefaults();

    }

    private void setDefaults() {

        binding.currentView.setOnClickListener(this);
        binding.pastView.setOnClickListener(this);

        Bundle bundle = new Bundle();
        bundle.putString("BPCardCodeShortCut", BPCardCode);
        Open_Opprtunity_Fragment fragment2 = new Open_Opprtunity_Fragment();
        fragment2.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.open_opp_container, fragment2);
        fragmentTransaction.commit();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.new_quatos:
                startActivity(new Intent(this, AddOpportunityActivity.class));


                break;

            case R.id.current_view:
                showcurrentPopup();
                break;
            case R.id.past_view:
                showpastPopUp();
                break;

        }
    }


    private void setTextViewDrawableColor(TextView current_text, TextView past_text, int black, int colorPrimary) {
        current_text.setTextColor(getResources().getColor(R.color.colorPrimary));

        for (Drawable drawable : current_text.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(colorPrimary), PorterDuff.Mode.SRC_IN));
            }
        }
        past_text.setTextColor(getResources().getColor(R.color.black));
        for (Drawable drawable : past_text.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(black), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void showpastPopUp() {

        PopupMenu popup = new PopupMenu(Opportunities_Pipeline_Activity.this, binding.pastView);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.past_popup, popup.getMenu());


        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onMenuItemClick(MenuItem item) {
                setTextViewDrawableColor(binding.pastText, binding.currentText, R.color.black, R.color.colorPrimary);

                switch (item.getItemId()) {

                    case R.id.general:
                        PastGeneral pastGeneral = new PastGeneral();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.open_opp_container, pastGeneral);
                        ft.commit();
                        break;
                    case R.id.status:
                        FinalStatusFragment fragment2 = new FinalStatusFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.open_opp_container, fragment2);
                        fragmentTransaction.commit();
                        break;


                }
                return true;

            }
        });
        popup.show();

    }

    private void showcurrentPopup() {
        PopupMenu popup = new PopupMenu(Opportunities_Pipeline_Activity.this, binding.currentView);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.current_popup, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onMenuItemClick(MenuItem item) {
                setTextViewDrawableColor(binding.currentText, binding.pastText, R.color.black, R.color.colorPrimary);
                switch (item.getItemId()) {

                    case R.id.general:
                        Open_Opprtunity_Fragment fragment2 = new Open_Opprtunity_Fragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
                        fragmentTransaction.replace(R.id.open_opp_container, fragment2);
                        fragmentTransaction.commit();

                        break;
                    case R.id.pipeline:
                        Opportunity_pipeline fragment1 = new Opportunity_pipeline();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.open_opp_container, fragment1);
                        fragmentTransaction1.commit();
                        break;

                }
                return true;

            }
        });
        popup.show();
    }

    @Override
    public void onBackPressed() {


        getSupportActionBar().show();
        if (binding.mainHeaderLay.getVisibility() == View.GONE) {

            binding.mainHeaderLay.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}