package com.example.avil.currencyconverter.model;

import android.app.Activity;

import com.example.avil.currencyconverter.model.dictionary.CurrencyDB;
import com.example.avil.currencyconverter.model.dictionary.CurrencyDict;

import java.util.HashMap;


public class Converter implements IConverter {

    // rub eur usd
    HashMap<String, Float> course = new HashMap<>();

    public Converter() {
        for (int i = 0; i < CurrencyDict.ALL.length; i++) {
            course.put(CurrencyDict.ALL[i], 1f);
        }
    }


    @Override
    public void put(String key, float v) {
        course.put(key, v);
    }

    @Override
    public void loadLocalData(Activity activity) {
        CurrencyDB currencyDB = new CurrencyDB(activity);
        CurrencyDict.setOther(currencyDB.getCurrencys());

        put(CurrencyDict.MAIN, 1f);

        for (ValuteGetted v : currencyDB.getAll()){
            put(v.code, v.value);
        }
    }


    public float convert(String from, String to, float v) {
        float fromVal = course.get(from);
        float toVal = course.get(to);

        return (fromVal / toVal) * v;
    }


}
