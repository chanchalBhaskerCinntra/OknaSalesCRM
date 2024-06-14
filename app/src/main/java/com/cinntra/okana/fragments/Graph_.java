package com.cinntra.okana.fragments;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cinntra.okana.R;
import com.cinntra.okana.customUI.RoundedBarChart;
import com.cinntra.okana.databinding.GraphBinding;
import com.cinntra.okana.globals.Globals;
import com.cinntra.okana.model.CounterResponse;
import com.cinntra.okana.model.SalesEmployeeItem;
import com.cinntra.okana.model.Team;
import com.cinntra.okana.model.Top5CustomerResponse;
import com.cinntra.okana.model.Top5Item;
import com.cinntra.okana.model.Top5ItemResponse;
import com.cinntra.okana.webservices.NewApiClient;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;


public class Graph_ extends Fragment implements View.OnClickListener {


//
//
//    @BindView(R.id.customer_barChart)
//    BarChart customer_barChart;
//    @BindView(R.id.selling_barChart)
//    BarChart selling_barChart;
//
//    @BindView(R.id.cus_1)
//    TextView cus_1;
//    @BindView(R.id.cus_2)
//    TextView cus_2;
//    @BindView(R.id.cus_3)
//    TextView cus_3;
//    @BindView(R.id.cus_4)
//    TextView cus_4;
//    @BindView(R.id.cus_5)
//    TextView cus_5;
//    @BindView(R.id.seller_1)
//    TextView seller_1;
//    @BindView(R.id.seller_2)
//    TextView seller_2;
//    @BindView(R.id.seller_3)
//    TextView seller_3;
//    @BindView(R.id.seller_4)
//    TextView seller_4;
//    @BindView(R.id.seller_5)
//    TextView seller_5;
//
//    @BindView(R.id.actual_progress_bar)
//    ProgressBar actual_progress_bar;
//    @BindView(R.id.actual_progress_value)
//    TextView actual_progress_value;
//    @BindView(R.id.initial_progress_bar)
//    ProgressBar initial_progress_bar;
//    @BindView(R.id.initial_progress_value)
//    TextView initial_progress_value;
//    @BindView(R.id.current_progress_bar)
//    ProgressBar current_progress_bar;
//    @BindView(R.id.current_progress_value)
//    TextView current_progress_value;
//
//    @BindView(R.id.inventory_pieChart)
//    PieChart pieChart;
//
//    @BindView(R.id.actual_projection_value)
//    TextView actual_projection_value;
//    @BindView(R.id.initial_projection_value)
//    TextView initial_projection_value;
//    @BindView(R.id.current_projection_value)
//    TextView current_projection_value;
//
//    @BindView(R.id.month_1)
//    TextView month_1;
//    @BindView(R.id.month_3)
//    TextView month_3;
//    @BindView(R.id.month_6)
//    TextView month_6;
//    @BindView(R.id.year)
//    TextView year;


    GraphBinding binding;


    public Graph_() {
    }


    // TODO: Rename and change types and number of parameters
    public static Graph_ newInstance(String param1, String param2) {
        Graph_ fragment = new Graph_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = GraphBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.graph, container, false);
        //  ButterKnife.bind(this,v);
        Globals.CURRENT_CLASS = getClass().getName();


        setDefaults();

        setupPieChart();
        addData();
        setProjectionData("1");
        loadTop5Customer();

