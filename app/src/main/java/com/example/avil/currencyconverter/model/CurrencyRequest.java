package com.example.avil.currencyconverter.model;


import com.example.avil.currencyconverter.model.curse_value.CurseParser;

import java.io.InputStream;
import java.io.InputStreamReader;
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
                StringBuffer stringBuffer = new StringBuffer();

                HttpURLConnection urlConnection = null;
                URL url;

                try {
                    url = new URL(path);

                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();

                    while (data != -1) {
                        stringBuffer.append((char) data);
                        data = isw.read();
                    }

                    parseData(stringBuffer.toString());

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


    private void parseData(String xml) {
        curseParser = new CurseParser(xml);
    }

    @Override
    public CurseParser getCurse() {
        return curseParser;
    }
}
