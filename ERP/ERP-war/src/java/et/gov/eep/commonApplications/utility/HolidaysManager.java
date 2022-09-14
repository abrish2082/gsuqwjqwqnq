/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.joda.time.*;
import org.joda.time.chrono.*;

/**
 *
 * @author Alula
 */
public class HolidaysManager {

    /**
     *
     */
    public HolidaysManager() {
    }
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DecimalFormat decimalFormat = new DecimalFormat("00");
    /**
     *the year before the birth of Jesus Christ.
     */
    public static long yearBC = 5500;
    /**
     *AtsfeAwrha is the 2 days remained when 30 days of the month are divided by 7
     */
    public static int AtsfeAwrha = 2;

    /**
     * AmeteAlem(year of creation) is the sum of the number of years before birth
     * of Jesus Christ and after the birth of Christ.
     * @param date
     * @return the year of creation
     */
    public static int getYearOfCreation(String date) {
        int currentYear = StringDateManipulation.getYear(date);
        int yearOfCreation;

        yearOfCreation = (int) (yearBC + currentYear);
        return yearOfCreation;
    }

    /**
     * Wengelawi means writers of gospel. The years are named after the four
     * apostles, namely Mathewos (Mathew), Markos (Mark), Lukas (Luke) and Yohannes (John).
     * To get the Wengelawi divide AmeteAlem by four and if the remainder is:
     * 1, then it is Mathewos. 2, then it is Markos
     * 3, then it is Lukas 0, then it is Yohannes
     * @param date
     * @return an integer indicating the wengelawi
     */
    public static int getWengelawi(String date) {
        return getYearOfCreation(date) % 4;
    }

    public static int getMetaneRabit(String date) {
        return getYearOfCreation(date) / 4;
    }

