package com.example.avil.currencyconverter.model;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.model.curse_value.CurseParser;
import com.example.avil.currencyconverter.model.curse_value.Valute;
import com.example.avil.currencyconverter.model.database.CurrencyDB;
import com.example.avil.currencyconverter.utils.LogMessage;
import com.example.avil.currencyconverter.view.MainActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class CurrencyRequest implements ICurrencyRequest {

    private static final String path = "http://www.cbr.ru/scripts/XML_daily.asp";

    private HandlerThread handlerThread;

    private MainActivity mainActivity;

    private CurrencyDB currencyDB;

    private SQLiteDatabase db;


    public CurrencyRequest(final MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        currencyDB = new CurrencyDB(mainActivity);

        LogMessage.toast(R.string.update_rate);

        handlerThread = new HandlerThread("currency_request");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection urlConnection = null;
                URL url;

                try {
                    url = new URL(path);

                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = urlConnection.getInputStream();

                    LogMessage.toast(R.string.currency_loaded);

                    CurseParser curseParser = new CurseParser(inputStream);

                    // ====
                    // Сохраняем в бд все валюты
                    List<Valute> list = curseParser.getValute();

                    // первый в списке
                    put("RUB", "1", 1, 0);

                    for (Valute v : list) {
                        if (v.code.equals("EUR") || v.code.equals("USD")) {
                            put(v.code, v.value, v.nominal, 1);
                        } else {
                            put(v.code, v.value, v.nominal, 2);
                        }
                    }

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.initSpinners();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    LogMessage.toast(R.string.currency_cant_load);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
        });
    }

    private void put(String code, String value, float nominal, float order) {
        float val = (Float.valueOf(value.replace(",", ".")) / nominal);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencyDB.FeedEntry.KEY_NAME, code);
        contentValues.put(CurrencyDB.FeedEntry.KEY_VALUE, val);
        contentValues.put(CurrencyDB.FeedEntry.KEY_ORDER, order);

        currencyDB.createOrUpdae(code, contentValues);
    }

    public void onDestroy() {
        handlerThread.quit();
    }

}
