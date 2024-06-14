package com.cinntra.okana.globals;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cinntra.okana.R;
import com.cinntra.okana.model.BPAddress;
import com.cinntra.okana.model.BPModel.BusinessPartnerAllResponse;
import com.cinntra.okana.model.Branch;
import com.cinntra.okana.model.ContactPersonData;
import com.cinntra.okana.model.CountryData;
import com.cinntra.okana.model.DocumentLines;
import com.cinntra.okana.model.IndustryItem;
import com.cinntra.okana.model.Items;
import com.cinntra.okana.model.LeadTypeData;
import com.cinntra.okana.model.OwnerItem;
import com.cinntra.okana.model.PayMentTerm;
import com.cinntra.okana.model.QuotationDocumentLines;
import com.cinntra.okana.model.QuotationItem;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.StagesItem;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.UTypeData;
import com.cinntra.okana.newapimodel.NewOpportunityRespose;
import com.cinntra.okana.receivers.ConnectivityReceiver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static android.content.Context.INPUT_METHOD_SERVICE;

import androidx.annotation.RequiresApi;

public class Globals {


    public static double currentlattitude = 28.622827380895615;
    public static double currentlongitude = 77.36626848578453;
    public static int TYPE_EVENT = 1;
    public static int TYPE_TASK = 2;
    public static int TopItem = 100;
    public static String APILog = "Login";
    public static String IP = "103.118.16.194";      // HANA
    //public static String IP     = "192.168.29.240"; // SAP
    //public static String PORT   = "1433";           // SAP
    public static String PORT = "1433";             //HANA
    public static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    public static String database = "SLDModel.SLDData";
    public static String username = "sa";
    public static String password = "sa@2019";

    public static String Query = "Exec";

    public static ArrayList<Branch> branchData = new ArrayList<Branch>();

    //Demo
    public static String SelectedDB = "TEST";

    //public static String SelectedDB  = "TEST2304";


    public static String curntDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    //     public static   String BaseURL           = "http://103.107.67.94:50001/";  // OFFICE HANA URL
    public static String BaseURL = "http://103.234.187.197:8019/";  // OFFICE HANA URL
    public static String Acess_Js_BaseURL = "http://103.107.67.94:8000/";   // OFFICE HANA URL
//    public static String ImageURl = "http://103.234.187.197:8050";



    /************Development*******************/
 /*   public static   String NewBaseUrl  =  "http://103.234.187.197:8019/";
    public static String ImageURl = "http://103.234.187.197:8019";
    public static String PDFURL = "http://103.234.187.197:4250/assets/html/";*/



    /************Live OKNA *******************/
    public static String NewBaseUrl = "http://103.107.67.48:8010/";

    public static String ImageURl = "http://103.107.67.48:8010";

    public static String PDFURL = "http://okna.bridgexd.com/assets/html/";


    /*************Client Demo(Pre prod)***************/

    /**************************  Get APIs Urls  **************************/
    public static String OpenQuotationList = "b1s/v1/Quotations?$filter=DocumentStatus eq 'bost_Open'&$orderby=DocEntry desc";
    public static String QuotationList = "b1s/v1/Quotations?$orderby=DocEntry desc ";
    public static String OverDueInvoicesList = "b1s/v1/Invoices?$filter=DocDueDate lt '";
    public static String deliveryOverDueList = "b1s/v1/DeliveryNotes?$filter=DocDueDate lt '";
    public static String ContactEmployeeList = "b1s/v1/BusinessPartners('";
    public static String GetUserID = "b1s/v1/Users?$select=InternalKey&$filter=UserCode eq '";
    public static String GetProfile = "b1s/v1/Users(";
    public static String GetProfileDetail = "b1s/v1/$crossjoin(Users,EmployeesInfo,EmployeePosition)? $expand=Users($select=UserName,eMail,MobilePhoneNumber), EmployeesInfo($select=JobTitle, HomeStreet,HomeZipCode,HomeState,HomeCountry,Active)  &$filter=Users/InternalKey eq EmployeesInfo/EmployeeID and  EmployeesInfo/EmployeeID eq EmployeePosition/PositionID and   Users/UserCode eq '";
    public static String GetCustomers = "b1s/v1/BusinessPartners/?$filter=CardType eq 'cCustomer'&$orderby=CardCode asc";
    public static String GetItems = "b1s/v1/Items";
    public static String GetCountry = "b1s/v1/Countries";
    public static String GetStates = "b1s/v1/States";
    public static String GetOpportunity = "b1s/v1/SalesOpportunities";
    public static String GetOpportunityOpen = "b1s/v1/SalesOpportunities?$filter=Status eq 'sos_Open'&$orderby=SequentialNo desc";
    public static String GetOpportunityWon = "b1s/v1/SalesOpportunities?$filter=Status eq 'sos_Sold'&$orderby=SequentialNo desc";
    public static String GetOpportunityLost = "b1s/v1/SalesOpportunities?$filter=Status eq 'sos_Missed'&$orderby=SequentialNo desc";
    public static String GetOrder = "b1s/v1/Orders";
    public static String GetOpenOrder = "b1s/v1/Orders?$filter=DocumentStatus eq 'bost_Open'&$orderby=DocEntry desc";
    public static String GetInvoice = "b1s/v1/Invoices";
    public static String GetAdminlevel = "AdminPannel/General/GetNextLevelEmployee.xsjs?DBName=TEST&empid=";

