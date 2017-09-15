package com.example.avil.currencyconverter.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.avil.currencyconverter.model.Converter;
import com.example.avil.currencyconverter.model.CurrencyRequest;
import com.example.avil.currencyconverter.model.dictionary.CurrencyDict;
import com.example.avil.currencyconverter.view.MainView;


public class MainPresenter implements IMainPresenter {

    private MainView mainView;
    private CurrencyRequest currencyRequest;

    private static MainPresenter instance = new MainPresenter();

    private Converter converter;


    private MainPresenter() {
        converter = new Converter();
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

    public void onDestroy() {
        currencyRequest.onDestroy();
    }


    public void updateCurce(Context context) {
        currencyRequest = new CurrencyRequest(context, converter);
    }


    public String[] getSpinnerData() {
        return CurrencyDict.ALL;
    }

}
