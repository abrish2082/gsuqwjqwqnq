/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.leave;

import et.gov.eep.hrms.entity.leave.HrLuHolidayNames;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrLuHolidayNamesBeanLocal {

    public List<HrLuHolidayNames> findAllHolidays();
    
}
