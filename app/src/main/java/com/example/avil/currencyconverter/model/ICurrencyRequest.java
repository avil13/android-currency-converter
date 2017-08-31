package com.example.avil.currencyconverter.model;

import com.example.avil.currencyconverter.curse_value.CurseParser;

/**
 * Created by avil on 31.08.17.
 */

interface ICurrencyRequest {
    void get(ICallBack callBack);

    CurseParser getCurse();
}
