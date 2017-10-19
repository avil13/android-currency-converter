package com.example.avil.currencyconverter.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.interactors.Converter;
import com.example.avil.currencyconverter.repos.IRepoCallbackData;
import com.example.avil.currencyconverter.repos.IRepoCurrency;
import com.example.avil.currencyconverter.repos.RepoCurrency;
import com.example.avil.currencyconverter.view.MainView;


public class MainPresenter implements IMainPresenter {

    private static MainPresenter instance = new MainPresenter();

    private static boolean updated = false;

    private MainView mainView;

    private Converter converter;
    private IRepoCurrency repo;


    private MainPresenter() {
    }

    public static MainPresenter getInstance() {
        return instance;
    }

    @Override
    public void setView(MainView mainView) {
//        WeakReference
        this.mainView = mainView;

        converter = new Converter(this.getContext());
        repo = new RepoCurrency(this.getContext());

        if (updated == true) {
            return;
        }

        update();
        updated = true;
    }

    public void update() {
        mainView.showProgressBar();

        repo.update(new IRepoCallbackData() {

            public void onFinish() {
                Activity activity = (Activity) mainView;
                // Колбек при обновлении данных
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainView.initSpinner(repo.getCurrencys());
                        mainView.hideProgressBar();
                    }
                });
            }

        });
    }

    public void onDestroy() {
        repo.onDestroy();
    }

    public Context getContext() {
        return (Context) mainView;
    }

    /**
     * Метод для пересчета результата
     */
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

}
