package com.project.yourExchangeRatesApp.modelForConversion;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class currencyConversionInstance {

    private static Retrofit retrofit = null;

    private static String BASE_URL = "https://api.apilayer.com/fixer/";

    //singleton pattern

    public static CurrencyConversionApi getservice(){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(CurrencyConversionApi.class);
    }
}
