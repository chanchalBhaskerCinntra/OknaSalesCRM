package com.cinntra.okana.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ImageAdapter;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.ImageModel;

import java.io.File;
import java.util.ArrayList;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class GetAllFileFromFolder extends AppCompatActivity {

    RecyclerView recyclerView;


    ArrayList<ImageModel> arrayList = new ArrayList<ImageModel>();



    private final ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            result -> {
            if(result)
            getAllimages();
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_getfile);


        recyclerView = findViewById(R.id.customer_lead_List);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            resultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            resultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }else{
            getAllimages();
        }
    }

    private void getAllimages() {

        arrayList.clear();
        String filepath = "/storage/emulated/0/Pictures";
        File file = new File(filepath);
        File[] files = file.listFiles();
        if(files!=null){
            for(File file1 : files){
                if(file1.getPath().endsWith(".jpg")||file1.getPath().endsWith(".png")||file1.getPath().endsWith(".JPG")||file1.getPath().endsWith(".jpeg")){
                    arrayList.add(new ImageModel(file1.getName(),file1.getPath()));
                }
            }
        }
        Log.d("Filessize", String.valueOf(arrayList.size()));
         ImageAdapter imageAdapter = new ImageAdapter(this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
       // customer_lead_List.setHasFixedSize(true);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        ArrayList<File> sendingfiles = new ArrayList<>();
        builder.setType(MultipartBody.FORM);
        for(int i =0;i<arrayList.size();i++){
            File f = new File(arrayList.get(i).getPath());

            sendingfiles.add(files[i]);
            Log.d("Filepath",f.toString());
            builder.addFormDataPart("Attach", f.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), files[i]));
        }
        builder.addFormDataPart("lead_id", "101");
        builder.addFormDataPart("CreatedBy", Globals.TeamEmployeeID);
        builder.addFormDataPart("CreateDate", Globals.getTodaysDatervrsfrmt());
        builder.addFormDataPart("CreateTime", Globals.getTCurrentTime());

        MultipartBody requestBody = builder.build();
       /* Call<LeadResponse> call = NewApiClient.getInstance().getApiService().updateLeadattachment(requestBody);
        call.enqueue(new Callback<LeadResponse>(){
            @Override
            public void onResponse(Call<LeadResponse> call, Response<LeadResponse> response) {

                if(response.code()==200)
                {
                    assert response.body() != null;
                    if(response.body().getStatus()==200){

                        Toasty.success(GetAllFileFromFolder.this, "Add Successfully", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toasty.warning(GetAllFileFromFolder.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    LeadResponse mError = new LeadResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s, LeadResponse.class);
                        Toast.makeText(GetAllFileFromFolder.this, mError.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                }
            }
            @Override
            public void onFailure(Call<LeadResponse> call, Throwable t) {
                Toasty.error(GetAllFileFromFolder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/






    }
}
