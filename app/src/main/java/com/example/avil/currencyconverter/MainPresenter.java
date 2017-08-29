package com.example.avil.currencyconverter;

/**
 * Created by avil on 29.08.17.
 */

public class MainPresenter {

    private MainActivity activity;

    private static MainPresenter instance = new MainPresenter();

    private MainPresenter() {}

    public static MainPresenter getInstance() {
        return instance;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }
}
