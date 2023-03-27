package com.project.yourExchangeRatesApp.fluctuation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FluctuationApi {

    @GET("timeseries")
    Call<Model> getRates(@Header("apikey") String apiKey
            , @Query("start_date") String start_date
            , @Query("end_date") String end_date
            , @Query("base") String baseCurrency
            , @Query("SYMBOLS") String SYMBOLS );
}
