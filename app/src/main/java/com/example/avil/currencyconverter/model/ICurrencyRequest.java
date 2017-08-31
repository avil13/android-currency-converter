package com.example.avil.currencyconverter.model;

import curseValue.CurseParser;

/**
 * Created by avil on 31.08.17.
 */

interface ICurrencyRequest {
    void get(ICallBack callBack);

    CurseParser getCurse();
}
