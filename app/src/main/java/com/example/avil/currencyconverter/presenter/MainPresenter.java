package com.example.avil.currencyconverter.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.model.Converter;
import com.example.avil.currencyconverter.repos.IRepoCallbackData;
import com.example.avil.currencyconverter.repos.IRepoCurrency;
import com.example.avil.currencyconverter.repos.RepoCurrency;
import com.example.avil.currencyconverter.view.MainView;


public class MainPresenter implements IMainPresenter, IRepoCallbackData  {

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

        initActions();

        if (updated == true) {
            return;
        }

        repo.update(this);
        updated = true;
    }

    /**
     * Колбек при обновлении данных
     */
    @Override
    public void update() {
        Activity activity = (Activity) mainView;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initActions();
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
    public void convert() {
        String from = mainView.getValue("spinner1");
        String to = mainView.getValue("spinner2");
        String v = mainView.getValue("moneyIn");
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

    private void initActions() {
        makeSpinner(mainView.getSpinner1(), 0);
        makeSpinner(mainView.getSpinner2(), 1);

        mainView.getMoneyIn().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    convert();
                }
                return false;
            }
        });
    }

    private void makeSpinner(Spinner spinner, int pos) {
        String[] data = repo.getCurrencys();
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, data);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        if (data.length <= pos) {
            pos = 0;
        }
        spinner.setSelection(pos); // Выделение элемента

        // обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
