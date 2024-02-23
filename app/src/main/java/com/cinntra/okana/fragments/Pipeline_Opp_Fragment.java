package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;



public class Pipeline_Opp_Fragment extends Fragment {
//     @BindView(R.id.recyclerview)
//      RecyclerView recyclerview;

    public Pipeline_Opp_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Pipeline_Opp_Fragment newInstance(String param1, String param2) {
        Pipeline_Opp_Fragment fragment = new Pipeline_Opp_Fragment();
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
        View v=inflater.inflate(R.layout.fragment_open_opp, container, false);
       // ButterKnife.bind(this,v);

        /*Open_Qts_Adapter adapter = new Open_Qts_Adapter(getActivity());
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        recyclerview.setAdapter(adapter);
        */
        return v;
    }
}