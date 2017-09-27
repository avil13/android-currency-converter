package com.example.avil.currencyconverter.model;

import android.content.Context;

import com.example.avil.currencyconverter.model.database.CurrencyDB;

import java.util.HashMap;


public class Converter implements IConverter {

    HashMap<String, Float> course = new HashMap<>();

    private CurrencyDB currencyDB;

    public Converter(Context context) {
        currencyDB = new CurrencyDB(context);
    }


    public float convert(String from, String to, float v) {
        float fromVal = course.get(from);
        float toVal = course.get(to);

        return (fromVal / toVal) * v;
    }

    public void resync() {
        for (ValuteGetted v : currencyDB.getAll()) {
            course.put(v.code, v.value);
        }
    }
}
