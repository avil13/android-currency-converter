package com.example.avil.currencyconverter.model.curse_value;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Valute")
class Valute {
    @Element(name = "Value")
    public String value;

    @Element(name = "CharCode")
    public String code;

    @Element(name = "Nominal")
    public String nominal;
}