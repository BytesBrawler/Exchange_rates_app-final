package com.project.yourExchangeRatesApp.modelForConversion;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CurrencyConversionApi {


    @GET("convert")
    Call<conversionList> getCurrencyConversion(@Header("apikey") String apiKey
                                       , @Query("to") String toCurrrency
                                    , @Query("from") String fromCurrency
                                    ,@Query("amount") String amount
                          );
}
