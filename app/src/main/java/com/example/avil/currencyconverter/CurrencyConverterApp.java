package com.example.avil.currencyconverter;

import android.app.Application;

/**
 * Created by avil on 26.09.17.
 */

public class CurrencyConverterApp extends Application {

    private static CurrencyConverterApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static CurrencyConverterApp getContext() {
        return instance;
    }
}
