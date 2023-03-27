package com.project.yourExchangeRatesApp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.yourExchangeRatesApp.R;

import java.util.List;
import java.util.Map;

public class ExchangeRatesAdapter extends RecyclerView.Adapter<ExchangeRatesAdapter.ExchangeRateViewHolder> {
  //  private List<Map.Entry<String, Double>> exchangeRatesList;

    private List<String> key;
    private  List<Double> value;

    public ExchangeRatesAdapter(List<String> key, List<Double> value) {
        this.key = key;
        this.value = value;
    }

    @NonNull
    @Override
    public ExchangeRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item, parent, false);
        return new ExchangeRateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeRateViewHolder holder, int position) {

        String string  = key.get(position);
        Double adouble  = value.get(position);
        Log.d("TAGy", "onBindViewHolder: "+ string);
        Log.d("TAGy", "onBindViewHolder: "+ adouble);

        holder.currencyTextView.setText(string);
        holder.rateTextView.setText(String.format("%.2f", adouble));
    }

    @Override
    public int getItemCount() {
        return key.size();
    }

    public static class ExchangeRateViewHolder extends RecyclerView.ViewHolder {
        private TextView currencyTextView;
        private TextView rateTextView;

        public ExchangeRateViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyTextView = itemView.findViewById(R.id.currencyTextView);
            rateTextView = itemView.findViewById(R.id.rate_text_view);
        }
    }
}

