package me.shouheng.live.common;

import java.text.DecimalFormat;

public class DecimalFormatUtil {

    private static final DecimalFormat decimalFormat = new DecimalFormat();

    public static String formatW(int vaule){
        if(vaule >= 10000){
            float l = vaule / 10000.0f;
            return format(l,"#.#'W'");
        }
       return String.valueOf(vaule);
    }

    public static String format(float vaule, String pattern){
        decimalFormat.applyPattern(pattern);
        return decimalFormat.format(vaule);
    }
}
