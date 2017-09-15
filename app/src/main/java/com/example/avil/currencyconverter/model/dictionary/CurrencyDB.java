package com.example.avil.currencyconverter.model.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.avil.currencyconverter.model.ValuteGetted;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avil on 15.09.17.
 */

public class CurrencyDB extends SQLiteOpenHelper {

    public static abstract class FeedEntry implements BaseColumns {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "currency_db";
        public static final String TABLE_NAME = "currencys";

        public static final String KEY_ID = "_id";
        public static final String KEY_NAME = "name";
        public static final String KEY_VALUE = "value";
    }


    public CurrencyDB(Context context) {
        super(context, FeedEntry.DATABASE_NAME, null, FeedEntry.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FeedEntry.TABLE_NAME +
                " (" +
                FeedEntry.KEY_ID + " INTEGER PRIMARY KEY, " +
                FeedEntry.KEY_NAME + " TEXT, " +
                FeedEntry.KEY_VALUE + " REAL " +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + FeedEntry.TABLE_NAME);
        onCreate(db);
    }


    /**
     * Создание или обновление данных о записи в БД
     * <p>
     * example:
     * createOrUpdate("EUR", val);
     *
     * @param key    Ключ - валюта
     * @param values
     */
    public void createOrUpdae(String key, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                null,
                FeedEntry.KEY_NAME + " = ?",
                new String[]{key},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // обновляем данные
            db.update(FeedEntry.TABLE_NAME, values, FeedEntry.KEY_NAME + " = ?", new String[]{key});
        } else {
            // создаем запись
            db.insert(FeedEntry.TABLE_NAME, null, values);
        }
        cursor.close();
        db.close();
    }


    /**
     * Получение списка валют которые имеются в БД
     *
     * @return
     */
    public String[] getCurrencys() {
        ArrayList<String> list = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(FeedEntry.TABLE_NAME, new String[]{FeedEntry.KEY_NAME}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(
                        cursor.getString(cursor.getColumnIndex(FeedEntry.KEY_NAME))
                );
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return list.toArray(new String[list.size()]);
    }

    /**
     * Получение стоимости по ключу
     *
     * @param key
     * @return
     */
    public Float getVal(String key) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                new String[]{FeedEntry.KEY_VALUE},
                FeedEntry.KEY_NAME + " = ?",
                new String[]{key},
                null,
                null,
                null,
                "1");

        float res = 1;

        if (cursor.moveToFirst()) {
            res = cursor.getFloat(cursor.getColumnIndex(FeedEntry.KEY_VALUE));
        }
        cursor.close();
        db.close();

        return res;
    }

    /**
     *  Получение всех значений из БД
     *
     * @return
     */
    public List<ValuteGetted> getAll(){

        ArrayList<ValuteGetted> list = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(FeedEntry.TABLE_NAME, new String[]{FeedEntry.KEY_NAME, FeedEntry.KEY_VALUE}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(
                        new ValuteGetted(
                                cursor.getString(cursor.getColumnIndex(FeedEntry.KEY_NAME)),
                                Float.valueOf(
                                    cursor.getString(cursor.getColumnIndex(FeedEntry.KEY_VALUE))
                                )
                        )
                );
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return list;
    }
}
