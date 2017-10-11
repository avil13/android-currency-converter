package com.example.avil.currencyconverter.view;


import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public interface MainView {
    void setValue(String value);

    EditText getMoneyIn();
    Spinner getSpinner1();
    Spinner getSpinner2();
    Button getUpdateBtn();

    void startLoader();
    void stopLoader();
}
