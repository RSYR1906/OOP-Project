package org.sc2002.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CAMSDateFormat {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate formatStringToDate(String dateString){
        return LocalDate.parse(dateString, formatter);
    }

    public static String formatDateToString(LocalDate date){
        return date.format(formatter);
    }
}
