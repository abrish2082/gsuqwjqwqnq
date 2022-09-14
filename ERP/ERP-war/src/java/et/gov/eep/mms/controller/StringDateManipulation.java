
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.regex.Pattern;

/**
 *
 * @author tsehayu
 */
public class StringDateManipulation {

    /**
     * Creates a new instance of StringDateManipulation
     */
    public StringDateManipulation() {
    }


    /**
     * reads the date of the client machien and change it to Ethiopian date
     *
     * @param todayInGC the current date in GC
     * @return returns the current date in EC with 'yyyy-MM-dd' format
     *
     */
    public static String todayInEC(String todayInGC) {

        long noYear = 0;
        currentYear = year1999;     // starting date's year
        currentMonth = year1999_1stMonth;
        currentDay = year1999_1stDay;
        currentDate = year1999_1stDate;
        // number of milliseconds
        long noOfDaysFromStart = Date.valueOf(todayInGC).getTime() - Date.valueOf("2006-09-11").getTime();

        noOfDaysFromStart /= (1000 * 60 * 60 * 24); //   number of days

        //Move years
        noYear = (4 * noOfDaysFromStart) / ((4 * 365) + 1);   // number of years in the gap
        currentYear += noYear;

        noOfDaysFromStart = (noOfDaysFromStart - ((noYear * ((4 * 365) + 1)) / 4));

        //Move months
        if ((noOfDaysFromStart % 30) == 0) {
            currentMonth = (noOfDaysFromStart / 30);
        } else {
            currentMonth += (noOfDaysFromStart / 30);
        }

        //move days
        if ((noOfDaysFromStart % 30) == 0) {
            currentDate = 30;
        } else {
            currentDate = (noOfDaysFromStart % 30);
        }

        String dateString = null;

        String _dateSeparator = "-";
        String _monthSeparator = "-";
        if (currentDate < 10) {
            _dateSeparator = "-0";
        }
        if (currentMonth < 10) {
            _monthSeparator = "-0";
        }

        dateString = currentYear + _monthSeparator + currentMonth + _dateSeparator + currentDate;

        return dateString;
    }

    /**
     * Changes the current date in GC to String format yyyy-MM-dd
     *
     * @return returns the java.util.Date() as yyyy-MM-dd
     */
    public static String currentDateInGC() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date curDate = new java.util.Date();

        return format.format(curDate);
    }

    /**
     * Calls the method toDayInEc(currentDateInGC())
     *
     * @return 
     * @returns toDayInEc(currentDateInGC())
     * @see #currentDateInGC()
     * @see #todayInEC(String todayInGC)
     */
    public static String todayInEC() {
        return todayInEC(currentDateInGC());
    }

    private static long currentYear, currentMonth, currentDay, currentDate;
    private static long year1999 = 1999, year1999_1stDate = 1, year1999_1stMonth = 1, year1999_1stDay = 1;


}

