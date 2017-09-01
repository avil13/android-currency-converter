package com.example.avil.currencyconverter.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.avil.currencyconverter.dictionary.CurrencyDict;
import com.example.avil.currencyconverter.presenter.IMainPresenter;
import com.example.avil.currencyconverter.presenter.MainPresenter;
import com.example.avil.currencyconverter.R;


public class MainActivity extends AppCompatActivity implements MainView {

    private IMainPresenter presenter;

    private EditText moneyIn;
    private TextView moneyOut;

    private Spinner spinner1;
    private Spinner spinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = MainPresenter.getInstance();

        presenter.setView(this);


        spinner1 = (Spinner) findViewById(R.id.spinner_1);
        makeSpinner(spinner1, 0);

        spinner2 = (Spinner) findViewById(R.id.spinner_2);
        makeSpinner(spinner2, 1);


        moneyOut = (TextView) findViewById(R.id.money_out);

        moneyIn = (EditText) findViewById(R.id.money_in);

        moneyIn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    updateMoneyOutText();
                }
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.updateCurce();
    }


    public void updateMoneyOutText() {
        String res = presenter.convert(
                spinner1.getSelectedItem().toString(),
                spinner2.getSelectedItem().toString(),
                moneyIn.getText().toString()
        );

        moneyOut.setText(res);
    }


    private void makeSpinner(Spinner spinner, int pos) {
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, presenter.spinnerData());

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        // spinner.setPrompt("Валюта"); // Заголовок
        spinner.setSelection(pos); // Выделение элемента

        // обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateMoneyOutText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}