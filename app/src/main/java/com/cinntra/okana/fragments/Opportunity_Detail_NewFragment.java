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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.AddOrderAct;
import com.cinntra.okana.activities.AddQuotationAct;
import com.cinntra.okana.activities.QuotationActivity;
import com.cinntra.okana.adapters.PreviousImageViewAdapter;
import com.cinntra.okana.databinding.OpportunityDetailNewscreenBinding;
import com.cinntra.okana.globals.FileUtilsPdf;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.CommentStage;
import com.cinntra.okana.interfaces.FragmentRefresher;
import com.cinntra.okana.model.AttachmentResponseModel;
import com.cinntra.okana.model.CompleteStageResponse;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.NewOppResponse;
import com.cinntra.okana.model.OpportunityModels.OpportunityItem;
import com.cinntra.okana.model.OpportunityModels.OpportunityStageResponse;
import com.cinntra.okana.model.StagesValue;
import com.cinntra.okana.newapimodel.AttachDocument;
import com.cinntra.okana.newapimodel.LeadDocumentResponse;
import com.cinntra.okana.newapimodel.LeadResponse;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.newapimodel.OpportunityValue;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Opportunity_Detail_NewFragment extends Fragment implements View.OnClickListener, FragmentRefresher, CommentStage, PreviousImageViewAdapter.DeleteItemClickListener {


    boolean bottomView = false;
    String selectedStage = "";
    LinearLayoutManager layoutManager;
    NewOpportunityRespose opportunityItem;
    TimelineAdapter adapter;
    List<StagesValue> arraylist = new ArrayList<StagesValue>();
    PreviousstageAdpater previousstageadapter;
    List<NewOpportunityRespose> particularoppdata = new ArrayList<>();
    String pos;

    OpportunityDetailNewscreenBinding binding;

    File file;
    String picturePath = "";

    private static final int RESULT_LOAD_IMAGE = 101;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;

    public Opportunity_Detail_NewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            opportunityItem = (NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
            Globals.opp = opportunityItem;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = OpportunityDetailNewscreenBinding.inflate(inflater, container, false);
        // View v=inflater.inflate(R.layout.opportunity_detail_newscreen, container, false);
        // ButterKnife.bind(this,v);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding.loader.setVisibility(View.VISIBLE);


        if (Globals.checkInternet(getContext())) {

            callApi(opportunityItem.getId());
        }

        manageclickevent();

        //todo click on attachment
        binding.quotAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAttachmentDialog();


            }
        });


        return binding.getRoot();
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




    private void callApi(String sequentialNo) {
        OpportunityValue opportunityValue = new OpportunityValue();
        opportunityValue.setId(Integer.valueOf(sequentialNo));
        Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().getparticularopportunity(opportunityValue);
        call.enqueue(new Callback<NewOppResponse>() {
            @Override
            public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {

                if (response.body() != null) {
                    particularoppdata.clear();
                    particularoppdata.add(response.body().getData().get(0));
                    setData(response.body().getData().get(0));
                }
            }

            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//        callContactApi(opportunityItem.getCardCode());

    }

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //todo for attachment selected---
        if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && data != null) {
                Bundle extras = data.getExtras();
                Uri selectedImage = data.getData();
                binding.ivQuotationImageSelected.setVisibility(View.GONE);
                binding.tvAttachments.setVisibility(View.GONE);


                picturePath= FileUtilsPdf.getPathFromUri(getContext(),selectedImage);

                Log.e("picturePath", picturePath);
                file = new File(picturePath);
                Log.e("FILE>>>>", "onActivityResult: " + file.getName());

                binding.loader.setVisibility(View.VISIBLE);

                FileExtension = "image";
                callOppAttachmentApi(opportunityItem.getId());


//                binding.ivQuotationImageSelected.setImageURI(selectedImage);

               /* String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.e("picturePath", picturePath);
                    file = new File(picturePath);
                    Log.e("FILE>>>>", "onActivityResult: " + file.getName());

                    binding.loader.setVisibility(View.VISIBLE);

                    FileExtension = "image";
                    callOppAttachmentApi(opportunityItem.getId());

                }*/


            }
        }

        /*** PDF Load**/
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

                    FileExtension = "pdf";
                    callOppAttachmentApi(opportunityItem.getId());
                }


            }
        }

    }


    private String FileExtension = "";


    //todo quotation Attachment api calling---
    private void callOppAttachmentApi(String qt_id) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //todo get model data in multipart body request..
        builder.addFormDataPart("oppId", qt_id.trim());
        builder.addFormDataPart("CreateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("CreateTime", Globals.getTCurrentTime());
        builder.addFormDataPart("FileExtension", FileExtension);
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

        Call<AttachmentResponseModel> call = NewApiClient.getInstance().getApiService().postOppAttachmentUploadApi(requestBody);
        call.enqueue(new Callback<AttachmentResponseModel>() {
            @Override
            public void onResponse(Call<AttachmentResponseModel> call, Response<AttachmentResponseModel> response) {
                binding.loader.setVisibility(View.GONE);

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        callApi(opportunityItem.getId());
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
                        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }

            @Override
            public void onFailure(Call<AttachmentResponseModel> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Log.e("TAG_Attachment_Api", "onFailure: AttachmentAPi" );
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        jsonObject.addProperty("oppId", opportunityItem.getId());

        Call<LeadDocumentResponse> call = NewApiClient.getInstance().getApiService().deleteOppAttachment(jsonObject);
        call.enqueue(new Callback<LeadDocumentResponse>() {
            @Override
            public void onResponse(Call<LeadDocumentResponse> call, Response<LeadDocumentResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getStatus() == 200) {
                        callApi(opportunityItem.getId());

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


    private void callStagesApi(String opp_id) {
        OpportunityItem oppitem = new OpportunityItem();
        oppitem.setOpp_Id(opp_id);
        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().getAllStages(oppitem);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.GONE);
                    if (response.body().getData().size() > 0) {
                        if (response.body().getData().get(response.body().getData().size() - 1).getStatus() == 2) {
                            binding.markascomplete.setVisibility(View.GONE);
                            binding.addStage.setVisibility(View.GONE);
                        } else if (response.body().getData().get(response.body().getData().size() - 1).getStatus() == 1) {
                            binding.markascomplete.setText("Complete");
                        } else {
                            binding.markascomplete.setVisibility(View.VISIBLE);
                            binding.addStage.setVisibility(View.VISIBLE);
                            binding.markascomplete.setText("Mark as Complete");
                        }
                        callstagecommentApi(opportunityItem.getCurrentStageNumber());
                        arraylist.clear();
                        arraylist.addAll(response.body().getData());
                        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                        adapter = new TimelineAdapter(Opportunity_Detail_NewFragment.this, getContext(), arraylist, particularoppdata.get(0).getCurrentStageNo());
                        binding.recyclerView.setLayoutManager(layoutManager);
                        binding.recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
                binding.loader.setVisibility(View.GONE);
            }
        });
    }

    private void manageclickevent() {
        binding.headerFrame.headTitle.setText(getString(R.string.opportunity));
        binding.headerFrame.backPress.setOnClickListener(this);
        binding.option.setOnClickListener(this);
        binding.multioption.setOnClickListener(this);
        binding.addStage.setOnClickListener(this);
        binding.seemore.setOnClickListener(this);
        binding.activityView.setOnClickListener(this);
        binding.markascurrent.setOnClickListener(this);
        binding.markascomplete.setOnClickListener(this);
        binding.chatterView.setOnClickListener(this);
    }



    private void setData(NewOpportunityRespose particularoppdata) {
//        binding.updateProjectStatus.setText(particularoppdata.getProject_Status());
        binding.headerFrame.headTitle.setText(particularoppdata.getOpportunityName());
        binding.opportunityNameValue.setText(particularoppdata.getCurrentStageName());
        binding.tvContactPerson.setText(particularoppdata.getContactPersonName());
        binding.tvClientBudget.setText("Rs 90000/-");
//        binding.tvStageTitle.setText(particularoppdata.getCurrentStageName());
        binding.mainName.setText(particularoppdata.getOpportunityName());
        if (!particularoppdata.getUType().isEmpty())
            binding.typeValue.setText(particularoppdata.getUType().get(0).getType());

        binding.tvProbability.setText(particularoppdata.getUProblty());
        binding.closeDateValue.setText(particularoppdata.getClosingDate());
        binding.leadSourceValue.setText(particularoppdata.getULsource());
        binding.stageValue.setText(particularoppdata.getCurrentStageName());
        binding.opportunityOwnerValue.setText(particularoppdata.getSalesPersonName());


        if (particularoppdata.getU_LEADNM().isEmpty()) {
            binding.tvLead.setText("NA");
        } else {
            binding.tvLead.setText(particularoppdata.getU_LEADNM());
        }
        binding.tvBusinessPartner.setText(particularoppdata.getCustomerName());

        binding.tvRemarks.setText(particularoppdata.getRemarks());

        binding.modifiedOn.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(particularoppdata.getUpdateDate()));
        binding.createdbyDate.setText(Globals.convert_yyyy_mm_dd_to_dd_mm_yyyy(particularoppdata.getStartDate()));

        callStagesApi(opportunityItem.getId());

        //todo set attachment data---
        if (particularoppdata.getAttach().size() > 0) {
            binding.tvAttachments.setVisibility(View.GONE);

            setAttachData(particularoppdata.getAttach());

//            adapter.notifyDataSetChanged();
        }else {
            setAttachData(particularoppdata.getAttach());

            binding.tvAttachments.setVisibility(View.VISIBLE);
        }

    }


    private void setAttachData(List<AttachDocument> data) {
        PreviousImageViewAdapter adapter = new PreviousImageViewAdapter(getContext(), data, "Quotation_Detail");
        binding.attachmentRecyclerList.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));
        binding.attachmentRecyclerList.setAdapter(adapter);
        adapter.setOnDeleteItemClick(this);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.add_stage:
                openaddstagedialog();
                break;
            case R.id.option:
                openoptionpopup();
                break;
            case R.id.multioption:
                editdeletepopup();
                break;
            case R.id.seemore:
                showmorevisibilty();
                break;
            case R.id.activity_view:

                Bundle b = new Bundle();
                b.putParcelable(Globals.OpportunityItem, opportunityItem);
                ActivityFragment fragment = new ActivityFragment();
//    Opportunity_Detail_NewFragment fragment = new Opportunity_Detail_NewFragment();
                fragment.setArguments(b);
                FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.quatoes_main_container, fragment);
                transaction.addToBackStack("Back");
                transaction.commit();
                break;
            case R.id.markascomplete:
                markascompletemethod(particularoppdata.get(0).getCurrentStageNumber());
                break;
            case R.id.chatterView:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Globals.OpportunityItem, opportunityItem);
                ChatterFragment chatterFragment = new ChatterFragment();
                chatterFragment.setArguments(bundle);
                FragmentTransaction chattransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                chattransaction.replace(R.id.quatoes_main_container, chatterFragment);
                chattransaction.addToBackStack("Back");
                chattransaction.commit();

        }
    }

    private void opencommentdialog(String stageno) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.addcomment_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button done = dialog.findViewById(R.id.done);
        EditText comment = dialog.findViewById(R.id.comment);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.COMMENT = comment.getText().toString();
                if (!comment.getText().toString().trim().isEmpty()) {
                    callUpdatestageapi(stageno);
                    dialog.cancel();
                } else {
                    Toast.makeText(getContext(), "Please write some Comment", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    private void markascompletemethod(String currentStageNo) {
        if (binding.markascomplete.getText().equals("Complete")) {
            opefinalpopup();
        } else {
            opencommentdialog(currentStageNo);

        }


    }

    private void opefinalpopup() {


        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.completestage_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Button done = dialog.findViewById(R.id.save);
        Spinner previous_stage = dialog.findViewById(R.id.previous_stage);
        EditText comments_val = dialog.findViewById(R.id.comments_val);


        previous_stage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStage = previous_stage.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStage = previous_stage.getSelectedItem().toString();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation(comments_val, selectedStage)) {
                    CompleteStageResponse completeStageResponse = new CompleteStageResponse();
                    completeStageResponse.setOppId(Integer.valueOf(opportunityItem.getId()));
                    completeStageResponse.setStatus(selectedStage);
                    completeStageResponse.setRemarks(comments_val.getText().toString());
                    completeStageResponse.setUpdateDate(Globals.getTodaysDate());
                    completeStageResponse.setUpdateTime(Globals.getTCurrentTime());
                    callcompletestageApi(completeStageResponse);
                    dialog.cancel();
                }
            }
        });

        dialog.show();

    }

    private boolean validation(EditText text, String selectedStage) {
        if (text.getText().toString().trim().isEmpty()) {
            text.setError("Enter remarks");
            return false;
        } else if (selectedStage.isEmpty()) {
            Toast.makeText(getContext(), "Please Select Stage", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void callcompletestageApi(CompleteStageResponse completeStageResponse) {


        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().completestage(completeStageResponse);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.VISIBLE);
                    callApi(opportunityItem.getId());
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
            }
        });
    }

    private void callUpdatestageapi(String currentStageNo) {
        StagesValue stval = new StagesValue();
        stval.setOppId(Integer.valueOf(opportunityItem.getId()));
        stval.setUpdateDate(Globals.getTodaysDate());
        stval.setUpdateTime(Globals.getTCurrentTime());
        stval.setStageno(currentStageNo);
        stval.setComment(Globals.COMMENT);
        stval.setFile("");
        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().updatestage(stval);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.VISIBLE);
                    callApi(opportunityItem.getId());
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
            }
        });
    }

    private void showmorevisibilty() {
        if (!bottomView) {
            bottomView = true;
            binding.seemore.setText("See less");
            binding.bottomView.setVisibility(View.VISIBLE);
        } else {
            bottomView = false;
            binding.seemore.setText("See more");
            binding.bottomView.setVisibility(View.GONE);
        }

    }

    private void editdeletepopup() {
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.multioption);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.editdeletemenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.edit:
                        Bundle b = new Bundle();
                        b.putParcelable(Globals.OpportunityItem, opportunityItem);
                        Opportunity_Update_Fragment fragment = new Opportunity_Update_Fragment();
