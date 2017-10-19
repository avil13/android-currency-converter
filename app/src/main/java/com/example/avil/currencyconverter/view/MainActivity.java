package com.example.avil.currencyconverter.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.avil.currencyconverter.R;
import com.example.avil.currencyconverter.presenter.IMainPresenter;
import com.example.avil.currencyconverter.presenter.MainPresenter;


public class MainActivity extends AppCompatActivity implements MainView {

    private IMainPresenter presenter;

    private EditText moneyIn;
    private TextView moneyOut;

    private Spinner spinner1;
    private Spinner spinner2;

    private Button update_btn;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner) findViewById(R.id.spinner_1);
        spinner2 = (Spinner) findViewById(R.id.spinner_2);

        moneyIn = (EditText) findViewById(R.id.money_in);
        moneyOut = (TextView) findViewById(R.id.money_out);

        update_btn = (Button) findViewById(R.id.update_btn);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // Presenter
        presenter = MainPresenter.getInstance();
        presenter.setView(this);

        // Start actions
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.update_btn) {
                    presenter.update();
                }
            }
        });

        moneyIn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    presenter.convert(
                            spinner1.getSelectedItem().toString(),
                            spinner2.getSelectedItem().toString(),
                            moneyIn.getText().toString()
                    );
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Отображение результата пересчета курса
     *
     * @param val
     */
    @Override
    public void setValue(String val) {
        moneyOut.setText(val);
    }

    @Override
    public void initSpinner(String[] data) {
        makeSpinner(spinner1, data, 0);
        makeSpinner(spinner2, data, 1);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void makeSpinner(Spinner spinner, String[] data, int pos) {
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, data);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        if (data.length <= pos) {
            pos = 0;
        }
        spinner.setSelection(pos); // Выделение элемента

        // обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.convert(
                        spinner1.getSelectedItem().toString(),
                        spinner2.getSelectedItem().toString(),
                        moneyIn.getText().toString()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
