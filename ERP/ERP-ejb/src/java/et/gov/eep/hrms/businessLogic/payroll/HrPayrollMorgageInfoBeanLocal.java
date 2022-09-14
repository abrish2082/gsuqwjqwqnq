/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.entity.payroll.HrPayrollMorgageInfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author GLORY
 */
@Local
public interface HrPayrollMorgageInfoBeanLocal {

    /**
     *
     * @param hrPayrollMorgageInfo
     */
    void create(HrPayrollMorgageInfo hrPayrollMorgageInfo);

    /**
     *
     * @param hrPayrollMorgageInfo
     */
    void edit(HrPayrollMorgageInfo hrPayrollMorgageInfo);

    /**
     *
     * @param hrPayrollMorgageInfo
     */
    void remove(HrPayrollMorgageInfo hrPayrollMorgageInfo);

    /**
     *
     * @param id
     * @return
     */
    HrPayrollMorgageInfo find(Object id);

    /**
     *
     * @return
     */
    List<HrPayrollMorgageInfo> findAll();

    /**
     *
     * @param range
     * @return
     */
    List<HrPayrollMorgageInfo> findRange(int[] range);

    /**
     *
     * @return
     */
    int count();

    public int updateMortageStatus(HrEmployees emp, HrPayrollEarningDeductions ed);

    public List<HrPayrollMorgageInfo> findByEmpId(HrEmployees emp);
}
