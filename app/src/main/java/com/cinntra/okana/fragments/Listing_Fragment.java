package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ListAdapter;
import com.cinntra.okana.databinding.FragmentListingBinding;


public class Listing_Fragment extends Fragment {

//   @BindView(R.id.listing)
//    RecyclerView listing;

    public Listing_Fragment() {
        // Required empty public constructor
    }




    // TODO: Rename and change types and number of parameters
    public static Listing_Fragment newInstance(String param1, String param2) {
        Listing_Fragment fragment = new Listing_Fragment();
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

    FragmentListingBinding binding;

    @Override
    public View onCreateView(
                LayoutInflater inflater,
                ViewGroup container,
                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentListingBinding.inflate(getLayoutInflater());
        View v=inflater.inflate(R.layout.fragment_listing, container, false);
     //   ButterKnife.bind(this,v);

        ListAdapter adapter = new ListAdapter(getActivity());
       binding. listing.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        binding.listing.setAdapter(adapter);

        return binding.getRoot();
    }
}