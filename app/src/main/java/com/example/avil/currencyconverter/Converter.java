package com.example.avil.currencyconverter;

import java.util.HashMap;

import dictionary.CurrencyDict;


public class Converter {

    private static Converter instance = new Converter();

    // rub eur usd
    HashMap<String, Double> course = new HashMap<>();

    private Converter() {
        for (int i = 0; i < CurrencyDict.ALL.length; i++) {
            course.put(CurrencyDict.ALL[i], 1d);
        }
    }

    public static Converter getInstance() {
        return instance;
    }


    public void put(String key, double v) {
        course.put(key, v);
    }


    double convert(String from, String to, double v) {
        Double fromVal = course.get(from);
        Double toVal = course.get(to);

        return (fromVal / toVal) * v;
    }
}
