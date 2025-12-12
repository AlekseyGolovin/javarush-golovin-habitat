package ua.net.agsoft.javarush.habitat.util;

import org.json.simple.JSONObject;

public class UtilJSON {
    public static int getIntFromObject(JSONObject jo, String name, int defVal) {
        int result = defVal;
        if (jo != null && jo.get(name) != null) {
            try {
                String strVal = jo.get(name).toString();
                if (strVal.equalsIgnoreCase("null")) {
                    strVal = "";
                }
                if (!strVal.isEmpty()) {
                    result = Integer.parseInt(strVal);
                }
            } catch (Exception ex) {
            }
        }
        return result;
    }

    public static double getDoubleFromObject(JSONObject jo, String name, double defVal) {
        double result = defVal;
        if (jo != null && jo.get(name) != null) {
            try {
                String strVal = jo.get(name).toString();
                if (strVal.equalsIgnoreCase("null")) strVal = "";
                if (strVal.length() > 0 ) {

                    //String strNumVal = UtilString.numDotOnly(strVal);

                    result = Double.parseDouble(strVal);
                }
            } catch (Exception ex) { }
        }
        return result;
    }

}
