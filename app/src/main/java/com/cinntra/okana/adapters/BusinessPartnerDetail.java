package com.cinntra.okana.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOpportunityActivity;
import com.cinntra.okana.activities.AddOrderAct;
import com.cinntra.okana.activities.AddQuotationAct;
import com.cinntra.okana.activities.InvoiceActivity;
import com.cinntra.okana.activities.Opportunities_Pipeline_Activity;
import com.cinntra.okana.activities.OrderActivity;
import com.cinntra.okana.activities.PaymentDetails;
import com.cinntra.okana.activities.QuotationActivity;

import com.cinntra.okana.databinding.CustomersdetailBinding;
import com.cinntra.okana.fragments.BPAddressDetails;
import com.cinntra.okana.fragments.BPDetailFragment;
import com.cinntra.okana.fragments.BusinessPartnerBranch;
import com.cinntra.okana.fragments.BusinessPartnerContact;

import com.cinntra.okana.fragments.Open_Opprtunity_Fragment;
import com.cinntra.okana.fragments.Update_BussinessPartner_Fragment;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.BPModel.BusinessPartnerData;
import com.cinntra.okana.model.BPModel.demoListModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;


public class BusinessPartnerDetail extends Fragment implements View.OnClickListener, DatabaseClick {

//    BusinessPartnerData customerItem;
    demoListModel.Datum customerItem;

    CustomersdetailBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Bundle b = getArguments();
            customerItem = (demoListModel.Datum) b.getSerializable(Globals.BussinessItemData);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomersdetailBinding.inflate(inflater, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        setDefaults();
        return binding.getRoot();


    }


    private String[] tabs = {"General", "Contact"};
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    private void setDefaults() {
        Prefs.putString(Globals.SelectAddress, "NoUpdate");
//        binding.headerBottomRounded.headTitle.setText(getResources().getString(R.string.customer));
        binding.headerLayout.headTitle.setText(getResources().getString(R.string.customer));
        /*fragments.add(new Update_BussinessPartner_Fragment(BusinessPartnerDetail.this, customerItem));
        fragments.add(new BusinessPartnerContact(customerItem));
        fragments.add(new BusinessPartnerBranch(BusinessPartnerDetail.this, customerItem));*/

        fragments.add(new BPDetailFragment(BusinessPartnerDetail.this, customerItem));
        fragments.add(new BPAddressDetails(BusinessPartnerDetail.this, customerItem));

        BusinessPagerAdapter pagerAdapter = new BusinessPagerAdapter(getChildFragmentManager(), fragments, tabs);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        binding.headerLayout.backPress.setOnClickListener(this);
//        binding.option.setOnClickListener(this);


        binding.companyName.setText(customerItem.getCardName());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int randomColor = generator.getRandomColor();
        if (!customerItem.getCardName().isEmpty()) {
            TextDrawable drawable = TextDrawable.builder().beginConfig().withBorder(4).endConfig()
                    .buildRound(String.valueOf(customerItem.getCardName().charAt(0)), randomColor);

            binding.nameIcon.setImageDrawable(drawable);
        }

        binding.headerLayout.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });


        binding.opprotunityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Opportunities_Pipeline_Activity.class);// todo comment by me bcz doing it later --- Opportunities_Pipeline_Activity
                intent.putExtra("BPCardCodeShortCut", customerItem.getCardCode());
                startActivity(intent);

            }
        });


        binding.proformaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddQuotationAct.class); //AddQuotationAct
                intent.putExtra("BPCardCodeShortCut", customerItem.getCardCode());
                startActivity(intent);

//                startActivity(new Intent(getActivity(), QuotationActivity.class));
            }
        });


        binding.onOrderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddOrderAct.class);
                intent.putExtra("BPCardCodeShortCut", customerItem.getCardCode());
                startActivity(intent);
//                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

   /*     binding.onOrderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddOrderAct.class);
                intent.putExtra("BPCardCodeShortCut", customerItem.getCardCode());
            }
        });*/


        binding.invoiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PaymentDetails.class));
            }
        });



    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                Globals.branchData.clear();
                requireActivity().onBackPressed();
                break;
            case R.id.option:
//                openPopup();
                break;
        }
    }

 /*   private void openPopup() {
        PopupMenu popup = new PopupMenu(getContext());
        popup.getMenuInflater().inflate(R.menu.businesspartnermenu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.opportunity:
                    startActivity(new Intent(getActivity(), Opportunities_Pipeline_Activity.class));
                    break;
                case R.id.quotation:
                    startActivity(new Intent(getActivity(), QuotationActivity.class));
                    break;
                case R.id.order:
                    startActivity(new Intent(getActivity(), OrderActivity.class));
                    break;
                case R.id.invoice:
                    startActivity(new Intent(getActivity(), InvoiceActivity.class));
                    break;

            }
            return true;
        });
        popup.show();


    }*/

    @Override
    public void onClick(int po) {
        binding.viewpager.setCurrentItem(po);
    }
}
