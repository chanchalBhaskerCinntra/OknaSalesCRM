package com.cinntra.okana.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.google.android.material.snackbar.Snackbar;
import com.webviewtopdf.PdfView;

import java.io.File;



public class ShowDocument extends Fragment implements View.OnClickListener {


//    @BindView(R.id.show_doucument)
//    WebView show_document;
//    @BindView(R.id.add)
//    ImageView add;
//
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
    WebView printWeb;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.invoicegenrate, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        ButterKnife.bind(this,v);
//        back_press.setOnClickListener(this);
//        add.setOnClickListener(this);
//        head_title.setText(getString(R.string.invoice));
//        add.setImageResource(R.drawable.ic_arrow_downward_24);






//        show_document.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                // initializing the printWeb Object
//                printWeb = show_document;
//            }
//        });
//
//        show_document.loadUrl("file:///android_asset/purchase.html");
//        show_document.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        show_document.getSettings().setBuiltInZoomControls(true);

        return v;

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_press:
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                getActivity().onBackPressed();
                break;
            case R.id.add:
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
                final String fileName="Test.pdf";

                final ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                PdfView.createWebPrintJob(requireActivity(), printWeb, directory, fileName, new PdfView.Callback() {

                    @Override
                    public void success(String path) {
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(requireView(), "Download successfully", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        openshareDialog(path);
                     //   PdfView.openPdfFile(getActivity(),getString(R.string.app_name),"Do you want to open the pdf file?"+fileName,path);
                    }

                    @Override
                    public void failure() {
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar
                                .make(requireView(), "Download Failed", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                });
                break;
        }
    }

    private void openshareDialog(String fileName) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.sharepdfdialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        TextView share = dialog.findViewById(R.id.share);
        TextView cancel = dialog.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri path = Uri.parse(fileName);
                Intent pdfOpenintent = new Intent();
                pdfOpenintent.setAction(Intent.ACTION_SEND);
                pdfOpenintent.setType("application/pdf");
                pdfOpenintent.putExtra(Intent.EXTRA_STREAM,path);
                try {
                    Intent shareIntent = Intent.createChooser(pdfOpenintent, getResources().getText(R.string.share));
                    startActivity(shareIntent);

                }
                catch (ActivityNotFoundException e) {

                }
                dialog.cancel();

            }

        });






        dialog.show();

    }


}
