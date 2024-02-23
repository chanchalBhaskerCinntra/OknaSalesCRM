package com.cinntra.okana.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cinntra.okana.activities.InvoiceItemList;
import com.cinntra.okana.adapters.SalesEmployeeAutoAdapter;
import com.cinntra.okana.databinding.InvoiceDetailBinding;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.R;
import com.cinntra.okana.adapters.ContactPersonAdapter;
import com.cinntra.okana.adapters.SalesEmployeeAdapter;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.InvoiceNewData;
import com.cinntra.okana.model.QuotationDocumentLines;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.UpdateQuotationModel;
import com.cinntra.okana.viewModel.ItemViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static android.app.Activity.RESULT_OK;


public class Invoice_Detail_Fragment extends Fragment implements View.OnClickListener {
    public static int SelectItemCode = 105;
    private String QT_ID = "";
    public static String CardValue;
    public static String salePCode;
    UpdateQuotationModel addQuotationObj;
    FragmentActivity act;
//    @BindView(R.id.add)
//    ImageView add;
//
//
//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//
//
//
//    @BindView(R.id.img1)
//    ImageView img1;
//
//
//    /******General Tab********/
//
//    @BindView(R.id.general)
//    TextView general;
//    @BindView(R.id.tab_1)
//    LinearLayout tab_1;
//    @BindView(R.id.general_frame)
//    FrameLayout general_frame;
//    @BindView(R.id.opportunity_name_value)
//    EditText opportunity_name_value;
//
//
//    @BindView(R.id.posting_value)
//    EditText posting_value;
//    @BindView(R.id.valid_till_value)
//    EditText valid_till_value;
//    @BindView(R.id.document_date_value)
//    EditText document_date_value;
//    @BindView(R.id.remark_value)
//    EditText remark_value;
//    @BindView(R.id.submit)
//    Button submit;
//    @BindView(R.id.companyNameCard)
//    CardView companyNameCard;
//    @BindView(R.id.company_name)
//    TextView company_name;
//    @BindView(R.id.qt_status)
//    TextView qt_status;
//    @BindView(R.id.valid_untill_date)
//    TextView valid_untill_date;
//    @BindView(R.id.contact_person_spinner)
//    Spinner contact_person_spinner;
//    @BindView(R.id.salesemployee_spinner)
//    Spinner salesemployee_spinner;
//
//
//    /******Total Tab********/
//    @BindView(R.id.tab_2)
//    LinearLayout tab_2;
//    @BindView(R.id.total_frame)
//    FrameLayout total_frame;
//    @BindView(R.id.total)
//    TextView total;
//    @BindView(R.id.total_before_discont_value)
//    EditText total_before_discont_value;
//    @BindView(R.id.discont_value)
//    EditText _discont_value;
//    @BindView(R.id.tax_value)
//    EditText tax_value;
//    @BindView(R.id.total_value)
//    EditText total_value;
//    @BindView(R.id.quo_namevalue)
//    EditText quo_namevalue;
//    @BindView(R.id.shipping_value)
//    EditText shipping_value;
//    @BindView(R.id.next_button)
//    Button next;
//    @BindView(R.id.items_count)
//    TextView items_count;
//    @BindView(R.id.item_frame)
//    RelativeLayout item_frame;
//
//
//
//    /******Address Tab********/
//    @BindView(R.id.tab_3)
//    LinearLayout tab_3;
//    @BindView(R.id.prepared_frame)
//    FrameLayout prepared_frame;
//    @BindView(R.id.address)
//    TextView address;
//    @BindView(R.id.billing_name_value)
//    EditText billing_name_value;
//    @BindView(R.id.zip_code_value)
//    EditText zip_code_value;
//    @BindView(R.id.shipping_spinner)
//    Spinner shipping_spinner;
//    @BindView(R.id.billing_address_value)
//    EditText billing_address_value;
//    @BindView(R.id.done_button)
//    Button done;
//    @BindView(R.id.validDate)
//    LinearLayout validDate;
//    @BindView(R.id.validCal)
//    ImageView validCal;
//    @BindView(R.id.documentDate)
//    LinearLayout documentDate;
//    @BindView(R.id.docCal)
//    ImageView docCal;
//    @BindView(R.id.postingDate)
//    LinearLayout postingDate;
//    @BindView(R.id.postCal)
//    ImageView postCal;
//    @BindView(R.id.shipping_name_value)
//    EditText shipping_name_value;
//    @BindView(R.id.shipping_address_value)
//    EditText shipping_address_value;
//    @BindView(R.id.zipcode_value2)
//    EditText zipcode_value2;
//
//    @BindView(R.id.shipping_spinner2)
//    Spinner shipping_spinner2;
//    @BindView(R.id.ship_block)
//    LinearLayout ship_block;
//    @BindView(R.id.quote_information)
//    TextView quote_information;
//    @BindView(R.id.img9)
//    ImageView img9;
//    @BindView(R.id.checkboxManager)
//    RelativeLayout checkboxManager;
//    @BindView(R.id.oppView)
//    LinearLayout oppView;
//    @BindView(R.id.img11)
//    ImageView img11;
//    @BindView(R.id.bpView)
//    LinearLayout bpView;
//    @BindView(R.id.ok)
//    ImageView ok;
//    @BindView(R.id.country_value)
//    TextView country_value;
//    @BindView(R.id.state_value)
//    TextView state_value;
//    @BindView(R.id.ship_country_value)
//    TextView ship_country_value;
//    @BindView(R.id.ship_state_value)
//    TextView ship_state_value;
//

