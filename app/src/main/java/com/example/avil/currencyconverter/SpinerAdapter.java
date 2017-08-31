package com.example.avil.currencyconverter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class SpinerAdapter {
    public static String[] DATA = {"RUB", "USD", "EUR"};


    public SpinerAdapter(final MainActivity mainActivity, Spinner spinner, int pos) {

        final Context context = (Context) mainActivity;

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, DATA);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
//      spinner.setPrompt("Валюта"); // Заголовок
        spinner.setSelection(pos); // Выделение элемента

        // обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//              Toast.makeText(context, "Spinner Position " + i, Toast.LENGTH_SHORT).show();
                mainActivity.updateMoneyOutText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
