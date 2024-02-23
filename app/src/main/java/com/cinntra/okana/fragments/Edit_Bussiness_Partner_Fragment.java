package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.cinntra.okana.R;



public class Edit_Bussiness_Partner_Fragment extends Fragment {



    public Edit_Bussiness_Partner_Fragment()
       {
        // Required empty public constructor
      }


    // TODO: Rename and change types and number of parameters
    public static Edit_Bussiness_Partner_Fragment newInstance(String param1, String param2) {
        Edit_Bussiness_Partner_Fragment fragment = new Edit_Bussiness_Partner_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
      {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState)
     {
        //Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_edit_bussiness_partner, container, false);
  //  ButterKnife.bind(this,v);
    return v;
     }
}