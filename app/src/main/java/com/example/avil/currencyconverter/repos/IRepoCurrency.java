package com.example.avil.currencyconverter.repos;

import com.example.avil.currencyconverter.utils.LogMessage;

public interface IRepoCurrency {
    float get(String key);

    String[] getCurrencys();

    void update();

    void update(IRepoCallbackData callback);

    void onDestroy();

    void setLogger(LogMessage logger);
}
