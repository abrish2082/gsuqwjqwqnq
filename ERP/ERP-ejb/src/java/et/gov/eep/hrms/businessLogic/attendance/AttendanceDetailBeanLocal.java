/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.attendance;

import et.gov.eep.hrms.entity.attendance.HrAttendanceDetails;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface AttendanceDetailBeanLocal {

    public void saveorupdate(HrAttendanceDetails hrsttendanceDetails);
    
}
