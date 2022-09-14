/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.FmsTaxRegistration;
import et.gov.eep.fcms.mapper.FmsLuBudgetYearFacade;
import et.gov.eep.fcms.mapper.FmsTaxRegistrationFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class fmsTaxRegistrationBean implements fmsTaxRegistrationBeanLocal {
    @EJB
    private FmsTaxRegistrationFacade fmsTaxRegistrationFacade;
    @EJB
    private FmsLuBudgetYearFacade fmsLuBudgetYearFacade;

    /**
     *
     * @param name
     * @return
     */
    @Override
    public ArrayList<HrEmployees> searchEmployeeName(HrEmployees name) {
       return fmsTaxRegistrationFacade.searchEmployeeName(name);
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public HrEmployees getEmployeeName(HrEmployees name) {
       return fmsTaxRegistrationFacade.getEmployeeName(name);
    }

    /**
     *
     * @param taxRegister
     */
    @Override
    public void edit(FmsTaxRegistration taxRegister) {
        fmsTaxRegistrationFacade.edit(taxRegister);
    }

    /**
     *
     * @param taxRegister
     */
    @Override
    public void create(FmsTaxRegistration taxRegister) {
        fmsTaxRegistrationFacade.create(taxRegister);
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public FmsTaxRegistration getEmployee(FmsTaxRegistration name) {
       return fmsTaxRegistrationFacade.getEmployee(name);
    }

    /**
     *
     * @param year
     * @return
     */
    @Override
    public FmsLuBudgetYear getYear(FmsLuBudgetYear year) {
       return fmsLuBudgetYearFacade.getYear(year);
    }

    /**
     *
     * @param year
     * @return
     */
    @Override
    public ArrayList<FmsTaxRegistration> searcheMonth(FmsLuBudgetYear year) {
       return fmsTaxRegistrationFacade.findMonth(year);
    }

    /**
     *
     * @param month
     * @param year
     * @return
     */
    @Override
    public ArrayList<FmsTaxRegistration> getMonth(FmsTaxRegistration month,FmsLuBudgetYear year) {
       return fmsTaxRegistrationFacade.getMonth(month,year);
    }

    @Override
    public List<FmsTaxRegistration> findAll() {
       return fmsTaxRegistrationFacade.findAll();
    }
}
