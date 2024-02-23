package com.cinntra.okana.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.databinding.BillInvoiceFragmentBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.QuotationItem;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;



public class InvoiceBill extends Fragment implements  View.OnClickListener{

//    @BindView(R.id.itemListView)
//    RecyclerView recordsView;
//    @BindView(R.id.taxListView)
//    RecyclerView taxListView;
//    @BindView(R.id.framelayout)
//    FrameLayout frameLayout;
//    @BindView(R.id.itemlayoutview)
//    LinearLayout itemlayoutview;
//    @BindView(R.id.taxlayoutview)
//    LinearLayout taxlayoutview;
//    InvoiceItemAdapter recordAdapter;
//    InvoiceTaxAdapter recordAdapter1;
//    @BindView(R.id.scollLay)
//    ScrollView scollLay;
//    @BindView(R.id.add)
//    ImageView button;
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.main_frame)
//    LinearLayout main_frame;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//
//    /*****************Pankaj Declaration******************/
//
//    @BindView(R.id.company_name)
//    TextView company_name;
//    @BindView(R.id.address)
//    TextView address;
//    @BindView(R.id.phone)
//    TextView phone;
//    @BindView(R.id.gstin)
//    TextView gstin;
//    @BindView(R.id.dlno)
//    TextView dlno;
//    @BindView(R.id.cardcode)
//    TextView cardcode;
//    @BindView(R.id.bill_to)
//    TextView bill_to;
//    @BindView(R.id.phone_number)
//    TextView phone_number;
//    @BindView(R.id.gstno)
//    TextView gstno;
//    @BindView(R.id.dl_no)
//    TextView dl_no;
//    @BindView(R.id.pan_no)
//    TextView pan_no;
//    @BindView(R.id.invoice_no_value)
//    TextView invoice_no_value;
//    @BindView(R.id.invoice_dt_value)
//    TextView invoice_dt_value;
//    @BindView(R.id.order_no)
//    TextView order_no;
//    @BindView(R.id.order_dt)
//    TextView order_dt;
//    @BindView(R.id.lr_no)
//    TextView lr_no;
//    @BindView(R.id.lr_dt)
//    TextView lr_dt;
//    @BindView(R.id.no_of_case)
//    TextView no_of_case;
//    @BindView(R.id.line_total)
//    TextView line_total;
//    @BindView(R.id.total_tax)
//    TextView total_tax;
//    @BindView(R.id.round_off)
//    TextView round_off;
//    @BindView(R.id.total_amnt)
//    TextView total_amnt;
//    @BindView(R.id.amount)
//    TextView amount;
//    @BindView(R.id.bank_details)
//    TextView bank_details;
//    @BindView(R.id.acc_no)
//    TextView acc_no;
//    @BindView(R.id.ifsc)
//    TextView ifsc;
//    @BindView(R.id.companyname2)
//    TextView companyname2;


    String[] taxrate = {"TaxRate","115.205","2524.24"};
    String[] taxsum = {"TaxSum","115.205","2524.24"};
    String[] cgst = {"CGST","115.205","2524.24"};
    String[] sgst = {"SGST","115.205","2524.24"};
    String[] total = {"TotalTax","115.205","2524.24"};


    private void setAdapterHeader(){

        DocumentLines items = new DocumentLines();
        items.setItemCode("ItemCode");
        items.setItemName("Desc of Goods");
        items.setQuantity("Qty");
//        items.setUnitPrice("Price");//todo comment by me
        items.setTaxCode("Tax");

        Globals.SelectedItems.add(0,items);
    }

    public InvoiceBill() {
    }

    QuotationItem quotationItem;

    public static InvoiceBill newInstance(String title) {
        InvoiceBill frag = new InvoiceBill();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            quotationItem =(QuotationItem) b.getSerializable(Globals.QuotationItem);
        }
    }


    BillInvoiceFragmentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=BillInvoiceFragmentBinding.inflate(getLayoutInflater());
        View v= inflater.inflate(R.layout.bill_invoice_fragment, container,false);
      //  ButterKnife.bind(this,v);
