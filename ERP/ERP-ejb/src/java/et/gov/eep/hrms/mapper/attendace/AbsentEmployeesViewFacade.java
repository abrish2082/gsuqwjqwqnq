/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.attendace;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.entity.attendance.AbsentEmployeesView;
import et.gov.eep.hrms.entity.attendance.HrAttendanceDetails;
import et.gov.eep.hrms.entity.attendance.HrAttendances;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class AbsentEmployeesViewFacade extends AbstractFacade<AbsentEmployeesView> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbsentEmployeesViewFacade() {
        super(AbsentEmployeesView.class);
    }

    public List findReport() {
        String month;
        String Shday[] = StringDateManipulation.toDayInEc().split("-");
        month = Shday[1];
        Query query = em.createNativeQuery("select h.first_name,h.middle_name, d.emp_id,d.no_of_days_absent,d.date_of_absence,d.reason_for_absence,d.late_minute\n"
                + "from HR_ATTENDANCES R INNER JOIN HR_ATTENDANCE_DETAILS D ON R.id=d.attendance_id inner join hr_employees h on h.id=d.emp_id\n"
                + "where r.MONTH='" + month + "'\n"
                + "AND r.status=3\n"
                + "group by h.first_name, h.middle_name, d.emp_id, d.no_of_days_absent,d.date_of_absence, d.reason_for_absence, d.late_minute  ORDER BY d.emp_id", AbsentEmployeesView.class);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

}
