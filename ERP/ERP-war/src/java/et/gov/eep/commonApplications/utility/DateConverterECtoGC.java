/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility;

/**
 *
 * @author user
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author one1423
 */
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Administrator
 */
public class DateConverterECtoGC {

    /**
     * Creates a new instance of DateConverter
     */
    public DateConverterECtoGC() {
        init();
    }

    public static String fromGCToEC(String date) {  // date is in 'yyyy-mm-dd' format
        if (date != null) {
            init();
            Date givenDate, refDate;
            //DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            givenDate = Date.valueOf(date);
            refDate = Date.valueOf(String.valueOf(gRefYear) + "-" + String.valueOf(gRefMonth) + "-" + String.valueOf(gRefDate));
            long dateDiff = givenDate.getTime() - refDate.getTime();

            int inDays = (int) (dateDiff / (1000 * 60 * 60 * 24));
            moveYearsOnEC(inDays);
            moveMonthsOnEC(remainingDays);
            moveDaysOnEC(remainingDays);
            String _eMonth = eMonth < 10 ? "0" + String.valueOf(eMonth) : String.valueOf(eMonth);
            String _eDate = eDate < 10 ? "0" + String.valueOf(eDate) : String.valueOf(eDate);

            eEquDate = String.valueOf(eYear) + "-" + _eMonth + "-" + _eDate;
            return eEquDate;
        } else {
            return null;
        }
    }

    public static String fromECToGC(String date) {
        if (date != null) {
            init();
            //java.util.Date dd = new java.util.Date();
            Date refDate = Date.valueOf("2006-09-11");
            //Date tempDate = Date.valueOf("2006-09-11");
            long inMilis = refDate.getTime();
            long upto = getDateDifference("1999-1-1", date);
            upto = upto * (1000 * 60 * 60 * 24);
            inMilis += upto;
            Date tempDate = new Date(inMilis);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            gEquDate = format.format(tempDate);
            //gEquDate = format.format(new Date(inMilis));
            return gEquDate;
        } else {
            return null;
        }
    }

    private static int getDateDifference(String date1, String date2) {
// both date must be in the form 'yyyy-mm-dd'
        // the value of date1 is '1999-1-1'

        int diffInDays = 0, year1, month1, dates1, year2, month2, dates2;
        year1 = Integer.parseInt(date1.substring(0, 4));
        year2 = Integer.parseInt(date2.substring(0, 4));
        month1 = Integer.parseInt(date1.substring(date1.indexOf('-') + 1, date1.lastIndexOf('-')));
        month2 = Integer.parseInt(date2.substring(date2.indexOf('-') + 1, date2.lastIndexOf('-')));
        dates1 = Integer.parseInt(date1.substring(date1.lastIndexOf('-') + 1));
        dates2 = Integer.parseInt(date2.substring(date2.lastIndexOf('-') + 1));
        if (year1 < year2) {
            diffInDays = 1;
            diffInDays += ((year2 - year1) * 365) + ((year2 - year1) / 4);
        } else if (year1 > year2) {
            diffInDays = ((year2 - year1) * 365) + ((year2 - year1) / 4);
        }
        if (month1 != month2) {
            diffInDays += Math.abs(((month1 - month2)) * 30);
        }
        if (dates1 != dates2) {
            diffInDays += Math.abs(dates1 - dates2);
        }
        //diffInDays = ((year1 - year2) * 365 ) + ((month1 - month2) * 30) + ((dates1 - dates2));
        return diffInDays;
    }

    private static int moveYearsOnEC(int days) {
        int years = 0, yearFlag = 1;
        if (days >= 366) { // year 1999 is a leap year
            days = days - 366; // eliminate 1999
            years++;

            int leapYears = (days / 365) / 4;
            remainingDays = (days % 365) - leapYears;   //((days / 365) / 4);

            years += (days - leapYears) / 365;
            eYear = eRefYear + years;
        } else if (days < 0) {   // for the past
            //yearFlag = -1;
            days = days * -1;   // eleminate the negative
            int leapYear = days / (365 * 4);
            remainingDays = (days - leapYear) % 365; // - leapYear;
            years = ((days - leapYear) / 365) + 1;  // to start from the previous year
            if (remainingDays == 0) {
                years--;    // on date;
            } else {
                if ((eYear % 4) == 3) {
                    remainingDays = 366 - remainingDays;
                } else {
                    remainingDays = 365 - remainingDays;
                }
            }
            eYear = eRefYear - years;

        } else {
            eYear = eRefYear;
            remainingDays = days;
        }

        return eYear;
    }

    private static int moveMonthsOnEC(int days) {  // after calculating for years so days < 366
        int months = 0;
        months = days / 30;
        remainingDays = days % 30;
        //if(days > 360)
        //  months ++;

        eMonth = eRefMonth + months;
        return eMonth;
    }

