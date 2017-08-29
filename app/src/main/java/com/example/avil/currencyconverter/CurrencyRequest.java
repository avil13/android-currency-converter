package com.example.avil.currencyconverter;


import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by avil on 29.08.17.
 */

public class CurrencyRequest {

    MainPresenter presenter;


    public CurrencyRequest(MainPresenter presenter) {
        this.presenter = presenter;
    }


    public void get() {

        StringBuilder stringBuilder = new StringBuilder();

        HttpURLConnection urlConnection = null;
        URL url;

        try {
            url = new URL("https://jsonplaceholder.typicode.com/posts");

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


    private void parseData(String data) {
        System.out.println(data);
    }

}
