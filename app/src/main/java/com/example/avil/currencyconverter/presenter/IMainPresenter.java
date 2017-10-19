package com.example.avil.currencyconverter.presenter;

import com.example.avil.currencyconverter.view.MainView;


public interface IMainPresenter {
    void setView(MainView mainView);

    void update();

    void convert(String from, String to, String v);

    void onDestroy();
}
