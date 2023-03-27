package com.project.yourExchangeRatesApp.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ExchangeRatesApi {
    @GET("latest")
    Call<ExchangeRatesResponse> getExchangeRates(@Header("apikey") String apiKey, @Query("base") String baseCurrency);
}