    public static String get_access = "SalesApp_Cinntra_Test";
    public static String BusinessPartnerCardCode = "_BusinessPartnerCardCode";
    //public static String get_access                  = "Cinntra_App";


    // public static String get_access                    = "SalesApp_Sun_Test";
    public static String getFastInvetory_access = get_access + "/Item/FastMovingItem.xsjs?DBName=";
    public static String getMediumInvetory_access = get_access + "/Item/MediumMoving.xsjs?DBName=";
    public static String getNonInvetory_access = get_access + "/Item/SlowMovingItem.xsjs?DBName=";
    public static String getAllInvetory_access = get_access + "/Item/AllInventory.xsjs?DBName=";
    public static String getOpenDeliveries_access = get_access + "/Delivery/GetOpenDelivery.xsjs?DBName=";
    public static String getCloseDeliveries_access = get_access + "/Delivery/ClosedDelivery.xsjs?DBName=";
    public static String getOverDueDeliveries_access = get_access + "/Delivery/OverDueDelievry.xsjs?DBName=";
    public static String getSalesTarget_access = get_access + "/SalesTargetAchived/SalesTargetAchived.xsjs?DBName=";
    public static String getTop5Customer_access = get_access + "/Customers/CustoerwiseTop5Sales.xsjs?DBName=";
    //public static String getTop5Item_access            = get_access+"/Item/Top5FastMovingItem.xsjs?DBName=";
    public static String getTop5Item_access = get_access + "/Item/Top5Last_3_MonthItem.xsjs?DBName=";
    public static String getHierarchy_access = get_access + "/General/LoginHierarchy2ndLevel.xsjs?DBName=";
    public static String getHierarchy_access_New = get_access + "/General/Get1stLevelHierarchy.xsjs?DBName=";
    public static String getBranches_access = get_access + "/General/Branch.xsjs?DBName=";
    public static String getUserCounters = get_access + "/General/UserCount.xsjs?DBName=";
    public static String getUserLogin = get_access + "/General/Login.xsjs?User=";

    public static String[] lead_status_list = {"New", "Follow Up", "Qualified", "Hold"};
    public static String[] material_type_list_gl = {"Aluminium", "UPVC", "Aluminium+UPVC"};

