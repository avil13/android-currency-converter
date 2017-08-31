package com.example.avil.currencyconverter.model;


import android.app.Activity;

public interface IConverter {
    void put(String key, float v);

    void saveRow(Activity activity, String k, float v) ;

    void loadLocalData(Activity activity);
}
