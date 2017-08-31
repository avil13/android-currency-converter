package com.example.avil.currencyconverter.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.model.Converter;
import com.example.avil.currencyconverter.model.CurrencyRequest;
import com.example.avil.currencyconverter.model.ICallBack;
import com.example.avil.currencyconverter.view.MainView;

import com.example.avil.currencyconverter.curse_value.CurseParser;
import com.example.avil.currencyconverter.dictionary.CurrencyDict;


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


    // обновление данных о курсах
    @Override
    public void updateCurseData() {
        CurseParser curseParser = currencyRequest.getCurse();

        for (int i = 0; i < CurrencyDict.OTHER.length; i++) {
            String name = CurrencyDict.OTHER[i];
            put(name, curseParser.getVal(name));
        }
    }


    public void makeSpinner(final MainView activity, Spinner spinner, int pos) {

        final Context context = (Context) activity;

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, CurrencyDict.ALL);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        // spinner.setPrompt("Валюта"); // Заголовок
        spinner.setSelection(pos); // Выделение элемента

        // обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activity.updateMoneyOutText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void put(String k, float v) {
        converter.put(k, v);

        converter.saveRow((Activity) mainView, k, v);
    }


}
