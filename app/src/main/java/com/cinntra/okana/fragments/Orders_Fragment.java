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

import java.util.ArrayList;



public class Orders_Fragment extends Fragment implements View.OnClickListener {

    String tabs[] = {"Tab-1","Tab-2","Tab-3"};

    ArrayList<Fragment> fragmentList =new  ArrayList<Fragment>();

    OrderPagerAdapter pagerAdapter;

//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    @BindView(R.id.tab_layout)
//    TabLayout tabLayout;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;

    public Orders_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Orders_Fragment newInstance(String param1, String param2) {
        Orders_Fragment fragment = new Orders_Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_orders, container, false);
      //  ButterKnife.bind(this,v);
        fragmentList.add(new Open_Order()); //Open_Order
        fragmentList.add(new All_Order());
        fragmentList.add(new Approval_Order());
     //   back_press.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pagerAdapter = new OrderPagerAdapter(getChildFragmentManager(),fragmentList,tabs);
       //  viewpager.setAdapter(pagerAdapter);
      //  tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
     case R.id.back_press:
     getActivity().onBackPressed();

        }
    }
}