package com.example.avil.currencyconverter;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import curseValue.CurseParser;

/**
 * Created by avil on 29.08.17.
 */

public class CurrencyRequest {

//    private static final String path = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static final String path = "http://localhost:8080/curs.xml";

    CurseParser curseParser;


    public void get(ICallBack callBack) {
        StringBuilder stringBuilder = new StringBuilder();

        HttpURLConnection urlConnection = null;
        URL url;

        try {
            url = new URL(path);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();

            while (data != -1) {
                stringBuilder.append((char) data);
                data = isw.read();
            }

            parseData(stringBuilder.toString());

            callBack.updateCurseData();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }


    private void parseData(String xml) {
        curseParser = new CurseParser(xml);
    }

    public CurseParser getCurse() {
        return curseParser;
    }

//
//    public void get() {
//        get(new FakeCallback());
//    }
//
//    private class FakeCallback implements ICallBack {
//        public void updateCurseData() {
//        }
//    }
}