    public static String LeadDetails = "_LeadDetails";
    public static String SelectedDatabase = "_Selected_Database";
    public static String SelectedBranch = "_Selected_Branch";
    public static String SelectedBranchID = "_Selected_Branch_ID";
    public static String SelectedWareHose = "_Selected_WareHouse_Code";
    public static String SelectedSqlIP = "_SelectedSqlIP";
    public static String STOREBPLID = "_STOREBPLID";
    public static String SelectedSqlUser = "_SelectedSqlUser";
    public static String SelectedSqlPass = "_SelectedSqlPass";
    public static String SAPUser = "_SAPUser";
    public static String SAPPassword = "_SAPPassword";
    public static String BussinessPageType = "_BussinessPageType";
    public static String App_USERID = "_User_ID_";
    public static String SESSION_TIMEOUT = "_SESSION_TIMEOUT";
    public static String SESSION_REMAIN_TIME = "_SESSION_REMAIN_TIME";
    public static String USER_TYPE = "_USER_TYPE";
    public static String USER_LABEL = "_USER_LABEL";
    public static String CountriesList = "_CountriesList";
    public static String StateList = "_StateList";
    public static String SalesEmployeeList = "_SalesEmployeeList";
    public static String USER_NAME = "_User_Name";
    public static String USER_PASSWORD = "_User_Password";
    public static String EmployeeID = "_Emp_Id";
    public static String Employee_Name = "_Employee_Name";
    public static String Employee_Code = "_Employee_Code";
    public static String Role = "_Role";
    public static String Position = "_Position";
    public static String SalesEmployeeCode = "_SalesEmployeeCode";  //For TEam Selection
    public static String SalesEmployeeName = "_SalesEmployeeName";
    public static String AddContactPerson = "_AddContactPerson";
    public static String QuotationListing = "_QuotationList";
    public static String OpportunityQuotation = "_OpportunityQuotation";

    public static String SelectQuotation = "_SelectQuotation";
    public static final String QuotationData = "_QuotationData";
    public static final String TOKEN = "_TOKEN";
    public static String TeamSalesEmployeCode = "";  //For login user
    public static String AppUserDetails = "_AppUserDetails";
    public static String TeamRole = "";
    public static String TeamEmployeeID = "";
    public static String MYEmployeeID = "";
    public static String Lattitude = "_latitude";
    public static String Longitude = "_longitude";
    public static String ItemType = "Paid";

    public static final String Location_FirstTime = "_Location_FirstTime";

    public static final String LocationRestart = "_LocationRestart";

    public static String channelId = "distance_channel_id";

    public static int notificationId = 123;

    public static boolean LocationShared = false;

    public static final String Location_Boolean_MInutes = "_Location_Boolean_MInutes";

    public static NewOpportunityRespose opp = null;
    /********************* Variables Names **********************/
    public static final String SessionID = "_session_id";
    public static final String QuotationItem = "_Quotation_Item";
    public static final String QuotationLine = "_Quotation_Line";
    public static final String OpportunityItem = "_OpportunityItem";
    public static final String OpportunityItemData = "_OpportunityItemData";
    public static final String CustomerItemData = "_cus_itemData";
    public static final String OwnerItemData = "_Owner_itemData";
    public static final String BussinessItemData = "_Bussiness_itemData";
    public static final String TaskEventList = "_TaskEventList";
    public static final String UserAdmin = "_UserAdmin";
    public static String SelectAddress = "_SelectAddress";
    public static String CampaignData = "_CampaignData";
    public static String ExpenseData = "_ExpenseData";
    public static String paymentDetailsData = "_paymentDetailsData";
    public static String AutoLogIn = "_AutoLogIn";
    public static String LeadPAgeType = "_LeadPAgeType";

    public static QuotationItem partent_proforma_invoice = null;
    public static String locationcondition = "_locationcondition";
    public static final String checkIn = "_checkIn";
    public static String MeetingMode = "_MeetingMode";
    /************************* Global Lists ****************************/


    public static ArrayList<Items> contentList = new ArrayList<>();
    public static ArrayList<DocumentLines> SelectedItems = new ArrayList<>();
    public static ArrayList<OwnerItem> OwnerList = new ArrayList<>();
    public static ArrayList<NewOpportunityRespose> opportunityData = new ArrayList<>();

    public static ArrayList<String> sourcechecklist = new ArrayList<>();


    public static boolean inventory_item_close = false;
    public static String CurrentSelectedDate = Globals.getTodaysDatervrsfrmt();
    public static String USERNAME = "_User_Name";
    public static String COMMENT = "";
    public static String CURRENT_CLASS = "";
    public static String SelectOpportnity = "_SelectOpportnity";
    public static String AddBp = "_AddBp";
    public static String Lead_Data = "_Lead_Data";
    public static String FromQuotation = "_FromQuotation";
    public static String[] productInterestList_gl = {"Aluminium", "UPVC", "Internal", "Combo/Mix", "Other"};