        return binding.getRoot();
    }

    private void setDefaults() {

        binding.month1.setOnClickListener(this);
        binding.month3.setOnClickListener(this);
        binding.month6.setOnClickListener(this);
        binding.year.setOnClickListener(this);


    }


    /************* Inventory PieChart ****************/
    public static final int[] COLORS = {
            rgb("#FFA63D"), rgb("#4A79E4"), rgb("#95B5FF"), rgb("#3498db")
    };

    float time[] = {Float.parseFloat("" + ((double) Dashboard.mediumInventoryList.size() / Dashboard.allInventoryList.size()) * 100), Float.parseFloat("" + ((double) Dashboard.fastInventoryList.size() / Dashboard.allInventoryList.size()) * 100), Float.parseFloat("" + ((double) Dashboard.nonInventoryList.size() / Dashboard.allInventoryList.size()) * 100)};
    String activity[] = {"Slow Moving", "Fast Moving", "Non Moving"};

    // CircularProgressIndicator circularProgress;
    private void setupPieChart() {

        //pupulating list of PieEntires
        List<PieEntry> pieEntires = new ArrayList<>();
        for (int i = 0; i < time.length; i++) {
            pieEntires.add(new PieEntry(time[i], activity[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntires, "");
        dataSet.setColors(COLORS);
        PieData data = new PieData(dataSet);
        //Get the chart
        binding.inventoryPieChart.setData(data);
        binding.inventoryPieChart.invalidate();
        binding.inventoryPieChart.setCenterText("100% \n ");
        binding.inventoryPieChart.setDrawEntryLabels(false);
        binding.inventoryPieChart.setContentDescription("");
        //pieChart.setDrawMarkers(true);
        //pieChart.setMaxHighlightDistance(34);
        binding.inventoryPieChart.setEntryLabelTextSize(12);
        //pieChart.setHoleRadius(75);

        //legend attributes
        Legend legend = binding.inventoryPieChart.getLegend();
        //legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);


    }


    private float[] yData = {Float.parseFloat("" + ((double) Dashboard.mediumInventoryList.size() / Dashboard.allInventoryList.size()) * 100), Float.parseFloat("" + ((double) Dashboard.fastInventoryList.size() / Dashboard.allInventoryList.size()) * 100), Float.parseFloat("" + ((double) Dashboard.nonInventoryList.size() / Dashboard.allInventoryList.size()) * 100)};

    private String[] xData = {"Slow Moving", "Fast Moving", "Non Moving"};

    private void addData() {
        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new PieEntry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1,
                "Market Share");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);


        // instantiate pie data object now
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        binding.inventoryPieChart.setData(data);

        // undo all highlights
        binding.inventoryPieChart.highlightValues(null);

        // update pie chart
        binding.inventoryPieChart.invalidate();
    }


    /***************** To 5 Item data *******************************/

    public static ArrayList<Top5Item> topItem_5 = new ArrayList<>();

    private void loadTop5Item() {
        HashMap<String, String> hd = new HashMap<>();
        hd.put("SalesPersonCode", Prefs.getString(Globals.SalesEmployeeCode, ""));

        Call<Top5ItemResponse> call = NewApiClient.getInstance().getApiService().getTop5Items(hd);
        call.enqueue(new Callback<Top5ItemResponse>() {
            @Override
            public void onResponse(Call<Top5ItemResponse> call, Response<Top5ItemResponse> response) {
                if (response != null) {
                    if (response.body().getValue().size() > 0) {
                        topItem_5.clear();
                        topItem_5.addAll(response.body().getValue());
                        ItemChart();

                    } else {
                        try {
                            Toast.makeText(getContext(), "No data Found", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    try {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<Top5ItemResponse> call, Throwable t) {
                try {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void ItemChart() {
        float setItemData[];

        String sellItems[];

        sellItems = new String[topItem_5.size()];
        setItemData = new float[topItem_5.size()];
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < topItem_5.size(); i++) {
            sellItems[i] = topItem_5.get(i).getItemCode();

            setItemData[i] = Float.valueOf(topItem_5.get(i).getTotal());
            entries.add(new BarEntry((float) i, setItemData[i]));
            if (i == 0)
                binding.seller1.setText(sellItems[0]);
            else if (i == 1)
                binding.seller2.setText(sellItems[1]);
            else if (i == 2)
                binding.seller3.setText(sellItems[2]);
            else if (i == 3)
                binding.seller4.setText(sellItems[3]);
            else if (i == 4)
                binding.seller5.setText(sellItems[4]);

        }
        RoundedBarChart roundedBarChartRenderer = new RoundedBarChart(binding.sellingBarChart, binding.sellingBarChart.getAnimator(), binding.sellingBarChart.getViewPortHandler());
        roundedBarChartRenderer.setmRadius(20f);
        binding.sellingBarChart.setRenderer(roundedBarChartRenderer);

        binding.sellingBarChart.setDrawBarShadow(false);
        binding.sellingBarChart.setDrawValueAboveBar(false);
        binding.sellingBarChart.getDescription().setEnabled(false);
        binding.sellingBarChart.setDrawGridBackground(false);


        binding.sellingBarChart.getAxisRight().setEnabled(false);
        Legend legend = binding.sellingBarChart.getLegend();
        legend.setEnabled(false);


        List<IBarDataSet> dataSets = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(entries, " ");
        barDataSet.setColor(R.color.graphBarColor);
        barDataSet.setDrawValues(false);
        dataSets.add(barDataSet);


        BarData data = new BarData(dataSets);
        data.setBarWidth(0.25f);
        data.setValueTextColor(Color.BLACK);
        binding.sellingBarChart.setData(data);
        binding.sellingBarChart.setFitBars(false);
        binding.sellingBarChart.invalidate();


        XAxis xaxis = binding.sellingBarChart.getXAxis();
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setGranularity(0.5f);
        xaxis.setGranularityEnabled(true);
        xaxis.setDrawLabels(false);    // make changes bottom txt
        xaxis.setDrawAxisLine(false);

        binding.sellingBarChart.setTouchEnabled(false);
        binding.sellingBarChart.setDrawBarShadow(false);


        //hide grid lines
        binding.sellingBarChart.getAxisLeft().setDrawGridLines(false);


        //remove right y-axis
        binding.sellingBarChart.getAxisRight().setEnabled(false);

        //remove legend


        //remove description label
        binding.sellingBarChart.getDescription().setEnabled(false);

        //add animation
        binding.sellingBarChart.animateY(3000);


        //draw chart
        binding.sellingBarChart.invalidate();

    }


    /**
     * Top  5 customer
     **/

    public static ArrayList<Team> topSeller_5 = new ArrayList<>();

    private void loadTop5Customer() {
        HashMap<String, String> hd = new HashMap<>();
        hd.put("SalesPersonCode", Prefs.getString(Globals.SalesEmployeeCode, ""));

        Call<Top5CustomerResponse> call = NewApiClient.getInstance().getApiService().getTop5Customer(hd);
        call.enqueue(new Callback<Top5CustomerResponse>() {
            @Override
            public void onResponse(Call<Top5CustomerResponse> call, Response<Top5CustomerResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getValue().size() > 0) {
                        topSeller_5.clear();
                        topSeller_5.addAll(response.body().getValue());
                        setCustomergraph();

                    } else {
                        Toast.makeText(getContext(), "No data Found", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Top5CustomerResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        loadTop5Item();
    }

    private void setCustomergraph() {
        String customers[];
        float setData[];
        customers = new String[topSeller_5.size()];
        setData = new float[topSeller_5.size()];
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < topSeller_5.size(); i++) {
            customers[i] = topSeller_5.get(i).getEmpName();

            setData[i] = Float.valueOf(topSeller_5.get(i).getId());
            entries.add(new BarEntry((float) i, setData[i]));

            if (i == 0)
                binding.cus1.setText(customers[0]);
            else if (i == 1)
                binding.cus2.setText(customers[1]);
            else if (i == 2)
                binding.cus3.setText(customers[2]);
            else if (i == 3)
                binding.cus4.setText(customers[3]);
            else if (i == 4)
                binding.cus5.setText(customers[4]);
        }

        RoundedBarChart roundedBarChartRenderer = new RoundedBarChart(binding.customerBarChart, binding.customerBarChart.getAnimator(), binding.customerBarChart.getViewPortHandler());
        roundedBarChartRenderer.setmRadius(20f);
        binding.customerBarChart.setRenderer(roundedBarChartRenderer);

        binding.customerBarChart.setDrawBarShadow(false);
        binding.customerBarChart.setDrawValueAboveBar(false);
        binding.customerBarChart.getDescription().setEnabled(false);
        binding.customerBarChart.setDrawGridBackground(false);


        binding.customerBarChart.getAxisRight().setEnabled(false);
        Legend legend = binding.customerBarChart.getLegend();
        legend.setEnabled(false);


        List<IBarDataSet> dataSets = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(entries, " ");
        barDataSet.setColor(R.color.graphBarColor);
        barDataSet.setDrawValues(false);
        dataSets.add(barDataSet);


        BarData data = new BarData(dataSets);
        data.setBarWidth(0.25f);
        data.setValueTextColor(Color.BLACK);
        binding.customerBarChart.setData(data);
        binding.customerBarChart.setFitBars(false);
        binding.customerBarChart.invalidate();


        XAxis xaxis = binding.customerBarChart.getXAxis();
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setGranularity(0.5f);
        xaxis.setGranularityEnabled(true);
        xaxis.setDrawLabels(false);    // make changes bottom txt
        xaxis.setDrawAxisLine(false);

        binding.customerBarChart.setTouchEnabled(false);
        binding.customerBarChart.setDrawBarShadow(false);


        //hide grid lines
        binding.customerBarChart.getAxisLeft().setDrawGridLines(false);


        //remove right y-axis
        binding.customerBarChart.getAxisRight().setEnabled(false);

        //remove legend


        //remove description label
        binding.customerBarChart.getDescription().setEnabled(false);

        //add animation
        binding.customerBarChart.animateY(3000);


        //draw chart
        binding.customerBarChart.invalidate();


    }


    /**
     * Three rounded projection data
     **/


    String initialProj;
    String actualColl;
    String currenProj;

    private void setProjectionData(String month) {


        SalesEmployeeItem salesEmployeeItem = new SalesEmployeeItem();
        salesEmployeeItem.setSalesEmployeeCode(Prefs.getString(Globals.SalesEmployeeCode, ""));
        salesEmployeeItem.setMonth(month);
        Call<CounterResponse> call = NewApiClient.getInstance().getApiService().projectiondata(salesEmployeeItem);
        call.enqueue(new Callback<CounterResponse>() {
            @Override
            public void onResponse(Call<CounterResponse> call, Response<CounterResponse> response) {
                if (response != null) {
                    initialProj = response.body().getValue().get(0).getAmount();
                    currenProj = response.body().getValue().get(0).getSale();
                    binding.initialProjectionValue.setText(initialProj);
                    binding.currentProjectionValue.setText(currenProj);
                    setProjectionGraph(initialProj, currenProj);

                }
            }

            @Override
            public void onFailure(Call<CounterResponse> call, Throwable t) {

            }
        });


    }


    private void setProjectionGraph(String initialProj, String currenProj) {
        Calendar cal = Calendar.getInstance();
        int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        int currentDays = Integer.parseInt(dateFormat.format(cal.getTime()));
        if (currentDays > 1)
            currentDays = currentDays - 1;

        actualColl = String.valueOf(Float.parseFloat(currenProj) / currentDays * monthMaxDays);

        binding.actualProjectionValue.setText(actualColl);


        float actPer = Float.parseFloat(actualColl) / Float.parseFloat(initialProj);
        actPer = actPer * 100;
        float initPer = Float.parseFloat(initialProj) / Float.parseFloat(String.valueOf(currentDays)) * 100;


        binding.actualProgressBar.setProgress((int) actPer);
        binding.actualProgressValue.setText("" + (int) actPer + "%");

        binding.initialProgressBar.setProgress((int) initPer);
        binding.initialProgressValue.setText("" + (int) initPer + "%");

        if (Float.parseFloat(currenProj) > Float.parseFloat(initialProj)) {
            float per = Float.parseFloat(currenProj) - Float.parseFloat(initialProj);
            per = per / Float.parseFloat(initialProj);
            per = per * 100;
            binding.currentProgressBar.setProgress((int) per);
            binding.currentProgressValue.setText("" + (int) per + "%");
            binding.currentProgressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

        } else {
            float per = Float.parseFloat(initialProj) - Float.parseFloat(currenProj);
            per = per / Float.parseFloat(initialProj);
            per = per * 100;
            binding.currentProgressBar.setProgress((int) per);
            binding.currentProgressValue.setText("" + (int) per + "%");
            binding.currentProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.month_1:

                setSelectedBackground(binding.month1, binding.month3, binding.month6, binding.year);
                setProjectionData("1");
                break;
            case R.id.month_3:

                setSelectedBackground(binding.month3, binding.month1, binding.month6, binding.year);
                setProjectionData("3");
                break;
            case R.id.month_6:

                setSelectedBackground(binding.month6, binding.month3, binding.month1, binding.year);
                setProjectionData("6");
                break;
            case R.id.year:

                setSelectedBackground(binding.year, binding.month3, binding.month6, binding.month1);
                setProjectionData("12");
                break;
        }
    }

    private void setSelectedBackground(TextView month_1, TextView month_3, TextView month_6, TextView year) {
        month_1.setBackground(getResources().getDrawable(R.drawable.text_selected_rounded_back));
        month_1.setTextColor(getResources().getColor(R.color.white));
        month_3.setBackground(getResources().getDrawable(R.drawable.text_rounded_back));
        month_3.setTextColor(getResources().getColor(R.color.black));
        month_6.setBackground(getResources().getDrawable(R.drawable.text_rounded_back));
        month_6.setTextColor(getResources().getColor(R.color.black));
        year.setBackground(getResources().getDrawable(R.drawable.text_rounded_back));
        year.setTextColor(getResources().getColor(R.color.black));
    }
}