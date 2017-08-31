package com.example.avil.currencyconverter.curse_value;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "ValCurs")
class ValCurs {
    @ElementList(entry = "Valute", inline = true)
    private List<Valute> valute;

    public List<Valute> getValute() {
        return valute;
    }

    public Valute getValute(String code) {
        for (Valute v : valute) {
            if (v.code.equals(code)) {
                return v;
            }
        }
        return null;
    }
}
