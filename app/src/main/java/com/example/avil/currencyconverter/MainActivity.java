package com.example.avil.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    String[] spins = {"", ""};
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = MainPresenter.getInstance();

        presenter.setActivity(this);


        Spinner spinner1 = (Spinner) findViewById(R.id.spinner_1);
        SpinerAdapter spinnerAdapter1 = new SpinerAdapter(this, spinner1);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_2);
        SpinerAdapter spinnerAdapter2 = new SpinerAdapter(this, spinner2);



    }


}
