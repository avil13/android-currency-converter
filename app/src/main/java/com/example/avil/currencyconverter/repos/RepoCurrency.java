package com.example.avil.currencyconverter.repos;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.model.CurrencyRequest;
import com.example.avil.currencyconverter.model.ValuteGetted;
import com.example.avil.currencyconverter.model.curse_value.CurseParser;
import com.example.avil.currencyconverter.model.curse_value.Valute;
import com.example.avil.currencyconverter.model.database.CurrencyDB;
import com.example.avil.currencyconverter.utils.LogMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class RepoCurrency implements IRepoCurrency, IRepoCallback<Valute> {

    private static HashMap<String, Float> course = new HashMap<>();

    private static IRepoCallbackData callback;

    private CurrencyDB currencyDB;

    private CurrencyRequest currencyRequest;

    private static RepoRequest repoRequest = null;


    public RepoCurrency(Context context) {
        currencyDB = new CurrencyDB(context);

        // создаем первый хэш на основе кэша в БД
        if (course.isEmpty()) {
            for (ValuteGetted v : currencyDB.getAll()) {
                course.put(v.code, v.value);
            }
        }
    }

    @Override
    public float get(String key) {
        return course.get(key);
    }

    @Override
    public String[] getCurrencys() {
        return currencyDB.getCurrencys();
    }

    /**
     * Обновление данных, через http запрос
     */
    @Override
    public void update() {
//        currencyRequest = new CurrencyRequest(this);
        if(repoRequest  != null) {
            log(R.string.request_already_sent);
            return;
        }
        repoRequest = (RepoRequest) new RepoRequest().execute("http://www.cbr.ru/scripts/XML_daily.asp");
    }

    @Override
    public void update(IRepoCallbackData callback) {
        this.callback = callback;
        update();
    }

    @Override
    public void update(List<Valute> list) {
        // очищаем список в кэше
        course.clear();
        // первый в списке
        put("RUB", "1", 1, 0);

        for (Valute v : list) {
            if (v.code.equals("EUR") || v.code.equals("USD")) {
                put(v.code, v.value, v.nominal, 1);
            } else {
                put(v.code, v.value, v.nominal, 2);
            }
        }

        if (callback != null) {
            callback.onFinish();
            callback = null;
        }
    }

    @Override
    public void onFinish() {
        if (callback != null) {
            callback.onFinish();
            callback = null;
        }
        if (repoRequest != null) {
            repoRequest = null;
        }
    }

    @Override
    public void log(String str) {
        LogMessage.toast(str);
    }

    @Override
    public void log(int str) {
        LogMessage.toast(str);
    }


    private void put(String code, String value, float nominal, float order) {
        float val = (Float.valueOf(value.replace(",", ".")) / nominal);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CurrencyDB.FeedEntry.KEY_NAME, code);
        contentValues.put(CurrencyDB.FeedEntry.KEY_VALUE, val);
        contentValues.put(CurrencyDB.FeedEntry.KEY_ORDER, order);

        currencyDB.createOrUpdate(code, contentValues);
        course.put(code, val);
    }

    @Override
    public void onDestroy() {
        if (currencyRequest != null) {
            currencyRequest.onDestroy();
        }
        if (repoRequest != null) {
            repoRequest.cancel(true);
        }
    }


    private class RepoRequest extends AsyncTask<String, Void, List<Valute>> {

        @Override
        protected List<Valute> doInBackground(String... path) {
            HttpURLConnection urlConnection = null;


            try {
                log(R.string.update_rate);

                URL url = new URL(path[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                log(R.string.currency_loaded);

                CurseParser curseParser = new CurseParser(inputStream);

                // Сохраняем все валюты =   =   =   =
                List<Valute> list = curseParser.getValute();

                return list;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                onFinish();

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }

        protected void onPostExecute(List<Valute> result) {
            if (result != null) {
                onFinish();
            }
        }
    }
}
