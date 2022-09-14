/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.FmsTaxRegistration;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface fmsTaxRegistrationBeanLocal {

    /**
     *
     * @param taxRegister
     */
    public void edit(FmsTaxRegistration taxRegister);

    /**
     *
     * @param taxRegister
     */
    public void create(FmsTaxRegistration taxRegister);

    public List<FmsTaxRegistration> findAll();

    /**
     *
     * @param name
     * @return
     */
    public ArrayList<HrEmployees> searchEmployeeName(HrEmployees name);

    /**
     *
     * @param name
     * @return
     */
    public HrEmployees getEmployeeName(HrEmployees name);

    /**
     *
     * @param name
     * @return
     */
    public FmsTaxRegistration getEmployee(FmsTaxRegistration name);

    /**
     *
     * @param month
     * @param year
     * @return
     */
    public ArrayList<FmsTaxRegistration> getMonth(FmsTaxRegistration month, FmsLuBudgetYear year);

    /**
     *
     * @param year
     * @return
     */
    public FmsLuBudgetYear getYear(FmsLuBudgetYear year);

    /**
     *
     * @param year
     * @return
     */
    public ArrayList<FmsTaxRegistration> searcheMonth(FmsLuBudgetYear year);
}
