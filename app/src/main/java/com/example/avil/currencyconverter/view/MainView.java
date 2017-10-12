package com.example.avil.currencyconverter.view;


import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public interface MainView {
    void setValue(String value);

    EditText getMoneyIn();
    Spinner getSpinner1();
    Spinner getSpinner2();
    Button getUpdateBtn();
    ProgressBar getProgressBar();

}