//                        Opportunity_Detail_NewFragment fragment = new Opportunity_Detail_NewFragment();
                        fragment.setArguments(b);
                        FragmentTransaction transaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.quatoes_main_container, fragment);
                        transaction.addToBackStack("Back");
                        transaction.commit();

                        break;
                }
                popupMenu.dismiss();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }

    private void openoptionpopup() {
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.option);

        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.optionmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                switch (menuItem.getItemId()) {
                    case R.id.quotation:
                       /* Globals.opportunityData.clear();
                        Prefs.putString(Globals.QuotationListing,opportunityItem.getId());
//                        Prefs.putBoolean(Globals.SelectQuotation,true);
                        Globals.opportunityData.add(opportunityItem);
                        Intent intent = new Intent(getContext(), QuotationActivity.class);
                        startActivity(intent);*/ //todo comment

                        Intent intent = new Intent(getActivity(), AddQuotationAct.class);
                        intent.putExtra("FROM_OPP_ID", String.valueOf(particularoppdata.get(0).getId()));
                        getActivity().startActivity(intent);

                        break;

                    case R.id.order:

                        Intent intent1 = new Intent(getActivity(), AddOrderAct.class);
                        intent1.putExtra("FROM_OPP_ID", String.valueOf(particularoppdata.get(0).getId()));
                        getActivity().startActivity(intent1);

                        break;
                }

                popupMenu.dismiss();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();

    }

    private void openaddstagedialog() {
        previousstageadapter = new PreviousstageAdpater(getContext(), arraylist);
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.addnewstage_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Spinner previous_stage = dialog.findViewById(R.id.previous_stage);
        EditText new_stage = dialog.findViewById(R.id.new_stage);
        EditText date_value = dialog.findViewById(R.id.date_value);
        Button add = dialog.findViewById(R.id.add);
        previous_stage.setAdapter(previousstageadapter);
        previous_stage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = String.valueOf(arraylist.get(position).getStageno());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pos = "0.0";
            }
        });
        date_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.selectDate(dialog.getContext(), date_value);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_stage.length() > 0) {
                    // arraylist.add(pos+1, new TimeLineView(new_stage.getText().toString(),date_value.getText().toString()));
                    callcreatestageapi(pos, new_stage.getText().toString());

                    dialog.cancel();
                } else {
                    Toast.makeText(getContext(), "Fill Properly", Toast.LENGTH_LONG).show();
                }
            }
        });


        dialog.show();
    }

    private void callcreatestageapi(String pos, String s) {
        StagesValue model = new StagesValue();
        model.setName(s);
        model.setOppId(Integer.valueOf(opportunityItem.getId()));
        model.setStageno(pos);
        model.setSequenceNo(opportunityItem.getSequentialNo());
        model.setClosingPercentage("0.0");
        model.setIsSales("tYES");
        model.setIsPurchasing("tYES");
        model.setCancelled("tNO");
        model.setCreateDate(Globals.getTodaysDate());
        model.setUpdateDate(Globals.getTodaysDate());

        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().createStages(model);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                        callApi(opportunityItem.getId());
                    }


                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onRefresh() {
        binding.loader.setVisibility(View.VISIBLE);
        callStagesApi(opportunityItem.getId());

    }

    private void callstagecommentApi(String val) {

        StagesValue oppitem = new StagesValue();
        oppitem.setOppId(Integer.valueOf(opportunityItem.getId()));
        oppitem.setStageno(val);
        Call<OpportunityStageResponse> call = NewApiClient.getInstance().getApiService().getStagesComment(oppitem);
        call.enqueue(new Callback<OpportunityStageResponse>() {
            @Override
            public void onResponse(Call<OpportunityStageResponse> call, Response<OpportunityStageResponse> response) {

                if (response.code() == 200) {
                    binding.loader.setVisibility(View.GONE);
                    assert response.body() != null;
                    if (response.body().getData().get(0).getComment().trim().isEmpty()) {
                        binding.bottomCommentview.setVisibility(View.GONE);
                    } else {
                        binding.bottomCommentview.setVisibility(View.VISIBLE);
                        binding.commentsVal.setText(response.body().getData().get(0).getComment());
                    }
                }
            }

            @Override
            public void onFailure(Call<OpportunityStageResponse> call, Throwable t) {
                Log.e("failure", "" + t.getMessage());
                binding.loader.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void stagecomment(String id, String name) {
        binding.opportunityNameValue.setText(name);
        binding.loader.setVisibility(View.VISIBLE);
        callstagecommentApi(id);
    }
}