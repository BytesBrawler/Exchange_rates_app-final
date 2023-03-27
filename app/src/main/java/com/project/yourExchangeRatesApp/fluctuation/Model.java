package com.project.yourExchangeRatesApp.fluctuation;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Model {

    @SerializedName("base")
    private String baseCurrency;
    @SerializedName("rates")
    private Map<String, Map<String,Double>> rates;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<String, Map<String, Double>> getRates() {
        return rates;
    }

    public void setRates(Map<String, Map<String, Double>> rates) {
        this.rates = rates;
    }
}
