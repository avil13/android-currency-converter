package com.example.avil.currencyconverter.presenter;

import android.app.Activity;

import com.example.avil.currencyconverter.model.Converter;
import com.example.avil.currencyconverter.model.CurrencyRequest;
import com.example.avil.currencyconverter.model.ICallBack;
import com.example.avil.currencyconverter.model.curse_value.CurseParser;
import com.example.avil.currencyconverter.model.dictionary.CurrencyDict;
import com.example.avil.currencyconverter.view.MainView;


public class MainPresenter implements ICallBack, IMainPresenter {

    private MainView mainView;

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


    @Override
    public void setView(MainView mainView) {
        this.mainView = mainView;

        converter.loadLocalData((Activity) mainView);
    }


    @Override
    public String convert(String from, String to, String v) {
        if ("".equals(v)) {
            return "";
        }

        float val = Float.parseFloat(v);
        float res = converter.convert(from, to, val);

        return String.format("%4.2f", res);
    }


    @Override
    public void updateCurce() {
        currencyRequest.get(instance);
    }


    // коллбек обновление данных о курсах
    @Override
    public void updateCurseData() {
        CurseParser curseParser = currencyRequest.getCurse();

        for (int i = 0; i < CurrencyDict.OTHER.length; i++) {
            String name = CurrencyDict.OTHER[i];
            put(name, curseParser.getVal(name));
        }
    }


    public String[] getSpinnerData() {
        return CurrencyDict.ALL;
    }


    private void put(String k, float v) {
        converter.put(k, v);

        converter.saveRow((Activity) mainView, k, v);
    }


}
