package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOrderAct;
import com.cinntra.okana.activities.ItemsList;
import com.cinntra.okana.activities.SelectedItems;
import com.cinntra.okana.adapters.CategoryAdapter;
import com.cinntra.okana.databinding.FragmentAddQtFormOneBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.ItemCategoryData;
import com.cinntra.okana.model.ItemCategoryResponse;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.PerformaInvoiceModel.QuotationOneAPiModel;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.TokenExpireModel;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.newapimodel.OpportunityValue;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;


import static android.app.Activity.RESULT_OK;
import static com.cinntra.okana.activities.AddOrderAct.fromquotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOrderForm_One_Fragment extends Fragment implements View.OnClickListener, DatabaseClick {

    FragmentActivity act;

    public static int ITEMSVIEWCODE = 1000;

    QuotationItem quotationItem = null;
    String mCardCode = "";
    NewOpportunityRespose mOpportunityItemValue = null;

    public AddOrderForm_One_Fragment(QuotationItem quotationItem1, String cardCode, NewOpportunityRespose opportunityItemValue) {
        quotationItem = quotationItem1;
        this.mCardCode = cardCode;
        this.mOpportunityItemValue = opportunityItemValue;
    }


    // TODO: Rename and change types and number of parameters
    public static AddOrderForm_One_Fragment newInstance(String param1, String param2, QuotationItem quotationItem1, String CardCode, NewOpportunityRespose opportunityItemValue) {
        AddOrderForm_One_Fragment fragment = new AddOrderForm_One_Fragment(quotationItem1, CardCode, opportunityItemValue);
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    FragmentAddQtFormOneBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        act = getActivity();
        binding = FragmentAddQtFormOneBinding.inflate(inflater, container, false);
        // ButterKnife.bind(this,v);
//        setDefaults();

        if (quotationItem.getId() != null && mOpportunityItemValue.getId() == null){
            callQuotationOneApi();
        }
        else if (mOpportunityItemValue.getId() != null && quotationItem.getId() == null){
            callOppOneApi();
        }
        else if (mOpportunityItemValue.getId() != null && quotationItem.getId() != null){
            callOppOneApi();
        }

        binding.discontValue.addTextChangedListener(NumberTextWatcher);

        binding.nextButton.setOnClickListener(this);
        binding.itemFrame.setOnClickListener(this);
        binding.headerLayout.headTitle.setText("Add Order");
        binding.headerLayout.backPress.setOnClickListener(this);


        return binding.getRoot();
    }


    double totalAfterItemDiscount = 0;

    //todo calculate sum of all edit text ..
    private TextWatcher NumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (charSequence.equals("")) {
                Log.e("TEXTWATCHER>>>", "onTextChanged: 0");
                calculateAndDisplaySum(0.0);
            } else {
                Log.e("TEXTWATCHER>>>", "onTextChanged: " + charSequence);
                Log.e("TEXTWATCHER>>>", "total: " + totalAfterItemDiscount);
                calculateAndDisplaySum(totalAfterItemDiscount);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void calculateAndDisplaySum(double total) {
        double num1 = parseEditTextValue(binding.discontValue);


        double sum = total - (total * num1) / 100;

        binding.totalBeforeDiscontValue.setText(String.valueOf(sum));
    }

    private int parseEditTextValue(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(text);
        }
    }

    private void setDefaults() {
        Globals.SelectedItems.clear();
        if (Prefs.getString(Globals.FromQuotation, "").equalsIgnoreCase("Quotation")) {
            Globals.SelectedItems.addAll(fromquotation.getDocumentLines());
        }
        binding.headerLayout.headTitle.setText(getResources().getString(R.string.add_order));
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) {
            if (validation(Globals.SelectedItems.size(), oppItemTempList_gl)) {
                try {

                    if (binding.discontValue.getText().toString().isEmpty()){
                        Globals.showErrorMessage(getActivity(), "Enter Discount Value");
                    }else {
                        AddOrderAct.addQuotationObj.setDiscountPercent(Float.valueOf(binding.discontValue.getText().toString().trim()));
                    }
                    AddOrderAct.addQuotationObj.setNetTotal(Float.valueOf(binding.totalBeforeDiscontValue.getText().toString().trim()));

                    //todo add items lines.
                    ArrayList<DocumentLines> documentLineArrayList = new ArrayList<>();
                    if (oppItemTempList_gl.size() > 0) {
                        for (int j = 0; j < oppItemTempList_gl.size(); j++) {
                            DocumentLines documentLines = new DocumentLines();
                            documentLines.setId(oppItemTempList_gl.get(j).getId());
                            documentLines.setQuantity(oppItemTempList_gl.get(j).getQuantity());
                            documentLines.setUnitPrice(oppItemTempList_gl.get(j).getUnitPrice());
                            documentLines.setDiscountPercent(oppItemTempList_gl.get(j).getDiscountPercent());
                            documentLines.setItemCode(oppItemTempList_gl.get(j).getItemCode());
//                            documentLines.setItemName(oppItemTempList_gl.get(j).getItemName());
                            documentLines.setItemDescription(oppItemTempList_gl.get(j).getItemDescription());
//                            if (quotationOneItemData != null && mOpportunityItemValue.getOppItem().size() == 0){
//                                documentLines.setItemDescription(oppItemTempList_gl.get(j).getItemDescription());
//                            }
//                            else if (quotationOneItemData != null && quotationOneItemData.getDocumentLines().size() > 0){
//                                documentLines.setItemDescription(quotationOneItemData.getDocumentLines().get(j).getItemDescription());
//                            }
//                            else if (mOpportunityItemValue != null && mOpportunityItemValue.getOppItem().size() > 0){
//                                documentLines.setItemDescription(mOpportunityItemValue.getOppItem().get(j).getItemDescription());
//                            }
//                            else if (mOpportunityItemValue != null && quotationOneItemData != null  && mOpportunityItemValue.getOppItem().size() > 0){
//                                documentLines.setItemDescription(mOpportunityItemValue.getOppItem().get(j).getItemDescription());
//                            }


                            documentLines.setTaxCode(oppItemTempList_gl.get(j).getTaxCode());
                            documentLines.setTax(oppItemTempList_gl.get(j).getTax());

                            documentLineArrayList.addAll(Collections.singleton(documentLines));
                        }
                    } else {
                        if (mOpportunityItemValue.getId() != null && quotationOneItemData == null){
                            for (int j = 0; j < mOpportunityItemValue.getOppItem().size(); j++) {
                                DocumentLines documentLines = new DocumentLines();

                                documentLines.setId(mOpportunityItemValue.getOppItem().get(j).getId());
                                documentLines.setQuantity(mOpportunityItemValue.getOppItem().get(j).getQuantity());
                                documentLines.setUnitPrice(mOpportunityItemValue.getOppItem().get(j).getUnitPrice());
                                documentLines.setDiscountPercent(mOpportunityItemValue.getOppItem().get(j).getDiscountPercent());
                                documentLines.setItemCode(mOpportunityItemValue.getOppItem().get(j).getItemCode());
//                                documentLines.setItemName(mOpportunityItemValue.getOppItem().get(j).getItemName());
                                documentLines.setItemDescription(mOpportunityItemValue.getOppItem().get(j).getItemDescription());
                                documentLines.setTaxCode(mOpportunityItemValue.getOppItem().get(j).getTaxCode());
                                documentLines.setTax(mOpportunityItemValue.getOppItem().get(j).getTax());//mOpportunityItemValue.getOppItem().get(j).getTax()

                                documentLineArrayList.addAll(Collections.singleton(documentLines));
                            }
                        }
                        else if (mOpportunityItemValue.getId() == null && quotationOneItemData != null){
                            for (int j = 0; j < quotationOneItemData.getDocumentLines().size(); j++) {
                                DocumentLines documentLines = new DocumentLines();

                                documentLines.setId(quotationOneItemData.getDocumentLines().get(j).getId());
                                documentLines.setQuantity(quotationOneItemData.getDocumentLines().get(j).getQuantity());
                                documentLines.setUnitPrice(quotationOneItemData.getDocumentLines().get(j).getUnitPrice());
                                documentLines.setDiscountPercent(quotationOneItemData.getDocumentLines().get(j).getDiscountPercent());
                                documentLines.setItemCode(quotationOneItemData.getDocumentLines().get(j).getItemCode());
//                                documentLines.setItemName(quotationOneItemData.getDocumentLines().get(j).getItemName());
                                documentLines.setItemDescription(quotationOneItemData.getDocumentLines().get(j).getItemDescription());
                                documentLines.setTaxCode(quotationOneItemData.getDocumentLines().get(j).getTaxCode());
                                documentLines.setTax(quotationOneItemData.getDocumentLines().get(j).getTax());//quotationOneItemData.getDocumentLines().get(j).getTax()

                                documentLineArrayList.addAll(Collections.singleton(documentLines));
                            }
                        }
                        else if (mOpportunityItemValue.getId() != null && quotationOneItemData != null){
                            for (int j = 0; j < mOpportunityItemValue.getOppItem().size(); j++) {
                                DocumentLines documentLines = new DocumentLines();

                                documentLines.setId(mOpportunityItemValue.getOppItem().get(j).getId());
                                documentLines.setQuantity(mOpportunityItemValue.getOppItem().get(j).getQuantity());
                                documentLines.setUnitPrice(mOpportunityItemValue.getOppItem().get(j).getUnitPrice());
                                documentLines.setDiscountPercent(mOpportunityItemValue.getOppItem().get(j).getDiscountPercent());
                                documentLines.setItemCode(mOpportunityItemValue.getOppItem().get(j).getItemCode());
//                                documentLines.setItemName(mOpportunityItemValue.getOppItem().get(j).getItemName());
                                documentLines.setItemDescription(mOpportunityItemValue.getOppItem().get(j).getItemDescription());
                                documentLines.setTaxCode(mOpportunityItemValue.getOppItem().get(j).getTaxCode());
                                documentLines.setTax(mOpportunityItemValue.getOppItem().get(j).getTax());//mOpportunityItemValue.getOppItem().get(j).getTax()

                                documentLineArrayList.addAll(Collections.singleton(documentLines));
                            }
                        }


                    }

//                    AddOrderAct.addQuotationObj.setDocumentLines(documentLineArrayList);
                    AddOrderAct.addQuotationObj.setDocumentLines("");

                    AddOrderForm_Fianl_Fragment fragment = new AddOrderForm_Fianl_Fragment(quotationItem, mOpportunityItemValue, mCardCode);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.temp, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        }

        else if (v.getId() == R.id.item_frame) {
            if (Globals.SelectedItems.size() == 0) {
                openCategorydailog();
            } else {
                Intent intent = new Intent(getContext(), SelectedItems.class);
                intent.putExtra("FromWhere", "addQt");
                startActivityForResult(intent, ITEMSVIEWCODE);
            }
        } else if (v.getId() == R.id.back_press) {
            getActivity().onBackPressed();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ITEMSVIEWCODE) {
            binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");
            binding.totalBeforeDiscontValue.setText(String.valueOf(Globals.calculatetotalofitem(Globals.SelectedItems)));

        }
        if (resultCode == RESULT_OK && requestCode == ITEMSVIEWCODE) {
            binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");
            totalAfterItemDiscount = Double.parseDouble(String.valueOf(Globals.calculateTotalOfItem(Globals.SelectedItems)));
            String sum = String.valueOf(Globals.calculateTotalOfItem(Globals.SelectedItems));
            binding.totalBeforeDiscontValue.setText(sum);
            getQuotationDocLin();

        }

    }


    //todo getting opp items list from items list screen..
    ArrayList<DocumentLines> oppItemTempList_gl = new ArrayList<>();

    public ArrayList<DocumentLines> getQuotationDocLin() {
        oppItemTempList_gl.clear();
        for (DocumentLines dc : Globals.SelectedItems) {
            DocumentLines dcc = new DocumentLines();
            dcc.setId(dc.getId());
            dcc.setUnitPrice(dc.getUnitPrice());
//            dcc.setCurrency(dc.getCurrency());
            dcc.setDiscountPercent(dc.getDiscountPercent());
            dcc.setItemCode(dc.getItemCode());
            dcc.setItemName(dc.getItemName());
            dcc.setTax(dc.getTax());
            dcc.setQuantity(dc.getQuantity());
            dcc.setTaxCode(dc.getTaxCode());
            dcc.setItemDescription(dc.getItemDescription());

            oppItemTempList_gl.add(dcc);
        }
        return oppItemTempList_gl;
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

    }



    //todo calling quotation one api here for show particular details..
    QuotationOneAPiModel.Data quotationOneItemData = null;

    private void callQuotationOneApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", quotationItem.getId());

        Call<QuotationOneAPiModel> call = NewApiClient.getInstance().getApiService().oneQuotationApi1(jsonObject);
        call.enqueue(new Callback<QuotationOneAPiModel>() {
            @Override
            public void onResponse(Call<QuotationOneAPiModel> call, Response<QuotationOneAPiModel> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatus() == 200) {

                                quotationOneItemData = response.body().getValue().get(0);

                                mCardCode = quotationOneItemData.getCardCode();

                                Globals.SelectedItems.clear();
                                //todo items set..
                                Globals.SelectedItems.addAll(quotationOneItemData.getDocumentLines());
                                binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

                                totalAfterItemDiscount = Double.parseDouble(String.valueOf(Globals.calculateTotalOfItem((ArrayList<DocumentLines>) quotationOneItemData.getDocumentLines())));

                                binding.totalBeforeDiscontValue.setText(String.valueOf(Globals.calculateTotalOfItem(quotationOneItemData.getDocumentLines())));


                            }

                        } else {
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 401) {
                        if (response.body().getStatus() == 301) {
                            Gson gson = new GsonBuilder().create();
                            TokenExpireModel mError = new TokenExpireModel();
                            try {
                                String s = response.errorBody().string();
                                mError = gson.fromJson(s, TokenExpireModel.class);
                                Toast.makeText(getActivity(), mError.getDetail(), Toast.LENGTH_LONG).show();
//                                Globals.logoutScreen(getActivity());
                            } catch (IOException e) {
                                // handle failure to read error
                            }

                        }
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Gson gson = new GsonBuilder().create();
                        QuotationOneAPiModel mError = new QuotationOneAPiModel();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationOneAPiModel.class);
                            Toast.makeText(getActivity(), mError.getError().getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<QuotationOneAPiModel> call, Throwable t) {
                Log.e("TAG_APi_failure", "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //todo calling opportunity on eapi here..
    private void callOppOneApi() {
        OpportunityValue opportunityValue = new OpportunityValue();
        opportunityValue.setId(Integer.valueOf(mOpportunityItemValue.getId()));
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().getparticularopportunity(opportunityValue);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        NewOpportunityRespose oppItems = response.body().getData().get(0);

                        mCardCode = oppItems.getCardCode();
//                        callBPOneAPi(oppItems.getCardCode(), "");

//                        setDefaults();
                        Globals.SelectedItems.clear();
                        //todo items set..
                        Globals.SelectedItems.addAll(oppItems.getOppItem());
                        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

                        totalAfterItemDiscount = Double.parseDouble(String.valueOf(Globals.calculateTotalOfItem((ArrayList<DocumentLines>) oppItems.getOppItem())));

                        binding.totalBeforeDiscontValue.setText(String.valueOf(Globals.calculateTotalOfItem(oppItems.getOppItem())));

                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 500){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 201){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }else if (response.code() == 401){
                    Toasty.warning(act, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }



    private boolean validation(int Items, ArrayList<DocumentLines> oppItemTempList_gl) {
        if (Items <= 0 && oppItemTempList_gl.size() <= 0) {
            Globals.showMessage(getActivity(), "Select atleast 1 item");
            return false;
        } /*else if (Total.isEmpty()) {
            Globals.showMessage(getActivity(), "Enter total before discount");
            return false;
        } else if (discount.isEmpty()) {
            Globals.showMessage(getActivity(), "Enter discount");
            return false;
        }*/
        return true;
    }


    Dialog TaxListdialog;

    private void openCategorydailog() {
        RelativeLayout backPress;
        TextView head_title;
        RecyclerView recyclerview;
        ProgressBar loader;

        TaxListdialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.taxes_alert, null);
        recyclerview = custom_dialog.findViewById(R.id.recyclerview);
        backPress = custom_dialog.findViewById(R.id.back_press);
        head_title = custom_dialog.findViewById(R.id.head_title);
        loader = custom_dialog.findViewById(R.id.loader);
        head_title.setText(getContext().getString(R.string.select_category));
        TaxListdialog.setContentView(custom_dialog);
        TaxListdialog.getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        TaxListdialog.show();

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxListdialog.dismiss();
            }
        });

        loader.setVisibility(View.VISIBLE);

        Call<ItemCategoryResponse> call = NewApiClient.getInstance().getApiService().getAllCategory();
        call.enqueue(new Callback<ItemCategoryResponse>() {
            @Override
            public void onResponse(Call<ItemCategoryResponse> call, Response<ItemCategoryResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == 200) {
                            loader.setVisibility(View.GONE);
                            if (response.body().getData().size() > 0 && response.body().getData() != null) {
                                List<ItemCategoryData> itemsList = response.body().getData();
                                CategoryAdapter adapter = new CategoryAdapter(AddOrderForm_One_Fragment.this, itemsList, TaxListdialog);
                                recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
                                recyclerview.setAdapter(adapter);
                            } else {
                                Globals.setmessage(requireActivity());
                            }

                        }
                    } else {
                        loader.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        QuotationResponse mError = new QuotationResponse();
                        try {
                            String s = response.errorBody().string();
                            mError = gson.fromJson(s, QuotationResponse.class);
                            Toast.makeText(requireActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ItemCategoryResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(requireActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(int po) {
        Intent intent = new Intent(getContext(), ItemsList.class);
        intent.putExtra("CategoryID", po);
        startActivityForResult(intent, ITEMSVIEWCODE);
    }
}