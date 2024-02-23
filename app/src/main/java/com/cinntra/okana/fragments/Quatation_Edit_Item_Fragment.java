package com.cinntra.okana.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cinntra.okana.R;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.QuotationItem;



public class Quatation_Edit_Item_Fragment extends Fragment implements View.OnClickListener {

//  @BindView(R.id.back_press)
//   RelativeLayout cancel;
//  @BindView(R.id.head_title)
//   TextView head_title;
//  @BindView(R.id.save)
//   TextView done;
//  @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//  @BindView(R.id.choose_item)
//  RelativeLayout choose_item;
//  @BindView(R.id.total_before_value)
//  TextView total_before_value;
//  @BindView(R.id.discount_value_per)
//  TextView discount_value_per;
//  @BindView(R.id.discount_value)
//  TextView discount_value;
//  @BindView(R.id.tax_value)
//  TextView tax_value;
//  @BindView(R.id.total_value)
//  TextView total_value;


    public Quatation_Edit_Item_Fragment() {
    //Required empty public constructor
       }


    // TODO: Rename and change types and number of parameters
    public static Quatation_Edit_Item_Fragment newInstance(String param1, String param2) {
        Quatation_Edit_Item_Fragment fragment = new Quatation_Edit_Item_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    QuotationItem quotationItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            quotationItem =(QuotationItem) b.getSerializable(Globals.QuotationItem);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
     //Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_quatation_item_edit, container, false);
   // ButterKnife.bind(this,v);

    setDeafaults();
    setData();





    return v;
     }

    private void setData() {
//     total_before_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotalSys()));
//     discount_value_per.setText(quotationItem.getDiscountPercent());//
//     discount_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getTotalDiscount()));
//     tax_value.setText(quotationItem.getTotalEqualizationTax());
//     total_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotal())+" ( "+Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotalSys())+" )");
//

           }

    private void setDeafaults() {
//        head_title.setText(getActivity().getString.string.items));
//        done.setText(getActivity().getString(R.string.done));
//        done.setOnClickListener(this);
//        cancel.setOnClickListener(this);
//        choose_item.setOnClickListener(this);
//
//        recyclerview.setLayoutManager( new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
//        recyclerview.setAdapter(new Quotation_Item_Edit_Adapter(getActivity(),quotationItem.getDocumentLines()));
    }

    @Override
    public void onClick(View v) {
    switch(v.getId())
           {
    case R.id.save:
      getActivity().onBackPressed();
    break;
    case R.id.cancel :
      getActivity().onBackPressed();
    break;
    case R.id.choose_item :
     Add_Item_Fragment fragment = new Add_Item_Fragment();
     FragmentManager fm       = getFragmentManager();
     FragmentTransaction transaction  = fm.beginTransaction();
     transaction.replace(R.id.main_frame, fragment);
     transaction.addToBackStack(null);
     transaction.commit();
      break;
        }
      }

}