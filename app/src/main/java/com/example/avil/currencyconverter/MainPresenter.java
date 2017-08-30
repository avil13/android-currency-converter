package com.example.avil.currencyconverter;

/**
 * Created by avil on 29.08.17.
 */

public class MainPresenter {

    private MainActivity activity;

    private static MainPresenter instance = new MainPresenter();

    private Converter converter;

    private MainPresenter() {
        converter = Converter.getInstance();

        CurrencyRequest currencyRequest = new CurrencyRequest();
        currencyRequest.get();


    }


    public static MainPresenter getInstance() {
        return instance;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public String convert(String from, String to, String v) {
        if ("".equals(v)) {
            return "";
        }

        double val = Double.parseDouble(v);
        double res = converter.convert(from, to, val);

        return String.valueOf(res);
    }

}
