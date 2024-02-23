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




public class Quatation_Edit_Fragment extends Fragment implements View.OnClickListener {

//  @BindView(R.id.cancel)
//    TextView cancel;
//  @BindView(R.id.head_title)
//    TextView head_title;
//  @BindView(R.id.create)
//  TextView create;
//  @BindView(R.id.editItems)
//  LinearLayout editItems;
//
//  @BindView(R.id.series_value)
//  TextView series_value;
//  @BindView(R.id.bussiness_partner_value)
//   TextView bussiness_partner_value;
//  @BindView(R.id.contact_person_value)
//   TextView contact_person_value;
//  @BindView(R.id.currency_value)
//   TextView currency_value;
//  @BindView(R.id.sales_employee_value)
//   TextView sales_employee_value;
//  @BindView(R.id.posting_date_value)
//   TextView posting_date_value;
//  @BindView(R.id.valid_untill_value)
//   TextView valid_untill_value;
//  @BindView(R.id.document_date_value)
//   TextView document_date_value;
//  @BindView(R.id.remark_value)
//  TextView remark_value;
//  @BindView(R.id.customer_ref_value)
//   TextView customer_ref_value;
//  @BindView(R.id.item_count)
//   TextView item_count;
//  @BindView(R.id.total_before_value)
//   TextView total_before_value;
//  @BindView(R.id.discount_value_per)
//   TextView discount_value_per;
//  @BindView(R.id.discount_value)
//   TextView discount_value;
//  @BindView(R.id.tax_value)
//   TextView tax_value;
//  @BindView(R.id.total_value)
//   TextView total_value;
//  @BindView(R.id.rounding_value)
//    TextView rounding_value;
//  @BindView(R.id.ship_to_value)
//    TextView ship_to_value;
//  @BindView(R.id.bill_to_value)
//    TextView bill_to_value;
//  @BindView(R.id.shipping_type_value)
//    TextView shipping_type_value;
//  @BindView(R.id.payment_term_value)
//    TextView payment_term_value;
//  @BindView(R.id.payment_method_value)
//    TextView payment_method_value;
//  @BindView(R.id.bp_project_value)
//    TextView bp_project_value;



    QuotationItem quotationItem;


    public Quatation_Edit_Fragment() {
    //Required empty public constructor
       }


    // TODO: Rename and change types and number of parameters
    public static Quatation_Edit_Fragment newInstance(String param1, String param2) {
        Quatation_Edit_Fragment fragment = new Quatation_Edit_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

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
    View v = inflater.inflate(R.layout.fragment_quatation_edit, container, false);
    //ButterKnife.bind(this,v);

    setDeafaults();
    setData();



   // new_quatos.setOnClickListener(this);


    return v;
     }

    private void setDeafaults() {
//        head_title.setText(getActivity().getString(R.string.quotation));
//        create.setText(getActivity().getString(R.string.save));
//        create.setOnClickListener(this);
//        cancel.setOnClickListener(this);
//        editItems.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       
    switch(v.getId())
           {
          case R.id.create:
         getActivity().onBackPressed();
           break;
         case R.id.cancel :
         getActivity().onBackPressed();
          break;
         case R.id.editItems :


             Bundle b = new Bundle();
             b.putSerializable(Globals.QuotationItem,quotationItem);
         Quatation_Edit_Item_Fragment fragment = new Quatation_Edit_Item_Fragment();
          fragment.setArguments(b);
         FragmentManager fm       = getFragmentManager();
             FragmentTransaction transaction  = fm.beginTransaction();
             transaction.replace(R.id.main_edit_qt_frame, fragment);
             transaction.addToBackStack(null);
             transaction.commit();
                   break;



              }


    }




    private void setData()
      {
       /* Globals.contentList.clear();
        Globals.contentList.addAll(quotationItem.getDocumentLines());*/


        /*********** Set Data In Header Section**************/

//        valid_untill_value.setText("Valid Until : "+quotationItem.getDocDueDate());
//        item_count.setText("Items ("+quotationItem.getDocumentLines().size()+")");
//        total_before_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotalSys()));
//        discount_value_per.setText(quotationItem.getDiscountPercent());//
//        discount_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getTotalDiscount()));
//        tax_value.setText(quotationItem.getTotalEqualizationTax());
//        total_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotal())+" ( "+Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getDocTotalSys())+" )");
//        rounding_value.setText(Globals.getAmmount(quotationItem.getDocCurrency(),quotationItem.getRoundingDiffAmount()));
//        series_value.setText(quotationItem.getSeries());
//        contact_person_value.setText(quotationItem.getBPLName());
//        sales_employee_value.setText("Sale Name");
//        currency_value.setText(quotationItem.getDocCurrency());
//        document_date_value.setText(quotationItem.getDocDate());
//        valid_untill_value.setText(quotationItem.getDocDueDate());
//        posting_date_value.setText(quotationItem.getCreationDate());
//        remark_value.setText(quotationItem.getComments());
//        customer_ref_value.setText(quotationItem.getDocNum());
//        bussiness_partner_value.setText("Not Set Value");
//
//
//        /*********** Set Data In Logistic Section**************/
//        ship_to_value.setText(quotationItem.getShipToDescription());
//        bill_to_value.setText(quotationItem.getAddress());
//        shipping_type_value.setText("Type");
//
//        /*********** Set Data In Accounting Section**************/
//        payment_term_value.setText(quotationItem.getDownPaymentType());
//        payment_method_value.setText(quotationItem.getPaymentMethod());
//        bp_project_value.setText(quotationItem.getBPLName());


    }

}