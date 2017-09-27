package com.example.avil.currencyconverter.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.avil.currencyconverter.model.Converter;
import com.example.avil.currencyconverter.model.CurrencyRequest;
import com.example.avil.currencyconverter.model.database.CurrencyDB;
import com.example.avil.currencyconverter.view.MainView;


public class MainPresenter implements IMainPresenter {

    private MainView mainView;
    private CurrencyRequest currencyRequest;

    private static MainPresenter instance = new MainPresenter();

    private Converter converter;
    private CurrencyDB currencyDB;

    private boolean updated = false;


    private MainPresenter() {
    }

    public static MainPresenter getInstance() {
        return instance;
    }

    @Override
    public void setView(MainView mainView) {
        this.mainView = mainView;

        if (updated == true) {
            return;
        }

        converter = new Converter(this.getContext());
        currencyDB = new CurrencyDB(this.getContext());
        currencyRequest = new CurrencyRequest(this);
        updated = true;
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

    public String[] getSpinnerData() {
        return currencyDB.getCurrencys();
    }

    public void updateViewData() {
        Activity activity = (Activity) mainView;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                converter.resync();
                mainView.initSpinners();
            }
        });
    }

    public Context getContext() {
        return (Context) mainView;
    }

}
