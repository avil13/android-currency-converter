package com.example.avil.currencyconverter.repos;

public interface IRepoCurrency {
    float get(String key);

    String[] getCurrencys();

    void update();

    void update(IRepoCallbackData callback);

    void onDestroy();
}
