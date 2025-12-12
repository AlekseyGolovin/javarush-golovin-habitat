package ua.net.agsoft.javarush.habitat.util;

import org.json.simple.JSONObject;

public class UtilJSON {
    public static int getIntFromObject(JSONObject jo, String name, int defVal) {
        try {
            if (jo == null) {
                return defVal;
            }
            if (jo.get(name) == null) {
                return defVal;
            }
            String value = jo.get(name).toString();
            if (value.equalsIgnoreCase("null")) {
                return defVal;
            }
            if (value.isEmpty()) {
                return defVal;
            }
            return Integer.parseInt(value);
        } catch (Exception ignore) {
        }
        return defVal;
    }

    public static double getDoubleFromObject(JSONObject jo, String name, double defVal) {
        try {
            if (jo == null) {
                return defVal;
            }
            if (jo.get(name) == null) {
                return defVal;
            }
            String value = jo.get(name).toString();
            if (value.equalsIgnoreCase("null")) {
                return defVal;
            }
            if (value.isEmpty()) {
                return defVal;
            }
            return Double.parseDouble(value);
        } catch (Exception ignore) {
        }
        return defVal;
    }

}
