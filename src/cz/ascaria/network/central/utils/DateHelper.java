/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.utils;

import cz.ascaria.network.central.Main;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

/**
 *
 * @author Ascaria Quynn
 */
public class DateHelper {

    /**
     * Creates date and time from given format ("yyyy-MM-dd HH:mm:ss") by given dateTimeString ("2014-10-17 21:14:55").
     * @param format
     * @param dateTime
     * @return in case of failure, returns new Date(0)
     */
    public static Date createDateTime(String format, String dateTimeString) {
        try {
            return new SimpleDateFormat(format, Locale.ENGLISH).parse(dateTimeString);
        } catch(ParseException ex) {
            Main.LOG.log(Level.SEVERE, null, ex);
        }
        return new Date(0);
    }

    /**
     * Formats given date with specified pattern.
     * @param pattern
     * @param date
     * @return 
     */
    public static String format(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * Formats actual date with specified pattern. Usage DateHelper.format("HH:mm:ss")
     * @param pattern
     * @return 
     */
    public static String format(String pattern) {
        return format(pattern, new Date());
    }

    /**
     * Returns current time as HH:mm:ss
     * @return 
     */
    public static String time() {
        return format("HH:mm:ss");
    }
}
