package com.example.avil.currencyconverter.model;

import android.content.Context;

import com.example.avil.currencyconverter.model.database.CurrencyDB;


public class Converter implements IConverter {

    private CurrencyDB currencyDB;

    public Converter(Context context) {
        currencyDB = new CurrencyDB(context);
    }


    public float convert(String from, String to, float v) {
        float fromVal = currencyDB.getVal(from);
        float toVal = currencyDB.getVal(to);

        return (fromVal / toVal) * v;
    }

}
