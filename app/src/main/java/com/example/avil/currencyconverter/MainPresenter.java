package com.example.avil.currencyconverter;

import android.content.SharedPreferences;
import android.widget.Toast;

import curseValue.CurseParser;
import dictionary.CurrencyDict;


public class MainPresenter implements ICallBack {

    private MainActivity activity;

    private static MainPresenter instance = new MainPresenter();

    private Converter converter;
    private static CurrencyRequest currencyRequest;

    private MainPresenter() {
        converter = new Converter();

        currencyRequest = new CurrencyRequest();
    }


    public static MainPresenter getInstance() {
        return instance;
    }


    public void setActivity(MainActivity activity) {
        this.activity = activity;

        readFromCache();
    }


    public String convert(String from, String to, String v) {
        if ("".equals(v)) {
            return "";
        }

        float val = Float.parseFloat(v);
        float res = converter.convert(from, to, val);

        return String.format("%4.2f", res);
    }


    public void updateCurce() {
        currencyRequest.get(instance);
    }


    // обновление данных о курсах
    public void updateCurseData() {
        CurseParser curseParser = currencyRequest.getCurse();

        for (int i = 0; i < CurrencyDict.OTHER.length; i++) {
            String name = CurrencyDict.OTHER[i];
            put(name, curseParser.getVal(name));
        }

        Toast.makeText(activity, "The course was updated", Toast.LENGTH_SHORT).show();
    }


    private void put(String k, float v) {
        converter.put(k, v);

        SharedPreferences.Editor editor = activity.getSharedPreferences("com.avil.cache", activity.MODE_PRIVATE).edit();
        editor.putFloat(k, v);
        editor.commit();
    }


    private void readFromCache() {
        SharedPreferences sp = activity.getSharedPreferences("com.avil.cache", activity.MODE_PRIVATE);

        for (int i = 0; i < CurrencyDict.ALL.length; i++) {
            converter.put(CurrencyDict.ALL[i], sp.getFloat(CurrencyDict.ALL[i], 1f));
        }
    }

}
