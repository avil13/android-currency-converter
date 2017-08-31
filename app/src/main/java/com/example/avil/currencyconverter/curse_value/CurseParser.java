package com.example.avil.currencyconverter.curse_value;


import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;


public class CurseParser {

    public ValCurs curs;

    public CurseParser(String xml) {
        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();

        try {
            curs = serializer.read(ValCurs.class, reader, false);
        } catch (Exception e) {
            Log.e("SimpleXML::", e.getMessage());
        }
    }


    public float getVal(String code) {
        return Float.parseFloat(curs.getValute(code).value.replace(",", "."));
    }

}
