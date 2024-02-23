package com.cinntra.okana.activities;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.cinntra.okana.R;
import com.cinntra.okana.adapters.EmployeeHeirarchiDropdownAdapter;
import com.cinntra.okana.adapters.LocationListingAdapter;
import com.cinntra.okana.databinding.FragmentLocationListingBinding;
import com.cinntra.okana.fragments.Dashboard;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.globals.MainBaseActivity;
import com.cinntra.okana.model.EmployeeValue;
import com.cinntra.okana.model.MapData;
import com.cinntra.okana.model.MapResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.webservices.NewApiClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListing extends MainBaseActivity {


    String employeeid;

    @Override
    protected void onResume() {
        super.onResume();

       /* if(Globals.checkInternet(this)){
            callexpenseapi();
        }*/
    }

    FragmentLocationListingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentLocationListingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
//        binding.empSpinner.setAdapter(new EmployeeHeirarchiDropdownAdapter(LocationListing.this, Dashboard.teamList_Hearchi));


        if (Prefs.getString(Globals.SalesEmployeeCode, "").equalsIgnoreCase("1") || Prefs.getString(Globals.SalesEmployeeCode, "").equalsIgnoreCase("169")
                || Prefs.getString(Globals.SalesEmployeeCode, "").equalsIgnoreCase("149")){

            binding.employeeListLayout.setVisibility(View.VISIBLE);
        }else {
            binding.employeeListLayout.setVisibility(View.GONE);
        }


        //todo call map listing and set emp id_----
        employeeid = Prefs.getString(Globals.SalesEmployeeCode, "");

        callgetlocationApi(Globals.getTodaysDatervrsfrmt());


        binding.empSpinner.setAdapter(new EmployeeHeirarchiDropdownAdapter(LocationListing.this, Dashboard.salesAllList_Hearchi));

        binding.loaderLayout.loader.setVisibility(View.GONE);

        binding.dateText.setText(Globals.getTodaysDatervrsfrmt());
        binding.dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(LocationListing.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                String s = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date strDate = dateFormatter.parse(s);
                                    binding.dateText.setText(dateFormatter.format(strDate));
                                    callgetlocationApi(binding.dateText.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
//                textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

        binding.showlisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdplist.size() > 0) {
                    Bundle b = new Bundle();
                    b.putSerializable("MapList", (Serializable) mdplist);
                    Intent intent = new Intent(LocationListing.this, MyMapLocation.class);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(LocationListing.this, "No Location Found", Toast.LENGTH_LONG).show();
                }
            }
        });


//        binding.empSpinner.setSelection(getEmployeeData(employeeid, Dashboard.teamList_Hearchi));
        binding.empSpinner.setSelection(getEmployeeData(employeeid, Dashboard.salesAllList_Hearchi));

        binding.empSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                employeeid = Dashboard.salesAllList_Hearchi.get(i).getSalesEmployeeCode();
                callgetlocationApi(binding.dateText.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               /* if (Globals.checkInternet(LocationListing.this)) {
                    callexpenseapi();
                } else*/
                binding.swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private int getEmployeeData2(String employeeid, ArrayList<EmployeeValue> teamList_hearchi) {
        int pos = 0;
        for (EmployeeValue emp : teamList_hearchi) {
            if (emp.getSalesEmployeeCode().equalsIgnoreCase(employeeid)) {
                pos = teamList_hearchi.indexOf(emp);
            }
        }
        return pos;
    }


    private int getEmployeeData(String employeeid, ArrayList<SalesEmployeeItem> teamList_hearchi) {
        int pos = 0;
        for (SalesEmployeeItem emp : teamList_hearchi) {
            if (emp.getSalesEmployeeCode().equalsIgnoreCase(employeeid)) {
                pos = teamList_hearchi.indexOf(emp);
            }
        }
        return pos;
    }

    List<MapData> mdplist = new ArrayList<>();

    private void callgetlocationApi(String date) {
        HashMap<String, String> mapData = new HashMap<>();

        mapData.put("Emp_Id", employeeid);
        mapData.put("UpdateDate", date);
        mapData.put("shape", "meeting");


        Call<MapResponse> call = NewApiClient.getInstance().getApiService().getmaplocation(mapData);
        call.enqueue(new Callback<MapResponse>() {
            @Override
            public void onResponse(Call<MapResponse> call, Response<MapResponse> response) {
                if (response != null) {
                    mdplist.clear();
                    mdplist.addAll(response.body().getValue());
                    expenseAdapter = new LocationListingAdapter(LocationListing.this, mdplist);
                    binding.customerLeadList.setLayoutManager(new LinearLayoutManager(LocationListing.this, RecyclerView.VERTICAL, false));
                    binding.customerLeadList.setAdapter(expenseAdapter);
                    expenseAdapter.notifyDataSetChanged();
                    nodatafoundimage();
                }else {
                    mdplist.addAll(response.body().getValue());
                    expenseAdapter = new LocationListingAdapter(LocationListing.this, mdplist);
                    binding.customerLeadList.setLayoutManager(new LinearLayoutManager(LocationListing.this, RecyclerView.VERTICAL, false));
                    binding.customerLeadList.setAdapter(expenseAdapter);
                    nodatafoundimage();
                }
            }

            @Override
            public void onFailure(Call<MapResponse> call, Throwable t) {

            }
        });

    }


    LocationListingAdapter expenseAdapter;


    public void nodatafoundimage() {
        if (expenseAdapter.getItemCount() == 0) {
            binding.noDatafound.setVisibility(View.VISIBLE);
        } else {
            binding.noDatafound.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.campaign_filter, menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = new SearchView((this).getSupportActionBar().getThemedContext());
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.plus).setVisible(false);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setQueryHint("Search Expense");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return true;
            }
        });


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                break;
            case R.id.plus:
                startActivity(new Intent(this, AddExpense.class));
                break;


            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }
}
