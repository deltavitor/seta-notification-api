package edu.costavitor.setanotificationapi.common;

public class StringToBooleanConverter {

    public static Boolean stringToBoolean(String value) {
        return value.equals("1") ? Boolean.TRUE : Boolean.FALSE;
    }
}
