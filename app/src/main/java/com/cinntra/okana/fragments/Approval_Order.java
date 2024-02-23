package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;


public class Approval_Order extends Fragment {



    public Approval_Order() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Approval_Order newInstance(String param1, String param2) {
        Approval_Order fragment = new Approval_Order();
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
        View v=inflater.inflate(R.layout.fragment_order_status, container, false);

        return v;
    }
}