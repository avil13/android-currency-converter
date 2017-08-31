package com.example.avil.currencyconverter;

import java.util.HashMap;


public class Converter {

    private static Converter instance = new Converter();

    // rub eur usd
    HashMap<String, Double> course;

    private Converter() {
        course = new HashMap<>();

        course.put("RUB", 1d);
        course.put("EUR", 70d);
        course.put("USD", 59d);
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
