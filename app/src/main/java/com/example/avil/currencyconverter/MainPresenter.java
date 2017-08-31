package com.example.avil.currencyconverter;

import android.util.Log;
import android.widget.Toast;

import curseValue.CurseParser;


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

        double val = Double.parseDouble(v);
        double res = converter.convert(from, to, val);

        return String.valueOf(res);
    }



    public void updateCurce(){
        currencyRequest.get(instance);
    }

    // обновление данных о курсах
    public void updateCurseData(){
        CurseParser curseParser = currencyRequest.getCurse();

        converter.put("EUR", curseParser.getVal("EUR"));
        converter.put("USD", curseParser.getVal("USD"));

        Toast.makeText(activity, "The course was updated", Toast.LENGTH_SHORT).show();
    }

}
