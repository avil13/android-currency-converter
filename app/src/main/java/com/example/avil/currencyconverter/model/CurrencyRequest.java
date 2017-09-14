package com.example.avil.currencyconverter.model;


import com.example.avil.currencyconverter.model.curse_value.CurseParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class CurrencyRequest implements ICurrencyRequest {

    private static final String path = "http://www.cbr.ru/scripts/XML_daily.asp";

    CurseParser curseParser;


    @Override
    public void get(final ICallBack callBack) {

        new Thread() {
            @Override
            public void run() {

                HttpURLConnection urlConnection = null;
                URL url;

                try {
                    url = new URL(path);

                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();

                    parseData(in);

                    callBack.updateCurseData();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            }
        }.start();
    }


    private void parseData(InputStream inputStream) {
        curseParser = new CurseParser(inputStream);
    }

    @Override
    public CurseParser getCurse() {
        return curseParser;
    }
}