    /**
     * To know the date the new year starts, add AmeteAlem(year of creation) and
     * Metene Rabiet then divide by 7.The remainder is given the name Tinte Qemer.
     * Tinte Qemer is the first Tuesday that numbers begun.
     * @param date
     * @return the day of the new year
     */
    public static String getNewYearDay(String date) {
        int day = (int) ((getYearOfCreation(date) + getMetaneRabit(date)) % 7);
        switch (day) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
        }
        return null;
    }

    /**
     * Medeb is obtained from the remainder of AmeteAlem(year of creation)
     * divided by 19(Nuskemer).
     * @param date
     * @return
     */
    public static int getMedeb(String date) {
        return getYearOfCreation(date) % 19;
    }

    /**
     * Wenber is obtained by subtracting 1 from Medeb and is useful for finding
     * Metqi and Abektie which are used in calculating fasting dates and holidays.
     * @param date
     * @return
     */
    public static int getWenber(String date) {
        return getMedeb(date) - 1;
    }

    /**
     * Metqi is used to obtain fasting dates and holidays of the
     * Ethiopian Orthodox Tewahido Church.
     * Metqi is obtained by multiplying Wenber by 19 and taking the remainder
     * of the quotient in dividing the product by 30.If the product is less than
     * 30 the product itself will be the Metqi of the year.
     * @param date
     * @return
     */
    public static int getMetqi(String date) {
        if ((getWenber(date) * 19) < 30) {
            return Math.abs(getWenber(date) * 19);
        } else {
            return Math.abs(getWenber(date) * 19) % 30;
        }
    }

    /**
     * Multiplying Wenber by 11 and if the product is greater than 30 ,
     * it is then divided by 30.The remainder in the division is called Abekte.
     * @param date
     * @return
     */
    public static int getAbektie(String date) {
        if ((getWenber(date) * 11) < 30) {
            return (getWenber(date) * 11);
        } else {
            return (getWenber(date) * 11) % 30;
        }
    }
    static String Neneweh = null;

    /**
     *
     * @return
     */
    public static String getNeneweh() {
        return Neneweh;
    }

    /**
     *
     * @param Neneweh
     */
    public static void setNeneweh(String Neneweh) {
        HolidaysManager.Neneweh = Neneweh;
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getBaeleMetqi(String date) {
        int metqui = getMetqi(date);
        if (metqui > 14) {
            setNeneweh("05");//Neneweh is in Tire
            return "01-" + decimalFormat.format(metqui); //In Meskerem
        } else {//if (getMetqi(date) < 14) 
            setNeneweh("06");//Neneweh is in Yekatit
            return "02-" + decimalFormat.format(metqui);  // In Tikimt
        }

    }

    /**
     *
     * @param today
     * @return
     */
    public static int getTewsacOfTheDay(String today) {
        if (today.equals("Sunday")) {
            return 7;
        } else if (today.equalsIgnoreCase("Monday")) {
            return 6;
        } else if (today.equalsIgnoreCase("Tuesday")) {
            return 5;
        } else if (today.equalsIgnoreCase("Wednesday")) {
            return 4;
        } else if (today.equalsIgnoreCase("Thursday")) {
            return 3;
        } else if (today.equalsIgnoreCase("Friday")) {
            return 2;
        } else if (today.equalsIgnoreCase("Saturday")) {
            return 8;
        } else {
            return 0; //No tewsac found
        } //end of if
    }

    /**
     * Mebaja Hamer is obtained by adding Metqi and Tewsak of the day of
     * Beale Metqi. If the result is greater than 30 as usual the remainder is taken.
     * @param date
     * @return intiger indicating Mebaja Hamer
     */
    public static int getMebajaHamer(String date) {
//        Neneweh = String.valueOf(getMetqi(date) + getTewsacOfTheDay(getBaeleMetqi(date)));
        return getMetqi(date) + getTewsacOfTheDay(getDay(String.valueOf(StringDateManipulation.getYear(date)) + "-" + getBaeleMetqi(date)));
    }

    /**
     * TinteOn is the first Wednesday in which Sun, Moon and Stars were created.
     * Add AmeteAlem & Metene Rabiet and divide the sum by 7.
     * Then remainder minus one gives TinteOn.
     * @param date
     * @return intiger indicating TinteOn
     */
    public static int getTinteOn(String date) {
        return (int) (((getYearOfCreation(date) + getMetaneRabit(date)) % 7) - 1);
    }

    /**
     * The method returns the day of the week from the given date
     * @param date the given date
     * @return day the day of the week
     */
    public static String getDay(String date) {
        int day = (getTinteOn(date) +
                AtsfeAwrha * (StringDateManipulation.getMonth(date) - 1) +
                StringDateManipulation.getDate(date) + 2) % 7;
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 0:
                return "Saturday";
        }
        return null;
    }

    /**
     *
     * @param date
     * @return
     */
    public static int getDayFromNenewe(String date) {
        getBaeleMetqi(date);
        String nenewe = String.valueOf(StringDateManipulation.getYear(date)) + "-" + Neneweh + "-" + String.valueOf(getMebajaHamer(date));
        return StringDateManipulation.compareDateDifference(nenewe, date);
    }

    /**
     * Tewsak of fasting dates and holydays are the number of days from the next
     * day of Neneveh up to the date of that fasting(entrance date) or holiday.
     * @param date the given date
     * @return the number of days(the date difference) from nenewe to the given date.
     */
    public static int getTewsac(String date) {
        return getDayFromNenewe(date) % 30;
    }

    /**
     *
     * @param date
     * @return
     */
    public static String getDateofNenewe(int year) {
        getBaeleMetqi(String.valueOf(year + "-01-01"));
        int mebajaHamer = getMebajaHamer(String.valueOf(year + "-01-01"));
        mebajaHamer = (mebajaHamer > 30) ? mebajaHamer % 30 : mebajaHamer;
        return String.valueOf(year) + "-" + Neneweh + "-" + decimalFormat.format(mebajaHamer);
    }

    public static String getDateOfGena(int year) {
        int wengelawi = getWengelawi(year + "-01-01");
        return (wengelawi != 0) ? year + "-04-29" : year + "-04-28";
    }

    public static String getDateOfGoodFriday(int year) {
        String dateOfNenewe = GregorianCalendarManipulation.ethiopianToGregorian(getDateofNenewe(year));
        Calendar Cal_dateOfNenewe = GregorianCalendarManipulation.convertDateToCalendar(dateOfNenewe);
        Cal_dateOfNenewe.add(Calendar.DATE, 67);
        String goodFriday = GregorianCalendarManipulation.convertCalendarToString(Cal_dateOfNenewe);
        return GregorianCalendarManipulation.gregorianToEthiopian(goodFriday);
    }

    public static String getDateOfTinsaye(int year) {
        String dateOfNenewe = GregorianCalendarManipulation.ethiopianToGregorian(getDateofNenewe(year));
        Calendar Cal_dateOfNenewe = GregorianCalendarManipulation.convertDateToCalendar(dateOfNenewe);
        Cal_dateOfNenewe.add(Calendar.DATE, 69);
        String tinsaye = GregorianCalendarManipulation.convertCalendarToString(Cal_dateOfNenewe);
        return GregorianCalendarManipulation.gregorianToEthiopian(tinsaye);
    }

    public static String getDateOfEidAlFitr(int year) {//Ramodan
        DateTime startDate = new DateTime(year, 6, 30, 0, 0, 0, 0);
        DateTime endDate = new DateTime(year + 1, 7, 1, 0, 0, 0, 0);
        DateTime startDTtIslamic = startDate.withChronology(IslamicChronology.getInstance());
        DateTime endDTtIslamic = endDate.withChronology(IslamicChronology.getInstance());
        int hMonth = startDTtIslamic.getMonthOfYear();
        int hDate = startDTtIslamic.getDayOfYear();
        int hyear;
        if (hMonth < 10) {
            hyear = startDTtIslamic.getYear();
        } else if (hMonth == 10 && hDate == 1) {
            hyear = startDTtIslamic.getYear();
        } else {
            hyear = endDTtIslamic.getYear();
        }
        DateTime eidAlfitir = new DateTime(hyear, 10, 1, 0, 0, 0, 0, IslamicChronology.getInstance());
        return dateFormat.format(eidAlfitir.toDate());
    }

    public static String getDateOfEidAlAdha(int year) {//Arafa
        DateTime startDate = new DateTime(year - 1, 6, 30, 0, 0, 0, 0);
        DateTime endDate = new DateTime(year, 7, 1, 0, 0, 0, 0);
        DateTime startDTtIslamic = startDate.withChronology(IslamicChronology.getInstance());
        DateTime endDTtIslamic = endDate.withChronology(IslamicChronology.getInstance());
        int hMonth = startDTtIslamic.getMonthOfYear();
        int hDate = startDTtIslamic.getDayOfYear();
        int hyear;
        if (hMonth < 12) {
            hyear = startDTtIslamic.getYear();
        } else if (hMonth == 12 && hDate <= 10) {
            hyear = startDTtIslamic.getYear();
        } else {
            hyear = endDTtIslamic.getYear();
        }
        DateTime eidAlAdha = new DateTime(hyear, 12, 10, 0, 0, 0, 0, IslamicChronology.getInstance());
        return dateFormat.format(eidAlAdha.toDate());
    }

    public static void main(String arg[]) {
        System.out.println(getDateOfEidAlAdha(2018));
        getDateOfEidAlAdha(2014);
    }

    public static String getDateOfMawlid(int year) {//Arafa
        DateTime startDate = new DateTime(year, 6, 30, 0, 0, 0, 0);
        DateTime endDate = new DateTime(year + 1, 7, 1, 0, 0, 0, 0);
        DateTime startDTtIslamic = startDate.withChronology(IslamicChronology.getInstance());
        DateTime endDTtIslamic = endDate.withChronology(IslamicChronology.getInstance());
        int hMonth = startDTtIslamic.getMonthOfYear();
        int hDate = startDTtIslamic.getDayOfYear();
        int hyear;
        if (hMonth < 3) {
            hyear = startDTtIslamic.getYear();
        } else if (hMonth == 3 && hDate <= 12) {
            hyear = startDTtIslamic.getYear();
        } else {
            hyear = endDTtIslamic.getYear();
        }
        DateTime mawlid = new DateTime(hyear, 3, 12, 0, 0, 0, 0, IslamicChronology.getInstance());
        return dateFormat.format(mawlid.toDate());
    }

    /**
     * The method identifies the christian holidays in a geiven day.
     * If there is any. Checks Ethiopian calender
     * @param date the given date
     * @return the holiday, or 0 if there is no any holiday.
     */
    public static String getChristianHolidays(String date) {
        int _date = StringDateManipulation.getDate(date);
        int _month = StringDateManipulation.getMonth(date);
        int hDate = StringDateManipulation.getDate(gregorianToHijri(date)); //the day in hijri
        int hMonth = StringDateManipulation.getMonth(gregorianToHijri(date)); //the month in hijri
        if (_date == 1 && _month == 1) {
            return "New Year";
        } else if (getTewsac(date) == 7) {
            return "GoodFriday";
        } else if (getTewsac(date) == 9) {
            return "Tinsaye";
        } else if (_date == 17 && _month == 1) {
            return "Meskel";
        } else if (getWengelawi(date) == 0 && _date == 28 && _month == 4) {
            return "Gena";
        } else if (getWengelawi(date) != 0 && _date == 29 && _month == 4) {
            return "Gena";
        } else if (_date == 11 && _month == 5) {
            return "Timiket";
        } else if (_date == 23 && _month == 6) {
            return "Adawa";
        } else if (_date == 23 && _month == 8) {
            return "Labour Day";
        } else if (_date == 27 && _month == 8) {
            return "Arbegnoch";
        } else if (_date == 20 && _month == 9) {
            return "G20";
        }
        return "0"; //no holiday
    }

    /**
     * date in gregorian calander
     * @param date
     * @return
     */
    public static int getStaticHolydays(String date) {

        String dateInEC = GregorianCalendarManipulation.gregorianToEthiopian(date);
        int _date = StringDateManipulation.getDate(dateInEC);
        int _month = StringDateManipulation.getMonth(dateInEC);
        if (_date == 1 && _month == 1) {
            return 1;
        } else if (_date == 17 && _month == 1) {
            return 1;
        } else if (_date == 11 && _month == 5) {
            return 1;
        } else if (_date == 23 && _month == 6) {
            return 1;
        } else if ((_date == 23 && _month == 8) || (_date == 27 && _month == 8)) {
            return 2;
        } else if (_date == 20 && _month == 9) {
            return 1;
        }
        return 0; //no holiday
    }public static int returnNumberofHolydaysInAMonth(String date) {

        String dateInEC = GregorianCalendarManipulation.gregorianToEthiopian(date);
        int _date = StringDateManipulation.getDate(dateInEC);
        int _month = StringDateManipulation.getMonth(dateInEC);
        if (_date == 1 && _month == 1) {
            return 1;
        } else if ( _month == 1) {
            return 1;
        } else if ( _month == 5) {
            return 1;
        } else if (_month == 6) {
            return 1;
        } else if (( _month == 8) || (_month == 8)) {
            return 2;
        } else if ( _month == 9) {
            return 1;
        }
        return 0; //no holiday
    }




    public static String getHoliydayNames(String date) {
        int _date = StringDateManipulation.getDate(date);
        int _month = StringDateManipulation.getMonth(date);
        int hDate = StringDateManipulation.getDate(gregorianToHijri(date)); //the day in hijri
        int hMonth = StringDateManipulation.getMonth(gregorianToHijri(date)); //the month in hijri
        if (_date == 1 && _month == 1) {
            return "New Year";

        } else if (_date == 17 && _month == 1) {
            return "Meskel";
        } else if (_date == 11 && _month == 5) {
            return "Timiket";
        } else if (_date == 23 && _month == 6) {
            return "Adawa";
        } else if (_date == 23 && _month == 8) {
            return "Labour Day";
        } else if (_date == 27 && _month == 8) {
            return "Arbegnoch";
        } else if (_date == 20 && _month == 9) {
            return "G20";
        }
        return "0"; //no holiday
    }

    /**
     * The method identifies the muslim holidays in a geiven date.
     * If there is any.Checks the gregorian date
     * @param date
     * @return the muslim holiday
     */
    public static String getIslamicHolidays(String date) {//the date must be converted into gregorian
        int hDate = StringDateManipulation.getDate(gregorianToHijri(date)); //the day in hijri
        int hMonth = StringDateManipulation.getMonth(gregorianToHijri(date)); //the month in hijri
        if (hDate == 1 && hMonth == 10) { // 1 Shawwal, the 10th month in hijri calander
            return "1";//Eid al-Fitr
        } else if (hDate == 10 && hMonth == 12) {//10 Dhu al-Hijjah, the 12th month in hijri calander
            return "2";//Eid al-Adha
        } else if (hDate == 12 && hMonth == 3) {//12 Rabi al Awal, the 3rd month in hijri calander
            return "3";//Mawlid an Nabi
        }
        return "0";//No holiday
    }

    /**
     * The method calculates the number of weekends in a given range of days
     * @param start_date the begining if the range
     * @param end_date
     * @return number of weekends in <code>int</code>.
     */
    public static int getWeekEnds(String start_date, String end_date) {
        String date = start_date;
        int i = 0;
        int diffInDates = StringDateManipulation.compareDateDifference(start_date, end_date);
        if (getDay(date).equalsIgnoreCase("Sunday") || getDay(date).equalsIgnoreCase("Saturday")) {
            i++;
        }
        for (int j = 0; j < diffInDates; j++) {
            date = StringDateManipulation.nextDayInEC(date);
            if (getDay(date).equals("Sunday") || getDay(date).equals("GoodFriday")) {
                i++;
            }
        }
        return i;
    }

    /**
     * The method calculates the working days in a range of given dates. Note
     * the mothed subtracts holydays, and weekends from the date difference.
     * @param start_date the begining if the range
     * @param end_Date the last date of the range
     * @return total working days in <code>int</code>.
     */
    public static int getWorkingDays(String start_date, String end_Date) {
        String date = start_date;
        int diffInDates = StringDateManipulation.compareDateDifference(start_date, end_Date);
        if (getWeekEnds(start_date, end_Date) != 0) {
            diffInDates = diffInDates - getWeekEnds(start_date, end_Date);
        }
        for (int j = 0; j < StringDateManipulation.compareDateDifference(start_date, end_Date); j++) {
            if (!getChristianHolidays(date).equalsIgnoreCase("0") ||
                    !getIslamicHolidays(date).equalsIgnoreCase("0")) {
                diffInDates--;
                date = StringDateManipulation.nextDayInEC(date);
            } else if (getChristianHolidays(date).equals("0")) {
                date = StringDateManipulation.nextDayInEC(date);
            }
        }
        return diffInDates;
    }

    /**
     * compute and return the closest integer numeber
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
     * Converts gregorian date into a hijti date
     * @param date the date in gregorian date
     * @return a hijri date
     */
    public static String gregorianToHijri(String date) {
        date = StringDateManipulation.todayInEC(date);
        int _date = StringDateManipulation.getDate(date);
        int _month = StringDateManipulation.getMonth(date);
        int _year = StringDateManipulation.getYear(date);
        int jd = 0;//Julian days
        if ((_year > 1582) || ((_year == 1582) && (_month > 10)) || ((_year == 1582) && (_month == 10) && (_date > 14))) {
            jd = getIntPart((1461 * (_year + 4800 + getIntPart((_month - 14) / 12))) / 4) + getIntPart((367 * (_month - 2 - 12 * (getIntPart((_month - 14) / 12)))) / 12) -
                    getIntPart((3 * (getIntPart((_year + 4900 + getIntPart((_month - 14) / 12)) / 100))) / 4) + _date - 32075;

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

    private static String gregorianToHijri() {
        return gregorianToHijri(StringDateManipulation.currentDateInGC());
    }

    /**
     * return number of days in a month
     * @param year
     * @param month
     * @return
     */
    public static int numberOfDaysInAMonth(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    public static int numberOfDaysInAGigienMonth(String date) {
        String conc[] = null;
        conc = date.split("-");
        int year = Integer.valueOf(conc[0]);
        int month = Integer.valueOf(conc[1]);        
        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    public static String getNameOfTheDay(String gregoryCalender) {
        try {
            Date now = new Date();
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
            now = dfm.parse(gregoryCalender);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d 'at' h:m:s a z");
            dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            dateFormatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            String nameOfTheDate = dateFormatter.format(now);
            return nameOfTheDate.substring(0, nameOfTheDate.indexOf(","));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
