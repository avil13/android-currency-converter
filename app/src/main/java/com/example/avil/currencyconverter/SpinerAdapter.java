package com.example.avil.currencyconverter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by avil on 29.08.17.
 */

public class SpinerAdapter {
    public static String[] DATA = {"RUB", "USD", "EUR"};


    public SpinerAdapter(final Context context, Spinner spinner) {

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, DATA);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

//        Spinner spinner = (Spinner) spinner.findViewById();

        spinner.setAdapter(adapter);
//        // Заголовок
//        spinner.setPrompt("Валюта");
        // Выделение элемента
        spinner.setSelection(0);

        // обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "Spinner Position " + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