    InvoiceDetailBinding binding;


    private void setDisable() {
        binding.addressContent.invoiceaddressSection.shipBlock.setVisibility(View.VISIBLE);
        binding.addressContent.invoiceaddressSection.checkboxManager.setVisibility(View.GONE);
        binding.quotationGeneralContent.bpView.setVisibility(View.GONE);
//        binding.quotationGeneralContent.img9.setVisibility(View.GONE);
        binding.addressContent.invoiceaddressSection.img11.setVisibility(View.GONE);
        binding.quotationGeneralContent.documentDateValue.setFocusable(false);
        binding.quotationGeneralContent.documentDateValue.setClickable(false);
        binding.quotationGeneralContent.quoNamevalue.setFocusable(false);
        binding.quotationGeneralContent.quoNamevalue.setClickable(false);
        binding.quotationGeneralContent.documentDate.setFocusable(false);
        binding.quotationGeneralContent.documentDate.setClickable(false);
        binding.quotationGeneralContent.validTillValue.setFocusable(false);
        binding.quotationGeneralContent.validTillValue.setClickable(false);
        binding.quotationGeneralContent.validDate.setFocusable(false);
        binding.quotationGeneralContent.validDate.setClickable(false);
        binding.quotationGeneralContent.edCreatedDate.setFocusable(false);
        binding.quotationGeneralContent.edCreatedDate.setClickable(false);
        binding.quotationGeneralContent.postingDate.setFocusable(false);
        binding.quotationGeneralContent.postingDate.setClickable(false);
        binding.addressContent.invoiceaddressSection.billingAddressValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.billingAddressValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.shippingSpinner.setFocusable(false);
        binding.addressContent.invoiceaddressSection.shippingSpinner.setClickable(false);

        binding.addressContent.invoiceaddressSection.shippingSpinner.setFocusable(false);
        binding.addressContent.invoiceaddressSection.shippingSpinner.setClickable(false);

        binding.addressContent.invoiceaddressSection.zipCodeValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.zipCodeValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.billingNameValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.billingNameValue.setClickable(false);

        binding.quotationTotalContent.discontValue.setFocusable(false);
        binding.quotationTotalContent.discontValue.setClickable(false);

        binding.quotationTotalContent.totalBeforeDiscontValue.setFocusable(false);
        binding.quotationTotalContent.totalBeforeDiscontValue.setClickable(false);
        binding.quotationGeneralContent.remarkValue.setFocusable(false);
        binding.quotationGeneralContent.remarkValue.setClickable(false);


        binding.quotationGeneralContent.opportunityNameValue.setFocusable(false);
        binding.quotationGeneralContent.opportunityNameValue.setClickable(false);


        binding.addressContent.invoiceaddressSection.shippingNameValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.shippingNameValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.shippingAddressValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.shippingAddressValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.zipcodeValue2.setFocusable(false);
        binding.addressContent.invoiceaddressSection.zipcodeValue2.setClickable(false);
        binding.addressContent.invoiceaddressSection.stateValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.stateValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.shipCountryValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.shipStateValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.countryValue.setFocusable(false);
        binding.addressContent.invoiceaddressSection.countryValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.shipStateValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.countryValue.setClickable(false);
        binding.addressContent.invoiceaddressSection.shippingSpinner.setEnabled(false);
        binding.addressContent.invoiceaddressSection.shippingSpinner2.setEnabled(false);


        binding.quotationGeneralContent.validCal.setVisibility(View.GONE);
        binding.quotationGeneralContent.postCal.setVisibility(View.GONE);
        binding.quotationGeneralContent.docCal.setVisibility(View.GONE);
        binding.quotationGeneralContent.postCal.setVisibility(View.GONE);

    }

