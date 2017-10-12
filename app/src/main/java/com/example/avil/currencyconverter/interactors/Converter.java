package com.example.avil.currencyconverter.interactors;

import android.content.Context;

import com.example.avil.currencyconverter.repos.IRepoCurrency;
import com.example.avil.currencyconverter.repos.RepoCurrency;


public class Converter implements IConverter {

    private IRepoCurrency repo;

    public Converter(Context context) {
        repo = new RepoCurrency(context);
    }


    public float convert(String from, String to, float v) {
        float fromVal = repo.get(from);
        float toVal = repo.get(to);

        return (fromVal / toVal) * v;
    }
}
