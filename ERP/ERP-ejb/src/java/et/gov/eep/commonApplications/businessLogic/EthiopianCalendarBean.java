/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author mora1
 */
@Stateless
public class EthiopianCalendarBean implements EthiopianCalendarBeanLocal {

// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public String getEthiopianCurrentDate() {
        DateFormat edf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String currentYear = edf.format(now);
        String currentEC = StringDateManipulation.todayInEC(currentYear);
        String[] currentEYear = currentEC.split("-");
        String eDayOfMonth = currentEYear[2];
        String eMonthOfYear = currentEYear[1];
        String eYear = currentEYear[0];
        if (eMonthOfYear.equals("11") || eMonthOfYear.equals("12") || eMonthOfYear.equals("13")) {
            eYear += 1;
        }
        return eYear;
    }

}
