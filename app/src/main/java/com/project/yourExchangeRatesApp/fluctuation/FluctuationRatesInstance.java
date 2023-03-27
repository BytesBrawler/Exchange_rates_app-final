package com.project.yourExchangeRatesApp.fluctuation;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FluctuationRatesInstance {

    private static Retrofit retrofit = null;

    private static String BASE_URL = "https://api.apilayer.com/fixer/";

    //singleton pattern

    public static FluctuationApi getservice(){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(FluctuationApi.class);
    }
}
