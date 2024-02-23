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
import com.cinntra.okana.activities.AddQuotationAct;
import com.cinntra.okana.activities.ItemsList;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.activities.SelectedItems;
import com.cinntra.okana.adapters.CategoryAdapter;
import com.cinntra.okana.databinding.FragmentAddQtFormOneBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.ChangeTeam;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.ContactPersonResponseModel;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.ItemCategoryData;
import com.cinntra.okana.model.ItemCategoryResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import static android.app.Activity.RESULT_OK;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddQuotationForm_One_Fragment extends Fragment implements View.OnClickListener, DatabaseClick, FragmentRefresher {

    FragmentActivity act;
    FragmentAddQtFormOneBinding binding;

    public static int ITEMSVIEWCODE = 1000;

    NewOpportunityRespose oppItemLines = new NewOpportunityRespose();

    String mCardCode, payment_term = "";
    ArrayList<ContactPersonResponseModel.Datum> contactPersonListgl = new ArrayList<>();

    public AddQuotationForm_One_Fragment(NewOpportunityRespose quotationItem1, String BPCode) {
        oppItemLines = quotationItem1;

        this.mCardCode = BPCode;
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddQuotationForm_One_Fragment newInstance(String param1, String param2, NewOpportunityRespose quotationItem1, String BPCode) {
        AddQuotationForm_One_Fragment fragment = new AddQuotationForm_One_Fragment(quotationItem1, BPCode);
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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        act = getActivity();
        binding = FragmentAddQtFormOneBinding.inflate(inflater, container, false);

        Globals.SelectedItems.clear();
        binding.headerLayout.headTitle.setText(getResources().getString(R.string.add_quotation));
        binding.headerLayout.backPress.setOnClickListener(this);
        binding.nextButton.setOnClickListener(this);
        binding.itemFrame.setOnClickListener(this);
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

        binding.discontValue.addTextChangedListener(NumberTextWatcher);


        callContactPersonOneApi();

        return binding.getRoot();
    }


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

    double totalAfterItemDiscount = 0;

    //todo set default data acc to parent proforma invoice.
    private void setDefaultData() {
        Globals.SelectedItems.clear();
        //todo items set..
        Globals.SelectedItems.addAll(oppItemLines.getOppItem());
        Log.e("SelectedLineTAG==>", "setDefaultData: " + Globals.SelectedItems.size());
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");

        totalAfterItemDiscount = Double.parseDouble(String.valueOf(Globals.calculateTotalOfItem((ArrayList<DocumentLines>) oppItemLines.getOppItem())));

        binding.totalBeforeDiscontValue.setText(String.valueOf(Globals.calculateTotalOfItem((ArrayList<DocumentLines>) oppItemLines.getOppItem())));

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) {
            getQuotationDocLin();
            if (validation(Globals.SelectedItems.size(), oppItemTempList_gl)) {
                try {
                    if (binding.discontValue.getText().toString().equalsIgnoreCase("")) {
                        Globals.showErrorMessage(getActivity(), "Enter Discount Value");
                    } else {
                        AddQuotationAct.addQuotationObj.setDiscountPercent(Float.valueOf(binding.discontValue.getText().toString().trim()));
                    }
                    AddQuotationAct.addQuotationObj.setNetTotal(Float.valueOf(binding.totalBeforeDiscontValue.getText().toString().trim()));

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
                            documentLines.setItemName(oppItemTempList_gl.get(j).getItemName());
                            documentLines.setItemDescription(oppItemTempList_gl.get(j).getItemDescription());
                           /* if (oppItemTempList_gl.get(j).getItemName() != null || !oppItemTempList_gl.get(j).getItemName().equalsIgnoreCase("")) {
                                documentLines.setItemDescription(oppItemTempList_gl.get(j).getItemName());

                            } else {
                                documentLines.setItemDescription(oppItemLines.getOppItem().get(j).getItemDescription());
                            }*/
//                            documentLines.setItemDescription(oppItemTempList_gl.get(j).getItemDescription());
                            documentLines.setTaxCode(oppItemTempList_gl.get(j).getTaxCode());
                            documentLines.setTax("18");//oppItemTempList_gl.get(j).getTax()

                            documentLineArrayList.addAll(Collections.singleton(documentLines));
                        }
                    } else {
                        for (int j = 0; j < oppItemLines.getOppItem().size(); j++) {
                            DocumentLines documentLines = new DocumentLines();

                            documentLines.setId(oppItemLines.getOppItem().get(j).getId());
                            documentLines.setQuantity(oppItemLines.getOppItem().get(j).getQuantity());
                            documentLines.setUnitPrice(oppItemLines.getOppItem().get(j).getUnitPrice());
                            documentLines.setDiscountPercent(oppItemLines.getOppItem().get(j).getDiscountPercent());
                            documentLines.setItemCode(oppItemLines.getOppItem().get(j).getItemCode());
                            documentLines.setItemName(oppItemLines.getOppItem().get(j).getItemName());
                            documentLines.setItemDescription(oppItemLines.getOppItem().get(j).getItemDescription());
                            documentLines.setTaxCode(oppItemLines.getOppItem().get(j).getTaxCode());
                            documentLines.setTax("18");//oppItemLines.getOppItem().get(j).getTax()

                            documentLineArrayList.addAll(Collections.singleton(documentLines));
                        }
                    }

                    AddQuotationAct.addQuotationObj.setDocumentLines(documentLineArrayList);

                    AddQuotationForm_Fianl_Fragment fragment = new AddQuotationForm_Fianl_Fragment(oppItemLines, mCardCode);
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.temp, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        } else if (v.getId() == R.id.item_frame) {

            if (Globals.SelectedItems.size() == 0) {
                 /*   NewItemSelectionSheet bottomSheetFragment = new NewItemSelectionSheet(AddQuotationForm_One_Fragment.this);
                    bottomSheetFragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), bottomSheetFragment.getTag());
*/
                   /* Intent intent = new Intent(getActivity(), ItemsList.class);
                    intent.putExtra("CardCode", mCardCode);
                    startActivityForResult(intent, ITEMSVIEWCODE);*/
                openCategorydailog(); //todo comment by me.
            } else {
                Intent intent = new Intent(getContext(), SelectedItems.class);
                intent.putExtra("FromWhere", "addQt");
                intent.putExtra("CardCode", mCardCode);
                startActivityForResult(intent, ITEMSVIEWCODE);
            }

        } else if (v.getId() == R.id.back_press) {
            requireActivity().onBackPressed();
//            getFragmentManager().popBackStackImmediate();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            dcc.setQuantity(dc.getQuantity());
            dcc.setUnitPrice(dc.getUnitPrice());
            dcc.setDiscountPercent(dc.getDiscountPercent());
            dcc.setItemCode(dc.getItemCode());
            dcc.setItemName(dc.getItemName());
            dcc.setItemDescription(dc.getItemDescription());
            dcc.setTaxCode(dc.getTaxCode());
            dcc.setTax(dc.getTax());
//            dcc.setId(dc.getId());

            oppItemTempList_gl.add(dcc);
        }
        return oppItemTempList_gl;
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.itemCount.setText("Item (" + Globals.SelectedItems.size() + ")");
    }


    //todo calling contact person api..
    private void callContactPersonOneApi() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CardCode", mCardCode);
        Call<ContactPersonResponseModel> call = NewApiClient.getInstance().getApiService().getContactPerson(jsonObject);
        call.enqueue(new Callback<ContactPersonResponseModel>() {
            @Override
            public void onResponse(Call<ContactPersonResponseModel> call, Response<ContactPersonResponseModel> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        contactPersonListgl.clear();
                        contactPersonListgl.addAll(response.body().getData());

                        setDefaultData();
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<ContactPersonResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation(int Items, ArrayList<DocumentLines> oppItemTempList_gl) { //, String Total, String discount, String shipping_handling
        if (Items <= 0 && oppItemTempList_gl.size() <= 0) {
            Globals.showMessage(getActivity(), "Select atleast 1 item");
            return false;
        }

        return true;
    }

    Dialog TaxListdialog;

    private void openCategorydailog() {
        RelativeLayout backPress;
        TextView head_title;
        RecyclerView recyclerview;
        SpinKitView loader;

        TaxListdialog = new Dialog(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View custom_dialog = layoutInflater.inflate(R.layout.taxes_alert, null);
        recyclerview = custom_dialog.findViewById(R.id.recyclerview);
        backPress = custom_dialog.findViewById(R.id.back_press);
        head_title = custom_dialog.findViewById(R.id.head_title);
        loader = custom_dialog.findViewById(R.id.loader);
        head_title.setText(getContext().getString(R.string.select_tax));
        TaxListdialog.setContentView(custom_dialog);
        TaxListdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                CategoryAdapter adapter = new CategoryAdapter(AddQuotationForm_One_Fragment.this, itemsList, TaxListdialog);
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
    public void onRefresh() {
        Intent intent = new Intent(getActivity(), ItemsList.class);
        intent.putExtra("CardCode", mCardCode);
        startActivityForResult(intent, ITEMSVIEWCODE);
    }


    @Override
    public void onClick(int po) {
        Intent intent = new Intent(getContext(), ItemsList.class);
        intent.putExtra("CategoryID", po);
        startActivityForResult(intent, ITEMSVIEWCODE);
    }
}