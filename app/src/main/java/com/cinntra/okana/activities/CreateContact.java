package com.cinntra.okana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.NewContactBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.AddQuotation;
import com.cinntra.okana.model.CustomerItem;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.webservices.APIsClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateContact extends MainBaseActivity implements View.OnClickListener {
     public static int PARTNERCODE = 100;
     public static int ITEMSCODE   = 1000;


    public static String CardValue;
    public static int PriceListNum = 1;
    NewContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding=NewContactBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

  //  ButterKnife.bind(this);
    setDefaults();

     }

    private void setDefaults() {
        binding.headerOnlyTitle.headTitle.setText(getResources().getString(R.string.new_contact));
        binding.headerOnlyTitle.backPress.setOnClickListener(this);
        binding.businessPartners.setOnClickListener(this);
        binding.itemsView.setOnClickListener(this);
        binding.submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
    case R.id.back_press:
        finish();
        break;
        case R.id.businessPartners:
            Prefs.putString(Globals.BussinessPageType,"CreateCon");
           Intent i = new Intent(CreateContact.this, BussinessPartners.class);
           startActivityForResult(i,PARTNERCODE);
           //startActivity(i);
            break;
        case R.id.itemsView:
            if(Globals.SelectedItems.size()==0) {
            Intent intent = new Intent(CreateContact.this, ItemsList.class);
            startActivityForResult(intent, ITEMSCODE);
            }
            else {
            Intent intent = new Intent(CreateContact.this, SelectedItems.class);
            intent.putExtra("FromWhere","NewContact");
            startActivityForResult(intent, ITEMSCODE);
            }
            break;
        case R.id.submit:

            if(Globals.checkInternet(getApplicationContext())) {
                if (postJson(Globals.SelectedItems) != null) {
                    AddQuotation obj = new AddQuotation();
                    obj.setCardCode(CardValue);
//                    obj.setDocumentLines(postJson(Globals.SelectedItems));//todo comment for now
                    addQuotation(obj);
                } else {
                    Toast.makeText(CreateContact.this, getString(R.string.something_wron_msz), Toast.LENGTH_SHORT).show();
                }
            }
            break;
          }
       }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PARTNERCODE) {
    if(resultCode == RESULT_OK) {
    CustomerItem customerItem = (CustomerItem) data.getSerializableExtra(Globals.CustomerItemData);
    setData(customerItem);
        }
      }
    else if(requestCode == ITEMSCODE)
    {
   binding.itemValue.setText(""+Globals.SelectedItems.size());
    }
    }

    private void setData(CustomerItem customerItem)
      {
     PriceListNum = customerItem.getPriceListNum();
     CardValue = customerItem.getCardCode();
     binding.seriesValue.setText(customerItem.getSeries());
     binding.bussinessPartnerValue.setText(customerItem.getCardName());
     //sales_employee_value.setText(customerItem.get);
     binding.currencyValue.setText(customerItem.getCurrency());
     binding.postingValue.setText(customerItem.getCreateDate());
     binding.validTillValue.setText(customerItem.getUpdateDate());
     binding.documentDateValue.setText(customerItem.getUpdateDate());
     binding.remarkValue.setText(customerItem.getRemarks());
     binding.shipToValue.setText(customerItem.getShipToDefault());
     //bill_to_value.setText(customerItem.getb);
      binding.shippingTypeValue.setText(customerItem.getShippingType());
      //payment_term_value.setText(customerItem.getShippingType());
      //payment_method_value.setText(customerItem.getShippingType());



      }
    ArrayList<DocumentLines> postlist;
      private ArrayList<DocumentLines> postJson(ArrayList<DocumentLines> list){
        postlist = new ArrayList<>();
          for (int i=0;i<list.size();i++
               ) {
              DocumentLines dc = new DocumentLines();
              dc.setItemCode(Globals.SelectedItems.get(i).getItemCode());
              dc.setQuantity(Globals.SelectedItems.get(i).getQuantity());
              dc.setTaxCode(Globals.SelectedItems.get(i).getTaxCode());//BED+VAT
              dc.setUnitPrice(Globals.SelectedItems.get(i).getUnitPrice());
              postlist.add(dc);
          }

          return postlist;
      }


    private void addQuotation(AddQuotation in)
        {
   Call<QuotationResponse> call = APIsClient.getInstance().getApiService().addQuotation(in);
   call.enqueue(new Callback<QuotationResponse>() {
      @Override
   public void onResponse(Call<QuotationResponse> call, Response<QuotationResponse> response) {
    if(response.code()==201)
          {
     Globals.SelectedItems.clear();
    Toast.makeText(CreateContact.this, "Posted Successfully.", Toast.LENGTH_SHORT).show();
        }
       else
        {
        //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
        Gson gson = new GsonBuilder().create();
        QuotationResponse mError = new QuotationResponse();
       try {
        String s =response.errorBody().string();
        mError= gson.fromJson(s,QuotationResponse.class);
        Toast.makeText(CreateContact.this, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
         } catch (IOException e) {
       //handle failure to read error
         }
       //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
            }
       }
       @Override
       public void onFailure(Call<QuotationResponse> call, Throwable t) {
       Toast.makeText(CreateContact.this, t.getMessage(), Toast.LENGTH_SHORT).show();
           }
        });
       }
   }