package com.bu.livinggood.tools;

import java.text.DecimalFormat;
import java.util.Locale;

public class Tools {
    public static double round(double value) {
        return (double) Math.round(value * 100) / 100;
    }

    /**
     * convert price/bath/bed string to int
     * - : null
     * 4000+ / 4+ : 4 / 4
     * 2000 / 2 : 2000 / 2
     * @param s
     * @return
     */
    public static Integer intWithPlus(String s) {
        if (s.equals("-"))
            return null;
        if (s.contains("+"))
            return Integer.parseInt(s.substring(0, -1));
        return Integer.parseInt(s);
    }

    /**
     * convert price/bath/bed string to int
     * - : 0
     * 4000+ / 4+ : 4 / 4
     * 2000 / 2 : 2000 / 2
     * @param s
     * @return
     */
    public static Integer floorIntWithPlus(String s) {
        if (s.equals("-"))
            return 0;
        if (s.contains("+"))
            return Integer.parseInt(s.substring(0, -1));
        return Integer.parseInt(s);
    }

    /**
     * convert price/bath/bed string to int
     * - / 4000+ / 4+ : INT.MAX_VALUE
     * 2000 / 2 : 2000 / 2
     * @param s
     * @return
     */
    public static Integer ceilIntWithPlus(String s) {
        if (s.equals("-") || s.contains("+"))
            return Integer.MAX_VALUE;
        return Integer.parseInt(s);
    }

    /**
     * generate string for bedroom and bathroom
     * @param bedroom
     * @param bathroom
     * @return
     */
    public static String formatBedroomBathroom(Integer bedroom, Double bathroom) {
        StringBuilder builder = new StringBuilder();
        if (bedroom != null) {
            builder.append(String.format(Locale.US, "%dbds ", bedroom));
        } else {
            builder.append("-bds ");
        }

        if (bathroom != null) {
            builder.append(new DecimalFormat("#.#ba").format(bathroom));
        } else {
            builder.append("-ba");
        }
        return builder.toString();
    }
}


