package curseValue;


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
//            System.out.println(getVal("EUR"));
//            System.out.println(getVal("USD"));

        } catch (Exception e) {
            Log.e("SimpleXML::", e.getMessage());
//            System.err.println(e.getMessage());
        }
    }


    public double getVal(String code) {
        return Double.parseDouble(curs.getValute(code).value.replace(",", "."));
    }

}
