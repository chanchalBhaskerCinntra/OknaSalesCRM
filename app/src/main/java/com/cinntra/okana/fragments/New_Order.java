package com.cinntra.okana.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.AddOrderBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;




public class New_Order extends Fragment implements View.OnClickListener {

//    @BindView(R.id.delivery_date)
//    EditText stage_start_date;
//    @BindView(R.id.document_date)
//    EditText stage_end_date;
//    @BindView(R.id.create)
//    TextView create;
//    @BindView(R.id.head_title)
//    TextView head_title;
//
//    @BindView(R.id.cancel)
//    TextView cancel;
    final Calendar myCalendar = Calendar.getInstance();

    EditText datesetter;

    DatePickerDialog.OnDateSetListener date;
  public New_Order()
      {
   //Required empty public constructor
      }


    // TODO: Rename and change types and number of parameters
    public static New_Order newInstance(String param1, String param2) {
        New_Order fragment = new New_Order();
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


    AddOrderBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
    //Inflate the layout for this fragment
binding=AddOrderBinding.inflate(getLayoutInflater());
    View v=inflater.inflate(R.layout.add_order, container, false);
   // ButterKnife.bind(this,v);
    binding.quotationGeneralContentInclude.documentDateValue.setOnClickListener(this);
    binding.quotationGeneralContentInclude.documentDateValue.setOnClickListener(this);
   // binding.quotationGeneralContentInclude.cre.setOnClickListener(this);
  //  cancel.setOnClickListener(this);
 //   head_title.setText(getString(R.string.sales_order));

    date = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
    int dayOfMonth)
      {
    //TODO Auto-generated method stub
    myCalendar.set(Calendar.YEAR, year);
    myCalendar.set(Calendar.MONTH, monthOfYear);
    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    updateLabel();
      }

        };
    return v;

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.posting_date_value)
           {
      //  datesetter = stage_start_date;
        new DatePickerDialog(getActivity(), date, myCalendar
        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
           }
        else if(v.getId()==R.id.valid_till_value)
           {
    //    datesetter = stage_end_date;
        new DatePickerDialog(getActivity(), date, myCalendar
        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
           }
        else if(v.getId()==R.id.create)
            {
       getActivity().onBackPressed();
            }
        else  if(v.getId()==R.id.cancel){
            getActivity().onBackPressed();
        }
          }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datesetter.setText(sdf.format(myCalendar.getTime()));
    }
}