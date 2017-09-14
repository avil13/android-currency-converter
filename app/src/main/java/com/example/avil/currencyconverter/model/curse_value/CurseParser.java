package com.example.avil.currencyconverter.model.curse_value;


import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.InputStream;


public class CurseParser {

    public ValCurs curs;

    public CurseParser(InputStream inputStream) {
        Persister serializer = new Persister();

        try {
            curs = serializer.read(ValCurs.class, inputStream, false);
        } catch (Exception e) {
            Log.e("SimpleXML::", e.getMessage());
        }
    }


    public float getVal(String code) {
        return Float.parseFloat(curs.getValute(code).value.replace(",", "."));
    }

}
