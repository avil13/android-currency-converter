package com.example.avil.currencyconverter.view;


import android.widget.EditText;
import android.widget.Spinner;

public interface MainView {
    void setValue(String value);
    String getValue(String id);

    EditText getMoneyIn();
    Spinner getSpinner1();
    Spinner getSpinner2();
}
