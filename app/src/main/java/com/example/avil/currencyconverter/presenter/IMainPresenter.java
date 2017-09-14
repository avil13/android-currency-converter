package com.example.avil.currencyconverter.presenter;

import android.content.Context;

import com.example.avil.currencyconverter.view.MainView;

/**
 * Created by avil on 31.08.17.
 */

public interface IMainPresenter {
    void setView(MainView mainView);

    String convert(String from, String to, String v);

    void updateCurce(Context context);

    String[] getSpinnerData();

    void onDestroy();
}
