package com.example.avil.currencyconverter.presenter;

import android.content.Context;

import com.example.avil.currencyconverter.model.Converter;
import com.example.avil.currencyconverter.model.CurrencyRequest;
import com.example.avil.currencyconverter.model.database.CurrencyDB;
import com.example.avil.currencyconverter.view.MainActivity;
import com.example.avil.currencyconverter.view.MainView;


public class MainPresenter implements IMainPresenter {

    private MainView mainView;
    private CurrencyRequest currencyRequest;

    private static MainPresenter instance = new MainPresenter();

    private Converter converter;
    private CurrencyDB currencyDB;


    private MainPresenter() {
    }


    public static MainPresenter getInstance() {
        return instance;
    }


    @Override
    public void setView(MainView mainView) {
        this.mainView = mainView;

        converter = new Converter((Context) mainView);
        currencyDB = new CurrencyDB((Context) mainView);
    }


    @Override
    public void convert(String from, String to, String v) {
        String value;

        if ("".equals(v)) {
            value = "";
        } else {
            float val = Float.parseFloat(v);
            float res = converter.convert(from, to, val);
            value = String.format("%4.2f", res);
        }

        mainView.setValue(value);
    }

    public void onDestroy() {
        currencyRequest.onDestroy();
    }


    public void updateCurce() {
        currencyRequest = new CurrencyRequest((MainActivity) mainView);
    }


    public String[] getSpinnerData() {
        return currencyDB.getCurrencys();
    }


}