//        head_title.setText(getResources().getString(R.string.invoice));
//        back_press.setOnClickListener(this);
//        button.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_share_24));
        setAdapterHeader();
        setData();

//        recordsView.setNestedScrollingEnabled(false);
//        taxListView.setNestedScrollingEnabled(false);
//
//        recordAdapter= new InvoiceItemAdapter(getActivity(), Globals.SelectedItems);
//        recordAdapter1= new InvoiceTaxAdapter(getActivity(),taxrate,taxsum,cgst,sgst,total);
//
//        recordsView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
//        recordsView.setAdapter(recordAdapter);
//        taxListView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
//        taxListView.setAdapter(recordAdapter1);
        reqPermission();

//       binding..setOnClickListener(new View.OnClickListener() {
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onClick(View v) {
//
//                share();
//            }
//        });

        return binding.getRoot();
    }

    private void setData() {
//
//        company_name.setText(quotationItem.getCardName());
//        companyname2.setText(quotationItem.getCardName());
//        address.setText(quotationItem.getAddress());
//        bill_to.setText(quotationItem.getAddress());

    }


    private void reqPermission()
    {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }

                })
                .check();
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void share() {
////        View view = View.inflate(this,R.layout.invoicelayout,null);
////        FrameLayout f = view.findViewById(R.id.framelayout);
//       // Bitmap bitmap = getBitmapfromView(main_frame);
////        Bitmap bitmap2 = getBitmapfromView(taxlayoutview);
//        try {
//            File file = new File(getActivity().getExternalCacheDir(),"share.png");
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
//            File file2 = new File(getActivity().getExternalCacheDir(),"share2.png");
//            FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
////            bitmap2.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream2);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//            fileOutputStream2.flush();
//            fileOutputStream2.close();
//            createPdf(bitmap);
//          /*  file.setReadable(true,false);
//            Intent intent = new Intent(Intent.ACTION_SENDTO);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setType("image/png");
//            startActivity(Intent.createChooser(intent,"share by"));*/
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void createPdf(Bitmap bitmap) {
////        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        //  Display display = wm.getDefaultDisplay();
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//
////        float hight = displaymetrics.heightPixels ;
////        float width = displaymetrics.widthPixels ;
//
//     //   int convertHighet = (int) main_frame.getHeight(), convertWidth = (int) main_frame.getWidth();
//
////        Resources mResources = getResources();
////        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);
//        Paint paint = new Paint();
//        PdfDocument document = new PdfDocument();
//        paint.setColor(Color.WHITE);
//     //   PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
//     //   PdfDocument.Page page = document.startPage(pageInfo);
//        Canvas canvas = page.getCanvas();
//        canvas.drawPaint(paint);
//      //  bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        document.finishPage(page);
//
//       /* PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 2).create();
//        PdfDocument.Page page1 = document.startPage(pageInfo1);
//        Canvas canvas2 = page1.getCanvas();
//        canvas2.drawPaint(paint);
//        bitmap2 = Bitmap.createScaledBitmap(bitmap2, convertWidth, convertHighet, true);
//        canvas2.drawBitmap(bitmap2, 0, 0 , paint);
//        document.finishPage(page1);*/
//
//
//        // write the document content
//        String targetPdf = "/sdcard/pdffromlayout.pdf";
//        File filePath;
//        filePath = new File(targetPdf);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getActivity(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
//        }
//        // close the document
//        document.close();
//
//        /* Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();*/
//        Snackbar snackbar = Snackbar
//                .make(frameLayout, "PDF is created!!!", Snackbar.LENGTH_LONG);
//        snackbar.setActionTextColor(Color.BLACK);
//        snackbar.show();
//        // openGeneratedPDF();
//      /*  if (filePath.exists()) {
//            Intent intent = new Intent(Intent.ACTION_SENDTO);
//            intent.setDataAndType(Uri.fromFile(filePath), "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(intent);
//        } else {
//            // sharePdf();
//        }*/
//
//    }

    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapfromView(View view){
        Bitmap retunedBitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(retunedBitmap);

        Drawable drawable = view.getBackground();
        if(drawable !=null){
            drawable.draw(canvas);
        }else {
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);

        return  retunedBitmap ;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
        }
    }
}