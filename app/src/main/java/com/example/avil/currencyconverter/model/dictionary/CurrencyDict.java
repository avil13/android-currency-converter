package com.example.avil.currencyconverter.model.dictionary;


import java.util.ArrayList;
import java.util.Arrays;

public class CurrencyDict {

    public final static String MAIN = "RUB";

    public static String[] OTHER = {"EUR", "USD"};

    public static String[] ALL = {"RUB", "EUR", "USD"};


    public  static void setOther(String[] newOther) {
        OTHER = newOther;
        ALL = getAll();
    }

    private static String[] getAll() {
        ArrayList<String> list = new ArrayList<>(1 + OTHER.length);

        list.add(MAIN);
        list.addAll(Arrays.asList(OTHER));

        return list.toArray(new String[1 + OTHER.length]);
    }
}
