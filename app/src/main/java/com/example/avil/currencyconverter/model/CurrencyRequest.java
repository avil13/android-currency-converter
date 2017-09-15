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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class CurrencyRequest implements ICurrencyRequest {

    private static final String path = "http://www.cbr.ru/scripts/XML_daily.asp";

    private HandlerThread handlerThread;

    private Context context;

    private Converter converter;

    private CurrencyDB currencyDB;

    private SQLiteDatabase db;


    public CurrencyRequest(final Context context, final Converter converter) {
        this.context = context;
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


                    Toast.makeText(context, R.string.currency_loaded, Toast.LENGTH_LONG).show();

                    CurseParser curseParser = new CurseParser(inputStream);

                    // ====
                    // Сохраняем в бд все валюты
                    currencyDB = new CurrencyDB(context);

                    for (Valute v : curseParser.getValute()) {
                        if (v.code.equals(CurrencyDict.MAIN)) {
                            continue;
                        }

                        float val = (Float.valueOf(v.value.replace(",", ".")) / v.nominal);

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(CurrencyDB.FeedEntry.KEY_NAME, v.code);
                        contentValues.put(CurrencyDB.FeedEntry.KEY_VALUE, val);

                        currencyDB.createOrUpdae(v.code, contentValues);
                    }

                    CurrencyDict.setOther(currencyDB.getCurrencys());

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, R.string.currency_cant_load, Toast.LENGTH_LONG).show();
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
