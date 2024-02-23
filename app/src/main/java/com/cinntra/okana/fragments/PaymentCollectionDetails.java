package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.adapters.AmountDetailAdapter;
import com.cinntra.okana.adapters.PaymentDetailAdater;
import com.cinntra.okana.databinding.TrackPaymentDetailBinding;


public class PaymentCollectionDetails extends Fragment implements View.OnClickListener {

//    @BindView(R.id.company_frame)
//    ImageView company_frame;
//    @BindView(R.id.company_name)
//    TextView company_name;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.qt_status)
//    TextView qt_status;
//    @BindView(R.id.company_id_value)
//    TextView company_id_value;
//    @BindView(R.id.invoice_no_value)
//    TextView invoice_no_value;
//    @BindView(R.id.contact_person_value)
//    TextView contact_person_value;
//    @BindView(R.id.order_id_value)
//    TextView order_id_value;
//    @BindView(R.id.item_subtotal_value)
//    TextView item_subtotal_value;
//    @BindView(R.id.shipping_value)
//    TextView shipping_value;
//    @BindView(R.id.tax_value)
//    TextView tax_value;
//    @BindView(R.id.grand_total_value)
//    TextView grand_total_value;
//    @BindView(R.id.see_more)
//    TextView see_more;
//    @BindView(R.id.total_value)
//    TextView total_value;
//    @BindView(R.id.payment_collection)
//    Button payment_collection;
//    @BindView(R.id.received_value)
//    TextView received_value;
//    @BindView(R.id.due_value)
//    TextView due_value;
//    @BindView(R.id.total_amnt_value)
//    TextView total_amnt_value;
//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;

    EditText editText;
    TextView mode1,mode2,mode3;
    Button button;
    public static String clickTab = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public void onResume() {
        super.onResume();
       // if((AppCompatActivity) getActivity()!=null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    TrackPaymentDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
      {
   // Inflate the layout for this fragment
          binding=TrackPaymentDetailBinding.inflate(inflater,container,false);
     View v=inflater.inflate(R.layout.track_payment_detail, container, false);
    // ButterKnife.bind(this,v);
     setDefaults();

        return binding.getRoot();
    }

    private void setDefaults() {
        binding.headerBottomroundEdit.headTitle.setText(getResources().getString(R.string.collect_payment));
        binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
        binding.paymentCollection.setOnClickListener(this);
        binding.seeMore.setOnClickListener(this);
        AmountDetailAdapter adapter = new AmountDetailAdapter(getContext());
       binding. recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.payment_collection:
                openAmountDialog();
                break;
            case R.id.see_more:
                openDetailDialog();
                break;
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.mode1:
                tabchangecolor(mode1,mode2,mode3);
                clickTab = "mode1";
                break;
            case R.id.mode2:
                tabchangecolor(mode2,mode1,mode3);
                clickTab = "mode2";
                break;
            case R.id.mode3:
                tabchangecolor(mode3,mode2,mode1);
                clickTab = "mode3";
                break;
        }
    }

    private void tabchangecolor(TextView mode1, TextView mode2, TextView mode3) {
    mode1.setBackground(getResources().getDrawable(R.drawable.background_yellow_rounded));
    mode2.setBackground(getResources().getDrawable(R.drawable.background_grey_rounded));
    mode3.setBackground(getResources().getDrawable(R.drawable.background_grey_rounded));
    }

    private void openAmountDialog() {

        Dialog dialog = new Dialog(getContext());
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog =layoutInflater.inflate(R.layout.enter_amount_alertbox,null);
        button       = custom_dialog.findViewById(R.id.button);
        editText       = custom_dialog.findViewById(R.id.editText);
        mode1       = custom_dialog.findViewById(R.id.mode1);
        mode2       = custom_dialog.findViewById(R.id.mode2);
        mode3       = custom_dialog.findViewById(R.id.mode3);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.show();
            mode1.setOnClickListener(this);
            mode2.setOnClickListener(this);
            mode3.setOnClickListener(this);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });


    }

    private void openDetailDialog() {
        RecyclerView productdetailview;
        Dialog dialog = new Dialog(getContext());
        // LayoutInflater layoutInflater = context.getLayoutInflater();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog =layoutInflater.inflate(R.layout.product_detail_alertbox,null);
        productdetailview       = custom_dialog.findViewById(R.id.productdetailview);
        PaymentDetailAdater adapter = new PaymentDetailAdater(getContext());
        productdetailview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        productdetailview.setAdapter(adapter);
        dialog.setContentView(custom_dialog);
        //dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
