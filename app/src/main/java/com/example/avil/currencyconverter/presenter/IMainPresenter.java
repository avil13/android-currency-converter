package com.example.avil.currencyconverter.presenter;

import android.widget.Spinner;

import com.example.avil.currencyconverter.view.MainActivity;
import com.example.avil.currencyconverter.view.MainView;

/**
 * Created by avil on 31.08.17.
 */

public interface IMainPresenter {
    void setView(MainView mainView);

    String convert(String from, String to, String v);

    void updateCurce();

    String[] spinnerData();

    // коллбек обновление данных о курсах
    void updateCurseData();
}
