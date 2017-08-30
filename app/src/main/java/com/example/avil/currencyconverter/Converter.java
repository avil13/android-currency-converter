package com.example.avil.currencyconverter;

import java.util.HashMap;

/**
 * Created by avil on 30.08.17.
 */

public class Converter {

    // rub eur usd
    HashMap<String, Double> course;


    public Converter() {
        course = new HashMap<>();

        course.put("RUB", 1d);
        course.put("EUR", 70d);
        course.put("USD", 59d);
    }

    double convert(String from, String to, double v) {
        Double fromVal = course.get(from);
        Double toVal = course.get(to);

        return (fromVal / toVal) * v;
    }
}
