package com.project.yourExchangeRatesApp.modelForConversion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class conversionList {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("result")
        @Expose
        private Double result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
