/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.payroll;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.hrms.entity.payroll.HrPayrollEarningDeductions;
import et.gov.eep.hrms.mapper.payroll.HrPayrollEarningDeductionsFacade;

/**
 *
 * @author GLORY
 */
@Stateless
public class HrPayrollEarningDeductionsBean implements HrPayrollEarningDeductionsBeanLocal {

    @EJB
    HrPayrollEarningDeductionsFacade hrPayrollEarningDeductionsFacade;

    @Override
    public List<HrPayrollEarningDeductions> findAll() {
        return hrPayrollEarningDeductionsFacade.findAll();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadListOfEarnings() {
        return hrPayrollEarningDeductionsFacade.loadListOfDeductions();
    }

    @Override
    public HrPayrollEarningDeductions cheakItemCode(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollEarningDeductionsFacade.cheakItemCode(hrPayrollEarningDeductions);
    }

    @Override
    public void create(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        hrPayrollEarningDeductionsFacade.saveOrUpdate(hrPayrollEarningDeductions);
    }

    @Override
    public HrPayrollEarningDeductions cheakErningDeductionCriterias(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollEarningDeductionsFacade.cheakErningDeductionCriterias(hrPayrollEarningDeductions);
    }

    @Override
    public HrPayrollEarningDeductions cheakItemCodeForUpdate(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        return hrPayrollEarningDeductionsFacade.cheakItemCodeForUpdate(hrPayrollEarningDeductions);
    }

    @Override
    public void edit(HrPayrollEarningDeductions hrPayrollEarningDeductions) {
        hrPayrollEarningDeductionsFacade.edit(hrPayrollEarningDeductions);
    }

    @Override
    public List<HrPayrollEarningDeductions> loadListOfDeductions() {
        return hrPayrollEarningDeductionsFacade.loadListOfDeductions();
    }

    @Override
    public HrPayrollEarningDeductions find(Object id) {
        return hrPayrollEarningDeductionsFacade.find(id);
    }

    @Override
    public List<HrPayrollEarningDeductions> findRange(int[] range) {
        return hrPayrollEarningDeductionsFacade.findRange(range);
    }

    @Override
    public List<HrPayrollEarningDeductions> loadOnlyEarnings() {
        return hrPayrollEarningDeductionsFacade.loadOnlyEarnings();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadAllEar() {
        return hrPayrollEarningDeductionsFacade.loadAllEar();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadAllowancesForLocation() {
        return hrPayrollEarningDeductionsFacade.loadAllowancesForLocation();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadEdForLocation() {
        return hrPayrollEarningDeductionsFacade.loadEdForLocation();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadEdForJObTitle() {
        return hrPayrollEarningDeductionsFacade.loadEdForJObTitle();
    }

    @Override
    public List<HrPayrollEarningDeductions> listOfOtTypes() {
        return hrPayrollEarningDeductionsFacade.listOfOtTypes();
    }

    @Override
    public List<HrPayrollEarningDeductions> listOfEarnngForAllEmp() {
        return hrPayrollEarningDeductionsFacade.listOfEarnngForAllEmp();
    }

    @Override
    public List<HrPayrollEarningDeductions> listOfDeductionsForAllEmp() {
        return hrPayrollEarningDeductionsFacade.listOfDeductionsForAllEmp();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadAllEmployeesEd() {
        return hrPayrollEarningDeductionsFacade.loadAllEmployeesEd();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadEarningAndDeductions() {
        return hrPayrollEarningDeductionsFacade.loadEarningAndDeductions();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadOnlyDeductions() {
        return hrPayrollEarningDeductionsFacade.loadOnlyDeductions();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadAllEmpEarnings() {
        return hrPayrollEarningDeductionsFacade.loadOnlyDeductions();
    }

    @Override
    public List<HrPayrollEarningDeductions> loadAllEmpDeductions() {
        return hrPayrollEarningDeductionsFacade.loadAllEmpDeductions();
    }

    @Override
    public String returnNumberOfMonths(String sDate, String eDate) {
        return hrPayrollEarningDeductionsFacade.returnNumberOfMonths(sDate, eDate);
    }

    @Override
    public HrPayrollEarningDeductions findCriteriaInfo(String criteria) {
        return hrPayrollEarningDeductionsFacade.findCriteriaInfo(criteria);
    }

    @Override
    public int count() {
        return hrPayrollEarningDeductionsFacade.count();
    }

    @Override
    public List<HrPayrollEarningDeductions> taxTypeLists() {
        return hrPayrollEarningDeductionsFacade.taxTypeLists();
    }

    @Override
    public HrPayrollEarningDeductions getTaxType(HrPayrollEarningDeductions taxType) {
        return hrPayrollEarningDeductionsFacade.getTaxType(taxType);
    }

    @Override
    public HrPayrollEarningDeductions findByCode(HrPayrollEarningDeductions code) {
        return hrPayrollEarningDeductionsFacade.findByCode(code);
    }

}
