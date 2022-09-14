/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollFamiliesInfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollFamiliesInfoBeanLocal {

    /**
     *
     * @param hrPayrollFamiliesInfo
     */
    void create(HrPayrollFamiliesInfo hrPayrollFamiliesInfo);

    /**
     *
     * @param hrPayrollFamiliesInfo
     */
    void edit(HrPayrollFamiliesInfo hrPayrollFamiliesInfo);

    /**
     *
     * @param hrPayrollFamiliesInfo
     */
    void remove(HrPayrollFamiliesInfo hrPayrollFamiliesInfo);

    /**
     *
     * @param id
     * @return
     */
    HrPayrollFamiliesInfo find(Object id);

    /**
     *
     * @return
     */
    List<HrPayrollFamiliesInfo> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrPayrollFamiliesInfo> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    public int updateFamilyStatus(HrEmployees emp, HrPayrollEarningDeductions ed);

    public List<HrPayrollFamiliesInfo> findByEmpId(HrEmployees emp);
}
