package com.example.avil.currencyconverter.view;


import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public interface MainView {
    void setValue(String value);

    void initSpinner(String[] data);

    void showProgressBar();
    void hideProgressBar();

}
