package com.cinntra.okana.fragments;

 import android.app.DatePickerDialog;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.DatePicker;
 import android.widget.TextView;
 import android.widget.Toast;
 import androidx.fragment.app.Fragment;
 import com.cinntra.okana.R;
 import com.cinntra.okana.model.NewQuotation;
 import com.cinntra.okana.model.QuotationResponse;
 import com.cinntra.okana.receivers.ConnectivityReceiver;
 import com.cinntra.okana.webservices.APIsClient;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Locale;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;


public class New_Quotation extends Fragment implements View.OnClickListener {

//    @BindView(R.id.posting_value)
//    TextView stage_start_date;
//    @BindView(R.id.valid_till_value)
//    TextView stage_end_date;
//    @BindView(R.id.submit)
//    Button create;
   /* @BindView(R.id.cancel)
    TextView cancel;*/
    final Calendar myCalendar = Calendar.getInstance();

    TextView datesetter;

    DatePickerDialog.OnDateSetListener date;
  public New_Quotation()
      {
   //Required empty public constructor
      }


    // TODO: Rename and change types and number of parameters
    public static New_Quotation newInstance(String param1, String param2) {
        New_Quotation fragment = new New_Quotation();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
     if (getArguments() != null)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
    //Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_add_quotation, container, false);
  //  ButterKnife.bind(this,v);
   // stage_end_date.setOnClickListener(this);
   // stage_start_date.setOnClickListener(this);
   // create.setOnClickListener(this);
   // cancel.setOnClickListener(this);
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
        if(v.getId()==R.id.posting_value)
           {
     //   datesetter = stage_start_date;
        new DatePickerDialog(getActivity(), date, myCalendar
        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
           }
        else if(v.getId()==R.id.valid_till_value)
           {
       // datesetter = stage_end_date;
        new DatePickerDialog(getActivity(), date, myCalendar
        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
           }
       /* else if(v.getId()==R.id.cancel){
            getActivity().onBackPressed();
        }*/
        else if(v.getId()==R.id.submit)
            {
       //getActivity().onBackPressed();
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected)
          {

              /*NewQuotation in = new NewQuotation();
               in.setCompanyDB("SBODemoIN");
               in.setPassword("manager");
               in.setUserName("manager");
               quotationList(in);*/


                }
        else
          {
                    Toast.makeText(getActivity(),"Please check your Internet",Toast.LENGTH_SHORT).show();
                }
            }
          }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datesetter.setText(sdf.format(myCalendar.getTime()));
    }

    private void quotationList(NewQuotation in)
       {
   Call<QuotationResponse> call = APIsClient.getInstance().getApiService().NewQuotation(in);
   call.enqueue(new Callback<QuotationResponse>() {
        @Override
   public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
     if (response != null)
           {
            }
       }
     @Override
     public void onFailure(Call<QuotationResponse> call, Throwable t) {
       Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
       Log.e("ErrorMsz==>",t.getMessage().toString());
            }
        });


    }
}