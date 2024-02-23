package com.cinntra.okana.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.TestPdfBinding;
import com.cinntra.okana.globals.Globals;
import com.pixplicity.easyprefs.library.Prefs;
import com.webviewtopdf.PdfView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class WebViewToPdf extends AppCompatActivity {
    WebView printWeb;
    TestPdfBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=TestPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
        String url = "";
        String PDfFrom = getIntent().getExtras().getString("PDfFrom");
        String PdfID   = getIntent().getExtras().getString("PdfID");
          if(PDfFrom.equalsIgnoreCase("Invoice"))
          {
              url = Globals.PDFURL+"invoice.html?id="+PdfID+"&token="+ Prefs.getString(Globals.TOKEN,"");
//              url = Globals.PDFURL+"invoice.html?id="+PdfID;
          }
          else if(PDfFrom.equalsIgnoreCase("Quotation"))
          {
              url = Globals.PDFURL+"quotation.html?id="+PdfID+"&token="+ Prefs.getString(Globals.SessionID,"");
            Log.e("pdfurl",url);
          }
          else if(PDfFrom.equalsIgnoreCase("Order")){
              url = Globals.PDFURL+"order.html?id="+PdfID+"&token="+ Prefs.getString(Globals.SessionID,"");
//           url = Globals.PDFURL+"order.html?id="+PdfID;
          }


        // Initializing the WebView
        final WebView webView = (WebView) findViewById(R.id.webViewMain);

        // Initializing the Button
        Button savePdfBtn = (Button) findViewById(R.id.savePdfBtn);


        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
      //  webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        // Setting we View Client
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // initializing the printWeb Object
                printWeb = webView;
            }
        });



        webView.loadUrl(url);


        savePdfBtn.setVisibility(View.GONE);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.share:
                String f_name = String.format("%s.pdf", new SimpleDateFormat("dd_MM_yyyyHH_mm_ss", Locale.US).format(new Date()));
                lab_pdf(printWeb,f_name);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private ParcelFileDescriptor getOutputFile(File path, String fileName)
    {
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (Exception e) {
            Log.e("TAG", "Failed to open ParcelFileDescriptor", e);
        }
        return null;
    }

    
    private void lab_pdf(WebView webView, String f_name){
        String path = Environment.getExternalStorageDirectory().getPath()+"/hana/";
//        File f = new File(path);
        File f = getApplicationContext().getExternalFilesDir(path);
        final String fileName = f_name;

        final ProgressDialog progressDialog=new ProgressDialog(WebViewToPdf.this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        PdfView.createWebPrintJob(WebViewToPdf.this, webView, f, fileName, new PdfView.Callback() {

            @Override
            public void success(String path) {
                progressDialog.dismiss();
               whatsappShare(fileName);
                //PdfView.openPdfFile(Pdf_Test.this,getString(R.string.app_name),"Do you want to open the pdf file?"+fileName,path);
            }

            @Override
            public void failure() {
                progressDialog.dismiss();

            }
        });
    }

    private void whatsappShare(String fName)
              {
        String stringFile = Environment.getExternalStorageDirectory().getPath()+"/hana"+"/"+fName;
//        File file = new File(stringFile);
   File file = getApplicationContext().getExternalFilesDir(stringFile);
   Uri apkURI = FileProvider.getUriForFile(
                this,
                getApplicationContext()
                        .getPackageName() + ".FileProvider", file);



        if(!file.exists()){
            Toast.makeText(this,"File Not exist",Toast.LENGTH_SHORT).show();

        }
        //    Uri path = Uri.fromFile(file);
        //  Log.e("Path==>", path.toString());
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, apkURI);
        share.setPackage("com.whatsapp");

        startActivity(share);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sharedoc, menu);

        return true;
    }



}