package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.FragmentEditItemBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.DocumentLines;


public class Edit_Item_Fragment extends Fragment implements View.OnClickListener {

//  @BindView(R.id.cancel)
//   TextView cancel;
//  @BindView(R.id.head_title)
//   TextView head_title;
//  @BindView(R.id.create)
//   TextView create;
//  @BindView(R.id.editItems)
//   LinearLayout editItems;
//  @BindView(R.id.item_code_value)
//  TextView item_code_value;
//  @BindView(R.id.description_value)
//  TextView description_value;
//  @BindView(R.id.warehouse_value)
//  TextView warehouse_value;
//  @BindView(R.id.currency_value)
//  TextView currency_value;
//  @BindView(R.id.quanity_value)
//  TextView quanity_value;
//  @BindView(R.id.item_per_unit_value)
//  TextView item_per_unit_value;
//  @BindView(R.id.discount_value_per)
//  TextView discount_value_per;
//  @BindView(R.id.unit_price_value)
//  TextView unit_price_value;
//  @BindView(R.id.gross_price_value)
//  TextView gross_price_value;
//  @BindView(R.id.tax_code_value)
//  TextView tax_code_value;
//  @BindView(R.id.total_value)
//  TextView total_value;


    public Edit_Item_Fragment() {
        //Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Edit_Item_Fragment newInstance(String param1, String param2) {
        Edit_Item_Fragment fragment = new Edit_Item_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    DocumentLines quotationLineItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            quotationLineItem = b.getParcelable(Globals.QuotationLine);

        }
    }

    FragmentEditItemBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding = FragmentEditItemBinding.inflate(inflater, container, false);
        View v = inflater.inflate(R.layout.fragment_edit_item, container, false);
        //ButterKnife.bind(this,v);

        setDeafaults();
        setData();

        return binding.getRoot();
    }

    private void setDeafaults() {
        binding.addNewHeader.headTitle.setText(getActivity().getString(R.string.item));
        binding.addNewHeader.create.setText(getActivity().getString(R.string.done));
        binding.addNewHeader.create.setOnClickListener(this);
        binding.addNewHeader.cancel.setOnClickListener(this);
        binding.editItems.setOnClickListener(this);
    }

    private void setData() {
        binding.itemCodeValue.setText(quotationLineItem.getItemCode());
        binding.descriptionValue.setText(quotationLineItem.getItemDescription());
        binding.warehouseValue.setText(quotationLineItem.getWarehouseCode());
        // currency_value.setText(quotationLineItem.getCurrency());
        binding.quanityValue.setText(quotationLineItem.getQuantity());
        binding.itemPerUnitValue.setText("Not Set");
        binding.unitPriceValue.setText(String.valueOf(quotationLineItem.getUnitPrice()));
        //   gross_price_value.setText(quotationLineItem.getGrossPrice());
        // discount_value_per.setText(quotationLineItem.getDiscountPercent());
        binding.taxCodeValue.setText(quotationLineItem.getTaxCode());
        //  total_value.setText(quotationLineItem.getLineTotal());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.create:
                getActivity().onBackPressed();
                break;

            case R.id.cancel:
                getActivity().onBackPressed();
                break;

            case R.id.editItems:
                Quatation_Edit_Item_Fragment fragment = new Quatation_Edit_Item_Fragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_edit_qt_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }


    }

}