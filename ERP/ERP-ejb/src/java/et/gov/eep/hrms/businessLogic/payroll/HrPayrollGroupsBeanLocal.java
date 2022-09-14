/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.payroll.HrPayrollGroups;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollGroupsBeanLocal {

    void create(HrPayrollGroups hrPayrollGroups);

    void edit(HrPayrollGroups hrPayrollGroups);

    void remove(HrPayrollGroups hrPayrollGroups);

    HrPayrollGroups find(Object id);

    List<HrPayrollGroups> findAll();

    List<HrPayrollGroups> findRange(int[] range);

    int count();
}
