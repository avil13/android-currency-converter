package com.example.avil.currencyconverter;

import android.widget.Toast;

import curseValue.CurseParser;
import dictionary.CurrencyDict;


public class MainPresenter implements ICallBack {

    private MainActivity activity;

    private static MainPresenter instance = new MainPresenter();

    private Converter converter;
    private static CurrencyRequest currencyRequest;

    private MainPresenter() {
        converter = Converter.getInstance();

        currencyRequest = new CurrencyRequest();
    }


    public static MainPresenter getInstance() {
        return instance;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public String convert(String from, String to, String v) {
        if ("".equals(v)) {
            return "";
        }

        float val = Float.parseFloat(v);
        float res = converter.convert(from, to, val);

        return String.valueOf(res);
    }



    public void updateCurce(){
        currencyRequest.get(instance);
    }

    // обновление данных о курсах
    public void updateCurseData(){
        CurseParser curseParser = currencyRequest.getCurse();

        for (int i = 0; i < CurrencyDict.OTHER.length; i++) {
            String name = CurrencyDict.OTHER[i];
            converter.put(name, curseParser.getVal(name));
        }

        Toast.makeText(activity, "The course was updated", Toast.LENGTH_SHORT).show();
    }

}
