package com.cinntra.okana.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;


import com.cinntra.okana.R;
import com.cinntra.okana.adapters.PreviousImageViewAdapter;
import com.cinntra.okana.databinding.FragmentOrderDetailBinding;
import com.cinntra.okana.globals.FileUtilsPdf;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.AttachmentResponseModel;
import com.cinntra.okana.model.PerformaInvoiceModel.QuotationOneAPiModel;
import com.cinntra.okana.model.TokenExpireModel;
import com.cinntra.okana.model.orderModels.OrderDetailResponseModel;
import com.cinntra.okana.model.orderModels.OrderListModel;
import com.cinntra.okana.newapimodel.AttachDocument;
import com.cinntra.okana.newapimodel.LeadDocumentResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailFragment extends Fragment implements PreviousImageViewAdapter.DeleteItemClickListener {

    FragmentActivity act;
    OrderListModel.Data quotationItem1;
    String Flag = "";

    FragmentOrderDetailBinding binding;
    public OrderDetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OrderDetailFragment newInstance(String param1, String param2) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            quotationItem1 = (OrderListModel.Data) b.getSerializable(Globals.QuotationItem);
            Flag = b.getString("Flag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        act = getActivity();
        binding = FragmentOrderDetailBinding.inflate(getLayoutInflater());

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding.loaderLayout.loader.setVisibility(View.GONE);
        binding.headerLayout.add.setVisibility(View.GONE);
        binding.headerLayout.ok.setVisibility(View.GONE);
        binding.headerLayout.headTitle.setText(getString(R.string.order_detail));


        binding.headerLayout.backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                Globals.hideKeybaord(v, getContext());
                getActivity().onBackPressed();
            }
        });


        if (Globals.checkInternet(getContext())) {
            callOrderOneApi();

        }

        //todo click on attachment
        binding.quotAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAttachmentDialog();
            }
        });


        return binding.getRoot();
    }



    //todo calling quotation one api here for show particular details..
    OrderDetailResponseModel.Data orderItem = null;

    private void callOrderOneApi() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", quotationItem1.getId());

        Call<OrderDetailResponseModel> call = NewApiClient.getInstance().getApiService().getOrderOneDetail(jsonObject);
        call.enqueue(new Callback<OrderDetailResponseModel>() {
            @Override
            public void onResponse(Call<OrderDetailResponseModel> call, Response<OrderDetailResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response.code() == 200) {
                            if (response.body().getStatus() == 200) {

                                orderItem = response.body().getData().get(0);
                                setDefaultData();
                            }

                        } else {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 401) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
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
                        binding.loaderLayout.loader.setVisibility(View.GONE);
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
                    binding.loaderLayout.loader.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailResponseModel> call, Throwable t) {
                Log.e("TAG_APi_failure", "onFailure: " + t.getMessage());
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //todo set default data---
    private void setDefaultData() {
        if (!orderItem.getU_OPPRNM().isEmpty()) {
            binding.tvOpportunity.setText(orderItem.getU_OPPRNM());
        } else {
            binding.tvOpportunity.setText("NA");
        }
        if (!orderItem.getU_QUOTNM().isEmpty()) {
            binding.tvQuotation.setText(orderItem.getU_QUOTNM());
        } else {
            binding.tvQuotation.setText("NA");
        }
        if (!orderItem.getCardName().isEmpty()) {
            binding.tvCustomer.setText(orderItem.getCardName());
        } else {
            binding.tvCustomer.setText("NA");
        }

        if (!orderItem.getSiteNumber().isEmpty()) {
            binding.tvSiteNumber.setText(orderItem.getSiteNumber());
        } else {
            binding.tvSiteNumber.setText("NA");
        }
        if (!orderItem.getContactPersonCode().isEmpty()) {
            binding.tvContactPerson.setText(orderItem.getContactPersonCode().get(0).getFirstName());
        } else {
            binding.tvContactPerson.setText("NA");
        }
        if (!orderItem.getSalesPersonCode().isEmpty()) {
            binding.tvSalesEmployee.setText(orderItem.getSalesPersonCode().get(0).getSalesEmployeeName());
        } else {
            binding.tvSalesEmployee.setText("NA");
        }
        if (!orderItem.getTaxDate().isEmpty()) {
            binding.tvPostingDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(orderItem.getTaxDate()));
        } else {
            binding.tvPostingDate.setText("NA");
        }
        if (!orderItem.getDocDueDate().isEmpty()) {
            binding.tvValidDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(orderItem.getDocDueDate()));
        } else {
            binding.tvValidDate.setText("NA");
        }
        if (!orderItem.getDocDate().isEmpty()) {
            binding.tvDocumentDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(orderItem.getDocDate()));
        } else {
            binding.tvDocumentDate.setText("NA");
        }

        if (!orderItem.getGlassDate().isEmpty()) {
            binding.tvGlassOrderDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(orderItem.getGlassDate()));
        } else {
            binding.tvGlassOrderDate.setText("NA");
        }

        if (!orderItem.getCoatingDate().isEmpty()) {
            binding.tvCoatingDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(orderItem.getCoatingDate()));
        } else {
            binding.tvCoatingDate.setText("NA");
        }

        if (!orderItem.getDeliveryDate().isEmpty()) {
            binding.tvDeliveryDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(orderItem.getDeliveryDate()));
        } else {
            binding.tvDeliveryDate.setText("NA");
        }

        if (!orderItem.getComments().isEmpty()) {
            binding.tvRemarks.setText(orderItem.getComments());
        } else {
            binding.tvRemarks.setText("NA");
        }

        if (!orderItem.getOrderID().isEmpty()) {
            binding.tvOrderID.setText(orderItem.getOrderID());
        } else {
            binding.tvOrderID.setText("NA");
        }

        if (!orderItem.getMatirialType().isEmpty()) {
            binding.tvMaterialType.setText(orderItem.getMatirialType());
        } else {
            binding.tvMaterialType.setText("NA");
        }


        //todo set address data--
        if (orderItem.getAddressExtension() != null) {
            binding.tvBillBuildingAddress.setText(orderItem.getAddressExtension().getBillToBuilding());
            binding.tvBillZipCode.setText(orderItem.getAddressExtension().getBillToZipCode());
            binding.tvBillCountry.setText(orderItem.getAddressExtension().getBillToCountry());
            binding.tvBillState.setText(orderItem.getAddressExtension().getBillToState());

            if (!orderItem.getAddressExtension().getBillToStreet().equalsIgnoreCase("")) {
                binding.tvBillingAddress.setText(orderItem.getAddressExtension().getBillToStreet());
            } else {
                binding.tvBillingAddress.setText("NA");
            }

            if (!orderItem.getAddressExtension().getU_SHPTYPB().equalsIgnoreCase("")) {
                binding.tvBillShippingType.setText(orderItem.getAddressExtension().getU_SHPTYPB());
            } else {
                binding.tvBillShippingType.setText("NA");
            }

            if (!orderItem.getAddressExtension().getBillToCity().equalsIgnoreCase("")) {
                binding.tvBillCity.setText(orderItem.getAddressExtension().getBillToCity());
            } else {
                binding.tvBillCity.setText("NA");
            }


            //todo ship to address---

            binding.tvShipBuildingAddress.setText(orderItem.getAddressExtension().getShipToBuilding());

            binding.tvShipZipCode.setText(orderItem.getAddressExtension().getShipToZipCode());
            binding.tvShipCountry.setText(orderItem.getAddressExtension().getShipToCountry());
            binding.tvShipState.setText(orderItem.getAddressExtension().getShipToState());

            if (!orderItem.getAddressExtension().getShipToCity().equalsIgnoreCase("")) {
                binding.tvShipCity.setText(orderItem.getAddressExtension().getShipToCity());
            } else {
                binding.tvShipCity.setText("NA");
            }
            if (!orderItem.getAddressExtension().getShipToStreet().equalsIgnoreCase("")) {
                binding.tvShipToShippingAddress.setText(orderItem.getAddressExtension().getShipToStreet());
            } else {
                binding.tvShipToShippingAddress.setText("NA");
            }

            if (!orderItem.getAddressExtension().getU_SHPTYPS().equalsIgnoreCase("")) {
                binding.tvShipShippingType.setText(orderItem.getAddressExtension().getU_SHPTYPS());
            } else {
                binding.tvShipShippingType.setText("NA");
            }


        }


      /*  //todo set document items line..
        Globals.SelectedItems.clear();

        Globals.SelectedItems.addAll(quotationItem.getDocumentLines());

        String totalBefor = String.valueOf(Globals.calculateTotalOfItemBeforeTax(Globals.SelectedItems));

        binding.tvTotalBeforeDis.setText(Globals.foo(Double.parseDouble(totalBefor)));

        binding.tvDiscount.setText(String.valueOf(quotationItem.getDiscountPercent()));

        String grandTotal = String.valueOf(Globals.calculateTotalOfItemAfterHeaderDis(Globals.SelectedItems, Double.parseDouble(quotationItem.getDiscountPercent())));

        binding.tvGrandTotal.setText(String.valueOf(Globals.foo(Double.parseDouble(grandTotal))));

        //todo reminder for grand total calculations--


        //todo set selected items here---
        if (quotationItem.getDocumentLines().size() > 0) {
            binding.tvLineNoDataFound.setVisibility(View.GONE);
            SaleOrderLinesAdapter adapter = new SaleOrderLinesAdapter(act, quotationItem.getDocumentLines());
            binding.rvDocumentLines.setLayoutManager(new LinearLayoutManager(act, LinearLayoutManager.VERTICAL, false));
            binding.rvDocumentLines.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            binding.rvDocumentLines.setVisibility(View.GONE);
            binding.tvLineNoDataFound.setVisibility(View.VISIBLE);
        }*///todo comment for now due to no require---


        //todo set attachment data---
        if (orderItem.getAttach().size() > 0) {
            binding.tvAttachments.setVisibility(View.GONE);
            setAttachData(orderItem.getAttach());

        }else {
            setAttachData(orderItem.getAttach());

            binding.tvAttachments.setVisibility(View.VISIBLE);
        }

    }


    private void setAttachData(List<AttachDocument> data) {
        PreviousImageViewAdapter adapter = new PreviousImageViewAdapter(getContext(), data, "Order_Detail");
        binding.attachmentRecyclerList.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        binding.attachmentRecyclerList.setAdapter(adapter);
        adapter.setOnDeleteItemClick(this);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDeleteItemClick(int attachId, Dialog dialog) {
        callAttachmentDeleteApi(attachId, dialog);
    }


    //todo call delete attachment api here---
    private void callAttachmentDeleteApi(int attachId, Dialog dialog) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", attachId);
        jsonObject.addProperty("ordId", orderItem.getId());

        Call<LeadDocumentResponse> call = NewApiClient.getInstance().getApiService().deleteOrderAttachment(jsonObject);
        call.enqueue(new Callback<LeadDocumentResponse>() {
            @Override
            public void onResponse(Call<LeadDocumentResponse> call, Response<LeadDocumentResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        callOrderOneApi();

                        dialog.dismiss();
                        Log.d("DeleteAttachResponse =>", "onResponse: Successful");
                    } else {
                        Log.d("DeleteAttachNot200St", "onResponse: QuotAttachmentNot200Status");
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(getActivity(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<LeadDocumentResponse> call, Throwable t) {
                Log.e("TAG_Attachment_Api", "onFailure: AttachmentAPi");
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static final int RESULT_LOAD_PDF = 2;

    private void openAttachmentDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.picturedialog);
        dialog.getWindow().getAttributes().width = ActionBar.LayoutParams.FILL_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView cancel = dialog.findViewById(R.id.canceldialog);
        ImageView gallery = dialog.findViewById(R.id.gallerySelect);
        ImageView camera = dialog.findViewById(R.id.cameraSelect);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i, RESULT_LOAD_IMAGE);*/

                intentDispatcher();
                dialog.dismiss();


            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, RESULT_LOAD_PDF);

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;

    //todo select attachment ---
    private void intentDispatcher() {
        checkAndRequestPermissions();

        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takePictureIntent.setType("image/*");
        startActivityForResult(takePictureIntent, RESULT_LOAD_IMAGE);
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int write = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (write != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[0]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    File file;
    String picturePath = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //todo for attachment selected---
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                Uri selectedImage = data.getData();
                binding.ivQuotationImageSelected.setVisibility(View.VISIBLE);
                binding.tvAttachments.setVisibility(View.GONE);

                picturePath= FileUtilsPdf.getPathFromUri(getContext(),selectedImage);

                Log.e("picturePath", picturePath);
                file = new File(picturePath);
                Log.e("FILE>>>>", "onActivityResult: " + file.getName());

                binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                FileExtension = "image";

                callOrderAttachmentApi(String.valueOf(orderItem.getId()));

//                binding.ivQuotationImageSelected.setImageURI(selectedImage);

               /* String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = act.getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.e("picturePath", picturePath);
                    file = new File(picturePath);
                    Log.e("FILE>>>>", "onActivityResult: " + file.getName());

                    binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                    FileExtension = "image";

                    callOrderAttachmentApi(String.valueOf(orderItem.getId()));
                }*/


            }
        }

        else if (requestCode == RESULT_LOAD_PDF && resultCode == RESULT_OK) {
            if (data != null) {
                Uri pdfUri = data.getData();


                String filePath = FileUtilsPdf.getPathFromUri(getActivity(),pdfUri);
                if (filePath != null) {
                    // Now you have the file path
                    Log.e("File Path", filePath);

                    picturePath = filePath;
                    Log.e("picturePath", picturePath);
                    file = new File(picturePath);
                    Log.e("FILE>>>>", "onActivityResult: " + file.getName());

                    binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                    FileExtension = "pdf";
                    callOrderAttachmentApi(String.valueOf(orderItem.getId()));

                }


            }
        }

    }


    private String FileExtension = "";

    //todo quotation Attachment api calling---
    private void callOrderAttachmentApi(String qt_id) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //todo get model data in multipart body request..
        builder.addFormDataPart("orderId", qt_id.trim());
        builder.addFormDataPart("FileExtension", FileExtension);
        builder.addFormDataPart("CreateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("CreateTime", Globals.getTCurrentTime());
        try {
            if (picturePath.isEmpty()) {
                builder.addFormDataPart("Attach", "");
            } else {
                builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        } catch (Exception e) {
            Log.d("TAG===>", "AddQuotationApi: ");
        }

        MultipartBody requestBody = builder.build();

        Call<AttachmentResponseModel> call = NewApiClient.getInstance().getApiService().postAttachmentUploadApiOrder(requestBody);
        call.enqueue(new Callback<AttachmentResponseModel>() {
            @Override
            public void onResponse(Call<AttachmentResponseModel> call, Response<AttachmentResponseModel> response) {
                binding.loaderLayout.loader.setVisibility(View.GONE);

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        callOrderOneApi();
                        Log.d("AttachmentResponse =>", "onResponse: Successful");
                    }else {
                        Log.d("AttachmentNot200Status", "onResponse: QuotAttachmentNot200Status");
                    }

                } else {
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(act, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<AttachmentResponseModel> call, Throwable t) {
                binding.loaderLayout.loader.setVisibility(View.GONE);
                Log.e("TAG_Attachment_Api", "onFailure: AttachmentAPi" );
                Toast.makeText(act, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}