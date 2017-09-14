package com.example.avil.currencyconverter.model;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.model.curse_value.CurseParser;
import com.example.avil.currencyconverter.model.dictionary.CurrencyDict;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class CurrencyRequest implements ICurrencyRequest {

    private static final String path = "http://www.cbr.ru/scripts/XML_daily.asp";

    private HandlerThread handlerThread;

    private Context context;

    private Converter converter;


    public CurrencyRequest(final Context context, Converter converter) {
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

                    for (int i = 0; i < CurrencyDict.OTHER.length; i++) {
                        String name = CurrencyDict.OTHER[i];
                        put(name, curseParser.getVal(name));
                    }

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


    private void put(String k, float v) {
        converter.put(k, v);
        converter.saveRow((Activity) context, k, v);
    }


    public void onDestroy() {
        handlerThread.quit();
    }

}
