/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.utility.number;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author meles
 */
public class NumAndDateFormatter {

//    public NumAndDateFormatter(){}
    DecimalFormat format = new DecimalFormat("##.00");
    SimpleDateFormat dateFormat = new SimpleDateFormat();

    /**
     * format double value
     *
     * @param number
     * @return
     */
    public double decimalFormat(double number) {

        return Double.valueOf(format.format(number));
    }

    /**
     * format float value
     *
     * @param number
     * @return
     */
    public float decimalFormat(float number) {
        return Float.valueOf(format.format(number));
    }

    /**
     * format date
     *
     * @param dfs
     * @return
     */
    public String formatDate(DateFormatSymbols dfs) {
        return dateFormat.format(dfs);
    }

    public String returnDateBySeparatingChar(String date, String separator) {
        String[] dates = null;
        dates = date.split("/");
        return dates[0] + separator + dates[1] + separator + dates[2];

    }

}
