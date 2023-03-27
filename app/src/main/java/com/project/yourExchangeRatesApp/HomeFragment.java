package com.project.yourExchangeRatesApp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.project.yourExchangeRatesApp.adapter.ExchangeRatesAdapter;

import com.project.yourExchangeRatesApp.fluctuation.FluctuationApi;
import com.project.yourExchangeRatesApp.fluctuation.FluctuationRatesInstance;
import com.project.yourExchangeRatesApp.fluctuation.Model;
import com.project.yourExchangeRatesApp.model.ExchangeRatesApi;
import com.project.yourExchangeRatesApp.model.ExchangeRatesResponse;
import com.project.yourExchangeRatesApp.model.exchangeratesInstance;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView exchangeRatesRecyclerView;

    LineChart hufChart,lbpChart;

    String endDate;
    String startDate;

    List<Double> hufValues;
    List<Double> lbpValues;

    Double changeHuf;
    Double changeLbp;
    String percentageChangeOfHuf;
    String percentageChangeOfLbp;

    TextView hufChange,lbpChange;

    ProgressBar progressBar2;



    public String API_KEY;

    private List<Map.Entry<String, Double>> exchangeRatesList = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        API_KEY =getString(R.string.api_key);


            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //task to perform in background

                    getChartData();
                    getCurrentRate();
                    //task to perform after completion of background task
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            });



    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        exchangeRatesRecyclerView = view.findViewById(R.id.recView);
        hufChart= view.findViewById(R.id.hufChart);
        lbpChart= view.findViewById(R.id.lbpChart);
        hufChange=view.findViewById(R.id.hufChange);
        lbpChange = view.findViewById(R.id.lbpChange);
        progressBar2 = view.findViewById(R.id.progressBar2);
    progressBar2.setVisibility(View.VISIBLE);


        return view;
    }

    private void getChartData() {


        getDates();



    FluctuationApi fluctuationApi = FluctuationRatesInstance.getservice();
    Call<Model> call = fluctuationApi.getRates(API_KEY, startDate, endDate, "USD", "LBP,HUF");
    call.enqueue(new Callback<Model>() {
        @Override
        public void onResponse(Call<Model> call, Response<Model> response) {
            if (response.isSuccessful() && response.body() != null) {
                Map<String, Map<String, Double>> map1 = response.body().getRates();


                hufValues = new ArrayList<>();
                for (Map<String, Double> innerMap : map1.values()) {
                    hufValues.add(innerMap.get("HUF"));
                }
                changeHuf = (((hufValues.get(hufValues.size() - 1) - hufValues.get(0)) / hufValues.get(0)) * 100);
                percentageChangeOfHuf = String.format("%.2f", changeHuf);



                lbpValues = new ArrayList<>();
                for (Map<String, Double> innerMap : map1.values()) {
                    lbpValues.add(innerMap.get("LBP"));
                }

                changeLbp = (((lbpValues.get(lbpValues.size() - 1) - lbpValues.get(0)) / lbpValues.get(0)) * 100);
                percentageChangeOfLbp = String.format("%.2f", changeLbp);


                enterValues();

                charthuf();

                chartLbp();


            }else{
                Toast.makeText(getContext(), "limit exceed", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(Call<Model> call, Throwable t) {
            getChartData();

        }
    });




    }
 public void getDates(){
     //date

     Date currentDate = new Date();
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     endDate = dateFormat.format(currentDate);

     Calendar calendar = Calendar.getInstance();
     calendar.setTime(currentDate);
     calendar.add(Calendar.DAY_OF_MONTH, -6);
     Date oneDayBefore = calendar.getTime();
     SimpleDateFormat dateFormatofprevious = new SimpleDateFormat("yyyy-MM-dd");
     startDate = dateFormatofprevious.format(oneDayBefore);
 }
    public void charthuf(){
        // Create an ArrayList of Entry objects from the HUF values
        ArrayList<Entry> hufEntries = new ArrayList<>();
        for (int i = 0; i < hufValues.size(); i++) {
            hufEntries.add(new Entry(i, hufValues.get(i).floatValue()));
        }

        LineDataSet hufDataSet = new LineDataSet(hufEntries, "huf rates");

        // Create a gradient drawable with a gradient color from blue to transparent
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.GREEN, Color.TRANSPARENT});

       // hufDataSet.setLineWidth(2f);
        hufDataSet.setColor(Color.RED);
        hufDataSet.setFillDrawable(gradientDrawable);


        LineData hufLineData = new LineData(hufDataSet);
        hufChart.setData(hufLineData);



        hufChart.getDescription().setEnabled(false);
        hufChart.getLegend().setEnabled(true);
        hufChart.getAxisRight().setEnabled(false);
        hufChart.getAxisLeft().setEnabled(false);

        hufChart.invalidate();
    }
    public void chartLbp(){

        ArrayList<Entry> lbpEntries = new ArrayList<>();
        for (int i = 0; i < lbpValues.size(); i++) {
            lbpEntries.add(new Entry(i, lbpValues.get(i).floatValue()));
        }


        LineDataSet lbpDataSet = new LineDataSet(lbpEntries, "LBP Exchange Rate");

        // Create a gradient drawable with a gradient color from blue to transparent
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.RED,Color.TRANSPARENT});

        // hufDataSet.setLineWidth(2f);
        lbpDataSet.setColor(Color.GREEN);
        lbpDataSet.setFillDrawable(gradientDrawable);
        lbpDataSet.setDrawValues(true);



        LineData lbpLineData = new LineData(lbpDataSet);
        lbpChart.setData(lbpLineData);



        lbpChart.getDescription().setEnabled(false);
        lbpChart.getLegend().setEnabled(true);
        lbpChart.getAxisRight().setEnabled(false);
        lbpChart.getAxisLeft().setEnabled(false);


        lbpChart.invalidate();

    }
    public void enterValues(){
        try{
            if (changeHuf < 0) {
                hufChange.setTextColor(Color.RED);
                hufChange.setText(percentageChangeOfHuf);

            } else {
                hufChange.setTextColor(Color.GREEN);
                hufChange.setText(percentageChangeOfHuf);

            }

            if (changeLbp < 0) {
                lbpChange.setTextColor(Color.RED);
                lbpChange.setText(percentageChangeOfLbp);
            } else {
                lbpChange.setTextColor(Color.GREEN);
                lbpChange.setText(percentageChangeOfLbp);
            }
        }catch(Exception e){
            Log.d("TAGY", "tetview " + e);
        }
    }







    private void getCurrentRate() {

            ExchangeRatesApi exchangeRatesApi = exchangeratesInstance.getservice();
            Call<ExchangeRatesResponse> call = exchangeRatesApi.getExchangeRates(API_KEY, "USD");
            call.enqueue(new Callback<ExchangeRatesResponse>() {
                @Override
                public void onResponse(Call<ExchangeRatesResponse> call, Response<ExchangeRatesResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        ExchangeRatesResponse exchangeRates = response.body();
                        String baseCurrency = exchangeRates.getBaseCurrency();
                        Map<String, Double> exchangeRatesMap = exchangeRates.getExchangeRates();

                        Set<String> keySet = exchangeRatesMap.keySet();
                        ArrayList<String> listOfKeys
                                = new ArrayList<String>(keySet);

                        Log.d("TAGY", "list " + listOfKeys);


                        Collection<Double> values = exchangeRatesMap.values();

                        // Creating an ArrayList of values
                        ArrayList<Double> listOfValues
                                = new ArrayList<>(values);
                        //    Log.d("TAGY", "onResponse: " + listOfValues);


                        ExchangeRatesAdapter exchangeRatesAdapter = new ExchangeRatesAdapter(listOfKeys,listOfValues);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        exchangeRatesRecyclerView.setLayoutManager(layoutManager);
                        exchangeRatesRecyclerView.setHasFixedSize(true);

                        exchangeRatesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                DividerItemDecoration.VERTICAL));
                        exchangeRatesRecyclerView.setAdapter(exchangeRatesAdapter);
                        progressBar2.setVisibility(View.GONE); // Hide the progress bar

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<ExchangeRatesResponse> call, Throwable t) {
                        getCurrentRate();
                }
            });

    }

}