package com.example.avil.currencyconverter;


import android.util.Log;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import curseValue.CurseParser;

/**
 * Created by avil on 29.08.17.
 */

public class CurrencyRequest {

//    String path = "http://www.cbr.ru/scripts/XML_daily.asp";
    String path = "http://localhost:8080/curs.xml";

    CurseParser curseParser;


    public void get() {
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

    public CurseParser getCurse(){
        return curseParser;
    }

}
