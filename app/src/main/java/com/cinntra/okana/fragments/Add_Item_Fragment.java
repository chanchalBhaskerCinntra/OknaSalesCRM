package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.OrderPagerAdapter;
import com.cinntra.okana.databinding.FragmentEditItemAddBinding;
import com.cinntra.okana.databinding.FragmentItemAddBinding;

import java.util.ArrayList;




public class Add_Item_Fragment extends Fragment implements View.OnClickListener {

    String tabs[] = {"Items","Recommendation"};

    ArrayList<Fragment> fragmentList =new  ArrayList<Fragment>();

    OrderPagerAdapter pagerAdapter;

//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.save)
//    TextView save;

    public Add_Item_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Add_Item_Fragment newInstance(String param1, String param2) {
        Add_Item_Fragment fragment = new Add_Item_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    FragmentEditItemAddBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentEditItemAddBinding.inflate(getLayoutInflater());
        View v=inflater.inflate(R.layout.fragment_edit_item_add, container, false);
     //   ButterKnife.bind(this,v);

        setDefaults();


        return binding.getRoot();
    }

    private void setDefaults() {
       binding.headerOneButtonTitle. save.setVisibility(View.GONE);
        binding.headerOneButtonTitle.headTitle.setText(getActivity().getString(R.string.items));
        fragmentList.add(new Item_Fragment());
        fragmentList.add(new Item_Recommendation_Fragment());

        binding.headerOneButtonTitle.backPress.setOnClickListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pagerAdapter = new OrderPagerAdapter(getChildFragmentManager(),fragmentList,tabs);
         binding.viewpager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
     case R.id.back_press:
     getActivity().onBackPressed();

        }
    }
}