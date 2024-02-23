package com.cinntra.okana.adapters;

import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinntra.okana.R;
import com.cinntra.okana.activities.MainActivity;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.interfaces.DatabaseClick;
import com.cinntra.okana.model.Branch;
import com.cinntra.okana.model.BranchResponse;
import com.cinntra.okana.model.QuotationResponse;
import com.cinntra.okana.model.StateData;
import com.cinntra.okana.model.StateRespose;
import com.cinntra.okana.webservices.NewApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.data.geojson.GeoJsonPoint;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ContactViewHolder> {

    DatabaseClick dbClick;
    Context context;
    CountryAdapter countryAdapter;
    StateAdapter stateAdapter;
    ArrayList<Branch> branchList = new ArrayList<>();

    public BranchAdapter(DatabaseClick dbClick, Context context1, ArrayList<Branch> branchList) {
        this.branchList.addAll(branchList);
        this.dbClick = dbClick;
        this.context = context1;

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.branch_adapter, parent, false);
        return new ContactViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Branch branch = branchList.get(position);
        holder.name_value.setText(branch.getAddressName());
        holder.address_value.setText(branch.getStreet() + ", " + branch.getU_STATE() + ", " + branch.getU_COUNTRY() + ", " + branch.getZipCode());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openupdatedialog(branch);
            }
        });
    }

    String countryCode, countryName, stateName, statecode;
    Spinner state;

    private void openupdatedialog(Branch branch) {

        countryAdapter = new CountryAdapter(context, MainActivity.countrylistFromLocal);
        statecode = branch.getState();
        stateName = branch.getU_STATE();
        countryCode = branch.getCountry();
        countryName = branch.getU_COUNTRY();
        callStateApi(branch.getCountry());
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.add_branch_dialog);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        EditText address_value = dialog.findViewById(R.id.address_value);
        TextView heading = dialog.findViewById(R.id.heading);
        Spinner country = dialog.findViewById(R.id.country);
        state = dialog.findViewById(R.id.state);
        EditText zipcode = dialog.findViewById(R.id.zipcode);
        EditText name = dialog.findViewById(R.id.name);
        Button done = dialog.findViewById(R.id.done);
        heading.setText("Update Branch");
        name.setText(branch.getAddressName());
        zipcode.setText(branch.getZipCode());
        address_value.setText(branch.getStreet());
        country.setAdapter(countryAdapter);


        if (branch.getCountry() != null) {
            country.setSelection(Globals.getCountrypos(MainActivity.countrylistFromLocal, countryName));
        }


        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryCode = MainActivity.countrylistFromLocal.get(position).getCode();
                countryName = MainActivity.countrylistFromLocal.get(position).getName();
                callStateApi(countryCode);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                countryCode = MainActivity.countrylistFromLocal.get(0).getCode();
                countryName = MainActivity.countrylistFromLocal.get(0).getName();
            }
        });


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateName = stateList.get(position).getName();
                statecode = stateList.get(position).getCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stateName = stateList.get(0).getName();
                statecode = stateList.get(0).getCode();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation(address_value, name, zipcode, stateName)) {
                    String addr = stateName;

                    GeoJsonPoint final_coordinates = null;
                    try {
                        final_coordinates = getLocationFromAddress(addr);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    double latitude = final_coordinates.getCoordinates().latitude;
                    double longitude = final_coordinates.getCoordinates().longitude;
                    Branch udbranch = new Branch();
                    udbranch.setId(branch.getId());
                    udbranch.setBPCode(branch.getBPCode());
                    udbranch.setBpid((branch.getBpid()));
                    udbranch.setBranchName(branch.getBranchName());
                    udbranch.setAddressName(name.getText().toString());
                    udbranch.setAddressName2(branch.getAddressName2());
                    udbranch.setAddressName3(branch.getAddressName3());
                    udbranch.setBuildingFloorRoom(branch.getBuildingFloorRoom());
                    udbranch.setStreet(address_value.getText().toString());
                    udbranch.setBlock(branch.getBlock());
                    udbranch.setCounty(branch.getCounty());
                    udbranch.setCity(branch.getCity());
                    udbranch.setZipCode(zipcode.getText().toString());
                    udbranch.setState(statecode);
                    udbranch.setCountry(countryCode);
                    udbranch.setPhone(branch.getPhone());
                    udbranch.setFax(branch.getFax());
                    udbranch.setEmail(branch.getEmail());
                    udbranch.setTaxOffice(branch.getTaxOffice());
                    udbranch.setGstin(branch.getGstin());
                    udbranch.setGstType(branch.getGstType());
                    udbranch.setShippingType(branch.getShippingType());
                    udbranch.setPaymentTerm(branch.getPaymentTerm());
                    udbranch.setCurrentBalance(branch.getCurrentBalance());
                    udbranch.setCreditLimit(branch.getCreditLimit());
                    udbranch.setCreateDate(Globals.getTodaysDate());
                    udbranch.setUpdateDate(Globals.getTodaysDate());
                    udbranch.setCreateTime(Globals.getTCurrentTime());
                    udbranch.setUpdateTime(Globals.getTCurrentTime());
                    udbranch.setLat(String.valueOf(latitude));
                    udbranch.setLong(String.valueOf(longitude));
                    udbranch.setU_STATE(stateName);
                    udbranch.setU_COUNTRY(countryName);
                    udbranch.setAddressType("bo_ShipTo");
                    udbranch.setStatus(branch.getStatus());
                    udbranch.setRowNum(branch.getRowNum());
                    if (Globals.checkInternet(context)) {
                        callupdatebranchAPi(udbranch);
                        dialog.cancel();
                    }
                }

            }
        });

        dialog.show();
    }

    public GeoJsonPoint getLocationFromAddress(String strAddress) throws IOException {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GeoJsonPoint p1 = null;


        address = coder.getFromLocationName(strAddress, 5);
        if (address == null) {
            return null;
        }
        Address location = address.get(0);
        double lat = location.getLatitude();
        double longi = location.getLongitude();

        LatLng coordinates = new LatLng(lat, longi);

        p1 = new GeoJsonPoint(coordinates);
        return p1;
    }

    private void callupdatebranchAPi(Branch branch) {

        Call<BranchResponse> call = NewApiClient.getInstance().getApiService().updateBranch(branch);
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {

                if (response.code() == 200) {
                    Toast.makeText(context, "Update Successfully", Toast.LENGTH_LONG).show();


                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(context, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    ArrayList<StateData> stateList = new ArrayList<>();

    private void callStateApi(String countryCode) {

        StateData stateData = new StateData();
        stateData.setCountry(countryCode);
        Call<StateRespose> call = NewApiClient.getInstance().getApiService().getStateList(stateData);
        call.enqueue(new Callback<StateRespose>() {
            @Override
            public void onResponse(Call<StateRespose> call, Response<StateRespose> response) {

                if (response.code() == 200) {


                    stateList.clear();
                    if (response.body().getData().size() > 0) {

                        stateList.addAll(response.body().getData());

                    } else {
                        StateData sta = new StateData();
                        sta.setName("Select State");
                        stateList.add(sta);
                    }
//                    stateAdapter = new StateAdapter(context, stateList);
                    state.setAdapter(stateAdapter);
                    stateAdapter.notifyDataSetChanged();
                    if (stateName != null)
                        state.setSelection(Globals.getStatePo(stateList, stateName));
                } else {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s = response.errorBody().string();
                        mError = gson.fromJson(s, QuotationResponse.class);
                        Toast.makeText(context, mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateRespose> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(EditText addresss, EditText name, EditText zipcode, String stateName) {
        if (addresss.length() < 0) {
            addresss.setError(context.getResources().getString(R.string.address_error));
            return false;
        } else if (name.length() < 0) {
            name.setError(context.getResources().getString(R.string.name_error));
            return false;
        } else if (zipcode.length() < 0) {
            zipcode.setError(context.getResources().getString(R.string.zip_code_error));
            return false;
        } else if (stateName.isEmpty()) {
            Toast.makeText(context, "Please Select State", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name_value, address_value;
        ImageView edit;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            address_value = itemView.findViewById(R.id.address_value);
            name_value = itemView.findViewById(R.id.name_value);
            edit = itemView.findViewById(R.id.edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Prefs.getString(Globals.SelectAddress, "").equalsIgnoreCase("NoUpdate")) {
                        Log.e("from", "Main");
                    } else {
                        Prefs.putString(Globals.SelectAddress, "NoUpdate");
                        Globals.branchData.add(branchList.get(getAdapterPosition()));
                        dbClick.onClick(0);

                    }
                }
            });


        }

    }
}
