package me.nikl.cookieclicker;

import java.math.BigInteger;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Created by Niklas on 07.07.2017.
 */
public class Utility {

    public static String convertHugeNumber(double number){
        if(number >= 1000.) return convertHugeNumber(BigInteger.valueOf((long) number));
        String numberStr = String.valueOf(number);
        String[] split = numberStr.split("\\.");
        if(split.length == 1) return numberStr;
        if(split[1].substring(0,1).equals("0")) return split[0];
        if(split[1].length() > 1){
            numberStr = split[0] + "." + split[1].substring(0,1);
        }

        return numberStr;
    }

    public static String convertHugeNumber(BigInteger number){
        String numberStr = String.valueOf(number);

        int index = (numberStr.length() - 1)/3;

        if(index == 0) return numberStr;

        if (index > 1) {
            numberStr = numberStr.substring(0, numberStr.length() - (index - 1) * 3);
        }

        if (numberStr.substring(numberStr.length() - 3).equals("000")){
            return numberStr.substring(0, numberStr.length() - 3) + " " + NAMES[index - 1];
        }

        numberStr = new StringBuilder(numberStr).insert(numberStr.length() - 3, ".").toString();

        if(numberStr.substring(numberStr.length() - 2).equals("00")){
             return numberStr.substring(0, numberStr.length() - 2) + " " + NAMES[index - 1];
        }

        if(numberStr.substring(numberStr.length() - 1).equals("0")){
            return numberStr.substring(0, numberStr.length() - 1) + " " + NAMES[index - 1];
        }

        return numberStr + " " + NAMES[index - 1];
    }

    private static final String NAMES[] = new String[]{
            "Thousand",
            "Million",
            "Billion",
            "Trillion",
            "Quadrillion",
            "Quintillion",
            "Sextillion",
            "Septillion",
            "Octillion",
            "Nonillion",
            "Decillion",
            "Undecillion",
            "Duodecillion",
            "Tredecillion",
            "Quattuordecillion",
            "Quindecillion",
            "Sexdecillion",
            "Septendecillion",
            "Octodecillion",
            "Novemdecillion",
            "Vigintillion",
    };

    private static final NavigableMap<Integer, String> MAP;
    static
    {
        MAP = new TreeMap<Integer, String>();
        for (int i=0; i<NAMES.length; i++)
        {
            MAP.put(3 * i + 3, NAMES[i]);
        }
    }
}