    public Invoice_Detail_Fragment() {
        //Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static com.cinntra.okana.fragments.Invoice_Detail_Fragment newInstance(String param1, String param2) {
        com.cinntra.okana.fragments.Invoice_Detail_Fragment fragment = new com.cinntra.okana.fragments.Invoice_Detail_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    InvoiceNewData quotationItem;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b = getArguments();
            quotationItem = (InvoiceNewData) b.getSerializable(Globals.QuotationItem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        binding = InvoiceDetailBinding.inflate(inflater, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        act = getActivity();
        View v = inflater.inflate(R.layout.invoice_detail, container, false);
        // ButterKnife.bind(this,v);
        setDefaults();
        setData();


        return binding.getRoot();
    }

    String ContactPersonCode = "";
    String salesPersonCode = "";

    private void setData() {

//        binding.quotationGeneralContent.salesemployeeSpinner.setEnabled(false);

        QT_ID = quotationItem.getDocEntry();
        ContactPersonCode = String.valueOf(quotationItem.getContactPersonCode().get(0).getId());
        salesPersonCode = quotationItem.getSalesPersonCode().get(0).getSalesEmployeeCode();
        if (Globals.checkInternet(getActivity()))
            callContactApi(quotationItem.getCardCode());


        Globals.SelectedItems.clear();
        /*********** Set Data In Header Section**************/
//          qt_status.setText(Globals.getStatus(quotationItem.getDocumentStatus()));
        if (Globals.viewStatus(quotationItem.getDocumentStatus()) == "Close") {
            binding.quotationGeneralContent.qtStatus.setText("Closed");
            binding.quotationGeneralContent.qtStatus.setBackground(getResources().getDrawable(R.drawable.closeroundsaffron));

        } else {
            binding.quotationGeneralContent.qtStatus.setText("Open");
            binding.quotationGeneralContent.qtStatus.setBackground(getResources().getDrawable(R.drawable.openroundedgreen));
        }

        //      Total_Before_Disscount = Total_Before_Disscount(quotationItem.getDocumentLines());

        binding.quotationGeneralContent.companyName.setText(quotationItem.getCardName());
        binding.quotationGeneralContent.validUntillDate.setText(getResources().getString(R.string.valid_untill) + ": " + quotationItem.getDocDueDate());

        binding.quotationTotalContent.discontValue.setText(quotationItem.getDiscountPercent());//
        binding.quotationTotalContent.totalBeforeDiscontValue.setText(Globals.getAmmount(quotationItem.getDocCurrency(), String.valueOf(Total_Before_Disscount)));

        binding.quotationGeneralContent.documentDateValue.setText(quotationItem.getDocDate());
        binding.quotationGeneralContent.validTillValue.setText(quotationItem.getDocDueDate());
        binding.quotationGeneralContent.edCreatedDate.setText(quotationItem.getCreationDate());
        binding.quotationGeneralContent.remarkValue.setText(quotationItem.getComments());
        // billing_name_value.setText(quotationItem.getAddressExtension().getBillToCity());
        if (quotationItem.getAddressExtension().get(0).getBillToStreet() != null)
            binding.addressContent.invoiceaddressSection.billingNameValue.setText(quotationItem.getAddressExtension().get(0).getBillToStreet());
        if (quotationItem.getAddressExtension().get(0).getBillToBuilding() != null)
            binding.addressContent.invoiceaddressSection.billingAddressValue.setText(quotationItem.getAddressExtension().get(0).getBillToBlock());
        if (quotationItem.getAddressExtension().get(0).getBillToZipCode() != null)
            binding.addressContent.invoiceaddressSection.zipCodeValue.setText(quotationItem.getAddressExtension().get(0).getBillToZipCode());
        if (quotationItem.getAddressExtension().get(0).getShipToStreet() != null)
            binding.addressContent.invoiceaddressSection.shippingNameValue.setText(quotationItem.getAddressExtension().get(0).getShipToStreet());
        if (quotationItem.getAddressExtension().get(0).getShipToBuilding() != null)
            binding.addressContent.invoiceaddressSection.shippingAddressValue.setText(quotationItem.getAddressExtension().get(0).getShipToBlock());
        if (quotationItem.getAddressExtension().get(0).getShipToBuilding() != null)
            binding.addressContent.invoiceaddressSection.zipcodeValue2.setText(quotationItem.getAddressExtension().get(0).getShipToBuilding());
        if (quotationItem.getAddressExtension().get(0).getU_BSTATE() != null)
            binding.addressContent.invoiceaddressSection.stateValue.setText(quotationItem.getAddressExtension().get(0).getU_BSTATE());
        if (quotationItem.getAddressExtension().get(0).getU_BCOUNTRY() != null)
            binding.addressContent.invoiceaddressSection.countryValue.setText(quotationItem.getAddressExtension().get(0).getU_BCOUNTRY());
        if (quotationItem.getAddressExtension().get(0).getU_SSTATE() != null)
            binding.addressContent.invoiceaddressSection.shipStateValue.setText(quotationItem.getAddressExtension().get(0).getU_SSTATE());
        if (quotationItem.getAddressExtension().get(0).getU_SCOUNTRY() != null)
            binding.addressContent.invoiceaddressSection.shipCountryValue.setText(quotationItem.getAddressExtension().get(0).getU_SCOUNTRY());
        binding.quotationTotalContent.itemsCount.setText("Items (" + quotationItem.getDocumentLines().size() + ")");
        //  Globals.SelectedItems.addAll(Globals.ItemarrayListConverter(quotationItem.getDocumentLines()));
        Globals.SelectedItems.addAll(quotationItem.getDocumentLines());


        /*********** Set Data In Content Section**************/
        frameManager(binding.generalFrame, binding.totalFrame, binding.preparedFrame, binding.general, binding.total, binding.address);


        /****************** Data for Api use ************************/
        addQuotationObj = new UpdateQuotationModel();
        CardValue = quotationItem.getCardCode();
        salePCode = String.valueOf(quotationItem.getContactPersonCode().get(0).getId());
        addQuotationObj.setCardCode(CardValue);
        addQuotationObj.setSalesPerson(salePCode);


        salesEmployeeItemList = Globals.getSaleEmployeeArrayList(Globals.SalesEmployeeList);
        if (salesEmployeeItemList == null)
            callSalessApi();
        else {
            salesadapter = new SalesEmployeeAdapter(getActivity(), salesEmployeeItemList);
//            binding.quotationGeneralContent.salesemployeeSpinner.setAdapter(salesadapter);
//            if (!salesEmployeeItemList.isEmpty() && !salesPersonCode.isEmpty())
//                binding.quotationGeneralContent.salesemployeeSpinner.setSelection(Globals.getSelectedSalesP(salesEmployeeItemList, salesPersonCode));
        }


    }

    float Total_Before_Disscount = 0;

    private float Total_Before_Disscount(ArrayList<QuotationDocumentLines> list) {
        float result = 0;
        for (int i = 0; i < list.size(); i++) {
            result = result + Float.parseFloat(list.get(i).getQuantity()) * Float.parseFloat(list.get(i).getPrice());
        }
        return result;
    }

    private void setDefaults() {
        binding.quotationGeneralContent.companyNameCard.setVisibility(View.VISIBLE);
        binding.headerBottomroundEdit.add.setImageResource(R.drawable.ic_arrow_downward_24);
        binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        binding.headerBottomroundEdit.headTitle.setText(getString(R.string.invoice));
        binding.quotationGeneralContent.quoteInformation.setText(getResources().getString(R.string.invoice_information));
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
        binding.general.setOnClickListener(this);
        binding.total.setOnClickListener(this);
        binding.tab1.setOnClickListener(this);
        binding.tab2.setOnClickListener(this);
        binding.tab3.setOnClickListener(this);
        binding.headerBottomroundEdit.add.setOnClickListener(this);
        binding.address.setOnClickListener(this);
        binding.quotationGeneralContent.oppView.setVisibility(View.GONE);
        binding.quotationTotalContent.nextButton.setVisibility(View.GONE);
        binding.addressContent.invoiceaddressSection.doneButton.setVisibility(View.GONE);
        binding.quotationGeneralContent.submit.setVisibility(View.GONE);
        binding.quotationTotalContent.itemFrame.setOnClickListener(this);
        setDisable();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.add:
       /*  FragmentManager fm = getChildFragmentManager();
         Bundle b = new Bundle();
         b.putSerializable(Globals.QuotationItem,quotationItem);
         InvoiceBill invoiceBill = new InvoiceBill();
         invoiceBill.setArguments(b);
         FragmentTransaction ft = fm.beginTransaction();
         ft.replace(R.id.mainPdfFrame,invoiceBill).addToBackStack(null).commit();*/
                break;
            case R.id.back_press:
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                getActivity().onBackPressed();
                break;
            case R.id.tab_1:
            case R.id.general:
                frameManager(binding.generalFrame, binding.totalFrame, binding.preparedFrame, binding.general, binding.total, binding.address);
                break;
            case R.id.tab_2:
            case R.id.total:
                frameManager(binding.totalFrame, binding.generalFrame, binding.preparedFrame, binding.total, binding.general, binding.address);
                break;
            case R.id.tab_3:
            case R.id.address:
                frameManager(binding.preparedFrame, binding.generalFrame, binding.totalFrame, binding.address, binding.general, binding.total);
                break;
            case R.id.submit:
                String opp_name = binding.quotationGeneralContent.opportunityNameValue.getText().toString().trim();
                if (validation(opp_name, binding.quotationGeneralContent.remarkValue.getText().toString().trim(), ContactPersonCode)) {
                    frameManager(binding.totalFrame, binding.generalFrame, binding.preparedFrame, binding.total, binding.general, binding.address);
                }
                break;
            case R.id.next_button:
                frameManager(binding.preparedFrame, binding.generalFrame, binding.totalFrame, binding.address, binding.general, binding.total);
                break;
            case R.id.done_button:
                break;
            case R.id.item_frame:
                Globals.inventory_item_close = true;
                Intent intent = new Intent(act, InvoiceItemList.class);
                intent.putExtra("FromWhere", "invoices");
                startActivityForResult(intent, SelectItemCode);
                break;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void pdfgeneration() {
        reqPermission();
        View v = getLayoutInflater().inflate(R.layout.invoicelayout, null, true);
        FrameLayout frameLayout = v.findViewById(R.id.framelayout);
        // View view = inflater.inflate(R.layout.invoicelayout,null,true);
        Bitmap bitmap = getBitmapfromView(frameLayout);
        try {
            File file = new File(getContext().getExternalCacheDir(), "share.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            createPdf(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(Bitmap bitmap) {
        // WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/pdffromlayout.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(getContext(), "PDF is created!!!", Toast.LENGTH_SHORT).show();


    }

    private void reqPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }

                })
                .check();
    }

    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapfromView(View view) {

        Bitmap retunedBitmap = Bitmap.createBitmap(200, 450, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(retunedBitmap);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        } else {
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);
        return retunedBitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SelectItemCode) {
            binding.quotationTotalContent.itemsCount.setText("Item (" + Globals.SelectedItems.size() + ")");
        }

    }


