package com.project.yourExchangeRatesApp.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class exchangeratesInstance {
    private static Retrofit retrofit = null;

    private static String BASE_URL = "https://api.apilayer.com/fixer/";

    //singleton pattern

    public static ExchangeRatesApi getservice(){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ExchangeRatesApi.class);
    }
}
