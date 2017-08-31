package com.example.avil.currencyconverter;

import java.util.HashMap;

import dictionary.CurrencyDict;


public class Converter {

    // rub eur usd
    HashMap<String, Float> course = new HashMap<>();

    Converter() {
        for (int i = 0; i < CurrencyDict.ALL.length; i++) {
            course.put(CurrencyDict.ALL[i], 1f);
        }
    }


    public void put(String key, float v) {
        course.put(key, v);
    }


    float convert(String from, String to, float v) {
        float fromVal = course.get(from);
        float toVal = course.get(to);

        return (fromVal / toVal) * v;
    }
}
