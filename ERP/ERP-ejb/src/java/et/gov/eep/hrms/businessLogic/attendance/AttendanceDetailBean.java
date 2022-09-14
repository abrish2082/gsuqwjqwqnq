/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.attendance;

import et.gov.eep.hrms.entity.attendance.HrAttendanceDetails;
import et.gov.eep.hrms.mapper.attendace.HrAttendanceDetailsFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author meles
 */
@Stateless
public class AttendanceDetailBean implements AttendanceDetailBeanLocal {
    @EJB
    HrAttendanceDetailsFacade hrAttendanceDetailsFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void saveorupdate(HrAttendanceDetails hrsttendanceDetails) {
       hrAttendanceDetailsFacade.edit(hrsttendanceDetails);
    }
}