    private void frameManager(FrameLayout visiblle_frame, FrameLayout f1, FrameLayout f2,
                              TextView selected, TextView t1, TextView t2) {
        selected.setTextColor(getResources().getColor(R.color.colorPrimary));
        t1.setTextColor(getResources().getColor(R.color.black));
        t2.setTextColor(getResources().getColor(R.color.black));

        visiblle_frame.setVisibility(View.VISIBLE);
        f1.setVisibility(View.GONE);
        f2.setVisibility(View.GONE);

    }

    private boolean validation(
            String cardCode, String stagesCode, String remark) {
        if (cardCode.isEmpty()) {
            Globals.showMessage(getContext(), getString(R.string.can_not_empty));
            return false;
        } else if (stagesCode.isEmpty()) {
            Globals.showMessage(getContext(), getString(R.string.can_not_empty));
            return false;
        } else if (remark.isEmpty()) {
            Globals.showMessage(getContext(), getString(R.string.can_not_empty));
            return false;
        }

        return true;
    }


    private List<ContactPersonData> ContactEmployeesList;
    ContactPersonAdapter contactPersonAdapter;

    private void callContactApi(String cardCode) {
        ContactEmployeesList = new ArrayList<>();
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getContactEmployeeList(cardCode).observe(getActivity(), new Observer<List<ContactPersonData>>() {
            @Override
            public void onChanged(@Nullable List<ContactPersonData> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getActivity());
                } else {
                    ContactEmployeesList = itemsList;
                    contactPersonAdapter = new ContactPersonAdapter(getActivity(), ContactEmployeesList);
                    /*binding.quotationGeneralContent.contactPersonSpinner.setAdapter(contactPersonAdapter);
                    //int index = ContactEmployeesList.get

                    if (!itemsList.isEmpty() && ContactPersonCode != null)
                        binding.quotationGeneralContent.contactPersonSpinner.setSelection(Globals.getSelectedContact(itemsList, ContactPersonCode));*/
                }
            }
        });
    }


    SalesEmployeeAdapter salesadapter;
    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();

    private void callSalessApi() {
        ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
        model.getSalesEmployeeList().observe(getActivity(), new Observer<List<SalesEmployeeItem>>() {
            @Override
            public void onChanged(@Nullable List<SalesEmployeeItem> itemsList) {
                if (itemsList == null || itemsList.size() == 0) {
                    Globals.setmessage(getActivity());
                } else {
                    salesEmployeeItemList = itemsList;
                    salesadapter = new SalesEmployeeAdapter(getActivity(), itemsList);
                    SalesEmployeeAutoAdapter adapter = new SalesEmployeeAutoAdapter(getActivity(), R.layout.drop_down_textview, itemsList);
                    binding.quotationGeneralContent.acSalesEmployee.setAdapter(adapter);
                }
            }
        });
    }
}