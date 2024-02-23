package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.FragmentContentDetailBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.QuotationDocumentLines;




public class Content_Detail_Fragment extends Fragment implements View.OnClickListener {

//     @BindView(R.id.head_title)
//    TextView head_title;
//     @BindView(R.id.back_press)
//    RelativeLayout back_press;
//     @BindView(R.id.save)
//     TextView save;
//
//     @BindView(R.id.item_code_value)
//     TextView item_code_value;
//     @BindView(R.id.description_value)
//     TextView description_value;
//     @BindView(R.id.warehouse_value)
//     TextView warehouse_value;
//     @BindView(R.id.delievry_date_value)
//     TextView delievry_date_value;
//     @BindView(R.id.quanity_value)
//     TextView quanity_value;
//     @BindView(R.id.item_per_unit_value)
//     TextView item_per_unit_value;
//     @BindView(R.id.measure_unit_value)
//     TextView measure_unit_value;
//     @BindView(R.id.project_value)
//     TextView project_value;
//     @BindView(R.id.distribution_rule_value)
//     TextView distribution_rule_value;
//     @BindView(R.id.unit_price_value)
//     TextView unit_price_value;
//     @BindView(R.id.gross_price_value)
//     TextView gross_price_value;
//     @BindView(R.id.discount_value_per)
//     TextView discount_value_per;
//     @BindView(R.id.tax_code_value)
//     TextView tax_code_value;
//     @BindView(R.id.total_value)
//     TextView total_value;
//     @BindView(R.id.open_inv_qty_value)
//     TextView open_inv_qty_value;

    QuotationDocumentLines quotationLineItem;

    public Content_Detail_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Content_Detail_Fragment newInstance(String param1, String param2) {
        Content_Detail_Fragment fragment = new Content_Detail_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            quotationLineItem =(QuotationDocumentLines) b.getSerializable(Globals.QuotationLine);
        }
    }

    FragmentContentDetailBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentContentDetailBinding.inflate(inflater,container,false);
        View v=inflater.inflate(R.layout.fragment_content_detail, container, false);
      //  ButterKnife.bind(this,v);
        setDefaults();
        setData();
        return binding.getRoot();
    }

    private void setData()
    {
        binding.itemCodeValue.setText(quotationLineItem.getItemCode());
        binding.descriptionValue.setText(quotationLineItem.getItemDescription());
        binding.warehouseValue.setText(quotationLineItem.getWarehouseCode());
        binding.delievryDateValue.setText(quotationLineItem.getShipDate());
        binding.quanityValue.setText(quotationLineItem.getQuantity());
        binding.itemPerUnitValue.setText("Not Set");
        binding.measureUnitValue.setText(quotationLineItem.getUnitsOfMeasurment());
        binding.projectValue.setText(quotationLineItem.getProjectCode());
        binding.distributionRuleValue.setText(quotationLineItem.getDistributeExpense());
        binding.unitPriceValue.setText(quotationLineItem.getUnitPrice());
        binding.grossPriceValue.setText(quotationLineItem.getGrossPrice());
        binding.discountValuePer.setText(quotationLineItem.getDiscountPercent());
        binding.taxCodeValue.setText(quotationLineItem.getTaxCode());
        binding.totalValue.setText(quotationLineItem.getLineTotal());
        binding.openInvQtyValue.setText(quotationLineItem.getInventoryQuantity());
    }

    private void setDefaults() {
        binding.headerOneButtonTitle.save.setVisibility(View.GONE);
        binding.headerOneButtonTitle.save.setText(getString(R.string.quotation));
        binding.headerOneButtonTitle.backPress.setOnClickListener(this);
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