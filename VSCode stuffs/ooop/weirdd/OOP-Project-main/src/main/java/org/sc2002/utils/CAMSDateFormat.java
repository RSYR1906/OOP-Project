package org.sc2002.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The type Cams date format.
 */
public class CAMSDateFormat {

    /**
     * The Formatter.
     */
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Format string to date local date.
     *
     * @param dateString the date string
     * @return the local date
     */
    public static LocalDate formatStringToDate(String dateString){
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Format date to string string.
     *
     * @param date the date
     * @return the string
     */
    public static String formatDateToString(LocalDate date){
        return date.format(formatter);
    }
}