    private static int moveDaysOnEC(int days) {    // after calculating for months so days < 30
        eDate = eRefDate + days;
        return eDate;
    }

    private static int moveYearsOnGC(int days) {
        int years = 0;
        if (days >= 366) { // year 1999 is a leap year
            remainingDays = (days % 365) - ((days / 365) / 4);
            years += (days / 365);
            if (remainingDays < 0) {
                years--;
                remainingDays += 365;   // if not leap year
            }
            gYear = gRefYear + years;
        } else if (days < 0) {
        }

        return gYear;
    }

    private static int moveMonthsOnGC(int days) { // after calculating for years so days < 366
        int months = 0;
        for (int i = 0; i < 12; i++) {
            if ((i == 0 || i == 2 || i == 7 || i == 9) && (int) (days / 30) > 0) {
                days -= 30;
            } else if ((i == 1 || i == 3 || i == 4 || i == 6 || i == 8 || i == 10 || i == 11) && (int) (days / 31) > 0) {
                days -= 31;
            } else if (i == 1) {
                if (gYear % 4 == 0 && (int) (days / 29) > 0) { // leap year
                    days -= 29;
                } else if ((int) (days / 28) > 0) {
                    days -= 28;
                }
            }
        }

        remainingDays = days;

        gMonth = gRefMonth + months;
        return gMonth;
    }

    private static int moveDaysOnGC(int days) { // after calculating for months so days < 31
        gDate = gRefDate + days;
        return gDate;
    }

    private static void init() {
        eRefYear = 1999;
        eRefMonth = 1;
        eRefDate = 1;
        gRefYear = 2006;
        gRefMonth = 9;
        gRefDate = 11;
        remainingDays = 0;
        eYear = 0;
        eMonth = 0;
        eDate = 0;
        gYear = 0;
        gMonth = 0;
        gDate = 0;
        String eEquDate = null;
        String gEquDate = null;

    }

    public int dateDiffrenceInGC(String date1, String date2) {
        java.util.Date gCal1 = convertDateToCalendar(date1).getTime();
        java.util.Date gCal2 = convertDateToCalendar(date2).getTime();
        long differrence = gCal2.getTime() - gCal1.getTime();
//    
        return (int) (differrence / (1000 * 60 * 60 * 24));
    }

    public Calendar convertDateToCalendar(String date) {
        try {
            String dateParameters[] = date.split("-");
            int year = Integer.parseInt(dateParameters[0]);
            int month = Integer.parseInt(dateParameters[1]);
            int day = Integer.parseInt(dateParameters[2]);

            return new GregorianCalendar(year, month, day);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String ar[]) {
//      
        //double age = 0.00;
//        DateConverter obj = new DateConverter();
////        DecimalFormat df = new DecimalFormat("#.00000");
////        df.format(age);
//
        int i = 0;
        int calculation = StringDateManipulation.compareDateDifference("1956-06-13", StringDateManipulation.toDayInEc());
        double calc = (double) calculation;
        for (int j = StringDateManipulation.getYear("1956-06-13"); j < StringDateManipulation.getYear(StringDateManipulation.toDayInEc()); j++) {
            if ((j % 4 == 0)) {
                i = i + 1;
            }
        }
        double k = (double) i;
        double diff = 365;//.0+k;

//        i = i + 365;
//        double j;
//        int k;
//        k=i;
//        j=(double)i;
//
//        age = calc / j;
//      //  age=Math.round(age);
//        Math.round(j);
//     // long name= (long)Math.floor(age + 0.9d);
//        String code=Double.toString(age);
//      //  code=code.substring(0,code.indexOf("."));
//       i=(int)age;
//               //intValue(age);
//        //String serviceYearCalculation=Double.toString(age);
//        //int index=serviceYearCalculation.indexOf(".");
//       // serviceYearCalculation.substring(index,index+1);
        //ystem.out.println("diffrence---" + obj.dateDiffrenceInGC(
        //    obj.fromECToGC("1979-11-29"), obj.fromECToGC(StringDateManipulation.toDayInEc())));
//        int calculation = obj.dateDiffrenceInGC(obj.fromECToGC("1979-11-29"), obj.fromECToGC(StringDateManipulation.toDayInEc()));
//        float dayes=0;
//       dayes= calculation / 365;
//      
//        String one = "12345";
//        int n = one.hashCode();
//        one = "1234";
//        n = one.hashCode();
//        one = "123";
//        n = one.hashCode();
        //46792755 46792755
    }

    public static boolean Test(double expected, double actual) {
        boolean result = expected == actual;
        if (!result) {
            //"Expected: " + expected + ", Actual: " + actual);
        }

        return result;
    }
    static int eYear, eMonth, eDate, gYear, gMonth, gDate;
    static int remainingDays;
    static int eRefYear = 1999, eRefMonth = 1, eRefDate = 1, gRefYear = 2006, gRefMonth = 9, gRefDate = 11;
    static String eEquDate, gEquDate;
}
