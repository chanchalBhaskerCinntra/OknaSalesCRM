package com.cinntra.okana.fragments;


import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.bpActivity.AddBPCustomer;
import com.cinntra.okana.activities.FileUtils;
import com.cinntra.okana.adapters.leadAdapter.LeadAttachemntViewAdapter;
import com.cinntra.okana.databinding.LeadInfoBinding;
import com.cinntra.okana.globals.FileUtilsPdf;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.newapimodel.AttachDocument;
import com.cinntra.okana.newapimodel.LeadDocumentResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.LeadValue;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pixplicity.easyprefs.library.Prefs;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadInformation extends Fragment implements View.OnClickListener, LeadAttachemntViewAdapter.DeleteItemClickListener {

    private final int REQUEST_CODE_CHOOSE = 1000;

    LeadValue leadValue;
    Context leadsActivity;
    List<LeadValue> leadValues = new ArrayList<>();
    String[] leadstatus = new String[4];
    String status = "";
    String leadtype = "";
    Integer id;
    ArrayList<LeadTypeData> leadTypeData = new ArrayList<>();
    Context context;

    LeadInfoBinding binding;

    public LeadInformation(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            if (b.getString("From").equalsIgnoreCase("Lead")) {
                leadValue = (LeadValue) b.getParcelable(Globals.LeadDetails);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                id = leadValue.getId();
            } else {
                id = Integer.parseInt(b.getString("From", "2"));
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }



    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = LeadInfoBinding.inflate(inflater, container, false);
        //  View v=inflater.inflate(R.layout.lead_info, container, false);
        // ButterKnife.bind(this,v);
        binding.headerBottomRounded.headTitle.setText("Lead Detail");
        binding.headerBottomRounded.backPress.setOnClickListener(this);
        binding.createBp.setOnClickListener(this);
        binding.history.setOnClickListener(this);
        binding.attachment.setOnClickListener(this);
        leadstatus = getResources().getStringArray(R.array.lead_status);

        // eventManager();
        if (Globals.checkInternet(getContext())) {
            callApi(id);
            callAttachmentApi(id);
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        }


        return binding.getRoot();
    }

    private void callAttachmentApi(Integer id) {
        HashMap<String, Integer> ld = new HashMap<>();
        ld.put("lead_id", id);
        Call<LeadDocumentResponse> call = NewApiClient.getInstance().getApiService().particularleadattachment(ld);
        call.enqueue(new Callback<LeadDocumentResponse>() {
            @Override
            public void onResponse(Call<LeadDocumentResponse> call, Response<LeadDocumentResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        setAttachData(response.body().getData());
                    }

                }
            }

            @Override
            public void onFailure(Call<LeadDocumentResponse> call, Throwable t) {
                Log.e("Api_failure===>", t.getMessage());
            }
        });
    }

    private void setAttachData(List<AttachDocument> data) {
        LeadAttachemntViewAdapter adapter = new LeadAttachemntViewAdapter(getContext(), data, "LeadDetail");
        binding.prevattachment.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        binding.prevattachment.setAdapter(adapter);
        adapter.setOnDeleteItemClick(this);
        adapter.notifyDataSetChanged();
    }


    //todo call delete over ride function---
    @Override
    public void onDeleteItemClick(int attachId, Dialog dialog) {
        callAttachmentDeleteApi(attachId, dialog);
    }



    //todo call delete attachment api here---
    private void callAttachmentDeleteApi(int attachId, Dialog dialog) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", attachId);
        jsonObject.addProperty("lead_id", id);

        Call<LeadDocumentResponse> call = NewApiClient.getInstance().getApiService().deleteLeadAttachment(jsonObject);
        call.enqueue(new Callback<LeadDocumentResponse>() {
            @Override
            public void onResponse(Call<LeadDocumentResponse> call, Response<LeadDocumentResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        callAttachmentApi(id);
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


    private void callApi(int id) {
        LeadValue lv = new LeadValue();
        lv.setId(id);
        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().particularlead(lv);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        leadValues = response.body().getData();
                        setData(leadValues.get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(LeadValue lv) {

        if (lv.getStatus().equalsIgnoreCase("Qualified")) {
            binding.createBp.setVisibility(View.VISIBLE);
        } else {
            binding.createBp.setVisibility(View.GONE);
        }
        leadtype = lv.getLeadType();
        status = lv.getStatus();
        //   status_spinner.setSelection(getStatuspos(lv.getStatus()));
        binding.companyName.setText(lv.getCompanyName());
        binding.contactPersonValue.setText(lv.getContactPerson());
        binding.phoneNumber.setText(lv.getPhoneNumber());
        if (lv.getEmail().isEmpty()) {
            binding.emailValue.setText("NA");
        } else {
            binding.emailValue.setText(lv.getEmail());
        }

        binding.productInterest.setText(lv.getProductInterest());
        binding.dateValue.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(lv.getDate()));
        binding.designation.setText(lv.getDesignation());
        binding.tvCategory.setText(lv.getTurnover());
        binding.tvProjectAmount.setText(lv.getProjectAmount());
        binding.tvLeadstatus.setText(lv.getStatus());
        binding.tvLeadSource.setText(lv.getSource());
        binding.tvLeadPriority.setText(lv.getLeadType());
        if (lv.getCustomerName().isEmpty()) {
            binding.tvCustomerName.setText("NA");
        } else {
            binding.tvCustomerName.setText(lv.getCustomerName());
        }
        if (lv.getLocation().isEmpty()) {
            binding.tvAddress.setText("NA");
        } else {
            binding.tvAddress.setText(lv.getLocation());
        }
        if (lv.getCountry().isEmpty()) {
            binding.tvCountry.setText("NA");
        } else {
            binding.tvCountry.setText(lv.getCountry());
        }
        if (lv.getState().isEmpty()) {
            binding.tvState.setText("NA");
        } else {
            binding.tvState.setText(lv.getState());
        }
        if (lv.getEmployeeId() != null) {
            if (lv.getEmployeeId().getFirstName().isEmpty()) {
                binding.tvCreatedBy.setText("NA");
            } else {
                binding.tvCreatedBy.setText(lv.getEmployeeId().getFirstName());
            }

        } else {
            binding.tvCreatedBy.setText("NA");
        }
        if (lv.getAssignedTo() != null) {
            if (lv.getAssignedTo().getFirstName().isEmpty()) {
                binding.tvAssignTo.setText("NA");
            } else {
                binding.tvAssignTo.setText(lv.getAssignedTo().getFirstName());
            }

        } else {
            binding.tvAssignTo.setText("NA");
        }

        if (lv.getMessage().isEmpty()) {
            binding.tvRemarks.setText("NA");
        } else {
            binding.tvRemarks.setText(lv.getMessage());
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_press:

                getActivity().onBackPressed();
                break;
            case R.id.create_bp:

                Prefs.putString(Globals.AddBp, "Lead");
                Intent intent = new Intent(context, AddBPCustomer.class);
                intent.putExtra(Globals.AddBp, leadValue);
                context.startActivity(intent);

                break;
            case R.id.history:

                Bundle bundle = new Bundle();
                bundle.putParcelable(Globals.Lead_Data, leadValue);
                LeadFollowUpFragment chatterFragment = new LeadFollowUpFragment();
                chatterFragment.setArguments(bundle);
                FragmentTransaction chattransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                chattransaction.add(R.id.customer_lead, chatterFragment).addToBackStack(null);
                chattransaction.commit();
                break;
            case R.id.attachment:

                openimageuploader();
//                openAttachmentDialog();


                break;

        }
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

                openimageuploader();
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



    private void openimageuploader() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Matisse.from(getActivity())
                                    .choose(MimeType.ofAll())
                                    .countable(true)
                                    .maxSelectable(5)
                                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .showPreview(false) // Default is `true`
                                    .forResult(REQUEST_CODE_CHOOSE);
                           /* Intent intent = new Intent();

                            // setting type to select to be image
                            intent.setType("image/*");

                            // allowing multiple image to be selected
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_CHOOSE);*/
                        } else {
                            Toast.makeText(getContext(), "Please enable permission", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    List<Uri> mSelected = new ArrayList<>();
    List<String> path = new ArrayList<>();

    String picturePath = "";
    File file;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && null != data) {

            //mSelected.add(data.getData());
            mSelected.clear();
            mSelected = Matisse.obtainResult(data);
            path.clear();
            for (int i = 0; i < mSelected.size(); i++) {
                path.add(FileUtils.getPath(getContext(), mSelected.get(i)));
            }
            binding.loader.setVisibility(View.VISIBLE);
            updateattachment();

            // Get the Image from data
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

                    binding.loader.setVisibility(View.VISIBLE);

                    updateattachment();

                }


            }
        }

        else {
            // show this if no image is selected
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }

    private void updateattachment() {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("lead_id", leadValues.get(0).getId().toString());
        builder.addFormDataPart("CreatedBy", Globals.TeamEmployeeID);
        builder.addFormDataPart("CreateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("CreateTime", Globals.getTCurrentTime());

        try {
            if (picturePath.isEmpty()) {
                for (int i = 0; i < mSelected.size(); i++) {
                    File file = new File(path.get(i));
                    builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                }

            } else {
                builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
        } catch (Exception e) {
            Log.d("TAG===>", "AddQuotationApi: ");
        }


   /*     for (int i = 0; i < mSelected.size(); i++) {
            File file = new File(path.get(i));
            builder.addFormDataPart("Attach", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }*/

        MultipartBody requestBody = builder.build();

        Call<LeadResponse> call = NewApiClient.getInstance().getApiService().updateLeadattachment(requestBody);
        call.enqueue(new Callback<LeadResponse>() {
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {

                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {

                        Toasty.success(requireContext(), "Add Successfully", Toast.LENGTH_LONG).show();
                        callAttachmentApi(leadValues.get(0).getId());
                    } else {
                        Toasty.warning(requireContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(requireContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
                binding.loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toasty.error(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
