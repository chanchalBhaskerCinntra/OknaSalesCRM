package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.PaymentCollectionAdapter;
import com.cinntra.okana.databinding.DeliveryActBinding;


public class PaymentCollection extends Fragment implements  View.OnClickListener {


//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.add)
//    ImageView add;
    private PaymentCollectionAdapter paymentCollectionAdapter;

    @Override
    public void onResume() {
        super.onResume();
      //  if((AppCompatActivity) getActivity()!=null)
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    DeliveryActBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

binding=DeliveryActBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.delivery_act, container, false);
     //   ButterKnife.bind(this,v);

        binding.quotesHeader.add.setVisibility(View.GONE);
        binding.quotesHeader.headTitle.setText(getResources().getString(R.string.collect_payment));
        binding.recyclerview.setHasFixedSize(true);
        binding. recyclerview.setLayoutManager(new LinearLayoutManager(v.getContext()));
        binding. recyclerview.setAdapter(new PaymentCollectionAdapter(getContext()));
        return binding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
        }
    }
}
