package com.example.avil.currencyconverter.utils;


import android.widget.Toast;
import com.example.avil.currencyconverter.CurrencyConverterApp;


public class LogMessage {
    public static void toast(int str) {
        Toast.makeText(CurrencyConverterApp.getContext(), str, Toast.LENGTH_LONG).show();
    }
}
