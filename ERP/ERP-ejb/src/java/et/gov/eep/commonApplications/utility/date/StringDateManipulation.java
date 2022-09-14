package et.gov.eep.commonApplications.utility.date;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
/*
 * StringDateManipulation.java
 *
 * Created on April 3, 2007, 4:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 * This class is to manipulate the date strings which comes from the text field
 * objects ( which in turn comes from the EthCalendar Object ) This class mainly
 * calculate the difference in year in months and in days and have 3 methods
 * correspondingly
 *
 * @author Administrator
 */
public class StringDateManipulation {

    public static class toDayInEc {

        public toDayInEc() {
        }
    }

     public static double getDuration45(int month, int day, int year, String accountingP) throws Exception {
        DateFormat sdfmt = new SimpleDateFormat("yyyy-mm-dd");
        String budjetend1 = accountingP;
        //Date endDate = sdfmt.parse(budjetend);
        /* input calendar date */
        month--; // following the 0-based rule
        Calendar cal = new GregorianCalendar(year, month, day);

        /* date today */
        //java.util.Date today = endDate;

        /* year now */
//        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
//        int intYear = Integer.parseInt(sdfYear.format(today));
        int intYear = Integer.parseInt(budjetend1.split("-")[0]);
        int intMonth = Integer.parseInt(budjetend1.split("-")[1]);
        int intDay = Integer.parseInt(budjetend1.split("-")[2]);

//        /* month now */
//        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
//        int intMonth = Integer.parseInt(sdfMonth.format(today));
//        intMonth--; // following the 0-based rule
//
//        /* day now */
//        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
//        int intDay = Integer.parseInt(sdfDay.format(today));

        /* calendar date now */
        Calendar now = new GregorianCalendar(intYear, intMonth, intDay);

        /* years duration */
        int yyyy = intYear - year;

        /* array of days in 12 months */
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        /*
         * an indicator if given date has already occurred. (-1) - if given date
         * is greater than the current date; 0 - if given date has not yet
         * occurred or if it's equal to the current date.
         */
        int factor = 0;

        int mm = 0; // month duration

        int dd = 0; // day duration

        /* determine if given date is greater than or equal to the current date */
        if ((month > intMonth) || (month == intMonth && day > intDay)) {
            factor = -1;
            yyyy += factor;
        }

        /* throw if any of the following exceptions occur */
        if (month > 12) {
            throw new Exception("Invalid input month");
        } else if (day > months[month]) {
            throw new Exception("Invalid input day");
        } else if (yyyy < 0) {
            throw new Exception("Invalid input date");
        }

        /*
         * if the given date has already passed or if it's equal to the current
         * date
         */
        if (factor == 0) {
            // compute for days in between the given and the current date
            dd = now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
            /* determine if the current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day of the current month
             */
            for (int i = month; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        } else { // if the given date is greater than the current date
            intYear--; // derive previous year
                /* set Calendar date for December 31 of the previous year */
            Calendar prev = new GregorianCalendar(intYear, 11, 31);
            /*
             * compute the days in between the given date last year and the
             * current date
             */
            dd = (prev.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) + now.get(Calendar.DAY_OF_YEAR);
            /* determine if previous year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day in December of the previous year
             */
            for (int i = month; i <= 11; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            intYear++; // set the value back to the current year
                /* determine if current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given day
             * in January of the current year and the current date
             */
            for (int i = 0; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        }

        //return diff;
        double mm2 = mm;
        double dd2 = mm;
        double x = yyyy;
        double y = mm2 / 12;
        double z = dd2 / 365;
        double tot = x + y + z;
        //System.err.println("duration two result=====" + tot);
        return tot;
    }
    
    
    
    
    public static double getDuration3(int month, int day, int year, String accountingP) throws Exception {
        DateFormat sdfmt = new SimpleDateFormat("dd/mm/yyyy");
        String budjetend = accountingP;
        Date endDate = sdfmt.parse(budjetend);
        /* input calendar date */
        month--; // following the 0-based rule
        Calendar cal = new GregorianCalendar(year, month, day);

        /* date today */
        //java.util.Date today = endDate;

        /* year now */
//        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
//        int intYear = Integer.parseInt(sdfYear.format(today));
        int intYear = Integer.parseInt(budjetend.split("/")[2]);
        int intMonth = Integer.parseInt(budjetend.split("/")[1]);
        intMonth--; // following the 0-based rule
        int intDay = Integer.parseInt(budjetend.split("/")[0]);

//        /* month now */
//        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
//        int intMonth = Integer.parseInt(sdfMonth.format(today));
//        intMonth--; // following the 0-based rule
//
//        /* day now */
//        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
//        int intDay = Integer.parseInt(sdfDay.format(today));

        /* calendar date now */
        Calendar now = new GregorianCalendar(intYear, intMonth, intDay);

        /* years duration */
        int yyyy = intYear - year;

        /* array of days in 12 months */
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        /*
         * an indicator if given date has already occurred. (-1) - if given date
         * is greater than the current date; 0 - if given date has not yet
         * occurred or if it's equal to the current date.
         */
        int factor = 0;

        int mm = 0; // month duration

        int dd = 0; // day duration

        /* determine if given date is greater than or equal to the current date */
        if ((month > intMonth) || (month == intMonth && day > intDay)) {
            factor = -1;
            yyyy += factor;
        }

        /* throw if any of the following exceptions occur */
        if (month > 12) {
            throw new Exception("Invalid input month");
        } else if (day > months[month]) {
            throw new Exception("Invalid input day");
        } else if (yyyy < 0) {
            throw new Exception("Invalid input date");
        }

        /*
         * if the given date has already passed or if it's equal to the current
         * date
         */
        if (factor == 0) {
            // compute for days in between the given and the current date
            dd = now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
            /* determine if the current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day of the current month
             */
            for (int i = month; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        } else { // if the given date is greater than the current date
            intYear--; // derive previous year
                /* set Calendar date for December 31 of the previous year */
            Calendar prev = new GregorianCalendar(intYear, 11, 31);
            /*
             * compute the days in between the given date last year and the
             * current date
             */
            dd = (prev.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) + now.get(Calendar.DAY_OF_YEAR);
            /* determine if previous year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day in December of the previous year
             */
            for (int i = month; i <= 11; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            intYear++; // set the value back to the current year
                /* determine if current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given day
             * in January of the current year and the current date
             */
            for (int i = 0; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        }

        //return diff;
        double mm2 = mm;
        double dd2 = mm;
        double x = yyyy;
        double y = mm2 / 12;
        double z = dd2 / 365;
        double tot = x + y + z;
        //System.err.println("duration two result=====" + tot);
        return tot;
    }

    public static double getDuration2(int month, int day, int year, String accountingP) throws Exception {
        DateFormat sdfmt = new SimpleDateFormat("yyyy-mm-dd");
        String budjetend = accountingP;
        //Date endDate = sdfmt.parse(budjetend);
        /* input calendar date */
        month--; // following the 0-based rule
        Calendar cal = new GregorianCalendar(year, month, day);

        /* date today */
        //java.util.Date today = endDate;

        /* year now */
//        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
//        int intYear = Integer.parseInt(sdfYear.format(today));
        int intYear = Integer.parseInt(budjetend.split("-")[0]);
        int intMonth = Integer.parseInt(budjetend.split("-")[1]);
        intMonth--; // following the 0-based rule
        int intDay = Integer.parseInt(budjetend.split("-")[2]);

//        /* month now */
//        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
//        int intMonth = Integer.parseInt(sdfMonth.format(today));
//        intMonth--; // following the 0-based rule
//
//        /* day now */
//        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
//        int intDay = Integer.parseInt(sdfDay.format(today));

        /* calendar date now */
        Calendar now = new GregorianCalendar(intYear, intMonth, intDay);

        /* years duration */
        int yyyy = intYear - year;

        /* array of days in 12 months */
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        /*
         * an indicator if given date has already occurred. (-1) - if given date
         * is greater than the current date; 0 - if given date has not yet
         * occurred or if it's equal to the current date.
         */
        int factor = 0;

        int mm = 0; // month duration

        int dd = 0; // day duration

        /* determine if given date is greater than or equal to the current date */
        if ((month > intMonth) || (month == intMonth && day > intDay)) {
            factor = -1;
            yyyy += factor;
        }

        /* throw if any of the following exceptions occur */
        if (month > 12) {
            throw new Exception("Invalid input month");
        } else if (day > months[month]) {
            throw new Exception("Invalid input day");
        } else if (yyyy < 0) {
            throw new Exception("Invalid input date");
        }

        /*
         * if the given date has already passed or if it's equal to the current
         * date
         */
        if (factor == 0) {
            // compute for days in between the given and the current date
            dd = now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
            /* determine if the current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day of the current month
             */
            for (int i = month; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        } else { // if the given date is greater than the current date
            intYear--; // derive previous year
                /* set Calendar date for December 31 of the previous year */
            Calendar prev = new GregorianCalendar(intYear, 11, 31);
            /*
             * compute the days in between the given date last year and the
             * current date
             */
            dd = (prev.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) + now.get(Calendar.DAY_OF_YEAR);
            /* determine if previous year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day in December of the previous year
             */
            for (int i = month; i <= 11; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            intYear++; // set the value back to the current year
                /* determine if current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given day
             * in January of the current year and the current date
             */
            for (int i = 0; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        }

        //return diff;
        double mm2 = mm;
        double dd2 = mm;
        double x = yyyy;
        double y = mm2 / 12;
        double z = dd2 / 365;
        double tot = x + y + z;
        //System.err.println("duration two result=====" + tot);
        return tot;
    }

    /**
     * Creates a new instance of StringDateManipulation
     */
    public StringDateManipulation() {
    }

    /**
     * This method calculates the 'year' difference between two date strings and
     * returns the number
     *
     * @param date1 is a String date (in yyyy-mm-dd )
     * @param date2 is a String date (in yyyy-mm-dd )
     * @see #differnceInMonths
     * @see #differenceInDays
     * @return returns an int value which is the difference between the years
     *
     */
    public static int differenceInYears(String date1, String date2) {
        String year1 = date1.substring(0, date1.indexOf('-'));
        String year2 = date2.substring(0, date2.indexOf('-'));
        int yearDiff = Integer.parseInt(year1) - Integer.parseInt(year2);

        return yearDiff;
    }

    /**
     *
     * @param _date
     * @return
     */
    public static String arrangeDateFormat(String _date) {
        String day;
        String month;
        String year;
        day = _date.substring(0, _date.indexOf('/'));
        month = _date.substring(_date.indexOf('/') + 1, _date.lastIndexOf('/'));
        year = _date.substring(_date.lastIndexOf('/') + 1);
        _date = year + "-" + month + "-" + day;
        return _date;
    }

    /**
     * This method clalusates the difference between two dates in months Unlike
     * differenceInMonths it considers the years as months _date1 and _date2
     * must be String type and in yyyy-MM-dd format
     *
     * @param _date1
     * @param _date2
     * @return returns (((_date1.year - _date2.year) * 12 ) + _date1.month) -
     * date2.month
     */
    public static int datesDifferenceInMonths(String _date1, String _date2) {
        return (((getYear(_date1) - getYear(_date2)) * 12) + getMonth(_date1)) - getMonth(_date2);
    }

    /**
     * This method clalusates the difference between two dates in days Unlike
     * differenceInMonths it considers the years as months _date1 and _date2
     * must be String type and in yyyy-MM-dd format
     *
     * @param _date1
     * @param _date2
     * @return returns (((_date1.year - _date2.year) * 12 ) + _date1.month) -
     * date2.month
     */
    public static int datesDifferenceInDays(String _date1, String _date2) {

        int yearDif = getYear(_date1) - getYear(_date2);
        int monthDif = (yearDif > 0) ? getMonth(_date1) - getMonth(_date2) : getMonth(_date2) - getMonth(_date1);
        int dayDif = (yearDif > 0) ? getDate(_date1) - getDate(_date2) : getDate(_date2) - getDate(_date1);
        int year = Math.abs(yearDif);
        int month = monthDif;
        int day = dayDif;
        if (day < 0) {
            day = 30 + day;
            month = month - 1;
        }
        if (month < 0) {
            month = 12 + month;
            year = year - 1;
        }
        if (year < 0) {
            if (monthDif < 0) {
                day = Math.abs(dayDif);
                month = Math.abs(monthDif);
            } else {
                month = 12 - month;
            }
            year = 0;
        }

        return year * 365 + month * 30 + day;
    }

    /**
     * Extracts the year value from _date
     *
     * @param _date a String date in yyyy-MM-dd format
     * @return returns the first for charachters as integer
     */
    public static int getYear(String _date) {
        return Integer.parseInt(_date.substring(0, _date.indexOf('-')));
    }

    /**
     * Extracts the month value from _date
     *
     * @param _date a string date in yyyy-MM-dd format
     * @return returns the MM from yyyy-MM-dd as integer
     */
    public static int getMonth(String _date) {
        return Integer.parseInt(_date.substring(_date.indexOf('-') + 1, _date.lastIndexOf('-')));
    }

    /**
     * Extracts the date value form _date
     *
     * @param _date a string date in yyyy-MM-dd format
     * @return returns the dd from yyyy-MM-dd as integer
     */
    public static int getDate(String _date) {
        return Integer.parseInt(_date.substring(_date.lastIndexOf('-') + 1));
    }

    /**
     * converts yyyy-mm-dd to <h1>monthNameInAmharic dd, yyyy</h1> to be easily
     * readable
     *
     * @param date the date in ethiopian calander
     */
    public static String convertDateToString(String date) {
        String dateName[] = {"መስከረ�?", "ጥቅ�?ት", "ህዳር", "ታህሳስ", "ጥር", "የካቲት", "መጋቢት", "ሚያ�?ያ", "�?ንቦት", "ሰኔ", "ሀ�?ሌ", "�?ሀሴ", "ጳጉሜ"};
        return dateName[getMonth(date) - 1] + " " + Integer.toString(getDate(date)) + ", " + Integer.toString(getYear(date));
    }

    /**
     * This method calculates the 'month' difference between two date strings
     * and returns +ve if date1 > date2 else returns -ve
     *
     * @param date1 is a String date (in yyyy-mm-dd )
     * @param date2 is a String date (in yyyy-mm-dd )
     * @see #differnceInYears
     * @see #differenceInDays
     * @return returns an int value which is the difference between the months
     *
     */
    public static int differenceInMonths(String date1, String date2) {
        String month1 = date1.substring(date1.indexOf('-') + 1, date1.lastIndexOf('-'));
        String month2 = date2.substring(date2.indexOf('-') + 1, date2.lastIndexOf('-'));
        int monthDiff = Integer.parseInt(month1) - Integer.parseInt(month2);

        return monthDiff;
    }

    /**
     * This method calculates the 'day' difference between two date strings and
     * returns the number
     *
     * @param date1 is a String date (in yyyy-mm-dd )
     * @param date2 is a String date (in yyyy-mm-dd )
     * @see #differnceInMonths , #differenceYears
     * @return returns an int value which is the difference between the days
     *
     */
    public static int differenceInDays(String date1, String date2) {
        String day1 = date1.substring(date1.lastIndexOf('-') + 1);
        String day2 = date2.substring(date2.lastIndexOf('-') + 1);
        int dayDiff = Integer.parseInt(day1) - Integer.parseInt(day2);

        return dayDiff;
    }

    /**
     * Computes for the next day in Ethiopian calendar
     *
     * @param _date the initial string date in yyyy-MM-dd format
     * @return returns the next day's date in yyyy-MM-dd format
     */
    public static String nextDayInEC(String _date) {
        String _day = String.valueOf(getDate(_date));
        String _month = String.valueOf(getMonth(_date));
        String _year = String.valueOf(getYear(_date));
        int day = getDate(_date);
        int month = getMonth(_date);
        day++;
        if (day <= 30) {

            _day = String.valueOf(day);
        } else if (day > 30) {
            _day = "1";
            month++;
            if (month > 12) {
                int year = Integer.parseInt(_year);
                year++; // next year
                _year = String.valueOf(year);
                month = 1;  // first month
            } else if (month < 10) {
                _month = "0" + String.valueOf(month);
            } else {
                _month = String.valueOf(month);
            }
        }
        if (_day.length() < 2) {
            _day = "0" + _day;
        }
        if (_month.length() < 2) {
            _month = "0" + _month;
        }

        // construct date
        String _nextDayDate = _year + "-" + _month + "-" + _day;

        return _nextDayDate;
    }

    /**
     * Computes for the next day in Ethiopian calendar
     *
     * @param _date the initial string date in yyyy-MM-dd format
     * @return returns the next day's date in yyyy-MM-dd format
     */
    public static String previousDayInEC(String _date) {
        String _day = String.valueOf(getDate(_date));
        String _month = String.valueOf(getMonth(_date));
        String _year = String.valueOf(getYear(_date));
        int day = getDate(_date);
        int month = getMonth(_date);
        day--;
        if (day >= 1) {
            if (day < 10) {
                _day = "0" + String.valueOf(day);
            } else {
                _day = String.valueOf(day);
            }
        } else if (day < 1) {
            _day = "30";
            month--;
            if (month < 1) {
                int year = Integer.parseInt(_year);
                year--; // next year
                _year = String.valueOf(year);
                month = 12;  // first month
            } else if (month > 1 && month < 10) {
                _month = "0" + String.valueOf(month);
            } else {
                _month = String.valueOf(month);
            }
        }
        //concat to the month if the month is between 1 and 10 inclusive
        if (month < 10) {
            _month = "0" + String.valueOf(month);
        } else {
            _month = String.valueOf(month);
        }
        // construct date
        String _previousDay = _year + "-" + _month + "-" + _day;

        return _previousDay;
    }

    /**
     * Computes for the next month in Ethiopian calendar (Eliminating Poagmea)
     *
     * @param _date the initial string date in yyyy-MM-dd format
     * @return returns the next month's date in yyyy-MM-dd format
     */
    public static String nextMonthInEC(String _date) {
        String _month = _date.substring(_date.indexOf('-') + 1,
                _date.lastIndexOf('-'));
        int month = Integer.parseInt(_month);
        String _year = _date.substring(0, _date.indexOf('-'));
        month++;    // increament the month
        if (month > 12) {
            int year = Integer.parseInt(_year);
            year++; // next year
            _year = String.valueOf(year);
            month = 1;  // first month
        }
        if (month < 10) {
            _month = "0" + String.valueOf(month);
        } else {
            _month = String.valueOf(month);
        }
        // construct date
        String _nextMonthsDate = _year + "-" + _month + "-"
                + _date.substring(_date.lastIndexOf('-') + 1);

        return _nextMonthsDate;
    }

    /**
     *
     * @param _date
     * @param spliter
     * @return
     */
    public static String nextMonthInEC(String _date, String spliter) {
        String _month = _date.substring(_date.indexOf(spliter) + 1,
                _date.lastIndexOf(spliter));
        int month = Integer.parseInt(_month);
        String _year = _date.substring(0, _date.indexOf(spliter));
        month++;    // increament the month
        if (month > 12) {
            int year = Integer.parseInt(_year);
            year++; // next year
            _year = String.valueOf(year);
            month = 1;  // first month
        }
        if (month < 10) {
            _month = "0" + String.valueOf(month);
        } else {
            _month = String.valueOf(month);
        }
        // construct date
        String _nextMonthsDate = _year + spliter + _month + spliter
                + _date.substring(_date.lastIndexOf(spliter) + 1);

        return _nextMonthsDate;
    }

    /**
     *
     * @param _date
     * @param spliter
     * @return
     */
    public static String nextMonthInECPuagume(String _date, String spliter) {

        int year = Integer.parseInt(_date.substring(6, 10));
        int month = Integer.parseInt(_date.substring(3, 5));
        int day = Integer.parseInt(_date.substring(0, 2));
        String _month;
        String _year;
        month++;
        if (month == 13 || month == 14) {
            month = 1;
            year++;
        }
        if (month < 10) {
            _month = "0" + String.valueOf(month);
        } else {
            _month = String.valueOf(month);
        }

        String _nextMonthsDate = day + spliter + _month + spliter
                + year;
        return _nextMonthsDate;
    }

    /**
     *
     * @param dateToSplit
     * @return
     */
    public static String returnYearAndMonth(String dateToSplit) {
        try {
            String dates[] = dateToSplit.split("/");
            return dates[1] + "/" + dates[2];

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

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
        long noOfDaysFromStart = java.sql.Date.valueOf(todayInGC).getTime() - java.sql.Date.valueOf("2006-09-11").getTime();
//        System.out.print("Number of years from start" + noOfDaysFromStart);

        noOfDaysFromStart /= (1000 * 60 * 60 * 24); //   number of days

        //Move years
//        noYear = noOfDaysFromStart / 365;   // number of years in the gap
        noYear = (4 * noOfDaysFromStart) / ((4 * 365) + 1);   // number of years in the gap

        currentYear += noYear;

        noOfDaysFromStart %= 365;

        if (noOfDaysFromStart < (noYear / 4)) {
            currentYear--;
            noOfDaysFromStart += 365;
        }

        noOfDaysFromStart -= (noYear / 4);

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
    public static String toDayInEc() {
//        System.out.println("to Day In GC "+currentDateInGC());
//        System.out.println("to Day In EC "+todayInEC(currentDateInGC()));
        return todayInEC(currentDateInGC());
    }

    /**
     * compute and return the closest integer numeber
     *
     * @param doubleNum <code>double</code> number
     * @return an integer number close to doubleNum
     */
    public static int getIntPart(double doubleNum) {
        int intPart = 0;
        if (doubleNum < -0.0000001) {
            intPart = (int) Math.ceil(doubleNum - 0.0000001);
        } else {
            intPart = (int) Math.floor(doubleNum + 0.0000001);
        }
        return intPart;
    }

    /**
     * returns name of the week
     *
     * @param date
     * @return returns Week Name
     */
    public static String getWeekDayName(String date) {
        String[] weekdays = {"ማክሰኞ", "ረብዑ", "�?ሙስ", "አርብ", "ቅዳሜ", "እ�?ድ", " ሰኞ"};
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
        //here date has first to be converted to dateInGC, first create a method to do this task
        //date=dateInGC(date);
        calendar.set(getYear(date), getMonth(date), getDate(date));
        String dateName = weekdays[calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1];
        return dateName;
    }

    /**
     * Change the given date in gregorian calendar to Hijri Calender date, or
     * Islamic Calender. (Note: Hijri Calender is a lunar calendar based on 12
     * lunar months in a year of 354 or 355 days.)
     *
     * @param date the date in gregorian date
     * @return returns the gregorian date in hijri with 'yyyy-MM-dd' format
     *
     */
    public static String todayInHC(String date) {
//        String dateString = null;
//        int thisYearGC = getYear(today);
//        int thisYearInHC = ((int) ((thisYearGC - 622) / 0.97)) + 1;
        int _date = StringDateManipulation.getDate(date);
        int _month = StringDateManipulation.getMonth(date);
        int _year = StringDateManipulation.getYear(date);
        int jd = 0;//Julian days
        if ((_year > 1582) || ((_year == 1582) && (_month > 10)) || ((_year == 1582) && (_month == 10) && (_date > 14))) {
            jd = getIntPart((1461 * (_year + 4800 + getIntPart((_month - 14) / 12))) / 4) + getIntPart((367 * (_month - 2 - 12 * (getIntPart((_month - 14) / 12)))) / 12)
                    - getIntPart((3 * (getIntPart((_year + 4900 + getIntPart((_month - 14) / 12)) / 100))) / 4) + _date - 32075;
        } else {
            jd = 367 * _year - getIntPart((7 * (_year + 5001 + getIntPart((_month - 9) / 7))) / 4) + getIntPart((275 * _month) / 9) + _date + 1729777;
        }
        int l = jd - 1948440 + 10632;
        int n = getIntPart((l - 1) / 10631);
        l = l - 10631 * n + 354;
        int j = (getIntPart((10985 - l) / 5316)) * (getIntPart((50 * l) / 17719)) + (getIntPart(l / 5670)) * (getIntPart((43 * l) / 15238));
        l = l - (getIntPart((30 - j) / 15)) * (getIntPart((17719 * j) / 50)) - (getIntPart(j / 16)) * (getIntPart((15238 * j) / 43)) + 29;
        _month = getIntPart((24 * l) / 709);
        _date = l - getIntPart((709 * _month) / 24);
        _year = 30 * n + j - 30;
        date = String.valueOf(_year) + "-" + String.valueOf(_month) + "-" + String.valueOf(_date);
        return date;
    }

    /**
     * Changes the current date in GC to String format yyyy-MM-dd
     *
     * @return returns the java.util.Date() as yyyy-MM-dd
     */
    public static String convertTodayInGCtoHC() {
        return todayInHC(currentDateInGC());
    }

    /**
     * Changes the current date in GC to String format yyyy-MM-dd
     *
     * @return returns the java.util.Date() as yyyy-MM-dd
     */
    public static String convertTodayInECtoHC() {
        return todayInHC(toDayInEc());
    }

    /**
     * This method compares _date with today, the method just calls
     * compareDate(todayInEc(), _date)
     *
     * @param _date is a String date (in yyyy-mm-dd )
     * @see #compareDate
     * @see #todayInEC
     * @return returns compareDate(todayInEc(), _date)
     *
     */
    public static int compareWithToday(String _date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("ooooooooooooooooooooo" + compareDate(todayInEC(format.format(new java.util.Date())), _date));
        return compareDate(todayInEC(format.format(new java.util.Date())), _date);
        
    }

    /**
     * extracts the date value from _date (Note : _date must be in 'yyyy-MM-dd'
     * format)
     *
     * @param _date the date from witch the date is to be extracted
     * @return returns an integer representative of the date
     */
    public static int extractDate(String _date) {
        return Integer.parseInt(_date.substring(_date.indexOf('-') + 1));
    }

    /**
     * extracts the year value from _date (Note : _date must be in 'yyyy-MM-dd'
     * format)
     *
     * @param _date the date from wich the year is to be extracted
     * @return returns an integer representative of the year
     */
    public static int extractYear(String _date) {
        return Integer.parseInt(_date.substring(0, _date.indexOf('-')));
    }

    /**
     * extracts the month value from _date (Note : _date must be in 'yyyy-MM-dd'
     * format)
     *
     * @param _date the date from wich the month is to be extracted
     * @return returns an integer representative of the month
     */
    public static int extractMonth(String _date) {
        return Integer.parseInt(_date.substring(_date.indexOf('-') + 1, _date.lastIndexOf('-')));
    }

    /**
     * This method compares two date strings
     *
     * @param date1 is a String date (in yyyy-mm-dd )
     * @param date2 is a String date (in yyyy-mm-dd )
     * @see #differnceInMonths , #differenceYears , #differenceInDays
     * @return returns 1 if date1 &gt; date2, -1 if date1 &lt; date2 and/or 0 if
     * date1 == date2
     *
     */
    public static int compareDate(String date1, String date2) {
        int flag = 0;
        if (differenceInYears(date1, date2) > 0) {
            flag = 1;
        } else if (differenceInYears(date1, date2) < 0) {
            flag = -1;
        } else if (differenceInYears(date1, date2) == 0) {

            if (differenceInMonths(date1, date2) > 0) {
                flag = 1;
            } else if (differenceInMonths(date1, date2) < 0) {
                flag = -1;
            } else if (differenceInMonths(date1, date2) == 0) {

                if (differenceInDays(date1, date2) > 0) {
                    flag = 1;
                } else if (differenceInDays(date1, date2) < 0) {
                    flag = -1;
                } else if (differenceInDays(date1, date2) == 0) {
                    flag = 0;
                }
            }
        }

        return flag;
    }

    /**
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDateDifference(String date1, String date2) {
        try {
            int yearDiff = differenceInYears(date2, date1);
            int monthDiff = differenceInMonths(date2, date1);
            int dayDiff = differenceInDays(date2, date1);
            int yearDiffInDays = 0;
            int monthDiffInDays = 0;
            int i = 0;
            int year = Integer.parseInt(date2.split("-")[0]);
            int preYear = Integer.parseInt(date1.split("-")[0]);
            int month = Integer.parseInt(date2.split("-")[1]);
            int day = Integer.parseInt(date2.split("-")[2]);
            for (int j = preYear; j < year; j++) {
                if ((j % 4 == 0)) {
                    i = i + 1;
                }
            }
            if (month == 13 && day == 6) {
                i = i + 1;
            }
            yearDiffInDays = (yearDiff * 365) + i;
            monthDiffInDays = monthDiff * 30;
            int totalDiffInDays = yearDiffInDays + monthDiffInDays + dayDiff;
            return totalDiffInDays;
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return 0;
    }

    /**
     * The method reads accepts two dates and calculates the year difference
     * between the two dates. It may be used to calculate the age if
     * date1=birth_date and date2=today date2>date1
     */
    public static int calculateYearDifferenceBetweenTwoDates(String date1, String date2) {
        int dateDifference = datesDifferenceInDays(date1, date2);
        return dateDifference / 365;
    }

    public static String calculateYearAndMonthDifference(String date1, String date2) {
        int difference = datesDifferenceInMonths(date1, date2);
        return calculateYearAndMonthDifference(difference);
    }

    public static String calculateYearAndMonthDifference(int difference) {
        int year = difference / 12;
        int month = difference % 12;
        String years = (year == 1) ? year + " year" : (year > 0) ? year + " years" : "";
        String months = (month == 1) ? month + " month" : (month > 0) ? month + " months" : "";
        String experiance = (year > 0 && month > 0) ? years + " and " + months : (year > 0) ? years : months;
        return experiance;
    }

    public static String calculateYearAndMonthDifferenceRound(int difference) {
        int year = difference / 12;
        int month = difference % 12;
        int date = (difference % 365) % 30;
        month = (date >= 15) ? month + 1 : month;
        String years = (year == 1) ? year + " year" : (year > 0) ? year + " years" : "";
        String months = (month == 1) ? month + " month" : (month > 0) ? month + " months" : "";
        String experiance = (year > 0 && month > 0) ? years + " and " + months : (year > 0) ? years : months;
        return experiance;
    }

    public static String calculateYearAndMonthAndDateDifference(String date1, String date2) {
        int difference = datesDifferenceInDays(date1, date2);
        return calculateYearAndMonthAndDateDifference(difference);
    }

    public static String calculateYearAndMonthAndDateDifference(int difference) {
        if (difference > 0) {
            int year = (difference / 365);
            int month = (difference % 365) / 30;
            int date = (difference % 365) % 30;
            String years = (year == 1) ? year + " Year" : (year > 0) ? year + " Years " : "";
            String months = (month == 1) ? month + " Month" : (month > 0) ? month + " Months " : "";
            String dates = (date == 1) ? date + " Day " : (date > 0) ? date + " Days " : "";
            String separatorM = (year > 0 && month > 0) ? " , " : "";
            String separatord = ((year > 0 || month > 0) && date > 0) ? " , " : "";
            String experiance = years + separatorM + months + separatord + dates;
            return experiance;
        } else {
            return String.valueOf(difference);
        }
    }

    public static int calculateYearOfExpereance(int difference) {
        if (difference > 0) {
            int year = (difference / 365);

            return year;
        } else {
            return difference;
        }
    }

    public static int getGCBudgetYear() {
        int year = StringDateManipulation.getYear(currentDateInGC());
        try {
            Date toDay = new SimpleDateFormat("yyyy-MM-dd").parse(currentDateInGC());
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-07-01");
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse((year + 1) + "-06-30");
            if (toDay.after(startDate) && toDay.before(endDate)) {
                year += 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return year;
    }

    public void calendarComparison() {
        GregorianCalendar date1 = new GregorianCalendar();
        GregorianCalendar date2 = new GregorianCalendar();
        date1.set(2001, 10, 10);
        date2.set(2003, 10, 6);
        int diff = date2.get(Calendar.MONTH) - date1.get(Calendar.MONTH);
    }
    public static long currentYear, currentMonth, currentDay, currentDate;
    static long year1999 = 1999, year1999_1stDate = 1, year1999_1stMonth = 1, year1999_1stDay = 1;
    //String date;

    public static int compareYearAndMonth(String date1, String date2) {
        int flag = 0;
        if (differenceInYears(date1, date2) > 0) {
            flag = 1;
        } else if (differenceInYears(date1, date2) < 0) {
            flag = -1;
        } else if (differenceInYears(date1, date2) == 0) {

            if (differenceInMonths(date1, date2) > 0) {
                flag = 1;
            } else if (differenceInMonths(date1, date2) < 0) {
                flag = -1;
            }
        }

        return flag;
    }
    static String[] monthNames = {"January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"};

    public static String getMonthName(int month) {
        return monthNames[month];
        
     
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        
    
        
        System.err.println(" you age is========"+getDuration(1, 1, 2000, "2017-06-30"));
    }

    public static Date getParseStringToDate(String dateVale) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse(dateVale);
        return date;
    }

    public static Date getParseStringToDate2(String dateVale) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date = format.parse(dateVale);
        return date;
    }

    public static Date getParseStringToDt(String dateVale) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateVale);
        return date;
    }
    public static int getDuration(int month, int day, int year, String accountingPeriod) throws Exception {
        DateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
        String endBudjet = accountingPeriod;
        //Date endDate = sdfm.parse(budjetend);
        // sdfm.format(endDate);
        /* input calendar date */
        month--; // following the 0-based rule
        Calendar cal = new GregorianCalendar(year, month, day);

        /* date endDate */
        //java.util.Date today = endDate;

        /* year now */
//        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
//        int intYear = Integer.parseInt(sdfYear.format(today));
//
//      
//        
//        /* month now */
//        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
//        int intMonth = Integer.parseInt(sdfMonth.format(today));
//        intMonth--; // following the 0-based rule
//
//        /* day now */
//        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
//        int intDay = Integer.parseInt(sdfDay.format(today));
        int intYear = Integer.parseInt(endBudjet.split("-")[0]);
        int intMonth = Integer.parseInt(endBudjet.split("-")[1]);
        intMonth--; // following the 0-based rule
        int intDay = Integer.parseInt(endBudjet.split("-")[2]);

        /* calendar date now */
        Calendar now = new GregorianCalendar(intYear, intMonth, intDay);

        /* years duration */
        int yyyy = intYear - year;

        /* array of days in 12 months */
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        /*
         * an indicator if given date has already occurred. (-1) - if given date
         * is greater than the current date; 0 - if given date has not yet
         * occurred or if it's equal to the current date.
         */
        int factor = 0;

        int mm = 0; // month duration

        int dd = 0; // day duration

        /* determine if given date is greater than or equal to the current date */
        if ((month > intMonth) || (month == intMonth && day > intDay)) {
            factor = -1;
            yyyy += factor;
        }

        /* throw if any of the following exceptions occur */
        if (month > 12) {
            throw new Exception("Invalid input month");
        } else if (day > months[month]) {
            throw new Exception("Invalid input day");
        } else if (yyyy < 0) {
            throw new Exception("Invalid input date");
        }

        /*
         * if the given date has already passed or if it's equal to the current
         * date
         */
        if (factor == 0) {
            // compute for days in between the given and the current date
            dd = now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
            /* determine if the current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day of the current month
             */
            for (int i = month; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        } else { // if the given date is greater than the current date
            intYear--; // derive previous year
                /* set Calendar date for December 31 of the previous year */
            Calendar prev = new GregorianCalendar(intYear, 11, 31);
            /*
             * compute the days in between the given date last year and the
             * current date
             */
            dd = (prev.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR)) + now.get(Calendar.DAY_OF_YEAR);
            /* determine if previous year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given
             * date and the given day in December of the previous year
             */
            for (int i = month; i <= 11; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            intYear++; // set the value back to the current year
                /* determine if current year is a leap year */
            if ((intYear % 4) == 0) {
                months[1]++; // increment the days in February by 1
            }
            /*
             * compute for day duration and month duration between the given day
             * in January of the current year and the current date
             */
            for (int i = 0; i <= intMonth; i++) {
                if (dd >= months[i]) {
                    dd -= months[i];
                    mm++;
                }
            }
            /*
             * if computed month duration is more than 12, finalize values for
             * year and month duration
             */
            if (mm >= 12) {
                yyyy += (mm / 12);
                mm %= 12;
            }
        }

        /* computed duration in years, months and days */
        //System.err.println(yyyy + " Year(s), " + mm + " Month(s), and " + dd + " Day(s) ");
        String diff = yyyy + " Year(s), " + mm + " Month(s), and " + dd + " Day(s) ";
        //System.err.println("duartion one result====" + yyyy);
        //return diff;

        return yyyy;
    }
   
}