    public static String[] probability_list = {"0%", "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};

    public static String[] status_list = {"Pending", "Approved", "Rejected"};

    public static String[] expenseModeList_gl = {"Travel", "Hotel Stay"};
    public static ArrayList<QuotationItem> quotationOrder = new ArrayList<>();
    public static Integer CallLeadID;
    public static String CallPhoneNum;

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static int getDeviceWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getDisplay().getRealMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;

        return width;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static int getDeviceHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getDisplay().getRealMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;


        return height;
    }

    public static String getStatus(String v) {

        if (v.equalsIgnoreCase("O"))
            return "Open";
        else
            return "Close";
    }

    public static String viewStatus(String v) {

        if (v.equalsIgnoreCase("bost_Open"))
            return "Open";
        else
            return "Close";
    }

    public static String getAmmount(String currency, String ammount) {
        if (currency.equalsIgnoreCase("INR"))
            return "\u20B9 " + ammount;
        else
            return ammount + " $";
    }

    public static void showMessage(Context context, String message) {
        Toasty.warning(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showSuccessMessage(Context context, String message) {
        Toasty.success(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showErrorMessage(Context context, String message) {
        Toasty.error(context, message, Toast.LENGTH_LONG).show();
    }

    public static String convertDecemal(String input) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(Double.parseDouble(input)) + "%";
    }

    public static String changeDecemal(String input) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(Double.parseDouble(input));
    }

    public static void ErrorMessage(Context context, String errorBlock) {
        Gson gson = new GsonBuilder().create();
        QuotationResponse mError = new QuotationResponse();

        try {
            // String s =response.errorBody().string();
            mError = gson.fromJson(errorBlock, QuotationResponse.class);
            Toast.makeText(context, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
            Log.e("Test=>", mError.getError().getMessage().getValue());
        } catch (JsonSyntaxException e) {
            // handle failure to read error
        }
    }

    public static String getTodaysDate() {

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return date;
    }

    public static String getTodaysDatervrsfrmt() {

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }

    public static String getTCurrentTime() {
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        return currentTime;
    }

    public static String getCurrentTimeIn_hh_mm_ss_aa() {
        String currentTime = new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault()).format(new Date());
        return currentTime;
    }


    public static String getCurrentTimeIn_hh_mm_ss() {
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        return currentTime;
    }

    public static String getSixDigitFormat(int inputNumber) {
        String result1 = String.format("%06d", inputNumber);
        return result1;
    }

    public static String convert_yyyy_mm_dd_to_dd_mm_yyyy(String str) {
        String convertedDate = "";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = inputDateFormat.parse(str);
            convertedDate = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String convert_yyyy_mm_dd_tt_to_dd_mm_yyyy_tt(String str) {
        String convertedDate = "";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date date = inputDateFormat.parse(str);
            convertedDate = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String convert_dd_MM_yyyy_to_yyyy_MM_dd(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String outputDate = "";

        try {
            Date date = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }


    public static void disablePastSelectDate(Context context, EditText textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String s = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date strDate = dateFormatter.parse(s);
                            textView.setText(dateFormatter.format(strDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.setMessage(textView.getHint().toString());
        datePickerDialog.show();
    }


    public static void disableFutureDates(Context context, EditText textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String s = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date strDate = dateFormatter.parse(s);
                            textView.setText(dateFormatter.format(strDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.setMessage(textView.getHint().toString());
        datePickerDialog.show();
    }


    public static double convertStringToDouble(String input) {
        try {
            // Convert the string to a double
            double number = Double.parseDouble(input);

            // Format the double as an integer
            int integerValue = (int) number;

            // Convert the integer back to a string
            return number;
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid number
            return 0.0;
        }
    }

    public static void enableAllCalenderDateSelect(Context context, EditText textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String s = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date strDate = dateFormatter.parse(s);
                            textView.setText(dateFormatter.format(strDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker(); //.setMinDate(System.currentTimeMillis() - 1000)
        datePickerDialog.setMessage(textView.getHint().toString());
        datePickerDialog.show();
    }

    public static int skipTo(int page) {
        return TopItem * page;
    }


    public static void selectDate(Context context, EditText textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String s = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                                "dd-MM-yyyy");
                        try {
                            Date strDate = dateFormatter.parse(s);
                            textView.setText(dateFormatter.format(strDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
//                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.setMessage(textView.getHint().toString());
        datePickerDialog.show();
    }

    public static ArrayList<Items> ItemarrayListConverter(ArrayList<QuotationDocumentLines> arr) {
        ArrayList<Items> items = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            Items item = new Items();

            item.setItemCode(arr.get(i).getItemCode());
            item.setItemName(arr.get(i).getItemDescription());
            item.setQuantity(arr.get(i).getQuantity());
            item.setItemUnitPrice(arr.get(i).getUnitPrice());
            item.setItemTaxCode(arr.get(i).getTaxCode());
            items.add(item);
        }

        return items;
    }


    public static double calculatetotalofitem(ArrayList<DocumentLines> selectedItems) {
        double sum = 0;
        for (DocumentLines i : selectedItems)
            sum += Double.parseDouble(String.valueOf((i.getUnitPrice()) * Double.parseDouble(i.getQuantity()))); //todo getQuantity()..
        return sum;
    }


    //todo total before header discount--
    public static double calculateTotalOfItem(ArrayList<DocumentLines> selectedItems) {
        double sum = 0;
        double storeSumOFQuantity = 0;
        double storeSumOfItemDiscount = 0;
        double totalAfterTax = 0;
        for (DocumentLines i : selectedItems) {
            storeSumOFQuantity = Double.parseDouble(String.valueOf((i.getUnitPrice()) * Double.parseDouble(i.getQuantity()))); //todo getQuantity()..
            storeSumOfItemDiscount = storeSumOFQuantity - (storeSumOFQuantity * Double.parseDouble(String.valueOf(i.getDiscountPercent()))) / 100 ;
            totalAfterTax = storeSumOfItemDiscount + (storeSumOfItemDiscount * Double.parseDouble(i.getTax())) / 100 ;
            sum += totalAfterTax;
        }
        return sum;
    }


    //todo total before item tax--
    public static double calculateTotalOfItemBeforeTax(ArrayList<DocumentLines> selectedItems) {
        double sum = 0;
        double storeSumOFQuantity = 0;
        double storeSumOfItemDiscount = 0;
        double totalAfterTax = 0;
        for (DocumentLines i : selectedItems) {
            storeSumOFQuantity = Double.parseDouble(String.valueOf((i.getUnitPrice()) * Double.parseDouble(i.getQuantity()))); //todo getQuantity()..
            storeSumOfItemDiscount = storeSumOFQuantity - (storeSumOFQuantity * Double.parseDouble(String.valueOf(i.getDiscountPercent()))) / 100 ;
            sum += storeSumOfItemDiscount;
        }
        return sum;
    }


    public static double calculateTotalOfItemAfterHeaderDis(ArrayList<DocumentLines> selectedItems, double headerDiscount) {
        double sum = 0;
        double storeSumOFQuantity = 0;
        double storeSumOfItemDiscount = 0;
        double totalAfterTax = 0;
        double totalAfterHeaderDis = 0;
        for (DocumentLines i : selectedItems) {
            storeSumOFQuantity = Double.parseDouble(String.valueOf((i.getUnitPrice()) * Double.parseDouble(i.getQuantity()))); //todo getQuantity()..
            storeSumOfItemDiscount = storeSumOFQuantity - (storeSumOFQuantity * Double.parseDouble(String.valueOf(i.getDiscountPercent()))) / 100 ;
            totalAfterHeaderDis = storeSumOfItemDiscount -(storeSumOfItemDiscount * Double.parseDouble(String.valueOf(headerDiscount))) / 100;
            totalAfterTax = totalAfterHeaderDis + (totalAfterHeaderDis * Double.parseDouble(i.getTax())) / 100 ;
            sum += totalAfterTax ;
        }
        return sum;
    }


    public static String foo(double value) {
        DecimalFormat formatter;

        if(value - (int)value > 0.0)
            formatter = new DecimalFormat("0.00"); //Here you can also deal with rounding if you wish..
        else
            formatter = new DecimalFormat("0.00");

        return formatter.format(value);
    }



    public static int getSelectedSalesP(List<SalesEmployeeItem> list, String salesCode) {
        int po = -1;
        for (SalesEmployeeItem obj : list) {
            if (obj.getSalesEmployeeCode().trim().equalsIgnoreCase(salesCode.trim())) {
                po = list.indexOf(obj);
                break;
            }
        }
        return po;
    }


    public static int getLeadSourcePos(List<LeadTypeData> list, String code) {
        int index = -1;
        for (LeadTypeData sd : list) {
            if (sd.getName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }


    public static int getSalesEmployeePos(List<SalesEmployeeItem> list, String code) {
        int index = -1;
        for (SalesEmployeeItem sd : list) {
            if (sd.getFirstName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }


    public static int getSelectedStage(List<StagesItem> list, String salesCode) {
        int po = -1;

        for (StagesItem obj : list) {
            if (obj.getStageno().trim().equalsIgnoreCase(salesCode.trim())) {
                po = list.indexOf(obj);
                break;
            }

        }
        return po;
    }

    public static int getSelectedContact(List<ContactPersonData> list, String salesCode) {
        int po = -1;

        for (ContactPersonData obj : list) {
            if (obj.getInternalCode().trim().equalsIgnoreCase(salesCode.trim())) {
                po = list.indexOf(obj);
                break;
            }

        }
        return po;
    }


    public static int getAssignToPos(List<SalesEmployeeItem> list, String code) {
        int index = -1;
        for (SalesEmployeeItem sd : list) {
            if (sd.getFirstName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }


    public static int getIndustryPos(List<IndustryItem> list, String code) {
        int index = -1;
        for (IndustryItem sd : list) {
            if (sd.getIndustryName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }

    public static int getOppType(List<UTypeData> list, String code) {
        int index = -1;
        for (UTypeData sd : list) {
            if (sd.getType().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }

    public static int getSalesEmployeePOs(List<SalesEmployeeItem> list, String code) {
        int index = -1;
        for (SalesEmployeeItem sd : list) {
            if (sd.getSalesEmployeeName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }

    public static int getCountryCodePos(ArrayList<CountryData> list, String code) {
        int index = -1;
        for (CountryData cd : list) {
            if (cd.getName().equalsIgnoreCase(code)) {
                index = list.indexOf(cd);
                break;
            }
        }
        return index;
    }


    public static int getSourcePos(List<LeadTypeData> list, String code) {
        int index = -1;
        for (LeadTypeData sd : list) {
            if (sd.getName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }


    public static int getCustomerPos(List<BusinessPartnerAllResponse.Datum> list, String code) {
        int index = -1;
        for (BusinessPartnerAllResponse.Datum sd : list) {
            if (sd.getCardName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }


    public static String getContactperson(List<ContactPersonData> list, String salesCode) {
        String contactper = "No contact person";

        for (ContactPersonData obj : list) {
            if (obj.getInternalCode().trim().equalsIgnoreCase(salesCode.trim())) {
                contactper = obj.getFirstName();
                return contactper;
            }

        }
        return contactper;
    }

    public static double calculatetotal(int i, double text) {
        double total = 0.00;
        total = text / 10;
        total = text + total;
        return total;
    }

    public static int getOwenerPo(ArrayList<OwnerItem> list, String code) {

        int po = -1;
        for (OwnerItem obj : list) {
            if (obj.getEmployeeID().trim().equalsIgnoreCase(code.trim())) {
                po = list.indexOf(obj);
                break;
            }

        }
        return po;
    }

    public static int getIndustryPo(List<IndustryItem> list, String code) {

        int po = -1;
        for (IndustryItem obj : list) {
            if (obj.getIndustryCode().equalsIgnoreCase(code.trim())) {
                po = list.indexOf(obj);
                break;
            }

        }
        return po;
    }

    public static int getPaymentTermPo(List<PayMentTerm> list, String Code) {
        int po = -1;

        for (PayMentTerm obj : list) {
            if (obj.getGroupNumber().equalsIgnoreCase(Code.trim())) {
                po = list.indexOf(obj);
                break;
            }

        }
        return po;
    }

    public static boolean isvalidateemail(EditText email_value) {
        String checkEmail = email_value.getText().toString();
        boolean hasSpecialEmail = Patterns.EMAIL_ADDRESS.matcher(checkEmail).matches();
        if (!hasSpecialEmail) {
            email_value.setError("This E-Mail address is not valid");
            return true;
        }
        return false;
    }


    public static int getAddres_Bill_Po(List<BPAddress> bpAddresses, String bo_billTo) {
        int po = -1;
        for (BPAddress obj : bpAddresses) {
            if (obj.getAddressType().trim().equalsIgnoreCase(bo_billTo.trim())) {
                po = bpAddresses.indexOf(obj);
                break;
            }
        }
        return po;

    }

    public static int getAddres_Ship_Po(List<BPAddress> bpAddresses, String bo_ShipTo) {
        int po = -1;
        for (BPAddress obj : bpAddresses) {
            if (obj.getAddressType().trim().equalsIgnoreCase(bo_ShipTo.trim())) {
                po = bpAddresses.indexOf(obj);
                break;
            }
        }
        return po;
    }


    public static boolean checkInternet(Context context) {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
        } else {
            Dialog dialog = new Dialog(context, R.style.DialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.no_internet_connection);
            dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.transparent));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            Button tryagain = dialog.findViewById(R.id.try_again);
            tryagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();

        }
        return isConnected;
    }

    public static void setmessage(Context context) {
        Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();
    }

    public static void openKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeybaord(View v, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    public static void saveSaleEmployeeArrayList(List<SalesEmployeeItem> list, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        Prefs.putString(key, json);
    }


    public static int SkipItem(int pageNo) {
        return 20 * pageNo;
    }

    public static int getShipTypePo(String[] list, String code) {

        int index = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(code)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static String getCountryCode(ArrayList<CountryData> list, String code) {

        String index = "IN";
        for (CountryData cd : list) {
            if (cd.getName().equals(code)) {
                index = cd.getCode();
                break;
            }
        }
        return index;
    }

    public static int getCountrypos(ArrayList<CountryData> list, String code) {

        int index = -1;
        for (CountryData cd : list) {
            if (cd.getName().equals(code)) {
                index = list.indexOf(cd);
                break;
            }
        }
        return index;
    }

    public static int getStatePo(ArrayList<StateData> list, String code) {

        int index = -1;
        for (StateData sd : list) {
            if (sd.getName().equals(code)) {
                index = list.indexOf(sd);
                break;
            }
        }
        return index;
    }

    public static String selectDat(Context context) {
        final String[] Date = {""};
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Date[0] = year + "-" + monthOfYear + 1 + "-" + dayOfMonth;

                    }
                }, mYear, mMonth, mDay);

        // datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
        return Date[0];
    }

    public static String getTimestamp() {
        final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf3.format(timestamp);
    }


    public static ArrayList<SalesEmployeeItem> getSaleEmployeeArrayList(String key) {
        Gson gson = new Gson();
        String json = Prefs.getString(key, null);
        Type type = new TypeToken<List<SalesEmployeeItem>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public static void noItemDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_connection);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.transparent));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        Button tryagain = dialog.findViewById(R.id.try_again);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });
        dialog.show();

    }

    public static void selectTime(Context context, EditText editText) {


        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar myTime = Calendar.getInstance();
                myTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myTime.set(Calendar.MINUTE, minute);
                myTime.set(Calendar.SECOND, 0);
                myTime.set(Calendar.MILLISECOND, 0);
                editText.setText(DateFormat.format("hh:mm aa", myTime).toString().toUpperCase());

            }
        }, 12, 0, false
        );

        timePickerDialog.show();
    }


    public static int getContactPos(List<ContactPersonData> data, String contactPerson) {
        int index = -1;
//        +  " " +data.get(i).getLastName()
        for (int i = 0; i < data.size(); i++) {
            String cp = data.get(i).getFirstName();
            if (cp.equals(contactPerson)) {
                index = i;
                break;
            }
        }
        return index;

    }

    public static int getleadType(List<LeadTypeData> data, String contactPerson) {
        int index = -1;
//        +  " " +data.get(i).getLastName()
        for (int i = 0; i < data.size(); i++) {
            String cp = data.get(i).getName();
            if (cp.equals(contactPerson)) {
                index = i;
                break;
            }
        }
        return index;

    }


}
