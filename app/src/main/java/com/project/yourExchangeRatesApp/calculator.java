package com.project.yourExchangeRatesApp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yourExchangeRatesApp.modelForConversion.CurrencyConversionApi;
import com.project.yourExchangeRatesApp.modelForConversion.conversionList;
import com.project.yourExchangeRatesApp.modelForConversion.currencyConversionInstance;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class calculator extends AppCompatActivity {

    public String API_KEY ;

    Spinner spinner1 , spinner2;
    EditText edtAmount;

    TextView txtView;
    Button btn;
    String base,toconvert;
    String amount;
    Double result;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        btn  =findViewById(R.id.btn);
         progressBar = findViewById(R.id.progressBar);

        txtView = findViewById(R.id.txtView);

        edtAmount= findViewById(R.id.edtAmount);

        API_KEY= getString(R.string.api_key);

        setSpinner();



//calculate button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = edtAmount.getText().toString();
                if(!base.isEmpty() && !toconvert.isEmpty() && !amount.isEmpty()){

                    progressBar.setVisibility(View.VISIBLE); // Show the progress bar

                    //get our result
                    doresultInBackground();

                }else{
                    Toast.makeText(calculator.this, "please complete the fields", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }



    private void getResult() {
        try {

            //this will give us our result
            CurrencyConversionApi currencyConversionApi = currencyConversionInstance.getservice();
            Call<conversionList> call = currencyConversionApi.getCurrencyConversion(API_KEY,
                    base, toconvert, amount );

            call.enqueue(new Callback<conversionList>() {
                @Override
                public void onResponse(Call<conversionList> call, Response<conversionList> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        conversionList clist = response.body();



                        result = clist.getResult();


                        txtView.setText(String.valueOf(result));
                        progressBar.setVisibility(View.GONE); // Show the progress bar

                    }
                }

                @Override
                public void onFailure(Call<conversionList> call, Throwable t) {
                    Toast.makeText(calculator.this, "Server Error " + " please retry", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE); // Show the progress bar
                }
            });
        }catch (Exception e ){

        }

    }

    private void setSpinner() {
try {
    List<String> currencyList = Arrays.asList("USD" ,"AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BYR", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VES", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD", "XDR", "XOF", "XPF", "YER", "ZAR", "ZMK", "ZMW", "ZWL");
    List<String> stringList = new ArrayList<>();
    for (String currency : currencyList) {
        stringList.add(currency);
    }

    Log.d("TAGY", "onCreate: " + stringList);


    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringList);
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner1.setAdapter(arrayAdapter);
    spinner2.setAdapter(arrayAdapter);
    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            toconvert = parent.getItemAtPosition(position).toString();
        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(calculator.this, "please select item from from", Toast.LENGTH_SHORT).show();
        }
    });
    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            base = parent.getItemAtPosition(position).toString();


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(calculator.this, "please select item from from", Toast.LENGTH_SHORT).show();
        }
    });
}catch (Exception e){
    Log.d("TAGY", "setSpinner: " + e);
}

    }

    @Override
    protected void onStart() {
        super.onStart();


    }



   private void  doresultInBackground(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //task to perform in background
                getResult();

                //task to perform after completion of background task
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

}