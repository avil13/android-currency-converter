package com.example.avil.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String[] spins = {"", ""};
    MainPresenter presenter;

    EditText moneyIn;
    TextView moneyOut;

    Spinner spinner1;
    Spinner spinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = MainPresenter.getInstance();

        presenter.setActivity(this);


        spinner1 = (Spinner) findViewById(R.id.spinner_1);
        SpinerAdapter spinnerAdapter1 = new SpinerAdapter(this, spinner1, 0);

        spinner2 = (Spinner) findViewById(R.id.spinner_2);
        SpinerAdapter spinnerAdapter2 = new SpinerAdapter(this, spinner2, 1);

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
}
