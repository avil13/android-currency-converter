package com.example.avil.currencyconverter.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.model.curse_value.CurseParser;
import com.example.avil.currencyconverter.model.curse_value.Valute;
import com.example.avil.currencyconverter.model.dictionary.CurrencyDB;
import com.example.avil.currencyconverter.model.dictionary.CurrencyDict;
import com.example.avil.currencyconverter.view.MainActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CurrencyRequest implements ICurrencyRequest {

    private static final String path = "http://www.cbr.ru/scripts/XML_daily.asp";

    private HandlerThread handlerThread;

    private MainActivity mainActivity;

    private Converter converter;

    private CurrencyDB currencyDB;

    private SQLiteDatabase db;


    public CurrencyRequest(final MainActivity mainActivity, final Converter converter) {
        this.mainActivity = mainActivity;
        this.converter = converter;


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


                    Toast.makeText(mainActivity, R.string.currency_loaded, Toast.LENGTH_LONG).show();

                    CurseParser curseParser = new CurseParser(inputStream);

                    // ====
                    // Сохраняем в бд все валюты
                    currencyDB = new CurrencyDB(mainActivity);

                    List<Valute> list = curseParser.getValute();
                    Collections.sort(list, new Comparator<Valute>() {
                        @Override
                        public int compare(Valute v1, Valute v2) {
                            return v1.toString().compareTo(v2.toString());
                        }
                    });

                    for (Valute v : list) {
                        float val = (Float.valueOf(v.value.replace(",", ".")) / v.nominal);

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(CurrencyDB.FeedEntry.KEY_NAME, v.code);
                        contentValues.put(CurrencyDB.FeedEntry.KEY_VALUE, val);

                        currencyDB.createOrUpdae(v.code, contentValues);
                    }

                    CurrencyDict.setOther(currencyDB.getCurrencys());


                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.initSpinners();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mainActivity, R.string.currency_cant_load, Toast.LENGTH_LONG).show();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }
        });
    }

    public void onDestroy() {
        handlerThread.quit();
    }

}
